package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.model.InvoiceDetail;
import com.example.naitei19javaecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
