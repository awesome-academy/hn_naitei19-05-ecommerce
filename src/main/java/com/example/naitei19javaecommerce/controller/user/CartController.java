package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller("cart")
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping()
    public String index(Model model) {
        Long userId = 1L;
        List<CartItem> items = cartItemService.CartItemByUserId(userId);

        int totalQuantity = cartItemService.totalQuantityItem(items);
        BigDecimal totalPrice = cartItemService.calculateTotalCartPrice(items);
        model.addAttribute("items", items);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        return "user/cart/layout-cart";
    }

    @PostMapping(value = "/update")
    public String update(Model model, @RequestParam("id") Long id, @RequestParam("quantity") int quantity) {
        Long userId = 1L;
        String message;
        if (cartItemService.updateItem(id, userId, quantity)) {
            message = "Update Success";
        } else {
            message = "Update fail";
        }
        model.addAttribute("alert", message);
        return "user/cart/layout-cart";
    }


    @DeleteMapping(value = "/{id}")
    public String delete(Model model, @PathVariable Long id) {
        String message;
        Long userId = 1L;
        if (cartItemService.removeItem(id, userId)) {
            message = "Delete Success";
        } else {
           message = "Item not exits!!! Please reload again!!!";
        }
        model.addAttribute("alert", message);
        return "user/cart/layout-cart";
    }
}
