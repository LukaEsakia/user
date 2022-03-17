
package com.klsoukas.samplecarshoponlinemanagement.controller;

import com.klsoukas.samplecarshoponlinemanagement.model.BrandBean;
import com.klsoukas.samplecarshoponlinemanagement.model.BrandDao;
import com.klsoukas.samplecarshoponlinemanagement.model.BrandDaoImpl;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ImageServingServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        if(request.getPathInfo()==null){
            BrandDao brandDao = new BrandDaoImpl();
            BrandBean b = brandDao.findBrandById(Integer.parseInt(request.getParameter("id")));
            byte[] logo = b.getLogo();
            if(logo != null){
                String fileExtension = b.getFileExtension();

                response.setContentType(getServletContext().getMimeType(fileExtension));
                response.setContentLength(logo.length);
                response.getOutputStream().write(logo);
               
            }
            else{


             request.getRequestDispatcher("images/logos/default_logo/no image.png").forward(request,response);
            }
     
        }
        else{
           
            String filename = URLDecoder.decode(request.getPathInfo(), "UTF-8");
            File file = new File(getServletContext().getInitParameter("uploads_location")+filename);

            response.setHeader("Content-Type", getServletContext().getMimeType(filename));
            response.setHeader("Content-Length", String.valueOf(file.length()));

            Files.copy(file.toPath(), response.getOutputStream());
        }
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
