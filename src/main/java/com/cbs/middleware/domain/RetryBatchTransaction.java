package com.cbs.middleware.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "retry_batch_transaction")

public class RetryBatchTransaction extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "batch_id")
    private String batchId;

    @Column(name = "batch_errors")
    private String batchErrors;

    public String getBatchErrors() {
        return batchErrors;
    }

    public void setBatchErrors(String batchErrors) {
        this.batchErrors = batchErrors;
    }

    @Column(name = "batch_ack_id")
    private String batchAckId;

    @Column(name = "triggeredDate")
    private Date triggeredDate;

    @Column(name = "status")
    private String status;

    public Long getIssPortalId() {
        return IssPortalId;
    }

    public void setIssPortalId(Long issPortalId) {
        IssPortalId = issPortalId;
    }

    @Column(name = "iss_portal_id")
    private Long IssPortalId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Date getTriggeredDate() {
        return triggeredDate;
    }

    public void setTriggeredDate(Date triggeredDate) {
        this.triggeredDate = triggeredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatchAckId() {
        return batchAckId;
    }

    public void setBatchAckId(String batchAckId) {
        this.batchAckId = batchAckId;
    }
}


