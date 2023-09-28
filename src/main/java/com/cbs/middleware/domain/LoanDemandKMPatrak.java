package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A LoanDemandKMPatrak.
 */
@Entity
@Table(name = "loan_demandkmpatrak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanDemandKMPatrak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "demand_code")
    private String demandCode;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "km_date")
    private LocalDate kmDate;

    @Column(name = "shares")
    private String shares;

    @Column(name = "pid")
    private String pid;

    @Column(name = "code")
    private String code;

    @Column(name = "demand_area")
    private String demandArea;

    @Column(name = "crop_type")
    private String cropType;

    @Column(name = "total")
    private String total;

    @Column(name = "jhi_check")
    private String check;

    @Column(name = "goods")
    private String goods;

    @Column(name = "sharesn")
    private String sharesn;

    @Column(name = "hn")
    private String hn;

    @Column(name = "area")
    private String area;

    @Column(name = "h_amount")
    private String hAmount;

    @Column(name = "name")
    private String name;

    @Column(name = "khate_code")
    private String khateCode;

    @Column(name = "remaining")
    private String remaining;

    @Column(name = "arrears")
    private String arrears;

    @Column(name = "km_acceptance")
    private String kmAcceptance;

    @Column(name = "paid_date")
    private String paidDate;

    @Column(name = "km_code")
    private String kmCode;

    @Column(name = "pending_date")
    private LocalDate pendingDate;

    @Column(name = "deposite_date")
    private LocalDate depositeDate;

    @Column(name = "account_number_b")
    private String accountNumberB;

    @Column(name = "loan_due")
    private String loanDue;

    @Column(name = "arrears_b")
    private String arrearsB;

    @Column(name = "due_date_b")
    private LocalDate dueDateB;

    @Column(name = "crop_b")
    private String cropB;

    @Column(name = "km_acceptance_b")
    private String kmAcceptanceB;

    @Column(name = "km_code_b")
    private String kmCodeB;

    @Column(name = "h_agreement_number_b")
    private String hAgreementNumberB;

    @Column(name = "h_agreement_area_b")
    private String hAgreementAreaB;

    @Column(name = "h_agreement_burden_b")
    private String hAgreementBurdenB;

    @Column(name = "total_paid_b")
    private String totalPaidB;

    @Column(name = "demand_area_b")
    private String demandAreaB;

    @Column(name = "check_in_the_form_of_payment_b")
    private String checkInTheFormOfPaymentB;

    @Column(name = "shares_b")
    private String sharesB;

    @Column(name = "vasul_patra_repayment_date_b")
    private LocalDate vasulPatraRepaymentDateB;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoanDemandKMPatrak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemandCode() {
        return this.demandCode;
    }

    public LoanDemandKMPatrak demandCode(String demandCode) {
        this.setDemandCode(demandCode);
        return this;
    }

    public void setDemandCode(String demandCode) {
        this.demandCode = demandCode;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LoanDemandKMPatrak date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getKmDate() {
        return this.kmDate;
    }

    public LoanDemandKMPatrak kmDate(LocalDate kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public void setKmDate(LocalDate kmDate) {
        this.kmDate = kmDate;
    }

    public String getShares() {
        return this.shares;
    }

    public LoanDemandKMPatrak shares(String shares) {
        this.setShares(shares);
        return this;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getPid() {
        return this.pid;
    }

    public LoanDemandKMPatrak pid(String pid) {
        this.setPid(pid);
        return this;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode() {
        return this.code;
    }

    public LoanDemandKMPatrak code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDemandArea() {
        return this.demandArea;
    }

    public LoanDemandKMPatrak demandArea(String demandArea) {
        this.setDemandArea(demandArea);
        return this;
    }

    public void setDemandArea(String demandArea) {
        this.demandArea = demandArea;
    }

    public String getCropType() {
        return this.cropType;
    }

    public LoanDemandKMPatrak cropType(String cropType) {
        this.setCropType(cropType);
        return this;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getTotal() {
        return this.total;
    }

    public LoanDemandKMPatrak total(String total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCheck() {
        return this.check;
    }

    public LoanDemandKMPatrak check(String check) {
        this.setCheck(check);
        return this;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getGoods() {
        return this.goods;
    }

    public LoanDemandKMPatrak goods(String goods) {
        this.setGoods(goods);
        return this;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getSharesn() {
        return this.sharesn;
    }

    public LoanDemandKMPatrak sharesn(String sharesn) {
        this.setSharesn(sharesn);
        return this;
    }

    public void setSharesn(String sharesn) {
        this.sharesn = sharesn;
    }

    public String getHn() {
        return this.hn;
    }

    public LoanDemandKMPatrak hn(String hn) {
        this.setHn(hn);
        return this;
    }

    public void setHn(String hn) {
        this.hn = hn;
    }

    public String getArea() {
        return this.area;
    }

    public LoanDemandKMPatrak area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String gethAmount() {
        return this.hAmount;
    }

    public LoanDemandKMPatrak hAmount(String hAmount) {
        this.sethAmount(hAmount);
        return this;
    }

    public void sethAmount(String hAmount) {
        this.hAmount = hAmount;
    }

    public String getName() {
        return this.name;
    }

    public LoanDemandKMPatrak name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKhateCode() {
        return this.khateCode;
    }

    public LoanDemandKMPatrak khateCode(String khateCode) {
        this.setKhateCode(khateCode);
        return this;
    }

    public void setKhateCode(String khateCode) {
        this.khateCode = khateCode;
    }

    public String getRemaining() {
        return this.remaining;
    }

    public LoanDemandKMPatrak remaining(String remaining) {
        this.setRemaining(remaining);
        return this;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getArrears() {
        return this.arrears;
    }

    public LoanDemandKMPatrak arrears(String arrears) {
        this.setArrears(arrears);
        return this;
    }

    public void setArrears(String arrears) {
        this.arrears = arrears;
    }

    public String getKmAcceptance() {
        return this.kmAcceptance;
    }

    public LoanDemandKMPatrak kmAcceptance(String kmAcceptance) {
        this.setKmAcceptance(kmAcceptance);
        return this;
    }

    public void setKmAcceptance(String kmAcceptance) {
        this.kmAcceptance = kmAcceptance;
    }

    public String getPaidDate() {
        return this.paidDate;
    }

    public LoanDemandKMPatrak paidDate(String paidDate) {
        this.setPaidDate(paidDate);
        return this;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getKmCode() {
        return this.kmCode;
    }

    public LoanDemandKMPatrak kmCode(String kmCode) {
        this.setKmCode(kmCode);
        return this;
    }

    public void setKmCode(String kmCode) {
        this.kmCode = kmCode;
    }

    public LocalDate getPendingDate() {
        return this.pendingDate;
    }

    public LoanDemandKMPatrak pendingDate(LocalDate pendingDate) {
        this.setPendingDate(pendingDate);
        return this;
    }

    public void setPendingDate(LocalDate pendingDate) {
        this.pendingDate = pendingDate;
    }

    public LocalDate getDepositeDate() {
        return this.depositeDate;
    }

    public LoanDemandKMPatrak depositeDate(LocalDate depositeDate) {
        this.setDepositeDate(depositeDate);
        return this;
    }

    public void setDepositeDate(LocalDate depositeDate) {
        this.depositeDate = depositeDate;
    }

    public String getAccountNumberB() {
        return this.accountNumberB;
    }

    public LoanDemandKMPatrak accountNumberB(String accountNumberB) {
        this.setAccountNumberB(accountNumberB);
        return this;
    }

    public void setAccountNumberB(String accountNumberB) {
        this.accountNumberB = accountNumberB;
    }

    public String getLoanDue() {
        return this.loanDue;
    }

    public LoanDemandKMPatrak loanDue(String loanDue) {
        this.setLoanDue(loanDue);
        return this;
    }

    public void setLoanDue(String loanDue) {
        this.loanDue = loanDue;
    }

    public String getArrearsB() {
        return this.arrearsB;
    }

    public LoanDemandKMPatrak arrearsB(String arrearsB) {
        this.setArrearsB(arrearsB);
        return this;
    }

    public void setArrearsB(String arrearsB) {
        this.arrearsB = arrearsB;
    }

    public LocalDate getDueDateB() {
        return this.dueDateB;
    }

    public LoanDemandKMPatrak dueDateB(LocalDate dueDateB) {
        this.setDueDateB(dueDateB);
        return this;
    }

    public void setDueDateB(LocalDate dueDateB) {
        this.dueDateB = dueDateB;
    }

    public String getCropB() {
        return this.cropB;
    }

    public LoanDemandKMPatrak cropB(String cropB) {
        this.setCropB(cropB);
        return this;
    }

    public void setCropB(String cropB) {
        this.cropB = cropB;
    }

    public String getKmAcceptanceB() {
        return this.kmAcceptanceB;
    }

    public LoanDemandKMPatrak kmAcceptanceB(String kmAcceptanceB) {
        this.setKmAcceptanceB(kmAcceptanceB);
        return this;
    }

    public void setKmAcceptanceB(String kmAcceptanceB) {
        this.kmAcceptanceB = kmAcceptanceB;
    }

    public String getKmCodeB() {
        return this.kmCodeB;
    }

    public LoanDemandKMPatrak kmCodeB(String kmCodeB) {
        this.setKmCodeB(kmCodeB);
        return this;
    }

    public void setKmCodeB(String kmCodeB) {
        this.kmCodeB = kmCodeB;
    }

    public String gethAgreementNumberB() {
        return this.hAgreementNumberB;
    }

    public LoanDemandKMPatrak hAgreementNumberB(String hAgreementNumberB) {
        this.sethAgreementNumberB(hAgreementNumberB);
        return this;
    }

    public void sethAgreementNumberB(String hAgreementNumberB) {
        this.hAgreementNumberB = hAgreementNumberB;
    }

    public String gethAgreementAreaB() {
        return this.hAgreementAreaB;
    }

    public LoanDemandKMPatrak hAgreementAreaB(String hAgreementAreaB) {
        this.sethAgreementAreaB(hAgreementAreaB);
        return this;
    }

    public void sethAgreementAreaB(String hAgreementAreaB) {
        this.hAgreementAreaB = hAgreementAreaB;
    }

    public String gethAgreementBurdenB() {
        return this.hAgreementBurdenB;
    }

    public LoanDemandKMPatrak hAgreementBurdenB(String hAgreementBurdenB) {
        this.sethAgreementBurdenB(hAgreementBurdenB);
        return this;
    }

    public void sethAgreementBurdenB(String hAgreementBurdenB) {
        this.hAgreementBurdenB = hAgreementBurdenB;
    }

    public String getTotalPaidB() {
        return this.totalPaidB;
    }

    public LoanDemandKMPatrak totalPaidB(String totalPaidB) {
        this.setTotalPaidB(totalPaidB);
        return this;
    }

    public void setTotalPaidB(String totalPaidB) {
        this.totalPaidB = totalPaidB;
    }

    public String getDemandAreaB() {
        return this.demandAreaB;
    }

    public LoanDemandKMPatrak demandAreaB(String demandAreaB) {
        this.setDemandAreaB(demandAreaB);
        return this;
    }

    public void setDemandAreaB(String demandAreaB) {
        this.demandAreaB = demandAreaB;
    }

    public String getCheckInTheFormOfPaymentB() {
        return this.checkInTheFormOfPaymentB;
    }

    public LoanDemandKMPatrak checkInTheFormOfPaymentB(String checkInTheFormOfPaymentB) {
        this.setCheckInTheFormOfPaymentB(checkInTheFormOfPaymentB);
        return this;
    }

    public void setCheckInTheFormOfPaymentB(String checkInTheFormOfPaymentB) {
        this.checkInTheFormOfPaymentB = checkInTheFormOfPaymentB;
    }

    public String getSharesB() {
        return this.sharesB;
    }

    public LoanDemandKMPatrak sharesB(String sharesB) {
        this.setSharesB(sharesB);
        return this;
    }

    public void setSharesB(String sharesB) {
        this.sharesB = sharesB;
    }

    public LocalDate getVasulPatraRepaymentDateB() {
        return this.vasulPatraRepaymentDateB;
    }

    public LoanDemandKMPatrak vasulPatraRepaymentDateB(LocalDate vasulPatraRepaymentDateB) {
        this.setVasulPatraRepaymentDateB(vasulPatraRepaymentDateB);
        return this;
    }

    public void setVasulPatraRepaymentDateB(LocalDate vasulPatraRepaymentDateB) {
        this.vasulPatraRepaymentDateB = vasulPatraRepaymentDateB;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDemandKMPatrak)) {
            return false;
        }
        return id != null && id.equals(((LoanDemandKMPatrak) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDemandKMPatrak{" +
            "id=" + getId() +
            ", demandCode='" + getDemandCode() + "'" +
            ", date='" + getDate() + "'" +
            ", kmDate='" + getKmDate() + "'" +
            ", shares='" + getShares() + "'" +
            ", pid='" + getPid() + "'" +
            ", code='" + getCode() + "'" +
            ", demandArea='" + getDemandArea() + "'" +
            ", cropType='" + getCropType() + "'" +
            ", total='" + getTotal() + "'" +
            ", check='" + getCheck() + "'" +
            ", goods='" + getGoods() + "'" +
            ", sharesn='" + getSharesn() + "'" +
            ", hn='" + getHn() + "'" +
            ", area='" + getArea() + "'" +
            ", hAmount='" + gethAmount() + "'" +
            ", name='" + getName() + "'" +
            ", khateCode='" + getKhateCode() + "'" +
            ", remaining='" + getRemaining() + "'" +
            ", arrears='" + getArrears() + "'" +
            ", kmAcceptance='" + getKmAcceptance() + "'" +
            ", paidDate='" + getPaidDate() + "'" +
            ", kmCode='" + getKmCode() + "'" +
            ", pendingDate='" + getPendingDate() + "'" +
            ", depositeDate='" + getDepositeDate() + "'" +
            ", accountNumberB='" + getAccountNumberB() + "'" +
            ", loanDue='" + getLoanDue() + "'" +
            ", arrearsB='" + getArrearsB() + "'" +
            ", dueDateB='" + getDueDateB() + "'" +
            ", cropB='" + getCropB() + "'" +
            ", kmAcceptanceB='" + getKmAcceptanceB() + "'" +
            ", kmCodeB='" + getKmCodeB() + "'" +
            ", hAgreementNumberB='" + gethAgreementNumberB() + "'" +
            ", hAgreementAreaB='" + gethAgreementAreaB() + "'" +
            ", hAgreementBurdenB='" + gethAgreementBurdenB() + "'" +
            ", totalPaidB='" + getTotalPaidB() + "'" +
            ", demandAreaB='" + getDemandAreaB() + "'" +
            ", checkInTheFormOfPaymentB='" + getCheckInTheFormOfPaymentB() + "'" +
            ", sharesB='" + getSharesB() + "'" +
            ", vasulPatraRepaymentDateB='" + getVasulPatraRepaymentDateB() + "'" +
            "}";
    }
}
