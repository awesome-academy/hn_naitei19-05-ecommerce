package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Invoice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i WHERE i.user.id = :userId ORDER BY i.createdAt DESC")
    List<Invoice> findProductRelatedById(@Param("userId") Long userId);


    @Query(value = "SELECT * FROM invoices where DATE(created_at) = :dataDate and status = 4 ", nativeQuery = true)
    List<Invoice>findAllWithDateAndStatus(String dataDate);

}
