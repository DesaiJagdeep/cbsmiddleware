package com.cbs.middleware.domain.domainUtil;

import org.springframework.stereotype.Component;

@Component
public class PacksList {

    private String packsName;

    public String getPacksName() {
        return packsName;
    }

    public void setPacksName(String packsName) {
        this.packsName = packsName;
    }
}
