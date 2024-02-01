package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A KamalSociety.
 */
@Entity
@Table(name = "kamal_society")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalSociety implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "financial_year", nullable = false)
    private String financialYear;

    @Column(name = "km_date")
    private Instant kmDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KamalSociety id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public KamalSociety financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Instant getKmDate() {
        return this.kmDate;
    }

    public KamalSociety kmDate(Instant kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public void setKmDate(Instant kmDate) {
        this.kmDate = kmDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KamalSociety)) {
            return false;
        }
        return id != null && id.equals(((KamalSociety) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalSociety{" +
            "id=" + getId() +
            ", financialYear='" + getFinancialYear() + "'" +
            ", kmDate='" + getKmDate() + "'" +
            "}";
    }
}
