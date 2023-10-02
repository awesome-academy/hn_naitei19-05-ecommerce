package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.dto.UserDTO;
import com.example.naitei19javaecommerce.constant.Role;
import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.model.UserDetail;
import com.example.naitei19javaecommerce.repository.UserRepository;
import com.example.naitei19javaecommerce.service.UserService;
import com.example.naitei19javaecommerce.service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

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

    public String encodePassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public void saveUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodePassword(userDTO.getPassword()));
        user.setRole(Role.USER.getValue());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive((byte) 1);

        UserDetail userDetail = new UserDetail();
        userDetail.setAddress(userDTO.getAddress());
        userDetail.setEmail(userDTO.getEmail());
        userDetail.setFirstName(userDTO.getFirstName());
        userDetail.setLastName(userDTO.getLastName());
        userDetail.setPhone(userDTO.getPhone());
        user.setUserDetail(userDetail);

        userRepository.save(user);
    }
}