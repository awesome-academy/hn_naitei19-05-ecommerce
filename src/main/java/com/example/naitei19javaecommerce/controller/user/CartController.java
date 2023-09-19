package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartItemService cartItemService;

    @RequestMapping("/cart")
    public String ViewCart(Model model) {
        Long User_id = 1L;
        List<CartItem> itemsList = cartItemService.CartItemByUserId(User_id);

        int totalQuantity = cartItemService.totalQuantityItem(itemsList);
        BigDecimal totalPrice = cartItemService.calculateTotalCartPrice(itemsList);
        model.addAttribute("itemsList", itemsList);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        return "user/cart/layout-cart";
    }
}
