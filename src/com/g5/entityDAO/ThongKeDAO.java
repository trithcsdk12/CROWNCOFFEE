/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entityDAO;

import com.g5.util.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThongKeDAO {

    public List<Object[]> getThongKeDoanhThu(Date ThoiGianBatDau, Date ThoiGianKetThuc) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String sql = "{call sp_ThongKeDoanhThu (?,?)}";
                rs = JDBCHelper.executeQuery(sql, ThoiGianBatDau, ThoiGianKetThuc);
                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("soluong"),
                        rs.getDouble("tienhaphang"),
                        rs.getDouble("tiecaonhat"),
                        rs.getDouble("tienthapnhat"),
                        rs.getDouble("tientb"),
                        rs.getDouble("tongtien"),
                        rs.getDouble("loinhuan")
                    };
                    list.add(model);
                }
            } finally {
//                rs.getStatement().getConnection().close();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Object[]> getThongKeSanPham() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeSanPham}";
                rs = JDBCHelper.executeQuery(sql);
                int i = 0;

                while (rs.next()) {

                    Object[] model = {
                        ++i,
                        rs.getString("TenSanPham"),
                        rs.getInt("SoLuongNhapVao"),
                        rs.getInt("SoLuongTonKho"),
                        rs.getInt("SoLuongBanRa"),};
                    list.add(model);
                }
            } finally {
                //     rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
