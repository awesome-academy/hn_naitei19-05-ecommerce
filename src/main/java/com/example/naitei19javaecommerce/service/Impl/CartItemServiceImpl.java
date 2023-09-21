package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.dto.ProductDTO;
import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.repository.CartItemRepository;
import com.example.naitei19javaecommerce.repository.ProductRepository;
import com.example.naitei19javaecommerce.service.CartItemService;
import com.example.naitei19javaecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private final CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public List<CartItem> CartItemByUserId(Long id) {
        return cartItemRepository.findCartItemsByUserId(id);
    }

    @Override
    public boolean updateItem(Long id, Long userId, int quantity) {
        Product curentProduct = productRepository.getReferenceById(id);
        if (quantity < curentProduct.getQuantity()) {
            Integer result = cartItemRepository.updateItem(quantity, id, userId);
            if (result != null) {
                return true;
            }
        } else {
            Integer result = cartItemRepository.updateItem(curentProduct.getQuantity(), id, userId);
            if (result != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BigDecimal calculateTotalCartPrice(List<CartItem> cartItems) {
        // Initialize total to zero
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            int quantity = cartItem.getQuantity();
            BigDecimal price = cartItem.getProduct().getPrice();

            // Calculate itemTotal as the product of quantity and price
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));

            // Add itemTotal to the total
            total = total.add(itemTotal);
        }
        return total;
    }

    @Override
    public int totalQuantityItem(List<CartItem> cartItems) {
        int totalQuantity = 0;
        for (CartItem cartItem : cartItems) {
            totalQuantity += cartItem.getQuantity();
        }
        return totalQuantity;
    }

    @Override
    public CartItem findById(Long id) {
        return null;
    }

    @Override
    public void addItem(CartItem item) {

    }

    @Override
    public boolean removeItem(Long id, Long userId) {
        Integer result = cartItemRepository.removeItem(id, userId);
        if (result != null) {
            return true;
        }
        return false;
    }
}
