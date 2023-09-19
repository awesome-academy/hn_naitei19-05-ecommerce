package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService extends BaseService<Long, CartItem> {
    public void addItem(CartItem item);

    public List<CartItem> CartItemByUserId(Long id);

    public void removeItem(CartItem item);

    public BigDecimal calculateTotalCartPrice(List<CartItem> cartItems);

    public int totalQuantityItem(List<CartItem> cartItems);
}
