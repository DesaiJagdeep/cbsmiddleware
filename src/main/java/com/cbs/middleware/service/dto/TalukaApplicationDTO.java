package com.cbs.middleware.service.dto;

public class TalukaApplicationDTO {

    Long application_count;
    Long error_record_count;
    Long kcc_submitted;
    Long kcc_accepted;
    Long kcc_error_count;
    String branch_name;
    Long pacs_code;
    String pacs_name;

    public Long getApplication_count() {
        return application_count;
    }

    public void setApplication_count(Long application_count) {
        this.application_count = application_count;
    }

    public Long getError_record_count() {
        return error_record_count;
    }

    public void setError_record_count(Long error_record_count) {
        this.error_record_count = error_record_count;
    }

    public Long getKcc_submitted() {
        return kcc_submitted;
    }

    public void setKcc_submitted(Long kcc_submitted) {
        this.kcc_submitted = kcc_submitted;
    }

    public Long getKcc_accepted() {
        return kcc_accepted;
    }

    public void setKcc_accepted(Long kcc_accepted) {
        this.kcc_accepted = kcc_accepted;
    }

    public Long getKcc_error_count() {
        return kcc_error_count;
    }

    public void setKcc_error_count(Long kcc_error_count) {
        this.kcc_error_count = kcc_error_count;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public Long getPacs_code() {
        return pacs_code;
    }

    public void setPacs_code(Long pacs_code) {
        this.pacs_code = pacs_code;
    }

    public String getPacs_name() {
        return pacs_name;
    }

    public void setPacs_name(String pacs_name) {
        this.pacs_name = pacs_name;
    }
}
