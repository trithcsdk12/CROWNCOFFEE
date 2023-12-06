/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entity;

/**
 *
 * @author sora6
 */
public class SanPhamKM {
    private int maSP;
    private String tenSP;
    private float Gia;
    private float GiaKM;
    private int maKM;
    private float chietkhau;
    public SanPhamKM() {
    }

    public SanPhamKM(int maSP, String tenSP, float Gia, float GiaKM, int maKM, float chietkhau) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.Gia = Gia;
        this.GiaKM = GiaKM;
        this.maKM = maKM;
        this.chietkhau = chietkhau;
    }

    public int getMaSP() {
        return maSP;
    }

    public float getChietkhau() {
        return chietkhau;
    }

    public void setChietkhau(float chietkhau) {
        this.chietkhau = chietkhau;
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

    public float getGia() {
        return Gia;
    }

    public void setGia(float Gia) {
        this.Gia = Gia;
    }

    public float getGiaKM() {
        return GiaKM;
    }

    public void setGiaKM(float GiaKM) {
        this.GiaKM = GiaKM;
    }

    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }
    
    
}
