package org.geekhub.pavlo.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class PurchaseInvoiceController {
    @Autowired
    public PurchaseInvoiceController() {
    }

    @GetMapping("/purchase-invoice")
    public String goodsPage(Model model) {
        model.addAttribute("currentDay", LocalDate.now());
        return "PurchaseInvoice";
    }

}
