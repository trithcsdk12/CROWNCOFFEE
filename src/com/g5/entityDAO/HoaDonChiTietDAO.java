/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entityDAO;

import com.g5.entity.HoaDon;
import com.g5.entity.HoaDonChiTiet;
import com.g5.util.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class HoaDonChiTietDAO {

    String selectByID = "select * from HoaDonChiTiet where MaHD = ?";
    String selectALL = "select * from HoaDonChiTiet";
    String insert = "insert into HoaDonChiTiet (MaHD, MaSP, SoLuong, Gia, Size, PTKhuyenMai) "
            + "values (?,?,?,?,?,?)";
    String update = "Update HoaDonChiTiet set MaHD=?, MaSP=?, SoLuong=?, Gia=?, Size=?, PTKhuyenMai=? where MaSP =?";
    String selectByIDHD = "select * from HoaDonChiTiet where MaSP = ?";
    String delete = "Delete from HoaDonChiTiet where MaSP = ?";
    String spTrung = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS TimThay FROM HoaDonChiTiet hdct WHERE hdct.MaSP = ? AND hdct.Size = ?";

    public int getSPbiTrung(int masp, String size) {
        ResultSet rs = null;
        try {
            rs = JDBCHelper.executeQuery(spTrung, masp, size.trim());
            rs.next();
            return rs.getInt("TimThay");
        } catch (Exception e) {
        //    e.printStackTrace();
        }
        return 0;
    }

    public List<HoaDonChiTiet> getByID(int MaHD) {
        return select(selectByID, MaHD);

    }

    public HoaDonChiTiet getByIDHD(Integer id) {
        List<HoaDonChiTiet> list = select(selectByID, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public HoaDonChiTiet getByIDSP(Integer id) {
        List<HoaDonChiTiet> list = select(selectByIDHD, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public Integer create(HoaDonChiTiet hd) {

        try {
            JDBCHelper.executeUpdate(insert,
                    hd.getMaHD(),
                    hd.getMaSP(),
                    hd.getSoluong(),
                    hd.getGia(),
                    hd.getSize(),
                    hd.getPTkhuyenmai()
            );
            return hd.getMaHDCT();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(HoaDonChiTiet hd) {

        try {
            JDBCHelper.executeUpdate(update,
                    hd.getMaHD(),
                    hd.getMaSP(),
                    hd.getSoluong(),
                    hd.getGia(),
                    hd.getSize(),
                    hd.getPTkhuyenmai(),
                    hd.getMaSP()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deteleByID(Integer id) {
        JDBCHelper.executeUpdate(delete, id);
    }

    public List<HoaDonChiTiet> getALL() {
        return select(selectALL);
    }

    private List<HoaDonChiTiet> select(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDonChiTiet model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                //   rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }

    private HoaDonChiTiet readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonChiTiet model = new HoaDonChiTiet();
        model.setMaHD(rs.getInt("MaHD"));
        model.setMaSP(rs.getInt("MaSP"));
        model.setSoluong(rs.getInt("SoLuong"));
        model.setGia(rs.getFloat("Gia"));
        model.setSize(rs.getString("Size"));
        model.setPTkhuyenmai(rs.getFloat("PTKhuyenMai"));
        return model;
    }
}
