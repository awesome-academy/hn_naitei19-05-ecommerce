package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.model.Cart;
import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.repository.CartItemRepository;
import com.example.naitei19javaecommerce.repository.ProductRepository;
import com.example.naitei19javaecommerce.service.CartItemService;
import com.example.naitei19javaecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private final CartItemRepository cartItemRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
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
    public boolean resetCart(Long userId) {
        //Check cart is empty
        if(cartItemRepository.findCartItemsByUserId(userId).isEmpty() == false) {
            // delete all cartitem by userid
            cartItemRepository.deleteAll(cartItemRepository.findCartItemsByUserId(userId));
            return true;
        }
        return false;
    }

    @Override
    public CartItem findById(Long id) {
        return null;
    }

    @Override
    public void addItem(Cart cart, Product product,Long userId, int quantity) {
        //Check if the current product is in the Cart. If so, just update the quantity
        if (cartItemRepository.findCartItems(userId,product.getId()) != null){
            int currentQuantity = cartItemRepository.findCartItems(userId,product.getId()).getQuantity();
            cartItemRepository.findCartItems(userId,product.getId()).setQuantity(currentQuantity + quantity);
            cartItemRepository.save(cartItemRepository.findCartItems(userId,product.getId()));
        }else {
            //Create a new cart to insert
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setModifiedAt(LocalDateTime.now());
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public boolean removeItem(Long id, Long userId) {
        Integer result = cartItemRepository.removeItem(id, userId);
        if (result != null) {
            return true;
        }
        return false;
    }

    private static final int CART_EXPIRATION_HOURS = 24 * 30 * 3; //Expiration time is 90 days
    @Override
    @Scheduled(fixedRate = 86400000) //Run every day
    public void cleanupExpiredCarts() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<CartItem> cartItems = cartItemRepository.findAll();

        for (CartItem cartItem : cartItems) {
            LocalDateTime itemModifiedAt = cartItem.getModifiedAt();
            long hoursSinceLastModified = currentTime.until(itemModifiedAt, ChronoUnit.HOURS);

            if (hoursSinceLastModified >= CART_EXPIRATION_HOURS) {
                cartItemRepository.removeItem(cartItem.getId(), userService.getUserisLogin().getId());
            }
        }
    }

}
