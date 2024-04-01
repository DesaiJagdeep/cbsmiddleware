package com.cbs.middleware.service.dto;

import java.math.BigInteger;
import java.util.List;

public class TalukaDTO {
    private BigInteger talukaId;
    private String talukaName;

    private List<BranchDTO> branch;


    public List<BranchDTO> getBranch() {
        return branch;
    }

    public void setBranch(List<BranchDTO> branch) {
        this.branch = branch;
    }

    public BigInteger getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(BigInteger talukaId) {
        this.talukaId = talukaId;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }
}
