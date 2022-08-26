package com.blitzco.distributorapp.models;

public class Profits {
    private String year;
    private String month;
    private String totalSales;
    private String profit;

    public Profits() {
    }
    public Profits(String year, String month, String totalSales, String profit) {
        this.year = year;
        this.month = month;
        this.totalSales = totalSales;
        this.profit = profit;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
