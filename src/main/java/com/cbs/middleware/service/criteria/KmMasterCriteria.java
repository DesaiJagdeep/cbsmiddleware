package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KmMaster} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KmMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /km-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter branchCode;

    private StringFilter farmerName;

    private StringFilter farmerNameMr;

    private StringFilter farmerAddress;

    private StringFilter farmerAddressMr;

    private StringFilter gender;

    private StringFilter caste;

    private StringFilter casteMr;

    private StringFilter pacsNumber;

    private StringFilter areaHect;

    private LongFilter aadharNo;

    private StringFilter aadharNoMr;

    private LongFilter panNo;

    private StringFilter panNoMr;

    private StringFilter mobileNo;

    private StringFilter mobileNoMr;

    private StringFilter kccNo;

    private StringFilter kccNoMr;

    private StringFilter savingNo;

    private StringFilter savingNoMr;

    private StringFilter entryFlag;

    private Boolean distinct;

    public KmMasterCriteria() {}

    public KmMasterCriteria(KmMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.branchCode = other.branchCode == null ? null : other.branchCode.copy();
        this.farmerName = other.farmerName == null ? null : other.farmerName.copy();
        this.farmerNameMr = other.farmerNameMr == null ? null : other.farmerNameMr.copy();
        this.farmerAddress = other.farmerAddress == null ? null : other.farmerAddress.copy();
        this.farmerAddressMr = other.farmerAddressMr == null ? null : other.farmerAddressMr.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.caste = other.caste == null ? null : other.caste.copy();
        this.casteMr = other.casteMr == null ? null : other.casteMr.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.areaHect = other.areaHect == null ? null : other.areaHect.copy();
        this.aadharNo = other.aadharNo == null ? null : other.aadharNo.copy();
        this.aadharNoMr = other.aadharNoMr == null ? null : other.aadharNoMr.copy();
        this.panNo = other.panNo == null ? null : other.panNo.copy();
        this.panNoMr = other.panNoMr == null ? null : other.panNoMr.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.mobileNoMr = other.mobileNoMr == null ? null : other.mobileNoMr.copy();
        this.kccNo = other.kccNo == null ? null : other.kccNo.copy();
        this.kccNoMr = other.kccNoMr == null ? null : other.kccNoMr.copy();
        this.savingNo = other.savingNo == null ? null : other.savingNo.copy();
        this.savingNoMr = other.savingNoMr == null ? null : other.savingNoMr.copy();
        this.entryFlag = other.entryFlag == null ? null : other.entryFlag.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KmMasterCriteria copy() {
        return new KmMasterCriteria(this);
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

    public StringFilter getBranchCode() {
        return branchCode;
    }

    public StringFilter branchCode() {
        if (branchCode == null) {
            branchCode = new StringFilter();
        }
        return branchCode;
    }

    public void setBranchCode(StringFilter branchCode) {
        this.branchCode = branchCode;
    }

    public StringFilter getFarmerName() {
        return farmerName;
    }

    public StringFilter farmerName() {
        if (farmerName == null) {
            farmerName = new StringFilter();
        }
        return farmerName;
    }

    public void setFarmerName(StringFilter farmerName) {
        this.farmerName = farmerName;
    }

    public StringFilter getFarmerNameMr() {
        return farmerNameMr;
    }

    public StringFilter farmerNameMr() {
        if (farmerNameMr == null) {
            farmerNameMr = new StringFilter();
        }
        return farmerNameMr;
    }

    public void setFarmerNameMr(StringFilter farmerNameMr) {
        this.farmerNameMr = farmerNameMr;
    }

    public StringFilter getFarmerAddress() {
        return farmerAddress;
    }

    public StringFilter farmerAddress() {
        if (farmerAddress == null) {
            farmerAddress = new StringFilter();
        }
        return farmerAddress;
    }

    public void setFarmerAddress(StringFilter farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public StringFilter getFarmerAddressMr() {
        return farmerAddressMr;
    }

    public StringFilter farmerAddressMr() {
        if (farmerAddressMr == null) {
            farmerAddressMr = new StringFilter();
        }
        return farmerAddressMr;
    }

    public void setFarmerAddressMr(StringFilter farmerAddressMr) {
        this.farmerAddressMr = farmerAddressMr;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getCaste() {
        return caste;
    }

    public StringFilter caste() {
        if (caste == null) {
            caste = new StringFilter();
        }
        return caste;
    }

    public void setCaste(StringFilter caste) {
        this.caste = caste;
    }

    public StringFilter getCasteMr() {
        return casteMr;
    }

    public StringFilter casteMr() {
        if (casteMr == null) {
            casteMr = new StringFilter();
        }
        return casteMr;
    }

    public void setCasteMr(StringFilter casteMr) {
        this.casteMr = casteMr;
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

    public StringFilter getAreaHect() {
        return areaHect;
    }

    public StringFilter areaHect() {
        if (areaHect == null) {
            areaHect = new StringFilter();
        }
        return areaHect;
    }

    public void setAreaHect(StringFilter areaHect) {
        this.areaHect = areaHect;
    }

    public LongFilter getAadharNo() {
        return aadharNo;
    }

    public LongFilter aadharNo() {
        if (aadharNo == null) {
            aadharNo = new LongFilter();
        }
        return aadharNo;
    }

    public void setAadharNo(LongFilter aadharNo) {
        this.aadharNo = aadharNo;
    }

    public StringFilter getAadharNoMr() {
        return aadharNoMr;
    }

    public StringFilter aadharNoMr() {
        if (aadharNoMr == null) {
            aadharNoMr = new StringFilter();
        }
        return aadharNoMr;
    }

    public void setAadharNoMr(StringFilter aadharNoMr) {
        this.aadharNoMr = aadharNoMr;
    }

    public LongFilter getPanNo() {
        return panNo;
    }

    public LongFilter panNo() {
        if (panNo == null) {
            panNo = new LongFilter();
        }
        return panNo;
    }

    public void setPanNo(LongFilter panNo) {
        this.panNo = panNo;
    }

    public StringFilter getPanNoMr() {
        return panNoMr;
    }

    public StringFilter panNoMr() {
        if (panNoMr == null) {
            panNoMr = new StringFilter();
        }
        return panNoMr;
    }

    public void setPanNoMr(StringFilter panNoMr) {
        this.panNoMr = panNoMr;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getMobileNoMr() {
        return mobileNoMr;
    }

    public StringFilter mobileNoMr() {
        if (mobileNoMr == null) {
            mobileNoMr = new StringFilter();
        }
        return mobileNoMr;
    }

    public void setMobileNoMr(StringFilter mobileNoMr) {
        this.mobileNoMr = mobileNoMr;
    }

    public StringFilter getKccNo() {
        return kccNo;
    }

    public StringFilter kccNo() {
        if (kccNo == null) {
            kccNo = new StringFilter();
        }
        return kccNo;
    }

    public void setKccNo(StringFilter kccNo) {
        this.kccNo = kccNo;
    }

    public StringFilter getKccNoMr() {
        return kccNoMr;
    }

    public StringFilter kccNoMr() {
        if (kccNoMr == null) {
            kccNoMr = new StringFilter();
        }
        return kccNoMr;
    }

    public void setKccNoMr(StringFilter kccNoMr) {
        this.kccNoMr = kccNoMr;
    }

    public StringFilter getSavingNo() {
        return savingNo;
    }

    public StringFilter savingNo() {
        if (savingNo == null) {
            savingNo = new StringFilter();
        }
        return savingNo;
    }

    public void setSavingNo(StringFilter savingNo) {
        this.savingNo = savingNo;
    }

    public StringFilter getSavingNoMr() {
        return savingNoMr;
    }

    public StringFilter savingNoMr() {
        if (savingNoMr == null) {
            savingNoMr = new StringFilter();
        }
        return savingNoMr;
    }

    public void setSavingNoMr(StringFilter savingNoMr) {
        this.savingNoMr = savingNoMr;
    }

    public StringFilter getEntryFlag() {
        return entryFlag;
    }

    public StringFilter entryFlag() {
        if (entryFlag == null) {
            entryFlag = new StringFilter();
        }
        return entryFlag;
    }

    public void setEntryFlag(StringFilter entryFlag) {
        this.entryFlag = entryFlag;
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
        final KmMasterCriteria that = (KmMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(branchCode, that.branchCode) &&
            Objects.equals(farmerName, that.farmerName) &&
            Objects.equals(farmerNameMr, that.farmerNameMr) &&
            Objects.equals(farmerAddress, that.farmerAddress) &&
            Objects.equals(farmerAddressMr, that.farmerAddressMr) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(caste, that.caste) &&
            Objects.equals(casteMr, that.casteMr) &&
            Objects.equals(pacsNumber, that.pacsNumber) &&
            Objects.equals(areaHect, that.areaHect) &&
            Objects.equals(aadharNo, that.aadharNo) &&
            Objects.equals(aadharNoMr, that.aadharNoMr) &&
            Objects.equals(panNo, that.panNo) &&
            Objects.equals(panNoMr, that.panNoMr) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(mobileNoMr, that.mobileNoMr) &&
            Objects.equals(kccNo, that.kccNo) &&
            Objects.equals(kccNoMr, that.kccNoMr) &&
            Objects.equals(savingNo, that.savingNo) &&
            Objects.equals(savingNoMr, that.savingNoMr) &&
            Objects.equals(entryFlag, that.entryFlag) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            branchCode,
            farmerName,
            farmerNameMr,
            farmerAddress,
            farmerAddressMr,
            gender,
            caste,
            casteMr,
            pacsNumber,
            areaHect,
            aadharNo,
            aadharNoMr,
            panNo,
            panNoMr,
            mobileNo,
            mobileNoMr,
            kccNo,
            kccNoMr,
            savingNo,
            savingNoMr,
            entryFlag,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMasterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (branchCode != null ? "branchCode=" + branchCode + ", " : "") +
            (farmerName != null ? "farmerName=" + farmerName + ", " : "") +
            (farmerNameMr != null ? "farmerNameMr=" + farmerNameMr + ", " : "") +
            (farmerAddress != null ? "farmerAddress=" + farmerAddress + ", " : "") +
            (farmerAddressMr != null ? "farmerAddressMr=" + farmerAddressMr + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (caste != null ? "caste=" + caste + ", " : "") +
            (casteMr != null ? "casteMr=" + casteMr + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (areaHect != null ? "areaHect=" + areaHect + ", " : "") +
            (aadharNo != null ? "aadharNo=" + aadharNo + ", " : "") +
            (aadharNoMr != null ? "aadharNoMr=" + aadharNoMr + ", " : "") +
            (panNo != null ? "panNo=" + panNo + ", " : "") +
            (panNoMr != null ? "panNoMr=" + panNoMr + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (mobileNoMr != null ? "mobileNoMr=" + mobileNoMr + ", " : "") +
            (kccNo != null ? "kccNo=" + kccNo + ", " : "") +
            (kccNoMr != null ? "kccNoMr=" + kccNoMr + ", " : "") +
            (savingNo != null ? "savingNo=" + savingNo + ", " : "") +
            (savingNoMr != null ? "savingNoMr=" + savingNoMr + ", " : "") +
            (entryFlag != null ? "entryFlag=" + entryFlag + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
