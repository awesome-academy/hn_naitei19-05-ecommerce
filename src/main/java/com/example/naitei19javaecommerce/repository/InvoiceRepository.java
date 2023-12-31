package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i WHERE i.user.id = :userId ORDER BY i.createdAt DESC")
    List<Invoice> findInvoicesByUser(@Param("userId") Long userId);

    @Query("SELECT i FROM Invoice i WHERE i.user.id = :userId and i.status = :status ORDER BY i.createdAt DESC")
    List<Invoice> findInvoicesByUserAndStatus(@Param("userId") Long userId, @Param("status") Integer status);

    @Query(value = "SELECT * FROM invoices where DATE(created_at) = :dataDate and status = 4 ", nativeQuery = true)
    List<Invoice> findAllWithDateAndStatus(String dataDate);

    @Query(" select i from Invoice i where i.status = 1")
    List<Invoice> findNewOrderList();
    @Query(" select i from Invoice i where i.id = :id and i.user.id = :userId")
    Invoice findInvoicesByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Query("""
    update Invoice i SET i.status = ?1
    , i.rejectionReason = CASE WHEN ?2 IS NULL THEN i.rejectionReason ELSE ?2 END
    WHERE i.id = ?3
    """)
    void updateInvoice(Integer status, String reason, Long id);
}
