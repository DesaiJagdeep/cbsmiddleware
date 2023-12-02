package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.domain.TalukaMaster;
import com.cbs.middleware.domain.domainUtil.BranchForPacksList;
import com.cbs.middleware.domain.domainUtil.PacsNameAndCode;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.BankMasterRepository;
import com.cbs.middleware.repository.NotificationRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.repository.TalukaMasterRepository;
import com.cbs.middleware.service.BankBranchMasterService;
import com.cbs.middleware.web.rest.errors.BadRequestAlertException;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.errors.UnAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.NotificationDataUtility;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;
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
import java.util.stream.Collectors;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * REST controller for managing
 * {@link com.cbs.middleware.domain.BankBranchMaster}.
 */
@RestController
@RequestMapping("/api")
public class BankBranchMasterResource {

    private final Logger log = LoggerFactory.getLogger(BankBranchMasterResource.class);

    private static final String ENTITY_NAME = "bankBranchMaster";

    @Autowired
    BankMasterRepository bankMasterRepository;

    @Autowired
    PacsMasterRepository pacsMasterRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationDataUtility notificationDataUtility;

    @Autowired
    TalukaMasterRepository talukaMasterRepository;

    @Autowired
    TranslationServiceUtility translationServiceUtility;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankBranchMasterService bankBranchMasterService;

    private final BankBranchMasterRepository bankBranchMasterRepository;

    public BankBranchMasterResource(
        BankBranchMasterService bankBranchMasterService,
        BankBranchMasterRepository bankBranchMasterRepository
    ) {
        this.bankBranchMasterService = bankBranchMasterService;
        this.bankBranchMasterRepository = bankBranchMasterRepository;
    }

    /**
     * {@code POST  /bank-branch-masters} : Create a new bankBranchMaster.
     *
     * @param bankBranchMaster the bankBranchMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new bankBranchMaster, or with status
     *         {@code 400 (Bad Request)} if the bankBranchMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-branch-masters")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankBranchMaster> createBankBranchMaster(@RequestBody BankBranchMaster bankBranchMaster)
        throws URISyntaxException {
        log.debug("REST request to save BankBranchMaster : {}", bankBranchMaster);
        if (bankBranchMaster.getId() != null) {
            throw new BadRequestAlertException("A new bankBranchMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankBranchMaster result = bankBranchMasterService.save(bankBranchMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Bank Branch Master Created",
                    "Bank Branch Master: " + result.getBranchName() + " Created",
                    false,
                    result.getCreatedDate(),
                    "AccountHolderMasterUpdated" //type
                );
            } catch (Exception e) {}
        }

        return ResponseEntity
            .created(new URI("/api/bank-branch-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

//    @GetMapping("/add-branch-user-list")
//    public List<BankBranchMaster> addBranchUserList() {
//        List<String> branchNameList = new ArrayList<>();
//        branchNameList.add("Adiware");
//        branchNameList.add("Avsari Bk");
//        branchNameList.add("Chas (Ambegaon)");
//        branchNameList.add("Dimbhe Khurd");
//        branchNameList.add("Ghodegaon");
//        branchNameList.add("Kalamb (Ambegaon)");
//        branchNameList.add("Loni Dhamani");
//        branchNameList.add("Manchar");
//        branchNameList.add("Mhalunge Padval");
//        branchNameList.add("Nirgudsar");
//        branchNameList.add("Pargoan T Avsari Bk");
//        branchNameList.add("Peth");
//        branchNameList.add("Pimpalgaon Khadaki");
//        branchNameList.add("Ranjani");
//        branchNameList.add("Shingave");
//        branchNameList.add("Shinoli");
//        branchNameList.add("Taleghar");
//        branchNameList.add("Baramati");
//        branchNameList.add("Baramati Bhigwan Road");
//        branchNameList.add("Bhikobanagar");
//        branchNameList.add("Deulgaon Rasal");
//        branchNameList.add("Dorlewadi");
//        branchNameList.add("Hol");
//        branchNameList.add("Karhati");
//        branchNameList.add("Kasaba Baramati");
//        branchNameList.add("Katyachi Wadi");
//        branchNameList.add("Khandaj");
//        branchNameList.add("Korhale Bk");
//        branchNameList.add("Late");
//        branchNameList.add("Loni Bhapkar");
//        branchNameList.add("Malegaon Bk");
//        branchNameList.add("Market Comm. Baramati");
//        branchNameList.add("Mekhali");
//        branchNameList.add("Midc Baramati");
//        branchNameList.add("Morgaon");
//        branchNameList.add("Murti");
//        branchNameList.add("Murum");
//        branchNameList.add("Nimbut");
//        branchNameList.add("Niravagaj");
//        branchNameList.add("Pandare");
//        branchNameList.add("Parawadi");
//        branchNameList.add("Sangavi Baramati");
//        branchNameList.add("Shivnagar");
//        branchNameList.add("Someshwar Nagar");
//        branchNameList.add("Songaon");
//        branchNameList.add("Supe");
//        branchNameList.add("Undwadi (Supe)");
//        branchNameList.add("Vadgaon Nimbalkar");
//        branchNameList.add("Ambavade");
//        branchNameList.add("Apti");
//        branchNameList.add("Bhor No.2");
//        branchNameList.add("Brahmanghar");
//        branchNameList.add("Kapurhol");
//        branchNameList.add("Kikavi");
//        branchNameList.add("Nasarapur");
//        branchNameList.add("Nere");
//        branchNameList.add("Nhavi");
//        branchNameList.add("Nigade");
//        branchNameList.add("Shivare");
//        branchNameList.add("Alegaon (Duand Sugar)");
//        branchNameList.add("Bhandgaon");
//        branchNameList.add("Bori Bhadak");
//        branchNameList.add("Dahitane");
//        branchNameList.add("Daund");
//        branchNameList.add("Delwadi");
//        branchNameList.add("Deoolgaon Raje");
//        branchNameList.add("Dhaygudewadi");
//        branchNameList.add("Gopalwadi");
//        branchNameList.add("Kadethan");
//        branchNameList.add("Kangaon");
//        branchNameList.add("Karkhana Patas");
//        branchNameList.add("Kedgaon");
//        branchNameList.add("Khamgaon");
//        branchNameList.add("Khutbav");
//        branchNameList.add("Kurkumbh");
//        branchNameList.add("Malthan (Daund)");
//        branchNameList.add("Nangaon");
//        branchNameList.add("Pargaon Salu Malu");
//        branchNameList.add("Patas");
//        branchNameList.add("Pimpalgaon (Daund)");
//        branchNameList.add("Rahu");
//        branchNameList.add("Rajegaon");
//        branchNameList.add("Ravangaon");
//        branchNameList.add("Sonde Sarpale");
//        branchNameList.add("Vadgaon Bande");
//        branchNameList.add("Valaki");
//        branchNameList.add("Warvand");
//        branchNameList.add("Yawat");
//        branchNameList.add("Ashtapur");
//        branchNameList.add("Aundh");
//        branchNameList.add("Charholi Bk");
//        branchNameList.add("Chikhali");
//        branchNameList.add("Dehugaon");
//        branchNameList.add("Dehuroad");
//        branchNameList.add("Hadapsar");
//        branchNameList.add("KESNAND");
//        branchNameList.add("Khadakwasla");
//        branchNameList.add("Khanapur");
//        branchNameList.add("Khed Shivapur");
//        branchNameList.add("Kunjirwadi");
//        branchNameList.add("Lohgaon");
//        branchNameList.add("Loni Kalbhor");
//        branchNameList.add("Manjari Bk");
//        branchNameList.add("Moshi");
//        branchNameList.add("Mundhwa");
//        branchNameList.add("Perane");
//        branchNameList.add("Theur");
//        branchNameList.add("Uruli Kanchan");
//        branchNameList.add("Uttamnagar");
//        branchNameList.add("Vadgaon Dhayari");
//        branchNameList.add("Vadgaon Maval");
//        branchNameList.add("Wagholi");
//        branchNameList.add("AKOLE");
//        branchNameList.add("Anthurne");
//        branchNameList.add("Bavada");
//        branchNameList.add("Bhavaninagar");
//        branchNameList.add("Bhigvan");
//        branchNameList.add("Bhodani");
//        branchNameList.add("Bijwadi");
//        branchNameList.add("Bori Indapur");
//        branchNameList.add("Gholapwadi");
//        branchNameList.add("Indapur");
//        branchNameList.add("Indapur Old Pune Solapur Highway");
//        branchNameList.add("Kalamb (W.Nagar)");
//        branchNameList.add("Kalas");
//        branchNameList.add("Kalthan");
//        branchNameList.add("KATI");
//        branchNameList.add("Kurwali");
//        branchNameList.add("Lasurne");
//        branchNameList.add("Loni Devkar");
//        branchNameList.add("Market Comiti Indapur");
//        branchNameList.add("Nimgaon Ketaki");
//        branchNameList.add("Nimsakhar");
//        branchNameList.add("Palasdev");
//        branchNameList.add("Pimpari (Indapur)");
//        branchNameList.add("Rednee");
//        branchNameList.add("Sansar");
//        branchNameList.add("Sarati");
//        branchNameList.add("Shelgaon");
//        branchNameList.add("Shetphalgade");
//        branchNameList.add("Varkute Bk");
//        branchNameList.add("Wadapuri");
//        branchNameList.add("Aane");
//        branchNameList.add("Alephata");
//        branchNameList.add("Amrapur");
//        branchNameList.add("Aptale");
//        branchNameList.add("Belhe");
//        branchNameList.add("Bori Bk");
//        branchNameList.add("Dingore");
//        branchNameList.add("Junnar");
//        branchNameList.add("Junnar Market Committi");
//        branchNameList.add("Khodad");
//        branchNameList.add("Madh");
//        branchNameList.add("Narayangaon");
//        branchNameList.add("Otur");
//        branchNameList.add("Ozar");
//        branchNameList.add("Pargaon Mangrul");
//        branchNameList.add("Pimpalwandi");
//        branchNameList.add("Rajuri");
//        branchNameList.add("Savargaon");
//        branchNameList.add("Umbraj");
//        branchNameList.add("Vadgaon Kandali");
//        branchNameList.add("Alandi Devachi");
//        branchNameList.add("Amboli");
//        branchNameList.add("Bahul");
//        branchNameList.add("Bhose");
//        branchNameList.add("Chakan");
//        branchNameList.add("Chakan Talegaon Road");
//        branchNameList.add("Chas (Khed)");
//        branchNameList.add("Davadi");
//        branchNameList.add("Dehane");
//        branchNameList.add("Kadus");
//        branchNameList.add("Khed");
//        branchNameList.add("khed Pabal Road");
//        branchNameList.add("Kurkundi");
//        branchNameList.add("Markal");
//        branchNameList.add("Pait");
//        branchNameList.add("Shive");
//        branchNameList.add("Vafgaon");
//        branchNameList.add("Wada");
//        branchNameList.add("Bebad Ohol");
//        branchNameList.add("Indouri");
//        branchNameList.add("Kale Colony");
//        branchNameList.add("Kamshet");
//        branchNameList.add("Kanhe");
//        branchNameList.add("Karla");
//        branchNameList.add("Lonavala");
//        branchNameList.add("Navlakh Umbre");
//        branchNameList.add("Somatane Phata");
//        branchNameList.add("Takave Bk");
//        branchNameList.add("Talegaon Dabhade");
//        branchNameList.add("Bhugaon");
//        branchNameList.add("Ghotwade");
//        branchNameList.add("Hinjawadi");
//        branchNameList.add("Kolvan");
//        branchNameList.add("Male");
//        branchNameList.add("Mutha");
//        branchNameList.add("Paud");
//        branchNameList.add("Pirangut");
//        branchNameList.add("Punawale");
//        branchNameList.add("Wakad");
//        branchNameList.add("Khadaki");
//        branchNameList.add("Chambali");
//        branchNameList.add("Dhankavadi (Purandhar)");
//        branchNameList.add("Javalarjun");
//        branchNameList.add("Jejuri");
//        branchNameList.add("Mandaki");
//        branchNameList.add("NARAYANPUR");
//        branchNameList.add("Naygaon");
//        branchNameList.add("Nira");
//        branchNameList.add("Parinche");
//        branchNameList.add("Rajewadi");
//        branchNameList.add("Saswad");
//        branchNameList.add("Shivari Stand");
//        branchNameList.add("Valha");
//        branchNameList.add("Dhamari");
//        branchNameList.add("Ghodnadi");
//        branchNameList.add("Inamgaon");
//        branchNameList.add("Jambut");
//        branchNameList.add("Kanhur Masai");
//        branchNameList.add("Karandi");
//        branchNameList.add("Karegaon");
//        branchNameList.add("Kawathe");
//        branchNameList.add("Kendur");
//        branchNameList.add("Koregaon Bhima");
//        branchNameList.add("Malthan (Shirur)");
//        branchNameList.add("Mandavgan Pharata");
//        branchNameList.add("Mukhai");
//        branchNameList.add("Nhavare");
//        branchNameList.add("Nimgaon Mhalungi");
//        branchNameList.add("Nimone");
//        branchNameList.add("Nirvi");
//        branchNameList.add("Pabal");
//        branchNameList.add("Ranjangaon Ganpati");
//        branchNameList.add("Ranjangaon Sandas");
//        branchNameList.add("Sanaswadi");
//        branchNameList.add("Shikrapur");
//        branchNameList.add("Takali Haji");
//        branchNameList.add("Talegaon Dhamdhere");
//        branchNameList.add("Tandali");
//        branchNameList.add("Vadgaon Rasai");
//        branchNameList.add("Vitthalwadi");
//        branchNameList.add("Ambavane");
//        branchNameList.add("Panshet");
//        branchNameList.add("Ranjane");
//        branchNameList.add("Sakhar");
//        branchNameList.add("Velhe");
//        branchNameList.add("Vinzhar");
//
//        List<String> branchCodeList = new ArrayList<>();
//        branchCodeList.add("148937");
//        branchCodeList.add("148951");
//        branchCodeList.add("148952");
//        branchCodeList.add("148994");
//        branchCodeList.add("148997");
//        branchCodeList.add("149015");
//        branchCodeList.add("149060");
//        branchCodeList.add("149067");
//        branchCodeList.add("149075");
//        branchCodeList.add("149101");
//        branchCodeList.add("149112");
//        branchCodeList.add("149119");
//        branchCodeList.add("149122");
//        branchCodeList.add("149136");
//        branchCodeList.add("149152");
//        branchCodeList.add("149153");
//        branchCodeList.add("149168");
//        branchCodeList.add("148955");
//        branchCodeList.add("161193");
//        branchCodeList.add("148962");
//        branchCodeList.add("169464");
//        branchCodeList.add("148995");
//        branchCodeList.add("149006");
//        branchCodeList.add("149026");
//        branchCodeList.add("149028");
//        branchCodeList.add("149032");
//        branchCodeList.add("149041");
//        branchCodeList.add("149050");
//        branchCodeList.add("161188");
//        branchCodeList.add("149058");
//        branchCodeList.add("149065");
//        branchCodeList.add("149073");
//        branchCodeList.add("149074");
//        branchCodeList.add("149076");
//        branchCodeList.add("149077");
//        branchCodeList.add("149081");
//        branchCodeList.add("149082");
//        branchCodeList.add("149094");
//        branchCodeList.add("149100");
//        branchCodeList.add("149108");
//        branchCodeList.add("154128");
//        branchCodeList.add("149142");
//        branchCodeList.add("149157");
//        branchCodeList.add("149161");
//        branchCodeList.add("161192");
//        branchCodeList.add("149163");
//        branchCodeList.add("149174");
//        branchCodeList.add("149181");
//        branchCodeList.add("148943");
//        branchCodeList.add("148948");
//        branchCodeList.add("148965");
//        branchCodeList.add("159538");
//        branchCodeList.add("149023");
//        branchCodeList.add("149046");
//        branchCodeList.add("149087");
//        branchCodeList.add("149089");
//        branchCodeList.add("149091");
//        branchCodeList.add("149092");
//        branchCodeList.add("149155");
//        branchCodeList.add("148941");
//        branchCodeList.add("161186");
//        branchCodeList.add("161187");
//        branchCodeList.add("148981");
//        branchCodeList.add("148983");
//        branchCodeList.add("148989");
//        branchCodeList.add("148990");
//        branchCodeList.add("160688");
//        branchCodeList.add("161185");
//        branchCodeList.add("149013");
//        branchCodeList.add("149021");
//        branchCodeList.add("149027");
//        branchCodeList.add("149034");
//        branchCodeList.add("149039");
//        branchCodeList.add("149045");
//        branchCodeList.add("149052");
//        branchCodeList.add("149066");
//        branchCodeList.add("149084");
//        branchCodeList.add("149111");
//        branchCodeList.add("149115");
//        branchCodeList.add("149121");
//        branchCodeList.add("149129");
//        branchCodeList.add("149130");
//        branchCodeList.add("149137");
//        branchCodeList.add("149177");
//        branchCodeList.add("195877");
//        branchCodeList.add("149184");
//        branchCodeList.add("149192");
//        branchCodeList.add("149193");
//        branchCodeList.add("148949");
//        branchCodeList.add("148950");
//        branchCodeList.add("148976");
//        branchCodeList.add("148979");
//        branchCodeList.add("148987");
//        branchCodeList.add("148988");
//        branchCodeList.add("149004");
//        branchCodeList.add("161177");
//        branchCodeList.add("149038");
//        branchCodeList.add("149040");
//        branchCodeList.add("149043");
//        branchCodeList.add("161180");
//        branchCodeList.add("161182");
//        branchCodeList.add("149061");
//        branchCodeList.add("149070");
//        branchCodeList.add("149078");
//        branchCodeList.add("149080");
//        branchCodeList.add("149118");
//        branchCodeList.add("149171");
//        branchCodeList.add("149175");
//        branchCodeList.add("149176");
//        branchCodeList.add("149178");
//        branchCodeList.add("149180");
//        branchCodeList.add("149189");
//        branchCodeList.add("161184");
//        branchCodeList.add("148947");
//        branchCodeList.add("148956");
//        branchCodeList.add("148960");
//        branchCodeList.add("148961");
//        branchCodeList.add("148963");
//        branchCodeList.add("148970");
//        branchCodeList.add("148972");
//        branchCodeList.add("149000");
//        branchCodeList.add("149008");
//        branchCodeList.add("161194");
//        branchCodeList.add("149016");
//        branchCodeList.add("149017");
//        branchCodeList.add("149019");
//        branchCodeList.add("161176");
//        branchCodeList.add("149054");
//        branchCodeList.add("149055");
//        branchCodeList.add("149059");
//        branchCodeList.add("149072");
//        branchCodeList.add("149095");
//        branchCodeList.add("149098");
//        branchCodeList.add("149107");
//        branchCodeList.add("149124");
//        branchCodeList.add("149138");
//        branchCodeList.add("149144");
//        branchCodeList.add("149145");
//        branchCodeList.add("149149");
//        branchCodeList.add("149150");
//        branchCodeList.add("149186");
//        branchCodeList.add("169466");
//        branchCodeList.add("148936");
//        branchCodeList.add("148942");
//        branchCodeList.add("148946");
//        branchCodeList.add("161178");
//        branchCodeList.add("148958");
//        branchCodeList.add("148971");
//        branchCodeList.add("161195");
//        branchCodeList.add("149012");
//        branchCodeList.add("161196");
//        branchCodeList.add("149044");
//        branchCodeList.add("149062");
//        branchCodeList.add("149086");
//        branchCodeList.add("149103");
//        branchCodeList.add("149104");
//        branchCodeList.add("149110");
//        branchCodeList.add("149123");
//        branchCodeList.add("149132");
//        branchCodeList.add("149147");
//        branchCodeList.add("149173");
//        branchCodeList.add("149179");
//        branchCodeList.add("148940");
//        branchCodeList.add("148945");
//        branchCodeList.add("148954");
//        branchCodeList.add("148967");
//        branchCodeList.add("148973");
//        branchCodeList.add("161190");
//        branchCodeList.add("148978");
//        branchCodeList.add("148984");
//        branchCodeList.add("148986");
//        branchCodeList.add("149014");
//        branchCodeList.add("149042");
//        branchCodeList.add("161189");
//        branchCodeList.add("149053");
//        branchCodeList.add("149071");
//        branchCodeList.add("149106");
//        branchCodeList.add("159546");
//        branchCodeList.add("149183");
//        branchCodeList.add("149188");
//        branchCodeList.add("148957");
//        branchCodeList.add("149009");
//        branchCodeList.add("149018");
//        branchCodeList.add("149020");
//        branchCodeList.add("159544");
//        branchCodeList.add("159541");
//        branchCodeList.add("149057");
//        branchCodeList.add("159539");
//        branchCodeList.add("149160");
//        branchCodeList.add("149165");
//        branchCodeList.add("149166");
//        branchCodeList.add("148968");
//        branchCodeList.add("149001");
//        branchCodeList.add("149005");
//        branchCodeList.add("149047");
//        branchCodeList.add("149064");
//        branchCodeList.add("149083");
//        branchCodeList.add("149116");
//        branchCodeList.add("149127");
//        branchCodeList.add("161183");
//        branchCodeList.add("149190");
//        branchCodeList.add("149036");
//        branchCodeList.add("148974");
//        branchCodeList.add("148993");
//        branchCodeList.add("161191");
//        branchCodeList.add("149011");
//        branchCodeList.add("149068");
//        branchCodeList.add("161181");
//        branchCodeList.add("149088");
//        branchCodeList.add("149099");
//        branchCodeList.add("149113");
//        branchCodeList.add("149131");
//        branchCodeList.add("149146");
//        branchCodeList.add("149156");
//        branchCodeList.add("149185");
//        branchCodeList.add("148991");
//        branchCodeList.add("148999");
//        branchCodeList.add("149007");
//        branchCodeList.add("149010");
//        branchCodeList.add("149022");
//        branchCodeList.add("149024");
//        branchCodeList.add("149025");
//        branchCodeList.add("149033");
//        branchCodeList.add("149035");
//        branchCodeList.add("149049");
//        branchCodeList.add("159790");
//        branchCodeList.add("149069");
//        branchCodeList.add("149079");
//        branchCodeList.add("149090");
//        branchCodeList.add("149096");
//        branchCodeList.add("149097");
//        branchCodeList.add("149102");
//        branchCodeList.add("149105");
//        branchCodeList.add("149134");
//        branchCodeList.add("149135");
//        branchCodeList.add("149141");
//        branchCodeList.add("149151");
//        branchCodeList.add("149164");
//        branchCodeList.add("149167");
//        branchCodeList.add("149169");
//        branchCodeList.add("149182");
//        branchCodeList.add("161179");
//        branchCodeList.add("148944");
//        branchCodeList.add("149109");
//        branchCodeList.add("149133");
//        branchCodeList.add("149140");
//        branchCodeList.add("149187");
//        branchCodeList.add("167916");
//
//        List<BankBranchMaster> bankBranchMasterList = new ArrayList<>();
//        BankMaster bankMaster = new BankMaster();
//        bankMaster.setId(1l);
//
//        for (int i = 0; i < branchCodeList.size(); i++) {
//            BankBranchMaster bankBranchMaster = new BankBranchMaster();
//            bankBranchMaster.setBranchName(branchNameList.get(i));
//            bankBranchMaster.setSchemeWiseBranchCode(branchCodeList.get(i));
//
//            bankBranchMaster.setBankMaster(bankMaster);
//
//            bankBranchMasterRepository.save(bankBranchMaster);
//        }
//
//        return bankBranchMasterList;
//    }

    /**
     * {@code PUT  /bank-branch-masters/:id} : Updates an existing bankBranchMaster.
     *
     * @param id               the id of the bankBranchMaster to save.
     * @param bankBranchMaster the bankBranchMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated bankBranchMaster, or with status
     *         {@code 400 (Bad Request)} if the bankBranchMaster is not valid, or
     *         with status {@code 500 (Internal Server Error)} if the
     *         bankBranchMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-branch-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankBranchMaster> updateBankBranchMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankBranchMaster bankBranchMaster
    ) throws URISyntaxException {
        log.debug("REST request to update BankBranchMaster : {}, {}", id, bankBranchMaster);
        if (bankBranchMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankBranchMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankBranchMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankBranchMaster result = bankBranchMasterService.update(bankBranchMaster);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Bank Branch Master Updated",
                    "Bank Branch Master: " + result.getBranchName() + " Updated",
                    false,
                    result.getCreatedDate(),
                    "AccountHolderMasterUpdated" //type
                );
            } catch (Exception e) {}
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankBranchMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-branch-masters/:id} : Partial updates given fields of an
     * existing bankBranchMaster, field will ignore if it is null
     *
     * @param id               the id of the bankBranchMaster to save.
     * @param bankBranchMaster the bankBranchMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated bankBranchMaster, or with status
     *         {@code 400 (Bad Request)} if the bankBranchMaster is not valid, or
     *         with status {@code 404 (Not Found)} if the bankBranchMaster is not
     *         found, or with status {@code 500 (Internal Server Error)} if the
     *         bankBranchMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-branch-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_UPDATE','EDIT')")
    public ResponseEntity<BankBranchMaster> partialUpdateBankBranchMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankBranchMaster bankBranchMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankBranchMaster partially : {}, {}", id, bankBranchMaster);
        if (bankBranchMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankBranchMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankBranchMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankBranchMaster> result = bankBranchMasterService.partialUpdate(bankBranchMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankBranchMaster.getId().toString())
        );
    }


    /**
     *
     *
     * */


    @GetMapping("/taluka-by-branch-name")
    public ResponseEntity<TalukaMaster> getTalukaBankBranchMasters(@RequestParam(value = "branch")String branch) {
        log.debug("REST request to get a page of BankBranchMasters");

        BankBranchMaster bankBranchMaster= bankBranchMasterRepository.findOneByBranchName(branch);

        return ResponseEntity.ok().body(bankBranchMaster.getTalukaMaster());
    }

    /**
     * {@code GET  /bank-branch-masters} : get all the bankBranchMasters.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of bankBranchMasters in body.
     */
    @GetMapping("/bank-branch-masters")
    public ResponseEntity<List<BankBranchMaster>> getAllBankBranchMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of BankBranchMasters");
        Page<BankBranchMaster> page;
        if (eagerload) {
            page = bankBranchMasterService.findAllWithEagerRelationships(pageable);
        } else {
            page = bankBranchMasterService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/bank-branch-masters-wt-pacs")
    public ResponseEntity<List<BranchForPacksList>> getAllBankBranchMastersWithPacks(@RequestParam(value = "bankName") String bankName) {
        log.debug("REST request to get a page of BankBranchMasters");

        Optional<BankMaster> findOneByBankName = bankMasterRepository.findOneByBankName(bankName);

        if (!findOneByBankName.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        List<BranchForPacksList> branchForPacksListList = new ArrayList<>();
        List<BankBranchMaster> branchForPacksList = bankBranchMasterRepository.findAllByBankMaster(findOneByBankName.get());
        if (!branchForPacksList.isEmpty()) {
            for (BankBranchMaster bankBranchMaster : branchForPacksList) {
                BranchForPacksList branchForPacksListObj = new BranchForPacksList();
                branchForPacksListObj.setBranchName(bankBranchMaster.getBranchName());
                List<PacsMaster> pacsMasterMaster = pacsMasterRepository.findAllByBankBranchMaster(bankBranchMaster);
                //branchForPacksListObj.setPacksNameList(pacsMasterMaster.stream().map(PacsMaster::getPacsName).collect(Collectors.toList()));

                branchForPacksListObj.setPacksNameList(
                    pacsMasterMaster.stream()
                        .map(pacsMaster -> new PacsNameAndCode(pacsMaster.getPacsName(), pacsMaster.getPacsNumber()))
                        .collect(Collectors.toList())
                );

                branchForPacksListList.add(branchForPacksListObj);
            }

            return ResponseEntity.ok().body(branchForPacksListList);
        } else {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
    }

    /**
     * {@code GET  /bank-branch-masters/:id} : get the "id" bankBranchMaster.
     *
     * @param id the id of the bankBranchMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the bankBranchMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-branch-masters/{id}")
    public ResponseEntity<BankBranchMaster> getBankBranchMaster(@PathVariable Long id) {
        log.debug("REST request to get BankBranchMaster : {}", id);
        Optional<BankBranchMaster> bankBranchMaster = bankBranchMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankBranchMaster);
    }

    /**
     * {@code DELETE  /bank-branch-masters/:id} : delete the "id" bankBranchMaster.
     *
     * @param id the id of the bankBranchMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-branch-masters/{id}")
    @PreAuthorize("@authentication.onDatabaseRecordPermission('MASTER_RECORD_DELETE','DELETE')")
    public ResponseEntity<Void> deleteBankBranchMaster(@PathVariable Long id) {
        log.debug("REST request to delete BankBranchMaster : {}", id);

        Optional<BankBranchMaster> bankBranchMaster = bankBranchMasterService.findOne(id);
        if (!bankBranchMaster.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankBranchMaster result = bankBranchMaster.get();
        bankBranchMasterService.delete(id);

        if (result != null) {
            try {
                notificationDataUtility.notificationData(
                    "Bank Branch Master Created",
                    "Bank Branch Master: " + result.getBranchName() + "deleted",
                    false,
                    result.getCreatedDate(),
                    "AccountHolderMasterDeleted" //type
                );
            } catch (Exception e) {}
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/branch-file-upload")
    public ResponseEntity<List<BankBranchMaster>> createBranchFile(
        @RequestParam("file") MultipartFile files,
        RedirectAttributes redirectAttributes
    ) throws Exception {
        String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());

        if (!"xlsx".equalsIgnoreCase(fileExtension)) {
            throw new BadRequestAlertException("Invalid file type", ENTITY_NAME, "fileInvalid");
        }

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            Row row = sheet.getRow(0); // Get the current row
            boolean flagForLabel = false;
            String talukacode = getCellValue(row.getCell(0));

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + talukacode);

            if (StringUtils.isNoneBlank(talukacode)) {
                talukacode = talukacode.trim().replace("\n", " ").toLowerCase();
                if (!talukacode.contains("talukacode")) {
                    flagForLabel = true;
                }
            } else {
                flagForLabel = true;
            }

            if (flagForLabel) {
                throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
            }
        } catch (BadRequestAlertException e) {
            e.printStackTrace();
            throw new BadRequestAlertException("Invalid file Or File have extra non data column", ENTITY_NAME, "fileInvalid");
        } catch (ForbiddenAuthRequestAlertException e) {
            throw new ForbiddenAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (UnAuthRequestAlertException e) {
            throw new UnAuthRequestAlertException("Access is denied", ENTITY_NAME, "unAuthorized");
        } catch (Exception e) {
            throw new Exception("Exception", e);
        }

        File originalFileDir = new File(Constants.KMP_FILE_PATH);
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

        int startRowIndex = 1; // Starting row index
        List<BankBranchMaster> bankBranchMasterUploadList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet
            int lastRowIndex = sheet.getLastRowNum();
            for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the current row
                BankBranchMaster bankBranchMaster = new BankBranchMaster();
                if (row != null) {
                    if (StringUtils.isBlank(getCellValue(row.getCell(0))) && StringUtils.isBlank(getCellValue(row.getCell(1)))) {
                        break;
                    }

                    String branchName = getCellValue(row.getCell(3));

                    if (StringUtils.isNotBlank(branchName) && !bankBranchMasterRepository.existsByBranchName(branchName)) {
                        String talukaName = getCellValue(row.getCell(1));
                        TalukaMaster talukaMaster = talukaMasterRepository.findOneByTalukaName(talukaName);

                        bankBranchMaster.setTalukaMaster(talukaMaster);

                        BankMaster bankMaster = new BankMaster();
                        bankMaster.setId(1l);
                        bankBranchMaster.setBankMaster(bankMaster);

                        // english
                        bankBranchMaster.setBranchName(branchName);

                        // marathi
                        bankBranchMaster.setBranchNameMr(translationServiceUtility.translationText(branchName));

                        String branchCode = getCellValue(row.getCell(2));

                        if (StringUtils.isNotBlank(branchCode)) {
                            // english
                            bankBranchMaster.setBranchCode(branchCode);
                        }

                        String schemeWiseBranchCode = getCellValue(row.getCell(5));

                        if (StringUtils.isNotBlank(schemeWiseBranchCode)) {
                            // english
                            bankBranchMaster.setSchemeWiseBranchCode(schemeWiseBranchCode);
                        }

                        String branchAddress = getCellValue(row.getCell(6));

                        if (StringUtils.isNotBlank(branchAddress)) {
                            // english
                            bankBranchMaster.setBranchAddress(branchAddress);

                            // marathi
                            bankBranchMaster.setBranchAddressMr(translationServiceUtility.translationText(branchAddress));
                        }

                        String pincode = getCellValue(row.getCell(7));

                        if (StringUtils.isNotBlank(pincode)) {
                            // english
                            bankBranchMaster.setPincode(pincode);
                        }
                        bankBranchMaster = bankBranchMasterRepository.save(bankBranchMaster);
                    }

                    bankBranchMasterUploadList.add(bankBranchMaster);
                }
            }

            if (!bankBranchMasterUploadList.isEmpty()) {
                if (bankBranchMasterUploadList.get(0) != null) {
                    try {
                        notificationDataUtility.notificationData(
                            "taluka master file uploaded",
                            "taluka master file : " + files.getOriginalFilename() + " uploaded",
                            false,
                            bankBranchMasterUploadList.get(0).getCreatedDate(),
                            "talukaMasterFileUploaded"
                        );
                    } catch (Exception e) {}
                }

                return ResponseEntity.ok().body(bankBranchMasterUploadList);
            } else {
                throw new BadRequestAlertException("File is already parsed", ENTITY_NAME, "FileExist");
            }
        } catch (IOException e) {
            throw new BadRequestAlertException("File have extra non data column", ENTITY_NAME, "nullColumn");
        }
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
