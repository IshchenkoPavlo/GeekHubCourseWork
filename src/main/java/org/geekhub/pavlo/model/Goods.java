package org.geekhub.pavlo.model;

public class Goods {
    private int id;
    private boolean isGroup;
    private int parentId;
    private String name;
    private float price;
    private float pricePurchase;

    public Goods() {
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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
}
