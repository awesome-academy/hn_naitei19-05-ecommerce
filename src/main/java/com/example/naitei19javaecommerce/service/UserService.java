package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.model.User;

public interface UserService {
    User findByUsername (String username);
}