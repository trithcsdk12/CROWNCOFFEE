/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.form;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Asus
 */
public class NewClass {

    public static void main(String[] args) {
        try {
            // Tạo một đối tượng Document
            Document document = new Document();

            // Chọn nơi lưu trữ file PDF và tạo một đối tượng PdfWriter
            PdfWriter.getInstance(document, new FileOutputStream("invoice.pdf"));

            // Mở Document để bắt đầu viết vào nó
            document.open();

            // Thiết lập font Times New Roman với hỗ trợ Unicode
            BaseFont timesRoman = BaseFont.createFont("D:/FPT Polytechnic/DuAn1/Hedvig_Letters_Serif/HedvigLettersSerif-Regular-VariableFont_opsz.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(timesRoman, 12);
            System.out.println(font);

            // Thêm nội dung vào Document (trong trường hợp này là một đoạn văn bản)
            document.add(new Paragraph("<html><font style='text-align: center;'><h1>HÓA ĐƠN BÁN HÀNG</h1></font></html>", font));
            document.add(new Paragraph("Ngày: 2023-12-04", font));
            document.add(new Paragraph("Tên sản phẩm: ABC", font));
            document.add(new Paragraph("Số lượng: 2", font));
            document.add(new Paragraph("Đơn giá: $10", font));
            document.add(new Paragraph("Tổng cộng: $20", font));

            // Đóng Document sau khi đã thêm nội dung
            document.close();

            System.out.println("Hóa đơn đã được tạo thành công bằng iText.");

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}
