package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A KamalCrop.
 */
@Entity
@Table(name = "kamal_crop")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalCrop extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pacs_number")
    private String pacsNumber;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "member_count")
    private String memberCount;

    @Column(name = "area")
    private String area;

    @Column(name = "pacs_amount")
    private String pacsAmount;

    @Column(name = "branch_amount")
    private String branchAmount;

    @Column(name = "head_office_amount")
    private String headOfficeAmount;

    @Column(name = "crop_eligibility_amount")
    private String cropEligibilityAmount;

    @Column(name = "divisional_office_amount")
    private String divisionalOfficeAmount;

    @Column(name = "agri_admin_amount")
    private String agriAdminAmount;
    @Column(name = "km_date")
    private Instant kmDate;

    @Column(name = "km_date_mr")
    private String kmDateMr;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "createdBy","createdDate","lastModifiedBy","lastModifiedDate" }, allowSetters = true)
    private KamalSociety kamalSociety;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "createdBy","createdDate","lastModifiedBy","lastModifiedDate" }, allowSetters = true)
    private FarmerTypeMaster farmerTypeMaster;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "createdBy","createdDate","lastModifiedBy","lastModifiedDate" }, allowSetters = true)
    private SeasonMaster seasonMaster;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "createdBy","createdDate","lastModifiedBy","lastModifiedDate" }, allowSetters = true)
    private CropMaster cropMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KamalCrop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public KamalCrop pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public KamalCrop financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getMemberCount() {
        return this.memberCount;
    }

    public KamalCrop memberCount(String memberCount) {
        this.setMemberCount(memberCount);
        return this;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }

    public String getArea() {
        return this.area;
    }

    public KamalCrop area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPacsAmount() {
        return this.pacsAmount;
    }

    public KamalCrop pacsAmount(String pacsAmount) {
        this.setPacsAmount(pacsAmount);
        return this;
    }

    public void setPacsAmount(String pacsAmount) {
        this.pacsAmount = pacsAmount;
    }

    public String getBranchAmount() {
        return this.branchAmount;
    }

    public KamalCrop branchAmount(String branchAmount) {
        this.setBranchAmount(branchAmount);
        return this;
    }

    public void setBranchAmount(String branchAmount) {
        this.branchAmount = branchAmount;
    }

    public String getHeadOfficeAmount() {
        return this.headOfficeAmount;
    }

    public KamalCrop headOfficeAmount(String headOfficeAmount) {
        this.setHeadOfficeAmount(headOfficeAmount);
        return this;
    }

    public void setHeadOfficeAmount(String headOfficeAmount) {
        this.headOfficeAmount = headOfficeAmount;
    }

    public String getCropEligibilityAmount() {
        return this.cropEligibilityAmount;
    }

    public KamalCrop cropEligibilityAmount(String cropEligibilityAmount) {
        this.setCropEligibilityAmount(cropEligibilityAmount);
        return this;
    }

    public void setCropEligibilityAmount(String cropEligibilityAmount) {
        this.cropEligibilityAmount = cropEligibilityAmount;
    }

    public KamalSociety getKamalSociety() {
        return this.kamalSociety;
    }

    public void setKamalSociety(KamalSociety kamalSociety) {
        this.kamalSociety = kamalSociety;
    }

    public KamalCrop kamalSociety(KamalSociety kamalSociety) {
        this.setKamalSociety(kamalSociety);
        return this;
    }

    public FarmerTypeMaster getFarmerTypeMaster() {
        return this.farmerTypeMaster;
    }

    public void setFarmerTypeMaster(FarmerTypeMaster farmerTypeMaster) {
        this.farmerTypeMaster = farmerTypeMaster;
    }

    public KamalCrop farmerTypeMaster(FarmerTypeMaster farmerTypeMaster) {
        this.setFarmerTypeMaster(farmerTypeMaster);
        return this;
    }

    public SeasonMaster getSeasonMaster() {
        return this.seasonMaster;
    }

    public void setSeasonMaster(SeasonMaster seasonMaster) {
        this.seasonMaster = seasonMaster;
    }

    public KamalCrop seasonMaster(SeasonMaster seasonMaster) {
        this.setSeasonMaster(seasonMaster);
        return this;
    }

    public CropMaster getCropMaster() {
        return this.cropMaster;
    }

    public void setCropMaster(CropMaster cropMaster) {
        this.cropMaster = cropMaster;
    }

    public KamalCrop cropMaster(CropMaster cropMaster) {
        this.setCropMaster(cropMaster);
        return this;
    }


    public String getDivisionalOfficeAmount() {
        return this.divisionalOfficeAmount;
    }

    public KamalCrop divisionalOfficeAmount(String divisionalOfficeAmount) {
        this.setDivisionalOfficeAmount(divisionalOfficeAmount);
        return this;
    }

    public void setDivisionalOfficeAmount(String divisionalOfficeAmount) {
        this.divisionalOfficeAmount = divisionalOfficeAmount;
    }

    public Instant getKmDate() {
        return this.kmDate;
    }

    public KamalCrop kmDate(Instant kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public void setKmDate(Instant kmDate) {
        this.kmDate = kmDate;
    }

    public String getKmDateMr() {
        return this.kmDateMr;
    }

    public KamalCrop kmDateMr(String kmDateMr) {
        this.setKmDateMr(kmDateMr);
        return this;
    }

    public void setKmDateMr(String kmDateMr) {
        this.kmDateMr = kmDateMr;
    }

    public String getAgriAdminAmount() {
        return this.agriAdminAmount;
    }

    public KamalCrop agriAdminAmount(String agriAdminAmount) {
        this.setAgriAdminAmount(agriAdminAmount);
        return this;
    }

    public void setAgriAdminAmount(String agriAdminAmount) {
        this.agriAdminAmount = agriAdminAmount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KamalCrop)) {
            return false;
        }
        return id != null && id.equals(((KamalCrop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalCrop{" +
            "id=" + getId() +
            ", pacsNumber='" + getPacsNumber() + "'" +
            ", financialYear='" + getFinancialYear() + "'" +
            ", memberCount='" + getMemberCount() + "'" +
            ", area='" + getArea() + "'" +
            ", pacsAmount='" + getPacsAmount() + "'" +
            ", branchAmount='" + getBranchAmount() + "'" +
            ", headOfficeAmount='" + getHeadOfficeAmount() + "'" +
            ", getDivisionalOfficeAmount='" + getDivisionalOfficeAmount() + "'" +
            ", cropEligibilityAmount='" + getCropEligibilityAmount() + "'" +
            ", kmDate='" + getKmDate() + "'" +
            ", kmDateMr='" + getKmDateMr() + "'" +
            "}";
    }

    public  String getIntegerPartOfNumber(String str){
        if (str==null){
            return "";
        }
        String[] parts = str.split("\\.");
        String integerPart = parts[0];
        return integerPart;
    }

    public  String getDecimalPartOfNumber(String str){
        if (str==null || !str.contains(".")){
            return "0";
        }
        String[] parts = str.split("\\.");
        String decimalPart = parts[1];
        return decimalPart;
    }
}
