package com.cbs.middleware.service;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.Authority;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.AuthorityRepository;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.BankMasterRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.security.SecurityUtils;
import com.cbs.middleware.service.dto.AdminUserDTO;
import com.cbs.middleware.service.dto.ResetPasswordDTO;
import com.cbs.middleware.service.dto.UserDTO;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    @Autowired
    BankMasterRepository bankMasterRepository;

    @Autowired
    BankBranchMasterRepository bankBranchMasterRepository;

    @Autowired
    PacsMasterRepository pacsMasterRepository;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
            .findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(User::isActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                this.clearUserCaches(user);
                return user;
            });
    }

    public User registerUser(AdminUserDTO userDTO, String password) {
        userRepository
            .findOneByLogin(userDTO.getLogin().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.ROLE_BRANCH_USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(AdminUserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }

        if (StringUtils.isNotBlank(userDTO.getBankCode())) {
            user.setBankCode(userDTO.getBankCode());
        }

        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }

        if (StringUtils.isNotBlank(userDTO.getMobileNumber())) {
            user.setMobileNumber(userDTO.getMobileNumber());
        }
        String encryptedPassword = passwordEncoder.encode(Constants.DefaultPassword);
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);

            for (Authority authority : authorities) {
                if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                    if (StringUtils.isBlank(userDTO.getBankName())) {
                        throw new BadRequestAlertException("provide bank name", "USER", "bankNameNotExist");
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));
                    }
                } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
                    if (StringUtils.isBlank(userDTO.getBankName()) && StringUtils.isBlank(userDTO.getBranchName())) {
                        throw new BadRequestAlertException("provide bank name and branch Name", "USER", "bankNameBranchNameNotExist");
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));

                        user.setBranchName(userDTO.getBranchName());
                        user.setSchemeWiseBranchCode(
                            bankBranchMasterRepository.findSchemeWiseBranchCodeByBranchName(userDTO.getBranchName())
                        );
                    }
                } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_USER)) {
                    if (
                        StringUtils.isBlank(userDTO.getBankName()) &&
                        StringUtils.isBlank(userDTO.getBranchName()) &&
                        StringUtils.isBlank(userDTO.getPacsName())
                    ) {
                        throw new BadRequestAlertException(
                            "provide bank name, branch name and packs name",
                            "USER",
                            "bankNameBranchNamePacksCodeNotExist"
                        );
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));

                        user.setBranchName(userDTO.getBranchName());

                        user.setSchemeWiseBranchCode(
                            bankBranchMasterRepository.findSchemeWiseBranchCodeByBranchName(userDTO.getBranchName())
                        );
                    }
                } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_PACS_USER)) {
                    if (
                        StringUtils.isBlank(userDTO.getBankName()) &&
                        StringUtils.isBlank(userDTO.getBranchName()) &&
                        StringUtils.isBlank(userDTO.getPacsName())
                    ) {
                        throw new BadRequestAlertException(
                            "provide bank name, branch name and packs name",
                            "USER",
                            "bankNameBranchNamePacksCodeNotExist"
                        );
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));

                        user.setBranchName(userDTO.getBranchName());
                        user.setSchemeWiseBranchCode(
                            bankBranchMasterRepository.findSchemeWiseBranchCodeByBranchName(userDTO.getBranchName())
                        );

                        user.setPacsName(userDTO.getPacsName());
                        user.setPacsNumber(pacsMasterRepository.findPacsNumberByPacsName(userDTO.getPacsName()));
                    }
                } else {
                    throw new BadRequestAlertException("provide valid role", "USER", "roleNotExist");
                }
            }
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public User createUserFromFile(AdminUserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }

        if (StringUtils.isNotBlank(userDTO.getBankCode())) {
            user.setBankCode(userDTO.getBankCode());
        }

        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }

        if (StringUtils.isNotBlank(userDTO.getMobileNumber())) {
            user.setMobileNumber(userDTO.getMobileNumber());
        }
        String encryptedPassword = passwordEncoder.encode(Constants.DefaultPassword);
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);

            for (Authority authority : authorities) {
                if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                    if (StringUtils.isBlank(userDTO.getBankName())) {
                        throw new BadRequestAlertException("provide bank name", "USER", "bankNameNotExist");
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));
                    }
                } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
                    if (StringUtils.isBlank(userDTO.getBankName()) && StringUtils.isBlank(userDTO.getBranchName())) {
                        throw new BadRequestAlertException("provide bank name and branch Name", "USER", "bankNameBranchNameNotExist");
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode("");

                        user.setBranchName(userDTO.getBranchName());
                        user.setSchemeWiseBranchCode(userDTO.getSchemeWiseBranchCode());
                    }
                } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_USER)) {
                    if (
                        StringUtils.isBlank(userDTO.getBankName()) &&
                        StringUtils.isBlank(userDTO.getBranchName()) &&
                        StringUtils.isBlank(userDTO.getPacsName())
                    ) {
                        throw new BadRequestAlertException(
                            "provide bank name, branch name and packs name",
                            "USER",
                            "bankNameBranchNamePacksCodeNotExist"
                        );
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode("");

                        user.setBranchName(userDTO.getBranchName());

                        user.setSchemeWiseBranchCode(userDTO.getSchemeWiseBranchCode());
                    }
                } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_PACS_USER)) {
                    if (
                        StringUtils.isBlank(userDTO.getBankName()) &&
                        StringUtils.isBlank(userDTO.getBranchName()) &&
                        StringUtils.isBlank(userDTO.getPacsName())
                    ) {
                        throw new BadRequestAlertException(
                            "provide bank name, branch name and packs name",
                            "USER",
                            "bankNameBranchNamePacksCodeNotExist"
                        );
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode("156");

                        user.setBranchName(userDTO.getBranchName());
                        user.setSchemeWiseBranchCode(userDTO.getSchemeWiseBranchCode());
                        user.setPacsName(userDTO.getPacsName());
                        user.setPacsNumber(pacsMasterRepository.findPacsNumberByPacsName(userDTO.getPacsName()));
                    }
                } else {
                    throw new BadRequestAlertException("provide valid role", "USER", "roleNotExist");
                }
            }
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    public User createPacsUserFromFile(AdminUserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }

        if (StringUtils.isNotBlank(userDTO.getBankCode())) {
            user.setBankCode(userDTO.getBankCode());
        }

        // user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }

        if (StringUtils.isNotBlank(userDTO.getMobileNumber())) {
            user.setMobileNumber(userDTO.getMobileNumber());
        }
        String encryptedPassword = passwordEncoder.encode(Constants.DefaultPasswordForPacs);
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);

            for (Authority authority : authorities) {
                if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_PACS_USER)) {
                    if (
                        StringUtils.isBlank(userDTO.getBankName()) &&
                        StringUtils.isBlank(userDTO.getBranchName()) &&
                        StringUtils.isBlank(userDTO.getPacsName())
                    ) {
                        throw new BadRequestAlertException(
                            "provide bank name, branch name and packs name",
                            "USER",
                            "bankNameBranchNamePacksCodeNotExist"
                        );
                    } else {
                        user.setBankName(userDTO.getBankName());
                        user.setBankCode(userDTO.getBankCode());

                        user.setBranchName(userDTO.getBranchName());
                        user.setSchemeWiseBranchCode(userDTO.getSchemeWiseBranchCode());
                        user.setPacsName(userDTO.getPacsName());
                        user.setPacsNumber(userDTO.getPacsNumber());
                    }
                }
            }
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional
            .of(userRepository.findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin().toLowerCase());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }

                if (StringUtils.isNoneBlank(userDTO.getBankCode())) {
                    user.setBankCode(userDTO.getBankCode());
                }
                if (StringUtils.isNotBlank(userDTO.getMobileNumber())) {
                    user.setMobileNumber(userDTO.getMobileNumber());
                }
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO
                    .getAuthorities()
                    .stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);

                for (Authority authority : managedAuthorities) {
                    if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                        if (StringUtils.isBlank(userDTO.getBankName())) {
                            throw new BadRequestAlertException("provide bank name", "USER", "bankNameNotExist");
                        } else {
                            user.setBankName(userDTO.getBankName());
                            user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));

                            user.setSchemeWiseBranchCode("");
                            user.setBranchName("");
                            user.setPacsName("");
                            user.setPacsNumber("");
                        }
                    } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
                        if (StringUtils.isBlank(userDTO.getBankName()) && StringUtils.isBlank(userDTO.getBranchName())) {
                            throw new BadRequestAlertException("provide bank name and branch Name", "USER", "bankNameBranchNameNotExist");
                        } else {
                            user.setBankName(userDTO.getBankName());
                            user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));

                            user.setBranchName(userDTO.getBranchName());
                            user.setSchemeWiseBranchCode(
                                bankBranchMasterRepository.findSchemeWiseBranchCodeByBranchName(userDTO.getBranchName())
                            );

                            user.setPacsName("");
                            user.setPacsNumber("");
                        }
                    } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_USER)) {
                        if (
                            StringUtils.isBlank(userDTO.getBankName()) &&
                            StringUtils.isBlank(userDTO.getBranchName()) &&
                            StringUtils.isBlank(userDTO.getPacsName())
                        ) {
                            throw new BadRequestAlertException(
                                "provide bank name, branch name and packs name",
                                "USER",
                                "bankNameBranchNamePacksCodeNotExist"
                            );
                        } else {
                            user.setBankName(userDTO.getBankName());
                            user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));

                            user.setBranchName(userDTO.getBranchName());
                            user.setSchemeWiseBranchCode(
                                bankBranchMasterRepository.findSchemeWiseBranchCodeByBranchName(userDTO.getBranchName())
                            );
                        }
                    } else if (authority.getName().equalsIgnoreCase(AuthoritiesConstants.ROLE_PACS_USER)) {
                        if (
                            StringUtils.isBlank(userDTO.getBankName()) &&
                            StringUtils.isBlank(userDTO.getBranchName()) &&
                            StringUtils.isBlank(userDTO.getPacsName())
                        ) {
                            throw new BadRequestAlertException(
                                "provide bank name, branch name and packs name",
                                "USER",
                                "bankNameBranchNamePacksCodeNotExist"
                            );
                        } else {
                            user.setBankName(userDTO.getBankName());
                            user.setBankCode(bankMasterRepository.findBankCodeByBankName(userDTO.getBankName()));

                            user.setBranchName(userDTO.getBranchName());
                            user.setSchemeWiseBranchCode(
                                bankBranchMasterRepository.findSchemeWiseBranchCodeByBranchName(userDTO.getBranchName())
                            );

                            user.setPacsName(userDTO.getPacsName());
                            user.setPacsNumber(pacsMasterRepository.findPacsNumberByPacsName(userDTO.getPacsName()));
                        }
                    } else {
                        throw new BadRequestAlertException("provide valid role", "USER", "roleNotExist");
                    }
                }
                userRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(AdminUserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(user -> {
                userRepository.delete(user);
                this.clearUserCaches(user);
                log.debug("Deleted User: {}", user);
            });
    }

    /**
     * Update basic information (first name, last name, email, language) for the
     * current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<User> findOneByLogin = userRepository.findOneByLogin(resetPasswordDTO.getLogin());
        if (!findOneByLogin.isPresent()) {
            throw new BadRequestAlertException("User not found", "userManagement", "notFound");
        }
        User user = findOneByLogin.get();
        this.clearUserCaches(user);
        String encryptedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsersBySchemeWiseBranchCode(String schemeWiseBranchCode, Pageable pageable) {
        return userRepository.findAllBySchemeWiseBranchCode(schemeWiseBranchCode, pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * Gets a list of all the authorities.
     *
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.BRANCH_USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.BRANCH_USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
