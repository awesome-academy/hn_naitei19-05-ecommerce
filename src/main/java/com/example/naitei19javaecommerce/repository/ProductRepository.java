package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Query("""
            select p from Product p where  p.price >:minPrice and p.price <:maxPrice
            """)
    List<Product> findAllByPrice(Double minPrice, Double maxPrice);

    @Query("""
            select p from Product p where
            (:category IS NULL OR p.category.name =:category ) AND
            (:minPrice IS NULL OR p.price >:minPrice ) AND
            (:maxPrice IS NULL OR p.price <:maxPrice )
            """)
    List<Product> filterProductByCateAndPrice(String category, Double minPrice, Double maxPrice);

    @Query("""
        SELECT p
        FROM Product p
        WHERE p.name LIKE %:keyword%
        OR p.description LIKE %:keyword%
        OR p.category.name LIKE %:keyword%
        OR p.nameBrand LIKE %:keyword%
        OR p.color LIKE %:keyword%
        """)
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.id = :productId")
    void updateProductQuantity(@Param("productId") Long productId, @Param("quantity") int quantity);
}
