package com.cbs.middleware.domain;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class One01ReportParam {

	private String financialYear;
    private String talukaName;
    private String bankName;
    private Long courtCaseId;
    private String oneZeroOneOption;
    private Instant firstNoticeDate;
    private Instant secondNoticeDate;
    private Instant noticeDate;

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

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

    public Long getCourtCaseId() {
        return courtCaseId;
    }

    public void setCourtCaseId(Long courtCaseId) {
        this.courtCaseId = courtCaseId;
    }

    public String getOneZeroOneOption() {
        return oneZeroOneOption;
    }

    public void setOneZeroOneOption(String oneZeroOneOption) {
        this.oneZeroOneOption = oneZeroOneOption;
    }

    public Instant getFirstNoticeDate() {
        return firstNoticeDate;
    }

    public void setFirstNoticeDate(Instant firstNoticeDate) {
        this.firstNoticeDate = firstNoticeDate;
    }

    public Instant getSecondNoticeDate() {
        return secondNoticeDate;
    }

    public void setSecondNoticeDate(Instant secondNoticeDate) {
        this.secondNoticeDate = secondNoticeDate;
    }

    public Instant getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Instant noticeDate) {
        this.noticeDate = noticeDate;
    }
}
