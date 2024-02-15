package com.cbs.middleware.service.dto;

import java.time.Instant;

public class NewKmReportPayload {
    private String financialYear;
    private String pacsNumber;
    private Instant kmDate;
    private String templateName;
    private Instant kmFromDate;
    private Instant kmToDate;

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getPacsNumber() {
        return pacsNumber;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public Instant getKmDate() {
        return kmDate;
    }

    public void setKmDate(Instant kmDate) {
        this.kmDate = kmDate;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Instant getKmFromDate() {
        return kmFromDate;
    }

    public void setKmFromDate(Instant kmFromDate) {
        this.kmFromDate = kmFromDate;
    }

    public Instant getKmToDate() {
        return kmToDate;
    }

    public void setKmToDate(Instant kmToDate) {
        this.kmToDate = kmToDate;
    }
}
