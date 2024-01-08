package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A KmDetails.
 */
@Entity
@Table(name = "km_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmDetails extends AbstractAuditingEntity<Long> implements Serializable {

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

    @Column(name = "deposite")
    private Double deposite;

    @Column(name = "deposite_mr")
    private String depositeMr;

    @Column(name = "due_loan")
    private Double dueLoan;

    @Column(name = "due_loan_mr")
    private String dueLoanMr;

    @Column(name = "due_amount")
    private Double dueAmount;

    @Column(name = "due_amount_mr")
    private String dueAmountMr;

    @Column(name = "due_date_mr")
    private String dueDateMr;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "km_date")
    private Instant kmDate;

    @Column(name = "km_date_mr")
    private String kmDateMr;

    @Column(name = "km_from_date")
    private Instant kmFromDate;

    @Column(name = "km_from_date_mr")
    private String kmFromDateMr;

    @Column(name = "km_to_date")
    private Instant kmToDate;

    @Column(name = "km_to_date_mr")
    private String kmToDateMr;

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

    @Column(name = "zindagi_amt")
    private Double zindagiAmt;

    @Column(name = "zindagi_no")
    private Long zindagiNo;

    @Column(name = "survey_no")
    private Long surveyNo;

    @Column(name = "land_value")
    private Double landValue;

    @Column(name = "land_value_mr")
    private String landValueMr;

    @Column(name = "e_agreement_amt")
    private Double eAgreementAmt;

    @Column(name = "e_agreement_amt_mr")
    private String eAgreementAmtMr;

    @Column(name = "e_agreement_date")
    private Instant eAgreementDate;

    @Column(name = "e_agreement_date_mr")
    private String eAgreementDateMr;

    @Column(name = "boja_amount")
    private Double bojaAmount;

    @Column(name = "boja_amount_mr")
    private String bojaAmountMr;

    @Column(name = "boja_date")
    private Instant bojaDate;

    @Column(name = "boja_date_mr")
    private String bojaDateMr;

    @JsonIgnoreProperties(value = { "farmerTypeMaster" }, allowSetters = true)
    @OneToOne
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

    public Double getDeposite() {
        return this.deposite;
    }

    public KmDetails deposite(Double deposite) {
        this.setDeposite(deposite);
        return this;
    }

    public void setDeposite(Double deposite) {
        this.deposite = deposite;
    }

    public String getDepositeMr() {
        return this.depositeMr;
    }

    public KmDetails depositeMr(String depositeMr) {
        this.setDepositeMr(depositeMr);
        return this;
    }

    public void setDepositeMr(String depositeMr) {
        this.depositeMr = depositeMr;
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

    public String getDueDateMr() {
        return this.dueDateMr;
    }

    public KmDetails dueDateMr(String dueDateMr) {
        this.setDueDateMr(dueDateMr);
        return this;
    }

    public void setDueDateMr(String dueDateMr) {
        this.dueDateMr = dueDateMr;
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

    public String getKmDateMr() {
        return this.kmDateMr;
    }

    public KmDetails kmDateMr(String kmDateMr) {
        this.setKmDateMr(kmDateMr);
        return this;
    }

    public void setKmDateMr(String kmDateMr) {
        this.kmDateMr = kmDateMr;
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

    public String getKmFromDateMr() {
        return this.kmFromDateMr;
    }

    public KmDetails kmFromDateMr(String kmFromDateMr) {
        this.setKmFromDateMr(kmFromDateMr);
        return this;
    }

    public void setKmFromDateMr(String kmFromDateMr) {
        this.kmFromDateMr = kmFromDateMr;
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

    public String getKmToDateMr() {
        return this.kmToDateMr;
    }

    public KmDetails kmToDateMr(String kmToDateMr) {
        this.setKmToDateMr(kmToDateMr);
        return this;
    }

    public void setKmToDateMr(String kmToDateMr) {
        this.kmToDateMr = kmToDateMr;
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

    public Double getZindagiAmt() {
        return this.zindagiAmt;
    }

    public KmDetails zindagiAmt(Double zindagiAmt) {
        this.setZindagiAmt(zindagiAmt);
        return this;
    }

    public void setZindagiAmt(Double zindagiAmt) {
        this.zindagiAmt = zindagiAmt;
    }

    public Long getZindagiNo() {
        return this.zindagiNo;
    }

    public KmDetails zindagiNo(Long zindagiNo) {
        this.setZindagiNo(zindagiNo);
        return this;
    }

    public void setZindagiNo(Long zindagiNo) {
        this.zindagiNo = zindagiNo;
    }

    public Long getSurveyNo() {
        return this.surveyNo;
    }

    public KmDetails surveyNo(Long surveyNo) {
        this.setSurveyNo(surveyNo);
        return this;
    }

    public void setSurveyNo(Long surveyNo) {
        this.surveyNo = surveyNo;
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

    public Double geteAgreementAmt() {
        return this.eAgreementAmt;
    }

    public KmDetails eAgreementAmt(Double eAgreementAmt) {
        this.seteAgreementAmt(eAgreementAmt);
        return this;
    }

    public void seteAgreementAmt(Double eAgreementAmt) {
        this.eAgreementAmt = eAgreementAmt;
    }

    public String geteAgreementAmtMr() {
        return this.eAgreementAmtMr;
    }

    public KmDetails eAgreementAmtMr(String eAgreementAmtMr) {
        this.seteAgreementAmtMr(eAgreementAmtMr);
        return this;
    }

    public void seteAgreementAmtMr(String eAgreementAmtMr) {
        this.eAgreementAmtMr = eAgreementAmtMr;
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

    public String geteAgreementDateMr() {
        return this.eAgreementDateMr;
    }

    public KmDetails eAgreementDateMr(String eAgreementDateMr) {
        this.seteAgreementDateMr(eAgreementDateMr);
        return this;
    }

    public void seteAgreementDateMr(String eAgreementDateMr) {
        this.eAgreementDateMr = eAgreementDateMr;
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

    public String getBojaDateMr() {
        return this.bojaDateMr;
    }

    public KmDetails bojaDateMr(String bojaDateMr) {
        this.setBojaDateMr(bojaDateMr);
        return this;
    }

    public void setBojaDateMr(String bojaDateMr) {
        this.bojaDateMr = bojaDateMr;
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
        return id != null && id.equals(((KmDetails) o).id);
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
            ", deposite=" + getDeposite() +
            ", depositeMr='" + getDepositeMr() + "'" +
            ", dueLoan=" + getDueLoan() +
            ", dueLoanMr='" + getDueLoanMr() + "'" +
            ", dueAmount=" + getDueAmount() +
            ", dueAmountMr='" + getDueAmountMr() + "'" +
            ", dueDateMr='" + getDueDateMr() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", kmDate='" + getKmDate() + "'" +
            ", kmDateMr='" + getKmDateMr() + "'" +
            ", kmFromDate='" + getKmFromDate() + "'" +
            ", kmFromDateMr='" + getKmFromDateMr() + "'" +
            ", kmToDate='" + getKmToDate() + "'" +
            ", kmToDateMr='" + getKmToDateMr() + "'" +
            ", bagayatHector=" + getBagayatHector() +
            ", bagayatHectorMr='" + getBagayatHectorMr() + "'" +
            ", bagayatAre=" + getBagayatAre() +
            ", bagayatAreMr='" + getBagayatAreMr() + "'" +
            ", jirayatHector=" + getJirayatHector() +
            ", jirayatHectorMr='" + getJirayatHectorMr() + "'" +
            ", jirayatAre=" + getJirayatAre() +
            ", jirayatAreMr='" + getJirayatAreMr() + "'" +
            ", zindagiAmt=" + getZindagiAmt() +
            ", zindagiNo=" + getZindagiNo() +
            ", surveyNo=" + getSurveyNo() +
            ", landValue=" + getLandValue() +
            ", landValueMr='" + getLandValueMr() + "'" +
            ", eAgreementAmt=" + geteAgreementAmt() +
            ", eAgreementAmtMr='" + geteAgreementAmtMr() + "'" +
            ", eAgreementDate='" + geteAgreementDate() + "'" +
            ", eAgreementDateMr='" + geteAgreementDateMr() + "'" +
            ", bojaAmount=" + getBojaAmount() +
            ", bojaAmountMr='" + getBojaAmountMr() + "'" +
            ", bojaDate='" + getBojaDate() + "'" +
            ", bojaDateMr='" + getBojaDateMr() + "'" +
            "}";
    }
}
