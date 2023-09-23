package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.CourtCaseQueryService;
import com.cbs.middleware.service.CourtCaseService;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.layout.font.FontProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    public CourtCaseResource(
        CourtCaseService courtCaseService,
        CourtCaseRepository courtCaseRepository,
        CourtCaseQueryService courtCaseQueryService
    ) {
        this.courtCaseService = courtCaseService;
        this.courtCaseRepository = courtCaseRepository;
        this.courtCaseQueryService = courtCaseQueryService;
    }

    @GetMapping("/pdfTest")
    public Object generatePDFFromHTML() throws Exception {
        //Optional<CourtCase> courtCase = courtCaseRepository.findById(2l);

        DateFormat Date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        String currentDate = Date.format(cals.getTime());
        System.out.println("Formatted Date: " + currentDate);

        String HTML =
            "<html>\r\n" +
            "\r\n" +
            "<style>\r\n" +
            "    h1 {\r\n" +
            "        text-align: center;\r\n" +
            "    }\r\n" +
            "\r\n" +
            "    p {\r\n" +
            "        text-align: center;\r\n" +
            "    }\r\n" +
            "\r\n" +
            "    table,\r\n" +
            "    th,\r\n" +
            "    td {\r\n" +
            "        border: 1px solid black;\r\n" +
            "        border-collapse: collapse;\r\n" +
            "    }\r\n" +
            "</style>\r\n" +
            "\r\n" +
            "<head>\r\n" +
            "    <div class=\"h1\">पुणे जिल्हा मध्यवर्ती सहकारी बँक मर्यादित, पुणे</div>\r\n" +
            "    <div class=\"h1\">मुख्य कचेरी : ४ . बी. जे. रोड, पुणे ४११००१.</div>\r\n" +
            "    <div class=\"h1\">punedc@mail.com</div>\r\n" +
            "    <div>____________________________________________</div>\r\n" +
            "</head>\r\n" +
            "\r\n" +
            "<body>\r\n" +
            "\r\n" +
            "    <div class=\"\">१०१ नुसार कारवाई करणेपूर्वीची नोटीस </div>\r\n" +
            "    <div class=\"\">रजिस्टर ए.डी./ यु.पी.सी.</div>\r\n" +
            "\r\n" +
            "    <div class=\"\">जा.क्र. / २०२३-२४ /</div>\r\n" +
            "    <div class=\"\">रजिस्टर ए.डी./ यु.पी.सी.</div>\r\n" +
            "\r\n" +
            "    <div class=\"\">दिनांक :- / /२०</div>\r\n" +
            "    <div></div>\r\n" +
            "\r\n" +
            "    <div class=\"\">कर्ज खाते क्रमांक :-</div>\r\n" +
            "\r\n" +
            "    <div class=\"\">कर्जदार :- श्री. / श्रीमती___________________</div>\r\n" +
            "\r\n" +
            "\r\n" +
            "    <div class=\"\">कर्जदार :- श्री. / श्रीमती___________________</div>\r\n" +
            "    <div class=\"\">कर्जदार :- श्री. / श्रीमती___________________</div>\r\n" +
            "    <div class=\"\">कर्जदार :- श्री. / श्रीमती___________________</div>\r\n" +
            "    <div class=\"\">राहणार :- मु.पो._________, ता.__________ जि. पुणे_________</div>\r\n" +
            "\r\n" +
            "    <div class=\"\">१) जामीनदार :- श्री. / श्रीमती___________________</div>\r\n" +
            "    <div class=\"\">राहणार :- मु.पो._________, ता.__________ जि. पुणे_________</div>\r\n" +
            "\r\n" +
            "    <div class=\"\">२) जामीनदार :- श्री. /श्रीमती___________________</div>\r\n" +
            "    <div class=\"\">राहणार :- मु.पो._________, ता.__________ जि. पुणे_________</div>\r\n" +
            "\r\n" +
            "    <div class=\"\">३) जामीनदार :- श्री. /श्रीमती___________________</div>\r\n" +
            "    <div class=\"\">राहणार :- मु.पो._________, ता.__________ जि. पुणे_________</div>\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "    <div class=\"\">/div>\r\n" +
            "\r\n" +
            "        <div class=\"\"> विषय: थकीत कर्ज परतफेड करणेबाबत..</div>\r\n" +
            "\r\n" +
            "\r\n" +
            "        <div class=\"\"> तुम्हास नोटीस देण्यात येते की,</div>\r\n" +
            "\r\n" +
            "\r\n" +
            "        <div class=\"\">१. तुम्ही बँकेच्या_________शाखेकडून दिनांक / /२० रोजी कर्ज योजने अंतर्गत कारणासाठी रूपये\r\n" +
            "\r\n" +
            "            _________/- चे कर्ज द.सा.द.शे....... % व्याजदराने...... वर्षेच्या मुदतीने घेतले आहे.\r\n" +
            "\r\n" +
            "\r\n" +
            "\r\n" +
            "        </div>\r\n" +
            "\r\n" +
            "\r\n" +
            "        <div class=\"\">\r\n" +
            "\r\n" +
            "            २. सदर कर्ज व्यवहारास श्री__________ व श्री____________हे व्यक्ति जामीनदार आहेत.\r\n" +
            "\r\n" +
            "\r\n" +
            "        </div>\r\n" +
            "\r\n" +
            "\r\n" +
            "        <div class=\"\">\r\n" +
            "            ३. मूळ कर्जदार व जामीनदार यांनी बँकेस दिनांक / /२० रोजी रितसर वचन विट्टी, करारनामे\r\n" +
            "            व इतर सर्व कागदपत्रे लिहून दिलेली आहेत.\r\n" +
            "        </div>\r\n" +
            "\r\n" +
            "        <div class=\"\">\r\n" +
            "\r\n" +
            "            ४. वचनचिठ्ठी व कारारनाम्यातील तपशीलाप्रमाणे तसेच तुम्ही सदर कर्ज रक्कम मंजूरी पत्रातील अटी व\r\n" +
            "            शर्तीनुसार परतफेड करण्यास वैयक्तिक व सामुदायिक जबावदार आहात.\r\n" +
            "        </div>\r\n" +
            "        <div class=\"\">\r\n" +
            "            5.आपण बँकेकडून घेतलेल्या कर्ज रकमेची परतफेड वेळेवर व करारातील अटी व शर्तीप्रमाणे केलेली नाही. म्हणून तुम्हास\r\n" +
            "            या पूर्वीही नोटिस पाठविण्यात आल्या होत्या तरीही तुम्ही थकीत कर्जाच्या रकमेची संपूर्ण परतफेड केलेली नाही.\r\n" +
            "            त्यामुळे महाराष्ट्र सहकारी संस्था अधिनियम १९६० चे कलम (१९०१ (१) अन्वये संबंधित मा. उपनिबंधक / मा. सहाय्यक\r\n" +
            "            निबंधक, सहकारी संस्था यांचेकडे अर्ज दाखल करून वसूली दाखला मिळणेबाबतचा अर्ज करणे वावत्तचा निर्णय घेण्यात\r\n" +
            "            आलेला आहे. तथापि तुम्हास एक संधी म्हणून सदर प्रकरणी अंतिम नोटिस देण्यात येत आहे.\r\n" +
            "        </div>\r\n" +
            "\r\n" +
            "        <div class=\"\">\r\n" +
            "            दिनांक\r\n" +
            "            / /२० अखेर तुमचेकडून खालीलप्रमाणे येणे आहे.\r\n" +
            "        </div>\r\n" +
            "\r\n" +
            "        <table>\r\n" +
            "            <tbody>\r\n" +
            "\r\n" +
            "                <tr>\r\n" +
            "                    <th> अनु. क्र.</th>\r\n" +
            "                    <th>तपशील</th>\r\n" +
            "                    <th>रक्कम रूपये</th>\r\n" +
            "                </tr>\r\n" +
            "                <tr>\r\n" +
            "                    <td>१.</td>\r\n" +
            "                    <td>२.</td>\r\n" +
            "                    <td>३.</td>\r\n" +
            "                </tr>\r\n" +
            "                <tr>\r\n" +
            "                    <td>मुद्दल</td>\r\n" +
            "                    <td>व्याज येणे + दंड व्याज</td>\r\n" +
            "                    <td>इतर खर्च - नोटिस इत्यादी</td>\r\n" +
            "                </tr>\r\n" +
            "                <tr>\r\n" +
            "                    <td>रूपये.</td>\r\n" +
            "                    <td>रूपये.</td>\r\n" +
            "                    <td>रूपये.</td>\r\n" +
            "                </tr>\r\n" +
            "                <tr>\r\n" +
            "                    <td></td>\r\n" +
            "                    <td>रूपये :-</td>\r\n" +
            "                    <td>रूपये.</td>\r\n" +
            "                </tr>\r\n" +
            "            </tbody>\r\n" +
            "        </table>\r\n" +
            "</body>\r\n" +
            "\r\n" +
            "</html>";

        try {
            // IndicLigaturizer g = new DevanagariLigaturizer();
            // String processed = g.process(HTML);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();
            FontProvider fontProvider = new FontProvider();
            fontProvider.addFont(
                "D:\\PDCC\\gitbranch\\cbs-middleware-document\\font\\NotoSansDevanagari-hinted\\NotoSansDevanagari-Regular.ttf",
                PdfEncodings.IDENTITY_H
            );
            // fontProvider.addFont("/home/ubuntu/pdcc/NotoSansDevanagari-Regular.ttf",
            // PdfEncodings.IDENTITY_H);
            converterProperties.setFontProvider(fontProvider);
            converterProperties.setCharset("UTF-8");

            HtmlConverter.convertToPdf(HTML, byteArrayOutputStream, converterProperties);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("content-disposition", "attachment; filename=" + "certificate.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/demoPdf")
    public Object test1234() throws Exception {
        // Load the JRXML template from the resources folder

        InputStream templateStream = getClass().getResourceAsStream("/Blank_A4_2.jrxml");
        // InputStream templateStream =
        // getClass().getResourceAsStream("/Blank_A4_3.jrxml");

        // Compile the JRXML template
        JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

        // Create a data source (you can use a list of POJOs)

        // Set parameters if needed

        // Generate the PDF report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JREmptyDataSource(1));

        // Export the report to a byte array (PDF format)
        byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("content-disposition", "attachment; filename=" + "certificate.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfReport, headers, HttpStatus.OK);

        return response;
    }

    /**
     * {@code POST  /court-cases} : Create a new courtCase.
     *
     * @param courtCase the courtCase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new courtCase, or with status {@code 400 (Bad Request)} if
     *         the courtCase has already an ID.
     * @throws Exception
     */
    @PostMapping("/court-case-file")
    public ResponseEntity<List<CourtCase>> createCourtCaseFile(
        @RequestParam("file") MultipartFile files,
        RedirectAttributes redirectAttributes
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        if (courtCaseRepository.existsByFileName(files.getOriginalFilename())) {
            throw new BadRequestAlertException("File already exist", ENTITY_NAME, "fileExist");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(2); // Get the current row
            boolean flagForLabel = false;
            String srNo = getCellValue(row.getCell(0));

            if (StringUtils.isNotBlank(srNo)) {
                srNo = srNo.trim().replace("\n", " ").toLowerCase();
                if (!srNo.contains("sr") || !srNo.contains("no")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String secondNoticeDate = getCellValue(row.getCell(25));
            if (StringUtils.isNoneBlank(secondNoticeDate)) {
                secondNoticeDate = secondNoticeDate.trim().replace("\n", " ").toLowerCase();
                if (!secondNoticeDate.contains("second") || !secondNoticeDate.contains("notice") || !secondNoticeDate.contains("date")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            String taluka = getCellValue(row.getCell(27));
            if (StringUtils.isNoneBlank(taluka)) {
                taluka = taluka.trim().replace("\n", " ").toLowerCase();
                if (!taluka.contains("taluka") || !taluka.contains("taluka") || !taluka.contains("taluka")) {
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

        File originalFileDir = new File(Constants.ORIGINAL_FILE_PATH);
        if (!originalFileDir.isDirectory()) {
            originalFileDir.mkdirs();
        }

        String filePath = originalFileDir.toString();
        boolean falgForFileName = false;
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

        int startRowIndex = 3; // Starting row index
        List<CourtCase> courtCaseList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
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

                    if (!falgForFileName) {
                        courtCase.setFileName(files.getOriginalFilename());
                        courtCase.setUniqueFileName(uniqueName + "." + fileExtension);
                        falgForFileName = true;
                    }

                    String srNo = getCellValue(row.getCell(0));
                    if (srNo.matches("\\d+")) {
                        courtCase.setSrNo(srNo);
                    }

                    // add logic for skipping records if exists

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(1)))) {
                        // english
                        courtCase.setAccountNoEn(getCellValue(row.getCell(1)));

                        // marathi
                        courtCase.setAccountNo(translationServiceUtility.translationText(getCellValue(row.getCell(1))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(2)))) {
                        // english
                        courtCase.setNameOfDefaulterEn(getCellValue(row.getCell(2)));
                        // marathi
                        courtCase.setNameOfDefaulter(translationServiceUtility.translationText(getCellValue(row.getCell(2))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(3)))) {
                        // english
                        courtCase.setAddressEn(getCellValue(row.getCell(3)));

                        // marathi
                        courtCase.setAddress(translationServiceUtility.translationText(getCellValue(row.getCell(3))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(4)))) {
                        // english
                        courtCase.setLoanTypeEn(getCellValue(row.getCell(4)));

                        // marathi
                        courtCase.setLoanType(translationServiceUtility.translationText(getCellValue(row.getCell(4))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(5)))) {
                        // english
                        courtCase.setLoanAmountEn(getCellAmountValue(row.getCell(5)));

                        // marathi
                        courtCase.setLoanAmount(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(5))));
                    }

                    if (getDateCellValue(row.getCell(6)) != null) {
                        courtCase.setLoanDate(getDateCellValue(row.getCell(6)));
                    }
                    if (StringUtils.isNotBlank(getCellValue(row.getCell(7)))) {
                        // english
                        courtCase.setTermOfLoanEn(getCellValue(row.getCell(7)));

                        // marathi
                        courtCase.setTermOfLoan(translationServiceUtility.translationText(getCellValue(row.getCell(7))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(8)))) {
                        // english
                        courtCase.setInterestRateEn(getCellAmountValue(row.getCell(8)));

                        // marathi
                        courtCase.setInterestRate(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(8))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(9)))) {
                        // english
                        courtCase.setInstallmentAmountEn(getCellAmountValue(row.getCell(9)));

                        // marathi
                        courtCase.setInstallmentAmount(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(9)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(10)))) {
                        // english
                        courtCase.setTotalCreditEn(getCellAmountValue(row.getCell(10)));

                        // marathi
                        courtCase.setTotalCredit(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(10))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(11)))) {
                        // english
                        courtCase.setBalanceEn(getCellAmountValue(row.getCell(11)));

                        // marathi
                        courtCase.setBalance(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(11))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(12)))) {
                        // english
                        courtCase.setInterestPaidEn(getCellAmountValue(row.getCell(12)));

                        // marathi
                        courtCase.setInterestPaid(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(12))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(13)))) {
                        // english
                        courtCase.setPenalInterestPaidEn(getCellAmountValue(row.getCell(13)));

                        // marathi
                        courtCase.setPenalInterestPaid(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(13)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(14)))) {
                        // english
                        courtCase.setDueAmountEn(getCellAmountValue(row.getCell(14)));

                        // marathi
                        courtCase.setDueAmount(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(14))));
                    }

                    if (getDateCellValue(row.getCell(15)) != null) {
                        courtCase.setDueDate(getDateCellValue(row.getCell(15)));
                    }
                    if (StringUtils.isNotBlank(getCellValue(row.getCell(16)))) {
                        // english
                        courtCase.setDueInterestEn(getCellAmountValue(row.getCell(16)));

                        // marathi
                        courtCase.setDueInterest(translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(16))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(17)))) {
                        // english
                        courtCase.setDuePenalInterestEn(getCellAmountValue(row.getCell(17)));
                        // marathi
                        courtCase.setDuePenalInterest(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(17)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(18)))) {
                        // english
                        courtCase.setDueMoreInterestEn(getCellAmountValue(row.getCell(18)));
                        // marathi
                        courtCase.setDueMoreInterest(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(18)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(19)))) {
                        // english
                        courtCase.setInterestRecivableEn(getCellAmountValue(row.getCell(19)));
                        // marathi
                        courtCase.setInterestRecivable(
                            translationServiceUtility.translationText(getCellAmountValueInString(row.getCell(19)))
                        );
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(20)))) {
                        // english
                        courtCase.setGaurentorOneEn(getCellValue(row.getCell(20)));
                        // marathi
                        courtCase.setGaurentorOne(translationServiceUtility.translationText(getCellValue(row.getCell(20))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(21)))) {
                        // english
                        courtCase.setGaurentorOneAddressEn(getCellValue(row.getCell(21)));
                        // marathi
                        courtCase.setGaurentorOneAddress(translationServiceUtility.translationText(getCellValue(row.getCell(21))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(22)))) {
                        // english
                        courtCase.setGaurentorTwoEn(getCellValue(row.getCell(22)));
                        // marathi
                        courtCase.setGaurentorTwo(translationServiceUtility.translationText(getCellValue(row.getCell(22))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(23)))) {
                        // english
                        courtCase.setGaurentorTwoAddressEn(getCellValue(row.getCell(23)));
                        // marathi
                        courtCase.setGaurentorTwoAddress(translationServiceUtility.translationText(getCellValue(row.getCell(23))));
                    }

                    if (getDateCellValue(row.getCell(24)) != null) {
                        courtCase.setFirstNoticeDate(getDateCellValue(row.getCell(24)));
                    }
                    if (getDateCellValue(row.getCell(25)) != null) {
                        courtCase.setSecondNoticeDate(getDateCellValue(row.getCell(25)));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(26)))) {
                        // english
                        courtCase.setBankNameEn(getCellValue(row.getCell(26)));
                        // marathi
                        courtCase.setBankName(translationServiceUtility.translationText(getCellValue(row.getCell(26))));
                    }

                    if (StringUtils.isNotBlank(getCellValue(row.getCell(27)))) {
                        // english
                        courtCase.setTalukaNameEn(getCellValue(row.getCell(27)));
                        // marathi
                        courtCase.setTalukaName(translationServiceUtility.translationText(getCellValue(row.getCell(27))));
                    }

                    courtCaseList.add(courtCase);
                }
            }

            if (!courtCaseList.isEmpty()) {
                courtCaseRepository.saveAll(courtCaseList);

                if (courtCaseList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "Court Case record file uploaded",
                            "Court Case record file : " + files.getOriginalFilename() + " uploaded",
                            false,
                            courtCaseList.get(0).getCreatedDate(),
                            "CourtCaseRecordFileUploaded" // type
                        );
                    } catch (Exception e) {}
                }

                return ResponseEntity.ok().body(courtCaseList);
            } else {
                throw new BadRequestAlertException("File is already parsed", ENTITY_NAME, "FileExist");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
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
        Page<CourtCase> page = courtCaseQueryService.findByCriteria(criteria, pageable);
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
}
