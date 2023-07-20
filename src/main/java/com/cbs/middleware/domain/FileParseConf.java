package com.cbs.middleware.domain;

public class FileParseConf {

    private String bankCode;
    private String bankName;
    private String branchCode;
    private String branchName;
    private String pacsCode;
    private String pacsName;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getPacsCode() {
        return pacsCode;
    }

    public void setPacsCode(String pacsCode) {
        this.pacsCode = pacsCode;
    }

    public String getPacsName() {
        return pacsName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
}
