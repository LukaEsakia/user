
package com.klsoukas.samplecarshoponlinemanagement.model;


public interface UserDao {
    UserBean findUserByGoogleId(String googleId);
    
    UserBean createNewUser(UserBean user);
}
