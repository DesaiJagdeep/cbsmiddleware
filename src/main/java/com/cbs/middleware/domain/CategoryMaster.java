package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A CategoryMaster.
 */
@Entity
@Table(name = "category_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoryMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cast_name")
    private String castName;

    @Column(name = "cast_code")
    private String castCode;

    @Column(name = "cast_category_code")
    private String castCategoryCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CategoryMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCastName() {
        return this.castName;
    }

    public CategoryMaster castName(String castName) {
        this.setCastName(castName);
        return this;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getCastCode() {
        return this.castCode;
    }

    public CategoryMaster castCode(String castCode) {
        this.setCastCode(castCode);
        return this;
    }

    public void setCastCode(String castCode) {
        this.castCode = castCode;
    }

    public String getCastCategoryCode() {
        return this.castCategoryCode;
    }

    public CategoryMaster castCategoryCode(String castCategoryCode) {
        this.setCastCategoryCode(castCategoryCode);
        return this;
    }

    public void setCastCategoryCode(String castCategoryCode) {
        this.castCategoryCode = castCategoryCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryMaster)) {
            return false;
        }
        return id != null && id.equals(((CategoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryMaster{" +
            "id=" + getId() +
            ", castName='" + getCastName() + "'" +
            ", castCode='" + getCastCode() + "'" +
            ", castCategoryCode='" + getCastCategoryCode() + "'" +
            "}";
    }
}
