package com.bootdo.common.utils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// 数据导出  工具类
public class Excelutil {

	 /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static void getHSSFWorkbook(String sheetName, List<String> header, List<List<String>> body, OutputStream out){
    	  //新建excel报表
        HSSFWorkbook excel = new HSSFWorkbook();
        //添加一个sheet
        HSSFSheet hssfSheet = excel.createSheet(sheetName);
        //往excel表格创建一行，excel的行号是从0开始的
        // 设置表头
        HSSFRow firstRow = hssfSheet.createRow(0);
        for (int columnNum = 0; columnNum < header.size(); columnNum ++) {
            //创建单元格
            HSSFCell hssfCell = firstRow.createCell(columnNum);
            //设置单元格的值
            hssfCell.setCellValue(header.size() < columnNum ? "-" : header.get(columnNum));
        }
        // 设置主体数据
        for (int rowNum = 0; rowNum < body.size(); rowNum ++) {
            //往excel表格创建一行，excel的行号是从0开始的
            HSSFRow hssfRow = hssfSheet.createRow(rowNum + 1);
            List<String> data = body.get(rowNum);
            for (int columnNum = 0; columnNum < data.size(); columnNum ++) {
                //创建单元格
                HSSFCell hssfCell = hssfRow.createCell(columnNum);
                //设置单元格的值
                hssfCell.setCellValue(data.size() < columnNum ? "-" : data.get(columnNum));
            }
        }
        try {
            excel.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出文件   任意位置
    
}
