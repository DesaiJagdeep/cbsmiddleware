package com.cbs.middleware.domain;

import java.io.Serializable;
import java.util.List;

public class BatchData implements Serializable {

    /*
     * "batchId": "29082202301220", "financialYear": "2022-2023",
     */

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String batchId;
    private String financialYear;
    private List<ApplicationPayload> applications;

    // GETTERS AND SETTERS

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public List<ApplicationPayload> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationPayload> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "BatchData [batchId=" + batchId + ", financialYear=" + financialYear + ", applications=" + applications + "]";
    }
}
