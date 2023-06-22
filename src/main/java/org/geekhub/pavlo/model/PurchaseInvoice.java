package org.geekhub.pavlo.model;

import java.math.BigDecimal;

public class PurchaseInvoice extends DocumentHead<PurchaseInvoiceRow> {
    private String comment;
    private BigDecimal total;

    public PurchaseInvoice() {
        setTypeId(1);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
