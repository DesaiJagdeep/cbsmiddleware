package com.cbs.middleware.service.dto;


public class SummaryReportUpdateDTO {
    private Integer distinctAadharCount;
    private Integer totalRecoveryAccount;
    private Long totalDisburseAmount;
    private Long totalRecoveryAmount;
    private Integer upto50000;
    private String socialCategory;
    private String gender;
    private Double totalInterest15;
    private Double totalInterest25;
    private Double totalInterest3;



//Total Loan Amount & accounts
    public SummaryReportUpdateDTO(Integer distinctAadharCount, Long totalDisburseAmount, Long totalRecoveryAmount, Integer upto50000) {
        this.distinctAadharCount = distinctAadharCount;
        this.totalDisburseAmount = totalDisburseAmount;
        this.totalRecoveryAmount = totalRecoveryAmount;
        this.upto50000 = upto50000;

    }
    //Total recovery accounts
    public SummaryReportUpdateDTO(Integer distinctAadharCount, Integer upto50000) {

        this.distinctAadharCount = distinctAadharCount;
        this.upto50000 = upto50000;

    }

    //Cast wise Interest Amounts & accounts - center first
    public SummaryReportUpdateDTO(Integer distinctAadharCount,Double totalInterest15,Double totalInterest25,Double totalInterest3, String socialCategory,Integer upto50000) {

        this.distinctAadharCount = distinctAadharCount;
        this.totalInterest15 = totalInterest15;
        this.totalInterest25 = totalInterest25;
        this.totalInterest3 = totalInterest3;
        this.upto50000 = upto50000;
        this.socialCategory = socialCategory;


    }

    //Gender wise Interest Amounts & accounts - center first
    public SummaryReportUpdateDTO(Integer distinctAadharCount, Double totalInterest15,Double totalInterest25,Double totalInterest3,Integer upto50000,String gender) {

        this.distinctAadharCount = distinctAadharCount;
        this.totalInterest15 = totalInterest15;
        this.totalInterest25 = totalInterest25;
        this.totalInterest3 = totalInterest3;
        this.gender = gender;
        this.upto50000 = upto50000;


    }
    //FarmerType wise Interest Amounts & accounts - center first
    public SummaryReportUpdateDTO(Integer distinctAadharCount, Double totalInterest15,Double totalInterest25,Double totalInterest3,Integer upto50000) {

        this.distinctAadharCount = distinctAadharCount;
        this.totalInterest15 = totalInterest15;
        this.totalInterest25 = totalInterest25;
        this.totalInterest3 = totalInterest3;
        this.upto50000 = upto50000;

    }

    public Integer getDistinctAadharCount() {
        return distinctAadharCount;
    }

    public void setDistinctAadharCount(Integer distinctAadharCount) {
        this.distinctAadharCount = distinctAadharCount;
    }

    public Long getTotalDisburseAmount() {
        return totalDisburseAmount;
    }

    public void setTotalDisburseAmount(Long totalDisburseAmount) {
        this.totalDisburseAmount = totalDisburseAmount;
    }

    public Long getTotalRecoveryAmount() {
        return totalRecoveryAmount;
    }

    public void setTotalRecoveryAmount(Long totalRecoveryAmount) {
        this.totalRecoveryAmount = totalRecoveryAmount;
    }

    public Integer getTotalRecoveryAccount() {
        return totalRecoveryAccount;
    }

    public void setTotalRecoveryAccount(Integer totalRecoveryAccount) {
        this.totalRecoveryAccount = totalRecoveryAccount;
    }

    public Integer getUpto50000() {
        return upto50000;
    }

    public void setUpto50000(Integer upto50000) {
        this.upto50000 = upto50000;
    }

    public String getSocialCategory() {
        return socialCategory;
    }

    public void setSocialCategory(String socialCategory) {
        this.socialCategory = socialCategory;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getTotalInterest15() {
        return totalInterest15;
    }

    public void setTotalInterest15(Double totalInterest15) {
        this.totalInterest15 = totalInterest15;
    }

    public Double getTotalInterest25() {
        return totalInterest25;
    }

    public void setTotalInterest25(Double totalInterest25) {
        this.totalInterest25 = totalInterest25;
    }

    public Double getTotalInterest3() {
        return totalInterest3;
    }

    public void setTotalInterest3(Double totalInterest3) {
        this.totalInterest3 = totalInterest3;
    }


}
