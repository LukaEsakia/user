
package com.klsoukas.samplecarshoponlinemanagement.util;

import com.klsoukas.samplecarshoponlinemanagement.model.CarBean;
import com.klsoukas.samplecarshoponlinemanagement.model.CarDao;
import com.klsoukas.samplecarshoponlinemanagement.model.CarDaoImpl;
import java.util.List;
import javax.servlet.ServletContext;


public class BackgroundContextAttributeUpdate implements Runnable{

    ServletContext sc;
    public BackgroundContextAttributeUpdate(ServletContext sc){
        this.sc = sc;
    }
    
    @Override
    public void run() {

                    
        CarDao carDao = new CarDaoImpl();
        List<CarBean> carlist = carDao.findAllCars();
        sc.setAttribute("carList",carlist);
    }
    
    
    
}
