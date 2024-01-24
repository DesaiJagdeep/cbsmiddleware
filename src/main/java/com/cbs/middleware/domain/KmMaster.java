package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A KmMaster.
 */
@Entity
@Table(name = "km_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMaster extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //
    @Size(max = 255)
    @Column(name = "branch_code", length = 255, nullable = true)
    private String branchCode;

    @Column(name = "branch_code_mr")
    private String branchCodeMr;

    //
    @Size(max = 255)
    @Column(name = "farmer_name", length = 255, nullable = true)
    private String farmerName;

    @Column(name = "farmer_name_mr")
    private String farmerNameMr;

    //
    @Size(max = 255)
    @Column(name = "farmer_address", length = 255, nullable = true)
    private String farmerAddress;

    @Column(name = "farmer_address_mr")
    private String farmerAddressMr;

    //
    @Size(max = 255)
    @Column(name = "gender", length = 255, nullable = true)
    private String gender;

    @Column(name = "gender_mr")
    private String genderMr;

    //
    @Size(max = 255)
    @Column(name = "caste", length = 255, nullable = true)
    private String caste;

    @Column(name = "caste_mr")
    private String casteMr;

    @Column(name = "pacs_number")
    private String pacsNumber;

    //
    @Column(name = "area_hector", nullable = true)
    private Double areaHector;

    @Column(name = "area_hector_mr")
    private String areaHectorMr;

    //
    @Size(min = 12, max = 12)
    //@Pattern(regexp = "^d{12}$")
    @Column(name = "aadhar_no", length = 12, nullable = true)
    private String aadharNo;

    @Column(name = "aadhar_no_mr")
    private String aadharNoMr;

    //
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$")
    @Column(name = "pan_no", length = 10, nullable = true)
    private String panNo;

    @Column(name = "pan_no_mr")
    private String panNoMr;

    //
    @Size(min = 10, max = 10)
    @Column(name = "mobile_no", length = 10, nullable = true)
    private String mobileNo;

    @Column(name = "mobile_no_mr")
    private String mobileNoMr;

    //
    @Column(name = "kcc_no", nullable = true)
    private String kccNo;

    @Column(name = "kcc_no_mr")
    private String kccNoMr;

    //
    @Column(name = "saving_ac_no", nullable = true)
    private Long savingAcNo;

    @Column(name = "saving_ac_no_mr")
    private String savingAcNoMr;

    //
    @Column(name = "pacs_member_code", nullable = true)
    private String pacsMemberCode;

    @Column(name = "pacs_member_code_mr")
    private String pacsMemberCodeMr;

    @Column(name = "entry_flag")
    private String entryFlag;

    @Column(name = "birth_date")
    private String birthDate;
    @Column(name = "birth_date_mr")
    private String birthDateMr;

    @Column(name = "loan_ac_no")
    private String loanAcNo;

    @Column(name = "loan_ac_no_mr")
    private String loanAcNoMr;


    @ManyToOne
    private FarmerTypeMaster farmerTypeMaster;

    @OneToOne(mappedBy = "kmMaster", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    //@JsonIgnore
    private KmDetails kmDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public String getBirthDateMr() {
        return birthDateMr;
    }

    public void setBirthDateMr(String birthDateMr) {
        this.birthDateMr = birthDateMr;
    }

    public String getLoanAcNo() {
        return loanAcNo;
    }

    public void setLoanAcNo(String loanAcNo) {
        this.loanAcNo = loanAcNo;
    }

    public String getLoanAcNoMr() {
        return loanAcNoMr;
    }

    public void setLoanAcNoMr(String loanAcNoMr) {
        this.loanAcNoMr = loanAcNoMr;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public KmDetails getKmDetails() {
        return kmDetails;
    }

    public void setKmDetails(KmDetails kmDetails) {
        this.kmDetails = kmDetails;
    }

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

    public String getBranchCodeMr() {
        return this.branchCodeMr;
    }

    public KmMaster branchCodeMr(String branchCodeMr) {
        this.setBranchCodeMr(branchCodeMr);
        return this;
    }

    public void setBranchCodeMr(String branchCodeMr) {
        this.branchCodeMr = branchCodeMr;
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

    public String getGenderMr() {
        return this.genderMr;
    }

    public KmMaster genderMr(String genderMr) {
        this.setGenderMr(genderMr);
        return this;
    }

    public void setGenderMr(String genderMr) {
        this.genderMr = genderMr;
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

    public Double getAreaHector() {
        return this.areaHector;
    }

    public KmMaster areaHector(Double areaHector) {
        this.setAreaHector(areaHector);
        return this;
    }

    public void setAreaHector(Double areaHector) {
        this.areaHector = areaHector;
    }

    public String getAreaHectorMr() {
        return this.areaHectorMr;
    }

    public KmMaster areaHectorMr(String areaHectorMr) {
        this.setAreaHectorMr(areaHectorMr);
        return this;
    }

 public void setAreaHectorMr(String areaHectorMr) {
        this.areaHectorMr = areaHectorMr;
    }

    public String getAadharNo() {
        return this.aadharNo;
    }

    public KmMaster aadharNo(String aadharNo) {
        this.setAadharNo(aadharNo);
        return this;
    }

    public void setAadharNo(String aadharNo) {
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

    public String getPanNo() {
        return this.panNo;
    }

    public KmMaster panNo(String panNo) {
        this.setPanNo(panNo);
        return this;
    }

    public void setPanNo(String panNo) {
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

    public Long getSavingAcNo() {
        return this.savingAcNo;
    }

    public KmMaster savingAcNo(Long savingAcNo) {
        this.setSavingAcNo(savingAcNo);
        return this;
    }

    public void setSavingAcNo(Long savingAcNo) {
        this.savingAcNo = savingAcNo;
    }

    public String getSavingAcNoMr() {
        return this.savingAcNoMr;
    }

    public KmMaster savingAcNoMr(String savingAcNoMr) {
        this.setSavingAcNoMr(savingAcNoMr);
        return this;
    }

    public void setSavingAcNoMr(String savingAcNoMr) {
        this.savingAcNoMr = savingAcNoMr;
    }

    public String getPacsMemberCode() {
        return this.pacsMemberCode;
    }

    public KmMaster pacsMemberCode(String pacsMemberCode) {
        this.setPacsMemberCode(pacsMemberCode);
        return this;
    }

    public void setPacsMemberCode(String pacsMemberCode) {
        this.pacsMemberCode = pacsMemberCode;
    }

    public String getPacsMemberCodeMr() {
        return this.pacsMemberCodeMr;
    }

    public KmMaster pacsMemberCodeMr(String pacsMemberCodeMr) {
        this.setPacsMemberCodeMr(pacsMemberCodeMr);
        return this;
    }

    public void setPacsMemberCodeMr(String pacsMemberCodeMr) {
        this.pacsMemberCodeMr = pacsMemberCodeMr;
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

    public FarmerTypeMaster getFarmerTypeMaster() {
        return this.farmerTypeMaster;
    }

    public void setFarmerTypeMaster(FarmerTypeMaster farmerTypeMaster) {
        this.farmerTypeMaster = farmerTypeMaster;
    }

    public KmMaster farmerTypeMaster(FarmerTypeMaster farmerTypeMaster) {
        this.setFarmerTypeMaster(farmerTypeMaster);
        return this;
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
        return id != null && id.equals(((KmMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "KmMaster{" +
            "id=" + id +
            ", branchCode='" + branchCode + '\'' +
            ", branchCodeMr='" + branchCodeMr + '\'' +
            ", farmerName='" + farmerName + '\'' +
            ", farmerNameMr='" + farmerNameMr + '\'' +
            ", farmerAddress='" + farmerAddress + '\'' +
            ", farmerAddressMr='" + farmerAddressMr + '\'' +
            ", gender='" + gender + '\'' +
            ", genderMr='" + genderMr + '\'' +
            ", caste='" + caste + '\'' +
            ", casteMr='" + casteMr + '\'' +
            ", pacsNumber='" + pacsNumber + '\'' +
            ", aadharNo='" + aadharNo + '\'' +
            ", aadharNoMr='" + aadharNoMr + '\'' +
            ", panNo='" + panNo + '\'' +
            ", panNoMr='" + panNoMr + '\'' +
            ", mobileNo='" + mobileNo + '\'' +
            ", mobileNoMr='" + mobileNoMr + '\'' +
            ", kccNo='" + kccNo + '\'' +
            ", kccNoMr='" + kccNoMr + '\'' +
            ", savingAcNo=" + savingAcNo +
            ", savingAcNoMr='" + savingAcNoMr + '\'' +
            ", pacsMemberCode='" + pacsMemberCode + '\'' +
            ", pacsMemberCodeMr='" + pacsMemberCodeMr + '\'' +
            ", entryFlag='" + entryFlag + '\'' +
            ", birthDate='" + birthDate + '\'' +
            ", birthDateMr='" + birthDateMr + '\'' +
            ", loanAcNo='" + loanAcNo + '\'' +
            ", loanAcNoMr='" + loanAcNoMr + '\'' +
            ", farmerTypeMaster=" + farmerTypeMaster +
            ", kmDetails=" + kmDetails +
            '}';
    }
}
