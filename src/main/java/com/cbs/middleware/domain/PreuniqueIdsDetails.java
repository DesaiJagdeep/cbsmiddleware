package com.cbs.middleware.domain;

public class PreuniqueIdsDetails {

    /*{
        "preUniqueID": "1212231569653097512",
        "accountNumber": "025001700007453",
        "farmerID": "23033363445",
        "beneficiaryName": "ULHAS DATTATRAY THIKEKAR"
    }*/

    public String getPreUniqueID() {
        return preUniqueID;
    }

    public void setPreUniqueID(String preUniqueID) {
        this.preUniqueID = preUniqueID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFarmerID() {
        return farmerID;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    private String preUniqueID;
    private String accountNumber;
    private String farmerID;
    private String beneficiaryName;
}
