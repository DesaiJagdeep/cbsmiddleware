package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A IsCalculateTemp.
 */
@Entity
@Table(name = "is_calculate_temp")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IsCalculateTemp implements Serializable {

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

    @Column(name = "loan_account_number_kcc")
    private String loanAccountNumberKcc;

    @Column(name = "farmer_name")
    private String farmerName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "cast")
    private String cast;

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
    private String loanSanctionDate;

    @Column(name = "loan_sanction_amount")
    private String loanSanctionAmount;

    @Column(name = "disbursement_date")
    private String disbursementDate;

    @Column(name = "disburse_amount")
    private String disburseAmount;

    @Column(name = "maturity_loan_date")
    private String maturityLoanDate;

    @Column(name = "bank_date")
    private String bankDate;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "recovery_amount")
    private String recoveryAmount;

    @Column(name = "recovery_interest")
    private String recoveryInterest;

    @Column(name = "recovery_date")
    private String recoveryDate;

    @Column(name = "balance_amount")
    private String balanceAmount;

    @Column(name = "prev_days")
    private Integer prevDays;

    @Column(name = "pres_days")
    private Integer presDays;

    @Column(name = "actual_days")
    private Integer actualDays;

    @Column(name = "n_prod")
    private Integer nProd;

    @Column(name = "product_amount")
    private String productAmount;

    @Column(name = "product_bank")
    private String productBank;

    @Column(name = "product_abh_3_lakh")
    private String productAbh3Lakh;

    @Column(name = "interest_first_15")
    private Long interestFirst15;

    @Column(name = "interest_first_25")
    private Long interestFirst25;

    @Column(name = "interest_second_15")
    private Long interestSecond15;

    @Column(name = "interest_second_25")
    private Long interestSecond25;

    @Column(name = "interest_state_first_3")
    private Long interestStateFirst3;

    @Column(name = "interest_state_second_3")
    private Long interestStateSecond3;

    @Column(name = "interest_first_abh_3")
    private Long interestFirstAbh3;

    @Column(name = "interest_second_abh_3")
    private Long interestSecondAbh3;

    @Column(name = "panjabrao_int_3")
    private Long panjabraoInt3;

    @Column(name = "is_recover")
    private Integer isRecover;

    @Column(name = "abh_3_lakh")
    private Long abh3Lakh;

    @Column(name = "amt_50000")
    private Integer amt50000;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IsCalculateTemp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerialNo() {
        return this.serialNo;
    }

    public IsCalculateTemp serialNo(Integer serialNo) {
        this.setSerialNo(serialNo);
        return this;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public IsCalculateTemp financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Long getIssFileParserId() {
        return this.issFileParserId;
    }

    public IsCalculateTemp issFileParserId(Long issFileParserId) {
        this.setIssFileParserId(issFileParserId);
        return this;
    }

    public void setIssFileParserId(Long issFileParserId) {
        this.issFileParserId = issFileParserId;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public IsCalculateTemp branchCode(String branchCode) {
        this.setBranchCode(branchCode);
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getLoanAccountNumberKcc() {
        return this.loanAccountNumberKcc;
    }

    public IsCalculateTemp loanAccountNumberKcc(String loanAccountNumberKcc) {
        this.setLoanAccountNumberKcc(loanAccountNumberKcc);
        return this;
    }

    public void setLoanAccountNumberKcc(String loanAccountNumberKcc) {
        this.loanAccountNumberKcc = loanAccountNumberKcc;
    }

    public String getFarmerName() {
        return this.farmerName;
    }

    public IsCalculateTemp farmerName(String farmerName) {
        this.setFarmerName(farmerName);
        return this;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getGender() {
        return this.gender;
    }

    public IsCalculateTemp gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCast() {
        return this.cast;
    }

    public IsCalculateTemp cast(String cast) {
        this.setCast(cast);
        return this;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getAadharNumber() {
        return this.aadharNumber;
    }

    public IsCalculateTemp aadharNumber(String aadharNumber) {
        this.setAadharNumber(aadharNumber);
        return this;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public IsCalculateTemp mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFarmerType() {
        return this.farmerType;
    }

    public IsCalculateTemp farmerType(String farmerType) {
        this.setFarmerType(farmerType);
        return this;
    }

    public void setFarmerType(String farmerType) {
        this.farmerType = farmerType;
    }

    public String getSocialCategory() {
        return this.socialCategory;
    }

    public IsCalculateTemp socialCategory(String socialCategory) {
        this.setSocialCategory(socialCategory);
        return this;
    }

    public void setSocialCategory(String socialCategory) {
        this.socialCategory = socialCategory;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public IsCalculateTemp accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getLoanSanctionDate() {
        return this.loanSanctionDate;
    }

    public IsCalculateTemp loanSanctionDate(String loanSanctionDate) {
        this.setLoanSanctionDate(loanSanctionDate);
        return this;
    }

    public void setLoanSanctionDate(String loanSanctionDate) {
        this.loanSanctionDate = loanSanctionDate;
    }

    public String getLoanSanctionAmount() {
        return this.loanSanctionAmount;
    }

    public IsCalculateTemp loanSanctionAmount(String loanSanctionAmount) {
        this.setLoanSanctionAmount(loanSanctionAmount);
        return this;
    }

    public void setLoanSanctionAmount(String loanSanctionAmount) {
        this.loanSanctionAmount = loanSanctionAmount;
    }

    public String getDisbursementDate() {
        return this.disbursementDate;
    }

    public IsCalculateTemp disbursementDate(String disbursementDate) {
        this.setDisbursementDate(disbursementDate);
        return this;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public String getDisburseAmount() {
        return this.disburseAmount;
    }

    public IsCalculateTemp disburseAmount(String disburseAmount) {
        this.setDisburseAmount(disburseAmount);
        return this;
    }

    public void setDisburseAmount(String disburseAmount) {
        this.disburseAmount = disburseAmount;
    }

    public String getMaturityLoanDate() {
        return this.maturityLoanDate;
    }

    public IsCalculateTemp maturityLoanDate(String maturityLoanDate) {
        this.setMaturityLoanDate(maturityLoanDate);
        return this;
    }

    public void setMaturityLoanDate(String maturityLoanDate) {
        this.maturityLoanDate = maturityLoanDate;
    }

    public String getBankDate() {
        return this.bankDate;
    }

    public IsCalculateTemp bankDate(String bankDate) {
        this.setBankDate(bankDate);
        return this;
    }

    public void setBankDate(String bankDate) {
        this.bankDate = bankDate;
    }

    public String getCropName() {
        return this.cropName;
    }

    public IsCalculateTemp cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getRecoveryAmount() {
        return this.recoveryAmount;
    }

    public IsCalculateTemp recoveryAmount(String recoveryAmount) {
        this.setRecoveryAmount(recoveryAmount);
        return this;
    }

    public void setRecoveryAmount(String recoveryAmount) {
        this.recoveryAmount = recoveryAmount;
    }

    public String getRecoveryInterest() {
        return this.recoveryInterest;
    }

    public IsCalculateTemp recoveryInterest(String recoveryInterest) {
        this.setRecoveryInterest(recoveryInterest);
        return this;
    }

    public void setRecoveryInterest(String recoveryInterest) {
        this.recoveryInterest = recoveryInterest;
    }

    public String getRecoveryDate() {
        return this.recoveryDate;
    }

    public IsCalculateTemp recoveryDate(String recoveryDate) {
        this.setRecoveryDate(recoveryDate);
        return this;
    }

    public void setRecoveryDate(String recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public String getBalanceAmount() {
        return this.balanceAmount;
    }

    public IsCalculateTemp balanceAmount(String balanceAmount) {
        this.setBalanceAmount(balanceAmount);
        return this;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getPrevDays() {
        return this.prevDays;
    }

    public IsCalculateTemp prevDays(Integer prevDays) {
        this.setPrevDays(prevDays);
        return this;
    }

    public void setPrevDays(Integer prevDays) {
        this.prevDays = prevDays;
    }

    public Integer getPresDays() {
        return this.presDays;
    }

    public IsCalculateTemp presDays(Integer presDays) {
        this.setPresDays(presDays);
        return this;
    }

    public void setPresDays(Integer presDays) {
        this.presDays = presDays;
    }

    public Integer getActualDays() {
        return this.actualDays;
    }

    public IsCalculateTemp actualDays(Integer actualDays) {
        this.setActualDays(actualDays);
        return this;
    }

    public void setActualDays(Integer actualDays) {
        this.actualDays = actualDays;
    }

    public Integer getnProd() {
        return this.nProd;
    }

    public IsCalculateTemp nProd(Integer nProd) {
        this.setnProd(nProd);
        return this;
    }

    public void setnProd(Integer nProd) {
        this.nProd = nProd;
    }

    public String getProductAmount() {
        return this.productAmount;
    }

    public IsCalculateTemp productAmount(String productAmount) {
        this.setProductAmount(productAmount);
        return this;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductBank() {
        return this.productBank;
    }

    public IsCalculateTemp productBank(String productBank) {
        this.setProductBank(productBank);
        return this;
    }

    public void setProductBank(String productBank) {
        this.productBank = productBank;
    }

    public String getProductAbh3Lakh() {
        return this.productAbh3Lakh;
    }

    public IsCalculateTemp productAbh3Lakh(String productAbh3Lakh) {
        this.setProductAbh3Lakh(productAbh3Lakh);
        return this;
    }

    public void setProductAbh3Lakh(String productAbh3Lakh) {
        this.productAbh3Lakh = productAbh3Lakh;
    }

    public Long getInterestFirst15() {
        return this.interestFirst15;
    }

    public IsCalculateTemp interestFirst15(Long interestFirst15) {
        this.setInterestFirst15(interestFirst15);
        return this;
    }

    public void setInterestFirst15(Long interestFirst15) {
        this.interestFirst15 = interestFirst15;
    }

    public Long getInterestFirst25() {
        return this.interestFirst25;
    }

    public IsCalculateTemp interestFirst25(Long interestFirst25) {
        this.setInterestFirst25(interestFirst25);
        return this;
    }

    public void setInterestFirst25(Long interestFirst25) {
        this.interestFirst25 = interestFirst25;
    }

    public Long getInterestSecond15() {
        return this.interestSecond15;
    }

    public IsCalculateTemp interestSecond15(Long interestSecond15) {
        this.setInterestSecond15(interestSecond15);
        return this;
    }

    public void setInterestSecond15(Long interestSecond15) {
        this.interestSecond15 = interestSecond15;
    }

    public Long getInterestSecond25() {
        return this.interestSecond25;
    }

    public IsCalculateTemp interestSecond25(Long interestSecond25) {
        this.setInterestSecond25(interestSecond25);
        return this;
    }

    public void setInterestSecond25(Long interestSecond25) {
        this.interestSecond25 = interestSecond25;
    }

    public Long getInterestStateFirst3() {
        return this.interestStateFirst3;
    }

    public IsCalculateTemp interestStateFirst3(Long interestStateFirst3) {
        this.setInterestStateFirst3(interestStateFirst3);
        return this;
    }

    public void setInterestStateFirst3(Long interestStateFirst3) {
        this.interestStateFirst3 = interestStateFirst3;
    }

    public Long getInterestStateSecond3() {
        return this.interestStateSecond3;
    }

    public IsCalculateTemp interestStateSecond3(Long interestStateSecond3) {
        this.setInterestStateSecond3(interestStateSecond3);
        return this;
    }

    public void setInterestStateSecond3(Long interestStateSecond3) {
        this.interestStateSecond3 = interestStateSecond3;
    }

    public Long getInterestFirstAbh3() {
        return this.interestFirstAbh3;
    }

    public IsCalculateTemp interestFirstAbh3(Long interestFirstAbh3) {
        this.setInterestFirstAbh3(interestFirstAbh3);
        return this;
    }

    public void setInterestFirstAbh3(Long interestFirstAbh3) {
        this.interestFirstAbh3 = interestFirstAbh3;
    }

    public Long getInterestSecondAbh3() {
        return this.interestSecondAbh3;
    }

    public IsCalculateTemp interestSecondAbh3(Long interestSecondAbh3) {
        this.setInterestSecondAbh3(interestSecondAbh3);
        return this;
    }

    public void setInterestSecondAbh3(Long interestSecondAbh3) {
        this.interestSecondAbh3 = interestSecondAbh3;
    }

    public Long getPanjabraoInt3() {
        return this.panjabraoInt3;
    }

    public IsCalculateTemp panjabraoInt3(Long panjabraoInt3) {
        this.setPanjabraoInt3(panjabraoInt3);
        return this;
    }

    public void setPanjabraoInt3(Long panjabraoInt3) {
        this.panjabraoInt3 = panjabraoInt3;
    }

    public Integer getIsRecover() {
        return this.isRecover;
    }

    public IsCalculateTemp isRecover(Integer isRecover) {
        this.setIsRecover(isRecover);
        return this;
    }

    public void setIsRecover(Integer isRecover) {
        this.isRecover = isRecover;
    }

    public Long getAbh3Lakh() {
        return this.abh3Lakh;
    }

    public IsCalculateTemp abh3Lakh(Long abh3Lakh) {
        this.setAbh3Lakh(abh3Lakh);
        return this;
    }

    public void setAbh3Lakh(Long abh3Lakh) {
        this.abh3Lakh = abh3Lakh;
    }

    public Integer getAmt50000() {
        return this.amt50000;
    }

    public IsCalculateTemp amt50000(Integer amt50000) {
        this.setAmt50000(amt50000);
        return this;
    }

    public void setAmt50000(Integer amt50000) {
        this.amt50000 = amt50000;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IsCalculateTemp)) {
            return false;
        }
        return id != null && id.equals(((IsCalculateTemp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsCalculateTemp{" +
            "id=" + getId() +
            ", serialNo=" + getSerialNo() +
            ", financialYear='" + getFinancialYear() + "'" +
            ", issFileParserId=" + getIssFileParserId() +
            ", branchCode='" + getBranchCode() + "'" +
            ", loanAccountNumberKcc='" + getLoanAccountNumberKcc() + "'" +
            ", farmerName='" + getFarmerName() + "'" +
            ", gender='" + getGender() + "'" +
            ", cast='" + getCast() + "'" +
            ", aadharNumber='" + getAadharNumber() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", farmerType='" + getFarmerType() + "'" +
            ", socialCategory='" + getSocialCategory() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", loanSanctionDate='" + getLoanSanctionDate() + "'" +
            ", loanSanctionAmount='" + getLoanSanctionAmount() + "'" +
            ", disbursementDate='" + getDisbursementDate() + "'" +
            ", disburseAmount='" + getDisburseAmount() + "'" +
            ", maturityLoanDate='" + getMaturityLoanDate() + "'" +
            ", bankDate='" + getBankDate() + "'" +
            ", cropName='" + getCropName() + "'" +
            ", recoveryAmount='" + getRecoveryAmount() + "'" +
            ", recoveryInterest='" + getRecoveryInterest() + "'" +
            ", recoveryDate='" + getRecoveryDate() + "'" +
            ", balanceAmount='" + getBalanceAmount() + "'" +
            ", prevDays=" + getPrevDays() +
            ", presDays=" + getPresDays() +
            ", actualDays=" + getActualDays() +
            ", nProd=" + getnProd() +
            ", productAmount='" + getProductAmount() + "'" +
            ", productBank='" + getProductBank() + "'" +
            ", productAbh3Lakh='" + getProductAbh3Lakh() + "'" +
            ", interestFirst15=" + getInterestFirst15() +
            ", interestFirst25=" + getInterestFirst25() +
            ", interestSecond15=" + getInterestSecond15() +
            ", interestSecond25=" + getInterestSecond25() +
            ", interestStateFirst3=" + getInterestStateFirst3() +
            ", interestStateSecond3=" + getInterestStateSecond3() +
            ", interestFirstAbh3=" + getInterestFirstAbh3() +
            ", interestSecondAbh3=" + getInterestSecondAbh3() +
            ", panjabraoInt3=" + getPanjabraoInt3() +
            ", isRecover=" + getIsRecover() +
            ", abh3Lakh=" + getAbh3Lakh() +
            ", amt50000=" + getAmt50000() +
            "}";
    }
}
