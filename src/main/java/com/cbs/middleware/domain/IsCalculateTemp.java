package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A IsCalculateTemp.
 */
@Entity
@Table(name = "is_calculate_temp")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IsCalculateTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_no")
    private Integer serialNo;

    @Column(name = "financial_year")
    private String financialYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IsCalculateTemp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerialNo() {
        return this.serialNo;
    }

    public IsCalculateTemp serialNo(Integer serialNo) {
        this.setSerialNo(serialNo);
        return this;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public IsCalculateTemp financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IsCalculateTemp)) {
            return false;
        }
        return id != null && id.equals(((IsCalculateTemp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IsCalculateTemp{" +
            "id=" + getId() +
            ", serialNo=" + getSerialNo() +
            ", financialYear='" + getFinancialYear() + "'" +
            "}";
    }
}
