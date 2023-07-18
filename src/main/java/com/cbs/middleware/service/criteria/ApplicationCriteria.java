package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.Application} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.ApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter batchId;

    private StringFilter uniqueId;

    private LongFilter recordStatus;

    private LongFilter applicationStatus;

    private LongFilter kccStatus;

    private StringFilter recipientUniqueId;

    private StringFilter farmerId;

    private LongFilter issFileParserId;

    private Boolean distinct;

    public ApplicationCriteria() {}

    public ApplicationCriteria(ApplicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.batchId = other.batchId == null ? null : other.batchId.copy();
        this.uniqueId = other.uniqueId == null ? null : other.uniqueId.copy();
        this.recordStatus = other.recordStatus == null ? null : other.recordStatus.copy();
        this.applicationStatus = other.applicationStatus == null ? null : other.applicationStatus.copy();
        this.kccStatus = other.kccStatus == null ? null : other.kccStatus.copy();
        this.recipientUniqueId = other.recipientUniqueId == null ? null : other.recipientUniqueId.copy();
        this.farmerId = other.farmerId == null ? null : other.farmerId.copy();
        this.issFileParserId = other.issFileParserId == null ? null : other.issFileParserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApplicationCriteria copy() {
        return new ApplicationCriteria(this);
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

    public StringFilter getBatchId() {
        return batchId;
    }

    public StringFilter batchId() {
        if (batchId == null) {
            batchId = new StringFilter();
        }
        return batchId;
    }

    public void setBatchId(StringFilter batchId) {
        this.batchId = batchId;
    }

    public StringFilter getUniqueId() {
        return uniqueId;
    }

    public StringFilter uniqueId() {
        if (uniqueId == null) {
            uniqueId = new StringFilter();
        }
        return uniqueId;
    }

    public void setUniqueId(StringFilter uniqueId) {
        this.uniqueId = uniqueId;
    }

    public LongFilter getRecordStatus() {
        return recordStatus;
    }

    public LongFilter recordStatus() {
        if (recordStatus == null) {
            recordStatus = new LongFilter();
        }
        return recordStatus;
    }

    public void setRecordStatus(LongFilter recordStatus) {
        this.recordStatus = recordStatus;
    }

    public LongFilter getApplicationStatus() {
        return applicationStatus;
    }

    public LongFilter applicationStatus() {
        if (applicationStatus == null) {
            applicationStatus = new LongFilter();
        }
        return applicationStatus;
    }

    public void setApplicationStatus(LongFilter applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LongFilter getKccStatus() {
        return kccStatus;
    }

    public LongFilter kccStatus() {
        if (kccStatus == null) {
            kccStatus = new LongFilter();
        }
        return kccStatus;
    }

    public void setKccStatus(LongFilter kccStatus) {
        this.kccStatus = kccStatus;
    }

    public StringFilter getRecipientUniqueId() {
        return recipientUniqueId;
    }

    public StringFilter recipientUniqueId() {
        if (recipientUniqueId == null) {
            recipientUniqueId = new StringFilter();
        }
        return recipientUniqueId;
    }

    public void setRecipientUniqueId(StringFilter recipientUniqueId) {
        this.recipientUniqueId = recipientUniqueId;
    }

    public StringFilter getFarmerId() {
        return farmerId;
    }

    public StringFilter farmerId() {
        if (farmerId == null) {
            farmerId = new StringFilter();
        }
        return farmerId;
    }

    public void setFarmerId(StringFilter farmerId) {
        this.farmerId = farmerId;
    }

    public LongFilter getIssFileParserId() {
        return issFileParserId;
    }

    public LongFilter issFileParserId() {
        if (issFileParserId == null) {
            issFileParserId = new LongFilter();
        }
        return issFileParserId;
    }

    public void setIssFileParserId(LongFilter issFileParserId) {
        this.issFileParserId = issFileParserId;
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
        final ApplicationCriteria that = (ApplicationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(uniqueId, that.uniqueId) &&
            Objects.equals(recordStatus, that.recordStatus) &&
            Objects.equals(applicationStatus, that.applicationStatus) &&
            Objects.equals(kccStatus, that.kccStatus) &&
            Objects.equals(recipientUniqueId, that.recipientUniqueId) &&
            Objects.equals(farmerId, that.farmerId) &&
            Objects.equals(issFileParserId, that.issFileParserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            batchId,
            uniqueId,
            recordStatus,
            applicationStatus,
            kccStatus,
            recipientUniqueId,
            farmerId,
            issFileParserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (batchId != null ? "batchId=" + batchId + ", " : "") +
            (uniqueId != null ? "uniqueId=" + uniqueId + ", " : "") +
            (recordStatus != null ? "recordStatus=" + recordStatus + ", " : "") +
            (applicationStatus != null ? "applicationStatus=" + applicationStatus + ", " : "") +
            (kccStatus != null ? "kccStatus=" + kccStatus + ", " : "") +
            (recipientUniqueId != null ? "recipientUniqueId=" + recipientUniqueId + ", " : "") +
            (farmerId != null ? "farmerId=" + farmerId + ", " : "") +
            (issFileParserId != null ? "issFileParserId=" + issFileParserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}