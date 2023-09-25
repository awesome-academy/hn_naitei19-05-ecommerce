package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.model.Cart;
import com.example.naitei19javaecommerce.model.UserDetail;
import com.example.naitei19javaecommerce.service.BaseService;

public interface CartService extends BaseService {

    //Check if Cart exists
    public Cart checkExist(Long userId);

    //Create a new cart
    public boolean addCart(User user);

}