package com.example.naitei19javaecommerce.dto;

import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailDTO {
    private Long id;
    private Product product ;
    private Integer quantity;
    private BigDecimal price;
}
