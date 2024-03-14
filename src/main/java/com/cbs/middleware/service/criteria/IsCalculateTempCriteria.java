package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.IsCalculateTemp} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.IsCalculateTempResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /is-calculate-temps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IsCalculateTempCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter serialNo;

    private StringFilter financialYear;

    private LongFilter issFileParserId;

    private StringFilter branchCode;

    private StringFilter loanAccountNumberKcc;

    private StringFilter farmerName;

    private StringFilter gender;

    private StringFilter aadharNumber;

    private StringFilter mobileNo;

    private StringFilter farmerType;

    private StringFilter socialCategory;

    private StringFilter accountNumber;

    private StringFilter loanSanctionDate;

    private StringFilter loanSanctionAmount;

    private StringFilter disbursementDate;

    private StringFilter disburseAmount;

    private StringFilter maturityLoanDate;

    private StringFilter bankDate;

    private StringFilter cropName;

    private StringFilter recoveryAmount;

    private StringFilter recoveryInterest;

    private StringFilter recoveryDate;

    private StringFilter balanceAmount;

    private LongFilter prevDays;

    private LongFilter presDays;

    private LongFilter actualDays;

    private IntegerFilter nProd;

    private StringFilter productAmount;

    private StringFilter productBank;

    private StringFilter productAbh3Lakh;

    private DoubleFilter interestFirst15;

    private DoubleFilter interestFirst25;

    private DoubleFilter interestSecond15;

    private DoubleFilter interestSecond25;

    private DoubleFilter interestStateFirst3;

    private DoubleFilter interestStateSecond3;

    private DoubleFilter interestFirstAbh3;

    private DoubleFilter interestSecondAbh3;

    private DoubleFilter interestAbove3Lakh;

    private DoubleFilter panjabraoInt3;

    private IntegerFilter isRecover;

    private LongFilter abh3LakhAmt;

    private IntegerFilter upto50000;

    private Boolean distinct;

    public IsCalculateTempCriteria() {}

    public IsCalculateTempCriteria(IsCalculateTempCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serialNo = other.serialNo == null ? null : other.serialNo.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.issFileParserId = other.issFileParserId == null ? null : other.issFileParserId.copy();
        this.branchCode = other.branchCode == null ? null : other.branchCode.copy();
        this.loanAccountNumberKcc = other.loanAccountNumberKcc == null ? null : other.loanAccountNumberKcc.copy();
        this.farmerName = other.farmerName == null ? null : other.farmerName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.aadharNumber = other.aadharNumber == null ? null : other.aadharNumber.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.farmerType = other.farmerType == null ? null : other.farmerType.copy();
        this.socialCategory = other.socialCategory == null ? null : other.socialCategory.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.loanSanctionDate = other.loanSanctionDate == null ? null : other.loanSanctionDate.copy();
        this.loanSanctionAmount = other.loanSanctionAmount == null ? null : other.loanSanctionAmount.copy();
        this.disbursementDate = other.disbursementDate == null ? null : other.disbursementDate.copy();
        this.disburseAmount = other.disburseAmount == null ? null : other.disburseAmount.copy();
        this.maturityLoanDate = other.maturityLoanDate == null ? null : other.maturityLoanDate.copy();
        this.bankDate = other.bankDate == null ? null : other.bankDate.copy();
        this.cropName = other.cropName == null ? null : other.cropName.copy();
        this.recoveryAmount = other.recoveryAmount == null ? null : other.recoveryAmount.copy();
        this.recoveryInterest = other.recoveryInterest == null ? null : other.recoveryInterest.copy();
        this.recoveryDate = other.recoveryDate == null ? null : other.recoveryDate.copy();
        this.balanceAmount = other.balanceAmount == null ? null : other.balanceAmount.copy();
        this.prevDays = other.prevDays == null ? null : other.prevDays.copy();
        this.presDays = other.presDays == null ? null : other.presDays.copy();
        this.actualDays = other.actualDays == null ? null : other.actualDays.copy();
        this.nProd = other.nProd == null ? null : other.nProd.copy();
        this.productAmount = other.productAmount == null ? null : other.productAmount.copy();
        this.productBank = other.productBank == null ? null : other.productBank.copy();
        this.productAbh3Lakh = other.productAbh3Lakh == null ? null : other.productAbh3Lakh.copy();
        this.interestFirst15 = other.interestFirst15 == null ? null : other.interestFirst15.copy();
        this.interestFirst25 = other.interestFirst25 == null ? null : other.interestFirst25.copy();
        this.interestSecond15 = other.interestSecond15 == null ? null : other.interestSecond15.copy();
        this.interestSecond25 = other.interestSecond25 == null ? null : other.interestSecond25.copy();
        this.interestStateFirst3 = other.interestStateFirst3 == null ? null : other.interestStateFirst3.copy();
        this.interestStateSecond3 = other.interestStateSecond3 == null ? null : other.interestStateSecond3.copy();
        this.interestFirstAbh3 = other.interestFirstAbh3 == null ? null : other.interestFirstAbh3.copy();
        this.interestSecondAbh3 = other.interestSecondAbh3 == null ? null : other.interestSecondAbh3.copy();
        this.interestAbove3Lakh = other.interestAbove3Lakh == null ? null : other.interestAbove3Lakh.copy();
        this.panjabraoInt3 = other.panjabraoInt3 == null ? null : other.panjabraoInt3.copy();
        this.isRecover = other.isRecover == null ? null : other.isRecover.copy();
        this.abh3LakhAmt = other.abh3LakhAmt == null ? null : other.abh3LakhAmt.copy();
        this.upto50000 = other.upto50000 == null ? null : other.upto50000.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IsCalculateTempCriteria copy() {
        return new IsCalculateTempCriteria(this);
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

    public IntegerFilter getSerialNo() {
        return serialNo;
    }

    public IntegerFilter serialNo() {
        if (serialNo == null) {
            serialNo = new IntegerFilter();
        }
        return serialNo;
    }

    public void setSerialNo(IntegerFilter serialNo) {
        this.serialNo = serialNo;
    }

    public StringFilter getFinancialYear() {
        return financialYear;
    }

    public StringFilter financialYear() {
        if (financialYear == null) {
            financialYear = new StringFilter();
        }
        return financialYear;
    }

    public void setFinancialYear(StringFilter financialYear) {
        this.financialYear = financialYear;
    }

    public LongFilter getIssFileParserId() {
        return issFileParserId;
    }

    public LongFilter issFileParserId() {
        if (issFileParserId == null) {
            issFileParserId = new LongFilter();
        }
        return issFileParserId;
    }

    public void setIssFileParserId(LongFilter issFileParserId) {
        this.issFileParserId = issFileParserId;
    }

    public StringFilter getBranchCode() {
        return branchCode;
    }

    public StringFilter branchCode() {
        if (branchCode == null) {
            branchCode = new StringFilter();
        }
        return branchCode;
    }

    public void setBranchCode(StringFilter branchCode) {
        this.branchCode = branchCode;
    }

    public StringFilter getLoanAccountNumberKcc() {
        return loanAccountNumberKcc;
    }

    public StringFilter loanAccountNumberKcc() {
        if (loanAccountNumberKcc == null) {
            loanAccountNumberKcc = new StringFilter();
        }
        return loanAccountNumberKcc;
    }

    public void setLoanAccountNumberKcc(StringFilter loanAccountNumberKcc) {
        this.loanAccountNumberKcc = loanAccountNumberKcc;
    }

    public StringFilter getFarmerName() {
        return farmerName;
    }

    public StringFilter farmerName() {
        if (farmerName == null) {
            farmerName = new StringFilter();
        }
        return farmerName;
    }

    public void setFarmerName(StringFilter farmerName) {
        this.farmerName = farmerName;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getAadharNumber() {
        return aadharNumber;
    }

    public StringFilter aadharNumber() {
        if (aadharNumber == null) {
            aadharNumber = new StringFilter();
        }
        return aadharNumber;
    }

    public void setAadharNumber(StringFilter aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getFarmerType() {
        return farmerType;
    }

    public StringFilter farmerType() {
        if (farmerType == null) {
            farmerType = new StringFilter();
        }
        return farmerType;
    }

    public void setFarmerType(StringFilter farmerType) {
        this.farmerType = farmerType;
    }

    public StringFilter getSocialCategory() {
        return socialCategory;
    }

    public StringFilter socialCategory() {
        if (socialCategory == null) {
            socialCategory = new StringFilter();
        }
        return socialCategory;
    }

    public void setSocialCategory(StringFilter socialCategory) {
        this.socialCategory = socialCategory;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public StringFilter accountNumber() {
        if (accountNumber == null) {
            accountNumber = new StringFilter();
        }
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getLoanSanctionDate() {
        return loanSanctionDate;
    }

    public StringFilter loanSanctionDate() {
        if (loanSanctionDate == null) {
            loanSanctionDate = new StringFilter();
        }
        return loanSanctionDate;
    }

    public void setLoanSanctionDate(StringFilter loanSanctionDate) {
        this.loanSanctionDate = loanSanctionDate;
    }

    public StringFilter getLoanSanctionAmount() {
        return loanSanctionAmount;
    }

    public StringFilter loanSanctionAmount() {
        if (loanSanctionAmount == null) {
            loanSanctionAmount = new StringFilter();
        }
        return loanSanctionAmount;
    }

    public void setLoanSanctionAmount(StringFilter loanSanctionAmount) {
        this.loanSanctionAmount = loanSanctionAmount;
    }

    public StringFilter getDisbursementDate() {
        return disbursementDate;
    }

    public StringFilter disbursementDate() {
        if (disbursementDate == null) {
            disbursementDate = new StringFilter();
        }
        return disbursementDate;
    }

    public void setDisbursementDate(StringFilter disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public StringFilter getDisburseAmount() {
        return disburseAmount;
    }

    public StringFilter disburseAmount() {
        if (disburseAmount == null) {
            disburseAmount = new StringFilter();
        }
        return disburseAmount;
    }

    public void setDisburseAmount(StringFilter disburseAmount) {
        this.disburseAmount = disburseAmount;
    }

    public StringFilter getMaturityLoanDate() {
        return maturityLoanDate;
    }

    public StringFilter maturityLoanDate() {
        if (maturityLoanDate == null) {
            maturityLoanDate = new StringFilter();
        }
        return maturityLoanDate;
    }

    public void setMaturityLoanDate(StringFilter maturityLoanDate) {
        this.maturityLoanDate = maturityLoanDate;
    }

    public StringFilter getBankDate() {
        return bankDate;
    }

    public StringFilter bankDate() {
        if (bankDate == null) {
            bankDate = new StringFilter();
        }
        return bankDate;
    }

    public void setBankDate(StringFilter bankDate) {
        this.bankDate = bankDate;
    }

    public StringFilter getCropName() {
        return cropName;
    }

    public StringFilter cropName() {
        if (cropName == null) {
            cropName = new StringFilter();
        }
        return cropName;
    }

    public void setCropName(StringFilter cropName) {
        this.cropName = cropName;
    }

    public StringFilter getRecoveryAmount() {
        return recoveryAmount;
    }

    public StringFilter recoveryAmount() {
        if (recoveryAmount == null) {
            recoveryAmount = new StringFilter();
        }
        return recoveryAmount;
    }

    public void setRecoveryAmount(StringFilter recoveryAmount) {
        this.recoveryAmount = recoveryAmount;
    }

    public StringFilter getRecoveryInterest() {
        return recoveryInterest;
    }

    public StringFilter recoveryInterest() {
        if (recoveryInterest == null) {
            recoveryInterest = new StringFilter();
        }
        return recoveryInterest;
    }

    public void setRecoveryInterest(StringFilter recoveryInterest) {
        this.recoveryInterest = recoveryInterest;
    }

    public StringFilter getRecoveryDate() {
        return recoveryDate;
    }

    public StringFilter recoveryDate() {
        if (recoveryDate == null) {
            recoveryDate = new StringFilter();
        }
        return recoveryDate;
    }

    public void setRecoveryDate(StringFilter recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public StringFilter getBalanceAmount() {
        return balanceAmount;
    }

    public StringFilter balanceAmount() {
        if (balanceAmount == null) {
            balanceAmount = new StringFilter();
        }
        return balanceAmount;
    }

    public void setBalanceAmount(StringFilter balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public LongFilter getPrevDays() {
        return prevDays;
    }

    public LongFilter prevDays() {
        if (prevDays == null) {
            prevDays = new LongFilter();
        }
        return prevDays;
    }

    public void setPrevDays(LongFilter prevDays) {
        this.prevDays = prevDays;
    }

    public LongFilter getPresDays() {
        return presDays;
    }

    public LongFilter presDays() {
        if (presDays == null) {
            presDays = new LongFilter();
        }
        return presDays;
    }

    public void setPresDays(LongFilter presDays) {
        this.presDays = presDays;
    }

    public LongFilter getActualDays() {
        return actualDays;
    }

    public LongFilter actualDays() {
        if (actualDays == null) {
            actualDays = new LongFilter();
        }
        return actualDays;
    }

    public void setActualDays(LongFilter actualDays) {
        this.actualDays = actualDays;
    }

    public IntegerFilter getnProd() {
        return nProd;
    }

    public IntegerFilter nProd() {
        if (nProd == null) {
            nProd = new IntegerFilter();
        }
        return nProd;
    }

    public void setnProd(IntegerFilter nProd) {
        this.nProd = nProd;
    }

    public StringFilter getProductAmount() {
        return productAmount;
    }

    public StringFilter productAmount() {
        if (productAmount == null) {
            productAmount = new StringFilter();
        }
        return productAmount;
    }

    public void setProductAmount(StringFilter productAmount) {
        this.productAmount = productAmount;
    }

    public StringFilter getProductBank() {
        return productBank;
    }

    public StringFilter productBank() {
        if (productBank == null) {
            productBank = new StringFilter();
        }
        return productBank;
    }

    public void setProductBank(StringFilter productBank) {
        this.productBank = productBank;
    }

    public StringFilter getProductAbh3Lakh() {
        return productAbh3Lakh;
    }

    public StringFilter productAbh3Lakh() {
        if (productAbh3Lakh == null) {
            productAbh3Lakh = new StringFilter();
        }
        return productAbh3Lakh;
    }

    public void setProductAbh3Lakh(StringFilter productAbh3Lakh) {
        this.productAbh3Lakh = productAbh3Lakh;
    }

    public DoubleFilter getInterestFirst15() {
        return interestFirst15;
    }

    public DoubleFilter interestFirst15() {
        if (interestFirst15 == null) {
            interestFirst15 = new DoubleFilter();
        }
        return interestFirst15;
    }

    public void setInterestFirst15(DoubleFilter interestFirst15) {
        this.interestFirst15 = interestFirst15;
    }

    public DoubleFilter getInterestFirst25() {
        return interestFirst25;
    }

    public DoubleFilter interestFirst25() {
        if (interestFirst25 == null) {
            interestFirst25 = new DoubleFilter();
        }
        return interestFirst25;
    }

    public void setInterestFirst25(DoubleFilter interestFirst25) {
        this.interestFirst25 = interestFirst25;
    }

    public DoubleFilter getInterestSecond15() {
        return interestSecond15;
    }

    public DoubleFilter interestSecond15() {
        if (interestSecond15 == null) {
            interestSecond15 = new DoubleFilter();
        }
        return interestSecond15;
    }

    public void setInterestSecond15(DoubleFilter interestSecond15) {
        this.interestSecond15 = interestSecond15;
    }

    public DoubleFilter getInterestSecond25() {
        return interestSecond25;
    }

    public DoubleFilter interestSecond25() {
        if (interestSecond25 == null) {
            interestSecond25 = new DoubleFilter();
        }
        return interestSecond25;
    }

    public void setInterestSecond25(DoubleFilter interestSecond25) {
        this.interestSecond25 = interestSecond25;
    }

    public DoubleFilter getInterestStateFirst3() {
        return interestStateFirst3;
    }

    public DoubleFilter interestStateFirst3() {
        if (interestStateFirst3 == null) {
            interestStateFirst3 = new DoubleFilter();
        }
        return interestStateFirst3;
    }

    public void setInterestStateFirst3(DoubleFilter interestStateFirst3) {
        this.interestStateFirst3 = interestStateFirst3;
    }

    public DoubleFilter getInterestStateSecond3() {
        return interestStateSecond3;
    }

    public DoubleFilter interestStateSecond3() {
        if (interestStateSecond3 == null) {
            interestStateSecond3 = new DoubleFilter();
        }
        return interestStateSecond3;
    }

    public void setInterestStateSecond3(DoubleFilter interestStateSecond3) {
        this.interestStateSecond3 = interestStateSecond3;
    }

    public DoubleFilter getInterestFirstAbh3() {
        return interestFirstAbh3;
    }

    public DoubleFilter interestFirstAbh3() {
        if (interestFirstAbh3 == null) {
            interestFirstAbh3 = new DoubleFilter();
        }
        return interestFirstAbh3;
    }

    public void setInterestFirstAbh3(DoubleFilter interestFirstAbh3) {
        this.interestFirstAbh3 = interestFirstAbh3;
    }

    public DoubleFilter getInterestSecondAbh3() {
        return interestSecondAbh3;
    }

    public DoubleFilter interestSecondAbh3() {
        if (interestSecondAbh3 == null) {
            interestSecondAbh3 = new DoubleFilter();
        }
        return interestSecondAbh3;
    }

    public void setInterestSecondAbh3(DoubleFilter interestSecondAbh3) {
        this.interestSecondAbh3 = interestSecondAbh3;
    }

    public DoubleFilter getInterestAbove3Lakh() {
        return interestAbove3Lakh;
    }

    public DoubleFilter interestAbove3Lakh() {
        if (interestAbove3Lakh == null) {
            interestAbove3Lakh = new DoubleFilter();
        }
        return interestAbove3Lakh;
    }

    public void setInterestAbove3Lakh(DoubleFilter interestAbove3Lakh) {
        this.interestAbove3Lakh = interestAbove3Lakh;
    }

    public DoubleFilter getPanjabraoInt3() {
        return panjabraoInt3;
    }

    public DoubleFilter panjabraoInt3() {
        if (panjabraoInt3 == null) {
            panjabraoInt3 = new DoubleFilter();
        }
        return panjabraoInt3;
    }

    public void setPanjabraoInt3(DoubleFilter panjabraoInt3) {
        this.panjabraoInt3 = panjabraoInt3;
    }

    public IntegerFilter getIsRecover() {
        return isRecover;
    }

    public IntegerFilter isRecover() {
        if (isRecover == null) {
            isRecover = new IntegerFilter();
        }
        return isRecover;
    }

    public void setIsRecover(IntegerFilter isRecover) {
        this.isRecover = isRecover;
    }

    public LongFilter getAbh3LakhAmt() {
        return abh3LakhAmt;
    }

    public LongFilter abh3LakhAmt() {
        if (abh3LakhAmt == null) {
            abh3LakhAmt = new LongFilter();
        }
        return abh3LakhAmt;
    }

    public void setAbh3LakhAmt(LongFilter abh3LakhAmt) {
        this.abh3LakhAmt = abh3LakhAmt;
    }

    public IntegerFilter getUpto50000() {
        return upto50000;
    }

    public IntegerFilter upto50000() {
        if (upto50000 == null) {
            upto50000 = new IntegerFilter();
        }
        return upto50000;
    }

    public void setUpto50000(IntegerFilter upto50000) {
        this.upto50000 = upto50000;
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
        final IsCalculateTempCriteria that = (IsCalculateTempCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(issFileParserId, that.issFileParserId) &&
            Objects.equals(branchCode, that.branchCode) &&
            Objects.equals(loanAccountNumberKcc, that.loanAccountNumberKcc) &&
            Objects.equals(farmerName, that.farmerName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(aadharNumber, that.aadharNumber) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(farmerType, that.farmerType) &&
            Objects.equals(socialCategory, that.socialCategory) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(loanSanctionDate, that.loanSanctionDate) &&
            Objects.equals(loanSanctionAmount, that.loanSanctionAmount) &&
            Objects.equals(disbursementDate, that.disbursementDate) &&
            Objects.equals(disburseAmount, that.disburseAmount) &&
            Objects.equals(maturityLoanDate, that.maturityLoanDate) &&
            Objects.equals(bankDate, that.bankDate) &&
            Objects.equals(cropName, that.cropName) &&
            Objects.equals(recoveryAmount, that.recoveryAmount) &&
            Objects.equals(recoveryInterest, that.recoveryInterest) &&
            Objects.equals(recoveryDate, that.recoveryDate) &&
            Objects.equals(balanceAmount, that.balanceAmount) &&
            Objects.equals(prevDays, that.prevDays) &&
            Objects.equals(presDays, that.presDays) &&
            Objects.equals(actualDays, that.actualDays) &&
            Objects.equals(nProd, that.nProd) &&
            Objects.equals(productAmount, that.productAmount) &&
            Objects.equals(productBank, that.productBank) &&
            Objects.equals(productAbh3Lakh, that.productAbh3Lakh) &&
            Objects.equals(interestFirst15, that.interestFirst15) &&
            Objects.equals(interestFirst25, that.interestFirst25) &&
            Objects.equals(interestSecond15, that.interestSecond15) &&
            Objects.equals(interestSecond25, that.interestSecond25) &&
            Objects.equals(interestStateFirst3, that.interestStateFirst3) &&
            Objects.equals(interestStateSecond3, that.interestStateSecond3) &&
            Objects.equals(interestFirstAbh3, that.interestFirstAbh3) &&
            Objects.equals(interestSecondAbh3, that.interestSecondAbh3) &&
            Objects.equals(interestAbove3Lakh, that.interestAbove3Lakh) &&
            Objects.equals(panjabraoInt3, that.panjabraoInt3) &&
            Objects.equals(isRecover, that.isRecover) &&
            Objects.equals(abh3LakhAmt, that.abh3LakhAmt) &&
            Objects.equals(upto50000, that.upto50000) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            serialNo,
            financialYear,
            issFileParserId,
            branchCode,
            loanAccountNumberKcc,
            farmerName,
            gender,
            aadharNumber,
            mobileNo,
            farmerType,
            socialCategory,
            accountNumber,
            loanSanctionDate,
            loanSanctionAmount,
            disbursementDate,
            disburseAmount,
            maturityLoanDate,
            bankDate,
            cropName,
            recoveryAmount,
            recoveryInterest,
            recoveryDate,
            balanceAmount,
            prevDays,
            presDays,
            actualDays,
            nProd,
            productAmount,
            productBank,
            productAbh3Lakh,
            interestFirst15,
            interestFirst25,
            interestSecond15,
            interestSecond25,
            interestStateFirst3,
            interestStateSecond3,
            interestFirstAbh3,
            interestSecondAbh3,
            interestAbove3Lakh,
            panjabraoInt3,
            isRecover,
            abh3LakhAmt,
            upto50000,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsCalculateTempCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (serialNo != null ? "serialNo=" + serialNo + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (issFileParserId != null ? "issFileParserId=" + issFileParserId + ", " : "") +
            (branchCode != null ? "branchCode=" + branchCode + ", " : "") +
            (loanAccountNumberKcc != null ? "loanAccountNumberKcc=" + loanAccountNumberKcc + ", " : "") +
            (farmerName != null ? "farmerName=" + farmerName + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (aadharNumber != null ? "aadharNumber=" + aadharNumber + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (farmerType != null ? "farmerType=" + farmerType + ", " : "") +
            (socialCategory != null ? "socialCategory=" + socialCategory + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (loanSanctionDate != null ? "loanSanctionDate=" + loanSanctionDate + ", " : "") +
            (loanSanctionAmount != null ? "loanSanctionAmount=" + loanSanctionAmount + ", " : "") +
            (disbursementDate != null ? "disbursementDate=" + disbursementDate + ", " : "") +
            (disburseAmount != null ? "disburseAmount=" + disburseAmount + ", " : "") +
            (maturityLoanDate != null ? "maturityLoanDate=" + maturityLoanDate + ", " : "") +
            (bankDate != null ? "bankDate=" + bankDate + ", " : "") +
            (cropName != null ? "cropName=" + cropName + ", " : "") +
            (recoveryAmount != null ? "recoveryAmount=" + recoveryAmount + ", " : "") +
            (recoveryInterest != null ? "recoveryInterest=" + recoveryInterest + ", " : "") +
            (recoveryDate != null ? "recoveryDate=" + recoveryDate + ", " : "") +
            (balanceAmount != null ? "balanceAmount=" + balanceAmount + ", " : "") +
            (prevDays != null ? "prevDays=" + prevDays + ", " : "") +
            (presDays != null ? "presDays=" + presDays + ", " : "") +
            (actualDays != null ? "actualDays=" + actualDays + ", " : "") +
            (nProd != null ? "nProd=" + nProd + ", " : "") +
            (productAmount != null ? "productAmount=" + productAmount + ", " : "") +
            (productBank != null ? "productBank=" + productBank + ", " : "") +
            (productAbh3Lakh != null ? "productAbh3Lakh=" + productAbh3Lakh + ", " : "") +
            (interestFirst15 != null ? "interestFirst15=" + interestFirst15 + ", " : "") +
            (interestFirst25 != null ? "interestFirst25=" + interestFirst25 + ", " : "") +
            (interestSecond15 != null ? "interestSecond15=" + interestSecond15 + ", " : "") +
            (interestSecond25 != null ? "interestSecond25=" + interestSecond25 + ", " : "") +
            (interestStateFirst3 != null ? "interestStateFirst3=" + interestStateFirst3 + ", " : "") +
            (interestStateSecond3 != null ? "interestStateSecond3=" + interestStateSecond3 + ", " : "") +
            (interestFirstAbh3 != null ? "interestFirstAbh3=" + interestFirstAbh3 + ", " : "") +
            (interestSecondAbh3 != null ? "interestSecondAbh3=" + interestSecondAbh3 + ", " : "") +
            (interestAbove3Lakh != null ? "interestAbove3Lakh=" + interestAbove3Lakh + ", " : "") +
            (panjabraoInt3 != null ? "panjabraoInt3=" + panjabraoInt3 + ", " : "") +
            (isRecover != null ? "isRecover=" + isRecover + ", " : "") +
            (abh3LakhAmt != null ? "abh3LakhAmt=" + abh3LakhAmt + ", " : "") +
            (upto50000 != null ? "upto50000=" + upto50000 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
