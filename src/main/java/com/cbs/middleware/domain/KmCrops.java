package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;

/**
 * A KmCrops.
 */
@Entity
@Table(name = "km_crops")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmCrops extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "crop_name_mr")
    private String cropNameMr;

    @Column(name = "hector")
    private Double hector;

    @Column(name = "hector_mr")
    private String hectorMr;

    @Column(name = "are")
    private Double are;

    @Column(name = "are_mr")
    private String areMr;

    @Column(name = "prvious_amt")
    private Double prviousAmt;

    @Column(name = "previous_amt_mr")
    private String previousAmtMr;

    @Column(name = "demand")
    private Double demand;

    @Column(name = "demand_mr")
    private String demandMr;

    @Column(name = "society")
    private String society;

    @Column(name = "society_mr")
    private String societyMr;

    @Column(name = "bank_amt")
    private Double bankAmt;

    @Column(name = "bank_amt_mr")
    private String bankAmtMr;

    @Column(name = "no_of_tree")
    private Double noOfTree;

    @Column(name = "no_of_tree_mr")
    private String noOfTreeMr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "kmMaster", "kmLoans", "kmCrops" }, allowSetters = true)
    private KmDetails kmDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KmCrops id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropName() {
        return this.cropName;
    }

    public KmCrops cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropNameMr() {
        return this.cropNameMr;
    }

    public KmCrops cropNameMr(String cropNameMr) {
        this.setCropNameMr(cropNameMr);
        return this;
    }

    public void setCropNameMr(String cropNameMr) {
        this.cropNameMr = cropNameMr;
    }

    public Double getHector() {
        return this.hector;
    }

    public KmCrops hector(Double hector) {
        this.setHector(hector);
        return this;
    }

    public void setHector(Double hector) {
        this.hector = hector;
    }

    public String getHectorMr() {
        return this.hectorMr;
    }

    public KmCrops hectorMr(String hectorMr) {
        this.setHectorMr(hectorMr);
        return this;
    }

    public void setHectorMr(String hectorMr) {
        this.hectorMr = hectorMr;
    }

    public Double getAre() {
        return this.are;
    }

    public KmCrops are(Double are) {
        this.setAre(are);
        return this;
    }

    public void setAre(Double are) {
        this.are = are;
    }

    public String getAreMr() {
        return this.areMr;
    }

    public KmCrops areMr(String areMr) {
        this.setAreMr(areMr);
        return this;
    }

    public void setAreMr(String areMr) {
        this.areMr = areMr;
    }

    public Double getPrviousAmt() {
        return this.prviousAmt;
    }

    public KmCrops prviousAmt(Double prviousAmt) {
        this.setPrviousAmt(prviousAmt);
        return this;
    }

    public void setPrviousAmt(Double prviousAmt) {
        this.prviousAmt = prviousAmt;
    }

    public String getPreviousAmtMr() {
        return this.previousAmtMr;
    }

    public KmCrops previousAmtMr(String previousAmtMr) {
        this.setPreviousAmtMr(previousAmtMr);
        return this;
    }

    public void setPreviousAmtMr(String previousAmtMr) {
        this.previousAmtMr = previousAmtMr;
    }

    public Double getDemand() {
        return this.demand;
    }

    public KmCrops demand(Double demand) {
        this.setDemand(demand);
        return this;
    }

    public void setDemand(Double demand) {
        this.demand = demand;
    }

    public String getDemandMr() {
        return this.demandMr;
    }

    public KmCrops demandMr(String demandMr) {
        this.setDemandMr(demandMr);
        return this;
    }

    public void setDemandMr(String demandMr) {
        this.demandMr = demandMr;
    }

    public String getSociety() {
        return this.society;
    }

    public KmCrops society(String society) {
        this.setSociety(society);
        return this;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getSocietyMr() {
        return this.societyMr;
    }

    public KmCrops societyMr(String societyMr) {
        this.setSocietyMr(societyMr);
        return this;
    }

    public void setSocietyMr(String societyMr) {
        this.societyMr = societyMr;
    }

    public Double getBankAmt() {
        return this.bankAmt;
    }

    public KmCrops bankAmt(Double bankAmt) {
        this.setBankAmt(bankAmt);
        return this;
    }

    public void setBankAmt(Double bankAmt) {
        this.bankAmt = bankAmt;
    }

    public String getBankAmtMr() {
        return this.bankAmtMr;
    }

    public KmCrops bankAmtMr(String bankAmtMr) {
        this.setBankAmtMr(bankAmtMr);
        return this;
    }

    public void setBankAmtMr(String bankAmtMr) {
        this.bankAmtMr = bankAmtMr;
    }

    public Double getNoOfTree() {
        return this.noOfTree;
    }

    public KmCrops noOfTree(Double noOfTree) {
        this.setNoOfTree(noOfTree);
        return this;
    }

    public void setNoOfTree(Double noOfTree) {
        this.noOfTree = noOfTree;
    }

    public String getNoOfTreeMr() {
        return this.noOfTreeMr;
    }

    public KmCrops noOfTreeMr(String noOfTreeMr) {
        this.setNoOfTreeMr(noOfTreeMr);
        return this;
    }

    public void setNoOfTreeMr(String noOfTreeMr) {
        this.noOfTreeMr = noOfTreeMr;
    }

    public KmDetails getKmDetails() {
        return this.kmDetails;
    }

    public void setKmDetails(KmDetails kmDetails) {
        this.kmDetails = kmDetails;
    }

    public KmCrops kmDetails(KmDetails kmDetails) {
        this.setKmDetails(kmDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KmCrops)) {
            return false;
        }
        return getId() != null && getId().equals(((KmCrops) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmCrops{" +
            "id=" + getId() +
            ", cropName='" + getCropName() + "'" +
            ", cropNameMr='" + getCropNameMr() + "'" +
            ", hector=" + getHector() +
            ", hectorMr='" + getHectorMr() + "'" +
            ", are=" + getAre() +
            ", areMr='" + getAreMr() + "'" +
            ", prviousAmt=" + getPrviousAmt() +
            ", previousAmtMr='" + getPreviousAmtMr() + "'" +
            ", demand=" + getDemand() +
            ", demandMr='" + getDemandMr() + "'" +
            ", society='" + getSociety() + "'" +
            ", societyMr='" + getSocietyMr() + "'" +
            ", bankAmt=" + getBankAmt() +
            ", bankAmtMr='" + getBankAmtMr() + "'" +
            ", noOfTree=" + getNoOfTree() +
            ", noOfTreeMr='" + getNoOfTreeMr() + "'" +
            "}";
    }
}
