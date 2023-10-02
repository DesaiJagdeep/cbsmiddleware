package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KarkhanaVasuli} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KarkhanaVasuliResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /karkhana-vasulis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KarkhanaVasuliCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter khataNumber;

    private StringFilter name;

    private StringFilter societyName;

    private StringFilter talukaName;

    private StringFilter branchName;

    private StringFilter defaulterName;

    private Boolean distinct;

    public KarkhanaVasuliCriteria() {}

    public KarkhanaVasuliCriteria(KarkhanaVasuliCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.khataNumber = other.khataNumber == null ? null : other.khataNumber.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.societyName = other.societyName == null ? null : other.societyName.copy();
        this.talukaName = other.talukaName == null ? null : other.talukaName.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.defaulterName = other.defaulterName == null ? null : other.defaulterName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KarkhanaVasuliCriteria copy() {
        return new KarkhanaVasuliCriteria(this);
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

    public StringFilter getKhataNumber() {
        return khataNumber;
    }

    public StringFilter khataNumber() {
        if (khataNumber == null) {
            khataNumber = new StringFilter();
        }
        return khataNumber;
    }

    public void setKhataNumber(StringFilter khataNumber) {
        this.khataNumber = khataNumber;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSocietyName() {
        return societyName;
    }

    public StringFilter societyName() {
        if (societyName == null) {
            societyName = new StringFilter();
        }
        return societyName;
    }

    public void setSocietyName(StringFilter societyName) {
        this.societyName = societyName;
    }

    public StringFilter getTalukaName() {
        return talukaName;
    }

    public StringFilter talukaName() {
        if (talukaName == null) {
            talukaName = new StringFilter();
        }
        return talukaName;
    }

    public void setTalukaName(StringFilter talukaName) {
        this.talukaName = talukaName;
    }

    public StringFilter getBranchName() {
        return branchName;
    }

    public StringFilter branchName() {
        if (branchName == null) {
            branchName = new StringFilter();
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public StringFilter getDefaulterName() {
        return defaulterName;
    }

    public StringFilter defaulterName() {
        if (defaulterName == null) {
            defaulterName = new StringFilter();
        }
        return defaulterName;
    }

    public void setDefaulterName(StringFilter defaulterName) {
        this.defaulterName = defaulterName;
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
        final KarkhanaVasuliCriteria that = (KarkhanaVasuliCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(khataNumber, that.khataNumber) &&
            Objects.equals(name, that.name) &&
            Objects.equals(societyName, that.societyName) &&
            Objects.equals(talukaName, that.talukaName) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(defaulterName, that.defaulterName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, khataNumber, name, societyName, talukaName, branchName, defaulterName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KarkhanaVasuliCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (khataNumber != null ? "khataNumber=" + khataNumber + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (societyName != null ? "societyName=" + societyName + ", " : "") +
            (talukaName != null ? "talukaName=" + talukaName + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (defaulterName != null ? "defaulterName=" + defaulterName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
