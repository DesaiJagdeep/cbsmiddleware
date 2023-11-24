package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A CourtCase.
 */
@Entity
@Table(name = "court_case")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCase extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "branch_or_pacs_code")
    private String branchOrPacsCode;

    @Column(name = "setting_code")
    private String settingCode;

    // //@NotNull
    // //@Min(value = 1)
    @Column(name = "sr_no")
    private String srNo;

    ////@NotNull
    ////@Min(value = 1)
    @Column(name = "financial_year")
    private String financialYear;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "bank_name")
    private String bankName;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "taluka_name")
    private String talukaName;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "unique_file_name")
    private String uniqueFileName;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "name_of_defaulter")
    private String nameOfDefaulter;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "address")
    private String address;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "loan_type")
    private String loanType;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "loan_amount")
    private String loanAmount;

    @Column(name = "loan_date")
    private LocalDate loanDate;


    @Column(name = "claim_date")
    private LocalDate claimDate;

    @Column(name = "claim_date_mr")
    private String claimDateMr;

    @Column(name = "loan_date_mr")
    private String loanDateMr;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "term_of_loan")
    private String termOfLoan;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "interest_rate")
    private String interestRate;

    @Column(name = "installment_amount")
    private String installmentAmount;

    @Column(name = "total_credit")
    private String totalCredit;

    @Column(name = "balance")
    private String balance;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "interest_paid")
    private String interestPaid;

    @Column(name = "penal_interest_paid")
    private String penalInterestPaid;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "due_amount")
    private String dueAmount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "due_date_mr")
    private String dueDateMr;

    @Column(name = "society_branch_name")
    private String societyBranchName;

    @Column(name = "society_branch_address")
    private String societyBranchAddress;

    @Column(name = "due_interest")
    private String dueInterest;

    //@NotNull
    //@Min(value = 1)
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

    //@NotNull
    //@Min(value = 1)
    @Column(name = "gaurentor_one")
    private String gaurentorOne;

    @Column(name = "gaurentor_one_address")
    private String gaurentorOneAddress;

    //@NotNull
    //@Min(value = 1)
    @Column(name = "gaurentor_two")
    private String gaurentorTwo;

    @Column(name = "gaurentor_two_address")
    private String gaurentorTwoAddress;

    @Column(name = "first_notice_date")
    private LocalDate firstNoticeDate;

    @Column(name = "first_notice_date_mr")
    private String firstNoticeDateMr;

    @Column(name = "second_notice_date")
    private LocalDate secondNoticeDate;

    @Column(name = "second_notice_date_mr")
    private String secondNoticeDateMr;

    //-----------------new added
    @Column(name = "insurance")
    private String insurance="0.00";

    @Column(name = "stamp_fee")
    private String stampFee="0.00";

    @Column(name = "surcharge")
    private String surcharge="0.00";

    @Column(name = "inquiry_fee")
    private String inquiryFee="0.00";

    @Column(name = "vasuli_expense")
    private String vasuliExpense="0.00";

    @Column(name = "other_expense")
    private String otherExpense="0.00";

    @Column(name = "notice_expense")
    private String noticeExpense="0.00";

    //English data

    @Column(name = "vasuli_expense_en")
    private Double vasuliExpenseEn=0.00;

    @Column(name = "other_expense_en")
    private Double otherExpenseEn=0.00;

    @Column(name = "notice_expense_en")
    private Double noticeExpenseEn=0.00;

    @Column(name = "insurance_en")
    private Double insuranceEn=0.00;

    @Column(name = "setting_code_en")
    private Long settingCodeEn;

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
    private Double loanAmountEn=0.00;

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
    private Double dueInterestEn=0.00;

    @Column(name = "due_penal_interest_en")
    private Double duePenalInterestEn=0.00;

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

    @Column(name = "due_amount_word")
    private String dueAmountWord;

    @Column(name = "intrest_amount_word")
    private String intrestAmountWord;

    @Column(name = "total_amount_word")
    private String totalAmountWord;

    @Column(name = "total_postage")
    private String totalPostage;

    @Column(name = "total_postage_en")
    private Double totalPostageEn;

    @Column(name = "notice_of_repay_loan_count")
    private Integer noticeOfRepayLoanCount=0;

    @Column(name = "prior_demand_notice_count")
    private Integer priorDemandNoticeCount=0;

    @Column(name = "sheti_karj_count")
    private Integer shetiKarjCount=0;

    @Column(name = "bigar_sheti_karj_count")
    private Integer bigarShetiKarjCount=0;

    @Column(name = "one_zero_one_prakaran_count")
    private Integer oneZeroOnePrakaranCount=0;

    @Column(name = "appendix_three_count")
    private Integer appendixThreeCount=0;

    @Column(name = "appendix_four_count")
    private Integer appendixFourCount=0;


    @Column(name = "prior_demand_vyaj")
    private String priorDemandVyajMr="";

    @Column(name = "prior_demand_dand_vyaj")
    private String priorDemandDandVyajMr="";


    @Column(name = "intrest_amount_sum")
    private String intrestAmountSum="";

    @Column(name = "prior_demand_total")
    private String priorDemandTotalMr="";


    @Column(name = "maturity_loan_date")
    private String maturityLoanDate="";

    @Column(name = "maturity_loan_date_en")
    private LocalDate maturityLoanDateEn;


    @ManyToOne()
    private CourtCaseFile courtCaseFile;

    @ManyToOne()
    private CourtCaseSetting courtCaseSetting;



    //101 printing date

    @Column(name = "notice_of_repay_loan_date")
    private LocalDate NoticeOfRepayLoanDate;

    @Column(name = "prior_demand_notice_date")
    private LocalDate priorDemandNoticeDate;

    @Column(name = "sheti_karj_date")
    private LocalDate shetiKarjDate;

    @Column(name = "bigar_sheti_karj_date")
    private LocalDate bigarShetiKarjDate;

    @Column(name = "one_zero_one_prakaran_date")
    private LocalDate oneZeroOnePrakaranDate;

    @Column(name = "appendix_three_date")
    private LocalDate appendixThreeDate;

    @Column(name = "appendix_four_date")
    private LocalDate appendixFourDate;



    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public String getStampFee() {
		return stampFee;
	}

	public void setStampFee(String stampFee) {
		this.stampFee = stampFee;
	}

	public String getSurcharge() {
		return surcharge;
	}



	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	public String getInquiryFee() {
		return inquiryFee;
	}



	public LocalDate getNoticeOfRepayLoanDate() {
		return NoticeOfRepayLoanDate;
	}

	public void setNoticeOfRepayLoanDate(LocalDate noticeOfRepayLoanDate) {
		NoticeOfRepayLoanDate = noticeOfRepayLoanDate;
	}



	public LocalDate getPriorDemandNoticeDate() {
		return priorDemandNoticeDate;
	}

	public void setPriorDemandNoticeDate(LocalDate priorDemandNoticeDate) {
		this.priorDemandNoticeDate = priorDemandNoticeDate;
	}

	public LocalDate getShetiKarjDate() {
		return shetiKarjDate;
	}

	public void setShetiKarjDate(LocalDate shetiKarjDate) {
		this.shetiKarjDate = shetiKarjDate;
	}

	public LocalDate getBigarShetiKarjDate() {
		return bigarShetiKarjDate;
	}

	public void setBigarShetiKarjDate(LocalDate bigarShetiKarjDate) {
		this.bigarShetiKarjDate = bigarShetiKarjDate;
	}

	public LocalDate getOneZeroOnePrakaranDate() {
		return oneZeroOnePrakaranDate;
	}

	public void setOneZeroOnePrakaranDate(LocalDate oneZeroOnePrakaranDate) {
		this.oneZeroOnePrakaranDate = oneZeroOnePrakaranDate;
	}

	public LocalDate getAppendixThreeDate() {
		return appendixThreeDate;
	}

	public void setAppendixThreeDate(LocalDate appendixThreeDate) {
		this.appendixThreeDate = appendixThreeDate;
	}

	public LocalDate getAppendixFourDate() {
		return appendixFourDate;
	}

	public void setAppendixFourDate(LocalDate appendixFourDate) {
		this.appendixFourDate = appendixFourDate;
	}

	public void setInquiryFee(String inquiryFee) {
		this.inquiryFee = inquiryFee;
	}

	public CourtCaseFile getCourtCaseFile() {
		return courtCaseFile;
	}

	public void setCourtCaseFile(CourtCaseFile courtCaseFile) {
		this.courtCaseFile = courtCaseFile;
	}

	public CourtCase id(Long id) {
        this.setId(id);
        return this;
    }

    public String getPriorDemandVyajMr() {
		return priorDemandVyajMr;
	}

	public void setPriorDemandVyajMr(String priorDemandVyajMr) {
		this.priorDemandVyajMr = priorDemandVyajMr;
	}



	public Double getTotalPostageEn() {
		return totalPostageEn;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public Double getInsuranceEn() {
		return insuranceEn;
	}

	public void setInsuranceEn(Double insuranceEn) {
		this.insuranceEn = insuranceEn;
	}

	public void setTotalPostageEn(Double totalPostageEn) {
		this.totalPostageEn = totalPostageEn;
	}

	public String getIntrestAmountSum() {
		return intrestAmountSum;
	}

	public void setIntrestAmountSum(String intrestAmountSum) {
		this.intrestAmountSum = intrestAmountSum;
	}

	public String getPriorDemandDandVyajMr() {
		return priorDemandDandVyajMr;
	}

	public void setPriorDemandDandVyajMr(String priorDemandDandVyajMr) {
		this.priorDemandDandVyajMr = priorDemandDandVyajMr;
	}



	public String getPriorDemandTotalMr() {
		return priorDemandTotalMr;
	}



	public String getMaturityLoanDate() {
		return maturityLoanDate;
	}

	public void setMaturityLoanDate(String maturityLoanDate) {
		this.maturityLoanDate = maturityLoanDate;
	}

    public Instant getMaturityLoanDateEn() {
        if (maturityLoanDateEn!= null) {
            return this.maturityLoanDateEn.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else {
            return null;
        }
    }

    public void setMaturityLoanDateEn(Instant maturityLoanDateEn) {
        if (maturityLoanDateEn != null){
            this.maturityLoanDateEn = LocalDate.ofInstant(maturityLoanDateEn, ZoneId.systemDefault());
        } else {
            this.maturityLoanDateEn = null;
        }
    }

	public void setPriorDemandTotalMr(String priorDemandTotalMr) {
		this.priorDemandTotalMr = priorDemandTotalMr;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getSrNo() {
        return this.srNo;
    }


	public String getTotalPostage() {
		return totalPostage;
	}

	public void setTotalPostage(String totalPostage) {
		this.totalPostage = totalPostage;
	}

	public String getSocietyBranchName() {
		return societyBranchName;
	}

	public Integer getNoticeOfRepayLoanCount() {
		return noticeOfRepayLoanCount;
	}

	public void setNoticeOfRepayLoanCount(Integer noticeOfRepayLoanCount) {
		this.noticeOfRepayLoanCount = noticeOfRepayLoanCount;
	}

	public Integer getPriorDemandNoticeCount() {
		return priorDemandNoticeCount;
	}

	public void setPriorDemandNoticeCount(Integer priorDemandNoticeCount) {
		this.priorDemandNoticeCount = priorDemandNoticeCount;
	}

	public Integer getShetiKarjCount() {
		return shetiKarjCount;
	}

	public void setShetiKarjCount(Integer shetiKarjCount) {
		this.shetiKarjCount = shetiKarjCount;
	}

	public Integer getBigarShetiKarjCount() {
		return bigarShetiKarjCount;
	}

	public void setBigarShetiKarjCount(Integer bigarShetiKarjCount) {
		this.bigarShetiKarjCount = bigarShetiKarjCount;
	}

	public Integer getOneZeroOnePrakaranCount() {
		return oneZeroOnePrakaranCount;
	}

	public void setOneZeroOnePrakaranCount(Integer oneZeroOnePrakaranCount) {
		this.oneZeroOnePrakaranCount = oneZeroOnePrakaranCount;
	}

	public Integer getAppendixThreeCount() {
		return appendixThreeCount;
	}

	public void setAppendixThreeCount(Integer appendixThreeCount) {
		this.appendixThreeCount = appendixThreeCount;
	}

	public Integer getAppendixFourCount() {
		return appendixFourCount;
	}

	public void setAppendixFourCount(Integer appendixFourCount) {
		this.appendixFourCount = appendixFourCount;
	}

	public void setSocietyBranchName(String societyBranchName) {
		this.societyBranchName = societyBranchName;
	}

	public CourtCaseSetting getCourtCaseSetting() {
		return courtCaseSetting;
	}

	public void setCourtCaseSetting(CourtCaseSetting courtCaseSetting) {
		this.courtCaseSetting = courtCaseSetting;
	}

	public String getSocietyBranchAddress() {
		return societyBranchAddress;
	}

	public void setSocietyBranchAddress(String societyBranchAddress) {
		this.societyBranchAddress = societyBranchAddress;
	}

	public CourtCase srNo(String srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFinancialYear() {
		return financialYear;
	}

	public String getBranchOrPacsCode() {
		return branchOrPacsCode;
	}

	public void setBranchOrPacsCode(String branchOrPacsCode) {
		this.branchOrPacsCode = branchOrPacsCode;
	}

	public Long getSettingCodeEn() {
		return settingCodeEn;
	}

	public void setSettingCodeEn(Long settingCodeEn) {
		this.settingCodeEn = settingCodeEn;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
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

    public String getDueAmountWord() {
		return dueAmountWord;
	}

	public void setDueAmountWord(String dueAmountWord) {
		this.dueAmountWord = dueAmountWord;
	}

	public String getIntrestAmountWord() {
		return intrestAmountWord;
	}

	public void setIntrestAmountWord(String intrestAmountWord) {
		this.intrestAmountWord = intrestAmountWord;
	}

	public String getTotalAmountWord() {
		return totalAmountWord;
	}

	public void setTotalAmountWord(String totalAmountWord) {
		this.totalAmountWord = totalAmountWord;
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

    public Instant getLoanDate() {
        if (loanDate!= null) {
            return this.loanDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else {
            return null;
        }
    }

    /*public CourtCase loanDate(LocalDate loanDate) {
        this.setLoanDate(loanDate);
        return this;
    }*/

    public void setLoanDate(Instant loanDate) {
        if (loanDate != null){
            this.loanDate = LocalDate.ofInstant(loanDate, ZoneId.systemDefault());
        } else {
            this.loanDate = null;
        }

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

    public String getClaimDateMr() {
		return claimDateMr;
	}

	public void setClaimDateMr(String claimDateMr) {
		this.claimDateMr = claimDateMr;
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

    public String getLoanDateMr() {
		return loanDateMr;
	}

	public void setLoanDateMr(String loanDateMr) {
		this.loanDateMr = loanDateMr;
	}

	public String getDueDateMr() {
		return dueDateMr;
	}

	public void setDueDateMr(String dueDateMr) {
		this.dueDateMr = dueDateMr;
	}

	public String getFirstNoticeDateMr() {
		return firstNoticeDateMr;
	}

	public void setFirstNoticeDateMr(String firstNoticeDateMr) {
		this.firstNoticeDateMr = firstNoticeDateMr;
	}

	public String getSecondNoticeDateMr() {
		return secondNoticeDateMr;
	}

	public void setSecondNoticeDateMr(String secondNoticeDateMr) {
		this.secondNoticeDateMr = secondNoticeDateMr;
	}

	public String getInterestPaid() {
        return this.interestPaid;
    }

    public CourtCase interestPaid(String interestPaid) {
        this.setInterestPaid(interestPaid);
        return this;
    }

    public String getVasuliExpense() {
		return vasuliExpense;
	}

	public void setVasuliExpense(String vasuliExpense) {
		this.vasuliExpense = vasuliExpense;
	}

	public String getOtherExpense() {
		return otherExpense;
	}

	public void setOtherExpense(String otherExpense) {
		this.otherExpense = otherExpense;
	}

	public String getNoticeExpense() {
		return noticeExpense;
	}

	public void setNoticeExpense(String noticeExpense) {
		this.noticeExpense = noticeExpense;
	}

	public Double getVasuliExpenseEn() {
		return vasuliExpenseEn;
	}

	public void setVasuliExpenseEn(Double vasuliExpenseEn) {
		this.vasuliExpenseEn = vasuliExpenseEn;
	}

	public Double getOtherExpenseEn() {
		return otherExpenseEn;
	}

	public void setOtherExpenseEn(Double otherExpenseEn) {
		this.otherExpenseEn = otherExpenseEn;
	}

	public Double getNoticeExpenseEn() {
		return noticeExpenseEn;
	}

	public void setNoticeExpenseEn(Double noticeExpenseEn) {
		this.noticeExpenseEn = noticeExpenseEn;
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


    public String getSettingCode() {
		return settingCode;
	}

	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
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

    public Instant getDueDate() {
        if (dueDate!= null) {
            return this.dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else {
            return null;
        }

    }

    public CourtCase dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return this;
    }

    public void setDueDate(Instant dueDate) {
        if (dueDate != null){
            this.dueDate = LocalDate.ofInstant(dueDate, ZoneId.systemDefault());
        } else {
            this.dueDate = null;
        }
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

    public Instant getClaimDate() {
        if (claimDate!= null) {
            return this.claimDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else {
            return null;
        }
    }

    public void setClaimDate(Instant claimDate) {
        if (claimDate != null){
            this.claimDate = LocalDate.ofInstant(claimDate, ZoneId.systemDefault());
        } else {
            this.claimDate = null;
        }
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

    public Instant getFirstNoticeDate() {
        if (firstNoticeDate!= null) {
            return this.firstNoticeDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else {
            return null;
        }
    }


    public CourtCase firstNoticeDate(LocalDate firstNoticeDate) {
        this.setFirstNoticeDate(firstNoticeDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return this;
    }

    public void setFirstNoticeDate(Instant firstNoticeDate) {
        if (firstNoticeDate != null){
            this.firstNoticeDate = LocalDate.ofInstant(firstNoticeDate, ZoneId.systemDefault());
        } else {
            this.firstNoticeDate = null;
        }
    }

    public Instant getSecondNoticeDate() {
        if (secondNoticeDate!= null) {
            return this.secondNoticeDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else {
            return null;
        }
    }

    public CourtCase secondNoticeDate(LocalDate secondNoticeDate) {
        this.setSecondNoticeDate(secondNoticeDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return this;
    }

    public void setSecondNoticeDate(Instant secondNoticeDate) {
        if (secondNoticeDate != null){
            this.secondNoticeDate = LocalDate.ofInstant(secondNoticeDate, ZoneId.systemDefault());
        } else {
            this.secondNoticeDate = null;
        }
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
