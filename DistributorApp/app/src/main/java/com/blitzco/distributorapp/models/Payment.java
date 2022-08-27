package com.blitzco.distributorapp.models;

public class Payment {
    private String paymentId;
    private String shopName;
    private long balance;
    private String lastPaydate;

    public Payment() {
    }

    public Payment(String paymentId, String shopName, long balance, String lastPaydate) {
        this.paymentId = paymentId;
        this.shopName = shopName;
        this.balance = balance;
        this.lastPaydate = lastPaydate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getLastPaydate() {
        return lastPaydate;
    }

    public void setLastPaydate(String lastPaydate) {
        this.lastPaydate = lastPaydate;
    }
}
