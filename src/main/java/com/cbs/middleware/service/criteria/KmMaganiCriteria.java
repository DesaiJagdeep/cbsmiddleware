package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KmMagani} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KmMaganiResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /km-maganis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMaganiCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter kmNumber;

    private StringFilter memberNumber;

    private StringFilter memberName;

    private StringFilter pacsNumber;

    private DoubleFilter share;

    private StringFilter financialYear;

    private InstantFilter kmDate;

    private InstantFilter maganiDate;

    private Boolean distinct;

    public KmMaganiCriteria() {}

    public KmMaganiCriteria(KmMaganiCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.kmNumber = other.kmNumber == null ? null : other.kmNumber.copy();
        this.memberNumber = other.memberNumber == null ? null : other.memberNumber.copy();
        this.memberName = other.memberName == null ? null : other.memberName.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.share = other.share == null ? null : other.share.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.kmDate = other.kmDate == null ? null : other.kmDate.copy();
        this.maganiDate = other.maganiDate == null ? null : other.maganiDate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KmMaganiCriteria copy() {
        return new KmMaganiCriteria(this);
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

    public StringFilter getKmNumber() {
        return kmNumber;
    }

    public StringFilter kmNumber() {
        if (kmNumber == null) {
            kmNumber = new StringFilter();
        }
        return kmNumber;
    }

    public void setKmNumber(StringFilter kmNumber) {
        this.kmNumber = kmNumber;
    }

    public StringFilter getMemberNumber() {
        return memberNumber;
    }

    public StringFilter memberNumber() {
        if (memberNumber == null) {
            memberNumber = new StringFilter();
        }
        return memberNumber;
    }

    public void setMemberNumber(StringFilter memberNumber) {
        this.memberNumber = memberNumber;
    }

    public StringFilter getMemberName() {
        return memberName;
    }

    public StringFilter memberName() {
        if (memberName == null) {
            memberName = new StringFilter();
        }
        return memberName;
    }

    public void setMemberName(StringFilter memberName) {
        this.memberName = memberName;
    }

    public StringFilter getPacsNumber() {
        return pacsNumber;
    }

    public StringFilter pacsNumber() {
        if (pacsNumber == null) {
            pacsNumber = new StringFilter();
        }
        return pacsNumber;
    }

    public void setPacsNumber(StringFilter pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public DoubleFilter getShare() {
        return share;
    }

    public DoubleFilter share() {
        if (share == null) {
            share = new DoubleFilter();
        }
        return share;
    }

    public void setShare(DoubleFilter share) {
        this.share = share;
    }

    public StringFilter getFinancialYear() {
        return financialYear;
    }

    public StringFilter financialYear() {
        if (financialYear == null) {
            financialYear = new StringFilter();
        }
        return financialYear;
    }

    public void setFinancialYear(StringFilter financialYear) {
        this.financialYear = financialYear;
    }

    public InstantFilter getKmDate() {
        return kmDate;
    }

    public InstantFilter kmDate() {
        if (kmDate == null) {
            kmDate = new InstantFilter();
        }
        return kmDate;
    }

    public void setKmDate(InstantFilter kmDate) {
        this.kmDate = kmDate;
    }

    public InstantFilter getMaganiDate() {
        return maganiDate;
    }

    public InstantFilter maganiDate() {
        if (maganiDate == null) {
            maganiDate = new InstantFilter();
        }
        return maganiDate;
    }

    public void setMaganiDate(InstantFilter maganiDate) {
        this.maganiDate = maganiDate;
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
        final KmMaganiCriteria that = (KmMaganiCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(kmNumber, that.kmNumber) &&
            Objects.equals(memberNumber, that.memberNumber) &&
            Objects.equals(memberName, that.memberName) &&
            Objects.equals(pacsNumber, that.pacsNumber) &&
            Objects.equals(share, that.share) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(kmDate, that.kmDate) &&
            Objects.equals(maganiDate, that.maganiDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kmNumber, memberNumber, memberName, pacsNumber, share, financialYear, kmDate, maganiDate, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMaganiCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (kmNumber != null ? "kmNumber=" + kmNumber + ", " : "") +
            (memberNumber != null ? "memberNumber=" + memberNumber + ", " : "") +
            (memberName != null ? "memberName=" + memberName + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (share != null ? "share=" + share + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (kmDate != null ? "kmDate=" + kmDate + ", " : "") +
            (maganiDate != null ? "maganiDate=" + maganiDate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
