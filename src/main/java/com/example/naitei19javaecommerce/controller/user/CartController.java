package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.service.CartItemService;
import com.example.naitei19javaecommerce.service.CartService;
import com.example.naitei19javaecommerce.service.ProductService;
import com.example.naitei19javaecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller("cart")
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String index(Model model) {
        Long userId = userService.getUserisLogin().getId();
        List<CartItem> items = cartItemService.CartItemByUserId(userId);

        int totalQuantity = cartItemService.totalQuantityItem(items);
        BigDecimal totalPrice = cartItemService.calculateTotalCartPrice(items);
        model.addAttribute("items", items);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        return "user/cart/layout-cart";
    }


    //add item into cart
    @PostMapping(value = "/")
    public String create(Model model,
                         @RequestParam("id") Long id,
                         @RequestParam("quantity") int quantity) {
        Long userId = userService.getUserisLogin().getId();
        if (cartService.checkExist(userId) != null) {
            cartItemService.addItem(cartService.checkExist(userId), productService.findProductById(id), userId, quantity);
        } else {
            cartService.addCart(userService.getUserisLogin());
            cartItemService.addItem(cartService.checkExist(userId), productService.findProductById(id), userId, quantity);
        }
        return "user/cart/layout-cart";
    }

    // update quantity item in cart
    @PostMapping(value = "/update")
    public String update(Model model,
                         @RequestParam("id") Long id,
                         @RequestParam("quantity") int quantity) {
        Long userId = userService.getUserisLogin().getId();
        String message;
        if (cartItemService.updateItem(id, userId, quantity)) {
            message = "ok";
        } else {
            message = "error";
        }
        model.addAttribute("alert", message);
        return "user/cart/layout-cart";
    }

    //delete item by id item
    @DeleteMapping(value = "/{id}")
    public String delete(Model model,
                         @PathVariable Long id) {
        String message;
        Long userId = userService.getUserisLogin().getId();
        if (cartItemService.removeItem(id, userId)) {
            message = "Delete Success";
        } else {
            message = "Item not exits!!! Please reload again!!!";
        }
        model.addAttribute("alert", message);
        return "user/cart/layout-cart";
    }


    @DeleteMapping(value = "/reset")
    public String reset(Model model) {
        Long userId = userService.getUserisLogin().getId();
        String status;
        if (cartItemService.resetCart(userId)) {
            status = "ok";
        }
        else {
            status = "fail";
        }
        model.addAttribute("alert", status);
        return "user/cart/layout-cart";
    }
}
