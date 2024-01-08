package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A KmMaster.
 */
@Entity
@Table(name = "km_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KmMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "pacs_member_code")
    private String pacsMemberCode;

    @OneToOne(mappedBy = "kmMaster")
    @JsonIgnore
    private FarmerTypeMaster farmerTypeMaster;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerNameMr() {
        return farmerNameMr;
    }

    public void setFarmerNameMr(String farmerNameMr) {
        this.farmerNameMr = farmerNameMr;
    }

    public String getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(String farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public String getFarmerAddressMr() {
        return farmerAddressMr;
    }

    public void setFarmerAddressMr(String farmerAddressMr) {
        this.farmerAddressMr = farmerAddressMr;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getCasteMr() {
        return casteMr;
    }

    public void setCasteMr(String casteMr) {
        this.casteMr = casteMr;
    }

    public String getPacsNumber() {
        return pacsNumber;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getAreaHect() {
        return areaHect;
    }

    public void setAreaHect(String areaHect) {
        this.areaHect = areaHect;
    }

    public Long getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(Long aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getAadharNoMr() {
        return aadharNoMr;
    }

    public void setAadharNoMr(String aadharNoMr) {
        this.aadharNoMr = aadharNoMr;
    }

    public Long getPanNo() {
        return panNo;
    }

    public void setPanNo(Long panNo) {
        this.panNo = panNo;
    }

    public String getPanNoMr() {
        return panNoMr;
    }

    public void setPanNoMr(String panNoMr) {
        this.panNoMr = panNoMr;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNoMr() {
        return mobileNoMr;
    }

    public void setMobileNoMr(String mobileNoMr) {
        this.mobileNoMr = mobileNoMr;
    }

    public String getKccNo() {
        return kccNo;
    }

    public void setKccNo(String kccNo) {
        this.kccNo = kccNo;
    }

    public String getKccNoMr() {
        return kccNoMr;
    }

    public void setKccNoMr(String kccNoMr) {
        this.kccNoMr = kccNoMr;
    }

    public String getSavingNo() {
        return savingNo;
    }

    public void setSavingNo(String savingNo) {
        this.savingNo = savingNo;
    }

    public String getSavingNoMr() {
        return savingNoMr;
    }

    public void setSavingNoMr(String savingNoMr) {
        this.savingNoMr = savingNoMr;
    }

    public String getEntryFlag() {
        return entryFlag;
    }

    public void setEntryFlag(String entryFlag) {
        this.entryFlag = entryFlag;
    }

    public String getPacsMemberCode() {
        return pacsMemberCode;
    }

    public void setPacsMemberCode(String pacsMemberCode) {
        this.pacsMemberCode = pacsMemberCode;
    }

    public FarmerTypeMaster getFarmerTypeMaster() {
        return farmerTypeMaster;
    }

    public void setFarmerTypeMaster(FarmerTypeMaster farmerTypeMaster) {
        this.farmerTypeMaster = farmerTypeMaster;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KmMaster kmMaster = (KmMaster) o;
        if (kmMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kmMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

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
            ", aadharNo='" + getAadharNo() + "'" +
            ", aadharNoMr='" + getAadharNoMr() + "'" +
            ", panNo='" + getPanNo() + "'" +
            ", panNoMr='" + getPanNoMr() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", mobileNoMr='" + getMobileNoMr() + "'" +
            ", kccNo='" + getKccNo() + "'" +
            ", kccNoMr='" + getKccNoMr() + "'" +
            ", savingNo='" + getSavingNo() + "'" +
            ", savingNoMr='" + getSavingNoMr() + "'" +
            ", entryFlag='" + getEntryFlag() + "'" +
            ", pacsMemberCode='" + getPacsMemberCode() + "'" +
            "}";
    }
}
