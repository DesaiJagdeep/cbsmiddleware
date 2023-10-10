package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A IssPortalFile.
 */
@Entity
@Table(name = "iss_child_portal_file")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssChildPortalFile extends AbstractAuditingEntity<Long> implements Serializable {

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

    @ManyToOne
    private IssPortalFile issPortalFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssChildPortalFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public IssChildPortalFile fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public IssChildPortalFile fileExtension(String fileExtension) {
        this.setFileExtension(fileExtension);
        return this;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Long getSchemeWiseBranchCode() {
        return this.schemeWiseBranchCode;
    }

    public IssChildPortalFile branchCode(Long schemeWiseBranchCode) {
        this.setSchemeWiseBranchCode(schemeWiseBranchCode);
        return this;
    }

    public void setSchemeWiseBranchCode(Long schemeWiseBranchCode) {
        this.schemeWiseBranchCode = schemeWiseBranchCode;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public IssChildPortalFile financialYear(String financialYear) {
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

    public IssChildPortalFile fromDisbursementDate(LocalDate fromDisbursementDate) {
        this.setFromDisbursementDate(fromDisbursementDate);
        return this;
    }

    public void setFromDisbursementDate(LocalDate fromDisbursementDate) {
        this.fromDisbursementDate = fromDisbursementDate;
    }

    public LocalDate getToDisbursementDate() {
        return this.toDisbursementDate;
    }

    public IssChildPortalFile toDisbursementDate(LocalDate toDisbursementDate) {
        this.setToDisbursementDate(toDisbursementDate);
        return this;
    }

    public void setToDisbursementDate(LocalDate toDisbursementDate) {
        this.toDisbursementDate = toDisbursementDate;
    }

    public Long getPacsCode() {
        return this.pacsCode;
    }

    public IssChildPortalFile pacsCode(Long pacsCode) {
        this.setPacsCode(pacsCode);
        return this;
    }

    public void setPacsCode(Long pacsCode) {
        this.pacsCode = pacsCode;
    }

    public String getStatus() {
        return this.status;
    }

    public IssChildPortalFile status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplicationCount() {
        return this.applicationCount;
    }

    public IssChildPortalFile applicationCount(String applicationCount) {
        this.setApplicationCount(applicationCount);
        return this;
    }

    public void setApplicationCount(String applicationCount) {
        this.applicationCount = applicationCount;
    }

    public String getNotes() {
        return this.notes;
    }

    public IssChildPortalFile notes(String notes) {
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
        if (!(o instanceof IssChildPortalFile)) {
            return false;
        }
        return id != null && id.equals(((IssChildPortalFile) o).id);
    }

    public Integer getErrorRecordCount() {
        return errorRecordCount;
    }

    public void setErrorRecordCount(Integer errorRecordCount) {
        this.errorRecordCount = errorRecordCount;
    }

    public IssPortalFile getIssPortalFile() {
        return issPortalFile;
    }

    public void setIssPortalFile(IssPortalFile issPortalFile) {
        this.issPortalFile = issPortalFile;
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
				+ getFileExtension() + "'" + ", SchemeWiseBranchCode=" + getSchemeWiseBranchCode() + ", financialYear='"
				+ getFinancialYear() + "'" + ", fromDisbursementDate='" + getFromDisbursementDate() + "'"
				+ ", toDisbursementDate='" + getToDisbursementDate() + "'" + ", pacsCode=" + getPacsCode()
				+ ", status='" + getStatus() + "'" + ", applicationCount='" + getApplicationCount() + "'" + ", notes='"
				+ getNotes() + "'" + "}";
	}
}
