package com.atguigu.eastexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        String fileName="E:\\testfile\\excel\\01.xlsx";
        EasyExcel.read(fileName,UserData.class,new ExcelListener()).sheet().doRead();
    }
}
