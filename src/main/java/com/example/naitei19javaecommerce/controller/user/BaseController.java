package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class BaseController {
    public static void loadCurretUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
//            session.setAttribute("currentUser", user);
            System.out.println( "hello" + auth.getName());
        }
        else {
            System.out.println("user not login");
        }
    }
}
