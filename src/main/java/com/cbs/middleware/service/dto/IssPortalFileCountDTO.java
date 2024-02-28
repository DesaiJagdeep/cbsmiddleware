package com.cbs.middleware.service.dto;
import java.io.Serializable;

public class IssPortalFileCountDTO {

    private int  totalApplications;
    private int   validationErrors;
    private int     kCCRejected;
    private int    kCCAccepted;
    private int totalFarmers;

    private int    kCCPending;
    private int    pendingToSubmit;
    private int    submitted;


    public IssPortalFileCountDTO() {
    }

    public IssPortalFileCountDTO(int totalApplications, int validationErrors, int kCCRejected, int kCCAccepted, int kCCPending,int totalFarmers) {
        this.totalApplications = totalApplications;
        this.validationErrors = validationErrors;
        this.kCCRejected = kCCRejected;
        this.kCCAccepted = kCCAccepted;
        this.kCCPending=kCCPending;
        this.totalFarmers=totalFarmers;
    }

    public int getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(int totalApplications) {
        this.totalApplications = totalApplications;
    }

    public int getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(int validationErrors) {
        this.validationErrors = validationErrors;
    }

    public int getkCCRejected() {
        return kCCRejected;
    }

    public void setkCCRejected(int kCCRejected) {
        this.kCCRejected = kCCRejected;
    }

    public int getkCCAccepted() {
        return kCCAccepted;
    }

    public void setkCCAccepted(int kCCAccepted) {
        this.kCCAccepted = kCCAccepted;
    }

    public int getkCCPending() {
        return kCCPending;
    }

    public void setkCCPending(int kCCPending) {
        this.kCCPending = kCCPending;
    }

    public int getTotalFarmers() {
        return totalFarmers;
    }
    public void setTotalFarmers(int totalFarmers) {
        this.totalFarmers = totalFarmers;
    }

    public int getPendingToSubmit() {
        return pendingToSubmit;
    }

    public void setPendingToSubmit(int pendingToSubmit) {
        this.pendingToSubmit = pendingToSubmit;
    }

    public int getSubmitted() {
        return submitted;
    }

    public void setSubmitted(int submitted) {
        this.submitted = submitted;
    }
}

