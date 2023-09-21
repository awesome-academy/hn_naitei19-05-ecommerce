package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.dto.ImageDTO;
import com.example.naitei19javaecommerce.dto.ProductDTO;
import com.example.naitei19javaecommerce.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getOutStandingProducts();

    ProductDTO findProductById(long id);

    List<ProductDTO> findProductRelatedById(Long categoryId, Long productId);

    List<ImageDTO> findImageByProductId (Long productId);

    List<Product> findAll();

    List<Product> findAllByPrice(Double minPrice , Double maxPrice);

    List<Product> filterProducts(List<String> selectedCategories, Double minPrice , Double maxPrice);
}
