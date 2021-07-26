package com.lovecredit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class IOExcel {

    public static void saveAsExcelFile(HashMap hashMap) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("ForegroundChecker-Window->Duration");
        Set<String> keySet = hashMap.keySet();
        int rowNumber = 0;
        for (String key: keySet) {
            Row row = sheet.createRow(rowNumber++);
            Object obj = hashMap.get(key);
            int cellnum = 0;
            Cell cell = row.createCell(cellnum++);
            cell.setCellValue(key);
            cell = row.createCell(cellnum++);
            if (obj instanceof Date) {
                cell.setCellValue((Date) obj);
            } else if (obj instanceof Boolean) {
                cell.setCellValue((Boolean) obj);
            } else if (obj instanceof String) {
                cell.setCellValue((String) obj);
            } else if (obj instanceof Double) {
                cell.setCellValue((Double) obj);
            } else if (obj instanceof Integer) {
                cell.setCellValue((int) obj);
            }
        }
        try {
            String dateTime = new SimpleDateFormat("yyMMddHHmm").format(new Date());
            String filePath = System.getProperty("user.dir") + "\\ForegroundChecker" + dateTime + ".xls";
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
