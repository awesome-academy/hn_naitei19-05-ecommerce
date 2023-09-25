package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.service.security.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    public  static CustomUserDetails loadCurrentUser (){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
            return customUserDetails;
        }
        else {
            return null;
        }
    }
}
