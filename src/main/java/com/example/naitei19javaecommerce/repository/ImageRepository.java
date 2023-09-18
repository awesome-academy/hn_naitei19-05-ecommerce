package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "SELECT * FROM images WHERE id_product = :productId", nativeQuery = true)
    List<Image> findImagesByProductId(@Param("productId") Long productId);
}
