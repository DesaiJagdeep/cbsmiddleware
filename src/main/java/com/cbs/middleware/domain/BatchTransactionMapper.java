package com.cbs.middleware.domain;

import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 * A BatchTransaction.
 */
@Component
public class BatchTransactionMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String status;

    private String batchDetails;

    private String batchErrors;

    private Long applicationCount;

    private String notes;

    private String batchId;

    private String batchAckId;

    private String issPortalId;

    private String fileName;

    private String branchName;

    private String packsName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BatchTransactionMapper id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPacksName() {
        return packsName;
    }

    public void setPacksName(String packsName) {
        this.packsName = packsName;
    }

    public BatchTransactionMapper status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatchDetails() {
        return this.batchDetails;
    }

    public String getBatchErrors() {
        return batchErrors;
    }

    public void setBatchErrors(String batchErrors) {
        this.batchErrors = batchErrors;
    }

    public BatchTransactionMapper batchDetails(String batchDetails) {
        this.setBatchDetails(batchDetails);
        return this;
    }

    public void setBatchDetails(String batchDetails) {
        this.batchDetails = batchDetails;
    }

    public Long getApplicationCount() {
        return this.applicationCount;
    }

    public BatchTransactionMapper applicationCount(Long applicationCount) {
        this.setApplicationCount(applicationCount);
        return this;
    }

    public void setApplicationCount(Long applicationCount) {
        this.applicationCount = applicationCount;
    }

    public String getNotes() {
        return this.notes;
    }

    public BatchTransactionMapper notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public BatchTransactionMapper batchId(String batchId) {
        this.setBatchId(batchId);
        return this;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchAckId() {
        return this.batchAckId;
    }

    public BatchTransactionMapper batchAckId(String batchAckId) {
        this.setBatchAckId(batchAckId);
        return this;
    }

    public void setBatchAckId(String batchAckId) {
        this.batchAckId = batchAckId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchTransactionMapper)) {
            return false;
        }
        return id != null && id.equals(((BatchTransactionMapper) o).id);
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
		return "BatchTransaction{" + "id=" + getId() + ", status='" + getStatus() + "'" + ", batchDetails='"
				+ getBatchDetails() + "'" + ", applicationCount=" + getApplicationCount() + ", notes='" + getNotes()
				+ "'" + ", batchId='" + getBatchId() + "'" + ", batchAckId='" + getBatchAckId() + "'" + "}";
	}
}
