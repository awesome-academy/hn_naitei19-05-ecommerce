package com.example.naitei19javaecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "star")
    private Integer star;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "id_invoice_detail", referencedColumnName = "id")
    private InvoiceDetail invoiceDetail ;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

}
