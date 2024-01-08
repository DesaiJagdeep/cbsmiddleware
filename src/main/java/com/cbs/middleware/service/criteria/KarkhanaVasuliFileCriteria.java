package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KarkhanaVasuliFile} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KarkhanaVasuliFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /karkhana-vasuli-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KarkhanaVasuliFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fileName;

    private StringFilter uniqueFileName;

    private StringFilter address;

    private StringFilter addressMr;

    private StringFilter hangam;

    private StringFilter hangamMr;

    private StringFilter factoryName;

    private StringFilter factoryNameMr;

    private DoubleFilter totalAmount;

    private StringFilter totalAmountMr;

    private InstantFilter fromDate;

    private InstantFilter toDate;

    private LongFilter branchCode;

    private StringFilter pacsName;

    private LongFilter factoryMasterId;

    private Boolean distinct;

    public KarkhanaVasuliFileCriteria() {}

    public KarkhanaVasuliFileCriteria(KarkhanaVasuliFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.uniqueFileName = other.uniqueFileName == null ? null : other.uniqueFileName.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.addressMr = other.addressMr == null ? null : other.addressMr.copy();
        this.hangam = other.hangam == null ? null : other.hangam.copy();
        this.hangamMr = other.hangamMr == null ? null : other.hangamMr.copy();
        this.factoryName = other.factoryName == null ? null : other.factoryName.copy();
        this.factoryNameMr = other.factoryNameMr == null ? null : other.factoryNameMr.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.totalAmountMr = other.totalAmountMr == null ? null : other.totalAmountMr.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.toDate = other.toDate == null ? null : other.toDate.copy();
        this.branchCode = other.branchCode == null ? null : other.branchCode.copy();
        this.pacsName = other.pacsName == null ? null : other.pacsName.copy();
        this.factoryMasterId = other.factoryMasterId == null ? null : other.factoryMasterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KarkhanaVasuliFileCriteria copy() {
        return new KarkhanaVasuliFileCriteria(this);
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

    public StringFilter getFileName() {
        return fileName;
    }

    public StringFilter fileName() {
        if (fileName == null) {
            fileName = new StringFilter();
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getUniqueFileName() {
        return uniqueFileName;
    }

    public StringFilter uniqueFileName() {
        if (uniqueFileName == null) {
            uniqueFileName = new StringFilter();
        }
        return uniqueFileName;
    }

    public void setUniqueFileName(StringFilter uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getAddressMr() {
        return addressMr;
    }

    public StringFilter addressMr() {
        if (addressMr == null) {
            addressMr = new StringFilter();
        }
        return addressMr;
    }

    public void setAddressMr(StringFilter addressMr) {
        this.addressMr = addressMr;
    }

    public StringFilter getHangam() {
        return hangam;
    }

    public StringFilter hangam() {
        if (hangam == null) {
            hangam = new StringFilter();
        }
        return hangam;
    }

    public void setHangam(StringFilter hangam) {
        this.hangam = hangam;
    }

    public StringFilter getHangamMr() {
        return hangamMr;
    }

    public StringFilter hangamMr() {
        if (hangamMr == null) {
            hangamMr = new StringFilter();
        }
        return hangamMr;
    }

    public void setHangamMr(StringFilter hangamMr) {
        this.hangamMr = hangamMr;
    }

    public StringFilter getFactoryName() {
        return factoryName;
    }

    public StringFilter factoryName() {
        if (factoryName == null) {
            factoryName = new StringFilter();
        }
        return factoryName;
    }

    public void setFactoryName(StringFilter factoryName) {
        this.factoryName = factoryName;
    }

    public StringFilter getFactoryNameMr() {
        return factoryNameMr;
    }

    public StringFilter factoryNameMr() {
        if (factoryNameMr == null) {
            factoryNameMr = new StringFilter();
        }
        return factoryNameMr;
    }

    public void setFactoryNameMr(StringFilter factoryNameMr) {
        this.factoryNameMr = factoryNameMr;
    }

    public DoubleFilter getTotalAmount() {
        return totalAmount;
    }

    public DoubleFilter totalAmount() {
        if (totalAmount == null) {
            totalAmount = new DoubleFilter();
        }
        return totalAmount;
    }

    public void setTotalAmount(DoubleFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public StringFilter getTotalAmountMr() {
        return totalAmountMr;
    }

    public StringFilter totalAmountMr() {
        if (totalAmountMr == null) {
            totalAmountMr = new StringFilter();
        }
        return totalAmountMr;
    }

    public void setTotalAmountMr(StringFilter totalAmountMr) {
        this.totalAmountMr = totalAmountMr;
    }

    public InstantFilter getFromDate() {
        return fromDate;
    }

    public InstantFilter fromDate() {
        if (fromDate == null) {
            fromDate = new InstantFilter();
        }
        return fromDate;
    }

    public void setFromDate(InstantFilter fromDate) {
        this.fromDate = fromDate;
    }

    public InstantFilter getToDate() {
        return toDate;
    }

    public InstantFilter toDate() {
        if (toDate == null) {
            toDate = new InstantFilter();
        }
        return toDate;
    }

    public void setToDate(InstantFilter toDate) {
        this.toDate = toDate;
    }

    public LongFilter getBranchCode() {
        return branchCode;
    }

    public LongFilter branchCode() {
        if (branchCode == null) {
            branchCode = new LongFilter();
        }
        return branchCode;
    }

    public void setBranchCode(LongFilter branchCode) {
        this.branchCode = branchCode;
    }

    public StringFilter getPacsName() {
        return pacsName;
    }

    public StringFilter pacsName() {
        if (pacsName == null) {
            pacsName = new StringFilter();
        }
        return pacsName;
    }

    public void setPacsName(StringFilter pacsName) {
        this.pacsName = pacsName;
    }

    public LongFilter getFactoryMasterId() {
        return factoryMasterId;
    }

    public LongFilter factoryMasterId() {
        if (factoryMasterId == null) {
            factoryMasterId = new LongFilter();
        }
        return factoryMasterId;
    }

    public void setFactoryMasterId(LongFilter factoryMasterId) {
        this.factoryMasterId = factoryMasterId;
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
        final KarkhanaVasuliFileCriteria that = (KarkhanaVasuliFileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(uniqueFileName, that.uniqueFileName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(addressMr, that.addressMr) &&
            Objects.equals(hangam, that.hangam) &&
            Objects.equals(hangamMr, that.hangamMr) &&
            Objects.equals(factoryName, that.factoryName) &&
            Objects.equals(factoryNameMr, that.factoryNameMr) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(totalAmountMr, that.totalAmountMr) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(branchCode, that.branchCode) &&
            Objects.equals(pacsName, that.pacsName) &&
            Objects.equals(factoryMasterId, that.factoryMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            fileName,
            uniqueFileName,
            address,
            addressMr,
            hangam,
            hangamMr,
            factoryName,
            factoryNameMr,
            totalAmount,
            totalAmountMr,
            fromDate,
            toDate,
            branchCode,
            pacsName,
            factoryMasterId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KarkhanaVasuliFileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fileName != null ? "fileName=" + fileName + ", " : "") +
            (uniqueFileName != null ? "uniqueFileName=" + uniqueFileName + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (addressMr != null ? "addressMr=" + addressMr + ", " : "") +
            (hangam != null ? "hangam=" + hangam + ", " : "") +
            (hangamMr != null ? "hangamMr=" + hangamMr + ", " : "") +
            (factoryName != null ? "factoryName=" + factoryName + ", " : "") +
            (factoryNameMr != null ? "factoryNameMr=" + factoryNameMr + ", " : "") +
            (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
            (totalAmountMr != null ? "totalAmountMr=" + totalAmountMr + ", " : "") +
            (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
            (toDate != null ? "toDate=" + toDate + ", " : "") +
            (branchCode != null ? "branchCode=" + branchCode + ", " : "") +
            (pacsName != null ? "pacsName=" + pacsName + ", " : "") +
            (factoryMasterId != null ? "factoryMasterId=" + factoryMasterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
