package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.BatchTransaction} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.BatchTransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /batch-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class BatchTransactionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter status;

    private StringFilter batchDetails;

    private LongFilter applicationCount;
    
    private LongFilter bankCode;
    
    private LongFilter schemeWiseBranchCode;

    private LongFilter packsCode;

    private StringFilter notes;

    private StringFilter batchId;

    private StringFilter batchAckId;

    private Boolean distinct;

    public BatchTransactionCriteria() {}

    public BatchTransactionCriteria(BatchTransactionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.packsCode = other.packsCode == null ? null : other.packsCode.copy();
        this.schemeWiseBranchCode = other.schemeWiseBranchCode == null ? null : other.schemeWiseBranchCode.copy();
        this.bankCode = other.bankCode == null ? null : other.bankCode.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.batchDetails = other.batchDetails == null ? null : other.batchDetails.copy();
        this.applicationCount = other.applicationCount == null ? null : other.applicationCount.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.batchId = other.batchId == null ? null : other.batchId.copy();
        this.batchAckId = other.batchAckId == null ? null : other.batchAckId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BatchTransactionCriteria copy() {
        return new BatchTransactionCriteria(this);
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
    
    
    public LongFilter getSchemeWiseBranchCode() {
        return schemeWiseBranchCode;
    }

    public LongFilter schemeWiseBranchCode() {
        if (schemeWiseBranchCode == null) {
        	schemeWiseBranchCode = new LongFilter();
        }
        return schemeWiseBranchCode;
    }

    public void setSchemeWiseBranchCode(LongFilter schemeWiseBranchCode) {
        this.schemeWiseBranchCode = schemeWiseBranchCode;
    }
    
    
    public LongFilter getBankCode() {
        return bankCode;
    }

    public LongFilter bankCode() {
        if (bankCode == null) {
        	bankCode = new LongFilter();
        }
        return bankCode;
    }

    public void setBankCode(LongFilter bankCode) {
        this.bankCode = bankCode;
    }
    
    public LongFilter getPacksCode() {
        return packsCode;
    }

    public LongFilter packsCode() {
        if (packsCode == null) {
        	packsCode = new LongFilter();
        }
        return packsCode;
    }

    public void setPacksCode(LongFilter packsCode) {
        this.packsCode = packsCode;
    }
    
    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getBatchDetails() {
        return batchDetails;
    }

    public StringFilter batchDetails() {
        if (batchDetails == null) {
            batchDetails = new StringFilter();
        }
        return batchDetails;
    }

    public void setBatchDetails(StringFilter batchDetails) {
        this.batchDetails = batchDetails;
    }

    public LongFilter getApplicationCount() {
        return applicationCount;
    }

    public LongFilter applicationCount() {
        if (applicationCount == null) {
            applicationCount = new LongFilter();
        }
        return applicationCount;
    }

    public void setApplicationCount(LongFilter applicationCount) {
        this.applicationCount = applicationCount;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
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

    public StringFilter getBatchAckId() {
        return batchAckId;
    }

    public StringFilter batchAckId() {
        if (batchAckId == null) {
            batchAckId = new StringFilter();
        }
        return batchAckId;
    }

    public void setBatchAckId(StringFilter batchAckId) {
        this.batchAckId = batchAckId;
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
        final BatchTransactionCriteria that = (BatchTransactionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(packsCode, that.packsCode) &&
            Objects.equals(bankCode, that.bankCode) &&
            Objects.equals(schemeWiseBranchCode, that.schemeWiseBranchCode) &&
            Objects.equals(status, that.status) &&
            Objects.equals(batchDetails, that.batchDetails) &&
            Objects.equals(applicationCount, that.applicationCount) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(batchAckId, that.batchAckId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  packsCode,
                bankCode,
                schemeWiseBranchCode, status, batchDetails, applicationCount, notes, batchId, batchAckId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatchTransactionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (packsCode != null ? "packsCode" + packsCode + ", " : "") +
            (bankCode != null ? "bankCode=" + bankCode + ", " : "") +
            (schemeWiseBranchCode != null ? "schemeWiseBranchCode=" + schemeWiseBranchCode + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (batchDetails != null ? "batchDetails=" + batchDetails + ", " : "") +
            (applicationCount != null ? "applicationCount=" + applicationCount + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (batchId != null ? "batchId=" + batchId + ", " : "") +
            (batchAckId != null ? "batchAckId=" + batchAckId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
