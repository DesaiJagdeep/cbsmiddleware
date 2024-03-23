package com.cbs.middleware.service.dto;

public class VerifyPendingDTO {
    private String pacsName;
    private String pacsNumber;
    private String  branchName;
    private String financialYear;

    public VerifyPendingDTO() {
    }

    public VerifyPendingDTO(String pacsName, String pacsNumber, String branchName, String financialYear) {
        this.pacsName = pacsName;
        this.pacsNumber = pacsNumber;
        this.branchName = branchName;
        this.financialYear = financialYear;
    }

    public String getPacsName() {
        return pacsName;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public String getPacsNumber() {
        return pacsNumber;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }
}
