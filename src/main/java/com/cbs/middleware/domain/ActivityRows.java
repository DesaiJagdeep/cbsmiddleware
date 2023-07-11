package com.cbs.middleware.domain;

public class ActivityRows {

    /*
     * { "landVillage": "413731", "cropCode": "011507800", "surveyNumber": "123",
     * "khataNumber": "12", "landArea": 5, "landType": 2, "season": 1
     * "plantationCode": "021400400", "plantationArea": 1 "liveStockType": 1,
     * "liveStockCode": 2, "unitCount": 23 "inlandType": 1, "totalUnits": 12,
     * "totalArea": 2 "marineType": 3, }
     */

    private String landVillage;
    private String cropCode;
    private String surveyNumber;
    private String khataNumber;
    private Float landArea;
    private Integer landType;
    private Integer season;
    private String plantationCode;
    private Long plantationArea;
    private Long liveStockType;
    private Long liveStockCode;
    private Long unitCount;
    private Long inlandType;
    private Long totalUnits;
    private Long totalArea;
    private Long marineType;

    // GETTERS AND SETTERS
    public String getLandVillage() {
        return landVillage;
    }

    public void setLandVillage(String landVillage) {
        this.landVillage = landVillage;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(String surveyNumber) {
        this.surveyNumber = surveyNumber;
    }

    public String getKhataNumber() {
        return khataNumber;
    }

    public void setKhataNumber(String khataNumber) {
        this.khataNumber = khataNumber;
    }

    public Float getLandArea() {
        return landArea;
    }

    public void setLandArea(Float landArea) {
        this.landArea = landArea;
    }

    public Integer getLandType() {
        return landType;
    }

    public void setLandType(Integer landType) {
        this.landType = landType;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getPlantationCode() {
        return plantationCode;
    }

    public void setPlantationCode(String plantationCode) {
        this.plantationCode = plantationCode;
    }

    public Long getPlantationArea() {
        return plantationArea;
    }

    public void setPlantationArea(Long plantationArea) {
        this.plantationArea = plantationArea;
    }

    public Long getLiveStockType() {
        return liveStockType;
    }

    public void setLiveStockType(Long liveStockType) {
        this.liveStockType = liveStockType;
    }

    public Long getLiveStockCode() {
        return liveStockCode;
    }

    public void setLiveStockCode(Long liveStockCode) {
        this.liveStockCode = liveStockCode;
    }

    public Long getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Long unitCount) {
        this.unitCount = unitCount;
    }

    public Long getInlandType() {
        return inlandType;
    }

    public void setInlandType(Long inlandType) {
        this.inlandType = inlandType;
    }

    public Long getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(Long totalUnits) {
        this.totalUnits = totalUnits;
    }

    public Long getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Long totalArea) {
        this.totalArea = totalArea;
    }

    public Long getMarineType() {
        return marineType;
    }

    public void setMarineType(Long marineType) {
        this.marineType = marineType;
    }
}
