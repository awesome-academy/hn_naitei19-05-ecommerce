package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.InvoiceDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
    @Query(""" 
            SELECT i_detail
            FROM InvoiceDetail i_detail INNER JOIN i_detail.invoice i
            WHERE i.id = :invoiceId
           """)
    List<InvoiceDetail> findInvoiceDetailsByProductId(@Param("invoiceId") Long invoiceId);

    @Modifying
    @Transactional
    @Query(value = """
                        INSERT INTO invoicesdetail (id_invoice, id_product, quantity, price)
                        VALUES (?1, ?2, ?3, ?4)
            """,
            nativeQuery = true)
    void addInvoiceDetail(Long invoiceId, Long productId, Integer quantity, BigDecimal price);

}
