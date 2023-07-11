package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A FarmerTypeMaster.
 */
@Entity
@Table(name = "farmer_type_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FarmerTypeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "farmer_type_code")
    private Integer farmerTypeCode;

    @Column(name = "farmer_type")
    private String farmerType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FarmerTypeMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFarmerTypeCode() {
        return this.farmerTypeCode;
    }

    public FarmerTypeMaster farmerTypeCode(Integer farmerTypeCode) {
        this.setFarmerTypeCode(farmerTypeCode);
        return this;
    }

    public void setFarmerTypeCode(Integer farmerTypeCode) {
        this.farmerTypeCode = farmerTypeCode;
    }

    public String getFarmerType() {
        return this.farmerType;
    }

    public FarmerTypeMaster farmerType(String farmerType) {
        this.setFarmerType(farmerType);
        return this;
    }

    public void setFarmerType(String farmerType) {
        this.farmerType = farmerType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FarmerTypeMaster)) {
            return false;
        }
        return id != null && id.equals(((FarmerTypeMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FarmerTypeMaster{" +
            "id=" + getId() +
            ", farmerTypeCode=" + getFarmerTypeCode() +
            ", farmerType='" + getFarmerType() + "'" +
            "}";
    }
}
