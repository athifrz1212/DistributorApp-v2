package com.blitzco.distributorapp.models;

public class Repairs {
    private String repairID;
    private String shopName;
    private String brandName;
    private String issueBrandName;
    private String modelName;
    private String issueModelName;
    private String issue;
    private String reType;
    private String reDate;
    private String yyyyMM;
    private String noOfItems;

    public Repairs() {
    }

    public Repairs(String repairID, String shopName, String brandName, String issueBrandName, String modelName, String issueModelName, String issue, String reType, String reDate, String yyyyMM, String noOfItems) {
        this.repairID = repairID;
        this.shopName = shopName;
        this.brandName = brandName;
        this.issueBrandName = issueBrandName;
        this.modelName = modelName;
        this.issueModelName = issueModelName;
        this.issue = issue;
        this.reType = reType;
        this.reDate = reDate;
        this.yyyyMM = yyyyMM;
        this.noOfItems = noOfItems;
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
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

    public String getIssueBrandName() {
        return issueBrandName;
    }

    public void setIssueBrandName(String issueBrandName) {
        this.issueBrandName = issueBrandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getIssueModelName() {
        return issueModelName;
    }

    public void setIssueModelName(String issueModelName) {
        this.issueModelName = issueModelName;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getReType() {
        return reType;
    }

    public void setReType(String reType) {
        this.reType = reType;
    }

    public String getReDate() {
        return reDate;
    }

    public void setReDate(String reDate) {
        this.reDate = reDate;
    }

    public String getYyyyMM() {
        return yyyyMM;
    }

    public void setYyyyMM(String yyyyMM) {
        this.yyyyMM = yyyyMM;
    }

    public String getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(String noOfItems) {
        this.noOfItems = noOfItems;
    }
}
