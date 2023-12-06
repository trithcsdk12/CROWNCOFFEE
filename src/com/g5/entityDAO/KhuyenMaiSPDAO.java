/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entityDAO;

import com.g5.DAO.KhuyenMaiSPDAOinterface;
import com.g5.entity.KhuyenMai;
import com.g5.entity.SanPhamKM;
import com.g5.util.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sora6
 */
public class KhuyenMaiSPDAO implements KhuyenMaiSPDAOinterface{
 String tableSP = "SELECT * FROM KhuyenMaiChiTiet KMCT JOIN SanPham SP ON KMCT.MaSP = SP.MaSP JOIN GiaSanPham GSP ON SP.MaSP = GSP.MaSP WHERE MaKM = ?;";
    
    
    public List<SanPhamKM> getByCondition(String sql, Object... args) {
    List<SanPhamKM> list = select(sql, args);
    return list;
}

    @Override
    public List<SanPhamKM> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer create(SanPhamKM entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(SanPhamKM entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deteleByID(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     private List<SanPhamKM> select(String sql, Object... args) {
        List<SanPhamKM> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    SanPhamKM model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
             //   rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private SanPhamKM readFromResultSet(ResultSet rs) throws SQLException {
        SanPhamKM model = new SanPhamKM();
        model.setMaSP(rs.getInt("MaSP"));
        model.setTenSP(rs.getString("TenSP"));
        model.setGia(rs.getFloat("Gia"));
        model.setMaKM(rs.getInt("MaKM"));
        model.setChietkhau(rs.getFloat("PTKhuyenMai"));
        return model;
    }

    @Override
    public SanPhamKM getByID(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
