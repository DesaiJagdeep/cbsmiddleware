package com.cbs.middleware.domain;

import java.util.List;

public class ClaimBatchData {

    private static final long serialVersionUID = 1L;
    private String batchId;
    private String financialYear;
    private List<ClaimApplicationPayload> applications;

    // GETTERS AND SETTERS

    public List<ClaimApplicationPayload> getApplications() {
        return applications;
    }

    public void setApplications(List<ClaimApplicationPayload> applications) {
        this.applications = applications;
    }

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



    @Override
    public String toString() {
        return "BatchData [batchId=" + batchId + ", financialYear=" + financialYear + ", applications=" + applications + "]";
    }
}
