package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public String Home(Model model) {

        List<Product> outStandingProducts = productService.getOutStandingProducts();
        model.addAttribute("outStandingProducts", outStandingProducts);

        return "user/static-pages/home-page";
    }
}
