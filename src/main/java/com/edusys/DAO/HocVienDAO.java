/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.entity.HocVien;
import com.edusys.helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minh Huy
 */
public class HocVienDAO extends EdusysDAO<HocVien,Integer>{
String INSERT_SQL="INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
String UPDATE_SQL="UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
String DELETE_SQL="DELETE FROM HocVien WHERE MaHV=?";
String SELECT_ALL_SQL="SELECT * FROM HocVien";
String SELECT_BY_ID_SQL="SELECT * FROM HocVien WHERE MaHV=?";

    @Override
    public void insert(HocVien e) {
    try {
        JdbcHelper.executeUpdate(INSERT_SQL,e.getMaKH(),e.getMaNH(),e.getDiem());
    } catch (SQLException ex) {
       ex.printStackTrace();
    }
    }

    @Override
    public void update(HocVien e) {
    try {
        JdbcHelper.executeUpdate(UPDATE_SQL,e.getMaKH(),e.getMaNH(),e.getDiem(),e.getMaHV());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public void delete(Integer key) {
    try {
        JdbcHelper.executeUpdate(DELETE_SQL,key);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public HocVien selectByID(Integer key) {
        List<HocVien> ls=this.selectBySQL(SELECT_BY_ID_SQL,key);
        if(ls.isEmpty()){
        return null;
        }else{
        return ls.get(0);
        }
    }

    @Override
    protected List<HocVien> selectBySQL(String sql, Object... args) {
        List<HocVien> ls=new ArrayList<>();
        try {
            ResultSet rs=JdbcHelper.executeQuery(sql, args);
            while(rs.next()){
            HocVien hv=new HocVien();
            hv.setMaHV(rs.getInt("MaHV"));
            hv.setMaKH(rs.getInt("MaKH"));
            hv.setMaNH(rs.getString("MaNH"));
            hv.setDiem(rs.getDouble("Diem"));
            ls.add(hv);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public List<HocVien> selectSQL(String sql,Object...args) {
    List<HocVien> ls=new ArrayList<>();
        try {
            ResultSet rs=JdbcHelper.executeQuery(sql, args);
            while(rs.next()){
            HocVien hv=new HocVien();
            hv.setMaHV(rs.getInt("MaHV"));
            hv.setMaNH(rs.getString("MaNH"));
            hv.setDiem(rs.getDouble("Diem"));
            ls.add(hv);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    } 
    
    
    public List<HocVien> selectByKhoaHoc(int maKH){
    String sql="select * FROM HocVien where MaKH=?";
    return this.selectBySQL(sql,maKH);
    }
    
    public List<HocVien> selectbyChuaNhapDiem(int maKH){
    String sql="select * from HocVien where diem<=0 and MaKH=?";
    return this.selectBySQL(sql, maKH);
    }
    public List<HocVien> selectbyDaNhapDiem(int maKH){
    String sql="select * from HocVien where diem>0 and MaKH=?";
    return this.selectBySQL(sql, maKH);
    }
    public List<HocVien> sortNameCND(int maKH) {
        String sql = "select * from HocVien inner join NguoiHoc \n"
                + "on HocVien.MaNH=NguoiHoc.MaNH where diem<=0 and HocVien.MaKH=? order by\n"
                + "(RIGHT(HoTen,CHARINDEX(' ',REVERSE(HoTen))))";
        return this.selectBySQL(sql, maKH);
    }
    public List<HocVien> sortPointDND(int maKH){
    String sql = "select * from HocVien inner join NguoiHoc \n"
                + "on HocVien.MaNH=NguoiHoc.MaNH where diem>0 and HocVien.MaKH=? order by Diem DESC";
    return this.selectBySQL(sql, maKH);
    }
    public List<HocVien> sortNameDND(int maKH) {
        String sql = "select * from HocVien inner join NguoiHoc \n"
                + "on HocVien.MaNH=NguoiHoc.MaNH where diem>0 and HocVien.MaKH=? order by\n"
                + "(RIGHT(HoTen,CHARINDEX(' ',REVERSE(HoTen)))) ";
        return this.selectBySQL(sql, maKH);
    }
    public List<HocVien> ThanhTich(int year, String macd) {
        String sql = "select  MaHV,Diem,MaNH from HocVien inner join KhoaHoc on HocVien.MaKH=KhoaHoc.MaKH\n"
                + "inner join ChuyenDe on KhoaHoc.MaCD=ChuyenDe.MaCD where Year(KhoaHoc.NgayKG)=? \n"
                + "and ChuyenDe.MaCD=? and Diem>=8 order by Diem DESC";
        return this.selectSQL(sql, year, macd);
    
    }
}
