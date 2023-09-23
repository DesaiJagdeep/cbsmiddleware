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

    private StringFilter talukaName;

    private StringFilter bankName;

    private StringFilter accountNo;

    private StringFilter nameOfDefaulter;

    private StringFilter address;

    private StringFilter loanType;

    private StringFilter loanAmount;

    private LocalDateFilter loanDate;

    private StringFilter termOfLoan;

    private StringFilter interestRate;

    private StringFilter installmentAmount;

    private StringFilter totalCredit;

    private StringFilter balance;

    private StringFilter interestPaid;

    private StringFilter penalInterestPaid;

    private StringFilter dueAmount;

    private LocalDateFilter dueDate;

    private StringFilter dueInterest;

    private StringFilter duePenalInterest;

    private StringFilter dueMoreInterest;

    private StringFilter interestRecivable;

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
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.talukaName = other.talukaName == null ? null : other.talukaName.copy();
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

    public StringFilter getTalukaName() {
        return talukaName;
    }

    public StringFilter talukaName() {
        if (talukaName == null) {
            talukaName = new StringFilter();
        }
        return talukaName;
    }

    public void setTalukaName(StringFilter talukaName) {
        this.talukaName = talukaName;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
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

    public StringFilter getLoanAmount() {
        return loanAmount;
    }

    public StringFilter loanAmount() {
        if (loanAmount == null) {
            loanAmount = new StringFilter();
        }
        return loanAmount;
    }

    public void setLoanAmount(StringFilter loanAmount) {
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

    public StringFilter getInterestRate() {
        return interestRate;
    }

    public StringFilter interestRate() {
        if (interestRate == null) {
            interestRate = new StringFilter();
        }
        return interestRate;
    }

    public void setInterestRate(StringFilter interestRate) {
        this.interestRate = interestRate;
    }

    public StringFilter getInstallmentAmount() {
        return installmentAmount;
    }

    public StringFilter installmentAmount() {
        if (installmentAmount == null) {
            installmentAmount = new StringFilter();
        }
        return installmentAmount;
    }

    public void setInstallmentAmount(StringFilter installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public StringFilter getTotalCredit() {
        return totalCredit;
    }

    public StringFilter totalCredit() {
        if (totalCredit == null) {
            totalCredit = new StringFilter();
        }
        return totalCredit;
    }

    public void setTotalCredit(StringFilter totalCredit) {
        this.totalCredit = totalCredit;
    }

    public StringFilter getBalance() {
        return balance;
    }

    public StringFilter balance() {
        if (balance == null) {
            balance = new StringFilter();
        }
        return balance;
    }

    public void setBalance(StringFilter balance) {
        this.balance = balance;
    }

    public StringFilter getInterestPaid() {
        return interestPaid;
    }

    public StringFilter interestPaid() {
        if (interestPaid == null) {
            interestPaid = new StringFilter();
        }
        return interestPaid;
    }

    public void setInterestPaid(StringFilter interestPaid) {
        this.interestPaid = interestPaid;
    }

    public StringFilter getPenalInterestPaid() {
        return penalInterestPaid;
    }

    public StringFilter penalInterestPaid() {
        if (penalInterestPaid == null) {
            penalInterestPaid = new StringFilter();
        }
        return penalInterestPaid;
    }

    public void setPenalInterestPaid(StringFilter penalInterestPaid) {
        this.penalInterestPaid = penalInterestPaid;
    }

    public StringFilter getDueAmount() {
        return dueAmount;
    }

    public StringFilter dueAmount() {
        if (dueAmount == null) {
            dueAmount = new StringFilter();
        }
        return dueAmount;
    }

    public void setDueAmount(StringFilter dueAmount) {
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

    public StringFilter getDueInterest() {
        return dueInterest;
    }

    public StringFilter dueInterest() {
        if (dueInterest == null) {
            dueInterest = new StringFilter();
        }
        return dueInterest;
    }

    public void setDueInterest(StringFilter dueInterest) {
        this.dueInterest = dueInterest;
    }

    public StringFilter getDuePenalInterest() {
        return duePenalInterest;
    }

    public StringFilter duePenalInterest() {
        if (duePenalInterest == null) {
            duePenalInterest = new StringFilter();
        }
        return duePenalInterest;
    }

    public void setDuePenalInterest(StringFilter duePenalInterest) {
        this.duePenalInterest = duePenalInterest;
    }

    public StringFilter getDueMoreInterest() {
        return dueMoreInterest;
    }

    public StringFilter dueMoreInterest() {
        if (dueMoreInterest == null) {
            dueMoreInterest = new StringFilter();
        }
        return dueMoreInterest;
    }

    public void setDueMoreInterest(StringFilter dueMoreInterest) {
        this.dueMoreInterest = dueMoreInterest;
    }

    public StringFilter getInterestRecivable() {
        return interestRecivable;
    }

    public StringFilter interestRecivable() {
        if (interestRecivable == null) {
            interestRecivable = new StringFilter();
        }
        return interestRecivable;
    }

    public void setInterestRecivable(StringFilter interestRecivable) {
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
            Objects.equals(talukaName, that.talukaName) &&
            Objects.equals(bankName, that.bankName) &&
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
            talukaName,
            bankName,
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
            (talukaName != null ? "talukaName=" + talukaName + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
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
