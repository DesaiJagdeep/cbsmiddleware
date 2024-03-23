package com.cbs.middleware.service.dto;

import java.time.Instant;

public class ClaimReportsDTO {
    private String financialYear;
    private String templateName;

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }


    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
