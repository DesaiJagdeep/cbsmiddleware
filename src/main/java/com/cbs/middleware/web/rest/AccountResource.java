package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.domain.Email;
import com.cbs.middleware.domain.ReCaptcha;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.security.SecurityUtils;
import com.cbs.middleware.service.MailService;
import com.cbs.middleware.service.UserService;
import com.cbs.middleware.service.dto.AdminUserDTO;
import com.cbs.middleware.service.dto.PasswordChangeDTO;
import com.cbs.middleware.service.dto.ResetPasswordDTO;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.EmailAlreadyUsedException;
import com.cbs.middleware.web.rest.errors.InvalidPasswordException;
import com.cbs.middleware.web.rest.errors.LoginAlreadyUsedException;
import com.cbs.middleware.web.rest.vm.KeyAndPasswordVM;
import com.cbs.middleware.web.rest.vm.ManagedUserVM;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static final String ENTITY_NAME = "account";

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    @Autowired
    ApplicationProperties applicationProperties;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (isPasswordLengthInvalid(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public AdminUserDTO getAccount() {
        return userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody AdminUserDTO userDTO) {
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        // Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        Optional<User> existingUser = userRepository.findOneByMobileNumber(userDTO.getMobileNumber());

        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getEmail(),
            userDTO.getLangKey(),
            userDTO.getImageUrl()
        );
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (isPasswordLengthInvalid(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/reset-password-admin")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        if (isPasswordLengthInvalid(resetPasswordDTO.getNewPassword())) {
            throw new InvalidPasswordException();
        }

        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword())) {
            throw new BadRequestAlertException("New password and confirm password not equal", ENTITY_NAME, "idinvalid");
        }

        userService.resetPassword(resetPasswordDTO);
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     */
    //    @PostMapping(path = "/account/reset-password/init")
    //	public void requestPasswordReset(@RequestBody Email mail) {
    //
    //		if (("".equalsIgnoreCase(mail.getEmail()))) {
    //			throw new BadRequestAlertException("Input parameter is empty", ENTITY_NAME, "idinvalid");
    //		}
    //
    //		if (!mail.getEmail().contains("@")) {
    //
    //			throw new BadRequestAlertException("Bad Request", ENTITY_NAME, "idinvalid");
    //		}
    //
    //		try {
    //			// Captcha Required
    //			String url = null;
    //			url = applicationProperties.getGoogleCaptchaUrlForWeb() + mail.getRecaptcha();
    //			HttpHeaders headers = new HttpHeaders();
    //			HttpEntity<ReCaptcha> requestEntity = new HttpEntity<>(null, headers);
    //			RestTemplate restTemplate = new RestTemplate();
    //			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    //			if (response.getStatusCode().is2xxSuccessful()) {
    //
    //				JSONObject jsonObject = new JSONObject(response.getBody());
    //				if (jsonObject.getBoolean("success")) {
    //
    //					List<User> userList = userRepository.findAllByEmailIgnoreCase(mail.getEmail());
    //					if (!userList.isEmpty()) {
    //
    //						for (User user : userList) {
    //							if (user != null && user.isActivated()) {
    //								try {
    //									mailService.sendPasswordResetMail(user);
    //
    //								} catch (Exception e) {
    //								}
    //							}
    //
    //						}
    //
    //					}
    //
    //				} else {
    //					log.error("Bad Request");
    //					throw new BadRequestAlertException("Bad Request", ENTITY_NAME, "idinvalid");
    //				}
    //
    //			} else {
    //				log.error("Captcha verification failed");
    //				throw new BadRequestAlertException("Invalid Captcha", ENTITY_NAME, "captchainvalid");
    //			}
    //		} catch (Exception e) {
    //			throw new BadRequestAlertException("Bad Request", ENTITY_NAME, "invaliddata");
    //
    //		}
    //
    //	}

    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody Email mail) {
        if (("".equalsIgnoreCase(mail.getEmail()))) {
            throw new BadRequestAlertException("Input parameter is empty", ENTITY_NAME, "idinvalid");
        }

        if (!mail.getEmail().contains("@")) {
            throw new BadRequestAlertException("Bad Request", ENTITY_NAME, "idinvalid");
        }

        List<User> userList = userRepository.findAllByEmailIgnoreCase(mail.getEmail());
        if (!userList.isEmpty()) {
            for (User user : userList) {
                if (user != null && user.isActivated()) {
                    try {
                        mailService.sendPasswordResetMail(user);
                    } catch (Exception e) {}
                }
            }
        }
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (isPasswordLengthInvalid(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
            password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }
}
