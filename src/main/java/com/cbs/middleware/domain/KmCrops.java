package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A KmCrops.
 */
@Entity
@Table(name = "km_crops")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmCrops implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "hector", nullable = false)
    private Double hector;

    @Column(name = "hector_mr")
    private String hectorMr;

    @NotNull
    @Column(name = "are", nullable = false)
    private Double are;

    @Column(name = "are_mr")
    private String areMr;

    @NotNull
    @Column(name = "no_of_tree", nullable = false)
    private Double noOfTree;

    @Column(name = "no_of_tree_mr")
    private String noOfTreeMr;

    @NotNull
    @Column(name = "demand", nullable = false)
    private Double demand;

    @Column(name = "demand_mr")
    private String demandMr;

    @NotNull
    @Size(max = 255)
    @Column(name = "society", length = 255, nullable = false)
    private String society;

    @Column(name = "society_mr")
    private String societyMr;

    @NotNull
    @Column(name = "bank_amt", nullable = false)
    private Double bankAmt;

    @Column(name = "bank_amt_mr")
    private String bankAmtMr;

    @NotNull
    @Size(max = 255)
    @Column(name = "vibhagi_adhikari", length = 255, nullable = false)
    private String vibhagiAdhikari;

    @NotNull
    @Size(max = 255)
    @Column(name = "vibhagi_adhikari_mr", length = 255, nullable = false)
    private String vibhagiAdhikariMr;

    @NotNull
    @Size(max = 255)
    @Column(name = "branch", length = 255, nullable = false)
    private String branch;

    @Column(name = "branch_mr")
    private String branchMr;

    @NotNull
    @Column(name = "insp_amt", nullable = false)
    private Double inspAmt;

    @Column(name = "insp_amt_mr")
    private String inspAmtMr;

    @ManyToOne
    private CropMaster cropMaster;

    @ManyToOne
    @JsonIgnoreProperties(value = { "kmMaster" }, allowSetters = true)
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

    public String getVibhagiAdhikari() {
        return this.vibhagiAdhikari;
    }

    public KmCrops vibhagiAdhikari(String vibhagiAdhikari) {
        this.setVibhagiAdhikari(vibhagiAdhikari);
        return this;
    }

    public void setVibhagiAdhikari(String vibhagiAdhikari) {
        this.vibhagiAdhikari = vibhagiAdhikari;
    }

    public String getVibhagiAdhikariMr() {
        return this.vibhagiAdhikariMr;
    }

    public KmCrops vibhagiAdhikariMr(String vibhagiAdhikariMr) {
        this.setVibhagiAdhikariMr(vibhagiAdhikariMr);
        return this;
    }

    public void setVibhagiAdhikariMr(String vibhagiAdhikariMr) {
        this.vibhagiAdhikariMr = vibhagiAdhikariMr;
    }

    public String getBranch() {
        return this.branch;
    }

    public KmCrops branch(String branch) {
        this.setBranch(branch);
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchMr() {
        return this.branchMr;
    }

    public KmCrops branchMr(String branchMr) {
        this.setBranchMr(branchMr);
        return this;
    }

    public void setBranchMr(String branchMr) {
        this.branchMr = branchMr;
    }

    public Double getInspAmt() {
        return this.inspAmt;
    }

    public KmCrops inspAmt(Double inspAmt) {
        this.setInspAmt(inspAmt);
        return this;
    }

    public void setInspAmt(Double inspAmt) {
        this.inspAmt = inspAmt;
    }

    public String getInspAmtMr() {
        return this.inspAmtMr;
    }

    public KmCrops inspAmtMr(String inspAmtMr) {
        this.setInspAmtMr(inspAmtMr);
        return this;
    }

    public void setInspAmtMr(String inspAmtMr) {
        this.inspAmtMr = inspAmtMr;
    }

    public CropMaster getCropMaster() {
        return this.cropMaster;
    }

    public void setCropMaster(CropMaster cropMaster) {
        this.cropMaster = cropMaster;
    }

    public KmCrops cropMaster(CropMaster cropMaster) {
        this.setCropMaster(cropMaster);
        return this;
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
        return id != null && id.equals(((KmCrops) o).id);
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
            ", hector=" + getHector() +
            ", hectorMr='" + getHectorMr() + "'" +
            ", are=" + getAre() +
            ", areMr='" + getAreMr() + "'" +
            ", noOfTree=" + getNoOfTree() +
            ", noOfTreeMr='" + getNoOfTreeMr() + "'" +
            ", demand=" + getDemand() +
            ", demandMr='" + getDemandMr() + "'" +
            ", society='" + getSociety() + "'" +
            ", societyMr='" + getSocietyMr() + "'" +
            ", bankAmt=" + getBankAmt() +
            ", bankAmtMr='" + getBankAmtMr() + "'" +
            ", vibhagiAdhikari='" + getVibhagiAdhikari() + "'" +
            ", vibhagiAdhikariMr='" + getVibhagiAdhikariMr() + "'" +
            ", branch='" + getBranch() + "'" +
            ", branchMr='" + getBranchMr() + "'" +
            ", inspAmt=" + getInspAmt() +
            ", inspAmtMr='" + getInspAmtMr() + "'" +
            "}";
    }
}