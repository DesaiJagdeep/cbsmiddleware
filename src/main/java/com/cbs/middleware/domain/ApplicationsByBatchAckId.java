package com.cbs.middleware.domain;

public class ApplicationsByBatchAckId {

    /*
     * "uniqueId": "1707231561234598695", "recordStatus": "1", "applicationStatus":
     * 0, "errors":
     * "activityType is Non Integer; Activity type OF Khata number is NOT proper; Activity type OF surveyNumber number is NOT proper; Activity type OF cropCode number is NOT proper; Activity type OR landArea number is NOT proper; Activity type OF landType is NOT proper; Activity type OF season is NOT proper; Activity type OF plantationCode is NOT proper; Activity type OF plantationArea is NOT proper; Activity type OF liveStockType is NOT proper; Activity type OF liveStockCode is NOT proper; Activity type OF unitCount is NOT proper ; Activity type OF totalUnits is NOT proper; Activity type OF inlandType is NOT proper; Activity type OF totalArea is NOT proper ; Activity type OF marineType is NOT proper; "
     * , "recipientUniqueID": "2227149017037758299"
     */

    private String uniqueId;
    private String recordStatus;
    private Integer applicationStatus;
    private String errors;
    private String recipientUniqueID;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Integer applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getRecipientUniqueID() {
        return recipientUniqueID;
    }

    public void setRecipientUniqueID(String recipientUniqueID) {
        this.recipientUniqueID = recipientUniqueID;
    }
}
