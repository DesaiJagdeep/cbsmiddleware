package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.IssPortalFile} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.IssPortalFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /iss-portal-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssPortalFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter batchId;

    private StringFilter fileName;

    private StringFilter fileExtension;

    private LongFilter branchCode;

    private StringFilter financialYear;

    private LocalDateFilter fromDisbursementDate;

    private LocalDateFilter toDisbursementDate;

    private LongFilter pacsCode;

    private StringFilter status;

    private StringFilter batchAckId;

    private StringFilter batchDetails;

    private StringFilter applicationCount;

    private StringFilter notes;

    private Boolean distinct;

    public IssPortalFileCriteria() {}

    public IssPortalFileCriteria(IssPortalFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.batchId = other.batchId == null ? null : other.batchId.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.fileExtension = other.fileExtension == null ? null : other.fileExtension.copy();
        this.branchCode = other.branchCode == null ? null : other.branchCode.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.fromDisbursementDate = other.fromDisbursementDate == null ? null : other.fromDisbursementDate.copy();
        this.toDisbursementDate = other.toDisbursementDate == null ? null : other.toDisbursementDate.copy();
        this.pacsCode = other.pacsCode == null ? null : other.pacsCode.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.batchAckId = other.batchAckId == null ? null : other.batchAckId.copy();
        this.batchDetails = other.batchDetails == null ? null : other.batchDetails.copy();
        this.applicationCount = other.applicationCount == null ? null : other.applicationCount.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IssPortalFileCriteria copy() {
        return new IssPortalFileCriteria(this);
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

    public LongFilter getBatchId() {
        return batchId;
    }

    public LongFilter batchId() {
        if (batchId == null) {
            batchId = new LongFilter();
        }
        return batchId;
    }

    public void setBatchId(LongFilter batchId) {
        this.batchId = batchId;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public StringFilter fileName() {
        if (fileName == null) {
            fileName = new StringFilter();
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getFileExtension() {
        return fileExtension;
    }

    public StringFilter fileExtension() {
        if (fileExtension == null) {
            fileExtension = new StringFilter();
        }
        return fileExtension;
    }

    public void setFileExtension(StringFilter fileExtension) {
        this.fileExtension = fileExtension;
    }

    public LongFilter getBranchCode() {
        return branchCode;
    }

    public LongFilter branchCode() {
        if (branchCode == null) {
            branchCode = new LongFilter();
        }
        return branchCode;
    }

    public void setBranchCode(LongFilter branchCode) {
        this.branchCode = branchCode;
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

    public LocalDateFilter getFromDisbursementDate() {
        return fromDisbursementDate;
    }

    public LocalDateFilter fromDisbursementDate() {
        if (fromDisbursementDate == null) {
            fromDisbursementDate = new LocalDateFilter();
        }
        return fromDisbursementDate;
    }

    public void setFromDisbursementDate(LocalDateFilter fromDisbursementDate) {
        this.fromDisbursementDate = fromDisbursementDate;
    }

    public LocalDateFilter getToDisbursementDate() {
        return toDisbursementDate;
    }

    public LocalDateFilter toDisbursementDate() {
        if (toDisbursementDate == null) {
            toDisbursementDate = new LocalDateFilter();
        }
        return toDisbursementDate;
    }

    public void setToDisbursementDate(LocalDateFilter toDisbursementDate) {
        this.toDisbursementDate = toDisbursementDate;
    }

    public LongFilter getPacsCode() {
        return pacsCode;
    }

    public LongFilter pacsCode() {
        if (pacsCode == null) {
            pacsCode = new LongFilter();
        }
        return pacsCode;
    }

    public void setPacsCode(LongFilter pacsCode) {
        this.pacsCode = pacsCode;
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

    public StringFilter getApplicationCount() {
        return applicationCount;
    }

    public StringFilter applicationCount() {
        if (applicationCount == null) {
            applicationCount = new StringFilter();
        }
        return applicationCount;
    }

    public void setApplicationCount(StringFilter applicationCount) {
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
        final IssPortalFileCriteria that = (IssPortalFileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(batchId, that.batchId) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(fileExtension, that.fileExtension) &&
            Objects.equals(branchCode, that.branchCode) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(fromDisbursementDate, that.fromDisbursementDate) &&
            Objects.equals(toDisbursementDate, that.toDisbursementDate) &&
            Objects.equals(pacsCode, that.pacsCode) &&
            Objects.equals(status, that.status) &&
            Objects.equals(batchAckId, that.batchAckId) &&
            Objects.equals(batchDetails, that.batchDetails) &&
            Objects.equals(applicationCount, that.applicationCount) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            batchId,
            fileName,
            fileExtension,
            branchCode,
            financialYear,
            fromDisbursementDate,
            toDisbursementDate,
            pacsCode,
            status,
            batchAckId,
            batchDetails,
            applicationCount,
            notes,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssPortalFileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (batchId != null ? "batchId=" + batchId + ", " : "") +
            (fileName != null ? "fileName=" + fileName + ", " : "") +
            (fileExtension != null ? "fileExtension=" + fileExtension + ", " : "") +
            (branchCode != null ? "branchCode=" + branchCode + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (fromDisbursementDate != null ? "fromDisbursementDate=" + fromDisbursementDate + ", " : "") +
            (toDisbursementDate != null ? "toDisbursementDate=" + toDisbursementDate + ", " : "") +
            (pacsCode != null ? "pacsCode=" + pacsCode + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (batchAckId != null ? "batchAckId=" + batchAckId + ", " : "") +
            (batchDetails != null ? "batchDetails=" + batchDetails + ", " : "") +
            (applicationCount != null ? "applicationCount=" + applicationCount + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
