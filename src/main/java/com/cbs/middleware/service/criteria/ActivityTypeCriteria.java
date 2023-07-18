package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.ActivityType} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.ActivityTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activity-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivityTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter activityType;

    private IntegerFilter activityTypeCode;

    private Boolean distinct;

    public ActivityTypeCriteria() {}

    public ActivityTypeCriteria(ActivityTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.activityType = other.activityType == null ? null : other.activityType.copy();
        this.activityTypeCode = other.activityTypeCode == null ? null : other.activityTypeCode.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ActivityTypeCriteria copy() {
        return new ActivityTypeCriteria(this);
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

    public StringFilter getActivityType() {
        return activityType;
    }

    public StringFilter activityType() {
        if (activityType == null) {
            activityType = new StringFilter();
        }
        return activityType;
    }

    public void setActivityType(StringFilter activityType) {
        this.activityType = activityType;
    }

    public IntegerFilter getActivityTypeCode() {
        return activityTypeCode;
    }

    public IntegerFilter activityTypeCode() {
        if (activityTypeCode == null) {
            activityTypeCode = new IntegerFilter();
        }
        return activityTypeCode;
    }

    public void setActivityTypeCode(IntegerFilter activityTypeCode) {
        this.activityTypeCode = activityTypeCode;
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
        final ActivityTypeCriteria that = (ActivityTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(activityType, that.activityType) &&
            Objects.equals(activityTypeCode, that.activityTypeCode) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityType, activityTypeCode, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (activityType != null ? "activityType=" + activityType + ", " : "") +
            (activityTypeCode != null ? "activityTypeCode=" + activityTypeCode + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
