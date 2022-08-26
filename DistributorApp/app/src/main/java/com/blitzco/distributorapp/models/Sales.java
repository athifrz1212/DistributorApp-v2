package com.blitzco.distributorapp.models;

public class Sales {
    private String shopName;
    private String brandName;
    private String modelName;
    private int monthlyTotal;
    private int noOfItems;
    private int profitTotal;
    private int balance;
    private String yyyyMM;
    private int month;

    public Sales() {
    }

    public Sales(String shopName, String brandName, String modelName, int monthlyTotal, int noOfItems, int profitTotal, int balance, String yyyyMM, int month) {
        this.shopName = shopName;
        this.brandName = brandName;
        this.modelName = modelName;
        this.monthlyTotal = monthlyTotal;
        this.noOfItems = noOfItems;
        this.profitTotal = profitTotal;
        this.balance = balance;
        this.yyyyMM = yyyyMM;
        this.month = month;
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

    public int getMonthlyTotal() {
        return monthlyTotal;
    }

    public void setMonthlyTotal(int monthlyTotal) {
        this.monthlyTotal = monthlyTotal;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public int getProfitTotal() {
        return profitTotal;
    }

    public void setProfitTotal(int profitTotal) {
        this.profitTotal = profitTotal;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getYyyyMM() {
        return yyyyMM;
    }

    public void setYyyyMM(String yyyyMM) {
        this.yyyyMM = yyyyMM;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
