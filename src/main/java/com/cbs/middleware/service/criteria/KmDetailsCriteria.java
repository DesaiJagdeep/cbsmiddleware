package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KmDetails} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KmDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /km-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter shares;

    private StringFilter sharesMr;

    private DoubleFilter sugarShares;

    private StringFilter sugarSharesMr;

    private DoubleFilter deposit;

    private StringFilter depositMr;

    private DoubleFilter dueLoan;

    private StringFilter dueLoanMr;

    private DoubleFilter dueAmount;

    private StringFilter dueAmountMr;

    private InstantFilter dueDate;

    private InstantFilter kmDate;

    private InstantFilter kmFromDate;

    private InstantFilter kmToDate;

    private DoubleFilter bagayatHector;

    private StringFilter bagayatHectorMr;

    private DoubleFilter bagayatAre;

    private StringFilter bagayatAreMr;

    private DoubleFilter jirayatHector;

    private StringFilter jirayatHectorMr;

    private DoubleFilter jirayatAre;

    private StringFilter jirayatAreMr;

    private DoubleFilter landValue;

    private StringFilter landValueMr;

    private DoubleFilter eAggrementAmt;

    private StringFilter eAgreementAmt;

    private InstantFilter eAgreementDate;

    private DoubleFilter bojaAmount;

    private StringFilter bojaAmountMr;

    private InstantFilter bojaDate;

    private LongFilter kmMasterId;

    private Boolean distinct;

    public KmDetailsCriteria() {}

    public KmDetailsCriteria(KmDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.shares = other.shares == null ? null : other.shares.copy();
        this.sharesMr = other.sharesMr == null ? null : other.sharesMr.copy();
        this.sugarShares = other.sugarShares == null ? null : other.sugarShares.copy();
        this.sugarSharesMr = other.sugarSharesMr == null ? null : other.sugarSharesMr.copy();
        this.deposit = other.deposit == null ? null : other.deposit.copy();
        this.depositMr = other.depositMr == null ? null : other.depositMr.copy();
        this.dueLoan = other.dueLoan == null ? null : other.dueLoan.copy();
        this.dueLoanMr = other.dueLoanMr == null ? null : other.dueLoanMr.copy();
        this.dueAmount = other.dueAmount == null ? null : other.dueAmount.copy();
        this.dueAmountMr = other.dueAmountMr == null ? null : other.dueAmountMr.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.kmDate = other.kmDate == null ? null : other.kmDate.copy();
        this.kmFromDate = other.kmFromDate == null ? null : other.kmFromDate.copy();
        this.kmToDate = other.kmToDate == null ? null : other.kmToDate.copy();
        this.bagayatHector = other.bagayatHector == null ? null : other.bagayatHector.copy();
        this.bagayatHectorMr = other.bagayatHectorMr == null ? null : other.bagayatHectorMr.copy();
        this.bagayatAre = other.bagayatAre == null ? null : other.bagayatAre.copy();
        this.bagayatAreMr = other.bagayatAreMr == null ? null : other.bagayatAreMr.copy();
        this.jirayatHector = other.jirayatHector == null ? null : other.jirayatHector.copy();
        this.jirayatHectorMr = other.jirayatHectorMr == null ? null : other.jirayatHectorMr.copy();
        this.jirayatAre = other.jirayatAre == null ? null : other.jirayatAre.copy();
        this.jirayatAreMr = other.jirayatAreMr == null ? null : other.jirayatAreMr.copy();
        this.landValue = other.landValue == null ? null : other.landValue.copy();
        this.landValueMr = other.landValueMr == null ? null : other.landValueMr.copy();
        this.eAggrementAmt = other.eAggrementAmt == null ? null : other.eAggrementAmt.copy();
        this.eAgreementAmt = other.eAgreementAmt == null ? null : other.eAgreementAmt.copy();
        this.eAgreementDate = other.eAgreementDate == null ? null : other.eAgreementDate.copy();
        this.bojaAmount = other.bojaAmount == null ? null : other.bojaAmount.copy();
        this.bojaAmountMr = other.bojaAmountMr == null ? null : other.bojaAmountMr.copy();
        this.bojaDate = other.bojaDate == null ? null : other.bojaDate.copy();
        this.kmMasterId = other.kmMasterId == null ? null : other.kmMasterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KmDetailsCriteria copy() {
        return new KmDetailsCriteria(this);
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

    public DoubleFilter getShares() {
        return shares;
    }

    public DoubleFilter shares() {
        if (shares == null) {
            shares = new DoubleFilter();
        }
        return shares;
    }

    public void setShares(DoubleFilter shares) {
        this.shares = shares;
    }

    public StringFilter getSharesMr() {
        return sharesMr;
    }

    public StringFilter sharesMr() {
        if (sharesMr == null) {
            sharesMr = new StringFilter();
        }
        return sharesMr;
    }

    public void setSharesMr(StringFilter sharesMr) {
        this.sharesMr = sharesMr;
    }

    public DoubleFilter getSugarShares() {
        return sugarShares;
    }

    public DoubleFilter sugarShares() {
        if (sugarShares == null) {
            sugarShares = new DoubleFilter();
        }
        return sugarShares;
    }

    public void setSugarShares(DoubleFilter sugarShares) {
        this.sugarShares = sugarShares;
    }

    public StringFilter getSugarSharesMr() {
        return sugarSharesMr;
    }

    public StringFilter sugarSharesMr() {
        if (sugarSharesMr == null) {
            sugarSharesMr = new StringFilter();
        }
        return sugarSharesMr;
    }

    public void setSugarSharesMr(StringFilter sugarSharesMr) {
        this.sugarSharesMr = sugarSharesMr;
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

    public DoubleFilter getDueLoan() {
        return dueLoan;
    }

    public DoubleFilter dueLoan() {
        if (dueLoan == null) {
            dueLoan = new DoubleFilter();
        }
        return dueLoan;
    }

    public void setDueLoan(DoubleFilter dueLoan) {
        this.dueLoan = dueLoan;
    }

    public StringFilter getDueLoanMr() {
        return dueLoanMr;
    }

    public StringFilter dueLoanMr() {
        if (dueLoanMr == null) {
            dueLoanMr = new StringFilter();
        }
        return dueLoanMr;
    }

    public void setDueLoanMr(StringFilter dueLoanMr) {
        this.dueLoanMr = dueLoanMr;
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

    public StringFilter getDueAmountMr() {
        return dueAmountMr;
    }

    public StringFilter dueAmountMr() {
        if (dueAmountMr == null) {
            dueAmountMr = new StringFilter();
        }
        return dueAmountMr;
    }

    public void setDueAmountMr(StringFilter dueAmountMr) {
        this.dueAmountMr = dueAmountMr;
    }

    public InstantFilter getDueDate() {
        return dueDate;
    }

    public InstantFilter dueDate() {
        if (dueDate == null) {
            dueDate = new InstantFilter();
        }
        return dueDate;
    }

    public void setDueDate(InstantFilter dueDate) {
        this.dueDate = dueDate;
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

    public DoubleFilter getBagayatHector() {
        return bagayatHector;
    }

    public DoubleFilter bagayatHector() {
        if (bagayatHector == null) {
            bagayatHector = new DoubleFilter();
        }
        return bagayatHector;
    }

    public void setBagayatHector(DoubleFilter bagayatHector) {
        this.bagayatHector = bagayatHector;
    }

    public StringFilter getBagayatHectorMr() {
        return bagayatHectorMr;
    }

    public StringFilter bagayatHectorMr() {
        if (bagayatHectorMr == null) {
            bagayatHectorMr = new StringFilter();
        }
        return bagayatHectorMr;
    }

    public void setBagayatHectorMr(StringFilter bagayatHectorMr) {
        this.bagayatHectorMr = bagayatHectorMr;
    }

    public DoubleFilter getBagayatAre() {
        return bagayatAre;
    }

    public DoubleFilter bagayatAre() {
        if (bagayatAre == null) {
            bagayatAre = new DoubleFilter();
        }
        return bagayatAre;
    }

    public void setBagayatAre(DoubleFilter bagayatAre) {
        this.bagayatAre = bagayatAre;
    }

    public StringFilter getBagayatAreMr() {
        return bagayatAreMr;
    }

    public StringFilter bagayatAreMr() {
        if (bagayatAreMr == null) {
            bagayatAreMr = new StringFilter();
        }
        return bagayatAreMr;
    }

    public void setBagayatAreMr(StringFilter bagayatAreMr) {
        this.bagayatAreMr = bagayatAreMr;
    }

    public DoubleFilter getJirayatHector() {
        return jirayatHector;
    }

    public DoubleFilter jirayatHector() {
        if (jirayatHector == null) {
            jirayatHector = new DoubleFilter();
        }
        return jirayatHector;
    }

    public void setJirayatHector(DoubleFilter jirayatHector) {
        this.jirayatHector = jirayatHector;
    }

    public StringFilter getJirayatHectorMr() {
        return jirayatHectorMr;
    }

    public StringFilter jirayatHectorMr() {
        if (jirayatHectorMr == null) {
            jirayatHectorMr = new StringFilter();
        }
        return jirayatHectorMr;
    }

    public void setJirayatHectorMr(StringFilter jirayatHectorMr) {
        this.jirayatHectorMr = jirayatHectorMr;
    }

    public DoubleFilter getJirayatAre() {
        return jirayatAre;
    }

    public DoubleFilter jirayatAre() {
        if (jirayatAre == null) {
            jirayatAre = new DoubleFilter();
        }
        return jirayatAre;
    }

    public void setJirayatAre(DoubleFilter jirayatAre) {
        this.jirayatAre = jirayatAre;
    }

    public StringFilter getJirayatAreMr() {
        return jirayatAreMr;
    }

    public StringFilter jirayatAreMr() {
        if (jirayatAreMr == null) {
            jirayatAreMr = new StringFilter();
        }
        return jirayatAreMr;
    }

    public void setJirayatAreMr(StringFilter jirayatAreMr) {
        this.jirayatAreMr = jirayatAreMr;
    }

    public DoubleFilter getLandValue() {
        return landValue;
    }

    public DoubleFilter landValue() {
        if (landValue == null) {
            landValue = new DoubleFilter();
        }
        return landValue;
    }

    public void setLandValue(DoubleFilter landValue) {
        this.landValue = landValue;
    }

    public StringFilter getLandValueMr() {
        return landValueMr;
    }

    public StringFilter landValueMr() {
        if (landValueMr == null) {
            landValueMr = new StringFilter();
        }
        return landValueMr;
    }

    public void setLandValueMr(StringFilter landValueMr) {
        this.landValueMr = landValueMr;
    }

    public DoubleFilter geteAggrementAmt() {
        return eAggrementAmt;
    }

    public DoubleFilter eAggrementAmt() {
        if (eAggrementAmt == null) {
            eAggrementAmt = new DoubleFilter();
        }
        return eAggrementAmt;
    }

    public void seteAggrementAmt(DoubleFilter eAggrementAmt) {
        this.eAggrementAmt = eAggrementAmt;
    }

    public StringFilter geteAgreementAmt() {
        return eAgreementAmt;
    }

    public StringFilter eAgreementAmt() {
        if (eAgreementAmt == null) {
            eAgreementAmt = new StringFilter();
        }
        return eAgreementAmt;
    }

    public void seteAgreementAmt(StringFilter eAgreementAmt) {
        this.eAgreementAmt = eAgreementAmt;
    }

    public InstantFilter geteAgreementDate() {
        return eAgreementDate;
    }

    public InstantFilter eAgreementDate() {
        if (eAgreementDate == null) {
            eAgreementDate = new InstantFilter();
        }
        return eAgreementDate;
    }

    public void seteAgreementDate(InstantFilter eAgreementDate) {
        this.eAgreementDate = eAgreementDate;
    }

    public DoubleFilter getBojaAmount() {
        return bojaAmount;
    }

    public DoubleFilter bojaAmount() {
        if (bojaAmount == null) {
            bojaAmount = new DoubleFilter();
        }
        return bojaAmount;
    }

    public void setBojaAmount(DoubleFilter bojaAmount) {
        this.bojaAmount = bojaAmount;
    }

    public StringFilter getBojaAmountMr() {
        return bojaAmountMr;
    }

    public StringFilter bojaAmountMr() {
        if (bojaAmountMr == null) {
            bojaAmountMr = new StringFilter();
        }
        return bojaAmountMr;
    }

    public void setBojaAmountMr(StringFilter bojaAmountMr) {
        this.bojaAmountMr = bojaAmountMr;
    }

    public InstantFilter getBojaDate() {
        return bojaDate;
    }

    public InstantFilter bojaDate() {
        if (bojaDate == null) {
            bojaDate = new InstantFilter();
        }
        return bojaDate;
    }

    public void setBojaDate(InstantFilter bojaDate) {
        this.bojaDate = bojaDate;
    }

    public LongFilter getKmMasterId() {
        return kmMasterId;
    }

    public LongFilter kmMasterId() {
        if (kmMasterId == null) {
            kmMasterId = new LongFilter();
        }
        return kmMasterId;
    }

    public void setKmMasterId(LongFilter kmMasterId) {
        this.kmMasterId = kmMasterId;
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
        final KmDetailsCriteria that = (KmDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(shares, that.shares) &&
            Objects.equals(sharesMr, that.sharesMr) &&
            Objects.equals(sugarShares, that.sugarShares) &&
            Objects.equals(sugarSharesMr, that.sugarSharesMr) &&
            Objects.equals(deposit, that.deposit) &&
            Objects.equals(depositMr, that.depositMr) &&
            Objects.equals(dueLoan, that.dueLoan) &&
            Objects.equals(dueLoanMr, that.dueLoanMr) &&
            Objects.equals(dueAmount, that.dueAmount) &&
            Objects.equals(dueAmountMr, that.dueAmountMr) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(kmDate, that.kmDate) &&
            Objects.equals(kmFromDate, that.kmFromDate) &&
            Objects.equals(kmToDate, that.kmToDate) &&
            Objects.equals(bagayatHector, that.bagayatHector) &&
            Objects.equals(bagayatHectorMr, that.bagayatHectorMr) &&
            Objects.equals(bagayatAre, that.bagayatAre) &&
            Objects.equals(bagayatAreMr, that.bagayatAreMr) &&
            Objects.equals(jirayatHector, that.jirayatHector) &&
            Objects.equals(jirayatHectorMr, that.jirayatHectorMr) &&
            Objects.equals(jirayatAre, that.jirayatAre) &&
            Objects.equals(jirayatAreMr, that.jirayatAreMr) &&
            Objects.equals(landValue, that.landValue) &&
            Objects.equals(landValueMr, that.landValueMr) &&
            Objects.equals(eAggrementAmt, that.eAggrementAmt) &&
            Objects.equals(eAgreementAmt, that.eAgreementAmt) &&
            Objects.equals(eAgreementDate, that.eAgreementDate) &&
            Objects.equals(bojaAmount, that.bojaAmount) &&
            Objects.equals(bojaAmountMr, that.bojaAmountMr) &&
            Objects.equals(bojaDate, that.bojaDate) &&
            Objects.equals(kmMasterId, that.kmMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            shares,
            sharesMr,
            sugarShares,
            sugarSharesMr,
            deposit,
            depositMr,
            dueLoan,
            dueLoanMr,
            dueAmount,
            dueAmountMr,
            dueDate,
            kmDate,
            kmFromDate,
            kmToDate,
            bagayatHector,
            bagayatHectorMr,
            bagayatAre,
            bagayatAreMr,
            jirayatHector,
            jirayatHectorMr,
            jirayatAre,
            jirayatAreMr,
            landValue,
            landValueMr,
            eAggrementAmt,
            eAgreementAmt,
            eAgreementDate,
            bojaAmount,
            bojaAmountMr,
            bojaDate,
            kmMasterId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (shares != null ? "shares=" + shares + ", " : "") +
            (sharesMr != null ? "sharesMr=" + sharesMr + ", " : "") +
            (sugarShares != null ? "sugarShares=" + sugarShares + ", " : "") +
            (sugarSharesMr != null ? "sugarSharesMr=" + sugarSharesMr + ", " : "") +
            (deposit != null ? "deposit=" + deposit + ", " : "") +
            (depositMr != null ? "depositMr=" + depositMr + ", " : "") +
            (dueLoan != null ? "dueLoan=" + dueLoan + ", " : "") +
            (dueLoanMr != null ? "dueLoanMr=" + dueLoanMr + ", " : "") +
            (dueAmount != null ? "dueAmount=" + dueAmount + ", " : "") +
            (dueAmountMr != null ? "dueAmountMr=" + dueAmountMr + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (kmDate != null ? "kmDate=" + kmDate + ", " : "") +
            (kmFromDate != null ? "kmFromDate=" + kmFromDate + ", " : "") +
            (kmToDate != null ? "kmToDate=" + kmToDate + ", " : "") +
            (bagayatHector != null ? "bagayatHector=" + bagayatHector + ", " : "") +
            (bagayatHectorMr != null ? "bagayatHectorMr=" + bagayatHectorMr + ", " : "") +
            (bagayatAre != null ? "bagayatAre=" + bagayatAre + ", " : "") +
            (bagayatAreMr != null ? "bagayatAreMr=" + bagayatAreMr + ", " : "") +
            (jirayatHector != null ? "jirayatHector=" + jirayatHector + ", " : "") +
            (jirayatHectorMr != null ? "jirayatHectorMr=" + jirayatHectorMr + ", " : "") +
            (jirayatAre != null ? "jirayatAre=" + jirayatAre + ", " : "") +
            (jirayatAreMr != null ? "jirayatAreMr=" + jirayatAreMr + ", " : "") +
            (landValue != null ? "landValue=" + landValue + ", " : "") +
            (landValueMr != null ? "landValueMr=" + landValueMr + ", " : "") +
            (eAggrementAmt != null ? "eAggrementAmt=" + eAggrementAmt + ", " : "") +
            (eAgreementAmt != null ? "eAgreementAmt=" + eAgreementAmt + ", " : "") +
            (eAgreementDate != null ? "eAgreementDate=" + eAgreementDate + ", " : "") +
            (bojaAmount != null ? "bojaAmount=" + bojaAmount + ", " : "") +
            (bojaAmountMr != null ? "bojaAmountMr=" + bojaAmountMr + ", " : "") +
            (bojaDate != null ? "bojaDate=" + bojaDate + ", " : "") +
            (kmMasterId != null ? "kmMasterId=" + kmMasterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
