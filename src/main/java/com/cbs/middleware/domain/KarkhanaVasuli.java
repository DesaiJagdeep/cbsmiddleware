package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A KarkhanaVasuli.
 */
@Entity
@Table(name = "karkhana_vasuli")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KarkhanaVasuli implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "khata_number")
    private String khataNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "society_name")
    private String societyName;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "defaulter_name")
    private String defaulterName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KarkhanaVasuli id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKhataNumber() {
        return this.khataNumber;
    }

    public KarkhanaVasuli khataNumber(String khataNumber) {
        this.setKhataNumber(khataNumber);
        return this;
    }

    public void setKhataNumber(String khataNumber) {
        this.khataNumber = khataNumber;
    }

    public String getName() {
        return this.name;
    }

    public KarkhanaVasuli name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocietyName() {
        return this.societyName;
    }

    public KarkhanaVasuli societyName(String societyName) {
        this.setSocietyName(societyName);
        return this;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getTalukaName() {
        return this.talukaName;
    }

    public KarkhanaVasuli talukaName(String talukaName) {
        this.setTalukaName(talukaName);
        return this;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public KarkhanaVasuli branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDefaulterName() {
        return this.defaulterName;
    }

    public KarkhanaVasuli defaulterName(String defaulterName) {
        this.setDefaulterName(defaulterName);
        return this;
    }

    public void setDefaulterName(String defaulterName) {
        this.defaulterName = defaulterName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KarkhanaVasuli)) {
            return false;
        }
        return id != null && id.equals(((KarkhanaVasuli) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KarkhanaVasuli{" +
            "id=" + getId() +
            ", khataNumber='" + getKhataNumber() + "'" +
            ", name='" + getName() + "'" +
            ", societyName='" + getSocietyName() + "'" +
            ", talukaName='" + getTalukaName() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", defaulterName='" + getDefaulterName() + "'" +
            "}";
    }
}
