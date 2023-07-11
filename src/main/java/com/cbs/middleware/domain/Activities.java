package com.cbs.middleware.domain;

import java.util.List;

public class Activities {

    /*
     * { "activityType": 1,
     * "loanSanctionedDate": "2022-06-17",
     * "loanSanctionedAmount": 6666,
     * "activityRows": []
     * }
     */

    private Long activityType;
    private String loanSanctionedDate;
    private Long loanSanctionedAmount;
    private List<ActivityRows> activityRows;

    // GETTERS AND SETTERS

    public Long getActivityType() {
        return activityType;
    }

    public void setActivityType(Long activityType) {
        this.activityType = activityType;
    }

    public String getLoanSanctionedDate() {
        return loanSanctionedDate;
    }

    public void setLoanSanctionedDate(String loanSanctionedDate) {
        this.loanSanctionedDate = loanSanctionedDate;
    }

    public Long getLoanSanctionedAmount() {
        return loanSanctionedAmount;
    }

    public void setLoanSanctionedAmount(Long loanSanctionedAmount) {
        this.loanSanctionedAmount = loanSanctionedAmount;
    }

    public List<ActivityRows> getActivityRows() {
        return activityRows;
    }

    public void setActivityRows(List<ActivityRows> activityRows) {
        this.activityRows = activityRows;
    }
}
