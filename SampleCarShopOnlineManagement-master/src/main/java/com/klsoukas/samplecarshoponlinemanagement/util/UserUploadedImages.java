
package com.klsoukas.samplecarshoponlinemanagement.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


public class UserUploadedImages {
    
    public static boolean saveInFileSystem(InputStream inputStream, File photoFile) throws IOException{
    
        BufferedImage originalImg = ImageIO.read(inputStream);
        BufferedImage resizedImg = originalImg;
        
        int originalImgWidth = originalImg.getWidth();
        int originalImgHeight = originalImg.getHeight();
        int newImgWidth=0;
        int newImgHeight=0;
        
        if(originalImgWidth > 1200 && Math.max(originalImgWidth,originalImgHeight)== originalImgWidth){
            newImgWidth = 1200;
            newImgHeight =(int)( newImgWidth*((float)originalImgHeight/originalImgWidth));
            resizedImg = resizeImg(originalImg,newImgWidth,newImgHeight);
        }
        else if(originalImgHeight > 1200 && Math.max(originalImgWidth,originalImgHeight)== originalImgHeight){
            newImgHeight = 1200;
            newImgWidth = (int)(newImgHeight*((float)originalImgWidth/originalImgHeight));
            resizedImg = resizeImg(originalImg,newImgWidth,newImgHeight);
        }
        System.out.println("original width: "+originalImgWidth);
        System.out.println("original height: "+originalImgHeight);
        System.out.println("new width: "+newImgWidth);
        System.out.println("new height: "+newImgHeight);
        
        return ImageIO.write(resizedImg,"jpeg",photoFile);
        
    }  
    
    public static BufferedImage resizeImg(BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage resizedImg = resizedImg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = resizedImg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return resizedImg;  
    }  
    
}
