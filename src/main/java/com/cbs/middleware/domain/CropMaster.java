package com.cbs.middleware.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A CropMaster.
 */
@Entity
@Table(name = "crop_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CropMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crop_code")
    private String cropCode;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "vatap_from_day")
    private Instant vatapFromDay;

    @Column(name = "vatap_to_month")
    private Instant vatapToMonth;

    @Column(name = "last_to_day")
    private Instant lastToDay;

    @Column(name = "last_to_month")
    private Instant lastToMonth;

    @Column(name = "due_day")
    private Instant dueDay;

    @Column(name = "due_month")
    private Instant dueMonth;

    @Column(name = "crop_period")
    private String cropPeriod;

    @Column(name = "sanction_amt")
    private Double sanctionAmt;

    @Column(name = "previous_amt")
    private Double previousAmt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CropMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropCode() {
        return this.cropCode;
    }

    public CropMaster cropCode(String cropCode) {
        this.setCropCode(cropCode);
        return this;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getCropName() {
        return this.cropName;
    }

    public CropMaster cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCategoryCode() {
        return this.categoryCode;
    }

    public CropMaster categoryCode(String categoryCode) {
        this.setCategoryCode(categoryCode);
        return this;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public CropMaster categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Instant getVatapFromDay() {
        return this.vatapFromDay;
    }

    public CropMaster vatapFromDay(Instant vatapFromDay) {
        this.setVatapFromDay(vatapFromDay);
        return this;
    }

    public void setVatapFromDay(Instant vatapFromDay) {
        this.vatapFromDay = vatapFromDay;
    }

    public Instant getVatapToMonth() {
        return this.vatapToMonth;
    }

    public CropMaster vatapToMonth(Instant vatapToMonth) {
        this.setVatapToMonth(vatapToMonth);
        return this;
    }

    public void setVatapToMonth(Instant vatapToMonth) {
        this.vatapToMonth = vatapToMonth;
    }

    public Instant getLastToDay() {
        return this.lastToDay;
    }

    public CropMaster lastToDay(Instant lastToDay) {
        this.setLastToDay(lastToDay);
        return this;
    }

    public void setLastToDay(Instant lastToDay) {
        this.lastToDay = lastToDay;
    }

    public Instant getLastToMonth() {
        return this.lastToMonth;
    }

    public CropMaster lastToMonth(Instant lastToMonth) {
        this.setLastToMonth(lastToMonth);
        return this;
    }

    public void setLastToMonth(Instant lastToMonth) {
        this.lastToMonth = lastToMonth;
    }

    public Instant getDueDay() {
        return this.dueDay;
    }

    public CropMaster dueDay(Instant dueDay) {
        this.setDueDay(dueDay);
        return this;
    }

    public void setDueDay(Instant dueDay) {
        this.dueDay = dueDay;
    }

    public Instant getDueMonth() {
        return this.dueMonth;
    }

    public CropMaster dueMonth(Instant dueMonth) {
        this.setDueMonth(dueMonth);
        return this;
    }

    public void setDueMonth(Instant dueMonth) {
        this.dueMonth = dueMonth;
    }

    public String getCropPeriod() {
        return this.cropPeriod;
    }

    public CropMaster cropPeriod(String cropPeriod) {
        this.setCropPeriod(cropPeriod);
        return this;
    }

    public void setCropPeriod(String cropPeriod) {
        this.cropPeriod = cropPeriod;
    }

    public Double getSanctionAmt() {
        return this.sanctionAmt;
    }

    public CropMaster sanctionAmt(Double sanctionAmt) {
        this.setSanctionAmt(sanctionAmt);
        return this;
    }

    public void setSanctionAmt(Double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public Double getPreviousAmt() {
        return this.previousAmt;
    }

    public CropMaster previousAmt(Double previousAmt) {
        this.setPreviousAmt(previousAmt);
        return this;
    }

    public void setPreviousAmt(Double previousAmt) {
        this.previousAmt = previousAmt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CropMaster)) {
            return false;
        }
        return getId() != null && getId().equals(((CropMaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CropMaster{" +
            "id=" + getId() +
            ", cropCode='" + getCropCode() + "'" +
            ", cropName='" + getCropName() + "'" +
            ", categoryCode='" + getCategoryCode() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", vatapFromDay='" + getVatapFromDay() + "'" +
            ", vatapToMonth='" + getVatapToMonth() + "'" +
            ", lastToDay='" + getLastToDay() + "'" +
            ", lastToMonth='" + getLastToMonth() + "'" +
            ", dueDay='" + getDueDay() + "'" +
            ", dueMonth='" + getDueMonth() + "'" +
            ", cropPeriod='" + getCropPeriod() + "'" +
            ", sanctionAmt=" + getSanctionAmt() +
            ", previousAmt=" + getPreviousAmt() +
            "}";
    }
}
