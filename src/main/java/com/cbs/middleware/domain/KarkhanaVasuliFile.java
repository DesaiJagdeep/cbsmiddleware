package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A KarkhanaVasuliFile.
 */
@Entity
@Table(name = "karkhana_vasuli_file")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KarkhanaVasuliFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "unique_file_name")
    private String uniqueFileName;

    @Column(name = "address")
    private String address;

    @Column(name = "address_mr")
    private String addressMr;

    @Column(name = "hangam")
    private String hangam;

    @Column(name = "hangam_mr")
    private String hangamMr;

    @Column(name = "factory_name")
    private String factoryName;

    @Column(name = "factory_name_mr")
    private String factoryNameMr;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "total_amount_mr")
    private String totalAmountMr;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "to_date")
    private Instant toDate;

    @Column(name = "branch_code")
    private Long branchCode;

    @Column(name = "pacs_name")
    private String pacsName;

    @OneToOne
    @JoinColumn(unique = true)
    private FactoryMaster factoryMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KarkhanaVasuliFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public KarkhanaVasuliFile fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUniqueFileName() {
        return this.uniqueFileName;
    }

    public KarkhanaVasuliFile uniqueFileName(String uniqueFileName) {
        this.setUniqueFileName(uniqueFileName);
        return this;
    }

    public void setUniqueFileName(String uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
    }

    public String getAddress() {
        return this.address;
    }

    public KarkhanaVasuliFile address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressMr() {
        return this.addressMr;
    }

    public KarkhanaVasuliFile addressMr(String addressMr) {
        this.setAddressMr(addressMr);
        return this;
    }

    public void setAddressMr(String addressMr) {
        this.addressMr = addressMr;
    }

    public String getHangam() {
        return this.hangam;
    }

    public KarkhanaVasuliFile hangam(String hangam) {
        this.setHangam(hangam);
        return this;
    }

    public void setHangam(String hangam) {
        this.hangam = hangam;
    }

    public String getHangamMr() {
        return this.hangamMr;
    }

    public KarkhanaVasuliFile hangamMr(String hangamMr) {
        this.setHangamMr(hangamMr);
        return this;
    }

    public void setHangamMr(String hangamMr) {
        this.hangamMr = hangamMr;
    }

    public String getFactoryName() {
        return this.factoryName;
    }

    public KarkhanaVasuliFile factoryName(String factoryName) {
        this.setFactoryName(factoryName);
        return this;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryNameMr() {
        return this.factoryNameMr;
    }

    public KarkhanaVasuliFile factoryNameMr(String factoryNameMr) {
        this.setFactoryNameMr(factoryNameMr);
        return this;
    }

    public void setFactoryNameMr(String factoryNameMr) {
        this.factoryNameMr = factoryNameMr;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public KarkhanaVasuliFile totalAmount(Double totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountMr() {
        return this.totalAmountMr;
    }

    public KarkhanaVasuliFile totalAmountMr(String totalAmountMr) {
        this.setTotalAmountMr(totalAmountMr);
        return this;
    }

    public void setTotalAmountMr(String totalAmountMr) {
        this.totalAmountMr = totalAmountMr;
    }

    public Instant getFromDate() {
        return this.fromDate;
    }

    public KarkhanaVasuliFile fromDate(Instant fromDate) {
        this.setFromDate(fromDate);
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return this.toDate;
    }

    public KarkhanaVasuliFile toDate(Instant toDate) {
        this.setToDate(toDate);
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Long getBranchCode() {
        return this.branchCode;
    }

    public KarkhanaVasuliFile branchCode(Long branchCode) {
        this.setBranchCode(branchCode);
        return this;
    }

    public void setBranchCode(Long branchCode) {
        this.branchCode = branchCode;
    }

    public String getPacsName() {
        return this.pacsName;
    }

    public KarkhanaVasuliFile pacsName(String pacsName) {
        this.setPacsName(pacsName);
        return this;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public FactoryMaster getFactoryMaster() {
        return this.factoryMaster;
    }

    public void setFactoryMaster(FactoryMaster factoryMaster) {
        this.factoryMaster = factoryMaster;
    }

    public KarkhanaVasuliFile factoryMaster(FactoryMaster factoryMaster) {
        this.setFactoryMaster(factoryMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KarkhanaVasuliFile)) {
            return false;
        }
        return id != null && id.equals(((KarkhanaVasuliFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KarkhanaVasuliFile{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", uniqueFileName='" + getUniqueFileName() + "'" +
            ", address='" + getAddress() + "'" +
            ", addressMr='" + getAddressMr() + "'" +
            ", hangam='" + getHangam() + "'" +
            ", hangamMr='" + getHangamMr() + "'" +
            ", factoryName='" + getFactoryName() + "'" +
            ", factoryNameMr='" + getFactoryNameMr() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", totalAmountMr='" + getTotalAmountMr() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", branchCode=" + getBranchCode() +
            ", pacsName='" + getPacsName() + "'" +
            "}";
    }
}
