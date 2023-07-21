package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A BatchTransaction.
 */
@Entity
@Table(name = "batch_transaction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BatchTransaction extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "batch_details")
    private String batchDetails;

    @Column(name = "batch_errors")
    private String batchErrors;

    @Column(name = "application_count")
    private Long applicationCount;

    @Column(name = "notes")
    private String notes;

    @Column(name = "batch_id")
    private String batchId;

    @Column(name = "batch_ack_id")
    private String batchAckId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BatchTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public BatchTransaction status(String status) {
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

    public BatchTransaction batchDetails(String batchDetails) {
        this.setBatchDetails(batchDetails);
        return this;
    }

    public void setBatchDetails(String batchDetails) {
        this.batchDetails = batchDetails;
    }

    public Long getApplicationCount() {
        return this.applicationCount;
    }

    public BatchTransaction applicationCount(Long applicationCount) {
        this.setApplicationCount(applicationCount);
        return this;
    }

    public void setApplicationCount(Long applicationCount) {
        this.applicationCount = applicationCount;
    }

    public String getNotes() {
        return this.notes;
    }

    public BatchTransaction notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public BatchTransaction batchId(String batchId) {
        this.setBatchId(batchId);
        return this;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchAckId() {
        return this.batchAckId;
    }

    public BatchTransaction batchAckId(String batchAckId) {
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
        if (!(o instanceof BatchTransaction)) {
            return false;
        }
        return id != null && id.equals(((BatchTransaction) o).id);
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
