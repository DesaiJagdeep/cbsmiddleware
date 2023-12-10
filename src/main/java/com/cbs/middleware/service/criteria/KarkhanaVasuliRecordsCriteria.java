package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KarkhanaVasuliRecords} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KarkhanaVasuliRecordsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /karkhana-vasuli-records?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KarkhanaVasuliRecordsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter factoryMemberCode;

    private StringFilter karkhanaMemberCodeMr;

    private StringFilter memberName;

    private StringFilter memberNameMr;

    private StringFilter villageName;

    private StringFilter villageNameMr;

    private DoubleFilter amount;

    private StringFilter amountMr;

    private LongFilter savingAccNo;

    private StringFilter savingAccNoMr;

    private BooleanFilter status;

    private LongFilter karkhanaVasuliFileId;

    private Boolean distinct;

    public KarkhanaVasuliRecordsCriteria() {}

    public KarkhanaVasuliRecordsCriteria(KarkhanaVasuliRecordsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.factoryMemberCode = other.factoryMemberCode == null ? null : other.factoryMemberCode.copy();
        this.karkhanaMemberCodeMr = other.karkhanaMemberCodeMr == null ? null : other.karkhanaMemberCodeMr.copy();
        this.memberName = other.memberName == null ? null : other.memberName.copy();
        this.memberNameMr = other.memberNameMr == null ? null : other.memberNameMr.copy();
        this.villageName = other.villageName == null ? null : other.villageName.copy();
        this.villageNameMr = other.villageNameMr == null ? null : other.villageNameMr.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.amountMr = other.amountMr == null ? null : other.amountMr.copy();
        this.savingAccNo = other.savingAccNo == null ? null : other.savingAccNo.copy();
        this.savingAccNoMr = other.savingAccNoMr == null ? null : other.savingAccNoMr.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.karkhanaVasuliFileId = other.karkhanaVasuliFileId == null ? null : other.karkhanaVasuliFileId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KarkhanaVasuliRecordsCriteria copy() {
        return new KarkhanaVasuliRecordsCriteria(this);
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

    public LongFilter getFactoryMemberCode() {
        return factoryMemberCode;
    }

    public LongFilter factoryMemberCode() {
        if (factoryMemberCode == null) {
            factoryMemberCode = new LongFilter();
        }
        return factoryMemberCode;
    }

    public void setFactoryMemberCode(LongFilter factoryMemberCode) {
        this.factoryMemberCode = factoryMemberCode;
    }

    public StringFilter getKarkhanaMemberCodeMr() {
        return karkhanaMemberCodeMr;
    }

    public StringFilter karkhanaMemberCodeMr() {
        if (karkhanaMemberCodeMr == null) {
            karkhanaMemberCodeMr = new StringFilter();
        }
        return karkhanaMemberCodeMr;
    }

    public void setKarkhanaMemberCodeMr(StringFilter karkhanaMemberCodeMr) {
        this.karkhanaMemberCodeMr = karkhanaMemberCodeMr;
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

    public StringFilter getMemberNameMr() {
        return memberNameMr;
    }

    public StringFilter memberNameMr() {
        if (memberNameMr == null) {
            memberNameMr = new StringFilter();
        }
        return memberNameMr;
    }

    public void setMemberNameMr(StringFilter memberNameMr) {
        this.memberNameMr = memberNameMr;
    }

    public StringFilter getVillageName() {
        return villageName;
    }

    public StringFilter villageName() {
        if (villageName == null) {
            villageName = new StringFilter();
        }
        return villageName;
    }

    public void setVillageName(StringFilter villageName) {
        this.villageName = villageName;
    }

    public StringFilter getVillageNameMr() {
        return villageNameMr;
    }

    public StringFilter villageNameMr() {
        if (villageNameMr == null) {
            villageNameMr = new StringFilter();
        }
        return villageNameMr;
    }

    public void setVillageNameMr(StringFilter villageNameMr) {
        this.villageNameMr = villageNameMr;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public StringFilter getAmountMr() {
        return amountMr;
    }

    public StringFilter amountMr() {
        if (amountMr == null) {
            amountMr = new StringFilter();
        }
        return amountMr;
    }

    public void setAmountMr(StringFilter amountMr) {
        this.amountMr = amountMr;
    }

    public LongFilter getSavingAccNo() {
        return savingAccNo;
    }

    public LongFilter savingAccNo() {
        if (savingAccNo == null) {
            savingAccNo = new LongFilter();
        }
        return savingAccNo;
    }

    public void setSavingAccNo(LongFilter savingAccNo) {
        this.savingAccNo = savingAccNo;
    }

    public StringFilter getSavingAccNoMr() {
        return savingAccNoMr;
    }

    public StringFilter savingAccNoMr() {
        if (savingAccNoMr == null) {
            savingAccNoMr = new StringFilter();
        }
        return savingAccNoMr;
    }

    public void setSavingAccNoMr(StringFilter savingAccNoMr) {
        this.savingAccNoMr = savingAccNoMr;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public BooleanFilter status() {
        if (status == null) {
            status = new BooleanFilter();
        }
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
    }

    public LongFilter getKarkhanaVasuliFileId() {
        return karkhanaVasuliFileId;
    }

    public LongFilter karkhanaVasuliFileId() {
        if (karkhanaVasuliFileId == null) {
            karkhanaVasuliFileId = new LongFilter();
        }
        return karkhanaVasuliFileId;
    }

    public void setKarkhanaVasuliFileId(LongFilter karkhanaVasuliFileId) {
        this.karkhanaVasuliFileId = karkhanaVasuliFileId;
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
        final KarkhanaVasuliRecordsCriteria that = (KarkhanaVasuliRecordsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(factoryMemberCode, that.factoryMemberCode) &&
            Objects.equals(karkhanaMemberCodeMr, that.karkhanaMemberCodeMr) &&
            Objects.equals(memberName, that.memberName) &&
            Objects.equals(memberNameMr, that.memberNameMr) &&
            Objects.equals(villageName, that.villageName) &&
            Objects.equals(villageNameMr, that.villageNameMr) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(amountMr, that.amountMr) &&
            Objects.equals(savingAccNo, that.savingAccNo) &&
            Objects.equals(savingAccNoMr, that.savingAccNoMr) &&
            Objects.equals(status, that.status) &&
            Objects.equals(karkhanaVasuliFileId, that.karkhanaVasuliFileId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            factoryMemberCode,
            karkhanaMemberCodeMr,
            memberName,
            memberNameMr,
            villageName,
            villageNameMr,
            amount,
            amountMr,
            savingAccNo,
            savingAccNoMr,
            status,
            karkhanaVasuliFileId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KarkhanaVasuliRecordsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (factoryMemberCode != null ? "factoryMemberCode=" + factoryMemberCode + ", " : "") +
            (karkhanaMemberCodeMr != null ? "karkhanaMemberCodeMr=" + karkhanaMemberCodeMr + ", " : "") +
            (memberName != null ? "memberName=" + memberName + ", " : "") +
            (memberNameMr != null ? "memberNameMr=" + memberNameMr + ", " : "") +
            (villageName != null ? "villageName=" + villageName + ", " : "") +
            (villageNameMr != null ? "villageNameMr=" + villageNameMr + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (amountMr != null ? "amountMr=" + amountMr + ", " : "") +
            (savingAccNo != null ? "savingAccNo=" + savingAccNo + ", " : "") +
            (savingAccNoMr != null ? "savingAccNoMr=" + savingAccNoMr + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (karkhanaVasuliFileId != null ? "karkhanaVasuliFileId=" + karkhanaVasuliFileId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
