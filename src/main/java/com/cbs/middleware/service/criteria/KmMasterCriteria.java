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

    private StringFilter branchCodeMr;

    private StringFilter farmerName;

    private StringFilter farmerNameMr;

    private StringFilter farmerAddress;

    private StringFilter farmerAddressMr;

    private StringFilter gender;

    private StringFilter genderMr;

    private StringFilter caste;

    private StringFilter casteMr;

    private StringFilter pacsNumber;

    private DoubleFilter areaHector;

    private StringFilter areaHectorMr;

    private StringFilter aadharNo;

    private StringFilter aadharNoMr;

    private StringFilter panNo;

    private StringFilter panNoMr;

    private StringFilter mobileNo;

    private StringFilter mobileNoMr;

    private StringFilter kccNo;

    private StringFilter kccNoMr;

    private LongFilter savingAcNo;

    private StringFilter savingAcNoMr;

    private StringFilter pacsMemberCode;

    private StringFilter pacsMemberCodeMr;

    private StringFilter entryFlag;

    private LongFilter farmerTypeMasterId;

    private Boolean distinct;

    public KmMasterCriteria() {}

    public KmMasterCriteria(KmMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.branchCode = other.branchCode == null ? null : other.branchCode.copy();
        this.branchCodeMr = other.branchCodeMr == null ? null : other.branchCodeMr.copy();
        this.farmerName = other.farmerName == null ? null : other.farmerName.copy();
        this.farmerNameMr = other.farmerNameMr == null ? null : other.farmerNameMr.copy();
        this.farmerAddress = other.farmerAddress == null ? null : other.farmerAddress.copy();
        this.farmerAddressMr = other.farmerAddressMr == null ? null : other.farmerAddressMr.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.genderMr = other.genderMr == null ? null : other.genderMr.copy();
        this.caste = other.caste == null ? null : other.caste.copy();
        this.casteMr = other.casteMr == null ? null : other.casteMr.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.areaHector = other.areaHector == null ? null : other.areaHector.copy();
        this.areaHectorMr = other.areaHectorMr == null ? null : other.areaHectorMr.copy();
        this.aadharNo = other.aadharNo == null ? null : other.aadharNo.copy();
        this.aadharNoMr = other.aadharNoMr == null ? null : other.aadharNoMr.copy();
        this.panNo = other.panNo == null ? null : other.panNo.copy();
        this.panNoMr = other.panNoMr == null ? null : other.panNoMr.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.mobileNoMr = other.mobileNoMr == null ? null : other.mobileNoMr.copy();
        this.kccNo = other.kccNo == null ? null : other.kccNo.copy();
        this.kccNoMr = other.kccNoMr == null ? null : other.kccNoMr.copy();
        this.savingAcNo = other.savingAcNo == null ? null : other.savingAcNo.copy();
        this.savingAcNoMr = other.savingAcNoMr == null ? null : other.savingAcNoMr.copy();
        this.pacsMemberCode = other.pacsMemberCode == null ? null : other.pacsMemberCode.copy();
        this.pacsMemberCodeMr = other.pacsMemberCodeMr == null ? null : other.pacsMemberCodeMr.copy();
        this.entryFlag = other.entryFlag == null ? null : other.entryFlag.copy();
        this.farmerTypeMasterId = other.farmerTypeMasterId == null ? null : other.farmerTypeMasterId.copy();
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

    public StringFilter getBranchCodeMr() {
        return branchCodeMr;
    }

    public StringFilter branchCodeMr() {
        if (branchCodeMr == null) {
            branchCodeMr = new StringFilter();
        }
        return branchCodeMr;
    }

    public void setBranchCodeMr(StringFilter branchCodeMr) {
        this.branchCodeMr = branchCodeMr;
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

    public StringFilter getGenderMr() {
        return genderMr;
    }

    public StringFilter genderMr() {
        if (genderMr == null) {
            genderMr = new StringFilter();
        }
        return genderMr;
    }

    public void setGenderMr(StringFilter genderMr) {
        this.genderMr = genderMr;
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

    public DoubleFilter getAreaHector() {
        return areaHector;
    }

    public DoubleFilter areaHector() {
        if (areaHector == null) {
            areaHector = new DoubleFilter();
        }
        return areaHector;
    }

    public void setAreaHector(DoubleFilter areaHector) {
        this.areaHector = areaHector;
    }

    public StringFilter getAreaHectorMr() {
        return areaHectorMr;
    }

    public StringFilter areaHectorMr() {
        if (areaHectorMr == null) {
            areaHectorMr = new StringFilter();
        }
        return areaHectorMr;
    }

    public void setAreaHectorMr(StringFilter areaHectorMr) {
        this.areaHectorMr = areaHectorMr;
    }

    public StringFilter getAadharNo() {
        return aadharNo;
    }

    public StringFilter aadharNo() {
        if (aadharNo == null) {
            aadharNo = new StringFilter();
        }
        return aadharNo;
    }

    public void setAadharNo(StringFilter aadharNo) {
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

    public StringFilter getPanNo() {
        return panNo;
    }

    public StringFilter panNo() {
        if (panNo == null) {
            panNo = new StringFilter();
        }
        return panNo;
    }

    public void setPanNo(StringFilter panNo) {
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

    public LongFilter getSavingAcNo() {
        return savingAcNo;
    }

    public LongFilter savingAcNo() {
        if (savingAcNo == null) {
            savingAcNo = new LongFilter();
        }
        return savingAcNo;
    }

    public void setSavingAcNo(LongFilter savingAcNo) {
        this.savingAcNo = savingAcNo;
    }

    public StringFilter getSavingAcNoMr() {
        return savingAcNoMr;
    }

    public StringFilter savingAcNoMr() {
        if (savingAcNoMr == null) {
            savingAcNoMr = new StringFilter();
        }
        return savingAcNoMr;
    }

    public void setSavingAcNoMr(StringFilter savingAcNoMr) {
        this.savingAcNoMr = savingAcNoMr;
    }

    public StringFilter getPacsMemberCode() {
        return pacsMemberCode;
    }

    public StringFilter pacsMemberCode() {
        if (pacsMemberCode == null) {
            pacsMemberCode = new StringFilter();
        }
        return pacsMemberCode;
    }

    public void setPacsMemberCode(StringFilter pacsMemberCode) {
        this.pacsMemberCode = pacsMemberCode;
    }

    public StringFilter getPacsMemberCodeMr() {
        return pacsMemberCodeMr;
    }

    public StringFilter pacsMemberCodeMr() {
        if (pacsMemberCodeMr == null) {
            pacsMemberCodeMr = new StringFilter();
        }
        return pacsMemberCodeMr;
    }

    public void setPacsMemberCodeMr(StringFilter pacsMemberCodeMr) {
        this.pacsMemberCodeMr = pacsMemberCodeMr;
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

    public LongFilter getFarmerTypeMasterId() {
        return farmerTypeMasterId;
    }

    public LongFilter farmerTypeMasterId() {
        if (farmerTypeMasterId == null) {
            farmerTypeMasterId = new LongFilter();
        }
        return farmerTypeMasterId;
    }

    public void setFarmerTypeMasterId(LongFilter farmerTypeMasterId) {
        this.farmerTypeMasterId = farmerTypeMasterId;
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
            Objects.equals(branchCodeMr, that.branchCodeMr) &&
            Objects.equals(farmerName, that.farmerName) &&
            Objects.equals(farmerNameMr, that.farmerNameMr) &&
            Objects.equals(farmerAddress, that.farmerAddress) &&
            Objects.equals(farmerAddressMr, that.farmerAddressMr) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(genderMr, that.genderMr) &&
            Objects.equals(caste, that.caste) &&
            Objects.equals(casteMr, that.casteMr) &&
            Objects.equals(pacsNumber, that.pacsNumber) &&
            Objects.equals(areaHector, that.areaHector) &&
            Objects.equals(areaHectorMr, that.areaHectorMr) &&
            Objects.equals(aadharNo, that.aadharNo) &&
            Objects.equals(aadharNoMr, that.aadharNoMr) &&
            Objects.equals(panNo, that.panNo) &&
            Objects.equals(panNoMr, that.panNoMr) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(mobileNoMr, that.mobileNoMr) &&
            Objects.equals(kccNo, that.kccNo) &&
            Objects.equals(kccNoMr, that.kccNoMr) &&
            Objects.equals(savingAcNo, that.savingAcNo) &&
            Objects.equals(savingAcNoMr, that.savingAcNoMr) &&
            Objects.equals(pacsMemberCode, that.pacsMemberCode) &&
            Objects.equals(pacsMemberCodeMr, that.pacsMemberCodeMr) &&
            Objects.equals(entryFlag, that.entryFlag) &&
            Objects.equals(farmerTypeMasterId, that.farmerTypeMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            branchCode,
            branchCodeMr,
            farmerName,
            farmerNameMr,
            farmerAddress,
            farmerAddressMr,
            gender,
            genderMr,
            caste,
            casteMr,
            pacsNumber,
            areaHector,
            areaHectorMr,
            aadharNo,
            aadharNoMr,
            panNo,
            panNoMr,
            mobileNo,
            mobileNoMr,
            kccNo,
            kccNoMr,
            savingAcNo,
            savingAcNoMr,
            pacsMemberCode,
            pacsMemberCodeMr,
            entryFlag,
            farmerTypeMasterId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMasterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (branchCode != null ? "branchCode=" + branchCode + ", " : "") +
            (branchCodeMr != null ? "branchCodeMr=" + branchCodeMr + ", " : "") +
            (farmerName != null ? "farmerName=" + farmerName + ", " : "") +
            (farmerNameMr != null ? "farmerNameMr=" + farmerNameMr + ", " : "") +
            (farmerAddress != null ? "farmerAddress=" + farmerAddress + ", " : "") +
            (farmerAddressMr != null ? "farmerAddressMr=" + farmerAddressMr + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (genderMr != null ? "genderMr=" + genderMr + ", " : "") +
            (caste != null ? "caste=" + caste + ", " : "") +
            (casteMr != null ? "casteMr=" + casteMr + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (areaHector != null ? "areaHector=" + areaHector + ", " : "") +
            (areaHectorMr != null ? "areaHectorMr=" + areaHectorMr + ", " : "") +
            (aadharNo != null ? "aadharNo=" + aadharNo + ", " : "") +
            (aadharNoMr != null ? "aadharNoMr=" + aadharNoMr + ", " : "") +
            (panNo != null ? "panNo=" + panNo + ", " : "") +
            (panNoMr != null ? "panNoMr=" + panNoMr + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (mobileNoMr != null ? "mobileNoMr=" + mobileNoMr + ", " : "") +
            (kccNo != null ? "kccNo=" + kccNo + ", " : "") +
            (kccNoMr != null ? "kccNoMr=" + kccNoMr + ", " : "") +
            (savingAcNo != null ? "savingAcNo=" + savingAcNo + ", " : "") +
            (savingAcNoMr != null ? "savingAcNoMr=" + savingAcNoMr + ", " : "") +
            (pacsMemberCode != null ? "pacsMemberCode=" + pacsMemberCode + ", " : "") +
            (pacsMemberCodeMr != null ? "pacsMemberCodeMr=" + pacsMemberCodeMr + ", " : "") +
            (entryFlag != null ? "entryFlag=" + entryFlag + ", " : "") +
            (farmerTypeMasterId != null ? "farmerTypeMasterId=" + farmerTypeMasterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
