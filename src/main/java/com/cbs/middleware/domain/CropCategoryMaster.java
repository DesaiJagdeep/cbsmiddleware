package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A CropCategoryMaster.
 */
@Entity
@Table(name = "crop_category_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CropCategoryMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "category_code_mr")
    private String categoryCodeMr;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_name_mr")
    private String categoryNameMr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CropCategoryMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryCode() {
        return this.categoryCode;
    }

    public CropCategoryMaster categoryCode(String categoryCode) {
        this.setCategoryCode(categoryCode);
        return this;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCodeMr() {
        return this.categoryCodeMr;
    }

    public CropCategoryMaster categoryCodeMr(String categoryCodeMr) {
        this.setCategoryCodeMr(categoryCodeMr);
        return this;
    }

    public void setCategoryCodeMr(String categoryCodeMr) {
        this.categoryCodeMr = categoryCodeMr;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public CropCategoryMaster categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNameMr() {
        return this.categoryNameMr;
    }

    public CropCategoryMaster categoryNameMr(String categoryNameMr) {
        this.setCategoryNameMr(categoryNameMr);
        return this;
    }

    public void setCategoryNameMr(String categoryNameMr) {
        this.categoryNameMr = categoryNameMr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CropCategoryMaster)) {
            return false;
        }
        return id != null && id.equals(((CropCategoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CropCategoryMaster{" +
            "id=" + getId() +
            ", categoryCode='" + getCategoryCode() + "'" +
            ", categoryCodeMr='" + getCategoryCodeMr() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", categoryNameMr='" + getCategoryNameMr() + "'" +
            "}";
    }
}
