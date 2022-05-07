/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Minh Huy
 */
public class XDate {
    static SimpleDateFormat formater=new SimpleDateFormat();
    public static Date toDate(String date,String pattern) throws ParseException{// chuyển chuỗi sang date
            formater.applyPattern(pattern);
            return formater.parse(date);   
    }
    public static String toString(Date date,String pattern){
    formater.applyPattern(pattern);
    return formater.format(date);
    }
    public static Date addDays(Date date,long days){// thêm ngày vào
    date.setTime(date.getTime()+days*24*60*60*100);
    return date;
    }
}
