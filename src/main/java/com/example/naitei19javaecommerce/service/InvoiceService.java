package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.dto.PaymentHistoryResponse;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO findInvoiceById(Long id);

    List<InvoiceDTO> findInvoicesListByUserId(Long id);

    List<PaymentHistoryResponse> loadAllInvoices(String dateData);
}
