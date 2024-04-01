package com.cbs.middleware.config;

/**
 * Application constants.
 */
public final class Constants {

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

	public static final String SYSTEM = "system";
	public static final String DEFAULT_LANGUAGE = "en";

	public static final String AUTH_CODE = "619cd8ee-c012-4cca-9b00-ec428f1a0b7f";

	//local or dev server
	public static final String ORIGINAL_FILE_PATH = "/home/ubuntu/pdcc/originalFiles/";
	public static final String CHILD_FILE_PATH = "/home/ubuntu/pdcc/childPortalFiles/";
	public static final String KMP_FILE_PATH = "/home/ubuntu/pdcc/kmpFiles/";
	public static final String KARKHANA_VASULI_FILE_PATH = "/home/ubuntu/pdcc/karkhanaVasuliFiles/";
	public static final String USER_DETAIL_FILE_PATH = "/home/ubuntu/pdcc/userDetailsFiles/";

//	public static final String fontFilePath="E:\\riteshFont\\NotoSans-Regular.ttf";
//	public static final String fontFilePath1="E:\\riteshFont\\NotoSans-Regular_bold.ttf";
//	public static final String imagePath="E:\\riteshFont\\kmHeadingImage.png";

	public static final String fontFilePath = "/home/ubuntu/pdcc/font/NotoSans-Regular.ttf";
    public static final String fontFilePath1 = "/home/ubuntu/pdcc/font/NotoSans-Regular_bold.ttf";
    public static final String imagePath = "/home/ubuntu/pdcc/headingImage/kmHeadingImage.png";

	//producation or dev server
//    public static final String fontFilePath = "/var/lib/tomcat9/webapps/font/NotoSans-Regular.ttf";
//    public static final String fontFilePath1 = "/var/lib/tomcat9/webapps/font/NotoSans-Regular_bold.ttf";
//    public static final String imagePath = "/var/lib/tomcat9/webapps/headingImage/kmHeadingImage.png";
//
//	public static final String ORIGINAL_FILE_PATH = "/var/lib/tomcat9/webapps/pdccFiles/originalFiles/";
//	public static final String CHILD_FILE_PATH = "/var/lib/tomcat9/webapps/pdccFiles/childPortalFiles/";
//	public static final String KMP_FILE_PATH = "/var/lib/tomcat9/webapps/pdccFiles/kmpFiles/";
//	public static final String KARKHANA_VASULI_FILE_PATH = "/var/lib/tomcat9/webapps/pdccFiles/karkhanaVasuliFiles/";
//	public static final String USER_DETAIL_FILE_PATH = "/var/lib/tomcat9/webapps/pdccFiles/userDetailsFiles/";

	public static final String DefaultPassword = "PdcCB@nk@411001";
	public static final String DefaultPasswordForPacs = "Pdcc@123";

	public static final String PACKS_CODE_KEY = "packsCode";
	public static final String KCC_ISS_BRANCH_CODE_KEY = "schemeWiseBranchCode";
	 public static final String BRANCH_CODE_KEY = "branchCode";
	public static final String BANK_CODE_KEY = "bankCode";

	public static final String DISCARDED = "Discarded";
	public static final String PENDING_FOR_PROCESSING = "Pending for processing";
	public static final String PROCESSING = "Processing";
	public static final String PROCESSED = "Processed";
	public static final String NEW = "New";

    public static final String SUBMITTED = "Submitted";


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

    public static final String addloandetails = "/addloandetails";

	public static final String databyrecipientuniqueids = "/databyrecipientuniqueids";
    public static final String getpreuniqueids = "/preuniqueidbyaccountnumbers";

	public static final String MALE = "male";
	public static final String FEMAIL = "female";

	public static final String ERROR = "ERROR";
	public static final String FIXED = "FIXED";
	public static final String SUCCESS = "SUCCESS";

	public static final String validationError = "Validation Error";
	public static final String kccError = "KCC Error";

	public static final String downloadKccError = "KCCError";

	public static final String HighSevierity = "HIGH";
	public static final String lowSevierity = "LOW";

	private Constants() {
	}
}
