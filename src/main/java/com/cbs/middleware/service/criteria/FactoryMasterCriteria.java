package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.FactoryMaster} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.FactoryMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /factory-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactoryMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter factoryName;

    private StringFilter factoryNameMr;

    private LongFilter factoryCode;

    private StringFilter factoryCodeMr;

    private StringFilter factoryAddress;

    private StringFilter factoryAddressMr;

    private StringFilter description;

    private BooleanFilter status;

    private Boolean distinct;

    public FactoryMasterCriteria() {}

    public FactoryMasterCriteria(FactoryMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.factoryName = other.factoryName == null ? null : other.factoryName.copy();
        this.factoryNameMr = other.factoryNameMr == null ? null : other.factoryNameMr.copy();
        this.factoryCode = other.factoryCode == null ? null : other.factoryCode.copy();
        this.factoryCodeMr = other.factoryCodeMr == null ? null : other.factoryCodeMr.copy();
        this.factoryAddress = other.factoryAddress == null ? null : other.factoryAddress.copy();
        this.factoryAddressMr = other.factoryAddressMr == null ? null : other.factoryAddressMr.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FactoryMasterCriteria copy() {
        return new FactoryMasterCriteria(this);
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

    public StringFilter getFactoryName() {
        return factoryName;
    }

    public StringFilter factoryName() {
        if (factoryName == null) {
            factoryName = new StringFilter();
        }
        return factoryName;
    }

    public void setFactoryName(StringFilter factoryName) {
        this.factoryName = factoryName;
    }

    public StringFilter getFactoryNameMr() {
        return factoryNameMr;
    }

    public StringFilter factoryNameMr() {
        if (factoryNameMr == null) {
            factoryNameMr = new StringFilter();
        }
        return factoryNameMr;
    }

    public void setFactoryNameMr(StringFilter factoryNameMr) {
        this.factoryNameMr = factoryNameMr;
    }

    public LongFilter getFactoryCode() {
        return factoryCode;
    }

    public LongFilter factoryCode() {
        if (factoryCode == null) {
            factoryCode = new LongFilter();
        }
        return factoryCode;
    }

    public void setFactoryCode(LongFilter factoryCode) {
        this.factoryCode = factoryCode;
    }

    public StringFilter getFactoryCodeMr() {
        return factoryCodeMr;
    }

    public StringFilter factoryCodeMr() {
        if (factoryCodeMr == null) {
            factoryCodeMr = new StringFilter();
        }
        return factoryCodeMr;
    }

    public void setFactoryCodeMr(StringFilter factoryCodeMr) {
        this.factoryCodeMr = factoryCodeMr;
    }

    public StringFilter getFactoryAddress() {
        return factoryAddress;
    }

    public StringFilter factoryAddress() {
        if (factoryAddress == null) {
            factoryAddress = new StringFilter();
        }
        return factoryAddress;
    }

    public void setFactoryAddress(StringFilter factoryAddress) {
        this.factoryAddress = factoryAddress;
    }

    public StringFilter getFactoryAddressMr() {
        return factoryAddressMr;
    }

    public StringFilter factoryAddressMr() {
        if (factoryAddressMr == null) {
            factoryAddressMr = new StringFilter();
        }
        return factoryAddressMr;
    }

    public void setFactoryAddressMr(StringFilter factoryAddressMr) {
        this.factoryAddressMr = factoryAddressMr;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public BooleanFilter status() {
        if (status == null) {
            status = new BooleanFilter();
        }
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
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
        final FactoryMasterCriteria that = (FactoryMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(factoryName, that.factoryName) &&
            Objects.equals(factoryNameMr, that.factoryNameMr) &&
            Objects.equals(factoryCode, that.factoryCode) &&
            Objects.equals(factoryCodeMr, that.factoryCodeMr) &&
            Objects.equals(factoryAddress, that.factoryAddress) &&
            Objects.equals(factoryAddressMr, that.factoryAddressMr) &&
            Objects.equals(description, that.description) &&
            Objects.equals(status, that.status) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            factoryName,
            factoryNameMr,
            factoryCode,
            factoryCodeMr,
            factoryAddress,
            factoryAddressMr,
            description,
            status,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactoryMasterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (factoryName != null ? "factoryName=" + factoryName + ", " : "") +
            (factoryNameMr != null ? "factoryNameMr=" + factoryNameMr + ", " : "") +
            (factoryCode != null ? "factoryCode=" + factoryCode + ", " : "") +
            (factoryCodeMr != null ? "factoryCodeMr=" + factoryCodeMr + ", " : "") +
            (factoryAddress != null ? "factoryAddress=" + factoryAddress + ", " : "") +
            (factoryAddressMr != null ? "factoryAddressMr=" + factoryAddressMr + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
