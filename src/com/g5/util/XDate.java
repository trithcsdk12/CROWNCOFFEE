package com.g5.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XDate {

    static SimpleDateFormat formater = new SimpleDateFormat();

    static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Chuyển đổi String sang Date
     *
     * @param date là String cần chuyển
     * @param pattern là định dạng thời gian
     * @return Date kết quả
     */
    public static Date toDate(String date, String pattern) {
        try {
            formater.applyPattern(pattern);
            return formater.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Date toDate2(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            DATE_FORMATER.applyPattern(pattern);
            return format.parse(date);
        } catch (ParseException ex) {

            throw new RuntimeException(ex);
        }
    }

    public static String chuyenDinhDangNgay(String ngay1, String format) {
        DateFormat inputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        DateFormat outputFormat = new SimpleDateFormat(format);

        try {
            // Chuyển đổi ngày đầu vào thành đối tượng Date
            Date date = inputFormat.parse(ngay1);

            // Chuyển đổi đối tượng Date thành chuỗi ngày mới
            String ngayMoi = outputFormat.format(date);

            return ngayMoi;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getToDay() {
        formater.applyPattern("dd/MM/yyyy");

        return formater.format(new Date());
    }

    /**
     * Chuyển đổi từ Date sang String
     *
     * @param date là Date cần chuyển đổi
     * @param pattern là định dạng thời gian
     * @return String kết quả
     */
    public static String toString(Date date, String pattern) {
        formater.applyPattern(pattern);
        return formater.format(date);
    }

    /**
     * Bổ sung số ngày vào thời gian
     *
     * @param date thời gian hiện có
     * @param days số ngày cần bổ sung váo date
     * @return Date kết quả
     */
    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    public static String ChuyenNgay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (date == null) {
            return formatter.format(XDate.now());
        }
        String strDate = formatter.format(date);
        return strDate;
    }

    public static String ChuyenNgay2(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        if (date == null) {
            return formatter.format(XDate.now());
        }
        String strDate = formatter.format(date);
        return strDate;
    }

    public static Date now() {

        return new Date();
    }
}
