package com.cbs.middleware.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String ROLE_BRANCH_ADMIN = "ROLE_BRANCH_ADMIN";

    public static final String ROLE_BRANCH_USER = "ROLE_BRANCH_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
