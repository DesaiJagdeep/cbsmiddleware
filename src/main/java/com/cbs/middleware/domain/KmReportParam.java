package com.cbs.middleware.domain;

import org.springframework.stereotype.Component;

@Component
public class KmReportParam {

    private String cropLoan;
    private String kmChart;
    private String demand;
    private String kmSummary;
    private String kmDate;
    private String toKMDate;
    private boolean coverPage;
    private String cropTypeNumber;
    private String cropType;
    private String fromHector;
    private String toHector;
    private String defaulterName;
    private String suchakName;
    private String anumodakName;
    private String meetingDate;
    private String resolutionNumber;

    public String getCropLoan() {
        return cropLoan;
    }

    public void setCropLoan(String cropLoan) {
        this.cropLoan = cropLoan;
    }

    public String getKmChart() {
        return kmChart;
    }

    public void setKmChart(String kmChart) {
        this.kmChart = kmChart;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getKmSummary() {
        return kmSummary;
    }

    public void setKmSummary(String kmSummary) {
        this.kmSummary = kmSummary;
    }

    public String getKmDate() {
        return kmDate;
    }

    public void setKmDate(String kmDate) {
        this.kmDate = kmDate;
    }

    public String getToKMDate() {
        return toKMDate;
    }

    public void setToKMDate(String toKMDate) {
        this.toKMDate = toKMDate;
    }

    public boolean isCoverPage() {
        return coverPage;
    }

    public void setCoverPage(boolean coverPage) {
        this.coverPage = coverPage;
    }

    public String getCropTypeNumber() {
        return cropTypeNumber;
    }

    public void setCropTypeNumber(String cropTypeNumber) {
        this.cropTypeNumber = cropTypeNumber;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getFromHector() {
        return fromHector;
    }

    public void setFromHector(String fromHector) {
        this.fromHector = fromHector;
    }

    public String getToHector() {
        return toHector;
    }

    public void setToHector(String toHector) {
        this.toHector = toHector;
    }

    public String getDefaulterName() {
        return defaulterName;
    }

    public void setDefaulterName(String defaulterName) {
        this.defaulterName = defaulterName;
    }

    public String getSuchakName() {
        return suchakName;
    }

    public void setSuchakName(String suchakName) {
        this.suchakName = suchakName;
    }

    public String getAnumodakName() {
        return anumodakName;
    }

    public void setAnumodakName(String anumodakName) {
        this.anumodakName = anumodakName;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getResolutionNumber() {
        return resolutionNumber;
    }

    public void setResolutionNumber(String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }
}
