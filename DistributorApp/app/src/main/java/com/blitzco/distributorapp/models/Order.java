package com.blitzco.distributorapp.models;

public class Order {
    private String orderID;
    private String shopName;
    private String brandName;
    private String modelName;
    private String costPrice;
    private String unitPrice;
    private String quantity;
    private String totalPrice;
    private String profit;
    private String dDate;
    private String yyyyMM;

    public Order() {
    }

    public Order(String orderID, String shopName, String brandName, String modelName, String costPrice, String unitPrice, String quantity, String totalPrice, String profit, String dDate, String yyyyMM) {
        this.orderID = orderID;
        this.shopName = shopName;
        this.brandName = brandName;
        this.modelName = modelName;
        this.costPrice = costPrice;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.profit = profit;
        this.dDate = dDate;
        this.yyyyMM = yyyyMM;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getYyyyMM() {
        return yyyyMM;
    }

    public void setYyyyMM(String yyyyMM) {
        this.yyyyMM = yyyyMM;
    }
}
