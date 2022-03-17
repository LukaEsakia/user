
package com.klsoukas.samplecarshoponlinemanagement.model;


public class PhotoBean {
    private int id;
    private int car_fk;
    private String location;
    
    
    public int getCar_fk() {
        return car_fk;
    }

    public void setCar_fk(int car_fk) {
        this.car_fk = car_fk;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
