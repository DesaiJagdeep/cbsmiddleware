package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.ApplicationLogHistoryRepository;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.service.IssPortalFileQueryService;
import com.cbs.middleware.service.IssPortalFileService;
import com.cbs.middleware.service.criteria.IssPortalFileCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssPortalFile}.
 */
@RestController
@RequestMapping("/api")
public class IssPortalFileResource {

    private final Logger log = LoggerFactory.getLogger(IssPortalFileResource.class);

    private static final String ENTITY_NAME = "issPortalFile";

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    IssFileParserRepository fileParserRepository;

    @Autowired
    ApplicationLogRepository applicationLogRepository;

    @Autowired
    ApplicationLogHistoryRepository applicationLogHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Autowired
    BankBranchPacksCodeGet bankBranchPacksCodeGet;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssPortalFileService issPortalFileService;

    private final IssPortalFileRepository issPortalFileRepository;

    private final IssPortalFileQueryService issPortalFileQueryService;

    @Autowired
    NotificationRepository notificationRepository;

    public IssPortalFileResource(
        IssPortalFileService issPortalFileService,
        IssPortalFileRepository issPortalFileRepository,
        IssPortalFileQueryService issPortalFileQueryService
    ) {
        this.issPortalFileService = issPortalFileService;
        this.issPortalFileRepository = issPortalFileRepository;
        this.issPortalFileQueryService = issPortalFileQueryService;
    }

    @GetMapping("/download-file/{idIFP}")
    @PreAuthorize("@authentication.hasPermision('',#idIFP,'','FILE_DOWNLOAD','DOWNLOAD')")
    public Object excelDownload(@PathVariable Long idIFP) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Optional<IssPortalFile> findByIssPortalFileId = issPortalFileRepository.findById(idIFP);
        if (findByIssPortalFileId.isPresent()) {
            Path file = Paths.get(Constants.ORIGINAL_FILE_PATH + findByIssPortalFileId.get().getUniqueName());

            if (!Files.exists(file)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            IssPortalFile issPortalFile = findByIssPortalFileId.get();
            byte[] fileBytes;
            try {
                fileBytes = Files.readAllBytes(file);
                ByteArrayResource resource = new ByteArrayResource(fileBytes);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("filename", issPortalFile.getFileName());

                List<String> contentDispositionList = new ArrayList<>();
                contentDispositionList.add("Content-Disposition");

                headers.setAccessControlExposeHeaders(contentDispositionList);
                headers.set("X-Cbsmiddlewareapp-Alert", "cbsMiddlewareApp.issPortalFile.download");
                headers.set("X-Cbsmiddlewareapp-Params", issPortalFile.getFileName());

                if (resource != null) {
                    try {
                        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
                        String name = "";
                        if (optUser.isPresent()) {
                            name = optUser.get().getFirstName() + " " + optUser.get().getLastName();
                        }

                        notificationDataUtility.notificationData(
                            "Application records file downloaded by user ",
                            "Application records file: " + issPortalFile.getFileName() + " is downloaded by user " + name,
                            false,
                            Instant.now(),
                            "ApplicationRecordFileDownload" // type
                        );

                        if (!issPortalFile.isDownloadFile()) {
                            issPortalFile.setDownloadFileTime(Instant.now());
                            issPortalFile.setDownloadFile(true);
                            issPortalFileRepository.save(issPortalFile);
                        }
                    } catch (Exception e) {}
                }

                return ResponseEntity.ok().headers(headers).contentLength(fileBytes.length).body(resource);
            } catch (IOException e) {
                throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
            }
        } else {
            throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
        }
    }

    @GetMapping("/verify-file/{fileId}")
    @PreAuthorize("@authentication.hasPermision('',#fileId,'','FILE_DOWNLOAD','DOWNLOAD')")
    public ResponseEntity<Void> verifyFile(@PathVariable Long fileId) {
        Optional<IssPortalFile> findByIssPortalFileId = issPortalFileRepository.findById(fileId);

        if (!findByIssPortalFileId.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        String loginName = "";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> optUser = userRepository.findOneByLogin(auth.getName());

            if (optUser.isPresent()) {
                // name=optUser.get().getFirstName()+" "+optUser.get().getLastName();
                loginName = optUser.get().getLogin();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        IssPortalFile issPortalFile = findByIssPortalFileId.get();
        if (!issPortalFile.isVerifiedFile()) {
            issPortalFile.setVerifiedFile(true);
            issPortalFile.setVerifiedBy(loginName);
            issPortalFile.setVerifiedFileTime(Instant.now());

            issPortalFileRepository.save(issPortalFile);
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, "verify", issPortalFile.getPacsName()))
            .build();
    }

    /**
     * {@code POST  /iss-portal-files} : Create a new issPortalFile.
     *
     * @param issPortalFile the issPortalFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new issPortalFile, or with status {@code 400 (Bad Request)}
     *         if the issPortalFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/iss-portal-files")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<IssPortalFile> createIssPortalFile(@RequestBody IssPortalFile issPortalFile) throws URISyntaxException {
        log.debug("REST request to save IssPortalFile : {}", issPortalFile);
        if (issPortalFile.getId() != null) {
            throw new BadRequestAlertException("A new issPortalFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssPortalFile result = issPortalFileService.save(issPortalFile);
        return ResponseEntity
            .created(new URI("/api/iss-portal-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /iss-portal-files/:id} : Updates an existing issPortalFile.
     *
     * @param id            the id of the issPortalFile to save.
     * @param issPortalFile the issPortalFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated issPortalFile, or with status {@code 400 (Bad Request)}
     *         if the issPortalFile is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the issPortalFile couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/iss-portal-files/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<IssPortalFile> updateIssPortalFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssPortalFile issPortalFile
    ) throws URISyntaxException {
        log.debug("REST request to update IssPortalFile : {}, {}", id, issPortalFile);
        if (issPortalFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issPortalFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issPortalFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IssPortalFile result = issPortalFileService.update(issPortalFile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issPortalFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /iss-portal-files/:id} : Partial updates given fields of an
     * existing issPortalFile, field will ignore if it is null
     *
     * @param id            the id of the issPortalFile to save.
     * @param issPortalFile the issPortalFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated issPortalFile, or with status {@code 400 (Bad Request)}
     *         if the issPortalFile is not valid, or with status
     *         {@code 404 (Not Found)} if the issPortalFile is not found, or with
     *         status {@code 500 (Internal Server Error)} if the issPortalFile
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/iss-portal-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<IssPortalFile> partialUpdateIssPortalFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssPortalFile issPortalFile
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssPortalFile partially : {}, {}", id, issPortalFile);
        if (issPortalFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issPortalFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issPortalFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IssPortalFile> result = issPortalFileService.partialUpdate(issPortalFile);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issPortalFile.getId().toString())
        );
    }

    /**
     * {@code GET  /iss-portal-files} : get all the issPortalFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of issPortalFiles in body.
     */

    @GetMapping("/iss-portal-files")
    public ResponseEntity<List<IssPortalFile>> getAllIssPortalFiles(
        IssPortalFileCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        List<IssPortalFile> page = null;
        Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            page =
                issPortalFileQueryService.findByCriteriaCountByPacsCode(
                    Long.parseLong(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY)),
                    pageable
                );
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY))) {
            page =
                issPortalFileQueryService.findByCriteriaCountBySchemeWiseBranchCode(
                    Long.parseLong(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY)),
                    criteria,
                    pageable
                );
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            page = issPortalFileQueryService.findByCriteriaCount(criteria, pageable);
        } else {
            throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }

        log.debug("REST request to get IssPortalFiles by criteria: {}", criteria);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + page.size());
        List<String> contentDispositionList = new ArrayList<>();
        contentDispositionList.add("X-Total-Count");

        headers.setAccessControlExposeHeaders(contentDispositionList);

        return ResponseEntity.ok().headers(headers).body(page);
        //
        //			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //			List<IssPortalFile> page = null;
        //
        //			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        //			GrantedAuthority authority = authorities.stream().findFirst().get();
        //
        //			if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
        //				page = issPortalFileQueryService.findByCriteriaCount(criteria, pageable);
        //			} else if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
        //
        //				Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        //				if (optUser.isPresent()) {
        //					String branchCode = optUser.get().getBranchCode();
        //					page = issPortalFileQueryService.findByCriteriaCountByBranchCode(Long.parseLong(branchCode), criteria,
        //							pageable);
        //				}
        //
        //			} else if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_USER)) {
        //
        //				Optional<User> optUser = userRepository.findOneByLogin(auth.getName());
        //				if (optUser.isPresent()) {
        //					String pacsCode = optUser.get().getPacsNumber();
        //					page = issPortalFileQueryService.findByCriteriaCountByPacsCode(Long.parseLong(pacsCode), pageable);
        //				}
        //
        //			}
        //
        //			log.debug("REST request to get IssPortalFiles by criteria: {}", criteria);
        //
        //			HttpHeaders headers = new HttpHeaders();
        //			headers.add("X-Total-Count", "" + page.size());
        //			List<String> contentDispositionList = new ArrayList<>();
        //			contentDispositionList.add("X-Total-Count");
        //
        //			headers.setAccessControlExposeHeaders(contentDispositionList);
        //
        //			return ResponseEntity.ok().headers(headers).body(page);

    }

    @GetMapping("/iss-portal-files1")
    public ResponseEntity<List<IssPortalFile>> getAllIssPortalFiles1(
        IssPortalFileCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get IssPortalFiles by criteria: {}", criteria);
        Page<IssPortalFile> page = issPortalFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /iss-portal-files/count} : count all the issPortalFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/iss-portal-files/count")
    public ResponseEntity<Long> countIssPortalFiles(IssPortalFileCriteria criteria) {
        log.debug("REST request to count IssPortalFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(issPortalFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /iss-portal-files/:id} : get the "id" issPortalFile.
     *
     * @param id the id of the issPortalFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the issPortalFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/iss-portal-files/{id}")
    public ResponseEntity<IssPortalFile> getIssPortalFile(@PathVariable Long id) {
        log.debug("REST request to get IssPortalFile : {}", id);
        Optional<IssPortalFile> issPortalFile = issPortalFileService.findOne(id);

        if (!issPortalFile.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        issPortalFile.get().setAppPendingToSubmitCount(applicationRepository.countByIssFilePortalIdAndBatchIdNull(id));

        issPortalFile.get().setAppSubmitedToKccCount(applicationRepository.countByIssFilePortalIdAndBatchIdNotNull(id));

        return ResponseUtil.wrapOrNotFound(issPortalFile);
    }

    /**
     * {@code DELETE  /iss-portal-files/:id} : delete the "id" issPortalFile.
     *
     * @param id the id of the issPortalFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @DeleteMapping("/iss-portal-files/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteIssPortalFile(@PathVariable Long id) {
        log.debug("REST request to delete IssPortalFile : {}", id);
        Optional<IssPortalFile> issPortalFileObj = issPortalFileService.findOne(id);

        if (!issPortalFileObj.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        List<ApplicationLog> findAllByIssPortalId = applicationLogRepository.findAllByIssPortalId(id);
        List<Application> findAllByIssFilePortalId = applicationRepository.findAllByIssFilePortalId(id);
        IssPortalFile issPortalFile = issPortalFileObj.get();
        List<IssFileParser> issFileParsersList = fileParserRepository.findAllByIssPortalFile(issPortalFile);
        if (!findAllByIssFilePortalId.isEmpty()) {
            applicationRepository.deleteAll(findAllByIssFilePortalId);
        }
        if (!findAllByIssPortalId.isEmpty()) {
            applicationLogRepository.deleteAll(findAllByIssPortalId);
        }
        if (!issFileParsersList.isEmpty()) {
            fileParserRepository.deleteAll(issFileParsersList);
        }

        issPortalFileService.delete(id);

        if (issPortalFile != null) {
            try {
                notificationDataUtility.notificationData(
                    "Application records file deleted",
                    "Application records file: " + issPortalFile.getFileName() + " is deleted",
                    false,
                    issPortalFile.getCreatedDate(),
                    "ApplicationRecordFiledeleted" //type
                );
            } catch (Exception e) {}
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
