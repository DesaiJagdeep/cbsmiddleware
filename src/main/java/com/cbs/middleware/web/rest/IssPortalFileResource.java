package com.cbs.middleware.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.service.dto.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.TalukaMaster;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.domain.domainUtil.TalukaWiseDataReport;
import com.cbs.middleware.repository.ApplicationLogHistoryRepository;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.repository.TalukaMasterRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.service.IssPortalFileQueryService;
import com.cbs.middleware.service.IssPortalFileService;
import com.cbs.middleware.service.criteria.IssPortalFileCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssPortalFile}.
 */
@RestController
@RequestMapping("/api")
public class IssPortalFileResource {

    private static final String ENTITY_NAME = "issPortalFile";
    private final Logger log = LoggerFactory.getLogger(IssPortalFileResource.class);
    private final IssPortalFileService issPortalFileService;
    private final IssPortalFileRepository issPortalFileRepository;
    private final IssPortalFileQueryService issPortalFileQueryService;
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
    @Autowired
    TalukaMasterRepository talukaMasterRepository;
    @Autowired
    PacsMasterRepository pacsMasterRepository;
    @Autowired
    BankBranchMasterRepository bankBranchMasterRepository;
    @Autowired
    IssFileParserRepository issFileParserRepository;
    @Autowired
    NotificationRepository notificationRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

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

//                        if (!issPortalFile.isDownloadFile()) {
//                            issPortalFile.setDownloadFileTime(Instant.now());
//                            issPortalFile.setDownloadFile(true);
//                            issPortalFileRepository.save(issPortalFile);
//                        }
                    } catch (Exception e) {
                    }
                }

                return ResponseEntity.ok().headers(headers).contentLength(fileBytes.length).body(resource);
            } catch (IOException e) {
                throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
            }
        } else {
            throw new BadRequestAlertException("Error in file download", ENTITY_NAME, "fileNotFound");
        }
    }
//    	int srno=0;
//    	talukaWiseDataReportList1.forEach(t->{
//    		t.setSrNo(srno+1);
//    		});

    /**
     * download-record-file
     */


    @GetMapping("/download-record-file/{idIFP}")
    //@PreAuthorize("@authentication.hasPermision('',#idIFP,'','FILE_DOWNLOAD','DOWNLOAD')")
    public Object downloadRecordFile(@PathVariable Long idIFP) {
        Optional<IssPortalFile> findByIssPortalFileId = issPortalFileRepository.findById(idIFP);
        if (!findByIssPortalFileId.isPresent()) {

            throw new BadRequestAlertException("id not found", ENTITY_NAME, "idNotFound");
        }
        IssPortalFile issPortalFile = findByIssPortalFileId.get();
        List<IssFileParser> issFileParserList = issFileParserRepository.findAllByIssPortalFile(issPortalFile);

        if (issFileParserList.isEmpty()) {
            throw new BadRequestAlertException("records not found", ENTITY_NAME, "recordsNotFound");
        }


        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Iss Portal Data for Pacs Member");
            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            row.createCell(colNum++).setCellValue("Financial Year 1");
            row.createCell(colNum++).setCellValue("Bank Name 2");
            row.createCell(colNum++).setCellValue("Bank Code 3");
            row.createCell(colNum++).setCellValue("Branch Name 4");
            row.createCell(colNum++).setCellValue("Branch  Code 5");
            row.createCell(colNum++).setCellValue("Scheme Wise Branch Code 6");
            row.createCell(colNum++).setCellValue("IFSC 7");
            row.createCell(colNum++).setCellValue("Loan Account Number kcc 8");
            row.createCell(colNum++).setCellValue("Farmer Name 9");
            row.createCell(colNum++).setCellValue("Gender 10");
            row.createCell(colNum++).setCellValue("Aadhar  Number 11");
            row.createCell(colNum++).setCellValue("Dateof  Birth 12");
            row.createCell(colNum++).setCellValue("Age At Time Of Sanction 13");
            row.createCell(colNum++).setCellValue("Mobile No 14");
            row.createCell(colNum++).setCellValue("Farmers Category 15");
            row.createCell(colNum++).setCellValue("Farmer  Type 16");
            row.createCell(colNum++).setCellValue("Social Category 17");
            row.createCell(colNum++).setCellValue("Relative Type 18");
            row.createCell(colNum++).setCellValue("Relative Name 19");
            row.createCell(colNum++).setCellValue("State Name 20");
            row.createCell(colNum++).setCellValue("State Code 21");
            row.createCell(colNum++).setCellValue("District Name 22");
            row.createCell(colNum++).setCellValue("District code 23");
            row.createCell(colNum++).setCellValue("Block Code 24");
            row.createCell(colNum++).setCellValue("Block Name 25");
            row.createCell(colNum++).setCellValue("Village Code 26");
            row.createCell(colNum++).setCellValue("Village Name 27");
            row.createCell(colNum++).setCellValue("Address 28");
            row.createCell(colNum++).setCellValue("Pin Code 29");
            row.createCell(colNum++).setCellValue("Account Type 30");
            row.createCell(colNum++).setCellValue("Account Number 31");
            row.createCell(colNum++).setCellValue("Pacs Name 32");
            row.createCell(colNum++).setCellValue("Pacs Number 33");
            row.createCell(colNum++).setCellValue("Account Holder Type 34");
            row.createCell(colNum++).setCellValue("Primary occupation 35");
            row.createCell(colNum++).setCellValue("Loan Saction Date 36");
            row.createCell(colNum++).setCellValue("Loan Sanction Amount 37");
            row.createCell(colNum++).setCellValue("Tenure OF Loan 38");
            row.createCell(colNum++).setCellValue("Date Of Over Due Payment 39");
            row.createCell(colNum++).setCellValue("KCC Crop Code 40");
            row.createCell(colNum++).setCellValue("KCC Crop Name 41");
            row.createCell(colNum++).setCellValue("Crop Name 42");
            row.createCell(colNum++).setCellValue("survey No 43");
            row.createCell(colNum++).setCellValue("Sat Bara Subsurvey No 44");
            row.createCell(colNum++).setCellValue("Season name 45");
            row.createCell(colNum++).setCellValue("Activity Type 46");
            row.createCell(colNum++).setCellValue("Area Hect 47");
            row.createCell(colNum++).setCellValue("Land Type 48");
            row.createCell(colNum++).setCellValue("Disbursement Date 49");
            row.createCell(colNum++).setCellValue("Disburse Amount 50");
            row.createCell(colNum++).setCellValue("Maturity Loan Date 51");
            row.createCell(colNum++).setCellValue("Recovery Amount Principle 52");
            row.createCell(colNum++).setCellValue("Recovery Amount Interest 53");
            row.createCell(colNum++).setCellValue("Recovery Date 54");

            row.createCell(colNum++).setCellValue("Second Recovery Amount Principle 55");
            row.createCell(colNum++).setCellValue("Second Recovery Amount Interest 56");
            row.createCell(colNum++).setCellValue("Second Recovery Date 57");

            row.createCell(colNum++).setCellValue("Third Recovery Amount Principle 58");
            row.createCell(colNum++).setCellValue("Third Recovery Amount Interest 59");
            row.createCell(colNum++).setCellValue("Third Recovery Date 60");

            row.createCell(colNum++).setCellValue("Fourth Recovery Amount Principle 61");
            row.createCell(colNum++).setCellValue("Fourth Recovery Amount Interest 62");
            row.createCell(colNum++).setCellValue("Fourth Recovery Date 63");


            for (IssFileParser issFileParser : issFileParserList) {
                colNum = 0;
                row = sheet.createRow(rowNum++);
                row.createCell(colNum++).setCellValue(issFileParser.getFinancialYear());
                row.createCell(colNum++).setCellValue(issFileParser.getBankName());
                row.createCell(colNum++).setCellValue(issFileParser.getBankCode());
                row.createCell(colNum++).setCellValue(issFileParser.getBranchName());
                row.createCell(colNum++).setCellValue(issFileParser.getBranchCode());
                row.createCell(colNum++).setCellValue(issFileParser.getSchemeWiseBranchCode());
                row.createCell(colNum++).setCellValue(issFileParser.getIfsc());
                row.createCell(colNum++).setCellValue(issFileParser.getLoanAccountNumberkcc());
                row.createCell(colNum++).setCellValue(issFileParser.getFarmerName());
                row.createCell(colNum++).setCellValue(issFileParser.getGender());
                row.createCell(colNum++).setCellValue(issFileParser.getAadharNumber());
                row.createCell(colNum++).setCellValue(issFileParser.getDateofBirth());
                row.createCell(colNum++).setCellValue(issFileParser.getAgeAtTimeOfSanction());

                row.createCell(colNum++).setCellValue(issFileParser.getMobileNo());

                row.createCell(colNum++).setCellValue(issFileParser.getFarmersCategory());
                row.createCell(colNum++).setCellValue(issFileParser.getFarmerType());
                row.createCell(colNum++).setCellValue(issFileParser.getSocialCategory());

                row.createCell(colNum++).setCellValue(issFileParser.getRelativeType());
                row.createCell(colNum++).setCellValue(issFileParser.getRelativeName());
                row.createCell(colNum++).setCellValue(issFileParser.getStateName());
                row.createCell(colNum++).setCellValue(issFileParser.getStateCode());
                row.createCell(colNum++).setCellValue(issFileParser.getDistrictName());
                row.createCell(colNum++).setCellValue(issFileParser.getDistrictCode());

                row.createCell(colNum++).setCellValue(issFileParser.getBlockCode());
                row.createCell(colNum++).setCellValue(issFileParser.getBlockName());
                row.createCell(colNum++).setCellValue(issFileParser.getVillageCode());
                row.createCell(colNum++).setCellValue(issFileParser.getVillageName());
                row.createCell(colNum++).setCellValue(issFileParser.getAddress());

                row.createCell(colNum++).setCellValue(issFileParser.getPinCode());
                row.createCell(colNum++).setCellValue(issFileParser.getAccountType());
                row.createCell(colNum++).setCellValue(issFileParser.getAccountNumber());
                row.createCell(colNum++).setCellValue(issFileParser.getPacsName());
                row.createCell(colNum++).setCellValue(issFileParser.getPacsNumber());
                row.createCell(colNum++).setCellValue(issFileParser.getAccountHolderType());
                row.createCell(colNum++).setCellValue(issFileParser.getPrimaryOccupation());

                row.createCell(colNum++).setCellValue(issFileParser.getLoanSactionDate());
                row.createCell(colNum++).setCellValue(issFileParser.getLoanSanctionAmount());
                row.createCell(colNum++).setCellValue(issFileParser.getTenureOFLoan());
                row.createCell(colNum++).setCellValue(issFileParser.getDateOfOverDuePayment());
                row.createCell(colNum++).setCellValue(issFileParser.getKccIssCropCode());
                row.createCell(colNum++).setCellValue(issFileParser.getKccIssCropName());
                row.createCell(colNum++).setCellValue(issFileParser.getCropName());
                row.createCell(colNum++).setCellValue(issFileParser.getSurveyNo());

                row.createCell(colNum++).setCellValue(issFileParser.getSatBaraSubsurveyNo());
                row.createCell(colNum++).setCellValue(issFileParser.getSeasonName());
                row.createCell(colNum++).setCellValue(issFileParser.getActivityType());
                row.createCell(colNum++).setCellValue(issFileParser.getAreaHect());
                row.createCell(colNum++).setCellValue(issFileParser.getLandType());
                row.createCell(colNum++).setCellValue(issFileParser.getDisbursementDate());

                row.createCell(colNum++).setCellValue(issFileParser.getDisburseAmount());
                row.createCell(colNum++).setCellValue(issFileParser.getMaturityLoanDate());
                row.createCell(colNum++).setCellValue(issFileParser.getRecoveryAmountPrinciple());
                row.createCell(colNum++).setCellValue(issFileParser.getRecoveryAmountInterest());
                row.createCell(colNum++).setCellValue(issFileParser.getRecoveryDate());


                row.createCell(colNum++).setCellValue(issFileParser.getSecondRecoveryAmountPrinciple());
                row.createCell(colNum++).setCellValue(issFileParser.getSecondRecoveryAmountInterest());
                row.createCell(colNum++).setCellValue(issFileParser.getSecondRecoveryDate());

                row.createCell(colNum++).setCellValue(issFileParser.getThirdRecoveryAmountPrinciple());
                row.createCell(colNum++).setCellValue(issFileParser.getThirdRecoveryAmountInterest());
                row.createCell(colNum++).setCellValue(issFileParser.getThirdRecoveryDate());


                row.createCell(colNum++).setCellValue(issFileParser.getFourthRecoveryAmountPrinciple());
                row.createCell(colNum++).setCellValue(issFileParser.getFourthRecoveryAmountInterest());
                row.createCell(colNum++).setCellValue(issFileParser.getFourthRecoveryDate());

            }

            // Write Excel to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            byte[] excelContent = outputStream.toByteArray();

            // Set up the HTTP headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String fileNameWithOutExt = FilenameUtils.removeExtension(findByIssPortalFileId.get().getFileName());

            headers.setContentDispositionFormData("filename", fileNameWithOutExt + "-" + getUniqueName() + ".xlsx");

            List<String> contentDispositionList = new ArrayList<>();
            contentDispositionList.add("Content-Disposition");

            headers.setAccessControlExposeHeaders(contentDispositionList);

            if (!issPortalFile.isDownloadFile()) {
                issPortalFile.setDownloadFileTime(Instant.now());
                issPortalFile.setDownloadFile(true);
                issPortalFileRepository.save(issPortalFile);
            }


            return new ResponseEntity<>(excelContent, headers, 200);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }


    }


    public String getUniqueName() {
        Calendar cal = new GregorianCalendar();
        return (
            "" +
                cal.get(Calendar.YEAR) +
                cal.get(Calendar.MONTH) +
                cal.get(Calendar.DAY_OF_MONTH) +
                cal.get(Calendar.HOUR) +
                cal.get(Calendar.MILLISECOND)
        );
    }

    /**
     *
     */
    @GetMapping("/taluka-wise-data/{financialYear}")
    @PreAuthorize("@authentication.hasPermision('','','','TALUKA_DATA','VIEW')")
    public List<TalukaWiseDataReport> talukaWiseData(@PathVariable String financialYear) {

        Set<TalukaWiseDataReport> talukaWiseDataReportList = new HashSet<TalukaWiseDataReport>();
        TalukaWiseDataReport talukaWiseDataReport = new TalukaWiseDataReport();
        //calculating current finantial year
        //String currentFinantialYear=calculateCurrentFinantialYear();

        List<TalukaMaster> talukaMasterList = talukaMasterRepository.findAll();

        for (TalukaMaster talukaMaster : talukaMasterList) {


            if (!talukaMaster.getTalukaName().equalsIgnoreCase("PUNE CITY") && !talukaMaster.getTalukaName().equalsIgnoreCase("PIMPRI-CHINCHWAD")) {


                talukaWiseDataReport = new TalukaWiseDataReport();
                talukaWiseDataReport.setTalukaName(talukaMaster.getTalukaName());
                talukaWiseDataReport.setTalukaId(talukaMaster.getId());
                //find society count
                //Integer countOfSocietiesByTalukaName=pacsMasterRepository.countOfSocietiesByTalukaName(talukaMaster.getTalukaName());
                //talukaWiseDataReport.setNoOfSocieties(countOfSocietiesByTalukaName);

                Integer countOfSocietiesByTalukaName = 0;
                switch (talukaMaster.getTalukaName()) {
                    case "BHOR":
                        countOfSocietiesByTalukaName = 68;
                        break;
                    case "AMBEGAON":
                        countOfSocietiesByTalukaName = 59;
                        break;
                    case "BARAMATI":
                        countOfSocietiesByTalukaName = 191;
                        break;
                    case "DAUND":
                        countOfSocietiesByTalukaName = 124;
                        break;
                    case "HAVELI":
                        countOfSocietiesByTalukaName = 140;
                        break;
                    case "INDAPUR":
                        countOfSocietiesByTalukaName = 222;
                        break;
                    case "JUNNAR":
                        countOfSocietiesByTalukaName = 76;
                        break;
                    case "KHED":
                        countOfSocietiesByTalukaName = 104;
                        break;
                    case "MAVAL":
                        countOfSocietiesByTalukaName = 55;
                        break;
                    case "MULSHI":
                        countOfSocietiesByTalukaName = 46;
                        break;
                    case "PURANDAR":
                        countOfSocietiesByTalukaName = 97;
                        break;
                    case "SHIRUR":
                        countOfSocietiesByTalukaName = 128;
                        break;
                    case "VELHA":
                        countOfSocietiesByTalukaName = 26;
                        break;
                    default:
                        countOfSocietiesByTalukaName = 0;
                        break;
                }

                talukaWiseDataReport.setNoOfSocieties(countOfSocietiesByTalukaName);
                Integer completedCount = 0;
                Integer inProgressCount = 0;
                Integer yetToStartCount = 0;
                //Integer pendingForApprovalCount = 0;
                Integer pendingApprovalFromBranchAdminCount = 0;
                Integer pendingApprovalFromBranchUserCount = 0;


//                List<BankBranchMaster> bankBranchMastersList = bankBranchMasterRepository.findAllByTalukaMaster(talukaMaster);
//                for (BankBranchMaster bankBranchMaster : bankBranchMastersList) {
//                    Long schemeWiseBranchCode = Long.parseLong(bankBranchMaster.getSchemeWiseBranchCode());
//
//                    Optional<List<IssPortalFile>> findAllBySchemeWiseBranchCode = issPortalFileRepository.findAllBySchemeWiseBranchCodeAndFinancialYear(schemeWiseBranchCode, financialYear);
//                    if (findAllBySchemeWiseBranchCode.isPresent()) {
//                        completedCount = completedCount + issPortalFileRepository.findCompletedCountByBankBranch(schemeWiseBranchCode, financialYear);
//                        inProgressCount = inProgressCount + issPortalFileRepository.findInProgressCountByBankBranch(schemeWiseBranchCode, financialYear);
//                       pendingForApprovalCount = pendingForApprovalCount + issPortalFileRepository.findPendingForApprovalCountByBankBranch(schemeWiseBranchCode, financialYear);
//
//                    }
                Integer totalIssPortalFile = issPortalFileRepository.findTotalIssPortalFileByTalukaId(talukaMaster.getId(), financialYear);
                Integer notNullIssPortalFile = issPortalFileRepository.findNotNullIssPortalFile(talukaMaster.getId(), financialYear);
                pendingApprovalFromBranchUserCount = issPortalFileRepository.findPendingForApprovalCountByBanchUser(talukaMaster.getId(), financialYear);

                completedCount = issPortalFileRepository.findCompletedCountByTalukaId(talukaMaster.getId(), financialYear);
                inProgressCount = issPortalFileRepository.findInProgressCountByTalukaId(talukaMaster.getId(), financialYear);
                // pendingApprovalFromBranchAdminCount = totalIssPortalFile - notNullIssPortalFile;
                pendingApprovalFromBranchAdminCount = issPortalFileRepository.findPendingForApprovalCountByBanchAdmin(talukaMaster.getId(), financialYear);
                yetToStartCount = countOfSocietiesByTalukaName - completedCount - inProgressCount - pendingApprovalFromBranchUserCount - pendingApprovalFromBranchAdminCount;

                talukaWiseDataReport.setCompleted(completedCount);
                talukaWiseDataReport.setInProgress(inProgressCount);
                talukaWiseDataReport.setPendingApprovalFromBranchUser(pendingApprovalFromBranchUserCount);
                talukaWiseDataReport.setPendingApprovalFromBranchAdmin(pendingApprovalFromBranchAdminCount);
                talukaWiseDataReport.setYetToStart(yetToStartCount);

                talukaWiseDataReportList.add(talukaWiseDataReport);


            }
        }


        List<TalukaWiseDataReport> talukaWiseDataReportList1 = talukaWiseDataReportList.stream()
            .sorted(Comparator.comparing(TalukaWiseDataReport::getTalukaName))
            .collect(Collectors.toList());

        int srno = 0;
        for (TalukaWiseDataReport talukaWiseDataReport2 : talukaWiseDataReportList1) {
            srno = srno + 1;
            talukaWiseDataReport2.setSrNo(srno);
        }


        return talukaWiseDataReportList1;

    }

    @GetMapping("/taluka-wise-applications/{talukaId}/{financialYear}")
    public List<TalukaApplicationDTO> getBankBranchByTalukaId(@PathVariable Long talukaId, @PathVariable String financialYear) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
            List<TalukaApplicationDTO> TalukaApplicationDTOList = issPortalFileService.findIssPortalFilesByTalukaIdAndFinacialYear(talukaId, financialYear);
            return TalukaApplicationDTOList;
        } else throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
    }

    @GetMapping("/pacs-wise-applications/{schemeBranchCode}/{financialYear}")
    public List<PacsApplicationDTO> getIssPortalFilesBySchemeWiseBranchCodeAndYear(@PathVariable Long schemeBranchCode, @PathVariable String financialYear) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        if (authority.toString().equals(AuthoritiesConstants.ADMIN) || checkValidUser(auth, schemeBranchCode, authority)) {
            List<PacsApplicationDTO> pacsApplicationDTOList = issPortalFileService.findPacsWiseDataBySchemeBranchCodeAndFinacialYear(schemeBranchCode, financialYear);
            return pacsApplicationDTOList;
        } else throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
    }

    private boolean checkValidUser(Authentication auth, Long schemeWiseBranchCode, GrantedAuthority authority) {
        Optional<User> user = userRepository.findOneByLogin(auth.getName());

        User jhiUser = user.get();
        if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_ADMIN) || authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_USER)) {
            if (String.valueOf(schemeWiseBranchCode).equals(jhiUser.getSchemeWiseBranchCode())) {
                return true;
            } else return false;

        } else return false;

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
     * body the new issPortalFile, or with status {@code 400 (Bad Request)}
     * if the issPortalFile has already an ID.
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
     * the updated issPortalFile, or with status {@code 400 (Bad Request)}
     * if the issPortalFile is not valid, or with status
     * {@code 500 (Internal Server Error)} if the issPortalFile couldn't be
     * updated.
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
     * the updated issPortalFile, or with status {@code 400 (Bad Request)}
     * if the issPortalFile is not valid, or with status
     * {@code 404 (Not Found)} if the issPortalFile is not found, or with
     * status {@code 500 (Internal Server Error)} if the issPortalFile
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/iss-portal-files/{id}", consumes = {"application/json", "application/merge-patch+json"})
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
     * of issPortalFiles in body.
     */

    @GetMapping("/iss-portal-files")
    public ResponseEntity<List<IssPortalFile>> getAllIssPortalFiles(
        IssPortalFileCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        Page<IssPortalFile> page = null;
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


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());

//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Count", "" + page.size());
//        List<String> contentDispositionList = new ArrayList<>();
//        contentDispositionList.add("X-Total-Count");
//
//        headers.setAccessControlExposeHeaders(contentDispositionList);
//
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
     * in body.
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
     * the issPortalFile, or with status {@code 404 (Not Found)}.
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
            } catch (Exception e) {
            }
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


    //calculate financial year
    String calculateCurrentFinantialYear() {
        Calendar calendar = Calendar.getInstance();

        // Set the current date to the end of the financial year
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        calendar.set(Calendar.DAY_OF_MONTH, 31);

        int currentYear = calendar.get(Calendar.YEAR);

        // Financial year starts from April 1 of the previous year
        int previousYear = currentYear - 1;

        String financialYear = previousYear + "-" + currentYear;
        System.out.println("Current Financial Year: " + financialYear);
        return financialYear;

    }

    @GetMapping("/iss-portal-files/counts")
    public IssPortalFileCountDTO getIssPortalFileCount(@RequestParam String financialYear) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
            IssPortalFileCountDTO issPortalCount = issPortalFileService.findCounts(financialYear);
            return issPortalCount;
        } else throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");

    }

    @GetMapping("/taluka-branch-pacs-excel")
    public ResponseEntity<byte[]> talukaBranchPacsWiseExcel(@RequestParam String financialYear) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        List<IssPortalFileDTO> issPortalFileDTOList = new ArrayList<>();
        if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
            issPortalFileDTOList = issPortalFileService.findAllRecordsByTalukaBranchPacsWise(financialYear);

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Taluka wise data");
                int rowNum = 0;
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                row.createCell(colNum++).setCellValue("Financial Year ");
                row.createCell(colNum++).setCellValue("Taluka");
                row.createCell(colNum++).setCellValue("Branch");
                row.createCell(colNum++).setCellValue("Pacs");
                row.createCell(colNum++).setCellValue("Total Application");
                row.createCell(colNum++).setCellValue("Validation Error");
                row.createCell(colNum++).setCellValue("KCC Accepted");
                row.createCell(colNum++).setCellValue("KCC Rejected");
                row.createCell(colNum++).setCellValue("KCC Pending");

                for (IssPortalFileDTO issPortalFile : issPortalFileDTOList) {
                    colNum = 0;
                    row = sheet.createRow(rowNum++);
                    row.createCell(colNum++).setCellValue(financialYear);
                    row.createCell(colNum++).setCellValue(issPortalFile.getTaluka());
                    row.createCell(colNum++).setCellValue(issPortalFile.getBranch());
                    row.createCell(colNum++).setCellValue(issPortalFile.getPacs());
                    row.createCell(colNum++).setCellValue(issPortalFile.getTotalApplications());
                    row.createCell(colNum++).setCellValue(issPortalFile.getValidationErrors());
                    row.createCell(colNum++).setCellValue(issPortalFile.getkCCAccepted());
                    row.createCell(colNum++).setCellValue(issPortalFile.getkCCRejected());
                    row.createCell(colNum++).setCellValue(issPortalFile.getkCCPending());

                }

                // Write Excel to ByteArrayOutputStream
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                outputStream.close();
                byte[] excelContent = outputStream.toByteArray();

                // Set up the HTTP headers for the response
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
                headers.setContentDispositionFormData("filename", "TalukaBranchPacs" + "-" + getUniqueName() + ".xlsx");

                List<String> contentDispositionList = new ArrayList<>();
                contentDispositionList.add("Content-Disposition");

                headers.setAccessControlExposeHeaders(contentDispositionList);
                return new ResponseEntity<>(excelContent, headers, 200);
            } catch (IOException e) {
                return ResponseEntity.status(500).body(null);
            }


        } else throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
    }

    @GetMapping("/pacs-not-yet-started-excel")
    public ResponseEntity<byte[]> pacsNotYetStartedExcel(@RequestParam String financialYear) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        List<NotYetStartedDTO> notYetStartedDTOList = new ArrayList<>();
        if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {

            notYetStartedDTOList = issPortalFileService.findAllRecordsWhoNotStarted(financialYear);

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("PacsNotYetStarted");
                int rowNum = 0;
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                row.createCell(colNum++).setCellValue("Financial Year");
                row.createCell(colNum++).setCellValue("PACS Number");
                row.createCell(colNum++).setCellValue("PACS Name");
                row.createCell(colNum++).setCellValue("Branch Name");
                row.createCell(colNum++).setCellValue("Taluka Name");

                for (NotYetStartedDTO notYetStartedDTO : notYetStartedDTOList) {
                    colNum = 0;
                    row = sheet.createRow(rowNum++);
                    row.createCell(colNum++).setCellValue(financialYear);
                    row.createCell(colNum++).setCellValue(notYetStartedDTO.getPacsNumber());
                    row.createCell(colNum++).setCellValue(notYetStartedDTO.getPacsName());
                    row.createCell(colNum++).setCellValue(notYetStartedDTO.getBranchName());
                    row.createCell(colNum++).setCellValue(notYetStartedDTO.getTalukaName());
                }

                // Write Excel to ByteArrayOutputStream
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                outputStream.close();
                byte[] excelContent = outputStream.toByteArray();

                // Set up the HTTP headers for the response
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                headers.setContentDispositionFormData("filename", "PacsWhoNotStarted" + "-" +  financialYear + "-" +getUniqueName() + ".xlsx");

                List<String> contentDispositionList = new ArrayList<>();
                contentDispositionList.add("Content-Disposition");

                headers.setAccessControlExposeHeaders(contentDispositionList);
                return new ResponseEntity<>(excelContent, headers, 200);
            } catch (IOException e) {
                return ResponseEntity.status(500).body(null);
            }


        } else throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
    }

    @GetMapping("/pending-verification")
    public ResponseEntity<byte[]> pendingFromBranchAdminExcel(@RequestParam String user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();
        List<VerifyPendingDTO> verifyPendingDTOList = new ArrayList<>();
        if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {

            if(user.equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_ADMIN)){
                verifyPendingDTOList = issPortalFileService.findAllpendingFromBranchAdmin();
            }else if(user.equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_USER)){
                verifyPendingDTOList = issPortalFileService.findAllpendingFromBranchUser();
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = null;
                if(user.equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_ADMIN)){
                     sheet = workbook.createSheet("PendingFromBranchAdmin");
                }
                else if (user.equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_USER)) {
                    sheet = workbook.createSheet("PendingFromBranchUser");
                }

                int rowNum = 0;
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                row.createCell(colNum++).setCellValue("Financial Year");
                row.createCell(colNum++).setCellValue("Branch Name");
                row.createCell(colNum++).setCellValue("PACS Number");
                row.createCell(colNum++).setCellValue("PACS Name");

                for (VerifyPendingDTO verifyPendingDTO : verifyPendingDTOList) {
                    colNum = 0;
                    row = sheet.createRow(rowNum++);
                    row.createCell(colNum++).setCellValue(verifyPendingDTO.getFinancialYear());
                    row.createCell(colNum++).setCellValue(verifyPendingDTO.getBranchName());
                    row.createCell(colNum++).setCellValue(verifyPendingDTO.getPacsNumber());
                    row.createCell(colNum++).setCellValue(verifyPendingDTO.getPacsName());

                }

                // Write Excel to ByteArrayOutputStream
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                outputStream.close();
                byte[] excelContent = outputStream.toByteArray();

                // Set up the HTTP headers for the response
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                if(user.equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_ADMIN)){
                    headers.setContentDispositionFormData("filename", "PendingFromBranchAdmin" + "-" + getUniqueName() + ".xlsx");
                }else if(user.equalsIgnoreCase(AuthoritiesConstants.ROLE_BRANCH_USER)){
                    headers.setContentDispositionFormData("filename", "PendingFromBranchUser" + "-" + getUniqueName() + ".xlsx");
                }

                List<String> contentDispositionList = new ArrayList<>();
                contentDispositionList.add("Content-Disposition");

                headers.setAccessControlExposeHeaders(contentDispositionList);
                return new ResponseEntity<>(excelContent, headers, 200);
            } catch (IOException e) {
                return ResponseEntity.status(500).body(null);
            }


        } else throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
    }


}
