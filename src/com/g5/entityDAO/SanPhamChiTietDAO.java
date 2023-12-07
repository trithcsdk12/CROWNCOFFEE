/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entityDAO;

import com.g5.entity.GiaSP;
import com.g5.entity.SanPham;
import com.g5.util.JDBCHelper;
import com.g5.util.TextMes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    String update = "Update GiaSanPham set Gia = ? where Size = ? and MaSP = ?";
    String delete = "Delete from GiaSanPham where MaSP = ? and Size = ?";
    String count = "Select Count(MaGSP) as SoLuong from GiaSanPham";
    String deleteByMaSP = "Delete from GiaSanPham where MaSP = ?";
    String resetIdentity = "DBCC CHECKIDENT (GiaSanPham,RESEED,?)";
    String check = "SELECT CASE WHEN size = ? THEN 1 ELSE 0 END AS timkiem FROM GiaSanPham where masp = ?";
    String slGiaSP = "SELECT COUNT(*) AS count FROM GiaSanPham WHERE masp = ?";
    String kiemtratensp = "SELECT case when exists  ( select * FROM SanPham WHERE tensp = ? ) then 1 else 0 end as soluong";
    String selectMaxMaGSP = "select max(MaGSP) as Max from Giasanpham";

    SanPhamDao spDAO = new SanPhamDao();

    public int getMaxGiaSP() {
        ResultSet rs = null;
        try {
            rs = JDBCHelper.executeQuery(selectMaxMaGSP);
            rs.next();
            return rs.getInt("Max");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tensp(String tensp) {
        ResultSet rs = null;
        try {
            rs = JDBCHelper.executeQuery(kiemtratensp, tensp);
            rs.next();
            return rs.getInt("soluong");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSLGiaSP(int masp) {
        ResultSet rs = null;
        try {
            rs = JDBCHelper.executeQuery(slGiaSP, masp);
            rs.next();
            return rs.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Object[]> check(String size, int masp) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;

            try {

                rs = JDBCHelper.executeQuery(check, size, masp);
                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("timkiem")
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
                sp.getGia(),
                sp.getSize(),
                sp.getMaSP()
        );
    }

    public void deteleByID(int maSP, String size) {
        JDBCHelper.executeUpdate(delete, maSP, size);
    }

    public void deteleByMaSP(int maSP) {
        JDBCHelper.executeUpdate(deleteByMaSP, maSP);
    }
}
