package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.repository.UserRepository;
import com.example.naitei19javaecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}