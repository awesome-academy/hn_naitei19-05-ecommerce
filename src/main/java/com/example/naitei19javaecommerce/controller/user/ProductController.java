package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.dto.ImageDTO;
import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.service.ProductService;
import org.springframework.ui.Model;
import com.example.naitei19javaecommerce.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller("products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public String show(@PathVariable("productId") long id,
                                Model model) {
        ProductDTO productDTO = productService.findProductById(id);
        if(productDTO == null) {
            return "errors/404";
        }

        model.addAttribute("product", productDTO);

        List<ProductDTO> productRelateds = productService
                .findProductRelatedById(productDTO.getCategory().getId(), productDTO.getId());
        model.addAttribute("productRelateds", productRelateds);

        List<ImageDTO> imageDTOs = productService
                .findImageByProductId(productDTO.getId());
        model.addAttribute("images", imageDTOs);

        return "user/products/show";
    }

}
