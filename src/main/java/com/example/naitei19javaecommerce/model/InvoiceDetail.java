package com.example.naitei19javaecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;

//@Data

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoicesdetail")
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_invoice", referencedColumnName = "id")
    private Invoice invoice;

    @OneToOne
    @JoinColumn(name= "id_product", referencedColumnName = "id")
    private Product product ;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;
}
