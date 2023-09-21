package com.example.naitei19javaecommerce.service;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.dto.PaymentHistoryResponse;
import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.model.CartItem;
import com.example.naitei19javaecommerce.model.User;
import com.example.naitei19javaecommerce.request.OrderRequest;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO findInvoiceById(Long id);

    List<InvoiceDTO> findInvoicesListByUserId(Long id);

    List<PaymentHistoryResponse> loadAllInvoices(String dateData);

    List<Invoice> findNewOrderList();

    void saveInvoice(OrderRequest orderRequest, List<CartItem> cartItems, User user);
}
