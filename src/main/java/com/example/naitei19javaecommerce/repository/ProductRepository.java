package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Category;
import com.example.naitei19javaecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.id <> :productId")
    List<Product> findProductRelatedById(@Param("categoryId") Long categoryId,
                                             @Param("productId") Long productId);
    @Query(value = """
            SELECT p.*
                          FROM products p
                          JOIN invoicesdetail i on p.id = i.id_product
                          GROUP BY i.id_product
                          ORDER BY sum(i.quantity) DESC limit 5
                         """, nativeQuery = true)
    List<Product> findOutStandingProducts();
}
