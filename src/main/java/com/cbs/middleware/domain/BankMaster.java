package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A BankMaster.
 */
@Entity
@Table(name = "bank_master")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_name_mr")
    private String bankNameMr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public BankMaster bankCode(String bankCode) {
        this.setBankCode(bankCode);
        return this;
    }

    public String getBankNameMr() {
        return bankNameMr;
    }

    public void setBankNameMr(String bankNameMr) {
        this.bankNameMr = bankNameMr;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return this.bankName;
    }

    public BankMaster bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankMaster)) {
            return false;
        }
        return id != null && id.equals(((BankMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankMaster{" +
            "id=" + getId() +
            ", bankCode='" + getBankCode() + "'" +
            ", bankName='" + getBankName() + "'" +
            "}";
    }
}
