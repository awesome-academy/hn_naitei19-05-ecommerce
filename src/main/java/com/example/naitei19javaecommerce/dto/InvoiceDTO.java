package com.example.naitei19javaecommerce.dto;

import com.example.naitei19javaecommerce.model.InvoiceDetail;
import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private Long id;
    private User user;
    private Set<InvoiceDetail> invoiceDetails;
    private Integer status;
    private String rejectionReason;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String note;
    private String receiveAddress;
    private String receivePhone;
    private LocalDateTime createdAt;
}
