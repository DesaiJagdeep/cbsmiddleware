package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A PacsMaster.
 */
@Entity
@Table(name = "pacs_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PacsMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pacs_name")
    private String pacsName;

    @Column(name = "pacs_number")
    private String pacsNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PacsMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacsName() {
        return this.pacsName;
    }

    public PacsMaster pacsName(String pacsName) {
        this.setPacsName(pacsName);
        return this;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public PacsMaster pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PacsMaster)) {
            return false;
        }
        return id != null && id.equals(((PacsMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PacsMaster{" +
            "id=" + getId() +
            ", pacsName='" + getPacsName() + "'" +
            ", pacsNumber='" + getPacsNumber() + "'" +
            "}";
    }
}