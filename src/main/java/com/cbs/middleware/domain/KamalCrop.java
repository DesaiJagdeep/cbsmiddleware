package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

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

    @NotNull
    @Column(name = "pacs_number", nullable = false)
    private Integer pacsNumber;

    @NotNull
    @Column(name = "mem_no", nullable = false)
    private Integer memNo;

    @NotNull
    @Column(name = "mem_hector", nullable = false)
    private Double memHector;

    @Column(name = "mem_no_mr")
    private String memNoMr;

    @Column(name = "mem_hector_mr")
    private String memHectorMr;

    @Column(name = "mem_aar")
    private Double memAar;

    @Column(name = "mem_aar_mr")
    private String memAarMr;

    @ManyToOne
    private FarmerTypeMaster farmerTypeMaster;

    @ManyToOne
    private SeasonMaster seasonMaster;

    @ManyToOne
    private CropMaster cropMaster;

    @ManyToOne
    @JsonIgnoreProperties(value = { "kamalCrops" }, allowSetters = true)
    private KamalSociety kamalSociety;

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

    public Integer getPacsNumber() {
        return this.pacsNumber;
    }

    public KamalCrop pacsNumber(Integer pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(Integer pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public Integer getMemNo() {
        return this.memNo;
    }

    public KamalCrop memNo(Integer memNo) {
        this.setMemNo(memNo);
        return this;
    }

    public void setMemNo(Integer memNo) {
        this.memNo = memNo;
    }

    public Double getMemHector() {
        return this.memHector;
    }

    public KamalCrop memHector(Double memHector) {
        this.setMemHector(memHector);
        return this;
    }

    public void setMemHector(Double memHector) {
        this.memHector = memHector;
    }

    public String getMemNoMr() {
        return this.memNoMr;
    }

    public KamalCrop memNoMr(String memNoMr) {
        this.setMemNoMr(memNoMr);
        return this;
    }

    public void setMemNoMr(String memNoMr) {
        this.memNoMr = memNoMr;
    }

    public String getMemHectorMr() {
        return this.memHectorMr;
    }

    public KamalCrop memHectorMr(String memHectorMr) {
        this.setMemHectorMr(memHectorMr);
        return this;
    }

    public void setMemHectorMr(String memHectorMr) {
        this.memHectorMr = memHectorMr;
    }

    public Double getMemAar() {
        return this.memAar;
    }

    public KamalCrop memAar(Double memAar) {
        this.setMemAar(memAar);
        return this;
    }

    public void setMemAar(Double memAar) {
        this.memAar = memAar;
    }

    public String getMemAarMr() {
        return this.memAarMr;
    }

    public KamalCrop memAarMr(String memAarMr) {
        this.setMemAarMr(memAarMr);
        return this;
    }

    public void setMemAarMr(String memAarMr) {
        this.memAarMr = memAarMr;
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
            ", pacsNumber=" + getPacsNumber() +
            ", memNo=" + getMemNo() +
            ", memHector=" + getMemHector() +
            ", memNoMr='" + getMemNoMr() + "'" +
            ", memHectorMr='" + getMemHectorMr() + "'" +
            ", memAar=" + getMemAar() +
            ", memAarMr='" + getMemAarMr() + "'" +
            "}";
    }
}
