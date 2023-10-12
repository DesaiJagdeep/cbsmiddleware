package com.cbs.middleware.web.rest.errors;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class MobileAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public MobileAlreadyUsedException() {
        super(ErrorConstants.MOBILE_NUMBER_ALREADY_USED_TYPE, "Mobile number is already in use!", "userManagement", "mobilenumberexists");
    }
}
