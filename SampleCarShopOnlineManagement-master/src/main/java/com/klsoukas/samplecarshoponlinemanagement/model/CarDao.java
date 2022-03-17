
package com.klsoukas.samplecarshoponlinemanagement.model;

import java.util.List;


public interface CarDao {
    
    List<CarBean> findAllCars();
    List<CarBean> findCarsByUser(UserBean user);
    int createCar(CarBean newCar);
    
    
    
}
