package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;

/**
 * A KarkhanaVasuliRecords.
 */
@Entity
@Table(name = "karkhana_vasuli_records")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KarkhanaVasuliRecords extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "factory_member_code")
    private Long factoryMemberCode;

    @Column(name = "karkhana_member_code_mr")
    private String karkhanaMemberCodeMr;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_name_mr")
    private String memberNameMr;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "village_name_mr")
    private String villageNameMr;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "amount_mr")
    private String amountMr;

    @Column(name = "saving_acc_no")
    private Long savingAccNo;

    @Column(name = "saving_acc_no_mr")
    private String savingAccNoMr;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    private KarkhanaVasuliFile karkhanaVasuliFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KarkhanaVasuliRecords id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFactoryMemberCode() {
        return this.factoryMemberCode;
    }

    public KarkhanaVasuliRecords factoryMemberCode(Long factoryMemberCode) {
        this.setFactoryMemberCode(factoryMemberCode);
        return this;
    }

    public void setFactoryMemberCode(Long factoryMemberCode) {
        this.factoryMemberCode = factoryMemberCode;
    }

    public String getKarkhanaMemberCodeMr() {
        return this.karkhanaMemberCodeMr;
    }

    public KarkhanaVasuliRecords karkhanaMemberCodeMr(String karkhanaMemberCodeMr) {
        this.setKarkhanaMemberCodeMr(karkhanaMemberCodeMr);
        return this;
    }

    public void setKarkhanaMemberCodeMr(String karkhanaMemberCodeMr) {
        this.karkhanaMemberCodeMr = karkhanaMemberCodeMr;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public KarkhanaVasuliRecords memberName(String memberName) {
        this.setMemberName(memberName);
        return this;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNameMr() {
        return this.memberNameMr;
    }

    public KarkhanaVasuliRecords memberNameMr(String memberNameMr) {
        this.setMemberNameMr(memberNameMr);
        return this;
    }

    public void setMemberNameMr(String memberNameMr) {
        this.memberNameMr = memberNameMr;
    }

    public String getVillageName() {
        return this.villageName;
    }

    public KarkhanaVasuliRecords villageName(String villageName) {
        this.setVillageName(villageName);
        return this;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getVillageNameMr() {
        return this.villageNameMr;
    }

    public KarkhanaVasuliRecords villageNameMr(String villageNameMr) {
        this.setVillageNameMr(villageNameMr);
        return this;
    }

    public void setVillageNameMr(String villageNameMr) {
        this.villageNameMr = villageNameMr;
    }

    public Double getAmount() {
        return this.amount;
    }

    public KarkhanaVasuliRecords amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAmountMr() {
        return this.amountMr;
    }

    public KarkhanaVasuliRecords amountMr(String amountMr) {
        this.setAmountMr(amountMr);
        return this;
    }

    public void setAmountMr(String amountMr) {
        this.amountMr = amountMr;
    }

    public Long getSavingAccNo() {
        return this.savingAccNo;
    }

    public KarkhanaVasuliRecords savingAccNo(Long savingAccNo) {
        this.setSavingAccNo(savingAccNo);
        return this;
    }

    public void setSavingAccNo(Long savingAccNo) {
        this.savingAccNo = savingAccNo;
    }

    public String getSavingAccNoMr() {
        return this.savingAccNoMr;
    }

    public KarkhanaVasuliRecords savingAccNoMr(String savingAccNoMr) {
        this.setSavingAccNoMr(savingAccNoMr);
        return this;
    }

    public void setSavingAccNoMr(String savingAccNoMr) {
        this.savingAccNoMr = savingAccNoMr;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public KarkhanaVasuliRecords status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public KarkhanaVasuliFile getKarkhanaVasuliFile() {
        return this.karkhanaVasuliFile;
    }

    public void setKarkhanaVasuliFile(KarkhanaVasuliFile karkhanaVasuliFile) {
        this.karkhanaVasuliFile = karkhanaVasuliFile;
    }

    public KarkhanaVasuliRecords karkhanaVasuliFile(KarkhanaVasuliFile karkhanaVasuliFile) {
        this.setKarkhanaVasuliFile(karkhanaVasuliFile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KarkhanaVasuliRecords)) {
            return false;
        }
        return getId() != null && getId().equals(((KarkhanaVasuliRecords) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KarkhanaVasuliRecords{" +
            "id=" + getId() +
            ", factoryMemberCode=" + getFactoryMemberCode() +
            ", karkhanaMemberCodeMr='" + getKarkhanaMemberCodeMr() + "'" +
            ", memberName='" + getMemberName() + "'" +
            ", memberNameMr='" + getMemberNameMr() + "'" +
            ", villageName='" + getVillageName() + "'" +
            ", villageNameMr='" + getVillageNameMr() + "'" +
            ", amount=" + getAmount() +
            ", amountMr='" + getAmountMr() + "'" +
            ", savingAccNo=" + getSavingAccNo() +
            ", savingAccNoMr='" + getSavingAccNoMr() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
