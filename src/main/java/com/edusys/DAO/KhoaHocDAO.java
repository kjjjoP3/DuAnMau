/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.entity.HocVien;
import com.edusys.entity.KhoaHoc;
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
public class KhoaHocDAO extends EdusysDAO<KhoaHoc,Integer>{
String INSERT_SQL="INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao) VALUES (?, ?, ?, ?, ?, ?, ?)";
String UPDATE_SQL="UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? ,NgayTao=? WHERE MaKH=?";
//String UPDATE_SQL="UPDATE KhoaHoc SET  HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=?, NgayTao=? WHERE MaCD=?";
String DELETE_SQL="DELETE FROM KhoaHoc WHERE MaKH=?";
String SELECT_ALL_SQL="SELECT * FROM KhoaHoc";
String SELECT_BY_ID="SELECT * FROM KhoaHoc WHERE MaKH=?";
    @Override
    public void insert(KhoaHoc e) {
    try {
        JdbcHelper.executeUpdate(INSERT_SQL,e.getMaCD(),e.getHocPhi(),e.getThoiLuong(),e.getNgayKG(),e.getGhiChu(),e.getMaNV(),e.getNgayTao());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public void update(KhoaHoc e) {
    try {
    JdbcHelper.executeUpdate(UPDATE_SQL,e.getMaCD(),e.getHocPhi(),e.getThoiLuong(),e.getNgayKG(),e.getGhiChu(),e.getMaNV(),e.getNgayTao(),e.getMaKH());
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
    public List<KhoaHoc> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public KhoaHoc selectByID(Integer key) {
       List<KhoaHoc> ls=this.selectBySQL(SELECT_BY_ID,key);
       if(ls.isEmpty()){
       return null;
       }else{
       return ls.get(0);
       }
    }
// MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao
    @Override
    protected List<KhoaHoc> selectBySQL(String sql, Object... args) {
       List<KhoaHoc> ls=new ArrayList<>();
        try {
            ResultSet rs=JdbcHelper.executeQuery(sql, args);
            while(rs.next()){
            KhoaHoc kh=new KhoaHoc();
           
            kh.setMaKH(rs.getInt("MaKH"));
            kh.setMaCD(rs.getString("MaCD"));
            kh.setHocPhi(rs.getDouble("HocPhi"));
            kh.setThoiLuong(rs.getInt("ThoiLuong"));
            kh.setNgayKG(rs.getDate("NgayKG"));
            kh.setGhiChu(rs.getString("GhiChu"));
            kh.setMaNV(rs.getString("MaNV"));
            kh.setNgayTao(rs.getDate("NgayTao"));
            ls.add(kh);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
   public List<KhoaHoc> SelectByCD(String cd){
   String sql="SELECT * FROM KhoaHoc WHERE MaCD=?";
   return this.selectBySQL(sql, cd);
   }
   
   public List<Integer> selectYears(){
   String sql="SELECT DISTINCT year(NgayKG) FROM KhoaHoc ORDER BY Year(NgayKG) DESC";
   List<Integer> ls=new ArrayList<>();
       try {
           ResultSet rs=JdbcHelper.executeQuery(sql);
           while(rs.next()){
           ls.add(rs.getInt(1));
           }
           rs.getStatement().getConnection().close();
           
       } catch (Exception e) {
           e.printStackTrace();
       }
       return ls;
   }
   
    
}
