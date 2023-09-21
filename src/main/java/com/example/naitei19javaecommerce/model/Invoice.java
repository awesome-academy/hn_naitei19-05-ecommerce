package com.example.naitei19javaecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

//@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice")
    private Set<InvoiceDetail> invoiceDetails;

    @Column(name = "status")
    private Integer status;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "note")
    private String note;

    @Column(name = "receive_address")
    private String receiveAddress;

    @Column(name = "receive_phone")
    private String receivePhone;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

}
