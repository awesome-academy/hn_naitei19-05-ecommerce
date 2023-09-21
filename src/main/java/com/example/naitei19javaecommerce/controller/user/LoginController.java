package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.model.UserDetail;
import com.example.naitei19javaecommerce.repository.UserRepository;
import com.example.naitei19javaecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String getLoginAdmin(){
        return "user/static-pages/login";
    }
}