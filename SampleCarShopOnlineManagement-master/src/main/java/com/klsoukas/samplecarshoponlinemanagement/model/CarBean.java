
package com.klsoukas.samplecarshoponlinemanagement.model;

import java.util.List;


public class CarBean {
    
    private int id;
    private int brand_fk;
    private int user_fk;

    public int getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(int user_fk) {
        this.user_fk = user_fk;
    }
    private String model;
    private String description;
    
    
    private String brandName;

   
    private List<PhotoBean> photoList;

   
    public int getPhotoNumber(){
        if(photoList !=null){
            return photoList.size();
        }
        else{
            return 0;
        }      
    }
    
    public List<PhotoBean> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<PhotoBean> photoList) {
        this.photoList = photoList;
    }
    
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    
    public CarBean(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrand_fk() {
        return brand_fk;
    }

    public void setBrand_fk(int brand_fk) {
        this.brand_fk = brand_fk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
