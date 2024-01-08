package com.cbs.middleware.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the KmMaster entity. This class is used in KmMasterResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /km-masters?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KmMasterCriteria implements Serializable {
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

    private StringFilter pacsMemberCode;

    private LongFilter farmerTypeMasterId;

    public KmMasterCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(StringFilter branchCode) {
        this.branchCode = branchCode;
    }

    public StringFilter getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(StringFilter farmerName) {
        this.farmerName = farmerName;
    }

    public StringFilter getFarmerNameMr() {
        return farmerNameMr;
    }

    public void setFarmerNameMr(StringFilter farmerNameMr) {
        this.farmerNameMr = farmerNameMr;
    }

    public StringFilter getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(StringFilter farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public StringFilter getFarmerAddressMr() {
        return farmerAddressMr;
    }

    public void setFarmerAddressMr(StringFilter farmerAddressMr) {
        this.farmerAddressMr = farmerAddressMr;
    }

    public StringFilter getGender() {
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getCaste() {
        return caste;
    }

    public void setCaste(StringFilter caste) {
        this.caste = caste;
    }

    public StringFilter getCasteMr() {
        return casteMr;
    }

    public void setCasteMr(StringFilter casteMr) {
        this.casteMr = casteMr;
    }

    public StringFilter getPacsNumber() {
        return pacsNumber;
    }

    public void setPacsNumber(StringFilter pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public StringFilter getAreaHect() {
        return areaHect;
    }

    public void setAreaHect(StringFilter areaHect) {
        this.areaHect = areaHect;
    }

    public LongFilter getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(LongFilter aadharNo) {
        this.aadharNo = aadharNo;
    }

    public StringFilter getAadharNoMr() {
        return aadharNoMr;
    }

    public void setAadharNoMr(StringFilter aadharNoMr) {
        this.aadharNoMr = aadharNoMr;
    }

    public LongFilter getPanNo() {
        return panNo;
    }

    public void setPanNo(LongFilter panNo) {
        this.panNo = panNo;
    }

    public StringFilter getPanNoMr() {
        return panNoMr;
    }

    public void setPanNoMr(StringFilter panNoMr) {
        this.panNoMr = panNoMr;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getMobileNoMr() {
        return mobileNoMr;
    }

    public void setMobileNoMr(StringFilter mobileNoMr) {
        this.mobileNoMr = mobileNoMr;
    }

    public StringFilter getKccNo() {
        return kccNo;
    }

    public void setKccNo(StringFilter kccNo) {
        this.kccNo = kccNo;
    }

    public StringFilter getKccNoMr() {
        return kccNoMr;
    }

    public void setKccNoMr(StringFilter kccNoMr) {
        this.kccNoMr = kccNoMr;
    }

    public StringFilter getSavingNo() {
        return savingNo;
    }

    public void setSavingNo(StringFilter savingNo) {
        this.savingNo = savingNo;
    }

    public StringFilter getSavingNoMr() {
        return savingNoMr;
    }

    public void setSavingNoMr(StringFilter savingNoMr) {
        this.savingNoMr = savingNoMr;
    }

    public StringFilter getEntryFlag() {
        return entryFlag;
    }

    public void setEntryFlag(StringFilter entryFlag) {
        this.entryFlag = entryFlag;
    }

    public StringFilter getPacsMemberCode() {
        return pacsMemberCode;
    }

    public void setPacsMemberCode(StringFilter pacsMemberCode) {
        this.pacsMemberCode = pacsMemberCode;
    }

    public LongFilter getFarmerTypeMasterId() {
        return farmerTypeMasterId;
    }

    public void setFarmerTypeMasterId(LongFilter farmerTypeMasterId) {
        this.farmerTypeMasterId = farmerTypeMasterId;
    }

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
                (pacsMemberCode != null ? "pacsMemberCode=" + pacsMemberCode + ", " : "") +
                (farmerTypeMasterId != null ? "farmerTypeMasterId=" + farmerTypeMasterId + ", " : "") +
            "}";
    }

}
