package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A DesignationMaster.
 */
@Entity
@Table(name = "designation_master")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class DesignationMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "designation_code")
    private String designationCode;

    @Column(name = "designation_name")
    private String designationName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DesignationMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignationCode() {
        return this.designationCode;
    }

    public DesignationMaster designationCode(String designationCode) {
        this.setDesignationCode(designationCode);
        return this;
    }

    public void setDesignationCode(String designationCode) {
        this.designationCode = designationCode;
    }

    public String getDesignationName() {
        return this.designationName;
    }

    public DesignationMaster designationName(String designationName) {
        this.setDesignationName(designationName);
        return this;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DesignationMaster)) {
            return false;
        }
        return id != null && id.equals(((DesignationMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DesignationMaster{" +
            "id=" + getId() +
            ", designationCode='" + getDesignationCode() + "'" +
            ", designationName='" + getDesignationName() + "'" +
            "}";
    }
}
