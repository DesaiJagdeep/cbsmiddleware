package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A KarkhanaVasuli.
 */
@Entity
@Table(name = "karkhana_vasuli")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class KarkhanaVasuli extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "unique_file_name")
    private String uniqueFileName;

    @Column(name = "khata_number")
    private String khataNumber;

    @Column(name = "karkhana_name")
    private String karkhanaName;

    @Column(name = "society_name")
    private String societyName;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "defaulter_name")
    private String defaulterName;

    //english data
    @Column(name = "khata_number_en")
    private String khataNumberEn;

    @Column(name = "karkhana_name_en")
    private String karkhanaNameEn;

    @Column(name = "society_name_en")
    private String societyNameEn;

    @Column(name = "taluka_name_en")
    private String talukaNameEn;

    @Column(name = "branch_name_en")
    private String branchNameEn;

    @Column(name = "defaulter_name_en")
    private String defaulterNameEn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KarkhanaVasuli id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKhataNumber() {
        return this.khataNumber;
    }

    public KarkhanaVasuli khataNumber(String khataNumber) {
        this.setKhataNumber(khataNumber);
        return this;
    }

    public void setKhataNumber(String khataNumber) {
        this.khataNumber = khataNumber;
    }

    public String getKarkhanaName() {
        return this.karkhanaName;
    }

    public KarkhanaVasuli karkhanaName(String karkhanaName) {
        this.setKarkhanaName(karkhanaName);
        return this;
    }

    public void setKarkhanaName(String karkhanaName) {
        this.karkhanaName = karkhanaName;
    }

    public String getSocietyName() {
        return this.societyName;
    }

    public String getKhataNumberEn() {
        return khataNumberEn;
    }

    public void setKhataNumberEn(String khataNumberEn) {
        this.khataNumberEn = khataNumberEn;
    }

    public String getKarkhanaNameEn() {
        return karkhanaNameEn;
    }

    public void setKarkhanaNameEn(String karkhanaNameEn) {
        this.karkhanaNameEn = karkhanaNameEn;
    }

    public String getSocietyNameEn() {
        return societyNameEn;
    }

    public void setSocietyNameEn(String societyNameEn) {
        this.societyNameEn = societyNameEn;
    }

    public String getTalukaNameEn() {
        return talukaNameEn;
    }

    public void setTalukaNameEn(String talukaNameEn) {
        this.talukaNameEn = talukaNameEn;
    }

    public String getBranchNameEn() {
        return branchNameEn;
    }

    public void setBranchNameEn(String branchNameEn) {
        this.branchNameEn = branchNameEn;
    }

    public String getDefaulterNameEn() {
        return defaulterNameEn;
    }

    public void setDefaulterNameEn(String defaulterNameEn) {
        this.defaulterNameEn = defaulterNameEn;
    }

    public KarkhanaVasuli societyName(String societyName) {
        this.setSocietyName(societyName);
        return this;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getTalukaName() {
        return this.talukaName;
    }

    public KarkhanaVasuli talukaName(String talukaName) {
        this.setTalukaName(talukaName);
        return this;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public KarkhanaVasuli branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDefaulterName() {
        return this.defaulterName;
    }

    public KarkhanaVasuli defaulterName(String defaulterName) {
        this.setDefaulterName(defaulterName);
        return this;
    }

    public void setDefaulterName(String defaulterName) {
        this.defaulterName = defaulterName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUniqueFileName() {
        return uniqueFileName;
    }

    public void setUniqueFileName(String uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KarkhanaVasuli)) {
            return false;
        }
        return id != null && id.equals(((KarkhanaVasuli) o).id);
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
		return "KarkhanaVasuli{" + "id=" + getId() + ", khataNumber='" + getKhataNumber() + "'" + ", karkhanaNameName='" + getKarkhanaName()
				+ "'" + ", societyName='" + getSocietyName() + "'" + ", talukaName='" + getTalukaName() + "'"
				+ ", branchName='" + getBranchName() + "'" + ", defaulterName='" + getDefaulterName() + "'" + "}";
	}
}
