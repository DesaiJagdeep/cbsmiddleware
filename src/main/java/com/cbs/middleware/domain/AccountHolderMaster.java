package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A AccountHolderMaster.
 */
@Entity
@Table(name = "account_holder_master")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountHolderMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_holder_code")
    private Integer accountHolderCode;

    @Column(name = "account_holder")
    private String accountHolder;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AccountHolderMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccountHolderCode() {
        return this.accountHolderCode;
    }

    public AccountHolderMaster accountHolderCode(Integer accountHolderCode) {
        this.setAccountHolderCode(accountHolderCode);
        return this;
    }

    public void setAccountHolderCode(Integer accountHolderCode) {
        this.accountHolderCode = accountHolderCode;
    }

    public String getAccountHolder() {
        return this.accountHolder;
    }

    public AccountHolderMaster accountHolder(String accountHolder) {
        this.setAccountHolder(accountHolder);
        return this;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountHolderMaster)) {
            return false;
        }
        return id != null && id.equals(((AccountHolderMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountHolderMaster{" +
            "id=" + getId() +
            ", accountHolderCode=" + getAccountHolderCode() +
            ", accountHolder='" + getAccountHolder() + "'" +
            "}";
    }
}
