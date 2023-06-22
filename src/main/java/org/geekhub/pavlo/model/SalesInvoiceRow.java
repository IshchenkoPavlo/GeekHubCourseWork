package org.geekhub.pavlo.model;

import java.math.BigDecimal;

public class SalesInvoiceRow extends DocumentRow {
    private int goodsId;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal amount;

    public SalesInvoiceRow() {
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

