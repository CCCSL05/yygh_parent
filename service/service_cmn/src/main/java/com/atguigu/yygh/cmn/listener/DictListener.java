package com.atguigu.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

public class DictListener extends AnalysisEventListener<DictEeVo> {

    //在监听器里使用mapper 要通过构造方法从service那里获取baseMapper
    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        //从excel中读到的是dictEeVo类型 要转成dict
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict); //从文件读到的数据 写入dict数据表中
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
