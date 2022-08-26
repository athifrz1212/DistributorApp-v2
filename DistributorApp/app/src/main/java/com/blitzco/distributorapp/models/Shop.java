package com.blitzco.distributorapp.models;

public class Shop {

    private String id;
    private String shop;
    private String area;
    private String address;
    private String contact;

    public Shop() {
    }

    public Shop(String id, String shop, String area, String address, String contact) {
        this.id = id;
        this.shop = shop;
        this.area = area;
        this.address = address;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}