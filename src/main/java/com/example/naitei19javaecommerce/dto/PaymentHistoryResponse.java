package com.example.naitei19javaecommerce.dto;

import com.example.naitei19javaecommerce.model.Invoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryResponse {
    private Invoice invoice;
    private String customerName;
}
