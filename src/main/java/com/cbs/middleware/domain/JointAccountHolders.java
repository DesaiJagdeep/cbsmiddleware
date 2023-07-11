package com.cbs.middleware.domain;

public class JointAccountHolders {

    /*
     * {
     * "name": "Test name",
     * "aadhaarNumber": "XXXXXXXXXXXX"
     * }
     */

    private String name;
    private String aadhaarNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }
}
