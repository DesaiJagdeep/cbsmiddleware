package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.KamalMaryadaPatrak} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.KamalMaryadaPatrakResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kamal-maryada-patraks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
//@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalMaryadaPatrakCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cropLoan;

    private StringFilter kmChart;

    private StringFilter demand;

    private StringFilter kmSummary;

    private LocalDateFilter kmDate;

    private LocalDateFilter toKMDate;

    private BooleanFilter coverPage;

    private StringFilter cropTypeNumber;

    private StringFilter cropType;

    private StringFilter fromHector;

    private StringFilter toHector;

    private StringFilter defaulterName;

    private StringFilter suchakName;

    private StringFilter anumodakName;

    private LocalDateFilter meetingDate;

    private StringFilter resolutionNumber;

    private Boolean distinct;

    public KamalMaryadaPatrakCriteria() {}

    public KamalMaryadaPatrakCriteria(KamalMaryadaPatrakCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cropLoan = other.cropLoan == null ? null : other.cropLoan.copy();
        this.kmChart = other.kmChart == null ? null : other.kmChart.copy();
        this.demand = other.demand == null ? null : other.demand.copy();
        this.kmSummary = other.kmSummary == null ? null : other.kmSummary.copy();
        this.kmDate = other.kmDate == null ? null : other.kmDate.copy();
        this.toKMDate = other.toKMDate == null ? null : other.toKMDate.copy();
        this.coverPage = other.coverPage == null ? null : other.coverPage.copy();
        this.cropTypeNumber = other.cropTypeNumber == null ? null : other.cropTypeNumber.copy();
        this.cropType = other.cropType == null ? null : other.cropType.copy();
        this.fromHector = other.fromHector == null ? null : other.fromHector.copy();
        this.toHector = other.toHector == null ? null : other.toHector.copy();
        this.defaulterName = other.defaulterName == null ? null : other.defaulterName.copy();
        this.suchakName = other.suchakName == null ? null : other.suchakName.copy();
        this.anumodakName = other.anumodakName == null ? null : other.anumodakName.copy();
        this.meetingDate = other.meetingDate == null ? null : other.meetingDate.copy();
        this.resolutionNumber = other.resolutionNumber == null ? null : other.resolutionNumber.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KamalMaryadaPatrakCriteria copy() {
        return new KamalMaryadaPatrakCriteria(this);
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

    public StringFilter getCropLoan() {
        return cropLoan;
    }

    public StringFilter cropLoan() {
        if (cropLoan == null) {
            cropLoan = new StringFilter();
        }
        return cropLoan;
    }

    public void setCropLoan(StringFilter cropLoan) {
        this.cropLoan = cropLoan;
    }

    public StringFilter getKmChart() {
        return kmChart;
    }

    public StringFilter kmChart() {
        if (kmChart == null) {
            kmChart = new StringFilter();
        }
        return kmChart;
    }

    public void setKmChart(StringFilter kmChart) {
        this.kmChart = kmChart;
    }

    public StringFilter getDemand() {
        return demand;
    }

    public StringFilter demand() {
        if (demand == null) {
            demand = new StringFilter();
        }
        return demand;
    }

    public void setDemand(StringFilter demand) {
        this.demand = demand;
    }

    public StringFilter getKmSummary() {
        return kmSummary;
    }

    public StringFilter kmSummary() {
        if (kmSummary == null) {
            kmSummary = new StringFilter();
        }
        return kmSummary;
    }

    public void setKmSummary(StringFilter kmSummary) {
        this.kmSummary = kmSummary;
    }

    public LocalDateFilter getKmDate() {
        return kmDate;
    }

    public LocalDateFilter kmDate() {
        if (kmDate == null) {
            kmDate = new LocalDateFilter();
        }
        return kmDate;
    }

    public void setKmDate(LocalDateFilter kmDate) {
        this.kmDate = kmDate;
    }

    public LocalDateFilter getToKMDate() {
        return toKMDate;
    }

    public LocalDateFilter toKMDate() {
        if (toKMDate == null) {
            toKMDate = new LocalDateFilter();
        }
        return toKMDate;
    }

    public void setToKMDate(LocalDateFilter toKMDate) {
        this.toKMDate = toKMDate;
    }

    public BooleanFilter getCoverPage() {
        return coverPage;
    }

    public BooleanFilter coverPage() {
        if (coverPage == null) {
            coverPage = new BooleanFilter();
        }
        return coverPage;
    }

    public void setCoverPage(BooleanFilter coverPage) {
        this.coverPage = coverPage;
    }

    public StringFilter getCropTypeNumber() {
        return cropTypeNumber;
    }

    public StringFilter cropTypeNumber() {
        if (cropTypeNumber == null) {
            cropTypeNumber = new StringFilter();
        }
        return cropTypeNumber;
    }

    public void setCropTypeNumber(StringFilter cropTypeNumber) {
        this.cropTypeNumber = cropTypeNumber;
    }

    public StringFilter getCropType() {
        return cropType;
    }

    public StringFilter cropType() {
        if (cropType == null) {
            cropType = new StringFilter();
        }
        return cropType;
    }

    public void setCropType(StringFilter cropType) {
        this.cropType = cropType;
    }

    public StringFilter getFromHector() {
        return fromHector;
    }

    public StringFilter fromHector() {
        if (fromHector == null) {
            fromHector = new StringFilter();
        }
        return fromHector;
    }

    public void setFromHector(StringFilter fromHector) {
        this.fromHector = fromHector;
    }

    public StringFilter getToHector() {
        return toHector;
    }

    public StringFilter toHector() {
        if (toHector == null) {
            toHector = new StringFilter();
        }
        return toHector;
    }

    public void setToHector(StringFilter toHector) {
        this.toHector = toHector;
    }

    public StringFilter getDefaulterName() {
        return defaulterName;
    }

    public StringFilter defaulterName() {
        if (defaulterName == null) {
            defaulterName = new StringFilter();
        }
        return defaulterName;
    }

    public void setDefaulterName(StringFilter defaulterName) {
        this.defaulterName = defaulterName;
    }

    public StringFilter getSuchakName() {
        return suchakName;
    }

    public StringFilter suchakName() {
        if (suchakName == null) {
            suchakName = new StringFilter();
        }
        return suchakName;
    }

    public void setSuchakName(StringFilter suchakName) {
        this.suchakName = suchakName;
    }

    public StringFilter getAnumodakName() {
        return anumodakName;
    }

    public StringFilter anumodakName() {
        if (anumodakName == null) {
            anumodakName = new StringFilter();
        }
        return anumodakName;
    }

    public void setAnumodakName(StringFilter anumodakName) {
        this.anumodakName = anumodakName;
    }

    public LocalDateFilter getMeetingDate() {
        return meetingDate;
    }

    public LocalDateFilter meetingDate() {
        if (meetingDate == null) {
            meetingDate = new LocalDateFilter();
        }
        return meetingDate;
    }

    public void setMeetingDate(LocalDateFilter meetingDate) {
        this.meetingDate = meetingDate;
    }

    public StringFilter getResolutionNumber() {
        return resolutionNumber;
    }

    public StringFilter resolutionNumber() {
        if (resolutionNumber == null) {
            resolutionNumber = new StringFilter();
        }
        return resolutionNumber;
    }

    public void setResolutionNumber(StringFilter resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
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
        final KamalMaryadaPatrakCriteria that = (KamalMaryadaPatrakCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cropLoan, that.cropLoan) &&
            Objects.equals(kmChart, that.kmChart) &&
            Objects.equals(demand, that.demand) &&
            Objects.equals(kmSummary, that.kmSummary) &&
            Objects.equals(kmDate, that.kmDate) &&
            Objects.equals(toKMDate, that.toKMDate) &&
            Objects.equals(coverPage, that.coverPage) &&
            Objects.equals(cropTypeNumber, that.cropTypeNumber) &&
            Objects.equals(cropType, that.cropType) &&
            Objects.equals(fromHector, that.fromHector) &&
            Objects.equals(toHector, that.toHector) &&
            Objects.equals(defaulterName, that.defaulterName) &&
            Objects.equals(suchakName, that.suchakName) &&
            Objects.equals(anumodakName, that.anumodakName) &&
            Objects.equals(meetingDate, that.meetingDate) &&
            Objects.equals(resolutionNumber, that.resolutionNumber) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            cropLoan,
            kmChart,
            demand,
            kmSummary,
            kmDate,
            toKMDate,
            coverPage,
            cropTypeNumber,
            cropType,
            fromHector,
            toHector,
            defaulterName,
            suchakName,
            anumodakName,
            meetingDate,
            resolutionNumber,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalMaryadaPatrakCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cropLoan != null ? "cropLoan=" + cropLoan + ", " : "") +
            (kmChart != null ? "kmChart=" + kmChart + ", " : "") +
            (demand != null ? "demand=" + demand + ", " : "") +
            (kmSummary != null ? "kmSummary=" + kmSummary + ", " : "") +
            (kmDate != null ? "kmDate=" + kmDate + ", " : "") +
            (toKMDate != null ? "toKMDate=" + toKMDate + ", " : "") +
            (coverPage != null ? "coverPage=" + coverPage + ", " : "") +
            (cropTypeNumber != null ? "cropTypeNumber=" + cropTypeNumber + ", " : "") +
            (cropType != null ? "cropType=" + cropType + ", " : "") +
            (fromHector != null ? "fromHector=" + fromHector + ", " : "") +
            (toHector != null ? "toHector=" + toHector + ", " : "") +
            (defaulterName != null ? "defaulterName=" + defaulterName + ", " : "") +
            (suchakName != null ? "suchakName=" + suchakName + ", " : "") +
            (anumodakName != null ? "anumodakName=" + anumodakName + ", " : "") +
            (meetingDate != null ? "meetingDate=" + meetingDate + ", " : "") +
            (resolutionNumber != null ? "resolutionNumber=" + resolutionNumber + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
