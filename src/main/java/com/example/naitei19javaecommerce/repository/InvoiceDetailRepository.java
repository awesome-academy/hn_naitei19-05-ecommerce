package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.dto.InvoiceDetailDTO;
import com.example.naitei19javaecommerce.model.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
    @Query(""" 
            SELECT i_detail
            FROM InvoiceDetail i_detail INNER JOIN i_detail.invoice i
            WHERE i.id = :invoiceId 
           """)
    List<InvoiceDetail> findInvoiceDetailsByProductId(@Param("invoiceId") Long invoiceId);
}
