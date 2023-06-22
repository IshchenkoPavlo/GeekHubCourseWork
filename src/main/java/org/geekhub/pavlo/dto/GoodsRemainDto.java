package org.geekhub.pavlo.dto;

import java.math.BigDecimal;

public class GoodsRemainDto {
    private int id;
    private boolean isGroup;
    private String name;
    private float price;
    private float pricePurchase;
    private BigDecimal remain;

    public GoodsRemainDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPricePurchase() {
        return pricePurchase;
    }

    public void setPricePurchase(float pricePurchase) {
        this.pricePurchase = pricePurchase;
    }

    public BigDecimal getRemain() {
        return remain;
    }

    public void setRemain(BigDecimal remain) {
        this.remain = remain;
    }

}
