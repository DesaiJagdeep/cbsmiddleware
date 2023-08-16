package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.CbsDataReport} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.CbsDataReportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cbs-data-reports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbsDataReportCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter farmerName;

    private Boolean distinct;

    public CbsDataReportCriteria() {}

    public CbsDataReportCriteria(CbsDataReportCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.farmerName = other.farmerName == null ? null : other.farmerName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CbsDataReportCriteria copy() {
        return new CbsDataReportCriteria(this);
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

    public StringFilter getFarmerName() {
        return farmerName;
    }

    public StringFilter farmerName() {
        if (farmerName == null) {
            farmerName = new StringFilter();
        }
        return farmerName;
    }

    public void setFarmerName(StringFilter farmerName) {
        this.farmerName = farmerName;
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
        final CbsDataReportCriteria that = (CbsDataReportCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(farmerName, that.farmerName) && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, farmerName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbsDataReportCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (farmerName != null ? "farmerName=" + farmerName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
