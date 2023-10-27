package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.domain.One01ReportParam;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.service.CourtCaseQueryService;
import com.cbs.middleware.service.CourtCaseService;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import com.cbs.middleware.web.rest.utility.PDFModel;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.licensing.base.LicenseKey;
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

    public CourtCaseResource(
        CourtCaseService courtCaseService,
        CourtCaseRepository courtCaseRepository,
        CourtCaseQueryService courtCaseQueryService
    ) {
        this.courtCaseService = courtCaseService;
        this.courtCaseRepository = courtCaseRepository;
        this.courtCaseQueryService = courtCaseQueryService;
    }

    @PostMapping("/oneZeroOneNoticePdf")
    public ResponseEntity<Object> generatePDFFromHTML(@RequestBody One01ReportParam one01ReportParam) throws Exception {
        CourtCaseCriteria criteria = new CourtCaseCriteria();
        List<CourtCase> courtCaseList = new ArrayList<>();

        if (one01ReportParam.isIs101Select()) {
            StringFilter talukaFilter = new StringFilter();
            talukaFilter.setEquals(one01ReportParam.getTalukaName());

            StringFilter bankFilter = new StringFilter();
            bankFilter.setEquals(one01ReportParam.getBankName());

            criteria.setTalukaName(talukaFilter);
            criteria.setBankName(bankFilter);

            courtCaseList = courtCaseQueryService.findByCriteriaWithputPage(criteria);

            if (courtCaseList.isEmpty()) {
                throw new BadRequestAlertException("Data not found", ENTITY_NAME, "datanotfound");
            }

            CourtCaseSetting courtCaseSetting = caseSettingRepository.findTopByOrderByIdDesc();

            List<PDFModel> one01NamunaList = generate101NamunaHtml(courtCaseList, courtCaseSetting);
            if (one01NamunaList.isEmpty()) {
                throw new BadRequestAlertException("Error in pdf genaration", ENTITY_NAME, "errorInPdfGeneration");
            }

            return ResponseEntity.ok().body(one01NamunaList);
        } else {
            StringFilter talukaFilter = new StringFilter();
            talukaFilter.setEquals(one01ReportParam.getTalukaName());

            StringFilter bankFilter = new StringFilter();
            bankFilter.setEquals(one01ReportParam.getBankName());

            StringFilter sabhasadNameFilter = new StringFilter();
            sabhasadNameFilter.setEquals(one01ReportParam.getSabhasadName());

            criteria.setTalukaName(talukaFilter);
            criteria.setBankName(bankFilter);
            criteria.setNameOfDefaulter(sabhasadNameFilter);
            courtCaseList = courtCaseQueryService.findByCriteriaWithputPage(criteria);

            if (courtCaseList.isEmpty()) {
                throw new BadRequestAlertException("Data not found", ENTITY_NAME, "datanotfound");
            }

            List<String> one01NamunaList = generate101Html(courtCaseList);

            if (one01NamunaList.isEmpty()) {
                throw new BadRequestAlertException("Error in pdf genaration", ENTITY_NAME, "errorInPdfGeneration");
            }

            return ResponseEntity.ok().body(one01NamunaList);
        }
    }

    @GetMapping("/testpdf")
    public ResponseEntity<byte[]> testPdf() throws Exception {
        //LicenseKey.loadLicenseFile(new File("C:\\Users\\swapnilp\\Desktop\\itext\\0811f48dd15bb96daa5373e2917c354d064d61460879cf6db372af5ea79a4792.json"));

        try {
            String HTML =
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>101 prakaran</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<!-- 101 prakaran araj -->\n" +
                "<div>\n" +
                "    <p>संस्थेचे नाव : <b>_______________________________________________________________________</b></p>\n" +
                "    <div style=\"height: 100px;\">\n" +
                "        <div style=\"float: left; width: 50%;\">\n" +
                "            <p><b>कोड क्रमांक : ______</b></p>\n" +
                "        </div>\n" +
                "        <div style=\"float: right; width: 50%;\">\n" +
                "            <p style=\"text-align: end;\"><b>प्रकरण क्रमांक: ________</b></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div style=\"border: 1px solid black; margin-top: 15px; box-shadow:  4.5px 4.5px black;\">\n" +
                "        <h1 style=\"text-align: center;\">( १०१ प्रकरणे अर्ज )</h1>\n" +
                "    </div>\n" +
                "\n" +
                "    <div>\n" +
                "        <p style=\"line-height: 55px;\">मे उपनिबंधक / सहाय्यक निबंधक साहेब</p>\n" +
                "        <p style=\"line-height: 30px;\"><b>सहाय्यक निबंधक सहकारी संस्था</b></p>\n" +
                "        <p style=\"line-height: 55px;\">थकबाकीदाराचे नाव</p>\n" +
                "        <p style=\"line-height: 55px;\">थकलेली रक्कम रूपये</p>\n" +
                "        <p style=\"line-height: 70px;\"> <b>एकुण : ____________ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; व्याज : ___________________ </b></p>\n" +
                "        <p style=\"line-height: 55px;\">प्रकरण केलेल्याची तारीख</p>\n" +
                "        <p style=\"line-height: 55px;\">प्रकरण केलेल्याची रक्कम रू</p>\n" +
                "        <p style=\"line-height: 55px;\">प्रकरण दाखला तारीख</p>\n" +
                "        <p style=\"line-height: 55px;\">मागणी नोटीस दिल्याची तारीख</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<!-- Karj Magani Notice -->\n" +
                "<div style=\"page-break-before: always;\">\n" +
                "    <div style=\"height: 277px;\">\n" +
                "        <div style=\"float: left; width: 20%; border: 1px solid;  padding: 18px;\">\n" +
                "            <div>\n" +
                "                <p><b>जावक क्रमांक : ___ </b></p>\n" +
                "            <table style=\"width: 100%; border: 1px solid black; border-collapse: collapse;\">\n" +
                "                <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">थकीत मुदद्ल :</td>\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">&nbsp; &nbsp;&nbsp;</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">व्याज : </td>\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">&nbsp; &nbsp;&nbsp;</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">दंड व्याज : </td>\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">&nbsp; &nbsp;&nbsp;</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">पोस्टेज व इतर :</td>\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">&nbsp; &nbsp;&nbsp;</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">एकुण : </td>\n" +
                "                    <td style=\"border: 1px solid black; border-collapse: collapse; padding: 6px;\">&nbsp; &nbsp;&nbsp;</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div style=\"float: right; width: 76%;\">\n" +
                "            <p style=\"text-align: center; line-height: 0.6;\"><b>दिनांक : ________</b></p>\n" +
                "            <p style=\"text-align: center; line-height: 0.6;\"><b>कर्ज खाते नं : ________</b></p>\n" +
                "            <p style=\"text-align: center; line-height: 0.6;\"><b>कर्ज प्रकार : ________</b></p>\n" +
                "            <div style=\"border: 1px solid black; margin-top: 15px; box-shadow:  4.5px 4.5px black; width: 70%;margin: 15px auto;\">\n" +
                "                <h3>कर्ज फेडीची नोटीस १०१ (कलम १०१ अन्वये कारवाईकरणेसाठी)</h3>\n" +
                "            </div>\n" +
                "            <p style=\"text-align: center; line-height: 0.6;\">______________________________________,</p>\n" +
                "            <p style=\"text-align: center; line-height: 0.6;\">मु.पो. ____, ता. ______, जि. ____</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <hr>\n" +
                "    <div style=\" padding: 50px;\">\n" +
                "        <p>प्रति,</p>\n" +
                "    <table style=\"margin: 10px 0px 30px 30px; width: 90%;  border-collapse: separate; border-spacing: 10px !important;\">\n" +
                "        <tr>\n" +
                "            <td style=\" vertical-align: baseline;\"rowspan=\"3\" ><b>१)</b></td>\n" +
                "            <td>&nbsp;&nbsp;&nbsp;&nbsp;<b>कर्जदाराचे नाव : ______________________________________________</b></td>\n" +
                "        </tr>\n" +
                "        <tr >\n" +
                "            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "        </tr>\n" +
                "        <tr >\n" +
                "            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "        </tr>\n" +
                "        <tr >\n" +
                "            <td style=\" vertical-align: baseline;\" rowspan=\"3\" ><b>२)</b></td>\n" +
                "            <td><b>जमीनदारांचे नाव : ______________________________________________</b></td>\n" +
                "        </tr>\n" +
                "        <tr >\n" +
                "            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "        </tr>\n" +
                "        <tr >\n" +
                "            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "        </tr>\n" +
                "        <tr >\n" +
                "            <td style=\" vertical-align: baseline;\" rowspan=\"3\" ><b>३)</b></td>\n" +
                "            <td><b>जमीनदारांचे नाव : ______________________________________________</b></td>\n" +
                "        </tr>\n" +
                "        <tr >\n" +
                "            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "        </tr>\n" +
                "        <tr >\n" +
                "            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "        <p style=\"text-indent: 35px; line-height: 33px;\">\n" +
                "            या नोटीसीने कळविण्यात येते की, सदर <b>____________________,</b> कडून घेतलेल्या रू : <b>______</b> कर्जापैकी रू <b>_____</b> अक्षरी ( <b>_________________</b>) थकीत झालेली आहे.\n" +
                "             सदरची थकीत रक्कम व्याजासह ता. <b>________</b> रोजी अगर मुदतीत परतफेड केली नसल्यामुळे महाराष्ट्र सहकारी संस्था अधिनियम १९६० च्या कलम १०९ प्रमाणे कर्ज वसूली बाबत तुमच्यावर कारवाई करणेस तुम्ही पात्र झाला आहात. \n" +
                "             तरी तुम्ही जर तुमच्याकडे असलेली थक मुदलाची रक्कम व व्याज <b>______</b> पर्यत होणारे व्याज ची बाकी नोटीस पोहचले पासून ८ दिवसाच्या आत बँकेकडे भरली नाही तर बॅक तुमच्या विरूध्द सहकारी संस्था अधिनियम \n" +
                "             १०१ अन्वये सक्षम प्राधिकरणाकडे वसुलीसपात्र रक्कम वसुलीचा दाखला मिळणेसाठी दावा / अर्ज दाखल करून कायदेशीर इलाज करेल व तुमच्याकडील येणे बाकी होणाऱ्या व्याजासह जमीन महसुलीचे थकबाकीप्रमाणे \n" +
                "             वसुल करण्यात येईल, तसेच तुम्ही, सदर रक्कम वसुल करणेस होणाऱ्या खर्चाच्या रक्कमेसही जबाबदार होत असुन सदरची रक्कम आपलेकडून वसुल केली जाईल.\n" +
                "        </p>\n" +
                "\n" +
                "        <p style=\"text-align: center;\"><b>सचिव</b></p>\n" +
                "        <p style=\"text-align: center;\"><b>______________________</b></p>\n" +
                "\n" +
                "        <div style=\"margin: 15px auto; padding: 0px 50px;\">\n" +
                "            <table style=\"width: 70%;\">\n" +
                "                <tr>\n" +
                "                    <td><b>१)</b></td>\n" +
                "                    <td><b>कर्जदारचे नाव : </b></td>\n" +
                "                    <td><b>_______________________________</b></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><b>२)</b></td>\n" +
                "                    <td><b>जामिनदाराचे नाव : </b></td>\n" +
                "                    <td>_______________________________</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><b>३)</b></td>\n" +
                "                    <td><b>जामिनदाराचे नाव : </b></td>\n" +
                "                    <td>_______________________________</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div style=\"margin-top: 250px;\">\n" +
                "        <hr>\n" +
                "        <p style=\"text-align: center;\">सुचना :थकबाकी भरणा केली असल्यास ही नोटीस रद्द समजावी, परंतु तशी शाखेत\n" +
                "            येऊन खात्री करावी.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "<!-- 101 prakaran rojnama -->\n" +
                "<div style=\"page-break-before: always;\">\n" +
                "    <div style=\"text-align: center;\">\n" +
                "        <h3><b>उप निबंधक सहकारी संस्था संगमनेर,</b></h3>\n" +
                "        <h3><b>महाराष्ट्र सहकारी संस्था अधिनियम १९६० चे कलम १०१ प्रमाणे सुनवणी</b></h3>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div style=\"display: flex; justify-content: flex-end;\">\n" +
                "            <div style=\"border: 1px solid black; padding: 20px;\">\n" +
                "            <p><b>दावा क्रमांक : _____ / कलम १०१/२०</b></p>\n" +
                "            <p>कर्ज प्रकार: <b><u>____________</u></b></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div style=\"margin: 50px;\">\n" +
                "        <table style=\"width: 100%;\">\n" +
                "            <tr>\n" +
                "                <td style=\"padding-bottom: 38px; \">संस्थेचे नाव : साखर ग्रुप वि. का.स सेवा सोसायटी मर्या. साखर, ..</td>\n" +
                "                <td style=\"padding-bottom: 38px; text-align: end;\">} अर्जदार</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><b>१). ________________________________________________________</b></td>\n" +
                "                <td rowspan=\"3\" style=\"text-align: end;\">} प्रतिवादी</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><b>२). ________________________________________________________</b></td>\n" +
                "          \n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><b>३). ________________________________________________________</b></td>\n" +
                "               \n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "\n" +
                "    <div style=\"text-align: center; padding: 50px;\">\n" +
                "        <h2><b>रोजनामा</b></h2>\n" +
                "        <table style=\"width: 100%; border: 1px solid black; border-collapse: collapse;\">\n" +
                "            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                <td style=\"border: 1px solid black; border-collapse: collapse;\">दिनांक</td>\n" +
                "                <td style=\"border: 1px solid black; border-collapse: collapse;\">केलेली कामकाज</td>\n" +
                "            </tr>\n" +
                "            <tr style=\"width: 100%; border: 1px solid black; border-collapse: collapse; height: 700px;\">\n" +
                "                <td style=\"border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                <td style=\"border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "    \n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "     <!-- page 1 -->\n" +
                "     <div style=\"page-break-before: always;\">\n" +
                "            <div style=\"display: flex; justify-content: space-between;\">\n" +
                "                <h3 style=\"margin: 0;\">उपनिबंधक सहकारी संस्था संगमनेर</h3>\n" +
                "                <p style=\"margin: 0; align-self: flex-end;\">यांचे समोर ....</p>\n" +
                "            </div>\n" +
                "            <div style=\"border: 1px solid black; margin-top: 15px; box-shadow:  4.5px 4.5px black;\">\n" +
                "                    <h1 style=\"line-height : .6; text-align : center; margin-top : 1.5rem;\">नमुना - \"यू\"</h1>\n" +
                "                    <p style=\"line-height : .6; text-align : center;\">(नियम ८६ - अ पहावा )</p>\n" +
                "                    <h2 style=\"text-align : center;\">महाराष्ट्र सहकारी संस्था अधिनियम, १९६० चे  कलम १०१ अन्वये वसुली दाखल्यासाठी दाखल करण्याचा अर्ज </h2>\n" +
                "            </div>\n" +
                "        <div>\n" +
                "                <p>सह/उप/सहाय्यक निबंधक सहकारी संस्था</p>\n" +
                "                <div style=\"display: flex; justify-content: space-between;\">\n" +
                "                    <p style=\"margin: 0;\"><b>अर्ज क्र.: --- &nbsp;&nbsp; कलम १०१ /२०२_ - २०२_</b></p>\n" +
                "                    <div style=\"border: 1px solid black; width: 30%; padding: 5px;\">\n" +
                "                        <p style=\"margin: 0; align-self: flex-end;\"><b>कर्ज प्रकार :</b> __________</p>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "        </div>\n" +
                "        <table style=\"margin: 10px 0px 30px 30px; width: 90%;\">\n" +
                "            <tr >\n" +
                "                <td rowspan=\"3\" style=\"vertical-align: baseline; width: 5%;\"><b>१)</b></td>\n" +
                "                <td>____________________________________________________________</td>\n" +
                "                <td rowspan=\"3\">&nbsp;&nbsp;&nbsp;} अर्जदार</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td >____________________________________________________________</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td >____________________________________________________________</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td colspan=\"3\" style=\" text-align: center;\"><b>विरुद्ध</b></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\" vertical-align: baseline;\"rowspan=\"3\" ><b>१)</b></td>\n" +
                "                <td>&nbsp;&nbsp;&nbsp;&nbsp;<b>कर्जदाराचे नाव : ______________________________________________</b></td>\n" +
                "                <td rowspan=\"9\">&nbsp;&nbsp;&nbsp;} जाब देणार</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td style=\" vertical-align: baseline;\" rowspan=\"3\" ><b>२)</b></td>\n" +
                "                <td><b>जमीनदारांचे नाव : ______________________________________________</b></td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td style=\" vertical-align: baseline;\" rowspan=\"3\" ><b>३)</b></td>\n" +
                "                <td><b>जमीनदारांचे नाव : ______________________________________________</b></td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "            </tr>\n" +
                "            <tr >\n" +
                "                <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "\n" +
                "        <table style=\"width: 100%; border: 1px solid black; border-collapse: collapse;\">\n" +
                "            <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>मुद्दल</b></td>\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>व्याज</b></td>\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>दंड व्याज</b></td>\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>इतर खर्च</b></td>\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>एकूण</b></td>\n" +
                "            </tr>\n" +
                "            <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "\n" +
                "        <p><b>अर्जदार : _______________________,.</b> खालील प्रमाणे अर्ज सादर करत आहे. </p>\n" +
                "                <!-- <p style=\"text-align: end;\"> वसुलीस पात्र रक्कम रु.  _____________</p>\n" +
                "                <p style=\"text-align: end;\">अर्जदार _____________ सहकारी संस्था मर्यादित ________________</p> -->\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- page 2 -->\n" +
                "    <div>\n" +
                "                <table>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>१)</b></td>\n" +
                "                        <td>अर्जदार <b>_________,</b> . हि संस्था महाराष्ट्र सहकारी संस्था अधिनियम, १९६० मधील तरतुदीस अनुसरून रजिस्टर करण्यात आलेली संस्था आहे.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>२)</b></td>\n" +
                "                        <td >वर नमूद केलेले जाब देणार क्र . १ ते ३ सर्वजण संस्थेचे सभासद / नामपञ सभासद आहेत.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>३)</b></td>\n" +
                "                        <td > अर्जदार संस्थेचे लोन / कॅश क्रेडिट मालतारण/ हायपोथिकेशन कर्ज/जामिनकी कर्ज मिळवण्यासाठी जाब देणार क्र. १ ने दिनांक <b>_____</b> रोजी रितसर अर्ज संस्थेकडे केला आहे. जाब देणार क्र. १\n" +
                "                            ते\n" +
                "                            <b>____</b> यांनी वरील कर्ज व्यवहारास जामीनदार राहण्याचे कबूल केले आहे.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>४)</b></td>\n" +
                "                        <td >सदर अर्ज बँकेच्या संचालक मंडळाने / कर्ज समितीने जाब देणार क्र._____ ते _____ यांनी कर्ज अर्जाचा विचार करुन जाब देणार क्र. १ ते ______ यांस रक्कम रु. <b>_______</b>(अक्षरी रक्कम रु. <b>________ फक्त</b> ) कर्ज मंजूर केले आहे.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>५)</b></td>\n" +
                "                        <td >सदर कर्ज व्यवहारास जाब देणार क्र. १ ते. ____  यांनी खाली नमूद केलेले तारण दिले आहे.\n" +
                "                            <p><b>(अ) तारणाचा तपशील : _______________________________________________________________________________________________________</b></p>\n" +
                "                            <p><b> ___________________________________________________________________________________________________________________________</b></p>\n" +
                "                            <p><b>(ब) वैयक्तिक जामीन : ________________________________________________________________________________________________________</b></p>\n" +
                "                            <p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;१)______________________________________________________________________________________________________________________</b></p>\n" +
                "                            <p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;२)______________________________________________________________________________________________________________________</b></p>\n" +
                "                            \n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>६)</b></td>\n" +
                "                        <td >सर्व जाब देणार यांनी बँकेत दिनांक ____ . रोजी डिमांड प्रॉमिसरी नोट आणि इतर योग्य ते दस्तऐवज, करारनामे लिहून दिले आहेत. सदर करारनाम्यातील तपशिलाप्रमाणे सर्व जाब देणार यांनी व्यक्तिशः आणि इतर संयुक्तिकरित्या एकत्रितरित्या कर्ज परतफेड करण्याची हमी दिली आहे. सर्व जाब देणार कर्ज परतफेड करण्यास व्यक्ति आणि संयुक्तिकरित्या जबाबदार आहेत.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>७)</b></td>\n" +
                "                        <td >जाब देणार क्र. ____ ते ____ यांनी करारनाम्यातील तपशिलाप्रमाणे अर्जदार बँकेच्या कर्ज रकमेची परतफेड व्याजासह वेळेवर केलेली नही. सदर रकमेची त्यांनी थकबाकी केली आहे. सदर रक्कम परतफेड करण्यास त्यांनी कसूर केलेली आहे.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>८)</b></td>\n" +
                "                        <td >याबाबत अर्जदार संस्थेने सर्व जाब देणार यांनी कर्जाची थकबाकीची रक्कम भरण्याबाबत वेळोवेळी लेखी तसेच तोंडी सूचना दिल्या. मात्र जाब देणार यांनी कर्जाची थकबाकी रकमेचा भरणा अर्जदार बँकेत भरलेला नाही. जाब देणार थकबाकीदार झालेले आहेत.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>९)</b></td>\n" +
                "                        <td >वरील जाब देणार यांनी लिहून दिलेल्या करारपत्रांच्या अटींप्रमाणे कर्ज रकमेची परतफेड न केल्याने अर्जदार संस्थेच्या संचालक मंडळ / वसुली समिती / प्रशासन मंडळ यांचे मंजुरीनुसार दिनांक ____ . रोजी झालेल्या सभेमध्ये जाब देणार यांच्या कर्ज व्यवहाराबाबत विचारविनिमय झाला. महाराष्ट्र सहकारी संस्था अधिनियम १९६० च्या कलम १०१ अन्वये सर्व जाब देणार यांचेविरुद्ध अर्ज दाखल करण्याबाबतचा ठराव मंजूर केला आहे. त्याचप्रमाणे महाराष्ट्र सहकारी संस्था अधिनियम १९६० च्या कलम १५६ आणि महाराष्ट्र सहकारी संस्था नियम १९६१ नियम क्र. १०७ अन्वये अर्जदारांना मिळणाऱ्या वसुली दाखल्याची अंमलबजावणी करून मिळण्याबाबत ठराव मंजूर केला आहे.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>१०</b>)</td>\n" +
                "                        <td >वर नमूद केल्याप्रमाणे अर्जदार संस्थेने सर्व जाब देणार यांनी रितसर रजिस्टर्ड ए. डी. पोस्टाने दि. ____ रोजी नोटीसा पाठवून रक्कम रु. ____ ची मागणी केली. त्याचप्रमाणे जाब देणार यांनी अर्जदार संस्थेकडे रकमेचा भरणा न केल्यास सर्व जाब देणार यांचेवर महाराष्ट्र सहकारी संस्था अधिनियम १९६० च्या कलम १०१ मधील तरतुदीप्रमाणे अर्ज दाखल करून रक्कम वसुलीबाबत कायदेशीर उपाययोजना करण्यात येईल असे कळविले आहे.</td>\n" +
                "                    </tr>\n" +
                "                    </table> \n" +
                "    </div>\n" +
                "\n" +
                "    <!-- page 3 -->\n" +
                "    <div>\n" +
                "                <table>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>११)</b></td>\n" +
                "                        <td >कर्जव्यवहाराबाबत तपशील (दिनांक ____ अखेर)\n" +
                "                            <p><b>(अ) कर्ज मंजुरीचा तपशील</b></p>\n" +
                "                            <table style=\" border: 1px solid black; border-collapse: collapse; width: 70%; margin-top: 15px;\">\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>अ. क्र. </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>तपशील </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>रक्कम रुपये </b></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>१)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">कर्ज मंजूर रक्कम</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>२)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">कर्ज दिल्याची तारीख</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>३)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">कर्ज परतफेडीचा देय दिनांक</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>४)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">कर्ज परतफेडीचा मासिक हप्ता (लागू असल्यास)</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>५)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">कर्जाचा व्याजदर (द. सा. द. शे. )</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>६)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">कर्ज मंजुरी नियम क्र. ___ प्रमाणे दंडव्याजाचा दर</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "\n" +
                "                            <p><b>(ब) कर्ज वसुलीबाबतचा तपशील</b></p>\n" +
                "                            <table style=\" border: 1px solid black; border-collapse: collapse; width: 70%; margin-top: 10px;\">\n" +
                "                                \n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>अ. क्र. </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>तपशील </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>रक्कम रुपये </b></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>१)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">मंजूर कर्ज रक्कम रु. ___</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">मुद्दल रक्कम रुपये</td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>२)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर मुद्दल वसूल रु.</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>३)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर येणे बाकी रु.</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "\n" +
                "                            <p><b>(क) व्याज आकारणीबाबत तपशील</b></p>\n" +
                "                            <table style=\" border: 1px solid black; border-collapse: collapse; width: 70%;\">\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>अ. क्र. </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>तपशील </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>रक्कम रुपये </b></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>४)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर आकारलेले एकूण व्याज\n" +
                "                                        <p>(द.सा.द.शे. दराने) सोबतच्या तक्त्याप्रमाणे</p>\n" +
                "                                    </td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">मुद्दल रक्कम रुपये</td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>५)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर वसूल झालेली व्याजाची एकूण रक्कम</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>६)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर येणेबाकी व्याज</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>७)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर आकारलेले एकूण जादा व्याज\n" +
                "                                        <p>(द.सा.द.शे. दराने) सोबतच्या तक्त्याप्रमाणे</p>\n" +
                "                                    </td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>८)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर वसूल झालेले जादा व्याज</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>९)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर येणे असलेले जादा व्याज</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "\n" +
                "                            <p><b>(ड) इतर खर्चाचा तपशील</b></p>\n" +
                "                            <table style=\" border: 1px solid black; border-collapse: collapse; width: 70%;\">\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>अ. क्र. </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>तपशील </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>रक्कम रुपये </b></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>१०</b>)</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर आकारलेला विमा हप्ता</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>११</b>)</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर आकारलेली एकूण नोटीस फी, पोस्टेज, स्टॅप ड्यूटी, जाहिरात इ.</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;text-align: center;\"><b>१२</b>)</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">दिनांक ___ अखेर येणे बाकी</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "\n" +
                "                            <p><b>(इ) एकूण वसुलपात्र रक्कम रुपये</b></p>\n" +
                "                            <table style=\" border: 1px solid black; border-collapse: collapse; width: 70%;\">\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;text-align: center;\"><b>अ. क्र. </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;text-align: center;\"><b>तपशील </b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;text-align: center;\"><b>रक्कम रुपये </b></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;text-align: center;\"><b>अ)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"> मुद्दल रक्कम रुपये</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;text-align: center;\"><b>ब)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"> व्याज रक्कम रुपये</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;text-align: center;\"><b>क)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">जादा व्याज रक्कम रुपये</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;text-align: center;\"><b>ड)</b></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">इतर खर्च रक्कम रुपये</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                                <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\">एकूण रुपये</td>\n" +
                "                                    <td style=\" border: 1px solid black; border-collapse: collapse;\"></td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>१२)</b></td>\n" +
                "                        <td>अर्जासोबत जाब देणार यांचे खाते उतारे, वचनचिठ्ठी, जामिनरोखा / मालतारण / हायपोथिकेशन/ गहाणखत इ.\n" +
                "                            करार इत्यादी दस्तऐवजाच्या नकला केलेल्या आहेत.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>१३)</b></td>\n" +
                "                        <td>वचनचिठ्ठी/ करारनामे या दस्तऐवजांचे वरुन व्याज व कर्जाच्या वसुलीसाठी हा अर्ज करीत आहे. अर्जदारांचा अर्ज हा नमुद रक्कम </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- page 4 -->\n" +
                "    <div>\n" +
                "        <div class=\"row\">\n" +
                "            <div class=\"col-md-12\">\n" +
                "                <p>अर्जदार क्लेम हा सद्हेतूने कायदेशीर असल्याचे अर्जदाराचे म्हणणे आहे व जाब देणार यांस आपले बचावासाठी कोणतेही संयुक्तिक व वैध कारण नाही याची अर्जदारास खात्री आहे. सबब महाराष्ट्र सहकारी संस्था नियम क्र. ७\n" +
                "                   </p>\n" +
                "                    <p> (क) मधील तरतुदीच्या तत्त्वास अनुसरुन अर्जांचा समरी पद्धतीने निर्णय करण्यात यावा ही विनंती.</p>\n" +
                "                <table class=\"mt-3\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>१४)</b></td>\n" +
                "                        <td >अर्जास कारण मे. उप/सहाय्यक निबंधक यांचे स्थलसीमेत जाब देणार क्र. १ ते. ___ यांनी दिनांक ___ रोजी कर्ज घेतले. जाब देणार क्र. १ ते. ___ यांनी वचनचिठ्ठी / करारनामा दस्तऐवज लिहून दिले. जाब देणार क्र. १ ते ___ यांनी शर्तींप्रमाणे कजांची परतफेड केली नाही. दिनांक ___ - अखेर जाब देणार क्र. १ ते. ___ यांचेकडून बाकी येणे झाली. त्याची मागणी अर्जदार संस्थेने दिनांक. ___ रोजीचे नोटीशीने करूनही परतफेड केली नाही. त्या प्रत्येक दिवशी घडले आहे. \n" +
                "                            </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>१५)</b></td>\n" +
                "                        <td>अर्जाचे मूल्यांकन (व्हॅल्यूएशन) रु. ___ केले असून अर्जास प्रोसेस व स्टॅम्प की रक्कम रु. ___ चा भरणा केला आहे.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"vertical-align: baseline;\"><b>१६)</b></td>\n" +
                "                        <td>\n" +
                "                            अर्जदाराची विनंती की,\n" +
                "                            <table>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"vertical-align: baseline;\">१)</td>\n" +
                "                                    <td>अर्जदार यांना सर्व जाब देणार यांचेकडून राजम रु. ___ व सदर रकमेवर होणारे दरसाल दरशेकडा ___ % प्रमाणे दिनांक ___ होणारी व्याजाची रक्कम अधिक अर्जाचा व इतर संपूर्ण फी चा खर्च जाब देणार यांनी देणेबाबत योग्य ते हुकूम व्हावेत व त्या रकमेचा वसुली दाखला मिळावा ही विनंती.</td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"vertical-align: baseline;\">२)</td>\n" +
                "                                    <td>वर नमूद केलेल्या वसुली दाखल्याची रक्कम ही जमीन महसूलची बाकी आहे असे समजून वसूल करण्याबाबत योग्य ते हुकूम व्हावेत.</td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"vertical-align: baseline;\">३)</td>\n" +
                "                                    <td>त्याप्रमाणे महाराष्ट्र सहकारी संस्था अधिनियम १९६० कलम १५६ आणि महाराष्ट्र सहकारी संस्था नियम १९६१ नियम १०७ (१) (इ) प्रमाणे वसुलीचा दाखला बजावणीसाठी संस्थेकडील योग्य त्या अधिकारी व विक्री अधिकारी यांचेकडे पुढील कार्यवाहीसाठी पाठविणेबाबत योग्य ते हुकूम व्हावेत.</td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"vertical-align: baseline;\">४)</td>\n" +
                "                                    <td>इतर योग्य ते न्यायाचे हुकूम व्हावेत ही विनंती. <br> हा अर्ज दिनांक ___ रोजी दाखला केला आहे.</p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div>\n" +
                "                <p style=\"text-align: end;\">मुख्य कार्यकारी अधिकारी/सचिव/ व्यवस्थापक</p>\n" +
                "                <p style=\"text-align: end;\"><b>_________________________________</b></p>\n" +
                "                <hr>\n" +
                "                <p style=\"text-indent: 35px;\">मा. श्री.<b> ___</b> सहकारी संस्था मर्यादित, ___ सही करणार सत्य प्रतिज्ञेवर मर्यादित, तर्फे अर्जासोबत अर्जदार यांनी खालीलप्रमाणे कागदपत्रे सादर केली आहेत.</p>\n" +
                "\n" +
                "                <table style=\"margin-left: 5%;\">\n" +
                "                    <tr>\n" +
                "                        <td>१)</td>\n" +
                "                        <td>या अर्जाचा एकूण २ नकला.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>२)</td>\n" +
                "                        <td>संचालक मंडळ / वसुली मंडळ / प्रशासक मंडळ यांचे ठरावाची प्रत</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>३)</td>\n" +
                "                        <td>जाब देणार यांना कर्ज खाते क्र. ___ बाबत दिनांक ___ रोजी पाठविलेल्या रजिस्टर्ड नोटीशीची व पोहोच पावतीची प्रत.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>४)</td>\n" +
                "                        <td>जाब देणार यांनी अर्जदार संस्थेकडे दिनांक ___ रोजी लिहून दिलेल्या डिमांड प्रॉमिसरी नोट / करारनाम्याची / दस्तऐवजाची खरी नक्कल.</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>५)</td>\n" +
                "                        <td>जाब देणार यांचे क्र. ___ थे खाते उताऱ्याची खरी नक्कल.</td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "                    <p style=\"text-align: end;\">हा अर्ज दिनांक ___ रोजी दाखला केला आहे.</p>\n" +
                "                    <p style=\"text-align: end;\">___ सहकारी संस्था मर्यादित, करिता</p>\n" +
                "                \n" +
                "            </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- page 6 & 7 -->\n" +
                "    <div style=\"page-break-before: always;\">\n" +
                "        <div style=\"display: flex; justify-content: space-between;\">\n" +
                "            <h3 style=\"margin: 0;\">उपनिबंधक सहकारी संस्था संगमनेर</h3>\n" +
                "            <p style=\"margin: 0; align-self: flex-end;\">यांचे समोर ....</p>\n" +
                "        </div>\n" +
                "        <div style=\"border: 1px solid black; margin-top: 15px; box-shadow:  4.5px 4.5px black;\">\n" +
                "                    <h1 style=\"line-height : .6; text-align : center; margin-top : 1.5rem;\">नमुना - \"व्ही\"</h1>\n" +
                "                    <p style=\"line-height : .6; text-align : center;\">(नियम ८६ क पहावा)</p>\n" +
                "                    <h2 style=\"text-align: center;\">कलम १०१ खाली वसुलीसाठी प्रमाणपत्राचा नमुना</h2>\n" +
                "        </div> \n" +
                "\n" +
                "        <p>सह / उप / सहाय्यक निबंधक सहकारी संस्था</p>\n" +
                "        <div>\n" +
                "            <div style=\"display: flex; justify-content: space-between;\">\n" +
                "                        <p style=\"margin: 0;\"><b>अर्ज क्र.: --- &nbsp;&nbsp; कलम १०१ /२०२_ - २०२_</b></p>\n" +
                "                        <div style=\"border: 1px solid black; width: 30%; padding: 5px;\">\n" +
                "                            <p style=\"margin: 0; align-self: flex-end;\"><b>कर्ज प्रकार :</b> __________</p>\n" +
                "                        </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"row\">\n" +
                "                    <table style=\"margin: 10px 0px 30px 30px; width: 90%;\">\n" +
                "                        <tr >\n" +
                "                            <td rowspan=\"3\" style=\"vertical-align: baseline; width: 5%;\"><b>१)</b></td>\n" +
                "                            <td>____________________________________________________________</td>\n" +
                "                            <td rowspan=\"3\">&nbsp;&nbsp;&nbsp;} अर्जदार</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td >____________________________________________________________</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td >____________________________________________________________</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td colspan=\"3\" style=\" text-align: center;\"><b>विरुद्ध</b></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\" vertical-align: baseline;\"rowspan=\"3\" ><b>१)</b></td>\n" +
                "                            <td>&nbsp;&nbsp;&nbsp;&nbsp;<b>कर्जदाराचे नाव : ______________________________________________</b></td>\n" +
                "                            <td rowspan=\"9\">&nbsp;&nbsp;&nbsp;} जाब देणार</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td style=\" vertical-align: baseline;\" rowspan=\"3\" ><b>२)</b></td>\n" +
                "                            <td><b>जमीनदारांचे नाव : ______________________________________________</b></td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td style=\" vertical-align: baseline;\" rowspan=\"3\" ><b>३)</b></td>\n" +
                "                            <td><b>जमीनदारांचे नाव : ______________________________________________</b></td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>राहणार : </b>________________________________________________</td>\n" +
                "                        </tr>\n" +
                "                        <tr >\n" +
                "                            <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>व्यवसाय : </b>________________________________________________</td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "        <div>\n" +
                "                        <h4>महाराष्ट्र सहकारी संस्था अधिनियम १९६० चे कलम १०१ अन्वये वसुली प्रकरण वसुलीस पात्र रक्कम रुपये. ______</h4>\n" +
                "                        <table style=\"width: 100%; border: 1px solid black; border-collapse: collapse;\">\n" +
                "                            <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>मुद्दल</b></td>\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>व्याज</b></td>\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>दंड व्याज</b></td>\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>इतर खर्च</b></td>\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse; text-align: center;\"><b>एकूण</b></td>\n" +
                "                            </tr>\n" +
                "                            <tr style=\" border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                                <td style=\" border: 1px solid black; border-collapse: collapse;\">&nbsp;</td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    \n" +
                "                        <table >\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">१)</td>\n" +
                "                                <td>अर्जदार संस्थेने दाखल केल्याची तारीख <b>________</b></td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">२)</td>\n" +
                "                                <td>प्रकरणासोबत अर्जदार संस्थेने सादर केलेल्या कागदपत्रांचा तपशील.\n" +
                "                                    <table class=\"mt-3\">\n" +
                "                                        <tr>\n" +
                "                                            <td >१)</td>\n" +
                "                                            <td>अर्जदार संस्थेने जाद देणार यांचेकडून रक्कम वसुलीसाठी दाखला मिळणेचा अर्ज.</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td >२)</td>\n" +
                "                                            <td>अर्जदार संस्था व्यवस्थापक मंडळाच्या दिनांक: _/_/२०२ ठरावाची सत्यप्रत.</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td >३)</td>\n" +
                "                                            <td>जाब देणार यांना अर्जदार संस्थेने प्रकरण सादर करण्यापूर्वी दिलेल्या नोटिशीची प्रत.</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td >४)</td>\n" +
                "                                            <td>जाब देणार यांना रुपये. ___ रक्कम अदा केल्यापासूनचा अद्यावत हिशोबी तक्ता.</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td >५)</td>\n" +
                "                                            <td>वचन चिठ्ठीची सत्यप्रत.</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td >६)</td>\n" +
                "                                            <td>करारनाम्याची सत्य प्रत</td>\n" +
                "                                        </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td >७)</td>\n" +
                "                                            <td>अर्जदार संस्थेस जाब देणार यांचेकडून येणे रक्कम सिद्ध करण्यास जरूर असणारी कागदपत्रे</td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">३)</td>\n" +
                "                                <td>जाब देणार यांना म्हणणे मांडण्यासाठी सुनावणी नोटीस पाठविल्याची तारीख: _/_/२०२</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">४)</td>\n" +
                "                                <td>जाब देणार हजर राहिल्याची तारीख: _/_/२०२</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">५)</td>\n" +
                "                                <td>सुनावणीची तारीख: _/_/२०२</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">६)</td>\n" +
                "                                <td>अर्जदार संस्थेतर्फे वकील _________</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">७)</td>\n" +
                "                                <td>जाब देणारातर्फे वकील _________</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">८)</td>\n" +
                "                                <td>जाब देणाऱ्या अर्जदार संस्थेने येणे बाकीबाबत सादर केलेले म्हणणे ______________</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"vertical-align: baseline; text-align: center;\">९)</td>\n" +
                "                                <td>वरील निवेदनावर निबंधकांचा अभिप्राय ___________</td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "\n" +
                "                        <p style=\"text-indent:35px\">\n" +
                "                        ज्या अर्थी भी वर नमूद केलेल्या सर्व जाब देणार यांना रीतसर नोटीस पाठवून न्यायाचे दृष्टीने सुनावणीसाठी व म्हणणे मांडण्यासाठी योग्य व भरपूर संधी दिली आहे. तसेच अर्जदार संस्थेला उपरोक्त जाब देणाऱ्यांनी करून दिलेली वचनचिठ्ठी, करारनामा व त्यानुसार संस्थेने सादर केलेला खाते उतारा इ. कागदपत्रांची व व्यवहाराची छाननी व चौकशी केली असता माझी अशी खात्री झाली आहे की, अर्जदार संस्थेकडून जाब देणार यांनी कर्ज घेतले असून ते थकविलेले आहे आणि ते वसूल होण्यासाठी मी अभिप्रायास अनुसरून खालीलप्रमाणे हुकूम करीत आहे.\n" +
                "                        </p>\n" +
                "\n" +
                "                        <p class=\"mt-2\">दि महाराष्ट्र सहकारी संस्था अधिनियम १९६० चे कलम १०१ अन्दये वसुली दाखला</p>\n" +
                "                        <p>उपरोक्त अर्जदार संस्थेस जाब देणार यांनी</p>\n" +
                "\n" +
                "\n" +
                "                        <table style=\"border: 1px solid black; border-collapse: collapse; width: 70%;\">\n" +
                "                            <tr>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">अर्जाची रकम</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">रुपये _______</td>\n" +
                "                            </tr>\n" +
                "                            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">या वर्षाची फी व खर्च</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">रुपये _______</td>\n" +
                "                            </tr>\n" +
                "                            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">व अन्य खर्च (नोटीस फी, पोस्टेज, स्टॅप डयूटी, जाहिरात इ.)</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">रुपये _______</td>\n" +
                "                            </tr>\n" +
                "                            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">असे एकूण</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">रुपये _______</td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                        <p style=\"text-indent:35px\">\n" +
                "                            तसेच मुद्दल रकम रुपये. _____ यावर दिनांक _/_/२०२ पासून पुढे ही सर्व रकम परतफेड होईपर्यंत द. सा. द. शे. _____ % दराने होणाऱ्या व्याजाची रकम अशी परंतु अशा रकमेमध्ये जाब देणार यांनी प्रस्तुतचा अर्ज दाखल केल्यानंतर भरणा केलेली सर्व रक्कम वजा करून शिल्लक राहणारी अशी सर्व रक्कम जाब देणार\n" +
                "                            यांचेकडून वसूल करावी.\n" +
                "                        </p>\n" +
                "                        <p><b>अर्जदार यांचा झालेला खर्च</b></p>\n" +
                "                        <table style=\"border: 1px solid black; border-collapse: collapse; width: 70%;\">\n" +
                "                            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse; vertical-align: baseline; text-align: center;\">(अ)</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\"> अर्जास लावलेला स्टॅम्प </td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">रुपये _______</td>\n" +
                "                            </tr>\n" +
                "                            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse; vertical-align: baseline; text-align: center;\">ब)</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\"> चौकशी फी व खर्च</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">रुपये _______</td>\n" +
                "                            </tr>\n" +
                "                            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse; vertical-align: baseline; text-align: center;\">क)</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\"> इतर खर्च</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">रुपये _______</td>\n" +
                "                            </tr>\n" +
                "                            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse; vertical-align: baseline; text-align: center;\">ड)</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\"> जाहीर समन्स</td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\">रुपये _______</td>\n" +
                "                            </tr> \n" +
                "                            <tr style=\"border: 1px solid black; border-collapse: collapse;\">\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse; vertical-align: baseline; text-align: center;\"></td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\"><b>एकूण खर्च</b></td>\n" +
                "                                <td style=\"border: 1px solid black; border-collapse: collapse;\"><b>रुपये _______</b></td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "\n" +
                "                        <p style=\"text-indent:35px\">मी आणखी असा हुकूम करतो की, जब देणार यांचेकडून वर नमूद केलेली रक्कम महाराष्ट्र महसूल अधिनियम १९६६ मधील तरतुदीस अनुसरून जमीन महसुलाची बाकी वसूल करण्याच्या रितीप्रमाणे ती जाब देणाऱ्यांकडून वसूल करावी अथवा</p>\n" +
                "                        <p style=\"text-indent:35px\">अर्जदार यांनी या वसुली दाखल्याची बजावणी त्यांचेकडील ज्या अधिकाऱ्यांना महाराष्ट्र सहकारी किंवा अधिनियम १९६० चे कलम १५६ आणि महाराष्ट्र सहकारी संस्था अधिनियम १९६० चे यातील नियम १०७ अन्वये ज्याला अधिकार प्राप्त झाले आहेत. त्यांचेप्रमाणे या वसुली दाखल्याची बजावणी करून वसूल करण्यात यावी. यासाठी स्वतंत्र जर्ज या कार्यालयाकडे करण्याची जरुरी नाही.</p>\n" +
                "                        <p style=\"text-indent:35px\">हा वसुली दाखला आज दिनांक _/_/२०२ रोजी माझी सही व कार्यालयाचे मुद्रेसह दिला आहे.</p>\n" +
                "                        \n" +
                "\n" +
                "                        <div style=\"height: 100px;\">\n" +
                "                            <div style=\"float: left; width: 50%;\">\n" +
                "                                <p>स्थळ :  </p>\n" +
                "                                <p>दिनांक :</p>\n" +
                "                            </div>\n" +
                "                            <div style=\"float: right; width: 50%;\">\n" +
                "                                <p style=\"text-align: end;\">सह / उप / सहाय्यक निबंधक</p>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "\n" +
                "                        <div>\n" +
                "                            <hr>\n" +
                "                            <p>टिप : १) सदरचा वसुली दाखला हा जाब देणार याचेकडून रक्कम वसुलीचा अतिम दाखला असून त्यावर त्यांना अपील करता येणार नाही. मात्र, सदरचा निर्णय अमान्य असल्यास याविरूद्ध विभागीय सहनिबंधक, सहकारी संस्था याचेकडे कलम १५४ अन्वये पुनरिक्षण अर्ज करता येईल / सह निबंधकांच्या निर्णयाविरूद्ध शासनाकडे कलम १५४ अन्वये पुनरिक्षण अर्ज करता येईल.</p>\n" +
                "                            <p>२) कलम ९१ खाली नमुद केलेल्या परतुकाप्रमाणे हा वादाचा विषय होत नसल्याने याविरुद्ध सहकार न्यायालयात अपील करता येणार नाही तसेच कलम ९५ प्रमाणे स्थगनादेशही घेता येणार नाही.</p>\n" +
                "                        </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Create ConverterProperties and set the font provider
            ConverterProperties converterProperties = new ConverterProperties();

            FontProvider fontProvider = new FontProvider();

            fontProvider.addFont("C:\\Users\\swapnilp\\Desktop\\Noto_Sans\\NotoSans-Regular.ttf", PdfEncodings.IDENTITY_H);

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

                return ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, files.getOriginalFilename()))
                    .body(courtCaseList);
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

    private List<PDFModel> generate101NamunaHtml(List<CourtCase> courtCaseList, CourtCaseSetting courtCaseSetting) {
        List<PDFModel> One01NamunaHtmlList = new ArrayList<>();

        for (CourtCase courtCase : courtCaseList) {
            PDFModel pdfModel = new PDFModel();
            String One01Namuna =
                "<!DOCTYPE html>" +
                "<htm lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                //                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                "</head>" +
                "<body>" +
                "    <div class=\"m-3\">" +
                "        <h1 style=\"line-height : .3; text-align : center; margin-top : 0.9rem;\">पुणे जिल्हा मध्यवर्ती सहकारी बँक मर्यादित, पुणे</h1>" +
                "        <h1 style=\"line-height : .3; text-align : center;\">मुख्य कचेरी : ४ . बी. जे. रोड, पुणे ४११००१.</h1>" +
                "        <h1 style=\"line-height : .3; text-align : center;\">punedc@mail.com</h1>" +
                "        <hr>" +
                "    </div>" +
                "    <div class=\"m-3\">" +
                "    <div style=\"text-decoration:underline;  text-align : center; margin-top : 0.1rem;\">१०१ नुसार कारवाई करणेपूर्वीची नोटीस </div>" +
                "    <div style=\"text-decoration:underline;    text-align : center; \">रजिस्टर ए.डी./ यु.पी.सी.</div>" +
                "    <div style=\"display: flex; justify-content: space-between;\">" +
                "        <div style=\"flex-basis: 50%; text-align: left;\">" +
                "            <p>जा.क्र. / २०२३-२४ /</p>" +
                "        </div>" +
                "        <div style=\"flex-basis: 50%; text-align: right;\">" +
                "            <div>" +
                "                <p>दिनांक :- " +
                oneZeroOneDateMr(LocalDate.now()) +
                "</p>" +
                "            </div>" +
                "        </div>" +
                "    </div>" +
                "<div>कर्जदार :- श्री. / श्रीमती:- " +
                courtCase.getNameOfDefaulter() +
                "</div>" +
                "    <div>राहणार :-" +
                courtCase.getAddress() +
                "</div>" +
                "    <div>१) जामीनदार :- श्री. / श्रीमती:-" +
                courtCase.getGaurentorOne() +
                "</div>" +
                "    <div>राहणार :- " +
                courtCase.getGaurentorOneAddress() +
                "</div>" +
                "    <div>२) जामीनदार :- श्री. /श्रीमती:-" +
                courtCase.getGaurentorTwo() +
                "</div>" +
                "    <div>राहणार :- " +
                courtCase.getGaurentorTwoAddress() +
                "</div><div style=\"margin-top : 1.5rem;\"> </div>" +
                "<div style=\"margin-top : 1.5rem;\">" +
                "<p style=\"text-indent: 50px;\"> विषय: थकीत कर्ज परतफेड करणेबाबत..</p> <div style=\"margin-top : 1.5rem;\"> </div>" +
                "<p style=\"text-decoration: underline;\"> तुम्हास नोटीस देण्यात येते की,</p>" +
                "<p style=\"line-height : 1.5;\">१. तुम्ही बँकेच्या " +
                courtCase.getBankName() +
                " शाखेकडून दिनांक " +
                oneZeroOneDateMr(courtCase.getLoanDate()) +
                " रोजी कर्ज योजने अंतर्गत कारणासाठी रूपये " +
                courtCase.getLoanAmount() +
                " /- चे कर्ज द.सा.द.शे. " +
                courtCase.getInterestRate() +
                "% व्याजदराने " +
                courtCase.getTermOfLoan() +
                " वर्षेच्या मुदतीने घेतले आहे." +
                "</p>" +
                "<p> </p>" +
                "<p style=\"line-height : 1.5;\">" +
                "२. सदर कर्ज व्यवहारास श्री " +
                courtCase.getGaurentorOne() +
                " व श्री " +
                courtCase.getGaurentorTwo() +
                " हे व्यक्ति जामीनदार आहेत." +
                "</p>" +
                "<p style=\"line-height : 1.5;\">" +
                "३. मूळ कर्जदार व जामीनदार यांनी बँकेस दिनांक " +
                oneZeroOneDateMr(courtCase.getLoanDate()) +
                " रोजी रितसर वचन विट्टी, करारनामे" +
                "व इतर सर्व कागदपत्रे लिहून दिलेली आहेत." +
                "</p>" +
                "<p style=\"line-height : 1.5;\">" +
                "४. वचनचिठ्ठी व कारारनाम्यातील तपशीलाप्रमाणे तसेच तुम्ही सदर कर्ज रक्कम मंजूरी पत्रातील अटी व" +
                "शर्तीनुसार परतफेड करण्यास वैयक्तिक व सामुदायिक जबावदार आहात." +
                "</p>" +
                "<p style=\"line-height : 1.5;\">" +
                "5.आपण बँकेकडून घेतलेल्या कर्ज रकमेची परतफेड वेळेवर व करारातील अटी व शर्तीप्रमाणे केलेली नाही. म्हणून तुम्हास" +
                "या पूर्वीही नोटिस पाठविण्यात आल्या होत्या तरीही तुम्ही थकीत कर्जाच्या रकमेची संपूर्ण परतफेड केलेली नाही." +
                "त्यामुळे महाराष्ट्र सहकारी संस्था अधिनियम १९६० चे कलम (१९०१ (१) अन्वये संबंधित मा. उपनिबंधक / मा. सहाय्यक" +
                "निबंधक, सहकारी संस्था यांचेकडे अर्ज दाखल करून वसूली दाखला मिळणेबाबतचा अर्ज करणे वावत्तचा निर्णय घेण्यात" +
                "आलेला आहे. तथापि तुम्हास एक संधी म्हणून सदर प्रकरणी अंतिम नोटिस देण्यात येत आहे." +
                "</p>" +
                "<b style=\"text-decoration: underline;\">" +
                "दिनांक: " +
                oneZeroOneDateMr(courtCase.getDueDate()) +
                " अखेर तुमचेकडून खालीलप्रमाणे येणे आहे." +
                "</b>" +
                "<table style=\"border-collapse: collapse; width: 100%; margin-top : 1.5rem;\">" +
                "<tr>" +
                "<th style=\"border: 1px solid black;\"> अनु. क्र.</th>" +
                "<th style=\"border: 1px solid black;\">तपशील</th>" +
                "<th style=\"border: 1px solid black;\">रक्कम रूपये</th>" +
                "</tr>" +
                "<tr>" +
                "<td style=\"border: 1px solid black;\">१.</td>" +
                "<td style=\"border: 1px solid black;\">मुद्दल</td>" +
                "<td style=\"border: 1px solid black;\">रूपये. " +
                courtCase.getLoanAmount() +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style=\"border: 1px solid black;\">२.</td>" +
                "<td style=\"border: 1px solid black;\">व्याज येणे + दंड व्याज</td>" +
                "<td style=\"border: 1px solid black;\">रूपये. " +
                courtCase.getDueInterest() +
                "+" +
                courtCase.getDuePenalInterest() +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style=\"border: 1px solid black;\">३.</td>" +
                "<td style=\"border: 1px solid black;\">इतर खर्च - नोटिस इत्यादी</td>" +
                "<td style=\"border: 1px solid black;\">रूपये.</td>" +
                "</tr>" +
                "<tr>" +
                "<td colspan=\"2\" class=\"text-end\" style=\"border: 1px solid black;\">एकूण:-</td>" +
                "<td style=\"border: 1px solid black;\">रूपये. " +
                getTotalValue(courtCase.getLoanAmountEn(), courtCase.getDueInterestEn(), courtCase.getDuePenalInterestEn()) +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</div>" +
                "<div style=\"margin-top : 1.5rem;\">" +
                "<p style=\"line-height : 1.5;\">तरी तुम्हास या नोटिशीने कळविण्यात येत की, ही नोटीस मिळाल्यापासून अठ दिवसांच्या आत वर नमूद केल्याप्रमाणे रक्कम भरावी/सदर मुदतीत रक्कम न भरल्यास आपले विरूद्ध पुढील कायदेशीर कारवाई करण्यात येईल व त्याच्या खर्चाची व परिणामाची संपूर्ण जवाबदारी तुमचेवर राहील, याची नोंद घ्यावी.</p>" +
                "</div>" +
                "<div>" +
                "<div>" +
                "<p style=\"text-align: right; line-height : 1.5;\">" +
                courtCaseSetting.getVasuliAdhikariName() +
                "</p>" +
                "<p style=\"text-align: right; line-height : 1.5;\">शाखा व्यवस्थापक / वसुली अधिकारी / विकास अधिकारी</p>" +
                "<pre style=\"text-align: right;\">विभाग :-              द्वारा :-              शाखा,</pre>" +
                "<p style=\"text-align: right;\">" +
                courtCaseSetting.getBankName() +
                "</p>" +
                "</div>" +
                "</div>" +
                "    </div>" +
                "</body>" +
                "</html>";

            pdfModel.setFileName(courtCase.getNameOfDefaulter() + "_" + courtCase.getAccountNo() + ".pdf");
            pdfModel.setHtmlString(One01Namuna);

            One01NamunaHtmlList.add(pdfModel);
        }
        return One01NamunaHtmlList;
    }

    private String oneZeroOneDateMr(LocalDate loanDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = loanDate.format(formatter);

            return translationServiceUtility.translationText(formattedDate);
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

    private List<String> generate101Html(List<CourtCase> courtCaseList) {
        List<String> one01NamunaList = new ArrayList<>();
        for (CourtCase courtCase : courtCaseList) {
            String one01Namuna =
                "<html>" +
                "    <style>" +
                "        .h1,.h2,.h3,.h4,.h5,.h6,h1,h2,h3,h4,h5,h6 {" +
                "            margin-top: 0;" +
                "            margin-bottom: .5rem;" +
                "            font-weight: 500;" +
                "            line-height: 1.2;" +
                "            color: var(--bs-heading-color)" +
                "        }" +
                "        .h1,h1 {" +
                "            font-size: calc(1.375rem + 1.5vw)" +
                "        }" +
                "        p {" +
                "            margin-top: 0;" +
                "            margin-bottom: 1rem" +
                "        }" +
                "        b," +
                "        strong {" +
                "            font-weight: bolder" +
                "        }" +
                "        table {" +
                "            caption-side: bottom;" +
                "            border-collapse: collapse" +
                "        }" +
                "        th {" +
                "            text-align: inherit;" +
                "            text-align: -webkit-match-parent" +
                "        }" +
                "        tbody," +
                "        td," +
                "        tfoot," +
                "        th," +
                "        thead," +
                "        tr {" +
                "            border-color: inherit;" +
                "            border-style: solid;" +
                "            border-width: 0" +
                "        }" +
                "        .col {" +
                "            flex: 1 0 0%" +
                "        }" +
                "        .d-flex {" +
                "            display: flex !important" +
                "        }" +
                "        .flex-row {" +
                "            flex-direction: row !important" +
                "        }" +
                "        .flex-row-reverse {" +
                "            flex-direction: row-reverse !important" +
                "        }" +
                "        .m-1 {" +
                "            margin: .25rem !important" +
                "        }" +
                "        .m-2 {" +
                "            margin: .5rem !important" +
                "        }" +
                "        .m-3 {" +
                "            margin: 1rem !important" +
                "        }" +
                "        .m-4 {" +
                "            margin: 1.5rem !important" +
                "        }" +
                "        .m-5 {" +
                "            margin: 3rem !important" +
                "        }" +
                "        .m-auto {" +
                "            margin: auto !important" +
                "        }" +
                "    " +
                "        .mt-0 {" +
                "            margin-top: 0 !important" +
                "        }" +
                "        .mt-1 {" +
                "            margin-top: .25rem !important" +
                "        }" +
                "        .mt-2 {" +
                "            margin-top: .5rem !important" +
                "        }" +
                "        .mt-3 {" +
                "            margin-top: 1rem !important" +
                "        }" +
                "        .mt-4 {" +
                "            margin-top: 1.5rem !important" +
                "        }" +
                "        .mt-5 {" +
                "            margin-top: 3rem !important" +
                "        }" +
                "        .p-0 {" +
                "            padding: 0 !important" +
                "        }" +
                "        .p-1 {" +
                "            padding: .25rem !important" +
                "        }" +
                "        .p-2 {" +
                "            padding: .5rem !important" +
                "        }" +
                "        .p-3 {" +
                "            padding: 1rem !important" +
                "        }" +
                "        .p-4 {" +
                "            padding: 1.5rem !important" +
                "        }" +
                "        .p-5 {" +
                "            padding: 3rem !important" +
                "        }" +
                "        .text-end {" +
                "            text-align: right !important" +
                "        }" +
                "        .text-center {" +
                "            text-align: center !important" +
                "        }" +
                "        .text-decoration-underline {" +
                "            text-decoration: underline !important" +
                "        }" +
                "        .table {" +
                "            border: 1px solid rgb(107, 107, 107) !important;" +
                "        }" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            background-color: #f7f7f7;" +
                "        }" +
                "        table {" +
                "            width: 100%;" +
                "            border-collapse: collapse;" +
                "            margin-top: 20px;" +
                "        }" +
                "        table," +
                "        th," +
                "        td {" +
                "            border: 1px solid #ccc;" +
                "        }" +
                "        th," +
                "        td {" +
                "            padding: 10px;" +
                "        }" +
                "    </style>" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <div class=\"m-3\">" +
                "    <h1 class=\"text-center mt-3\">पुणे जिल्हा मध्यवर्ती सहकारी बँक मर्यादित, पुणे</h1>" +
                "    <h1 class=\"text-center\">मुख्य कचेरी : ४ . बी. जे. रोड, पुणे ४११००१.</h1>" +
                "    <h1 class=\"text-center\">punedc@mail.com</h1>" +
                "    <div class=\"text-center\">____________________________________________</div>" +
                "    </div>" +
                "</head>" +
                "<body>" +
                "    <div class=\"m-3\">" +
                "    <div class=\"text-center mt-3\" style=\"text-decoration:underline;\">१०१ नुसार कारवाई करणेपूर्वीची नोटीस </div>" +
                "    <div class=\"text-center mt-1\" style=\"text-decoration:underline;\">रजिस्टर ए.डी./ यु.पी.सी.</div>" +
                "    <div class=\"\">" +
                "        <p>जा.क्र. / २०२३-२४ /</p>" +
                "    </div>" +
                "    <div class=\"d-flex flex-row-reverse p-2\">" +
                "        <div>       " +
                "         <p>दिनांक :- / /२०</p>" +
                "        </div>" +
                "    </div>" +
                "    <div></div>" +
                "    <div>कर्ज खाते क्रमांक :-</div>" +
                "    <div>कर्जदार :- श्री. / श्रीमती:- " +
                courtCase.getNameOfDefaulter() +
                "</div>" +
                "    <div>राहणार :-" +
                courtCase.getAddress() +
                "</div>" +
                "    <div>१) जामीनदार :- श्री. / श्रीमती:-" +
                courtCase.getGaurentorOne() +
                "</div>" +
                "    <div>राहणार :- " +
                courtCase.getGaurentorOneAddress() +
                "</div>" +
                "    <div>२) जामीनदार :- श्री. /श्रीमती:-" +
                courtCase.getGaurentorTwo() +
                "</div>" +
                "    <div>राहणार :- " +
                courtCase.getGaurentorTwoAddress() +
                "</div>" +
                "    <div class=\"mt-3\">" +
                "        <p class=\"text-center\"> विषय: थकीत कर्ज परतफेड करणेबाबत..</p>" +
                "        <p style=\"text-decoration: underline;\"> तुम्हास नोटीस देण्यात येते की,</p>" +
                "        <p>१. तुम्ही बँकेच्या_________शाखेकडून दिनांक / /२० रोजी कर्ज योजने अंतर्गत कारणासाठी रूपये" +
                "            _________/- चे कर्ज द.सा.द.शे....... % व्याजदराने...... वर्षेच्या मुदतीने घेतले आहे." +
                "        </p>" +
                "        <p>" +
                "            २. सदर कर्ज व्यवहारास श्री__________ व श्री____________हे व्यक्ति जामीनदार आहेत." +
                "        </p>" +
                "        <p>" +
                "            ३. मूळ कर्जदार व जामीनदार यांनी बँकेस दिनांक / /२० रोजी रितसर वचन विट्टी, करारनामे" +
                "            व इतर सर्व कागदपत्रे लिहून दिलेली आहेत." +
                "        </p>" +
                "        <p>" +
                "            ४. वचनचिठ्ठी व कारारनाम्यातील तपशीलाप्रमाणे तसेच तुम्ही सदर कर्ज रक्कम मंजूरी पत्रातील अटी व" +
                "            शर्तीनुसार परतफेड करण्यास वैयक्तिक व सामुदायिक जबावदार आहात." +
                "        </p>" +
                "        <p>" +
                "            5.आपण बँकेकडून घेतलेल्या कर्ज रकमेची परतफेड वेळेवर व करारातील अटी व शर्तीप्रमाणे केलेली नाही. म्हणून तुम्हास" +
                "            या पूर्वीही नोटिस पाठविण्यात आल्या होत्या तरीही तुम्ही थकीत कर्जाच्या रकमेची संपूर्ण परतफेड केलेली नाही." +
                "            त्यामुळे महाराष्ट्र सहकारी संस्था अधिनियम १९६० चे कलम (१९०१ (१) अन्वये संबंधित मा. उपनिबंधक / मा. सहाय्यक" +
                "            निबंधक, सहकारी संस्था यांचेकडे अर्ज दाखल करून वसूली दाखला मिळणेबाबतचा अर्ज करणे वावत्तचा निर्णय घेण्यात" +
                "            आलेला आहे. तथापि तुम्हास एक संधी म्हणून सदर प्रकरणी अंतिम नोटिस देण्यात येत आहे." +
                "        </p>" +
                "        <b style=\"text-decoration: underline;\">" +
                "            दिनांक" +
                "            / /२० अखेर तुमचेकडून खालीलप्रमाणे येणे आहे." +
                "        </b>" +
                "        <table>" +
                "            <tbody>" +
                "                <tr>" +
                "                    <th> अनु. क्र.</th>" +
                "                    <th>तपशील</th>" +
                "                    <th>रक्कम रूपये</th>" +
                "                </tr>" +
                "                <tr>" +
                "                    <td>१.</td>" +
                "                    <td>मुद्दल</td>" +
                "                    <td>रूपये.</td>" +
                "               " +
                "                  " +
                "                </tr>" +
                "                <tr>" +
                "                    <td>२.</td>" +
                "                    <td>व्याज येणे + दंड व्याज</td>" +
                "                    <td>रूपये.</td>" +
                "                </tr>" +
                "                <tr>" +
                "                    <td>३.</td>" +
                "                    <td>इतर खर्च - नोटिस इत्यादी</td>" +
                "                    <td>रूपये.</td>" +
                "                </tr>" +
                "                <tr>" +
                "                    <td colspan=\"2\" class=\"text-end\">एकूण:-</td>" +
                "                    <td>रूपये.</td>" +
                "                </tr>" +
                "            </tbody>" +
                "        </table>" +
                "    </div>" +
                "    <div class=\"mt-3\">" +
                "     <p>तरी तुम्हास या नोटिशीने कळविण्यात येत की, ही नोटीस मिळाल्यापासून अठ दिवसांच्या आत वर नमूद केल्याप्रमाणे रक्कम भरावी/सदर मुदतीत रक्कम न भरल्यास आपले विरूद्ध पुढील कायदेशीर कारवाई करण्यात येईल व त्याच्या खर्चाची व परिणामाची संपूर्ण जवाबदारी तुमचेवर राहील, याची नोंद घ्यावी.</p>" +
                "    </div>" +
                "    <div class=\"d-flex flex-row-reverse p-2\">" +
                "        <div>       " +
                "         <p>शाखा व्यवस्थापक / वसुली अधिकारी / विकास अधिकारी</p>" +
                "           <span>विभाग :-</span>" +
                "            <span class=\"m-5\">द्वारा :-</span>" +
                "            <span>शाखा,</span>" +
                "        " +
                "         <p>पुणे जिल्हा मध्यवर्ती सहकारी बँक मर्या., पुणे</p>" +
                "        </div>" +
                "    </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

            one01NamunaList.add(one01Namuna);
        }
        return one01NamunaList;
    }
    //-----------------------------------------------pdf-------------------------------------------

    //    @GetMapping("/pdf-demo")
    //    public ResponseEntity<byte[]> pdfDemo()
    //    {
    //
    //    	Optional<CourtCase> findById = courtCaseRepository.findById(1l);
    //
    //         DateFormat Date = DateFormat.getDateInstance();
    //         Calendar cals = Calendar.getInstance();
    //         String currentDate = Date.format(cals.getTime());
    //         System.out.println("Formatted Date: " + currentDate);
    //
    //         		String HTML="जिल्हा";
    //             try {
    //                 // IndicLigaturizer g = new DevanagariLigaturizer();
    //                 // String processed = g.process(HTML);
    //
    //                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //
    //                 // Create ConverterProperties and set the font provider
    //                 ConverterProperties converterProperties = new ConverterProperties();
    //
    //                 FontProvider fontProvider = new FontProvider();
    //
    //					fontProvider.addFont("C:\\Users\\swapnilp\\Desktop\\Noto_Sans\\NotoSans-Regular.ttf",
    //							PdfEncodings.IDENTITY_H);
    //
    //					//String REGULAR = "C:\\\\Users\\\\swapnilp\\\\Desktop\\\\Noto_Sans\\\\NotoSans-Regular.ttf";
    //
    //
    //                 converterProperties.setFontProvider(fontProvider);
    //                 converterProperties.setCharset("UTF-8");
    //
    //                 HtmlConverter.convertToPdf(findById.get().getNameOfDefaulter(), byteArrayOutputStream, converterProperties);
    //
    //                 HttpHeaders headers = new HttpHeaders();
    //                 headers.add("Content-Type", "application/pdf");
    //                 headers.add("content-disposition", "attachment; filename=" + "certificate.pdf");
    //                 headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    //                 ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    //
    //                 return response;
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //			return null;
    //
    //
    //    }

}
