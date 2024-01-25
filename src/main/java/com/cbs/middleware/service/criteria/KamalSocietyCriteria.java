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

    private LongFilter pacsNumber;

    private InstantFilter zindagiDate;

    private StringFilter zindagiDateMr;

    private StringFilter village1;

    private StringFilter village1Mr;

    private StringFilter village2;

    private StringFilter village2Mr;

    private StringFilter village3;

    private StringFilter village3Mr;

    private DoubleFilter totalLand;

    private StringFilter totalLandMr;

    private DoubleFilter totalMem;

    private StringFilter totalMemMr;

    private DoubleFilter totalNonMem;

    private StringFilter totalNonMemMr;

    private DoubleFilter totalGMem;

    private StringFilter totalGMemMr;

    private DoubleFilter memLoan;

    private StringFilter memLoanMr;

    private DoubleFilter memDue;

    private StringFilter memDueMr;

    private DoubleFilter memDueper;

    private StringFilter memDueperMr;

    private DoubleFilter memVasulpatra;

    private StringFilter memVasulpatraMr;

    private DoubleFilter memVasul;

    private StringFilter memVasulMr;

    private DoubleFilter memVasulPer;

    private StringFilter memVasulPerMr;

    private DoubleFilter bankLoan;

    private StringFilter bankLoanMr;

    private DoubleFilter bankDue;

    private StringFilter bankDueMr;

    private DoubleFilter bankDueper;

    private StringFilter bankDueperMr;

    private DoubleFilter bankVasulpatra;

    private StringFilter bankVasulpatraMr;

    private DoubleFilter bankVasul;

    private StringFilter bankVasulMr;

    private DoubleFilter bankVasulPer;

    private StringFilter bankVasulPerMr;

    private DoubleFilter shareCapital;

    private StringFilter shareCapitalMr;

    private DoubleFilter share;

    private StringFilter shareMr;

    private DoubleFilter funds;

    private StringFilter fundsMr;

    private DoubleFilter deposit;

    private StringFilter depositMr;

    private DoubleFilter payable;

    private StringFilter payableMr;

    private DoubleFilter profit;

    private StringFilter profitMr;

    private DoubleFilter cashInHand;

    private StringFilter cashInHandMr;

    private DoubleFilter investment;

    private StringFilter investmentMr;

    private DoubleFilter deadStock;

    private StringFilter deadStockMr;

    private DoubleFilter otherPay;

    private StringFilter otherPayMr;

    private DoubleFilter loss;

    private StringFilter lossMr;

    private DoubleFilter totalBagayat;

    private StringFilter totalBagayatMr;

    private DoubleFilter totalJirayat;

    private StringFilter totalJirayatMr;

    private LongFilter kamalCropId;

    private Boolean distinct;

    public KamalSocietyCriteria() {}

    public KamalSocietyCriteria(KamalSocietyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.zindagiDate = other.zindagiDate == null ? null : other.zindagiDate.copy();
        this.zindagiDateMr = other.zindagiDateMr == null ? null : other.zindagiDateMr.copy();
        this.village1 = other.village1 == null ? null : other.village1.copy();
        this.village1Mr = other.village1Mr == null ? null : other.village1Mr.copy();
        this.village2 = other.village2 == null ? null : other.village2.copy();
        this.village2Mr = other.village2Mr == null ? null : other.village2Mr.copy();
        this.village3 = other.village3 == null ? null : other.village3.copy();
        this.village3Mr = other.village3Mr == null ? null : other.village3Mr.copy();
        this.totalLand = other.totalLand == null ? null : other.totalLand.copy();
        this.totalLandMr = other.totalLandMr == null ? null : other.totalLandMr.copy();
        this.totalMem = other.totalMem == null ? null : other.totalMem.copy();
        this.totalMemMr = other.totalMemMr == null ? null : other.totalMemMr.copy();
        this.totalNonMem = other.totalNonMem == null ? null : other.totalNonMem.copy();
        this.totalNonMemMr = other.totalNonMemMr == null ? null : other.totalNonMemMr.copy();
        this.totalGMem = other.totalGMem == null ? null : other.totalGMem.copy();
        this.totalGMemMr = other.totalGMemMr == null ? null : other.totalGMemMr.copy();
        this.memLoan = other.memLoan == null ? null : other.memLoan.copy();
        this.memLoanMr = other.memLoanMr == null ? null : other.memLoanMr.copy();
        this.memDue = other.memDue == null ? null : other.memDue.copy();
        this.memDueMr = other.memDueMr == null ? null : other.memDueMr.copy();
        this.memDueper = other.memDueper == null ? null : other.memDueper.copy();
        this.memDueperMr = other.memDueperMr == null ? null : other.memDueperMr.copy();
        this.memVasulpatra = other.memVasulpatra == null ? null : other.memVasulpatra.copy();
        this.memVasulpatraMr = other.memVasulpatraMr == null ? null : other.memVasulpatraMr.copy();
        this.memVasul = other.memVasul == null ? null : other.memVasul.copy();
        this.memVasulMr = other.memVasulMr == null ? null : other.memVasulMr.copy();
        this.memVasulPer = other.memVasulPer == null ? null : other.memVasulPer.copy();
        this.memVasulPerMr = other.memVasulPerMr == null ? null : other.memVasulPerMr.copy();
        this.bankLoan = other.bankLoan == null ? null : other.bankLoan.copy();
        this.bankLoanMr = other.bankLoanMr == null ? null : other.bankLoanMr.copy();
        this.bankDue = other.bankDue == null ? null : other.bankDue.copy();
        this.bankDueMr = other.bankDueMr == null ? null : other.bankDueMr.copy();
        this.bankDueper = other.bankDueper == null ? null : other.bankDueper.copy();
        this.bankDueperMr = other.bankDueperMr == null ? null : other.bankDueperMr.copy();
        this.bankVasulpatra = other.bankVasulpatra == null ? null : other.bankVasulpatra.copy();
        this.bankVasulpatraMr = other.bankVasulpatraMr == null ? null : other.bankVasulpatraMr.copy();
        this.bankVasul = other.bankVasul == null ? null : other.bankVasul.copy();
        this.bankVasulMr = other.bankVasulMr == null ? null : other.bankVasulMr.copy();
        this.bankVasulPer = other.bankVasulPer == null ? null : other.bankVasulPer.copy();
        this.bankVasulPerMr = other.bankVasulPerMr == null ? null : other.bankVasulPerMr.copy();
        this.shareCapital = other.shareCapital == null ? null : other.shareCapital.copy();
        this.shareCapitalMr = other.shareCapitalMr == null ? null : other.shareCapitalMr.copy();
        this.share = other.share == null ? null : other.share.copy();
        this.shareMr = other.shareMr == null ? null : other.shareMr.copy();
        this.funds = other.funds == null ? null : other.funds.copy();
        this.fundsMr = other.fundsMr == null ? null : other.fundsMr.copy();
        this.deposit = other.deposit == null ? null : other.deposit.copy();
        this.depositMr = other.depositMr == null ? null : other.depositMr.copy();
        this.payable = other.payable == null ? null : other.payable.copy();
        this.payableMr = other.payableMr == null ? null : other.payableMr.copy();
        this.profit = other.profit == null ? null : other.profit.copy();
        this.profitMr = other.profitMr == null ? null : other.profitMr.copy();
        this.cashInHand = other.cashInHand == null ? null : other.cashInHand.copy();
        this.cashInHandMr = other.cashInHandMr == null ? null : other.cashInHandMr.copy();
        this.investment = other.investment == null ? null : other.investment.copy();
        this.investmentMr = other.investmentMr == null ? null : other.investmentMr.copy();
        this.deadStock = other.deadStock == null ? null : other.deadStock.copy();
        this.deadStockMr = other.deadStockMr == null ? null : other.deadStockMr.copy();
        this.otherPay = other.otherPay == null ? null : other.otherPay.copy();
        this.otherPayMr = other.otherPayMr == null ? null : other.otherPayMr.copy();
        this.loss = other.loss == null ? null : other.loss.copy();
        this.lossMr = other.lossMr == null ? null : other.lossMr.copy();
        this.totalBagayat = other.totalBagayat == null ? null : other.totalBagayat.copy();
        this.totalBagayatMr = other.totalBagayatMr == null ? null : other.totalBagayatMr.copy();
        this.totalJirayat = other.totalJirayat == null ? null : other.totalJirayat.copy();
        this.totalJirayatMr = other.totalJirayatMr == null ? null : other.totalJirayatMr.copy();
        this.kamalCropId = other.kamalCropId == null ? null : other.kamalCropId.copy();
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

    public LongFilter getPacsNumber() {
        return pacsNumber;
    }

    public LongFilter pacsNumber() {
        if (pacsNumber == null) {
            pacsNumber = new LongFilter();
        }
        return pacsNumber;
    }

    public void setPacsNumber(LongFilter pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public InstantFilter getZindagiDate() {
        return zindagiDate;
    }

    public InstantFilter zindagiDate() {
        if (zindagiDate == null) {
            zindagiDate = new InstantFilter();
        }
        return zindagiDate;
    }

    public void setZindagiDate(InstantFilter zindagiDate) {
        this.zindagiDate = zindagiDate;
    }

    public StringFilter getZindagiDateMr() {
        return zindagiDateMr;
    }

    public StringFilter zindagiDateMr() {
        if (zindagiDateMr == null) {
            zindagiDateMr = new StringFilter();
        }
        return zindagiDateMr;
    }

    public void setZindagiDateMr(StringFilter zindagiDateMr) {
        this.zindagiDateMr = zindagiDateMr;
    }

    public StringFilter getVillage1() {
        return village1;
    }

    public StringFilter village1() {
        if (village1 == null) {
            village1 = new StringFilter();
        }
        return village1;
    }

    public void setVillage1(StringFilter village1) {
        this.village1 = village1;
    }

    public StringFilter getVillage1Mr() {
        return village1Mr;
    }

    public StringFilter village1Mr() {
        if (village1Mr == null) {
            village1Mr = new StringFilter();
        }
        return village1Mr;
    }

    public void setVillage1Mr(StringFilter village1Mr) {
        this.village1Mr = village1Mr;
    }

    public StringFilter getVillage2() {
        return village2;
    }

    public StringFilter village2() {
        if (village2 == null) {
            village2 = new StringFilter();
        }
        return village2;
    }

    public void setVillage2(StringFilter village2) {
        this.village2 = village2;
    }

    public StringFilter getVillage2Mr() {
        return village2Mr;
    }

    public StringFilter village2Mr() {
        if (village2Mr == null) {
            village2Mr = new StringFilter();
        }
        return village2Mr;
    }

    public void setVillage2Mr(StringFilter village2Mr) {
        this.village2Mr = village2Mr;
    }

    public StringFilter getVillage3() {
        return village3;
    }

    public StringFilter village3() {
        if (village3 == null) {
            village3 = new StringFilter();
        }
        return village3;
    }

    public void setVillage3(StringFilter village3) {
        this.village3 = village3;
    }

    public StringFilter getVillage3Mr() {
        return village3Mr;
    }

    public StringFilter village3Mr() {
        if (village3Mr == null) {
            village3Mr = new StringFilter();
        }
        return village3Mr;
    }

    public void setVillage3Mr(StringFilter village3Mr) {
        this.village3Mr = village3Mr;
    }

    public DoubleFilter getTotalLand() {
        return totalLand;
    }

    public DoubleFilter totalLand() {
        if (totalLand == null) {
            totalLand = new DoubleFilter();
        }
        return totalLand;
    }

    public void setTotalLand(DoubleFilter totalLand) {
        this.totalLand = totalLand;
    }

    public StringFilter getTotalLandMr() {
        return totalLandMr;
    }

    public StringFilter totalLandMr() {
        if (totalLandMr == null) {
            totalLandMr = new StringFilter();
        }
        return totalLandMr;
    }

    public void setTotalLandMr(StringFilter totalLandMr) {
        this.totalLandMr = totalLandMr;
    }

    public DoubleFilter getTotalMem() {
        return totalMem;
    }

    public DoubleFilter totalMem() {
        if (totalMem == null) {
            totalMem = new DoubleFilter();
        }
        return totalMem;
    }

    public void setTotalMem(DoubleFilter totalMem) {
        this.totalMem = totalMem;
    }

    public StringFilter getTotalMemMr() {
        return totalMemMr;
    }

    public StringFilter totalMemMr() {
        if (totalMemMr == null) {
            totalMemMr = new StringFilter();
        }
        return totalMemMr;
    }

    public void setTotalMemMr(StringFilter totalMemMr) {
        this.totalMemMr = totalMemMr;
    }

    public DoubleFilter getTotalNonMem() {
        return totalNonMem;
    }

    public DoubleFilter totalNonMem() {
        if (totalNonMem == null) {
            totalNonMem = new DoubleFilter();
        }
        return totalNonMem;
    }

    public void setTotalNonMem(DoubleFilter totalNonMem) {
        this.totalNonMem = totalNonMem;
    }

    public StringFilter getTotalNonMemMr() {
        return totalNonMemMr;
    }

    public StringFilter totalNonMemMr() {
        if (totalNonMemMr == null) {
            totalNonMemMr = new StringFilter();
        }
        return totalNonMemMr;
    }

    public void setTotalNonMemMr(StringFilter totalNonMemMr) {
        this.totalNonMemMr = totalNonMemMr;
    }

    public DoubleFilter getTotalGMem() {
        return totalGMem;
    }

    public DoubleFilter totalGMem() {
        if (totalGMem == null) {
            totalGMem = new DoubleFilter();
        }
        return totalGMem;
    }

    public void setTotalGMem(DoubleFilter totalGMem) {
        this.totalGMem = totalGMem;
    }

    public StringFilter getTotalGMemMr() {
        return totalGMemMr;
    }

    public StringFilter totalGMemMr() {
        if (totalGMemMr == null) {
            totalGMemMr = new StringFilter();
        }
        return totalGMemMr;
    }

    public void setTotalGMemMr(StringFilter totalGMemMr) {
        this.totalGMemMr = totalGMemMr;
    }

    public DoubleFilter getMemLoan() {
        return memLoan;
    }

    public DoubleFilter memLoan() {
        if (memLoan == null) {
            memLoan = new DoubleFilter();
        }
        return memLoan;
    }

    public void setMemLoan(DoubleFilter memLoan) {
        this.memLoan = memLoan;
    }

    public StringFilter getMemLoanMr() {
        return memLoanMr;
    }

    public StringFilter memLoanMr() {
        if (memLoanMr == null) {
            memLoanMr = new StringFilter();
        }
        return memLoanMr;
    }

    public void setMemLoanMr(StringFilter memLoanMr) {
        this.memLoanMr = memLoanMr;
    }

    public DoubleFilter getMemDue() {
        return memDue;
    }

    public DoubleFilter memDue() {
        if (memDue == null) {
            memDue = new DoubleFilter();
        }
        return memDue;
    }

    public void setMemDue(DoubleFilter memDue) {
        this.memDue = memDue;
    }

    public StringFilter getMemDueMr() {
        return memDueMr;
    }

    public StringFilter memDueMr() {
        if (memDueMr == null) {
            memDueMr = new StringFilter();
        }
        return memDueMr;
    }

    public void setMemDueMr(StringFilter memDueMr) {
        this.memDueMr = memDueMr;
    }

    public DoubleFilter getMemDueper() {
        return memDueper;
    }

    public DoubleFilter memDueper() {
        if (memDueper == null) {
            memDueper = new DoubleFilter();
        }
        return memDueper;
    }

    public void setMemDueper(DoubleFilter memDueper) {
        this.memDueper = memDueper;
    }

    public StringFilter getMemDueperMr() {
        return memDueperMr;
    }

    public StringFilter memDueperMr() {
        if (memDueperMr == null) {
            memDueperMr = new StringFilter();
        }
        return memDueperMr;
    }

    public void setMemDueperMr(StringFilter memDueperMr) {
        this.memDueperMr = memDueperMr;
    }

    public DoubleFilter getMemVasulpatra() {
        return memVasulpatra;
    }

    public DoubleFilter memVasulpatra() {
        if (memVasulpatra == null) {
            memVasulpatra = new DoubleFilter();
        }
        return memVasulpatra;
    }

    public void setMemVasulpatra(DoubleFilter memVasulpatra) {
        this.memVasulpatra = memVasulpatra;
    }

    public StringFilter getMemVasulpatraMr() {
        return memVasulpatraMr;
    }

    public StringFilter memVasulpatraMr() {
        if (memVasulpatraMr == null) {
            memVasulpatraMr = new StringFilter();
        }
        return memVasulpatraMr;
    }

    public void setMemVasulpatraMr(StringFilter memVasulpatraMr) {
        this.memVasulpatraMr = memVasulpatraMr;
    }

    public DoubleFilter getMemVasul() {
        return memVasul;
    }

    public DoubleFilter memVasul() {
        if (memVasul == null) {
            memVasul = new DoubleFilter();
        }
        return memVasul;
    }

    public void setMemVasul(DoubleFilter memVasul) {
        this.memVasul = memVasul;
    }

    public StringFilter getMemVasulMr() {
        return memVasulMr;
    }

    public StringFilter memVasulMr() {
        if (memVasulMr == null) {
            memVasulMr = new StringFilter();
        }
        return memVasulMr;
    }

    public void setMemVasulMr(StringFilter memVasulMr) {
        this.memVasulMr = memVasulMr;
    }

    public DoubleFilter getMemVasulPer() {
        return memVasulPer;
    }

    public DoubleFilter memVasulPer() {
        if (memVasulPer == null) {
            memVasulPer = new DoubleFilter();
        }
        return memVasulPer;
    }

    public void setMemVasulPer(DoubleFilter memVasulPer) {
        this.memVasulPer = memVasulPer;
    }

    public StringFilter getMemVasulPerMr() {
        return memVasulPerMr;
    }

    public StringFilter memVasulPerMr() {
        if (memVasulPerMr == null) {
            memVasulPerMr = new StringFilter();
        }
        return memVasulPerMr;
    }

    public void setMemVasulPerMr(StringFilter memVasulPerMr) {
        this.memVasulPerMr = memVasulPerMr;
    }

    public DoubleFilter getBankLoan() {
        return bankLoan;
    }

    public DoubleFilter bankLoan() {
        if (bankLoan == null) {
            bankLoan = new DoubleFilter();
        }
        return bankLoan;
    }

    public void setBankLoan(DoubleFilter bankLoan) {
        this.bankLoan = bankLoan;
    }

    public StringFilter getBankLoanMr() {
        return bankLoanMr;
    }

    public StringFilter bankLoanMr() {
        if (bankLoanMr == null) {
            bankLoanMr = new StringFilter();
        }
        return bankLoanMr;
    }

    public void setBankLoanMr(StringFilter bankLoanMr) {
        this.bankLoanMr = bankLoanMr;
    }

    public DoubleFilter getBankDue() {
        return bankDue;
    }

    public DoubleFilter bankDue() {
        if (bankDue == null) {
            bankDue = new DoubleFilter();
        }
        return bankDue;
    }

    public void setBankDue(DoubleFilter bankDue) {
        this.bankDue = bankDue;
    }

    public StringFilter getBankDueMr() {
        return bankDueMr;
    }

    public StringFilter bankDueMr() {
        if (bankDueMr == null) {
            bankDueMr = new StringFilter();
        }
        return bankDueMr;
    }

    public void setBankDueMr(StringFilter bankDueMr) {
        this.bankDueMr = bankDueMr;
    }

    public DoubleFilter getBankDueper() {
        return bankDueper;
    }

    public DoubleFilter bankDueper() {
        if (bankDueper == null) {
            bankDueper = new DoubleFilter();
        }
        return bankDueper;
    }

    public void setBankDueper(DoubleFilter bankDueper) {
        this.bankDueper = bankDueper;
    }

    public StringFilter getBankDueperMr() {
        return bankDueperMr;
    }

    public StringFilter bankDueperMr() {
        if (bankDueperMr == null) {
            bankDueperMr = new StringFilter();
        }
        return bankDueperMr;
    }

    public void setBankDueperMr(StringFilter bankDueperMr) {
        this.bankDueperMr = bankDueperMr;
    }

    public DoubleFilter getBankVasulpatra() {
        return bankVasulpatra;
    }

    public DoubleFilter bankVasulpatra() {
        if (bankVasulpatra == null) {
            bankVasulpatra = new DoubleFilter();
        }
        return bankVasulpatra;
    }

    public void setBankVasulpatra(DoubleFilter bankVasulpatra) {
        this.bankVasulpatra = bankVasulpatra;
    }

    public StringFilter getBankVasulpatraMr() {
        return bankVasulpatraMr;
    }

    public StringFilter bankVasulpatraMr() {
        if (bankVasulpatraMr == null) {
            bankVasulpatraMr = new StringFilter();
        }
        return bankVasulpatraMr;
    }

    public void setBankVasulpatraMr(StringFilter bankVasulpatraMr) {
        this.bankVasulpatraMr = bankVasulpatraMr;
    }

    public DoubleFilter getBankVasul() {
        return bankVasul;
    }

    public DoubleFilter bankVasul() {
        if (bankVasul == null) {
            bankVasul = new DoubleFilter();
        }
        return bankVasul;
    }

    public void setBankVasul(DoubleFilter bankVasul) {
        this.bankVasul = bankVasul;
    }

    public StringFilter getBankVasulMr() {
        return bankVasulMr;
    }

    public StringFilter bankVasulMr() {
        if (bankVasulMr == null) {
            bankVasulMr = new StringFilter();
        }
        return bankVasulMr;
    }

    public void setBankVasulMr(StringFilter bankVasulMr) {
        this.bankVasulMr = bankVasulMr;
    }

    public DoubleFilter getBankVasulPer() {
        return bankVasulPer;
    }

    public DoubleFilter bankVasulPer() {
        if (bankVasulPer == null) {
            bankVasulPer = new DoubleFilter();
        }
        return bankVasulPer;
    }

    public void setBankVasulPer(DoubleFilter bankVasulPer) {
        this.bankVasulPer = bankVasulPer;
    }

    public StringFilter getBankVasulPerMr() {
        return bankVasulPerMr;
    }

    public StringFilter bankVasulPerMr() {
        if (bankVasulPerMr == null) {
            bankVasulPerMr = new StringFilter();
        }
        return bankVasulPerMr;
    }

    public void setBankVasulPerMr(StringFilter bankVasulPerMr) {
        this.bankVasulPerMr = bankVasulPerMr;
    }

    public DoubleFilter getShareCapital() {
        return shareCapital;
    }

    public DoubleFilter shareCapital() {
        if (shareCapital == null) {
            shareCapital = new DoubleFilter();
        }
        return shareCapital;
    }

    public void setShareCapital(DoubleFilter shareCapital) {
        this.shareCapital = shareCapital;
    }

    public StringFilter getShareCapitalMr() {
        return shareCapitalMr;
    }

    public StringFilter shareCapitalMr() {
        if (shareCapitalMr == null) {
            shareCapitalMr = new StringFilter();
        }
        return shareCapitalMr;
    }

    public void setShareCapitalMr(StringFilter shareCapitalMr) {
        this.shareCapitalMr = shareCapitalMr;
    }

    public DoubleFilter getShare() {
        return share;
    }

    public DoubleFilter share() {
        if (share == null) {
            share = new DoubleFilter();
        }
        return share;
    }

    public void setShare(DoubleFilter share) {
        this.share = share;
    }

    public StringFilter getShareMr() {
        return shareMr;
    }

    public StringFilter shareMr() {
        if (shareMr == null) {
            shareMr = new StringFilter();
        }
        return shareMr;
    }

    public void setShareMr(StringFilter shareMr) {
        this.shareMr = shareMr;
    }

    public DoubleFilter getFunds() {
        return funds;
    }

    public DoubleFilter funds() {
        if (funds == null) {
            funds = new DoubleFilter();
        }
        return funds;
    }

    public void setFunds(DoubleFilter funds) {
        this.funds = funds;
    }

    public StringFilter getFundsMr() {
        return fundsMr;
    }

    public StringFilter fundsMr() {
        if (fundsMr == null) {
            fundsMr = new StringFilter();
        }
        return fundsMr;
    }

    public void setFundsMr(StringFilter fundsMr) {
        this.fundsMr = fundsMr;
    }

    public DoubleFilter getDeposit() {
        return deposit;
    }

    public DoubleFilter deposit() {
        if (deposit == null) {
            deposit = new DoubleFilter();
        }
        return deposit;
    }

    public void setDeposit(DoubleFilter deposit) {
        this.deposit = deposit;
    }

    public StringFilter getDepositMr() {
        return depositMr;
    }

    public StringFilter depositMr() {
        if (depositMr == null) {
            depositMr = new StringFilter();
        }
        return depositMr;
    }

    public void setDepositMr(StringFilter depositMr) {
        this.depositMr = depositMr;
    }

    public DoubleFilter getPayable() {
        return payable;
    }

    public DoubleFilter payable() {
        if (payable == null) {
            payable = new DoubleFilter();
        }
        return payable;
    }

    public void setPayable(DoubleFilter payable) {
        this.payable = payable;
    }

    public StringFilter getPayableMr() {
        return payableMr;
    }

    public StringFilter payableMr() {
        if (payableMr == null) {
            payableMr = new StringFilter();
        }
        return payableMr;
    }

    public void setPayableMr(StringFilter payableMr) {
        this.payableMr = payableMr;
    }

    public DoubleFilter getProfit() {
        return profit;
    }

    public DoubleFilter profit() {
        if (profit == null) {
            profit = new DoubleFilter();
        }
        return profit;
    }

    public void setProfit(DoubleFilter profit) {
        this.profit = profit;
    }

    public StringFilter getProfitMr() {
        return profitMr;
    }

    public StringFilter profitMr() {
        if (profitMr == null) {
            profitMr = new StringFilter();
        }
        return profitMr;
    }

    public void setProfitMr(StringFilter profitMr) {
        this.profitMr = profitMr;
    }

    public DoubleFilter getCashInHand() {
        return cashInHand;
    }

    public DoubleFilter cashInHand() {
        if (cashInHand == null) {
            cashInHand = new DoubleFilter();
        }
        return cashInHand;
    }

    public void setCashInHand(DoubleFilter cashInHand) {
        this.cashInHand = cashInHand;
    }

    public StringFilter getCashInHandMr() {
        return cashInHandMr;
    }

    public StringFilter cashInHandMr() {
        if (cashInHandMr == null) {
            cashInHandMr = new StringFilter();
        }
        return cashInHandMr;
    }

    public void setCashInHandMr(StringFilter cashInHandMr) {
        this.cashInHandMr = cashInHandMr;
    }

    public DoubleFilter getInvestment() {
        return investment;
    }

    public DoubleFilter investment() {
        if (investment == null) {
            investment = new DoubleFilter();
        }
        return investment;
    }

    public void setInvestment(DoubleFilter investment) {
        this.investment = investment;
    }

    public StringFilter getInvestmentMr() {
        return investmentMr;
    }

    public StringFilter investmentMr() {
        if (investmentMr == null) {
            investmentMr = new StringFilter();
        }
        return investmentMr;
    }

    public void setInvestmentMr(StringFilter investmentMr) {
        this.investmentMr = investmentMr;
    }

    public DoubleFilter getDeadStock() {
        return deadStock;
    }

    public DoubleFilter deadStock() {
        if (deadStock == null) {
            deadStock = new DoubleFilter();
        }
        return deadStock;
    }

    public void setDeadStock(DoubleFilter deadStock) {
        this.deadStock = deadStock;
    }

    public StringFilter getDeadStockMr() {
        return deadStockMr;
    }

    public StringFilter deadStockMr() {
        if (deadStockMr == null) {
            deadStockMr = new StringFilter();
        }
        return deadStockMr;
    }

    public void setDeadStockMr(StringFilter deadStockMr) {
        this.deadStockMr = deadStockMr;
    }

    public DoubleFilter getOtherPay() {
        return otherPay;
    }

    public DoubleFilter otherPay() {
        if (otherPay == null) {
            otherPay = new DoubleFilter();
        }
        return otherPay;
    }

    public void setOtherPay(DoubleFilter otherPay) {
        this.otherPay = otherPay;
    }

    public StringFilter getOtherPayMr() {
        return otherPayMr;
    }

    public StringFilter otherPayMr() {
        if (otherPayMr == null) {
            otherPayMr = new StringFilter();
        }
        return otherPayMr;
    }

    public void setOtherPayMr(StringFilter otherPayMr) {
        this.otherPayMr = otherPayMr;
    }

    public DoubleFilter getLoss() {
        return loss;
    }

    public DoubleFilter loss() {
        if (loss == null) {
            loss = new DoubleFilter();
        }
        return loss;
    }

    public void setLoss(DoubleFilter loss) {
        this.loss = loss;
    }

    public StringFilter getLossMr() {
        return lossMr;
    }

    public StringFilter lossMr() {
        if (lossMr == null) {
            lossMr = new StringFilter();
        }
        return lossMr;
    }

    public void setLossMr(StringFilter lossMr) {
        this.lossMr = lossMr;
    }

    public DoubleFilter getTotalBagayat() {
        return totalBagayat;
    }

    public DoubleFilter totalBagayat() {
        if (totalBagayat == null) {
            totalBagayat = new DoubleFilter();
        }
        return totalBagayat;
    }

    public void setTotalBagayat(DoubleFilter totalBagayat) {
        this.totalBagayat = totalBagayat;
    }

    public StringFilter getTotalBagayatMr() {
        return totalBagayatMr;
    }

    public StringFilter totalBagayatMr() {
        if (totalBagayatMr == null) {
            totalBagayatMr = new StringFilter();
        }
        return totalBagayatMr;
    }

    public void setTotalBagayatMr(StringFilter totalBagayatMr) {
        this.totalBagayatMr = totalBagayatMr;
    }

    public DoubleFilter getTotalJirayat() {
        return totalJirayat;
    }

    public DoubleFilter totalJirayat() {
        if (totalJirayat == null) {
            totalJirayat = new DoubleFilter();
        }
        return totalJirayat;
    }

    public void setTotalJirayat(DoubleFilter totalJirayat) {
        this.totalJirayat = totalJirayat;
    }

    public StringFilter getTotalJirayatMr() {
        return totalJirayatMr;
    }

    public StringFilter totalJirayatMr() {
        if (totalJirayatMr == null) {
            totalJirayatMr = new StringFilter();
        }
        return totalJirayatMr;
    }

    public void setTotalJirayatMr(StringFilter totalJirayatMr) {
        this.totalJirayatMr = totalJirayatMr;
    }

    public LongFilter getKamalCropId() {
        return kamalCropId;
    }

    public LongFilter kamalCropId() {
        if (kamalCropId == null) {
            kamalCropId = new LongFilter();
        }
        return kamalCropId;
    }

    public void setKamalCropId(LongFilter kamalCropId) {
        this.kamalCropId = kamalCropId;
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
            Objects.equals(pacsNumber, that.pacsNumber) &&
            Objects.equals(zindagiDate, that.zindagiDate) &&
            Objects.equals(zindagiDateMr, that.zindagiDateMr) &&
            Objects.equals(village1, that.village1) &&
            Objects.equals(village1Mr, that.village1Mr) &&
            Objects.equals(village2, that.village2) &&
            Objects.equals(village2Mr, that.village2Mr) &&
            Objects.equals(village3, that.village3) &&
            Objects.equals(village3Mr, that.village3Mr) &&
            Objects.equals(totalLand, that.totalLand) &&
            Objects.equals(totalLandMr, that.totalLandMr) &&
            Objects.equals(totalMem, that.totalMem) &&
            Objects.equals(totalMemMr, that.totalMemMr) &&
            Objects.equals(totalNonMem, that.totalNonMem) &&
            Objects.equals(totalNonMemMr, that.totalNonMemMr) &&
            Objects.equals(totalGMem, that.totalGMem) &&
            Objects.equals(totalGMemMr, that.totalGMemMr) &&
            Objects.equals(memLoan, that.memLoan) &&
            Objects.equals(memLoanMr, that.memLoanMr) &&
            Objects.equals(memDue, that.memDue) &&
            Objects.equals(memDueMr, that.memDueMr) &&
            Objects.equals(memDueper, that.memDueper) &&
            Objects.equals(memDueperMr, that.memDueperMr) &&
            Objects.equals(memVasulpatra, that.memVasulpatra) &&
            Objects.equals(memVasulpatraMr, that.memVasulpatraMr) &&
            Objects.equals(memVasul, that.memVasul) &&
            Objects.equals(memVasulMr, that.memVasulMr) &&
            Objects.equals(memVasulPer, that.memVasulPer) &&
            Objects.equals(memVasulPerMr, that.memVasulPerMr) &&
            Objects.equals(bankLoan, that.bankLoan) &&
            Objects.equals(bankLoanMr, that.bankLoanMr) &&
            Objects.equals(bankDue, that.bankDue) &&
            Objects.equals(bankDueMr, that.bankDueMr) &&
            Objects.equals(bankDueper, that.bankDueper) &&
            Objects.equals(bankDueperMr, that.bankDueperMr) &&
            Objects.equals(bankVasulpatra, that.bankVasulpatra) &&
            Objects.equals(bankVasulpatraMr, that.bankVasulpatraMr) &&
            Objects.equals(bankVasul, that.bankVasul) &&
            Objects.equals(bankVasulMr, that.bankVasulMr) &&
            Objects.equals(bankVasulPer, that.bankVasulPer) &&
            Objects.equals(bankVasulPerMr, that.bankVasulPerMr) &&
            Objects.equals(shareCapital, that.shareCapital) &&
            Objects.equals(shareCapitalMr, that.shareCapitalMr) &&
            Objects.equals(share, that.share) &&
            Objects.equals(shareMr, that.shareMr) &&
            Objects.equals(funds, that.funds) &&
            Objects.equals(fundsMr, that.fundsMr) &&
            Objects.equals(deposit, that.deposit) &&
            Objects.equals(depositMr, that.depositMr) &&
            Objects.equals(payable, that.payable) &&
            Objects.equals(payableMr, that.payableMr) &&
            Objects.equals(profit, that.profit) &&
            Objects.equals(profitMr, that.profitMr) &&
            Objects.equals(cashInHand, that.cashInHand) &&
            Objects.equals(cashInHandMr, that.cashInHandMr) &&
            Objects.equals(investment, that.investment) &&
            Objects.equals(investmentMr, that.investmentMr) &&
            Objects.equals(deadStock, that.deadStock) &&
            Objects.equals(deadStockMr, that.deadStockMr) &&
            Objects.equals(otherPay, that.otherPay) &&
            Objects.equals(otherPayMr, that.otherPayMr) &&
            Objects.equals(loss, that.loss) &&
            Objects.equals(lossMr, that.lossMr) &&
            Objects.equals(totalBagayat, that.totalBagayat) &&
            Objects.equals(totalBagayatMr, that.totalBagayatMr) &&
            Objects.equals(totalJirayat, that.totalJirayat) &&
            Objects.equals(totalJirayatMr, that.totalJirayatMr) &&
            Objects.equals(kamalCropId, that.kamalCropId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            pacsNumber,
            zindagiDate,
            zindagiDateMr,
            village1,
            village1Mr,
            village2,
            village2Mr,
            village3,
            village3Mr,
            totalLand,
            totalLandMr,
            totalMem,
            totalMemMr,
            totalNonMem,
            totalNonMemMr,
            totalGMem,
            totalGMemMr,
            memLoan,
            memLoanMr,
            memDue,
            memDueMr,
            memDueper,
            memDueperMr,
            memVasulpatra,
            memVasulpatraMr,
            memVasul,
            memVasulMr,
            memVasulPer,
            memVasulPerMr,
            bankLoan,
            bankLoanMr,
            bankDue,
            bankDueMr,
            bankDueper,
            bankDueperMr,
            bankVasulpatra,
            bankVasulpatraMr,
            bankVasul,
            bankVasulMr,
            bankVasulPer,
            bankVasulPerMr,
            shareCapital,
            shareCapitalMr,
            share,
            shareMr,
            funds,
            fundsMr,
            deposit,
            depositMr,
            payable,
            payableMr,
            profit,
            profitMr,
            cashInHand,
            cashInHandMr,
            investment,
            investmentMr,
            deadStock,
            deadStockMr,
            otherPay,
            otherPayMr,
            loss,
            lossMr,
            totalBagayat,
            totalBagayatMr,
            totalJirayat,
            totalJirayatMr,
            kamalCropId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalSocietyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (zindagiDate != null ? "zindagiDate=" + zindagiDate + ", " : "") +
            (zindagiDateMr != null ? "zindagiDateMr=" + zindagiDateMr + ", " : "") +
            (village1 != null ? "village1=" + village1 + ", " : "") +
            (village1Mr != null ? "village1Mr=" + village1Mr + ", " : "") +
            (village2 != null ? "village2=" + village2 + ", " : "") +
            (village2Mr != null ? "village2Mr=" + village2Mr + ", " : "") +
            (village3 != null ? "village3=" + village3 + ", " : "") +
            (village3Mr != null ? "village3Mr=" + village3Mr + ", " : "") +
            (totalLand != null ? "totalLand=" + totalLand + ", " : "") +
            (totalLandMr != null ? "totalLandMr=" + totalLandMr + ", " : "") +
            (totalMem != null ? "totalMem=" + totalMem + ", " : "") +
            (totalMemMr != null ? "totalMemMr=" + totalMemMr + ", " : "") +
            (totalNonMem != null ? "totalNonMem=" + totalNonMem + ", " : "") +
            (totalNonMemMr != null ? "totalNonMemMr=" + totalNonMemMr + ", " : "") +
            (totalGMem != null ? "totalGMem=" + totalGMem + ", " : "") +
            (totalGMemMr != null ? "totalGMemMr=" + totalGMemMr + ", " : "") +
            (memLoan != null ? "memLoan=" + memLoan + ", " : "") +
            (memLoanMr != null ? "memLoanMr=" + memLoanMr + ", " : "") +
            (memDue != null ? "memDue=" + memDue + ", " : "") +
            (memDueMr != null ? "memDueMr=" + memDueMr + ", " : "") +
            (memDueper != null ? "memDueper=" + memDueper + ", " : "") +
            (memDueperMr != null ? "memDueperMr=" + memDueperMr + ", " : "") +
            (memVasulpatra != null ? "memVasulpatra=" + memVasulpatra + ", " : "") +
            (memVasulpatraMr != null ? "memVasulpatraMr=" + memVasulpatraMr + ", " : "") +
            (memVasul != null ? "memVasul=" + memVasul + ", " : "") +
            (memVasulMr != null ? "memVasulMr=" + memVasulMr + ", " : "") +
            (memVasulPer != null ? "memVasulPer=" + memVasulPer + ", " : "") +
            (memVasulPerMr != null ? "memVasulPerMr=" + memVasulPerMr + ", " : "") +
            (bankLoan != null ? "bankLoan=" + bankLoan + ", " : "") +
            (bankLoanMr != null ? "bankLoanMr=" + bankLoanMr + ", " : "") +
            (bankDue != null ? "bankDue=" + bankDue + ", " : "") +
            (bankDueMr != null ? "bankDueMr=" + bankDueMr + ", " : "") +
            (bankDueper != null ? "bankDueper=" + bankDueper + ", " : "") +
            (bankDueperMr != null ? "bankDueperMr=" + bankDueperMr + ", " : "") +
            (bankVasulpatra != null ? "bankVasulpatra=" + bankVasulpatra + ", " : "") +
            (bankVasulpatraMr != null ? "bankVasulpatraMr=" + bankVasulpatraMr + ", " : "") +
            (bankVasul != null ? "bankVasul=" + bankVasul + ", " : "") +
            (bankVasulMr != null ? "bankVasulMr=" + bankVasulMr + ", " : "") +
            (bankVasulPer != null ? "bankVasulPer=" + bankVasulPer + ", " : "") +
            (bankVasulPerMr != null ? "bankVasulPerMr=" + bankVasulPerMr + ", " : "") +
            (shareCapital != null ? "shareCapital=" + shareCapital + ", " : "") +
            (shareCapitalMr != null ? "shareCapitalMr=" + shareCapitalMr + ", " : "") +
            (share != null ? "share=" + share + ", " : "") +
            (shareMr != null ? "shareMr=" + shareMr + ", " : "") +
            (funds != null ? "funds=" + funds + ", " : "") +
            (fundsMr != null ? "fundsMr=" + fundsMr + ", " : "") +
            (deposit != null ? "deposit=" + deposit + ", " : "") +
            (depositMr != null ? "depositMr=" + depositMr + ", " : "") +
            (payable != null ? "payable=" + payable + ", " : "") +
            (payableMr != null ? "payableMr=" + payableMr + ", " : "") +
            (profit != null ? "profit=" + profit + ", " : "") +
            (profitMr != null ? "profitMr=" + profitMr + ", " : "") +
            (cashInHand != null ? "cashInHand=" + cashInHand + ", " : "") +
            (cashInHandMr != null ? "cashInHandMr=" + cashInHandMr + ", " : "") +
            (investment != null ? "investment=" + investment + ", " : "") +
            (investmentMr != null ? "investmentMr=" + investmentMr + ", " : "") +
            (deadStock != null ? "deadStock=" + deadStock + ", " : "") +
            (deadStockMr != null ? "deadStockMr=" + deadStockMr + ", " : "") +
            (otherPay != null ? "otherPay=" + otherPay + ", " : "") +
            (otherPayMr != null ? "otherPayMr=" + otherPayMr + ", " : "") +
            (loss != null ? "loss=" + loss + ", " : "") +
            (lossMr != null ? "lossMr=" + lossMr + ", " : "") +
            (totalBagayat != null ? "totalBagayat=" + totalBagayat + ", " : "") +
            (totalBagayatMr != null ? "totalBagayatMr=" + totalBagayatMr + ", " : "") +
            (totalJirayat != null ? "totalJirayat=" + totalJirayat + ", " : "") +
            (totalJirayatMr != null ? "totalJirayatMr=" + totalJirayatMr + ", " : "") +
            (kamalCropId != null ? "kamalCropId=" + kamalCropId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
