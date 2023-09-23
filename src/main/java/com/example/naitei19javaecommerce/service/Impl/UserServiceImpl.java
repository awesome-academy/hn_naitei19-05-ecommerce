package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.model.UserDetail;
import com.example.naitei19javaecommerce.repository.UserRepository;
import com.example.naitei19javaecommerce.service.UserService;
import com.example.naitei19javaecommerce.service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserisLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if(customUserDetails.getUser() != null){
            return customUserDetails.getUser();
        }
        return null;
    }

}