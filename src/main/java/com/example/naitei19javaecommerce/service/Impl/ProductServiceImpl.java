package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.repository.ProductRepository;
import com.example.naitei19javaecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository ;

    @Override
    public List<Product> getOutStandingProducts() {
        return productRepository.findOutStandingProducts();
    }
}

