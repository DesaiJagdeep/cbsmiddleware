package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

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

    //
    @Column(name = "hector", nullable = true)
    private Double hector;

    @Column(name = "hector_mr")
    private String hectorMr;

    //
    @Column(name = "are", nullable = true)
    private Double are;

    @Column(name = "aremr")
    private String aremr;

    //
    @Column(name = "no_of_tree", nullable = true)
    private Double noOfTree;

    @Column(name = "no_of_tree_mr")
    private String noOfTreeMr;

    //
    @Column(name = "sanction_amt", nullable = true)
    private Double sanctionAmt;

    @Column(name = "sanction_amt_mr")
    private String sanctionAmtMr;

    //
    @Column(name = "loan_amt", nullable = true)
    private Double loanAmt;

    @Column(name = "loan_amt_mr")
    private String loanAmtMr;

    //
    @Column(name = "receivable_amt", nullable = true)
    private Double receivableAmt;

    @Column(name = "receivable_amt_mr")
    private String receivableAmtMr;

    //
    @Column(name = "due_amt", nullable = true)
    private Double dueAmt;

    @Column(name = "due_amt_mr")
    private String dueAmtMr;

    //
    @Column(name = "due_date", nullable = true)
    private Instant dueDate;

    @Column(name = "due_date_mr")
    private String dueDateMr;

    @Column(name = "spare")
    private String spare;

    @ManyToOne
    private CropMaster cropMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "kmMaster"}, allowSetters = true)
    //@JsonIgnore
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

    public Double getHector() {
        return this.hector;
    }

    public KmLoans hector(Double hector) {
        this.setHector(hector);
        return this;
    }

    public void setHector(Double hector) {
        this.hector = hector;
    }

    public String getHectorMr() {
        return this.hectorMr;
    }

    public KmLoans hectorMr(String hectorMr) {
        this.setHectorMr(hectorMr);
        return this;
    }

    public void setHectorMr(String hectorMr) {
        this.hectorMr = hectorMr;
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

    public String getAremr() {
        return this.aremr;
    }

    public KmLoans aremr(String aremr) {
        this.setAremr(aremr);
        return this;
    }

    public void setAremr(String aremr) {
        this.aremr = aremr;
    }

    public Double getNoOfTree() {
        return this.noOfTree;
    }

    public KmLoans noOfTree(Double noOfTree) {
        this.setNoOfTree(noOfTree);
        return this;
    }

    public void setNoOfTree(Double noOfTree) {
        this.noOfTree = noOfTree;
    }

    public String getNoOfTreeMr() {
        return this.noOfTreeMr;
    }

    public KmLoans noOfTreeMr(String noOfTreeMr) {
        this.setNoOfTreeMr(noOfTreeMr);
        return this;
    }

    public void setNoOfTreeMr(String noOfTreeMr) {
        this.noOfTreeMr = noOfTreeMr;
    }

    public Double getSanctionAmt() {
        return this.sanctionAmt;
    }

    public KmLoans sanctionAmt(Double sanctionAmt) {
        this.setSanctionAmt(sanctionAmt);
        return this;
    }

    public void setSanctionAmt(Double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public String getSanctionAmtMr() {
        return this.sanctionAmtMr;
    }

    public KmLoans sanctionAmtMr(String sanctionAmtMr) {
        this.setSanctionAmtMr(sanctionAmtMr);
        return this;
    }

    public void setSanctionAmtMr(String sanctionAmtMr) {
        this.sanctionAmtMr = sanctionAmtMr;
    }

    public Double getLoanAmt() {
        return this.loanAmt;
    }

    public KmLoans loanAmt(Double loanAmt) {
        this.setLoanAmt(loanAmt);
        return this;
    }

    public void setLoanAmt(Double loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getLoanAmtMr() {
        return this.loanAmtMr;
    }

    public KmLoans loanAmtMr(String loanAmtMr) {
        this.setLoanAmtMr(loanAmtMr);
        return this;
    }

    public void setLoanAmtMr(String loanAmtMr) {
        this.loanAmtMr = loanAmtMr;
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

    public String getDueDateMr() {
        return this.dueDateMr;
    }

    public KmLoans dueDateMr(String dueDateMr) {
        this.setDueDateMr(dueDateMr);
        return this;
    }

    public void setDueDateMr(String dueDateMr) {
        this.dueDateMr = dueDateMr;
    }

    public String getSpare() {
        return this.spare;
    }

    public KmLoans spare(String spare) {
        this.setSpare(spare);
        return this;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public CropMaster getCropMaster() {
        return this.cropMaster;
    }

    public void setCropMaster(CropMaster cropMaster) {
        this.cropMaster = cropMaster;
    }

    public KmLoans cropMaster(CropMaster cropMaster) {
        this.setCropMaster(cropMaster);
        return this;
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
        return id != null && id.equals(((KmLoans) o).id);
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
            ", hector=" + getHector() +
            ", hectorMr='" + getHectorMr() + "'" +
            ", are=" + getAre() +
            ", aremr='" + getAremr() + "'" +
            ", noOfTree=" + getNoOfTree() +
            ", noOfTreeMr='" + getNoOfTreeMr() + "'" +
            ", sanctionAmt=" + getSanctionAmt() +
            ", sanctionAmtMr='" + getSanctionAmtMr() + "'" +
            ", loanAmt=" + getLoanAmt() +
            ", loanAmtMr='" + getLoanAmtMr() + "'" +
            ", receivableAmt=" + getReceivableAmt() +
            ", receivableAmtMr='" + getReceivableAmtMr() + "'" +
            ", dueAmt=" + getDueAmt() +
            ", dueAmtMr='" + getDueAmtMr() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", dueDateMr='" + getDueDateMr() + "'" +
            ", spare='" + getSpare() + "'" +
            "}";
    }
}
