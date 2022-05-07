/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.helper.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Minh Huy
 */
public class ThongKeDAO {
    private List<Object[]> getListOfArray(String sql,String[] cols,Object...args){
        List<Object[]> list=new ArrayList<>();
        try {
            
            ResultSet rs=JdbcHelper.executeQuery(sql, args);
            while(rs.next()){
            Object[] vals=new Object[cols.length];
            for(int i=0;i<cols.length;i++){
            vals[i]=rs.getObject(cols[i]);
            }
            list.add(vals);
            }
            rs.getStatement().getConnection().close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    return list;
    }
    public List<Object[]> getBangDiem(Integer makh){
    String sql="{CALL sp_BangDiem(?)}";
    String cols[]={"MaNH","HoTen","Diem"};
    return this.getListOfArray(sql, cols, makh);
    }
    
    public List<Object[]> getLuongNguoiHoc(){
    String sql="{CALL sp_ThongKeNguoiHoc}";
    String cols[]={"Nam","SoLuong","DauTien","CuoiCung"};
    return this.getListOfArray(sql, cols);
    }
    
    public List<Object[]> getDiemChuyenDe(){
    String sql="{CALL sp_ThongKeDiem}";
    String cols[]={"ChuyenDe","SoHV","ThapNhat","CaoNhat","TrungBinh"};
    return this.getListOfArray(sql, cols);
    }
    
    public List<Object[]> getDoanhThu(int nam){
    String sql="{CALL sp_ThongKeDoanhThu(?)}";
    String cols[]={"ChuyenDe","SoKH","SoHV","DoanhThu","ThapNhat","CaoNhat","TrungBinh"};
    return this.getListOfArray(sql, cols,nam);
    }
    public List<Object[]> getNangXuat(int nam){
    String sql="{CALL sp_ThongKeNangXuat(?)}";
    String cols[]={"manv","hoten","SoLuongNguoiHoc"};
    return this.getListOfArray(sql, cols, nam);
    }
    public List<Integer> selectYearFromCD(String macd){
        String sql = "select Year(KhoaHoc.NgayKG) from KhoaHoc inner join ChuyenDe on KhoaHoc.MaCD=ChuyenDe.MaCD\n"
                + "where ChuyenDe.MaCD=?";
        List<Integer> ls = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, macd);
            while (rs.next()) {
                ls.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    public List<Integer> selectYearsNX(){
   String sql="SELECT DISTINCT year(NgayDK) FROM NguoiHoc ORDER BY Year(NgayDK) DESC";
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
