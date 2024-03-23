package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A KmMaganiCrop.
 */
@Entity
@Table(name = "km_magani_crop")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMaganiCrop extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "crop_balance")
    private Double cropBalance;

    @Column(name = "crop_due")
    private Double cropDue;

    @Column(name = "crop_due_date")
    private Instant cropDueDate;

    @Column(name = "crop_vasuli_patra_date")
    private Instant cropVasuliPatraDate;

    @Column(name = "km_manjuri")
    private Double kmManjuri;

    @Column(name = "km_area")
    private Double kmArea;

    @Column(name = "e_karar_number")
    private String eKararNumber;

    @Column(name = "e_karar_amount")
    private Double eKararAmount;

    @Column(name = "e_karar_area")
    private Double eKararArea;

    @Column(name = "magani_area")
    private Double maganiArea;

    @Column(name = "magani_amount")
    private Double maganiAmount;

    @Column(name = "magani_share")
    private Double maganiShare;

    @Column(name = "pacs_number")
    private String pacsNumber;

    @ManyToOne(fetch = FetchType.LAZY,cascade= CascadeType.DETACH)
    @JoinColumn(name = "km_magani_id")
    @JsonIgnoreProperties(value = {"kmNumber","memberNumber","memberName","pacsNumber","share","financialYear","kmDate","maganiDate","kmMaganiCrop","createdBy","createdDate","lastModifiedBy","lastModifiedDate"}, allowSetters = true)
    private KmMagani kmMagani;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KmMaganiCrop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropName() {
        return this.cropName;
    }

    public KmMaganiCrop cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Double getCropBalance() {
        return this.cropBalance;
    }

    public KmMaganiCrop cropBalance(Double cropBalance) {
        this.setCropBalance(cropBalance);
        return this;
    }

    public void setCropBalance(Double cropBalance) {
        this.cropBalance = cropBalance;
    }

    public Double getCropDue() {
        return this.cropDue;
    }

    public KmMaganiCrop cropDue(Double cropDue) {
        this.setCropDue(cropDue);
        return this;
    }

    public void setCropDue(Double cropDue) {
        this.cropDue = cropDue;
    }

    public Instant getCropDueDate() {
        return this.cropDueDate;
    }

    public KmMaganiCrop cropDueDate(Instant cropDueDate) {
        this.setCropDueDate(cropDueDate);
        return this;
    }

    public void setCropDueDate(Instant cropDueDate) {
        this.cropDueDate = cropDueDate;
    }

    public Instant getCropVasuliPatraDate() {
        return this.cropVasuliPatraDate;
    }

    public KmMaganiCrop cropVasuliPatraDate(Instant cropVasuliPatraDate) {
        this.setCropVasuliPatraDate(cropVasuliPatraDate);
        return this;
    }

    public void setCropVasuliPatraDate(Instant cropVasuliPatraDate) {
        this.cropVasuliPatraDate = cropVasuliPatraDate;
    }

    public Double getKmManjuri() {
        return this.kmManjuri;
    }

    public KmMaganiCrop kmManjuri(Double kmManjuri) {
        this.setKmManjuri(kmManjuri);
        return this;
    }

    public void setKmManjuri(Double kmManjuri) {
        this.kmManjuri = kmManjuri;
    }

    public Double getKmArea() {
        return this.kmArea;
    }

    public KmMaganiCrop kmArea(Double kmArea) {
        this.setKmArea(kmArea);
        return this;
    }

    public void setKmArea(Double kmArea) {
        this.kmArea = kmArea;
    }

    public String geteKararNumber() {
        return this.eKararNumber;
    }

    public KmMaganiCrop eKararNumber(String eKararNumber) {
        this.seteKararNumber(eKararNumber);
        return this;
    }

    public void seteKararNumber(String eKararNumber) {
        this.eKararNumber = eKararNumber;
    }

    public Double geteKararAmount() {
        return this.eKararAmount;
    }

    public KmMaganiCrop eKararAmount(Double eKararAmount) {
        this.seteKararAmount(eKararAmount);
        return this;
    }

    public void seteKararAmount(Double eKararAmount) {
        this.eKararAmount = eKararAmount;
    }

    public Double geteKararArea() {
        return this.eKararArea;
    }

    public KmMaganiCrop eKararArea(Double eKararArea) {
        this.seteKararArea(eKararArea);
        return this;
    }

    public void seteKararArea(Double eKararArea) {
        this.eKararArea = eKararArea;
    }

    public Double getMaganiArea() {
        return this.maganiArea;
    }

    public KmMaganiCrop maganiArea(Double maganiArea) {
        this.setMaganiArea(maganiArea);
        return this;
    }

    public void setMaganiArea(Double maganiArea) {
        this.maganiArea = maganiArea;
    }

    public Double getMaganiAmount() {
        return this.maganiAmount;
    }

    public KmMaganiCrop maganiAmount(Double maganiAmount) {
        this.setMaganiAmount(maganiAmount);
        return this;
    }

    public void setMaganiAmount(Double maganiAmount) {
        this.maganiAmount = maganiAmount;
    }

    public Double getMaganiShare() {
        return this.maganiShare;
    }

    public KmMaganiCrop maganiShare(Double maganiShare) {
        this.setMaganiShare(maganiShare);
        return this;
    }

    public void setMaganiShare(Double maganiShare) {
        this.maganiShare = maganiShare;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public KmMaganiCrop pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public KmMagani getKmMagani() {
        return this.kmMagani;
    }

    public void setKmMagani(KmMagani kmMagani) {
        this.kmMagani = kmMagani;
    }

    public KmMaganiCrop kmMagani(KmMagani kmMagani) {
        this.setKmMagani(kmMagani);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KmMaganiCrop)) {
            return false;
        }
        return id != null && id.equals(((KmMaganiCrop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMaganiCrop{" +
            "id=" + getId() +
            ", cropName='" + getCropName() + "'" +
            ", cropBalance=" + getCropBalance() +
            ", cropDue=" + getCropDue() +
            ", cropDueDate='" + getCropDueDate() + "'" +
            ", cropVasuliPatraDate='" + getCropVasuliPatraDate() + "'" +
            ", kmManjuri=" + getKmManjuri() +
            ", kmArea=" + getKmArea() +
            ", eKararNumber='" + geteKararNumber() + "'" +
            ", eKararAmount=" + geteKararAmount() +
            ", eKararArea=" + geteKararArea() +
            ", maganiArea=" + getMaganiArea() +
            ", maganiAmount=" + getMaganiAmount() +
            ", maganiShare=" + getMaganiShare() +
            ", pacsNumber='" + getPacsNumber() + "'" +
            "}";
    }
}
