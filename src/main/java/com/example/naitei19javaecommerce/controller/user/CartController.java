package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @DeleteMapping(value = "/{id}")
    public String delete(Model model,@PathVariable Long id) {
        Long userId = 1L;
        if(cartItemService.removeItem(id,userId)) {
            String message = "Delete Success";
            model.addAttribute("alert", message);
        }else {
            String message = "Item not exits!!! Please reload again!!!";
            model.addAttribute("alert", message);
        }
        return "user/cart/shopping-cart";
    }
}
