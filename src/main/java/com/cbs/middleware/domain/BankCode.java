package com.cbs.middleware.domain;

import org.springframework.stereotype.Component;

@Component
public class BankCode {

    private String bankName;
    private String branchName;
    private String packsName;

    private String bankCode;
    private String schemeWiseBranchCode;
    private String packsCode;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPacksName() {
        return packsName;
    }

    public void setPacksName(String packsName) {
        this.packsName = packsName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSchemeWiseBranchCode() {
        return schemeWiseBranchCode;
    }

    public void setSchemeWiseBranchCode(String schemeWiseBranchCode) {
        this.schemeWiseBranchCode = schemeWiseBranchCode;
    }

    public String getPacksCode() {
        return packsCode;
    }

    public void setPacksCode(String packsCode) {
        this.packsCode = packsCode;
    }

    @Override
    public String toString() {
        return (
            "BankCode [bankName=" +
            bankName +
            ", branchName=" +
            branchName +
            ", packsName=" +
            packsName +
            ", bankCode=" +
            bankCode +
            ", schemeWiseBranchCode=" +
            schemeWiseBranchCode +
            ", packsCode=" +
            packsCode +
            "]"
        );
    }
}
