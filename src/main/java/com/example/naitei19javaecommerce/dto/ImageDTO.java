package com.example.naitei19javaecommerce.dto;

import com.example.naitei19javaecommerce.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String image;
}
