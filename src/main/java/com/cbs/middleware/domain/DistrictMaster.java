package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A DistrictMaster.
 */
@Entity
@Table(name = "district_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "state_code")
    private String stateCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DistrictMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictCode() {
        return this.districtCode;
    }

    public DistrictMaster districtCode(String districtCode) {
        this.setDistrictCode(districtCode);
        return this;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return this.districtName;
    }

    public DistrictMaster districtName(String districtName) {
        this.setDistrictName(districtName);
        return this;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public DistrictMaster stateCode(String stateCode) {
        this.setStateCode(stateCode);
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictMaster)) {
            return false;
        }
        return id != null && id.equals(((DistrictMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictMaster{" +
            "id=" + getId() +
            ", districtCode='" + getDistrictCode() + "'" +
            ", districtName='" + getDistrictName() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            "}";
    }
}
