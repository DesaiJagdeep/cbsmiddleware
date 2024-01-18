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

    private DoubleFilter hector;

    private StringFilter hectorMr;

    private DoubleFilter are;

    private StringFilter aremr;

    private DoubleFilter noOfTree;

    private StringFilter noOfTreeMr;

    private DoubleFilter sanctionAmt;

    private StringFilter sanctionAmtMr;

    private DoubleFilter loanAmt;

    private StringFilter loanAmtMr;

    private DoubleFilter receivableAmt;

    private StringFilter receivableAmtMr;

    private DoubleFilter dueAmt;

    private StringFilter dueAmtMr;

    private InstantFilter dueDate;

    private StringFilter dueDateMr;

    private StringFilter spare;

    private LongFilter cropMasterId;

    private LongFilter kmDetailsId;

    private Boolean distinct;

    public KmLoansCriteria() {}

    public KmLoansCriteria(KmLoansCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hector = other.hector == null ? null : other.hector.copy();
        this.hectorMr = other.hectorMr == null ? null : other.hectorMr.copy();
        this.are = other.are == null ? null : other.are.copy();
        this.aremr = other.aremr == null ? null : other.aremr.copy();
        this.noOfTree = other.noOfTree == null ? null : other.noOfTree.copy();
        this.noOfTreeMr = other.noOfTreeMr == null ? null : other.noOfTreeMr.copy();
        this.sanctionAmt = other.sanctionAmt == null ? null : other.sanctionAmt.copy();
        this.sanctionAmtMr = other.sanctionAmtMr == null ? null : other.sanctionAmtMr.copy();
        this.loanAmt = other.loanAmt == null ? null : other.loanAmt.copy();
        this.loanAmtMr = other.loanAmtMr == null ? null : other.loanAmtMr.copy();
        this.receivableAmt = other.receivableAmt == null ? null : other.receivableAmt.copy();
        this.receivableAmtMr = other.receivableAmtMr == null ? null : other.receivableAmtMr.copy();
        this.dueAmt = other.dueAmt == null ? null : other.dueAmt.copy();
        this.dueAmtMr = other.dueAmtMr == null ? null : other.dueAmtMr.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.dueDateMr = other.dueDateMr == null ? null : other.dueDateMr.copy();
        this.spare = other.spare == null ? null : other.spare.copy();
        this.cropMasterId = other.cropMasterId == null ? null : other.cropMasterId.copy();
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

    public DoubleFilter getHector() {
        return hector;
    }

    public DoubleFilter hector() {
        if (hector == null) {
            hector = new DoubleFilter();
        }
        return hector;
    }

    public void setHector(DoubleFilter hector) {
        this.hector = hector;
    }

    public StringFilter getHectorMr() {
        return hectorMr;
    }

    public StringFilter hectorMr() {
        if (hectorMr == null) {
            hectorMr = new StringFilter();
        }
        return hectorMr;
    }

    public void setHectorMr(StringFilter hectorMr) {
        this.hectorMr = hectorMr;
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

    public StringFilter getAremr() {
        return aremr;
    }

    public StringFilter aremr() {
        if (aremr == null) {
            aremr = new StringFilter();
        }
        return aremr;
    }

    public void setAremr(StringFilter aremr) {
        this.aremr = aremr;
    }

    public DoubleFilter getNoOfTree() {
        return noOfTree;
    }

    public DoubleFilter noOfTree() {
        if (noOfTree == null) {
            noOfTree = new DoubleFilter();
        }
        return noOfTree;
    }

    public void setNoOfTree(DoubleFilter noOfTree) {
        this.noOfTree = noOfTree;
    }

    public StringFilter getNoOfTreeMr() {
        return noOfTreeMr;
    }

    public StringFilter noOfTreeMr() {
        if (noOfTreeMr == null) {
            noOfTreeMr = new StringFilter();
        }
        return noOfTreeMr;
    }

    public void setNoOfTreeMr(StringFilter noOfTreeMr) {
        this.noOfTreeMr = noOfTreeMr;
    }

    public DoubleFilter getSanctionAmt() {
        return sanctionAmt;
    }

    public DoubleFilter sanctionAmt() {
        if (sanctionAmt == null) {
            sanctionAmt = new DoubleFilter();
        }
        return sanctionAmt;
    }

    public void setSanctionAmt(DoubleFilter sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public StringFilter getSanctionAmtMr() {
        return sanctionAmtMr;
    }

    public StringFilter sanctionAmtMr() {
        if (sanctionAmtMr == null) {
            sanctionAmtMr = new StringFilter();
        }
        return sanctionAmtMr;
    }

    public void setSanctionAmtMr(StringFilter sanctionAmtMr) {
        this.sanctionAmtMr = sanctionAmtMr;
    }

    public DoubleFilter getLoanAmt() {
        return loanAmt;
    }

    public DoubleFilter loanAmt() {
        if (loanAmt == null) {
            loanAmt = new DoubleFilter();
        }
        return loanAmt;
    }

    public void setLoanAmt(DoubleFilter loanAmt) {
        this.loanAmt = loanAmt;
    }

    public StringFilter getLoanAmtMr() {
        return loanAmtMr;
    }

    public StringFilter loanAmtMr() {
        if (loanAmtMr == null) {
            loanAmtMr = new StringFilter();
        }
        return loanAmtMr;
    }

    public void setLoanAmtMr(StringFilter loanAmtMr) {
        this.loanAmtMr = loanAmtMr;
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

    public StringFilter getDueDateMr() {
        return dueDateMr;
    }

    public StringFilter dueDateMr() {
        if (dueDateMr == null) {
            dueDateMr = new StringFilter();
        }
        return dueDateMr;
    }

    public void setDueDateMr(StringFilter dueDateMr) {
        this.dueDateMr = dueDateMr;
    }

    public StringFilter getSpare() {
        return spare;
    }

    public StringFilter spare() {
        if (spare == null) {
            spare = new StringFilter();
        }
        return spare;
    }

    public void setSpare(StringFilter spare) {
        this.spare = spare;
    }

    public LongFilter getCropMasterId() {
        return cropMasterId;
    }

    public LongFilter cropMasterId() {
        if (cropMasterId == null) {
            cropMasterId = new LongFilter();
        }
        return cropMasterId;
    }

    public void setCropMasterId(LongFilter cropMasterId) {
        this.cropMasterId = cropMasterId;
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
            Objects.equals(hector, that.hector) &&
            Objects.equals(hectorMr, that.hectorMr) &&
            Objects.equals(are, that.are) &&
            Objects.equals(aremr, that.aremr) &&
            Objects.equals(noOfTree, that.noOfTree) &&
            Objects.equals(noOfTreeMr, that.noOfTreeMr) &&
            Objects.equals(sanctionAmt, that.sanctionAmt) &&
            Objects.equals(sanctionAmtMr, that.sanctionAmtMr) &&
            Objects.equals(loanAmt, that.loanAmt) &&
            Objects.equals(loanAmtMr, that.loanAmtMr) &&
            Objects.equals(receivableAmt, that.receivableAmt) &&
            Objects.equals(receivableAmtMr, that.receivableAmtMr) &&
            Objects.equals(dueAmt, that.dueAmt) &&
            Objects.equals(dueAmtMr, that.dueAmtMr) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(dueDateMr, that.dueDateMr) &&
            Objects.equals(spare, that.spare) &&
            Objects.equals(cropMasterId, that.cropMasterId) &&
            Objects.equals(kmDetailsId, that.kmDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            hector,
            hectorMr,
            are,
            aremr,
            noOfTree,
            noOfTreeMr,
            sanctionAmt,
            sanctionAmtMr,
            loanAmt,
            loanAmtMr,
            receivableAmt,
            receivableAmtMr,
            dueAmt,
            dueAmtMr,
            dueDate,
            dueDateMr,
            spare,
            cropMasterId,
            kmDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmLoansCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (hector != null ? "hector=" + hector + ", " : "") +
            (hectorMr != null ? "hectorMr=" + hectorMr + ", " : "") +
            (are != null ? "are=" + are + ", " : "") +
            (aremr != null ? "aremr=" + aremr + ", " : "") +
            (noOfTree != null ? "noOfTree=" + noOfTree + ", " : "") +
            (noOfTreeMr != null ? "noOfTreeMr=" + noOfTreeMr + ", " : "") +
            (sanctionAmt != null ? "sanctionAmt=" + sanctionAmt + ", " : "") +
            (sanctionAmtMr != null ? "sanctionAmtMr=" + sanctionAmtMr + ", " : "") +
            (loanAmt != null ? "loanAmt=" + loanAmt + ", " : "") +
            (loanAmtMr != null ? "loanAmtMr=" + loanAmtMr + ", " : "") +
            (receivableAmt != null ? "receivableAmt=" + receivableAmt + ", " : "") +
            (receivableAmtMr != null ? "receivableAmtMr=" + receivableAmtMr + ", " : "") +
            (dueAmt != null ? "dueAmt=" + dueAmt + ", " : "") +
            (dueAmtMr != null ? "dueAmtMr=" + dueAmtMr + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (dueDateMr != null ? "dueDateMr=" + dueDateMr + ", " : "") +
            (spare != null ? "spare=" + spare + ", " : "") +
            (cropMasterId != null ? "cropMasterId=" + cropMasterId + ", " : "") +
            (kmDetailsId != null ? "kmDetailsId=" + kmDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
