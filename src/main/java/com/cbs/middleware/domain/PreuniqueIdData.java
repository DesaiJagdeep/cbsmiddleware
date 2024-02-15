package com.cbs.middleware.domain;

import java.io.Serializable;
import java.util.List;

public class PreuniqueIdData  {

/* [
    {
        "preUniqueID": "1212231569653097512",
        "accountNumber": "025001700007453",
        "farmerID": "23033363445",
        "beneficiaryName": "ULHAS DATTATRAY THIKEKAR"
    }
    ]
    */

    private List<PreuniqueIdsDetails> data;

    public List<PreuniqueIdsDetails> getData() {
        return data;
    }

    public void setData(List<PreuniqueIdsDetails> data) {
        this.data = data;
    }






//    private String error;
//    private Integer status;

}
