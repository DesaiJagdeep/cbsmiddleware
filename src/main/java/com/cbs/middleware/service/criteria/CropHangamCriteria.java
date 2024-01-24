package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.CropHangam} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.CropHangamResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /crop-hangams?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CropHangamCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter hangam;

    private StringFilter hangamMr;

    private Boolean distinct;

    public CropHangamCriteria() {}

    public CropHangamCriteria(CropHangamCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hangam = other.hangam == null ? null : other.hangam.copy();
        this.hangamMr = other.hangamMr == null ? null : other.hangamMr.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CropHangamCriteria copy() {
        return new CropHangamCriteria(this);
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

    public StringFilter getHangam() {
        return hangam;
    }

    public StringFilter hangam() {
        if (hangam == null) {
            hangam = new StringFilter();
        }
        return hangam;
    }

    public void setHangam(StringFilter hangam) {
        this.hangam = hangam;
    }

    public StringFilter getHangamMr() {
        return hangamMr;
    }

    public StringFilter hangamMr() {
        if (hangamMr == null) {
            hangamMr = new StringFilter();
        }
        return hangamMr;
    }

    public void setHangamMr(StringFilter hangamMr) {
        this.hangamMr = hangamMr;
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
        final CropHangamCriteria that = (CropHangamCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(hangam, that.hangam) &&
            Objects.equals(hangamMr, that.hangamMr) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hangam, hangamMr, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CropHangamCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (hangam != null ? "hangam=" + hangam + ", " : "") +
            (hangamMr != null ? "hangamMr=" + hangamMr + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
