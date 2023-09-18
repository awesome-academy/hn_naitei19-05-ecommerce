package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = """
            SELECT p.*
                          FROM products p
                          JOIN invoicesdetail i on p.id = i.id_product
                          GROUP BY i.id_product
                          ORDER BY sum(i.quantity) DESC limit 5
                         """, nativeQuery = true)
    List<Product> findOutStandingProducts();
}
