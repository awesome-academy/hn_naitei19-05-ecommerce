package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.constant.Status;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;

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

    @Autowired
    private ProductService productService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

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
    public List<InvoiceDTO> findInvoicesListByUserIdAndStatus(Long userId, Integer status) {
        List<Invoice> invoices;
        if (status < 0) { // Pass in a number less than 0 if you want to get all invoices
            invoices = invoiceRepository.findInvoicesByUser(userId);
        } else {
            invoices = invoiceRepository.findInvoicesByUserAndStatus(userId, status);
        }
        if (invoices.isEmpty()) {
            return Collections.emptyList();
        }
        return invoices.stream()
                .map(invoice -> {
                    InvoiceDTO invoiceDTO = new InvoiceDTO();
                    BeanUtils.copyProperties(invoice, invoiceDTO);
                    return invoiceDTO;
                })
                .collect(Collectors.toList());
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
    public List<Invoice> findNewOrderList() {
        return invoiceRepository.findNewOrderList();
    }

    @Transactional
    public void saveInvoice(OrderRequest orderRequest, List<CartItem> cartItems, User user) {
        if (orderRequest.getReceiveAddress().compareTo("") == 0) {
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
        invoice.setStatus(Status.ORDER_PLACED.getCode());
        invoice.setInvoiceDetails(invoiceDetails);
        invoiceRepository.save(invoice);
        cartItemService.resetCart(user.getId());
        productService.updateQuantityProducts(invoice);
    }

    @Transactional
    public boolean sendConfirmMail(InvoiceDTO invoice, Integer status,String reason) {
        try{
            invoice.setStatus(status);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(invoice.getUser().getUserDetail().getEmail());
            mailMessage.setSubject("Ecommerce-5 Store Order Delivery");
            if(status == Status.ORDER_RECEIVED.getCode()) {
                mailMessage.setText("Dear " + invoice.getUser().getUserDetail().getLastName() + "! \n" +
                        " Thanks for choosing Ecommerce-5 ™!\n" +
                        " Your Order Id  :" + invoice.getId() + "\n" +
                        " Payment Type : Cash\n" +
                        " Total cost :" + invoice.getTotalPrice() + " $\n" +
                        " We will delivery to:" + invoice.getReceiveAddress() + "\n" +
                        " with phone number :" + invoice.getReceivePhone() + "\n");
            }else{
                Invoice invoice1 = new Invoice();
                BeanUtils.copyProperties(invoice, invoice1);
                productService.updateRejectProductQuantity(invoice1);
                mailMessage.setText("Dear " + invoice.getUser().getUserDetail().getLastName() + "! \n" +
                        " This order delivery to" +  invoice.getReceiveAddress()  +" has been rejected.\n"+
                        " with reason :" + reason + "\n" +
                        " We hope that you will continue buy our products next time." );
            }
            javaMailSender.send(mailMessage);
            invoiceRepository.updateInvoice(status,reason,invoice.getId());
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean cancelInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceDTO, invoice);
        if (Objects.equals(invoice.getStatus(), Status.ORDER_PLACED.getCode())) {
            invoiceRepository.updateInvoice(Status.ORDER_CANCELED.getCode(), "", invoice.getId());
            productService.updateRejectProductQuantity(invoice);
            return true;
        }
        return false;
    }
}
