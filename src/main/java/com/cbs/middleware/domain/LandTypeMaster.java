package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A LandTypeMaster.
 */
@Entity
@Table(name = "land_type_master")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class LandTypeMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "land_type_code")
    private Integer landTypeCode;

    @Column(name = "land_type")
    private String landType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LandTypeMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLandTypeCode() {
        return this.landTypeCode;
    }

    public LandTypeMaster landTypeCode(Integer landTypeCode) {
        this.setLandTypeCode(landTypeCode);
        return this;
    }

    public void setLandTypeCode(Integer landTypeCode) {
        this.landTypeCode = landTypeCode;
    }

    public String getLandType() {
        return this.landType;
    }

    public LandTypeMaster landType(String landType) {
        this.setLandType(landType);
        return this;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandTypeMaster)) {
            return false;
        }
        return id != null && id.equals(((LandTypeMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LandTypeMaster{" +
            "id=" + getId() +
            ", landTypeCode=" + getLandTypeCode() +
            ", landType='" + getLandType() + "'" +
            "}";
    }
}
