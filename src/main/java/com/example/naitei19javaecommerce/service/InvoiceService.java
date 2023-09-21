package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.model.InvoiceDetail;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO findInvoiceById(Long id);
}
