
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


public class CarDaoImpl implements CarDao{

    @Override
    public List<CarBean> findAllCars() {
        try {
            Class.forName(DbUtil.DRIVER_CLASS_NAME);
            
            Connection con = DriverManager.getConnection(DbUtil.CONNECTION_URL,DbUtil.USERNAME,DbUtil.PASSWORD);
            
            PreparedStatement pstmt = con.prepareStatement("SELECT  brand.name, car.id, car.brand_fk, car.model, car.description, photo.location, photo.id as photoId "
                    + "FROM car INNER JOIN brand ON car.brand_fk = brand.id LEFT JOIN photo ON car.id = photo.car_fk "
                    + "ORDER BY car.id DESC");
            
            ResultSet res = pstmt.executeQuery();
            
            List<CarBean> carList = new ArrayList<>();
            List<PhotoBean> photoList = new ArrayList<>();
            
            int prevCarId = 0;
            
            while(res.next()){
                
                if (res.getInt("id")!= prevCarId){
                    CarBean c = new CarBean();
                    c.setBrandName(res.getString("name"));
                    c.setId(res.getInt("id"));
                    c.setBrand_fk(res.getInt("brand_fk"));
                    c.setModel(res.getString("model"));
                    c.setDescription(res.getString("description"));
                    if(res.getInt("photoId")!= 0){
                        photoList = new ArrayList<>();
                        PhotoBean p = new PhotoBean();
                        p.setId(res.getInt("photoId"));
                        p.setLocation(res.getString("location"));
                        photoList.add(p);
                        c.setPhotoList(photoList);
                    }
                    carList.add(c);
                    prevCarId = res.getInt("id");
                }
                else{
                    PhotoBean p = new PhotoBean();
                    p.setId(res.getInt("photoId"));
                    p.setLocation(res.getString("location"));
                    photoList.add(p);
                }
                
            }
            
            res.close();
            pstmt.close();
            con.close();
            
            return carList;
            
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(CarDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            return null;
        }
        catch (SQLException ex) {
            Logger.getLogger(CarDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            return null;
        }
        
        
    }
    
    
    
    @Override
    public List<CarBean> findCarsByUser(UserBean user) {
        try {
            Class.forName(DbUtil.DRIVER_CLASS_NAME);
            
            Connection con = DriverManager.getConnection(DbUtil.CONNECTION_URL,DbUtil.USERNAME,DbUtil.PASSWORD);
            
            PreparedStatement pstmt = con.prepareStatement("SELECT  brand.name, car.id, car.brand_fk, car.user_fk, car.model, car.description, photo.location, photo.id as photoId "
                    + "FROM car INNER JOIN brand ON car.brand_fk = brand.id LEFT JOIN photo ON car.id = photo.car_fk WHERE car.user_fk = ?" + " ORDER BY car.id DESC");
            
            pstmt.setInt(1,user.getId());
            
            ResultSet res = pstmt.executeQuery();
            
            List<CarBean> carList = new ArrayList<>();
            List<PhotoBean> photoList = new ArrayList<>();
            
            int prevCarId = 0;
            
            while(res.next()){
                
                if (res.getInt("id")!= prevCarId){
                    CarBean c = new CarBean();
                    c.setBrandName(res.getString("name"));
                    c.setId(res.getInt("id"));
                    c.setBrand_fk(res.getInt("brand_fk"));
                    c.setUser_fk(res.getInt("user_fk"));
                    c.setModel(res.getString("model"));
                    c.setDescription(res.getString("description"));
                    if(res.getInt("photoId")!= 0){
                        photoList = new ArrayList<>();
                        PhotoBean p = new PhotoBean();
                        p.setId(res.getInt("photoId"));
                        p.setLocation(res.getString("location"));
                        photoList.add(p);
                        c.setPhotoList(photoList);
                    }
                    carList.add(c);
                    prevCarId = res.getInt("id");
                }
                else{
                    PhotoBean p = new PhotoBean();
                    p.setId(res.getInt("photoId"));
                    p.setLocation(res.getString("location"));
                    photoList.add(p);
                }
                
            }
            
            res.close();
            pstmt.close();
            con.close();
            
            return carList;
            
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(CarDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            return null;
        }
        catch (SQLException ex) {
            Logger.getLogger(CarDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            return null;
        }
        
        
    }

    @Override
    public int createCar(CarBean newCar) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            Class.forName(DbUtil.DRIVER_CLASS_NAME);
            
            con = DriverManager.getConnection(DbUtil.CONNECTION_URL,DbUtil.USERNAME,DbUtil.PASSWORD);
            con.setAutoCommit(false);
            
            if(newCar.getDescription() == null){
                pstmt = con.prepareStatement("INSERT INTO car (brand_fk,user_fk,model) VALUES (?,?,?)");
                pstmt.setInt(1,newCar.getBrand_fk());
                pstmt.setInt(2,newCar.getUser_fk());
                pstmt.setString(3,newCar.getModel());              
            }
            else{
                pstmt = con.prepareStatement("INSERT INTO car (brand_fk,user_fk,model,description) VALUES (?,?,?,?)");
                pstmt.setInt(1,newCar.getBrand_fk());
                pstmt.setInt(2,newCar.getUser_fk());
                pstmt.setString(3,newCar.getModel());
                pstmt.setString(4,newCar.getDescription());
            }
            
            pstmt.close();

            
            pstmt = con.prepareStatement("SELECT id FROM car WHERE brand_fk=? AND model=?");
            pstmt.setInt(1,newCar.getBrand_fk());
            pstmt.setString(2,newCar.getModel());
            res = pstmt.executeQuery();
            res.next();
            int carId = res.getInt("id");
            res.close();
            pstmt.close();
            

            
            con.commit();
            con.close();
            return carId;
        }  
        catch (ClassNotFoundException ex) {
            Logger.getLogger(CarDaoImpl.class.getName()).log(Level.SEVERE, "Incorrect Driver Class Name!", ex);
            
            return -1;
        }
        catch (SQLException ex) {
            Logger.getLogger(CarDaoImpl.class.getName()).log(Level.SEVERE, "Database Error Occured!", ex);
            
            return -1;
        }
        
         
    }
    
}
