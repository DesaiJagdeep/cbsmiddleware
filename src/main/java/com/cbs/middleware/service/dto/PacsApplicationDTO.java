package com.cbs.middleware.service.dto;

public class PacsApplicationDTO {
    Long totalApplication;
    Long validationError;
    Long kccAccepted;
    Long kccRejected;
    Long kccPending;
    String pacsName;
    Long  pacsCode;

    public PacsApplicationDTO() {
    }

    public PacsApplicationDTO(Long totalApplication, Long validationError, Long kccAccepted, Long kccRejected, Long kccPending, String pacsName, Long pacsCode) {
        this.totalApplication = totalApplication;
        this.validationError = validationError;
        this.kccAccepted = kccAccepted;
        this.kccRejected = kccRejected;
        this.kccPending = kccPending;
        this.pacsName = pacsName;
        this.pacsCode = pacsCode;
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

    public String getPacsName() {
        return pacsName;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public Long getPacsCode() {
        return pacsCode;
    }

    public void setPacsCode(Long pacsCode) {
        this.pacsCode = pacsCode;
    }
}
