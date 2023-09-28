package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.LoanDemandKMPatrak} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.LoanDemandKMPatrakResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-demand-km-patraks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanDemandKMPatrakCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter demandCode;

    private LocalDateFilter date;

    private LocalDateFilter kmDate;

    private StringFilter shares;

    private StringFilter pid;

    private StringFilter code;

    private StringFilter demandArea;

    private StringFilter cropType;

    private StringFilter total;

    private StringFilter check;

    private StringFilter goods;

    private StringFilter sharesn;

    private StringFilter hn;

    private StringFilter area;

    private StringFilter hAmount;

    private StringFilter name;

    private StringFilter khateCode;

    private StringFilter remaining;

    private StringFilter arrears;

    private StringFilter kmAcceptance;

    private StringFilter paidDate;

    private StringFilter kmCode;

    private LocalDateFilter pendingDate;

    private LocalDateFilter depositeDate;

    private StringFilter accountNumberB;

    private StringFilter loanDue;

    private StringFilter arrearsB;

    private LocalDateFilter dueDateB;

    private StringFilter cropB;

    private StringFilter kmAcceptanceB;

    private StringFilter kmCodeB;

    private StringFilter hAgreementNumberB;

    private StringFilter hAgreementAreaB;

    private StringFilter hAgreementBurdenB;

    private StringFilter totalPaidB;

    private StringFilter demandAreaB;

    private StringFilter checkInTheFormOfPaymentB;

    private StringFilter sharesB;

    private LocalDateFilter vasulPatraRepaymentDateB;

    private Boolean distinct;

    public LoanDemandKMPatrakCriteria() {}

    public LoanDemandKMPatrakCriteria(LoanDemandKMPatrakCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.demandCode = other.demandCode == null ? null : other.demandCode.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.kmDate = other.kmDate == null ? null : other.kmDate.copy();
        this.shares = other.shares == null ? null : other.shares.copy();
        this.pid = other.pid == null ? null : other.pid.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.demandArea = other.demandArea == null ? null : other.demandArea.copy();
        this.cropType = other.cropType == null ? null : other.cropType.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.check = other.check == null ? null : other.check.copy();
        this.goods = other.goods == null ? null : other.goods.copy();
        this.sharesn = other.sharesn == null ? null : other.sharesn.copy();
        this.hn = other.hn == null ? null : other.hn.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.hAmount = other.hAmount == null ? null : other.hAmount.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.khateCode = other.khateCode == null ? null : other.khateCode.copy();
        this.remaining = other.remaining == null ? null : other.remaining.copy();
        this.arrears = other.arrears == null ? null : other.arrears.copy();
        this.kmAcceptance = other.kmAcceptance == null ? null : other.kmAcceptance.copy();
        this.paidDate = other.paidDate == null ? null : other.paidDate.copy();
        this.kmCode = other.kmCode == null ? null : other.kmCode.copy();
        this.pendingDate = other.pendingDate == null ? null : other.pendingDate.copy();
        this.depositeDate = other.depositeDate == null ? null : other.depositeDate.copy();
        this.accountNumberB = other.accountNumberB == null ? null : other.accountNumberB.copy();
        this.loanDue = other.loanDue == null ? null : other.loanDue.copy();
        this.arrearsB = other.arrearsB == null ? null : other.arrearsB.copy();
        this.dueDateB = other.dueDateB == null ? null : other.dueDateB.copy();
        this.cropB = other.cropB == null ? null : other.cropB.copy();
        this.kmAcceptanceB = other.kmAcceptanceB == null ? null : other.kmAcceptanceB.copy();
        this.kmCodeB = other.kmCodeB == null ? null : other.kmCodeB.copy();
        this.hAgreementNumberB = other.hAgreementNumberB == null ? null : other.hAgreementNumberB.copy();
        this.hAgreementAreaB = other.hAgreementAreaB == null ? null : other.hAgreementAreaB.copy();
        this.hAgreementBurdenB = other.hAgreementBurdenB == null ? null : other.hAgreementBurdenB.copy();
        this.totalPaidB = other.totalPaidB == null ? null : other.totalPaidB.copy();
        this.demandAreaB = other.demandAreaB == null ? null : other.demandAreaB.copy();
        this.checkInTheFormOfPaymentB = other.checkInTheFormOfPaymentB == null ? null : other.checkInTheFormOfPaymentB.copy();
        this.sharesB = other.sharesB == null ? null : other.sharesB.copy();
        this.vasulPatraRepaymentDateB = other.vasulPatraRepaymentDateB == null ? null : other.vasulPatraRepaymentDateB.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanDemandKMPatrakCriteria copy() {
        return new LoanDemandKMPatrakCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDemandCode() {
        return demandCode;
    }

    public StringFilter demandCode() {
        if (demandCode == null) {
            demandCode = new StringFilter();
        }
        return demandCode;
    }

    public void setDemandCode(StringFilter demandCode) {
        this.demandCode = demandCode;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LocalDateFilter getKmDate() {
        return kmDate;
    }

    public LocalDateFilter kmDate() {
        if (kmDate == null) {
            kmDate = new LocalDateFilter();
        }
        return kmDate;
    }

    public void setKmDate(LocalDateFilter kmDate) {
        this.kmDate = kmDate;
    }

    public StringFilter getShares() {
        return shares;
    }

    public StringFilter shares() {
        if (shares == null) {
            shares = new StringFilter();
        }
        return shares;
    }

    public void setShares(StringFilter shares) {
        this.shares = shares;
    }

    public StringFilter getPid() {
        return pid;
    }

    public StringFilter pid() {
        if (pid == null) {
            pid = new StringFilter();
        }
        return pid;
    }

    public void setPid(StringFilter pid) {
        this.pid = pid;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getDemandArea() {
        return demandArea;
    }

    public StringFilter demandArea() {
        if (demandArea == null) {
            demandArea = new StringFilter();
        }
        return demandArea;
    }

    public void setDemandArea(StringFilter demandArea) {
        this.demandArea = demandArea;
    }

    public StringFilter getCropType() {
        return cropType;
    }

    public StringFilter cropType() {
        if (cropType == null) {
            cropType = new StringFilter();
        }
        return cropType;
    }

    public void setCropType(StringFilter cropType) {
        this.cropType = cropType;
    }

    public StringFilter getTotal() {
        return total;
    }

    public StringFilter total() {
        if (total == null) {
            total = new StringFilter();
        }
        return total;
    }

    public void setTotal(StringFilter total) {
        this.total = total;
    }

    public StringFilter getCheck() {
        return check;
    }

    public StringFilter check() {
        if (check == null) {
            check = new StringFilter();
        }
        return check;
    }

    public void setCheck(StringFilter check) {
        this.check = check;
    }

    public StringFilter getGoods() {
        return goods;
    }

    public StringFilter goods() {
        if (goods == null) {
            goods = new StringFilter();
        }
        return goods;
    }

    public void setGoods(StringFilter goods) {
        this.goods = goods;
    }

    public StringFilter getSharesn() {
        return sharesn;
    }

    public StringFilter sharesn() {
        if (sharesn == null) {
            sharesn = new StringFilter();
        }
        return sharesn;
    }

    public void setSharesn(StringFilter sharesn) {
        this.sharesn = sharesn;
    }

    public StringFilter getHn() {
        return hn;
    }

    public StringFilter hn() {
        if (hn == null) {
            hn = new StringFilter();
        }
        return hn;
    }

    public void setHn(StringFilter hn) {
        this.hn = hn;
    }

    public StringFilter getArea() {
        return area;
    }

    public StringFilter area() {
        if (area == null) {
            area = new StringFilter();
        }
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
    }

    public StringFilter gethAmount() {
        return hAmount;
    }

    public StringFilter hAmount() {
        if (hAmount == null) {
            hAmount = new StringFilter();
        }
        return hAmount;
    }

    public void sethAmount(StringFilter hAmount) {
        this.hAmount = hAmount;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getKhateCode() {
        return khateCode;
    }

    public StringFilter khateCode() {
        if (khateCode == null) {
            khateCode = new StringFilter();
        }
        return khateCode;
    }

    public void setKhateCode(StringFilter khateCode) {
        this.khateCode = khateCode;
    }

    public StringFilter getRemaining() {
        return remaining;
    }

    public StringFilter remaining() {
        if (remaining == null) {
            remaining = new StringFilter();
        }
        return remaining;
    }

    public void setRemaining(StringFilter remaining) {
        this.remaining = remaining;
    }

    public StringFilter getArrears() {
        return arrears;
    }

    public StringFilter arrears() {
        if (arrears == null) {
            arrears = new StringFilter();
        }
        return arrears;
    }

    public void setArrears(StringFilter arrears) {
        this.arrears = arrears;
    }

    public StringFilter getKmAcceptance() {
        return kmAcceptance;
    }

    public StringFilter kmAcceptance() {
        if (kmAcceptance == null) {
            kmAcceptance = new StringFilter();
        }
        return kmAcceptance;
    }

    public void setKmAcceptance(StringFilter kmAcceptance) {
        this.kmAcceptance = kmAcceptance;
    }

    public StringFilter getPaidDate() {
        return paidDate;
    }

    public StringFilter paidDate() {
        if (paidDate == null) {
            paidDate = new StringFilter();
        }
        return paidDate;
    }

    public void setPaidDate(StringFilter paidDate) {
        this.paidDate = paidDate;
    }

    public StringFilter getKmCode() {
        return kmCode;
    }

    public StringFilter kmCode() {
        if (kmCode == null) {
            kmCode = new StringFilter();
        }
        return kmCode;
    }

    public void setKmCode(StringFilter kmCode) {
        this.kmCode = kmCode;
    }

    public LocalDateFilter getPendingDate() {
        return pendingDate;
    }

    public LocalDateFilter pendingDate() {
        if (pendingDate == null) {
            pendingDate = new LocalDateFilter();
        }
        return pendingDate;
    }

    public void setPendingDate(LocalDateFilter pendingDate) {
        this.pendingDate = pendingDate;
    }

    public LocalDateFilter getDepositeDate() {
        return depositeDate;
    }

    public LocalDateFilter depositeDate() {
        if (depositeDate == null) {
            depositeDate = new LocalDateFilter();
        }
        return depositeDate;
    }

    public void setDepositeDate(LocalDateFilter depositeDate) {
        this.depositeDate = depositeDate;
    }

    public StringFilter getAccountNumberB() {
        return accountNumberB;
    }

    public StringFilter accountNumberB() {
        if (accountNumberB == null) {
            accountNumberB = new StringFilter();
        }
        return accountNumberB;
    }

    public void setAccountNumberB(StringFilter accountNumberB) {
        this.accountNumberB = accountNumberB;
    }

    public StringFilter getLoanDue() {
        return loanDue;
    }

    public StringFilter loanDue() {
        if (loanDue == null) {
            loanDue = new StringFilter();
        }
        return loanDue;
    }

    public void setLoanDue(StringFilter loanDue) {
        this.loanDue = loanDue;
    }

    public StringFilter getArrearsB() {
        return arrearsB;
    }

    public StringFilter arrearsB() {
        if (arrearsB == null) {
            arrearsB = new StringFilter();
        }
        return arrearsB;
    }

    public void setArrearsB(StringFilter arrearsB) {
        this.arrearsB = arrearsB;
    }

    public LocalDateFilter getDueDateB() {
        return dueDateB;
    }

    public LocalDateFilter dueDateB() {
        if (dueDateB == null) {
            dueDateB = new LocalDateFilter();
        }
        return dueDateB;
    }

    public void setDueDateB(LocalDateFilter dueDateB) {
        this.dueDateB = dueDateB;
    }

    public StringFilter getCropB() {
        return cropB;
    }

    public StringFilter cropB() {
        if (cropB == null) {
            cropB = new StringFilter();
        }
        return cropB;
    }

    public void setCropB(StringFilter cropB) {
        this.cropB = cropB;
    }

    public StringFilter getKmAcceptanceB() {
        return kmAcceptanceB;
    }

    public StringFilter kmAcceptanceB() {
        if (kmAcceptanceB == null) {
            kmAcceptanceB = new StringFilter();
        }
        return kmAcceptanceB;
    }

    public void setKmAcceptanceB(StringFilter kmAcceptanceB) {
        this.kmAcceptanceB = kmAcceptanceB;
    }

    public StringFilter getKmCodeB() {
        return kmCodeB;
    }

    public StringFilter kmCodeB() {
        if (kmCodeB == null) {
            kmCodeB = new StringFilter();
        }
        return kmCodeB;
    }

    public void setKmCodeB(StringFilter kmCodeB) {
        this.kmCodeB = kmCodeB;
    }

    public StringFilter gethAgreementNumberB() {
        return hAgreementNumberB;
    }

    public StringFilter hAgreementNumberB() {
        if (hAgreementNumberB == null) {
            hAgreementNumberB = new StringFilter();
        }
        return hAgreementNumberB;
    }

    public void sethAgreementNumberB(StringFilter hAgreementNumberB) {
        this.hAgreementNumberB = hAgreementNumberB;
    }

    public StringFilter gethAgreementAreaB() {
        return hAgreementAreaB;
    }

    public StringFilter hAgreementAreaB() {
        if (hAgreementAreaB == null) {
            hAgreementAreaB = new StringFilter();
        }
        return hAgreementAreaB;
    }

    public void sethAgreementAreaB(StringFilter hAgreementAreaB) {
        this.hAgreementAreaB = hAgreementAreaB;
    }

    public StringFilter gethAgreementBurdenB() {
        return hAgreementBurdenB;
    }

    public StringFilter hAgreementBurdenB() {
        if (hAgreementBurdenB == null) {
            hAgreementBurdenB = new StringFilter();
        }
        return hAgreementBurdenB;
    }

    public void sethAgreementBurdenB(StringFilter hAgreementBurdenB) {
        this.hAgreementBurdenB = hAgreementBurdenB;
    }

    public StringFilter getTotalPaidB() {
        return totalPaidB;
    }

    public StringFilter totalPaidB() {
        if (totalPaidB == null) {
            totalPaidB = new StringFilter();
        }
        return totalPaidB;
    }

    public void setTotalPaidB(StringFilter totalPaidB) {
        this.totalPaidB = totalPaidB;
    }

    public StringFilter getDemandAreaB() {
        return demandAreaB;
    }

    public StringFilter demandAreaB() {
        if (demandAreaB == null) {
            demandAreaB = new StringFilter();
        }
        return demandAreaB;
    }

    public void setDemandAreaB(StringFilter demandAreaB) {
        this.demandAreaB = demandAreaB;
    }

    public StringFilter getCheckInTheFormOfPaymentB() {
        return checkInTheFormOfPaymentB;
    }

    public StringFilter checkInTheFormOfPaymentB() {
        if (checkInTheFormOfPaymentB == null) {
            checkInTheFormOfPaymentB = new StringFilter();
        }
        return checkInTheFormOfPaymentB;
    }

    public void setCheckInTheFormOfPaymentB(StringFilter checkInTheFormOfPaymentB) {
        this.checkInTheFormOfPaymentB = checkInTheFormOfPaymentB;
    }

    public StringFilter getSharesB() {
        return sharesB;
    }

    public StringFilter sharesB() {
        if (sharesB == null) {
            sharesB = new StringFilter();
        }
        return sharesB;
    }

    public void setSharesB(StringFilter sharesB) {
        this.sharesB = sharesB;
    }

    public LocalDateFilter getVasulPatraRepaymentDateB() {
        return vasulPatraRepaymentDateB;
    }

    public LocalDateFilter vasulPatraRepaymentDateB() {
        if (vasulPatraRepaymentDateB == null) {
            vasulPatraRepaymentDateB = new LocalDateFilter();
        }
        return vasulPatraRepaymentDateB;
    }

    public void setVasulPatraRepaymentDateB(LocalDateFilter vasulPatraRepaymentDateB) {
        this.vasulPatraRepaymentDateB = vasulPatraRepaymentDateB;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LoanDemandKMPatrakCriteria that = (LoanDemandKMPatrakCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(demandCode, that.demandCode) &&
            Objects.equals(date, that.date) &&
            Objects.equals(kmDate, that.kmDate) &&
            Objects.equals(shares, that.shares) &&
            Objects.equals(pid, that.pid) &&
            Objects.equals(code, that.code) &&
            Objects.equals(demandArea, that.demandArea) &&
            Objects.equals(cropType, that.cropType) &&
            Objects.equals(total, that.total) &&
            Objects.equals(check, that.check) &&
            Objects.equals(goods, that.goods) &&
            Objects.equals(sharesn, that.sharesn) &&
            Objects.equals(hn, that.hn) &&
            Objects.equals(area, that.area) &&
            Objects.equals(hAmount, that.hAmount) &&
            Objects.equals(name, that.name) &&
            Objects.equals(khateCode, that.khateCode) &&
            Objects.equals(remaining, that.remaining) &&
            Objects.equals(arrears, that.arrears) &&
            Objects.equals(kmAcceptance, that.kmAcceptance) &&
            Objects.equals(paidDate, that.paidDate) &&
            Objects.equals(kmCode, that.kmCode) &&
            Objects.equals(pendingDate, that.pendingDate) &&
            Objects.equals(depositeDate, that.depositeDate) &&
            Objects.equals(accountNumberB, that.accountNumberB) &&
            Objects.equals(loanDue, that.loanDue) &&
            Objects.equals(arrearsB, that.arrearsB) &&
            Objects.equals(dueDateB, that.dueDateB) &&
            Objects.equals(cropB, that.cropB) &&
            Objects.equals(kmAcceptanceB, that.kmAcceptanceB) &&
            Objects.equals(kmCodeB, that.kmCodeB) &&
            Objects.equals(hAgreementNumberB, that.hAgreementNumberB) &&
            Objects.equals(hAgreementAreaB, that.hAgreementAreaB) &&
            Objects.equals(hAgreementBurdenB, that.hAgreementBurdenB) &&
            Objects.equals(totalPaidB, that.totalPaidB) &&
            Objects.equals(demandAreaB, that.demandAreaB) &&
            Objects.equals(checkInTheFormOfPaymentB, that.checkInTheFormOfPaymentB) &&
            Objects.equals(sharesB, that.sharesB) &&
            Objects.equals(vasulPatraRepaymentDateB, that.vasulPatraRepaymentDateB) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            demandCode,
            date,
            kmDate,
            shares,
            pid,
            code,
            demandArea,
            cropType,
            total,
            check,
            goods,
            sharesn,
            hn,
            area,
            hAmount,
            name,
            khateCode,
            remaining,
            arrears,
            kmAcceptance,
            paidDate,
            kmCode,
            pendingDate,
            depositeDate,
            accountNumberB,
            loanDue,
            arrearsB,
            dueDateB,
            cropB,
            kmAcceptanceB,
            kmCodeB,
            hAgreementNumberB,
            hAgreementAreaB,
            hAgreementBurdenB,
            totalPaidB,
            demandAreaB,
            checkInTheFormOfPaymentB,
            sharesB,
            vasulPatraRepaymentDateB,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDemandKMPatrakCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (demandCode != null ? "demandCode=" + demandCode + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (kmDate != null ? "kmDate=" + kmDate + ", " : "") +
            (shares != null ? "shares=" + shares + ", " : "") +
            (pid != null ? "pid=" + pid + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (demandArea != null ? "demandArea=" + demandArea + ", " : "") +
            (cropType != null ? "cropType=" + cropType + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            (check != null ? "check=" + check + ", " : "") +
            (goods != null ? "goods=" + goods + ", " : "") +
            (sharesn != null ? "sharesn=" + sharesn + ", " : "") +
            (hn != null ? "hn=" + hn + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (hAmount != null ? "hAmount=" + hAmount + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (khateCode != null ? "khateCode=" + khateCode + ", " : "") +
            (remaining != null ? "remaining=" + remaining + ", " : "") +
            (arrears != null ? "arrears=" + arrears + ", " : "") +
            (kmAcceptance != null ? "kmAcceptance=" + kmAcceptance + ", " : "") +
            (paidDate != null ? "paidDate=" + paidDate + ", " : "") +
            (kmCode != null ? "kmCode=" + kmCode + ", " : "") +
            (pendingDate != null ? "pendingDate=" + pendingDate + ", " : "") +
            (depositeDate != null ? "depositeDate=" + depositeDate + ", " : "") +
            (accountNumberB != null ? "accountNumberB=" + accountNumberB + ", " : "") +
            (loanDue != null ? "loanDue=" + loanDue + ", " : "") +
            (arrearsB != null ? "arrearsB=" + arrearsB + ", " : "") +
            (dueDateB != null ? "dueDateB=" + dueDateB + ", " : "") +
            (cropB != null ? "cropB=" + cropB + ", " : "") +
            (kmAcceptanceB != null ? "kmAcceptanceB=" + kmAcceptanceB + ", " : "") +
            (kmCodeB != null ? "kmCodeB=" + kmCodeB + ", " : "") +
            (hAgreementNumberB != null ? "hAgreementNumberB=" + hAgreementNumberB + ", " : "") +
            (hAgreementAreaB != null ? "hAgreementAreaB=" + hAgreementAreaB + ", " : "") +
            (hAgreementBurdenB != null ? "hAgreementBurdenB=" + hAgreementBurdenB + ", " : "") +
            (totalPaidB != null ? "totalPaidB=" + totalPaidB + ", " : "") +
            (demandAreaB != null ? "demandAreaB=" + demandAreaB + ", " : "") +
            (checkInTheFormOfPaymentB != null ? "checkInTheFormOfPaymentB=" + checkInTheFormOfPaymentB + ", " : "") +
            (sharesB != null ? "sharesB=" + sharesB + ", " : "") +
            (vasulPatraRepaymentDateB != null ? "vasulPatraRepaymentDateB=" + vasulPatraRepaymentDateB + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
