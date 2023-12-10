/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entityDAO;

import com.g5.DAO.KhuyenMaiDAOinterface;
import com.g5.entity.HoaDon;
import com.g5.entity.KhuyenMai;
import com.g5.util.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class KhuyenMaiDAO implements KhuyenMaiDAOinterface {

    String selectByID = "select * from KhuyenMai where MaKM = ?";
    String selectAll = "select * from KhuyenMai";
    String insert = "insert into KhuyenMai (ThoiGianBD,ThoiGianKT,TenKM,PTKhuyenMai,MaNV) "
            + "values (?,?,?,?,?)";
    String update = "Update KhuyenMai set ThoiGianBD=?, ThoiGianKT=?, TenKM=?, PTKhuyenMai=?, MaNV=? where MaKM =?";
    String delete = "Delete from KhuyenMai where MaKM = ?";
    String khuyenmai = "SELECT KM.PTKhuyenMai FROM KhuyenMaiChiTiet KMCT JOIN KhuyenMai KM ON KMCT.MaKM = KM.MaKM WHERE KMCT.MaSP = ?;";
    String timKM = "SELECT CASE WHEN GETDATE() BETWEEN km.ThoiGianBD AND km.ThoiGianKT THEN 1 ELSE 0 END AS TimThay FROM KhuyenMai km JOIN KhuyenMaiChiTiet kmct ON km.MaKM = kmct.MaKM WHERE kmct.MaSP = ?";
    String ptKMcuaSP = "select sum(km.PTKhuyenMai) as ptkm from KhuyenMaiChiTiet kmct join KhuyenMai km on kmct.MaKM = km.MaKM where kmct.MaSP = ? and GETDATE() BETWEEN km.ThoiGianBD AND km.ThoiGianKT";

    public float getPTKMcuaSP(int masp) {
        float ptkm = 0;
        ResultSet rs = null;
        try {
            rs = JDBCHelper.executeQuery(ptKMcuaSP, masp);
            rs.next();
            ptkm = rs.getFloat("ptkm");
            return ptkm;
        } catch (Exception e) {
            //  e.printStackTrace();
        }
        return 0;
    }

    public int getSPINKM(int masp) {
        ResultSet rs = null;
        try {
            rs = JDBCHelper.executeQuery(timKM, masp);
            rs.next();
            return rs.getInt("TimThay");
        } catch (Exception e) {
            //  e.printStackTrace();
        }
        return 0;
    }

    public float getKhuyenMai(int maSP) {
        try {
            ResultSet rs = JDBCHelper.executeQuery(khuyenmai, maSP);
            if (rs.next()) {
                return rs.getFloat("PTKhuyenMai");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1.0f;
    }

    @Override
    public KhuyenMai getByID(Integer id) {
        List<KhuyenMai> list = select(selectByID, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<KhuyenMai> getAll() {
        return select(selectAll);
    }

    @Override
    public Integer create(KhuyenMai km) {
        try {
            JDBCHelper.executeUpdate(insert,
                    km.getTGBatDau(),
                    km.getTGKetThuc(),
                    km.getTenKM(),
                    km.getPhanTramKM(),
                    km.getMaNV()
            );

            return km.getMaKM();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(KhuyenMai km) {
        JDBCHelper.executeUpdate(update,
                km.getTGBatDau(),
                km.getTGKetThuc(),
                km.getTenKM(),
                km.getPhanTramKM(),
                km.getMaNV(),
                km.getMaKM()
        );
    }

    @Override
    public void deteleByID(Integer id) {
        JDBCHelper.executeUpdate(delete, id);
    }

    private List<KhuyenMai> select(String sql, Object... args) {
        List<KhuyenMai> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    KhuyenMai model = readFromResultSet(rs);
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

    private KhuyenMai readFromResultSet(ResultSet rs) throws SQLException {
        KhuyenMai model = new KhuyenMai();
        model.setMaKM(rs.getInt("MaKM"));
        model.setTGBatDau(rs.getDate("ThoiGianBD"));
        model.setTGKetThuc(rs.getDate("ThoiGianKT"));
        model.setTenKM(rs.getString("TenKM"));
        model.setPhanTramKM(rs.getFloat("PTKhuyenMai"));
        model.setMaNV(rs.getInt("MaNV"));

        return model;
    }

}
