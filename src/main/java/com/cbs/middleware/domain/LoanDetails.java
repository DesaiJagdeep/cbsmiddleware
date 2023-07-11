package com.cbs.middleware.domain;

public class LoanDetails {

    /*
     * { "kccLoanSanctionedDate": "2022-06-17", "kccLimitSanctionedAmount": 5521,
     * "kccDrawingLimitForFY": 5000 }
     */

    private String kccLoanSanctionedDate;
    private Long kccLimitSanctionedAmount;
    private Long kccDrawingLimitForFY;

    // GETTERS AND SETTERS
    public String getKccLoanSanctionedDate() {
        return kccLoanSanctionedDate;
    }

    public void setKccLoanSanctionedDate(String kccLoanSanctionedDate) {
        this.kccLoanSanctionedDate = kccLoanSanctionedDate;
    }

    public Long getKccLimitSanctionedAmount() {
        return kccLimitSanctionedAmount;
    }

    public void setKccLimitSanctionedAmount(Long kccLimitSanctionedAmount) {
        this.kccLimitSanctionedAmount = kccLimitSanctionedAmount;
    }

    public Long getKccDrawingLimitForFY() {
        return kccDrawingLimitForFY;
    }

    public void setKccDrawingLimitForFY(Long kccDrawingLimitForFY) {
        this.kccDrawingLimitForFY = kccDrawingLimitForFY;
    }
}
