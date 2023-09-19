package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.service.ProductService;
import com.example.naitei19javaecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;


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
        BaseController.loadCurretUser();
        return "user/static-pages/home-page";
    }

    @GetMapping("/shop")
    public String Shop(Model model) {
        model.addAttribute("allProducts", productService.findAll());
        return "user/search-page/index";
    }

    @PostMapping("/filter")
    public String filterProducts(Model model,
                                 @RequestParam(name = "selectedCategories", required = false) List<String> selectedCategories,
                                 @RequestParam(name = "minValue", required = false) String  minValue,
                                 @RequestParam(name = "maxValue", required = false) String maxValue ) {
            Double minPrice = Double.valueOf(minValue.substring(1));
            Double maxPrice = Double.valueOf(maxValue.substring(1));

            List<Product> filteredResult = productService.filterProducts(selectedCategories,minPrice,maxPrice);
            if(filteredResult.isEmpty()){
                model.addAttribute("message","Không tồn tại sản phẩm!");
            }else {
                model.addAttribute("filterProducts",filteredResult);
            }
        return "user/search-page/index";
    }
}

