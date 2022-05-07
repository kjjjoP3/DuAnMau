/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.entity.NguoiHoc;
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
public  class NguoiHocDAO extends EdusysDAO<NguoiHoc,String>{
String INSERT_SQL="INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
String UPDATE_SQL="UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=? WHERE MaNH=?";
String DELETE_SQL="DELETE FROM NguoiHoc WHERE MaNH=?";
String SELECT_ALL_SQL="SELECT * FROM NguoiHoc";
String SELECT_BY_ID_SQL="SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
String SELECT_BY_ID_SQL1="SELECT * FROM NguoiHoc WHERE MaNH= ?";
    @Override
    public void insert(NguoiHoc e) {
    try {
        JdbcHelper.executeUpdate(INSERT_SQL,e.getMaNH(),e.getHoTen(),e.getNgaySinh(),e.getGioiTinh(),e.getDienThoai(),e.getEmail(),
                e.getGhiChu(),e.getMaNV());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }
// MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV
    @Override
    public void update(NguoiHoc e) {
    try {
        JdbcHelper.executeUpdate(UPDATE_SQL, e.getHoTen(),e.getNgaySinh(),e.getGioiTinh(),e.getDienThoai(),e.getEmail(),e.getGhiChu(),
                e.getMaNV(),e.getMaNH());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public void delete(String key) {
    try {
        JdbcHelper.executeUpdate(DELETE_SQL,key);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NguoiHoc selectByID(String key) {
        List<NguoiHoc> ls=this.selectBySQL(SELECT_BY_ID_SQL,key);
        if(ls.isEmpty()){
        return null;
        }else{
        return ls.get(0);
        }
    }
    public NguoiHoc selectByID1(String key) {
        List<NguoiHoc> ls=this.selectBySQL(SELECT_BY_ID_SQL1,key);
        if(ls.isEmpty()){
        return null;
        }else{
        return ls.get(0);
        }
    }

    @Override
    protected List<NguoiHoc> selectBySQL(String sql, Object... args) {
       List<NguoiHoc> ls=new ArrayList<>();
        try {
            ResultSet rs=JdbcHelper.executeQuery(sql, args);
            while(rs.next()){
            NguoiHoc nh=new NguoiHoc();
            nh.setDienThoai(rs.getString("DienThoai"));
            nh.setEmail(rs.getString("Email"));
            nh.setGhiChu(rs.getString("GhiChu"));
            nh.setGioiTinh(rs.getBoolean("GioiTinh"));
            nh.setHoTen(rs.getString("HoTen"));
            nh.setMaNH(rs.getString("MaNH"));
            nh.setMaNV(rs.getString("MaNV"));
            nh.setNgayDK(rs.getDate("NgayDK"));
            nh.setNgaySinh(rs.getDate("NgaySinh"));
            ls.add(nh);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    public List<NguoiHoc> selectByKeyWord(String id){
    String sql="SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
    return this.selectBySQL(sql,"%"+id+"%");
    }
    
    public List<NguoiHoc> selectNotInCourse(int makh, String keyword){
    String sql="SELECT * FROM NguoiHoc where HoTen LIKE ? and MaNH Not In (Select MaNH FROM HocVien WHERE MaKH=?)";
    return this.selectBySQL(sql,"%"+keyword+"%",makh);
    }
    
}
