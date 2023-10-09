package com.cbs.middleware.web.rest.utility;

import org.springframework.stereotype.Component;

@Component
public class PDFModel {

    private String fileName;
    private String htmlString;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHtmlString() {
        return htmlString;
    }

    public void setHtmlString(String htmlString) {
        this.htmlString = htmlString;
    }
}
