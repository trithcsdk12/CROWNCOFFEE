/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.util;

import com.g5.entityDAO.HoaDonDAO;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {

    public static Workbook printToLichSuHD(JTable tblBangDiem, HoaDonDAO hd) throws FileNotFoundException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Người học");

        List<Object[]> list = hd.getLichSuHDDaThanhToan();
        int rownum = 0;
        Cell cell;
        Row row;

        // Style for title
        HSSFCellStyle style = createStyleForTitle(workbook);

        // Header row
        row = sheet.createRow(rownum);
        createCell(row, 0, "STT", style);
        createCell(row, 1, "Trạng Thái", style);
        createCell(row, 2, "Mã hóa đơn", style);
        createCell(row, 3, "Ngày Tạo", style);
        createCell(row, 4, "Mã nhân viên", style);
        createCell(row, 5, "Tổng tiền", style);
        createCell(row, 6, "Tiền khách trả", style);
        createCell(row, 7, "Ghi chú", style);

        // Data
        for (int i = 0; i < list.size(); i++) {
            rownum++;
            row = sheet.createRow(rownum);
            System.out.println(i);
            createCell(row, 0, getStringValue(tblBangDiem, i, 0), null);
            createCell(row, 1, getStringValue(tblBangDiem, i, 1), null);
            createCell(row, 2, getStringValue(tblBangDiem, i, 2), null);
            createCell(row, 3, getStringValue(tblBangDiem, i, 3), null);
            createCell(row, 4, getStringValue(tblBangDiem, i, 4), null);
            createCell(row, 5, getStringValue(tblBangDiem, i, 5), null);
            createCell(row, 6, getStringValue(tblBangDiem, i, 6), null);
            createCell(row, 7, getStringValue(tblBangDiem, i, 7), null);
        }
        return workbook;
    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    private static void createCell(Row row, int columnIndex, String value, HSSFCellStyle style) {
        Cell cell = row.createCell(columnIndex, CellType.STRING);
        cell.setCellValue(value != null ? value : "");
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    private static String getStringValue(JTable table, int row, int column) {
        Object value = table.getValueAt(row, column);
   //     System.out.println(row);
//        System.out.println(value);
        return value != null ? value.toString() : "";
    }

}
