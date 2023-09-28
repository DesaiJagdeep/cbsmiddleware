package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A KMPUpload.
 */
@Entity
@Table(name = "kmpupload")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KMPUpload implements Serializable {

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

    public KMPUpload id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public KMPUpload fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUniqueFileName() {
        return this.uniqueFileName;
    }

    public KMPUpload uniqueFileName(String uniqueFileName) {
        this.setUniqueFileName(uniqueFileName);
        return this;
    }

    public void setUniqueFileName(String uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KMPUpload)) {
            return false;
        }
        return id != null && id.equals(((KMPUpload) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KMPUpload{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", uniqueFileName='" + getUniqueFileName() + "'" +
            "}";
    }
}
