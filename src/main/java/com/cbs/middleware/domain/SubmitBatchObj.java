package com.cbs.middleware.domain;

import org.springframework.stereotype.Component;

@Component
public class SubmitBatchObj {

    private String financialYear;

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }
}
