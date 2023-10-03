package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.service.InvoiceService;
import com.example.naitei19javaecommerce.service.ProductService;
import com.example.naitei19javaecommerce.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("invoices")
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String list(Model model) {
        Long userId = userService.getUserisLogin().getId();
        List<InvoiceDTO> invoices = invoiceService.findInvoicesListByUserIdAndStatus(userId, -1);
        if (invoices.isEmpty()){
            model.addAttribute("message", "There are no invoices yet!");
        }
        model.addAttribute("invoices", invoices);
        return "user/invoices/list";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(name = "status", required = false) Integer status,
                       Model model) {
        Long userId = userService.getUserisLogin().getId();
        List<InvoiceDTO> invoiceDTOs = invoiceService.findInvoicesListByUserIdAndStatus(userId, status);
        if (invoiceDTOs.isEmpty()){
            model.addAttribute("message", "There are no invoices yet!");
        }
        model.addAttribute("invoices", invoiceDTOs);
        return "user/invoices/list";
    }

    @GetMapping("/{invoiceId}")
    public String show(@PathVariable("invoiceId") Long id,
                       Model model) {
        InvoiceDTO invoiceDTO = invoiceService.findInvoiceById(id);
        if(invoiceDTO == null) {
            return "errors/404";
        }
        model.addAttribute("invoice", invoiceDTO);
        return "user/invoices/layout-invoice";
    }
}
