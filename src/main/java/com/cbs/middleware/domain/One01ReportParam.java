package com.cbs.middleware.domain;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class One01ReportParam {

	private String financialYear;
    private String talukaName;
    private String bankName;
    private String sabhasadName;
    private String oneZeroOneOption;
    private LocalDate firstNoticeDate;
    private boolean isFirstNoticeDateChange;
    
    private String courtCaseSettingCode;

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getBankName() {
        return bankName;
    }

    public boolean isFirstNoticeDateChange() {
		return isFirstNoticeDateChange;
	}

	public void setFirstNoticeDateChange(boolean isFirstNoticeDateChange) {
		this.isFirstNoticeDateChange = isFirstNoticeDateChange;
	}

	public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSabhasadName() {
        return sabhasadName;
    } 

    public LocalDate getFirstNoticeDate() {
		return firstNoticeDate;
	}

	public void setFirstNoticeDate(LocalDate firstNoticeDate) {
		this.firstNoticeDate = firstNoticeDate;
	}

	public void setSabhasadName(String sabhasadName) {
        this.sabhasadName = sabhasadName;
    }

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getOneZeroOneOption() {
		return oneZeroOneOption;
	}

	public void setOneZeroOneOption(String oneZeroOneOption) {
		this.oneZeroOneOption = oneZeroOneOption;
	}

	public String getCourtCaseSettingCode() {
		return courtCaseSettingCode;
	}

	public void setCourtCaseSettingCode(String courtCaseSettingCode) {
		this.courtCaseSettingCode = courtCaseSettingCode;
	}
	
	

   
}
