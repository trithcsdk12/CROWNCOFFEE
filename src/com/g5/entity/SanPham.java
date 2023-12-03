/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entity;

/**
 *
 * @author Asus
 */
public class SanPham {

    private int maSP;
    private String tenSP;
    private int soLuong;
    private int MaNV;
    private String MoTa;
    private String Hinh;
    private String LoaiSP;
    private float giaNguyenLieu;

    public SanPham() {
    }

    public SanPham(int maSP, String tenSP, int soLuong, int MaNV, String MoTa, String Hinh, String LoaiSP, float giaNguyenLieu) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.MaNV = MaNV;
        this.MoTa = MoTa;
        this.Hinh = Hinh;
        this.LoaiSP = LoaiSP;
        this.giaNguyenLieu = giaNguyenLieu;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int MaNV) {
        this.MaNV = MaNV;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getLoaiSP() {
        return LoaiSP;
    }

    public void setLoaiSP(String LoaiSP) {
        this.LoaiSP = LoaiSP;
    }

    public float getGiaNguyenLieu() {
        return giaNguyenLieu;
    }

    public void setGiaNguyenLieu(float giaNguyenLieu) {
        this.giaNguyenLieu = giaNguyenLieu;
    }

}
