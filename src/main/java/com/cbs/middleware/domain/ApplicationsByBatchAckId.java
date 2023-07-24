package com.cbs.middleware.domain;

public class ApplicationsByBatchAckId {

    //    "uniqueId": "1907231562701031471",
    //    "recordStatus": "1",
    //    "applicationNumber": "2127149017017075478",
    //    "applicationStatus": 1,
    //    "farmerId": "23021748757",
    //    "recipientUniqueID": "2127149017044470362"

    private String uniqueId;
    private String recordStatus;
    private String applicationNumber;
    private Integer applicationStatus;
    private String farmerId;
    private String recipientUniqueID;
    private String errors;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
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

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
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
