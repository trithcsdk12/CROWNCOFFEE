/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entityDAO;

import com.g5.util.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TruyVanBieuDo {

    public List<Object[]> getBieuDo(int ThoiGian) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_thongkebieudo(?) }";
                rs = JDBCHelper.executeQuery(sql, ThoiGian);
                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("thang1"),
                        rs.getInt("thang2"),
                        rs.getInt("thang3"),
                        rs.getInt("thang4"),
                        rs.getInt("thang5"),
                        rs.getInt("thang6"),
                        rs.getInt("thang7"),
                        rs.getInt("thang8"),
                        rs.getInt("thang9"),
                        rs.getInt("thang10"),
                        rs.getInt("thang11"),
                        rs.getInt("thang12")

                    };
                    list.add(model);
                }
            } finally {
                if(rs != null){
                    rs.close();
                }
//                rs.getStatement().getConnection().close();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
