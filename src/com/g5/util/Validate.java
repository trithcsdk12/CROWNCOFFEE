/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author anhba
 */
public class Validate {

    public static void main(String[] args) {

    }

    public static boolean kiemtratuoi(int day, int month, int year, int tuoilonhon) {
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Tạo LocalDate từ ngày tháng năm sinh
        LocalDate birthDate = LocalDate.of(year, month, day);

        // Tính tuổi
        Period age = Period.between(birthDate, currentDate);
        if (age.getYears() < tuoilonhon) {
            TextMes.Alert(null, "Tuổi phải lớn hơn " + tuoilonhon);
            return false;
        }
        // Kiểm tra tuổi có lớn hơn 16 hay không
        return true;
    }

    public static String chuyenGiaVietNam(float gia) {
        // Tạo một đối tượng NumberFormat với Locale là Vietnam
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Định dạng số thành chuỗi
        String formattedNumber = currencyFormat.format(gia);
        return formattedNumber;
    }

    public static String chuyenGiaMacDinh(String gia) {
        int intValue = 0;
        // Tạo một đối tượng NumberFormat với Locale là Vietnam
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        try {
            Number parsedNumber = currencyFormat.parse(gia.trim());

            // Chuyển đối số Number thành số nguyên
            intValue = parsedNumber.intValue();
            
            return String.valueOf(intValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Định dạng số thành chuỗi
        
        return String.valueOf(intValue);
    }

    public static boolean kiemTraEmail(String email) {
        // Biểu thức chính quy kiểm tra xem có ký tự @ hay không
        String regex = ".*@.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            TextMes.Alert(null, "Email không hợp lệ");
            return false;
        }
        return true;
    }

    public static boolean kiemTraSDT(String phoneNumber) {
        // Biểu thức chính quy kiểm tra số điện thoại có đúng 10 chữ số hay không
        String regex = "^\\d{9,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            TextMes.Alert(null, "SDT phải từ 10 số");
            return false;
        }
        return true;
    }

    public static boolean kiemTraMa(String input) {
        if (input.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean kiemTraSo(String soluong) {
        if (soluong.isEmpty()) {
            return false;
        }
        return true;

    }

    public static boolean kiemTraTrongText(List<JTextField> text, List<JPasswordField> pass) {
        //// Đầu tiên tạo List<JTextField> textFieldList = List.of(textField1, textField2, textField3);
        //// List.of(textField1, textField2, textField3); -> trong ngoặc (textfield số 1 , textfiled số 2, ....)
        //// Truyền vào hết textfiled cần kiểm tra 
        //// List<JTextField> textFieldList = List.of(textField1, textField2, textField3);
        ////  
        ////    
        ////    tạo List<JTextField> textFieldList = List.of(textField1, textField2, textField3);
        ////    gọi hàm vào truyền textFieldList vào
        ////    if(!kiemTraTrong(textFieldList)){
        ////    return;
        ////    }
        ////
        if (pass != null) {
            for (JPasswordField textField : pass) {
                if (textField.getText().trim().isEmpty()) {
                    TextMes.Alert(null, "Vui lòng nhập đầy đủ thông tin");
                    return false;
                }
            }
        }

        if (text != null) {
            for (JTextField textField : text) {
                if (textField.getText().trim().isEmpty()) {
                    TextMes.Alert(null, "Vui lòng nhập đầy đủ thông tin");
                    return false;
                }
            }
        }

        return true;
    }
}
