package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.CropRateMaster} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.CropRateMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /crop-rate-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CropRateMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter financialYear;

    private DoubleFilter currentAmount;

    private StringFilter currentAmountMr;

    private DoubleFilter percentage;

    private BooleanFilter activeFlag;

    private LongFilter cropMasterId;

    private Boolean distinct;

    public CropRateMasterCriteria() {}

    public CropRateMasterCriteria(CropRateMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.currentAmount = other.currentAmount == null ? null : other.currentAmount.copy();
        this.currentAmountMr = other.currentAmountMr == null ? null : other.currentAmountMr.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.activeFlag = other.activeFlag == null ? null : other.activeFlag.copy();
        this.cropMasterId = other.cropMasterId == null ? null : other.cropMasterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CropRateMasterCriteria copy() {
        return new CropRateMasterCriteria(this);
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

    public DoubleFilter getCurrentAmount() {
        return currentAmount;
    }

    public DoubleFilter currentAmount() {
        if (currentAmount == null) {
            currentAmount = new DoubleFilter();
        }
        return currentAmount;
    }

    public void setCurrentAmount(DoubleFilter currentAmount) {
        this.currentAmount = currentAmount;
    }

    public StringFilter getCurrentAmountMr() {
        return currentAmountMr;
    }

    public StringFilter currentAmountMr() {
        if (currentAmountMr == null) {
            currentAmountMr = new StringFilter();
        }
        return currentAmountMr;
    }

    public void setCurrentAmountMr(StringFilter currentAmountMr) {
        this.currentAmountMr = currentAmountMr;
    }

    public DoubleFilter getPercentage() {
        return percentage;
    }

    public DoubleFilter percentage() {
        if (percentage == null) {
            percentage = new DoubleFilter();
        }
        return percentage;
    }

    public void setPercentage(DoubleFilter percentage) {
        this.percentage = percentage;
    }

    public BooleanFilter getActiveFlag() {
        return activeFlag;
    }

    public BooleanFilter activeFlag() {
        if (activeFlag == null) {
            activeFlag = new BooleanFilter();
        }
        return activeFlag;
    }

    public void setActiveFlag(BooleanFilter activeFlag) {
        this.activeFlag = activeFlag;
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
        final CropRateMasterCriteria that = (CropRateMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(currentAmount, that.currentAmount) &&
            Objects.equals(currentAmountMr, that.currentAmountMr) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(activeFlag, that.activeFlag) &&
            Objects.equals(cropMasterId, that.cropMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, financialYear, currentAmount, currentAmountMr, percentage, activeFlag, cropMasterId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CropRateMasterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (currentAmount != null ? "currentAmount=" + currentAmount + ", " : "") +
            (currentAmountMr != null ? "currentAmountMr=" + currentAmountMr + ", " : "") +
            (percentage != null ? "percentage=" + percentage + ", " : "") +
            (activeFlag != null ? "activeFlag=" + activeFlag + ", " : "") +
            (cropMasterId != null ? "cropMasterId=" + cropMasterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
