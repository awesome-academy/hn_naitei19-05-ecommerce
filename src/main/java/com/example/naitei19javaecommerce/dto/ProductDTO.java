package com.example.naitei19javaecommerce.dto;

import com.example.naitei19javaecommerce.model.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Category category;
    private String color;
    private String type;
    private Integer quantity;
    private BigDecimal price;
    private String nameBrand;
    private String description;
    private Long soldQuantity;
    private String imageAvatar;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
}
