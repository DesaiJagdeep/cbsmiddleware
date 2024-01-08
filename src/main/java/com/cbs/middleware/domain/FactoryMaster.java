package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A FactoryMaster.
 */
@Entity
@Table(name = "factory_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactoryMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "factory_name")
    private String factoryName;

    @Column(name = "factory_name_mr")
    private String factoryNameMr;

    @Column(name = "factory_code")
    private Long factoryCode;

    @Column(name = "factory_code_mr")
    private String factoryCodeMr;

    @Column(name = "factory_address")
    private String factoryAddress;

    @Column(name = "factory_address_mr")
    private String factoryAddressMr;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FactoryMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFactoryName() {
        return this.factoryName;
    }

    public FactoryMaster factoryName(String factoryName) {
        this.setFactoryName(factoryName);
        return this;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryNameMr() {
        return this.factoryNameMr;
    }

    public FactoryMaster factoryNameMr(String factoryNameMr) {
        this.setFactoryNameMr(factoryNameMr);
        return this;
    }

    public void setFactoryNameMr(String factoryNameMr) {
        this.factoryNameMr = factoryNameMr;
    }

    public Long getFactoryCode() {
        return this.factoryCode;
    }

    public FactoryMaster factoryCode(Long factoryCode) {
        this.setFactoryCode(factoryCode);
        return this;
    }

    public void setFactoryCode(Long factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getFactoryCodeMr() {
        return this.factoryCodeMr;
    }

    public FactoryMaster factoryCodeMr(String factoryCodeMr) {
        this.setFactoryCodeMr(factoryCodeMr);
        return this;
    }

    public void setFactoryCodeMr(String factoryCodeMr) {
        this.factoryCodeMr = factoryCodeMr;
    }

    public String getFactoryAddress() {
        return this.factoryAddress;
    }

    public FactoryMaster factoryAddress(String factoryAddress) {
        this.setFactoryAddress(factoryAddress);
        return this;
    }

    public void setFactoryAddress(String factoryAddress) {
        this.factoryAddress = factoryAddress;
    }

    public String getFactoryAddressMr() {
        return this.factoryAddressMr;
    }

    public FactoryMaster factoryAddressMr(String factoryAddressMr) {
        this.setFactoryAddressMr(factoryAddressMr);
        return this;
    }

    public void setFactoryAddressMr(String factoryAddressMr) {
        this.factoryAddressMr = factoryAddressMr;
    }

    public String getDescription() {
        return this.description;
    }

    public FactoryMaster description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public FactoryMaster status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactoryMaster)) {
            return false;
        }
        return id != null && id.equals(((FactoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactoryMaster{" +
            "id=" + getId() +
            ", factoryName='" + getFactoryName() + "'" +
            ", factoryNameMr='" + getFactoryNameMr() + "'" +
            ", factoryCode=" + getFactoryCode() +
            ", factoryCodeMr='" + getFactoryCodeMr() + "'" +
            ", factoryAddress='" + getFactoryAddress() + "'" +
            ", factoryAddressMr='" + getFactoryAddressMr() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
