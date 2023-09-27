package com.cbs.middleware.domain;

import org.springframework.stereotype.Component;

@Component
public class One01ReportParam {

    private String talukaName;
    private String bankName;
    private String sabhasadName;
    private boolean is101Select;

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSabhasadName() {
        return sabhasadName;
    }

    public void setSabhasadName(String sabhasadName) {
        this.sabhasadName = sabhasadName;
    }

    public boolean isIs101Select() {
        return is101Select;
    }

    public void setIs101Select(boolean is101Select) {
        this.is101Select = is101Select;
    }
}
