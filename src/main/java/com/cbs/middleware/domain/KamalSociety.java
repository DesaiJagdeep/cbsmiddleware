package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A KamalSociety.
 */
@Entity
@Table(name = "kamal_society")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalSociety implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "financial_year", nullable = false)
    private String financialYear;

    @Column(name = "km_date")
    private Instant kmDate;

    @Column(name = "km_date_mr")
    private String kmDateMr;

    @Column(name = "km_from_date")
    private Instant kmFromDate;

    @Column(name = "km_from_date_mr")
    private String kmFromDateMr;

    @Column(name = "km_to_date")
    private Instant kmToDate;

    @Column(name = "km_to_date_mr")
    private String kmToDateMr;

    @Column(name = "pacs_number")
    private String pacsNumber;

    @Column(name = "pacs_name")
    private String pacsName;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "zindagi_patrak_date")
    private Instant zindagiPatrakDate;

    @Column(name = "zindagi_patrak_date_mr")
    private String zindagiPatrakDateMr;

    @Column(name = "bank_tapasani_date")
    private Instant bankTapasaniDate;

    @Column(name = "bank_tapasani_date_mr")
    private String bankTapasaniDateMr;

    @Column(name = "gov_tapasani_date")
    private Instant govTapasaniDate;

    @Column(name = "gov_tapasani_date_mr")
    private String govTapasaniDateMr;

    @Column(name = "sanstha_tapasani_date")
    private Instant sansthaTapasaniDate;

    @Column(name = "sanstha_tapasani_date_mr")
    private String sansthaTapasaniDateMr;

    @Column(name = "total_land")
    private String totalLand;

    @Column(name = "bagayat")
    private String bagayat;

    @Column(name = "jirayat")
    private String jirayat;

    @Column(name = "total_farmer")
    private String totalFarmer;

    @Column(name = "member_farmer")
    private String memberFarmer;

    @Column(name = "non_member_farmer")
    private String nonMemberFarmer;

    @Column(name = "taleband_date")
    private Instant talebandDate;

    @Column(name = "mem_loan")
    private String memLoan;

    @Column(name = "mem_due")
    private String memDue;

    @Column(name = "mem_vasuli")
    private String memVasuli;

    @Column(name = "mem_vasuli_per")
    private String memVasuliPer;

    @Column(name = "bank_loan")
    private String bankLoan;

    @Column(name = "bank_due")
    private String bankDue;

    @Column(name = "bank_vasuli")
    private String bankVasuli;

    @Column(name = "bank_vasuli_per")
    private String bankVasuliPer;

    @Column(name = "balance_sheet_date")
    private Instant balanceSheetDate;

    @Column(name = "balance_sheet_date_mr")
    private String balanceSheetDateMr;

    @Column(name = "liability_adhikrut_share_capital")
    private String liabilityAdhikrutShareCapital;

    @Column(name = "liability_vasul_share_capital")
    private String liabilityVasulShareCapital;

    @Column(name = "liability_fund")
    private String liabilityFund;

    @Column(name = "liability_spare_fund")
    private String liabilitySpareFund;

    @Column(name = "liability_deposite")
    private String liabilityDeposite;

    @Column(name = "liability_balance_sheet_bank_loan")
    private String liabilityBalanceSheetBankLoan;

    @Column(name = "liability_other_payable")
    private String liabilityOtherPayable;

    @Column(name = "liability_profit")
    private String liabilityProfit;

    @Column(name = "asset_cash")
    private String assetCash;

    @Column(name = "asset_investment")
    private String assetInvestment;

    @Column(name = "asset_imarat_fund")
    private String assetImaratFund;

    @Column(name = "asset_member_loan")
    private String assetMemberLoan;

    @Column(name = "asset_dead_stock")
    private String assetDeadStock;

    @Column(name = "asset_other_receivable")
    private String assetOtherReceivable;

    @Column(name = "asset_loss")
    private String assetLoss;

    @Column(name = "total_liability")
    private String totalLiability;

    @Column(name = "total_asset")
    private String totalAsset;

    @Column(name = "village_code")
    private String villageCode;

    @Column(name = "pacs_verified_flag")
    private Boolean pacsVerifiedFlag;

    @Column(name = "branch_verified_flag")
    private Boolean branchVerifiedFlag;

    @Column(name = "head_office_verified_flag")
    private Boolean headOfficeVerifiedFlag;

    @Column(name = "is_supplimentery_flag")
    private Boolean isSupplimenteryFlag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KamalSociety id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public KamalSociety financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Instant getKmDate() {
        return this.kmDate;
    }

    public KamalSociety kmDate(Instant kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public void setKmDate(Instant kmDate) {
        this.kmDate = kmDate;
    }

    public String getKmDateMr() {
        return this.kmDateMr;
    }

    public KamalSociety kmDateMr(String kmDateMr) {
        this.setKmDateMr(kmDateMr);
        return this;
    }

    public void setKmDateMr(String kmDateMr) {
        this.kmDateMr = kmDateMr;
    }

    public Instant getKmFromDate() {
        return this.kmFromDate;
    }

    public KamalSociety kmFromDate(Instant kmFromDate) {
        this.setKmFromDate(kmFromDate);
        return this;
    }

    public void setKmFromDate(Instant kmFromDate) {
        this.kmFromDate = kmFromDate;
    }

    public String getKmFromDateMr() {
        return this.kmFromDateMr;
    }

    public KamalSociety kmFromDateMr(String kmFromDateMr) {
        this.setKmFromDateMr(kmFromDateMr);
        return this;
    }

    public void setKmFromDateMr(String kmFromDateMr) {
        this.kmFromDateMr = kmFromDateMr;
    }

    public Instant getKmToDate() {
        return this.kmToDate;
    }

    public KamalSociety kmToDate(Instant kmToDate) {
        this.setKmToDate(kmToDate);
        return this;
    }

    public void setKmToDate(Instant kmToDate) {
        this.kmToDate = kmToDate;
    }

    public String getKmToDateMr() {
        return this.kmToDateMr;
    }

    public KamalSociety kmToDateMr(String kmToDateMr) {
        this.setKmToDateMr(kmToDateMr);
        return this;
    }

    public void setKmToDateMr(String kmToDateMr) {
        this.kmToDateMr = kmToDateMr;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public KamalSociety pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getPacsName() {
        return this.pacsName;
    }

    public KamalSociety pacsName(String pacsName) {
        this.setPacsName(pacsName);
        return this;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public KamalSociety branchId(Long branchId) {
        this.setBranchId(branchId);
        return this;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public KamalSociety branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Instant getZindagiPatrakDate() {
        return this.zindagiPatrakDate;
    }

    public KamalSociety zindagiPatrakDate(Instant zindagiPatrakDate) {
        this.setZindagiPatrakDate(zindagiPatrakDate);
        return this;
    }

    public void setZindagiPatrakDate(Instant zindagiPatrakDate) {
        this.zindagiPatrakDate = zindagiPatrakDate;
    }

    public String getZindagiPatrakDateMr() {
        return this.zindagiPatrakDateMr;
    }

    public KamalSociety zindagiPatrakDateMr(String zindagiPatrakDateMr) {
        this.setZindagiPatrakDateMr(zindagiPatrakDateMr);
        return this;
    }

    public void setZindagiPatrakDateMr(String zindagiPatrakDateMr) {
        this.zindagiPatrakDateMr = zindagiPatrakDateMr;
    }

    public Instant getBankTapasaniDate() {
        return this.bankTapasaniDate;
    }

    public KamalSociety bankTapasaniDate(Instant bankTapasaniDate) {
        this.setBankTapasaniDate(bankTapasaniDate);
        return this;
    }

    public void setBankTapasaniDate(Instant bankTapasaniDate) {
        this.bankTapasaniDate = bankTapasaniDate;
    }

    public String getBankTapasaniDateMr() {
        return this.bankTapasaniDateMr;
    }

    public KamalSociety bankTapasaniDateMr(String bankTapasaniDateMr) {
        this.setBankTapasaniDateMr(bankTapasaniDateMr);
        return this;
    }

    public void setBankTapasaniDateMr(String bankTapasaniDateMr) {
        this.bankTapasaniDateMr = bankTapasaniDateMr;
    }

    public Instant getGovTapasaniDate() {
        return this.govTapasaniDate;
    }

    public KamalSociety govTapasaniDate(Instant govTapasaniDate) {
        this.setGovTapasaniDate(govTapasaniDate);
        return this;
    }

    public void setGovTapasaniDate(Instant govTapasaniDate) {
        this.govTapasaniDate = govTapasaniDate;
    }

    public String getGovTapasaniDateMr() {
        return this.govTapasaniDateMr;
    }

    public KamalSociety govTapasaniDateMr(String govTapasaniDateMr) {
        this.setGovTapasaniDateMr(govTapasaniDateMr);
        return this;
    }

    public void setGovTapasaniDateMr(String govTapasaniDateMr) {
        this.govTapasaniDateMr = govTapasaniDateMr;
    }

    public Instant getSansthaTapasaniDate() {
        return this.sansthaTapasaniDate;
    }

    public KamalSociety sansthaTapasaniDate(Instant sansthaTapasaniDate) {
        this.setSansthaTapasaniDate(sansthaTapasaniDate);
        return this;
    }

    public void setSansthaTapasaniDate(Instant sansthaTapasaniDate) {
        this.sansthaTapasaniDate = sansthaTapasaniDate;
    }

    public String getSansthaTapasaniDateMr() {
        return this.sansthaTapasaniDateMr;
    }

    public KamalSociety sansthaTapasaniDateMr(String sansthaTapasaniDateMr) {
        this.setSansthaTapasaniDateMr(sansthaTapasaniDateMr);
        return this;
    }

    public void setSansthaTapasaniDateMr(String sansthaTapasaniDateMr) {
        this.sansthaTapasaniDateMr = sansthaTapasaniDateMr;
    }

    public String getTotalLand() {
        return this.totalLand;
    }

    public KamalSociety totalLand(String totalLand) {
        this.setTotalLand(totalLand);
        return this;
    }

    public void setTotalLand(String totalLand) {
        this.totalLand = totalLand;
    }

    public String getBagayat() {
        return this.bagayat;
    }

    public KamalSociety bagayat(String bagayat) {
        this.setBagayat(bagayat);
        return this;
    }

    public void setBagayat(String bagayat) {
        this.bagayat = bagayat;
    }

    public String getJirayat() {
        return this.jirayat;
    }

    public KamalSociety jirayat(String jirayat) {
        this.setJirayat(jirayat);
        return this;
    }

    public void setJirayat(String jirayat) {
        this.jirayat = jirayat;
    }

    public String getTotalFarmer() {
        return this.totalFarmer;
    }

    public KamalSociety totalFarmer(String totalFarmer) {
        this.setTotalFarmer(totalFarmer);
        return this;
    }

    public void setTotalFarmer(String totalFarmer) {
        this.totalFarmer = totalFarmer;
    }

    public String getMemberFarmer() {
        return this.memberFarmer;
    }

    public KamalSociety memberFarmer(String memberFarmer) {
        this.setMemberFarmer(memberFarmer);
        return this;
    }

    public void setMemberFarmer(String memberFarmer) {
        this.memberFarmer = memberFarmer;
    }

    public String getNonMemberFarmer() {
        return this.nonMemberFarmer;
    }

    public KamalSociety nonMemberFarmer(String nonMemberFarmer) {
        this.setNonMemberFarmer(nonMemberFarmer);
        return this;
    }

    public void setNonMemberFarmer(String nonMemberFarmer) {
        this.nonMemberFarmer = nonMemberFarmer;
    }

    public Instant getTalebandDate() {
        return this.talebandDate;
    }

    public KamalSociety talebandDate(Instant talebandDate) {
        this.setTalebandDate(talebandDate);
        return this;
    }

    public void setTalebandDate(Instant talebandDate) {
        this.talebandDate = talebandDate;
    }

    public String getMemLoan() {
        return this.memLoan;
    }

    public KamalSociety memLoan(String memLoan) {
        this.setMemLoan(memLoan);
        return this;
    }

    public void setMemLoan(String memLoan) {
        this.memLoan = memLoan;
    }

    public String getMemDue() {
        return this.memDue;
    }

    public KamalSociety memDue(String memDue) {
        this.setMemDue(memDue);
        return this;
    }

    public void setMemDue(String memDue) {
        this.memDue = memDue;
    }

    public String getMemVasuli() {
        return this.memVasuli;
    }

    public KamalSociety memVasuli(String memVasuli) {
        this.setMemVasuli(memVasuli);
        return this;
    }

    public void setMemVasuli(String memVasuli) {
        this.memVasuli = memVasuli;
    }

    public String getMemVasuliPer() {
        return this.memVasuliPer;
    }

    public KamalSociety memVasuliPer(String memVasuliPer) {
        this.setMemVasuliPer(memVasuliPer);
        return this;
    }

    public void setMemVasuliPer(String memVasuliPer) {
        this.memVasuliPer = memVasuliPer;
    }

    public String getBankLoan() {
        return this.bankLoan;
    }

    public KamalSociety bankLoan(String bankLoan) {
        this.setBankLoan(bankLoan);
        return this;
    }

    public void setBankLoan(String bankLoan) {
        this.bankLoan = bankLoan;
    }

    public String getBankDue() {
        return this.bankDue;
    }

    public KamalSociety bankDue(String bankDue) {
        this.setBankDue(bankDue);
        return this;
    }

    public void setBankDue(String bankDue) {
        this.bankDue = bankDue;
    }

    public String getBankVasuli() {
        return this.bankVasuli;
    }

    public KamalSociety bankVasuli(String bankVasuli) {
        this.setBankVasuli(bankVasuli);
        return this;
    }

    public void setBankVasuli(String bankVasuli) {
        this.bankVasuli = bankVasuli;
    }

    public String getBankVasuliPer() {
        return this.bankVasuliPer;
    }

    public KamalSociety bankVasuliPer(String bankVasuliPer) {
        this.setBankVasuliPer(bankVasuliPer);
        return this;
    }

    public void setBankVasuliPer(String bankVasuliPer) {
        this.bankVasuliPer = bankVasuliPer;
    }

    public Instant getBalanceSheetDate() {
        return this.balanceSheetDate;
    }

    public KamalSociety balanceSheetDate(Instant balanceSheetDate) {
        this.setBalanceSheetDate(balanceSheetDate);
        return this;
    }

    public void setBalanceSheetDate(Instant balanceSheetDate) {
        this.balanceSheetDate = balanceSheetDate;
    }

    public String getBalanceSheetDateMr() {
        return this.balanceSheetDateMr;
    }

    public KamalSociety balanceSheetDateMr(String balanceSheetDateMr) {
        this.setBalanceSheetDateMr(balanceSheetDateMr);
        return this;
    }

    public void setBalanceSheetDateMr(String balanceSheetDateMr) {
        this.balanceSheetDateMr = balanceSheetDateMr;
    }

    public String getLiabilityAdhikrutShareCapital() {
        return this.liabilityAdhikrutShareCapital;
    }

    public KamalSociety liabilityAdhikrutShareCapital(String liabilityAdhikrutShareCapital) {
        this.setLiabilityAdhikrutShareCapital(liabilityAdhikrutShareCapital);
        return this;
    }

    public void setLiabilityAdhikrutShareCapital(String liabilityAdhikrutShareCapital) {
        this.liabilityAdhikrutShareCapital = liabilityAdhikrutShareCapital;
    }

    public String getLiabilityVasulShareCapital() {
        return this.liabilityVasulShareCapital;
    }

    public KamalSociety liabilityVasulShareCapital(String liabilityVasulShareCapital) {
        this.setLiabilityVasulShareCapital(liabilityVasulShareCapital);
        return this;
    }

    public void setLiabilityVasulShareCapital(String liabilityVasulShareCapital) {
        this.liabilityVasulShareCapital = liabilityVasulShareCapital;
    }

    public String getLiabilityFund() {
        return this.liabilityFund;
    }

    public KamalSociety liabilityFund(String liabilityFund) {
        this.setLiabilityFund(liabilityFund);
        return this;
    }

    public void setLiabilityFund(String liabilityFund) {
        this.liabilityFund = liabilityFund;
    }

    public String getLiabilitySpareFund() {
        return this.liabilitySpareFund;
    }

    public KamalSociety liabilitySpareFund(String liabilitySpareFund) {
        this.setLiabilitySpareFund(liabilitySpareFund);
        return this;
    }

    public void setLiabilitySpareFund(String liabilitySpareFund) {
        this.liabilitySpareFund = liabilitySpareFund;
    }

    public String getLiabilityDeposite() {
        return this.liabilityDeposite;
    }

    public KamalSociety liabilityDeposite(String liabilityDeposite) {
        this.setLiabilityDeposite(liabilityDeposite);
        return this;
    }

    public void setLiabilityDeposite(String liabilityDeposite) {
        this.liabilityDeposite = liabilityDeposite;
    }

    public String getLiabilityBalanceSheetBankLoan() {
        return this.liabilityBalanceSheetBankLoan;
    }

    public KamalSociety liabilityBalanceSheetBankLoan(String liabilityBalanceSheetBankLoan) {
        this.setLiabilityBalanceSheetBankLoan(liabilityBalanceSheetBankLoan);
        return this;
    }

    public void setLiabilityBalanceSheetBankLoan(String liabilityBalanceSheetBankLoan) {
        this.liabilityBalanceSheetBankLoan = liabilityBalanceSheetBankLoan;
    }

    public String getLiabilityOtherPayable() {
        return this.liabilityOtherPayable;
    }

    public KamalSociety liabilityOtherPayable(String liabilityOtherPayable) {
        this.setLiabilityOtherPayable(liabilityOtherPayable);
        return this;
    }

    public void setLiabilityOtherPayable(String liabilityOtherPayable) {
        this.liabilityOtherPayable = liabilityOtherPayable;
    }

    public String getLiabilityProfit() {
        return this.liabilityProfit;
    }

    public KamalSociety liabilityProfit(String liabilityProfit) {
        this.setLiabilityProfit(liabilityProfit);
        return this;
    }

    public void setLiabilityProfit(String liabilityProfit) {
        this.liabilityProfit = liabilityProfit;
    }

    public String getAssetCash() {
        return this.assetCash;
    }

    public KamalSociety assetCash(String assetCash) {
        this.setAssetCash(assetCash);
        return this;
    }

    public void setAssetCash(String assetCash) {
        this.assetCash = assetCash;
    }

    public String getAssetInvestment() {
        return this.assetInvestment;
    }

    public KamalSociety assetInvestment(String assetInvestment) {
        this.setAssetInvestment(assetInvestment);
        return this;
    }

    public void setAssetInvestment(String assetInvestment) {
        this.assetInvestment = assetInvestment;
    }

    public String getAssetImaratFund() {
        return this.assetImaratFund;
    }

    public KamalSociety assetImaratFund(String assetImaratFund) {
        this.setAssetImaratFund(assetImaratFund);
        return this;
    }

    public void setAssetImaratFund(String assetImaratFund) {
        this.assetImaratFund = assetImaratFund;
    }

    public String getAssetMemberLoan() {
        return this.assetMemberLoan;
    }

    public KamalSociety assetMemberLoan(String assetMemberLoan) {
        this.setAssetMemberLoan(assetMemberLoan);
        return this;
    }

    public void setAssetMemberLoan(String assetMemberLoan) {
        this.assetMemberLoan = assetMemberLoan;
    }

    public String getAssetDeadStock() {
        return this.assetDeadStock;
    }

    public KamalSociety assetDeadStock(String assetDeadStock) {
        this.setAssetDeadStock(assetDeadStock);
        return this;
    }

    public void setAssetDeadStock(String assetDeadStock) {
        this.assetDeadStock = assetDeadStock;
    }

    public String getAssetOtherReceivable() {
        return this.assetOtherReceivable;
    }

    public KamalSociety assetOtherReceivable(String assetOtherReceivable) {
        this.setAssetOtherReceivable(assetOtherReceivable);
        return this;
    }

    public void setAssetOtherReceivable(String assetOtherReceivable) {
        this.assetOtherReceivable = assetOtherReceivable;
    }

    public String getAssetLoss() {
        return this.assetLoss;
    }

    public KamalSociety assetLoss(String assetLoss) {
        this.setAssetLoss(assetLoss);
        return this;
    }

    public void setAssetLoss(String assetLoss) {
        this.assetLoss = assetLoss;
    }

    public String getTotalLiability() {
        return this.totalLiability;
    }

    public KamalSociety totalLiability(String totalLiability) {
        this.setTotalLiability(totalLiability);
        return this;
    }

    public void setTotalLiability(String totalLiability) {
        this.totalLiability = totalLiability;
    }

    public String getTotalAsset() {
        return this.totalAsset;
    }

    public KamalSociety totalAsset(String totalAsset) {
        this.setTotalAsset(totalAsset);
        return this;
    }

    public void setTotalAsset(String totalAsset) {
        this.totalAsset = totalAsset;
    }

    public String getVillageCode() {
        return this.villageCode;
    }

    public KamalSociety villageCode(String villageCode) {
        this.setVillageCode(villageCode);
        return this;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public Boolean getPacsVerifiedFlag() {
        return this.pacsVerifiedFlag;
    }

    public KamalSociety pacsVerifiedFlag(Boolean pacsVerifiedFlag) {
        this.setPacsVerifiedFlag(pacsVerifiedFlag);
        return this;
    }

    public void setPacsVerifiedFlag(Boolean pacsVerifiedFlag) {
        this.pacsVerifiedFlag = pacsVerifiedFlag;
    }

    public Boolean getBranchVerifiedFlag() {
        return this.branchVerifiedFlag;
    }

    public KamalSociety branchVerifiedFlag(Boolean branchVerifiedFlag) {
        this.setBranchVerifiedFlag(branchVerifiedFlag);
        return this;
    }

    public void setBranchVerifiedFlag(Boolean branchVerifiedFlag) {
        this.branchVerifiedFlag = branchVerifiedFlag;
    }

    public Boolean getHeadOfficeVerifiedFlag() {
        return this.headOfficeVerifiedFlag;
    }

    public KamalSociety headOfficeVerifiedFlag(Boolean headOfficeVerifiedFlag) {
        this.setHeadOfficeVerifiedFlag(headOfficeVerifiedFlag);
        return this;
    }

    public void setHeadOfficeVerifiedFlag(Boolean headOfficeVerifiedFlag) {
        this.headOfficeVerifiedFlag = headOfficeVerifiedFlag;
    }

    public Boolean getIsSupplimenteryFlag() {
        return this.isSupplimenteryFlag;
    }

    public KamalSociety isSupplimenteryFlag(Boolean isSupplimenteryFlag) {
        this.setIsSupplimenteryFlag(isSupplimenteryFlag);
        return this;
    }

    public void setIsSupplimenteryFlag(Boolean isSupplimenteryFlag) {
        this.isSupplimenteryFlag = isSupplimenteryFlag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KamalSociety)) {
            return false;
        }
        return id != null && id.equals(((KamalSociety) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalSociety{" +
            "id=" + getId() +
            ", financialYear='" + getFinancialYear() + "'" +
            ", kmDate='" + getKmDate() + "'" +
            ", kmDateMr='" + getKmDateMr() + "'" +
            ", kmFromDate='" + getKmFromDate() + "'" +
            ", kmFromDateMr='" + getKmFromDateMr() + "'" +
            ", kmToDate='" + getKmToDate() + "'" +
            ", kmToDateMr='" + getKmToDateMr() + "'" +
            ", pacsNumber='" + getPacsNumber() + "'" +
            ", pacsName='" + getPacsName() + "'" +
            ", branchId=" + getBranchId() +
            ", branchName='" + getBranchName() + "'" +
            ", zindagiPatrakDate='" + getZindagiPatrakDate() + "'" +
            ", zindagiPatrakDateMr='" + getZindagiPatrakDateMr() + "'" +
            ", bankTapasaniDate='" + getBankTapasaniDate() + "'" +
            ", bankTapasaniDateMr='" + getBankTapasaniDateMr() + "'" +
            ", govTapasaniDate='" + getGovTapasaniDate() + "'" +
            ", govTapasaniDateMr='" + getGovTapasaniDateMr() + "'" +
            ", sansthaTapasaniDate='" + getSansthaTapasaniDate() + "'" +
            ", sansthaTapasaniDateMr='" + getSansthaTapasaniDateMr() + "'" +
            ", totalLand='" + getTotalLand() + "'" +
            ", bagayat='" + getBagayat() + "'" +
            ", jirayat='" + getJirayat() + "'" +
            ", totalFarmer='" + getTotalFarmer() + "'" +
            ", memberFarmer='" + getMemberFarmer() + "'" +
            ", nonMemberFarmer='" + getNonMemberFarmer() + "'" +
            ", talebandDate='" + getTalebandDate() + "'" +
            ", memLoan='" + getMemLoan() + "'" +
            ", memDue='" + getMemDue() + "'" +
            ", memVasuli='" + getMemVasuli() + "'" +
            ", memVasuliPer='" + getMemVasuliPer() + "'" +
            ", bankLoan='" + getBankLoan() + "'" +
            ", bankDue='" + getBankDue() + "'" +
            ", bankVasuli='" + getBankVasuli() + "'" +
            ", bankVasuliPer='" + getBankVasuliPer() + "'" +
            ", balanceSheetDate='" + getBalanceSheetDate() + "'" +
            ", balanceSheetDateMr='" + getBalanceSheetDateMr() + "'" +
            ", liabilityAdhikrutShareCapital='" + getLiabilityAdhikrutShareCapital() + "'" +
            ", liabilityVasulShareCapital='" + getLiabilityVasulShareCapital() + "'" +
            ", liabilityFund='" + getLiabilityFund() + "'" +
            ", liabilitySpareFund='" + getLiabilitySpareFund() + "'" +
            ", liabilityDeposite='" + getLiabilityDeposite() + "'" +
            ", liabilityBalanceSheetBankLoan='" + getLiabilityBalanceSheetBankLoan() + "'" +
            ", liabilityOtherPayable='" + getLiabilityOtherPayable() + "'" +
            ", liabilityProfit='" + getLiabilityProfit() + "'" +
            ", assetCash='" + getAssetCash() + "'" +
            ", assetInvestment='" + getAssetInvestment() + "'" +
            ", assetImaratFund='" + getAssetImaratFund() + "'" +
            ", assetMemberLoan='" + getAssetMemberLoan() + "'" +
            ", assetDeadStock='" + getAssetDeadStock() + "'" +
            ", assetOtherReceivable='" + getAssetOtherReceivable() + "'" +
            ", assetLoss='" + getAssetLoss() + "'" +
            ", totalLiability='" + getTotalLiability() + "'" +
            ", totalAsset='" + getTotalAsset() + "'" +
            ", villageCode='" + getVillageCode() + "'" +
            ", pacsVerifiedFlag='" + getPacsVerifiedFlag() + "'" +
            ", branchVerifiedFlag='" + getBranchVerifiedFlag() + "'" +
            ", headOfficeVerifiedFlag='" + getHeadOfficeVerifiedFlag() + "'" +
            ", isSupplimenteryFlag='" + getIsSupplimenteryFlag() + "'" +
            "}";
    }
}
