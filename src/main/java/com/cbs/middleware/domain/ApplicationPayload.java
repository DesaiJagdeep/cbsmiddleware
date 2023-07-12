package com.cbs.middleware.domain;

import java.util.List;

public class ApplicationPayload {

    private String uniqueId;
    private Long recordStatus;
    private BasicDetails basicDetails;
    private ResidentialDetails residentialDetails;
    private AccountDetails accountDetails;
    private LoanDetails loanDetails;
    private List<Activities> activities;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Long recordStatus) {
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
