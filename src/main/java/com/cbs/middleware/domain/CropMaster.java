package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A CropMaster.
 */
@Entity
@Table(name = "crop_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CropMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crop_code")
    private String cropCode;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "category_code")
    private String categoryCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CropMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropCode() {
        return this.cropCode;
    }

    public CropMaster cropCode(String cropCode) {
        this.setCropCode(cropCode);
        return this;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getCropName() {
        return this.cropName;
    }

    public CropMaster cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCategoryCode() {
        return this.categoryCode;
    }

    public CropMaster categoryCode(String categoryCode) {
        this.setCategoryCode(categoryCode);
        return this;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CropMaster)) {
            return false;
        }
        return id != null && id.equals(((CropMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CropMaster{" +
            "id=" + getId() +
            ", cropCode='" + getCropCode() + "'" +
            ", cropName='" + getCropName() + "'" +
            ", categoryCode='" + getCategoryCode() + "'" +
            "}";
    }
}
