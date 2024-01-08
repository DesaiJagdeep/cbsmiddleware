package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KmCrops} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KmCropsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /km-crops?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmCropsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter hector;

    private StringFilter hectorMr;

    private DoubleFilter are;

    private StringFilter areMr;

    private DoubleFilter noOfTree;

    private StringFilter noOfTreeMr;

    private DoubleFilter demand;

    private StringFilter demandMr;

    private StringFilter society;

    private StringFilter societyMr;

    private DoubleFilter bankAmt;

    private StringFilter bankAmtMr;

    private StringFilter vibhagiAdhikari;

    private StringFilter vibhagiAdhikariMr;

    private StringFilter branch;

    private StringFilter branchMr;

    private DoubleFilter inspAmt;

    private StringFilter inspAmtMr;

    private LongFilter cropMasterId;

    private LongFilter kmDetailsId;

    private Boolean distinct;

    public KmCropsCriteria() {}

    public KmCropsCriteria(KmCropsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hector = other.hector == null ? null : other.hector.copy();
        this.hectorMr = other.hectorMr == null ? null : other.hectorMr.copy();
        this.are = other.are == null ? null : other.are.copy();
        this.areMr = other.areMr == null ? null : other.areMr.copy();
        this.noOfTree = other.noOfTree == null ? null : other.noOfTree.copy();
        this.noOfTreeMr = other.noOfTreeMr == null ? null : other.noOfTreeMr.copy();
        this.demand = other.demand == null ? null : other.demand.copy();
        this.demandMr = other.demandMr == null ? null : other.demandMr.copy();
        this.society = other.society == null ? null : other.society.copy();
        this.societyMr = other.societyMr == null ? null : other.societyMr.copy();
        this.bankAmt = other.bankAmt == null ? null : other.bankAmt.copy();
        this.bankAmtMr = other.bankAmtMr == null ? null : other.bankAmtMr.copy();
        this.vibhagiAdhikari = other.vibhagiAdhikari == null ? null : other.vibhagiAdhikari.copy();
        this.vibhagiAdhikariMr = other.vibhagiAdhikariMr == null ? null : other.vibhagiAdhikariMr.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.branchMr = other.branchMr == null ? null : other.branchMr.copy();
        this.inspAmt = other.inspAmt == null ? null : other.inspAmt.copy();
        this.inspAmtMr = other.inspAmtMr == null ? null : other.inspAmtMr.copy();
        this.cropMasterId = other.cropMasterId == null ? null : other.cropMasterId.copy();
        this.kmDetailsId = other.kmDetailsId == null ? null : other.kmDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KmCropsCriteria copy() {
        return new KmCropsCriteria(this);
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

    public DoubleFilter getDemand() {
        return demand;
    }

    public DoubleFilter demand() {
        if (demand == null) {
            demand = new DoubleFilter();
        }
        return demand;
    }

    public void setDemand(DoubleFilter demand) {
        this.demand = demand;
    }

    public StringFilter getDemandMr() {
        return demandMr;
    }

    public StringFilter demandMr() {
        if (demandMr == null) {
            demandMr = new StringFilter();
        }
        return demandMr;
    }

    public void setDemandMr(StringFilter demandMr) {
        this.demandMr = demandMr;
    }

    public StringFilter getSociety() {
        return society;
    }

    public StringFilter society() {
        if (society == null) {
            society = new StringFilter();
        }
        return society;
    }

    public void setSociety(StringFilter society) {
        this.society = society;
    }

    public StringFilter getSocietyMr() {
        return societyMr;
    }

    public StringFilter societyMr() {
        if (societyMr == null) {
            societyMr = new StringFilter();
        }
        return societyMr;
    }

    public void setSocietyMr(StringFilter societyMr) {
        this.societyMr = societyMr;
    }

    public DoubleFilter getBankAmt() {
        return bankAmt;
    }

    public DoubleFilter bankAmt() {
        if (bankAmt == null) {
            bankAmt = new DoubleFilter();
        }
        return bankAmt;
    }

    public void setBankAmt(DoubleFilter bankAmt) {
        this.bankAmt = bankAmt;
    }

    public StringFilter getBankAmtMr() {
        return bankAmtMr;
    }

    public StringFilter bankAmtMr() {
        if (bankAmtMr == null) {
            bankAmtMr = new StringFilter();
        }
        return bankAmtMr;
    }

    public void setBankAmtMr(StringFilter bankAmtMr) {
        this.bankAmtMr = bankAmtMr;
    }

    public StringFilter getVibhagiAdhikari() {
        return vibhagiAdhikari;
    }

    public StringFilter vibhagiAdhikari() {
        if (vibhagiAdhikari == null) {
            vibhagiAdhikari = new StringFilter();
        }
        return vibhagiAdhikari;
    }

    public void setVibhagiAdhikari(StringFilter vibhagiAdhikari) {
        this.vibhagiAdhikari = vibhagiAdhikari;
    }

    public StringFilter getVibhagiAdhikariMr() {
        return vibhagiAdhikariMr;
    }

    public StringFilter vibhagiAdhikariMr() {
        if (vibhagiAdhikariMr == null) {
            vibhagiAdhikariMr = new StringFilter();
        }
        return vibhagiAdhikariMr;
    }

    public void setVibhagiAdhikariMr(StringFilter vibhagiAdhikariMr) {
        this.vibhagiAdhikariMr = vibhagiAdhikariMr;
    }

    public StringFilter getBranch() {
        return branch;
    }

    public StringFilter branch() {
        if (branch == null) {
            branch = new StringFilter();
        }
        return branch;
    }

    public void setBranch(StringFilter branch) {
        this.branch = branch;
    }

    public StringFilter getBranchMr() {
        return branchMr;
    }

    public StringFilter branchMr() {
        if (branchMr == null) {
            branchMr = new StringFilter();
        }
        return branchMr;
    }

    public void setBranchMr(StringFilter branchMr) {
        this.branchMr = branchMr;
    }

    public DoubleFilter getInspAmt() {
        return inspAmt;
    }

    public DoubleFilter inspAmt() {
        if (inspAmt == null) {
            inspAmt = new DoubleFilter();
        }
        return inspAmt;
    }

    public void setInspAmt(DoubleFilter inspAmt) {
        this.inspAmt = inspAmt;
    }

    public StringFilter getInspAmtMr() {
        return inspAmtMr;
    }

    public StringFilter inspAmtMr() {
        if (inspAmtMr == null) {
            inspAmtMr = new StringFilter();
        }
        return inspAmtMr;
    }

    public void setInspAmtMr(StringFilter inspAmtMr) {
        this.inspAmtMr = inspAmtMr;
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
        final KmCropsCriteria that = (KmCropsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(hector, that.hector) &&
            Objects.equals(hectorMr, that.hectorMr) &&
            Objects.equals(are, that.are) &&
            Objects.equals(areMr, that.areMr) &&
            Objects.equals(noOfTree, that.noOfTree) &&
            Objects.equals(noOfTreeMr, that.noOfTreeMr) &&
            Objects.equals(demand, that.demand) &&
            Objects.equals(demandMr, that.demandMr) &&
            Objects.equals(society, that.society) &&
            Objects.equals(societyMr, that.societyMr) &&
            Objects.equals(bankAmt, that.bankAmt) &&
            Objects.equals(bankAmtMr, that.bankAmtMr) &&
            Objects.equals(vibhagiAdhikari, that.vibhagiAdhikari) &&
            Objects.equals(vibhagiAdhikariMr, that.vibhagiAdhikariMr) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(branchMr, that.branchMr) &&
            Objects.equals(inspAmt, that.inspAmt) &&
            Objects.equals(inspAmtMr, that.inspAmtMr) &&
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
            areMr,
            noOfTree,
            noOfTreeMr,
            demand,
            demandMr,
            society,
            societyMr,
            bankAmt,
            bankAmtMr,
            vibhagiAdhikari,
            vibhagiAdhikariMr,
            branch,
            branchMr,
            inspAmt,
            inspAmtMr,
            cropMasterId,
            kmDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmCropsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (hector != null ? "hector=" + hector + ", " : "") +
            (hectorMr != null ? "hectorMr=" + hectorMr + ", " : "") +
            (are != null ? "are=" + are + ", " : "") +
            (areMr != null ? "areMr=" + areMr + ", " : "") +
            (noOfTree != null ? "noOfTree=" + noOfTree + ", " : "") +
            (noOfTreeMr != null ? "noOfTreeMr=" + noOfTreeMr + ", " : "") +
            (demand != null ? "demand=" + demand + ", " : "") +
            (demandMr != null ? "demandMr=" + demandMr + ", " : "") +
            (society != null ? "society=" + society + ", " : "") +
            (societyMr != null ? "societyMr=" + societyMr + ", " : "") +
            (bankAmt != null ? "bankAmt=" + bankAmt + ", " : "") +
            (bankAmtMr != null ? "bankAmtMr=" + bankAmtMr + ", " : "") +
            (vibhagiAdhikari != null ? "vibhagiAdhikari=" + vibhagiAdhikari + ", " : "") +
            (vibhagiAdhikariMr != null ? "vibhagiAdhikariMr=" + vibhagiAdhikariMr + ", " : "") +
            (branch != null ? "branch=" + branch + ", " : "") +
            (branchMr != null ? "branchMr=" + branchMr + ", " : "") +
            (inspAmt != null ? "inspAmt=" + inspAmt + ", " : "") +
            (inspAmtMr != null ? "inspAmtMr=" + inspAmtMr + ", " : "") +
            (cropMasterId != null ? "cropMasterId=" + cropMasterId + ", " : "") +
            (kmDetailsId != null ? "kmDetailsId=" + kmDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
