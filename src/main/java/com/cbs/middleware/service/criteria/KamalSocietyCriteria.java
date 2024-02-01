package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KamalSociety} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KamalSocietyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kamal-societies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalSocietyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter financialYear;

    private InstantFilter kmDate;

    private Boolean distinct;

    public KamalSocietyCriteria() {}

    public KamalSocietyCriteria(KamalSocietyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.kmDate = other.kmDate == null ? null : other.kmDate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KamalSocietyCriteria copy() {
        return new KamalSocietyCriteria(this);
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
        final KamalSocietyCriteria that = (KamalSocietyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(kmDate, that.kmDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, financialYear, kmDate, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalSocietyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (kmDate != null ? "kmDate=" + kmDate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
