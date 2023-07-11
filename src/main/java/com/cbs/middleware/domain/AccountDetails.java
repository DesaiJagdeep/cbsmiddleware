package com.cbs.middleware.domain;

import java.util.List;

public class AccountDetails {

    /*
     * {
     * "accountNumber": "12354689092",
     * "ifsc": "PUNB0173900",
     * "branchCode": "0402",
     * "accountHolder": 2,
     * "jointAccountHolders":
     *  [
     *   { "name": "Test name",
     *    "aadhaarNumber": "XXXXXXXXXXXX"
     *   }
     *  ]
     * }
     */

    private String accountNumber;
    private String ifsc;
    private String branchCode;
    private int accountHolder;
    private List<JointAccountHolders> jointAccountHolders;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public int getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(int accountHolder) {
        this.accountHolder = accountHolder;
    }

    public List<JointAccountHolders> getJointAccountHolders() {
        return jointAccountHolders;
    }

    public void setJointAccountHolders(List<JointAccountHolders> jointAccountHolders) {
        this.jointAccountHolders = jointAccountHolders;
    }
}
