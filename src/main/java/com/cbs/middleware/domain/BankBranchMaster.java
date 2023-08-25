package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A BankBranchMaster.
 */
@Entity
@Table(name = "bank_branch_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankBranchMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_address")
    private String branchAddress;

    @ManyToOne
    private BankMaster bankMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankBranchMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public BankBranchMaster branchCode(String branchCode) {
        this.setBranchCode(branchCode);
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public BankBranchMaster branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return this.branchAddress;
    }

    public BankBranchMaster branchAddress(String branchAddress) {
        this.setBranchAddress(branchAddress);
        return this;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public BankMaster getBankMaster() {
        return this.bankMaster;
    }

    public void setBankMaster(BankMaster bankMaster) {
        this.bankMaster = bankMaster;
    }

    public BankBranchMaster bankMaster(BankMaster bankMaster) {
        this.setBankMaster(bankMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankBranchMaster)) {
            return false;
        }
        return id != null && id.equals(((BankBranchMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankBranchMaster{" +
            "id=" + getId() +
            ", branchCode='" + getBranchCode() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", branchAddress='" + getBranchAddress() + "'" +
            "}";
    }
}
