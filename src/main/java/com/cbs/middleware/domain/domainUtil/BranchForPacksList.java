package com.cbs.middleware.domain.domainUtil;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BranchForPacksList {

    private String branchName;
    private List<String> packsNameList;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<String> getPacksNameList() {
        return packsNameList;
    }

    public void setPacksNameList(List<String> packsNameList) {
        this.packsNameList = packsNameList;
    }
}
