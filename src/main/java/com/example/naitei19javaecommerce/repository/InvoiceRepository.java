package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE i.user.id = :userId ORDER BY i.createdAt DESC")
    List<Invoice> findProductRelatedById(@Param("userId") Long userId);
}
