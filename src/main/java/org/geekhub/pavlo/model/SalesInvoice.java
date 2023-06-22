package org.geekhub.pavlo.model;

import java.math.BigDecimal;

public class SalesInvoice extends DocumentHead<SalesInvoiceRow> {
    private BigDecimal total;

    public SalesInvoice() {
        setTypeId(2);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
