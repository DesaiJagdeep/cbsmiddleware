package com.cbs.middleware.domain;

import java.util.List;

public class BankBranchMasterMapper {

    private String bankCode;
    private String bankName;
    private List<BranchDetailsMapper> branches;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<BranchDetailsMapper> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDetailsMapper> branches) {
        this.branches = branches;
    }
}
