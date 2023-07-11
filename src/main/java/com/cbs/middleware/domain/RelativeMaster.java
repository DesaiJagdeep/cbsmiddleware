package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A RelativeMaster.
 */
@Entity
@Table(name = "relative_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelativeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "relative_code")
    private Integer relativeCode;

    @Column(name = "relative_name")
    private String relativeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RelativeMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRelativeCode() {
        return this.relativeCode;
    }

    public RelativeMaster relativeCode(Integer relativeCode) {
        this.setRelativeCode(relativeCode);
        return this;
    }

    public void setRelativeCode(Integer relativeCode) {
        this.relativeCode = relativeCode;
    }

    public String getRelativeName() {
        return this.relativeName;
    }

    public RelativeMaster relativeName(String relativeName) {
        this.setRelativeName(relativeName);
        return this;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelativeMaster)) {
            return false;
        }
        return id != null && id.equals(((RelativeMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelativeMaster{" +
            "id=" + getId() +
            ", relativeCode=" + getRelativeCode() +
            ", relativeName='" + getRelativeName() + "'" +
            "}";
    }
}
