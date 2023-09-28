package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A KamalMaryadaPatrak.
 */
@Entity
@Table(name = "kamal_maryada_patrak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KamalMaryadaPatrak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crop_loan")
    private String cropLoan;

    @Column(name = "km_chart")
    private String kmChart;

    @Column(name = "demand")
    private String demand;

    @Column(name = "km_summary")
    private String kmSummary;

    @Column(name = "km_date")
    private LocalDate kmDate;

    @Column(name = "to_km_date")
    private LocalDate toKMDate;

    @Column(name = "cover_page")
    private Boolean coverPage;

    @Column(name = "crop_type_number")
    private String cropTypeNumber;

    @Column(name = "crop_type")
    private String cropType;

    @Column(name = "from_hector")
    private String fromHector;

    @Column(name = "to_hector")
    private String toHector;

    @Column(name = "defaulter_name")
    private String defaulterName;

    @Column(name = "suchak_name")
    private String suchakName;

    @Column(name = "anumodak_name")
    private String anumodakName;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "resolution_number")
    private String resolutionNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KamalMaryadaPatrak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropLoan() {
        return this.cropLoan;
    }

    public KamalMaryadaPatrak cropLoan(String cropLoan) {
        this.setCropLoan(cropLoan);
        return this;
    }

    public void setCropLoan(String cropLoan) {
        this.cropLoan = cropLoan;
    }

    public String getKmChart() {
        return this.kmChart;
    }

    public KamalMaryadaPatrak kmChart(String kmChart) {
        this.setKmChart(kmChart);
        return this;
    }

    public void setKmChart(String kmChart) {
        this.kmChart = kmChart;
    }

    public String getDemand() {
        return this.demand;
    }

    public KamalMaryadaPatrak demand(String demand) {
        this.setDemand(demand);
        return this;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getKmSummary() {
        return this.kmSummary;
    }

    public KamalMaryadaPatrak kmSummary(String kmSummary) {
        this.setKmSummary(kmSummary);
        return this;
    }

    public void setKmSummary(String kmSummary) {
        this.kmSummary = kmSummary;
    }

    public LocalDate getKmDate() {
        return this.kmDate;
    }

    public KamalMaryadaPatrak kmDate(LocalDate kmDate) {
        this.setKmDate(kmDate);
        return this;
    }

    public void setKmDate(LocalDate kmDate) {
        this.kmDate = kmDate;
    }

    public LocalDate getToKMDate() {
        return this.toKMDate;
    }

    public KamalMaryadaPatrak toKMDate(LocalDate toKMDate) {
        this.setToKMDate(toKMDate);
        return this;
    }

    public void setToKMDate(LocalDate toKMDate) {
        this.toKMDate = toKMDate;
    }

    public Boolean getCoverPage() {
        return this.coverPage;
    }

    public KamalMaryadaPatrak coverPage(Boolean coverPage) {
        this.setCoverPage(coverPage);
        return this;
    }

    public void setCoverPage(Boolean coverPage) {
        this.coverPage = coverPage;
    }

    public String getCropTypeNumber() {
        return this.cropTypeNumber;
    }

    public KamalMaryadaPatrak cropTypeNumber(String cropTypeNumber) {
        this.setCropTypeNumber(cropTypeNumber);
        return this;
    }

    public void setCropTypeNumber(String cropTypeNumber) {
        this.cropTypeNumber = cropTypeNumber;
    }

    public String getCropType() {
        return this.cropType;
    }

    public KamalMaryadaPatrak cropType(String cropType) {
        this.setCropType(cropType);
        return this;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getFromHector() {
        return this.fromHector;
    }

    public KamalMaryadaPatrak fromHector(String fromHector) {
        this.setFromHector(fromHector);
        return this;
    }

    public void setFromHector(String fromHector) {
        this.fromHector = fromHector;
    }

    public String getToHector() {
        return this.toHector;
    }

    public KamalMaryadaPatrak toHector(String toHector) {
        this.setToHector(toHector);
        return this;
    }

    public void setToHector(String toHector) {
        this.toHector = toHector;
    }

    public String getDefaulterName() {
        return this.defaulterName;
    }

    public KamalMaryadaPatrak defaulterName(String defaulterName) {
        this.setDefaulterName(defaulterName);
        return this;
    }

    public void setDefaulterName(String defaulterName) {
        this.defaulterName = defaulterName;
    }

    public String getSuchakName() {
        return this.suchakName;
    }

    public KamalMaryadaPatrak suchakName(String suchakName) {
        this.setSuchakName(suchakName);
        return this;
    }

    public void setSuchakName(String suchakName) {
        this.suchakName = suchakName;
    }

    public String getAnumodakName() {
        return this.anumodakName;
    }

    public KamalMaryadaPatrak anumodakName(String anumodakName) {
        this.setAnumodakName(anumodakName);
        return this;
    }

    public void setAnumodakName(String anumodakName) {
        this.anumodakName = anumodakName;
    }

    public LocalDate getMeetingDate() {
        return this.meetingDate;
    }

    public KamalMaryadaPatrak meetingDate(LocalDate meetingDate) {
        this.setMeetingDate(meetingDate);
        return this;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getResolutionNumber() {
        return this.resolutionNumber;
    }

    public KamalMaryadaPatrak resolutionNumber(String resolutionNumber) {
        this.setResolutionNumber(resolutionNumber);
        return this;
    }

    public void setResolutionNumber(String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KamalMaryadaPatrak)) {
            return false;
        }
        return id != null && id.equals(((KamalMaryadaPatrak) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KamalMaryadaPatrak{" +
            "id=" + getId() +
            ", cropLoan='" + getCropLoan() + "'" +
            ", kmChart='" + getKmChart() + "'" +
            ", demand='" + getDemand() + "'" +
            ", kmSummary='" + getKmSummary() + "'" +
            ", kmDate='" + getKmDate() + "'" +
            ", toKMDate='" + getToKMDate() + "'" +
            ", coverPage='" + getCoverPage() + "'" +
            ", cropTypeNumber='" + getCropTypeNumber() + "'" +
            ", cropType='" + getCropType() + "'" +
            ", fromHector='" + getFromHector() + "'" +
            ", toHector='" + getToHector() + "'" +
            ", defaulterName='" + getDefaulterName() + "'" +
            ", suchakName='" + getSuchakName() + "'" +
            ", anumodakName='" + getAnumodakName() + "'" +
            ", meetingDate='" + getMeetingDate() + "'" +
            ", resolutionNumber='" + getResolutionNumber() + "'" +
            "}";
    }
}
