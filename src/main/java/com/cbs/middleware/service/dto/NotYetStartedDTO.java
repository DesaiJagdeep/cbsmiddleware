package com.cbs.middleware.service.dto;

public class NotYetStartedDTO {

    private String pacsNumber;
    private String pacsName;
    private String branchName;
    private String talukaName;

    public NotYetStartedDTO() {
    }

    public NotYetStartedDTO(String pacsNumber, String pacsName, String branchName, String talukaName) {
        this.pacsNumber = pacsNumber;
        this.pacsName = pacsName;
        this.branchName = branchName;
        this.talukaName = talukaName;
    }

    public String getPacsNumber() {
        return pacsNumber;
    }

    public void setPacsNumber(String pacsNumber) {
        this.pacsNumber = pacsNumber;
    }

    public String getPacsName() {
        return pacsName;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }
}
