package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A KamalCrop.
 */
@Entity
@Table(name = "kamal_crop")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalCrop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pacs_number")
    private String pacsNumber;

    @Column(name = "financial_year")
    private String financialYear;

    @ManyToOne
    private KamalSociety kamalSociety;

    @ManyToOne
    private FarmerTypeMaster farmerTypeMaster;

    @ManyToOne
    private SeasonMaster seasonMaster;

    @ManyToOne
    private CropMaster cropMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KamalCrop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public KamalCrop pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public KamalCrop financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public KamalSociety getKamalSociety() {
        return this.kamalSociety;
    }

    public void setKamalSociety(KamalSociety kamalSociety) {
        this.kamalSociety = kamalSociety;
    }

    public KamalCrop kamalSociety(KamalSociety kamalSociety) {
        this.setKamalSociety(kamalSociety);
        return this;
    }

    public FarmerTypeMaster getFarmerTypeMaster() {
        return this.farmerTypeMaster;
    }

    public void setFarmerTypeMaster(FarmerTypeMaster farmerTypeMaster) {
        this.farmerTypeMaster = farmerTypeMaster;
    }

    public KamalCrop farmerTypeMaster(FarmerTypeMaster farmerTypeMaster) {
        this.setFarmerTypeMaster(farmerTypeMaster);
        return this;
    }

    public SeasonMaster getSeasonMaster() {
        return this.seasonMaster;
    }

    public void setSeasonMaster(SeasonMaster seasonMaster) {
        this.seasonMaster = seasonMaster;
    }

    public KamalCrop seasonMaster(SeasonMaster seasonMaster) {
        this.setSeasonMaster(seasonMaster);
        return this;
    }

    public CropMaster getCropMaster() {
        return this.cropMaster;
    }

    public void setCropMaster(CropMaster cropMaster) {
        this.cropMaster = cropMaster;
    }

    public KamalCrop cropMaster(CropMaster cropMaster) {
        this.setCropMaster(cropMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KamalCrop)) {
            return false;
        }
        return id != null && id.equals(((KamalCrop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalCrop{" +
            "id=" + getId() +
            ", pacsNumber='" + getPacsNumber() + "'" +
            ", financialYear='" + getFinancialYear() + "'" +
            "}";
    }
}
