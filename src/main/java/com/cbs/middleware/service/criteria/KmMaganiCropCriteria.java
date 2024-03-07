package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KmMaganiCrop} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KmMaganiCropResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /km-magani-crops?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMaganiCropCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cropName;

    private DoubleFilter cropBalance;

    private DoubleFilter cropDue;

    private InstantFilter cropDueDate;

    private InstantFilter cropVasuliPatraDate;

    private DoubleFilter kmManjuri;

    private DoubleFilter kmArea;

    private StringFilter eKararNumber;

    private DoubleFilter eKararAmount;

    private DoubleFilter eKararArea;

    private DoubleFilter maganiArea;

    private DoubleFilter maganiAmount;

    private DoubleFilter maganiShare;

    private StringFilter pacsNumber;

    private LongFilter kmMaganiId;

    private Boolean distinct;

    public KmMaganiCropCriteria() {}

    public KmMaganiCropCriteria(KmMaganiCropCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cropName = other.cropName == null ? null : other.cropName.copy();
        this.cropBalance = other.cropBalance == null ? null : other.cropBalance.copy();
        this.cropDue = other.cropDue == null ? null : other.cropDue.copy();
        this.cropDueDate = other.cropDueDate == null ? null : other.cropDueDate.copy();
        this.cropVasuliPatraDate = other.cropVasuliPatraDate == null ? null : other.cropVasuliPatraDate.copy();
        this.kmManjuri = other.kmManjuri == null ? null : other.kmManjuri.copy();
        this.kmArea = other.kmArea == null ? null : other.kmArea.copy();
        this.eKararNumber = other.eKararNumber == null ? null : other.eKararNumber.copy();
        this.eKararAmount = other.eKararAmount == null ? null : other.eKararAmount.copy();
        this.eKararArea = other.eKararArea == null ? null : other.eKararArea.copy();
        this.maganiArea = other.maganiArea == null ? null : other.maganiArea.copy();
        this.maganiAmount = other.maganiAmount == null ? null : other.maganiAmount.copy();
        this.maganiShare = other.maganiShare == null ? null : other.maganiShare.copy();
        this.pacsNumber = other.pacsNumber == null ? null : other.pacsNumber.copy();
        this.kmMaganiId = other.kmMaganiId == null ? null : other.kmMaganiId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KmMaganiCropCriteria copy() {
        return new KmMaganiCropCriteria(this);
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

    public StringFilter getCropName() {
        return cropName;
    }

    public StringFilter cropName() {
        if (cropName == null) {
            cropName = new StringFilter();
        }
        return cropName;
    }

    public void setCropName(StringFilter cropName) {
        this.cropName = cropName;
    }

    public DoubleFilter getCropBalance() {
        return cropBalance;
    }

    public DoubleFilter cropBalance() {
        if (cropBalance == null) {
            cropBalance = new DoubleFilter();
        }
        return cropBalance;
    }

    public void setCropBalance(DoubleFilter cropBalance) {
        this.cropBalance = cropBalance;
    }

    public DoubleFilter getCropDue() {
        return cropDue;
    }

    public DoubleFilter cropDue() {
        if (cropDue == null) {
            cropDue = new DoubleFilter();
        }
        return cropDue;
    }

    public void setCropDue(DoubleFilter cropDue) {
        this.cropDue = cropDue;
    }

    public InstantFilter getCropDueDate() {
        return cropDueDate;
    }

    public InstantFilter cropDueDate() {
        if (cropDueDate == null) {
            cropDueDate = new InstantFilter();
        }
        return cropDueDate;
    }

    public void setCropDueDate(InstantFilter cropDueDate) {
        this.cropDueDate = cropDueDate;
    }

    public InstantFilter getCropVasuliPatraDate() {
        return cropVasuliPatraDate;
    }

    public InstantFilter cropVasuliPatraDate() {
        if (cropVasuliPatraDate == null) {
            cropVasuliPatraDate = new InstantFilter();
        }
        return cropVasuliPatraDate;
    }

    public void setCropVasuliPatraDate(InstantFilter cropVasuliPatraDate) {
        this.cropVasuliPatraDate = cropVasuliPatraDate;
    }

    public DoubleFilter getKmManjuri() {
        return kmManjuri;
    }

    public DoubleFilter kmManjuri() {
        if (kmManjuri == null) {
            kmManjuri = new DoubleFilter();
        }
        return kmManjuri;
    }

    public void setKmManjuri(DoubleFilter kmManjuri) {
        this.kmManjuri = kmManjuri;
    }

    public DoubleFilter getKmArea() {
        return kmArea;
    }

    public DoubleFilter kmArea() {
        if (kmArea == null) {
            kmArea = new DoubleFilter();
        }
        return kmArea;
    }

    public void setKmArea(DoubleFilter kmArea) {
        this.kmArea = kmArea;
    }

    public StringFilter geteKararNumber() {
        return eKararNumber;
    }

    public StringFilter eKararNumber() {
        if (eKararNumber == null) {
            eKararNumber = new StringFilter();
        }
        return eKararNumber;
    }

    public void seteKararNumber(StringFilter eKararNumber) {
        this.eKararNumber = eKararNumber;
    }

    public DoubleFilter geteKararAmount() {
        return eKararAmount;
    }

    public DoubleFilter eKararAmount() {
        if (eKararAmount == null) {
            eKararAmount = new DoubleFilter();
        }
        return eKararAmount;
    }

    public void seteKararAmount(DoubleFilter eKararAmount) {
        this.eKararAmount = eKararAmount;
    }

    public DoubleFilter geteKararArea() {
        return eKararArea;
    }

    public DoubleFilter eKararArea() {
        if (eKararArea == null) {
            eKararArea = new DoubleFilter();
        }
        return eKararArea;
    }

    public void seteKararArea(DoubleFilter eKararArea) {
        this.eKararArea = eKararArea;
    }

    public DoubleFilter getMaganiArea() {
        return maganiArea;
    }

    public DoubleFilter maganiArea() {
        if (maganiArea == null) {
            maganiArea = new DoubleFilter();
        }
        return maganiArea;
    }

    public void setMaganiArea(DoubleFilter maganiArea) {
        this.maganiArea = maganiArea;
    }

    public DoubleFilter getMaganiAmount() {
        return maganiAmount;
    }

    public DoubleFilter maganiAmount() {
        if (maganiAmount == null) {
            maganiAmount = new DoubleFilter();
        }
        return maganiAmount;
    }

    public void setMaganiAmount(DoubleFilter maganiAmount) {
        this.maganiAmount = maganiAmount;
    }

    public DoubleFilter getMaganiShare() {
        return maganiShare;
    }

    public DoubleFilter maganiShare() {
        if (maganiShare == null) {
            maganiShare = new DoubleFilter();
        }
        return maganiShare;
    }

    public void setMaganiShare(DoubleFilter maganiShare) {
        this.maganiShare = maganiShare;
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

    public LongFilter getKmMaganiId() {
        return kmMaganiId;
    }

    public LongFilter kmMaganiId() {
        if (kmMaganiId == null) {
            kmMaganiId = new LongFilter();
        }
        return kmMaganiId;
    }

    public void setKmMaganiId(LongFilter kmMaganiId) {
        this.kmMaganiId = kmMaganiId;
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
        final KmMaganiCropCriteria that = (KmMaganiCropCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cropName, that.cropName) &&
            Objects.equals(cropBalance, that.cropBalance) &&
            Objects.equals(cropDue, that.cropDue) &&
            Objects.equals(cropDueDate, that.cropDueDate) &&
            Objects.equals(cropVasuliPatraDate, that.cropVasuliPatraDate) &&
            Objects.equals(kmManjuri, that.kmManjuri) &&
            Objects.equals(kmArea, that.kmArea) &&
            Objects.equals(eKararNumber, that.eKararNumber) &&
            Objects.equals(eKararAmount, that.eKararAmount) &&
            Objects.equals(eKararArea, that.eKararArea) &&
            Objects.equals(maganiArea, that.maganiArea) &&
            Objects.equals(maganiAmount, that.maganiAmount) &&
            Objects.equals(maganiShare, that.maganiShare) &&
            Objects.equals(pacsNumber, that.pacsNumber) &&
            Objects.equals(kmMaganiId, that.kmMaganiId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            cropName,
            cropBalance,
            cropDue,
            cropDueDate,
            cropVasuliPatraDate,
            kmManjuri,
            kmArea,
            eKararNumber,
            eKararAmount,
            eKararArea,
            maganiArea,
            maganiAmount,
            maganiShare,
            pacsNumber,
            kmMaganiId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMaganiCropCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cropName != null ? "cropName=" + cropName + ", " : "") +
            (cropBalance != null ? "cropBalance=" + cropBalance + ", " : "") +
            (cropDue != null ? "cropDue=" + cropDue + ", " : "") +
            (cropDueDate != null ? "cropDueDate=" + cropDueDate + ", " : "") +
            (cropVasuliPatraDate != null ? "cropVasuliPatraDate=" + cropVasuliPatraDate + ", " : "") +
            (kmManjuri != null ? "kmManjuri=" + kmManjuri + ", " : "") +
            (kmArea != null ? "kmArea=" + kmArea + ", " : "") +
            (eKararNumber != null ? "eKararNumber=" + eKararNumber + ", " : "") +
            (eKararAmount != null ? "eKararAmount=" + eKararAmount + ", " : "") +
            (eKararArea != null ? "eKararArea=" + eKararArea + ", " : "") +
            (maganiArea != null ? "maganiArea=" + maganiArea + ", " : "") +
            (maganiAmount != null ? "maganiAmount=" + maganiAmount + ", " : "") +
            (maganiShare != null ? "maganiShare=" + maganiShare + ", " : "") +
            (pacsNumber != null ? "pacsNumber=" + pacsNumber + ", " : "") +
            (kmMaganiId != null ? "kmMaganiId=" + kmMaganiId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
