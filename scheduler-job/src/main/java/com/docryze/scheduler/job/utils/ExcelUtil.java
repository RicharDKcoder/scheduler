package com.docryze.scheduler.job.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    private static final int MAX_ROW_LIMIT = 500;

    /**
     * 生成excel文件
     * @param titles        表头
     * @param context       内容
     * @param path          保存路径
     * @throws IOException
     */
    public static String generateSqlResExcel(String[] titles, List<Map<String,Object>> context, String path, String fileName) throws IOException {
        Workbook wb = null;
        OutputStream fileOut = null;
        try {
            wb = new SXSSFWorkbook(MAX_ROW_LIMIT);

            String finalFileName = checkFilePath(path, fileName);

            fileOut = new BufferedOutputStream(new FileOutputStream(finalFileName));
            Sheet singleSheet = wb.createSheet();
            int rowNum = 0;
            //创建表头
            Row row = null;
            Map<String, Object> firstRow =  context.get(0);
            if(ArrayUtils.isNotEmpty(titles) && titles.length == firstRow.size()){
                row = singleSheet.createRow(rowNum);
                rowNum++;
                for(int i = 0; i < titles.length; i++){
                    row.createCell(i).setCellValue(titles[i]);
                }
            }else{
                String[] defaultTitles = firstRow.keySet().toArray(new String[]{});
                row = singleSheet.createRow(rowNum);
                rowNum++;
                for(int i = 0; i < defaultTitles.length; i++){
                    row.createCell(i).setCellValue(defaultTitles[i]);
                }
            }

            String[] keyOrderArray = context.get(0).keySet().toArray(new String[]{});

            //填充内容
            for(Map<String,Object> cellMap : context){
                row = singleSheet.createRow(rowNum);
                rowNum++;
                for(int i = 0; i < cellMap.size(); i++){
                    row.createCell(i).setCellValue(String.valueOf(cellMap.get(keyOrderArray[i])));
                }
            }

            wb.write(fileOut);


            return fileName;
        } finally {
            if(fileOut != null) fileOut.close();

            if(wb != null) wb.close();
        }


    }

    private static String checkFilePath(String path, String fileName) {
        if(StringUtils.endsWith(path,"/")){
            fileName = path + fileName;
        }else{
            fileName = path + "/" + fileName;
        }

        if (!fileName.endsWith(".xlsx")){
            fileName = fileName + ".xlsx";
        }
        return fileName;
    }

    public static void main(String[] args) {
//        generateSqlResExcel();
    }
}
