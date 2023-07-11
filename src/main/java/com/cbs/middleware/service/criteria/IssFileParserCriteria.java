package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.IssFileParser} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.IssFileParserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /iss-file-parsers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssFileParserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter financialYear;

    private StringFilter bankName;

    private StringFilter bankCode;

    private StringFilter branchName;

    private StringFilter branchCode;

    private StringFilter schemeWiseBranchCode;

    private StringFilter ifsc;

    private StringFilter loanAccountNumberkcc;

    private StringFilter farmerName;

    private StringFilter gender;

    private StringFilter aadharNumber;

    private StringFilter dateofBirth;

    private StringFilter ageAtTimeOfSanction;

    private StringFilter mobileNo;

    private StringFilter farmersCategory;

    private StringFilter farmerType;

    private StringFilter socialCategory;

    private StringFilter relativeType;

    private StringFilter relativeName;

    private StringFilter stateName;

    private StringFilter stateCode;

    private StringFilter districtName;

    private StringFilter districtCode;

    private StringFilter blockCode;

    private StringFilter blockName;

    private StringFilter villageCode;

    private StringFilter villageName;

    private StringFilter address;

    private StringFilter pinCode;

    private StringFilter accountType;

    private StringFilter accountNumber;

    private StringFilter pacsName;

    private StringFilter pacsNumber;

    private StringFilter accountHolderType;

    private StringFilter primaryOccupation;

    private StringFilter loanSactionDate;

    private StringFilter loanSanctionAmount;

    private StringFilter tenureOFLoan;

    private StringFilter dateOfOverDuePayment;

    private StringFilter cropName;

    private StringFilter surveyNo;

    private StringFilter satBaraSubsurveyNo;

    private StringFilter seasonName;

    private StringFilter areaHect;

    private StringFilter landType;

    private StringFilter disbursementDate;

    private StringFilter disburseAmount;

    private StringFilter maturityLoanDate;

    private StringFilter recoveryAmountPrinciple;

    private StringFilter recoveryAmountInterest;

    private StringFilter recoveryDate;

    private LongFilter issPortalFileId;

    private Boolean distinct;

    public IssFileParserCriteria() {}

    public IssFileParserCriteria(IssFileParserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.bankCode = other.bankCode == null ? null : other.bankCode.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.branchCode = other.branchCode == null ? null : other.branchCode.copy();
        this.schemeWiseBranchCode = other.schemeWiseBranchCode == null ? null : other.schemeWiseBranchCode.copy();
        this.ifsc = other.ifsc == null ? null : other.ifsc.copy();
        this.loanAccountNumberkcc = other.loanAccountNumberkcc == null ? null : other.loanAccountNumberkcc.copy();
        this.farmerName = other.farmerName == null ? null : other.farmerName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.aadharNumber = other.aadharNumber == null ? null : other.aadharNumber.copy();
        this.dateofBirth = other.dateofBirth == null ? null : other.dateofBirth.copy();
        this.ageAtTimeOfSanction = other.ageAtTimeOfSanction == null ? null : other.ageAtTimeOfSanction.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.farmersCategory = other.farmersCategory == null ? null : other.farmersCategory.copy();
        this.farmerType = other.farmerType == null ? null : other.farmerType.copy();
        this.socialCategory = other.socialCategory == null ? null : other.socialCategory.copy();
        this.relativeType = other.relativeType == null ? null : other.relativeType.copy();
        this.relativeName = other.relativeName == null ? null : other.relativeName.copy();
        this.stateName = other.stateName == null ? null : other.stateName.copy();
        this.stateCode = other.stateCode == null ? null : other.stateCode.copy();
        this.districtName = other.districtName == null ? null : other.districtName.copy();
        this.districtCode = other.districtCode == null ? null : other.districtCode.copy();
        this.blockCode = other.blockCode == null ? null : other.blockCode.copy();
        this.blockName = other.blockName == null ? null : other.blockName.copy();
        this.villageCode = other.villageCode == null ? null : other.villageCode.copy();
        this.villageName = other.villageName == null ? null : other.villageName.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
        this.accountType = other.accountType == null ? null : other.accountType.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.pacsName = other.pacsName == null ? null : other.pacsName.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.accountHolderType = other.accountHolderType == null ? null : other.accountHolderType.copy();
        this.primaryOccupation = other.primaryOccupation == null ? null : other.primaryOccupation.copy();
        this.loanSactionDate = other.loanSactionDate == null ? null : other.loanSactionDate.copy();
        this.loanSanctionAmount = other.loanSanctionAmount == null ? null : other.loanSanctionAmount.copy();
        this.tenureOFLoan = other.tenureOFLoan == null ? null : other.tenureOFLoan.copy();
        this.dateOfOverDuePayment = other.dateOfOverDuePayment == null ? null : other.dateOfOverDuePayment.copy();
        this.cropName = other.cropName == null ? null : other.cropName.copy();
        this.surveyNo = other.surveyNo == null ? null : other.surveyNo.copy();
        this.satBaraSubsurveyNo = other.satBaraSubsurveyNo == null ? null : other.satBaraSubsurveyNo.copy();
        this.seasonName = other.seasonName == null ? null : other.seasonName.copy();
        this.areaHect = other.areaHect == null ? null : other.areaHect.copy();
        this.landType = other.landType == null ? null : other.landType.copy();
        this.disbursementDate = other.disbursementDate == null ? null : other.disbursementDate.copy();
        this.disburseAmount = other.disburseAmount == null ? null : other.disburseAmount.copy();
        this.maturityLoanDate = other.maturityLoanDate == null ? null : other.maturityLoanDate.copy();
        this.recoveryAmountPrinciple = other.recoveryAmountPrinciple == null ? null : other.recoveryAmountPrinciple.copy();
        this.recoveryAmountInterest = other.recoveryAmountInterest == null ? null : other.recoveryAmountInterest.copy();
        this.recoveryDate = other.recoveryDate == null ? null : other.recoveryDate.copy();
        this.issPortalFileId = other.issPortalFileId == null ? null : other.issPortalFileId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IssFileParserCriteria copy() {
        return new IssFileParserCriteria(this);
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

    public StringFilter getBankCode() {
        return bankCode;
    }

    public StringFilter bankCode() {
        if (bankCode == null) {
            bankCode = new StringFilter();
        }
        return bankCode;
    }

    public void setBankCode(StringFilter bankCode) {
        this.bankCode = bankCode;
    }

    public StringFilter getBranchName() {
        return branchName;
    }

    public StringFilter branchName() {
        if (branchName == null) {
            branchName = new StringFilter();
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
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

    public StringFilter getSchemeWiseBranchCode() {
        return schemeWiseBranchCode;
    }

    public StringFilter schemeWiseBranchCode() {
        if (schemeWiseBranchCode == null) {
            schemeWiseBranchCode = new StringFilter();
        }
        return schemeWiseBranchCode;
    }

    public void setSchemeWiseBranchCode(StringFilter schemeWiseBranchCode) {
        this.schemeWiseBranchCode = schemeWiseBranchCode;
    }

    public StringFilter getIfsc() {
        return ifsc;
    }

    public StringFilter ifsc() {
        if (ifsc == null) {
            ifsc = new StringFilter();
        }
        return ifsc;
    }

    public void setIfsc(StringFilter ifsc) {
        this.ifsc = ifsc;
    }

    public StringFilter getLoanAccountNumberkcc() {
        return loanAccountNumberkcc;
    }

    public StringFilter loanAccountNumberkcc() {
        if (loanAccountNumberkcc == null) {
            loanAccountNumberkcc = new StringFilter();
        }
        return loanAccountNumberkcc;
    }

    public void setLoanAccountNumberkcc(StringFilter loanAccountNumberkcc) {
        this.loanAccountNumberkcc = loanAccountNumberkcc;
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

    public StringFilter getDateofBirth() {
        return dateofBirth;
    }

    public StringFilter dateofBirth() {
        if (dateofBirth == null) {
            dateofBirth = new StringFilter();
        }
        return dateofBirth;
    }

    public void setDateofBirth(StringFilter dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public StringFilter getAgeAtTimeOfSanction() {
        return ageAtTimeOfSanction;
    }

    public StringFilter ageAtTimeOfSanction() {
        if (ageAtTimeOfSanction == null) {
            ageAtTimeOfSanction = new StringFilter();
        }
        return ageAtTimeOfSanction;
    }

    public void setAgeAtTimeOfSanction(StringFilter ageAtTimeOfSanction) {
        this.ageAtTimeOfSanction = ageAtTimeOfSanction;
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

    public StringFilter getFarmersCategory() {
        return farmersCategory;
    }

    public StringFilter farmersCategory() {
        if (farmersCategory == null) {
            farmersCategory = new StringFilter();
        }
        return farmersCategory;
    }

    public void setFarmersCategory(StringFilter farmersCategory) {
        this.farmersCategory = farmersCategory;
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

    public StringFilter getRelativeType() {
        return relativeType;
    }

    public StringFilter relativeType() {
        if (relativeType == null) {
            relativeType = new StringFilter();
        }
        return relativeType;
    }

    public void setRelativeType(StringFilter relativeType) {
        this.relativeType = relativeType;
    }

    public StringFilter getRelativeName() {
        return relativeName;
    }

    public StringFilter relativeName() {
        if (relativeName == null) {
            relativeName = new StringFilter();
        }
        return relativeName;
    }

    public void setRelativeName(StringFilter relativeName) {
        this.relativeName = relativeName;
    }

    public StringFilter getStateName() {
        return stateName;
    }

    public StringFilter stateName() {
        if (stateName == null) {
            stateName = new StringFilter();
        }
        return stateName;
    }

    public void setStateName(StringFilter stateName) {
        this.stateName = stateName;
    }

    public StringFilter getStateCode() {
        return stateCode;
    }

    public StringFilter stateCode() {
        if (stateCode == null) {
            stateCode = new StringFilter();
        }
        return stateCode;
    }

    public void setStateCode(StringFilter stateCode) {
        this.stateCode = stateCode;
    }

    public StringFilter getDistrictName() {
        return districtName;
    }

    public StringFilter districtName() {
        if (districtName == null) {
            districtName = new StringFilter();
        }
        return districtName;
    }

    public void setDistrictName(StringFilter districtName) {
        this.districtName = districtName;
    }

    public StringFilter getDistrictCode() {
        return districtCode;
    }

    public StringFilter districtCode() {
        if (districtCode == null) {
            districtCode = new StringFilter();
        }
        return districtCode;
    }

    public void setDistrictCode(StringFilter districtCode) {
        this.districtCode = districtCode;
    }

    public StringFilter getBlockCode() {
        return blockCode;
    }

    public StringFilter blockCode() {
        if (blockCode == null) {
            blockCode = new StringFilter();
        }
        return blockCode;
    }

    public void setBlockCode(StringFilter blockCode) {
        this.blockCode = blockCode;
    }

    public StringFilter getBlockName() {
        return blockName;
    }

    public StringFilter blockName() {
        if (blockName == null) {
            blockName = new StringFilter();
        }
        return blockName;
    }

    public void setBlockName(StringFilter blockName) {
        this.blockName = blockName;
    }

    public StringFilter getVillageCode() {
        return villageCode;
    }

    public StringFilter villageCode() {
        if (villageCode == null) {
            villageCode = new StringFilter();
        }
        return villageCode;
    }

    public void setVillageCode(StringFilter villageCode) {
        this.villageCode = villageCode;
    }

    public StringFilter getVillageName() {
        return villageName;
    }

    public StringFilter villageName() {
        if (villageName == null) {
            villageName = new StringFilter();
        }
        return villageName;
    }

    public void setVillageName(StringFilter villageName) {
        this.villageName = villageName;
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

    public StringFilter getPinCode() {
        return pinCode;
    }

    public StringFilter pinCode() {
        if (pinCode == null) {
            pinCode = new StringFilter();
        }
        return pinCode;
    }

    public void setPinCode(StringFilter pinCode) {
        this.pinCode = pinCode;
    }

    public StringFilter getAccountType() {
        return accountType;
    }

    public StringFilter accountType() {
        if (accountType == null) {
            accountType = new StringFilter();
        }
        return accountType;
    }

    public void setAccountType(StringFilter accountType) {
        this.accountType = accountType;
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

    public StringFilter getPacsName() {
        return pacsName;
    }

    public StringFilter pacsName() {
        if (pacsName == null) {
            pacsName = new StringFilter();
        }
        return pacsName;
    }

    public void setPacsName(StringFilter pacsName) {
        this.pacsName = pacsName;
    }

    public StringFilter getPacsNumber() {
        return pacsNumber;
    }

    public StringFilter pacsNumber() {
        if (pacsNumber == null) {
            pacsNumber = new StringFilter();
        }
        return pacsNumber;
    }

    public void setPacsNumber(StringFilter pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public StringFilter getAccountHolderType() {
        return accountHolderType;
    }

    public StringFilter accountHolderType() {
        if (accountHolderType == null) {
            accountHolderType = new StringFilter();
        }
        return accountHolderType;
    }

    public void setAccountHolderType(StringFilter accountHolderType) {
        this.accountHolderType = accountHolderType;
    }

    public StringFilter getPrimaryOccupation() {
        return primaryOccupation;
    }

    public StringFilter primaryOccupation() {
        if (primaryOccupation == null) {
            primaryOccupation = new StringFilter();
        }
        return primaryOccupation;
    }

    public void setPrimaryOccupation(StringFilter primaryOccupation) {
        this.primaryOccupation = primaryOccupation;
    }

    public StringFilter getLoanSactionDate() {
        return loanSactionDate;
    }

    public StringFilter loanSactionDate() {
        if (loanSactionDate == null) {
            loanSactionDate = new StringFilter();
        }
        return loanSactionDate;
    }

    public void setLoanSactionDate(StringFilter loanSactionDate) {
        this.loanSactionDate = loanSactionDate;
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

    public StringFilter getTenureOFLoan() {
        return tenureOFLoan;
    }

    public StringFilter tenureOFLoan() {
        if (tenureOFLoan == null) {
            tenureOFLoan = new StringFilter();
        }
        return tenureOFLoan;
    }

    public void setTenureOFLoan(StringFilter tenureOFLoan) {
        this.tenureOFLoan = tenureOFLoan;
    }

    public StringFilter getDateOfOverDuePayment() {
        return dateOfOverDuePayment;
    }

    public StringFilter dateOfOverDuePayment() {
        if (dateOfOverDuePayment == null) {
            dateOfOverDuePayment = new StringFilter();
        }
        return dateOfOverDuePayment;
    }

    public void setDateOfOverDuePayment(StringFilter dateOfOverDuePayment) {
        this.dateOfOverDuePayment = dateOfOverDuePayment;
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

    public StringFilter getSurveyNo() {
        return surveyNo;
    }

    public StringFilter surveyNo() {
        if (surveyNo == null) {
            surveyNo = new StringFilter();
        }
        return surveyNo;
    }

    public void setSurveyNo(StringFilter surveyNo) {
        this.surveyNo = surveyNo;
    }

    public StringFilter getSatBaraSubsurveyNo() {
        return satBaraSubsurveyNo;
    }

    public StringFilter satBaraSubsurveyNo() {
        if (satBaraSubsurveyNo == null) {
            satBaraSubsurveyNo = new StringFilter();
        }
        return satBaraSubsurveyNo;
    }

    public void setSatBaraSubsurveyNo(StringFilter satBaraSubsurveyNo) {
        this.satBaraSubsurveyNo = satBaraSubsurveyNo;
    }

    public StringFilter getSeasonName() {
        return seasonName;
    }

    public StringFilter seasonName() {
        if (seasonName == null) {
            seasonName = new StringFilter();
        }
        return seasonName;
    }

    public void setSeasonName(StringFilter seasonName) {
        this.seasonName = seasonName;
    }

    public StringFilter getAreaHect() {
        return areaHect;
    }

    public StringFilter areaHect() {
        if (areaHect == null) {
            areaHect = new StringFilter();
        }
        return areaHect;
    }

    public void setAreaHect(StringFilter areaHect) {
        this.areaHect = areaHect;
    }

    public StringFilter getLandType() {
        return landType;
    }

    public StringFilter landType() {
        if (landType == null) {
            landType = new StringFilter();
        }
        return landType;
    }

    public void setLandType(StringFilter landType) {
        this.landType = landType;
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

    public StringFilter getRecoveryAmountPrinciple() {
        return recoveryAmountPrinciple;
    }

    public StringFilter recoveryAmountPrinciple() {
        if (recoveryAmountPrinciple == null) {
            recoveryAmountPrinciple = new StringFilter();
        }
        return recoveryAmountPrinciple;
    }

    public void setRecoveryAmountPrinciple(StringFilter recoveryAmountPrinciple) {
        this.recoveryAmountPrinciple = recoveryAmountPrinciple;
    }

    public StringFilter getRecoveryAmountInterest() {
        return recoveryAmountInterest;
    }

    public StringFilter recoveryAmountInterest() {
        if (recoveryAmountInterest == null) {
            recoveryAmountInterest = new StringFilter();
        }
        return recoveryAmountInterest;
    }

    public void setRecoveryAmountInterest(StringFilter recoveryAmountInterest) {
        this.recoveryAmountInterest = recoveryAmountInterest;
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

    public LongFilter getIssPortalFileId() {
        return issPortalFileId;
    }

    public LongFilter issPortalFileId() {
        if (issPortalFileId == null) {
            issPortalFileId = new LongFilter();
        }
        return issPortalFileId;
    }

    public void setIssPortalFileId(LongFilter issPortalFileId) {
        this.issPortalFileId = issPortalFileId;
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
        final IssFileParserCriteria that = (IssFileParserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(bankCode, that.bankCode) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(branchCode, that.branchCode) &&
            Objects.equals(schemeWiseBranchCode, that.schemeWiseBranchCode) &&
            Objects.equals(ifsc, that.ifsc) &&
            Objects.equals(loanAccountNumberkcc, that.loanAccountNumberkcc) &&
            Objects.equals(farmerName, that.farmerName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(aadharNumber, that.aadharNumber) &&
            Objects.equals(dateofBirth, that.dateofBirth) &&
            Objects.equals(ageAtTimeOfSanction, that.ageAtTimeOfSanction) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(farmersCategory, that.farmersCategory) &&
            Objects.equals(farmerType, that.farmerType) &&
            Objects.equals(socialCategory, that.socialCategory) &&
            Objects.equals(relativeType, that.relativeType) &&
            Objects.equals(relativeName, that.relativeName) &&
            Objects.equals(stateName, that.stateName) &&
            Objects.equals(stateCode, that.stateCode) &&
            Objects.equals(districtName, that.districtName) &&
            Objects.equals(districtCode, that.districtCode) &&
            Objects.equals(blockCode, that.blockCode) &&
            Objects.equals(blockName, that.blockName) &&
            Objects.equals(villageCode, that.villageCode) &&
            Objects.equals(villageName, that.villageName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(accountType, that.accountType) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(pacsName, that.pacsName) &&
            Objects.equals(pacsNumber, that.pacsNumber) &&
            Objects.equals(accountHolderType, that.accountHolderType) &&
            Objects.equals(primaryOccupation, that.primaryOccupation) &&
            Objects.equals(loanSactionDate, that.loanSactionDate) &&
            Objects.equals(loanSanctionAmount, that.loanSanctionAmount) &&
            Objects.equals(tenureOFLoan, that.tenureOFLoan) &&
            Objects.equals(dateOfOverDuePayment, that.dateOfOverDuePayment) &&
            Objects.equals(cropName, that.cropName) &&
            Objects.equals(surveyNo, that.surveyNo) &&
            Objects.equals(satBaraSubsurveyNo, that.satBaraSubsurveyNo) &&
            Objects.equals(seasonName, that.seasonName) &&
            Objects.equals(areaHect, that.areaHect) &&
            Objects.equals(landType, that.landType) &&
            Objects.equals(disbursementDate, that.disbursementDate) &&
            Objects.equals(disburseAmount, that.disburseAmount) &&
            Objects.equals(maturityLoanDate, that.maturityLoanDate) &&
            Objects.equals(recoveryAmountPrinciple, that.recoveryAmountPrinciple) &&
            Objects.equals(recoveryAmountInterest, that.recoveryAmountInterest) &&
            Objects.equals(recoveryDate, that.recoveryDate) &&
            Objects.equals(issPortalFileId, that.issPortalFileId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            financialYear,
            bankName,
            bankCode,
            branchName,
            branchCode,
            schemeWiseBranchCode,
            ifsc,
            loanAccountNumberkcc,
            farmerName,
            gender,
            aadharNumber,
            dateofBirth,
            ageAtTimeOfSanction,
            mobileNo,
            farmersCategory,
            farmerType,
            socialCategory,
            relativeType,
            relativeName,
            stateName,
            stateCode,
            districtName,
            districtCode,
            blockCode,
            blockName,
            villageCode,
            villageName,
            address,
            pinCode,
            accountType,
            accountNumber,
            pacsName,
            pacsNumber,
            accountHolderType,
            primaryOccupation,
            loanSactionDate,
            loanSanctionAmount,
            tenureOFLoan,
            dateOfOverDuePayment,
            cropName,
            surveyNo,
            satBaraSubsurveyNo,
            seasonName,
            areaHect,
            landType,
            disbursementDate,
            disburseAmount,
            maturityLoanDate,
            recoveryAmountPrinciple,
            recoveryAmountInterest,
            recoveryDate,
            issPortalFileId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssFileParserCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (bankCode != null ? "bankCode=" + bankCode + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (branchCode != null ? "branchCode=" + branchCode + ", " : "") +
            (schemeWiseBranchCode != null ? "schemeWiseBranchCode=" + schemeWiseBranchCode + ", " : "") +
            (ifsc != null ? "ifsc=" + ifsc + ", " : "") +
            (loanAccountNumberkcc != null ? "loanAccountNumberkcc=" + loanAccountNumberkcc + ", " : "") +
            (farmerName != null ? "farmerName=" + farmerName + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (aadharNumber != null ? "aadharNumber=" + aadharNumber + ", " : "") +
            (dateofBirth != null ? "dateofBirth=" + dateofBirth + ", " : "") +
            (ageAtTimeOfSanction != null ? "ageAtTimeOfSanction=" + ageAtTimeOfSanction + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (farmersCategory != null ? "farmersCategory=" + farmersCategory + ", " : "") +
            (farmerType != null ? "farmerType=" + farmerType + ", " : "") +
            (socialCategory != null ? "socialCategory=" + socialCategory + ", " : "") +
            (relativeType != null ? "relativeType=" + relativeType + ", " : "") +
            (relativeName != null ? "relativeName=" + relativeName + ", " : "") +
            (stateName != null ? "stateName=" + stateName + ", " : "") +
            (stateCode != null ? "stateCode=" + stateCode + ", " : "") +
            (districtName != null ? "districtName=" + districtName + ", " : "") +
            (districtCode != null ? "districtCode=" + districtCode + ", " : "") +
            (blockCode != null ? "blockCode=" + blockCode + ", " : "") +
            (blockName != null ? "blockName=" + blockName + ", " : "") +
            (villageCode != null ? "villageCode=" + villageCode + ", " : "") +
            (villageName != null ? "villageName=" + villageName + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
            (accountType != null ? "accountType=" + accountType + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (pacsName != null ? "pacsName=" + pacsName + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (accountHolderType != null ? "accountHolderType=" + accountHolderType + ", " : "") +
            (primaryOccupation != null ? "primaryOccupation=" + primaryOccupation + ", " : "") +
            (loanSactionDate != null ? "loanSactionDate=" + loanSactionDate + ", " : "") +
            (loanSanctionAmount != null ? "loanSanctionAmount=" + loanSanctionAmount + ", " : "") +
            (tenureOFLoan != null ? "tenureOFLoan=" + tenureOFLoan + ", " : "") +
            (dateOfOverDuePayment != null ? "dateOfOverDuePayment=" + dateOfOverDuePayment + ", " : "") +
            (cropName != null ? "cropName=" + cropName + ", " : "") +
            (surveyNo != null ? "surveyNo=" + surveyNo + ", " : "") +
            (satBaraSubsurveyNo != null ? "satBaraSubsurveyNo=" + satBaraSubsurveyNo + ", " : "") +
            (seasonName != null ? "seasonName=" + seasonName + ", " : "") +
            (areaHect != null ? "areaHect=" + areaHect + ", " : "") +
            (landType != null ? "landType=" + landType + ", " : "") +
            (disbursementDate != null ? "disbursementDate=" + disbursementDate + ", " : "") +
            (disburseAmount != null ? "disburseAmount=" + disburseAmount + ", " : "") +
            (maturityLoanDate != null ? "maturityLoanDate=" + maturityLoanDate + ", " : "") +
            (recoveryAmountPrinciple != null ? "recoveryAmountPrinciple=" + recoveryAmountPrinciple + ", " : "") +
            (recoveryAmountInterest != null ? "recoveryAmountInterest=" + recoveryAmountInterest + ", " : "") +
            (recoveryDate != null ? "recoveryDate=" + recoveryDate + ", " : "") +
            (issPortalFileId != null ? "issPortalFileId=" + issPortalFileId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
