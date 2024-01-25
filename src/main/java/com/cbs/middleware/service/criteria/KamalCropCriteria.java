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

    private IntegerFilter pacsNumber;

    private IntegerFilter memNo;

    private DoubleFilter memHector;

    private StringFilter memNoMr;

    private StringFilter memHectorMr;

    private DoubleFilter memAar;

    private StringFilter memAarMr;

    private LongFilter farmerTypeMasterId;

    private LongFilter seasonMasterId;

    private LongFilter cropMasterId;

    private LongFilter kamalSocietyId;

    private Boolean distinct;

    public KamalCropCriteria() {}

    public KamalCropCriteria(KamalCropCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.memNo = other.memNo == null ? null : other.memNo.copy();
        this.memHector = other.memHector == null ? null : other.memHector.copy();
        this.memNoMr = other.memNoMr == null ? null : other.memNoMr.copy();
        this.memHectorMr = other.memHectorMr == null ? null : other.memHectorMr.copy();
        this.memAar = other.memAar == null ? null : other.memAar.copy();
        this.memAarMr = other.memAarMr == null ? null : other.memAarMr.copy();
        this.farmerTypeMasterId = other.farmerTypeMasterId == null ? null : other.farmerTypeMasterId.copy();
        this.seasonMasterId = other.seasonMasterId == null ? null : other.seasonMasterId.copy();
        this.cropMasterId = other.cropMasterId == null ? null : other.cropMasterId.copy();
        this.kamalSocietyId = other.kamalSocietyId == null ? null : other.kamalSocietyId.copy();
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

    public IntegerFilter getPacsNumber() {
        return pacsNumber;
    }

    public IntegerFilter pacsNumber() {
        if (pacsNumber == null) {
            pacsNumber = new IntegerFilter();
        }
        return pacsNumber;
    }

    public void setPacsNumber(IntegerFilter pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public IntegerFilter getMemNo() {
        return memNo;
    }

    public IntegerFilter memNo() {
        if (memNo == null) {
            memNo = new IntegerFilter();
        }
        return memNo;
    }

    public void setMemNo(IntegerFilter memNo) {
        this.memNo = memNo;
    }

    public DoubleFilter getMemHector() {
        return memHector;
    }

    public DoubleFilter memHector() {
        if (memHector == null) {
            memHector = new DoubleFilter();
        }
        return memHector;
    }

    public void setMemHector(DoubleFilter memHector) {
        this.memHector = memHector;
    }

    public StringFilter getMemNoMr() {
        return memNoMr;
    }

    public StringFilter memNoMr() {
        if (memNoMr == null) {
            memNoMr = new StringFilter();
        }
        return memNoMr;
    }

    public void setMemNoMr(StringFilter memNoMr) {
        this.memNoMr = memNoMr;
    }

    public StringFilter getMemHectorMr() {
        return memHectorMr;
    }

    public StringFilter memHectorMr() {
        if (memHectorMr == null) {
            memHectorMr = new StringFilter();
        }
        return memHectorMr;
    }

    public void setMemHectorMr(StringFilter memHectorMr) {
        this.memHectorMr = memHectorMr;
    }

    public DoubleFilter getMemAar() {
        return memAar;
    }

    public DoubleFilter memAar() {
        if (memAar == null) {
            memAar = new DoubleFilter();
        }
        return memAar;
    }

    public void setMemAar(DoubleFilter memAar) {
        this.memAar = memAar;
    }

    public StringFilter getMemAarMr() {
        return memAarMr;
    }

    public StringFilter memAarMr() {
        if (memAarMr == null) {
            memAarMr = new StringFilter();
        }
        return memAarMr;
    }

    public void setMemAarMr(StringFilter memAarMr) {
        this.memAarMr = memAarMr;
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
            Objects.equals(memNo, that.memNo) &&
            Objects.equals(memHector, that.memHector) &&
            Objects.equals(memNoMr, that.memNoMr) &&
            Objects.equals(memHectorMr, that.memHectorMr) &&
            Objects.equals(memAar, that.memAar) &&
            Objects.equals(memAarMr, that.memAarMr) &&
            Objects.equals(farmerTypeMasterId, that.farmerTypeMasterId) &&
            Objects.equals(seasonMasterId, that.seasonMasterId) &&
            Objects.equals(cropMasterId, that.cropMasterId) &&
            Objects.equals(kamalSocietyId, that.kamalSocietyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            pacsNumber,
            memNo,
            memHector,
            memNoMr,
            memHectorMr,
            memAar,
            memAarMr,
            farmerTypeMasterId,
            seasonMasterId,
            cropMasterId,
            kamalSocietyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalCropCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (memNo != null ? "memNo=" + memNo + ", " : "") +
            (memHector != null ? "memHector=" + memHector + ", " : "") +
            (memNoMr != null ? "memNoMr=" + memNoMr + ", " : "") +
            (memHectorMr != null ? "memHectorMr=" + memHectorMr + ", " : "") +
            (memAar != null ? "memAar=" + memAar + ", " : "") +
            (memAarMr != null ? "memAarMr=" + memAarMr + ", " : "") +
            (farmerTypeMasterId != null ? "farmerTypeMasterId=" + farmerTypeMasterId + ", " : "") +
            (seasonMasterId != null ? "seasonMasterId=" + seasonMasterId + ", " : "") +
            (cropMasterId != null ? "cropMasterId=" + cropMasterId + ", " : "") +
            (kamalSocietyId != null ? "kamalSocietyId=" + kamalSocietyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
