package com.cbs.middleware.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A KmMaster.
 */
@Entity
@Table(name = "km_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMaster  extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "farmer_name")
    private String farmerName;

    @Column(name = "farmer_name_mr")
    private String farmerNameMr;

    @Column(name = "farmer_address")
    private String farmerAddress;

    @Column(name = "farmer_address_mr")
    private String farmerAddressMr;

    @Column(name = "gender")
    private String gender;

    @Column(name = "caste")
    private String caste;

    @Column(name = "caste_mr")
    private String casteMr;

    @Column(name = "pacs_number")
    private String pacsNumber;

    @Column(name = "area_hect")
    private String areaHect;

    @Column(name = "aadhar_no")
    private Long aadharNo;

    @Column(name = "aadhar_no_mr")
    private String aadharNoMr;

    @Column(name = "pan_no")
    private Long panNo;

    @Column(name = "pan_no_mr")
    private String panNoMr;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "mobile_no_mr")
    private String mobileNoMr;

    @Column(name = "kcc_no")
    private String kccNo;

    @Column(name = "kcc_no_mr")
    private String kccNoMr;

    @Column(name = "saving_no")
    private String savingNo;

    @Column(name = "saving_no_mr")
    private String savingNoMr;

    @Column(name = "entry_flag")
    private String entryFlag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KmMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public KmMaster branchCode(String branchCode) {
        this.setBranchCode(branchCode);
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFarmerName() {
        return this.farmerName;
    }

    public KmMaster farmerName(String farmerName) {
        this.setFarmerName(farmerName);
        return this;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerNameMr() {
        return this.farmerNameMr;
    }

    public KmMaster farmerNameMr(String farmerNameMr) {
        this.setFarmerNameMr(farmerNameMr);
        return this;
    }

    public void setFarmerNameMr(String farmerNameMr) {
        this.farmerNameMr = farmerNameMr;
    }

    public String getFarmerAddress() {
        return this.farmerAddress;
    }

    public KmMaster farmerAddress(String farmerAddress) {
        this.setFarmerAddress(farmerAddress);
        return this;
    }

    public void setFarmerAddress(String farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public String getFarmerAddressMr() {
        return this.farmerAddressMr;
    }

    public KmMaster farmerAddressMr(String farmerAddressMr) {
        this.setFarmerAddressMr(farmerAddressMr);
        return this;
    }

    public void setFarmerAddressMr(String farmerAddressMr) {
        this.farmerAddressMr = farmerAddressMr;
    }

    public String getGender() {
        return this.gender;
    }

    public KmMaster gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCaste() {
        return this.caste;
    }

    public KmMaster caste(String caste) {
        this.setCaste(caste);
        return this;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getCasteMr() {
        return this.casteMr;
    }

    public KmMaster casteMr(String casteMr) {
        this.setCasteMr(casteMr);
        return this;
    }

    public void setCasteMr(String casteMr) {
        this.casteMr = casteMr;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public KmMaster pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getAreaHect() {
        return this.areaHect;
    }

    public KmMaster areaHect(String areaHect) {
        this.setAreaHect(areaHect);
        return this;
    }

    public void setAreaHect(String areaHect) {
        this.areaHect = areaHect;
    }

    public Long getAadharNo() {
        return this.aadharNo;
    }

    public KmMaster aadharNo(Long aadharNo) {
        this.setAadharNo(aadharNo);
        return this;
    }

    public void setAadharNo(Long aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getAadharNoMr() {
        return this.aadharNoMr;
    }

    public KmMaster aadharNoMr(String aadharNoMr) {
        this.setAadharNoMr(aadharNoMr);
        return this;
    }

    public void setAadharNoMr(String aadharNoMr) {
        this.aadharNoMr = aadharNoMr;
    }

    public Long getPanNo() {
        return this.panNo;
    }

    public KmMaster panNo(Long panNo) {
        this.setPanNo(panNo);
        return this;
    }

    public void setPanNo(Long panNo) {
        this.panNo = panNo;
    }

    public String getPanNoMr() {
        return this.panNoMr;
    }

    public KmMaster panNoMr(String panNoMr) {
        this.setPanNoMr(panNoMr);
        return this;
    }

    public void setPanNoMr(String panNoMr) {
        this.panNoMr = panNoMr;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public KmMaster mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNoMr() {
        return this.mobileNoMr;
    }

    public KmMaster mobileNoMr(String mobileNoMr) {
        this.setMobileNoMr(mobileNoMr);
        return this;
    }

    public void setMobileNoMr(String mobileNoMr) {
        this.mobileNoMr = mobileNoMr;
    }

    public String getKccNo() {
        return this.kccNo;
    }

    public KmMaster kccNo(String kccNo) {
        this.setKccNo(kccNo);
        return this;
    }

    public void setKccNo(String kccNo) {
        this.kccNo = kccNo;
    }

    public String getKccNoMr() {
        return this.kccNoMr;
    }

    public KmMaster kccNoMr(String kccNoMr) {
        this.setKccNoMr(kccNoMr);
        return this;
    }

    public void setKccNoMr(String kccNoMr) {
        this.kccNoMr = kccNoMr;
    }

    public String getSavingNo() {
        return this.savingNo;
    }

    public KmMaster savingNo(String savingNo) {
        this.setSavingNo(savingNo);
        return this;
    }

    public void setSavingNo(String savingNo) {
        this.savingNo = savingNo;
    }

    public String getSavingNoMr() {
        return this.savingNoMr;
    }

    public KmMaster savingNoMr(String savingNoMr) {
        this.setSavingNoMr(savingNoMr);
        return this;
    }

    public void setSavingNoMr(String savingNoMr) {
        this.savingNoMr = savingNoMr;
    }

    public String getEntryFlag() {
        return this.entryFlag;
    }

    public KmMaster entryFlag(String entryFlag) {
        this.setEntryFlag(entryFlag);
        return this;
    }

    public void setEntryFlag(String entryFlag) {
        this.entryFlag = entryFlag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KmMaster)) {
            return false;
        }
        return getId() != null && getId().equals(((KmMaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMaster{" +
            "id=" + getId() +
            ", branchCode='" + getBranchCode() + "'" +
            ", farmerName='" + getFarmerName() + "'" +
            ", farmerNameMr='" + getFarmerNameMr() + "'" +
            ", farmerAddress='" + getFarmerAddress() + "'" +
            ", farmerAddressMr='" + getFarmerAddressMr() + "'" +
            ", gender='" + getGender() + "'" +
            ", caste='" + getCaste() + "'" +
            ", casteMr='" + getCasteMr() + "'" +
            ", pacsNumber='" + getPacsNumber() + "'" +
            ", areaHect='" + getAreaHect() + "'" +
            ", aadharNo=" + getAadharNo() +
            ", aadharNoMr='" + getAadharNoMr() + "'" +
            ", panNo=" + getPanNo() +
            ", panNoMr='" + getPanNoMr() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", mobileNoMr='" + getMobileNoMr() + "'" +
            ", kccNo='" + getKccNo() + "'" +
            ", kccNoMr='" + getKccNoMr() + "'" +
            ", savingNo='" + getSavingNo() + "'" +
            ", savingNoMr='" + getSavingNoMr() + "'" +
            ", entryFlag='" + getEntryFlag() + "'" +
            "}";
    }
}