package com.example.naitei19javaecommerce.controller.admin;

import com.example.naitei19javaecommerce.dto.InvoiceDTO;
import com.example.naitei19javaecommerce.dto.PaymentHistoryResponse;
import com.example.naitei19javaecommerce.model.Invoice;
import com.example.naitei19javaecommerce.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("")
    public String getPaymentHistory(Model model,
                                    @RequestParam(name = "dateData", required = false) String dateData) {
        BigDecimal totalSales = new BigDecimal("0.0");
        if (dateData == null || dateData.equals("")) {
            Date dateToday = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateData = dateFormat.format(dateToday);
        }
        List<PaymentHistoryResponse> paymentHistoryResponseList = invoiceService.loadAllInvoices(dateData);
        for (PaymentHistoryResponse paymentHistoryResponse : paymentHistoryResponseList) {
            totalSales = totalSales.add(paymentHistoryResponse.getInvoice().getTotalPrice());
        }
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("orderAmout", paymentHistoryResponseList.size());
        model.addAttribute("dateFilter", dateData);
        model.addAttribute("payments", paymentHistoryResponseList);
        return "admin/invoice/index";
    }

    @GetMapping("/orders/by-waiting")
    public String ordersByStatus(Model model) {
        List<Invoice> newOrders = invoiceService.findNewOrderList();
        model.addAttribute("newOrders", newOrders);
        model.addAttribute("orderAmount", newOrders.size());
        return "admin/invoice/order-by-status";
    }

    @GetMapping("/orders/{id}")
    public String getOrderDetail(Model model , @PathVariable(value = "id") Long orderId){
        InvoiceDTO orderDetailResponse = invoiceService.findInvoiceById(orderId);
        model.addAttribute("order",orderDetailResponse);
        return "admin/invoice/order-detail";
    }

    @PostMapping("/orders/{id}")
    public String HandleOrder(Model model,@PathVariable(value = "id") Long orderId,@ModelAttribute("reason") String reason ,
                               @ModelAttribute("status") Integer status ){
        InvoiceDTO orderResponse = invoiceService.findInvoiceById(orderId);
        if(!invoiceService.sendConfirmMail(orderResponse,status,reason)){
            return "errors/404";
        }
        model.addAttribute("order",orderResponse);
        return "admin/invoice/order-detail";
    }

}
