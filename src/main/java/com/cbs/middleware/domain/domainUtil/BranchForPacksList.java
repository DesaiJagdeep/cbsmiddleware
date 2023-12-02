package com.cbs.middleware.domain.domainUtil;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BranchForPacksList {

    private String branchName;
    private List<PacsNameAndCode> packsNameList;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<PacsNameAndCode> getPacksNameList() {
        return packsNameList;
    }

    public void setPacksNameList(List<PacsNameAndCode> packsNameList) {
        this.packsNameList = packsNameList;
    }
}
