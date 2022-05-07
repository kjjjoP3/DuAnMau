/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Minh Huy
 */
public class MgsBox {
    public static void Alert(Component parent,String message){
        JOptionPane.showMessageDialog(parent, message,"Hệ Quản Lý Đào Tạo",JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean Confirm(Component parent, String message){
    int result=JOptionPane.showConfirmDialog(parent, message,"Hệ Quản Trị Đào Tạo",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    return result==JOptionPane.YES_OPTION;
    }
    public static String Prompt(Component parent,String message){
    return JOptionPane.showInputDialog(parent,message,"Hệ Quản Lý Đào Tạo",JOptionPane.INFORMATION_MESSAGE);
    
    }
}
