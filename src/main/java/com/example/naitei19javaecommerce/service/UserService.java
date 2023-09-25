package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.model.UserDetail;

public interface UserService {
    User findByUsername (String username);

    //Get the currently logged in User
    User getUserisLogin();
}