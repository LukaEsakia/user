
package com.klsoukas.samplecarshoponlinemanagement.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PhotoDaoImpl implements PhotoDao{

    @Override
    public boolean addPhotos(List<PhotoBean> photoList) {
        
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(DbUtil.DRIVER_CLASS_NAME);
            
            con = DriverManager.getConnection(DbUtil.CONNECTION_URL,DbUtil.USERNAME,DbUtil.PASSWORD);
            con.setAutoCommit(false);
            
            pstmt = con.prepareStatement("INSERT INTO photo (car_fk,location) VALUES (?,?)");
            for(PhotoBean photo : photoList){
                pstmt.setInt(1,photo.getCar_fk());
                pstmt.setString(2,photo.getLocation());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.close();
            con.commit();
            con.close();
            return true;
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(CarDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            
            return false;
        }
        catch (SQLException ex) {
            Logger.getLogger(CarDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            
            return false;
        }
    }
    
    
    
}
