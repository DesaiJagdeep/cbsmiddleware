package com.cbs.middleware.domain.domainUtil;

public class PacsNameAndCode {

    private String pacsName;
    private String pacsCode;

    public PacsNameAndCode(String pacsName, String pacsCode) {
        this.pacsName = pacsName;
        this.pacsCode = pacsCode;
    }

    public String getPacsName() {
        return pacsName;
    }

    public void setPacsName(String pacsName) {
        this.pacsName = pacsName;
    }

    public String getPacsCode() {
        return pacsCode;
    }

    public void setPacsCode(String pacsCode) {
        this.pacsCode = pacsCode;
    }
}
