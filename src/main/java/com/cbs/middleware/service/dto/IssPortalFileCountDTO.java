package com.cbs.middleware.service.dto;
import java.io.Serializable;

public class IssPortalFileCountDTO {

    private int  totalApplications;
    private int   validationErrors;
    private int     kCCSubmitted;
    private int    kCCAccepted;
    private int    kCCErrors;

    public IssPortalFileCountDTO(int totalApplications, int validationErrors, int kCCSubmitted, int kCCAccepted, int kCCErrors) {
        this.totalApplications = totalApplications;
        this.validationErrors = validationErrors;
        this.kCCSubmitted = kCCSubmitted;
        this.kCCAccepted = kCCAccepted;
        this.kCCErrors = kCCErrors;
    }

    public IssPortalFileCountDTO() {
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

    public int getkCCSubmitted() {
        return kCCSubmitted;
    }

    public void setkCCSubmitted(int kCCSubmitted) {
        this.kCCSubmitted = kCCSubmitted;
    }

    public int getkCCAccepted() {
        return kCCAccepted;
    }

    public void setkCCAccepted(int kCCAccepted) {
        this.kCCAccepted = kCCAccepted;
    }

    public int getkCCErrors() {
        return kCCErrors;
    }

    public void setkCCErrors(int kCCErrors) {
        this.kCCErrors = kCCErrors;
    }
}

