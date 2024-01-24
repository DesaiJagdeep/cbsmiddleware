package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A KamalSociety.
 */
@Entity
@Table(name = "kamal_society")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalSociety implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pacs_number")
    private Long pacsNumber;

    @Column(name = "zindagi_date")
    private Instant zindagiDate;

    @Column(name = "zindagi_date_mr")
    private String zindagiDateMr;

    @Column(name = "village_1")
    private String village1;

    @Column(name = "village_1_mr")
    private String village1Mr;

    @Column(name = "village_2")
    private String village2;

    @Column(name = "village_2_mr")
    private String village2Mr;

    @Column(name = "village_3")
    private String village3;

    @Column(name = "village_3_mr")
    private String village3Mr;

    @Column(name = "total_land")
    private Double totalLand;

    @Column(name = "total_land_mr")
    private String totalLandMr;

    @Column(name = "total_mem")
    private Double totalMem;

    @Column(name = "total_mem_mr")
    private String totalMemMr;

    @Column(name = "total_non_mem")
    private Double totalNonMem;

    @Column(name = "total_non_mem_mr")
    private String totalNonMemMr;

    @Column(name = "total_g_mem")
    private Double totalGMem;

    @Column(name = "total_g_mem_mr")
    private String totalGMemMr;

    @Column(name = "mem_loan")
    private Double memLoan;

    @Column(name = "mem_loan_mr")
    private String memLoanMr;

    @Column(name = "mem_due")
    private Double memDue;

    @Column(name = "mem_due_mr")
    private String memDueMr;

    @Column(name = "mem_dueper")
    private Double memDueper;

    @Column(name = "mem_dueper_mr")
    private String memDueperMr;

    @Column(name = "mem_vasulpatra")
    private Double memVasulpatra;

    @Column(name = "mem_vasulpatra_mr")
    private String memVasulpatraMr;

    @Column(name = "mem_vasul")
    private Double memVasul;

    @Column(name = "mem_vasul_mr")
    private String memVasulMr;

    @Column(name = "mem_vasul_per")
    private Double memVasulPer;

    @Column(name = "mem_vasul_per_mr")
    private String memVasulPerMr;

    @Column(name = "bank_loan")
    private Double bankLoan;

    @Column(name = "bank_loan_mr")
    private String bankLoanMr;

    @Column(name = "bank_due")
    private Double bankDue;

    @Column(name = "bank_due_mr")
    private String bankDueMr;

    @Column(name = "bank_dueper")
    private Double bankDueper;

    @Column(name = "bank_dueper_mr")
    private String bankDueperMr;

    @Column(name = "bank_vasulpatra")
    private Double bankVasulpatra;

    @Column(name = "bank_vasulpatra_mr")
    private String bankVasulpatraMr;

    @Column(name = "bank_vasul")
    private Double bankVasul;

    @Column(name = "bank_vasul_mr")
    private String bankVasulMr;

    @Column(name = "bank_vasul_per")
    private Double bankVasulPer;

    @Column(name = "bank_vasul_per_mr")
    private String bankVasulPerMr;

    @Column(name = "share_capital")
    private Double shareCapital;

    @Column(name = "share_capital_mr")
    private String shareCapitalMr;

    @Column(name = "share")
    private Double share;

    @Column(name = "share_mr")
    private String shareMr;

    @Column(name = "funds")
    private Double funds;

    @Column(name = "funds_mr")
    private String fundsMr;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "deposit_mr")
    private String depositMr;

    @Column(name = "payable")
    private Double payable;

    @Column(name = "payable_mr")
    private String payableMr;

    @Column(name = "profit")
    private Double profit;

    @Column(name = "profit_mr")
    private String profitMr;

    @Column(name = "cash_in_hand")
    private Double cashInHand;

    @Column(name = "cash_in_hand_mr")
    private String cashInHandMr;

    @Column(name = "investment")
    private Double investment;

    @Column(name = "investment_mr")
    private String investmentMr;

    @Column(name = "dead_stock")
    private Double deadStock;

    @Column(name = "dead_stock_mr")
    private String deadStockMr;

    @Column(name = "other_pay")
    private Double otherPay;

    @Column(name = "other_pay_mr")
    private String otherPayMr;

    @Column(name = "loss")
    private Double loss;

    @Column(name = "loss_mr")
    private String lossMr;

    @OneToMany(mappedBy = "kamalSociety")
    @JsonIgnoreProperties(value = { "farmerTypeMaster", "cropHangam", "cropMaster", "kamalSociety" }, allowSetters = true)
    private Set<KamalCrop> kamalCrops = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KamalSociety id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacsNumber() {
        return this.pacsNumber;
    }

    public KamalSociety pacsNumber(Long pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(Long pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public Instant getZindagiDate() {
        return this.zindagiDate;
    }

    public KamalSociety zindagiDate(Instant zindagiDate) {
        this.setZindagiDate(zindagiDate);
        return this;
    }

    public void setZindagiDate(Instant zindagiDate) {
        this.zindagiDate = zindagiDate;
    }

    public String getZindagiDateMr() {
        return this.zindagiDateMr;
    }

    public KamalSociety zindagiDateMr(String zindagiDateMr) {
        this.setZindagiDateMr(zindagiDateMr);
        return this;
    }

    public void setZindagiDateMr(String zindagiDateMr) {
        this.zindagiDateMr = zindagiDateMr;
    }

    public String getVillage1() {
        return this.village1;
    }

    public KamalSociety village1(String village1) {
        this.setVillage1(village1);
        return this;
    }

    public void setVillage1(String village1) {
        this.village1 = village1;
    }

    public String getVillage1Mr() {
        return this.village1Mr;
    }

    public KamalSociety village1Mr(String village1Mr) {
        this.setVillage1Mr(village1Mr);
        return this;
    }

    public void setVillage1Mr(String village1Mr) {
        this.village1Mr = village1Mr;
    }

    public String getVillage2() {
        return this.village2;
    }

    public KamalSociety village2(String village2) {
        this.setVillage2(village2);
        return this;
    }

    public void setVillage2(String village2) {
        this.village2 = village2;
    }

    public String getVillage2Mr() {
        return this.village2Mr;
    }

    public KamalSociety village2Mr(String village2Mr) {
        this.setVillage2Mr(village2Mr);
        return this;
    }

    public void setVillage2Mr(String village2Mr) {
        this.village2Mr = village2Mr;
    }

    public String getVillage3() {
        return this.village3;
    }

    public KamalSociety village3(String village3) {
        this.setVillage3(village3);
        return this;
    }

    public void setVillage3(String village3) {
        this.village3 = village3;
    }

    public String getVillage3Mr() {
        return this.village3Mr;
    }

    public KamalSociety village3Mr(String village3Mr) {
        this.setVillage3Mr(village3Mr);
        return this;
    }

    public void setVillage3Mr(String village3Mr) {
        this.village3Mr = village3Mr;
    }

    public Double getTotalLand() {
        return this.totalLand;
    }

    public KamalSociety totalLand(Double totalLand) {
        this.setTotalLand(totalLand);
        return this;
    }

    public void setTotalLand(Double totalLand) {
        this.totalLand = totalLand;
    }

    public String getTotalLandMr() {
        return this.totalLandMr;
    }

    public KamalSociety totalLandMr(String totalLandMr) {
        this.setTotalLandMr(totalLandMr);
        return this;
    }

    public void setTotalLandMr(String totalLandMr) {
        this.totalLandMr = totalLandMr;
    }

    public Double getTotalMem() {
        return this.totalMem;
    }

    public KamalSociety totalMem(Double totalMem) {
        this.setTotalMem(totalMem);
        return this;
    }

    public void setTotalMem(Double totalMem) {
        this.totalMem = totalMem;
    }

    public String getTotalMemMr() {
        return this.totalMemMr;
    }

    public KamalSociety totalMemMr(String totalMemMr) {
        this.setTotalMemMr(totalMemMr);
        return this;
    }

    public void setTotalMemMr(String totalMemMr) {
        this.totalMemMr = totalMemMr;
    }

    public Double getTotalNonMem() {
        return this.totalNonMem;
    }

    public KamalSociety totalNonMem(Double totalNonMem) {
        this.setTotalNonMem(totalNonMem);
        return this;
    }

    public void setTotalNonMem(Double totalNonMem) {
        this.totalNonMem = totalNonMem;
    }

    public String getTotalNonMemMr() {
        return this.totalNonMemMr;
    }

    public KamalSociety totalNonMemMr(String totalNonMemMr) {
        this.setTotalNonMemMr(totalNonMemMr);
        return this;
    }

    public void setTotalNonMemMr(String totalNonMemMr) {
        this.totalNonMemMr = totalNonMemMr;
    }

    public Double getTotalGMem() {
        return this.totalGMem;
    }

    public KamalSociety totalGMem(Double totalGMem) {
        this.setTotalGMem(totalGMem);
        return this;
    }

    public void setTotalGMem(Double totalGMem) {
        this.totalGMem = totalGMem;
    }

    public String getTotalGMemMr() {
        return this.totalGMemMr;
    }

    public KamalSociety totalGMemMr(String totalGMemMr) {
        this.setTotalGMemMr(totalGMemMr);
        return this;
    }

    public void setTotalGMemMr(String totalGMemMr) {
        this.totalGMemMr = totalGMemMr;
    }

    public Double getMemLoan() {
        return this.memLoan;
    }

    public KamalSociety memLoan(Double memLoan) {
        this.setMemLoan(memLoan);
        return this;
    }

    public void setMemLoan(Double memLoan) {
        this.memLoan = memLoan;
    }

    public String getMemLoanMr() {
        return this.memLoanMr;
    }

    public KamalSociety memLoanMr(String memLoanMr) {
        this.setMemLoanMr(memLoanMr);
        return this;
    }

    public void setMemLoanMr(String memLoanMr) {
        this.memLoanMr = memLoanMr;
    }

    public Double getMemDue() {
        return this.memDue;
    }

    public KamalSociety memDue(Double memDue) {
        this.setMemDue(memDue);
        return this;
    }

    public void setMemDue(Double memDue) {
        this.memDue = memDue;
    }

    public String getMemDueMr() {
        return this.memDueMr;
    }

    public KamalSociety memDueMr(String memDueMr) {
        this.setMemDueMr(memDueMr);
        return this;
    }

    public void setMemDueMr(String memDueMr) {
        this.memDueMr = memDueMr;
    }

    public Double getMemDueper() {
        return this.memDueper;
    }

    public KamalSociety memDueper(Double memDueper) {
        this.setMemDueper(memDueper);
        return this;
    }

    public void setMemDueper(Double memDueper) {
        this.memDueper = memDueper;
    }

    public String getMemDueperMr() {
        return this.memDueperMr;
    }

    public KamalSociety memDueperMr(String memDueperMr) {
        this.setMemDueperMr(memDueperMr);
        return this;
    }

    public void setMemDueperMr(String memDueperMr) {
        this.memDueperMr = memDueperMr;
    }

    public Double getMemVasulpatra() {
        return this.memVasulpatra;
    }

    public KamalSociety memVasulpatra(Double memVasulpatra) {
        this.setMemVasulpatra(memVasulpatra);
        return this;
    }

    public void setMemVasulpatra(Double memVasulpatra) {
        this.memVasulpatra = memVasulpatra;
    }

    public String getMemVasulpatraMr() {
        return this.memVasulpatraMr;
    }

    public KamalSociety memVasulpatraMr(String memVasulpatraMr) {
        this.setMemVasulpatraMr(memVasulpatraMr);
        return this;
    }

    public void setMemVasulpatraMr(String memVasulpatraMr) {
        this.memVasulpatraMr = memVasulpatraMr;
    }

    public Double getMemVasul() {
        return this.memVasul;
    }

    public KamalSociety memVasul(Double memVasul) {
        this.setMemVasul(memVasul);
        return this;
    }

    public void setMemVasul(Double memVasul) {
        this.memVasul = memVasul;
    }

    public String getMemVasulMr() {
        return this.memVasulMr;
    }

    public KamalSociety memVasulMr(String memVasulMr) {
        this.setMemVasulMr(memVasulMr);
        return this;
    }

    public void setMemVasulMr(String memVasulMr) {
        this.memVasulMr = memVasulMr;
    }

    public Double getMemVasulPer() {
        return this.memVasulPer;
    }

    public KamalSociety memVasulPer(Double memVasulPer) {
        this.setMemVasulPer(memVasulPer);
        return this;
    }

    public void setMemVasulPer(Double memVasulPer) {
        this.memVasulPer = memVasulPer;
    }

    public String getMemVasulPerMr() {
        return this.memVasulPerMr;
    }

    public KamalSociety memVasulPerMr(String memVasulPerMr) {
        this.setMemVasulPerMr(memVasulPerMr);
        return this;
    }

    public void setMemVasulPerMr(String memVasulPerMr) {
        this.memVasulPerMr = memVasulPerMr;
    }

    public Double getBankLoan() {
        return this.bankLoan;
    }

    public KamalSociety bankLoan(Double bankLoan) {
        this.setBankLoan(bankLoan);
        return this;
    }

    public void setBankLoan(Double bankLoan) {
        this.bankLoan = bankLoan;
    }

    public String getBankLoanMr() {
        return this.bankLoanMr;
    }

    public KamalSociety bankLoanMr(String bankLoanMr) {
        this.setBankLoanMr(bankLoanMr);
        return this;
    }

    public void setBankLoanMr(String bankLoanMr) {
        this.bankLoanMr = bankLoanMr;
    }

    public Double getBankDue() {
        return this.bankDue;
    }

    public KamalSociety bankDue(Double bankDue) {
        this.setBankDue(bankDue);
        return this;
    }

    public void setBankDue(Double bankDue) {
        this.bankDue = bankDue;
    }

    public String getBankDueMr() {
        return this.bankDueMr;
    }

    public KamalSociety bankDueMr(String bankDueMr) {
        this.setBankDueMr(bankDueMr);
        return this;
    }

    public void setBankDueMr(String bankDueMr) {
        this.bankDueMr = bankDueMr;
    }

    public Double getBankDueper() {
        return this.bankDueper;
    }

    public KamalSociety bankDueper(Double bankDueper) {
        this.setBankDueper(bankDueper);
        return this;
    }

    public void setBankDueper(Double bankDueper) {
        this.bankDueper = bankDueper;
    }

    public String getBankDueperMr() {
        return this.bankDueperMr;
    }

    public KamalSociety bankDueperMr(String bankDueperMr) {
        this.setBankDueperMr(bankDueperMr);
        return this;
    }

    public void setBankDueperMr(String bankDueperMr) {
        this.bankDueperMr = bankDueperMr;
    }

    public Double getBankVasulpatra() {
        return this.bankVasulpatra;
    }

    public KamalSociety bankVasulpatra(Double bankVasulpatra) {
        this.setBankVasulpatra(bankVasulpatra);
        return this;
    }

    public void setBankVasulpatra(Double bankVasulpatra) {
        this.bankVasulpatra = bankVasulpatra;
    }

    public String getBankVasulpatraMr() {
        return this.bankVasulpatraMr;
    }

    public KamalSociety bankVasulpatraMr(String bankVasulpatraMr) {
        this.setBankVasulpatraMr(bankVasulpatraMr);
        return this;
    }

    public void setBankVasulpatraMr(String bankVasulpatraMr) {
        this.bankVasulpatraMr = bankVasulpatraMr;
    }

    public Double getBankVasul() {
        return this.bankVasul;
    }

    public KamalSociety bankVasul(Double bankVasul) {
        this.setBankVasul(bankVasul);
        return this;
    }

    public void setBankVasul(Double bankVasul) {
        this.bankVasul = bankVasul;
    }

    public String getBankVasulMr() {
        return this.bankVasulMr;
    }

    public KamalSociety bankVasulMr(String bankVasulMr) {
        this.setBankVasulMr(bankVasulMr);
        return this;
    }

    public void setBankVasulMr(String bankVasulMr) {
        this.bankVasulMr = bankVasulMr;
    }

    public Double getBankVasulPer() {
        return this.bankVasulPer;
    }

    public KamalSociety bankVasulPer(Double bankVasulPer) {
        this.setBankVasulPer(bankVasulPer);
        return this;
    }

    public void setBankVasulPer(Double bankVasulPer) {
        this.bankVasulPer = bankVasulPer;
    }

    public String getBankVasulPerMr() {
        return this.bankVasulPerMr;
    }

    public KamalSociety bankVasulPerMr(String bankVasulPerMr) {
        this.setBankVasulPerMr(bankVasulPerMr);
        return this;
    }

    public void setBankVasulPerMr(String bankVasulPerMr) {
        this.bankVasulPerMr = bankVasulPerMr;
    }

    public Double getShareCapital() {
        return this.shareCapital;
    }

    public KamalSociety shareCapital(Double shareCapital) {
        this.setShareCapital(shareCapital);
        return this;
    }

    public void setShareCapital(Double shareCapital) {
        this.shareCapital = shareCapital;
    }

    public String getShareCapitalMr() {
        return this.shareCapitalMr;
    }

    public KamalSociety shareCapitalMr(String shareCapitalMr) {
        this.setShareCapitalMr(shareCapitalMr);
        return this;
    }

    public void setShareCapitalMr(String shareCapitalMr) {
        this.shareCapitalMr = shareCapitalMr;
    }

    public Double getShare() {
        return this.share;
    }

    public KamalSociety share(Double share) {
        this.setShare(share);
        return this;
    }

    public void setShare(Double share) {
        this.share = share;
    }

    public String getShareMr() {
        return this.shareMr;
    }

    public KamalSociety shareMr(String shareMr) {
        this.setShareMr(shareMr);
        return this;
    }

    public void setShareMr(String shareMr) {
        this.shareMr = shareMr;
    }

    public Double getFunds() {
        return this.funds;
    }

    public KamalSociety funds(Double funds) {
        this.setFunds(funds);
        return this;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    public String getFundsMr() {
        return this.fundsMr;
    }

    public KamalSociety fundsMr(String fundsMr) {
        this.setFundsMr(fundsMr);
        return this;
    }

    public void setFundsMr(String fundsMr) {
        this.fundsMr = fundsMr;
    }

    public Double getDeposit() {
        return this.deposit;
    }

    public KamalSociety deposit(Double deposit) {
        this.setDeposit(deposit);
        return this;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getDepositMr() {
        return this.depositMr;
    }

    public KamalSociety depositMr(String depositMr) {
        this.setDepositMr(depositMr);
        return this;
    }

    public void setDepositMr(String depositMr) {
        this.depositMr = depositMr;
    }

    public Double getPayable() {
        return this.payable;
    }

    public KamalSociety payable(Double payable) {
        this.setPayable(payable);
        return this;
    }

    public void setPayable(Double payable) {
        this.payable = payable;
    }

    public String getPayableMr() {
        return this.payableMr;
    }

    public KamalSociety payableMr(String payableMr) {
        this.setPayableMr(payableMr);
        return this;
    }

    public void setPayableMr(String payableMr) {
        this.payableMr = payableMr;
    }

    public Double getProfit() {
        return this.profit;
    }

    public KamalSociety profit(Double profit) {
        this.setProfit(profit);
        return this;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getProfitMr() {
        return this.profitMr;
    }

    public KamalSociety profitMr(String profitMr) {
        this.setProfitMr(profitMr);
        return this;
    }

    public void setProfitMr(String profitMr) {
        this.profitMr = profitMr;
    }

    public Double getCashInHand() {
        return this.cashInHand;
    }

    public KamalSociety cashInHand(Double cashInHand) {
        this.setCashInHand(cashInHand);
        return this;
    }

    public void setCashInHand(Double cashInHand) {
        this.cashInHand = cashInHand;
    }

    public String getCashInHandMr() {
        return this.cashInHandMr;
    }

    public KamalSociety cashInHandMr(String cashInHandMr) {
        this.setCashInHandMr(cashInHandMr);
        return this;
    }

    public void setCashInHandMr(String cashInHandMr) {
        this.cashInHandMr = cashInHandMr;
    }

    public Double getInvestment() {
        return this.investment;
    }

    public KamalSociety investment(Double investment) {
        this.setInvestment(investment);
        return this;
    }

    public void setInvestment(Double investment) {
        this.investment = investment;
    }

    public String getInvestmentMr() {
        return this.investmentMr;
    }

    public KamalSociety investmentMr(String investmentMr) {
        this.setInvestmentMr(investmentMr);
        return this;
    }

    public void setInvestmentMr(String investmentMr) {
        this.investmentMr = investmentMr;
    }

    public Double getDeadStock() {
        return this.deadStock;
    }

    public KamalSociety deadStock(Double deadStock) {
        this.setDeadStock(deadStock);
        return this;
    }

    public void setDeadStock(Double deadStock) {
        this.deadStock = deadStock;
    }

    public String getDeadStockMr() {
        return this.deadStockMr;
    }

    public KamalSociety deadStockMr(String deadStockMr) {
        this.setDeadStockMr(deadStockMr);
        return this;
    }

    public void setDeadStockMr(String deadStockMr) {
        this.deadStockMr = deadStockMr;
    }

    public Double getOtherPay() {
        return this.otherPay;
    }

    public KamalSociety otherPay(Double otherPay) {
        this.setOtherPay(otherPay);
        return this;
    }

    public void setOtherPay(Double otherPay) {
        this.otherPay = otherPay;
    }

    public String getOtherPayMr() {
        return this.otherPayMr;
    }

    public KamalSociety otherPayMr(String otherPayMr) {
        this.setOtherPayMr(otherPayMr);
        return this;
    }

    public void setOtherPayMr(String otherPayMr) {
        this.otherPayMr = otherPayMr;
    }

    public Double getLoss() {
        return this.loss;
    }

    public KamalSociety loss(Double loss) {
        this.setLoss(loss);
        return this;
    }

    public void setLoss(Double loss) {
        this.loss = loss;
    }

    public String getLossMr() {
        return this.lossMr;
    }

    public KamalSociety lossMr(String lossMr) {
        this.setLossMr(lossMr);
        return this;
    }

    public void setLossMr(String lossMr) {
        this.lossMr = lossMr;
    }

    public Set<KamalCrop> getKamalCrops() {
        return this.kamalCrops;
    }

    public void setKamalCrops(Set<KamalCrop> kamalCrops) {
        if (this.kamalCrops != null) {
            this.kamalCrops.forEach(i -> i.setKamalSociety(null));
        }
        if (kamalCrops != null) {
            kamalCrops.forEach(i -> i.setKamalSociety(this));
        }
        this.kamalCrops = kamalCrops;
    }

    public KamalSociety kamalCrops(Set<KamalCrop> kamalCrops) {
        this.setKamalCrops(kamalCrops);
        return this;
    }

    public KamalSociety addKamalCrop(KamalCrop kamalCrop) {
        this.kamalCrops.add(kamalCrop);
        kamalCrop.setKamalSociety(this);
        return this;
    }

    public KamalSociety removeKamalCrop(KamalCrop kamalCrop) {
        this.kamalCrops.remove(kamalCrop);
        kamalCrop.setKamalSociety(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KamalSociety)) {
            return false;
        }
        return id != null && id.equals(((KamalSociety) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalSociety{" +
            "id=" + getId() +
            ", pacsNumber=" + getPacsNumber() +
            ", zindagiDate='" + getZindagiDate() + "'" +
            ", zindagiDateMr='" + getZindagiDateMr() + "'" +
            ", village1='" + getVillage1() + "'" +
            ", village1Mr='" + getVillage1Mr() + "'" +
            ", village2='" + getVillage2() + "'" +
            ", village2Mr='" + getVillage2Mr() + "'" +
            ", village3='" + getVillage3() + "'" +
            ", village3Mr='" + getVillage3Mr() + "'" +
            ", totalLand=" + getTotalLand() +
            ", totalLandMr='" + getTotalLandMr() + "'" +
            ", totalMem=" + getTotalMem() +
            ", totalMemMr='" + getTotalMemMr() + "'" +
            ", totalNonMem=" + getTotalNonMem() +
            ", totalNonMemMr='" + getTotalNonMemMr() + "'" +
            ", totalGMem=" + getTotalGMem() +
            ", totalGMemMr='" + getTotalGMemMr() + "'" +
            ", memLoan=" + getMemLoan() +
            ", memLoanMr='" + getMemLoanMr() + "'" +
            ", memDue=" + getMemDue() +
            ", memDueMr='" + getMemDueMr() + "'" +
            ", memDueper=" + getMemDueper() +
            ", memDueperMr='" + getMemDueperMr() + "'" +
            ", memVasulpatra=" + getMemVasulpatra() +
            ", memVasulpatraMr='" + getMemVasulpatraMr() + "'" +
            ", memVasul=" + getMemVasul() +
            ", memVasulMr='" + getMemVasulMr() + "'" +
            ", memVasulPer=" + getMemVasulPer() +
            ", memVasulPerMr='" + getMemVasulPerMr() + "'" +
            ", bankLoan=" + getBankLoan() +
            ", bankLoanMr='" + getBankLoanMr() + "'" +
            ", bankDue=" + getBankDue() +
            ", bankDueMr='" + getBankDueMr() + "'" +
            ", bankDueper=" + getBankDueper() +
            ", bankDueperMr='" + getBankDueperMr() + "'" +
            ", bankVasulpatra=" + getBankVasulpatra() +
            ", bankVasulpatraMr='" + getBankVasulpatraMr() + "'" +
            ", bankVasul=" + getBankVasul() +
            ", bankVasulMr='" + getBankVasulMr() + "'" +
            ", bankVasulPer=" + getBankVasulPer() +
            ", bankVasulPerMr='" + getBankVasulPerMr() + "'" +
            ", shareCapital=" + getShareCapital() +
            ", shareCapitalMr='" + getShareCapitalMr() + "'" +
            ", share=" + getShare() +
            ", shareMr='" + getShareMr() + "'" +
            ", funds=" + getFunds() +
            ", fundsMr='" + getFundsMr() + "'" +
            ", deposit=" + getDeposit() +
            ", depositMr='" + getDepositMr() + "'" +
            ", payable=" + getPayable() +
            ", payableMr='" + getPayableMr() + "'" +
            ", profit=" + getProfit() +
            ", profitMr='" + getProfitMr() + "'" +
            ", cashInHand=" + getCashInHand() +
            ", cashInHandMr='" + getCashInHandMr() + "'" +
            ", investment=" + getInvestment() +
            ", investmentMr='" + getInvestmentMr() + "'" +
            ", deadStock=" + getDeadStock() +
            ", deadStockMr='" + getDeadStockMr() + "'" +
            ", otherPay=" + getOtherPay() +
            ", otherPayMr='" + getOtherPayMr() + "'" +
            ", loss=" + getLoss() +
            ", lossMr='" + getLossMr() + "'" +
            "}";
    }
}
