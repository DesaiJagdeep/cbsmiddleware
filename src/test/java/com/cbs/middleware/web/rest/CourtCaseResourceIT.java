package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CourtCaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CourtCaseResourceIT {

    private static final Long DEFAULT_CODE = 1L;
    private static final Long UPDATED_CODE = 2L;
    private static final Long SMALLER_CODE = 1L - 1L;

    private static final Instant DEFAULT_CASE_DINANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CASE_DINANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TALUKA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TALUKA_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_TALUKA_CODE = 1L;
    private static final Long UPDATED_TALUKA_CODE = 2L;
    private static final Long SMALLER_TALUKA_CODE = 1L - 1L;

    private static final String DEFAULT_SABASAD_SAVING_ACC_NO = "AAAAAAAAAA";
    private static final String UPDATED_SABASAD_SAVING_ACC_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SABASAD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SABASAD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SABASAD_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SABASAD_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_KARJ_PRAKAR = "AAAAAAAAAA";
    private static final String UPDATED_KARJ_PRAKAR = "BBBBBBBBBB";

    private static final String DEFAULT_VASULI_ADHIKARI = "AAAAAAAAAA";
    private static final String UPDATED_VASULI_ADHIKARI = "BBBBBBBBBB";

    private static final Double DEFAULT_EKUN_JAMA = 1D;
    private static final Double UPDATED_EKUN_JAMA = 2D;
    private static final Double SMALLER_EKUN_JAMA = 1D - 1D;

    private static final Double DEFAULT_BAKI = 1D;
    private static final Double UPDATED_BAKI = 2D;
    private static final Double SMALLER_BAKI = 1D - 1D;

    private static final String DEFAULT_AR_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_AR_OFFICE = "BBBBBBBBBB";

    private static final Double DEFAULT_EKUN_VYAJ = 1D;
    private static final Double UPDATED_EKUN_VYAJ = 2D;
    private static final Double SMALLER_EKUN_VYAJ = 1D - 1D;

    private static final Double DEFAULT_JAMA_VYAJ = 1D;
    private static final Double UPDATED_JAMA_VYAJ = 2D;
    private static final Double SMALLER_JAMA_VYAJ = 1D - 1D;

    private static final Double DEFAULT_DAND_VYAJ = 1D;
    private static final Double UPDATED_DAND_VYAJ = 2D;
    private static final Double SMALLER_DAND_VYAJ = 1D - 1D;

    private static final Double DEFAULT_KARJ_RAKKAM = 1D;
    private static final Double UPDATED_KARJ_RAKKAM = 2D;
    private static final Double SMALLER_KARJ_RAKKAM = 1D - 1D;

    private static final Instant DEFAULT_THAK_DINNANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THAK_DINNANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_KARJ_DINNANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KARJ_DINNANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MUDAT_SAMPTE_DINANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MUDAT_SAMPTE_DINANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MUDAT = "AAAAAAAAAA";
    private static final String UPDATED_MUDAT = "BBBBBBBBBB";

    private static final String DEFAULT_VYAJ = "AAAAAAAAAA";
    private static final String UPDATED_VYAJ = "BBBBBBBBBB";

    private static final Double DEFAULT_HAPTA_RAKKAM = 1D;
    private static final Double UPDATED_HAPTA_RAKKAM = 2D;
    private static final Double SMALLER_HAPTA_RAKKAM = 1D - 1D;

    private static final String DEFAULT_SHAKHA_VEVSTHAPAK = "AAAAAAAAAA";
    private static final String UPDATED_SHAKHA_VEVSTHAPAK = "BBBBBBBBBB";

    private static final String DEFAULT_SUCHAK = "AAAAAAAAAA";
    private static final String UPDATED_SUCHAK = "BBBBBBBBBB";

    private static final String DEFAULT_ANUMODAK = "AAAAAAAAAA";
    private static final String UPDATED_ANUMODAK = "BBBBBBBBBB";

    private static final Double DEFAULT_DAVA = 1D;
    private static final Double UPDATED_DAVA = 2D;
    private static final Double SMALLER_DAVA = 1D - 1D;

    private static final Float DEFAULT_VYAJ_DAR = 1F;
    private static final Float UPDATED_VYAJ_DAR = 2F;
    private static final Float SMALLER_VYAJ_DAR = 1F - 1F;

    private static final Double DEFAULT_SARCHARGE = 1D;
    private static final Double UPDATED_SARCHARGE = 2D;
    private static final Double SMALLER_SARCHARGE = 1D - 1D;

    private static final Double DEFAULT_JYADA_VYAJ = 1D;
    private static final Double UPDATED_JYADA_VYAJ = 2D;
    private static final Double SMALLER_JYADA_VYAJ = 1D - 1D;

    private static final Double DEFAULT_YENE_VYAJ = 1D;
    private static final Double UPDATED_YENE_VYAJ = 2D;
    private static final Double SMALLER_YENE_VYAJ = 1D - 1D;

    private static final Double DEFAULT_VASULI_KHARCH = 1D;
    private static final Double UPDATED_VASULI_KHARCH = 2D;
    private static final Double SMALLER_VASULI_KHARCH = 1D - 1D;

    private static final Double DEFAULT_ETHAR_KHARCH = 1D;
    private static final Double UPDATED_ETHAR_KHARCH = 2D;
    private static final Double SMALLER_ETHAR_KHARCH = 1D - 1D;

    private static final Double DEFAULT_VIMA = 1D;
    private static final Double UPDATED_VIMA = 2D;
    private static final Double SMALLER_VIMA = 1D - 1D;

    private static final Double DEFAULT_NOTICE = 1D;
    private static final Double UPDATED_NOTICE = 2D;
    private static final Double SMALLER_NOTICE = 1D - 1D;

    private static final Long DEFAULT_THARAV_NUMBER = 1L;
    private static final Long UPDATED_THARAV_NUMBER = 2L;
    private static final Long SMALLER_THARAV_NUMBER = 1L - 1L;

    private static final Instant DEFAULT_THARAV_DINANK = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THARAV_DINANK = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VISHAY_KRAMANK = 1;
    private static final Integer UPDATED_VISHAY_KRAMANK = 2;
    private static final Integer SMALLER_VISHAY_KRAMANK = 1 - 1;

    private static final Instant DEFAULT_NOTICE_ONE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOTICE_ONE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NOTICE_TWO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOTICE_TWO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_WAR = "AAAAAAAAAA";
    private static final String UPDATED_WAR = "BBBBBBBBBB";

    private static final String DEFAULT_VEL = "AAAAAAAAAA";
    private static final String UPDATED_VEL = "BBBBBBBBBB";

    private static final String DEFAULT_JAMINDAR_ONE = "AAAAAAAAAA";
    private static final String UPDATED_JAMINDAR_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_JAMINDAR_ONE_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_JAMINDAR_ONE_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_JAMINDAR_TWO = "AAAAAAAAAA";
    private static final String UPDATED_JAMINDAR_TWO = "BBBBBBBBBB";

    private static final String DEFAULT_JAMINDAR_TWO_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_JAMINDAR_TWO_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TARAN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TARAN_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TARAN_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_TARAN_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/court-cases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CourtCaseRepository courtCaseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourtCaseMockMvc;

    private CourtCase courtCase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCase createEntity(EntityManager em) {
        CourtCase courtCase = new CourtCase()
            .code(DEFAULT_CODE)
            .caseDinank(DEFAULT_CASE_DINANK)
            .bankName(DEFAULT_BANK_NAME)
            .talukaName(DEFAULT_TALUKA_NAME)
            .talukaCode(DEFAULT_TALUKA_CODE)
            .sabasadSavingAccNo(DEFAULT_SABASAD_SAVING_ACC_NO)
            .sabasadName(DEFAULT_SABASAD_NAME)
            .sabasadAddress(DEFAULT_SABASAD_ADDRESS)
            .karjPrakar(DEFAULT_KARJ_PRAKAR)
            .vasuliAdhikari(DEFAULT_VASULI_ADHIKARI)
            .ekunJama(DEFAULT_EKUN_JAMA)
            .baki(DEFAULT_BAKI)
            .arOffice(DEFAULT_AR_OFFICE)
            .ekunVyaj(DEFAULT_EKUN_VYAJ)
            .jamaVyaj(DEFAULT_JAMA_VYAJ)
            .dandVyaj(DEFAULT_DAND_VYAJ)
            .karjRakkam(DEFAULT_KARJ_RAKKAM)
            .thakDinnank(DEFAULT_THAK_DINNANK)
            .karjDinnank(DEFAULT_KARJ_DINNANK)
            .mudatSampteDinank(DEFAULT_MUDAT_SAMPTE_DINANK)
            .mudat(DEFAULT_MUDAT)
            .vyaj(DEFAULT_VYAJ)
            .haptaRakkam(DEFAULT_HAPTA_RAKKAM)
            .shakhaVevsthapak(DEFAULT_SHAKHA_VEVSTHAPAK)
            .suchak(DEFAULT_SUCHAK)
            .anumodak(DEFAULT_ANUMODAK)
            .dava(DEFAULT_DAVA)
            .vyajDar(DEFAULT_VYAJ_DAR)
            .sarcharge(DEFAULT_SARCHARGE)
            .jyadaVyaj(DEFAULT_JYADA_VYAJ)
            .yeneVyaj(DEFAULT_YENE_VYAJ)
            .vasuliKharch(DEFAULT_VASULI_KHARCH)
            .etharKharch(DEFAULT_ETHAR_KHARCH)
            .vima(DEFAULT_VIMA)
            .notice(DEFAULT_NOTICE)
            .tharavNumber(DEFAULT_THARAV_NUMBER)
            .tharavDinank(DEFAULT_THARAV_DINANK)
            .vishayKramank(DEFAULT_VISHAY_KRAMANK)
            .noticeOne(DEFAULT_NOTICE_ONE)
            .noticeTwo(DEFAULT_NOTICE_TWO)
            .war(DEFAULT_WAR)
            .vel(DEFAULT_VEL)
            .jamindarOne(DEFAULT_JAMINDAR_ONE)
            .jamindarOneAddress(DEFAULT_JAMINDAR_ONE_ADDRESS)
            .jamindarTwo(DEFAULT_JAMINDAR_TWO)
            .jamindarTwoAddress(DEFAULT_JAMINDAR_TWO_ADDRESS)
            .taranType(DEFAULT_TARAN_TYPE)
            .taranDetails(DEFAULT_TARAN_DETAILS);
        return courtCase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourtCase createUpdatedEntity(EntityManager em) {
        CourtCase courtCase = new CourtCase()
            .code(UPDATED_CODE)
            .caseDinank(UPDATED_CASE_DINANK)
            .bankName(UPDATED_BANK_NAME)
            .talukaName(UPDATED_TALUKA_NAME)
            .talukaCode(UPDATED_TALUKA_CODE)
            .sabasadSavingAccNo(UPDATED_SABASAD_SAVING_ACC_NO)
            .sabasadName(UPDATED_SABASAD_NAME)
            .sabasadAddress(UPDATED_SABASAD_ADDRESS)
            .karjPrakar(UPDATED_KARJ_PRAKAR)
            .vasuliAdhikari(UPDATED_VASULI_ADHIKARI)
            .ekunJama(UPDATED_EKUN_JAMA)
            .baki(UPDATED_BAKI)
            .arOffice(UPDATED_AR_OFFICE)
            .ekunVyaj(UPDATED_EKUN_VYAJ)
            .jamaVyaj(UPDATED_JAMA_VYAJ)
            .dandVyaj(UPDATED_DAND_VYAJ)
            .karjRakkam(UPDATED_KARJ_RAKKAM)
            .thakDinnank(UPDATED_THAK_DINNANK)
            .karjDinnank(UPDATED_KARJ_DINNANK)
            .mudatSampteDinank(UPDATED_MUDAT_SAMPTE_DINANK)
            .mudat(UPDATED_MUDAT)
            .vyaj(UPDATED_VYAJ)
            .haptaRakkam(UPDATED_HAPTA_RAKKAM)
            .shakhaVevsthapak(UPDATED_SHAKHA_VEVSTHAPAK)
            .suchak(UPDATED_SUCHAK)
            .anumodak(UPDATED_ANUMODAK)
            .dava(UPDATED_DAVA)
            .vyajDar(UPDATED_VYAJ_DAR)
            .sarcharge(UPDATED_SARCHARGE)
            .jyadaVyaj(UPDATED_JYADA_VYAJ)
            .yeneVyaj(UPDATED_YENE_VYAJ)
            .vasuliKharch(UPDATED_VASULI_KHARCH)
            .etharKharch(UPDATED_ETHAR_KHARCH)
            .vima(UPDATED_VIMA)
            .notice(UPDATED_NOTICE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDinank(UPDATED_THARAV_DINANK)
            .vishayKramank(UPDATED_VISHAY_KRAMANK)
            .noticeOne(UPDATED_NOTICE_ONE)
            .noticeTwo(UPDATED_NOTICE_TWO)
            .war(UPDATED_WAR)
            .vel(UPDATED_VEL)
            .jamindarOne(UPDATED_JAMINDAR_ONE)
            .jamindarOneAddress(UPDATED_JAMINDAR_ONE_ADDRESS)
            .jamindarTwo(UPDATED_JAMINDAR_TWO)
            .jamindarTwoAddress(UPDATED_JAMINDAR_TWO_ADDRESS)
            .taranType(UPDATED_TARAN_TYPE)
            .taranDetails(UPDATED_TARAN_DETAILS);
        return courtCase;
    }

    @BeforeEach
    public void initTest() {
        courtCase = createEntity(em);
    }

    @Test
    @Transactional
    void createCourtCase() throws Exception {
        int databaseSizeBeforeCreate = courtCaseRepository.findAll().size();
        // Create the CourtCase
        restCourtCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCase)))
            .andExpect(status().isCreated());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeCreate + 1);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCourtCase.getCaseDinank()).isEqualTo(DEFAULT_CASE_DINANK);
        assertThat(testCourtCase.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCourtCase.getTalukaName()).isEqualTo(DEFAULT_TALUKA_NAME);
        assertThat(testCourtCase.getTalukaCode()).isEqualTo(DEFAULT_TALUKA_CODE);
        assertThat(testCourtCase.getSabasadSavingAccNo()).isEqualTo(DEFAULT_SABASAD_SAVING_ACC_NO);
        assertThat(testCourtCase.getSabasadName()).isEqualTo(DEFAULT_SABASAD_NAME);
        assertThat(testCourtCase.getSabasadAddress()).isEqualTo(DEFAULT_SABASAD_ADDRESS);
        assertThat(testCourtCase.getKarjPrakar()).isEqualTo(DEFAULT_KARJ_PRAKAR);
        assertThat(testCourtCase.getVasuliAdhikari()).isEqualTo(DEFAULT_VASULI_ADHIKARI);
        assertThat(testCourtCase.getEkunJama()).isEqualTo(DEFAULT_EKUN_JAMA);
        assertThat(testCourtCase.getBaki()).isEqualTo(DEFAULT_BAKI);
        assertThat(testCourtCase.getArOffice()).isEqualTo(DEFAULT_AR_OFFICE);
        assertThat(testCourtCase.getEkunVyaj()).isEqualTo(DEFAULT_EKUN_VYAJ);
        assertThat(testCourtCase.getJamaVyaj()).isEqualTo(DEFAULT_JAMA_VYAJ);
        assertThat(testCourtCase.getDandVyaj()).isEqualTo(DEFAULT_DAND_VYAJ);
        assertThat(testCourtCase.getKarjRakkam()).isEqualTo(DEFAULT_KARJ_RAKKAM);
        assertThat(testCourtCase.getThakDinnank()).isEqualTo(DEFAULT_THAK_DINNANK);
        assertThat(testCourtCase.getKarjDinnank()).isEqualTo(DEFAULT_KARJ_DINNANK);
        assertThat(testCourtCase.getMudatSampteDinank()).isEqualTo(DEFAULT_MUDAT_SAMPTE_DINANK);
        assertThat(testCourtCase.getMudat()).isEqualTo(DEFAULT_MUDAT);
        assertThat(testCourtCase.getVyaj()).isEqualTo(DEFAULT_VYAJ);
        assertThat(testCourtCase.getHaptaRakkam()).isEqualTo(DEFAULT_HAPTA_RAKKAM);
        assertThat(testCourtCase.getShakhaVevsthapak()).isEqualTo(DEFAULT_SHAKHA_VEVSTHAPAK);
        assertThat(testCourtCase.getSuchak()).isEqualTo(DEFAULT_SUCHAK);
        assertThat(testCourtCase.getAnumodak()).isEqualTo(DEFAULT_ANUMODAK);
        assertThat(testCourtCase.getDava()).isEqualTo(DEFAULT_DAVA);
        assertThat(testCourtCase.getVyajDar()).isEqualTo(DEFAULT_VYAJ_DAR);
        assertThat(testCourtCase.getSarcharge()).isEqualTo(DEFAULT_SARCHARGE);
        assertThat(testCourtCase.getJyadaVyaj()).isEqualTo(DEFAULT_JYADA_VYAJ);
        assertThat(testCourtCase.getYeneVyaj()).isEqualTo(DEFAULT_YENE_VYAJ);
        assertThat(testCourtCase.getVasuliKharch()).isEqualTo(DEFAULT_VASULI_KHARCH);
        assertThat(testCourtCase.getEtharKharch()).isEqualTo(DEFAULT_ETHAR_KHARCH);
        assertThat(testCourtCase.getVima()).isEqualTo(DEFAULT_VIMA);
        assertThat(testCourtCase.getNotice()).isEqualTo(DEFAULT_NOTICE);
        assertThat(testCourtCase.getTharavNumber()).isEqualTo(DEFAULT_THARAV_NUMBER);
        assertThat(testCourtCase.getTharavDinank()).isEqualTo(DEFAULT_THARAV_DINANK);
        assertThat(testCourtCase.getVishayKramank()).isEqualTo(DEFAULT_VISHAY_KRAMANK);
        assertThat(testCourtCase.getNoticeOne()).isEqualTo(DEFAULT_NOTICE_ONE);
        assertThat(testCourtCase.getNoticeTwo()).isEqualTo(DEFAULT_NOTICE_TWO);
        assertThat(testCourtCase.getWar()).isEqualTo(DEFAULT_WAR);
        assertThat(testCourtCase.getVel()).isEqualTo(DEFAULT_VEL);
        assertThat(testCourtCase.getJamindarOne()).isEqualTo(DEFAULT_JAMINDAR_ONE);
        assertThat(testCourtCase.getJamindarOneAddress()).isEqualTo(DEFAULT_JAMINDAR_ONE_ADDRESS);
        assertThat(testCourtCase.getJamindarTwo()).isEqualTo(DEFAULT_JAMINDAR_TWO);
        assertThat(testCourtCase.getJamindarTwoAddress()).isEqualTo(DEFAULT_JAMINDAR_TWO_ADDRESS);
        assertThat(testCourtCase.getTaranType()).isEqualTo(DEFAULT_TARAN_TYPE);
        assertThat(testCourtCase.getTaranDetails()).isEqualTo(DEFAULT_TARAN_DETAILS);
    }

    @Test
    @Transactional
    void createCourtCaseWithExistingId() throws Exception {
        // Create the CourtCase with an existing ID
        courtCase.setId(1L);

        int databaseSizeBeforeCreate = courtCaseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourtCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCase)))
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCourtCases() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.intValue())))
            .andExpect(jsonPath("$.[*].caseDinank").value(hasItem(DEFAULT_CASE_DINANK.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].talukaCode").value(hasItem(DEFAULT_TALUKA_CODE.intValue())))
            .andExpect(jsonPath("$.[*].sabasadSavingAccNo").value(hasItem(DEFAULT_SABASAD_SAVING_ACC_NO)))
            .andExpect(jsonPath("$.[*].sabasadName").value(hasItem(DEFAULT_SABASAD_NAME)))
            .andExpect(jsonPath("$.[*].sabasadAddress").value(hasItem(DEFAULT_SABASAD_ADDRESS)))
            .andExpect(jsonPath("$.[*].karjPrakar").value(hasItem(DEFAULT_KARJ_PRAKAR)))
            .andExpect(jsonPath("$.[*].vasuliAdhikari").value(hasItem(DEFAULT_VASULI_ADHIKARI)))
            .andExpect(jsonPath("$.[*].ekunJama").value(hasItem(DEFAULT_EKUN_JAMA.doubleValue())))
            .andExpect(jsonPath("$.[*].baki").value(hasItem(DEFAULT_BAKI.doubleValue())))
            .andExpect(jsonPath("$.[*].arOffice").value(hasItem(DEFAULT_AR_OFFICE)))
            .andExpect(jsonPath("$.[*].ekunVyaj").value(hasItem(DEFAULT_EKUN_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].jamaVyaj").value(hasItem(DEFAULT_JAMA_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].dandVyaj").value(hasItem(DEFAULT_DAND_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].karjRakkam").value(hasItem(DEFAULT_KARJ_RAKKAM.doubleValue())))
            .andExpect(jsonPath("$.[*].thakDinnank").value(hasItem(DEFAULT_THAK_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].karjDinnank").value(hasItem(DEFAULT_KARJ_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].mudatSampteDinank").value(hasItem(DEFAULT_MUDAT_SAMPTE_DINANK.toString())))
            .andExpect(jsonPath("$.[*].mudat").value(hasItem(DEFAULT_MUDAT)))
            .andExpect(jsonPath("$.[*].vyaj").value(hasItem(DEFAULT_VYAJ)))
            .andExpect(jsonPath("$.[*].haptaRakkam").value(hasItem(DEFAULT_HAPTA_RAKKAM.doubleValue())))
            .andExpect(jsonPath("$.[*].shakhaVevsthapak").value(hasItem(DEFAULT_SHAKHA_VEVSTHAPAK)))
            .andExpect(jsonPath("$.[*].suchak").value(hasItem(DEFAULT_SUCHAK)))
            .andExpect(jsonPath("$.[*].anumodak").value(hasItem(DEFAULT_ANUMODAK)))
            .andExpect(jsonPath("$.[*].dava").value(hasItem(DEFAULT_DAVA.doubleValue())))
            .andExpect(jsonPath("$.[*].vyajDar").value(hasItem(DEFAULT_VYAJ_DAR.doubleValue())))
            .andExpect(jsonPath("$.[*].sarcharge").value(hasItem(DEFAULT_SARCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].jyadaVyaj").value(hasItem(DEFAULT_JYADA_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].yeneVyaj").value(hasItem(DEFAULT_YENE_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].vasuliKharch").value(hasItem(DEFAULT_VASULI_KHARCH.doubleValue())))
            .andExpect(jsonPath("$.[*].etharKharch").value(hasItem(DEFAULT_ETHAR_KHARCH.doubleValue())))
            .andExpect(jsonPath("$.[*].vima").value(hasItem(DEFAULT_VIMA.doubleValue())))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tharavNumber").value(hasItem(DEFAULT_THARAV_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].tharavDinank").value(hasItem(DEFAULT_THARAV_DINANK.toString())))
            .andExpect(jsonPath("$.[*].vishayKramank").value(hasItem(DEFAULT_VISHAY_KRAMANK)))
            .andExpect(jsonPath("$.[*].noticeOne").value(hasItem(DEFAULT_NOTICE_ONE.toString())))
            .andExpect(jsonPath("$.[*].noticeTwo").value(hasItem(DEFAULT_NOTICE_TWO.toString())))
            .andExpect(jsonPath("$.[*].war").value(hasItem(DEFAULT_WAR)))
            .andExpect(jsonPath("$.[*].vel").value(hasItem(DEFAULT_VEL)))
            .andExpect(jsonPath("$.[*].jamindarOne").value(hasItem(DEFAULT_JAMINDAR_ONE)))
            .andExpect(jsonPath("$.[*].jamindarOneAddress").value(hasItem(DEFAULT_JAMINDAR_ONE_ADDRESS)))
            .andExpect(jsonPath("$.[*].jamindarTwo").value(hasItem(DEFAULT_JAMINDAR_TWO)))
            .andExpect(jsonPath("$.[*].jamindarTwoAddress").value(hasItem(DEFAULT_JAMINDAR_TWO_ADDRESS)))
            .andExpect(jsonPath("$.[*].taranType").value(hasItem(DEFAULT_TARAN_TYPE)))
            .andExpect(jsonPath("$.[*].taranDetails").value(hasItem(DEFAULT_TARAN_DETAILS)));
    }

    @Test
    @Transactional
    void getCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get the courtCase
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL_ID, courtCase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courtCase.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.intValue()))
            .andExpect(jsonPath("$.caseDinank").value(DEFAULT_CASE_DINANK.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.talukaName").value(DEFAULT_TALUKA_NAME))
            .andExpect(jsonPath("$.talukaCode").value(DEFAULT_TALUKA_CODE.intValue()))
            .andExpect(jsonPath("$.sabasadSavingAccNo").value(DEFAULT_SABASAD_SAVING_ACC_NO))
            .andExpect(jsonPath("$.sabasadName").value(DEFAULT_SABASAD_NAME))
            .andExpect(jsonPath("$.sabasadAddress").value(DEFAULT_SABASAD_ADDRESS))
            .andExpect(jsonPath("$.karjPrakar").value(DEFAULT_KARJ_PRAKAR))
            .andExpect(jsonPath("$.vasuliAdhikari").value(DEFAULT_VASULI_ADHIKARI))
            .andExpect(jsonPath("$.ekunJama").value(DEFAULT_EKUN_JAMA.doubleValue()))
            .andExpect(jsonPath("$.baki").value(DEFAULT_BAKI.doubleValue()))
            .andExpect(jsonPath("$.arOffice").value(DEFAULT_AR_OFFICE))
            .andExpect(jsonPath("$.ekunVyaj").value(DEFAULT_EKUN_VYAJ.doubleValue()))
            .andExpect(jsonPath("$.jamaVyaj").value(DEFAULT_JAMA_VYAJ.doubleValue()))
            .andExpect(jsonPath("$.dandVyaj").value(DEFAULT_DAND_VYAJ.doubleValue()))
            .andExpect(jsonPath("$.karjRakkam").value(DEFAULT_KARJ_RAKKAM.doubleValue()))
            .andExpect(jsonPath("$.thakDinnank").value(DEFAULT_THAK_DINNANK.toString()))
            .andExpect(jsonPath("$.karjDinnank").value(DEFAULT_KARJ_DINNANK.toString()))
            .andExpect(jsonPath("$.mudatSampteDinank").value(DEFAULT_MUDAT_SAMPTE_DINANK.toString()))
            .andExpect(jsonPath("$.mudat").value(DEFAULT_MUDAT))
            .andExpect(jsonPath("$.vyaj").value(DEFAULT_VYAJ))
            .andExpect(jsonPath("$.haptaRakkam").value(DEFAULT_HAPTA_RAKKAM.doubleValue()))
            .andExpect(jsonPath("$.shakhaVevsthapak").value(DEFAULT_SHAKHA_VEVSTHAPAK))
            .andExpect(jsonPath("$.suchak").value(DEFAULT_SUCHAK))
            .andExpect(jsonPath("$.anumodak").value(DEFAULT_ANUMODAK))
            .andExpect(jsonPath("$.dava").value(DEFAULT_DAVA.doubleValue()))
            .andExpect(jsonPath("$.vyajDar").value(DEFAULT_VYAJ_DAR.doubleValue()))
            .andExpect(jsonPath("$.sarcharge").value(DEFAULT_SARCHARGE.doubleValue()))
            .andExpect(jsonPath("$.jyadaVyaj").value(DEFAULT_JYADA_VYAJ.doubleValue()))
            .andExpect(jsonPath("$.yeneVyaj").value(DEFAULT_YENE_VYAJ.doubleValue()))
            .andExpect(jsonPath("$.vasuliKharch").value(DEFAULT_VASULI_KHARCH.doubleValue()))
            .andExpect(jsonPath("$.etharKharch").value(DEFAULT_ETHAR_KHARCH.doubleValue()))
            .andExpect(jsonPath("$.vima").value(DEFAULT_VIMA.doubleValue()))
            .andExpect(jsonPath("$.notice").value(DEFAULT_NOTICE.doubleValue()))
            .andExpect(jsonPath("$.tharavNumber").value(DEFAULT_THARAV_NUMBER.intValue()))
            .andExpect(jsonPath("$.tharavDinank").value(DEFAULT_THARAV_DINANK.toString()))
            .andExpect(jsonPath("$.vishayKramank").value(DEFAULT_VISHAY_KRAMANK))
            .andExpect(jsonPath("$.noticeOne").value(DEFAULT_NOTICE_ONE.toString()))
            .andExpect(jsonPath("$.noticeTwo").value(DEFAULT_NOTICE_TWO.toString()))
            .andExpect(jsonPath("$.war").value(DEFAULT_WAR))
            .andExpect(jsonPath("$.vel").value(DEFAULT_VEL))
            .andExpect(jsonPath("$.jamindarOne").value(DEFAULT_JAMINDAR_ONE))
            .andExpect(jsonPath("$.jamindarOneAddress").value(DEFAULT_JAMINDAR_ONE_ADDRESS))
            .andExpect(jsonPath("$.jamindarTwo").value(DEFAULT_JAMINDAR_TWO))
            .andExpect(jsonPath("$.jamindarTwoAddress").value(DEFAULT_JAMINDAR_TWO_ADDRESS))
            .andExpect(jsonPath("$.taranType").value(DEFAULT_TARAN_TYPE))
            .andExpect(jsonPath("$.taranDetails").value(DEFAULT_TARAN_DETAILS));
    }

    @Test
    @Transactional
    void getCourtCasesByIdFiltering() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        Long id = courtCase.getId();

        defaultCourtCaseShouldBeFound("id.equals=" + id);
        defaultCourtCaseShouldNotBeFound("id.notEquals=" + id);

        defaultCourtCaseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourtCaseShouldNotBeFound("id.greaterThan=" + id);

        defaultCourtCaseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourtCaseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where code equals to DEFAULT_CODE
        defaultCourtCaseShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the courtCaseList where code equals to UPDATED_CODE
        defaultCourtCaseShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCourtCaseShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the courtCaseList where code equals to UPDATED_CODE
        defaultCourtCaseShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where code is not null
        defaultCourtCaseShouldBeFound("code.specified=true");

        // Get all the courtCaseList where code is null
        defaultCourtCaseShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where code is greater than or equal to DEFAULT_CODE
        defaultCourtCaseShouldBeFound("code.greaterThanOrEqual=" + DEFAULT_CODE);

        // Get all the courtCaseList where code is greater than or equal to UPDATED_CODE
        defaultCourtCaseShouldNotBeFound("code.greaterThanOrEqual=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where code is less than or equal to DEFAULT_CODE
        defaultCourtCaseShouldBeFound("code.lessThanOrEqual=" + DEFAULT_CODE);

        // Get all the courtCaseList where code is less than or equal to SMALLER_CODE
        defaultCourtCaseShouldNotBeFound("code.lessThanOrEqual=" + SMALLER_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where code is less than DEFAULT_CODE
        defaultCourtCaseShouldNotBeFound("code.lessThan=" + DEFAULT_CODE);

        // Get all the courtCaseList where code is less than UPDATED_CODE
        defaultCourtCaseShouldBeFound("code.lessThan=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where code is greater than DEFAULT_CODE
        defaultCourtCaseShouldNotBeFound("code.greaterThan=" + DEFAULT_CODE);

        // Get all the courtCaseList where code is greater than SMALLER_CODE
        defaultCourtCaseShouldBeFound("code.greaterThan=" + SMALLER_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseDinankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseDinank equals to DEFAULT_CASE_DINANK
        defaultCourtCaseShouldBeFound("caseDinank.equals=" + DEFAULT_CASE_DINANK);

        // Get all the courtCaseList where caseDinank equals to UPDATED_CASE_DINANK
        defaultCourtCaseShouldNotBeFound("caseDinank.equals=" + UPDATED_CASE_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseDinankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseDinank in DEFAULT_CASE_DINANK or UPDATED_CASE_DINANK
        defaultCourtCaseShouldBeFound("caseDinank.in=" + DEFAULT_CASE_DINANK + "," + UPDATED_CASE_DINANK);

        // Get all the courtCaseList where caseDinank equals to UPDATED_CASE_DINANK
        defaultCourtCaseShouldNotBeFound("caseDinank.in=" + UPDATED_CASE_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByCaseDinankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where caseDinank is not null
        defaultCourtCaseShouldBeFound("caseDinank.specified=true");

        // Get all the courtCaseList where caseDinank is null
        defaultCourtCaseShouldNotBeFound("caseDinank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankName equals to DEFAULT_BANK_NAME
        defaultCourtCaseShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the courtCaseList where bankName equals to UPDATED_BANK_NAME
        defaultCourtCaseShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultCourtCaseShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the courtCaseList where bankName equals to UPDATED_BANK_NAME
        defaultCourtCaseShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankName is not null
        defaultCourtCaseShouldBeFound("bankName.specified=true");

        // Get all the courtCaseList where bankName is null
        defaultCourtCaseShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankName contains DEFAULT_BANK_NAME
        defaultCourtCaseShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the courtCaseList where bankName contains UPDATED_BANK_NAME
        defaultCourtCaseShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where bankName does not contain DEFAULT_BANK_NAME
        defaultCourtCaseShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the courtCaseList where bankName does not contain UPDATED_BANK_NAME
        defaultCourtCaseShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaName equals to DEFAULT_TALUKA_NAME
        defaultCourtCaseShouldBeFound("talukaName.equals=" + DEFAULT_TALUKA_NAME);

        // Get all the courtCaseList where talukaName equals to UPDATED_TALUKA_NAME
        defaultCourtCaseShouldNotBeFound("talukaName.equals=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaName in DEFAULT_TALUKA_NAME or UPDATED_TALUKA_NAME
        defaultCourtCaseShouldBeFound("talukaName.in=" + DEFAULT_TALUKA_NAME + "," + UPDATED_TALUKA_NAME);

        // Get all the courtCaseList where talukaName equals to UPDATED_TALUKA_NAME
        defaultCourtCaseShouldNotBeFound("talukaName.in=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaName is not null
        defaultCourtCaseShouldBeFound("talukaName.specified=true");

        // Get all the courtCaseList where talukaName is null
        defaultCourtCaseShouldNotBeFound("talukaName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaName contains DEFAULT_TALUKA_NAME
        defaultCourtCaseShouldBeFound("talukaName.contains=" + DEFAULT_TALUKA_NAME);

        // Get all the courtCaseList where talukaName contains UPDATED_TALUKA_NAME
        defaultCourtCaseShouldNotBeFound("talukaName.contains=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaName does not contain DEFAULT_TALUKA_NAME
        defaultCourtCaseShouldNotBeFound("talukaName.doesNotContain=" + DEFAULT_TALUKA_NAME);

        // Get all the courtCaseList where talukaName does not contain UPDATED_TALUKA_NAME
        defaultCourtCaseShouldBeFound("talukaName.doesNotContain=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaCode equals to DEFAULT_TALUKA_CODE
        defaultCourtCaseShouldBeFound("talukaCode.equals=" + DEFAULT_TALUKA_CODE);

        // Get all the courtCaseList where talukaCode equals to UPDATED_TALUKA_CODE
        defaultCourtCaseShouldNotBeFound("talukaCode.equals=" + UPDATED_TALUKA_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaCodeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaCode in DEFAULT_TALUKA_CODE or UPDATED_TALUKA_CODE
        defaultCourtCaseShouldBeFound("talukaCode.in=" + DEFAULT_TALUKA_CODE + "," + UPDATED_TALUKA_CODE);

        // Get all the courtCaseList where talukaCode equals to UPDATED_TALUKA_CODE
        defaultCourtCaseShouldNotBeFound("talukaCode.in=" + UPDATED_TALUKA_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaCode is not null
        defaultCourtCaseShouldBeFound("talukaCode.specified=true");

        // Get all the courtCaseList where talukaCode is null
        defaultCourtCaseShouldNotBeFound("talukaCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaCode is greater than or equal to DEFAULT_TALUKA_CODE
        defaultCourtCaseShouldBeFound("talukaCode.greaterThanOrEqual=" + DEFAULT_TALUKA_CODE);

        // Get all the courtCaseList where talukaCode is greater than or equal to UPDATED_TALUKA_CODE
        defaultCourtCaseShouldNotBeFound("talukaCode.greaterThanOrEqual=" + UPDATED_TALUKA_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaCode is less than or equal to DEFAULT_TALUKA_CODE
        defaultCourtCaseShouldBeFound("talukaCode.lessThanOrEqual=" + DEFAULT_TALUKA_CODE);

        // Get all the courtCaseList where talukaCode is less than or equal to SMALLER_TALUKA_CODE
        defaultCourtCaseShouldNotBeFound("talukaCode.lessThanOrEqual=" + SMALLER_TALUKA_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaCode is less than DEFAULT_TALUKA_CODE
        defaultCourtCaseShouldNotBeFound("talukaCode.lessThan=" + DEFAULT_TALUKA_CODE);

        // Get all the courtCaseList where talukaCode is less than UPDATED_TALUKA_CODE
        defaultCourtCaseShouldBeFound("talukaCode.lessThan=" + UPDATED_TALUKA_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTalukaCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where talukaCode is greater than DEFAULT_TALUKA_CODE
        defaultCourtCaseShouldNotBeFound("talukaCode.greaterThan=" + DEFAULT_TALUKA_CODE);

        // Get all the courtCaseList where talukaCode is greater than SMALLER_TALUKA_CODE
        defaultCourtCaseShouldBeFound("talukaCode.greaterThan=" + SMALLER_TALUKA_CODE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadSavingAccNoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadSavingAccNo equals to DEFAULT_SABASAD_SAVING_ACC_NO
        defaultCourtCaseShouldBeFound("sabasadSavingAccNo.equals=" + DEFAULT_SABASAD_SAVING_ACC_NO);

        // Get all the courtCaseList where sabasadSavingAccNo equals to UPDATED_SABASAD_SAVING_ACC_NO
        defaultCourtCaseShouldNotBeFound("sabasadSavingAccNo.equals=" + UPDATED_SABASAD_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadSavingAccNoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadSavingAccNo in DEFAULT_SABASAD_SAVING_ACC_NO or UPDATED_SABASAD_SAVING_ACC_NO
        defaultCourtCaseShouldBeFound("sabasadSavingAccNo.in=" + DEFAULT_SABASAD_SAVING_ACC_NO + "," + UPDATED_SABASAD_SAVING_ACC_NO);

        // Get all the courtCaseList where sabasadSavingAccNo equals to UPDATED_SABASAD_SAVING_ACC_NO
        defaultCourtCaseShouldNotBeFound("sabasadSavingAccNo.in=" + UPDATED_SABASAD_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadSavingAccNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadSavingAccNo is not null
        defaultCourtCaseShouldBeFound("sabasadSavingAccNo.specified=true");

        // Get all the courtCaseList where sabasadSavingAccNo is null
        defaultCourtCaseShouldNotBeFound("sabasadSavingAccNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadSavingAccNoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadSavingAccNo contains DEFAULT_SABASAD_SAVING_ACC_NO
        defaultCourtCaseShouldBeFound("sabasadSavingAccNo.contains=" + DEFAULT_SABASAD_SAVING_ACC_NO);

        // Get all the courtCaseList where sabasadSavingAccNo contains UPDATED_SABASAD_SAVING_ACC_NO
        defaultCourtCaseShouldNotBeFound("sabasadSavingAccNo.contains=" + UPDATED_SABASAD_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadSavingAccNoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadSavingAccNo does not contain DEFAULT_SABASAD_SAVING_ACC_NO
        defaultCourtCaseShouldNotBeFound("sabasadSavingAccNo.doesNotContain=" + DEFAULT_SABASAD_SAVING_ACC_NO);

        // Get all the courtCaseList where sabasadSavingAccNo does not contain UPDATED_SABASAD_SAVING_ACC_NO
        defaultCourtCaseShouldBeFound("sabasadSavingAccNo.doesNotContain=" + UPDATED_SABASAD_SAVING_ACC_NO);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadNameIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadName equals to DEFAULT_SABASAD_NAME
        defaultCourtCaseShouldBeFound("sabasadName.equals=" + DEFAULT_SABASAD_NAME);

        // Get all the courtCaseList where sabasadName equals to UPDATED_SABASAD_NAME
        defaultCourtCaseShouldNotBeFound("sabasadName.equals=" + UPDATED_SABASAD_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadNameIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadName in DEFAULT_SABASAD_NAME or UPDATED_SABASAD_NAME
        defaultCourtCaseShouldBeFound("sabasadName.in=" + DEFAULT_SABASAD_NAME + "," + UPDATED_SABASAD_NAME);

        // Get all the courtCaseList where sabasadName equals to UPDATED_SABASAD_NAME
        defaultCourtCaseShouldNotBeFound("sabasadName.in=" + UPDATED_SABASAD_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadName is not null
        defaultCourtCaseShouldBeFound("sabasadName.specified=true");

        // Get all the courtCaseList where sabasadName is null
        defaultCourtCaseShouldNotBeFound("sabasadName.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadNameContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadName contains DEFAULT_SABASAD_NAME
        defaultCourtCaseShouldBeFound("sabasadName.contains=" + DEFAULT_SABASAD_NAME);

        // Get all the courtCaseList where sabasadName contains UPDATED_SABASAD_NAME
        defaultCourtCaseShouldNotBeFound("sabasadName.contains=" + UPDATED_SABASAD_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadNameNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadName does not contain DEFAULT_SABASAD_NAME
        defaultCourtCaseShouldNotBeFound("sabasadName.doesNotContain=" + DEFAULT_SABASAD_NAME);

        // Get all the courtCaseList where sabasadName does not contain UPDATED_SABASAD_NAME
        defaultCourtCaseShouldBeFound("sabasadName.doesNotContain=" + UPDATED_SABASAD_NAME);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadAddress equals to DEFAULT_SABASAD_ADDRESS
        defaultCourtCaseShouldBeFound("sabasadAddress.equals=" + DEFAULT_SABASAD_ADDRESS);

        // Get all the courtCaseList where sabasadAddress equals to UPDATED_SABASAD_ADDRESS
        defaultCourtCaseShouldNotBeFound("sabasadAddress.equals=" + UPDATED_SABASAD_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadAddressIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadAddress in DEFAULT_SABASAD_ADDRESS or UPDATED_SABASAD_ADDRESS
        defaultCourtCaseShouldBeFound("sabasadAddress.in=" + DEFAULT_SABASAD_ADDRESS + "," + UPDATED_SABASAD_ADDRESS);

        // Get all the courtCaseList where sabasadAddress equals to UPDATED_SABASAD_ADDRESS
        defaultCourtCaseShouldNotBeFound("sabasadAddress.in=" + UPDATED_SABASAD_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadAddress is not null
        defaultCourtCaseShouldBeFound("sabasadAddress.specified=true");

        // Get all the courtCaseList where sabasadAddress is null
        defaultCourtCaseShouldNotBeFound("sabasadAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadAddressContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadAddress contains DEFAULT_SABASAD_ADDRESS
        defaultCourtCaseShouldBeFound("sabasadAddress.contains=" + DEFAULT_SABASAD_ADDRESS);

        // Get all the courtCaseList where sabasadAddress contains UPDATED_SABASAD_ADDRESS
        defaultCourtCaseShouldNotBeFound("sabasadAddress.contains=" + UPDATED_SABASAD_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySabasadAddressNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sabasadAddress does not contain DEFAULT_SABASAD_ADDRESS
        defaultCourtCaseShouldNotBeFound("sabasadAddress.doesNotContain=" + DEFAULT_SABASAD_ADDRESS);

        // Get all the courtCaseList where sabasadAddress does not contain UPDATED_SABASAD_ADDRESS
        defaultCourtCaseShouldBeFound("sabasadAddress.doesNotContain=" + UPDATED_SABASAD_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjPrakarIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjPrakar equals to DEFAULT_KARJ_PRAKAR
        defaultCourtCaseShouldBeFound("karjPrakar.equals=" + DEFAULT_KARJ_PRAKAR);

        // Get all the courtCaseList where karjPrakar equals to UPDATED_KARJ_PRAKAR
        defaultCourtCaseShouldNotBeFound("karjPrakar.equals=" + UPDATED_KARJ_PRAKAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjPrakarIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjPrakar in DEFAULT_KARJ_PRAKAR or UPDATED_KARJ_PRAKAR
        defaultCourtCaseShouldBeFound("karjPrakar.in=" + DEFAULT_KARJ_PRAKAR + "," + UPDATED_KARJ_PRAKAR);

        // Get all the courtCaseList where karjPrakar equals to UPDATED_KARJ_PRAKAR
        defaultCourtCaseShouldNotBeFound("karjPrakar.in=" + UPDATED_KARJ_PRAKAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjPrakarIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjPrakar is not null
        defaultCourtCaseShouldBeFound("karjPrakar.specified=true");

        // Get all the courtCaseList where karjPrakar is null
        defaultCourtCaseShouldNotBeFound("karjPrakar.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjPrakarContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjPrakar contains DEFAULT_KARJ_PRAKAR
        defaultCourtCaseShouldBeFound("karjPrakar.contains=" + DEFAULT_KARJ_PRAKAR);

        // Get all the courtCaseList where karjPrakar contains UPDATED_KARJ_PRAKAR
        defaultCourtCaseShouldNotBeFound("karjPrakar.contains=" + UPDATED_KARJ_PRAKAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjPrakarNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjPrakar does not contain DEFAULT_KARJ_PRAKAR
        defaultCourtCaseShouldNotBeFound("karjPrakar.doesNotContain=" + DEFAULT_KARJ_PRAKAR);

        // Get all the courtCaseList where karjPrakar does not contain UPDATED_KARJ_PRAKAR
        defaultCourtCaseShouldBeFound("karjPrakar.doesNotContain=" + UPDATED_KARJ_PRAKAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliAdhikariIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliAdhikari equals to DEFAULT_VASULI_ADHIKARI
        defaultCourtCaseShouldBeFound("vasuliAdhikari.equals=" + DEFAULT_VASULI_ADHIKARI);

        // Get all the courtCaseList where vasuliAdhikari equals to UPDATED_VASULI_ADHIKARI
        defaultCourtCaseShouldNotBeFound("vasuliAdhikari.equals=" + UPDATED_VASULI_ADHIKARI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliAdhikariIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliAdhikari in DEFAULT_VASULI_ADHIKARI or UPDATED_VASULI_ADHIKARI
        defaultCourtCaseShouldBeFound("vasuliAdhikari.in=" + DEFAULT_VASULI_ADHIKARI + "," + UPDATED_VASULI_ADHIKARI);

        // Get all the courtCaseList where vasuliAdhikari equals to UPDATED_VASULI_ADHIKARI
        defaultCourtCaseShouldNotBeFound("vasuliAdhikari.in=" + UPDATED_VASULI_ADHIKARI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliAdhikariIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliAdhikari is not null
        defaultCourtCaseShouldBeFound("vasuliAdhikari.specified=true");

        // Get all the courtCaseList where vasuliAdhikari is null
        defaultCourtCaseShouldNotBeFound("vasuliAdhikari.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliAdhikariContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliAdhikari contains DEFAULT_VASULI_ADHIKARI
        defaultCourtCaseShouldBeFound("vasuliAdhikari.contains=" + DEFAULT_VASULI_ADHIKARI);

        // Get all the courtCaseList where vasuliAdhikari contains UPDATED_VASULI_ADHIKARI
        defaultCourtCaseShouldNotBeFound("vasuliAdhikari.contains=" + UPDATED_VASULI_ADHIKARI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliAdhikariNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliAdhikari does not contain DEFAULT_VASULI_ADHIKARI
        defaultCourtCaseShouldNotBeFound("vasuliAdhikari.doesNotContain=" + DEFAULT_VASULI_ADHIKARI);

        // Get all the courtCaseList where vasuliAdhikari does not contain UPDATED_VASULI_ADHIKARI
        defaultCourtCaseShouldBeFound("vasuliAdhikari.doesNotContain=" + UPDATED_VASULI_ADHIKARI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunJamaIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunJama equals to DEFAULT_EKUN_JAMA
        defaultCourtCaseShouldBeFound("ekunJama.equals=" + DEFAULT_EKUN_JAMA);

        // Get all the courtCaseList where ekunJama equals to UPDATED_EKUN_JAMA
        defaultCourtCaseShouldNotBeFound("ekunJama.equals=" + UPDATED_EKUN_JAMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunJamaIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunJama in DEFAULT_EKUN_JAMA or UPDATED_EKUN_JAMA
        defaultCourtCaseShouldBeFound("ekunJama.in=" + DEFAULT_EKUN_JAMA + "," + UPDATED_EKUN_JAMA);

        // Get all the courtCaseList where ekunJama equals to UPDATED_EKUN_JAMA
        defaultCourtCaseShouldNotBeFound("ekunJama.in=" + UPDATED_EKUN_JAMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunJamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunJama is not null
        defaultCourtCaseShouldBeFound("ekunJama.specified=true");

        // Get all the courtCaseList where ekunJama is null
        defaultCourtCaseShouldNotBeFound("ekunJama.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunJamaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunJama is greater than or equal to DEFAULT_EKUN_JAMA
        defaultCourtCaseShouldBeFound("ekunJama.greaterThanOrEqual=" + DEFAULT_EKUN_JAMA);

        // Get all the courtCaseList where ekunJama is greater than or equal to UPDATED_EKUN_JAMA
        defaultCourtCaseShouldNotBeFound("ekunJama.greaterThanOrEqual=" + UPDATED_EKUN_JAMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunJamaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunJama is less than or equal to DEFAULT_EKUN_JAMA
        defaultCourtCaseShouldBeFound("ekunJama.lessThanOrEqual=" + DEFAULT_EKUN_JAMA);

        // Get all the courtCaseList where ekunJama is less than or equal to SMALLER_EKUN_JAMA
        defaultCourtCaseShouldNotBeFound("ekunJama.lessThanOrEqual=" + SMALLER_EKUN_JAMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunJamaIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunJama is less than DEFAULT_EKUN_JAMA
        defaultCourtCaseShouldNotBeFound("ekunJama.lessThan=" + DEFAULT_EKUN_JAMA);

        // Get all the courtCaseList where ekunJama is less than UPDATED_EKUN_JAMA
        defaultCourtCaseShouldBeFound("ekunJama.lessThan=" + UPDATED_EKUN_JAMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunJamaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunJama is greater than DEFAULT_EKUN_JAMA
        defaultCourtCaseShouldNotBeFound("ekunJama.greaterThan=" + DEFAULT_EKUN_JAMA);

        // Get all the courtCaseList where ekunJama is greater than SMALLER_EKUN_JAMA
        defaultCourtCaseShouldBeFound("ekunJama.greaterThan=" + SMALLER_EKUN_JAMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBakiIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where baki equals to DEFAULT_BAKI
        defaultCourtCaseShouldBeFound("baki.equals=" + DEFAULT_BAKI);

        // Get all the courtCaseList where baki equals to UPDATED_BAKI
        defaultCourtCaseShouldNotBeFound("baki.equals=" + UPDATED_BAKI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBakiIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where baki in DEFAULT_BAKI or UPDATED_BAKI
        defaultCourtCaseShouldBeFound("baki.in=" + DEFAULT_BAKI + "," + UPDATED_BAKI);

        // Get all the courtCaseList where baki equals to UPDATED_BAKI
        defaultCourtCaseShouldNotBeFound("baki.in=" + UPDATED_BAKI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBakiIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where baki is not null
        defaultCourtCaseShouldBeFound("baki.specified=true");

        // Get all the courtCaseList where baki is null
        defaultCourtCaseShouldNotBeFound("baki.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByBakiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where baki is greater than or equal to DEFAULT_BAKI
        defaultCourtCaseShouldBeFound("baki.greaterThanOrEqual=" + DEFAULT_BAKI);

        // Get all the courtCaseList where baki is greater than or equal to UPDATED_BAKI
        defaultCourtCaseShouldNotBeFound("baki.greaterThanOrEqual=" + UPDATED_BAKI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBakiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where baki is less than or equal to DEFAULT_BAKI
        defaultCourtCaseShouldBeFound("baki.lessThanOrEqual=" + DEFAULT_BAKI);

        // Get all the courtCaseList where baki is less than or equal to SMALLER_BAKI
        defaultCourtCaseShouldNotBeFound("baki.lessThanOrEqual=" + SMALLER_BAKI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBakiIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where baki is less than DEFAULT_BAKI
        defaultCourtCaseShouldNotBeFound("baki.lessThan=" + DEFAULT_BAKI);

        // Get all the courtCaseList where baki is less than UPDATED_BAKI
        defaultCourtCaseShouldBeFound("baki.lessThan=" + UPDATED_BAKI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByBakiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where baki is greater than DEFAULT_BAKI
        defaultCourtCaseShouldNotBeFound("baki.greaterThan=" + DEFAULT_BAKI);

        // Get all the courtCaseList where baki is greater than SMALLER_BAKI
        defaultCourtCaseShouldBeFound("baki.greaterThan=" + SMALLER_BAKI);
    }

    @Test
    @Transactional
    void getAllCourtCasesByArOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where arOffice equals to DEFAULT_AR_OFFICE
        defaultCourtCaseShouldBeFound("arOffice.equals=" + DEFAULT_AR_OFFICE);

        // Get all the courtCaseList where arOffice equals to UPDATED_AR_OFFICE
        defaultCourtCaseShouldNotBeFound("arOffice.equals=" + UPDATED_AR_OFFICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByArOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where arOffice in DEFAULT_AR_OFFICE or UPDATED_AR_OFFICE
        defaultCourtCaseShouldBeFound("arOffice.in=" + DEFAULT_AR_OFFICE + "," + UPDATED_AR_OFFICE);

        // Get all the courtCaseList where arOffice equals to UPDATED_AR_OFFICE
        defaultCourtCaseShouldNotBeFound("arOffice.in=" + UPDATED_AR_OFFICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByArOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where arOffice is not null
        defaultCourtCaseShouldBeFound("arOffice.specified=true");

        // Get all the courtCaseList where arOffice is null
        defaultCourtCaseShouldNotBeFound("arOffice.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByArOfficeContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where arOffice contains DEFAULT_AR_OFFICE
        defaultCourtCaseShouldBeFound("arOffice.contains=" + DEFAULT_AR_OFFICE);

        // Get all the courtCaseList where arOffice contains UPDATED_AR_OFFICE
        defaultCourtCaseShouldNotBeFound("arOffice.contains=" + UPDATED_AR_OFFICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByArOfficeNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where arOffice does not contain DEFAULT_AR_OFFICE
        defaultCourtCaseShouldNotBeFound("arOffice.doesNotContain=" + DEFAULT_AR_OFFICE);

        // Get all the courtCaseList where arOffice does not contain UPDATED_AR_OFFICE
        defaultCourtCaseShouldBeFound("arOffice.doesNotContain=" + UPDATED_AR_OFFICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunVyajIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunVyaj equals to DEFAULT_EKUN_VYAJ
        defaultCourtCaseShouldBeFound("ekunVyaj.equals=" + DEFAULT_EKUN_VYAJ);

        // Get all the courtCaseList where ekunVyaj equals to UPDATED_EKUN_VYAJ
        defaultCourtCaseShouldNotBeFound("ekunVyaj.equals=" + UPDATED_EKUN_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunVyajIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunVyaj in DEFAULT_EKUN_VYAJ or UPDATED_EKUN_VYAJ
        defaultCourtCaseShouldBeFound("ekunVyaj.in=" + DEFAULT_EKUN_VYAJ + "," + UPDATED_EKUN_VYAJ);

        // Get all the courtCaseList where ekunVyaj equals to UPDATED_EKUN_VYAJ
        defaultCourtCaseShouldNotBeFound("ekunVyaj.in=" + UPDATED_EKUN_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunVyajIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunVyaj is not null
        defaultCourtCaseShouldBeFound("ekunVyaj.specified=true");

        // Get all the courtCaseList where ekunVyaj is null
        defaultCourtCaseShouldNotBeFound("ekunVyaj.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunVyajIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunVyaj is greater than or equal to DEFAULT_EKUN_VYAJ
        defaultCourtCaseShouldBeFound("ekunVyaj.greaterThanOrEqual=" + DEFAULT_EKUN_VYAJ);

        // Get all the courtCaseList where ekunVyaj is greater than or equal to UPDATED_EKUN_VYAJ
        defaultCourtCaseShouldNotBeFound("ekunVyaj.greaterThanOrEqual=" + UPDATED_EKUN_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunVyajIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunVyaj is less than or equal to DEFAULT_EKUN_VYAJ
        defaultCourtCaseShouldBeFound("ekunVyaj.lessThanOrEqual=" + DEFAULT_EKUN_VYAJ);

        // Get all the courtCaseList where ekunVyaj is less than or equal to SMALLER_EKUN_VYAJ
        defaultCourtCaseShouldNotBeFound("ekunVyaj.lessThanOrEqual=" + SMALLER_EKUN_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunVyajIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunVyaj is less than DEFAULT_EKUN_VYAJ
        defaultCourtCaseShouldNotBeFound("ekunVyaj.lessThan=" + DEFAULT_EKUN_VYAJ);

        // Get all the courtCaseList where ekunVyaj is less than UPDATED_EKUN_VYAJ
        defaultCourtCaseShouldBeFound("ekunVyaj.lessThan=" + UPDATED_EKUN_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEkunVyajIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where ekunVyaj is greater than DEFAULT_EKUN_VYAJ
        defaultCourtCaseShouldNotBeFound("ekunVyaj.greaterThan=" + DEFAULT_EKUN_VYAJ);

        // Get all the courtCaseList where ekunVyaj is greater than SMALLER_EKUN_VYAJ
        defaultCourtCaseShouldBeFound("ekunVyaj.greaterThan=" + SMALLER_EKUN_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamaVyajIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamaVyaj equals to DEFAULT_JAMA_VYAJ
        defaultCourtCaseShouldBeFound("jamaVyaj.equals=" + DEFAULT_JAMA_VYAJ);

        // Get all the courtCaseList where jamaVyaj equals to UPDATED_JAMA_VYAJ
        defaultCourtCaseShouldNotBeFound("jamaVyaj.equals=" + UPDATED_JAMA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamaVyajIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamaVyaj in DEFAULT_JAMA_VYAJ or UPDATED_JAMA_VYAJ
        defaultCourtCaseShouldBeFound("jamaVyaj.in=" + DEFAULT_JAMA_VYAJ + "," + UPDATED_JAMA_VYAJ);

        // Get all the courtCaseList where jamaVyaj equals to UPDATED_JAMA_VYAJ
        defaultCourtCaseShouldNotBeFound("jamaVyaj.in=" + UPDATED_JAMA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamaVyajIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamaVyaj is not null
        defaultCourtCaseShouldBeFound("jamaVyaj.specified=true");

        // Get all the courtCaseList where jamaVyaj is null
        defaultCourtCaseShouldNotBeFound("jamaVyaj.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamaVyajIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamaVyaj is greater than or equal to DEFAULT_JAMA_VYAJ
        defaultCourtCaseShouldBeFound("jamaVyaj.greaterThanOrEqual=" + DEFAULT_JAMA_VYAJ);

        // Get all the courtCaseList where jamaVyaj is greater than or equal to UPDATED_JAMA_VYAJ
        defaultCourtCaseShouldNotBeFound("jamaVyaj.greaterThanOrEqual=" + UPDATED_JAMA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamaVyajIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamaVyaj is less than or equal to DEFAULT_JAMA_VYAJ
        defaultCourtCaseShouldBeFound("jamaVyaj.lessThanOrEqual=" + DEFAULT_JAMA_VYAJ);

        // Get all the courtCaseList where jamaVyaj is less than or equal to SMALLER_JAMA_VYAJ
        defaultCourtCaseShouldNotBeFound("jamaVyaj.lessThanOrEqual=" + SMALLER_JAMA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamaVyajIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamaVyaj is less than DEFAULT_JAMA_VYAJ
        defaultCourtCaseShouldNotBeFound("jamaVyaj.lessThan=" + DEFAULT_JAMA_VYAJ);

        // Get all the courtCaseList where jamaVyaj is less than UPDATED_JAMA_VYAJ
        defaultCourtCaseShouldBeFound("jamaVyaj.lessThan=" + UPDATED_JAMA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamaVyajIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamaVyaj is greater than DEFAULT_JAMA_VYAJ
        defaultCourtCaseShouldNotBeFound("jamaVyaj.greaterThan=" + DEFAULT_JAMA_VYAJ);

        // Get all the courtCaseList where jamaVyaj is greater than SMALLER_JAMA_VYAJ
        defaultCourtCaseShouldBeFound("jamaVyaj.greaterThan=" + SMALLER_JAMA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDandVyajIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dandVyaj equals to DEFAULT_DAND_VYAJ
        defaultCourtCaseShouldBeFound("dandVyaj.equals=" + DEFAULT_DAND_VYAJ);

        // Get all the courtCaseList where dandVyaj equals to UPDATED_DAND_VYAJ
        defaultCourtCaseShouldNotBeFound("dandVyaj.equals=" + UPDATED_DAND_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDandVyajIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dandVyaj in DEFAULT_DAND_VYAJ or UPDATED_DAND_VYAJ
        defaultCourtCaseShouldBeFound("dandVyaj.in=" + DEFAULT_DAND_VYAJ + "," + UPDATED_DAND_VYAJ);

        // Get all the courtCaseList where dandVyaj equals to UPDATED_DAND_VYAJ
        defaultCourtCaseShouldNotBeFound("dandVyaj.in=" + UPDATED_DAND_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDandVyajIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dandVyaj is not null
        defaultCourtCaseShouldBeFound("dandVyaj.specified=true");

        // Get all the courtCaseList where dandVyaj is null
        defaultCourtCaseShouldNotBeFound("dandVyaj.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDandVyajIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dandVyaj is greater than or equal to DEFAULT_DAND_VYAJ
        defaultCourtCaseShouldBeFound("dandVyaj.greaterThanOrEqual=" + DEFAULT_DAND_VYAJ);

        // Get all the courtCaseList where dandVyaj is greater than or equal to UPDATED_DAND_VYAJ
        defaultCourtCaseShouldNotBeFound("dandVyaj.greaterThanOrEqual=" + UPDATED_DAND_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDandVyajIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dandVyaj is less than or equal to DEFAULT_DAND_VYAJ
        defaultCourtCaseShouldBeFound("dandVyaj.lessThanOrEqual=" + DEFAULT_DAND_VYAJ);

        // Get all the courtCaseList where dandVyaj is less than or equal to SMALLER_DAND_VYAJ
        defaultCourtCaseShouldNotBeFound("dandVyaj.lessThanOrEqual=" + SMALLER_DAND_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDandVyajIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dandVyaj is less than DEFAULT_DAND_VYAJ
        defaultCourtCaseShouldNotBeFound("dandVyaj.lessThan=" + DEFAULT_DAND_VYAJ);

        // Get all the courtCaseList where dandVyaj is less than UPDATED_DAND_VYAJ
        defaultCourtCaseShouldBeFound("dandVyaj.lessThan=" + UPDATED_DAND_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDandVyajIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dandVyaj is greater than DEFAULT_DAND_VYAJ
        defaultCourtCaseShouldNotBeFound("dandVyaj.greaterThan=" + DEFAULT_DAND_VYAJ);

        // Get all the courtCaseList where dandVyaj is greater than SMALLER_DAND_VYAJ
        defaultCourtCaseShouldBeFound("dandVyaj.greaterThan=" + SMALLER_DAND_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjRakkamIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjRakkam equals to DEFAULT_KARJ_RAKKAM
        defaultCourtCaseShouldBeFound("karjRakkam.equals=" + DEFAULT_KARJ_RAKKAM);

        // Get all the courtCaseList where karjRakkam equals to UPDATED_KARJ_RAKKAM
        defaultCourtCaseShouldNotBeFound("karjRakkam.equals=" + UPDATED_KARJ_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjRakkamIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjRakkam in DEFAULT_KARJ_RAKKAM or UPDATED_KARJ_RAKKAM
        defaultCourtCaseShouldBeFound("karjRakkam.in=" + DEFAULT_KARJ_RAKKAM + "," + UPDATED_KARJ_RAKKAM);

        // Get all the courtCaseList where karjRakkam equals to UPDATED_KARJ_RAKKAM
        defaultCourtCaseShouldNotBeFound("karjRakkam.in=" + UPDATED_KARJ_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjRakkamIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjRakkam is not null
        defaultCourtCaseShouldBeFound("karjRakkam.specified=true");

        // Get all the courtCaseList where karjRakkam is null
        defaultCourtCaseShouldNotBeFound("karjRakkam.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjRakkamIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjRakkam is greater than or equal to DEFAULT_KARJ_RAKKAM
        defaultCourtCaseShouldBeFound("karjRakkam.greaterThanOrEqual=" + DEFAULT_KARJ_RAKKAM);

        // Get all the courtCaseList where karjRakkam is greater than or equal to UPDATED_KARJ_RAKKAM
        defaultCourtCaseShouldNotBeFound("karjRakkam.greaterThanOrEqual=" + UPDATED_KARJ_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjRakkamIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjRakkam is less than or equal to DEFAULT_KARJ_RAKKAM
        defaultCourtCaseShouldBeFound("karjRakkam.lessThanOrEqual=" + DEFAULT_KARJ_RAKKAM);

        // Get all the courtCaseList where karjRakkam is less than or equal to SMALLER_KARJ_RAKKAM
        defaultCourtCaseShouldNotBeFound("karjRakkam.lessThanOrEqual=" + SMALLER_KARJ_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjRakkamIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjRakkam is less than DEFAULT_KARJ_RAKKAM
        defaultCourtCaseShouldNotBeFound("karjRakkam.lessThan=" + DEFAULT_KARJ_RAKKAM);

        // Get all the courtCaseList where karjRakkam is less than UPDATED_KARJ_RAKKAM
        defaultCourtCaseShouldBeFound("karjRakkam.lessThan=" + UPDATED_KARJ_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjRakkamIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjRakkam is greater than DEFAULT_KARJ_RAKKAM
        defaultCourtCaseShouldNotBeFound("karjRakkam.greaterThan=" + DEFAULT_KARJ_RAKKAM);

        // Get all the courtCaseList where karjRakkam is greater than SMALLER_KARJ_RAKKAM
        defaultCourtCaseShouldBeFound("karjRakkam.greaterThan=" + SMALLER_KARJ_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByThakDinnankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where thakDinnank equals to DEFAULT_THAK_DINNANK
        defaultCourtCaseShouldBeFound("thakDinnank.equals=" + DEFAULT_THAK_DINNANK);

        // Get all the courtCaseList where thakDinnank equals to UPDATED_THAK_DINNANK
        defaultCourtCaseShouldNotBeFound("thakDinnank.equals=" + UPDATED_THAK_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByThakDinnankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where thakDinnank in DEFAULT_THAK_DINNANK or UPDATED_THAK_DINNANK
        defaultCourtCaseShouldBeFound("thakDinnank.in=" + DEFAULT_THAK_DINNANK + "," + UPDATED_THAK_DINNANK);

        // Get all the courtCaseList where thakDinnank equals to UPDATED_THAK_DINNANK
        defaultCourtCaseShouldNotBeFound("thakDinnank.in=" + UPDATED_THAK_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByThakDinnankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where thakDinnank is not null
        defaultCourtCaseShouldBeFound("thakDinnank.specified=true");

        // Get all the courtCaseList where thakDinnank is null
        defaultCourtCaseShouldNotBeFound("thakDinnank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjDinnankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjDinnank equals to DEFAULT_KARJ_DINNANK
        defaultCourtCaseShouldBeFound("karjDinnank.equals=" + DEFAULT_KARJ_DINNANK);

        // Get all the courtCaseList where karjDinnank equals to UPDATED_KARJ_DINNANK
        defaultCourtCaseShouldNotBeFound("karjDinnank.equals=" + UPDATED_KARJ_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjDinnankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjDinnank in DEFAULT_KARJ_DINNANK or UPDATED_KARJ_DINNANK
        defaultCourtCaseShouldBeFound("karjDinnank.in=" + DEFAULT_KARJ_DINNANK + "," + UPDATED_KARJ_DINNANK);

        // Get all the courtCaseList where karjDinnank equals to UPDATED_KARJ_DINNANK
        defaultCourtCaseShouldNotBeFound("karjDinnank.in=" + UPDATED_KARJ_DINNANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByKarjDinnankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where karjDinnank is not null
        defaultCourtCaseShouldBeFound("karjDinnank.specified=true");

        // Get all the courtCaseList where karjDinnank is null
        defaultCourtCaseShouldNotBeFound("karjDinnank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByMudatSampteDinankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where mudatSampteDinank equals to DEFAULT_MUDAT_SAMPTE_DINANK
        defaultCourtCaseShouldBeFound("mudatSampteDinank.equals=" + DEFAULT_MUDAT_SAMPTE_DINANK);

        // Get all the courtCaseList where mudatSampteDinank equals to UPDATED_MUDAT_SAMPTE_DINANK
        defaultCourtCaseShouldNotBeFound("mudatSampteDinank.equals=" + UPDATED_MUDAT_SAMPTE_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByMudatSampteDinankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where mudatSampteDinank in DEFAULT_MUDAT_SAMPTE_DINANK or UPDATED_MUDAT_SAMPTE_DINANK
        defaultCourtCaseShouldBeFound("mudatSampteDinank.in=" + DEFAULT_MUDAT_SAMPTE_DINANK + "," + UPDATED_MUDAT_SAMPTE_DINANK);

        // Get all the courtCaseList where mudatSampteDinank equals to UPDATED_MUDAT_SAMPTE_DINANK
        defaultCourtCaseShouldNotBeFound("mudatSampteDinank.in=" + UPDATED_MUDAT_SAMPTE_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByMudatSampteDinankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where mudatSampteDinank is not null
        defaultCourtCaseShouldBeFound("mudatSampteDinank.specified=true");

        // Get all the courtCaseList where mudatSampteDinank is null
        defaultCourtCaseShouldNotBeFound("mudatSampteDinank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByMudatIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where mudat equals to DEFAULT_MUDAT
        defaultCourtCaseShouldBeFound("mudat.equals=" + DEFAULT_MUDAT);

        // Get all the courtCaseList where mudat equals to UPDATED_MUDAT
        defaultCourtCaseShouldNotBeFound("mudat.equals=" + UPDATED_MUDAT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByMudatIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where mudat in DEFAULT_MUDAT or UPDATED_MUDAT
        defaultCourtCaseShouldBeFound("mudat.in=" + DEFAULT_MUDAT + "," + UPDATED_MUDAT);

        // Get all the courtCaseList where mudat equals to UPDATED_MUDAT
        defaultCourtCaseShouldNotBeFound("mudat.in=" + UPDATED_MUDAT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByMudatIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where mudat is not null
        defaultCourtCaseShouldBeFound("mudat.specified=true");

        // Get all the courtCaseList where mudat is null
        defaultCourtCaseShouldNotBeFound("mudat.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByMudatContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where mudat contains DEFAULT_MUDAT
        defaultCourtCaseShouldBeFound("mudat.contains=" + DEFAULT_MUDAT);

        // Get all the courtCaseList where mudat contains UPDATED_MUDAT
        defaultCourtCaseShouldNotBeFound("mudat.contains=" + UPDATED_MUDAT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByMudatNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where mudat does not contain DEFAULT_MUDAT
        defaultCourtCaseShouldNotBeFound("mudat.doesNotContain=" + DEFAULT_MUDAT);

        // Get all the courtCaseList where mudat does not contain UPDATED_MUDAT
        defaultCourtCaseShouldBeFound("mudat.doesNotContain=" + UPDATED_MUDAT);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyaj equals to DEFAULT_VYAJ
        defaultCourtCaseShouldBeFound("vyaj.equals=" + DEFAULT_VYAJ);

        // Get all the courtCaseList where vyaj equals to UPDATED_VYAJ
        defaultCourtCaseShouldNotBeFound("vyaj.equals=" + UPDATED_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyaj in DEFAULT_VYAJ or UPDATED_VYAJ
        defaultCourtCaseShouldBeFound("vyaj.in=" + DEFAULT_VYAJ + "," + UPDATED_VYAJ);

        // Get all the courtCaseList where vyaj equals to UPDATED_VYAJ
        defaultCourtCaseShouldNotBeFound("vyaj.in=" + UPDATED_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyaj is not null
        defaultCourtCaseShouldBeFound("vyaj.specified=true");

        // Get all the courtCaseList where vyaj is null
        defaultCourtCaseShouldNotBeFound("vyaj.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyaj contains DEFAULT_VYAJ
        defaultCourtCaseShouldBeFound("vyaj.contains=" + DEFAULT_VYAJ);

        // Get all the courtCaseList where vyaj contains UPDATED_VYAJ
        defaultCourtCaseShouldNotBeFound("vyaj.contains=" + UPDATED_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyaj does not contain DEFAULT_VYAJ
        defaultCourtCaseShouldNotBeFound("vyaj.doesNotContain=" + DEFAULT_VYAJ);

        // Get all the courtCaseList where vyaj does not contain UPDATED_VYAJ
        defaultCourtCaseShouldBeFound("vyaj.doesNotContain=" + UPDATED_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByHaptaRakkamIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where haptaRakkam equals to DEFAULT_HAPTA_RAKKAM
        defaultCourtCaseShouldBeFound("haptaRakkam.equals=" + DEFAULT_HAPTA_RAKKAM);

        // Get all the courtCaseList where haptaRakkam equals to UPDATED_HAPTA_RAKKAM
        defaultCourtCaseShouldNotBeFound("haptaRakkam.equals=" + UPDATED_HAPTA_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByHaptaRakkamIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where haptaRakkam in DEFAULT_HAPTA_RAKKAM or UPDATED_HAPTA_RAKKAM
        defaultCourtCaseShouldBeFound("haptaRakkam.in=" + DEFAULT_HAPTA_RAKKAM + "," + UPDATED_HAPTA_RAKKAM);

        // Get all the courtCaseList where haptaRakkam equals to UPDATED_HAPTA_RAKKAM
        defaultCourtCaseShouldNotBeFound("haptaRakkam.in=" + UPDATED_HAPTA_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByHaptaRakkamIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where haptaRakkam is not null
        defaultCourtCaseShouldBeFound("haptaRakkam.specified=true");

        // Get all the courtCaseList where haptaRakkam is null
        defaultCourtCaseShouldNotBeFound("haptaRakkam.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByHaptaRakkamIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where haptaRakkam is greater than or equal to DEFAULT_HAPTA_RAKKAM
        defaultCourtCaseShouldBeFound("haptaRakkam.greaterThanOrEqual=" + DEFAULT_HAPTA_RAKKAM);

        // Get all the courtCaseList where haptaRakkam is greater than or equal to UPDATED_HAPTA_RAKKAM
        defaultCourtCaseShouldNotBeFound("haptaRakkam.greaterThanOrEqual=" + UPDATED_HAPTA_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByHaptaRakkamIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where haptaRakkam is less than or equal to DEFAULT_HAPTA_RAKKAM
        defaultCourtCaseShouldBeFound("haptaRakkam.lessThanOrEqual=" + DEFAULT_HAPTA_RAKKAM);

        // Get all the courtCaseList where haptaRakkam is less than or equal to SMALLER_HAPTA_RAKKAM
        defaultCourtCaseShouldNotBeFound("haptaRakkam.lessThanOrEqual=" + SMALLER_HAPTA_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByHaptaRakkamIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where haptaRakkam is less than DEFAULT_HAPTA_RAKKAM
        defaultCourtCaseShouldNotBeFound("haptaRakkam.lessThan=" + DEFAULT_HAPTA_RAKKAM);

        // Get all the courtCaseList where haptaRakkam is less than UPDATED_HAPTA_RAKKAM
        defaultCourtCaseShouldBeFound("haptaRakkam.lessThan=" + UPDATED_HAPTA_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByHaptaRakkamIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where haptaRakkam is greater than DEFAULT_HAPTA_RAKKAM
        defaultCourtCaseShouldNotBeFound("haptaRakkam.greaterThan=" + DEFAULT_HAPTA_RAKKAM);

        // Get all the courtCaseList where haptaRakkam is greater than SMALLER_HAPTA_RAKKAM
        defaultCourtCaseShouldBeFound("haptaRakkam.greaterThan=" + SMALLER_HAPTA_RAKKAM);
    }

    @Test
    @Transactional
    void getAllCourtCasesByShakhaVevsthapakIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where shakhaVevsthapak equals to DEFAULT_SHAKHA_VEVSTHAPAK
        defaultCourtCaseShouldBeFound("shakhaVevsthapak.equals=" + DEFAULT_SHAKHA_VEVSTHAPAK);

        // Get all the courtCaseList where shakhaVevsthapak equals to UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseShouldNotBeFound("shakhaVevsthapak.equals=" + UPDATED_SHAKHA_VEVSTHAPAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByShakhaVevsthapakIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where shakhaVevsthapak in DEFAULT_SHAKHA_VEVSTHAPAK or UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseShouldBeFound("shakhaVevsthapak.in=" + DEFAULT_SHAKHA_VEVSTHAPAK + "," + UPDATED_SHAKHA_VEVSTHAPAK);

        // Get all the courtCaseList where shakhaVevsthapak equals to UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseShouldNotBeFound("shakhaVevsthapak.in=" + UPDATED_SHAKHA_VEVSTHAPAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByShakhaVevsthapakIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where shakhaVevsthapak is not null
        defaultCourtCaseShouldBeFound("shakhaVevsthapak.specified=true");

        // Get all the courtCaseList where shakhaVevsthapak is null
        defaultCourtCaseShouldNotBeFound("shakhaVevsthapak.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByShakhaVevsthapakContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where shakhaVevsthapak contains DEFAULT_SHAKHA_VEVSTHAPAK
        defaultCourtCaseShouldBeFound("shakhaVevsthapak.contains=" + DEFAULT_SHAKHA_VEVSTHAPAK);

        // Get all the courtCaseList where shakhaVevsthapak contains UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseShouldNotBeFound("shakhaVevsthapak.contains=" + UPDATED_SHAKHA_VEVSTHAPAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByShakhaVevsthapakNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where shakhaVevsthapak does not contain DEFAULT_SHAKHA_VEVSTHAPAK
        defaultCourtCaseShouldNotBeFound("shakhaVevsthapak.doesNotContain=" + DEFAULT_SHAKHA_VEVSTHAPAK);

        // Get all the courtCaseList where shakhaVevsthapak does not contain UPDATED_SHAKHA_VEVSTHAPAK
        defaultCourtCaseShouldBeFound("shakhaVevsthapak.doesNotContain=" + UPDATED_SHAKHA_VEVSTHAPAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySuchakIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where suchak equals to DEFAULT_SUCHAK
        defaultCourtCaseShouldBeFound("suchak.equals=" + DEFAULT_SUCHAK);

        // Get all the courtCaseList where suchak equals to UPDATED_SUCHAK
        defaultCourtCaseShouldNotBeFound("suchak.equals=" + UPDATED_SUCHAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySuchakIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where suchak in DEFAULT_SUCHAK or UPDATED_SUCHAK
        defaultCourtCaseShouldBeFound("suchak.in=" + DEFAULT_SUCHAK + "," + UPDATED_SUCHAK);

        // Get all the courtCaseList where suchak equals to UPDATED_SUCHAK
        defaultCourtCaseShouldNotBeFound("suchak.in=" + UPDATED_SUCHAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySuchakIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where suchak is not null
        defaultCourtCaseShouldBeFound("suchak.specified=true");

        // Get all the courtCaseList where suchak is null
        defaultCourtCaseShouldNotBeFound("suchak.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySuchakContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where suchak contains DEFAULT_SUCHAK
        defaultCourtCaseShouldBeFound("suchak.contains=" + DEFAULT_SUCHAK);

        // Get all the courtCaseList where suchak contains UPDATED_SUCHAK
        defaultCourtCaseShouldNotBeFound("suchak.contains=" + UPDATED_SUCHAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySuchakNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where suchak does not contain DEFAULT_SUCHAK
        defaultCourtCaseShouldNotBeFound("suchak.doesNotContain=" + DEFAULT_SUCHAK);

        // Get all the courtCaseList where suchak does not contain UPDATED_SUCHAK
        defaultCourtCaseShouldBeFound("suchak.doesNotContain=" + UPDATED_SUCHAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAnumodakIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where anumodak equals to DEFAULT_ANUMODAK
        defaultCourtCaseShouldBeFound("anumodak.equals=" + DEFAULT_ANUMODAK);

        // Get all the courtCaseList where anumodak equals to UPDATED_ANUMODAK
        defaultCourtCaseShouldNotBeFound("anumodak.equals=" + UPDATED_ANUMODAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAnumodakIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where anumodak in DEFAULT_ANUMODAK or UPDATED_ANUMODAK
        defaultCourtCaseShouldBeFound("anumodak.in=" + DEFAULT_ANUMODAK + "," + UPDATED_ANUMODAK);

        // Get all the courtCaseList where anumodak equals to UPDATED_ANUMODAK
        defaultCourtCaseShouldNotBeFound("anumodak.in=" + UPDATED_ANUMODAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAnumodakIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where anumodak is not null
        defaultCourtCaseShouldBeFound("anumodak.specified=true");

        // Get all the courtCaseList where anumodak is null
        defaultCourtCaseShouldNotBeFound("anumodak.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByAnumodakContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where anumodak contains DEFAULT_ANUMODAK
        defaultCourtCaseShouldBeFound("anumodak.contains=" + DEFAULT_ANUMODAK);

        // Get all the courtCaseList where anumodak contains UPDATED_ANUMODAK
        defaultCourtCaseShouldNotBeFound("anumodak.contains=" + UPDATED_ANUMODAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByAnumodakNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where anumodak does not contain DEFAULT_ANUMODAK
        defaultCourtCaseShouldNotBeFound("anumodak.doesNotContain=" + DEFAULT_ANUMODAK);

        // Get all the courtCaseList where anumodak does not contain UPDATED_ANUMODAK
        defaultCourtCaseShouldBeFound("anumodak.doesNotContain=" + UPDATED_ANUMODAK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDavaIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dava equals to DEFAULT_DAVA
        defaultCourtCaseShouldBeFound("dava.equals=" + DEFAULT_DAVA);

        // Get all the courtCaseList where dava equals to UPDATED_DAVA
        defaultCourtCaseShouldNotBeFound("dava.equals=" + UPDATED_DAVA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDavaIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dava in DEFAULT_DAVA or UPDATED_DAVA
        defaultCourtCaseShouldBeFound("dava.in=" + DEFAULT_DAVA + "," + UPDATED_DAVA);

        // Get all the courtCaseList where dava equals to UPDATED_DAVA
        defaultCourtCaseShouldNotBeFound("dava.in=" + UPDATED_DAVA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDavaIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dava is not null
        defaultCourtCaseShouldBeFound("dava.specified=true");

        // Get all the courtCaseList where dava is null
        defaultCourtCaseShouldNotBeFound("dava.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByDavaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dava is greater than or equal to DEFAULT_DAVA
        defaultCourtCaseShouldBeFound("dava.greaterThanOrEqual=" + DEFAULT_DAVA);

        // Get all the courtCaseList where dava is greater than or equal to UPDATED_DAVA
        defaultCourtCaseShouldNotBeFound("dava.greaterThanOrEqual=" + UPDATED_DAVA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDavaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dava is less than or equal to DEFAULT_DAVA
        defaultCourtCaseShouldBeFound("dava.lessThanOrEqual=" + DEFAULT_DAVA);

        // Get all the courtCaseList where dava is less than or equal to SMALLER_DAVA
        defaultCourtCaseShouldNotBeFound("dava.lessThanOrEqual=" + SMALLER_DAVA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDavaIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dava is less than DEFAULT_DAVA
        defaultCourtCaseShouldNotBeFound("dava.lessThan=" + DEFAULT_DAVA);

        // Get all the courtCaseList where dava is less than UPDATED_DAVA
        defaultCourtCaseShouldBeFound("dava.lessThan=" + UPDATED_DAVA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByDavaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where dava is greater than DEFAULT_DAVA
        defaultCourtCaseShouldNotBeFound("dava.greaterThan=" + DEFAULT_DAVA);

        // Get all the courtCaseList where dava is greater than SMALLER_DAVA
        defaultCourtCaseShouldBeFound("dava.greaterThan=" + SMALLER_DAVA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajDarIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyajDar equals to DEFAULT_VYAJ_DAR
        defaultCourtCaseShouldBeFound("vyajDar.equals=" + DEFAULT_VYAJ_DAR);

        // Get all the courtCaseList where vyajDar equals to UPDATED_VYAJ_DAR
        defaultCourtCaseShouldNotBeFound("vyajDar.equals=" + UPDATED_VYAJ_DAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajDarIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyajDar in DEFAULT_VYAJ_DAR or UPDATED_VYAJ_DAR
        defaultCourtCaseShouldBeFound("vyajDar.in=" + DEFAULT_VYAJ_DAR + "," + UPDATED_VYAJ_DAR);

        // Get all the courtCaseList where vyajDar equals to UPDATED_VYAJ_DAR
        defaultCourtCaseShouldNotBeFound("vyajDar.in=" + UPDATED_VYAJ_DAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajDarIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyajDar is not null
        defaultCourtCaseShouldBeFound("vyajDar.specified=true");

        // Get all the courtCaseList where vyajDar is null
        defaultCourtCaseShouldNotBeFound("vyajDar.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajDarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyajDar is greater than or equal to DEFAULT_VYAJ_DAR
        defaultCourtCaseShouldBeFound("vyajDar.greaterThanOrEqual=" + DEFAULT_VYAJ_DAR);

        // Get all the courtCaseList where vyajDar is greater than or equal to UPDATED_VYAJ_DAR
        defaultCourtCaseShouldNotBeFound("vyajDar.greaterThanOrEqual=" + UPDATED_VYAJ_DAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajDarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyajDar is less than or equal to DEFAULT_VYAJ_DAR
        defaultCourtCaseShouldBeFound("vyajDar.lessThanOrEqual=" + DEFAULT_VYAJ_DAR);

        // Get all the courtCaseList where vyajDar is less than or equal to SMALLER_VYAJ_DAR
        defaultCourtCaseShouldNotBeFound("vyajDar.lessThanOrEqual=" + SMALLER_VYAJ_DAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajDarIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyajDar is less than DEFAULT_VYAJ_DAR
        defaultCourtCaseShouldNotBeFound("vyajDar.lessThan=" + DEFAULT_VYAJ_DAR);

        // Get all the courtCaseList where vyajDar is less than UPDATED_VYAJ_DAR
        defaultCourtCaseShouldBeFound("vyajDar.lessThan=" + UPDATED_VYAJ_DAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVyajDarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vyajDar is greater than DEFAULT_VYAJ_DAR
        defaultCourtCaseShouldNotBeFound("vyajDar.greaterThan=" + DEFAULT_VYAJ_DAR);

        // Get all the courtCaseList where vyajDar is greater than SMALLER_VYAJ_DAR
        defaultCourtCaseShouldBeFound("vyajDar.greaterThan=" + SMALLER_VYAJ_DAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySarchargeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sarcharge equals to DEFAULT_SARCHARGE
        defaultCourtCaseShouldBeFound("sarcharge.equals=" + DEFAULT_SARCHARGE);

        // Get all the courtCaseList where sarcharge equals to UPDATED_SARCHARGE
        defaultCourtCaseShouldNotBeFound("sarcharge.equals=" + UPDATED_SARCHARGE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySarchargeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sarcharge in DEFAULT_SARCHARGE or UPDATED_SARCHARGE
        defaultCourtCaseShouldBeFound("sarcharge.in=" + DEFAULT_SARCHARGE + "," + UPDATED_SARCHARGE);

        // Get all the courtCaseList where sarcharge equals to UPDATED_SARCHARGE
        defaultCourtCaseShouldNotBeFound("sarcharge.in=" + UPDATED_SARCHARGE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySarchargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sarcharge is not null
        defaultCourtCaseShouldBeFound("sarcharge.specified=true");

        // Get all the courtCaseList where sarcharge is null
        defaultCourtCaseShouldNotBeFound("sarcharge.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesBySarchargeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sarcharge is greater than or equal to DEFAULT_SARCHARGE
        defaultCourtCaseShouldBeFound("sarcharge.greaterThanOrEqual=" + DEFAULT_SARCHARGE);

        // Get all the courtCaseList where sarcharge is greater than or equal to UPDATED_SARCHARGE
        defaultCourtCaseShouldNotBeFound("sarcharge.greaterThanOrEqual=" + UPDATED_SARCHARGE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySarchargeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sarcharge is less than or equal to DEFAULT_SARCHARGE
        defaultCourtCaseShouldBeFound("sarcharge.lessThanOrEqual=" + DEFAULT_SARCHARGE);

        // Get all the courtCaseList where sarcharge is less than or equal to SMALLER_SARCHARGE
        defaultCourtCaseShouldNotBeFound("sarcharge.lessThanOrEqual=" + SMALLER_SARCHARGE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySarchargeIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sarcharge is less than DEFAULT_SARCHARGE
        defaultCourtCaseShouldNotBeFound("sarcharge.lessThan=" + DEFAULT_SARCHARGE);

        // Get all the courtCaseList where sarcharge is less than UPDATED_SARCHARGE
        defaultCourtCaseShouldBeFound("sarcharge.lessThan=" + UPDATED_SARCHARGE);
    }

    @Test
    @Transactional
    void getAllCourtCasesBySarchargeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where sarcharge is greater than DEFAULT_SARCHARGE
        defaultCourtCaseShouldNotBeFound("sarcharge.greaterThan=" + DEFAULT_SARCHARGE);

        // Get all the courtCaseList where sarcharge is greater than SMALLER_SARCHARGE
        defaultCourtCaseShouldBeFound("sarcharge.greaterThan=" + SMALLER_SARCHARGE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJyadaVyajIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jyadaVyaj equals to DEFAULT_JYADA_VYAJ
        defaultCourtCaseShouldBeFound("jyadaVyaj.equals=" + DEFAULT_JYADA_VYAJ);

        // Get all the courtCaseList where jyadaVyaj equals to UPDATED_JYADA_VYAJ
        defaultCourtCaseShouldNotBeFound("jyadaVyaj.equals=" + UPDATED_JYADA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJyadaVyajIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jyadaVyaj in DEFAULT_JYADA_VYAJ or UPDATED_JYADA_VYAJ
        defaultCourtCaseShouldBeFound("jyadaVyaj.in=" + DEFAULT_JYADA_VYAJ + "," + UPDATED_JYADA_VYAJ);

        // Get all the courtCaseList where jyadaVyaj equals to UPDATED_JYADA_VYAJ
        defaultCourtCaseShouldNotBeFound("jyadaVyaj.in=" + UPDATED_JYADA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJyadaVyajIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jyadaVyaj is not null
        defaultCourtCaseShouldBeFound("jyadaVyaj.specified=true");

        // Get all the courtCaseList where jyadaVyaj is null
        defaultCourtCaseShouldNotBeFound("jyadaVyaj.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByJyadaVyajIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jyadaVyaj is greater than or equal to DEFAULT_JYADA_VYAJ
        defaultCourtCaseShouldBeFound("jyadaVyaj.greaterThanOrEqual=" + DEFAULT_JYADA_VYAJ);

        // Get all the courtCaseList where jyadaVyaj is greater than or equal to UPDATED_JYADA_VYAJ
        defaultCourtCaseShouldNotBeFound("jyadaVyaj.greaterThanOrEqual=" + UPDATED_JYADA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJyadaVyajIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jyadaVyaj is less than or equal to DEFAULT_JYADA_VYAJ
        defaultCourtCaseShouldBeFound("jyadaVyaj.lessThanOrEqual=" + DEFAULT_JYADA_VYAJ);

        // Get all the courtCaseList where jyadaVyaj is less than or equal to SMALLER_JYADA_VYAJ
        defaultCourtCaseShouldNotBeFound("jyadaVyaj.lessThanOrEqual=" + SMALLER_JYADA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJyadaVyajIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jyadaVyaj is less than DEFAULT_JYADA_VYAJ
        defaultCourtCaseShouldNotBeFound("jyadaVyaj.lessThan=" + DEFAULT_JYADA_VYAJ);

        // Get all the courtCaseList where jyadaVyaj is less than UPDATED_JYADA_VYAJ
        defaultCourtCaseShouldBeFound("jyadaVyaj.lessThan=" + UPDATED_JYADA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJyadaVyajIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jyadaVyaj is greater than DEFAULT_JYADA_VYAJ
        defaultCourtCaseShouldNotBeFound("jyadaVyaj.greaterThan=" + DEFAULT_JYADA_VYAJ);

        // Get all the courtCaseList where jyadaVyaj is greater than SMALLER_JYADA_VYAJ
        defaultCourtCaseShouldBeFound("jyadaVyaj.greaterThan=" + SMALLER_JYADA_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByYeneVyajIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where yeneVyaj equals to DEFAULT_YENE_VYAJ
        defaultCourtCaseShouldBeFound("yeneVyaj.equals=" + DEFAULT_YENE_VYAJ);

        // Get all the courtCaseList where yeneVyaj equals to UPDATED_YENE_VYAJ
        defaultCourtCaseShouldNotBeFound("yeneVyaj.equals=" + UPDATED_YENE_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByYeneVyajIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where yeneVyaj in DEFAULT_YENE_VYAJ or UPDATED_YENE_VYAJ
        defaultCourtCaseShouldBeFound("yeneVyaj.in=" + DEFAULT_YENE_VYAJ + "," + UPDATED_YENE_VYAJ);

        // Get all the courtCaseList where yeneVyaj equals to UPDATED_YENE_VYAJ
        defaultCourtCaseShouldNotBeFound("yeneVyaj.in=" + UPDATED_YENE_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByYeneVyajIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where yeneVyaj is not null
        defaultCourtCaseShouldBeFound("yeneVyaj.specified=true");

        // Get all the courtCaseList where yeneVyaj is null
        defaultCourtCaseShouldNotBeFound("yeneVyaj.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByYeneVyajIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where yeneVyaj is greater than or equal to DEFAULT_YENE_VYAJ
        defaultCourtCaseShouldBeFound("yeneVyaj.greaterThanOrEqual=" + DEFAULT_YENE_VYAJ);

        // Get all the courtCaseList where yeneVyaj is greater than or equal to UPDATED_YENE_VYAJ
        defaultCourtCaseShouldNotBeFound("yeneVyaj.greaterThanOrEqual=" + UPDATED_YENE_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByYeneVyajIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where yeneVyaj is less than or equal to DEFAULT_YENE_VYAJ
        defaultCourtCaseShouldBeFound("yeneVyaj.lessThanOrEqual=" + DEFAULT_YENE_VYAJ);

        // Get all the courtCaseList where yeneVyaj is less than or equal to SMALLER_YENE_VYAJ
        defaultCourtCaseShouldNotBeFound("yeneVyaj.lessThanOrEqual=" + SMALLER_YENE_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByYeneVyajIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where yeneVyaj is less than DEFAULT_YENE_VYAJ
        defaultCourtCaseShouldNotBeFound("yeneVyaj.lessThan=" + DEFAULT_YENE_VYAJ);

        // Get all the courtCaseList where yeneVyaj is less than UPDATED_YENE_VYAJ
        defaultCourtCaseShouldBeFound("yeneVyaj.lessThan=" + UPDATED_YENE_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByYeneVyajIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where yeneVyaj is greater than DEFAULT_YENE_VYAJ
        defaultCourtCaseShouldNotBeFound("yeneVyaj.greaterThan=" + DEFAULT_YENE_VYAJ);

        // Get all the courtCaseList where yeneVyaj is greater than SMALLER_YENE_VYAJ
        defaultCourtCaseShouldBeFound("yeneVyaj.greaterThan=" + SMALLER_YENE_VYAJ);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliKharchIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliKharch equals to DEFAULT_VASULI_KHARCH
        defaultCourtCaseShouldBeFound("vasuliKharch.equals=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseList where vasuliKharch equals to UPDATED_VASULI_KHARCH
        defaultCourtCaseShouldNotBeFound("vasuliKharch.equals=" + UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliKharchIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliKharch in DEFAULT_VASULI_KHARCH or UPDATED_VASULI_KHARCH
        defaultCourtCaseShouldBeFound("vasuliKharch.in=" + DEFAULT_VASULI_KHARCH + "," + UPDATED_VASULI_KHARCH);

        // Get all the courtCaseList where vasuliKharch equals to UPDATED_VASULI_KHARCH
        defaultCourtCaseShouldNotBeFound("vasuliKharch.in=" + UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliKharchIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliKharch is not null
        defaultCourtCaseShouldBeFound("vasuliKharch.specified=true");

        // Get all the courtCaseList where vasuliKharch is null
        defaultCourtCaseShouldNotBeFound("vasuliKharch.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliKharchIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliKharch is greater than or equal to DEFAULT_VASULI_KHARCH
        defaultCourtCaseShouldBeFound("vasuliKharch.greaterThanOrEqual=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseList where vasuliKharch is greater than or equal to UPDATED_VASULI_KHARCH
        defaultCourtCaseShouldNotBeFound("vasuliKharch.greaterThanOrEqual=" + UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliKharchIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliKharch is less than or equal to DEFAULT_VASULI_KHARCH
        defaultCourtCaseShouldBeFound("vasuliKharch.lessThanOrEqual=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseList where vasuliKharch is less than or equal to SMALLER_VASULI_KHARCH
        defaultCourtCaseShouldNotBeFound("vasuliKharch.lessThanOrEqual=" + SMALLER_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliKharchIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliKharch is less than DEFAULT_VASULI_KHARCH
        defaultCourtCaseShouldNotBeFound("vasuliKharch.lessThan=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseList where vasuliKharch is less than UPDATED_VASULI_KHARCH
        defaultCourtCaseShouldBeFound("vasuliKharch.lessThan=" + UPDATED_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVasuliKharchIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vasuliKharch is greater than DEFAULT_VASULI_KHARCH
        defaultCourtCaseShouldNotBeFound("vasuliKharch.greaterThan=" + DEFAULT_VASULI_KHARCH);

        // Get all the courtCaseList where vasuliKharch is greater than SMALLER_VASULI_KHARCH
        defaultCourtCaseShouldBeFound("vasuliKharch.greaterThan=" + SMALLER_VASULI_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEtharKharchIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where etharKharch equals to DEFAULT_ETHAR_KHARCH
        defaultCourtCaseShouldBeFound("etharKharch.equals=" + DEFAULT_ETHAR_KHARCH);

        // Get all the courtCaseList where etharKharch equals to UPDATED_ETHAR_KHARCH
        defaultCourtCaseShouldNotBeFound("etharKharch.equals=" + UPDATED_ETHAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEtharKharchIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where etharKharch in DEFAULT_ETHAR_KHARCH or UPDATED_ETHAR_KHARCH
        defaultCourtCaseShouldBeFound("etharKharch.in=" + DEFAULT_ETHAR_KHARCH + "," + UPDATED_ETHAR_KHARCH);

        // Get all the courtCaseList where etharKharch equals to UPDATED_ETHAR_KHARCH
        defaultCourtCaseShouldNotBeFound("etharKharch.in=" + UPDATED_ETHAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEtharKharchIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where etharKharch is not null
        defaultCourtCaseShouldBeFound("etharKharch.specified=true");

        // Get all the courtCaseList where etharKharch is null
        defaultCourtCaseShouldNotBeFound("etharKharch.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByEtharKharchIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where etharKharch is greater than or equal to DEFAULT_ETHAR_KHARCH
        defaultCourtCaseShouldBeFound("etharKharch.greaterThanOrEqual=" + DEFAULT_ETHAR_KHARCH);

        // Get all the courtCaseList where etharKharch is greater than or equal to UPDATED_ETHAR_KHARCH
        defaultCourtCaseShouldNotBeFound("etharKharch.greaterThanOrEqual=" + UPDATED_ETHAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEtharKharchIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where etharKharch is less than or equal to DEFAULT_ETHAR_KHARCH
        defaultCourtCaseShouldBeFound("etharKharch.lessThanOrEqual=" + DEFAULT_ETHAR_KHARCH);

        // Get all the courtCaseList where etharKharch is less than or equal to SMALLER_ETHAR_KHARCH
        defaultCourtCaseShouldNotBeFound("etharKharch.lessThanOrEqual=" + SMALLER_ETHAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEtharKharchIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where etharKharch is less than DEFAULT_ETHAR_KHARCH
        defaultCourtCaseShouldNotBeFound("etharKharch.lessThan=" + DEFAULT_ETHAR_KHARCH);

        // Get all the courtCaseList where etharKharch is less than UPDATED_ETHAR_KHARCH
        defaultCourtCaseShouldBeFound("etharKharch.lessThan=" + UPDATED_ETHAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByEtharKharchIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where etharKharch is greater than DEFAULT_ETHAR_KHARCH
        defaultCourtCaseShouldNotBeFound("etharKharch.greaterThan=" + DEFAULT_ETHAR_KHARCH);

        // Get all the courtCaseList where etharKharch is greater than SMALLER_ETHAR_KHARCH
        defaultCourtCaseShouldBeFound("etharKharch.greaterThan=" + SMALLER_ETHAR_KHARCH);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVimaIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vima equals to DEFAULT_VIMA
        defaultCourtCaseShouldBeFound("vima.equals=" + DEFAULT_VIMA);

        // Get all the courtCaseList where vima equals to UPDATED_VIMA
        defaultCourtCaseShouldNotBeFound("vima.equals=" + UPDATED_VIMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVimaIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vima in DEFAULT_VIMA or UPDATED_VIMA
        defaultCourtCaseShouldBeFound("vima.in=" + DEFAULT_VIMA + "," + UPDATED_VIMA);

        // Get all the courtCaseList where vima equals to UPDATED_VIMA
        defaultCourtCaseShouldNotBeFound("vima.in=" + UPDATED_VIMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVimaIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vima is not null
        defaultCourtCaseShouldBeFound("vima.specified=true");

        // Get all the courtCaseList where vima is null
        defaultCourtCaseShouldNotBeFound("vima.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVimaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vima is greater than or equal to DEFAULT_VIMA
        defaultCourtCaseShouldBeFound("vima.greaterThanOrEqual=" + DEFAULT_VIMA);

        // Get all the courtCaseList where vima is greater than or equal to UPDATED_VIMA
        defaultCourtCaseShouldNotBeFound("vima.greaterThanOrEqual=" + UPDATED_VIMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVimaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vima is less than or equal to DEFAULT_VIMA
        defaultCourtCaseShouldBeFound("vima.lessThanOrEqual=" + DEFAULT_VIMA);

        // Get all the courtCaseList where vima is less than or equal to SMALLER_VIMA
        defaultCourtCaseShouldNotBeFound("vima.lessThanOrEqual=" + SMALLER_VIMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVimaIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vima is less than DEFAULT_VIMA
        defaultCourtCaseShouldNotBeFound("vima.lessThan=" + DEFAULT_VIMA);

        // Get all the courtCaseList where vima is less than UPDATED_VIMA
        defaultCourtCaseShouldBeFound("vima.lessThan=" + UPDATED_VIMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVimaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vima is greater than DEFAULT_VIMA
        defaultCourtCaseShouldNotBeFound("vima.greaterThan=" + DEFAULT_VIMA);

        // Get all the courtCaseList where vima is greater than SMALLER_VIMA
        defaultCourtCaseShouldBeFound("vima.greaterThan=" + SMALLER_VIMA);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where notice equals to DEFAULT_NOTICE
        defaultCourtCaseShouldBeFound("notice.equals=" + DEFAULT_NOTICE);

        // Get all the courtCaseList where notice equals to UPDATED_NOTICE
        defaultCourtCaseShouldNotBeFound("notice.equals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where notice in DEFAULT_NOTICE or UPDATED_NOTICE
        defaultCourtCaseShouldBeFound("notice.in=" + DEFAULT_NOTICE + "," + UPDATED_NOTICE);

        // Get all the courtCaseList where notice equals to UPDATED_NOTICE
        defaultCourtCaseShouldNotBeFound("notice.in=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where notice is not null
        defaultCourtCaseShouldBeFound("notice.specified=true");

        // Get all the courtCaseList where notice is null
        defaultCourtCaseShouldNotBeFound("notice.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where notice is greater than or equal to DEFAULT_NOTICE
        defaultCourtCaseShouldBeFound("notice.greaterThanOrEqual=" + DEFAULT_NOTICE);

        // Get all the courtCaseList where notice is greater than or equal to UPDATED_NOTICE
        defaultCourtCaseShouldNotBeFound("notice.greaterThanOrEqual=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where notice is less than or equal to DEFAULT_NOTICE
        defaultCourtCaseShouldBeFound("notice.lessThanOrEqual=" + DEFAULT_NOTICE);

        // Get all the courtCaseList where notice is less than or equal to SMALLER_NOTICE
        defaultCourtCaseShouldNotBeFound("notice.lessThanOrEqual=" + SMALLER_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where notice is less than DEFAULT_NOTICE
        defaultCourtCaseShouldNotBeFound("notice.lessThan=" + DEFAULT_NOTICE);

        // Get all the courtCaseList where notice is less than UPDATED_NOTICE
        defaultCourtCaseShouldBeFound("notice.lessThan=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where notice is greater than DEFAULT_NOTICE
        defaultCourtCaseShouldNotBeFound("notice.greaterThan=" + DEFAULT_NOTICE);

        // Get all the courtCaseList where notice is greater than SMALLER_NOTICE
        defaultCourtCaseShouldBeFound("notice.greaterThan=" + SMALLER_NOTICE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavNumber equals to DEFAULT_THARAV_NUMBER
        defaultCourtCaseShouldBeFound("tharavNumber.equals=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseList where tharavNumber equals to UPDATED_THARAV_NUMBER
        defaultCourtCaseShouldNotBeFound("tharavNumber.equals=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavNumberIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavNumber in DEFAULT_THARAV_NUMBER or UPDATED_THARAV_NUMBER
        defaultCourtCaseShouldBeFound("tharavNumber.in=" + DEFAULT_THARAV_NUMBER + "," + UPDATED_THARAV_NUMBER);

        // Get all the courtCaseList where tharavNumber equals to UPDATED_THARAV_NUMBER
        defaultCourtCaseShouldNotBeFound("tharavNumber.in=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavNumber is not null
        defaultCourtCaseShouldBeFound("tharavNumber.specified=true");

        // Get all the courtCaseList where tharavNumber is null
        defaultCourtCaseShouldNotBeFound("tharavNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavNumber is greater than or equal to DEFAULT_THARAV_NUMBER
        defaultCourtCaseShouldBeFound("tharavNumber.greaterThanOrEqual=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseList where tharavNumber is greater than or equal to UPDATED_THARAV_NUMBER
        defaultCourtCaseShouldNotBeFound("tharavNumber.greaterThanOrEqual=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavNumber is less than or equal to DEFAULT_THARAV_NUMBER
        defaultCourtCaseShouldBeFound("tharavNumber.lessThanOrEqual=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseList where tharavNumber is less than or equal to SMALLER_THARAV_NUMBER
        defaultCourtCaseShouldNotBeFound("tharavNumber.lessThanOrEqual=" + SMALLER_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavNumber is less than DEFAULT_THARAV_NUMBER
        defaultCourtCaseShouldNotBeFound("tharavNumber.lessThan=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseList where tharavNumber is less than UPDATED_THARAV_NUMBER
        defaultCourtCaseShouldBeFound("tharavNumber.lessThan=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavNumber is greater than DEFAULT_THARAV_NUMBER
        defaultCourtCaseShouldNotBeFound("tharavNumber.greaterThan=" + DEFAULT_THARAV_NUMBER);

        // Get all the courtCaseList where tharavNumber is greater than SMALLER_THARAV_NUMBER
        defaultCourtCaseShouldBeFound("tharavNumber.greaterThan=" + SMALLER_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavDinankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavDinank equals to DEFAULT_THARAV_DINANK
        defaultCourtCaseShouldBeFound("tharavDinank.equals=" + DEFAULT_THARAV_DINANK);

        // Get all the courtCaseList where tharavDinank equals to UPDATED_THARAV_DINANK
        defaultCourtCaseShouldNotBeFound("tharavDinank.equals=" + UPDATED_THARAV_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavDinankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavDinank in DEFAULT_THARAV_DINANK or UPDATED_THARAV_DINANK
        defaultCourtCaseShouldBeFound("tharavDinank.in=" + DEFAULT_THARAV_DINANK + "," + UPDATED_THARAV_DINANK);

        // Get all the courtCaseList where tharavDinank equals to UPDATED_THARAV_DINANK
        defaultCourtCaseShouldNotBeFound("tharavDinank.in=" + UPDATED_THARAV_DINANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTharavDinankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where tharavDinank is not null
        defaultCourtCaseShouldBeFound("tharavDinank.specified=true");

        // Get all the courtCaseList where tharavDinank is null
        defaultCourtCaseShouldNotBeFound("tharavDinank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVishayKramankIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vishayKramank equals to DEFAULT_VISHAY_KRAMANK
        defaultCourtCaseShouldBeFound("vishayKramank.equals=" + DEFAULT_VISHAY_KRAMANK);

        // Get all the courtCaseList where vishayKramank equals to UPDATED_VISHAY_KRAMANK
        defaultCourtCaseShouldNotBeFound("vishayKramank.equals=" + UPDATED_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVishayKramankIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vishayKramank in DEFAULT_VISHAY_KRAMANK or UPDATED_VISHAY_KRAMANK
        defaultCourtCaseShouldBeFound("vishayKramank.in=" + DEFAULT_VISHAY_KRAMANK + "," + UPDATED_VISHAY_KRAMANK);

        // Get all the courtCaseList where vishayKramank equals to UPDATED_VISHAY_KRAMANK
        defaultCourtCaseShouldNotBeFound("vishayKramank.in=" + UPDATED_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVishayKramankIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vishayKramank is not null
        defaultCourtCaseShouldBeFound("vishayKramank.specified=true");

        // Get all the courtCaseList where vishayKramank is null
        defaultCourtCaseShouldNotBeFound("vishayKramank.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVishayKramankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vishayKramank is greater than or equal to DEFAULT_VISHAY_KRAMANK
        defaultCourtCaseShouldBeFound("vishayKramank.greaterThanOrEqual=" + DEFAULT_VISHAY_KRAMANK);

        // Get all the courtCaseList where vishayKramank is greater than or equal to UPDATED_VISHAY_KRAMANK
        defaultCourtCaseShouldNotBeFound("vishayKramank.greaterThanOrEqual=" + UPDATED_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVishayKramankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vishayKramank is less than or equal to DEFAULT_VISHAY_KRAMANK
        defaultCourtCaseShouldBeFound("vishayKramank.lessThanOrEqual=" + DEFAULT_VISHAY_KRAMANK);

        // Get all the courtCaseList where vishayKramank is less than or equal to SMALLER_VISHAY_KRAMANK
        defaultCourtCaseShouldNotBeFound("vishayKramank.lessThanOrEqual=" + SMALLER_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVishayKramankIsLessThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vishayKramank is less than DEFAULT_VISHAY_KRAMANK
        defaultCourtCaseShouldNotBeFound("vishayKramank.lessThan=" + DEFAULT_VISHAY_KRAMANK);

        // Get all the courtCaseList where vishayKramank is less than UPDATED_VISHAY_KRAMANK
        defaultCourtCaseShouldBeFound("vishayKramank.lessThan=" + UPDATED_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVishayKramankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vishayKramank is greater than DEFAULT_VISHAY_KRAMANK
        defaultCourtCaseShouldNotBeFound("vishayKramank.greaterThan=" + DEFAULT_VISHAY_KRAMANK);

        // Get all the courtCaseList where vishayKramank is greater than SMALLER_VISHAY_KRAMANK
        defaultCourtCaseShouldBeFound("vishayKramank.greaterThan=" + SMALLER_VISHAY_KRAMANK);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeOneIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where noticeOne equals to DEFAULT_NOTICE_ONE
        defaultCourtCaseShouldBeFound("noticeOne.equals=" + DEFAULT_NOTICE_ONE);

        // Get all the courtCaseList where noticeOne equals to UPDATED_NOTICE_ONE
        defaultCourtCaseShouldNotBeFound("noticeOne.equals=" + UPDATED_NOTICE_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeOneIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where noticeOne in DEFAULT_NOTICE_ONE or UPDATED_NOTICE_ONE
        defaultCourtCaseShouldBeFound("noticeOne.in=" + DEFAULT_NOTICE_ONE + "," + UPDATED_NOTICE_ONE);

        // Get all the courtCaseList where noticeOne equals to UPDATED_NOTICE_ONE
        defaultCourtCaseShouldNotBeFound("noticeOne.in=" + UPDATED_NOTICE_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where noticeOne is not null
        defaultCourtCaseShouldBeFound("noticeOne.specified=true");

        // Get all the courtCaseList where noticeOne is null
        defaultCourtCaseShouldNotBeFound("noticeOne.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where noticeTwo equals to DEFAULT_NOTICE_TWO
        defaultCourtCaseShouldBeFound("noticeTwo.equals=" + DEFAULT_NOTICE_TWO);

        // Get all the courtCaseList where noticeTwo equals to UPDATED_NOTICE_TWO
        defaultCourtCaseShouldNotBeFound("noticeTwo.equals=" + UPDATED_NOTICE_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeTwoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where noticeTwo in DEFAULT_NOTICE_TWO or UPDATED_NOTICE_TWO
        defaultCourtCaseShouldBeFound("noticeTwo.in=" + DEFAULT_NOTICE_TWO + "," + UPDATED_NOTICE_TWO);

        // Get all the courtCaseList where noticeTwo equals to UPDATED_NOTICE_TWO
        defaultCourtCaseShouldNotBeFound("noticeTwo.in=" + UPDATED_NOTICE_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByNoticeTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where noticeTwo is not null
        defaultCourtCaseShouldBeFound("noticeTwo.specified=true");

        // Get all the courtCaseList where noticeTwo is null
        defaultCourtCaseShouldNotBeFound("noticeTwo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByWarIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where war equals to DEFAULT_WAR
        defaultCourtCaseShouldBeFound("war.equals=" + DEFAULT_WAR);

        // Get all the courtCaseList where war equals to UPDATED_WAR
        defaultCourtCaseShouldNotBeFound("war.equals=" + UPDATED_WAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByWarIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where war in DEFAULT_WAR or UPDATED_WAR
        defaultCourtCaseShouldBeFound("war.in=" + DEFAULT_WAR + "," + UPDATED_WAR);

        // Get all the courtCaseList where war equals to UPDATED_WAR
        defaultCourtCaseShouldNotBeFound("war.in=" + UPDATED_WAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByWarIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where war is not null
        defaultCourtCaseShouldBeFound("war.specified=true");

        // Get all the courtCaseList where war is null
        defaultCourtCaseShouldNotBeFound("war.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByWarContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where war contains DEFAULT_WAR
        defaultCourtCaseShouldBeFound("war.contains=" + DEFAULT_WAR);

        // Get all the courtCaseList where war contains UPDATED_WAR
        defaultCourtCaseShouldNotBeFound("war.contains=" + UPDATED_WAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByWarNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where war does not contain DEFAULT_WAR
        defaultCourtCaseShouldNotBeFound("war.doesNotContain=" + DEFAULT_WAR);

        // Get all the courtCaseList where war does not contain UPDATED_WAR
        defaultCourtCaseShouldBeFound("war.doesNotContain=" + UPDATED_WAR);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVelIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vel equals to DEFAULT_VEL
        defaultCourtCaseShouldBeFound("vel.equals=" + DEFAULT_VEL);

        // Get all the courtCaseList where vel equals to UPDATED_VEL
        defaultCourtCaseShouldNotBeFound("vel.equals=" + UPDATED_VEL);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVelIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vel in DEFAULT_VEL or UPDATED_VEL
        defaultCourtCaseShouldBeFound("vel.in=" + DEFAULT_VEL + "," + UPDATED_VEL);

        // Get all the courtCaseList where vel equals to UPDATED_VEL
        defaultCourtCaseShouldNotBeFound("vel.in=" + UPDATED_VEL);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVelIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vel is not null
        defaultCourtCaseShouldBeFound("vel.specified=true");

        // Get all the courtCaseList where vel is null
        defaultCourtCaseShouldNotBeFound("vel.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByVelContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vel contains DEFAULT_VEL
        defaultCourtCaseShouldBeFound("vel.contains=" + DEFAULT_VEL);

        // Get all the courtCaseList where vel contains UPDATED_VEL
        defaultCourtCaseShouldNotBeFound("vel.contains=" + UPDATED_VEL);
    }

    @Test
    @Transactional
    void getAllCourtCasesByVelNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where vel does not contain DEFAULT_VEL
        defaultCourtCaseShouldNotBeFound("vel.doesNotContain=" + DEFAULT_VEL);

        // Get all the courtCaseList where vel does not contain UPDATED_VEL
        defaultCourtCaseShouldBeFound("vel.doesNotContain=" + UPDATED_VEL);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOne equals to DEFAULT_JAMINDAR_ONE
        defaultCourtCaseShouldBeFound("jamindarOne.equals=" + DEFAULT_JAMINDAR_ONE);

        // Get all the courtCaseList where jamindarOne equals to UPDATED_JAMINDAR_ONE
        defaultCourtCaseShouldNotBeFound("jamindarOne.equals=" + UPDATED_JAMINDAR_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOne in DEFAULT_JAMINDAR_ONE or UPDATED_JAMINDAR_ONE
        defaultCourtCaseShouldBeFound("jamindarOne.in=" + DEFAULT_JAMINDAR_ONE + "," + UPDATED_JAMINDAR_ONE);

        // Get all the courtCaseList where jamindarOne equals to UPDATED_JAMINDAR_ONE
        defaultCourtCaseShouldNotBeFound("jamindarOne.in=" + UPDATED_JAMINDAR_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOne is not null
        defaultCourtCaseShouldBeFound("jamindarOne.specified=true");

        // Get all the courtCaseList where jamindarOne is null
        defaultCourtCaseShouldNotBeFound("jamindarOne.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOne contains DEFAULT_JAMINDAR_ONE
        defaultCourtCaseShouldBeFound("jamindarOne.contains=" + DEFAULT_JAMINDAR_ONE);

        // Get all the courtCaseList where jamindarOne contains UPDATED_JAMINDAR_ONE
        defaultCourtCaseShouldNotBeFound("jamindarOne.contains=" + UPDATED_JAMINDAR_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOne does not contain DEFAULT_JAMINDAR_ONE
        defaultCourtCaseShouldNotBeFound("jamindarOne.doesNotContain=" + DEFAULT_JAMINDAR_ONE);

        // Get all the courtCaseList where jamindarOne does not contain UPDATED_JAMINDAR_ONE
        defaultCourtCaseShouldBeFound("jamindarOne.doesNotContain=" + UPDATED_JAMINDAR_ONE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOneAddress equals to DEFAULT_JAMINDAR_ONE_ADDRESS
        defaultCourtCaseShouldBeFound("jamindarOneAddress.equals=" + DEFAULT_JAMINDAR_ONE_ADDRESS);

        // Get all the courtCaseList where jamindarOneAddress equals to UPDATED_JAMINDAR_ONE_ADDRESS
        defaultCourtCaseShouldNotBeFound("jamindarOneAddress.equals=" + UPDATED_JAMINDAR_ONE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneAddressIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOneAddress in DEFAULT_JAMINDAR_ONE_ADDRESS or UPDATED_JAMINDAR_ONE_ADDRESS
        defaultCourtCaseShouldBeFound("jamindarOneAddress.in=" + DEFAULT_JAMINDAR_ONE_ADDRESS + "," + UPDATED_JAMINDAR_ONE_ADDRESS);

        // Get all the courtCaseList where jamindarOneAddress equals to UPDATED_JAMINDAR_ONE_ADDRESS
        defaultCourtCaseShouldNotBeFound("jamindarOneAddress.in=" + UPDATED_JAMINDAR_ONE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOneAddress is not null
        defaultCourtCaseShouldBeFound("jamindarOneAddress.specified=true");

        // Get all the courtCaseList where jamindarOneAddress is null
        defaultCourtCaseShouldNotBeFound("jamindarOneAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneAddressContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOneAddress contains DEFAULT_JAMINDAR_ONE_ADDRESS
        defaultCourtCaseShouldBeFound("jamindarOneAddress.contains=" + DEFAULT_JAMINDAR_ONE_ADDRESS);

        // Get all the courtCaseList where jamindarOneAddress contains UPDATED_JAMINDAR_ONE_ADDRESS
        defaultCourtCaseShouldNotBeFound("jamindarOneAddress.contains=" + UPDATED_JAMINDAR_ONE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarOneAddressNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarOneAddress does not contain DEFAULT_JAMINDAR_ONE_ADDRESS
        defaultCourtCaseShouldNotBeFound("jamindarOneAddress.doesNotContain=" + DEFAULT_JAMINDAR_ONE_ADDRESS);

        // Get all the courtCaseList where jamindarOneAddress does not contain UPDATED_JAMINDAR_ONE_ADDRESS
        defaultCourtCaseShouldBeFound("jamindarOneAddress.doesNotContain=" + UPDATED_JAMINDAR_ONE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwo equals to DEFAULT_JAMINDAR_TWO
        defaultCourtCaseShouldBeFound("jamindarTwo.equals=" + DEFAULT_JAMINDAR_TWO);

        // Get all the courtCaseList where jamindarTwo equals to UPDATED_JAMINDAR_TWO
        defaultCourtCaseShouldNotBeFound("jamindarTwo.equals=" + UPDATED_JAMINDAR_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwo in DEFAULT_JAMINDAR_TWO or UPDATED_JAMINDAR_TWO
        defaultCourtCaseShouldBeFound("jamindarTwo.in=" + DEFAULT_JAMINDAR_TWO + "," + UPDATED_JAMINDAR_TWO);

        // Get all the courtCaseList where jamindarTwo equals to UPDATED_JAMINDAR_TWO
        defaultCourtCaseShouldNotBeFound("jamindarTwo.in=" + UPDATED_JAMINDAR_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwo is not null
        defaultCourtCaseShouldBeFound("jamindarTwo.specified=true");

        // Get all the courtCaseList where jamindarTwo is null
        defaultCourtCaseShouldNotBeFound("jamindarTwo.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwo contains DEFAULT_JAMINDAR_TWO
        defaultCourtCaseShouldBeFound("jamindarTwo.contains=" + DEFAULT_JAMINDAR_TWO);

        // Get all the courtCaseList where jamindarTwo contains UPDATED_JAMINDAR_TWO
        defaultCourtCaseShouldNotBeFound("jamindarTwo.contains=" + UPDATED_JAMINDAR_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwo does not contain DEFAULT_JAMINDAR_TWO
        defaultCourtCaseShouldNotBeFound("jamindarTwo.doesNotContain=" + DEFAULT_JAMINDAR_TWO);

        // Get all the courtCaseList where jamindarTwo does not contain UPDATED_JAMINDAR_TWO
        defaultCourtCaseShouldBeFound("jamindarTwo.doesNotContain=" + UPDATED_JAMINDAR_TWO);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwoAddress equals to DEFAULT_JAMINDAR_TWO_ADDRESS
        defaultCourtCaseShouldBeFound("jamindarTwoAddress.equals=" + DEFAULT_JAMINDAR_TWO_ADDRESS);

        // Get all the courtCaseList where jamindarTwoAddress equals to UPDATED_JAMINDAR_TWO_ADDRESS
        defaultCourtCaseShouldNotBeFound("jamindarTwoAddress.equals=" + UPDATED_JAMINDAR_TWO_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoAddressIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwoAddress in DEFAULT_JAMINDAR_TWO_ADDRESS or UPDATED_JAMINDAR_TWO_ADDRESS
        defaultCourtCaseShouldBeFound("jamindarTwoAddress.in=" + DEFAULT_JAMINDAR_TWO_ADDRESS + "," + UPDATED_JAMINDAR_TWO_ADDRESS);

        // Get all the courtCaseList where jamindarTwoAddress equals to UPDATED_JAMINDAR_TWO_ADDRESS
        defaultCourtCaseShouldNotBeFound("jamindarTwoAddress.in=" + UPDATED_JAMINDAR_TWO_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwoAddress is not null
        defaultCourtCaseShouldBeFound("jamindarTwoAddress.specified=true");

        // Get all the courtCaseList where jamindarTwoAddress is null
        defaultCourtCaseShouldNotBeFound("jamindarTwoAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoAddressContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwoAddress contains DEFAULT_JAMINDAR_TWO_ADDRESS
        defaultCourtCaseShouldBeFound("jamindarTwoAddress.contains=" + DEFAULT_JAMINDAR_TWO_ADDRESS);

        // Get all the courtCaseList where jamindarTwoAddress contains UPDATED_JAMINDAR_TWO_ADDRESS
        defaultCourtCaseShouldNotBeFound("jamindarTwoAddress.contains=" + UPDATED_JAMINDAR_TWO_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByJamindarTwoAddressNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where jamindarTwoAddress does not contain DEFAULT_JAMINDAR_TWO_ADDRESS
        defaultCourtCaseShouldNotBeFound("jamindarTwoAddress.doesNotContain=" + DEFAULT_JAMINDAR_TWO_ADDRESS);

        // Get all the courtCaseList where jamindarTwoAddress does not contain UPDATED_JAMINDAR_TWO_ADDRESS
        defaultCourtCaseShouldBeFound("jamindarTwoAddress.doesNotContain=" + UPDATED_JAMINDAR_TWO_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranType equals to DEFAULT_TARAN_TYPE
        defaultCourtCaseShouldBeFound("taranType.equals=" + DEFAULT_TARAN_TYPE);

        // Get all the courtCaseList where taranType equals to UPDATED_TARAN_TYPE
        defaultCourtCaseShouldNotBeFound("taranType.equals=" + UPDATED_TARAN_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranTypeIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranType in DEFAULT_TARAN_TYPE or UPDATED_TARAN_TYPE
        defaultCourtCaseShouldBeFound("taranType.in=" + DEFAULT_TARAN_TYPE + "," + UPDATED_TARAN_TYPE);

        // Get all the courtCaseList where taranType equals to UPDATED_TARAN_TYPE
        defaultCourtCaseShouldNotBeFound("taranType.in=" + UPDATED_TARAN_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranType is not null
        defaultCourtCaseShouldBeFound("taranType.specified=true");

        // Get all the courtCaseList where taranType is null
        defaultCourtCaseShouldNotBeFound("taranType.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranTypeContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranType contains DEFAULT_TARAN_TYPE
        defaultCourtCaseShouldBeFound("taranType.contains=" + DEFAULT_TARAN_TYPE);

        // Get all the courtCaseList where taranType contains UPDATED_TARAN_TYPE
        defaultCourtCaseShouldNotBeFound("taranType.contains=" + UPDATED_TARAN_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranTypeNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranType does not contain DEFAULT_TARAN_TYPE
        defaultCourtCaseShouldNotBeFound("taranType.doesNotContain=" + DEFAULT_TARAN_TYPE);

        // Get all the courtCaseList where taranType does not contain UPDATED_TARAN_TYPE
        defaultCourtCaseShouldBeFound("taranType.doesNotContain=" + UPDATED_TARAN_TYPE);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranDetails equals to DEFAULT_TARAN_DETAILS
        defaultCourtCaseShouldBeFound("taranDetails.equals=" + DEFAULT_TARAN_DETAILS);

        // Get all the courtCaseList where taranDetails equals to UPDATED_TARAN_DETAILS
        defaultCourtCaseShouldNotBeFound("taranDetails.equals=" + UPDATED_TARAN_DETAILS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranDetails in DEFAULT_TARAN_DETAILS or UPDATED_TARAN_DETAILS
        defaultCourtCaseShouldBeFound("taranDetails.in=" + DEFAULT_TARAN_DETAILS + "," + UPDATED_TARAN_DETAILS);

        // Get all the courtCaseList where taranDetails equals to UPDATED_TARAN_DETAILS
        defaultCourtCaseShouldNotBeFound("taranDetails.in=" + UPDATED_TARAN_DETAILS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranDetails is not null
        defaultCourtCaseShouldBeFound("taranDetails.specified=true");

        // Get all the courtCaseList where taranDetails is null
        defaultCourtCaseShouldNotBeFound("taranDetails.specified=false");
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranDetailsContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranDetails contains DEFAULT_TARAN_DETAILS
        defaultCourtCaseShouldBeFound("taranDetails.contains=" + DEFAULT_TARAN_DETAILS);

        // Get all the courtCaseList where taranDetails contains UPDATED_TARAN_DETAILS
        defaultCourtCaseShouldNotBeFound("taranDetails.contains=" + UPDATED_TARAN_DETAILS);
    }

    @Test
    @Transactional
    void getAllCourtCasesByTaranDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        // Get all the courtCaseList where taranDetails does not contain DEFAULT_TARAN_DETAILS
        defaultCourtCaseShouldNotBeFound("taranDetails.doesNotContain=" + DEFAULT_TARAN_DETAILS);

        // Get all the courtCaseList where taranDetails does not contain UPDATED_TARAN_DETAILS
        defaultCourtCaseShouldBeFound("taranDetails.doesNotContain=" + UPDATED_TARAN_DETAILS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourtCaseShouldBeFound(String filter) throws Exception {
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courtCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.intValue())))
            .andExpect(jsonPath("$.[*].caseDinank").value(hasItem(DEFAULT_CASE_DINANK.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].talukaCode").value(hasItem(DEFAULT_TALUKA_CODE.intValue())))
            .andExpect(jsonPath("$.[*].sabasadSavingAccNo").value(hasItem(DEFAULT_SABASAD_SAVING_ACC_NO)))
            .andExpect(jsonPath("$.[*].sabasadName").value(hasItem(DEFAULT_SABASAD_NAME)))
            .andExpect(jsonPath("$.[*].sabasadAddress").value(hasItem(DEFAULT_SABASAD_ADDRESS)))
            .andExpect(jsonPath("$.[*].karjPrakar").value(hasItem(DEFAULT_KARJ_PRAKAR)))
            .andExpect(jsonPath("$.[*].vasuliAdhikari").value(hasItem(DEFAULT_VASULI_ADHIKARI)))
            .andExpect(jsonPath("$.[*].ekunJama").value(hasItem(DEFAULT_EKUN_JAMA.doubleValue())))
            .andExpect(jsonPath("$.[*].baki").value(hasItem(DEFAULT_BAKI.doubleValue())))
            .andExpect(jsonPath("$.[*].arOffice").value(hasItem(DEFAULT_AR_OFFICE)))
            .andExpect(jsonPath("$.[*].ekunVyaj").value(hasItem(DEFAULT_EKUN_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].jamaVyaj").value(hasItem(DEFAULT_JAMA_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].dandVyaj").value(hasItem(DEFAULT_DAND_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].karjRakkam").value(hasItem(DEFAULT_KARJ_RAKKAM.doubleValue())))
            .andExpect(jsonPath("$.[*].thakDinnank").value(hasItem(DEFAULT_THAK_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].karjDinnank").value(hasItem(DEFAULT_KARJ_DINNANK.toString())))
            .andExpect(jsonPath("$.[*].mudatSampteDinank").value(hasItem(DEFAULT_MUDAT_SAMPTE_DINANK.toString())))
            .andExpect(jsonPath("$.[*].mudat").value(hasItem(DEFAULT_MUDAT)))
            .andExpect(jsonPath("$.[*].vyaj").value(hasItem(DEFAULT_VYAJ)))
            .andExpect(jsonPath("$.[*].haptaRakkam").value(hasItem(DEFAULT_HAPTA_RAKKAM.doubleValue())))
            .andExpect(jsonPath("$.[*].shakhaVevsthapak").value(hasItem(DEFAULT_SHAKHA_VEVSTHAPAK)))
            .andExpect(jsonPath("$.[*].suchak").value(hasItem(DEFAULT_SUCHAK)))
            .andExpect(jsonPath("$.[*].anumodak").value(hasItem(DEFAULT_ANUMODAK)))
            .andExpect(jsonPath("$.[*].dava").value(hasItem(DEFAULT_DAVA.doubleValue())))
            .andExpect(jsonPath("$.[*].vyajDar").value(hasItem(DEFAULT_VYAJ_DAR.doubleValue())))
            .andExpect(jsonPath("$.[*].sarcharge").value(hasItem(DEFAULT_SARCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].jyadaVyaj").value(hasItem(DEFAULT_JYADA_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].yeneVyaj").value(hasItem(DEFAULT_YENE_VYAJ.doubleValue())))
            .andExpect(jsonPath("$.[*].vasuliKharch").value(hasItem(DEFAULT_VASULI_KHARCH.doubleValue())))
            .andExpect(jsonPath("$.[*].etharKharch").value(hasItem(DEFAULT_ETHAR_KHARCH.doubleValue())))
            .andExpect(jsonPath("$.[*].vima").value(hasItem(DEFAULT_VIMA.doubleValue())))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tharavNumber").value(hasItem(DEFAULT_THARAV_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].tharavDinank").value(hasItem(DEFAULT_THARAV_DINANK.toString())))
            .andExpect(jsonPath("$.[*].vishayKramank").value(hasItem(DEFAULT_VISHAY_KRAMANK)))
            .andExpect(jsonPath("$.[*].noticeOne").value(hasItem(DEFAULT_NOTICE_ONE.toString())))
            .andExpect(jsonPath("$.[*].noticeTwo").value(hasItem(DEFAULT_NOTICE_TWO.toString())))
            .andExpect(jsonPath("$.[*].war").value(hasItem(DEFAULT_WAR)))
            .andExpect(jsonPath("$.[*].vel").value(hasItem(DEFAULT_VEL)))
            .andExpect(jsonPath("$.[*].jamindarOne").value(hasItem(DEFAULT_JAMINDAR_ONE)))
            .andExpect(jsonPath("$.[*].jamindarOneAddress").value(hasItem(DEFAULT_JAMINDAR_ONE_ADDRESS)))
            .andExpect(jsonPath("$.[*].jamindarTwo").value(hasItem(DEFAULT_JAMINDAR_TWO)))
            .andExpect(jsonPath("$.[*].jamindarTwoAddress").value(hasItem(DEFAULT_JAMINDAR_TWO_ADDRESS)))
            .andExpect(jsonPath("$.[*].taranType").value(hasItem(DEFAULT_TARAN_TYPE)))
            .andExpect(jsonPath("$.[*].taranDetails").value(hasItem(DEFAULT_TARAN_DETAILS)));

        // Check, that the count call also returns 1
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourtCaseShouldNotBeFound(String filter) throws Exception {
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourtCaseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCourtCase() throws Exception {
        // Get the courtCase
        restCourtCaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase
        CourtCase updatedCourtCase = courtCaseRepository.findById(courtCase.getId()).get();
        // Disconnect from session so that the updates on updatedCourtCase are not directly saved in db
        em.detach(updatedCourtCase);
        updatedCourtCase
            .code(UPDATED_CODE)
            .caseDinank(UPDATED_CASE_DINANK)
            .bankName(UPDATED_BANK_NAME)
            .talukaName(UPDATED_TALUKA_NAME)
            .talukaCode(UPDATED_TALUKA_CODE)
            .sabasadSavingAccNo(UPDATED_SABASAD_SAVING_ACC_NO)
            .sabasadName(UPDATED_SABASAD_NAME)
            .sabasadAddress(UPDATED_SABASAD_ADDRESS)
            .karjPrakar(UPDATED_KARJ_PRAKAR)
            .vasuliAdhikari(UPDATED_VASULI_ADHIKARI)
            .ekunJama(UPDATED_EKUN_JAMA)
            .baki(UPDATED_BAKI)
            .arOffice(UPDATED_AR_OFFICE)
            .ekunVyaj(UPDATED_EKUN_VYAJ)
            .jamaVyaj(UPDATED_JAMA_VYAJ)
            .dandVyaj(UPDATED_DAND_VYAJ)
            .karjRakkam(UPDATED_KARJ_RAKKAM)
            .thakDinnank(UPDATED_THAK_DINNANK)
            .karjDinnank(UPDATED_KARJ_DINNANK)
            .mudatSampteDinank(UPDATED_MUDAT_SAMPTE_DINANK)
            .mudat(UPDATED_MUDAT)
            .vyaj(UPDATED_VYAJ)
            .haptaRakkam(UPDATED_HAPTA_RAKKAM)
            .shakhaVevsthapak(UPDATED_SHAKHA_VEVSTHAPAK)
            .suchak(UPDATED_SUCHAK)
            .anumodak(UPDATED_ANUMODAK)
            .dava(UPDATED_DAVA)
            .vyajDar(UPDATED_VYAJ_DAR)
            .sarcharge(UPDATED_SARCHARGE)
            .jyadaVyaj(UPDATED_JYADA_VYAJ)
            .yeneVyaj(UPDATED_YENE_VYAJ)
            .vasuliKharch(UPDATED_VASULI_KHARCH)
            .etharKharch(UPDATED_ETHAR_KHARCH)
            .vima(UPDATED_VIMA)
            .notice(UPDATED_NOTICE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDinank(UPDATED_THARAV_DINANK)
            .vishayKramank(UPDATED_VISHAY_KRAMANK)
            .noticeOne(UPDATED_NOTICE_ONE)
            .noticeTwo(UPDATED_NOTICE_TWO)
            .war(UPDATED_WAR)
            .vel(UPDATED_VEL)
            .jamindarOne(UPDATED_JAMINDAR_ONE)
            .jamindarOneAddress(UPDATED_JAMINDAR_ONE_ADDRESS)
            .jamindarTwo(UPDATED_JAMINDAR_TWO)
            .jamindarTwoAddress(UPDATED_JAMINDAR_TWO_ADDRESS)
            .taranType(UPDATED_TARAN_TYPE)
            .taranDetails(UPDATED_TARAN_DETAILS);

        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCourtCase.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCourtCase.getCaseDinank()).isEqualTo(UPDATED_CASE_DINANK);
        assertThat(testCourtCase.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCourtCase.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testCourtCase.getTalukaCode()).isEqualTo(UPDATED_TALUKA_CODE);
        assertThat(testCourtCase.getSabasadSavingAccNo()).isEqualTo(UPDATED_SABASAD_SAVING_ACC_NO);
        assertThat(testCourtCase.getSabasadName()).isEqualTo(UPDATED_SABASAD_NAME);
        assertThat(testCourtCase.getSabasadAddress()).isEqualTo(UPDATED_SABASAD_ADDRESS);
        assertThat(testCourtCase.getKarjPrakar()).isEqualTo(UPDATED_KARJ_PRAKAR);
        assertThat(testCourtCase.getVasuliAdhikari()).isEqualTo(UPDATED_VASULI_ADHIKARI);
        assertThat(testCourtCase.getEkunJama()).isEqualTo(UPDATED_EKUN_JAMA);
        assertThat(testCourtCase.getBaki()).isEqualTo(UPDATED_BAKI);
        assertThat(testCourtCase.getArOffice()).isEqualTo(UPDATED_AR_OFFICE);
        assertThat(testCourtCase.getEkunVyaj()).isEqualTo(UPDATED_EKUN_VYAJ);
        assertThat(testCourtCase.getJamaVyaj()).isEqualTo(UPDATED_JAMA_VYAJ);
        assertThat(testCourtCase.getDandVyaj()).isEqualTo(UPDATED_DAND_VYAJ);
        assertThat(testCourtCase.getKarjRakkam()).isEqualTo(UPDATED_KARJ_RAKKAM);
        assertThat(testCourtCase.getThakDinnank()).isEqualTo(UPDATED_THAK_DINNANK);
        assertThat(testCourtCase.getKarjDinnank()).isEqualTo(UPDATED_KARJ_DINNANK);
        assertThat(testCourtCase.getMudatSampteDinank()).isEqualTo(UPDATED_MUDAT_SAMPTE_DINANK);
        assertThat(testCourtCase.getMudat()).isEqualTo(UPDATED_MUDAT);
        assertThat(testCourtCase.getVyaj()).isEqualTo(UPDATED_VYAJ);
        assertThat(testCourtCase.getHaptaRakkam()).isEqualTo(UPDATED_HAPTA_RAKKAM);
        assertThat(testCourtCase.getShakhaVevsthapak()).isEqualTo(UPDATED_SHAKHA_VEVSTHAPAK);
        assertThat(testCourtCase.getSuchak()).isEqualTo(UPDATED_SUCHAK);
        assertThat(testCourtCase.getAnumodak()).isEqualTo(UPDATED_ANUMODAK);
        assertThat(testCourtCase.getDava()).isEqualTo(UPDATED_DAVA);
        assertThat(testCourtCase.getVyajDar()).isEqualTo(UPDATED_VYAJ_DAR);
        assertThat(testCourtCase.getSarcharge()).isEqualTo(UPDATED_SARCHARGE);
        assertThat(testCourtCase.getJyadaVyaj()).isEqualTo(UPDATED_JYADA_VYAJ);
        assertThat(testCourtCase.getYeneVyaj()).isEqualTo(UPDATED_YENE_VYAJ);
        assertThat(testCourtCase.getVasuliKharch()).isEqualTo(UPDATED_VASULI_KHARCH);
        assertThat(testCourtCase.getEtharKharch()).isEqualTo(UPDATED_ETHAR_KHARCH);
        assertThat(testCourtCase.getVima()).isEqualTo(UPDATED_VIMA);
        assertThat(testCourtCase.getNotice()).isEqualTo(UPDATED_NOTICE);
        assertThat(testCourtCase.getTharavNumber()).isEqualTo(UPDATED_THARAV_NUMBER);
        assertThat(testCourtCase.getTharavDinank()).isEqualTo(UPDATED_THARAV_DINANK);
        assertThat(testCourtCase.getVishayKramank()).isEqualTo(UPDATED_VISHAY_KRAMANK);
        assertThat(testCourtCase.getNoticeOne()).isEqualTo(UPDATED_NOTICE_ONE);
        assertThat(testCourtCase.getNoticeTwo()).isEqualTo(UPDATED_NOTICE_TWO);
        assertThat(testCourtCase.getWar()).isEqualTo(UPDATED_WAR);
        assertThat(testCourtCase.getVel()).isEqualTo(UPDATED_VEL);
        assertThat(testCourtCase.getJamindarOne()).isEqualTo(UPDATED_JAMINDAR_ONE);
        assertThat(testCourtCase.getJamindarOneAddress()).isEqualTo(UPDATED_JAMINDAR_ONE_ADDRESS);
        assertThat(testCourtCase.getJamindarTwo()).isEqualTo(UPDATED_JAMINDAR_TWO);
        assertThat(testCourtCase.getJamindarTwoAddress()).isEqualTo(UPDATED_JAMINDAR_TWO_ADDRESS);
        assertThat(testCourtCase.getTaranType()).isEqualTo(UPDATED_TARAN_TYPE);
        assertThat(testCourtCase.getTaranDetails()).isEqualTo(UPDATED_TARAN_DETAILS);
    }

    @Test
    @Transactional
    void putNonExistingCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, courtCase.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(courtCase)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCourtCaseWithPatch() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase using partial update
        CourtCase partialUpdatedCourtCase = new CourtCase();
        partialUpdatedCourtCase.setId(courtCase.getId());

        partialUpdatedCourtCase
            .talukaName(UPDATED_TALUKA_NAME)
            .karjPrakar(UPDATED_KARJ_PRAKAR)
            .vasuliAdhikari(UPDATED_VASULI_ADHIKARI)
            .ekunJama(UPDATED_EKUN_JAMA)
            .baki(UPDATED_BAKI)
            .jamaVyaj(UPDATED_JAMA_VYAJ)
            .karjDinnank(UPDATED_KARJ_DINNANK)
            .mudatSampteDinank(UPDATED_MUDAT_SAMPTE_DINANK)
            .mudat(UPDATED_MUDAT)
            .shakhaVevsthapak(UPDATED_SHAKHA_VEVSTHAPAK)
            .suchak(UPDATED_SUCHAK)
            .dava(UPDATED_DAVA)
            .vyajDar(UPDATED_VYAJ_DAR)
            .jyadaVyaj(UPDATED_JYADA_VYAJ)
            .vasuliKharch(UPDATED_VASULI_KHARCH)
            .vima(UPDATED_VIMA)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDinank(UPDATED_THARAV_DINANK)
            .vishayKramank(UPDATED_VISHAY_KRAMANK)
            .noticeTwo(UPDATED_NOTICE_TWO)
            .war(UPDATED_WAR);

        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCourtCase.getCaseDinank()).isEqualTo(DEFAULT_CASE_DINANK);
        assertThat(testCourtCase.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCourtCase.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testCourtCase.getTalukaCode()).isEqualTo(DEFAULT_TALUKA_CODE);
        assertThat(testCourtCase.getSabasadSavingAccNo()).isEqualTo(DEFAULT_SABASAD_SAVING_ACC_NO);
        assertThat(testCourtCase.getSabasadName()).isEqualTo(DEFAULT_SABASAD_NAME);
        assertThat(testCourtCase.getSabasadAddress()).isEqualTo(DEFAULT_SABASAD_ADDRESS);
        assertThat(testCourtCase.getKarjPrakar()).isEqualTo(UPDATED_KARJ_PRAKAR);
        assertThat(testCourtCase.getVasuliAdhikari()).isEqualTo(UPDATED_VASULI_ADHIKARI);
        assertThat(testCourtCase.getEkunJama()).isEqualTo(UPDATED_EKUN_JAMA);
        assertThat(testCourtCase.getBaki()).isEqualTo(UPDATED_BAKI);
        assertThat(testCourtCase.getArOffice()).isEqualTo(DEFAULT_AR_OFFICE);
        assertThat(testCourtCase.getEkunVyaj()).isEqualTo(DEFAULT_EKUN_VYAJ);
        assertThat(testCourtCase.getJamaVyaj()).isEqualTo(UPDATED_JAMA_VYAJ);
        assertThat(testCourtCase.getDandVyaj()).isEqualTo(DEFAULT_DAND_VYAJ);
        assertThat(testCourtCase.getKarjRakkam()).isEqualTo(DEFAULT_KARJ_RAKKAM);
        assertThat(testCourtCase.getThakDinnank()).isEqualTo(DEFAULT_THAK_DINNANK);
        assertThat(testCourtCase.getKarjDinnank()).isEqualTo(UPDATED_KARJ_DINNANK);
        assertThat(testCourtCase.getMudatSampteDinank()).isEqualTo(UPDATED_MUDAT_SAMPTE_DINANK);
        assertThat(testCourtCase.getMudat()).isEqualTo(UPDATED_MUDAT);
        assertThat(testCourtCase.getVyaj()).isEqualTo(DEFAULT_VYAJ);
        assertThat(testCourtCase.getHaptaRakkam()).isEqualTo(DEFAULT_HAPTA_RAKKAM);
        assertThat(testCourtCase.getShakhaVevsthapak()).isEqualTo(UPDATED_SHAKHA_VEVSTHAPAK);
        assertThat(testCourtCase.getSuchak()).isEqualTo(UPDATED_SUCHAK);
        assertThat(testCourtCase.getAnumodak()).isEqualTo(DEFAULT_ANUMODAK);
        assertThat(testCourtCase.getDava()).isEqualTo(UPDATED_DAVA);
        assertThat(testCourtCase.getVyajDar()).isEqualTo(UPDATED_VYAJ_DAR);
        assertThat(testCourtCase.getSarcharge()).isEqualTo(DEFAULT_SARCHARGE);
        assertThat(testCourtCase.getJyadaVyaj()).isEqualTo(UPDATED_JYADA_VYAJ);
        assertThat(testCourtCase.getYeneVyaj()).isEqualTo(DEFAULT_YENE_VYAJ);
        assertThat(testCourtCase.getVasuliKharch()).isEqualTo(UPDATED_VASULI_KHARCH);
        assertThat(testCourtCase.getEtharKharch()).isEqualTo(DEFAULT_ETHAR_KHARCH);
        assertThat(testCourtCase.getVima()).isEqualTo(UPDATED_VIMA);
        assertThat(testCourtCase.getNotice()).isEqualTo(DEFAULT_NOTICE);
        assertThat(testCourtCase.getTharavNumber()).isEqualTo(UPDATED_THARAV_NUMBER);
        assertThat(testCourtCase.getTharavDinank()).isEqualTo(UPDATED_THARAV_DINANK);
        assertThat(testCourtCase.getVishayKramank()).isEqualTo(UPDATED_VISHAY_KRAMANK);
        assertThat(testCourtCase.getNoticeOne()).isEqualTo(DEFAULT_NOTICE_ONE);
        assertThat(testCourtCase.getNoticeTwo()).isEqualTo(UPDATED_NOTICE_TWO);
        assertThat(testCourtCase.getWar()).isEqualTo(UPDATED_WAR);
        assertThat(testCourtCase.getVel()).isEqualTo(DEFAULT_VEL);
        assertThat(testCourtCase.getJamindarOne()).isEqualTo(DEFAULT_JAMINDAR_ONE);
        assertThat(testCourtCase.getJamindarOneAddress()).isEqualTo(DEFAULT_JAMINDAR_ONE_ADDRESS);
        assertThat(testCourtCase.getJamindarTwo()).isEqualTo(DEFAULT_JAMINDAR_TWO);
        assertThat(testCourtCase.getJamindarTwoAddress()).isEqualTo(DEFAULT_JAMINDAR_TWO_ADDRESS);
        assertThat(testCourtCase.getTaranType()).isEqualTo(DEFAULT_TARAN_TYPE);
        assertThat(testCourtCase.getTaranDetails()).isEqualTo(DEFAULT_TARAN_DETAILS);
    }

    @Test
    @Transactional
    void fullUpdateCourtCaseWithPatch() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();

        // Update the courtCase using partial update
        CourtCase partialUpdatedCourtCase = new CourtCase();
        partialUpdatedCourtCase.setId(courtCase.getId());

        partialUpdatedCourtCase
            .code(UPDATED_CODE)
            .caseDinank(UPDATED_CASE_DINANK)
            .bankName(UPDATED_BANK_NAME)
            .talukaName(UPDATED_TALUKA_NAME)
            .talukaCode(UPDATED_TALUKA_CODE)
            .sabasadSavingAccNo(UPDATED_SABASAD_SAVING_ACC_NO)
            .sabasadName(UPDATED_SABASAD_NAME)
            .sabasadAddress(UPDATED_SABASAD_ADDRESS)
            .karjPrakar(UPDATED_KARJ_PRAKAR)
            .vasuliAdhikari(UPDATED_VASULI_ADHIKARI)
            .ekunJama(UPDATED_EKUN_JAMA)
            .baki(UPDATED_BAKI)
            .arOffice(UPDATED_AR_OFFICE)
            .ekunVyaj(UPDATED_EKUN_VYAJ)
            .jamaVyaj(UPDATED_JAMA_VYAJ)
            .dandVyaj(UPDATED_DAND_VYAJ)
            .karjRakkam(UPDATED_KARJ_RAKKAM)
            .thakDinnank(UPDATED_THAK_DINNANK)
            .karjDinnank(UPDATED_KARJ_DINNANK)
            .mudatSampteDinank(UPDATED_MUDAT_SAMPTE_DINANK)
            .mudat(UPDATED_MUDAT)
            .vyaj(UPDATED_VYAJ)
            .haptaRakkam(UPDATED_HAPTA_RAKKAM)
            .shakhaVevsthapak(UPDATED_SHAKHA_VEVSTHAPAK)
            .suchak(UPDATED_SUCHAK)
            .anumodak(UPDATED_ANUMODAK)
            .dava(UPDATED_DAVA)
            .vyajDar(UPDATED_VYAJ_DAR)
            .sarcharge(UPDATED_SARCHARGE)
            .jyadaVyaj(UPDATED_JYADA_VYAJ)
            .yeneVyaj(UPDATED_YENE_VYAJ)
            .vasuliKharch(UPDATED_VASULI_KHARCH)
            .etharKharch(UPDATED_ETHAR_KHARCH)
            .vima(UPDATED_VIMA)
            .notice(UPDATED_NOTICE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDinank(UPDATED_THARAV_DINANK)
            .vishayKramank(UPDATED_VISHAY_KRAMANK)
            .noticeOne(UPDATED_NOTICE_ONE)
            .noticeTwo(UPDATED_NOTICE_TWO)
            .war(UPDATED_WAR)
            .vel(UPDATED_VEL)
            .jamindarOne(UPDATED_JAMINDAR_ONE)
            .jamindarOneAddress(UPDATED_JAMINDAR_ONE_ADDRESS)
            .jamindarTwo(UPDATED_JAMINDAR_TWO)
            .jamindarTwoAddress(UPDATED_JAMINDAR_TWO_ADDRESS)
            .taranType(UPDATED_TARAN_TYPE)
            .taranDetails(UPDATED_TARAN_DETAILS);

        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCourtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCourtCase))
            )
            .andExpect(status().isOk());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
        CourtCase testCourtCase = courtCaseList.get(courtCaseList.size() - 1);
        assertThat(testCourtCase.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCourtCase.getCaseDinank()).isEqualTo(UPDATED_CASE_DINANK);
        assertThat(testCourtCase.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCourtCase.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testCourtCase.getTalukaCode()).isEqualTo(UPDATED_TALUKA_CODE);
        assertThat(testCourtCase.getSabasadSavingAccNo()).isEqualTo(UPDATED_SABASAD_SAVING_ACC_NO);
        assertThat(testCourtCase.getSabasadName()).isEqualTo(UPDATED_SABASAD_NAME);
        assertThat(testCourtCase.getSabasadAddress()).isEqualTo(UPDATED_SABASAD_ADDRESS);
        assertThat(testCourtCase.getKarjPrakar()).isEqualTo(UPDATED_KARJ_PRAKAR);
        assertThat(testCourtCase.getVasuliAdhikari()).isEqualTo(UPDATED_VASULI_ADHIKARI);
        assertThat(testCourtCase.getEkunJama()).isEqualTo(UPDATED_EKUN_JAMA);
        assertThat(testCourtCase.getBaki()).isEqualTo(UPDATED_BAKI);
        assertThat(testCourtCase.getArOffice()).isEqualTo(UPDATED_AR_OFFICE);
        assertThat(testCourtCase.getEkunVyaj()).isEqualTo(UPDATED_EKUN_VYAJ);
        assertThat(testCourtCase.getJamaVyaj()).isEqualTo(UPDATED_JAMA_VYAJ);
        assertThat(testCourtCase.getDandVyaj()).isEqualTo(UPDATED_DAND_VYAJ);
        assertThat(testCourtCase.getKarjRakkam()).isEqualTo(UPDATED_KARJ_RAKKAM);
        assertThat(testCourtCase.getThakDinnank()).isEqualTo(UPDATED_THAK_DINNANK);
        assertThat(testCourtCase.getKarjDinnank()).isEqualTo(UPDATED_KARJ_DINNANK);
        assertThat(testCourtCase.getMudatSampteDinank()).isEqualTo(UPDATED_MUDAT_SAMPTE_DINANK);
        assertThat(testCourtCase.getMudat()).isEqualTo(UPDATED_MUDAT);
        assertThat(testCourtCase.getVyaj()).isEqualTo(UPDATED_VYAJ);
        assertThat(testCourtCase.getHaptaRakkam()).isEqualTo(UPDATED_HAPTA_RAKKAM);
        assertThat(testCourtCase.getShakhaVevsthapak()).isEqualTo(UPDATED_SHAKHA_VEVSTHAPAK);
        assertThat(testCourtCase.getSuchak()).isEqualTo(UPDATED_SUCHAK);
        assertThat(testCourtCase.getAnumodak()).isEqualTo(UPDATED_ANUMODAK);
        assertThat(testCourtCase.getDava()).isEqualTo(UPDATED_DAVA);
        assertThat(testCourtCase.getVyajDar()).isEqualTo(UPDATED_VYAJ_DAR);
        assertThat(testCourtCase.getSarcharge()).isEqualTo(UPDATED_SARCHARGE);
        assertThat(testCourtCase.getJyadaVyaj()).isEqualTo(UPDATED_JYADA_VYAJ);
        assertThat(testCourtCase.getYeneVyaj()).isEqualTo(UPDATED_YENE_VYAJ);
        assertThat(testCourtCase.getVasuliKharch()).isEqualTo(UPDATED_VASULI_KHARCH);
        assertThat(testCourtCase.getEtharKharch()).isEqualTo(UPDATED_ETHAR_KHARCH);
        assertThat(testCourtCase.getVima()).isEqualTo(UPDATED_VIMA);
        assertThat(testCourtCase.getNotice()).isEqualTo(UPDATED_NOTICE);
        assertThat(testCourtCase.getTharavNumber()).isEqualTo(UPDATED_THARAV_NUMBER);
        assertThat(testCourtCase.getTharavDinank()).isEqualTo(UPDATED_THARAV_DINANK);
        assertThat(testCourtCase.getVishayKramank()).isEqualTo(UPDATED_VISHAY_KRAMANK);
        assertThat(testCourtCase.getNoticeOne()).isEqualTo(UPDATED_NOTICE_ONE);
        assertThat(testCourtCase.getNoticeTwo()).isEqualTo(UPDATED_NOTICE_TWO);
        assertThat(testCourtCase.getWar()).isEqualTo(UPDATED_WAR);
        assertThat(testCourtCase.getVel()).isEqualTo(UPDATED_VEL);
        assertThat(testCourtCase.getJamindarOne()).isEqualTo(UPDATED_JAMINDAR_ONE);
        assertThat(testCourtCase.getJamindarOneAddress()).isEqualTo(UPDATED_JAMINDAR_ONE_ADDRESS);
        assertThat(testCourtCase.getJamindarTwo()).isEqualTo(UPDATED_JAMINDAR_TWO);
        assertThat(testCourtCase.getJamindarTwoAddress()).isEqualTo(UPDATED_JAMINDAR_TWO_ADDRESS);
        assertThat(testCourtCase.getTaranType()).isEqualTo(UPDATED_TARAN_TYPE);
        assertThat(testCourtCase.getTaranDetails()).isEqualTo(UPDATED_TARAN_DETAILS);
    }

    @Test
    @Transactional
    void patchNonExistingCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, courtCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isBadRequest());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCourtCase() throws Exception {
        int databaseSizeBeforeUpdate = courtCaseRepository.findAll().size();
        courtCase.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCourtCaseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(courtCase))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CourtCase in the database
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCourtCase() throws Exception {
        // Initialize the database
        courtCaseRepository.saveAndFlush(courtCase);

        int databaseSizeBeforeDelete = courtCaseRepository.findAll().size();

        // Delete the courtCase
        restCourtCaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, courtCase.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourtCase> courtCaseList = courtCaseRepository.findAll();
        assertThat(courtCaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
