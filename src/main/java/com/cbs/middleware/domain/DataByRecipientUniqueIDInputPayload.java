package com.cbs.middleware.domain;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataByRecipientUniqueIDInputPayload {

    //	{
    //		"authCode":"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
    //		"data":{
    //		"batchAckId": "13102200000014",
    //		"recipientUniqueIds":[
    //		“2210234560000000183”,
    //		“2210234560000000184”
    //		]
    //		}
    //		}
    //

    private String batchAckId;
    private List<String> recipientUniqueIds;

    public String getBatchAckId() {
        return batchAckId;
    }

    public void setBatchAckId(String batchAckId) {
        this.batchAckId = batchAckId;
    }

    public List<String> getRecipientUniqueIds() {
        return recipientUniqueIds;
    }

    public void setRecipientUniqueIds(List<String> recipientUniqueIds) {
        this.recipientUniqueIds = recipientUniqueIds;
    }
}
