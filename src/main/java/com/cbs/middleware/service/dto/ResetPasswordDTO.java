package com.cbs.middleware.service.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * A DTO representing a password change required data - current and new
 * password.
 */
public class ResetPasswordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String login;

    @NotNull
    private String newPassword;

    @NotNull
    private String confirmPassword;

    public ResetPasswordDTO() {}

    public ResetPasswordDTO(String login, String newPassword, String confirmPassword) {
        this.login = login;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
