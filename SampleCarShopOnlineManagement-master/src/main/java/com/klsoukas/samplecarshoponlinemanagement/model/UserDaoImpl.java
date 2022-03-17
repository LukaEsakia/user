
package com.klsoukas.samplecarshoponlinemanagement.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;







public class UserDaoImpl implements UserDao{

    @Override
    public UserBean findUserByGoogleId(String googleId) {
        try {
            Class.forName(DbUtil.DRIVER_CLASS_NAME);      
            Connection con = DriverManager.getConnection(DbUtil.CONNECTION_URL, DbUtil.USERNAME, DbUtil.PASSWORD);
        
            PreparedStatement pstmt = con.prepareStatement("SELECT * from user where googleId=?");
            pstmt.setString(1, googleId);
            
            ResultSet res = pstmt.executeQuery();
            
            if(res.isBeforeFirst()){
                UserBean user = new UserBean();
                res.next();
                user.setId(res.getInt("id"));
                user.setGoogleId(res.getString("googleId"));
                user.setFirstName(res.getString("firstName"));
                user.setLastName(res.getString("lastName"));
                user.setImageURL(res.getString("imageURL"));
                user.setEmail(res.getString("email"));
                pstmt.close();
                con.close();
                return user;
            }
            else {
                pstmt.close();
                con.close();
                return null;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            return null;
        }catch (SQLException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            return null;
        }
    }

    @Override
    public UserBean createNewUser(UserBean user) {
        try {
            Class.forName(DbUtil.DRIVER_CLASS_NAME);      
            Connection con = DriverManager.getConnection(DbUtil.CONNECTION_URL, DbUtil.USERNAME, DbUtil.PASSWORD);

            
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO user "
                    + "(googleId,firstName,lastName,imageURL,email)VALUES(?,?,?,?,?)" );
            
            
            pstmt.setString(1,user.getGoogleId());
            pstmt.setString(2,user.getFirstName());
            pstmt.setString(3,user.getLastName());
            pstmt.setString(4,user.getImageURL());
            pstmt.setString(5,user.getEmail());
                    
            int updatedRowCount = pstmt.executeUpdate();
            
            pstmt.close();
            
            if (updatedRowCount == 1){
                pstmt = con.prepareStatement("SELECT id FROM user where googleId=?");
                pstmt.setString(1,user.getGoogleId());
                ResultSet res = pstmt.executeQuery();
                if(res.isBeforeFirst()){
                    res.next();
                    user.setId(res.getInt("id"));
                    res.close();
                    pstmt.close();

                    con.close();
                    return user;
                }
                else {
                    pstmt.close();

                    con.close();
                    return null;
                }
            }
            else{
                con.close();
                return null;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            return null;
        }catch (SQLException ex) {
            Logger.getLogger(BrandDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            return null;
        }
    }
    
}
