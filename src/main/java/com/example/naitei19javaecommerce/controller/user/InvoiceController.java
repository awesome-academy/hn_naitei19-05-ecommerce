package com.example.naitei19javaecommerce.controller.user;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.service.InvoiceService;
import com.example.naitei19javaecommerce.service.ProductService;
import com.example.naitei19javaecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<InvoiceDTO> invoices = invoiceService.findInvoicesListByUserId(userId);
        if (invoices.isEmpty()){
            model.addAttribute("message", "Chưa có hóa đơn nào!");
        }
        model.addAttribute("invoices", invoices);
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
        return "user/invoices/show";
    }
}
