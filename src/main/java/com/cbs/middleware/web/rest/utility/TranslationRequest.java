package com.cbs.middleware.web.rest.utility;

public class TranslationRequest {

    private String q;
    private String source;
    private String target;

    public TranslationRequest(String q, String source, String target) {
        this.q = q;
        this.source = source;
        this.target = target;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "TranslationRequest [q=" + q + ", source=" + source + ", target=" + target + "]";
    }
}
