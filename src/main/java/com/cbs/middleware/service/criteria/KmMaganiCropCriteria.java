package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KmMaganiCrop} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KmMaganiCropResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /km-magani-crops?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMaganiCropCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cropName;

    private LongFilter kmMaganiId;

    private Boolean distinct;

    public KmMaganiCropCriteria() {}

    public KmMaganiCropCriteria(KmMaganiCropCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cropName = other.cropName == null ? null : other.cropName.copy();
        this.kmMaganiId = other.kmMaganiId == null ? null : other.kmMaganiId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KmMaganiCropCriteria copy() {
        return new KmMaganiCropCriteria(this);
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

    public LongFilter getKmMaganiId() {
        return kmMaganiId;
    }

    public LongFilter kmMaganiId() {
        if (kmMaganiId == null) {
            kmMaganiId = new LongFilter();
        }
        return kmMaganiId;
    }

    public void setKmMaganiId(LongFilter kmMaganiId) {
        this.kmMaganiId = kmMaganiId;
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
        final KmMaganiCropCriteria that = (KmMaganiCropCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cropName, that.cropName) &&
            Objects.equals(kmMaganiId, that.kmMaganiId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cropName, kmMaganiId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMaganiCropCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cropName != null ? "cropName=" + cropName + ", " : "") +
            (kmMaganiId != null ? "kmMaganiId=" + kmMaganiId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
