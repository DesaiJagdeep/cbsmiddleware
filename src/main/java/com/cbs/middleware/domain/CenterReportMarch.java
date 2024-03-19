package com.cbs.middleware.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "center_report_march")
public class CenterReportMarch extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_no")
    private Integer serialNo;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "iss_file_parser_id")
    private Long issFileParserId;

    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "pacs_number")
    private String pacsNumber;

    @Column(name = "loan_account_number_kcc")
    private String loanAccountNumberKcc;

    @Column(name = "farmer_name")
    private String farmerName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "aadhar_number")
    private String aadharNumber;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "farmer_type")
    private String farmerType;

    @Column(name = "social_category")
    private String socialCategory;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "loan_sanction_date")
    private LocalDate loanSanctionDate;

    @Column(name = "loan_sanction_amount")
    private Long loanSanctionAmount;

    @Column(name = "disbursement_date")
    private LocalDate disbursementDate;

    @Column(name = "disburse_amount")
    private Long disburseAmount;

    @Column(name = "maturity_loan_date")
    private LocalDate maturityLoanDate;

    @Column(name = "bank_date")
    private LocalDate bankDate;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "recovery_amount")
    private Long recoveryAmount;

    @Column(name = "recovery_interest")
    private String recoveryInterest;

    @Column(name = "recovery_date")
    private LocalDate recoveryDate;

    @Column(name = "balance_amount")
    private Long balanceAmount;

    @Column(name = "prev_days")
    private Long prevDays;

    @Column(name = "pres_days")
    private Long presDays;

    @Column(name = "actual_days")
    private Long actualDays;

    @Column(name = "n_prod")
    private Integer nProd;

    @Column(name = "product_amount")
    private Long productAmount;

    @Column(name = "product_bank")
    private Long productBank;

    @Column(name = "product_abh_3_lakh")
    private Long productAbh3Lakh;

    @Column(name = "interest_first_15")
    private Double interestFirst15;

    @Column(name = "interest_first_25")
    private Double interestFirst25;

    @Column(name = "interest_second_15")
    private Double interestSecond15;

    @Column(name = "interest_second_25")
    private Double interestSecond25;

    @Column(name = "interest_state_first_3")
    private Double interestStateFirst3;

    @Column(name = "interest_state_second_3")
    private Double interestStateSecond3;

    @Column(name = "interest_first_abh_3")
    private Double interestFirstAbh3;

    @Column(name = "interest_second_abh_3")
    private Double interestSecondAbh3;

    @Column(name = "interest_above_3_lakh")
    private Double interestAbove3Lakh;

    @Column(name = "panjabrao_int_3")
    private Double panjabraoInt3;

    @Column(name = "is_recover")
    private Integer isRecover;

    @Column(name = "abh_3_lakh_amt")
    private Long abh3LakhAmt;

    @Column(name = "upto_50000")
    private Integer upto50000;

    // jhipster-needle-entity-add-field - JHipster will add fields here


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Long getIssFileParserId() {
        return issFileParserId;
    }

    public void setIssFileParserId(Long issFileParserId) {
        this.issFileParserId = issFileParserId;
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

    public String getLoanAccountNumberKcc() {
        return loanAccountNumberKcc;
    }

    public void setLoanAccountNumberKcc(String loanAccountNumberKcc) {
        this.loanAccountNumberKcc = loanAccountNumberKcc;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFarmerType() {
        return farmerType;
    }

    public void setFarmerType(String farmerType) {
        this.farmerType = farmerType;
    }

    public String getSocialCategory() {
        return socialCategory;
    }

    public void setSocialCategory(String socialCategory) {
        this.socialCategory = socialCategory;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getLoanSanctionDate() {
        return loanSanctionDate;
    }

    public void setLoanSanctionDate(LocalDate loanSanctionDate) {
        this.loanSanctionDate = loanSanctionDate;
    }

    public Long getLoanSanctionAmount() {
        return loanSanctionAmount;
    }

    public void setLoanSanctionAmount(Long loanSanctionAmount) {
        this.loanSanctionAmount = loanSanctionAmount;
    }

    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(LocalDate disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Long getDisburseAmount() {
        return disburseAmount;
    }

    public void setDisburseAmount(Long disburseAmount) {
        this.disburseAmount = disburseAmount;
    }

    public LocalDate getMaturityLoanDate() {
        return maturityLoanDate;
    }

    public void setMaturityLoanDate(LocalDate maturityLoanDate) {
        this.maturityLoanDate = maturityLoanDate;
    }

    public LocalDate getBankDate() {
        return bankDate;
    }

    public void setBankDate(LocalDate bankDate) {
        this.bankDate = bankDate;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Long getRecoveryAmount() {
        return recoveryAmount;
    }

    public void setRecoveryAmount(Long recoveryAmount) {
        this.recoveryAmount = recoveryAmount;
    }

    public String getRecoveryInterest() {
        return recoveryInterest;
    }

    public void setRecoveryInterest(String recoveryInterest) {
        this.recoveryInterest = recoveryInterest;
    }

    public LocalDate getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(LocalDate recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public Long getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Long balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Long getPrevDays() {
        return prevDays;
    }

    public void setPrevDays(Long prevDays) {
        this.prevDays = prevDays;
    }

    public Long getPresDays() {
        return presDays;
    }

    public void setPresDays(Long presDays) {
        this.presDays = presDays;
    }

    public Long getActualDays() {
        return actualDays;
    }

    public void setActualDays(Long actualDays) {
        this.actualDays = actualDays;
    }

    public Integer getnProd() {
        return nProd;
    }

    public void setnProd(Integer nProd) {
        this.nProd = nProd;
    }

    public Long getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Long productAmount) {
        this.productAmount = productAmount;
    }

    public Long getProductBank() {
        return productBank;
    }

    public void setProductBank(Long productBank) {
        this.productBank = productBank;
    }

    public Long getProductAbh3Lakh() {
        return productAbh3Lakh;
    }

    public void setProductAbh3Lakh(Long productAbh3Lakh) {
        this.productAbh3Lakh = productAbh3Lakh;
    }

    public Double getInterestFirst15() {
        return interestFirst15;
    }

    public void setInterestFirst15(Double interestFirst15) {
        this.interestFirst15 = interestFirst15;
    }

    public Double getInterestFirst25() {
        return interestFirst25;
    }

    public void setInterestFirst25(Double interestFirst25) {
        this.interestFirst25 = interestFirst25;
    }

    public Double getInterestSecond15() {
        return interestSecond15;
    }

    public void setInterestSecond15(Double interestSecond15) {
        this.interestSecond15 = interestSecond15;
    }

    public Double getInterestSecond25() {
        return interestSecond25;
    }

    public void setInterestSecond25(Double interestSecond25) {
        this.interestSecond25 = interestSecond25;
    }

    public Double getInterestStateFirst3() {
        return interestStateFirst3;
    }

    public void setInterestStateFirst3(Double interestStateFirst3) {
        this.interestStateFirst3 = interestStateFirst3;
    }

    public Double getInterestStateSecond3() {
        return interestStateSecond3;
    }

    public void setInterestStateSecond3(Double interestStateSecond3) {
        this.interestStateSecond3 = interestStateSecond3;
    }

    public Double getInterestFirstAbh3() {
        return interestFirstAbh3;
    }

    public void setInterestFirstAbh3(Double interestFirstAbh3) {
        this.interestFirstAbh3 = interestFirstAbh3;
    }

    public Double getInterestSecondAbh3() {
        return interestSecondAbh3;
    }

    public void setInterestSecondAbh3(Double interestSecondAbh3) {
        this.interestSecondAbh3 = interestSecondAbh3;
    }

    public Double getInterestAbove3Lakh() {
        return interestAbove3Lakh;
    }

    public void setInterestAbove3Lakh(Double interestAbove3Lakh) {
        this.interestAbove3Lakh = interestAbove3Lakh;
    }

    public Double getPanjabraoInt3() {
        return panjabraoInt3;
    }

    public void setPanjabraoInt3(Double panjabraoInt3) {
        this.panjabraoInt3 = panjabraoInt3;
    }

    public Integer getIsRecover() {
        return isRecover;
    }

    public void setIsRecover(Integer isRecover) {
        this.isRecover = isRecover;
    }

    public Long getAbh3LakhAmt() {
        return abh3LakhAmt;
    }

    public void setAbh3LakhAmt(Long abh3LakhAmt) {
        this.abh3LakhAmt = abh3LakhAmt;
    }

    public Integer getUpto50000() {
        return upto50000;
    }

    public void setUpto50000(Integer upto50000) {
        this.upto50000 = upto50000;
    }

    @Override
    public String toString() {
        return "CenterReportMarch{" +
            "id=" + id +
            ", serialNo=" + serialNo +
            ", financialYear='" + financialYear + '\'' +
            ", issFileParserId=" + issFileParserId +
            ", branchCode='" + branchCode + '\'' +
            ", pacsNumber='" + pacsNumber + '\'' +
            ", loanAccountNumberKcc='" + loanAccountNumberKcc + '\'' +
            ", farmerName='" + farmerName + '\'' +
            ", gender='" + gender + '\'' +
            ", aadharNumber='" + aadharNumber + '\'' +
            ", mobileNo='" + mobileNo + '\'' +
            ", farmerType='" + farmerType + '\'' +
            ", socialCategory='" + socialCategory + '\'' +
            ", accountNumber='" + accountNumber + '\'' +
            ", loanSanctionDate=" + loanSanctionDate +
            ", loanSanctionAmount=" + loanSanctionAmount +
            ", disbursementDate=" + disbursementDate +
            ", disburseAmount=" + disburseAmount +
            ", maturityLoanDate=" + maturityLoanDate +
            ", bankDate=" + bankDate +
            ", cropName='" + cropName + '\'' +
            ", recoveryAmount=" + recoveryAmount +
            ", recoveryInterest='" + recoveryInterest + '\'' +
            ", recoveryDate=" + recoveryDate +
            ", balanceAmount=" + balanceAmount +
            ", prevDays=" + prevDays +
            ", presDays=" + presDays +
            ", actualDays=" + actualDays +
            ", nProd=" + nProd +
            ", productAmount=" + productAmount +
            ", productBank=" + productBank +
            ", productAbh3Lakh=" + productAbh3Lakh +
            ", interestFirst15=" + interestFirst15 +
            ", interestFirst25=" + interestFirst25 +
            ", interestSecond15=" + interestSecond15 +
            ", interestSecond25=" + interestSecond25 +
            ", interestStateFirst3=" + interestStateFirst3 +
            ", interestStateSecond3=" + interestStateSecond3 +
            ", interestFirstAbh3=" + interestFirstAbh3 +
            ", interestSecondAbh3=" + interestSecondAbh3 +
            ", interestAbove3Lakh=" + interestAbove3Lakh +
            ", panjabraoInt3=" + panjabraoInt3 +
            ", isRecover=" + isRecover +
            ", abh3LakhAmt=" + abh3LakhAmt +
            ", upto50000=" + upto50000 +
            '}';
    }
}
