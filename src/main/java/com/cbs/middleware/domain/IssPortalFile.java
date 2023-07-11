package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A IssPortalFile.
 */
@Entity
@Table(name = "iss_portal_file")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssPortalFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "batch_id")
    private Long batchId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "branch_code")
    private Long branchCode;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "from_disbursement_date")
    private LocalDate fromDisbursementDate;

    @Column(name = "to_disbursement_date")
    private LocalDate toDisbursementDate;

    @Column(name = "pacs_code")
    private Long pacsCode;

    @Column(name = "status")
    private String status;

    @Column(name = "batch_ack_id")
    private String batchAckId;

    @Column(name = "batch_details")
    private String batchDetails;

    @Column(name = "application_count")
    private String applicationCount;

    @Column(name = "notes")
    private String notes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssPortalFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBatchId() {
        return this.batchId;
    }

    public IssPortalFile batchId(Long batchId) {
        this.setBatchId(batchId);
        return this;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public IssPortalFile fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public IssPortalFile fileExtension(String fileExtension) {
        this.setFileExtension(fileExtension);
        return this;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Long getBranchCode() {
        return this.branchCode;
    }

    public IssPortalFile branchCode(Long branchCode) {
        this.setBranchCode(branchCode);
        return this;
    }

    public void setBranchCode(Long branchCode) {
        this.branchCode = branchCode;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public IssPortalFile financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public LocalDate getFromDisbursementDate() {
        return this.fromDisbursementDate;
    }

    public IssPortalFile fromDisbursementDate(LocalDate fromDisbursementDate) {
        this.setFromDisbursementDate(fromDisbursementDate);
        return this;
    }

    public void setFromDisbursementDate(LocalDate fromDisbursementDate) {
        this.fromDisbursementDate = fromDisbursementDate;
    }

    public LocalDate getToDisbursementDate() {
        return this.toDisbursementDate;
    }

    public IssPortalFile toDisbursementDate(LocalDate toDisbursementDate) {
        this.setToDisbursementDate(toDisbursementDate);
        return this;
    }

    public void setToDisbursementDate(LocalDate toDisbursementDate) {
        this.toDisbursementDate = toDisbursementDate;
    }

    public Long getPacsCode() {
        return this.pacsCode;
    }

    public IssPortalFile pacsCode(Long pacsCode) {
        this.setPacsCode(pacsCode);
        return this;
    }

    public void setPacsCode(Long pacsCode) {
        this.pacsCode = pacsCode;
    }

    public String getStatus() {
        return this.status;
    }

    public IssPortalFile status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatchAckId() {
        return this.batchAckId;
    }

    public IssPortalFile batchAckId(String batchAckId) {
        this.setBatchAckId(batchAckId);
        return this;
    }

    public void setBatchAckId(String batchAckId) {
        this.batchAckId = batchAckId;
    }

    public String getBatchDetails() {
        return this.batchDetails;
    }

    public IssPortalFile batchDetails(String batchDetails) {
        this.setBatchDetails(batchDetails);
        return this;
    }

    public void setBatchDetails(String batchDetails) {
        this.batchDetails = batchDetails;
    }

    public String getApplicationCount() {
        return this.applicationCount;
    }

    public IssPortalFile applicationCount(String applicationCount) {
        this.setApplicationCount(applicationCount);
        return this;
    }

    public void setApplicationCount(String applicationCount) {
        this.applicationCount = applicationCount;
    }

    public String getNotes() {
        return this.notes;
    }

    public IssPortalFile notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssPortalFile)) {
            return false;
        }
        return id != null && id.equals(((IssPortalFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
    // prettier-ignore
    /*
     * @Override public String toString() { return "IssPortalFile{" + "id=" +
     * getId() + ", batchId=" + getBatchId() + ", fileName='" + getFileName() + "'"
     * + ", fileExtension='" + getFileExtension() + "'" + ", branchCode=" +
     * getBranchCode() + ", financialYear='" + getFinancialYear() + "'" +
     * ", fromDisbursementDate='" + getFromDisbursementDate() + "'" +
     * ", toDisbursementDate='" + getToDisbursementDate() + "'" + ", pacsCode=" +
     * getPacsCode() + ", status='" + getStatus() + "'" + ", batchAckId='" +
     * getBatchAckId() + "'" + ", batchDetails='" + getBatchDetails() + "'" +
     * ", applicationCount='" + getApplicationCount() + "'" + ", notes='" +
     * getNotes() + "'" + "}"; }
     */
}
