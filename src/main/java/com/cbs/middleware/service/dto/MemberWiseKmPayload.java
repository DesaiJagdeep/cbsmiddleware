package com.cbs.middleware.service.dto;

import java.time.Instant;

public class MemberWiseKmPayload {

    private String financialYear;
    private Instant kmDate;
    private String templateName;


    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
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
}
