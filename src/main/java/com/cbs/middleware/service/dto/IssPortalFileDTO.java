package com.cbs.middleware.service.dto;

public class IssPortalFileDTO {

    private String taluka;
    private String branch;
    private String pacs;
    private int  totalApplications;
    private int  validationErrors;
    private int  kCCRejected;
    private int  kCCAccepted;
    private int  kCCPending;

    public IssPortalFileDTO() {
    }

    public IssPortalFileDTO(String taluka, String branch, String pacs, int totalApplications, int validationErrors, int kCCRejected, int kCCAccepted, int kCCPending) {
        this.taluka = taluka;
        this.branch = branch;
        this.pacs = pacs;
        this.totalApplications = totalApplications;
        this.validationErrors = validationErrors;
        this.kCCRejected = kCCRejected;
        this.kCCAccepted = kCCAccepted;
        this.kCCPending = kCCPending;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPacs() {
        return pacs;
    }

    public void setPacs(String pacs) {
        this.pacs = pacs;
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
}
