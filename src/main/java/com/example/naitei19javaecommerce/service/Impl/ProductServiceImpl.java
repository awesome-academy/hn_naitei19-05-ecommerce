package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.dto.ImageDTO;
import com.example.naitei19javaecommerce.dto.ProductDTO;
import com.example.naitei19javaecommerce.model.Image;
import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.repository.ImageRepository;
import com.example.naitei19javaecommerce.repository.ProductRepository;
import com.example.naitei19javaecommerce.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository ;
    @Autowired
    ImageRepository imageRepository;

    @Override
    public List<Product> getOutStandingProducts() {
        return productRepository.findOutStandingProducts();
    }

    @Override
    public ProductDTO findProductById(long id) {
        Product product = productRepository.findById(id).orElseGet(() -> null);
        if (product != null) {
            return mapToProductDto(product);
        } else {
            return null;
        }
    }


    @Override
    public List<ProductDTO> findProductRelatedById(Long categoryId, Long productId) {
        List<Product> relatedProducts = productRepository.findProductRelatedById(categoryId, productId);
        return relatedProducts.stream().
                map((relatedProduct) -> mapToProductDto(relatedProduct)).collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> findImageByProductId(Long productId) {
        List<Image> images = imageRepository.findImagesByProductId(productId);
        return  images.stream().limit(4)
                .map((image) -> mapToImageDto(image)).collect(Collectors.toList());
    }

    private Product mapToProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }

    private ProductDTO mapToProductDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    private Image mapToImage(ImageDTO imageDTO) {
        Image image = new Image();
        BeanUtils.copyProperties(imageDTO, image);
        return image;
    }

    private ImageDTO mapToImageDto(Image image) {
        ImageDTO imageDTO  = new ImageDTO();
        BeanUtils.copyProperties(image, imageDTO);
        return imageDTO;
    }
}

