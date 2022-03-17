
package com.klsoukas.samplecarshoponlinemanagement.controller;

import com.klsoukas.samplecarshoponlinemanagement.model.BrandBean;
import com.klsoukas.samplecarshoponlinemanagement.model.BrandDao;
import com.klsoukas.samplecarshoponlinemanagement.model.BrandDaoImpl;
import com.klsoukas.samplecarshoponlinemanagement.model.CarBean;
import com.klsoukas.samplecarshoponlinemanagement.model.CarDao;
import com.klsoukas.samplecarshoponlinemanagement.model.CarDaoImpl;
import com.klsoukas.samplecarshoponlinemanagement.model.PhotoBean;
import com.klsoukas.samplecarshoponlinemanagement.model.PhotoDao;
import com.klsoukas.samplecarshoponlinemanagement.model.PhotoDaoImpl;
import com.klsoukas.samplecarshoponlinemanagement.model.UserBean;
import com.klsoukas.samplecarshoponlinemanagement.util.UserUploadedImages;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;


public class AddCarServlet extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
 
        BrandDao brandDao = new BrandDaoImpl();
        List<BrandBean> brandList = brandDao.findAllBrands();
        request.setAttribute("brandList", brandList);
        
        
        UserBean thisUser = (UserBean)request.getSession().getAttribute("user");
        
        CarDao carDao = new CarDaoImpl();
        List<CarBean> carList = carDao.findCarsByUser(thisUser);
        request.setAttribute("carList",carList);
        
        request.getRequestDispatcher("WEB-INF/jsp/addCars.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String submittedForm = request.getParameter("submit");
        if(submittedForm.equals("submitBrand")){
            BrandBean newBrand = new BrandBean();
            newBrand.setName(request.getParameter("name"));
            Part logoPart = request.getPart("file");
            String submittedFileName = logoPart.getSubmittedFileName();
            
            if(submittedFileName.equals("") || logoPart.getSize()>=65000){
            } else {

                byte[] logo = IOUtils.toByteArray(logoPart.getInputStream());
                
                String ext = submittedFileName.substring(submittedFileName.lastIndexOf("."));
                if(ext.equals(".jpg")||ext.equals(".jpeg")||ext.equals(".png")||ext.equals(".gif")){
                    newBrand.setLogo(logo);
                    newBrand.setFileExtension(ext);
                }
            }
            BrandDao brandDao = new BrandDaoImpl();
            brandDao.createBrand(newBrand);
            

        }
        else if(submittedForm.equals("submitCars")){
            
            int carsAdded = Integer.parseInt(request.getParameter("carNumber"));
            ArrayList<PhotoBean> photoList = new ArrayList<>();
            for(int i=1; i<=carsAdded;i++){
                CarBean c = new CarBean();
                
                c.setBrand_fk(Integer.parseInt(request.getParameter("brand"+i)));
                UserBean thisUser = (UserBean)request.getSession().getAttribute("user");
                c.setUser_fk(thisUser.getId());
                c.setModel(request.getParameter("model"+i));
                
                if(!request.getParameter("description"+i).isEmpty()){
                    c.setDescription(request.getParameter("description"+i));
                }
                
                CarDao carDao = new CarDaoImpl();
                int car_id = carDao.createCar(c);
                
                
                Collection<Part> parts = request.getParts();
                Iterator<Part> part_iterator = parts.iterator();
                while(part_iterator.hasNext()){
                    Part currentPart = part_iterator.next();
                    String uploadedFileName = currentPart.getSubmittedFileName();
                    if(uploadedFileName != null  && !uploadedFileName.isEmpty()){
                            
                        String uploadedFileExtension = uploadedFileName.substring(uploadedFileName.lastIndexOf("."));

                        if(currentPart.getSize()>8000000 || !( (uploadedFileExtension.equals(".jpg")) 
                                || (uploadedFileExtension.equals(".jpeg")) 
                                || (uploadedFileExtension.equals(".png")) 
                                || (uploadedFileExtension.equals(".gif")) 
                                )){

                            currentPart.delete();

                        }
                        else{
                            if(currentPart.getName().equals("file"+i)){
                                InputStream fileStream = currentPart.getInputStream();
                                

                                File uploads_location = new File(getServletContext().getInitParameter("uploads_location"));
                                if(!uploads_location.exists()){
                                    uploads_location.mkdirs();
                                }

                                String car_specific_subfolder =  "/car_id_"+car_id;
                                File car_specific_location = new File(uploads_location, car_specific_subfolder);
                                car_specific_location.mkdir();

                                
                                File photo = File.createTempFile("_photo_",uploadedFileExtension,car_specific_location);
                       

                                if(UserUploadedImages.saveInFileSystem(fileStream, photo)){
                                
                                    System.out.println("ADDCARSERVLET SAVEFILEINFILESYSTEM IS TRUE!!!");
                                    PhotoBean p = new PhotoBean();
                                    p.setCar_fk(car_id);
                                    p.setLocation(car_specific_subfolder+"/"+photo.getName());

                                    photoList.add(p);
                                } 
                            }
                        }                        
                    }
                }              
            }
            
            PhotoDao photoDao = new PhotoDaoImpl();
            photoDao.addPhotos(photoList);

        }

        response.sendRedirect(request.getContextPath()+"/addcars");

    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
