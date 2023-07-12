package com.cbs.middleware.domain;

import java.io.Serializable;

public class BasicDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /*
     * {
     * "beneficiaryName": "TestName",
     * "aadhaarNumber": "XXXXXXXXXXXX",
     * "beneficiaryPassbookName": "TestName",
     * "mobile": "XXXXXXXXXX",
     * "dob":
     * "1990-08-02",
     * "gender": 2,
     * "socialCategory": 2,
     * "farmerCategory": 1,
     * "farmerType": 2,
     * "primaryOccupation": 1,
     * "relativeType": 2,
     * "relativeName":
     * "Test relative"
     * }
     */

    private String beneficiaryName;
    private String aadhaarNumber;
    private String beneficiaryPassbookName;
    private String mobile;
    private String dob;
    private int gender;
    private int socialCategory;
    private int farmerCategory;
    private int farmerType;
    private int primaryOccupation;
    private int relativeType;
    private String relativeName;

    //GETTERS AND SETTERS

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getBeneficiaryPassbookName() {
        return beneficiaryPassbookName;
    }

    public void setBeneficiaryPassbookName(String beneficiaryPassbookName) {
        this.beneficiaryPassbookName = beneficiaryPassbookName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getSocialCategory() {
        return socialCategory;
    }

    public void setSocialCategory(int socialCategory) {
        this.socialCategory = socialCategory;
    }

    public int getFarmerCategory() {
        return farmerCategory;
    }

    public void setFarmerCategory(int farmerCategory) {
        this.farmerCategory = farmerCategory;
    }

    public int getFarmerType() {
        return farmerType;
    }

    public void setFarmerType(int farmerType) {
        this.farmerType = farmerType;
    }

    public int getPrimaryOccupation() {
        return primaryOccupation;
    }

    public void setPrimaryOccupation(int primaryOccupation) {
        this.primaryOccupation = primaryOccupation;
    }

    public int getRelativeType() {
        return relativeType;
    }

    public void setRelativeType(int relativeType) {
        this.relativeType = relativeType;
    }

    public String getRelativeName() {
        return relativeName;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }

    @Override
    public String toString() {
        return (
            "BasicDetails [beneficiaryName=" +
            beneficiaryName +
            ", aadhaarNumber=" +
            aadhaarNumber +
            ", beneficiaryPassbookName=" +
            beneficiaryPassbookName +
            ", mobile=" +
            mobile +
            ", dob=" +
            dob +
            ", gender=" +
            gender +
            ", socialCategory=" +
            socialCategory +
            ", farmerCategory=" +
            farmerCategory +
            ", farmerType=" +
            farmerType +
            ", primaryOccupation=" +
            primaryOccupation +
            ", relativeType=" +
            relativeType +
            ", relativeName=" +
            relativeName +
            "]"
        );
    }
}
