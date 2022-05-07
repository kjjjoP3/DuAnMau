/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.entity.ChuyenDe;
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
public  class ChuyenDeDAO extends EdusysDAO<ChuyenDe,String>{
String INSERT_SQL="INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
String UPDATE_SQL="UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
String DELETE_SQL="DELETE FROM ChuyenDe WHERE MaCD=?";
String SELECT_ALL_SQL="SELECT * FROM ChuyenDe";
String SELECT_BY_ID_SQL="SELECT * FROM ChuyenDe WHERE MaCD=?";
String FindName="SELECT * FROM ChuyenDe WHERE TenCD LIKE ?";
    @Override
    public void insert(ChuyenDe e) {
    try {
        JdbcHelper.executeUpdate(INSERT_SQL,e.getMaCD(),e.getTenCD(),e.getHocPhi(),e.getThoiLuong(),e.getHinh(),e.getMoTa());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public void update(ChuyenDe e) {
    try {
        JdbcHelper.executeUpdate(UPDATE_SQL,e.getTenCD(),e.getHocPhi(),e.getThoiLuong(),e.getHinh(),e.getMoTa(),e.getMaCD());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public void delete(String key) {
    try {
        JdbcHelper.executeUpdate(DELETE_SQL, key);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public ChuyenDe selectByID(String key) {
        List<ChuyenDe> ls=this.selectBySQL(SELECT_BY_ID_SQL,key);
        if(ls.isEmpty()){
        return null;
        }else{
        return ls.get(0);
        }
    }

    @Override
    protected List<ChuyenDe> selectBySQL(String sql, Object... args) {
        List<ChuyenDe> ls=new ArrayList<>();
        try {
            ResultSet rs=JdbcHelper.executeQuery(sql, args);
            while(rs.next()){
            ChuyenDe cd=new ChuyenDe();
            cd.setMaCD(rs.getString("MaCD"));
            cd.setTenCD(rs.getString("TenCD"));
            cd.setHocPhi(rs.getDouble("HocPhi"));
            cd.setThoiLuong(rs.getInt("ThoiLuong"));
            cd.setHinh(rs.getString("Hinh"));
            cd.setMoTa(rs.getString("MoTa"));
            ls.add(cd);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    public List<ChuyenDe> findName(String ten){
    return this.selectBySQL(FindName,"%"+ten+"%");
    }
    
}
