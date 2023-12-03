/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.entity;

/**
 *
 * @author trith
 */
public class GiaSP {

    private int MaGSP;
    private int MaSP;
    private String Size;
    private float Gia;

    public GiaSP() {
    }

    public GiaSP(int MaGSP, int MaSP, String Size, float Gia) {
        this.MaGSP = MaGSP;
        this.MaSP = MaSP;
        this.Size = Size;
        this.Gia = Gia;
    }

    public int getMaGSP() {
        return MaGSP;
    }

    public void setMaGSP(int MaGSP) {
        this.MaGSP = MaGSP;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int MaSP) {
        this.MaSP = MaSP;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public float getGia() {
        return Gia;
    }

    public void setGia(float Gia) {
        this.Gia = Gia;
    }

}
