package com.blitzco.distributorapp.models;

public class Order {
    private String orderID;
    private String agentId;
    private String shopName;
    private String brandName;
    private String modelName;
    private long costPrice;
    private long unitPrice;
    private long quantity;
    private long totalPrice;
    private long profit;
    private String dDate;
    private String yyyyMM;

    public Order() {
    }

    public Order(String orderID, String agentId, String shopName, String brandName, String modelName, long costPrice, long unitPrice, long quantity, long totalPrice, long profit, String dDate, String yyyyMM) {
        this.orderID = orderID;
        this.agentId = agentId;
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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
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

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
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
