package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A CourtCase.
 */
@Entity
@Table(name = "court_case")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCase extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sr_no")
    private String srNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "unique_file_name")
    private String uniqueFileName;

    @Column(name = "name_of_defaulter")
    private String nameOfDefaulter;

    @Column(name = "address")
    private String address;

    @Column(name = "loan_type")
    private String loanType;

    @Column(name = "loan_amount")
    private String loanAmount;

    @Column(name = "loan_date")
    private LocalDate loanDate;

    @Column(name = "term_of_loan")
    private String termOfLoan;

    @Column(name = "interest_rate")
    private String interestRate;

    @Column(name = "installment_amount")
    private String installmentAmount;

    @Column(name = "total_credit")
    private String totalCredit;

    @Column(name = "balance")
    private String balance;

    @Column(name = "interest_paid")
    private String interestPaid;

    @Column(name = "penal_interest_paid")
    private String penalInterestPaid;

    @Column(name = "due_amount")
    private String dueAmount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "due_interest")
    private String dueInterest;

    @Column(name = "due_penal_interest")
    private String duePenalInterest;

    @Column(name = "due_more_interest")
    private String dueMoreInterest;

    @Column(name = "interest_recivable")
    private String interestRecivable;

    @Column(name = "taaran")
    private String taaran;

    @Column(name = "taaran_en")
    private String taaranEn;

    @Column(name = "gaurentor_one")
    private String gaurentorOne;

    @Column(name = "gaurentor_one_address")
    private String gaurentorOneAddress;

    @Column(name = "gaurentor_two")
    private String gaurentorTwo;

    @Column(name = "gaurentor_two_address")
    private String gaurentorTwoAddress;

    @Column(name = "first_notice_date")
    private LocalDate firstNoticeDate;

    @Column(name = "second_notice_date")
    private LocalDate secondNoticeDate;

    //English data

    @Column(name = "sr_no_en")
    private Long srNoEn;

    @Column(name = "bank_name_en")
    private String bankNameEn;

    @Column(name = "taluka_name_en")
    private String talukaNameEn;

    @Column(name = "account_no_en")
    private String accountNoEn;

    @Column(name = "name_of_defaulter_en")
    private String nameOfDefaulterEn;

    @Column(name = "address_en")
    private String addressEn;

    @Column(name = "loan_type_en")
    private String loanTypeEn;

    @Column(name = "loan_amount_en")
    private Double loanAmountEn;

    @Column(name = "term_of_loan_en")
    private String termOfLoanEn;

    @Column(name = "interest_rate_en")
    private Double interestRateEn;

    @Column(name = "installment_amount_en")
    private Double installmentAmountEn;

    @Column(name = "total_credit_en")
    private Double totalCreditEn;

    @Column(name = "balance_en")
    private Double balanceEn;

    @Column(name = "interest_paid_en")
    private Double interestPaidEn;

    @Column(name = "penal_interest_paid_en")
    private Double penalInterestPaidEn;

    @Column(name = "due_amount_en")
    private Double dueAmountEn;

    @Column(name = "due_interest_en")
    private Double dueInterestEn;

    @Column(name = "due_penal_interest_en")
    private Double duePenalInterestEn;

    @Column(name = "due_more_interest_en")
    private Double dueMoreInterestEn;

    @Column(name = "interest_recivable_en")
    private Double interestRecivableEn;

    @Column(name = "gaurentor_one_en")
    private String gaurentorOneEn;

    @Column(name = "gaurentor_one_address_en")
    private String gaurentorOneAddressEn;

    @Column(name = "gaurentor_two_en")
    private String gaurentorTwoEn;

    @Column(name = "gaurentor_two_address_en")
    private String gaurentorTwoAddressEn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourtCase id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrNo() {
        return this.srNo;
    }

    public CourtCase srNo(String srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUniqueFileName() {
        return uniqueFileName;
    }

    public void setUniqueFileName(String uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
    }

    public String getAccountNoEn() {
        return accountNoEn;
    }

    public String getTaaran() {
        return taaran;
    }

    public void setTaaran(String taaran) {
        this.taaran = taaran;
    }

    public String getTaaranEn() {
        return taaranEn;
    }

    public void setTaaranEn(String taaranEn) {
        this.taaranEn = taaranEn;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getBankNameEn() {
        return bankNameEn;
    }

    public void setBankNameEn(String bankNameEn) {
        this.bankNameEn = bankNameEn;
    }

    public String getTalukaNameEn() {
        return talukaNameEn;
    }

    public void setTalukaNameEn(String talukaNameEn) {
        this.talukaNameEn = talukaNameEn;
    }

    public void setAccountNoEn(String accountNoEn) {
        this.accountNoEn = accountNoEn;
    }

    public String getNameOfDefaulterEn() {
        return nameOfDefaulterEn;
    }

    public void setNameOfDefaulterEn(String nameOfDefaulterEn) {
        this.nameOfDefaulterEn = nameOfDefaulterEn;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getLoanTypeEn() {
        return loanTypeEn;
    }

    public void setLoanTypeEn(String loanTypeEn) {
        this.loanTypeEn = loanTypeEn;
    }

    public String getTermOfLoanEn() {
        return termOfLoanEn;
    }

    public void setTermOfLoanEn(String termOfLoanEn) {
        this.termOfLoanEn = termOfLoanEn;
    }

    public String getGaurentorOneEn() {
        return gaurentorOneEn;
    }

    public void setGaurentorOneEn(String gaurentorOneEn) {
        this.gaurentorOneEn = gaurentorOneEn;
    }

    public String getGaurentorOneAddressEn() {
        return gaurentorOneAddressEn;
    }

    public void setGaurentorOneAddressEn(String gaurentorOneAddressEn) {
        this.gaurentorOneAddressEn = gaurentorOneAddressEn;
    }

    public String getGaurentorTwoEn() {
        return gaurentorTwoEn;
    }

    public void setGaurentorTwoEn(String gaurentorTwoEn) {
        this.gaurentorTwoEn = gaurentorTwoEn;
    }

    public String getGaurentorTwoAddressEn() {
        return gaurentorTwoAddressEn;
    }

    public void setGaurentorTwoAddressEn(String gaurentorTwoAddressEn) {
        this.gaurentorTwoAddressEn = gaurentorTwoAddressEn;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public CourtCase accountNo(String accountNo) {
        this.setAccountNo(accountNo);
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getNameOfDefaulter() {
        return this.nameOfDefaulter;
    }

    public CourtCase nameOfDefaulter(String nameOfDefaulter) {
        this.setNameOfDefaulter(nameOfDefaulter);
        return this;
    }

    public void setNameOfDefaulter(String nameOfDefaulter) {
        this.nameOfDefaulter = nameOfDefaulter;
    }

    public String getAddress() {
        return this.address;
    }

    public CourtCase address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoanType() {
        return this.loanType;
    }

    public CourtCase loanType(String loanType) {
        this.setLoanType(loanType);
        return this;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanAmount() {
        return this.loanAmount;
    }

    public CourtCase loanAmount(String loanAmount) {
        this.setLoanAmount(loanAmount);
        return this;
    }

    public Long getSrNoEn() {
        return srNoEn;
    }

    public void setSrNoEn(Long srNoEn) {
        this.srNoEn = srNoEn;
    }

    public Double getLoanAmountEn() {
        return loanAmountEn;
    }

    public void setLoanAmountEn(Double loanAmountEn) {
        this.loanAmountEn = loanAmountEn;
    }

    public Double getInterestRateEn() {
        return interestRateEn;
    }

    public void setInterestRateEn(Double interestRateEn) {
        this.interestRateEn = interestRateEn;
    }

    public Double getInstallmentAmountEn() {
        return installmentAmountEn;
    }

    public void setInstallmentAmountEn(Double installmentAmountEn) {
        this.installmentAmountEn = installmentAmountEn;
    }

    public Double getTotalCreditEn() {
        return totalCreditEn;
    }

    public void setTotalCreditEn(Double totalCreditEn) {
        this.totalCreditEn = totalCreditEn;
    }

    public Double getBalanceEn() {
        return balanceEn;
    }

    public void setBalanceEn(Double balanceEn) {
        this.balanceEn = balanceEn;
    }

    public Double getInterestPaidEn() {
        return interestPaidEn;
    }

    public void setInterestPaidEn(Double interestPaidEn) {
        this.interestPaidEn = interestPaidEn;
    }

    public Double getPenalInterestPaidEn() {
        return penalInterestPaidEn;
    }

    public void setPenalInterestPaidEn(Double penalInterestPaidEn) {
        this.penalInterestPaidEn = penalInterestPaidEn;
    }

    public Double getDueAmountEn() {
        return dueAmountEn;
    }

    public void setDueAmountEn(Double dueAmountEn) {
        this.dueAmountEn = dueAmountEn;
    }

    public Double getDueInterestEn() {
        return dueInterestEn;
    }

    public void setDueInterestEn(Double dueInterestEn) {
        this.dueInterestEn = dueInterestEn;
    }

    public Double getDuePenalInterestEn() {
        return duePenalInterestEn;
    }

    public void setDuePenalInterestEn(Double duePenalInterestEn) {
        this.duePenalInterestEn = duePenalInterestEn;
    }

    public Double getDueMoreInterestEn() {
        return dueMoreInterestEn;
    }

    public void setDueMoreInterestEn(Double dueMoreInterestEn) {
        this.dueMoreInterestEn = dueMoreInterestEn;
    }

    public Double getInterestRecivableEn() {
        return interestRecivableEn;
    }

    public void setInterestRecivableEn(Double interestRecivableEn) {
        this.interestRecivableEn = interestRecivableEn;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LocalDate getLoanDate() {
        return this.loanDate;
    }

    public CourtCase loanDate(LocalDate loanDate) {
        this.setLoanDate(loanDate);
        return this;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public String getTermOfLoan() {
        return this.termOfLoan;
    }

    public CourtCase termOfLoan(String termOfLoan) {
        this.setTermOfLoan(termOfLoan);
        return this;
    }

    public void setTermOfLoan(String termOfLoan) {
        this.termOfLoan = termOfLoan;
    }

    public String getInterestRate() {
        return this.interestRate;
    }

    public CourtCase interestRate(String interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getInstallmentAmount() {
        return this.installmentAmount;
    }

    public CourtCase installmentAmount(String installmentAmount) {
        this.setInstallmentAmount(installmentAmount);
        return this;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getTotalCredit() {
        return this.totalCredit;
    }

    public CourtCase totalCredit(String totalCredit) {
        this.setTotalCredit(totalCredit);
        return this;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getBalance() {
        return this.balance;
    }

    public CourtCase balance(String balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getInterestPaid() {
        return this.interestPaid;
    }

    public CourtCase interestPaid(String interestPaid) {
        this.setInterestPaid(interestPaid);
        return this;
    }

    public void setInterestPaid(String interestPaid) {
        this.interestPaid = interestPaid;
    }

    public String getPenalInterestPaid() {
        return this.penalInterestPaid;
    }

    public CourtCase penalInterestPaid(String penalInterestPaid) {
        this.setPenalInterestPaid(penalInterestPaid);
        return this;
    }

    public void setPenalInterestPaid(String penalInterestPaid) {
        this.penalInterestPaid = penalInterestPaid;
    }

    public String getDueAmount() {
        return this.dueAmount;
    }

    public CourtCase dueAmount(String dueAmount) {
        this.setDueAmount(dueAmount);
        return this;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public CourtCase dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueInterest() {
        return this.dueInterest;
    }

    public CourtCase dueInterest(String dueInterest) {
        this.setDueInterest(dueInterest);
        return this;
    }

    public void setDueInterest(String dueInterest) {
        this.dueInterest = dueInterest;
    }

    public String getDuePenalInterest() {
        return this.duePenalInterest;
    }

    public CourtCase duePenalInterest(String duePenalInterest) {
        this.setDuePenalInterest(duePenalInterest);
        return this;
    }

    public void setDuePenalInterest(String duePenalInterest) {
        this.duePenalInterest = duePenalInterest;
    }

    public String getDueMoreInterest() {
        return this.dueMoreInterest;
    }

    public CourtCase dueMoreInterest(String dueMoreInterest) {
        this.setDueMoreInterest(dueMoreInterest);
        return this;
    }

    public void setDueMoreInterest(String dueMoreInterest) {
        this.dueMoreInterest = dueMoreInterest;
    }

    public String getInterestRecivable() {
        return this.interestRecivable;
    }

    public CourtCase interestRecivable(String interestRecivable) {
        this.setInterestRecivable(interestRecivable);
        return this;
    }

    public void setInterestRecivable(String interestRecivable) {
        this.interestRecivable = interestRecivable;
    }

    public String getGaurentorOne() {
        return this.gaurentorOne;
    }

    public CourtCase gaurentorOne(String gaurentorOne) {
        this.setGaurentorOne(gaurentorOne);
        return this;
    }

    public void setGaurentorOne(String gaurentorOne) {
        this.gaurentorOne = gaurentorOne;
    }

    public String getGaurentorOneAddress() {
        return this.gaurentorOneAddress;
    }

    public CourtCase gaurentorOneAddress(String gaurentorOneAddress) {
        this.setGaurentorOneAddress(gaurentorOneAddress);
        return this;
    }

    public void setGaurentorOneAddress(String gaurentorOneAddress) {
        this.gaurentorOneAddress = gaurentorOneAddress;
    }

    public String getGaurentorTwo() {
        return this.gaurentorTwo;
    }

    public CourtCase gaurentorTwo(String gaurentorTwo) {
        this.setGaurentorTwo(gaurentorTwo);
        return this;
    }

    public void setGaurentorTwo(String gaurentorTwo) {
        this.gaurentorTwo = gaurentorTwo;
    }

    public String getGaurentorTwoAddress() {
        return this.gaurentorTwoAddress;
    }

    public CourtCase gaurentorTwoAddress(String gaurentorTwoAddress) {
        this.setGaurentorTwoAddress(gaurentorTwoAddress);
        return this;
    }

    public void setGaurentorTwoAddress(String gaurentorTwoAddress) {
        this.gaurentorTwoAddress = gaurentorTwoAddress;
    }

    public LocalDate getFirstNoticeDate() {
        return this.firstNoticeDate;
    }

    public CourtCase firstNoticeDate(LocalDate firstNoticeDate) {
        this.setFirstNoticeDate(firstNoticeDate);
        return this;
    }

    public void setFirstNoticeDate(LocalDate firstNoticeDate) {
        this.firstNoticeDate = firstNoticeDate;
    }

    public LocalDate getSecondNoticeDate() {
        return this.secondNoticeDate;
    }

    public CourtCase secondNoticeDate(LocalDate secondNoticeDate) {
        this.setSecondNoticeDate(secondNoticeDate);
        return this;
    }

    public void setSecondNoticeDate(LocalDate secondNoticeDate) {
        this.secondNoticeDate = secondNoticeDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourtCase)) {
            return false;
        }
        return id != null && id.equals(((CourtCase) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCase{" +
            "id=" + getId() +
            ", srNo='" + getSrNo() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", nameOfDefaulter='" + getNameOfDefaulter() + "'" +
            ", address='" + getAddress() + "'" +
            ", loanType='" + getLoanType() + "'" +
            ", loanAmount=" + getLoanAmount() +
            ", loanDate='" + getLoanDate() + "'" +
            ", termOfLoan='" + getTermOfLoan() + "'" +
            ", interestRate=" + getInterestRate() +
            ", installmentAmount=" + getInstallmentAmount() +
            ", totalCredit=" + getTotalCredit() +
            ", balance=" + getBalance() +
            ", interestPaid=" + getInterestPaid() +
            ", penalInterestPaid=" + getPenalInterestPaid() +
            ", dueAmount=" + getDueAmount() +
            ", dueDate='" + getDueDate() + "'" +
            ", dueInterest=" + getDueInterest() +
            ", duePenalInterest=" + getDuePenalInterest() +
            ", dueMoreInterest=" + getDueMoreInterest() +
            ", interestRecivable=" + getInterestRecivable() +
            ", gaurentorOne='" + getGaurentorOne() + "'" +
            ", gaurentorOneAddress='" + getGaurentorOneAddress() + "'" +
            ", gaurentorTwo='" + getGaurentorTwo() + "'" +
            ", gaurentorTwoAddress='" + getGaurentorTwoAddress() + "'" +
            ", firstNoticeDate='" + getFirstNoticeDate() + "'" +
            ", secondNoticeDate='" + getSecondNoticeDate() + "'" +
            "}";
    }
}
