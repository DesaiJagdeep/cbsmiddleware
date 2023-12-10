package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A VillageMaster.
 */
@Entity
@Table(name = "village_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VillageMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "village_name", nullable = false)
    private String villageName;

    @Column(name = "village_name_mr")
    private String villageNameMr;

    @Column(name = "village_code")
    private Long villageCode;

    @Column(name = "village_code_mr")
    private String villageCodeMr;

    @JsonIgnoreProperties(value = { "villageMaster" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private TalukaMaster talukaMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VillageMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVillageName() {
        return this.villageName;
    }

    public VillageMaster villageName(String villageName) {
        this.setVillageName(villageName);
        return this;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getVillageNameMr() {
        return this.villageNameMr;
    }

    public VillageMaster villageNameMr(String villageNameMr) {
        this.setVillageNameMr(villageNameMr);
        return this;
    }

    public void setVillageNameMr(String villageNameMr) {
        this.villageNameMr = villageNameMr;
    }

    public Long getVillageCode() {
        return this.villageCode;
    }

    public VillageMaster villageCode(Long villageCode) {
        this.setVillageCode(villageCode);
        return this;
    }

    public void setVillageCode(Long villageCode) {
        this.villageCode = villageCode;
    }

    public String getVillageCodeMr() {
        return this.villageCodeMr;
    }

    public VillageMaster villageCodeMr(String villageCodeMr) {
        this.setVillageCodeMr(villageCodeMr);
        return this;
    }

    public void setVillageCodeMr(String villageCodeMr) {
        this.villageCodeMr = villageCodeMr;
    }

    public TalukaMaster getTalukaMaster() {
        return this.talukaMaster;
    }

    public void setTalukaMaster(TalukaMaster talukaMaster) {
        this.talukaMaster = talukaMaster;
    }

    public VillageMaster talukaMaster(TalukaMaster talukaMaster) {
        this.setTalukaMaster(talukaMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VillageMaster)) {
            return false;
        }
        return getId() != null && getId().equals(((VillageMaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VillageMaster{" +
            "id=" + getId() +
            ", villageName='" + getVillageName() + "'" +
            ", villageNameMr='" + getVillageNameMr() + "'" +
            ", villageCode=" + getVillageCode() +
            ", villageCodeMr='" + getVillageCodeMr() + "'" +
            "}";
    }
}
