package com.cbs.middleware.web.rest;

public class CBSMiddleareInputPayload {

    private String authCode;
    private String data;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CBSMiddleareInputPayload [authCode=" + authCode + ", data=" + data + "]";
    }
}
