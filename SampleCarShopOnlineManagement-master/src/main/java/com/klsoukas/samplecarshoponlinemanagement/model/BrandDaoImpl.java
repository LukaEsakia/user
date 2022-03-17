
package com.klsoukas.samplecarshoponlinemanagement.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BrandDaoImpl implements BrandDao{

    @Override
    public List<BrandBean> findAllBrands() {      
        try {
            
            Class.forName(DbUtil.DRIVER_CLASS_NAME);      
            Connection con = DriverManager.getConnection(DbUtil.CONNECTION_URL, DbUtil.USERNAME, DbUtil.PASSWORD);
            
            PreparedStatement pstmt = con.prepareStatement("SELECT id,name,logo, fileExtension FROM brand");
            
            ResultSet res = pstmt.executeQuery();
            
            List<BrandBean>  brandList = new ArrayList<>();
            
            while(res.next()){
                
                BrandBean b = new BrandBean();
               
                b.setId(res.getInt("id"));
                b.setName(res.getString("name"));
                b.setLogo(res.getBytes("logo"));
                b.setFileExtension(res.getString("fileExtension"));
                
                brandList.add(b);
     
            }
            res.close();
            pstmt.close();
            con.close();
            return brandList;
            
            
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            return null;
        }
        catch (SQLException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            return null;
        }
    }


    @Override
    public BrandBean findBrandById(int id) {
        
        try {
            Class.forName(DbUtil.DRIVER_CLASS_NAME);
            Connection con = DriverManager.getConnection(DbUtil.CONNECTION_URL,DbUtil.USERNAME,DbUtil.PASSWORD);
            
            PreparedStatement pstmt = con.prepareStatement("SELECT id,name,logo, fileExtension FROM brand WHERE id=?");
            pstmt.setInt(1,id);
            
            ResultSet res = pstmt.executeQuery();
            
            BrandBean b = null;
            
            if(res.next()){
                b = new BrandBean();
                b.setId(res.getInt("id"));
                b.setName(res.getString("name"));
                b.setLogo(res.getBytes("logo"));
                b.setFileExtension(res.getString("fileExtension"));
                
            }
            
            res.close();
            pstmt.close();
            con.close();
            
            return b;
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            return null;
        } 
        catch (SQLException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            return null;
        }
    }

    @Override
    public boolean createBrand(BrandBean newBrand) {
        try {
            Class.forName(DbUtil.DRIVER_CLASS_NAME);      
            Connection con = DriverManager.getConnection(DbUtil.CONNECTION_URL, DbUtil.USERNAME, DbUtil.PASSWORD);
            
            PreparedStatement pstmt;
            if(newBrand.getLogo()!=null){
                pstmt = con.prepareStatement("INSERT INTO brand (name, logo, fileExtension) VALUES (?,?,?)");
                pstmt.setString(1, newBrand.getName());
                pstmt.setBytes(2, newBrand.getLogo());
                pstmt.setString(3, newBrand.getFileExtension());
            }
            else{
                pstmt = con.prepareStatement("INSERT INTO brand (name) VALUES (?)");
                pstmt.setString(1, newBrand.getName());
            }
            int updatedRowCount = pstmt.executeUpdate();
              
            pstmt.close();
            con.close();
            
            return updatedRowCount == 1;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            return false;
        }catch (SQLException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            return false;
        }
        
    }
    
    

    
}
