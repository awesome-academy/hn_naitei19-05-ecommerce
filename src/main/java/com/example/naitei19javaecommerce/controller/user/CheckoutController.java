package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.request.OrderRequest;
import com.example.naitei19javaecommerce.service.CartItemService;
import com.example.naitei19javaecommerce.service.InvoiceService;
import com.example.naitei19javaecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller("checkout")
public class CheckoutController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Long userId = userService.getUserisLogin().getId();
        List<CartItem> items = cartItemService.CartItemByUserId(userId);
        if (items.isEmpty()) {
            return "errors/404";
        }
        int totalQuantity = cartItemService.totalQuantityItem(items);
        BigDecimal totalPrice = cartItemService.calculateTotalCartPrice(items);
        OrderRequest orderRequest = new OrderRequest();
        model.addAttribute("items", items);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("order", orderRequest);
        return "user/checkout/checkout";
    }

    @PostMapping("/checkout")
    public String saveInvoice(@ModelAttribute("order") OrderRequest orderRequest,
                              Model model) {
        User user = userService.getUserisLogin();
        List<CartItem> items = cartItemService.CartItemByUserId(user.getId());
        orderRequest.setTotalQuantity(cartItemService.totalQuantityItem(items));
        orderRequest.setTotalPrice(cartItemService.calculateTotalCartPrice(items));
        invoiceService.saveInvoice(orderRequest, items, user);
        return "redirect:/invoices";
    }
}
