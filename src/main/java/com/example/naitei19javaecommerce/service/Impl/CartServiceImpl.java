package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.model.Cart;
import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.model.UserDetail;
import com.example.naitei19javaecommerce.repository.CartRepository;
import com.example.naitei19javaecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Object findById(Object id) {
        return null;
    }

    @Override
    public boolean addCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());
        if(cartRepository.save(cart) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Cart checkExist(Long userId) {
        if (cartRepository.findCartByUserId(userId) != null) {
            return cartRepository.findCartByUserId(userId);
        }
        return null;
    }


}
