package com.cbs.middleware.domain;

import java.util.List;

public class DataByBatchAckId {

    /*{
	    "batchAckId": "17072300543632",
	    "batchId": "17072315612345",
	    "status": 3,
	    "applications": [
	        {
	            "uniqueId": "1707231561234598695",
	            "recordStatus": "1",
	            "applicationStatus": 0,
	            "errors": "activityType is Non Integer; Activity type OF Khata number is NOT proper; Activity type OF surveyNumber number is NOT proper; Activity type OF cropCode number is NOT proper; Activity type OR landArea number is NOT proper; Activity type OF landType is NOT proper; Activity type OF season is NOT proper; Activity type OF plantationCode is NOT proper; Activity type OF plantationArea is NOT proper; Activity type OF liveStockType is NOT proper; Activity type OF liveStockCode is NOT proper; Activity type OF unitCount is NOT proper ; Activity type OF totalUnits is NOT proper; Activity type OF inlandType is NOT proper; Activity type OF totalArea is NOT proper ; Activity type OF marineType is NOT proper; ",
	            "recipientUniqueID": "2227149017037758299"
	        }
	    ]
	}*/

    private String batchAckId;
    private String batchId;
    private Integer status;
    private List<ApplicationsByBatchAckId> applications;

    public String getBatchAckId() {
        return batchAckId;
    }

    public void setBatchAckId(String batchAckId) {
        this.batchAckId = batchAckId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ApplicationsByBatchAckId> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationsByBatchAckId> applications) {
        this.applications = applications;
    }
}
