package com.st.studentregistration.models.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Auth {
    public static Auth getInstance(){
        return new Auth();
    }

    public boolean isUserLoggedIn(HttpServletRequest request){
        try {
            HttpSession session = request.getSession(false);
            return session.getAttribute("currentUser") != null;
        }catch (Exception e){
            return false;
        }
    }
}
