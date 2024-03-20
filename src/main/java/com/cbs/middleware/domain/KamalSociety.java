package com.cbs.middleware.domain;

import com.cbs.middleware.service.criteria.KamalSocietyCriteria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A KamalSociety.
 */
@Entity
@Table(name = "kamal_society")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalSociety extends AbstractAuditingEntity<Long> implements Serializable {

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
    @Column(name = "taluka_id")
    private Long talukaId;

    @Column(name = "taluka_name")
    private String talukaName;

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

    @Column(name = "sanstha_tapasani_varg")
    private String sansthaTapasaniVarg;


    @Column(name = "branch_verified_by")
    private String branchVerifiedBy;


    @Column(name = "branch_verified_date")
    private Instant branchVerifiedDate;

    @Column(name = "head_office_verified_by")
    private String headOfficeVerifiedBy;


    @Column(name = "head_office_verified_date")
    private Instant headOfficeVerifiedDate;

    @Column(name = "divisional_office_verified_flag")
    private Boolean divisionalOfficeVerifiedFlag;

    @Column(name = "divisional_office_verified_by")
    private String divisionalOfficeVerifiedBy;

    @Column(name = "divisional_office_verified_date")
    private Instant divisionalOfficeVerifiedDate;

    @Column(name = "agri_admin_verified_flag")
    private Boolean agriAdminVerifiedFlag;

    @Column(name = "agri_admin_verified_by")
    private String agriAdminVerifiedBy;

    @Column(name = "agri_admin_verified_date")
    private Instant agriAdminVerifiedDate;

    @Column(name = "dosh_purtata_date")
    private Instant doshPurtataDate;

    @Column(name = "gambhir_dosh")
    private String gambhirDosh;

    @Column(name = "branch_inward_number")
    private String branchInwardNumber;

    @Column(name = "branch_inward_date")
    private Instant branchInwardDate;

    @Column(name = "branch_outward_number")
    private String branchOutwardNumber;

    @Column(name = "branch_outward_date")
    private Instant branchOutwardDate;


    @Column(name = "head_office_inward_number")
    private String headOfficeInwardNumber;

    @Column(name = "head_office_inward_date")
    private Instant headOfficeInwardDate;


    @Column(name = "head_office_outward_number")
    private String headOfficeOutwardNumber;

    @Column(name = "head_office_outward_date")
    private Instant headOfficeOutwardDate;


    @Column(name = "tharav_number")
    private String tharavNumber;


    @Column(name = "tharav_date")
    private Instant tharavDate;

    @Column(name = "kamal_karj_marayada_amount")
    private Double kamalKarjMarayadaAmount;


    @OneToMany(mappedBy = "kamalSociety", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"kamalSociety"}, allowSetters = true)
    private Set<KamalCrop> kamalCrops = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public Double getKamalKarjMarayadaAmount() {
        return kamalKarjMarayadaAmount;
    }

    public void setKamalKarjMarayadaAmount(Double kamalKarjMarayadaAmount) {
        this.kamalKarjMarayadaAmount = kamalKarjMarayadaAmount;
    }

    public KamalSociety kamalKarjMarayadaAmount(Double kamalKarjMarayadaAmount) {
        this.setKamalKarjMarayadaAmount(kamalKarjMarayadaAmount);
        return this;
    }

    public Set<KamalCrop> getKamalCrops() {
        return this.kamalCrops;
    }

    public void setKamalCrops(Set<KamalCrop> kamalCrops) {
        if (this.kamalCrops != null) {
            this.kamalCrops.forEach(i -> i.setKamalSociety(null));
        }
        if (kamalCrops != null) {
            kamalCrops.forEach(i -> i.setKamalSociety(this));
        }
        this.kamalCrops = kamalCrops;
    }

    public KamalSociety kamalCrops(Set<KamalCrop> kamalCrops) {
        this.setKamalCrops(kamalCrops);
        return this;
    }

    public KamalSociety addKamalCrop(KamalCrop kamalCrop) {
        this.kamalCrops.add(kamalCrop);
        kamalCrop.setKamalSociety(this);
        return this;
    }

    public KamalSociety removeKamalCrop(KamalCrop kamalCrop) {
        this.kamalCrops.remove(kamalCrop);
        kamalCrop.setKamalSociety(null);
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KamalSociety id(Long id) {
        this.setId(id);
        return this;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public KamalSociety financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public Instant getKmDate() {
        return this.kmDate;
    }

    public void setKmDate(Instant kmDate) {
        this.kmDate = kmDate;
    }

    public KamalSociety kmDate(Instant kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public String getKmDateMr() {
        return this.kmDateMr;
    }

    public void setKmDateMr(String kmDateMr) {
        this.kmDateMr = kmDateMr;
    }

    public KamalSociety kmDateMr(String kmDateMr) {
        this.setKmDateMr(kmDateMr);
        return this;
    }

    public Instant getKmFromDate() {
        return this.kmFromDate;
    }

    public void setKmFromDate(Instant kmFromDate) {
        this.kmFromDate = kmFromDate;
    }

    public KamalSociety kmFromDate(Instant kmFromDate) {
        this.setKmFromDate(kmFromDate);
        return this;
    }

    public String getKmFromDateMr() {
        return this.kmFromDateMr;
    }

    public void setKmFromDateMr(String kmFromDateMr) {
        this.kmFromDateMr = kmFromDateMr;
    }

    public KamalSociety kmFromDateMr(String kmFromDateMr) {
        this.setKmFromDateMr(kmFromDateMr);
        return this;
    }

    public Instant getKmToDate() {
        return this.kmToDate;
    }

    public void setKmToDate(Instant kmToDate) {
        this.kmToDate = kmToDate;
    }

    public KamalSociety kmToDate(Instant kmToDate) {
        this.setKmToDate(kmToDate);
        return this;
    }

    public String getKmToDateMr() {
        return this.kmToDateMr;
    }

    public void setKmToDateMr(String kmToDateMr) {
        this.kmToDateMr = kmToDateMr;
    }

    public KamalSociety kmToDateMr(String kmToDateMr) {
        this.setKmToDateMr(kmToDateMr);
        return this;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public KamalSociety pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public String getPacsName() {
        return this.pacsName;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public KamalSociety pacsName(String pacsName) {
        this.setPacsName(pacsName);
        return this;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public KamalSociety branchId(Long branchId) {
        this.setBranchId(branchId);
        return this;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public KamalSociety branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public Instant getZindagiPatrakDate() {
        return this.zindagiPatrakDate;
    }

    public void setZindagiPatrakDate(Instant zindagiPatrakDate) {
        this.zindagiPatrakDate = zindagiPatrakDate;
    }

    public KamalSociety zindagiPatrakDate(Instant zindagiPatrakDate) {
        this.setZindagiPatrakDate(zindagiPatrakDate);
        return this;
    }

    public String getZindagiPatrakDateMr() {
        return this.zindagiPatrakDateMr;
    }

    public void setZindagiPatrakDateMr(String zindagiPatrakDateMr) {
        this.zindagiPatrakDateMr = zindagiPatrakDateMr;
    }

    public KamalSociety zindagiPatrakDateMr(String zindagiPatrakDateMr) {
        this.setZindagiPatrakDateMr(zindagiPatrakDateMr);
        return this;
    }

    public Instant getBankTapasaniDate() {
        return this.bankTapasaniDate;
    }

    public void setBankTapasaniDate(Instant bankTapasaniDate) {
        this.bankTapasaniDate = bankTapasaniDate;
    }

    public KamalSociety bankTapasaniDate(Instant bankTapasaniDate) {
        this.setBankTapasaniDate(bankTapasaniDate);
        return this;
    }

    public String getBankTapasaniDateMr() {
        return this.bankTapasaniDateMr;
    }

    public void setBankTapasaniDateMr(String bankTapasaniDateMr) {
        this.bankTapasaniDateMr = bankTapasaniDateMr;
    }

    public KamalSociety bankTapasaniDateMr(String bankTapasaniDateMr) {
        this.setBankTapasaniDateMr(bankTapasaniDateMr);
        return this;
    }

    public Instant getGovTapasaniDate() {
        return this.govTapasaniDate;
    }

    public void setGovTapasaniDate(Instant govTapasaniDate) {
        this.govTapasaniDate = govTapasaniDate;
    }

    public KamalSociety govTapasaniDate(Instant govTapasaniDate) {
        this.setGovTapasaniDate(govTapasaniDate);
        return this;
    }

    public String getGovTapasaniDateMr() {
        return this.govTapasaniDateMr;
    }

    public void setGovTapasaniDateMr(String govTapasaniDateMr) {
        this.govTapasaniDateMr = govTapasaniDateMr;
    }

    public KamalSociety govTapasaniDateMr(String govTapasaniDateMr) {
        this.setGovTapasaniDateMr(govTapasaniDateMr);
        return this;
    }

    public Instant getSansthaTapasaniDate() {
        return this.sansthaTapasaniDate;
    }

    public void setSansthaTapasaniDate(Instant sansthaTapasaniDate) {
        this.sansthaTapasaniDate = sansthaTapasaniDate;
    }

    public KamalSociety sansthaTapasaniDate(Instant sansthaTapasaniDate) {
        this.setSansthaTapasaniDate(sansthaTapasaniDate);
        return this;
    }

    public String getSansthaTapasaniDateMr() {
        return this.sansthaTapasaniDateMr;
    }

    public void setSansthaTapasaniDateMr(String sansthaTapasaniDateMr) {
        this.sansthaTapasaniDateMr = sansthaTapasaniDateMr;
    }

    public KamalSociety sansthaTapasaniDateMr(String sansthaTapasaniDateMr) {
        this.setSansthaTapasaniDateMr(sansthaTapasaniDateMr);
        return this;
    }

    public String getTotalLand() {
        return this.totalLand;
    }

    public void setTotalLand(String totalLand) {
        this.totalLand = totalLand;
    }

    public KamalSociety totalLand(String totalLand) {
        this.setTotalLand(totalLand);
        return this;
    }

    public String getBagayat() {
        return this.bagayat;
    }

    public void setBagayat(String bagayat) {
        this.bagayat = bagayat;
    }

    public KamalSociety bagayat(String bagayat) {
        this.setBagayat(bagayat);
        return this;
    }

    public String getJirayat() {
        return this.jirayat;
    }

    public void setJirayat(String jirayat) {
        this.jirayat = jirayat;
    }

    public KamalSociety jirayat(String jirayat) {
        this.setJirayat(jirayat);
        return this;
    }

    public String getTotalFarmer() {
        return this.totalFarmer;
    }

    public void setTotalFarmer(String totalFarmer) {
        this.totalFarmer = totalFarmer;
    }

    public KamalSociety totalFarmer(String totalFarmer) {
        this.setTotalFarmer(totalFarmer);
        return this;
    }

    public String getMemberFarmer() {
        return this.memberFarmer;
    }

    public void setMemberFarmer(String memberFarmer) {
        this.memberFarmer = memberFarmer;
    }

    public KamalSociety memberFarmer(String memberFarmer) {
        this.setMemberFarmer(memberFarmer);
        return this;
    }

    public String getNonMemberFarmer() {
        return this.nonMemberFarmer;
    }

    public void setNonMemberFarmer(String nonMemberFarmer) {
        this.nonMemberFarmer = nonMemberFarmer;
    }

    public KamalSociety nonMemberFarmer(String nonMemberFarmer) {
        this.setNonMemberFarmer(nonMemberFarmer);
        return this;
    }

    public Instant getTalebandDate() {
        return this.talebandDate;
    }

    public void setTalebandDate(Instant talebandDate) {
        this.talebandDate = talebandDate;
    }

    public KamalSociety talebandDate(Instant talebandDate) {
        this.setTalebandDate(talebandDate);
        return this;
    }

    public String getMemLoan() {
        return this.memLoan;
    }

    public void setMemLoan(String memLoan) {
        this.memLoan = memLoan;
    }

    public KamalSociety memLoan(String memLoan) {
        this.setMemLoan(memLoan);
        return this;
    }

    public String getMemDue() {
        return this.memDue;
    }

    public void setMemDue(String memDue) {
        this.memDue = memDue;
    }

    public KamalSociety memDue(String memDue) {
        this.setMemDue(memDue);
        return this;
    }

    public String getMemVasuli() {
        return this.memVasuli;
    }

    public void setMemVasuli(String memVasuli) {
        this.memVasuli = memVasuli;
    }

    public KamalSociety memVasuli(String memVasuli) {
        this.setMemVasuli(memVasuli);
        return this;
    }

    public String getMemVasuliPer() {
        return this.memVasuliPer;
    }

    public void setMemVasuliPer(String memVasuliPer) {
        this.memVasuliPer = memVasuliPer;
    }

    public KamalSociety memVasuliPer(String memVasuliPer) {
        this.setMemVasuliPer(memVasuliPer);
        return this;
    }

    public String getBankLoan() {
        return this.bankLoan;
    }

    public void setBankLoan(String bankLoan) {
        this.bankLoan = bankLoan;
    }

    public KamalSociety bankLoan(String bankLoan) {
        this.setBankLoan(bankLoan);
        return this;
    }

    public String getBankDue() {
        return this.bankDue;
    }

    public void setBankDue(String bankDue) {
        this.bankDue = bankDue;
    }

    public KamalSociety bankDue(String bankDue) {
        this.setBankDue(bankDue);
        return this;
    }

    public String getBankVasuli() {
        return this.bankVasuli;
    }

    public void setBankVasuli(String bankVasuli) {
        this.bankVasuli = bankVasuli;
    }

    public KamalSociety bankVasuli(String bankVasuli) {
        this.setBankVasuli(bankVasuli);
        return this;
    }

    public String getBankVasuliPer() {
        return this.bankVasuliPer;
    }

    public void setBankVasuliPer(String bankVasuliPer) {
        this.bankVasuliPer = bankVasuliPer;
    }

    public KamalSociety bankVasuliPer(String bankVasuliPer) {
        this.setBankVasuliPer(bankVasuliPer);
        return this;
    }

    public Instant getBalanceSheetDate() {
        return this.balanceSheetDate;
    }

    public void setBalanceSheetDate(Instant balanceSheetDate) {
        this.balanceSheetDate = balanceSheetDate;
    }

    public KamalSociety balanceSheetDate(Instant balanceSheetDate) {
        this.setBalanceSheetDate(balanceSheetDate);
        return this;
    }

    public String getBalanceSheetDateMr() {
        return this.balanceSheetDateMr;
    }

    public void setBalanceSheetDateMr(String balanceSheetDateMr) {
        this.balanceSheetDateMr = balanceSheetDateMr;
    }

    public KamalSociety balanceSheetDateMr(String balanceSheetDateMr) {
        this.setBalanceSheetDateMr(balanceSheetDateMr);
        return this;
    }

    public String getLiabilityAdhikrutShareCapital() {
        return this.liabilityAdhikrutShareCapital;
    }

    public void setLiabilityAdhikrutShareCapital(String liabilityAdhikrutShareCapital) {
        this.liabilityAdhikrutShareCapital = liabilityAdhikrutShareCapital;
    }

    public KamalSociety liabilityAdhikrutShareCapital(String liabilityAdhikrutShareCapital) {
        this.setLiabilityAdhikrutShareCapital(liabilityAdhikrutShareCapital);
        return this;
    }

    public String getLiabilityVasulShareCapital() {
        return this.liabilityVasulShareCapital;
    }

    public void setLiabilityVasulShareCapital(String liabilityVasulShareCapital) {
        this.liabilityVasulShareCapital = liabilityVasulShareCapital;
    }

    public KamalSociety liabilityVasulShareCapital(String liabilityVasulShareCapital) {
        this.setLiabilityVasulShareCapital(liabilityVasulShareCapital);
        return this;
    }

    public String getLiabilityFund() {
        return this.liabilityFund;
    }

    public void setLiabilityFund(String liabilityFund) {
        this.liabilityFund = liabilityFund;
    }

    public KamalSociety liabilityFund(String liabilityFund) {
        this.setLiabilityFund(liabilityFund);
        return this;
    }

    public String getLiabilitySpareFund() {
        return this.liabilitySpareFund;
    }

    public void setLiabilitySpareFund(String liabilitySpareFund) {
        this.liabilitySpareFund = liabilitySpareFund;
    }

    public KamalSociety liabilitySpareFund(String liabilitySpareFund) {
        this.setLiabilitySpareFund(liabilitySpareFund);
        return this;
    }

    public String getLiabilityDeposite() {
        return this.liabilityDeposite;
    }

    public void setLiabilityDeposite(String liabilityDeposite) {
        this.liabilityDeposite = liabilityDeposite;
    }

    public KamalSociety liabilityDeposite(String liabilityDeposite) {
        this.setLiabilityDeposite(liabilityDeposite);
        return this;
    }

    public String getLiabilityBalanceSheetBankLoan() {
        return this.liabilityBalanceSheetBankLoan;
    }

    public void setLiabilityBalanceSheetBankLoan(String liabilityBalanceSheetBankLoan) {
        this.liabilityBalanceSheetBankLoan = liabilityBalanceSheetBankLoan;
    }

    public KamalSociety liabilityBalanceSheetBankLoan(String liabilityBalanceSheetBankLoan) {
        this.setLiabilityBalanceSheetBankLoan(liabilityBalanceSheetBankLoan);
        return this;
    }

    public String getLiabilityOtherPayable() {
        return this.liabilityOtherPayable;
    }

    public void setLiabilityOtherPayable(String liabilityOtherPayable) {
        this.liabilityOtherPayable = liabilityOtherPayable;
    }

    public KamalSociety liabilityOtherPayable(String liabilityOtherPayable) {
        this.setLiabilityOtherPayable(liabilityOtherPayable);
        return this;
    }

    public String getLiabilityProfit() {
        return this.liabilityProfit;
    }

    public void setLiabilityProfit(String liabilityProfit) {
        this.liabilityProfit = liabilityProfit;
    }

    public KamalSociety liabilityProfit(String liabilityProfit) {
        this.setLiabilityProfit(liabilityProfit);
        return this;
    }

    public String getAssetCash() {
        return this.assetCash;
    }

    public void setAssetCash(String assetCash) {
        this.assetCash = assetCash;
    }

    public KamalSociety assetCash(String assetCash) {
        this.setAssetCash(assetCash);
        return this;
    }

    public String getAssetInvestment() {
        return this.assetInvestment;
    }

    public void setAssetInvestment(String assetInvestment) {
        this.assetInvestment = assetInvestment;
    }

    public KamalSociety assetInvestment(String assetInvestment) {
        this.setAssetInvestment(assetInvestment);
        return this;
    }

    public String getAssetImaratFund() {
        return this.assetImaratFund;
    }

    public void setAssetImaratFund(String assetImaratFund) {
        this.assetImaratFund = assetImaratFund;
    }

    public KamalSociety assetImaratFund(String assetImaratFund) {
        this.setAssetImaratFund(assetImaratFund);
        return this;
    }

    public String getAssetMemberLoan() {
        return this.assetMemberLoan;
    }

    public void setAssetMemberLoan(String assetMemberLoan) {
        this.assetMemberLoan = assetMemberLoan;
    }

    public KamalSociety assetMemberLoan(String assetMemberLoan) {
        this.setAssetMemberLoan(assetMemberLoan);
        return this;
    }

    public String getAssetDeadStock() {
        return this.assetDeadStock;
    }

    public void setAssetDeadStock(String assetDeadStock) {
        this.assetDeadStock = assetDeadStock;
    }

    public KamalSociety assetDeadStock(String assetDeadStock) {
        this.setAssetDeadStock(assetDeadStock);
        return this;
    }

    public String getAssetOtherReceivable() {
        return this.assetOtherReceivable;
    }

    public void setAssetOtherReceivable(String assetOtherReceivable) {
        this.assetOtherReceivable = assetOtherReceivable;
    }

    public KamalSociety assetOtherReceivable(String assetOtherReceivable) {
        this.setAssetOtherReceivable(assetOtherReceivable);
        return this;
    }

    public String getAssetLoss() {
        return this.assetLoss;
    }

    public void setAssetLoss(String assetLoss) {
        this.assetLoss = assetLoss;
    }

    public KamalSociety assetLoss(String assetLoss) {
        this.setAssetLoss(assetLoss);
        return this;
    }

    public String getTotalLiability() {
        return this.totalLiability;
    }

    public void setTotalLiability(String totalLiability) {
        this.totalLiability = totalLiability;
    }

    public KamalSociety totalLiability(String totalLiability) {
        this.setTotalLiability(totalLiability);
        return this;
    }

    public String getTotalAsset() {
        return this.totalAsset;
    }

    public void setTotalAsset(String totalAsset) {
        this.totalAsset = totalAsset;
    }

    public KamalSociety totalAsset(String totalAsset) {
        this.setTotalAsset(totalAsset);
        return this;
    }

    public String getVillageCode() {
        return this.villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public KamalSociety villageCode(String villageCode) {
        this.setVillageCode(villageCode);
        return this;
    }

    public Boolean getPacsVerifiedFlag() {
        return this.pacsVerifiedFlag;
    }

    public void setPacsVerifiedFlag(Boolean pacsVerifiedFlag) {
        this.pacsVerifiedFlag = pacsVerifiedFlag;
    }

    public KamalSociety pacsVerifiedFlag(Boolean pacsVerifiedFlag) {
        this.setPacsVerifiedFlag(pacsVerifiedFlag);
        return this;
    }

    public Boolean getBranchVerifiedFlag() {
        return this.branchVerifiedFlag;
    }

    public void setBranchVerifiedFlag(Boolean branchVerifiedFlag) {
        this.branchVerifiedFlag = branchVerifiedFlag;
    }

    public KamalSociety branchVerifiedFlag(Boolean branchVerifiedFlag) {
        this.setBranchVerifiedFlag(branchVerifiedFlag);
        return this;
    }

    public Boolean getHeadOfficeVerifiedFlag() {
        return this.headOfficeVerifiedFlag;
    }

    public void setHeadOfficeVerifiedFlag(Boolean headOfficeVerifiedFlag) {
        this.headOfficeVerifiedFlag = headOfficeVerifiedFlag;
    }

    public KamalSociety headOfficeVerifiedFlag(Boolean headOfficeVerifiedFlag) {
        this.setHeadOfficeVerifiedFlag(headOfficeVerifiedFlag);
        return this;
    }

    public Boolean getIsSupplimenteryFlag() {
        return this.isSupplimenteryFlag;
    }

    public void setIsSupplimenteryFlag(Boolean isSupplimenteryFlag) {
        this.isSupplimenteryFlag = isSupplimenteryFlag;
    }

    public KamalSociety isSupplimenteryFlag(Boolean isSupplimenteryFlag) {
        this.setIsSupplimenteryFlag(isSupplimenteryFlag);
        return this;
    }

    public String getSansthaTapasaniVarg() {
        return this.sansthaTapasaniVarg;
    }

    public void setSansthaTapasaniVarg(String sansthaTapasaniVarg) {
        this.sansthaTapasaniVarg = sansthaTapasaniVarg;
    }

    public KamalSociety sansthaTapasaniVarg(String sansthaTapasaniVarg) {
        this.setSansthaTapasaniVarg(sansthaTapasaniVarg);
        return this;
    }

    public String getBranchVerifiedBy() {
        return this.branchVerifiedBy;
    }

    public void setBranchVerifiedBy(String branchVerifiedBy) {
        this.branchVerifiedBy = branchVerifiedBy;
    }

    public KamalSociety branchVerifiedBy(String branchVerifiedBy) {
        this.setBranchVerifiedBy(branchVerifiedBy);
        return this;
    }

    public Instant getBranchVerifiedDate() {
        return this.branchVerifiedDate;
    }

    public void setBranchVerifiedDate(Instant branchVerifiedDate) {
        this.branchVerifiedDate = branchVerifiedDate;
    }

    public KamalSociety branchVerifiedDate(Instant branchVerifiedDate) {
        this.setBranchVerifiedDate(branchVerifiedDate);
        return this;
    }

    public String getHeadOfficeVerifiedBy() {
        return this.headOfficeVerifiedBy;
    }

    public void setHeadOfficeVerifiedBy(String headOfficeVerifiedBy) {
        this.headOfficeVerifiedBy = headOfficeVerifiedBy;
    }

    public KamalSociety headOfficeVerifiedBy(String headOfficeVerifiedBy) {
        this.setHeadOfficeVerifiedBy(headOfficeVerifiedBy);
        return this;
    }

    public Instant getHeadOfficeVerifiedDate() {
        return this.headOfficeVerifiedDate;
    }

    public void setHeadOfficeVerifiedDate(Instant headOfficeVerifiedDate) {
        this.headOfficeVerifiedDate = headOfficeVerifiedDate;
    }

    public KamalSociety headOfficeVerifiedDate(Instant headOfficeVerifiedDate) {
        this.setHeadOfficeVerifiedDate(headOfficeVerifiedDate);
        return this;
    }

    public Long getTalukaId() {
        return this.talukaId;
    }

    public void setTalukaId(Long talukaId) {
        this.talukaId = talukaId;
    }

    public KamalSociety talukaId(Long talukaId) {
        this.setTalukaId(talukaId);
        return this;
    }

    public String getTalukaName() {
        return this.talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public KamalSociety talukaName(String talukaName) {
        this.setTalukaName(talukaName);
        return this;
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


    public Boolean getDivisionalOfficeVerifiedFlag() {
        return this.divisionalOfficeVerifiedFlag;
    }

    public void setDivisionalOfficeVerifiedFlag(Boolean divisionalOfficeVerifiedFlag) {
        this.divisionalOfficeVerifiedFlag = divisionalOfficeVerifiedFlag;
    }

    public KamalSociety divisionalOfficeVerifiedFlag(Boolean divisionalOfficeVerifiedFlag) {
        this.setDivisionalOfficeVerifiedFlag(divisionalOfficeVerifiedFlag);
        return this;
    }

    public String getDivisionalOfficeVerifiedBy() {
        return this.divisionalOfficeVerifiedBy;
    }

    public void setDivisionalOfficeVerifiedBy(String divisionalOfficeVerifiedBy) {
        this.divisionalOfficeVerifiedBy = divisionalOfficeVerifiedBy;
    }

    public KamalSociety divisionalOfficeVerifiedBy(String divisionalOfficeVerifiedBy) {
        this.setDivisionalOfficeVerifiedBy(divisionalOfficeVerifiedBy);
        return this;
    }

    public Instant getDivisionalOfficeVerifiedDate() {
        return this.divisionalOfficeVerifiedDate;
    }

    public void setDivisionalOfficeVerifiedDate(Instant divisionalOfficeVerifiedDate) {
        this.divisionalOfficeVerifiedDate = divisionalOfficeVerifiedDate;
    }

    public KamalSociety divisionalOfficeVerifiedDate(Instant divisionalOfficeVerifiedDate) {
        this.setDivisionalOfficeVerifiedDate(divisionalOfficeVerifiedDate);
        return this;
    }

    public Instant getDoshPurtataDate() {
        return this.doshPurtataDate;
    }

    public void setDoshPurtataDate(Instant doshPurtataDate) {
        this.doshPurtataDate = doshPurtataDate;
    }

    public KamalSociety doshPurtataDate(Instant doshPurtataDate) {
        this.setDoshPurtataDate(doshPurtataDate);
        return this;
    }

    public String getGambhirDosh() {
        return this.gambhirDosh;
    }

    public void setGambhirDosh(String gambhirDosh) {
        this.gambhirDosh = gambhirDosh;
    }

    public KamalSociety gambhirDosh(String gambhirDosh) {
        this.setGambhirDosh(gambhirDosh);
        return this;
    }

    public Boolean getSupplimenteryFlag() {
        return isSupplimenteryFlag;
    }

    public void setSupplimenteryFlag(Boolean supplimenteryFlag) {
        isSupplimenteryFlag = supplimenteryFlag;
    }

    public String getBranchInwardNumber() {
        return branchInwardNumber;
    }

    public void setBranchInwardNumber(String branchInwardNumber) {
        this.branchInwardNumber = branchInwardNumber;
    }

    public Instant getBranchInwardDate() {
        return branchInwardDate;
    }

    public void setBranchInwardDate(Instant branchInwardDate) {
        this.branchInwardDate = branchInwardDate;
    }

    public String getBranchOutwardNumber() {
        return branchOutwardNumber;
    }

    public void setBranchOutwardNumber(String branchOutwardNumber) {
        this.branchOutwardNumber = branchOutwardNumber;
    }

    public Instant getBranchOutwardDate() {
        return branchOutwardDate;
    }

    public void setBranchOutwardDate(Instant branchOutwardDate) {
        this.branchOutwardDate = branchOutwardDate;
    }

    public String getHeadOfficeInwardNumber() {
        return headOfficeInwardNumber;
    }

    public void setHeadOfficeInwardNumber(String headOfficeInwardNumber) {
        this.headOfficeInwardNumber = headOfficeInwardNumber;
    }

    public Instant getHeadOfficeInwardDate() {
        return headOfficeInwardDate;
    }

    public void setHeadOfficeInwardDate(Instant headOfficeInwardDate) {
        this.headOfficeInwardDate = headOfficeInwardDate;
    }

    public String getHeadOfficeOutwardNumber() {
        return headOfficeOutwardNumber;
    }

    public void setHeadOfficeOutwardNumber(String headOfficeOutwardNumber) {
        this.headOfficeOutwardNumber = headOfficeOutwardNumber;
    }

    public Instant getHeadOfficeOutwardDate() {
        return headOfficeOutwardDate;
    }

    public void setHeadOfficeOutwardDate(Instant headOfficeOutwardDate) {
        this.headOfficeOutwardDate = headOfficeOutwardDate;
    }

    public String getTharavNumber() {
        return tharavNumber;
    }

    public void setTharavNumber(String tharavNumber) {
        this.tharavNumber = tharavNumber;
    }

    public Instant getTharavDate() {
        return tharavDate;
    }

    public void setTharavDate(Instant tharavDate) {
        this.tharavDate = tharavDate;
    }

    public Boolean getAgriAdminVerifiedFlag() {
        return this.agriAdminVerifiedFlag;
    }

    public void setAgriAdminVerifiedFlag(Boolean agriAdminVerifiedFlag) {
        this.agriAdminVerifiedFlag = agriAdminVerifiedFlag;
    }

    public KamalSociety agriAdminVerifiedFlag(Boolean agriAdminVerifiedFlag) {
        this.setAgriAdminVerifiedFlag(agriAdminVerifiedFlag);
        return this;
    }

    public String getAgriAdminVerifiedBy() {
        return this.agriAdminVerifiedBy;
    }

    public void setAgriAdminVerifiedBy(String agriAdminVerifiedBy) {
        this.agriAdminVerifiedBy = agriAdminVerifiedBy;
    }

    public KamalSociety agriAdminVerifiedBy(String agriAdminVerifiedBy) {
        this.setAgriAdminVerifiedBy(agriAdminVerifiedBy);
        return this;
    }

    public Instant getAgriAdminVerifiedDate() {
        return this.agriAdminVerifiedDate;
    }

    public void setAgriAdminVerifiedDate(Instant agriAdminVerifiedDate) {
        this.agriAdminVerifiedDate = agriAdminVerifiedDate;
    }

    public KamalSociety agriAdminVerifiedDate(Instant agriAdminVerifiedDate) {
        this.setAgriAdminVerifiedDate(agriAdminVerifiedDate);
        return this;
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
            ", sansthaTapasaniVarg='" + getSansthaTapasaniVarg() + "'" +
            ", branchVerifiedBy='" + getBranchVerifiedBy() + "'" +
            ", branchVerifiedDate='" + getBranchVerifiedDate() + "'" +
            ", headOfficeVerifiedBy='" + getHeadOfficeVerifiedBy() + "'" +
            ", headOfficeVerifiedDate='" + getHeadOfficeVerifiedDate() + "'" +
            ", divisionalOfficeVerifiedBy='" + getDivisionalOfficeVerifiedBy() + "'" +
            ", divisionalOfficeVerifiedDate='" + getDivisionalOfficeVerifiedDate() + "'" +
            ", doshPurtataDate='" + getDoshPurtataDate() + "'" +
            ", gambhirDosh='" + getGambhirDosh() + "'" +
            ", divisionalOfficeVerifiedFlag='" + getDivisionalOfficeVerifiedFlag() + "'" +
            ", talukaId=" + getTalukaId() +
            ", talukaName='" + getTalukaName() + "'" +
            "}";
    }


    public LocalDate instantToLocalDate(Instant instantDate) {
        if (instantDate==null) {
            return null;
        }
        ZonedDateTime zonedDateTime = instantDate.atZone(ZoneId.of("Asia/Kolkata"));

        LocalDate localDate = zonedDateTime.toLocalDate();
        return localDate;
    }

}
