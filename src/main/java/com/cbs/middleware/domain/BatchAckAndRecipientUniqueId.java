package com.cbs.middleware.domain;

import java.util.ArrayList;

public class BatchAckAndRecipientUniqueId {
    private String batchAckId;
    private ArrayList<String> recipientUniqueIds;

    public String getBatchAckId() {
        return batchAckId;
    }

    public void setBatchAckId(String batchAckId) {
        this.batchAckId = batchAckId;
    }

    public ArrayList<String> getRecipientUniqueIds() {
        return recipientUniqueIds;
    }

    public void setRecipientUniqueIds(ArrayList<String> recipientUniqueIds) {
        this.recipientUniqueIds = recipientUniqueIds;
    }
}
