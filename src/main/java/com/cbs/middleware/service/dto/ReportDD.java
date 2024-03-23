package com.cbs.middleware.service.dto;

import com.cbs.middleware.domain.KamalSociety;

import java.util.List;

public class ReportDD {
    public String branchName;
    public List<KamalSociety> kamalSocieties;

    public ReportDD(String branchName, List<KamalSociety> kamalSocieties) {
        this.branchName = branchName;
        this.kamalSocieties = kamalSocieties;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<KamalSociety> getKamalSocieties() {
        return kamalSocieties;
    }

    public void setKamalSocieties(List<KamalSociety> kamalSocieties) {
        this.kamalSocieties = kamalSocieties;
    }
}
