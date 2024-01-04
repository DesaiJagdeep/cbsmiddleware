package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A KmDetails.
 */
@Entity
@Table(name = "km_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shares")
    private Double shares;

    @Column(name = "shares_mr")
    private String sharesMr;

    @Column(name = "sugar_shares")
    private Double sugarShares;

    @Column(name = "sugar_shares_mr")
    private String sugarSharesMr;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "deposit_mr")
    private String depositMr;

    @Column(name = "due_loan")
    private Double dueLoan;

    @Column(name = "due_loan_mr")
    private String dueLoanMr;

    @Column(name = "due_amount")
    private Double dueAmount;

    @Column(name = "due_amount_mr")
    private String dueAmountMr;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "km_date")
    private Instant kmDate;

    @Column(name = "km_from_date")
    private Instant kmFromDate;

    @Column(name = "km_to_date")
    private Instant kmToDate;

    @Column(name = "bagayat_hector")
    private Double bagayatHector;

    @Column(name = "bagayat_hector_mr")
    private String bagayatHectorMr;

    @Column(name = "bagayat_are")
    private Double bagayatAre;

    @Column(name = "bagayat_are_mr")
    private String bagayatAreMr;

    @Column(name = "jirayat_hector")
    private Double jirayatHector;

    @Column(name = "jirayat_hector_mr")
    private String jirayatHectorMr;

    @Column(name = "jirayat_are")
    private Double jirayatAre;

    @Column(name = "jirayat_are_mr")
    private String jirayatAreMr;

    @Column(name = "land_value")
    private Double landValue;

    @Column(name = "land_value_mr")
    private String landValueMr;

    @Column(name = "e_aggrement_amt")
    private Double eAggrementAmt;

    @Column(name = "e_agreement_amt")
    private String eAgreementAmt;

    @Column(name = "e_agreement_date")
    private Instant eAgreementDate;

    @Column(name = "boja_amount")
    private Double bojaAmount;

    @Column(name = "boja_amount_mr")
    private String bojaAmountMr;

    @Column(name = "boja_date")
    private Instant bojaDate;

    @JsonIgnoreProperties(value = { "kmDetails" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private KmMaster kmMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KmDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getShares() {
        return this.shares;
    }

    public KmDetails shares(Double shares) {
        this.setShares(shares);
        return this;
    }

    public void setShares(Double shares) {
        this.shares = shares;
    }

    public String getSharesMr() {
        return this.sharesMr;
    }

    public KmDetails sharesMr(String sharesMr) {
        this.setSharesMr(sharesMr);
        return this;
    }

    public void setSharesMr(String sharesMr) {
        this.sharesMr = sharesMr;
    }

    public Double getSugarShares() {
        return this.sugarShares;
    }

    public KmDetails sugarShares(Double sugarShares) {
        this.setSugarShares(sugarShares);
        return this;
    }

    public void setSugarShares(Double sugarShares) {
        this.sugarShares = sugarShares;
    }

    public String getSugarSharesMr() {
        return this.sugarSharesMr;
    }

    public KmDetails sugarSharesMr(String sugarSharesMr) {
        this.setSugarSharesMr(sugarSharesMr);
        return this;
    }

    public void setSugarSharesMr(String sugarSharesMr) {
        this.sugarSharesMr = sugarSharesMr;
    }

    public Double getDeposit() {
        return this.deposit;
    }

    public KmDetails deposit(Double deposit) {
        this.setDeposit(deposit);
        return this;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getDepositMr() {
        return this.depositMr;
    }

    public KmDetails depositMr(String depositMr) {
        this.setDepositMr(depositMr);
        return this;
    }

    public void setDepositMr(String depositMr) {
        this.depositMr = depositMr;
    }

    public Double getDueLoan() {
        return this.dueLoan;
    }

    public KmDetails dueLoan(Double dueLoan) {
        this.setDueLoan(dueLoan);
        return this;
    }

    public void setDueLoan(Double dueLoan) {
        this.dueLoan = dueLoan;
    }

    public String getDueLoanMr() {
        return this.dueLoanMr;
    }

    public KmDetails dueLoanMr(String dueLoanMr) {
        this.setDueLoanMr(dueLoanMr);
        return this;
    }

    public void setDueLoanMr(String dueLoanMr) {
        this.dueLoanMr = dueLoanMr;
    }

    public Double getDueAmount() {
        return this.dueAmount;
    }

    public KmDetails dueAmount(Double dueAmount) {
        this.setDueAmount(dueAmount);
        return this;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getDueAmountMr() {
        return this.dueAmountMr;
    }

    public KmDetails dueAmountMr(String dueAmountMr) {
        this.setDueAmountMr(dueAmountMr);
        return this;
    }

    public void setDueAmountMr(String dueAmountMr) {
        this.dueAmountMr = dueAmountMr;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public KmDetails dueDate(Instant dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getKmDate() {
        return this.kmDate;
    }

    public KmDetails kmDate(Instant kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public void setKmDate(Instant kmDate) {
        this.kmDate = kmDate;
    }

    public Instant getKmFromDate() {
        return this.kmFromDate;
    }

    public KmDetails kmFromDate(Instant kmFromDate) {
        this.setKmFromDate(kmFromDate);
        return this;
    }

    public void setKmFromDate(Instant kmFromDate) {
        this.kmFromDate = kmFromDate;
    }

    public Instant getKmToDate() {
        return this.kmToDate;
    }

    public KmDetails kmToDate(Instant kmToDate) {
        this.setKmToDate(kmToDate);
        return this;
    }

    public void setKmToDate(Instant kmToDate) {
        this.kmToDate = kmToDate;
    }

    public Double getBagayatHector() {
        return this.bagayatHector;
    }

    public KmDetails bagayatHector(Double bagayatHector) {
        this.setBagayatHector(bagayatHector);
        return this;
    }

    public void setBagayatHector(Double bagayatHector) {
        this.bagayatHector = bagayatHector;
    }

    public String getBagayatHectorMr() {
        return this.bagayatHectorMr;
    }

    public KmDetails bagayatHectorMr(String bagayatHectorMr) {
        this.setBagayatHectorMr(bagayatHectorMr);
        return this;
    }

    public void setBagayatHectorMr(String bagayatHectorMr) {
        this.bagayatHectorMr = bagayatHectorMr;
    }

    public Double getBagayatAre() {
        return this.bagayatAre;
    }

    public KmDetails bagayatAre(Double bagayatAre) {
        this.setBagayatAre(bagayatAre);
        return this;
    }

    public void setBagayatAre(Double bagayatAre) {
        this.bagayatAre = bagayatAre;
    }

    public String getBagayatAreMr() {
        return this.bagayatAreMr;
    }

    public KmDetails bagayatAreMr(String bagayatAreMr) {
        this.setBagayatAreMr(bagayatAreMr);
        return this;
    }

    public void setBagayatAreMr(String bagayatAreMr) {
        this.bagayatAreMr = bagayatAreMr;
    }

    public Double getJirayatHector() {
        return this.jirayatHector;
    }

    public KmDetails jirayatHector(Double jirayatHector) {
        this.setJirayatHector(jirayatHector);
        return this;
    }

    public void setJirayatHector(Double jirayatHector) {
        this.jirayatHector = jirayatHector;
    }

    public String getJirayatHectorMr() {
        return this.jirayatHectorMr;
    }

    public KmDetails jirayatHectorMr(String jirayatHectorMr) {
        this.setJirayatHectorMr(jirayatHectorMr);
        return this;
    }

    public void setJirayatHectorMr(String jirayatHectorMr) {
        this.jirayatHectorMr = jirayatHectorMr;
    }

    public Double getJirayatAre() {
        return this.jirayatAre;
    }

    public KmDetails jirayatAre(Double jirayatAre) {
        this.setJirayatAre(jirayatAre);
        return this;
    }

    public void setJirayatAre(Double jirayatAre) {
        this.jirayatAre = jirayatAre;
    }

    public String getJirayatAreMr() {
        return this.jirayatAreMr;
    }

    public KmDetails jirayatAreMr(String jirayatAreMr) {
        this.setJirayatAreMr(jirayatAreMr);
        return this;
    }

    public void setJirayatAreMr(String jirayatAreMr) {
        this.jirayatAreMr = jirayatAreMr;
    }

    public Double getLandValue() {
        return this.landValue;
    }

    public KmDetails landValue(Double landValue) {
        this.setLandValue(landValue);
        return this;
    }

    public void setLandValue(Double landValue) {
        this.landValue = landValue;
    }

    public String getLandValueMr() {
        return this.landValueMr;
    }

    public KmDetails landValueMr(String landValueMr) {
        this.setLandValueMr(landValueMr);
        return this;
    }

    public void setLandValueMr(String landValueMr) {
        this.landValueMr = landValueMr;
    }

    public Double geteAggrementAmt() {
        return this.eAggrementAmt;
    }

    public KmDetails eAggrementAmt(Double eAggrementAmt) {
        this.seteAggrementAmt(eAggrementAmt);
        return this;
    }

    public void seteAggrementAmt(Double eAggrementAmt) {
        this.eAggrementAmt = eAggrementAmt;
    }

    public String geteAgreementAmt() {
        return this.eAgreementAmt;
    }

    public KmDetails eAgreementAmt(String eAgreementAmt) {
        this.seteAgreementAmt(eAgreementAmt);
        return this;
    }

    public void seteAgreementAmt(String eAgreementAmt) {
        this.eAgreementAmt = eAgreementAmt;
    }

    public Instant geteAgreementDate() {
        return this.eAgreementDate;
    }

    public KmDetails eAgreementDate(Instant eAgreementDate) {
        this.seteAgreementDate(eAgreementDate);
        return this;
    }

    public void seteAgreementDate(Instant eAgreementDate) {
        this.eAgreementDate = eAgreementDate;
    }

    public Double getBojaAmount() {
        return this.bojaAmount;
    }

    public KmDetails bojaAmount(Double bojaAmount) {
        this.setBojaAmount(bojaAmount);
        return this;
    }

    public void setBojaAmount(Double bojaAmount) {
        this.bojaAmount = bojaAmount;
    }

    public String getBojaAmountMr() {
        return this.bojaAmountMr;
    }

    public KmDetails bojaAmountMr(String bojaAmountMr) {
        this.setBojaAmountMr(bojaAmountMr);
        return this;
    }

    public void setBojaAmountMr(String bojaAmountMr) {
        this.bojaAmountMr = bojaAmountMr;
    }

    public Instant getBojaDate() {
        return this.bojaDate;
    }

    public KmDetails bojaDate(Instant bojaDate) {
        this.setBojaDate(bojaDate);
        return this;
    }

    public void setBojaDate(Instant bojaDate) {
        this.bojaDate = bojaDate;
    }

    public KmMaster getKmMaster() {
        return this.kmMaster;
    }

    public void setKmMaster(KmMaster kmMaster) {
        this.kmMaster = kmMaster;
    }

    public KmDetails kmMaster(KmMaster kmMaster) {
        this.setKmMaster(kmMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KmDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((KmDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmDetails{" +
            "id=" + getId() +
            ", shares=" + getShares() +
            ", sharesMr='" + getSharesMr() + "'" +
            ", sugarShares=" + getSugarShares() +
            ", sugarSharesMr='" + getSugarSharesMr() + "'" +
            ", deposit=" + getDeposit() +
            ", depositMr='" + getDepositMr() + "'" +
            ", dueLoan=" + getDueLoan() +
            ", dueLoanMr='" + getDueLoanMr() + "'" +
            ", dueAmount=" + getDueAmount() +
            ", dueAmountMr='" + getDueAmountMr() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", kmDate='" + getKmDate() + "'" +
            ", kmFromDate='" + getKmFromDate() + "'" +
            ", kmToDate='" + getKmToDate() + "'" +
            ", bagayatHector=" + getBagayatHector() +
            ", bagayatHectorMr='" + getBagayatHectorMr() + "'" +
            ", bagayatAre=" + getBagayatAre() +
            ", bagayatAreMr='" + getBagayatAreMr() + "'" +
            ", jirayatHector=" + getJirayatHector() +
            ", jirayatHectorMr='" + getJirayatHectorMr() + "'" +
            ", jirayatAre=" + getJirayatAre() +
            ", jirayatAreMr='" + getJirayatAreMr() + "'" +
            ", landValue=" + getLandValue() +
            ", landValueMr='" + getLandValueMr() + "'" +
            ", eAggrementAmt=" + geteAggrementAmt() +
            ", eAgreementAmt='" + geteAgreementAmt() + "'" +
            ", eAgreementDate='" + geteAgreementDate() + "'" +
            ", bojaAmount=" + getBojaAmount() +
            ", bojaAmountMr='" + getBojaAmountMr() + "'" +
            ", bojaDate='" + getBojaDate() + "'" +
            "}";
    }
}
