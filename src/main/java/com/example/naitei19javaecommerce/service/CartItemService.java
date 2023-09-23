package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.model.Cart;
import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService extends BaseService<Long, CartItem> {
    public void addItem(Cart cart, Product product, Long userId, int quantity);

    public List<CartItem> CartItemByUserId(Long id);

    public boolean updateItem(Long id, Long userId, int quantity);

    public boolean removeItem(Long id, Long userId);

    public BigDecimal calculateTotalCartPrice(List<CartItem> cartItems);

    public int totalQuantityItem(List<CartItem> cartItems);
}
