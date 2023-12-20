package com.cbs.middleware.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "retry_batch_transaction_details")
public class RetryBatchTransactionDetails extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "iss_file_parser_id")
    private Long ISSFileParserId;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name ="retry_batch_transaction_id")
    private RetryBatchTransaction retryBatchTransaction;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Long getISSFileParserId() {
        return ISSFileParserId;
    }

    public void setISSFileParserId(Long ISSFileParserId) {
        this.ISSFileParserId = ISSFileParserId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public RetryBatchTransaction getRetryBatchTransaction() {
        return retryBatchTransaction;
    }

    public void setRetryBatchTransaction(RetryBatchTransaction retryBatchTransaction) {
        this.retryBatchTransaction = retryBatchTransaction;
    }
}
