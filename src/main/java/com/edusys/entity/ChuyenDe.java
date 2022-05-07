/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.entity;



/**
 *
 * @author Minh Huy
 */
public class ChuyenDe {
    String MaCD;
    String TenCD;
    Double HocPhi;
    int ThoiLuong;
    String Hinh;
    String MoTa;

    public ChuyenDe(String MaCD, String TenCD, Double HocPhi, int ThoiLuong, String Hinh, String MoTa) {
        this.MaCD = MaCD;
        this.TenCD = TenCD;
        this.HocPhi = HocPhi;
        this.ThoiLuong = ThoiLuong;
        this.Hinh = Hinh;
        this.MoTa = MoTa;
    }

    public ChuyenDe() {
    }
       
    public String getMaCD() {
        return MaCD;
    }

    public void setMaCD(String MaCD) {
        this.MaCD = MaCD;
    }

    public String getTenCD() {
        return TenCD;
    }

    public void setTenCD(String TenCD) {
        this.TenCD = TenCD;
    }

    public Double getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(Double HocPhi) {
        this.HocPhi = HocPhi;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int ThoiLuong) {
        this.ThoiLuong = ThoiLuong;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
   
    @Override
    public String toString(){
    return this.TenCD;
    }
    
   
//    @Override
//    public boolean equals(Object obj){
//    ChuyenDe other=(ChuyenDe)obj;
//    return other.getMaCD().equals(this.MaCD);
//    }
    
    @Override
     public boolean equals(Object obj) {
        ChuyenDe other = (ChuyenDe) obj;
//        return other.getMaCD().equals(this.getMaCD());
        return other.getMaCD().equals(this.getMaCD());
    }

    
}
//    ChuyenDe other=(ChuyenDe)obj;
//    return other.getMaCD().equals(this.MaCD);