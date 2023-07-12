package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.ApplicationLogHistory} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.ApplicationLogHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /application-log-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationLogHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter errorType;

    private StringFilter errorCode;

    private StringFilter errorMessage;

    private LongFilter rowNumber;

    private LongFilter columnNumber;

    private StringFilter sevierity;

    private StringFilter expectedSolution;

    private StringFilter status;

    private LongFilter issFileParserId;

    private Boolean distinct;

    public ApplicationLogHistoryCriteria() {}

    public ApplicationLogHistoryCriteria(ApplicationLogHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.errorType = other.errorType == null ? null : other.errorType.copy();
        this.errorCode = other.errorCode == null ? null : other.errorCode.copy();
        this.errorMessage = other.errorMessage == null ? null : other.errorMessage.copy();
        this.rowNumber = other.rowNumber == null ? null : other.rowNumber.copy();
        this.columnNumber = other.columnNumber == null ? null : other.columnNumber.copy();
        this.sevierity = other.sevierity == null ? null : other.sevierity.copy();
        this.expectedSolution = other.expectedSolution == null ? null : other.expectedSolution.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.issFileParserId = other.issFileParserId == null ? null : other.issFileParserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApplicationLogHistoryCriteria copy() {
        return new ApplicationLogHistoryCriteria(this);
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

    public StringFilter getErrorType() {
        return errorType;
    }

    public StringFilter errorType() {
        if (errorType == null) {
            errorType = new StringFilter();
        }
        return errorType;
    }

    public void setErrorType(StringFilter errorType) {
        this.errorType = errorType;
    }

    public StringFilter getErrorCode() {
        return errorCode;
    }

    public StringFilter errorCode() {
        if (errorCode == null) {
            errorCode = new StringFilter();
        }
        return errorCode;
    }

    public void setErrorCode(StringFilter errorCode) {
        this.errorCode = errorCode;
    }

    public StringFilter getErrorMessage() {
        return errorMessage;
    }

    public StringFilter errorMessage() {
        if (errorMessage == null) {
            errorMessage = new StringFilter();
        }
        return errorMessage;
    }

    public void setErrorMessage(StringFilter errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LongFilter getRowNumber() {
        return rowNumber;
    }

    public LongFilter rowNumber() {
        if (rowNumber == null) {
            rowNumber = new LongFilter();
        }
        return rowNumber;
    }

    public void setRowNumber(LongFilter rowNumber) {
        this.rowNumber = rowNumber;
    }

    public LongFilter getColumnNumber() {
        return columnNumber;
    }

    public LongFilter columnNumber() {
        if (columnNumber == null) {
            columnNumber = new LongFilter();
        }
        return columnNumber;
    }

    public void setColumnNumber(LongFilter columnNumber) {
        this.columnNumber = columnNumber;
    }

    public StringFilter getSevierity() {
        return sevierity;
    }

    public StringFilter sevierity() {
        if (sevierity == null) {
            sevierity = new StringFilter();
        }
        return sevierity;
    }

    public void setSevierity(StringFilter sevierity) {
        this.sevierity = sevierity;
    }

    public StringFilter getExpectedSolution() {
        return expectedSolution;
    }

    public StringFilter expectedSolution() {
        if (expectedSolution == null) {
            expectedSolution = new StringFilter();
        }
        return expectedSolution;
    }

    public void setExpectedSolution(StringFilter expectedSolution) {
        this.expectedSolution = expectedSolution;
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
        final ApplicationLogHistoryCriteria that = (ApplicationLogHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(errorType, that.errorType) &&
            Objects.equals(errorCode, that.errorCode) &&
            Objects.equals(errorMessage, that.errorMessage) &&
            Objects.equals(rowNumber, that.rowNumber) &&
            Objects.equals(columnNumber, that.columnNumber) &&
            Objects.equals(sevierity, that.sevierity) &&
            Objects.equals(expectedSolution, that.expectedSolution) &&
            Objects.equals(status, that.status) &&
            Objects.equals(issFileParserId, that.issFileParserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            errorType,
            errorCode,
            errorMessage,
            rowNumber,
            columnNumber,
            sevierity,
            expectedSolution,
            status,
            issFileParserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationLogHistoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (errorType != null ? "errorType=" + errorType + ", " : "") +
            (errorCode != null ? "errorCode=" + errorCode + ", " : "") +
            (errorMessage != null ? "errorMessage=" + errorMessage + ", " : "") +
            (rowNumber != null ? "rowNumber=" + rowNumber + ", " : "") +
            (columnNumber != null ? "columnNumber=" + columnNumber + ", " : "") +
            (sevierity != null ? "sevierity=" + sevierity + ", " : "") +
            (expectedSolution != null ? "expectedSolution=" + expectedSolution + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (issFileParserId != null ? "issFileParserId=" + issFileParserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
