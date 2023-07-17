package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A ApplicationLog.
 */
@Entity
@Table(name = "application_log")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "error_type")
    private String errorType;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "column_number")
    private Long columnNumber;

    @Column(name = "sevierity")
    private String sevierity;

    @Column(name = "expected_solution")
    private String expectedSolution;

    @Column(name = "status")
    private String status;

    @Column(name = "jhi_row_number")
    private Long rowNumber;

    @Column(name = "batch_id")
    private String batchId;

    @Column(name = "error_record_count")
    private Integer errorRecordCount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "issPortalFile" }, allowSetters = true)
    private IssFileParser issFileParser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public ApplicationLog errorType(String errorType) {
        this.setErrorType(errorType);
        return this;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public ApplicationLog errorCode(String errorCode) {
        this.setErrorCode(errorCode);
        return this;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public ApplicationLog errorMessage(String errorMessage) {
        this.setErrorMessage(errorMessage);
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getColumnNumber() {
        return this.columnNumber;
    }

    public ApplicationLog columnNumber(Long columnNumber) {
        this.setColumnNumber(columnNumber);
        return this;
    }

    public void setColumnNumber(Long columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getSevierity() {
        return this.sevierity;
    }

    public ApplicationLog sevierity(String sevierity) {
        this.setSevierity(sevierity);
        return this;
    }

    public void setSevierity(String sevierity) {
        this.sevierity = sevierity;
    }

    public String getExpectedSolution() {
        return this.expectedSolution;
    }

    public ApplicationLog expectedSolution(String expectedSolution) {
        this.setExpectedSolution(expectedSolution);
        return this;
    }

    public void setExpectedSolution(String expectedSolution) {
        this.expectedSolution = expectedSolution;
    }

    public String getStatus() {
        return this.status;
    }

    public ApplicationLog status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRowNumber() {
        return this.rowNumber;
    }

    public ApplicationLog rowNumber(Long rowNumber) {
        this.setRowNumber(rowNumber);
        return this;
    }

    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public ApplicationLog batchId(String batchId) {
        this.setBatchId(batchId);
        return this;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public IssFileParser getIssFileParser() {
        return this.issFileParser;
    }

    public void setIssFileParser(IssFileParser issFileParser) {
        this.issFileParser = issFileParser;
    }

    public ApplicationLog issFileParser(IssFileParser issFileParser) {
        this.setIssFileParser(issFileParser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationLog)) {
            return false;
        }
        return id != null && id.equals(((ApplicationLog) o).id);
    }

    public Integer getErrorRecordCount() {
        return errorRecordCount;
    }

    public void setErrorRecordCount(Integer errorRecordCount) {
        this.errorRecordCount = errorRecordCount;
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "ApplicationLog{" + "id=" + getId() + ", errorType='" + getErrorType() + "'" + ", errorCode='"
				+ getErrorCode() + "'" + ", errorMessage='" + getErrorMessage() + "'" + ", columnNumber="
				+ getColumnNumber() + ", sevierity='" + getSevierity() + "'" + ", expectedSolution='"
				+ getExpectedSolution() + "'" + ", status='" + getStatus() + "'" + ", rowNumber=" + getRowNumber()
				+ ", batchId='" + getBatchId() + "'" + "}";
	}
}
