package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Application.
 */
@Entity
@Table(name = "application_transaction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "batch_id")
    private String batchId;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "record_status")
    private Long recordStatus;

    @Column(name = "application_status")
    private Long applicationStatus;

    @Column(name = "kcc_status")
    private Long kccStatus;

    @Column(name = "recipient_unique_id")
    private String recipientUniqueId;

    @Column(name = "iss_file_portal_id")
    private Long issFilePortalId;

    @Column(name = "farmer_id")
    private String farmerId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "issPortalFile" }, allowSetters = true)
    private IssFileParser issFileParser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Application id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public Application batchId(String batchId) {
        this.setBatchId(batchId);
        return this;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public Application uniqueId(String uniqueId) {
        this.setUniqueId(uniqueId);
        return this;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getRecordStatus() {
        return this.recordStatus;
    }

    public Application recordStatus(Long recordStatus) {
        this.setRecordStatus(recordStatus);
        return this;
    }

    public void setRecordStatus(Long recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Long getApplicationStatus() {
        return this.applicationStatus;
    }

    public Application applicationStatus(Long applicationStatus) {
        this.setApplicationStatus(applicationStatus);
        return this;
    }

    public void setApplicationStatus(Long applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Long getKccStatus() {
        return this.kccStatus;
    }

    public Application kccStatus(Long kccStatus) {
        this.setKccStatus(kccStatus);
        return this;
    }

    public void setKccStatus(Long kccStatus) {
        this.kccStatus = kccStatus;
    }

    public String getRecipientUniqueId() {
        return this.recipientUniqueId;
    }

    public Application recipientUniqueId(String recipientUniqueId) {
        this.setRecipientUniqueId(recipientUniqueId);
        return this;
    }

    public void setRecipientUniqueId(String recipientUniqueId) {
        this.recipientUniqueId = recipientUniqueId;
    }

    public String getFarmerId() {
        return this.farmerId;
    }

    public Application farmerId(String farmerId) {
        this.setFarmerId(farmerId);
        return this;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public IssFileParser getIssFileParser() {
        return this.issFileParser;
    }

    public void setIssFileParser(IssFileParser issFileParser) {
        this.issFileParser = issFileParser;
    }

    public Application issFileParser(IssFileParser issFileParser) {
        this.setIssFileParser(issFileParser);
        return this;
    }

    public Long getIssFilePortalId() {
        return issFilePortalId;
    }

    public void setIssFilePortalId(Long issFilePortalId) {
        this.issFilePortalId = issFilePortalId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
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
		return "Application{" + "id=" + getId() + ", batchId='" + getBatchId() + "'" + ", uniqueId='" + getUniqueId()
				+ "'" + ", recordStatus=" + getRecordStatus() + ", applicationStatus=" + getApplicationStatus()
				+ ", kccStatus=" + getKccStatus() + ", recipientUniqueId='" + getRecipientUniqueId() + "'"
				+ ", farmerId='" + getFarmerId() + "'" + "}";
	}
}
