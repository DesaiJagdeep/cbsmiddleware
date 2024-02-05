package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KamalSociety} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KamalSocietyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kamal-societies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalSocietyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter financialYear;

    private InstantFilter kmDate;

    private StringFilter kmDateMr;

    private InstantFilter kmFromDate;

    private StringFilter kmFromDateMr;

    private InstantFilter kmToDate;

    private StringFilter kmToDateMr;

    private StringFilter pacsNumber;

    private StringFilter pacsName;

    private LongFilter branchId;

    private StringFilter branchName;

    private InstantFilter zindagiPatrakDate;

    private StringFilter zindagiPatrakDateMr;

    private InstantFilter bankTapasaniDate;

    private StringFilter bankTapasaniDateMr;

    private InstantFilter govTapasaniDate;

    private StringFilter govTapasaniDateMr;

    private InstantFilter sansthaTapasaniDate;

    private StringFilter sansthaTapasaniDateMr;

    private StringFilter totalLand;

    private StringFilter bagayat;

    private StringFilter jirayat;

    private StringFilter totalFarmer;

    private StringFilter memberFarmer;

    private StringFilter nonMemberFarmer;

    private InstantFilter talebandDate;

    private StringFilter memLoan;

    private StringFilter memDue;

    private StringFilter memVasuli;

    private StringFilter memVasuliPer;

    private StringFilter bankLoan;

    private StringFilter bankDue;

    private StringFilter bankVasuli;

    private StringFilter bankVasuliPer;

    private InstantFilter balanceSheetDate;

    private StringFilter balanceSheetDateMr;

    private StringFilter liabilityAdhikrutShareCapital;

    private StringFilter liabilityVasulShareCapital;

    private StringFilter liabilityFund;

    private StringFilter liabilitySpareFund;

    private StringFilter liabilityDeposite;

    private StringFilter liabilityBalanceSheetBankLoan;

    private StringFilter liabilityOtherPayable;

    private StringFilter liabilityProfit;

    private StringFilter assetCash;

    private StringFilter assetInvestment;

    private StringFilter assetImaratFund;

    private StringFilter assetMemberLoan;

    private StringFilter assetDeadStock;

    private StringFilter assetOtherReceivable;

    private StringFilter assetLoss;

    private StringFilter totalLiability;

    private StringFilter totalAsset;

    private StringFilter villageCode;

    private BooleanFilter pacsVerifiedFlag;

    private BooleanFilter branchVerifiedFlag;

    private BooleanFilter headOfficeVerifiedFlag;

    private BooleanFilter isSupplimenteryFlag;

    private Boolean distinct;

    public KamalSocietyCriteria() {}

    public KamalSocietyCriteria(KamalSocietyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.kmDate = other.kmDate == null ? null : other.kmDate.copy();
        this.kmDateMr = other.kmDateMr == null ? null : other.kmDateMr.copy();
        this.kmFromDate = other.kmFromDate == null ? null : other.kmFromDate.copy();
        this.kmFromDateMr = other.kmFromDateMr == null ? null : other.kmFromDateMr.copy();
        this.kmToDate = other.kmToDate == null ? null : other.kmToDate.copy();
        this.kmToDateMr = other.kmToDateMr == null ? null : other.kmToDateMr.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.pacsName = other.pacsName == null ? null : other.pacsName.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.zindagiPatrakDate = other.zindagiPatrakDate == null ? null : other.zindagiPatrakDate.copy();
        this.zindagiPatrakDateMr = other.zindagiPatrakDateMr == null ? null : other.zindagiPatrakDateMr.copy();
        this.bankTapasaniDate = other.bankTapasaniDate == null ? null : other.bankTapasaniDate.copy();
        this.bankTapasaniDateMr = other.bankTapasaniDateMr == null ? null : other.bankTapasaniDateMr.copy();
        this.govTapasaniDate = other.govTapasaniDate == null ? null : other.govTapasaniDate.copy();
        this.govTapasaniDateMr = other.govTapasaniDateMr == null ? null : other.govTapasaniDateMr.copy();
        this.sansthaTapasaniDate = other.sansthaTapasaniDate == null ? null : other.sansthaTapasaniDate.copy();
        this.sansthaTapasaniDateMr = other.sansthaTapasaniDateMr == null ? null : other.sansthaTapasaniDateMr.copy();
        this.totalLand = other.totalLand == null ? null : other.totalLand.copy();
        this.bagayat = other.bagayat == null ? null : other.bagayat.copy();
        this.jirayat = other.jirayat == null ? null : other.jirayat.copy();
        this.totalFarmer = other.totalFarmer == null ? null : other.totalFarmer.copy();
        this.memberFarmer = other.memberFarmer == null ? null : other.memberFarmer.copy();
        this.nonMemberFarmer = other.nonMemberFarmer == null ? null : other.nonMemberFarmer.copy();
        this.talebandDate = other.talebandDate == null ? null : other.talebandDate.copy();
        this.memLoan = other.memLoan == null ? null : other.memLoan.copy();
        this.memDue = other.memDue == null ? null : other.memDue.copy();
        this.memVasuli = other.memVasuli == null ? null : other.memVasuli.copy();
        this.memVasuliPer = other.memVasuliPer == null ? null : other.memVasuliPer.copy();
        this.bankLoan = other.bankLoan == null ? null : other.bankLoan.copy();
        this.bankDue = other.bankDue == null ? null : other.bankDue.copy();
        this.bankVasuli = other.bankVasuli == null ? null : other.bankVasuli.copy();
        this.bankVasuliPer = other.bankVasuliPer == null ? null : other.bankVasuliPer.copy();
        this.balanceSheetDate = other.balanceSheetDate == null ? null : other.balanceSheetDate.copy();
        this.balanceSheetDateMr = other.balanceSheetDateMr == null ? null : other.balanceSheetDateMr.copy();
        this.liabilityAdhikrutShareCapital =
            other.liabilityAdhikrutShareCapital == null ? null : other.liabilityAdhikrutShareCapital.copy();
        this.liabilityVasulShareCapital = other.liabilityVasulShareCapital == null ? null : other.liabilityVasulShareCapital.copy();
        this.liabilityFund = other.liabilityFund == null ? null : other.liabilityFund.copy();
        this.liabilitySpareFund = other.liabilitySpareFund == null ? null : other.liabilitySpareFund.copy();
        this.liabilityDeposite = other.liabilityDeposite == null ? null : other.liabilityDeposite.copy();
        this.liabilityBalanceSheetBankLoan =
            other.liabilityBalanceSheetBankLoan == null ? null : other.liabilityBalanceSheetBankLoan.copy();
        this.liabilityOtherPayable = other.liabilityOtherPayable == null ? null : other.liabilityOtherPayable.copy();
        this.liabilityProfit = other.liabilityProfit == null ? null : other.liabilityProfit.copy();
        this.assetCash = other.assetCash == null ? null : other.assetCash.copy();
        this.assetInvestment = other.assetInvestment == null ? null : other.assetInvestment.copy();
        this.assetImaratFund = other.assetImaratFund == null ? null : other.assetImaratFund.copy();
        this.assetMemberLoan = other.assetMemberLoan == null ? null : other.assetMemberLoan.copy();
        this.assetDeadStock = other.assetDeadStock == null ? null : other.assetDeadStock.copy();
        this.assetOtherReceivable = other.assetOtherReceivable == null ? null : other.assetOtherReceivable.copy();
        this.assetLoss = other.assetLoss == null ? null : other.assetLoss.copy();
        this.totalLiability = other.totalLiability == null ? null : other.totalLiability.copy();
        this.totalAsset = other.totalAsset == null ? null : other.totalAsset.copy();
        this.villageCode = other.villageCode == null ? null : other.villageCode.copy();
        this.pacsVerifiedFlag = other.pacsVerifiedFlag == null ? null : other.pacsVerifiedFlag.copy();
        this.branchVerifiedFlag = other.branchVerifiedFlag == null ? null : other.branchVerifiedFlag.copy();
        this.headOfficeVerifiedFlag = other.headOfficeVerifiedFlag == null ? null : other.headOfficeVerifiedFlag.copy();
        this.isSupplimenteryFlag = other.isSupplimenteryFlag == null ? null : other.isSupplimenteryFlag.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KamalSocietyCriteria copy() {
        return new KamalSocietyCriteria(this);
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

    public InstantFilter getKmDate() {
        return kmDate;
    }

    public InstantFilter kmDate() {
        if (kmDate == null) {
            kmDate = new InstantFilter();
        }
        return kmDate;
    }

    public void setKmDate(InstantFilter kmDate) {
        this.kmDate = kmDate;
    }

    public StringFilter getKmDateMr() {
        return kmDateMr;
    }

    public StringFilter kmDateMr() {
        if (kmDateMr == null) {
            kmDateMr = new StringFilter();
        }
        return kmDateMr;
    }

    public void setKmDateMr(StringFilter kmDateMr) {
        this.kmDateMr = kmDateMr;
    }

    public InstantFilter getKmFromDate() {
        return kmFromDate;
    }

    public InstantFilter kmFromDate() {
        if (kmFromDate == null) {
            kmFromDate = new InstantFilter();
        }
        return kmFromDate;
    }

    public void setKmFromDate(InstantFilter kmFromDate) {
        this.kmFromDate = kmFromDate;
    }

    public StringFilter getKmFromDateMr() {
        return kmFromDateMr;
    }

    public StringFilter kmFromDateMr() {
        if (kmFromDateMr == null) {
            kmFromDateMr = new StringFilter();
        }
        return kmFromDateMr;
    }

    public void setKmFromDateMr(StringFilter kmFromDateMr) {
        this.kmFromDateMr = kmFromDateMr;
    }

    public InstantFilter getKmToDate() {
        return kmToDate;
    }

    public InstantFilter kmToDate() {
        if (kmToDate == null) {
            kmToDate = new InstantFilter();
        }
        return kmToDate;
    }

    public void setKmToDate(InstantFilter kmToDate) {
        this.kmToDate = kmToDate;
    }

    public StringFilter getKmToDateMr() {
        return kmToDateMr;
    }

    public StringFilter kmToDateMr() {
        if (kmToDateMr == null) {
            kmToDateMr = new StringFilter();
        }
        return kmToDateMr;
    }

    public void setKmToDateMr(StringFilter kmToDateMr) {
        this.kmToDateMr = kmToDateMr;
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

    public LongFilter getBranchId() {
        return branchId;
    }

    public LongFilter branchId() {
        if (branchId == null) {
            branchId = new LongFilter();
        }
        return branchId;
    }

    public void setBranchId(LongFilter branchId) {
        this.branchId = branchId;
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

    public InstantFilter getZindagiPatrakDate() {
        return zindagiPatrakDate;
    }

    public InstantFilter zindagiPatrakDate() {
        if (zindagiPatrakDate == null) {
            zindagiPatrakDate = new InstantFilter();
        }
        return zindagiPatrakDate;
    }

    public void setZindagiPatrakDate(InstantFilter zindagiPatrakDate) {
        this.zindagiPatrakDate = zindagiPatrakDate;
    }

    public StringFilter getZindagiPatrakDateMr() {
        return zindagiPatrakDateMr;
    }

    public StringFilter zindagiPatrakDateMr() {
        if (zindagiPatrakDateMr == null) {
            zindagiPatrakDateMr = new StringFilter();
        }
        return zindagiPatrakDateMr;
    }

    public void setZindagiPatrakDateMr(StringFilter zindagiPatrakDateMr) {
        this.zindagiPatrakDateMr = zindagiPatrakDateMr;
    }

    public InstantFilter getBankTapasaniDate() {
        return bankTapasaniDate;
    }

    public InstantFilter bankTapasaniDate() {
        if (bankTapasaniDate == null) {
            bankTapasaniDate = new InstantFilter();
        }
        return bankTapasaniDate;
    }

    public void setBankTapasaniDate(InstantFilter bankTapasaniDate) {
        this.bankTapasaniDate = bankTapasaniDate;
    }

    public StringFilter getBankTapasaniDateMr() {
        return bankTapasaniDateMr;
    }

    public StringFilter bankTapasaniDateMr() {
        if (bankTapasaniDateMr == null) {
            bankTapasaniDateMr = new StringFilter();
        }
        return bankTapasaniDateMr;
    }

    public void setBankTapasaniDateMr(StringFilter bankTapasaniDateMr) {
        this.bankTapasaniDateMr = bankTapasaniDateMr;
    }

    public InstantFilter getGovTapasaniDate() {
        return govTapasaniDate;
    }

    public InstantFilter govTapasaniDate() {
        if (govTapasaniDate == null) {
            govTapasaniDate = new InstantFilter();
        }
        return govTapasaniDate;
    }

    public void setGovTapasaniDate(InstantFilter govTapasaniDate) {
        this.govTapasaniDate = govTapasaniDate;
    }

    public StringFilter getGovTapasaniDateMr() {
        return govTapasaniDateMr;
    }

    public StringFilter govTapasaniDateMr() {
        if (govTapasaniDateMr == null) {
            govTapasaniDateMr = new StringFilter();
        }
        return govTapasaniDateMr;
    }

    public void setGovTapasaniDateMr(StringFilter govTapasaniDateMr) {
        this.govTapasaniDateMr = govTapasaniDateMr;
    }

    public InstantFilter getSansthaTapasaniDate() {
        return sansthaTapasaniDate;
    }

    public InstantFilter sansthaTapasaniDate() {
        if (sansthaTapasaniDate == null) {
            sansthaTapasaniDate = new InstantFilter();
        }
        return sansthaTapasaniDate;
    }

    public void setSansthaTapasaniDate(InstantFilter sansthaTapasaniDate) {
        this.sansthaTapasaniDate = sansthaTapasaniDate;
    }

    public StringFilter getSansthaTapasaniDateMr() {
        return sansthaTapasaniDateMr;
    }

    public StringFilter sansthaTapasaniDateMr() {
        if (sansthaTapasaniDateMr == null) {
            sansthaTapasaniDateMr = new StringFilter();
        }
        return sansthaTapasaniDateMr;
    }

    public void setSansthaTapasaniDateMr(StringFilter sansthaTapasaniDateMr) {
        this.sansthaTapasaniDateMr = sansthaTapasaniDateMr;
    }

    public StringFilter getTotalLand() {
        return totalLand;
    }

    public StringFilter totalLand() {
        if (totalLand == null) {
            totalLand = new StringFilter();
        }
        return totalLand;
    }

    public void setTotalLand(StringFilter totalLand) {
        this.totalLand = totalLand;
    }

    public StringFilter getBagayat() {
        return bagayat;
    }

    public StringFilter bagayat() {
        if (bagayat == null) {
            bagayat = new StringFilter();
        }
        return bagayat;
    }

    public void setBagayat(StringFilter bagayat) {
        this.bagayat = bagayat;
    }

    public StringFilter getJirayat() {
        return jirayat;
    }

    public StringFilter jirayat() {
        if (jirayat == null) {
            jirayat = new StringFilter();
        }
        return jirayat;
    }

    public void setJirayat(StringFilter jirayat) {
        this.jirayat = jirayat;
    }

    public StringFilter getTotalFarmer() {
        return totalFarmer;
    }

    public StringFilter totalFarmer() {
        if (totalFarmer == null) {
            totalFarmer = new StringFilter();
        }
        return totalFarmer;
    }

    public void setTotalFarmer(StringFilter totalFarmer) {
        this.totalFarmer = totalFarmer;
    }

    public StringFilter getMemberFarmer() {
        return memberFarmer;
    }

    public StringFilter memberFarmer() {
        if (memberFarmer == null) {
            memberFarmer = new StringFilter();
        }
        return memberFarmer;
    }

    public void setMemberFarmer(StringFilter memberFarmer) {
        this.memberFarmer = memberFarmer;
    }

    public StringFilter getNonMemberFarmer() {
        return nonMemberFarmer;
    }

    public StringFilter nonMemberFarmer() {
        if (nonMemberFarmer == null) {
            nonMemberFarmer = new StringFilter();
        }
        return nonMemberFarmer;
    }

    public void setNonMemberFarmer(StringFilter nonMemberFarmer) {
        this.nonMemberFarmer = nonMemberFarmer;
    }

    public InstantFilter getTalebandDate() {
        return talebandDate;
    }

    public InstantFilter talebandDate() {
        if (talebandDate == null) {
            talebandDate = new InstantFilter();
        }
        return talebandDate;
    }

    public void setTalebandDate(InstantFilter talebandDate) {
        this.talebandDate = talebandDate;
    }

    public StringFilter getMemLoan() {
        return memLoan;
    }

    public StringFilter memLoan() {
        if (memLoan == null) {
            memLoan = new StringFilter();
        }
        return memLoan;
    }

    public void setMemLoan(StringFilter memLoan) {
        this.memLoan = memLoan;
    }

    public StringFilter getMemDue() {
        return memDue;
    }

    public StringFilter memDue() {
        if (memDue == null) {
            memDue = new StringFilter();
        }
        return memDue;
    }

    public void setMemDue(StringFilter memDue) {
        this.memDue = memDue;
    }

    public StringFilter getMemVasuli() {
        return memVasuli;
    }

    public StringFilter memVasuli() {
        if (memVasuli == null) {
            memVasuli = new StringFilter();
        }
        return memVasuli;
    }

    public void setMemVasuli(StringFilter memVasuli) {
        this.memVasuli = memVasuli;
    }

    public StringFilter getMemVasuliPer() {
        return memVasuliPer;
    }

    public StringFilter memVasuliPer() {
        if (memVasuliPer == null) {
            memVasuliPer = new StringFilter();
        }
        return memVasuliPer;
    }

    public void setMemVasuliPer(StringFilter memVasuliPer) {
        this.memVasuliPer = memVasuliPer;
    }

    public StringFilter getBankLoan() {
        return bankLoan;
    }

    public StringFilter bankLoan() {
        if (bankLoan == null) {
            bankLoan = new StringFilter();
        }
        return bankLoan;
    }

    public void setBankLoan(StringFilter bankLoan) {
        this.bankLoan = bankLoan;
    }

    public StringFilter getBankDue() {
        return bankDue;
    }

    public StringFilter bankDue() {
        if (bankDue == null) {
            bankDue = new StringFilter();
        }
        return bankDue;
    }

    public void setBankDue(StringFilter bankDue) {
        this.bankDue = bankDue;
    }

    public StringFilter getBankVasuli() {
        return bankVasuli;
    }

    public StringFilter bankVasuli() {
        if (bankVasuli == null) {
            bankVasuli = new StringFilter();
        }
        return bankVasuli;
    }

    public void setBankVasuli(StringFilter bankVasuli) {
        this.bankVasuli = bankVasuli;
    }

    public StringFilter getBankVasuliPer() {
        return bankVasuliPer;
    }

    public StringFilter bankVasuliPer() {
        if (bankVasuliPer == null) {
            bankVasuliPer = new StringFilter();
        }
        return bankVasuliPer;
    }

    public void setBankVasuliPer(StringFilter bankVasuliPer) {
        this.bankVasuliPer = bankVasuliPer;
    }

    public InstantFilter getBalanceSheetDate() {
        return balanceSheetDate;
    }

    public InstantFilter balanceSheetDate() {
        if (balanceSheetDate == null) {
            balanceSheetDate = new InstantFilter();
        }
        return balanceSheetDate;
    }

    public void setBalanceSheetDate(InstantFilter balanceSheetDate) {
        this.balanceSheetDate = balanceSheetDate;
    }

    public StringFilter getBalanceSheetDateMr() {
        return balanceSheetDateMr;
    }

    public StringFilter balanceSheetDateMr() {
        if (balanceSheetDateMr == null) {
            balanceSheetDateMr = new StringFilter();
        }
        return balanceSheetDateMr;
    }

    public void setBalanceSheetDateMr(StringFilter balanceSheetDateMr) {
        this.balanceSheetDateMr = balanceSheetDateMr;
    }

    public StringFilter getLiabilityAdhikrutShareCapital() {
        return liabilityAdhikrutShareCapital;
    }

    public StringFilter liabilityAdhikrutShareCapital() {
        if (liabilityAdhikrutShareCapital == null) {
            liabilityAdhikrutShareCapital = new StringFilter();
        }
        return liabilityAdhikrutShareCapital;
    }

    public void setLiabilityAdhikrutShareCapital(StringFilter liabilityAdhikrutShareCapital) {
        this.liabilityAdhikrutShareCapital = liabilityAdhikrutShareCapital;
    }

    public StringFilter getLiabilityVasulShareCapital() {
        return liabilityVasulShareCapital;
    }

    public StringFilter liabilityVasulShareCapital() {
        if (liabilityVasulShareCapital == null) {
            liabilityVasulShareCapital = new StringFilter();
        }
        return liabilityVasulShareCapital;
    }

    public void setLiabilityVasulShareCapital(StringFilter liabilityVasulShareCapital) {
        this.liabilityVasulShareCapital = liabilityVasulShareCapital;
    }

    public StringFilter getLiabilityFund() {
        return liabilityFund;
    }

    public StringFilter liabilityFund() {
        if (liabilityFund == null) {
            liabilityFund = new StringFilter();
        }
        return liabilityFund;
    }

    public void setLiabilityFund(StringFilter liabilityFund) {
        this.liabilityFund = liabilityFund;
    }

    public StringFilter getLiabilitySpareFund() {
        return liabilitySpareFund;
    }

    public StringFilter liabilitySpareFund() {
        if (liabilitySpareFund == null) {
            liabilitySpareFund = new StringFilter();
        }
        return liabilitySpareFund;
    }

    public void setLiabilitySpareFund(StringFilter liabilitySpareFund) {
        this.liabilitySpareFund = liabilitySpareFund;
    }

    public StringFilter getLiabilityDeposite() {
        return liabilityDeposite;
    }

    public StringFilter liabilityDeposite() {
        if (liabilityDeposite == null) {
            liabilityDeposite = new StringFilter();
        }
        return liabilityDeposite;
    }

    public void setLiabilityDeposite(StringFilter liabilityDeposite) {
        this.liabilityDeposite = liabilityDeposite;
    }

    public StringFilter getLiabilityBalanceSheetBankLoan() {
        return liabilityBalanceSheetBankLoan;
    }

    public StringFilter liabilityBalanceSheetBankLoan() {
        if (liabilityBalanceSheetBankLoan == null) {
            liabilityBalanceSheetBankLoan = new StringFilter();
        }
        return liabilityBalanceSheetBankLoan;
    }

    public void setLiabilityBalanceSheetBankLoan(StringFilter liabilityBalanceSheetBankLoan) {
        this.liabilityBalanceSheetBankLoan = liabilityBalanceSheetBankLoan;
    }

    public StringFilter getLiabilityOtherPayable() {
        return liabilityOtherPayable;
    }

    public StringFilter liabilityOtherPayable() {
        if (liabilityOtherPayable == null) {
            liabilityOtherPayable = new StringFilter();
        }
        return liabilityOtherPayable;
    }

    public void setLiabilityOtherPayable(StringFilter liabilityOtherPayable) {
        this.liabilityOtherPayable = liabilityOtherPayable;
    }

    public StringFilter getLiabilityProfit() {
        return liabilityProfit;
    }

    public StringFilter liabilityProfit() {
        if (liabilityProfit == null) {
            liabilityProfit = new StringFilter();
        }
        return liabilityProfit;
    }

    public void setLiabilityProfit(StringFilter liabilityProfit) {
        this.liabilityProfit = liabilityProfit;
    }

    public StringFilter getAssetCash() {
        return assetCash;
    }

    public StringFilter assetCash() {
        if (assetCash == null) {
            assetCash = new StringFilter();
        }
        return assetCash;
    }

    public void setAssetCash(StringFilter assetCash) {
        this.assetCash = assetCash;
    }

    public StringFilter getAssetInvestment() {
        return assetInvestment;
    }

    public StringFilter assetInvestment() {
        if (assetInvestment == null) {
            assetInvestment = new StringFilter();
        }
        return assetInvestment;
    }

    public void setAssetInvestment(StringFilter assetInvestment) {
        this.assetInvestment = assetInvestment;
    }

    public StringFilter getAssetImaratFund() {
        return assetImaratFund;
    }

    public StringFilter assetImaratFund() {
        if (assetImaratFund == null) {
            assetImaratFund = new StringFilter();
        }
        return assetImaratFund;
    }

    public void setAssetImaratFund(StringFilter assetImaratFund) {
        this.assetImaratFund = assetImaratFund;
    }

    public StringFilter getAssetMemberLoan() {
        return assetMemberLoan;
    }

    public StringFilter assetMemberLoan() {
        if (assetMemberLoan == null) {
            assetMemberLoan = new StringFilter();
        }
        return assetMemberLoan;
    }

    public void setAssetMemberLoan(StringFilter assetMemberLoan) {
        this.assetMemberLoan = assetMemberLoan;
    }

    public StringFilter getAssetDeadStock() {
        return assetDeadStock;
    }

    public StringFilter assetDeadStock() {
        if (assetDeadStock == null) {
            assetDeadStock = new StringFilter();
        }
        return assetDeadStock;
    }

    public void setAssetDeadStock(StringFilter assetDeadStock) {
        this.assetDeadStock = assetDeadStock;
    }

    public StringFilter getAssetOtherReceivable() {
        return assetOtherReceivable;
    }

    public StringFilter assetOtherReceivable() {
        if (assetOtherReceivable == null) {
            assetOtherReceivable = new StringFilter();
        }
        return assetOtherReceivable;
    }

    public void setAssetOtherReceivable(StringFilter assetOtherReceivable) {
        this.assetOtherReceivable = assetOtherReceivable;
    }

    public StringFilter getAssetLoss() {
        return assetLoss;
    }

    public StringFilter assetLoss() {
        if (assetLoss == null) {
            assetLoss = new StringFilter();
        }
        return assetLoss;
    }

    public void setAssetLoss(StringFilter assetLoss) {
        this.assetLoss = assetLoss;
    }

    public StringFilter getTotalLiability() {
        return totalLiability;
    }

    public StringFilter totalLiability() {
        if (totalLiability == null) {
            totalLiability = new StringFilter();
        }
        return totalLiability;
    }

    public void setTotalLiability(StringFilter totalLiability) {
        this.totalLiability = totalLiability;
    }

    public StringFilter getTotalAsset() {
        return totalAsset;
    }

    public StringFilter totalAsset() {
        if (totalAsset == null) {
            totalAsset = new StringFilter();
        }
        return totalAsset;
    }

    public void setTotalAsset(StringFilter totalAsset) {
        this.totalAsset = totalAsset;
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

    public BooleanFilter getPacsVerifiedFlag() {
        return pacsVerifiedFlag;
    }

    public BooleanFilter pacsVerifiedFlag() {
        if (pacsVerifiedFlag == null) {
            pacsVerifiedFlag = new BooleanFilter();
        }
        return pacsVerifiedFlag;
    }

    public void setPacsVerifiedFlag(BooleanFilter pacsVerifiedFlag) {
        this.pacsVerifiedFlag = pacsVerifiedFlag;
    }

    public BooleanFilter getBranchVerifiedFlag() {
        return branchVerifiedFlag;
    }

    public BooleanFilter branchVerifiedFlag() {
        if (branchVerifiedFlag == null) {
            branchVerifiedFlag = new BooleanFilter();
        }
        return branchVerifiedFlag;
    }

    public void setBranchVerifiedFlag(BooleanFilter branchVerifiedFlag) {
        this.branchVerifiedFlag = branchVerifiedFlag;
    }

    public BooleanFilter getHeadOfficeVerifiedFlag() {
        return headOfficeVerifiedFlag;
    }

    public BooleanFilter headOfficeVerifiedFlag() {
        if (headOfficeVerifiedFlag == null) {
            headOfficeVerifiedFlag = new BooleanFilter();
        }
        return headOfficeVerifiedFlag;
    }

    public void setHeadOfficeVerifiedFlag(BooleanFilter headOfficeVerifiedFlag) {
        this.headOfficeVerifiedFlag = headOfficeVerifiedFlag;
    }

    public BooleanFilter getIsSupplimenteryFlag() {
        return isSupplimenteryFlag;
    }

    public BooleanFilter isSupplimenteryFlag() {
        if (isSupplimenteryFlag == null) {
            isSupplimenteryFlag = new BooleanFilter();
        }
        return isSupplimenteryFlag;
    }

    public void setIsSupplimenteryFlag(BooleanFilter isSupplimenteryFlag) {
        this.isSupplimenteryFlag = isSupplimenteryFlag;
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
        final KamalSocietyCriteria that = (KamalSocietyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(kmDate, that.kmDate) &&
            Objects.equals(kmDateMr, that.kmDateMr) &&
            Objects.equals(kmFromDate, that.kmFromDate) &&
            Objects.equals(kmFromDateMr, that.kmFromDateMr) &&
            Objects.equals(kmToDate, that.kmToDate) &&
            Objects.equals(kmToDateMr, that.kmToDateMr) &&
            Objects.equals(pacsNumber, that.pacsNumber) &&
            Objects.equals(pacsName, that.pacsName) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(zindagiPatrakDate, that.zindagiPatrakDate) &&
            Objects.equals(zindagiPatrakDateMr, that.zindagiPatrakDateMr) &&
            Objects.equals(bankTapasaniDate, that.bankTapasaniDate) &&
            Objects.equals(bankTapasaniDateMr, that.bankTapasaniDateMr) &&
            Objects.equals(govTapasaniDate, that.govTapasaniDate) &&
            Objects.equals(govTapasaniDateMr, that.govTapasaniDateMr) &&
            Objects.equals(sansthaTapasaniDate, that.sansthaTapasaniDate) &&
            Objects.equals(sansthaTapasaniDateMr, that.sansthaTapasaniDateMr) &&
            Objects.equals(totalLand, that.totalLand) &&
            Objects.equals(bagayat, that.bagayat) &&
            Objects.equals(jirayat, that.jirayat) &&
            Objects.equals(totalFarmer, that.totalFarmer) &&
            Objects.equals(memberFarmer, that.memberFarmer) &&
            Objects.equals(nonMemberFarmer, that.nonMemberFarmer) &&
            Objects.equals(talebandDate, that.talebandDate) &&
            Objects.equals(memLoan, that.memLoan) &&
            Objects.equals(memDue, that.memDue) &&
            Objects.equals(memVasuli, that.memVasuli) &&
            Objects.equals(memVasuliPer, that.memVasuliPer) &&
            Objects.equals(bankLoan, that.bankLoan) &&
            Objects.equals(bankDue, that.bankDue) &&
            Objects.equals(bankVasuli, that.bankVasuli) &&
            Objects.equals(bankVasuliPer, that.bankVasuliPer) &&
            Objects.equals(balanceSheetDate, that.balanceSheetDate) &&
            Objects.equals(balanceSheetDateMr, that.balanceSheetDateMr) &&
            Objects.equals(liabilityAdhikrutShareCapital, that.liabilityAdhikrutShareCapital) &&
            Objects.equals(liabilityVasulShareCapital, that.liabilityVasulShareCapital) &&
            Objects.equals(liabilityFund, that.liabilityFund) &&
            Objects.equals(liabilitySpareFund, that.liabilitySpareFund) &&
            Objects.equals(liabilityDeposite, that.liabilityDeposite) &&
            Objects.equals(liabilityBalanceSheetBankLoan, that.liabilityBalanceSheetBankLoan) &&
            Objects.equals(liabilityOtherPayable, that.liabilityOtherPayable) &&
            Objects.equals(liabilityProfit, that.liabilityProfit) &&
            Objects.equals(assetCash, that.assetCash) &&
            Objects.equals(assetInvestment, that.assetInvestment) &&
            Objects.equals(assetImaratFund, that.assetImaratFund) &&
            Objects.equals(assetMemberLoan, that.assetMemberLoan) &&
            Objects.equals(assetDeadStock, that.assetDeadStock) &&
            Objects.equals(assetOtherReceivable, that.assetOtherReceivable) &&
            Objects.equals(assetLoss, that.assetLoss) &&
            Objects.equals(totalLiability, that.totalLiability) &&
            Objects.equals(totalAsset, that.totalAsset) &&
            Objects.equals(villageCode, that.villageCode) &&
            Objects.equals(pacsVerifiedFlag, that.pacsVerifiedFlag) &&
            Objects.equals(branchVerifiedFlag, that.branchVerifiedFlag) &&
            Objects.equals(headOfficeVerifiedFlag, that.headOfficeVerifiedFlag) &&
            Objects.equals(isSupplimenteryFlag, that.isSupplimenteryFlag) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            financialYear,
            kmDate,
            kmDateMr,
            kmFromDate,
            kmFromDateMr,
            kmToDate,
            kmToDateMr,
            pacsNumber,
            pacsName,
            branchId,
            branchName,
            zindagiPatrakDate,
            zindagiPatrakDateMr,
            bankTapasaniDate,
            bankTapasaniDateMr,
            govTapasaniDate,
            govTapasaniDateMr,
            sansthaTapasaniDate,
            sansthaTapasaniDateMr,
            totalLand,
            bagayat,
            jirayat,
            totalFarmer,
            memberFarmer,
            nonMemberFarmer,
            talebandDate,
            memLoan,
            memDue,
            memVasuli,
            memVasuliPer,
            bankLoan,
            bankDue,
            bankVasuli,
            bankVasuliPer,
            balanceSheetDate,
            balanceSheetDateMr,
            liabilityAdhikrutShareCapital,
            liabilityVasulShareCapital,
            liabilityFund,
            liabilitySpareFund,
            liabilityDeposite,
            liabilityBalanceSheetBankLoan,
            liabilityOtherPayable,
            liabilityProfit,
            assetCash,
            assetInvestment,
            assetImaratFund,
            assetMemberLoan,
            assetDeadStock,
            assetOtherReceivable,
            assetLoss,
            totalLiability,
            totalAsset,
            villageCode,
            pacsVerifiedFlag,
            branchVerifiedFlag,
            headOfficeVerifiedFlag,
            isSupplimenteryFlag,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalSocietyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (kmDate != null ? "kmDate=" + kmDate + ", " : "") +
            (kmDateMr != null ? "kmDateMr=" + kmDateMr + ", " : "") +
            (kmFromDate != null ? "kmFromDate=" + kmFromDate + ", " : "") +
            (kmFromDateMr != null ? "kmFromDateMr=" + kmFromDateMr + ", " : "") +
            (kmToDate != null ? "kmToDate=" + kmToDate + ", " : "") +
            (kmToDateMr != null ? "kmToDateMr=" + kmToDateMr + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (pacsName != null ? "pacsName=" + pacsName + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (zindagiPatrakDate != null ? "zindagiPatrakDate=" + zindagiPatrakDate + ", " : "") +
            (zindagiPatrakDateMr != null ? "zindagiPatrakDateMr=" + zindagiPatrakDateMr + ", " : "") +
            (bankTapasaniDate != null ? "bankTapasaniDate=" + bankTapasaniDate + ", " : "") +
            (bankTapasaniDateMr != null ? "bankTapasaniDateMr=" + bankTapasaniDateMr + ", " : "") +
            (govTapasaniDate != null ? "govTapasaniDate=" + govTapasaniDate + ", " : "") +
            (govTapasaniDateMr != null ? "govTapasaniDateMr=" + govTapasaniDateMr + ", " : "") +
            (sansthaTapasaniDate != null ? "sansthaTapasaniDate=" + sansthaTapasaniDate + ", " : "") +
            (sansthaTapasaniDateMr != null ? "sansthaTapasaniDateMr=" + sansthaTapasaniDateMr + ", " : "") +
            (totalLand != null ? "totalLand=" + totalLand + ", " : "") +
            (bagayat != null ? "bagayat=" + bagayat + ", " : "") +
            (jirayat != null ? "jirayat=" + jirayat + ", " : "") +
            (totalFarmer != null ? "totalFarmer=" + totalFarmer + ", " : "") +
            (memberFarmer != null ? "memberFarmer=" + memberFarmer + ", " : "") +
            (nonMemberFarmer != null ? "nonMemberFarmer=" + nonMemberFarmer + ", " : "") +
            (talebandDate != null ? "talebandDate=" + talebandDate + ", " : "") +
            (memLoan != null ? "memLoan=" + memLoan + ", " : "") +
            (memDue != null ? "memDue=" + memDue + ", " : "") +
            (memVasuli != null ? "memVasuli=" + memVasuli + ", " : "") +
            (memVasuliPer != null ? "memVasuliPer=" + memVasuliPer + ", " : "") +
            (bankLoan != null ? "bankLoan=" + bankLoan + ", " : "") +
            (bankDue != null ? "bankDue=" + bankDue + ", " : "") +
            (bankVasuli != null ? "bankVasuli=" + bankVasuli + ", " : "") +
            (bankVasuliPer != null ? "bankVasuliPer=" + bankVasuliPer + ", " : "") +
            (balanceSheetDate != null ? "balanceSheetDate=" + balanceSheetDate + ", " : "") +
            (balanceSheetDateMr != null ? "balanceSheetDateMr=" + balanceSheetDateMr + ", " : "") +
            (liabilityAdhikrutShareCapital != null ? "liabilityAdhikrutShareCapital=" + liabilityAdhikrutShareCapital + ", " : "") +
            (liabilityVasulShareCapital != null ? "liabilityVasulShareCapital=" + liabilityVasulShareCapital + ", " : "") +
            (liabilityFund != null ? "liabilityFund=" + liabilityFund + ", " : "") +
            (liabilitySpareFund != null ? "liabilitySpareFund=" + liabilitySpareFund + ", " : "") +
            (liabilityDeposite != null ? "liabilityDeposite=" + liabilityDeposite + ", " : "") +
            (liabilityBalanceSheetBankLoan != null ? "liabilityBalanceSheetBankLoan=" + liabilityBalanceSheetBankLoan + ", " : "") +
            (liabilityOtherPayable != null ? "liabilityOtherPayable=" + liabilityOtherPayable + ", " : "") +
            (liabilityProfit != null ? "liabilityProfit=" + liabilityProfit + ", " : "") +
            (assetCash != null ? "assetCash=" + assetCash + ", " : "") +
            (assetInvestment != null ? "assetInvestment=" + assetInvestment + ", " : "") +
            (assetImaratFund != null ? "assetImaratFund=" + assetImaratFund + ", " : "") +
            (assetMemberLoan != null ? "assetMemberLoan=" + assetMemberLoan + ", " : "") +
            (assetDeadStock != null ? "assetDeadStock=" + assetDeadStock + ", " : "") +
            (assetOtherReceivable != null ? "assetOtherReceivable=" + assetOtherReceivable + ", " : "") +
            (assetLoss != null ? "assetLoss=" + assetLoss + ", " : "") +
            (totalLiability != null ? "totalLiability=" + totalLiability + ", " : "") +
            (totalAsset != null ? "totalAsset=" + totalAsset + ", " : "") +
            (villageCode != null ? "villageCode=" + villageCode + ", " : "") +
            (pacsVerifiedFlag != null ? "pacsVerifiedFlag=" + pacsVerifiedFlag + ", " : "") +
            (branchVerifiedFlag != null ? "branchVerifiedFlag=" + branchVerifiedFlag + ", " : "") +
            (headOfficeVerifiedFlag != null ? "headOfficeVerifiedFlag=" + headOfficeVerifiedFlag + ", " : "") +
            (isSupplimenteryFlag != null ? "isSupplimenteryFlag=" + isSupplimenteryFlag + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
