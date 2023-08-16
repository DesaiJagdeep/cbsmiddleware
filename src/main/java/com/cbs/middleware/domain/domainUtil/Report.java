package com.cbs.middleware.domain.domainUtil;

import com.cbs.middleware.domain.Application;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Report {

    private Long recordsInYear;
    private Long recordSubmittedToKCC;
    private Long acceptedRecordByKCC;
    private Long rejectedRecordByKCC;

    private List<Application> applicationList;

    public Long getRecordsInYear() {
        return recordsInYear;
    }

    public void setRecordsInYear(Long recordsInYear) {
        this.recordsInYear = recordsInYear;
    }

    public Long getRecordSubmittedToKCC() {
        return recordSubmittedToKCC;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    public void setRecordSubmittedToKCC(Long recordSubmittedToKCC) {
        this.recordSubmittedToKCC = recordSubmittedToKCC;
    }

    public Long getAcceptedRecordByKCC() {
        return acceptedRecordByKCC;
    }

    public void setAcceptedRecordByKCC(Long acceptedRecordByKCC) {
        this.acceptedRecordByKCC = acceptedRecordByKCC;
    }

    public Long getRejectedRecordByKCC() {
        return rejectedRecordByKCC;
    }

    public void setRejectedRecordByKCC(Long rejectedRecordByKCC) {
        this.rejectedRecordByKCC = rejectedRecordByKCC;
    }
}
