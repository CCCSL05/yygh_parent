package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
public class HospitalSetController {

    // 注入service
    @Autowired
    private HospitalSetService hospitalSetService;

    // http://localhost:8201/admin/hosp/hospitalSet/findAll

    // 1.查询医院设置表所有信息
    @ApiOperation("1 获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospitalSet() {
        // 调用service的方法
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list); // 返回json数据
    }

    // 2 逻辑删除医院设置
    @ApiOperation("2 逻辑删除医院设置")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag == true) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 3 条件查询带分页
    @ApiOperation("3 条件查询带分页")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable Long current,
                                  @PathVariable Long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();//医院名称
        String hoscode = hospitalSetQueryVo.getHoscode();//医院编号
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }
        //调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);
        //返回结果
        return Result.ok(pageHospitalSet);

    }

    // 4 添加医院设置
    @ApiOperation("4 添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        // 设置状态1使用 0不能使用
        hospitalSet.setStatus(1);
        // 签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() +
                "" + random.nextInt(1000)));
        // 调用service 添加数据
        boolean flag = hospitalSetService.save(hospitalSet);
        if (flag) {
            return Result.ok(flag);
        } else {
            return Result.fail(flag);
        }
    }
    // 5 根据id获取医院设置
    @ApiOperation("5 根据id获取医院设置")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id){
        //模拟异常
//        try{
//            int a=1/0;
//        }catch (Exception e){
//            throw new YyghException("失败了",202);
//        }

        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }
    // 6 修改医院设置
    @ApiOperation("6 修改医院设置")
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok(flag);
        } else {
            return Result.fail(flag);
        }
    }

    // 7 批量删除医院设置
    @ApiOperation("7 批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList){
        boolean b = hospitalSetService.removeByIds(idList);
        return Result.ok(b);
    }

    // 8 医院设置锁定和解锁
    @ApiOperation("8 医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status){
        // 根据id查询医院 设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        // 设置状态
        hospitalSet.setStatus(status);
        // 调用方法
        boolean b = hospitalSetService.updateById(hospitalSet);
        return Result.ok(b);
    }
    // 9 发送签名密钥
    @ApiOperation("9 发送签名密钥")
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }


}
