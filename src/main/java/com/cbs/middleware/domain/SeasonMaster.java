package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A SeasonMaster.
 */
@Entity
@Table(name = "season_master")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class SeasonMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "season_code")
    private Integer seasonCode;

    @Column(name = "season_name")
    private String seasonName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SeasonMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeasonCode() {
        return this.seasonCode;
    }

    public SeasonMaster seasonCode(Integer seasonCode) {
        this.setSeasonCode(seasonCode);
        return this;
    }

    public void setSeasonCode(Integer seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSeasonName() {
        return this.seasonName;
    }

    public SeasonMaster seasonName(String seasonName) {
        this.setSeasonName(seasonName);
        return this;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeasonMaster)) {
            return false;
        }
        return id != null && id.equals(((SeasonMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeasonMaster{" +
            "id=" + getId() +
            ", seasonCode=" + getSeasonCode() +
            ", seasonName='" + getSeasonName() + "'" +
            "}";
    }
}
