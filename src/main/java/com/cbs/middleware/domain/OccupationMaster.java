package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A OccupationMaster.
 */
@Entity
@Table(name = "occupation_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OccupationMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "occupation_code")
    private Integer occupationCode = 0;

    @Column(name = "occupation_name")
    private String occupationName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OccupationMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOccupationCode() {
        return this.occupationCode;
    }

    public OccupationMaster occupationCode(Integer occupationCode) {
        this.setOccupationCode(occupationCode);
        return this;
    }

    public void setOccupationCode(Integer occupationCode) {
        this.occupationCode = occupationCode;
    }

    public String getOccupationName() {
        return this.occupationName;
    }

    public OccupationMaster occupationName(String occupationName) {
        this.setOccupationName(occupationName);
        return this;
    }

    public void setOccupationName(String occupationName) {
        this.occupationName = occupationName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OccupationMaster)) {
            return false;
        }
        return id != null && id.equals(((OccupationMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OccupationMaster{" +
            "id=" + getId() +
            ", occupationCode=" + getOccupationCode() +
            ", occupationName='" + getOccupationName() + "'" +
            "}";
    }
}
