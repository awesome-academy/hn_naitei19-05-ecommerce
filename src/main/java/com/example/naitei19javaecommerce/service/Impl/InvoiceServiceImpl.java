package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.dto.ProductDTO;
import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.model.InvoiceDetail;
import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.repository.ImageRepository;
import com.example.naitei19javaecommerce.repository.InvoiceRepository;
import com.example.naitei19javaecommerce.repository.ProductRepository;
import com.example.naitei19javaecommerce.service.InvoiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

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
        if (invoices.isEmpty()){
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
}
