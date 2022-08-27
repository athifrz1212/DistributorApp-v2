package com.blitzco.distributorapp.models;

public class Product {
    private String id;
    private String brandName;
    private String modelName;
    private long unitPrice;
    private long qty;

    public Product() {
    }

    public Product(String id, String brandName, String modelName, long unitPrice, long qty) {
        this.id = id;
        this.brandName = brandName;
        this.modelName = modelName;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }
}
