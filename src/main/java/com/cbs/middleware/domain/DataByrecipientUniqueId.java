package com.cbs.middleware.domain;

import java.util.List;

public class DataByrecipientUniqueId {
    private boolean status;
    private List<ApplicationPayload> data;
    private String error;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ApplicationPayload> getData() {
        return data;
    }

    public void setData(List<ApplicationPayload> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
