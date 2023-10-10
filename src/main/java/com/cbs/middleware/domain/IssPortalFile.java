package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A IssPortalFile.
 */
@Entity
@Table(name = "iss_portal_file")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssPortalFile extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "unique_name")
    private String uniqueName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "scheme_wise_branch_code")
    private Long schemeWiseBranchCode;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "from_disbursement_date")
    private LocalDate fromDisbursementDate;

    @Column(name = "to_disbursement_date")
    private LocalDate toDisbursementDate;

    @Column(name = "pacs_name")
    private String pacsName;

    @Column(name = "pacs_code")
    private Long pacsCode;

    @Column(name = "status")
    private String status;

    @Column(name = "application_count")
    private String applicationCount;

    @Column(name = "notes")
    private String notes;

    @Column(name = "error_record_count")
    private Integer errorRecordCount;

    @Column(name = "kcc_error_record_count")
    private Integer kccErrorRecordCount;

    @Column(name = "app_submited_to_kcc_count")
    private Long appSubmitedToKccCount;

    @Column(name = "app_pending_to_submit_count")
    private Long appPendingToSubmitCount;

    @Column(name = "app_accepted_by_kcc_count")
    private Long appAcceptedByKccCount;

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

    public String getFileName() {
        return this.fileName;
    }

    public IssPortalFile fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public Long getAppSubmitedToKccCount() {
        return appSubmitedToKccCount;
    }

    public void setAppSubmitedToKccCount(Long appSubmitedToKccCount) {
        this.appSubmitedToKccCount = appSubmitedToKccCount;
    }

    public Long getAppAcceptedByKccCount() {
        return appAcceptedByKccCount;
    }

    public void setAppAcceptedByKccCount(Long appAcceptedByKccCount) {
        this.appAcceptedByKccCount = appAcceptedByKccCount;
    }

    public Long getAppPendingToSubmitCount() {
        return appPendingToSubmitCount;
    }

    public void setAppPendingToSubmitCount(Long appPendingToSubmitCount) {
        this.appPendingToSubmitCount = appPendingToSubmitCount;
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

    public Long getSchemeWiseBranchCode() {
        return this.schemeWiseBranchCode;
    }

    public IssPortalFile branchCode(Long schemeWiseBranchCode) {
        this.setSchemeWiseBranchCode(schemeWiseBranchCode);
        return this;
    }

    public void setSchemeWiseBranchCode(Long schemeWiseBranchCode) {
        this.schemeWiseBranchCode = schemeWiseBranchCode;
    }

    public Integer getKccErrorRecordCount() {
        return kccErrorRecordCount;
    }

    public void setKccErrorRecordCount(Integer kccErrorRecordCount) {
        this.kccErrorRecordCount = kccErrorRecordCount;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public IssPortalFile financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
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

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

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

    public Integer getErrorRecordCount() {
        return errorRecordCount;
    }

    public void setErrorRecordCount(Integer errorRecordCount) {
        this.errorRecordCount = errorRecordCount;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPacsName() {
        return pacsName;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
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
		return "IssPortalFile{" + "id=" + getId() + ", fileName='" + getFileName() + "'" + ", fileExtension='"
				+ getFileExtension() + "'" + ", setSchemeWiseBranchCode=" + getSchemeWiseBranchCode() + ", financialYear='"
				+ getFinancialYear() + "'" + ", fromDisbursementDate='" + getFromDisbursementDate() + "'"
				+ ", toDisbursementDate='" + getToDisbursementDate() + "'" + ", pacsCode=" + getPacsCode()
				+ ", status='" + getStatus() + "'" + ", applicationCount='" + getApplicationCount() + "'" + ", notes='"
				+ getNotes() + "'" + "}";
	}
}
