

package com.klsoukas.samplecarshoponlinemanagement.model;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.api.client.json.jackson2.JacksonFactory;


public class GoogleTokenVerificationUtil {
    
    private static final com.google.api.client.http.HttpTransport TRANSPORT;
    private static final com.google.api.client.json.JsonFactory JSON_FACTORY;

    static {
        try {
            
            TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            JSON_FACTORY = JacksonFactory.getDefaultInstance();

        } catch (GeneralSecurityException ex) {
            Logger.getLogger(GoogleTokenVerificationUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleTokenVerificationUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
    
    
    public static UserBean userIsValid(String idTokenString){
    
        try {
            
             
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, JSON_FACTORY)
                    .setAudience(Collections.singletonList("289108522458-ccr2le3rbcmthadua80hk9epkrsakal1.apps.googleusercontent.com"))
                    
                    .build();
            
            
            
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                

                String googleId = payload.getSubject();
                UserDao userDao = new UserDaoImpl();
                UserBean user = userDao.findUserByGoogleId(googleId);
                if (user == null){
                    
                    String email = payload.getEmail();
                    String pictureUrl = (String) payload.get("picture");
                    String familyName = (String) payload.get("family_name");
                    String givenName = (String) payload.get("given_name");

                    user = new UserBean();
                    user.setGoogleId(googleId);
                    user.setFirstName(givenName);
                    user.setLastName(familyName);
                    user.setImageURL(pictureUrl);
                    user.setEmail(email);
                    
                    user = userDao.createNewUser(user);
                }
                
                return user;
            } else {
                System.out.println("Invalid ID token.");
                return null;
            }
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(GoogleTokenVerificationUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(GoogleTokenVerificationUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
        
    }
    
}
