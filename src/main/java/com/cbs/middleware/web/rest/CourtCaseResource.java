package com.cbs.middleware.web.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.domain.CourtCaseFile;
import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.domain.CourtCaseSettingFile;
import com.cbs.middleware.domain.One01ReportParam;
import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.CourtCaseFileRepository;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.repository.CourtCaseSettingFileRepository;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.security.RBAControl;
import com.cbs.middleware.service.CourtCaseQueryService;
import com.cbs.middleware.service.CourtCaseService;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.layout.font.FontProvider;

import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
/**
 * REST controller for managing {@link com.cbs.middleware.domain.CourtCase}.
 */
@RestController
@RequestMapping("/api")
public class CourtCaseResource {

    private final Logger log = LoggerFactory.getLogger(CourtCaseResource.class);

    private static final String ENTITY_NAME = "courtCase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourtCaseService courtCaseService;

    private final CourtCaseRepository courtCaseRepository;

    private final CourtCaseQueryService courtCaseQueryService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Autowired
    TranslationServiceUtility translationServiceUtility;

    @Autowired
    CourtCaseSettingRepository caseSettingRepository;
    
    
    @Autowired
    RBAControl rbaControl;
    
    @Autowired
    ResourceLoader resourceLoader;
    
    @Autowired
    private SpringTemplateEngine templateEngine;
    
    @Autowired
    private CourtCaseSettingFileRepository caseSettingFileRepository;
    
    @Autowired
    private CourtCaseFileRepository courtCaseFileRepository;
    
    @Autowired
    private PacsMasterRepository pacsMasterRepository;
    
    @Autowired
    private BankBranchMasterRepository branchMasterRepository;
    
    @Autowired
    BankBranchPacksCodeGet bankBranchPacksCodeGet;

    public CourtCaseResource(
        CourtCaseService courtCaseService,
        CourtCaseRepository courtCaseRepository,
        CourtCaseQueryService courtCaseQueryService
    ) {
        this.courtCaseService = courtCaseService;
        this.courtCaseRepository = courtCaseRepository;
        this.courtCaseQueryService = courtCaseQueryService;
    }
    
    
    /**
     * {@code POST  /oneZeroOneNotice} : download a courtCase file.
     *
     */    
    @PostMapping("/oneZeroOneNotice")
    public ResponseEntity<byte[]> generatePDFFromHTML(@RequestBody One01ReportParam one01ReportParam) throws Exception {
    	
    	
    	List<String> htmlList=new ArrayList<String>();
    	
        CourtCaseCriteria criteria = new CourtCaseCriteria();
        
        //creating filter
        StringFilter talukaFilter = new StringFilter();
        talukaFilter.setEquals(one01ReportParam.getTalukaName());

        StringFilter bankFilter = new StringFilter();
        bankFilter.setEquals(one01ReportParam.getBankName());
        
        StringFilter financialYear = new StringFilter();
        financialYear.setEquals(one01ReportParam.getFinancialYear());
        
        //adding initial values
        criteria.setTalukaName(talukaFilter);
        criteria.setBankName(bankFilter);
        criteria.setFinancialYear(financialYear);
        
        
        
        
        switch (one01ReportParam.getOneZeroOneOption()) {
        case "NoticeofRepayLoan":
			// call function to generate html string
        	htmlList=new ArrayList<String>();
        	for (CourtCase courtCase : getCourtCaseList(criteria, one01ReportParam.getSabhasadName())) {
				// generating html from template
				String htmlStringForPdf = oneZeroOneTemplate("oneZeroOneNotice/NoticeofRepayLoan.html",courtCase, getCourtCaseSetting(courtCase.getSettingCode()));
				htmlList.add(htmlStringForPdf);
			}

			break;
			
        case "PriorDemandNotice":
			// call function to generate html string
        	htmlList=new ArrayList<String>();
        	for (CourtCase courtCase : getCourtCaseList(criteria, one01ReportParam.getSabhasadName())) {
				// generating html from template
        		String htmlStringForPdf = oneZeroOneTemplate("oneZeroOneNotice/PriorDemandNotice.html",courtCase, getCourtCaseSetting(courtCase.getSettingCode()));
				htmlList.add(htmlStringForPdf);
			}
			break;
			
		case "101prakaran":
			// call function to generate html string
			htmlList=new ArrayList<String>();
			for (CourtCase courtCase : getCourtCaseList(criteria, one01ReportParam.getSabhasadName())) {
				// generating html from template
				String htmlStringForPdf = oneZeroOneTemplate("oneZeroOneNotice/101prakaran.html",courtCase, getCourtCaseSetting(courtCase.getSettingCode()));
				htmlList.add(htmlStringForPdf);
			}
			break;
			
			
			
		case "ShetiKarj":
			// call function to generate html string
			htmlList=new ArrayList<String>();
			for (CourtCase courtCase : getCourtCaseList(criteria, one01ReportParam.getSabhasadName())) {
				// generating html from template
				String htmlStringForPdf = oneZeroOneTemplate("oneZeroOneNotice/ShetiKarj.html",courtCase, getCourtCaseSetting(courtCase.getSettingCode()));
				htmlList.add(htmlStringForPdf);
			}
			break;
			
			
		case "BigarShetiKarj":
			// call function to generate html string
			htmlList=new ArrayList<String>();
			for (CourtCase courtCase : getCourtCaseList(criteria, one01ReportParam.getSabhasadName())) {
				// generating html from template
				String htmlStringForPdf = oneZeroOneTemplate("oneZeroOneNotice/BigarShetiKarj.html",courtCase, getCourtCaseSetting(courtCase.getSettingCode()));
				htmlList.add(htmlStringForPdf);
			}
			break;

		case "Appendix3":
			// call function to generate html string
			htmlList=new ArrayList<String>();
			for (CourtCase courtCase : getCourtCaseList(criteria, one01ReportParam.getSabhasadName())) {
				// generating html from template
				String htmlStringForPdf = oneZeroOneTemplate("oneZeroOneNotice/Appendix3.html",courtCase, getCourtCaseSetting(courtCase.getSettingCode()));
				htmlList.add(htmlStringForPdf);
			}
			break;

		case "Appendix4":
			// call function to generate html string
			htmlList=new ArrayList<String>();
			for (CourtCase courtCase : getCourtCaseList(criteria, one01ReportParam.getSabhasadName())) {
				// generating html from template
				String htmlStringForPdf = oneZeroOneTemplate("oneZeroOneNotice/Appendix4.html",courtCase, getCourtCaseSetting(courtCase.getSettingCode()));
				htmlList.add(htmlStringForPdf);
			}
			break;

		default:

		}
        
        
        
        ResponseEntity<byte[]> response=null;
        if(htmlList.size()==1)
        {
        	//code for the generating pdf from html string
            
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();

            FontProvider fontProvider = new FontProvider();
            
            
            File file=new File(Constants.fontFilePath);
            
            
           // Resource resource = resourceLoader.getResource("classpath:" + "fonts/NotoSans-Regular.ttf");
            //String filepath=resource.getFile().getAbsolutePath();
            
            String filepath=file.getAbsolutePath();
            
            
            
            
            fontProvider.addFont(filepath, PdfEncodings.IDENTITY_H);

            converterProperties.setFontProvider(fontProvider);
            converterProperties.setCharset("UTF-8");
            
            //converting html to pdf
            HtmlConverter.convertToPdf(htmlList.get(0), byteArrayOutputStream, converterProperties);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("content-disposition", "attachment; filename=" +getUniqueNumberString()+ "certificate.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
        }
        else if(htmlList.size()>1)
        {
        	
        	  // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();

            FontProvider fontProvider = new FontProvider();
            Resource resource = resourceLoader.getResource("classpath:" + "fonts/NotoSans-Regular.ttf");
            String filepath=resource.getFile().getAbsolutePath();
            fontProvider.addFont(filepath, PdfEncodings.IDENTITY_H);

            converterProperties.setFontProvider(fontProvider);
            converterProperties.setCharset("UTF-8");
        	
        	try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

                for (String htmlString : htmlList) {
                	
                	//code for the generating pdf from html string
                    
                	ByteArrayOutputStream  byteArrayOutputStream1 = new ByteArrayOutputStream();
                	//converting html to pdf
                    HtmlConverter.convertToPdf(htmlString, byteArrayOutputStream1, converterProperties);

                	//adding files in zip
                    ZipEntry zipEntry = new ZipEntry("certificate"+getUniqueNumberString()+".pdf");
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.write(byteArrayOutputStream1.toByteArray());
                    zipOutputStream.closeEntry();
                }

                zipOutputStream.close();
                byteArrayOutputStream.close();

                byte[] zipBytes = byteArrayOutputStream.toByteArray();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "file"+getUniqueNumberString()+".zip");

                return new ResponseEntity<>(zipBytes, headers, org.springframework.http.HttpStatus.OK);
            } catch (Exception e) {
            	  throw new BadRequestAlertException("Error in file downloading", ENTITY_NAME, "errorInFileDownload");
            }
        	
        }
        else
        {
        	 throw new BadRequestAlertException("Error in file downloading", ENTITY_NAME, "errorInFileDownload");
        }
        
        return response;
    }

    
/**
 * upload court case and court case setting file
 * 
 **/   
    
    @PostMapping("/court-case-and-setting-file")
    public ResponseEntity<String> uploadCourtCaseAndSettingFile(
    	@RequestParam("courtCaseFile") MultipartFile courtCaseFile,
        @RequestParam("courtCaseSettingFile") MultipartFile courtCaseSettingFile,
        @RequestParam("financialYear") String financialYear,
        @RequestParam("courtCaseDate") LocalDate courtCaseDate,
        RedirectAttributes redirectAttributes
    ) throws Exception {
    	
    	 // Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();
    	
    	//validating court Case File
    	String courtCaseFileExtension = FilenameUtils.getExtension(courtCaseFile.getOriginalFilename());
        if (!"xlsx".equalsIgnoreCase(courtCaseFileExtension)&&!"xls".equalsIgnoreCase(courtCaseFileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }
        if (courtCaseRepository.existsByFileName(courtCaseFile.getOriginalFilename())) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }
    	
    	
    	//validating court Case Setting File
    	String courtCaseSettingFileExtension = FilenameUtils.getExtension(courtCaseSettingFile.getOriginalFilename());
        if (!"xlsx".equalsIgnoreCase(courtCaseSettingFileExtension)&&!"xls".equalsIgnoreCase(courtCaseFileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }
        if (caseSettingRepository.existsByFileName(courtCaseSettingFile.getOriginalFilename())) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }

        //validating court case setting header name
        try (Workbook workbook = WorkbookFactory.create(courtCaseSettingFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int filecount = 0;
            Row rowForCCS = null;
            for (int i = 0; i < 10; i++) {
            	rowForCCS = sheet.getRow(i); // Get the current row
                String financialYearInFile = getCellValue(rowForCCS.getCell(0));
                if (StringUtils.isNotBlank(financialYearInFile)) {
                    financialYearInFile = financialYearInFile.trim().replace("\n", " ").toLowerCase();
                    if (financialYearInFile.contains("vasuli") && financialYearInFile.contains("adhikari")) {
                        filecount = i;
                        break;
                    }
                }
            }

            rowForCCS = sheet.getRow(filecount); // Get the current row
            boolean flagForLabel = false;
            String srNo = getCellValue(rowForCCS.getCell(0));
            if (StringUtils.isNotBlank(srNo)) {
                srNo = srNo.trim().replace("\n", " ").toLowerCase();
                if (!srNo.contains("vasuli") || !srNo.contains("adhikari") || !srNo.contains("name")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String secondNoticeDate = getCellValue(rowForCCS.getCell(13));
            if (StringUtils.isNoneBlank(secondNoticeDate)) {
                secondNoticeDate = secondNoticeDate.trim().replace("\n", " ").toLowerCase();
                if (!secondNoticeDate.contains("meeting") || !secondNoticeDate.contains("time")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            if (flagForLabel) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }
        } catch (BadRequestAlertException e) {
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (ForbiddenAuthRequestAlertException e) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (UnAuthRequestAlertException e) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (Exception e) {
            throw new Exception("Exception", e);
        }

        
        String societyOrBranchName="";
        String societyOrBranchAddress="";
        
        
        //validating court case file header name
        try (Workbook workbook = WorkbookFactory.create(courtCaseFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int filecount = 0;
            Row rowForCS = null;
            for (int i = 0; i < 10; i++) {
            	rowForCS = sheet.getRow(i); // Get the current row
            	if(rowForCS!=null)
            	{
            		 String financialYearInFile = getCellValue(rowForCS.getCell(0));
                     if (StringUtils.isNotBlank(financialYearInFile)) {
                         financialYearInFile = financialYearInFile.trim().replace("\n", " ").toLowerCase();
                         if (financialYearInFile.contains("sr") && financialYearInFile.contains("no")) {
                             filecount = i;
                             break;
                         }
                     }
            	}
            	
               
            }

            rowForCS = sheet.getRow(filecount); // Get the current row
            
            boolean flagForLabel = false;
            
            
            String srNo = getCellValue(rowForCS.getCell(0));

            if (StringUtils.isNotBlank(srNo)) {
                srNo = srNo.trim().replace("\n", " ").toLowerCase();
                if (!srNo.contains("sr") && !srNo.contains("no")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String financialYearLabel = getCellValue(rowForCS.getCell(1));
            if (StringUtils.isNotBlank(financialYearLabel)) {
            	financialYearLabel = financialYearLabel.trim().replace("\n", " ").toLowerCase();
                if (!financialYearLabel.contains("financial") && !financialYearLabel.contains("year")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String branchOrPacsCode = getCellValue(rowForCS.getCell(2));
            if (StringUtils.isNotBlank(branchOrPacsCode)) {
            	branchOrPacsCode = branchOrPacsCode.trim().replace("\n", " ").toLowerCase();
                if (!branchOrPacsCode.contains("branch") && !branchOrPacsCode.contains("pacs")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String acountNo = getCellValue(rowForCS.getCell(3));
            if (StringUtils.isNotBlank(acountNo)) {
            	acountNo = acountNo.trim().replace("\n", " ").toLowerCase();
                if (!acountNo.contains("a/c") && !acountNo.contains("no")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            

            String nameOfDefaulter = getCellValue(rowForCS.getCell(4));
            if (StringUtils.isNotBlank(nameOfDefaulter)) {
            	nameOfDefaulter = nameOfDefaulter.trim().replace("\n", " ").toLowerCase();
                if (!nameOfDefaulter.contains("name") && !nameOfDefaulter.contains("defaulter")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            
            
            String address = getCellValue(rowForCS.getCell(5));
            if (StringUtils.isNotBlank(address)) {
            	address = address.trim().replace("\n", " ").toLowerCase();
                if (!address.contains("address")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String loanType = getCellValue(rowForCS.getCell(6));
            if (StringUtils.isNotBlank(loanType)) {
            	loanType = loanType.trim().replace("\n", " ").toLowerCase();
                if (!loanType.contains("loan")&&!loanType.contains("type")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String loanAmount = getCellValue(rowForCS.getCell(7));
            if (StringUtils.isNotBlank(loanAmount)) {
            	loanAmount = loanAmount.trim().replace("\n", " ").toLowerCase();
                if (!loanAmount.contains("loan")&&!loanAmount.contains("amount")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String loanDate = getCellValue(rowForCS.getCell(8));
            if (StringUtils.isNotBlank(loanDate)) {
            	loanDate = loanDate.trim().replace("\n", " ").toLowerCase();
                if (!loanDate.contains("loan")&&!loanDate.contains("date")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String termOfLoan = getCellValue(rowForCS.getCell(9));
            if (StringUtils.isNotBlank(termOfLoan)) {
            	termOfLoan = termOfLoan.trim().replace("\n", " ").toLowerCase();
                if (!termOfLoan.contains("term")&&!termOfLoan.contains("loan")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            

            
            String interestRate = getCellValue(rowForCS.getCell(10));
            if (StringUtils.isNotBlank(interestRate)) {
            	interestRate = interestRate.trim().replace("\n", " ").toLowerCase();
                if (!interestRate.contains("interest")&&!interestRate.contains("rate")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String installmentAmount = getCellValue(rowForCS.getCell(11));
            if (StringUtils.isNotBlank(installmentAmount)) {
            	installmentAmount = installmentAmount.trim().replace("\n", " ").toLowerCase();
                if (!installmentAmount.contains("installment")&&!installmentAmount.contains("amount")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            
            String totalCredit = getCellValue(rowForCS.getCell(12));
            if (StringUtils.isNotBlank(totalCredit)) {
            	totalCredit = totalCredit.trim().replace("\n", " ").toLowerCase();
                if (!totalCredit.contains("total")&&!totalCredit.contains("credit")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String interestPaid = getCellValue(rowForCS.getCell(13));
            if (StringUtils.isNotBlank(interestPaid)) {
            	interestPaid = interestPaid.trim().replace("\n", " ").toLowerCase();
                if (!interestPaid.contains("interest")&&!interestPaid.contains("paid")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String penalInterestPaid = getCellValue(rowForCS.getCell(14));
            if (StringUtils.isNotBlank(penalInterestPaid)) {
            	penalInterestPaid = penalInterestPaid.trim().replace("\n", " ").toLowerCase();
                if (!penalInterestPaid.contains("penal")&&!penalInterestPaid.contains("interest")&&
                		!penalInterestPaid.contains("paid")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            
            String balance = getCellValue(rowForCS.getCell(15));
            if (StringUtils.isNotBlank(balance)) {
            	balance = balance.trim().replace("\n", " ").toLowerCase();
                if (!balance.contains("balance")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String dueAmount = getCellValue(rowForCS.getCell(16));
            if (StringUtils.isNotBlank(dueAmount)) {
            	dueAmount = dueAmount.trim().replace("\n", " ").toLowerCase();
                if (!dueAmount.contains("due")&&!dueAmount.contains("amount")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String dueDate = getCellValue(rowForCS.getCell(17));
            if (StringUtils.isNotBlank(dueDate)) {
            	dueDate = dueDate.trim().replace("\n", " ").toLowerCase();
                if (!dueDate.contains("due")&&!dueDate.contains("date")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String dueInterest = getCellValue(rowForCS.getCell(18));
            if (StringUtils.isNotBlank(dueInterest)) {
            	dueInterest = dueInterest.trim().replace("\n", " ").toLowerCase();
                if (!dueInterest.contains("due")&&!dueInterest.contains("interest")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String duePenalInterest = getCellValue(rowForCS.getCell(19));
            if (StringUtils.isNotBlank(duePenalInterest)) {
            	duePenalInterest = duePenalInterest.trim().replace("\n", " ").toLowerCase();
                if (!duePenalInterest.contains("due")&&!duePenalInterest.contains("penal")
                		&&!duePenalInterest.contains("interest")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String dueMoreInterest = getCellValue(rowForCS.getCell(20));
            if (StringUtils.isNotBlank(dueMoreInterest)) {
            	dueMoreInterest = dueMoreInterest.trim().replace("\n", " ").toLowerCase();
                if (!dueMoreInterest.contains("due")&&!dueMoreInterest.contains("more")
                		&&!dueMoreInterest.contains("interest")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            String interestRecivable= getCellValue(rowForCS.getCell(21));
            if (StringUtils.isNotBlank(interestRecivable)) {
            	interestRecivable = interestRecivable.trim().replace("\n", " ").toLowerCase();
                if (!interestRecivable.contains("interest")&&!interestRecivable.contains("recivable")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String gaurentorOne = getCellValue(rowForCS.getCell(22));
            if (StringUtils.isNotBlank(gaurentorOne)) {
            	gaurentorOne = gaurentorOne.trim().replace("\n", " ").toLowerCase();
                if (!gaurentorOne.contains("gaurentor")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            
            String gaurentorOneAddress = getCellValue(rowForCS.getCell(23));
            if (StringUtils.isNotBlank(gaurentorOneAddress)) {
            	gaurentorOneAddress = gaurentorOneAddress.trim().replace("\n", " ").toLowerCase();
                if (!gaurentorOneAddress.contains("gaurentor")&&!gaurentorOneAddress.contains("address")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String gaurentorTwo= getCellValue(rowForCS.getCell(24));
            if (StringUtils.isNotBlank(gaurentorTwo)) {
            	gaurentorTwo = gaurentorTwo.trim().replace("\n", " ").toLowerCase();
                if (!gaurentorTwo.contains("gaurentor")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            String gaurentorTwoAddress = getCellValue(rowForCS.getCell(25));
            if (StringUtils.isNotBlank(gaurentorTwoAddress)) {
            	gaurentorTwoAddress = gaurentorTwoAddress.trim().replace("\n", " ").toLowerCase();
                if (!gaurentorTwoAddress.contains("gaurentor")&&!gaurentorTwoAddress.contains("address")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }
            
            
            rowForCS = sheet.getRow(filecount+1); 
            String branchOrPacsCodeValue = getCellValue(rowForCS.getCell(2));
            
            if(StringUtils.isNotBlank(branchOrPacsCodeValue))
            {
            	//validating user
            	rbaControl.checkValidationForUsers(branchOrPacsCodeValue);
                
            	//checking code is available in db or not
                Optional<PacsMaster> findOneByPacsNumber = pacsMasterRepository.findOneByPacsNumber(branchOrPacsCodeValue);
                Optional<BankBranchMaster> findOneBySchemeWiseBranchCode = branchMasterRepository.findOneBySchemeWiseBranchCode(branchOrPacsCodeValue);

                if(findOneByPacsNumber.isEmpty()&&findOneBySchemeWiseBranchCode.isEmpty())
                {
                	throw new BadRequestAlertException("Invalid pacs code or scheme wise branch code", ENTITY_NAME, "fileInvalid");
                }
                
                
                String packsOrBranchNumber="";
				if (findOneByPacsNumber.isPresent()) {
					packsOrBranchNumber=findOneByPacsNumber.get().getPacsNumber();
					societyOrBranchName = findOneByPacsNumber.get().getPacsNameMr();
					societyOrBranchAddress = findOneByPacsNumber.get().getPacsAddressMr();

				} else if (findOneBySchemeWiseBranchCode.isPresent()) {
					packsOrBranchNumber=findOneBySchemeWiseBranchCode.get().getSchemeWiseBranchCode();
					societyOrBranchName = findOneBySchemeWiseBranchCode.get().getBranchNameMr();
					societyOrBranchAddress = findOneBySchemeWiseBranchCode.get().getBranchAddressMr();
				}
                
                
				
//				 if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
//		              //page =issFileParserQueryService.findByCriteriaPackNumber(criteria, pageable, branchOrPacksNumber.get(Constants.PACKS_CODE_KEY));
//		          if(!branchOrPacksNumber.get(Constants.PACKS_CODE_KEY).equalsIgnoreCase(packsOrBranchNumber))
//		          {
//		        	  
//		        	  
//		          }
//				 
//				 
//				 } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY))) {
//		              //page =issFileParserQueryService.findByCriteriaSchemeWiseBranchCode(criteria,pageable,branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY));
//		          
//				 
//				 
//				 } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
//		         //     page = issFileParserQueryService.findByCriteria(criteria, pageable);
//		         
//				 
//				 
//				 
//				 } else {
//		              throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
//		          }
            	
            }else
            {
            	 throw new ForbiddenAuthRequestAlertException("Invalid file, required pacs or branch code", ENTITY_NAME, "invalidFile");
            }
            
            
            if (flagForLabel) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }
        } catch (BadRequestAlertException e) {
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (ForbiddenAuthRequestAlertException e) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (UnAuthRequestAlertException e) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (Exception e) {
            throw new Exception("Exception", e);
        }
        
        
      //getting records from court case setting file
        List<CourtCaseSetting> courtCaseSettingList = createCourtCaseSettingFileRecords(courtCaseSettingFile,financialYear,societyOrBranchAddress);
        
        //getting records from court case setting file
        List<CourtCase> courtCaseList = createCourtCaseFileRecords(courtCaseFile, financialYear, societyOrBranchName, societyOrBranchAddress);
        
        if (!courtCaseList.isEmpty() && !courtCaseSettingList.isEmpty()) {
        	
			CourtCaseFile courtCaseFileObj = new CourtCaseFile();
			courtCaseFileObj.setFileName(courtCaseFile.getOriginalFilename());
			courtCaseFileObj.setUniqueFileName(courtCaseList.get(0).getUniqueFileName());
			final CourtCaseFile finalCourtCaseFile = courtCaseFileRepository.save(courtCaseFileObj);

			CourtCaseSettingFile courtCaseSettingFileObj = new CourtCaseSettingFile();
			courtCaseSettingFileObj.setFileName(courtCaseSettingFile.getOriginalFilename());
			courtCaseSettingFileObj.setUniqueFileName(courtCaseSettingList.get(0).getUniqueFileName());
			final CourtCaseSettingFile finalCourtCaseSettingFile = caseSettingFileRepository.save(courtCaseSettingFileObj);
			
			
			
			 String totalPostageValue = getTotalPostageValue(courtCaseSettingList.get(0).getVasuliExpenseEn(),
					 courtCaseSettingList.get(0).getOtherExpenseEn(),
					 courtCaseSettingList.get(0).getNoticeExpenseEn());
					 
			
			//Marathi
			String settingCodeText = translationServiceUtility.translationText(""+finalCourtCaseFile.getId());
			String courtCaseDateText = translationServiceUtility.oneZeroOneDateMr(courtCaseDate);
			
			courtCaseList.forEach(cc -> {
				cc.setCourtCaseFile(finalCourtCaseFile);
				cc.setSettingCode(settingCodeText);
				cc.setSettingCodeEn(finalCourtCaseFile.getId());
				cc.setFirstNoticeDate(courtCaseDate);
				cc.setFirstNoticeDateMr(courtCaseDateText);
				cc.setTotalPostage(totalPostageValue);
				});
			
			
			courtCaseSettingList.forEach(ccs -> {ccs.setCourtCaseSettingFile(finalCourtCaseSettingFile);
			ccs.setSettingCode(settingCodeText);
			ccs.setSettingCodeEn(finalCourtCaseFile.getId());
			});
			
			
			
            courtCaseRepository.saveAll(courtCaseList);
            caseSettingRepository.saveAll(courtCaseSettingList);

            if (courtCaseList.get(0) != null) {
                try {
                    notificationDataUtility.notificationData(
                        "Court Case record file uploaded",
                        "Court Case record file : " + courtCaseFile.getOriginalFilename() + " uploaded",
                        false,
                        courtCaseList.get(0).getCreatedDate(),
                        "CourtCaseRecordFileUploaded" // type
                    );
                } catch (Exception e) {}
            }
            
            if (courtCaseSettingList.get(0) != null) {
                try {
                    notificationDataUtility.notificationData(
                        "Court Case record file uploaded",
                        "Court Case record file : " + courtCaseSettingFile.getOriginalFilename() + " uploaded",
                        false,
                        courtCaseSettingList.get(0).getCreatedDate(),
                        "CourtCaseRecordFileUploaded" // type
                    );
                } catch (Exception e) {}
            }

            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ""))
                .body("File Uploaded Successfully");
        } else {
            throw new BadRequestAlertException("Error in file uploading", ENTITY_NAME, "ErrorInFileUpload");
        }
        
       
    }
    
    
    /**
    *
    * @param courtCaseSettingFile the courtCaseSetting to create.
    * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
    *         body the new courtCase, or with status {@code 400 (Bad Request)} if
    *         the courtCaseSetting has already an ID.
    * @throws Exception
    */
    
    private List<CourtCaseSetting> createCourtCaseSettingFileRecords(
    	MultipartFile courtCaseSettingFile, String financialYear,
    	String branchOrPacsCodeValue
    ) throws Exception {
    	
    	//validating court Case Setting File
    	String courtCaseSettingFileExtension = FilenameUtils.getExtension(courtCaseSettingFile.getOriginalFilename());
        File originalFileDir = new File(Constants.ORIGINAL_FILE_PATH);
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

        Path path = Paths.get(filePath + File.separator + uniqueName + "." + courtCaseSettingFileExtension);
        try {
            byte[] imgbyte = null;
            imgbyte = courtCaseSettingFile.getBytes();
            Files.write(path, imgbyte);
        } catch (IOException e) {
            throw new BadRequestAlertException("file not saved successfully", ENTITY_NAME, "fileInvalid");
        }

        int startRowIndex = 3; // Starting row index
        List<CourtCaseSetting> courtCaseSettingList = new ArrayList<>();
       
        int recourdCount=0;
        try (Workbook workbook = WorkbookFactory.create(courtCaseSettingFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
            	recourdCount=recourdCount+1;
                Row row = sheet.getRow(rowIndex); // Get the current row
                CourtCaseSetting courtCaseSetting = new CourtCaseSetting();
                if (row != null) {
                    if (
                        StringUtils.isBlank(getCellValue(row.getCell(0))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(1))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(2))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(3)))
                    ) {
                        break;
                    }
                    
                    if(recourdCount==2)
                    {
                    	throw new BadRequestAlertException("court case setting file must require only one record", ENTITY_NAME, "fileInvalid");
                    }
                    
                    
                    
                   courtCaseSetting.setFileName(courtCaseSettingFile.getOriginalFilename());
                   courtCaseSetting.setUniqueFileName(uniqueName + "." + courtCaseSettingFileExtension);
                   courtCaseSetting.setBranchOrPacsCode(branchOrPacsCodeValue);
                    
                    courtCaseSetting.setFinancialYear(financialYear);

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(0)))) {
                        //English
                        courtCaseSetting.setVasuliAdhikariNameEn(getCellValue(row.getCell(0)));

                        //Marathi
                        courtCaseSetting.setVasuliAdhikariName(translationServiceUtility.translationText(getCellValue(row.getCell(0))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(1)))) {
                        //English
                        courtCaseSetting.setArOfficeNameEn(getCellValue(row.getCell(1)));

                        //Marathi
                        courtCaseSetting.setArOfficeName(translationServiceUtility.translationText(getCellValue(row.getCell(1))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(2)))) {
                        //English
                        courtCaseSetting.setChairmanNameEn(getCellValue(row.getCell(2)));

                        //Marathi
                        courtCaseSetting.setChairmanName(translationServiceUtility.translationText(getCellValue(row.getCell(2))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(3)))) {
                        //English
                        courtCaseSetting.setSachivNameEn(getCellValue(row.getCell(3)));

                        //Marathi
                        courtCaseSetting.setSachivName(translationServiceUtility.translationText(getCellValue(row.getCell(3))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(4)))) {
                        //English
                        courtCaseSetting.setSuchakNameEn(getCellValue(row.getCell(4)));

                        //Marathi
                        courtCaseSetting.setSuchakName(translationServiceUtility.translationText(getCellValue(row.getCell(4))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(5)))) {
                        //English
                        courtCaseSetting.setAnumodakNameEn(getCellValue(row.getCell(5)));

                        //Marathi
                        courtCaseSetting.setAnumodakName(translationServiceUtility.translationText(getCellValue(row.getCell(5))));
                    }
                    if (StringUtils.isNotBlank(getCellValue(row.getCell(6)))) {
                        //English
                        courtCaseSetting.setVasuliExpenseEn(getCellAmountValue(row.getCell(6)));

                        //Marathi

                        courtCaseSetting.setVasuliExpense(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(6)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(7)))) {
                        //English
                        courtCaseSetting.setOtherExpenseEn(getCellAmountValue(row.getCell(7)));

                        //Marathi
                        courtCaseSetting.setOtherExpense(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(7)))
                        );
                    }
                    if (StringUtils.isNotBlank(getCellValue(row.getCell(8)))) {
                        //English
                        courtCaseSetting.setNoticeExpenseEn(getCellAmountValue(row.getCell(8)));

                        //Marathi
                        courtCaseSetting.setNoticeExpense(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(8)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(9))) && getCellValue(row.getCell(9)).matches("[0-9]+")) {
                        //English
                        courtCaseSetting.setMeetingNoEn(Long.parseLong(getCellValue(row.getCell(9))));

                        //Marathi

                        courtCaseSetting.setMeetingNo(translationServiceUtility.translationText(getCellValue(row.getCell(9))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(10)))) {
                        courtCaseSetting.setMeetingDate(getDateCellValue(row.getCell(10)));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(11))) && getCellValue(row.getCell(11)).matches("[0-9]+")) {
                        //English
                        courtCaseSetting.setSubjectNoEn(Long.parseLong(getCellValue(row.getCell(11))));

                        //Marathi

                        courtCaseSetting.setSubjectNo(translationServiceUtility.translationText(getCellValue(row.getCell(11))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(12)))) {
                        //English
                        courtCaseSetting.setMeetingDayEn(getCellValue(row.getCell(12)));

                        //Marathi
                        courtCaseSetting.setMeetingDay(translationServiceUtility.translationText(getCellValue(row.getCell(12))));
                    }

                    String meetingTime = getCellValue(row.getCell(13));
                    if (StringUtils.isNotBlank(meetingTime)) {
                        //English
                        courtCaseSetting.setMeetingTimeEn(meetingTime);

                        //Marathi
                        courtCaseSetting.setMeetingTime(translationServiceUtility.translationText(meetingTime));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(14)))) {
                        // english
                        courtCaseSetting.setBankNameEn(getCellValue(row.getCell(14)));
                        // marathi
                        courtCaseSetting.setBankName(translationServiceUtility.translationText(getCellValue(row.getCell(14))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(15)))) {
                        // english
                        courtCaseSetting.setTalukaNameEn(getCellValue(row.getCell(15)));
                        // marathi
                        courtCaseSetting.setTalukaName(translationServiceUtility.translationText(getCellValue(row.getCell(15))));
                    }
                    
                   
                    

                    courtCaseSettingList.add(courtCaseSetting);
                }
            }
            
          
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
        
        
    	 return courtCaseSettingList;
    }

    
    /**
     *
     * @param courtCaseFile the courtCase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase has already an ID.
     * @throws Exception
     */
    public List<CourtCase> createCourtCaseFileRecords(
        MultipartFile files, String financialYearFromUI,
        String societyOrBranchName,
        String societyOrBranchAddress
        
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());
        int filecount = 0;
        File originalFileDir = new File(Constants.ORIGINAL_FILE_PATH);
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
            throw new BadRequestAlertException("file not saved successfully", ENTITY_NAME, "fileInvalid");
        }
        
        
        Row row=null;
        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); 
        for (int i = 0; i < 10; i++) {
            row = sheet.getRow(i); // Get the current row
            	String financialYearInFile = getCellValue(row.getCell(1));
                if (StringUtils.isNotBlank(financialYearInFile)) {
                    financialYearInFile = financialYearInFile.trim().replace("\n", " ").toLowerCase();
                    if (financialYearInFile.contains("financial") && financialYearInFile.contains("year")) {
                        filecount = i;
                        break;
                    }
                }
            
            
        }
        }
        
        

        int startRowIndex = filecount+1; // Starting row index
        List<CourtCase> courtCaseList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                row = sheet.getRow(rowIndex); // Get the current row
                CourtCase courtCase = new CourtCase();
                if (row != null) {
                    if (
                        StringUtils.isBlank(getCellValue(row.getCell(0))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(1))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(2))) &&
                        StringUtils.isBlank(getCellValue(row.getCell(3)))
                    ) {
                        break;
                    }

					courtCase.setFileName(files.getOriginalFilename());
					courtCase.setUniqueFileName(uniqueName + "." + fileExtension);

					courtCase.setBankNameEn(Constants.BANK_NAME);
					courtCase.setBankName(Constants.BANK_NAME_MARATHI);
					
					courtCase.setSocietyBranchName(societyOrBranchName);
					courtCase.setSocietyBranchAddress(societyOrBranchAddress);
                    
                    
                    String srNo = getCellValue(row.getCell(0));
                    if (srNo.matches("\\d+")) {
                        // english
                        courtCase.setSrNoEn(Long.parseLong(srNo));
                        // marathi
                        courtCase.setSrNo(translationServiceUtility.translationText(srNo));
                    }
                    
                    
                    
                    
                    String financialYear = getCellValue(row.getCell(1));
                    if (StringUtils.isNotBlank(financialYear)) {
                    	if(!financialYear.equalsIgnoreCase(financialYearFromUI))
                    	{
                    		  throw new BadRequestAlertException("Invalid Finantial Year", ENTITY_NAME, "FinantialYearInvalid");
                    	}
                    	
                        // english
                        courtCase.setFinancialYear(financialYear);

                    }
                    
                    String branchOrPacsCode = getCellValue(row.getCell(2));
                    if (StringUtils.isNotBlank(branchOrPacsCode)) {
                        // english
                        courtCase.setBranchOrPacsCode(branchOrPacsCode);

                    }

                    String acountNo = getCellValue(row.getCell(3));
                    if (StringUtils.isNotBlank(acountNo)) {
                        // english
                        courtCase.setAccountNoEn(acountNo);

                        // marathi
                        courtCase.setAccountNo(translationServiceUtility.translationText(acountNo));
                    }

                    
                    String nameOfDefaulter = getCellValue(row.getCell(4));
                    if (StringUtils.isNotBlank(nameOfDefaulter)) {
                        // english
                        courtCase.setNameOfDefaulterEn(nameOfDefaulter);
                        // marathi
                        courtCase.setNameOfDefaulter(translationServiceUtility.translationText(nameOfDefaulter));
                    }

                    
                    String address = getCellValue(row.getCell(5));
                    if (StringUtils.isNotBlank(address)) {
                        // english
                        courtCase.setAddressEn(address);

                        // marathi
                        courtCase.setAddress(translationServiceUtility.translationText(address));
                    }

                    String loanType = getCellValue(row.getCell(6));
                    if (StringUtils.isNotBlank(loanType)) {
                        // english
                        courtCase.setLoanTypeEn(loanType);

                        // marathi
                        courtCase.setLoanType(translationServiceUtility.translationText(loanType));
                    }

                    
                    Double loanAmount =getCellAmountValue(row.getCell(7));
                    if (loanAmount!=null) {
                        // english
                        courtCase.setLoanAmountEn(loanAmount);

                        // marathi
                        courtCase.setLoanAmount(translationServiceUtility.translationText(""+loanAmount));
                    }

                    
                    LocalDate loanDate = getDateCellValue(row.getCell(8));
                    if (loanDate != null) {
                        courtCase.setLoanDate(loanDate);
                        
                        // marathi
                        courtCase.setLoanDateMr(translationServiceUtility.oneZeroOneDateMr(loanDate));
                        
                    }
                    
                    
                    String termOfLoan = getCellValue(row.getCell(9));
                    if (StringUtils.isNotBlank(termOfLoan)) {
                        // english
                        courtCase.setTermOfLoanEn(termOfLoan);

                        // marathi
                        courtCase.setTermOfLoan(translationServiceUtility.translationText(termOfLoan));
                    }
                    
                    
                    
                    Double interestRate = getCellAmountValue(row.getCell(10));
                    if (interestRate!=null) {
                        // english
                        courtCase.setInterestRateEn(interestRate);

                        // marathi
                        courtCase.setInterestRate(translationServiceUtility.translationText(""+interestRate));
                    }

                    
                    Double installmentAmount = getCellAmountValue(row.getCell(11));
                    if (installmentAmount!=null) {
                        // english
                        courtCase.setInstallmentAmountEn(installmentAmount);

                        // marathi
                        courtCase.setInstallmentAmount(
                            translationServiceUtility.translationText(""+installmentAmount)
                        );
                    }

                    
                    Double totalCredit = getCellAmountValue(row.getCell(12));
                    if (totalCredit!=null) {
                        // english
                        courtCase.setTotalCreditEn(totalCredit);

                        // marathi
                        courtCase.setTotalCredit(translationServiceUtility.translationText(""+totalCredit));
                    }
                    
                    
                    Double interestPaid = getCellAmountValue(row.getCell(13));
                    if (interestPaid!=null) {
                    	
                    	 courtCase.setInterestPaidEn(interestPaid);

                         // marathi
                         courtCase.setInterestPaid(translationServiceUtility.translationText(""+interestPaid));
                         
                         
                       
                    }

                    
                    Double penalInterestPaid = getCellAmountValue(row.getCell(14));
                    if (penalInterestPaid!=null) {
                        // english
                    	
                    	 courtCase.setPenalInterestPaidEn(penalInterestPaid);

                         // marathi
                         courtCase.setPenalInterestPaid(
                             translationServiceUtility.translationText(""+penalInterestPaid)
                         );
                       
                    }

                    
                    Double balance = getCellAmountValue(row.getCell(15));
                    if (balance!=null) {
                    	
                    	 // english
                        courtCase.setBalanceEn(balance);

                        // marathi
                        courtCase.setBalance(translationServiceUtility.translationText(""+balance));
                        // english
                       
                    }
                    
                    Double dueAmount = getCellAmountValue(row.getCell(16));
                    if (dueAmount!=null) {
                        // english
                        courtCase.setDueAmountEn(dueAmount);

                        // marathi
                        courtCase.setDueAmount(translationServiceUtility.translationText(""+dueAmount));
                    }

                    
                    LocalDate dueDate = getDateCellValue(row.getCell(17));
                    if (dueDate != null) {
                        courtCase.setDueDate(dueDate);
                        
                        // marathi
                        courtCase.setDueDateMr(translationServiceUtility.oneZeroOneDateMr(dueDate));
                    }
                    
                    
                    Double dueInterest = getCellAmountValue(row.getCell(18));
                    if (dueInterest!=null) {
                        // english
                        courtCase.setDueInterestEn(dueInterest);

                        // marathi
                        courtCase.setDueInterest(translationServiceUtility.translationText(""+dueInterest));
                    }

                    
                    Double duePenalInterest = getCellAmountValue(row.getCell(19));
                    if (duePenalInterest!=null) {
                        // english
                        courtCase.setDuePenalInterestEn(duePenalInterest);
                        // marathi
                        courtCase.setDuePenalInterest(
                            translationServiceUtility.translationText(""+duePenalInterest)
                        );
                    }

                    
                    Double dueMoreInterest = getCellAmountValue(row.getCell(20));
                    if (dueMoreInterest!=null) {
                        // english
                        courtCase.setDueMoreInterestEn(dueMoreInterest);
                        // marathi
                        courtCase.setDueMoreInterest(
                            translationServiceUtility.translationText(""+dueMoreInterest)
                        );
                    }

                    
                    
                    Double interestRecivable= getCellAmountValue(row.getCell(21));
                    if (interestRecivable!=null) {
                        // english
                        courtCase.setInterestRecivableEn(interestRecivable);
                        // marathi
                        courtCase.setInterestRecivable(
                            translationServiceUtility.translationText(""+interestRecivable)
                        );
                    }

                    
                    String gaurentorOne = getCellValue(row.getCell(22));
                    if (StringUtils.isNotBlank(gaurentorOne)) {
                        // english
                        courtCase.setGaurentorOneEn(gaurentorOne);
                        // marathi
                        courtCase.setGaurentorOne(translationServiceUtility.translationText(gaurentorOne));
                    }

                    
                    
                    String gaurentorOneAddress = getCellValue(row.getCell(23));
                    if (StringUtils.isNotBlank(gaurentorOneAddress)) {
                        // english
                        courtCase.setGaurentorOneAddressEn(gaurentorOneAddress);
                        // marathi
                        courtCase.setGaurentorOneAddress(translationServiceUtility.translationText(gaurentorOneAddress));
                    }

                    
                    String gaurentorTwo= getCellValue(row.getCell(24));
                    if (StringUtils.isNotBlank(gaurentorTwo)) {
                        // english
                        courtCase.setGaurentorTwoEn(gaurentorTwo);
                        // marathi
                        courtCase.setGaurentorTwo(translationServiceUtility.translationText(gaurentorTwo));
                    }

                    
                    String gaurentorTwoAddress = getCellValue(row.getCell(25));
                    if (StringUtils.isNotBlank(gaurentorTwoAddress)) {
                        // english
                        courtCase.setGaurentorTwoAddressEn(gaurentorTwoAddress);
                        // marathi
                        courtCase.setGaurentorTwoAddress(translationServiceUtility.translationText(gaurentorTwoAddress));
                    }
                    
                    
                    
                    //making word for amount
                  //  String totalValue = getTotalValue(courtCase.getLoanAmountEn(), courtCase.getDueInterestEn(), courtCase.getDuePenalInterestEn());
                   
                   // String moreIntrestValue = getMoreIntrestValue(courtCase.getLoanAmountEn(), courtCase.getDueInterestEn(), courtCase.getDuePenalInterestEn());
                   
                  //  String totalIntrest = getTotalIntrest(courtCase.getLoanAmountEn(), courtCase.getDueInterestEn(), courtCase.getDuePenalInterestEn());
                    
                   
                  
                    courtCaseList.add(courtCase);
                }
            }

            
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
        
        return courtCaseList;
    }

    /**
     * {@code POST  /court-cases} : Create a new courtCase.
     *
     * @param courtCase the courtCase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/court-cases")
    public ResponseEntity<CourtCase> createCourtCase(@RequestBody CourtCase courtCase) throws URISyntaxException {
        log.debug("REST request to save CourtCase : {}", courtCase);
        if (courtCase.getId() != null) {
            throw new BadRequestAlertException("A new courtCase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourtCase result = courtCaseService.save(courtCase);
        return ResponseEntity
            .created(new URI("/api/court-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /court-cases/:id} : Updates an existing courtCase.
     *
     * @param id        the id of the courtCase to save.
     * @param courtCase the courtCase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the courtCase couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/court-cases/{id}")
    public ResponseEntity<CourtCase> updateCourtCase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCase courtCase
    ) throws URISyntaxException {
        log.debug("REST request to update CourtCase : {}, {}", id, courtCase);
        if (courtCase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourtCase result = courtCaseService.update(courtCase);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCase.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /court-cases/:id} : Partial updates given fields of an existing
     * courtCase, field will ignore if it is null
     *
     * @param id        the id of the courtCase to save.
     * @param courtCase the courtCase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase is not valid, or with status {@code 404 (Not Found)} if
     *         the courtCase is not found, or with status
     *         {@code 500 (Internal Server Error)} if the courtCase couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/court-cases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourtCase> partialUpdateCourtCase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCase courtCase
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourtCase partially : {}, {}", id, courtCase);
        if (courtCase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourtCase> result = courtCaseService.partialUpdate(courtCase);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCase.getId().toString())
        );
    }

    /**
     * {@code GET  /court-cases} : get all the courtCases.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of courtCases in body.
     */
    @GetMapping("/court-cases")
    public ResponseEntity<List<CourtCase>> getAllCourtCases(
        CourtCaseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourtCases by criteria: {}", criteria);
        //Page<CourtCase> page = courtCaseQueryService.findByCriteria(criteria, pageable);
        
        Page<CourtCase> page = null;
        Map<String, String> branchOrPacksNumber = bankBranchPacksCodeGet.getCodeNumber();

        if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.PACKS_CODE_KEY))) {
            page =
            		courtCaseQueryService.findByCriteriaPackNumber(criteria, pageable, branchOrPacksNumber.get(Constants.PACKS_CODE_KEY));
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY))) {
            page =
            		courtCaseQueryService.findByCriteriaSchemeWiseBranchCode(
                    criteria,
                    pageable,
                    branchOrPacksNumber.get(Constants.KCC_ISS_BRANCH_CODE_KEY)
                );
        } else if (StringUtils.isNotBlank(branchOrPacksNumber.get(Constants.BANK_CODE_KEY))) {
            page = courtCaseQueryService.findByCriteria(criteria, pageable);
        } else {
            throw new ForbiddenAuthRequestAlertException("Invalid token", ENTITY_NAME, "tokeninvalid");
        }
        
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /court-cases/count} : count all the courtCases.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/court-cases/count")
    public ResponseEntity<Long> countCourtCases(CourtCaseCriteria criteria) {
        log.debug("REST request to count CourtCases by criteria: {}", criteria);
        return ResponseEntity.ok().body(courtCaseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /court-cases/:id} : get the "id" courtCase.
     *
     * @param id the id of the courtCase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the courtCase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/court-cases/{id}")
    public ResponseEntity<CourtCase> getCourtCase(@PathVariable Long id) {
        log.debug("REST request to get CourtCase : {}", id);
        Optional<CourtCase> courtCase = courtCaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courtCase);
    }

    @GetMapping("/court-cases1/{nameOFdef}")
    public ResponseEntity<CourtCase> getCourtCase(@PathVariable String nameOFdef) {
        log.debug("REST request to get CourtCase : {}", nameOFdef);
        Optional<CourtCase> courtCase = courtCaseRepository.findOneByNameOfDefaulter(nameOFdef);
        return ResponseUtil.wrapOrNotFound(courtCase);
    }

    /**
     * {@code DELETE  /court-cases/:id} : delete the "id" courtCase.
     *
     * @param id the id of the courtCase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/court-cases/{id}")
    public ResponseEntity<Void> deleteCourtCase(@PathVariable Long id) {
        log.debug("REST request to delete CourtCase : {}", id);
        courtCaseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
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

    private static Double getCellAmountValue(Cell cell) {
        Double cellValue = 0.0;

        if (cell == null) {
            cellValue = 0.0;
        } else if (cell.getCellType() == CellType.STRING) {
            String cellValueInString = cell.getStringCellValue().trim();

            try {
                cellValue = Double.parseDouble(cellValueInString);
            } catch (Exception e) {}
        } else if (cell.getCellType() == CellType.NUMERIC) {
            double numericValue = cell.getNumericCellValue();
            // Convert it to a Double
            cellValue = numericValue;
        } else if (cell.getCellType() == CellType.BLANK) {
            cellValue = 0.0;
        }

        return cellValue;
    }

    private static String getCellAmountValueInString(Cell cell) {
        String cellValue = "0.0";

        if (cell == null) {
            return cellValue;
        } else if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            double numericValue = cell.getNumericCellValue();
            // Convert it to a Double
            return "" + numericValue;
        } else if (cell.getCellType() == CellType.BLANK) {
            return cellValue;
        } else {
            return cellValue;
        }
    }

    private static LocalDate getDateCellValue(Cell cell) {
        LocalDate localDate = null;

        if (cell == null) {
            localDate = null;
        } else if (cell.getCellType() == CellType.STRING) {
            Pattern patternYYYYMMDD = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            Pattern patternDDMMYYYY = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
            if (patternYYYYMMDD.matcher(cell.getStringCellValue()).matches()) { // yyyy-MM-dd
                localDate = LocalDate.parse(cell.getStringCellValue());
            } else if (patternDDMMYYYY.matcher(cell.getStringCellValue()).matches()) { // dd/mm/yyyy
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                localDate = LocalDate.parse(cell.getStringCellValue(), inputFormatter);
            }
        } else if (cell.getCellType() == CellType.NUMERIC) {
            localDate = LocalDate.of(1900, 1, 1).plusDays((long) cell.getNumericCellValue() - 2);
        } else if (cell.getCellType() == CellType.FORMULA) {
            localDate = LocalDate.of(1900, 1, 1).plusDays((long) cell.getNumericCellValue() - 2);
        } else if (cell.getCellType() == CellType.BLANK) {
            localDate = null;
        }

        return localDate;
    }




   

    
    
    
    private String getTotalIntrest(Double getLoanAmountEn, Double getDueInterestEn, Double getDuePenalInterestEn) {
        try {
            String format = String.format("%.2f", Double.sum(getLoanAmountEn, Double.sum(getDueInterestEn, getDuePenalInterestEn)));

            return translationServiceUtility.translationText(format);
        } catch (Exception e) {
            return "Error in translation";
        }
    }
    
    
    private String getTotalPostageValue(Double getLoanAmountEn, Double getDueInterestEn, Double getDuePenalInterestEn) {
        try {
            String format = String.format("%.2f", Double.sum(getLoanAmountEn, Double.sum(getDueInterestEn, getDuePenalInterestEn)));

            return translationServiceUtility.translationText(format);
        } catch (Exception e) {
            return "Error in translation";
        }
    }
    
    
    private String getMoreIntrestValue(Double getLoanAmountEn, Double getDueInterestEn, Double getDuePenalInterestEn) {
        try {
            String format = String.format("%.2f", Double.sum(getLoanAmountEn, Double.sum(getDueInterestEn, getDuePenalInterestEn)));

            return translationServiceUtility.translationText(format);
        } catch (Exception e) {
            return "Error in translation";
        }
    }
    
    
    
    
    private String getTotalValue(Double getLoanAmountEn, Double getDueInterestEn, Double getDuePenalInterestEn) {
        try {
            String format = String.format("%.2f", Double.sum(getLoanAmountEn, Double.sum(getDueInterestEn, getDuePenalInterestEn)));

            return translationServiceUtility.translationText(format);
        } catch (Exception e) {
            return "Error in translation";
        }
    }

    
   
//....................................................................................................................................
    
    @PostMapping("/oneZeroOneNoticeDemo")
    public ResponseEntity<byte[]> generatePDFFromHTMLDemo( @RequestParam("file") MultipartFile files) throws Exception {
    	
    	InputStream inputStream = files.getInputStream();
    	
    	List<InputStream> htmlList=new ArrayList<InputStream>();
		htmlList.add(inputStream);
        
        
        
        ResponseEntity<byte[]> response=null;
        if(htmlList.size()==1)
        {
        	//code for the generating pdf from html string
            
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();

            FontProvider fontProvider = new FontProvider();
            
            
            File file=new File(Constants.fontFilePath);
            
            
           // Resource resource = resourceLoader.getResource("classpath:" + "fonts/NotoSans-Regular.ttf");
            //String filepath=resource.getFile().getAbsolutePath();
            
            String filepath=file.getAbsolutePath();
            
            
            
            
            fontProvider.addFont(filepath, PdfEncodings.IDENTITY_H);

            converterProperties.setFontProvider(fontProvider);
            converterProperties.setCharset("UTF-8");
            
            //converting html to pdf
            HtmlConverter.convertToPdf(htmlList.get(0), byteArrayOutputStream, converterProperties);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("content-disposition", "attachment; filename=" +getUniqueNumberString()+ "certificate.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
        }
        
        else
        {
        	 throw new BadRequestAlertException("Error in file downloading", ENTITY_NAME, "errorInFileDownload");
        }
        
        return response;
    }
    
    
    //getting court case with or without sabhasad name
    
  	List<CourtCase> getCourtCaseList(CourtCaseCriteria criteria, String sabhasadName) {

  		if (StringUtils.isNotBlank(sabhasadName)) {
  			StringFilter sabhasadNameFilter = new StringFilter();
  			sabhasadNameFilter.setEquals(sabhasadName);
  			criteria.setNameOfDefaulter(sabhasadNameFilter);
  		}

  		
  		
  		//List<CourtCase> courtCaseList = courtCaseQueryService.findByCriteriaWithoutPage(criteria);
  		
  		Optional<CourtCase> findById = courtCaseRepository.findById(1l);
  		List<CourtCase> courtCaseList =new ArrayList<>();
  		
  		
  		courtCaseList.add(findById.get());
  		
  		

  		if (courtCaseList.isEmpty()) {
  			throw new BadRequestAlertException("Data not found", ENTITY_NAME, "datanotfound");
  		}
  		return courtCaseList;

  	}
  	
  	 //getting court case setting with or without setting
  	CourtCaseSetting getCourtCaseSetting(String settingCode) {
  		CourtCaseSetting courtCaseSetting = new CourtCaseSetting();
  		if (StringUtils.isNotBlank(settingCode)) {
  			courtCaseSetting = caseSettingRepository.findOneBySettingCode(settingCode);
  		} else {
  			courtCaseSetting = caseSettingRepository.findTopByOrderByIdDesc();
  		}

  		if (courtCaseSetting == null) {
  			throw new BadRequestAlertException("Data not found", ENTITY_NAME, "datanotfound");
  		}

  		return courtCaseSetting;

  	}
      
  	
  	//generate unique number string
  	String getUniqueNumberString()
  	{
  		Calendar cal = new GregorianCalendar();
          return
              "" +
              cal.get(Calendar.MILLISECOND);
  	}
      
      
      

      //processing template
      String oneZeroOneTemplate(String template,CourtCase courtCase, CourtCaseSetting courtCaseSettings)
      {
      	 Locale locale = Locale.forLanguageTag("en");
      	 Context context = new Context(locale);
           context.setVariable("courtCase", courtCase);
           context.setVariable("courtCaseSettings", courtCaseSettings);
           String content = templateEngine.process(template, context);
           return content;
      }
}
