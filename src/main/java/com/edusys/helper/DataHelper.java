/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Minh Huy
 */
public class DataHelper {
    public static Connection openConnection() throws Exception{
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    String url="jdbc:sqlserver://localhost:1433;database=Polypro;";
    String user="sa";
    String pass="01219164372";
    Connection conn=DriverManager.getConnection(url,user,pass);
    return conn;
    }
    public static void main(String[] args) throws Exception {
       openConnection();
    }
}
