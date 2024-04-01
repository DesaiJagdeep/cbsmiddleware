package com.cbs.middleware.service.dto;

import java.math.BigInteger;

public class BranchDTO {

    private BigInteger branchId;
    private String branchName;

    public BigInteger getBranchId() {
        return branchId;
    }

    public void setBranchId(BigInteger branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
