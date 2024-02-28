package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

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

    //
    @Column(name = "shares", nullable = true)
    private Double shares;

    @Column(name = "shares_mr")
    private String sharesMr;

    //
    @Column(name = "sugar_shares", nullable = true)
    private Double sugarShares;

    @Column(name = "sugar_shares_mr")
    private String sugarSharesMr;

    //
    @Column(name = "deposite", nullable = true)
    private Double deposite;

    @Column(name = "deposite_mr")
    private String depositeMr;

    //
    @Column(name = "due_loan", nullable = true)
    private Double dueLoan;

    @Column(name = "due_loan_mr")
    private String dueLoanMr;

    //
    @Column(name = "due_amount", nullable = true)
    private Double dueAmount;

    @Column(name = "due_amount_mr")
    private String dueAmountMr;

    @Column(name = "due_date_mr")
    private String dueDateMr;

    //
    @Column(name = "due_date", nullable = true)
    private Instant dueDate;

    //
    @Column(name = "km_date", nullable = true)
    private Instant kmDate;

    @Column(name = "km_date_mr")
    private String kmDateMr;

    //
    @Column(name = "km_from_date", nullable = true)
    private Instant kmFromDate;

    @Column(name = "km_from_date_mr")
    private String kmFromDateMr;

    //
    @Column(name = "km_to_date", nullable = true)
    private Instant kmToDate;

    @Column(name = "km_to_date_mr")
    private String kmToDateMr;

    //
    @Column(name = "bagayat_hector", nullable = true)
    private Double bagayatHector;

    @Column(name = "bagayat_hector_mr")
    private String bagayatHectorMr;

    //
    @Column(name = "bagayat_are", nullable = true)
    private Double bagayatAre;

    @Column(name = "bagayat_are_mr")
    private String bagayatAreMr;

    //
    @Column(name = "jirayat_hector", nullable = true)
    private Double jirayatHector;

    @Column(name = "jirayat_hector_mr")
    private String jirayatHectorMr;

    //
    @Column(name = "jirayat_are", nullable = true)
    private Double jirayatAre;

    @Column(name = "jirayat_are_mr")
    private String jirayatAreMr;

    //
    @Column(name = "zindagi_amt", nullable = true)
    private Double zindagiAmt;


    //
    @Column(name = "zindagi_no", nullable = true)
    private Long zindagiNo;

    //
    @Size(max = 1000)
    @Column(name = "survey_no", length = 1000, nullable = true)
    private String surveyNo;

    @Column(name = "survey_no_mr")
    private String surveyNoMr;

    //
    @Column(name = "land_value", nullable = true)
    private Double landValue;

    @Column(name = "land_value_mr")
    private String landValueMr;

    //
    @Column(name = "e_agreement_amt", nullable = true)
    private Double eAgreementAmt;

    @Column(name = "e_agreement_amt_mr")
    private String eAgreementAmtMr;

    //
    @Column(name = "e_agreement_date", nullable = true)
    private Instant eAgreementDate;

    @Column(name = "e_agreement_date_mr")
    private String eAgreementDateMr;

    //
    @Column(name = "boja_amount", nullable = true)
    private Double bojaAmount;

    @Column(name = "boja_amount_mr")
    private String bojaAmountMr;

    //
    @Column(name = "boja_date", nullable = true)
    private Instant bojaDate;

    @Column(name = "boja_date_mr")
    private String bojaDateMr;

    @Column(name = "gatt_no")
    private String gattNo;
    @Column(name = "gatt_no_mr")
    private String gattNoMr;

    @Column(name = "dob")
    private Instant dob;

    @Column(name = "financial_year")
    private String financialYear;

    @JsonIgnoreProperties(value = {"branchCode", "branchCodeMr", "farmerName", "farmerNameMr", "farmerAddress", "farmerAddressMr", "gender", "genderMr", "caste", "casteMr", "pacsNumber", "aadharNo", "aadharNoMr", "panNo", "panNoMr", "mobileNo", "mobileNoMr", "kccNo", "kccNoMr", "savingAcNo", "savingAcNoMr", "pacsMemberCode", "pacsMemberCodeMr", "entryFlag", "birthDate", "birthDateMr", "loanAcNo", "loanAcNoMr", "farmerTypeMaster", "kmDetails"}, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "kmMaster_id")
    private KmMaster kmMaster;

    @OneToMany(mappedBy = "kmDetails", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"kmDetails"}, allowSetters = true, allowGetters = true)
    private Set<KmLoans> kmLoans;

    @OneToMany(mappedBy = "kmDetails", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"kmDetails"}, allowSetters = true, allowGetters = true)
    private Set<KmCrops> kmCrops;
    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getSurveyNoMr() {
        return surveyNoMr;
    }

    public void setSurveyNoMr(String surveyNoMr) {
        this.surveyNoMr = surveyNoMr;
    }

    public String getGattNo() {
        return gattNo;
    }

    public void setGattNo(String gattNo) {
        this.gattNo = gattNo;
    }

    public String getGattNoMr() {
        return gattNoMr;
    }

    public void setGattNoMr(String gattNoMr) {
        this.gattNoMr = gattNoMr;
    }

    public Set<KmLoans> getKmLoans() {
        return kmLoans;
    }

    public void setKmLoans(Set<KmLoans> kmLoans) {
        this.kmLoans = kmLoans;
    }

    public Set<KmCrops> getKmCrops() {
        return kmCrops;
    }

    public void setKmCrops(Set<KmCrops> kmCrops) {
        this.kmCrops = kmCrops;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KmDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public Double getShares() {
        return this.shares;
    }

    public void setShares(Double shares) {
        this.shares = shares;
    }

    public KmDetails shares(Double shares) {
        this.setShares(shares);
        return this;
    }

    public String getSharesMr() {
        return this.sharesMr;
    }

    public void setSharesMr(String sharesMr) {
        this.sharesMr = sharesMr;
    }

    public KmDetails sharesMr(String sharesMr) {
        this.setSharesMr(sharesMr);
        return this;
    }

    public Double getSugarShares() {
        return this.sugarShares;
    }

    public void setSugarShares(Double sugarShares) {
        this.sugarShares = sugarShares;
    }

    public KmDetails sugarShares(Double sugarShares) {
        this.setSugarShares(sugarShares);
        return this;
    }

    public String getSugarSharesMr() {
        return this.sugarSharesMr;
    }

    public void setSugarSharesMr(String sugarSharesMr) {
        this.sugarSharesMr = sugarSharesMr;
    }

    public KmDetails sugarSharesMr(String sugarSharesMr) {
        this.setSugarSharesMr(sugarSharesMr);
        return this;
    }

    public Double getDeposite() {
        return this.deposite;
    }

    public void setDeposite(Double deposite) {
        this.deposite = deposite;
    }

    public KmDetails deposite(Double deposite) {
        this.setDeposite(deposite);
        return this;
    }

    public String getDepositeMr() {
        return this.depositeMr;
    }

    public void setDepositeMr(String depositeMr) {
        this.depositeMr = depositeMr;
    }

    public KmDetails depositeMr(String depositeMr) {
        this.setDepositeMr(depositeMr);
        return this;
    }

    public Double getDueLoan() {
        return this.dueLoan;
    }

    public void setDueLoan(Double dueLoan) {
        this.dueLoan = dueLoan;
    }

    public KmDetails dueLoan(Double dueLoan) {
        this.setDueLoan(dueLoan);
        return this;
    }

    public String getDueLoanMr() {
        return this.dueLoanMr;
    }

    public void setDueLoanMr(String dueLoanMr) {
        this.dueLoanMr = dueLoanMr;
    }

    public KmDetails dueLoanMr(String dueLoanMr) {
        this.setDueLoanMr(dueLoanMr);
        return this;
    }

    public Double getDueAmount() {
        return this.dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public KmDetails dueAmount(Double dueAmount) {
        this.setDueAmount(dueAmount);
        return this;
    }

    public String getDueAmountMr() {
        return this.dueAmountMr;
    }

    public void setDueAmountMr(String dueAmountMr) {
        this.dueAmountMr = dueAmountMr;
    }

    public KmDetails dueAmountMr(String dueAmountMr) {
        this.setDueAmountMr(dueAmountMr);
        return this;
    }

    public String getDueDateMr() {
        return this.dueDateMr;
    }

    public void setDueDateMr(String dueDateMr) {
        this.dueDateMr = dueDateMr;
    }

    public KmDetails dueDateMr(String dueDateMr) {
        this.setDueDateMr(dueDateMr);
        return this;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public KmDetails dueDate(Instant dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public Instant getKmDate() {
        return this.kmDate;
    }

    public void setKmDate(Instant kmDate) {
        this.kmDate = kmDate;
    }

    public KmDetails kmDate(Instant kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public String getKmDateMr() {
        return this.kmDateMr;
    }

    public void setKmDateMr(String kmDateMr) {
        this.kmDateMr = kmDateMr;
    }

    public KmDetails kmDateMr(String kmDateMr) {
        this.setKmDateMr(kmDateMr);
        return this;
    }

    public Instant getKmFromDate() {
        return this.kmFromDate;
    }

    public void setKmFromDate(Instant kmFromDate) {
        this.kmFromDate = kmFromDate;
    }

    public KmDetails kmFromDate(Instant kmFromDate) {
        this.setKmFromDate(kmFromDate);
        return this;
    }

    public String getKmFromDateMr() {
        return this.kmFromDateMr;
    }

    public void setKmFromDateMr(String kmFromDateMr) {
        this.kmFromDateMr = kmFromDateMr;
    }

    public KmDetails kmFromDateMr(String kmFromDateMr) {
        this.setKmFromDateMr(kmFromDateMr);
        return this;
    }

    public Instant getKmToDate() {
        return this.kmToDate;
    }

    public void setKmToDate(Instant kmToDate) {
        this.kmToDate = kmToDate;
    }

    public KmDetails kmToDate(Instant kmToDate) {
        this.setKmToDate(kmToDate);
        return this;
    }

    public String getKmToDateMr() {
        return this.kmToDateMr;
    }

    public void setKmToDateMr(String kmToDateMr) {
        this.kmToDateMr = kmToDateMr;
    }

    public KmDetails kmToDateMr(String kmToDateMr) {
        this.setKmToDateMr(kmToDateMr);
        return this;
    }

    public Double getBagayatHector() {
        return this.bagayatHector;
    }

    public void setBagayatHector(Double bagayatHector) {
        this.bagayatHector = bagayatHector;
    }

    public KmDetails bagayatHector(Double bagayatHector) {
        this.setBagayatHector(bagayatHector);
        return this;
    }

    public String getBagayatHectorMr() {
        return this.bagayatHectorMr;
    }

    public void setBagayatHectorMr(String bagayatHectorMr) {
        this.bagayatHectorMr = bagayatHectorMr;
    }

    public KmDetails bagayatHectorMr(String bagayatHectorMr) {
        this.setBagayatHectorMr(bagayatHectorMr);
        return this;
    }

    public Double getBagayatAre() {
        return this.bagayatAre;
    }

    public void setBagayatAre(Double bagayatAre) {
        this.bagayatAre = bagayatAre;
    }

    public KmDetails bagayatAre(Double bagayatAre) {
        this.setBagayatAre(bagayatAre);
        return this;
    }

    public String getBagayatAreMr() {
        return this.bagayatAreMr;
    }

    public void setBagayatAreMr(String bagayatAreMr) {
        this.bagayatAreMr = bagayatAreMr;
    }

    public KmDetails bagayatAreMr(String bagayatAreMr) {
        this.setBagayatAreMr(bagayatAreMr);
        return this;
    }

    public Double getJirayatHector() {
        return this.jirayatHector;
    }

    public void setJirayatHector(Double jirayatHector) {
        this.jirayatHector = jirayatHector;
    }

    public KmDetails jirayatHector(Double jirayatHector) {
        this.setJirayatHector(jirayatHector);
        return this;
    }

    public String getJirayatHectorMr() {
        return this.jirayatHectorMr;
    }

    public void setJirayatHectorMr(String jirayatHectorMr) {
        this.jirayatHectorMr = jirayatHectorMr;
    }

    public KmDetails jirayatHectorMr(String jirayatHectorMr) {
        this.setJirayatHectorMr(jirayatHectorMr);
        return this;
    }

    public Double getJirayatAre() {
        return this.jirayatAre;
    }

    public void setJirayatAre(Double jirayatAre) {
        this.jirayatAre = jirayatAre;
    }

    public KmDetails jirayatAre(Double jirayatAre) {
        this.setJirayatAre(jirayatAre);
        return this;
    }

    public String getJirayatAreMr() {
        return this.jirayatAreMr;
    }

    public void setJirayatAreMr(String jirayatAreMr) {
        this.jirayatAreMr = jirayatAreMr;
    }

    public KmDetails jirayatAreMr(String jirayatAreMr) {
        this.setJirayatAreMr(jirayatAreMr);
        return this;
    }

    public Double getZindagiAmt() {
        return this.zindagiAmt;
    }

    public void setZindagiAmt(Double zindagiAmt) {
        this.zindagiAmt = zindagiAmt;
    }

    public KmDetails zindagiAmt(Double zindagiAmt) {
        this.setZindagiAmt(zindagiAmt);
        return this;
    }

    public Long getZindagiNo() {
        return this.zindagiNo;
    }

    public void setZindagiNo(Long zindagiNo) {
        this.zindagiNo = zindagiNo;
    }

    public KmDetails zindagiNo(Long zindagiNo) {
        this.setZindagiNo(zindagiNo);
        return this;
    }

    public String getSurveyNo() {
        return this.surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public KmDetails surveyNo(String surveyNo) {
        this.setSurveyNo(surveyNo);
        return this;
    }

    public Double getLandValue() {
        return this.landValue;
    }

    public void setLandValue(Double landValue) {
        this.landValue = landValue;
    }

    public KmDetails landValue(Double landValue) {
        this.setLandValue(landValue);
        return this;
    }

    public String getLandValueMr() {
        return this.landValueMr;
    }

    public void setLandValueMr(String landValueMr) {
        this.landValueMr = landValueMr;
    }

    public KmDetails landValueMr(String landValueMr) {
        this.setLandValueMr(landValueMr);
        return this;
    }

    public Double geteAgreementAmt() {
        return this.eAgreementAmt;
    }

    public void seteAgreementAmt(Double eAgreementAmt) {
        this.eAgreementAmt = eAgreementAmt;
    }

    public KmDetails eAgreementAmt(Double eAgreementAmt) {
        this.seteAgreementAmt(eAgreementAmt);
        return this;
    }

    public String geteAgreementAmtMr() {
        return this.eAgreementAmtMr;
    }

    public void seteAgreementAmtMr(String eAgreementAmtMr) {
        this.eAgreementAmtMr = eAgreementAmtMr;
    }

    public KmDetails eAgreementAmtMr(String eAgreementAmtMr) {
        this.seteAgreementAmtMr(eAgreementAmtMr);
        return this;
    }

    public Instant geteAgreementDate() {
        return this.eAgreementDate;
    }

    public void seteAgreementDate(Instant eAgreementDate) {
        this.eAgreementDate = eAgreementDate;
    }

    public KmDetails eAgreementDate(Instant eAgreementDate) {
        this.seteAgreementDate(eAgreementDate);
        return this;
    }

    public String geteAgreementDateMr() {
        return this.eAgreementDateMr;
    }

    public void seteAgreementDateMr(String eAgreementDateMr) {
        this.eAgreementDateMr = eAgreementDateMr;
    }

    public KmDetails eAgreementDateMr(String eAgreementDateMr) {
        this.seteAgreementDateMr(eAgreementDateMr);
        return this;
    }

    public Double getBojaAmount() {
        return this.bojaAmount;
    }

    public void setBojaAmount(Double bojaAmount) {
        this.bojaAmount = bojaAmount;
    }

    public KmDetails bojaAmount(Double bojaAmount) {
        this.setBojaAmount(bojaAmount);
        return this;
    }

    public String getBojaAmountMr() {
        return this.bojaAmountMr;
    }

    public void setBojaAmountMr(String bojaAmountMr) {
        this.bojaAmountMr = bojaAmountMr;
    }

    public KmDetails bojaAmountMr(String bojaAmountMr) {
        this.setBojaAmountMr(bojaAmountMr);
        return this;
    }

    public Instant getBojaDate() {
        return this.bojaDate;
    }

    public void setBojaDate(Instant bojaDate) {
        this.bojaDate = bojaDate;
    }

    public KmDetails bojaDate(Instant bojaDate) {
        this.setBojaDate(bojaDate);
        return this;
    }

    public String getBojaDateMr() {
        return this.bojaDateMr;
    }

    public void setBojaDateMr(String bojaDateMr) {
        this.bojaDateMr = bojaDateMr;
    }

    public KmDetails bojaDateMr(String bojaDateMr) {
        this.setBojaDateMr(bojaDateMr);
        return this;
    }

    public KmMaster getKmMaster() {
        return this.kmMaster;
    }

    public void setKmMaster(KmMaster kmMaster) {
        this.kmMaster = kmMaster;
    }

    public Instant getDob() {
        return this.dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public KmDetails dob(Instant dob) {
        this.setDob(dob);
        return this;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public KmDetails financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
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

    @Override
    public String toString() {
        return "KmDetails{" +
            "id=" + id +
            ", shares=" + shares +
            ", sharesMr='" + sharesMr + '\'' +
            ", sugarShares=" + sugarShares +
            ", sugarSharesMr='" + sugarSharesMr + '\'' +
            ", deposite=" + deposite +
            ", depositeMr='" + depositeMr + '\'' +
            ", dueLoan=" + dueLoan +
            ", dueLoanMr='" + dueLoanMr + '\'' +
            ", dueAmount=" + dueAmount +
            ", dueAmountMr='" + dueAmountMr + '\'' +
            ", dueDateMr='" + dueDateMr + '\'' +
            ", dueDate=" + dueDate +
            ", kmDate=" + kmDate +
            ", kmDateMr='" + kmDateMr + '\'' +
            ", kmFromDate=" + kmFromDate +
            ", kmFromDateMr='" + kmFromDateMr + '\'' +
            ", kmToDate=" + kmToDate +
            ", kmToDateMr='" + kmToDateMr + '\'' +
            ", bagayatHector=" + bagayatHector +
            ", bagayatHectorMr='" + bagayatHectorMr + '\'' +
            ", bagayatAre=" + bagayatAre +
            ", bagayatAreMr='" + bagayatAreMr + '\'' +
            ", jirayatHector=" + jirayatHector +
            ", jirayatHectorMr='" + jirayatHectorMr + '\'' +
            ", jirayatAre=" + jirayatAre +
            ", jirayatAreMr='" + jirayatAreMr + '\'' +
            ", zindagiAmt=" + zindagiAmt +
            ", zindagiNo=" + zindagiNo +
            ", surveyNo='" + surveyNo + '\'' +
            ", surveyNoMr='" + surveyNoMr + '\'' +
            ", landValue=" + landValue +
            ", landValueMr='" + landValueMr + '\'' +
            ", eAgreementAmt=" + eAgreementAmt +
            ", eAgreementAmtMr='" + eAgreementAmtMr + '\'' +
            ", eAgreementDate=" + eAgreementDate +
            ", eAgreementDateMr='" + eAgreementDateMr + '\'' +
            ", bojaAmount=" + bojaAmount +
            ", bojaAmountMr='" + bojaAmountMr + '\'' +
            ", bojaDate=" + bojaDate +
            ", bojaDateMr='" + bojaDateMr + '\'' +
            ", gattNo='" + gattNo + '\'' +
            ", gattNoMr='" + gattNoMr + '\'' +
            ", kmMaster=" + kmMaster +
            ", kmLoans=" + kmLoans +
            ", kmCrops=" + kmCrops +
            '}';
    }
}
