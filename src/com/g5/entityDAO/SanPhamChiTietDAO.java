/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entityDAO;

import com.g5.entity.GiaSP;
import com.g5.entity.SanPham;
import com.g5.util.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trith
 */
public class SanPhamChiTietDAO {

    String selectAll = "select * from GiaSanPham";
    String selectByID = "select * from giasanpham  where MaSP = ?";
    String selectBySize = "select * from GiaSanPham  where MaSP = ? and Size = '?'";
    String insert = "insert into GiaSanPham (MaSP,Size,Gia)"
            + "values (?,?,?)";
    String update = "Update GiaSanPham set Size = ?,Gia = ? where MaGSP = ?";
    String delete = "Delete from GiaSanPham where MaSP = ? and Size = ?";
    String count = "Select Count(MaGSP) as SoLuong from GiaSanPham";
    String deleteByMaSP = "Delete from GiaSanPham where MaSP = ?";
    String resetIdentity = "DBCC CHECKIDENT (GiaSanPham,RESEED,?)";
    
    SanPhamDao spDAO = new SanPhamDao();

    public List<GiaSP> countID() {
        return this.select(count);
    }

    public GiaSP getByID(Integer maSP) {
        List<GiaSP> list = select(selectByID, maSP);
        return list.size() > 0 ? list.get(0) : null;
    }

    public GiaSP getBySize(Integer masp, String Size) {
        List<GiaSP> list = select(selectBySize, masp, Size);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<GiaSP> selectByID(Integer maSP) {
        return this.select(selectByID, maSP);
    }

    private List<GiaSP> select(String sql, Object... args) {
        List<GiaSP> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    GiaSP model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                //rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private GiaSP readFromResultSet(ResultSet rs) throws SQLException {
        GiaSP model = new GiaSP();
        model.setMaGSP(rs.getInt("MaGSP"));
        model.setMaSP(rs.getInt("MaSP"));
        model.setSize(rs.getString("Size"));
        model.setGia(rs.getFloat("Gia"));
        return model;
    }

     public void resetIdentity(int colum) {
        JDBCHelper.executeUpdate(resetIdentity, colum);
    }
    
    public List<GiaSP> getAll() {
        return select(selectAll);
    }

    public Integer create(GiaSP sp) {
        try {
            JDBCHelper.executeUpdate(insert,
                    sp.getMaSP(),
                    sp.getSize(),
                    sp.getGia()
            );
            return sp.getMaSP();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(GiaSP sp) {
        JDBCHelper.executeUpdate(update,
                sp.getSize(),
                sp.getGia(),
                sp.getMaGSP()
        );
    }

    public void deteleByID(int maSP, String size) {
        JDBCHelper.executeUpdate(delete, maSP, size);
    }

    public void deteleByMaSP(int maSP) {
        JDBCHelper.executeUpdate(deleteByMaSP, maSP);
    }
}
