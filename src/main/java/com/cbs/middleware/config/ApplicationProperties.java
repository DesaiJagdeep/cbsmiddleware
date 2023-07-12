package com.cbs.middleware.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to CBS Middleware.
 * <p>
 * Properties are configured in the {@code application.yml} file. See
 * {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String CBSMiddlewareBaseURL;
    private String algorithm;
    private int keySizeBits;
    private String secretKey;
    private String iv;
    private Long recordStatusForFarmer;
    private Long recordStatusForFarmerAndLoan;
    private int recordStatusForUpdateFarmerAndLoan;

    public String getCBSMiddlewareBaseURL() {
        return CBSMiddlewareBaseURL;
    }

    public void setCBSMiddlewareBaseURL(String cBSMiddlewareBaseURL) {
        CBSMiddlewareBaseURL = cBSMiddlewareBaseURL;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getKeySizeBits() {
        return keySizeBits;
    }

    public void setKeySizeBits(int keySizeBits) {
        this.keySizeBits = keySizeBits;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public Long getRecordStatusForFarmer() {
        return recordStatusForFarmer;
    }

    public void setRecordStatusForFarmer(Long recordStatusForFarmer) {
        this.recordStatusForFarmer = recordStatusForFarmer;
    }

    public Long getRecordStatusForFarmerAndLoan() {
        return recordStatusForFarmerAndLoan;
    }

    public void setRecordStatusForFarmerAndLoan(Long recordStatusForFarmerAndLoan) {
        this.recordStatusForFarmerAndLoan = recordStatusForFarmerAndLoan;
    }

    public int getRecordStatusForUpdateFarmerAndLoan() {
        return recordStatusForUpdateFarmerAndLoan;
    }

    public void setRecordStatusForUpdateFarmerAndLoan(int recordStatusForUpdateFarmerAndLoan) {
        this.recordStatusForUpdateFarmerAndLoan = recordStatusForUpdateFarmerAndLoan;
    }
}
