/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Minh Huy
 */
public class XImages {
    public static Image getAppIcon(){
    URL url=XImages.class.getResource("/images/fpt.png");
    return  new ImageIcon(url).getImage();
    }
    public static void save(File src){
    File dst=new File("logos",src.getName());
    if(!dst.getParentFile().exists()){
    dst.getParentFile().mkdirs();
    }
        try {
            Path from= Paths.get(src.getAbsolutePath());
            Path to= Paths.get(dst.getAbsolutePath());
            Files.copy(from, to,StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ImageIcon read(String fileName){
    File path=new File("logos",fileName);
    return new ImageIcon(path.getAbsolutePath());
    }
    public static Image resize(Image originalImage, int targetWidth, int targetHeight){// đổi size ảnh
    Image resultingImage=originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
    return resultingImage;
    }
    
}
