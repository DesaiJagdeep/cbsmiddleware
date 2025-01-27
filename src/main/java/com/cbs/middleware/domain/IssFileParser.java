package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;

/**
 * A IssFileParser.
 */
@Entity
@Table(name = "iss_file_parser")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssFileParser extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "scheme_wise_branch_code")
    private String schemeWiseBranchCode;

    @Column(name = "ifsc")
    private String ifsc;

    @Column(name = "loan_account_numberkcc")
    private String loanAccountNumberkcc;

    @Column(name = "farmer_name")
    private String farmerName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "aadhar_number")
    private String aadharNumber;

    @Column(name = "dateof_birth")
    private String dateofBirth;

    @Column(name = "age_at_time_of_sanction")
    private String ageAtTimeOfSanction;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "farmers_category")
    private String farmersCategory;

    @Column(name = "farmer_type")
    private String farmerType;

    @Column(name = "social_category")
    private String socialCategory;

    @Column(name = "relative_type")
    private String relativeType;

    @Column(name = "relative_name")
    private String relativeName;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "block_code")
    private String blockCode;

    @Column(name = "block_name")
    private String blockName;

    @Column(name = "village_code")
    private String villageCode;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "address")
    private String address;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "pacs_name")
    private String pacsName;

    @Column(name = "pacs_number")
    private String pacsNumber;

    @Column(name = "account_holder_type")
    private String accountHolderType;

    @Column(name = "primary_occupation")
    private String primaryOccupation;

    @Column(name = "loan_saction_date")
    private String loanSactionDate;

    @Column(name = "loan_sanction_amount")
    private String loanSanctionAmount;

    @Column(name = "tenure_of_loan")
    private String tenureOFLoan;

    @Column(name = "date_of_over_due_payment")
    private String dateOfOverDuePayment;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "kcc_iss_crop_code")
    private String kccIssCropCode;

    @Column(name = "kcc_iss_crop_name")
    private String kccIssCropName;

    @Column(name = "survey_no")
    private String surveyNo;

    @Column(name = "sat_bara_subsurvey_no")
    private String satBaraSubsurveyNo;

    @Column(name = "season_name")
    private String seasonName;

    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "area_hect")
    private String areaHect;

    @Column(name = "land_type")
    private String landType;

    @Column(name = "disbursement_date")
    private String disbursementDate;

    @Column(name = "disburse_amount")
    private String disburseAmount;

    @Column(name = "maturity_loan_date")
    private String maturityLoanDate;

    //first recovery details
    @Column(name = "recovery_amount_principle")
    private String recoveryAmountPrinciple;

    @Column(name = "recovery_amount_interest")
    private String recoveryAmountInterest;

    @Column(name = "recovery_date")
    private String recoveryDate;
    
    
    //second recovery details
    @Column(name = "second_recovery_amount_principle")
    private String secondRecoveryAmountPrinciple;

    @Column(name = "second_recovery_amount_interest")
    private String secondRecoveryAmountInterest;

    @Column(name = "second_recovery_date")
    private String secondRecoveryDate;
    
   //third recovery details
    @Column(name = "third_recovery_amount_principle")
    private String thirdRecoveryAmountPrinciple;

    @Column(name = "third_recovery_amount_interest")
    private String thirdRecoveryAmountInterest;

    @Column(name = "third_recovery_date")
    private String thirdRecoveryDate;
    
    
    //fourth recovery details
    @Column(name = "fourth_recovery_amount_principle")
    private String fourthRecoveryAmountPrinciple;

    @Column(name = "fourth_recovery_amount_interest")
    private String fourthRecoveryAmountInterest;

    @Column(name = "fourth_recovery_date")
    private String fourthRecoveryDate;
    

    @ManyToOne
    private IssPortalFile issPortalFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssFileParser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public IssFileParser financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getBankName() {
        return this.bankName;
    }

    public IssFileParser bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public String getKccIssCropCode() {
        return kccIssCropCode;
    }

    public void setKccIssCropCode(String kccIssCropCode) {
    	if(StringUtils.isNotBlank(kccIssCropCode)&&kccIssCropCode.length()==8)
    	{
    		this.kccIssCropCode = "0"+kccIssCropCode;
    	}
    	else
    	{
    		this.kccIssCropCode = kccIssCropCode;
    	}
    	
        
    }

    public String getKccIssCropName() {
        return kccIssCropName;
    }

    public String getSecondRecoveryAmountPrinciple() {
		return secondRecoveryAmountPrinciple;
	}

	public void setSecondRecoveryAmountPrinciple(String secondRecoveryAmountPrinciple) {
		this.secondRecoveryAmountPrinciple = secondRecoveryAmountPrinciple;
	}

	public String getSecondRecoveryAmountInterest() {
		return secondRecoveryAmountInterest;
	}

	public void setSecondRecoveryAmountInterest(String secondRecoveryAmountInterest) {
		this.secondRecoveryAmountInterest = secondRecoveryAmountInterest;
	}

	public String getSecondRecoveryDate() {
		return secondRecoveryDate;
	}

	public void setSecondRecoveryDate(String secondRecoveryDate) {
		this.secondRecoveryDate = secondRecoveryDate;
	}

	public String getThirdRecoveryAmountPrinciple() {
		return thirdRecoveryAmountPrinciple;
	}

	public void setThirdRecoveryAmountPrinciple(String thirdRecoveryAmountPrinciple) {
		this.thirdRecoveryAmountPrinciple = thirdRecoveryAmountPrinciple;
	}

	public String getThirdRecoveryAmountInterest() {
		return thirdRecoveryAmountInterest;
	}

	public void setThirdRecoveryAmountInterest(String thirdRecoveryAmountInterest) {
		this.thirdRecoveryAmountInterest = thirdRecoveryAmountInterest;
	}

	public String getThirdRecoveryDate() {
		return thirdRecoveryDate;
	}

	public void setThirdRecoveryDate(String thirdRecoveryDate) {
		this.thirdRecoveryDate = thirdRecoveryDate;
	}

	public String getFourthRecoveryAmountPrinciple() {
		return fourthRecoveryAmountPrinciple;
	}

	public void setFourthRecoveryAmountPrinciple(String fourthRecoveryAmountPrinciple) {
		this.fourthRecoveryAmountPrinciple = fourthRecoveryAmountPrinciple;
	}

	public String getFourthRecoveryAmountInterest() {
		return fourthRecoveryAmountInterest;
	}

	public void setFourthRecoveryAmountInterest(String fourthRecoveryAmountInterest) {
		this.fourthRecoveryAmountInterest = fourthRecoveryAmountInterest;
	}

	public String getFourthRecoveryDate() {
		return fourthRecoveryDate;
	}

	public void setFourthRecoveryDate(String fourthRecoveryDate) {
		this.fourthRecoveryDate = fourthRecoveryDate;
	}

	public void setKccIssCropName(String kccIssCropName) {
        this.kccIssCropName = kccIssCropName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public IssFileParser bankCode(String bankCode) {
        this.setBankCode(bankCode);
        return this;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public IssFileParser branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public IssFileParser branchCode(String branchCode) {
        this.setBranchCode(branchCode);
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getSchemeWiseBranchCode() {
        return this.schemeWiseBranchCode;
    }

    public IssFileParser schemeWiseBranchCode(String schemeWiseBranchCode) {
        this.setSchemeWiseBranchCode(schemeWiseBranchCode);
        return this;
    }

    public void setSchemeWiseBranchCode(String schemeWiseBranchCode) {
        this.schemeWiseBranchCode = schemeWiseBranchCode;
    }

    public String getIfsc() {
        return this.ifsc;
    }

    public IssFileParser ifsc(String ifsc) {
        this.setIfsc(ifsc);
        return this;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getLoanAccountNumberkcc() {
        return this.loanAccountNumberkcc;
    }

    public IssFileParser loanAccountNumberkcc(String loanAccountNumberkcc) {
        this.setLoanAccountNumberkcc(loanAccountNumberkcc);
        return this;
    }

    public void setLoanAccountNumberkcc(String loanAccountNumberkcc) {
        this.loanAccountNumberkcc = loanAccountNumberkcc;
    }

    public String getFarmerName() {
        return this.farmerName;
    }

    public IssFileParser farmerName(String farmerName) {
        this.setFarmerName(farmerName);
        return this;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getGender() {
        return this.gender;
    }

    public IssFileParser gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAadharNumber() {
        return this.aadharNumber;
    }

    public IssFileParser aadharNumber(String aadharNumber) {
        this.setAadharNumber(aadharNumber);
        return this;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getDateofBirth() {
        return this.dateofBirth;
    }

    public IssFileParser dateofBirth(String dateofBirth) {
        this.setDateofBirth(dateofBirth);
        return this;
    }

    public void setDateofBirth(String dateofBirth) {
        if (StringUtils.isNotBlank(dateofBirth)) {
            this.dateofBirth = dateInYYYYMMDD(dateofBirth);
        } else {
            this.dateofBirth = dateofBirth;
        }
    }

    public String getAgeAtTimeOfSanction() {
        return this.ageAtTimeOfSanction;
    }

    public IssFileParser ageAtTimeOfSanction(String ageAtTimeOfSanction) {
        this.setAgeAtTimeOfSanction(ageAtTimeOfSanction);
        return this;
    }

    public void setAgeAtTimeOfSanction(String ageAtTimeOfSanction) {
        this.ageAtTimeOfSanction = ageAtTimeOfSanction;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public IssFileParser mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFarmersCategory() {
        return this.farmersCategory;
    }

    public IssFileParser farmersCategory(String farmersCategory) {
        this.setFarmersCategory(farmersCategory);
        return this;
    }

    public void setFarmersCategory(String farmersCategory) {
        this.farmersCategory = farmersCategory;
    }

    public String getFarmerType() {
        return this.farmerType;
    }

    public IssFileParser farmerType(String farmerType) {
        this.setFarmerType(farmerType);
        return this;
    }

    public void setFarmerType(String farmerType) {
        this.farmerType = farmerType;
    }

    public String getSocialCategory() {
        return this.socialCategory;
    }

    public IssFileParser socialCategory(String socialCategory) {
        this.setSocialCategory(socialCategory);
        return this;
    }

    public void setSocialCategory(String socialCategory) {
        this.socialCategory = socialCategory;
    }

    public String getRelativeType() {
        return this.relativeType;
    }

    public IssFileParser relativeType(String relativeType) {
        this.setRelativeType(relativeType);
        return this;
    }

    public void setRelativeType(String relativeType) {
        this.relativeType = relativeType;
    }

    public String getRelativeName() {
        return this.relativeName;
    }

    public IssFileParser relativeName(String relativeName) {
        this.setRelativeName(relativeName);
        return this;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }

    public String getStateName() {
        return this.stateName;
    }

    public IssFileParser stateName(String stateName) {
        this.setStateName(stateName);
        return this;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public IssFileParser stateCode(String stateCode) {
        this.setStateCode(stateCode);
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDistrictName() {
        return this.districtName;
    }

    public IssFileParser districtName(String districtName) {
        this.setDistrictName(districtName);
        return this;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return this.districtCode;
    }

    public IssFileParser districtCode(String districtCode) {
        this.setDistrictCode(districtCode);
        return this;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getBlockCode() {
        return this.blockCode;
    }

    public IssFileParser blockCode(String blockCode) {
        this.setBlockCode(blockCode);
        return this;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getBlockName() {
        return this.blockName;
    }

    public IssFileParser blockName(String blockName) {
        this.setBlockName(blockName);
        return this;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getVillageCode() {
        return this.villageCode;
    }

    public IssFileParser villageCode(String villageCode) {
        this.setVillageCode(villageCode);
        return this;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getVillageName() {
        return this.villageName;
    }

    public IssFileParser villageName(String villageName) {
        this.setVillageName(villageName);
        return this;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getAddress() {
        return this.address;
    }

    public IssFileParser address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public IssFileParser pinCode(String pinCode) {
        this.setPinCode(pinCode);
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public IssFileParser accountType(String accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public IssFileParser accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPacsName() {
        return this.pacsName;
    }

    public IssFileParser pacsName(String pacsName) {
        this.setPacsName(pacsName);
        return this;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public IssFileParser pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getAccountHolderType() {
        return this.accountHolderType;
    }

    public IssFileParser accountHolderType(String accountHolderType) {
        this.setAccountHolderType(accountHolderType);
        return this;
    }

    public void setAccountHolderType(String accountHolderType) {
        this.accountHolderType = accountHolderType;
    }

    public String getPrimaryOccupation() {
        return this.primaryOccupation;
    }

    public IssFileParser primaryOccupation(String primaryOccupation) {
        this.setPrimaryOccupation(primaryOccupation);
        return this;
    }

    public void setPrimaryOccupation(String primaryOccupation) {
        this.primaryOccupation = primaryOccupation;
    }

    public String getLoanSactionDate() {
        return this.loanSactionDate;
    }

    public IssFileParser loanSactionDate(String loanSactionDate) {
        this.setLoanSactionDate(loanSactionDate);
        return this;
    }

    public void setLoanSactionDate(String loanSactionDate) {
        if (StringUtils.isNotBlank(loanSactionDate)) {
            this.loanSactionDate = dateInYYYYMMDD(loanSactionDate);
        } else {
            this.loanSactionDate = loanSactionDate;
        }
    }

    public String getLoanSanctionAmount() {
        return this.loanSanctionAmount;
    }

    public IssFileParser loanSanctionAmount(String loanSanctionAmount) {
        this.setLoanSanctionAmount(loanSanctionAmount);
        return this;
    }

    public void setLoanSanctionAmount(String loanSanctionAmount) {
        this.loanSanctionAmount = loanSanctionAmount;
    }

    public String getTenureOFLoan() {
        return this.tenureOFLoan;
    }

    public IssFileParser tenureOFLoan(String tenureOFLoan) {
        this.setTenureOFLoan(tenureOFLoan);
        return this;
    }

    public void setTenureOFLoan(String tenureOFLoan) {
        this.tenureOFLoan = tenureOFLoan;
    }

    public String getDateOfOverDuePayment() {
        return this.dateOfOverDuePayment;
    }

    public IssFileParser dateOfOverDuePayment(String dateOfOverDuePayment) {
        this.setDateOfOverDuePayment(dateOfOverDuePayment);
        return this;
    }

    public void setDateOfOverDuePayment(String dateOfOverDuePayment) {
        if (StringUtils.isNotBlank(dateOfOverDuePayment)) {
            this.dateOfOverDuePayment = dateInYYYYMMDD(dateOfOverDuePayment);
        } else {
            this.dateOfOverDuePayment = dateOfOverDuePayment;
        }
    }

    public String getCropName() {
        return this.cropName;
    }

    public IssFileParser cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSurveyNo() {
        return this.surveyNo;
    }

    public IssFileParser surveyNo(String surveyNo) {
        this.setSurveyNo(surveyNo);
        return this;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getSatBaraSubsurveyNo() {
        return this.satBaraSubsurveyNo;
    }

    public IssFileParser satBaraSubsurveyNo(String satBaraSubsurveyNo) {
        this.setSatBaraSubsurveyNo(satBaraSubsurveyNo);
        return this;
    }

    public void setSatBaraSubsurveyNo(String satBaraSubsurveyNo) {
        this.satBaraSubsurveyNo = satBaraSubsurveyNo;
    }

    public String getSeasonName() {
        return this.seasonName;
    }

    public IssFileParser seasonName(String seasonName) {
        this.setSeasonName(seasonName);
        return this;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getAreaHect() {
        return this.areaHect;
    }

    public IssFileParser areaHect(String areaHect) {
        this.setAreaHect(areaHect);
        return this;
    }

    public void setAreaHect(String areaHect) {
        this.areaHect = areaHect;
    }

    public String getLandType() {
        return this.landType;
    }

    public IssFileParser landType(String landType) {
        this.setLandType(landType);
        return this;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getDisbursementDate() {
        return this.disbursementDate;
    }

    public IssFileParser disbursementDate(String disbursementDate) {
        this.setDisbursementDate(disbursementDate);
        return this;
    }

    public void setDisbursementDate(String disbursementDate) {
        if (StringUtils.isNotBlank(disbursementDate)) {
            this.disbursementDate = dateInYYYYMMDD(disbursementDate);
        } else {
            this.disbursementDate = disbursementDate;
        }
    }

    public String getDisburseAmount() {
        return this.disburseAmount;
    }

    public IssFileParser disburseAmount(String disburseAmount) {
        this.setDisburseAmount(disburseAmount);
        return this;
    }

    public void setDisburseAmount(String disburseAmount) {
        this.disburseAmount = disburseAmount;
    }

    public String getMaturityLoanDate() {
        return this.maturityLoanDate;
    }

    public IssFileParser maturityLoanDate(String maturityLoanDate) {
        this.setMaturityLoanDate(maturityLoanDate);
        return this;
    }

    public void setMaturityLoanDate(String maturityLoanDate) {
        if (StringUtils.isNotBlank(maturityLoanDate)) {
            this.maturityLoanDate = dateInYYYYMMDD(maturityLoanDate);
        } else {
            this.maturityLoanDate = maturityLoanDate;
        }
    }

    public String getRecoveryAmountPrinciple() {
        return this.recoveryAmountPrinciple;
    }

    public IssFileParser recoveryAmountPrinciple(String recoveryAmountPrinciple) {
        this.setRecoveryAmountPrinciple(recoveryAmountPrinciple);
        return this;
    }

    public void setRecoveryAmountPrinciple(String recoveryAmountPrinciple) {
        this.recoveryAmountPrinciple = recoveryAmountPrinciple;
    }

    public String getRecoveryAmountInterest() {
        return this.recoveryAmountInterest;
    }

    public IssFileParser recoveryAmountInterest(String recoveryAmountInterest) {
        this.setRecoveryAmountInterest(recoveryAmountInterest);
        return this;
    }

    public void setRecoveryAmountInterest(String recoveryAmountInterest) {
        this.recoveryAmountInterest = recoveryAmountInterest;
    }

    public String getRecoveryDate() {
        return this.recoveryDate;
    }

    public IssFileParser recoveryDate(String recoveryDate) {
        this.setRecoveryDate(recoveryDate);
        return this;
    }

    public void setRecoveryDate(String recoveryDate) {
        if (StringUtils.isNotBlank(recoveryDate)) {
            this.recoveryDate = dateInYYYYMMDD(recoveryDate);
        } else {
            this.recoveryDate = recoveryDate;
        }
    }

    public IssPortalFile getIssPortalFile() {
        return this.issPortalFile;
    }

    public void setIssPortalFile(IssPortalFile issPortalFile) {
        this.issPortalFile = issPortalFile;
    }

    public IssFileParser issPortalFile(IssPortalFile issPortalFile) {
        this.setIssPortalFile(issPortalFile);
        return this;
    }

    private String dateInYYYYMMDD(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pattern patternYYYYMMDD = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");
        Pattern patternYYYY_MM_DD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Pattern patternDDMMYYYY = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
        Pattern patternDD_MM_YYYY = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

        if (patternYYYYMMDD.matcher(date).matches()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            date = localDate.format(formatter).trim();
        }

        if (patternYYYY_MM_DD.matcher(date).matches()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            date = localDate.format(formatter).trim();
        }

        if (patternDDMMYYYY.matcher(date).matches()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            date = localDate.format(formatter).trim();
        }

        if (patternDD_MM_YYYY.matcher(date).matches()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            date = localDate.format(formatter).trim();
        }

        return date;
    }

    public boolean isDuplicate(IssFileParser other) {
        return this.loanAccountNumberkcc.equals(other.loanAccountNumberkcc);
    }

    public boolean isDuplicateApp(IssFileParser other) {
        return (
            this.accountNumber.equals(other.accountNumber) &&
            this.aadharNumber.equals(other.aadharNumber) &&
            this.ageAtTimeOfSanction.equals(other.ageAtTimeOfSanction) &&
            this.loanAccountNumberkcc.equals(other.loanAccountNumberkcc) &&
            this.schemeWiseBranchCode.equals(other.schemeWiseBranchCode) &&
            this.loanSactionDate.equals(other.loanSactionDate) &&
            this.maturityLoanDate.equals(other.maturityLoanDate) &&
            this.kccIssCropCode.equals(other.kccIssCropCode) &&
            this.areaHect.equals(other.areaHect) &&
            this.kccIssCropCode.equals(other.kccIssCropCode)
        );
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssFileParser)) {
            return false;
        }
        return id != null && id.equals(((IssFileParser) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "IssFileParser{" + "id=" + getId() +
		// ", financialYear='" + getFinancialYear() + "'" +
//            ", bankName='" + getBankName() + "'" +
//            ", bankCode='" + getBankCode() + "'" +
//            ", branchName='" + getBranchName() + "'" +
//            ", branchCode='" + getBranchCode() + "'" +
//            ", schemeWiseBranchCode='" + getSchemeWiseBranchCode() + "'" +
//            ", ifsc='" + getIfsc() + "'" +
//            ", loanAccountNumberkcc='" + getLoanAccountNumberkcc() + "'" +
//            ", farmerName='" + getFarmerName() + "'" +
//            ", gender='" + getGender() + "'" +
//            ", aadharNumber='" + getAadharNumber() + "'" +
//            ", dateofBirth='" + getDateofBirth() + "'" +
//            ", ageAtTimeOfSanction='" + getAgeAtTimeOfSanction() + "'" +
//            ", mobileNo='" + getMobileNo() + "'" +
//            ", farmersCategory='" + getFarmersCategory() + "'" +
//            ", farmerType='" + getFarmerType() + "'" +
//            ", socialCategory='" + getSocialCategory() + "'" +
//            ", relativeType='" + getRelativeType() + "'" +
//            ", relativeName='" + getRelativeName() + "'" +
//            ", stateName='" + getStateName() + "'" +
//            ", stateCode='" + getStateCode() + "'" +
//            ", districtName='" + getDistrictName() + "'" +
//            ", districtCode='" + getDistrictCode() + "'" +
//            ", blockCode='" + getBlockCode() + "'" +
//            ", blockName='" + getBlockName() + "'" +
//            ", villageCode='" + getVillageCode() + "'" +
//            ", villageName='" + getVillageName() + "'" +
//            ", address='" + getAddress() + "'" +
//            ", pinCode='" + getPinCode() + "'" +
//            ", accountType='" + getAccountType() + "'" +
//            ", accountNumber='" + getAccountNumber() + "'" +
//            ", pacsName='" + getPacsName() + "'" +
//            ", pacsNumber='" + getPacsNumber() + "'" +
//            ", accountHolderType='" + getAccountHolderType() + "'" +
//            ", primaryOccupation='" + getPrimaryOccupation() + "'" +
//            ", loanSactionDate='" + getLoanSactionDate() + "'" +
//            ", loanSanctionAmount='" + getLoanSanctionAmount() + "'" +
//            ", tenureOFLoan='" + getTenureOFLoan() + "'" +
//            ", dateOfOverDuePayment='" + getDateOfOverDuePayment() + "'" +
//            ", cropName='" + getCropName() + "'" +
//            ", surveyNo='" + getSurveyNo() + "'" +
//            ", satBaraSubsurveyNo='" + getSatBaraSubsurveyNo() + "'" +
//            ", seasonName='" + getSeasonName() + "'" +
//            ", areaHect='" + getAreaHect() + "'" +
//            ", landType='" + getLandType() + "'" +
//            ", disbursementDate='" + getDisbursementDate() + "'" +
//            ", disburseAmount='" + getDisburseAmount() + "'" +
//            ", maturityLoanDate='" + getMaturityLoanDate() + "'" +
//            ", recoveryAmountPrinciple='" + getRecoveryAmountPrinciple() + "'" +
//            ", recoveryAmountInterest='" + getRecoveryAmountInterest() + "'" +
//            ", recoveryDate='" + getRecoveryDate() + "'" +
				"}";
	}
}
