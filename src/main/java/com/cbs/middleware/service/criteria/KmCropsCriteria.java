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

    private StringFilter cropName;

    private StringFilter cropNameMr;

    private DoubleFilter hector;

    private StringFilter hectorMr;

    private DoubleFilter are;

    private StringFilter areMr;

    private DoubleFilter prviousAmt;

    private StringFilter previousAmtMr;

    private DoubleFilter demand;

    private StringFilter demandMr;

    private StringFilter society;

    private StringFilter societyMr;

    private DoubleFilter bankAmt;

    private StringFilter bankAmtMr;

    private DoubleFilter noOfTree;

    private StringFilter noOfTreeMr;

    private LongFilter kmDetailsId;

    private Boolean distinct;

    public KmCropsCriteria() {}

    public KmCropsCriteria(KmCropsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cropName = other.cropName == null ? null : other.cropName.copy();
        this.cropNameMr = other.cropNameMr == null ? null : other.cropNameMr.copy();
        this.hector = other.hector == null ? null : other.hector.copy();
        this.hectorMr = other.hectorMr == null ? null : other.hectorMr.copy();
        this.are = other.are == null ? null : other.are.copy();
        this.areMr = other.areMr == null ? null : other.areMr.copy();
        this.prviousAmt = other.prviousAmt == null ? null : other.prviousAmt.copy();
        this.previousAmtMr = other.previousAmtMr == null ? null : other.previousAmtMr.copy();
        this.demand = other.demand == null ? null : other.demand.copy();
        this.demandMr = other.demandMr == null ? null : other.demandMr.copy();
        this.society = other.society == null ? null : other.society.copy();
        this.societyMr = other.societyMr == null ? null : other.societyMr.copy();
        this.bankAmt = other.bankAmt == null ? null : other.bankAmt.copy();
        this.bankAmtMr = other.bankAmtMr == null ? null : other.bankAmtMr.copy();
        this.noOfTree = other.noOfTree == null ? null : other.noOfTree.copy();
        this.noOfTreeMr = other.noOfTreeMr == null ? null : other.noOfTreeMr.copy();
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

    public DoubleFilter getPrviousAmt() {
        return prviousAmt;
    }

    public DoubleFilter prviousAmt() {
        if (prviousAmt == null) {
            prviousAmt = new DoubleFilter();
        }
        return prviousAmt;
    }

    public void setPrviousAmt(DoubleFilter prviousAmt) {
        this.prviousAmt = prviousAmt;
    }

    public StringFilter getPreviousAmtMr() {
        return previousAmtMr;
    }

    public StringFilter previousAmtMr() {
        if (previousAmtMr == null) {
            previousAmtMr = new StringFilter();
        }
        return previousAmtMr;
    }

    public void setPreviousAmtMr(StringFilter previousAmtMr) {
        this.previousAmtMr = previousAmtMr;
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
            Objects.equals(cropName, that.cropName) &&
            Objects.equals(cropNameMr, that.cropNameMr) &&
            Objects.equals(hector, that.hector) &&
            Objects.equals(hectorMr, that.hectorMr) &&
            Objects.equals(are, that.are) &&
            Objects.equals(areMr, that.areMr) &&
            Objects.equals(prviousAmt, that.prviousAmt) &&
            Objects.equals(previousAmtMr, that.previousAmtMr) &&
            Objects.equals(demand, that.demand) &&
            Objects.equals(demandMr, that.demandMr) &&
            Objects.equals(society, that.society) &&
            Objects.equals(societyMr, that.societyMr) &&
            Objects.equals(bankAmt, that.bankAmt) &&
            Objects.equals(bankAmtMr, that.bankAmtMr) &&
            Objects.equals(noOfTree, that.noOfTree) &&
            Objects.equals(noOfTreeMr, that.noOfTreeMr) &&
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
            hector,
            hectorMr,
            are,
            areMr,
            prviousAmt,
            previousAmtMr,
            demand,
            demandMr,
            society,
            societyMr,
            bankAmt,
            bankAmtMr,
            noOfTree,
            noOfTreeMr,
            kmDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmCropsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cropName != null ? "cropName=" + cropName + ", " : "") +
            (cropNameMr != null ? "cropNameMr=" + cropNameMr + ", " : "") +
            (hector != null ? "hector=" + hector + ", " : "") +
            (hectorMr != null ? "hectorMr=" + hectorMr + ", " : "") +
            (are != null ? "are=" + are + ", " : "") +
            (areMr != null ? "areMr=" + areMr + ", " : "") +
            (prviousAmt != null ? "prviousAmt=" + prviousAmt + ", " : "") +
            (previousAmtMr != null ? "previousAmtMr=" + previousAmtMr + ", " : "") +
            (demand != null ? "demand=" + demand + ", " : "") +
            (demandMr != null ? "demandMr=" + demandMr + ", " : "") +
            (society != null ? "society=" + society + ", " : "") +
            (societyMr != null ? "societyMr=" + societyMr + ", " : "") +
            (bankAmt != null ? "bankAmt=" + bankAmt + ", " : "") +
            (bankAmtMr != null ? "bankAmtMr=" + bankAmtMr + ", " : "") +
            (noOfTree != null ? "noOfTree=" + noOfTree + ", " : "") +
            (noOfTreeMr != null ? "noOfTreeMr=" + noOfTreeMr + ", " : "") +
            (kmDetailsId != null ? "kmDetailsId=" + kmDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
