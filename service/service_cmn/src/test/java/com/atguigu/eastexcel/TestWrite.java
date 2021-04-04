package com.atguigu.eastexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;

public class TestWrite {
    public static void main(String[] args) {
        //构件数据list列表
        ArrayList<UserData> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            UserData data = new UserData();
            data.setUid(i);
            data.setUserName("user"+i);
            list.add(data);//添加一个又一个UserData对象
        }

        //设置excel文件路径和文件名称
        String fileName="E:\\testfile\\excel\\01.xlsx";
        //调用方法实现写操作
        EasyExcel.write(fileName,UserData.class).sheet("用户信息").doWrite(list);
    }
}
