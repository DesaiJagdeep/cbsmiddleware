package com.cbs.middleware.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "summary_report")
public class SummaryReport extends AbstractAuditingEntity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "financial_year")
    private String financialYear;
    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "pacs_number")
    private String pacsNumber;

    @Column(name = "taluka_code")
    private String talukaCode;
    @Column(name = "type_of_report")
    private Integer typeOfReport;

    @Column(name = "upto_50000")
    private Integer upto50000;

    @Column(name = "total_loan_amount")
    private Long totalLoanAmount;

    @Column(name = "no_of_loan_accounts")
    private Integer NoOfLoanAccounts;

    @Column(name = "total_recovery_amount")
    private Long totalRecoveryAmount;

    @Column(name = "no_of_recovery_accounts")
    private Integer NoOfRecoveryAccounts;

    @Column(name = "general_amount")
    private Long generalAmount;

    @Column(name = "no_of_general_accounts")
    private Integer NoOfGeneralAccounts;

    @Column(name = "sc_amount")
    private Long scAmount;

    @Column(name = "no_of_sc_accounts")
    private Integer NoOfSCAccounts;

    @Column(name = "st_amount")
    private Long stAmount;

    @Column(name = "no_of_st_accounts")
    private Integer NoOfSTAccounts;

    @Column(name = "small_medium_amount")
    private Long smallMediumAmount;

    @Column(name = "no_of_small_medium_accounts")
    private Integer NoOfSmallMediumAccounts;

    @Column(name = "women_amount")
    private Long womenAmount;

    @Column(name = "no_of_women_accounts")
    private Integer NoOfWomenAccounts;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    public Integer getTypeOfReport() {
        return typeOfReport;
    }

    public void setTypeOfReport(Integer typeOfReport) {
        this.typeOfReport = typeOfReport;
    }

    public Integer getUpto50000() {
        return upto50000;
    }

    public void setUpto50000(Integer upto50000) {
        this.upto50000 = upto50000;
    }

    public Long getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(Long totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public Integer getNoOfLoanAccounts() {
        return NoOfLoanAccounts;
    }

    public void setNoOfLoanAccounts(Integer noOfLoanAccounts) {
        NoOfLoanAccounts = noOfLoanAccounts;
    }

    public Long getTotalRecoveryAmount() {
        return totalRecoveryAmount;
    }

    public void setTotalRecoveryAmount(Long totalRecoveryAmount) {
        this.totalRecoveryAmount = totalRecoveryAmount;
    }

    public Integer getNoOfRecoveryAccounts() {
        return NoOfRecoveryAccounts;
    }

    public void setNoOfRecoveryAccounts(Integer noOfRecoveryAccounts) {
        NoOfRecoveryAccounts = noOfRecoveryAccounts;
    }

    public Long getGeneralAmount() {
        return generalAmount;
    }

    public void setGeneralAmount(Long generalAmount) {
        this.generalAmount = generalAmount;
    }

    public Integer getNoOfGeneralAccounts() {
        return NoOfGeneralAccounts;
    }

    public void setNoOfGeneralAccounts(Integer noOfGeneralAccounts) {
        NoOfGeneralAccounts = noOfGeneralAccounts;
    }

    public Long getScAmount() {
        return scAmount;
    }

    public void setScAmount(Long scAmount) {
        this.scAmount = scAmount;
    }

    public Integer getNoOfSCAccounts() {
        return NoOfSCAccounts;
    }

    public void setNoOfSCAccounts(Integer noOfSCAccounts) {
        NoOfSCAccounts = noOfSCAccounts;
    }

    public Long getStAmount() {
        return stAmount;
    }

    public void setStAmount(Long stAmount) {
        this.stAmount = stAmount;
    }

    public Integer getNoOfSTAccounts() {
        return NoOfSTAccounts;
    }

    public void setNoOfSTAccounts(Integer noOfSTAccounts) {
        NoOfSTAccounts = noOfSTAccounts;
    }

    public Long getSmallMediumAmount() {
        return smallMediumAmount;
    }

    public void setSmallMediumAmount(Long smallMediumAmount) {
        this.smallMediumAmount = smallMediumAmount;
    }

    public Integer getNoOfSmallMediumAccounts() {
        return NoOfSmallMediumAccounts;
    }

    public void setNoOfSmallMediumAccounts(Integer noOfSmallMediumAccounts) {
        NoOfSmallMediumAccounts = noOfSmallMediumAccounts;
    }

    public Long getWomenAmount() {
        return womenAmount;
    }

    public void setWomenAmount(Long womenAmount) {
        this.womenAmount = womenAmount;
    }

    public Integer getNoOfWomenAccounts() {
        return NoOfWomenAccounts;
    }

    public void setNoOfWomenAccounts(Integer noOfWomenAccounts) {
        NoOfWomenAccounts = noOfWomenAccounts;
    }

    @Override
    public String toString() {
        return "SummaryReport{" +
            "id=" + id +
            ", financialYear='" + financialYear + '\'' +
            ", branchCode='" + branchCode + '\'' +
            ", pacsNumber='" + pacsNumber + '\'' +
            ", talukaCode='" + talukaCode + '\'' +
            ", typeOfReport=" + typeOfReport +
            ", upto50000=" + upto50000 +
            ", totalLoanAmount=" + totalLoanAmount +
            ", NoOfLoanAccounts=" + NoOfLoanAccounts +
            ", totalRecoveryAmount=" + totalRecoveryAmount +
            ", NoOfRecoveryAccounts=" + NoOfRecoveryAccounts +
            ", generalAmount=" + generalAmount +
            ", NoOfGeneralAccounts=" + NoOfGeneralAccounts +
            ", scAmount=" + scAmount +
            ", NoOfSCAccounts=" + NoOfSCAccounts +
            ", stAmount=" + stAmount +
            ", NoOfSTAccounts=" + NoOfSTAccounts +
            ", smallMediumAmount=" + smallMediumAmount +
            ", NoOfSmallMediumAccounts=" + NoOfSmallMediumAccounts +
            ", womenAmount=" + womenAmount +
            ", NoOfWomenAccounts=" + NoOfWomenAccounts +
            '}';
    }
}
