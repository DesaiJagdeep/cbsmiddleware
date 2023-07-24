package com.cbs.middleware.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String AUTH_CODE = "68f18c5f-a0e2-4d35-930f-5da9b947f5e1";

    public static final String ORIGINAL_FILE_PATH = "/home/ubuntu/pdcc/originalFiles/";

    public static final String DefaultPassword = "Bank@123";

    public static final String PACKS_CODE_KEY = "packsCode";
    public static final String BRANCH_CODE_KEY = "branchCode";
    public static final String BANK_CODE_KEY = "bankCode";

    public static final String DISCARDED = "Discarded";
    public static final String PENDING_FOR_PROCESSING = "Pending for processing";
    public static final String PROCESSING = "Processing";
    public static final String PROCESSED = "Processed";
    public static final String NEW = "New";

    public static final Integer DISCARDED_BATCH_STATUS_CODE = 0; // Discarded
    public static final Integer PENDING_FOR_PROCESSING_BATCH_STATUS_CODE = 1; // Pending for processing
    public static final Integer PROCESSING_BATCH_STATUS_CODE = 2; // Processing
    public static final Integer PROCESSED_BATCH_STATUS_CODE = 3; // Processed
    public static final Integer NEW_BATCH_STATUS_CODE = 4; // new

    public static final Integer COMPLETE_FARMER_DETAIL_AND_LOAN_DETAIL = 1;
    public static final Integer COMPLETE_FARMER_DETAIL = 2;
    public static final Integer LOAN_DETAIL = 3;

    public static final Integer APPLICATION_SUCCESS_STATUS = 1;
    public static final Integer APPLICATION_FAILLURE_STATUS = 0;
    public static final Integer APPLICATION_INITIAL_STATUS_FOR_LOAD = 2;

    public static final String databybatchackid = "/databybatchackid";
    public static final String submitbatch = "/submitbatch";

    public static final String MALE = "male";
    public static final String FEMAIL = "female";

    private Constants() {}
}
