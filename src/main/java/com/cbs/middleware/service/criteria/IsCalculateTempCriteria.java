package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.IsCalculateTemp} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.IsCalculateTempResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /is-calculate-temps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IsCalculateTempCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter serialNo;

    private StringFilter financialYear;

    private Boolean distinct;

    public IsCalculateTempCriteria() {}

    public IsCalculateTempCriteria(IsCalculateTempCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serialNo = other.serialNo == null ? null : other.serialNo.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IsCalculateTempCriteria copy() {
        return new IsCalculateTempCriteria(this);
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

    public IntegerFilter getSerialNo() {
        return serialNo;
    }

    public IntegerFilter serialNo() {
        if (serialNo == null) {
            serialNo = new IntegerFilter();
        }
        return serialNo;
    }

    public void setSerialNo(IntegerFilter serialNo) {
        this.serialNo = serialNo;
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
        final IsCalculateTempCriteria that = (IsCalculateTempCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNo, financialYear, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsCalculateTempCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (serialNo != null ? "serialNo=" + serialNo + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
