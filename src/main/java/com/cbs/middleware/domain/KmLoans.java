package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A KmLoans.
 */
@Entity
@Table(name = "km_loans")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmLoans extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crop_name")
    private String cropName;

    @Column(name = "crop_name_mr")
    private String cropNameMr;

    @Column(name = "loan_date")
    private Instant loanDate;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "loan_amount_mr")
    private String loanAmountMr;

    @Column(name = "are")
    private Double are;

    @Column(name = "are_mr")
    private String areMr;

    @Column(name = "receivable_amt")
    private Double receivableAmt;

    @Column(name = "receivable_amt_mr")
    private String receivableAmtMr;

    @Column(name = "due_amt")
    private Double dueAmt;

    @Column(name = "due_amt_mr")
    private String dueAmtMr;

    @Column(name = "due_date")
    private Instant dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "kmMaster", "kmLoans" }, allowSetters = true)
    private KmDetails kmDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KmLoans id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropName() {
        return this.cropName;
    }

    public KmLoans cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropNameMr() {
        return this.cropNameMr;
    }

    public KmLoans cropNameMr(String cropNameMr) {
        this.setCropNameMr(cropNameMr);
        return this;
    }

    public void setCropNameMr(String cropNameMr) {
        this.cropNameMr = cropNameMr;
    }

    public Instant getLoanDate() {
        return this.loanDate;
    }

    public KmLoans loanDate(Instant loanDate) {
        this.setLoanDate(loanDate);
        return this;
    }

    public void setLoanDate(Instant loanDate) {
        this.loanDate = loanDate;
    }

    public Double getLoanAmount() {
        return this.loanAmount;
    }

    public KmLoans loanAmount(Double loanAmount) {
        this.setLoanAmount(loanAmount);
        return this;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAmountMr() {
        return this.loanAmountMr;
    }

    public KmLoans loanAmountMr(String loanAmountMr) {
        this.setLoanAmountMr(loanAmountMr);
        return this;
    }

    public void setLoanAmountMr(String loanAmountMr) {
        this.loanAmountMr = loanAmountMr;
    }

    public Double getAre() {
        return this.are;
    }

    public KmLoans are(Double are) {
        this.setAre(are);
        return this;
    }

    public void setAre(Double are) {
        this.are = are;
    }

    public String getAreMr() {
        return this.areMr;
    }

    public KmLoans areMr(String areMr) {
        this.setAreMr(areMr);
        return this;
    }

    public void setAreMr(String areMr) {
        this.areMr = areMr;
    }

    public Double getReceivableAmt() {
        return this.receivableAmt;
    }

    public KmLoans receivableAmt(Double receivableAmt) {
        this.setReceivableAmt(receivableAmt);
        return this;
    }

    public void setReceivableAmt(Double receivableAmt) {
        this.receivableAmt = receivableAmt;
    }

    public String getReceivableAmtMr() {
        return this.receivableAmtMr;
    }

    public KmLoans receivableAmtMr(String receivableAmtMr) {
        this.setReceivableAmtMr(receivableAmtMr);
        return this;
    }

    public void setReceivableAmtMr(String receivableAmtMr) {
        this.receivableAmtMr = receivableAmtMr;
    }

    public Double getDueAmt() {
        return this.dueAmt;
    }

    public KmLoans dueAmt(Double dueAmt) {
        this.setDueAmt(dueAmt);
        return this;
    }

    public void setDueAmt(Double dueAmt) {
        this.dueAmt = dueAmt;
    }

    public String getDueAmtMr() {
        return this.dueAmtMr;
    }

    public KmLoans dueAmtMr(String dueAmtMr) {
        this.setDueAmtMr(dueAmtMr);
        return this;
    }

    public void setDueAmtMr(String dueAmtMr) {
        this.dueAmtMr = dueAmtMr;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public KmLoans dueDate(Instant dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public KmDetails getKmDetails() {
        return this.kmDetails;
    }

    public void setKmDetails(KmDetails kmDetails) {
        this.kmDetails = kmDetails;
    }

    public KmLoans kmDetails(KmDetails kmDetails) {
        this.setKmDetails(kmDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KmLoans)) {
            return false;
        }
        return getId() != null && getId().equals(((KmLoans) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmLoans{" +
            "id=" + getId() +
            ", cropName='" + getCropName() + "'" +
            ", cropNameMr='" + getCropNameMr() + "'" +
            ", loanDate='" + getLoanDate() + "'" +
            ", loanAmount=" + getLoanAmount() +
            ", loanAmountMr='" + getLoanAmountMr() + "'" +
            ", are=" + getAre() +
            ", areMr='" + getAreMr() + "'" +
            ", receivableAmt=" + getReceivableAmt() +
            ", receivableAmtMr='" + getReceivableAmtMr() + "'" +
            ", dueAmt=" + getDueAmt() +
            ", dueAmtMr='" + getDueAmtMr() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            "}";
    }
}
