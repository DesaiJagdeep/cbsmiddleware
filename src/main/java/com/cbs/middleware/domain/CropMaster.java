package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A CropMaster.
 */
@Entity
@Table(name = "crop_master")
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class CropMaster extends AbstractAuditingEntity<Long> implements Serializable {

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

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "to_date")
    private Instant toDate;

    @Column(name = "due_date")
    private Instant dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "createdBy","createdDate","lastModifiedBy","lastModifiedDate" }, allowSetters = true)
    private SeasonMaster seasonMaster;
    // jhipster-needle-entity-add-field - JHipster will add fields here


    public Instant getFromDate() {
        return fromDate;
    }

    public CropMaster fromDate(Instant fromDate){
        this.setFromDate(fromDate);
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }
    public CropMaster toDate(Instant toDate){
        this.setToDate(toDate);
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Instant getDueDate() {
        return dueDate;
    }
    public CropMaster dueDate(Instant dueDate){
        this.setDueDate(dueDate);
        return this;
    }
    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public SeasonMaster getSeasonMaster() {
        return seasonMaster;
    }
    public CropMaster seasonMaster(SeasonMaster seasonMaster) {
        this.setSeasonMaster(seasonMaster);
        return this;
    }
    public void setSeasonMaster(SeasonMaster seasonMaster) {
        this.seasonMaster = seasonMaster;
    }

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
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CropMaster)) {
            return false;
        }
        return id != null && id.equals(((CropMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "CropMaster [id=" +
            id +
            ", cropCode=" +
            cropCode +
            ", cropName=" +
            cropName +
            ", categoryCode=" +
            categoryCode +
            ", categoryName=" +
            categoryName +
            "]"
        );
    }
    // prettier-ignore

}
