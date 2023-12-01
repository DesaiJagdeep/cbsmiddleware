package com.cbs.middleware.web.rest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.websocket.server.PathParam;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.domain.UserFileDetails;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.repository.UserFileDetailsRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.service.MailService;
import com.cbs.middleware.service.UserQueryService;
import com.cbs.middleware.service.UserService;
import com.cbs.middleware.service.criteria.UserCriteria;
import com.cbs.middleware.service.dto.AdminUserDTO;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.EmailAlreadyUsedException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.LoginAlreadyUsedException;
import com.cbs.middleware.web.rest.errors.MobileAlreadyUsedException;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;

import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the {@link com.cbs.middleware.domain.User} entity, and
 * needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship
 * between User and Authority, and send everything to the client side: there
 * would be no View Model and DTO, a lot less code, and an outer-join which
 * would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities,
 * because people will quite often do relationships with the user, and we don't
 * want them to get the authorities all the time for nothing (for performance
 * reasons). This is the #1 goal: we should not impact our users' application
 * because of this use-case.</li>
 * <li>Not having an outer join causes n+1 requests to the database. This is not
 * a real issue as we have by default a second-level cache. This means on the
 * first HTTP call we do the n+1 requests, but then all authorities come from
 * the cache, so in fact it's much better than doing an outer join (which will
 * get lots of data from the database, for each HTTP call).</li>
 * <li>As this manages users, for security reasons, we'd rather have a DTO
 * layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this
 * case.
 */
@RestController
@RequestMapping("/api/admin")
public class UserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList(
            "id",
            "login",
            "firstName",
            "lastName",
            "email",
            "activated",
            "langKey",
            "createdBy",
            "createdDate",
            "lastModifiedBy",
            "lastModifiedDate"
        )
    );

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;
    private final UserRepository userRepository;
    private final MailService mailService;
    @Autowired
    UserFileDetailsRepository userFileDetailsRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationDataUtility notificationDataUtility;
    @Autowired
    UserQueryService userQueryService;
    @Autowired
    PacsMasterRepository masterRepository;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public UserResource(UserService userService, UserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";

        if (cell == null) {
            cellValue = "";
        } else if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue().trim();

            if (cellValue.contains(".0")) {
                cellValue = cellValue.substring(0, cellValue.indexOf("."));
            }
        } else if (cell.getCellType() == CellType.NUMERIC) {
            cellValue = String.valueOf(cell.getNumericCellValue());
            BigDecimal bigDecimal = new BigDecimal(cellValue);
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat decimalFormat = new DecimalFormat("0", symbols);

            cellValue = decimalFormat.format(bigDecimal);
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            cellValue = String.valueOf(cell.getNumericCellValue());

            if (cellValue.contains(".0")) {
                cellValue = cellValue.substring(0, cellValue.indexOf("."));
            }
        } else if (cell.getCellType() == CellType.BLANK) {
            cellValue = "";
        }

        return cellValue;
    }

    /**
     * {@code POST  /admin/users} : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link. The user needs to be activated on creation.
     *
     * @param userDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     * body the new user, or with status {@code 400 (Bad Request)} if the
     * login or email is already in use.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or
     *                                  email is already in use.
     */
    @PostMapping("/users")
    @PreAuthorize("@authentication.hasPermision('','','','USER','CREATE')")
    public ResponseEntity<User> createUser(@Valid @RequestBody AdminUserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByMobileNumber(userDTO.getMobileNumber()).isPresent()) {
            throw new MobileAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity
                .created(new URI("/api/admin/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert(applicationName, "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * {@code PUT /admin/users} : Updates an existing User.
     *
     * @param userDTO the user to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated user.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
     *                                   already in use.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is
     *                                   already in use.
     */
    @PutMapping("/users")
    @PreAuthorize("@authentication.userCheck(#userDTO.login,'USER','EDIT')")
    public ResponseEntity<AdminUserDTO> updateUser(@Valid @RequestBody AdminUserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> existingUser = userRepository.findOneByMobileNumber(userDTO.getMobileNumber());

        //        if (userRepository.findOneByMobileNumber(userDTO.getMobileNumber()).isPresent()) {
        //            throw new MobileAlreadyUsedException();
        //        }

        /* if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }*/

        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }

        changeRole(auth, userDTO, existingUser.get());

        Optional<AdminUserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(
            updatedUser,
            HeaderUtil.createAlert(applicationName, "userManagement.updated", userDTO.getLogin())
        );
    }

    @GetMapping("/password-changed/{userId}")
    public void passwordChanged(@PathVariable Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
        }
        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        if (!optUser.isPresent()) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (authorities.size() == 1) {
            GrantedAuthority authority = authorities.stream().findFirst().get();

            if (authority.toString().equals(AuthoritiesConstants.ANONYMOUS)) {
                throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
            }
        }
        Optional<User> optUserById = userRepository.findOneById(userId);
        if (!optUser.isPresent()) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
        } else if (optUser.get().getLogin().equalsIgnoreCase(optUserById.get().getLogin())) {
            User user = optUserById.get();
            user.setPasswordChanged(true);
            userRepository.save(user);
        } else {
            throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
        }
    }

    private AdminUserDTO changeRole(Authentication auth, AdminUserDTO adminUserDTO, User user) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        if (AuthoritiesConstants.ANONYMOUS.equals(authority.toString())) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
        } else if (AuthoritiesConstants.ADMIN.equals(authority.toString())) {
            return adminUserDTO;
        } else if (AuthoritiesConstants.ROLE_BRANCH_ADMIN.equals(authority.toString())) {
            if (!adminUserDTO.getAuthorities().isEmpty()) {
                if (adminUserDTO.getAuthorities().iterator().next().equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                    throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
                }
            }

            adminUserDTO.setBankCode(user.getBankCode());
            adminUserDTO.setBankName(user.getBankName());

            adminUserDTO.setBranchName(user.getBranchName());
            adminUserDTO.setSchemeWiseBranchCode(user.getSchemeWiseBranchCode());

            return adminUserDTO;
        } else if (AuthoritiesConstants.ROLE_BRANCH_USER.equals(authority.toString())) {
            if (!adminUserDTO.getAuthorities().isEmpty()) {
                String role = adminUserDTO.getAuthorities().iterator().next();
                if (role.equalsIgnoreCase(AuthoritiesConstants.ADMIN) || role.equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
                    throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
                }
            }

            adminUserDTO.setBankCode(user.getBankCode());
            adminUserDTO.setBankName(user.getBankName());

            adminUserDTO.setBranchName(user.getBranchName());
            adminUserDTO.setSchemeWiseBranchCode(user.getSchemeWiseBranchCode());

            adminUserDTO.setPacsName(user.getPacsName());
            adminUserDTO.setPacsNumber(user.getPacsNumber());

            return adminUserDTO;
        } else if (AuthoritiesConstants.ROLE_PACS_USER.equals(authority.toString())) {
            if (!adminUserDTO.getAuthorities().isEmpty()) {
                String role = adminUserDTO.getAuthorities().iterator().next();
                if (role.equalsIgnoreCase(AuthoritiesConstants.ADMIN) || role.equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
                    throw new ForbiddenAuthRequestAlertException("Access is denied", "USER", "unAuthorized");
                }
            }

            adminUserDTO.setBankCode(user.getBankCode());
            adminUserDTO.setBankName(user.getBankName());

            adminUserDTO.setBranchName(user.getBranchName());
            adminUserDTO.setSchemeWiseBranchCode(user.getSchemeWiseBranchCode());

            adminUserDTO.setPacsName(user.getPacsName());
            adminUserDTO.setPacsNumber(user.getPacsNumber());

            return adminUserDTO;
        }
        return adminUserDTO;
    }

    /**
     * search with all param
     */

    @GetMapping("/search-user")
    public ResponseEntity<List<AdminUserDTO>> getBySearchUser(@RequestParam(value = "key") String key,
                                                              @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        Page<AdminUserDTO> page = null;
        if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {

            UserCriteria userCriteria = new UserCriteria();
            StringFilter filter = new StringFilter();
            filter.setContains(key);
            userCriteria.setLogin(filter);
            userCriteria.setFirstName(filter);
            userCriteria.setMiddleName(filter);
            userCriteria.setLastName(filter);
            userCriteria.setMobileNumber(filter);
            userCriteria.setEmail(filter);
            userCriteria.setBranchName(filter);
            userCriteria.setPacsName(filter);
            page = userQueryService.findByCriteriaOr(userCriteria, pageable);

            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);


        } else if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
            Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
            if (optUser.isPresent()) {

                UserCriteria userCriteria = new UserCriteria();

                String branchName = optUser.get().getBranchName();
                StringFilter branchNameFilter = new StringFilter();
                branchNameFilter.setEquals(branchName);
                StringFilter filter = new StringFilter();
                filter.setContains(key);

                userCriteria.setLogin(filter);
                userCriteria.setFirstName(filter);
                userCriteria.setMiddleName(filter);
                userCriteria.setLastName(filter);
                userCriteria.setMobileNumber(filter);
                userCriteria.setEmail(filter);
                userCriteria.setBranchName(branchNameFilter);
                userCriteria.setPacsName(filter);
                page = userQueryService.findByCriteriaAndBranchName(userCriteria, pageable);

                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    /**
     * {@code GET /admin/users} : get all users with all the details - calling this
     * are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDTO>> getAllUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all User for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Page<AdminUserDTO> page = null;

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();

        if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
            page = userService.getAllManagedUsers(pageable);
        } else if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
            Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
            if (optUser.isPresent()) {
                String setSchemeWiseBranchCode = optUser.get().getSchemeWiseBranchCode();
                page = userService.getAllManagedUsersBySchemeWiseBranchCode(setSchemeWiseBranchCode, pageable);
            }
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * {@code GET /admin/users/:login} : get the "login" user.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/{login}")
    @PreAuthorize("@authentication.userCheck(#login,'USER','EDIT')")
    public ResponseEntity<AdminUserDTO> getUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
        log.debug("REST request to get User : {}", userService.getUserWithAuthoritiesByLogin(login));
        return ResponseUtil.wrapOrNotFound(userService.getUserWithAuthoritiesByLogin(login).map(AdminUserDTO::new));
    }

    /**
     * {@code DELETE /admin/users/:login} : delete the "login" User.
     *
     * @param login the login of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users/{login}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", login)).build();
    }

    @PostMapping("/master-user-upload")
    @PreAuthorize("@authentication.hasPermision('','','','USER','CREATE')")
    public Object createKMPFile(@RequestParam("file") MultipartFile files, RedirectAttributes redirectAttributes) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());
        List<AdminUserDTO> UserList = new ArrayList<>();
        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", "", "fileInvalid");
        }

        UserFileDetails userFileDetails = new UserFileDetails();
        File originalFileDir = new File(Constants.USER_DETAIL_FILE_PATH);
        if (!originalFileDir.isDirectory()) {
            originalFileDir.mkdirs();
        }

        String filePath = originalFileDir.toString();
        Calendar cal = new GregorianCalendar();
        String uniqueName =
            "" +
                cal.get(Calendar.YEAR) +
                cal.get(Calendar.MONTH) +
                cal.get(Calendar.DAY_OF_MONTH) +
                cal.get(Calendar.HOUR) +
                cal.get(Calendar.MINUTE) +
                cal.get(Calendar.SECOND) +
                cal.get(Calendar.MILLISECOND) +
                cal.get(Calendar.MINUTE) +
                cal.get(Calendar.MILLISECOND) +
                cal.get(Calendar.MONTH) +
                cal.get(Calendar.DAY_OF_MONTH) +
                cal.get(Calendar.HOUR) +
                cal.get(Calendar.SECOND) +
                cal.get(Calendar.MILLISECOND);

        Path path = Paths.get(filePath + File.separator + uniqueName + "." + fileExtension);
        try {
            byte[] imgbyte = null;
            imgbyte = files.getBytes();
            Files.write(path, imgbyte);
        } catch (IOException e) {
            throw new BadRequestAlertException("file not saved successfully", "", "fileInvalid");
        }

        int filecount = 0;
        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet

            Row row = null;
            for (int i = 0; i < 10; i++) {
                row = sheet.getRow(i); // Get the current row
                String talukaFile = getCellValue(row.getCell(1));
                if (StringUtils.isNotBlank(talukaFile)) {
                    talukaFile = talukaFile.trim().replace("\n", " ").toLowerCase();
                    if (!talukaFile.contains("taluka")) {
                        filecount = i;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Set<String> branchAdmin = new HashSet<>();
        branchAdmin.add("ROLE_BRANCH_ADMIN");

        Set<String> branchUser = new HashSet<>();
        branchUser.add("ROLE_BRANCH_USER");
        Set<String> mobileIfPresent = new HashSet<>();

        int startRowIndex = filecount + 1; // Starting row index

        boolean falgForFileName = false;
        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                AdminUserDTO adminUserDTO = new AdminUserDTO();
                adminUserDTO.setBankName("Pune District Central Co.op Bank Ltd");
                if (row != null) {
                    String mobileNumber = getCellValue(row.getCell(3));

                    if (StringUtils.isNotBlank(mobileNumber) && !userRepository.findOneByMobileNumber(mobileNumber).isPresent()) {
                        if (
                            StringUtils.isBlank(getCellValue(row.getCell(0))) &&
                                StringUtils.isBlank(getCellValue(row.getCell(1))) &&
                                StringUtils.isBlank(getCellValue(row.getCell(2))) &&
                                StringUtils.isBlank(getCellValue(row.getCell(3)))
                        ) {
                            break;
                        }

                        if (!falgForFileName) {
                            userFileDetails.setFileName(files.getOriginalFilename());
                            userFileDetails.setUniqueFileName(uniqueName + "." + fileExtension);
                            falgForFileName = true;
                        }

                        // add logic for skipping records if exists

                        String talukaName = getCellValue(row.getCell(0));

                        if (StringUtils.isNotBlank(talukaName)) {
                        }

                        String branchName = getCellValue(row.getCell(1));

                        if (StringUtils.isNotBlank(branchName)) {
                            adminUserDTO.setBranchName(branchName);
                        }

                        String fullName = getCellValue(row.getCell(2));

                        if (StringUtils.isNotBlank(fullName)) {
                            String[] fullNameArray = fullName.split("\\s+");

                            if (fullNameArray.length == 1) {
                                String firstName = fullNameArray[0];
                                adminUserDTO.setFirstName(firstName);
                            } else if (fullNameArray.length == 2) {
                                String firstName = fullNameArray[0];

                                String lastName = fullNameArray[1];

                                adminUserDTO.setFirstName(firstName);
                                adminUserDTO.setLastName(lastName);
                            } else if (fullNameArray.length == 3) {
                                String firstName = fullNameArray[0];
                                String middleName = fullNameArray[1];
                                String lastName = fullNameArray[2];

                                adminUserDTO.setFirstName(firstName);
                                adminUserDTO.setMiddleName(middleName);
                                adminUserDTO.setLastName(lastName);
                            } else if (fullNameArray.length == 4) {
                                String firstName = fullNameArray[0];
                                String firstName1 = fullNameArray[1];
                                String middleName = fullNameArray[2];
                                String lastName = fullNameArray[3];

                                adminUserDTO.setFirstName(firstName + " " + firstName1);
                                adminUserDTO.setMiddleName(middleName);
                                adminUserDTO.setLastName(lastName);
                            }
                        }

                        if (StringUtils.isNotBlank(mobileNumber)) {
                            adminUserDTO.setLogin(mobileNumber);
                            adminUserDTO.setMobileNumber(mobileNumber);
                        }

                        String emailName = getCellValue(row.getCell(4));

                        if (StringUtils.isNotBlank(emailName)) {
                            adminUserDTO.setEmail(emailName);
                        } else {
                            adminUserDTO.setEmail(mobileNumber + "@test.com");
                        }
                        String activeStatus = getCellValue(row.getCell(5));

                        if (StringUtils.isNotBlank(activeStatus)) {
                            if (activeStatus.equalsIgnoreCase("active")) {
                                adminUserDTO.setActivated(true);
                            } else {
                                adminUserDTO.setActivated(false);
                            }
                        }
                        String userType = getCellValue(row.getCell(6));

                        if (StringUtils.isNotBlank(userType)) {
                            userType = userType.trim().toLowerCase();

                            if (userType.equals("cooperative bank branch manager")) {
                                adminUserDTO.setAuthorities(branchAdmin);
                            } else if (userType.equals("cooperative bank branch user")) {
                                adminUserDTO.setAuthorities(branchUser);
                            }
                        }
                        String schemeWiseBranchCode = getCellValue(row.getCell(7));
                        if (StringUtils.isNotBlank(schemeWiseBranchCode)) {
                            adminUserDTO.setSchemeWiseBranchCode(schemeWiseBranchCode);
                        }

                        userService.createUserFromFile(adminUserDTO);

                        UserList.add(adminUserDTO);
                    } else {
                        mobileIfPresent.add(mobileNumber);
                    }
                }
            }
            if (!UserList.isEmpty()) {
                userFileDetailsRepository.save(userFileDetails);
                //                            if (UserList.get(0) != null) {
                //                                try {
                //                                    notificationDataUtility.notificationData(
                //                                        "Master User file uploaded",
                //                                        "Master User file : " + files.getOriginalFilename() + " uploaded",
                //                                        false,
                //                                        UserList.get(0).getCreatedDate(),
                //                                        "masterUserListUploaded" // type
                //                                    );
                //                                } catch (Exception e) {}
                //                            }

            }/*
             * else { throw new BadRequestAlertException("File is already parsed", "",
             * "FileExist"); }
             */

            if (!mobileIfPresent.isEmpty()) {
                return mobileIfPresent;
            } else {
                return UserList;
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", "", "nullColumn");
        }
    }

    @GetMapping("/get-by-packs/{pacsNumber}")
    public PacsMaster packsMasterData(@PathVariable String pacsNumber) {
        Optional<PacsMaster> findOneByPacsNumber = masterRepository.findOneByPacsNumber(pacsNumber);
        return findOneByPacsNumber.get();
    }

    @PostMapping("/master-pacs-upload")
    @PreAuthorize("@authentication.hasPermision('','','','USER','CREATE')")
    public Object createPacsFile(@RequestParam("file") MultipartFile files, RedirectAttributes redirectAttributes) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());
        List<AdminUserDTO> UserList = new ArrayList<>();
        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", "", "fileInvalid");
        }

        UserFileDetails userFileDetails = new UserFileDetails();
        File originalFileDir = new File(Constants.USER_DETAIL_FILE_PATH);
        if (!originalFileDir.isDirectory()) {
            originalFileDir.mkdirs();
        }

        String filePath = originalFileDir.toString();
        Calendar cal = new GregorianCalendar();
        String uniqueName =
            "" +
                cal.get(Calendar.YEAR) +
                cal.get(Calendar.MONTH) +
                cal.get(Calendar.DAY_OF_MONTH) +
                cal.get(Calendar.HOUR) +
                cal.get(Calendar.MINUTE) +
                cal.get(Calendar.SECOND) +
                cal.get(Calendar.MILLISECOND) +
                cal.get(Calendar.MINUTE) +
                cal.get(Calendar.MILLISECOND) +
                cal.get(Calendar.MONTH) +
                cal.get(Calendar.DAY_OF_MONTH) +
                cal.get(Calendar.HOUR) +
                cal.get(Calendar.SECOND) +
                cal.get(Calendar.MILLISECOND);

        Path path = Paths.get(filePath + File.separator + uniqueName + "." + fileExtension);
        try {
            byte[] imgbyte = null;
            imgbyte = files.getBytes();
            Files.write(path, imgbyte);
        } catch (IOException e) {
            throw new BadRequestAlertException("file not saved successfully", "", "fileInvalid");
        }

        int filecount = 0;
        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet

            Row row = null;
            for (int i = 0; i < 10; i++) {
                row = sheet.getRow(i); // Get the current row
                String talukaFile = getCellValue(row.getCell(1));
                if (StringUtils.isNotBlank(talukaFile)) {
                    talukaFile = talukaFile.trim().replace("\n", " ").toLowerCase();
                    if (!talukaFile.contains("sr.no")) {
                        filecount = i;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Set<String> packsUserSet = new HashSet<>();
        packsUserSet.add("ROLE_PACS_USER");

        Set<String> mobileIfPresent = new HashSet<>();

        int startRowIndex = filecount + 1; // Starting row index

        boolean falgForFileName = false;
        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row

                if (
                    StringUtils.isBlank(getCellValue(row.getCell(0))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(1))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(2))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(3)))
                ) {
                    break;
                }

                if (!falgForFileName) {
                    userFileDetails.setFileName(files.getOriginalFilename());
                    userFileDetails.setUniqueFileName(uniqueName + "." + fileExtension);
                    falgForFileName = true;
                }

                AdminUserDTO adminUserDTO = new AdminUserDTO();
                adminUserDTO.setBankName("Pune District Central Co.op Bank Ltd");
                adminUserDTO.setBankCode("156");
                if (row != null) {
                    //making login name

                    String login = getCellValue(row.getCell(7));

                    String packsNumber = getCellValue(row.getCell(3));

                    if (StringUtils.isBlank(login)) {
                        login = "MH" + packsNumber;
                    } else {
                        login = login.substring(0, login.indexOf("@"));
                    }

                    adminUserDTO.setLogin(login);

                    adminUserDTO.setFirstName(login);
                    adminUserDTO.setLastName(getCellValue(row.getCell(4)));

                    if (StringUtils.isNotBlank(login) && !userRepository.findOneByLogin(login).isPresent()) {
                        // add logic for skipping records if exists

                        adminUserDTO.setAuthorities(packsUserSet);

                        // System.out.println("................................................................"+packsNumber);
                        Optional<PacsMaster> findOneByPacsNumberGet = masterRepository.findOneByPacsNumber(packsNumber);

                        PacsMaster findOneByPacsNumber = findOneByPacsNumberGet.get();

                        if (findOneByPacsNumber != null) {
                            adminUserDTO.setPacsName(findOneByPacsNumber.getPacsName());
                            adminUserDTO.setPacsNumber(findOneByPacsNumber.getPacsNumber());

                            String branchNName = findOneByPacsNumber.getBankBranchMaster().getBranchName();
                            adminUserDTO.setBranchName(branchNName);
                            adminUserDTO.setSchemeWiseBranchCode(findOneByPacsNumber.getBankBranchMaster().getSchemeWiseBranchCode());
                            //                        	 String branchNameFromexcel=getCellValue(row.getCell(2));
                            //                        	 if(StringUtils.isNotBlank(branchNName)&&StringUtils.isNotBlank(branchNameFromexcel)
                            //                        			 &&!branchNName.equalsIgnoreCase(branchNameFromexcel))
                            //                        	 {
                            //                        		 System.out.println("/////////////////////////branchNName///////////////////////////"+branchNName);
                            //                        		 System.out.println("/////////////////////////branchNameFromexcel///////////////////////////"+branchNameFromexcel);
                            //                        	 }

                        }

                        //setting email
                        String emailName = getCellValue(row.getCell(5));

                        if (StringUtils.isNotBlank(emailName)) {
                            adminUserDTO.setEmail(emailName);
                        }

                        adminUserDTO.setActivated(true);

                        userService.createPacsUserFromFile(adminUserDTO);

                        UserList.add(adminUserDTO);
                    } else {
                        mobileIfPresent.add(login);
                    }
                }
            }
            if (!UserList.isEmpty()) {
                userFileDetailsRepository.save(userFileDetails);
                //                            if (UserList.get(0) != null) {
                //                                try {
                //                                    notificationDataUtility.notificationData(
                //                                        "Master User file uploaded",
                //                                        "Master User file : " + files.getOriginalFilename() + " uploaded",
                //                                        false,
                //                                        UserList.get(0).getCreatedDate(),
                //                                        "masterUserListUploaded" // type
                //                                    );
                //                                } catch (Exception e) {}
                //                            }

            }/*
             * else { throw new BadRequestAlertException("File is already parsed", "",
             * "FileExist"); }
             */

            if (!mobileIfPresent.isEmpty()) {
                Map<String, Object> test = new HashMap<>();

                test.put("MobileNumber", mobileIfPresent);
                test.put("UserList", UserList);

                return test;
            } else {
                return UserList;
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", "", "nullColumn");
        }
    }
}
