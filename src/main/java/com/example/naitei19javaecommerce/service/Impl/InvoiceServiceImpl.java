package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.dto.PaymentHistoryResponse;
import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.repository.InvoiceRepository;
import com.example.naitei19javaecommerce.repository.ProductRepository;
import com.example.naitei19javaecommerce.model.*;
import com.example.naitei19javaecommerce.repository.*;
import com.example.naitei19javaecommerce.request.OrderRequest;
import com.example.naitei19javaecommerce.service.CartItemService;
import com.example.naitei19javaecommerce.service.InvoiceService;
import com.example.naitei19javaecommerce.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public InvoiceDTO findInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseGet(() -> null);
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        if (invoice != null) {
            BeanUtils.copyProperties(invoice, invoiceDTO);
            return invoiceDTO;
        }
        return null;
    }

    @Override
    public List<InvoiceDTO> findInvoicesListByUserId(Long userId) {
        List<Invoice> invoices = invoiceRepository.findProductRelatedById(userId);
        if (invoices.isEmpty()) {
            return Collections.emptyList();
        }
        List<InvoiceDTO> invoiceDTOs = invoices.stream()
                .map(invoice -> {
                    InvoiceDTO invoiceDTO = new InvoiceDTO();
                    BeanUtils.copyProperties(invoice, invoiceDTO);
                    return invoiceDTO;
                })
                .collect(Collectors.toList());
        return invoiceDTOs;
    }
    public List<PaymentHistoryResponse> loadAllInvoices(String dataDate) {
        List<PaymentHistoryResponse> paymentHistoryResponseList = new ArrayList<>();
        List<Invoice> invoices = invoiceRepository.findAllWithDateAndStatus(dataDate);
        for (Invoice invoice : invoices) {
            PaymentHistoryResponse paymentHistoryResponse = new PaymentHistoryResponse();
            paymentHistoryResponse.setInvoice(invoice);
            paymentHistoryResponse.setCustomerName(Objects.requireNonNullElse(invoice.getUser().getUserDetail().getLastName(), "Unknown User"));
            paymentHistoryResponseList.add(paymentHistoryResponse);
        }
        return paymentHistoryResponseList;
    }

    @Override
    @Transactional
    public void saveInvoice(OrderRequest orderRequest, List<CartItem> cartItems, User user) {
        if (orderRequest.getReceiveAddress().compareTo("") == 0){
            orderRequest.setReceiveAddress(user.getUserDetail().getAddress());
        }
        if (orderRequest.getReceivePhone().compareTo("") == 0) {
            orderRequest.setReceivePhone(user.getUserDetail().getPhone());
        }
        Invoice invoice = new Invoice();
        Set<InvoiceDetail> invoiceDetails = cartItems.stream()
                .map(cartItem -> InvoiceDetail.builder()
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                        .invoice(invoice)
                        .build())
                .collect(Collectors.toSet());
        BeanUtils.copyProperties(orderRequest, invoice);
        invoice.setUser(user);
        invoice.setStatus(1);
        invoice.setInvoiceDetails(invoiceDetails);
        invoiceRepository.save(invoice);
        cartItemService.resetCart(user.getId());
    }
}
