package com.cbs.middleware.service.dto;

public class FileUploadResponseDTO {

    private String title;
    private int status;

    public FileUploadResponseDTO() {
        this.title = "File uploaded Successfully";
        this.status = 200;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
