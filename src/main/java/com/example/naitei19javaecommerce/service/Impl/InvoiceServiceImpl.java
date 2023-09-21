package com.example.naitei19javaecommerce.service.Impl;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.dto.InvoiceDetailDTO;
import com.example.naitei19javaecommerce.dto.ProductDTO;
import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.model.InvoiceDetail;
import com.example.naitei19javaecommerce.model.Product;
import com.example.naitei19javaecommerce.repository.ImageRepository;
import com.example.naitei19javaecommerce.repository.InvoiceDetailRepository;
import com.example.naitei19javaecommerce.repository.InvoiceRepository;
import com.example.naitei19javaecommerce.repository.ProductRepository;
import com.example.naitei19javaecommerce.service.InvoiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public InvoiceDTO findInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseGet(() -> null);
        List<InvoiceDetailDTO> invoiceDetails = invoiceDetailRepository.findInvoiceDetailsByProductId(id)
                .stream().map((invoiceDetail) -> mapToInvoiceDetailDto(invoiceDetail)).collect(Collectors.toList());
        if (invoice != null) {
            return mapToInvoiceDto(invoice, invoiceDetails);
        }
        return null;
    }

    private InvoiceDTO mapToInvoiceDto(Invoice invoice, List<InvoiceDetailDTO> invoiceDetails) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        BeanUtils.copyProperties(invoice, invoiceDTO);
        invoiceDTO.setInvoiceDetails(invoiceDetails);
        return invoiceDTO;
    }

    private InvoiceDetailDTO mapToInvoiceDetailDto(InvoiceDetail invoiceDetail){
        InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
        BeanUtils.copyProperties(invoiceDetail, invoiceDetailDTO);
        return invoiceDetailDTO;
    }
}
