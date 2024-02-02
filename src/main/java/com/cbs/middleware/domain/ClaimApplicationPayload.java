package com.cbs.middleware.domain;

public class ClaimApplicationPayload {

    private static final long serialVersionUID = 1L;
    private String uniqueId;
    private String loanApplicationNumber;

    private String claimType;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getLoanApplicationNumber() {
        return loanApplicationNumber;
    }

    public void setLoanApplicationNumber(String loanApplicationNumber) {
        this.loanApplicationNumber = loanApplicationNumber;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public Integer getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(Integer submissionType) {
        this.submissionType = submissionType;
    }

    public String getFirstLoanDisbursalDate() {
        return firstLoanDisbursalDate;
    }

    public void setFirstLoanDisbursalDate(String firstLoanDisbursalDate) {
        this.firstLoanDisbursalDate = firstLoanDisbursalDate;
    }

    public String getLoanRepaymentDate() {
        return loanRepaymentDate;
    }

    public void setLoanRepaymentDate(String loanRepaymentDate) {
        this.loanRepaymentDate = loanRepaymentDate;
    }

    public Long getMaxWithdrawalAmount() {
        return maxWithdrawalAmount;
    }

    public void setMaxWithdrawalAmount(Long maxWithdrawalAmount) {
        this.maxWithdrawalAmount = maxWithdrawalAmount;
    }

    public Long getApplicableISAmount() {
        return applicableISAmount;
    }

    public void setApplicableISAmount(Long applicableISAmount) {
        this.applicableISAmount = applicableISAmount;
    }

    private Integer submissionType;

    private String firstLoanDisbursalDate;

    private String loanRepaymentDate;
    private Long maxWithdrawalAmount;
    private Long applicableISAmount;


    @Override
    public String toString() {
        return (
            "ClaimApplicationPayload [uniqueId=" +
                uniqueId +
                ",loanApplicationNumber=" +
                loanApplicationNumber +
                ",claimType=" +
                claimType + ",submissionType=" +
                submissionType +
                ", firstLoanDisbursalDate=" +
                firstLoanDisbursalDate +
                ", loanRepaymentDate=" +
                loanRepaymentDate +
                ", maxWithdrawalAmount=" +
                maxWithdrawalAmount +
                ", applicableISAmount=" +
                applicableISAmount +
                "]"
        );
    }
}
