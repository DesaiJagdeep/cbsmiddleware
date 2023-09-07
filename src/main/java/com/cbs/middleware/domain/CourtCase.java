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
    private Double loanAmount;

    @Column(name = "loan_date")
    private LocalDate loanDate;

    @Column(name = "term_of_loan")
    private String termOfLoan;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "installment_amount")
    private Double installmentAmount;

    @Column(name = "total_credit")
    private Double totalCredit;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "interest_paid")
    private Double interestPaid;

    @Column(name = "penal_interest_paid")
    private Double penalInterestPaid;

    @Column(name = "due_amount")
    private Double dueAmount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "due_interest")
    private Double dueInterest;

    @Column(name = "due_penal_interest")
    private Double duePenalInterest;

    @Column(name = "due_more_interest")
    private Double dueMoreInterest;

    @Column(name = "interest_recivable")
    private Double interestRecivable;

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

    public Double getLoanAmount() {
        return this.loanAmount;
    }

    public CourtCase loanAmount(Double loanAmount) {
        this.setLoanAmount(loanAmount);
        return this;
    }

    public void setLoanAmount(Double loanAmount) {
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

    public Double getInterestRate() {
        return this.interestRate;
    }

    public CourtCase interestRate(Double interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getInstallmentAmount() {
        return this.installmentAmount;
    }

    public CourtCase installmentAmount(Double installmentAmount) {
        this.setInstallmentAmount(installmentAmount);
        return this;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Double getTotalCredit() {
        return this.totalCredit;
    }

    public CourtCase totalCredit(Double totalCredit) {
        this.setTotalCredit(totalCredit);
        return this;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Double getBalance() {
        return this.balance;
    }

    public CourtCase balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getInterestPaid() {
        return this.interestPaid;
    }

    public CourtCase interestPaid(Double interestPaid) {
        this.setInterestPaid(interestPaid);
        return this;
    }

    public void setInterestPaid(Double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public Double getPenalInterestPaid() {
        return this.penalInterestPaid;
    }

    public CourtCase penalInterestPaid(Double penalInterestPaid) {
        this.setPenalInterestPaid(penalInterestPaid);
        return this;
    }

    public void setPenalInterestPaid(Double penalInterestPaid) {
        this.penalInterestPaid = penalInterestPaid;
    }

    public Double getDueAmount() {
        return this.dueAmount;
    }

    public CourtCase dueAmount(Double dueAmount) {
        this.setDueAmount(dueAmount);
        return this;
    }

    public void setDueAmount(Double dueAmount) {
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

    public Double getDueInterest() {
        return this.dueInterest;
    }

    public CourtCase dueInterest(Double dueInterest) {
        this.setDueInterest(dueInterest);
        return this;
    }

    public void setDueInterest(Double dueInterest) {
        this.dueInterest = dueInterest;
    }

    public Double getDuePenalInterest() {
        return this.duePenalInterest;
    }

    public CourtCase duePenalInterest(Double duePenalInterest) {
        this.setDuePenalInterest(duePenalInterest);
        return this;
    }

    public void setDuePenalInterest(Double duePenalInterest) {
        this.duePenalInterest = duePenalInterest;
    }

    public Double getDueMoreInterest() {
        return this.dueMoreInterest;
    }

    public CourtCase dueMoreInterest(Double dueMoreInterest) {
        this.setDueMoreInterest(dueMoreInterest);
        return this;
    }

    public void setDueMoreInterest(Double dueMoreInterest) {
        this.dueMoreInterest = dueMoreInterest;
    }

    public Double getInterestRecivable() {
        return this.interestRecivable;
    }

    public CourtCase interestRecivable(Double interestRecivable) {
        this.setInterestRecivable(interestRecivable);
        return this;
    }

    public void setInterestRecivable(Double interestRecivable) {
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
