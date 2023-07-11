package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A CastCategoryMaster.
 */
@Entity
@Table(name = "cast_category_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CastCategoryMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cast_category_code")
    private Integer castCategoryCode;

    @Column(name = "cast_category_name")
    private String castCategoryName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CastCategoryMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCastCategoryCode() {
        return this.castCategoryCode;
    }

    public CastCategoryMaster castCategoryCode(Integer castCategoryCode) {
        this.setCastCategoryCode(castCategoryCode);
        return this;
    }

    public void setCastCategoryCode(Integer castCategoryCode) {
        this.castCategoryCode = castCategoryCode;
    }

    public String getCastCategoryName() {
        return this.castCategoryName;
    }

    public CastCategoryMaster castCategoryName(String castCategoryName) {
        this.setCastCategoryName(castCategoryName);
        return this;
    }

    public void setCastCategoryName(String castCategoryName) {
        this.castCategoryName = castCategoryName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CastCategoryMaster)) {
            return false;
        }
        return id != null && id.equals(((CastCategoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CastCategoryMaster{" +
            "id=" + getId() +
            ", castCategoryCode=" + getCastCategoryCode() +
            ", castCategoryName='" + getCastCategoryName() + "'" +
            "}";
    }
}
