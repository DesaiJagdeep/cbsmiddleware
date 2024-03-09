package com.cbs.middleware.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A KmMagani.
 */
@Entity
@Table(name = "km_magani")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMagani extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "km_number")
    private String kmNumber;

    @Column(name = "member_number")
    private String memberNumber;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "pacs_number")
    private String pacsNumber;

    @Column(name = "share")
    private Double share;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "km_date")
    private Instant kmDate;

    @Column(name = "magani_date")
    private Instant maganiDate;

    @OneToMany(mappedBy = "kmMagani", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"kmMagani"}, allowSetters = true, allowGetters = true)
    private Set<KmMaganiCrop> kmMaganiCrop = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public Set<KmMaganiCrop> getKmMaganiCrop() {
        return kmMaganiCrop;
    }

    public void setKmMaganiCrop(Set<KmMaganiCrop> kmMaganiCrop) {
        if (this.kmMaganiCrop != null) {
            this.kmMaganiCrop.forEach(i -> i.setKmMagani(null));
        }
        if (kmMaganiCrop != null) {
            kmMaganiCrop.forEach(i -> i.setKmMagani(this));
        }
        this.kmMaganiCrop = kmMaganiCrop;
    }

    public KmMagani kmMaganiCrop(Set<KmMaganiCrop> kmMaganiCrop) {
        this.setKmMaganiCrop(kmMaganiCrop);
        return this;
    }

    public KmMagani addKmMaganiCrop(KmMaganiCrop kmMaganiCrop) {
        this.kmMaganiCrop.add(kmMaganiCrop);
        kmMaganiCrop.setKmMagani(this);
        return this;
    }

    public KmMagani removeKmMaganiCrop(KmMaganiCrop kmMaganiCrop) {
        this.kmMaganiCrop.remove(kmMaganiCrop);
        kmMaganiCrop.setKmMagani(null);
        return this;
    }




    public Long getId() {
        return this.id;
    }

    public KmMagani id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKmNumber() {
        return this.kmNumber;
    }

    public KmMagani kmNumber(String kmNumber) {
        this.setKmNumber(kmNumber);
        return this;
    }

    public void setKmNumber(String kmNumber) {
        this.kmNumber = kmNumber;
    }

    public String getMemberNumber() {
        return this.memberNumber;
    }

    public KmMagani memberNumber(String memberNumber) {
        this.setMemberNumber(memberNumber);
        return this;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public KmMagani memberName(String memberName) {
        this.setMemberName(memberName);
        return this;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPacsNumber() {
        return this.pacsNumber;
    }

    public KmMagani pacsNumber(String pacsNumber) {
        this.setPacsNumber(pacsNumber);
        return this;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public Double getShare() {
        return this.share;
    }

    public KmMagani share(Double share) {
        this.setShare(share);
        return this;
    }

    public void setShare(Double share) {
        this.share = share;
    }

    public String getFinancialYear() {
        return this.financialYear;
    }

    public KmMagani financialYear(String financialYear) {
        this.setFinancialYear(financialYear);
        return this;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Instant getKmDate() {
        return this.kmDate;
    }

    public KmMagani kmDate(Instant kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public void setKmDate(Instant kmDate) {
        this.kmDate = kmDate;
    }

    public Instant getMaganiDate() {
        return this.maganiDate;
    }

    public KmMagani maganiDate(Instant maganiDate) {
        this.setMaganiDate(maganiDate);
        return this;
    }

    public void setMaganiDate(Instant maganiDate) {
        this.maganiDate = maganiDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KmMagani)) {
            return false;
        }
        return id != null && id.equals(((KmMagani) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMagani{" +
            "id=" + getId() +
            ", kmNumber='" + getKmNumber() + "'" +
            ", memberNumber='" + getMemberNumber() + "'" +
            ", memberName='" + getMemberName() + "'" +
            ", pacsNumber='" + getPacsNumber() + "'" +
            ", share=" + getShare() +
            ", financialYear='" + getFinancialYear() + "'" +
            ", kmDate='" + getKmDate() + "'" +
            ", maganiDate='" + getMaganiDate() + "'" +
            "}";
    }
}
