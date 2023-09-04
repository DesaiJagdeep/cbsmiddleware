package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.CourtCase} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.CourtCaseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /court-cases?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCaseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter srNo;

    private StringFilter accountNo;

    private StringFilter nameOfDefaulter;

    private StringFilter address;

    private StringFilter loanType;

    private DoubleFilter loanAmount;

    private LocalDateFilter loanDate;

    private StringFilter termOfLoan;

    private DoubleFilter interestRate;

    private DoubleFilter installmentAmount;

    private DoubleFilter totalCredit;

    private DoubleFilter balance;

    private DoubleFilter interestPaid;

    private DoubleFilter penalInterestPaid;

    private DoubleFilter dueAmount;

    private LocalDateFilter dueDate;

    private DoubleFilter dueInterest;

    private DoubleFilter duePenalInterest;

    private DoubleFilter dueMoreInterest;

    private DoubleFilter interestRecivable;

    private StringFilter gaurentorOne;

    private StringFilter gaurentorOneAddress;

    private StringFilter gaurentorTwo;

    private StringFilter gaurentorTwoAddress;

    private LocalDateFilter firstNoticeDate;

    private LocalDateFilter secondNoticeDate;

    private Boolean distinct;

    public CourtCaseCriteria() {}

    public CourtCaseCriteria(CourtCaseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.srNo = other.srNo == null ? null : other.srNo.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.nameOfDefaulter = other.nameOfDefaulter == null ? null : other.nameOfDefaulter.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.loanType = other.loanType == null ? null : other.loanType.copy();
        this.loanAmount = other.loanAmount == null ? null : other.loanAmount.copy();
        this.loanDate = other.loanDate == null ? null : other.loanDate.copy();
        this.termOfLoan = other.termOfLoan == null ? null : other.termOfLoan.copy();
        this.interestRate = other.interestRate == null ? null : other.interestRate.copy();
        this.installmentAmount = other.installmentAmount == null ? null : other.installmentAmount.copy();
        this.totalCredit = other.totalCredit == null ? null : other.totalCredit.copy();
        this.balance = other.balance == null ? null : other.balance.copy();
        this.interestPaid = other.interestPaid == null ? null : other.interestPaid.copy();
        this.penalInterestPaid = other.penalInterestPaid == null ? null : other.penalInterestPaid.copy();
        this.dueAmount = other.dueAmount == null ? null : other.dueAmount.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.dueInterest = other.dueInterest == null ? null : other.dueInterest.copy();
        this.duePenalInterest = other.duePenalInterest == null ? null : other.duePenalInterest.copy();
        this.dueMoreInterest = other.dueMoreInterest == null ? null : other.dueMoreInterest.copy();
        this.interestRecivable = other.interestRecivable == null ? null : other.interestRecivable.copy();
        this.gaurentorOne = other.gaurentorOne == null ? null : other.gaurentorOne.copy();
        this.gaurentorOneAddress = other.gaurentorOneAddress == null ? null : other.gaurentorOneAddress.copy();
        this.gaurentorTwo = other.gaurentorTwo == null ? null : other.gaurentorTwo.copy();
        this.gaurentorTwoAddress = other.gaurentorTwoAddress == null ? null : other.gaurentorTwoAddress.copy();
        this.firstNoticeDate = other.firstNoticeDate == null ? null : other.firstNoticeDate.copy();
        this.secondNoticeDate = other.secondNoticeDate == null ? null : other.secondNoticeDate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourtCaseCriteria copy() {
        return new CourtCaseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSrNo() {
        return srNo;
    }

    public StringFilter srNo() {
        if (srNo == null) {
            srNo = new StringFilter();
        }
        return srNo;
    }

    public void setSrNo(StringFilter srNo) {
        this.srNo = srNo;
    }

    public StringFilter getAccountNo() {
        return accountNo;
    }

    public StringFilter accountNo() {
        if (accountNo == null) {
            accountNo = new StringFilter();
        }
        return accountNo;
    }

    public void setAccountNo(StringFilter accountNo) {
        this.accountNo = accountNo;
    }

    public StringFilter getNameOfDefaulter() {
        return nameOfDefaulter;
    }

    public StringFilter nameOfDefaulter() {
        if (nameOfDefaulter == null) {
            nameOfDefaulter = new StringFilter();
        }
        return nameOfDefaulter;
    }

    public void setNameOfDefaulter(StringFilter nameOfDefaulter) {
        this.nameOfDefaulter = nameOfDefaulter;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getLoanType() {
        return loanType;
    }

    public StringFilter loanType() {
        if (loanType == null) {
            loanType = new StringFilter();
        }
        return loanType;
    }

    public void setLoanType(StringFilter loanType) {
        this.loanType = loanType;
    }

    public DoubleFilter getLoanAmount() {
        return loanAmount;
    }

    public DoubleFilter loanAmount() {
        if (loanAmount == null) {
            loanAmount = new DoubleFilter();
        }
        return loanAmount;
    }

    public void setLoanAmount(DoubleFilter loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LocalDateFilter getLoanDate() {
        return loanDate;
    }

    public LocalDateFilter loanDate() {
        if (loanDate == null) {
            loanDate = new LocalDateFilter();
        }
        return loanDate;
    }

    public void setLoanDate(LocalDateFilter loanDate) {
        this.loanDate = loanDate;
    }

    public StringFilter getTermOfLoan() {
        return termOfLoan;
    }

    public StringFilter termOfLoan() {
        if (termOfLoan == null) {
            termOfLoan = new StringFilter();
        }
        return termOfLoan;
    }

    public void setTermOfLoan(StringFilter termOfLoan) {
        this.termOfLoan = termOfLoan;
    }

    public DoubleFilter getInterestRate() {
        return interestRate;
    }

    public DoubleFilter interestRate() {
        if (interestRate == null) {
            interestRate = new DoubleFilter();
        }
        return interestRate;
    }

    public void setInterestRate(DoubleFilter interestRate) {
        this.interestRate = interestRate;
    }

    public DoubleFilter getInstallmentAmount() {
        return installmentAmount;
    }

    public DoubleFilter installmentAmount() {
        if (installmentAmount == null) {
            installmentAmount = new DoubleFilter();
        }
        return installmentAmount;
    }

    public void setInstallmentAmount(DoubleFilter installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public DoubleFilter getTotalCredit() {
        return totalCredit;
    }

    public DoubleFilter totalCredit() {
        if (totalCredit == null) {
            totalCredit = new DoubleFilter();
        }
        return totalCredit;
    }

    public void setTotalCredit(DoubleFilter totalCredit) {
        this.totalCredit = totalCredit;
    }

    public DoubleFilter getBalance() {
        return balance;
    }

    public DoubleFilter balance() {
        if (balance == null) {
            balance = new DoubleFilter();
        }
        return balance;
    }

    public void setBalance(DoubleFilter balance) {
        this.balance = balance;
    }

    public DoubleFilter getInterestPaid() {
        return interestPaid;
    }

    public DoubleFilter interestPaid() {
        if (interestPaid == null) {
            interestPaid = new DoubleFilter();
        }
        return interestPaid;
    }

    public void setInterestPaid(DoubleFilter interestPaid) {
        this.interestPaid = interestPaid;
    }

    public DoubleFilter getPenalInterestPaid() {
        return penalInterestPaid;
    }

    public DoubleFilter penalInterestPaid() {
        if (penalInterestPaid == null) {
            penalInterestPaid = new DoubleFilter();
        }
        return penalInterestPaid;
    }

    public void setPenalInterestPaid(DoubleFilter penalInterestPaid) {
        this.penalInterestPaid = penalInterestPaid;
    }

    public DoubleFilter getDueAmount() {
        return dueAmount;
    }

    public DoubleFilter dueAmount() {
        if (dueAmount == null) {
            dueAmount = new DoubleFilter();
        }
        return dueAmount;
    }

    public void setDueAmount(DoubleFilter dueAmount) {
        this.dueAmount = dueAmount;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public LocalDateFilter dueDate() {
        if (dueDate == null) {
            dueDate = new LocalDateFilter();
        }
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public DoubleFilter getDueInterest() {
        return dueInterest;
    }

    public DoubleFilter dueInterest() {
        if (dueInterest == null) {
            dueInterest = new DoubleFilter();
        }
        return dueInterest;
    }

    public void setDueInterest(DoubleFilter dueInterest) {
        this.dueInterest = dueInterest;
    }

    public DoubleFilter getDuePenalInterest() {
        return duePenalInterest;
    }

    public DoubleFilter duePenalInterest() {
        if (duePenalInterest == null) {
            duePenalInterest = new DoubleFilter();
        }
        return duePenalInterest;
    }

    public void setDuePenalInterest(DoubleFilter duePenalInterest) {
        this.duePenalInterest = duePenalInterest;
    }

    public DoubleFilter getDueMoreInterest() {
        return dueMoreInterest;
    }

    public DoubleFilter dueMoreInterest() {
        if (dueMoreInterest == null) {
            dueMoreInterest = new DoubleFilter();
        }
        return dueMoreInterest;
    }

    public void setDueMoreInterest(DoubleFilter dueMoreInterest) {
        this.dueMoreInterest = dueMoreInterest;
    }

    public DoubleFilter getInterestRecivable() {
        return interestRecivable;
    }

    public DoubleFilter interestRecivable() {
        if (interestRecivable == null) {
            interestRecivable = new DoubleFilter();
        }
        return interestRecivable;
    }

    public void setInterestRecivable(DoubleFilter interestRecivable) {
        this.interestRecivable = interestRecivable;
    }

    public StringFilter getGaurentorOne() {
        return gaurentorOne;
    }

    public StringFilter gaurentorOne() {
        if (gaurentorOne == null) {
            gaurentorOne = new StringFilter();
        }
        return gaurentorOne;
    }

    public void setGaurentorOne(StringFilter gaurentorOne) {
        this.gaurentorOne = gaurentorOne;
    }

    public StringFilter getGaurentorOneAddress() {
        return gaurentorOneAddress;
    }

    public StringFilter gaurentorOneAddress() {
        if (gaurentorOneAddress == null) {
            gaurentorOneAddress = new StringFilter();
        }
        return gaurentorOneAddress;
    }

    public void setGaurentorOneAddress(StringFilter gaurentorOneAddress) {
        this.gaurentorOneAddress = gaurentorOneAddress;
    }

    public StringFilter getGaurentorTwo() {
        return gaurentorTwo;
    }

    public StringFilter gaurentorTwo() {
        if (gaurentorTwo == null) {
            gaurentorTwo = new StringFilter();
        }
        return gaurentorTwo;
    }

    public void setGaurentorTwo(StringFilter gaurentorTwo) {
        this.gaurentorTwo = gaurentorTwo;
    }

    public StringFilter getGaurentorTwoAddress() {
        return gaurentorTwoAddress;
    }

    public StringFilter gaurentorTwoAddress() {
        if (gaurentorTwoAddress == null) {
            gaurentorTwoAddress = new StringFilter();
        }
        return gaurentorTwoAddress;
    }

    public void setGaurentorTwoAddress(StringFilter gaurentorTwoAddress) {
        this.gaurentorTwoAddress = gaurentorTwoAddress;
    }

    public LocalDateFilter getFirstNoticeDate() {
        return firstNoticeDate;
    }

    public LocalDateFilter firstNoticeDate() {
        if (firstNoticeDate == null) {
            firstNoticeDate = new LocalDateFilter();
        }
        return firstNoticeDate;
    }

    public void setFirstNoticeDate(LocalDateFilter firstNoticeDate) {
        this.firstNoticeDate = firstNoticeDate;
    }

    public LocalDateFilter getSecondNoticeDate() {
        return secondNoticeDate;
    }

    public LocalDateFilter secondNoticeDate() {
        if (secondNoticeDate == null) {
            secondNoticeDate = new LocalDateFilter();
        }
        return secondNoticeDate;
    }

    public void setSecondNoticeDate(LocalDateFilter secondNoticeDate) {
        this.secondNoticeDate = secondNoticeDate;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CourtCaseCriteria that = (CourtCaseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(nameOfDefaulter, that.nameOfDefaulter) &&
            Objects.equals(address, that.address) &&
            Objects.equals(loanType, that.loanType) &&
            Objects.equals(loanAmount, that.loanAmount) &&
            Objects.equals(loanDate, that.loanDate) &&
            Objects.equals(termOfLoan, that.termOfLoan) &&
            Objects.equals(interestRate, that.interestRate) &&
            Objects.equals(installmentAmount, that.installmentAmount) &&
            Objects.equals(totalCredit, that.totalCredit) &&
            Objects.equals(balance, that.balance) &&
            Objects.equals(interestPaid, that.interestPaid) &&
            Objects.equals(penalInterestPaid, that.penalInterestPaid) &&
            Objects.equals(dueAmount, that.dueAmount) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(dueInterest, that.dueInterest) &&
            Objects.equals(duePenalInterest, that.duePenalInterest) &&
            Objects.equals(dueMoreInterest, that.dueMoreInterest) &&
            Objects.equals(interestRecivable, that.interestRecivable) &&
            Objects.equals(gaurentorOne, that.gaurentorOne) &&
            Objects.equals(gaurentorOneAddress, that.gaurentorOneAddress) &&
            Objects.equals(gaurentorTwo, that.gaurentorTwo) &&
            Objects.equals(gaurentorTwoAddress, that.gaurentorTwoAddress) &&
            Objects.equals(firstNoticeDate, that.firstNoticeDate) &&
            Objects.equals(secondNoticeDate, that.secondNoticeDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            srNo,
            accountNo,
            nameOfDefaulter,
            address,
            loanType,
            loanAmount,
            loanDate,
            termOfLoan,
            interestRate,
            installmentAmount,
            totalCredit,
            balance,
            interestPaid,
            penalInterestPaid,
            dueAmount,
            dueDate,
            dueInterest,
            duePenalInterest,
            dueMoreInterest,
            interestRecivable,
            gaurentorOne,
            gaurentorOneAddress,
            gaurentorTwo,
            gaurentorTwoAddress,
            firstNoticeDate,
            secondNoticeDate,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (srNo != null ? "srNo=" + srNo + ", " : "") +
            (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
            (nameOfDefaulter != null ? "nameOfDefaulter=" + nameOfDefaulter + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (loanType != null ? "loanType=" + loanType + ", " : "") +
            (loanAmount != null ? "loanAmount=" + loanAmount + ", " : "") +
            (loanDate != null ? "loanDate=" + loanDate + ", " : "") +
            (termOfLoan != null ? "termOfLoan=" + termOfLoan + ", " : "") +
            (interestRate != null ? "interestRate=" + interestRate + ", " : "") +
            (installmentAmount != null ? "installmentAmount=" + installmentAmount + ", " : "") +
            (totalCredit != null ? "totalCredit=" + totalCredit + ", " : "") +
            (balance != null ? "balance=" + balance + ", " : "") +
            (interestPaid != null ? "interestPaid=" + interestPaid + ", " : "") +
            (penalInterestPaid != null ? "penalInterestPaid=" + penalInterestPaid + ", " : "") +
            (dueAmount != null ? "dueAmount=" + dueAmount + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (dueInterest != null ? "dueInterest=" + dueInterest + ", " : "") +
            (duePenalInterest != null ? "duePenalInterest=" + duePenalInterest + ", " : "") +
            (dueMoreInterest != null ? "dueMoreInterest=" + dueMoreInterest + ", " : "") +
            (interestRecivable != null ? "interestRecivable=" + interestRecivable + ", " : "") +
            (gaurentorOne != null ? "gaurentorOne=" + gaurentorOne + ", " : "") +
            (gaurentorOneAddress != null ? "gaurentorOneAddress=" + gaurentorOneAddress + ", " : "") +
            (gaurentorTwo != null ? "gaurentorTwo=" + gaurentorTwo + ", " : "") +
            (gaurentorTwoAddress != null ? "gaurentorTwoAddress=" + gaurentorTwoAddress + ", " : "") +
            (firstNoticeDate != null ? "firstNoticeDate=" + firstNoticeDate + ", " : "") +
            (secondNoticeDate != null ? "secondNoticeDate=" + secondNoticeDate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
