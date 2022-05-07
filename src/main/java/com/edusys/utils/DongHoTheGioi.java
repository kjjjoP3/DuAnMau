/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author Minh Huy
 */
public class DongHoTheGioi extends Thread{
    private JLabel x;

    public DongHoTheGioi(JLabel x) {
        this.x = x;
    }
    public void run(){
    while(true){
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss aa");
        Date now=new Date();
        String kq=sdf.format(now);
        x.setText(kq);
    
    }
    
    }
}
