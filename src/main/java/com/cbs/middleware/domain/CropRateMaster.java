package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CropRateMaster.
 */
@Entity
@Table(name = "crop_rate_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CropRateMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "financial_year", nullable = false)
    private String financialYear;

    @NotNull
    @Column(name = "current_amount", nullable = false)
    private Double currentAmount;

    @Column(name = "current_amount_mr")
    private String currentAmountMr;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "active_flag")
    private Boolean activeFlag;

    @ManyToOne
    private CropMaster cropMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CropRateMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public CropRateMaster financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Double getCurrentAmount() {
        return this.currentAmount;
    }

    public CropRateMaster currentAmount(Double currentAmount) {
        this.setCurrentAmount(currentAmount);
        return this;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getCurrentAmountMr() {
        return this.currentAmountMr;
    }

    public CropRateMaster currentAmountMr(String currentAmountMr) {
        this.setCurrentAmountMr(currentAmountMr);
        return this;
    }

    public void setCurrentAmountMr(String currentAmountMr) {
        this.currentAmountMr = currentAmountMr;
    }

    public Double getPercentage() {
        return this.percentage;
    }

    public CropRateMaster percentage(Double percentage) {
        this.setPercentage(percentage);
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Boolean getActiveFlag() {
        return this.activeFlag;
    }

    public CropRateMaster activeFlag(Boolean activeFlag) {
        this.setActiveFlag(activeFlag);
        return this;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public CropMaster getCropMaster() {
        return this.cropMaster;
    }

    public void setCropMaster(CropMaster cropMaster) {
        this.cropMaster = cropMaster;
    }

    public CropRateMaster cropMaster(CropMaster cropMaster) {
        this.setCropMaster(cropMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CropRateMaster)) {
            return false;
        }
        return id != null && id.equals(((CropRateMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CropRateMaster{" +
            "id=" + getId() +
            ", financialYear='" + getFinancialYear() + "'" +
            ", currentAmount=" + getCurrentAmount() +
            ", currentAmountMr='" + getCurrentAmountMr() + "'" +
            ", percentage=" + getPercentage() +
            ", activeFlag='" + getActiveFlag() + "'" +
            "}";
    }
}
