package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KamalCrop} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KamalCropResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kamal-crops?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalCropCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter pacsNumber;

    private StringFilter financialYear;

    private LongFilter kamalSocietyId;

    private LongFilter farmerTypeMasterId;

    private LongFilter seasonMasterId;

    private LongFilter cropMasterId;

    private Boolean distinct;

    public KamalCropCriteria() {}

    public KamalCropCriteria(KamalCropCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.kamalSocietyId = other.kamalSocietyId == null ? null : other.kamalSocietyId.copy();
        this.farmerTypeMasterId = other.farmerTypeMasterId == null ? null : other.farmerTypeMasterId.copy();
        this.seasonMasterId = other.seasonMasterId == null ? null : other.seasonMasterId.copy();
        this.cropMasterId = other.cropMasterId == null ? null : other.cropMasterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KamalCropCriteria copy() {
        return new KamalCropCriteria(this);
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

    public LongFilter getKamalSocietyId() {
        return kamalSocietyId;
    }

    public LongFilter kamalSocietyId() {
        if (kamalSocietyId == null) {
            kamalSocietyId = new LongFilter();
        }
        return kamalSocietyId;
    }

    public void setKamalSocietyId(LongFilter kamalSocietyId) {
        this.kamalSocietyId = kamalSocietyId;
    }

    public LongFilter getFarmerTypeMasterId() {
        return farmerTypeMasterId;
    }

    public LongFilter farmerTypeMasterId() {
        if (farmerTypeMasterId == null) {
            farmerTypeMasterId = new LongFilter();
        }
        return farmerTypeMasterId;
    }

    public void setFarmerTypeMasterId(LongFilter farmerTypeMasterId) {
        this.farmerTypeMasterId = farmerTypeMasterId;
    }

    public LongFilter getSeasonMasterId() {
        return seasonMasterId;
    }

    public LongFilter seasonMasterId() {
        if (seasonMasterId == null) {
            seasonMasterId = new LongFilter();
        }
        return seasonMasterId;
    }

    public void setSeasonMasterId(LongFilter seasonMasterId) {
        this.seasonMasterId = seasonMasterId;
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
        final KamalCropCriteria that = (KamalCropCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(pacsNumber, that.pacsNumber) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(kamalSocietyId, that.kamalSocietyId) &&
            Objects.equals(farmerTypeMasterId, that.farmerTypeMasterId) &&
            Objects.equals(seasonMasterId, that.seasonMasterId) &&
            Objects.equals(cropMasterId, that.cropMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pacsNumber, financialYear, kamalSocietyId, farmerTypeMasterId, seasonMasterId, cropMasterId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalCropCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (kamalSocietyId != null ? "kamalSocietyId=" + kamalSocietyId + ", " : "") +
            (farmerTypeMasterId != null ? "farmerTypeMasterId=" + farmerTypeMasterId + ", " : "") +
            (seasonMasterId != null ? "seasonMasterId=" + seasonMasterId + ", " : "") +
            (cropMasterId != null ? "cropMasterId=" + cropMasterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
