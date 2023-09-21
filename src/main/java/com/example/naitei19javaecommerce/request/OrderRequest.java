package com.example.naitei19javaecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String receivePhone;
    private String receiveAddress;
    private String note;
}
