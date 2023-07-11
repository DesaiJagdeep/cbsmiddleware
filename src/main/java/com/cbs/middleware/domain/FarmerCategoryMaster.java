package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A FarmerCategoryMaster.
 */
@Entity
@Table(name = "farmer_category_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FarmerCategoryMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "farmer_category_code")
    private Integer farmerCategoryCode;

    @Column(name = "farmer_category")
    private String farmerCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FarmerCategoryMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFarmerCategoryCode() {
        return this.farmerCategoryCode;
    }

    public FarmerCategoryMaster farmerCategoryCode(Integer farmerCategoryCode) {
        this.setFarmerCategoryCode(farmerCategoryCode);
        return this;
    }

    public void setFarmerCategoryCode(Integer farmerCategoryCode) {
        this.farmerCategoryCode = farmerCategoryCode;
    }

    public String getFarmerCategory() {
        return this.farmerCategory;
    }

    public FarmerCategoryMaster farmerCategory(String farmerCategory) {
        this.setFarmerCategory(farmerCategory);
        return this;
    }

    public void setFarmerCategory(String farmerCategory) {
        this.farmerCategory = farmerCategory;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FarmerCategoryMaster)) {
            return false;
        }
        return id != null && id.equals(((FarmerCategoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FarmerCategoryMaster{" +
            "id=" + getId() +
            ", farmerCategoryCode=" + getFarmerCategoryCode() +
            ", farmerCategory='" + getFarmerCategory() + "'" +
            "}";
    }
}
