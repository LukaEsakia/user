
package com.klsoukas.samplecarshoponlinemanagement.controller;

import com.klsoukas.samplecarshoponlinemanagement.model.GoogleTokenVerificationUtil;
import com.klsoukas.samplecarshoponlinemanagement.model.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TokenSignIn extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        
        HttpSession session = request.getSession();

        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();        
        String ajaxResponseUrlString = url.substring(0,url.length() - uri.length() + contextPath.length());    
        
        if(session.getAttribute("user") != null){
            response.getWriter().write(ajaxResponseUrlString);
        }
        else{

            String idTokenString = request.getParameter("idtoken");
            UserBean user = GoogleTokenVerificationUtil.userIsValid(idTokenString);
            if(user == null){
                System.out.println("Invalid Id token or database Error!");
                response.getWriter().write(ajaxResponseUrlString);
            }
            else{

                try{
                session.invalidate();
                }
                catch(Exception e){
                    System.out.println("SESSION ALREADY INVALIDATED: "+e);
                }
                session = request.getSession();
                session.setAttribute("user", user);

                response.getWriter().write(ajaxResponseUrlString);
            }
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
