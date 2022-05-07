/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.spi.DirStateFactory;

/**
 *
 * @author Minh Huy
 */
public class JdbcHelper {
    private static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl="jdbc:sqlserver://localhost;database=Polypro";
    private static String username="sa";
    private static String password="01219164372";
    
    static{
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static PreparedStatement prepareStatement(String sql,Object...args) throws SQLException{
    Connection conn=DriverManager.getConnection(dburl,username,password);
    PreparedStatement ps=null;
    if(sql.trim().startsWith("{")){
    ps=conn.prepareCall(sql);
    }else{
    ps=conn.prepareStatement(sql);
    }
    for(int i=0;i<args.length;i++){
    ps.setObject(i+1, args[i]);
    }
    return ps;
    }
    
    public static void executeUpdate(String sql,Object...args) throws SQLException{
    try(
         PreparedStatement ps=prepareStatement(sql, args);
            ){
    ps.executeUpdate();
    }
    }
    
    public static ResultSet executeQuery(String sql,Object...args) throws SQLException{
        PreparedStatement ps=prepareStatement(sql, args);
    return ps.executeQuery();
    }
}
