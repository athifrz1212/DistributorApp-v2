package com.blitzco.distributorapp.models;

public class Brand {
    private String brandID;
    private String brandName;
    private String sellerName;
    private String address;
    private String contactNumber;

    public Brand() {
    }

    public Brand(String brandID, String brandName, String sellerName, String address, String contactNumber) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.sellerName = sellerName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

}