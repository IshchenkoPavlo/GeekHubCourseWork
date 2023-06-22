package org.geekhub.pavlo.controller.rest;

import org.geekhub.pavlo.model.PurchaseInvoice;
import org.geekhub.pavlo.model.PurchaseInvoiceRow;
import org.geekhub.pavlo.service.PurchaseInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PurchaseInvoiceRestController {
    private final PurchaseInvoiceService invoiceService;

    @Autowired
    public PurchaseInvoiceRestController(PurchaseInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping (value = "/api/v1/purchase-invoice")
    public ResponseEntity<?> addPurchaseInvoice(@RequestBody PurchaseInvoice doc) {
        validateDoc(doc);
        invoiceService.add(doc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateDoc(PurchaseInvoice doc) {
        for (int i = 0; i < doc.getRows().size(); i++) {
            PurchaseInvoiceRow r = doc.getRows().get(i);
            if (r.getGoodsId() == 0) {
                throw new IllegalArgumentException(String.format("Row %d: Goods field not filled", i));
            }

            BigDecimal quantity = r.getQuantity();
            if (quantity == null || quantity.equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException(String.format("Row %d: Quantity field not filled", i));
            }

            if (r.getPrice() == null) r.setPrice(BigDecimal.valueOf(0));
            if (r.getAmount() == null) r.setAmount(BigDecimal.valueOf(0));
        }
    }
}
