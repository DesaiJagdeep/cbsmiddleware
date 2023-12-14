package com.cbs.middleware.service.dto;

public class TalukaApplicationDTO {

    Long totalApplication;
    Long validationError;
    Long kccAccepted;
    Long kccRejected;
    Long kccPending;
    String branchName;
    Long schemeWiseBranchCode;
    Long pendingApprovalFromBranchAdmin;
    Long pendingApprovalFromBranchUser;

    public TalukaApplicationDTO() {
    }

    public TalukaApplicationDTO(Long totalApplication, Long validationError, Long kccAccepted, Long kccRejected, Long kccPending, String branchName, Long schemeWiseBranchCode, Long pendingApprovalFromBranchAdmin, Long pendingApprovalFromBranchUser) {
        this.totalApplication = totalApplication;
        this.validationError = validationError;
        this.kccAccepted = kccAccepted;
        this.kccRejected = kccRejected;
        this.kccPending = kccPending;
        this.branchName = branchName;
        this.schemeWiseBranchCode = schemeWiseBranchCode;
        this.pendingApprovalFromBranchAdmin = pendingApprovalFromBranchAdmin;
        this.pendingApprovalFromBranchUser = pendingApprovalFromBranchUser;
    }

    public Long getTotalApplication() {
        return totalApplication;
    }

    public void setTotalApplication(Long totalApplication) {
        this.totalApplication = totalApplication;
    }

    public Long getValidationError() {
        return validationError;
    }

    public void setValidationError(Long validationError) {
        this.validationError = validationError;
    }

    public Long getKccAccepted() {
        return kccAccepted;
    }

    public void setKccAccepted(Long kccAccepted) {
        this.kccAccepted = kccAccepted;
    }

    public Long getKccRejected() {
        return kccRejected;
    }

    public void setKccRejected(Long kccRejected) {
        this.kccRejected = kccRejected;
    }

    public Long getKccPending() {
        return kccPending;
    }

    public void setKccPending(Long kccPending) {
        this.kccPending = kccPending;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getSchemeWiseBranchCode() {
        return schemeWiseBranchCode;
    }

    public void setSchemeWiseBranchCode(Long schemeWiseBranchCode) {
        this.schemeWiseBranchCode = schemeWiseBranchCode;
    }

    public Long getPendingApprovalFromBranchAdmin() {
        return pendingApprovalFromBranchAdmin;
    }

    public void setPendingApprovalFromBranchAdmin(Long pendingApprovalFromBranchAdmin) {
        this.pendingApprovalFromBranchAdmin = pendingApprovalFromBranchAdmin;
    }

    public Long getPendingApprovalFromBranchUser() {
        return pendingApprovalFromBranchUser;
    }

    public void setPendingApprovalFromBranchUser(Long pendingApprovalFromBranchUser) {
        this.pendingApprovalFromBranchUser = pendingApprovalFromBranchUser;
    }
}
