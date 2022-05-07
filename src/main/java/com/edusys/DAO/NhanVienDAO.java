/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.entity.NhanVien;
import com.edusys.helper.JdbcHelper;
import java.sql.PreparedStatement;
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
public class NhanVienDAO extends EdusysDAO<NhanVien, String> {
String INSERT_SQL="INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro) VALUES (?, ?, ?, ?)";
String UPDATE_SQL="UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
String DELETE_SQL="DELETE FROM NhanVien WHERE MaNV=?";
String SELECT_ALL_SQL="SELECT * FROM NhanVien";
String SELECT_BY_ID_SQL="SELECT * FROM NhanVien WHERE MaNV=?";
String FindName="SELECT * FROM NhanVien WHERE HoTen LIKE ?";
   

    @Override
    public void insert(NhanVien e) {
    try {
        JdbcHelper.executeUpdate(INSERT_SQL,e.getMaNV(),e.getMatKhau(),e.getHoTen(),e.getVaiTro());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public void update(NhanVien e) {
    try {
        JdbcHelper.executeUpdate(UPDATE_SQL, e.getMatKhau(),e.getHoTen(),e.getVaiTro(),e.getMaNV());
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
    public NhanVien selectByID(String key) {
        List<NhanVien> ls=this.selectBySQL(SELECT_BY_ID_SQL,key);
        if(ls.isEmpty()){
        return null;
        }
        return ls.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> ls=new ArrayList<>();
        try {
            ResultSet rs=JdbcHelper.executeQuery(sql, args);
            while(rs.next()){
            NhanVien nv=new NhanVien();
            nv.setMaNV(rs.getString("MaNV"));
            nv.setMatKhau(rs.getString("MatKhau"));
            nv.setHoTen(rs.getString("HoTen"));
            nv.setVaiTro(rs.getBoolean("VaiTro"));
            ls.add(nv);
            }
            rs.getStatement().getConnection().close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public List<NhanVien> FindByKeyword(String id){
    return this.selectBySQL(FindName,"%"+id+"%");
    }
    
}
