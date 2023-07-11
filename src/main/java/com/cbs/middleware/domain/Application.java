package com.cbs.middleware.domain;

import java.util.List;

public class Application {

    /*
     * "uniqueId": "2908222301220911121", "recordStatus": 1,
     */

    private String uniqueId;
    private Integer recordStatus;
    private BasicDetails basicDetails;
    private ResidentialDetails residentialDetails;
    private AccountDetails accountDetails;
    private LoanDetails loanDetails;
    private List<Activities> activities;

    // GETTERS AND SETTERS

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public BasicDetails getBasicDetails() {
        return basicDetails;
    }

    public void setBasicDetails(BasicDetails basicDetails) {
        this.basicDetails = basicDetails;
    }

    public ResidentialDetails getResidentialDetails() {
        return residentialDetails;
    }

    public void setResidentialDetails(ResidentialDetails residentialDetails) {
        this.residentialDetails = residentialDetails;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }

    public List<Activities> getActivities() {
        return activities;
    }

    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }
}
