
package com.klsoukas.samplecarshoponlinemanagement.controller;

import com.klsoukas.samplecarshoponlinemanagement.model.BrandBean;
import com.klsoukas.samplecarshoponlinemanagement.model.BrandDao;
import com.klsoukas.samplecarshoponlinemanagement.model.BrandDaoImpl;
import com.klsoukas.samplecarshoponlinemanagement.model.CarBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        BrandDao brandDao = new BrandDaoImpl();
        List<BrandBean> brandList = brandDao.findAllBrands();
        request.setAttribute("brandList", brandList);
        
        

        List<CarBean> fullCarList = (List<CarBean>)getServletContext().getAttribute("carList");
        
        int carList_max_size = fullCarList.size();        

        
        int max_page_number = carList_max_size/5;
        
       
        if(carList_max_size%5>0){
            max_page_number++;
        }
        
        
        int page_number = 1;
        if(request.getParameter("page")!=null){
            try{
                page_number = Integer.parseInt(request.getParameter("page"));
                if(page_number>max_page_number){
                    page_number=1;
                }
            }
            
            catch(Exception e){
             
                page_number = 1;
            }
        }

        
        List<CarBean> carList = new ArrayList<CarBean>(5);
        
        for(int i=0+(page_number-1)*5; i<5+(page_number-1)*5; i++){
            try{
                CarBean c = fullCarList.get(i);
                carList.add(c);
            }
            catch(IndexOutOfBoundsException e){
                break;
            }
            
        }
        
        request.setAttribute("carList",carList);
        request.setAttribute("maxPageNumber",max_page_number);
        
        request.getRequestDispatcher("WEB-INF/jsp/main.jsp").forward(request, response);
 
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
