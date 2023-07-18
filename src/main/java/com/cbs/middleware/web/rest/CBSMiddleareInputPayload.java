package com.cbs.middleware.web.rest;

public class CBSMiddleareInputPayload {

    private String authCode;
    private Object data;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CBSMiddleareInputPayload [authCode=" + authCode + ", data=" + data + "]";
    }
}
