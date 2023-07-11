package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A TalukaMaster.
 */
@Entity
@Table(name = "taluka_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TalukaMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "taluka_code")
    private String talukaCode;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "district_code")
    private String districtCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TalukaMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTalukaCode() {
        return this.talukaCode;
    }

    public TalukaMaster talukaCode(String talukaCode) {
        this.setTalukaCode(talukaCode);
        return this;
    }

    public void setTalukaCode(String talukaCode) {
        this.talukaCode = talukaCode;
    }

    public String getTalukaName() {
        return this.talukaName;
    }

    public TalukaMaster talukaName(String talukaName) {
        this.setTalukaName(talukaName);
        return this;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getDistrictCode() {
        return this.districtCode;
    }

    public TalukaMaster districtCode(String districtCode) {
        this.setDistrictCode(districtCode);
        return this;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TalukaMaster)) {
            return false;
        }
        return id != null && id.equals(((TalukaMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TalukaMaster{" +
            "id=" + getId() +
            ", talukaCode='" + getTalukaCode() + "'" +
            ", talukaName='" + getTalukaName() + "'" +
            ", districtCode='" + getDistrictCode() + "'" +
            "}";
    }
}
