package com.cbs.middleware.service.dto;

import java.util.Date;

public class InterestSubventionDTO {

    private String financialYear;


    //@Column(name = "pacs_number")
    private String pacsNumber;

    private String talukaCode;

    //  @Column(name = "from_date")
    private Date fromDate;

    //  @Column(name = "to_date")
    private Date toDate;

    /// @Column(name = "from_amount")
    private String fromAmount;

    //@Column(name = "to_amount")
    private String toAmount;

    // @Column(name = "from_interest")
    private Double fromInterest;

    //  @Column(name = "to_interest")
    private Double toInterest;

    //  @Column(name = "from_bank_interest")
    private Double fromBankInterest;

    // @Column(name = "to_bank_interest")
    private Double toBankInterest;

    private Double interestAbove3Lakh;

    /// @Column(name = "last_credit_date")
    private String lastCreditDate;

    // @Column(name = "due_date")
    private String dueDate;

    // @Column(name = "report_type")
    private Integer reportType;

    ///  @Column(name = "report_condition")
    private Integer reportCondition;



    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getPacsNumber() {
        return pacsNumber;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getTalukaCode() {
        return talukaCode;
    }

    public void setTalukaCode(String talukaCode) {
        this.talukaCode = talukaCode;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }






    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getReportCondition() {
        return reportCondition;
    }

    public void setReportCondition(Integer reportCondition) {
        this.reportCondition = reportCondition;
    }

    public Double getFromInterest() {
        return fromInterest;
    }

    public void setFromInterest(Double fromInterest) {
        this.fromInterest = fromInterest;
    }

    public Double getToInterest() {
        return toInterest;
    }

    public void setToInterest(Double toInterest) {
        this.toInterest = toInterest;
    }

    public Double getFromBankInterest() {
        return fromBankInterest;
    }

    public void setFromBankInterest(Double fromBankInterest) {
        this.fromBankInterest = fromBankInterest;
    }

    public Double getToBankInterest() {
        return toBankInterest;
    }

    public void setToBankInterest(Double toBankInterest) {
        this.toBankInterest = toBankInterest;
    }

    public Double getInterestAbove3Lakh() {
        return interestAbove3Lakh;
    }

    public void setInterestAbove3Lakh(Double interestAbove3Lakh) {
        this.interestAbove3Lakh = interestAbove3Lakh;
    }

    public String getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(String fromAmount) {
        this.fromAmount = fromAmount;
    }

    public String getToAmount() {
        return toAmount;
    }

    public void setToAmount(String toAmount) {
        this.toAmount = toAmount;
    }

    public String getLastCreditDate() {
        return lastCreditDate;
    }

    public void setLastCreditDate(String lastCreditDate) {
        this.lastCreditDate = lastCreditDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "InterestSubventionDTO{" +
            "financialYear='" + financialYear + '\'' +
            ", pacsNumber='" + pacsNumber + '\'' +
            ", talukaCode='" + talukaCode + '\'' +
            ", fromDate=" + fromDate +
            ", toDate=" + toDate +
            ", fromAmount='" + fromAmount + '\'' +
            ", toAmount='" + toAmount + '\'' +
            ", fromInterest=" + fromInterest +
            ", toInterest=" + toInterest +
            ", fromBankInterest=" + fromBankInterest +
            ", toBankInterest=" + toBankInterest +
            ", interestAbove3Lakh=" + interestAbove3Lakh +
            ", lastCreditDate='" + lastCreditDate + '\'' +
            ", dueDate='" + dueDate + '\'' +
            ", reportType=" + reportType +
            ", reportCondition=" + reportCondition +
            '}';
    }
}
