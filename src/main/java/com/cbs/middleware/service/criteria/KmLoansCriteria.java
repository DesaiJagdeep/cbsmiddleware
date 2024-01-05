package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KmLoans} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KmLoansResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /km-loans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmLoansCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cropName;

    private StringFilter cropNameMr;

    private InstantFilter loanDate;

    private DoubleFilter loanAmount;

    private StringFilter loanAmountMr;

    private DoubleFilter are;

    private StringFilter areMr;

    private DoubleFilter receivableAmt;

    private StringFilter receivableAmtMr;

    private DoubleFilter dueAmt;

    private StringFilter dueAmtMr;

    private InstantFilter dueDate;

    private LongFilter kmDetailsId;

    private Boolean distinct;

    public KmLoansCriteria() {}

    public KmLoansCriteria(KmLoansCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cropName = other.cropName == null ? null : other.cropName.copy();
        this.cropNameMr = other.cropNameMr == null ? null : other.cropNameMr.copy();
        this.loanDate = other.loanDate == null ? null : other.loanDate.copy();
        this.loanAmount = other.loanAmount == null ? null : other.loanAmount.copy();
        this.loanAmountMr = other.loanAmountMr == null ? null : other.loanAmountMr.copy();
        this.are = other.are == null ? null : other.are.copy();
        this.areMr = other.areMr == null ? null : other.areMr.copy();
        this.receivableAmt = other.receivableAmt == null ? null : other.receivableAmt.copy();
        this.receivableAmtMr = other.receivableAmtMr == null ? null : other.receivableAmtMr.copy();
        this.dueAmt = other.dueAmt == null ? null : other.dueAmt.copy();
        this.dueAmtMr = other.dueAmtMr == null ? null : other.dueAmtMr.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.kmDetailsId = other.kmDetailsId == null ? null : other.kmDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KmLoansCriteria copy() {
        return new KmLoansCriteria(this);
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

    public StringFilter getCropNameMr() {
        return cropNameMr;
    }

    public StringFilter cropNameMr() {
        if (cropNameMr == null) {
            cropNameMr = new StringFilter();
        }
        return cropNameMr;
    }

    public void setCropNameMr(StringFilter cropNameMr) {
        this.cropNameMr = cropNameMr;
    }

    public InstantFilter getLoanDate() {
        return loanDate;
    }

    public InstantFilter loanDate() {
        if (loanDate == null) {
            loanDate = new InstantFilter();
        }
        return loanDate;
    }

    public void setLoanDate(InstantFilter loanDate) {
        this.loanDate = loanDate;
    }

    public DoubleFilter getLoanAmount() {
        return loanAmount;
    }

    public DoubleFilter loanAmount() {
        if (loanAmount == null) {
            loanAmount = new DoubleFilter();
        }
        return loanAmount;
    }

    public void setLoanAmount(DoubleFilter loanAmount) {
        this.loanAmount = loanAmount;
    }

    public StringFilter getLoanAmountMr() {
        return loanAmountMr;
    }

    public StringFilter loanAmountMr() {
        if (loanAmountMr == null) {
            loanAmountMr = new StringFilter();
        }
        return loanAmountMr;
    }

    public void setLoanAmountMr(StringFilter loanAmountMr) {
        this.loanAmountMr = loanAmountMr;
    }

    public DoubleFilter getAre() {
        return are;
    }

    public DoubleFilter are() {
        if (are == null) {
            are = new DoubleFilter();
        }
        return are;
    }

    public void setAre(DoubleFilter are) {
        this.are = are;
    }

    public StringFilter getAreMr() {
        return areMr;
    }

    public StringFilter areMr() {
        if (areMr == null) {
            areMr = new StringFilter();
        }
        return areMr;
    }

    public void setAreMr(StringFilter areMr) {
        this.areMr = areMr;
    }

    public DoubleFilter getReceivableAmt() {
        return receivableAmt;
    }

    public DoubleFilter receivableAmt() {
        if (receivableAmt == null) {
            receivableAmt = new DoubleFilter();
        }
        return receivableAmt;
    }

    public void setReceivableAmt(DoubleFilter receivableAmt) {
        this.receivableAmt = receivableAmt;
    }

    public StringFilter getReceivableAmtMr() {
        return receivableAmtMr;
    }

    public StringFilter receivableAmtMr() {
        if (receivableAmtMr == null) {
            receivableAmtMr = new StringFilter();
        }
        return receivableAmtMr;
    }

    public void setReceivableAmtMr(StringFilter receivableAmtMr) {
        this.receivableAmtMr = receivableAmtMr;
    }

    public DoubleFilter getDueAmt() {
        return dueAmt;
    }

    public DoubleFilter dueAmt() {
        if (dueAmt == null) {
            dueAmt = new DoubleFilter();
        }
        return dueAmt;
    }

    public void setDueAmt(DoubleFilter dueAmt) {
        this.dueAmt = dueAmt;
    }

    public StringFilter getDueAmtMr() {
        return dueAmtMr;
    }

    public StringFilter dueAmtMr() {
        if (dueAmtMr == null) {
            dueAmtMr = new StringFilter();
        }
        return dueAmtMr;
    }

    public void setDueAmtMr(StringFilter dueAmtMr) {
        this.dueAmtMr = dueAmtMr;
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

    public LongFilter getKmDetailsId() {
        return kmDetailsId;
    }

    public LongFilter kmDetailsId() {
        if (kmDetailsId == null) {
            kmDetailsId = new LongFilter();
        }
        return kmDetailsId;
    }

    public void setKmDetailsId(LongFilter kmDetailsId) {
        this.kmDetailsId = kmDetailsId;
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
        final KmLoansCriteria that = (KmLoansCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cropName, that.cropName) &&
            Objects.equals(cropNameMr, that.cropNameMr) &&
            Objects.equals(loanDate, that.loanDate) &&
            Objects.equals(loanAmount, that.loanAmount) &&
            Objects.equals(loanAmountMr, that.loanAmountMr) &&
            Objects.equals(are, that.are) &&
            Objects.equals(areMr, that.areMr) &&
            Objects.equals(receivableAmt, that.receivableAmt) &&
            Objects.equals(receivableAmtMr, that.receivableAmtMr) &&
            Objects.equals(dueAmt, that.dueAmt) &&
            Objects.equals(dueAmtMr, that.dueAmtMr) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(kmDetailsId, that.kmDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            cropName,
            cropNameMr,
            loanDate,
            loanAmount,
            loanAmountMr,
            are,
            areMr,
            receivableAmt,
            receivableAmtMr,
            dueAmt,
            dueAmtMr,
            dueDate,
            kmDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmLoansCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cropName != null ? "cropName=" + cropName + ", " : "") +
            (cropNameMr != null ? "cropNameMr=" + cropNameMr + ", " : "") +
            (loanDate != null ? "loanDate=" + loanDate + ", " : "") +
            (loanAmount != null ? "loanAmount=" + loanAmount + ", " : "") +
            (loanAmountMr != null ? "loanAmountMr=" + loanAmountMr + ", " : "") +
            (are != null ? "are=" + are + ", " : "") +
            (areMr != null ? "areMr=" + areMr + ", " : "") +
            (receivableAmt != null ? "receivableAmt=" + receivableAmt + ", " : "") +
            (receivableAmtMr != null ? "receivableAmtMr=" + receivableAmtMr + ", " : "") +
            (dueAmt != null ? "dueAmt=" + dueAmt + ", " : "") +
            (dueAmtMr != null ? "dueAmtMr=" + dueAmtMr + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (kmDetailsId != null ? "kmDetailsId=" + kmDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
