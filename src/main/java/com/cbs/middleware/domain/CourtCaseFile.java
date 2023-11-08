package com.cbs.middleware.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A CourtCase.
 */
@Entity
@Table(name = "court_case_file")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCaseFile extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "unique_file_name")
    private String uniqueFileName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourtCaseFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
        if (!(o instanceof CourtCaseFile)) {
            return false;
        }
        return id != null && id.equals(((CourtCaseFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCase{" +
            "id=" + getId() +
            "}";
    }
}
