package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
import com.cbs.middleware.domain.KamalCrop;
import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.repository.KamalSocietyRepository;
import com.cbs.middleware.service.criteria.KamalSocietyCriteria;
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
 * Integration tests for the {@link KamalSocietyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KamalSocietyResourceIT {

    private static final Long DEFAULT_PACS_NUMBER = 1L;
    private static final Long UPDATED_PACS_NUMBER = 2L;
    private static final Long SMALLER_PACS_NUMBER = 1L - 1L;

    private static final Instant DEFAULT_ZINDAGI_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ZINDAGI_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ZINDAGI_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_ZINDAGI_DATE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_1 = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_1_MR = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_1_MR = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_2 = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_2_MR = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_2_MR = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_3 = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_3_MR = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_3_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_LAND = 1D;
    private static final Double UPDATED_TOTAL_LAND = 2D;
    private static final Double SMALLER_TOTAL_LAND = 1D - 1D;

    private static final String DEFAULT_TOTAL_LAND_MR = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_LAND_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_MEM = 1D;
    private static final Double UPDATED_TOTAL_MEM = 2D;
    private static final Double SMALLER_TOTAL_MEM = 1D - 1D;

    private static final String DEFAULT_TOTAL_MEM_MR = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_MEM_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_NON_MEM = 1D;
    private static final Double UPDATED_TOTAL_NON_MEM = 2D;
    private static final Double SMALLER_TOTAL_NON_MEM = 1D - 1D;

    private static final String DEFAULT_TOTAL_NON_MEM_MR = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_NON_MEM_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_G_MEM = 1D;
    private static final Double UPDATED_TOTAL_G_MEM = 2D;
    private static final Double SMALLER_TOTAL_G_MEM = 1D - 1D;

    private static final String DEFAULT_TOTAL_G_MEM_MR = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_G_MEM_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_MEM_LOAN = 1D;
    private static final Double UPDATED_MEM_LOAN = 2D;
    private static final Double SMALLER_MEM_LOAN = 1D - 1D;

    private static final String DEFAULT_MEM_LOAN_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_LOAN_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_MEM_DUE = 1D;
    private static final Double UPDATED_MEM_DUE = 2D;
    private static final Double SMALLER_MEM_DUE = 1D - 1D;

    private static final String DEFAULT_MEM_DUE_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_DUE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_MEM_DUEPER = 1D;
    private static final Double UPDATED_MEM_DUEPER = 2D;
    private static final Double SMALLER_MEM_DUEPER = 1D - 1D;

    private static final String DEFAULT_MEM_DUEPER_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_DUEPER_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_MEM_VASULPATRA = 1D;
    private static final Double UPDATED_MEM_VASULPATRA = 2D;
    private static final Double SMALLER_MEM_VASULPATRA = 1D - 1D;

    private static final String DEFAULT_MEM_VASULPATRA_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_VASULPATRA_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_MEM_VASUL = 1D;
    private static final Double UPDATED_MEM_VASUL = 2D;
    private static final Double SMALLER_MEM_VASUL = 1D - 1D;

    private static final String DEFAULT_MEM_VASUL_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_VASUL_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_MEM_VASUL_PER = 1D;
    private static final Double UPDATED_MEM_VASUL_PER = 2D;
    private static final Double SMALLER_MEM_VASUL_PER = 1D - 1D;

    private static final String DEFAULT_MEM_VASUL_PER_MR = "AAAAAAAAAA";
    private static final String UPDATED_MEM_VASUL_PER_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BANK_LOAN = 1D;
    private static final Double UPDATED_BANK_LOAN = 2D;
    private static final Double SMALLER_BANK_LOAN = 1D - 1D;

    private static final String DEFAULT_BANK_LOAN_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_LOAN_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BANK_DUE = 1D;
    private static final Double UPDATED_BANK_DUE = 2D;
    private static final Double SMALLER_BANK_DUE = 1D - 1D;

    private static final String DEFAULT_BANK_DUE_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_DUE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BANK_DUEPER = 1D;
    private static final Double UPDATED_BANK_DUEPER = 2D;
    private static final Double SMALLER_BANK_DUEPER = 1D - 1D;

    private static final String DEFAULT_BANK_DUEPER_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_DUEPER_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BANK_VASULPATRA = 1D;
    private static final Double UPDATED_BANK_VASULPATRA = 2D;
    private static final Double SMALLER_BANK_VASULPATRA = 1D - 1D;

    private static final String DEFAULT_BANK_VASULPATRA_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_VASULPATRA_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BANK_VASUL = 1D;
    private static final Double UPDATED_BANK_VASUL = 2D;
    private static final Double SMALLER_BANK_VASUL = 1D - 1D;

    private static final String DEFAULT_BANK_VASUL_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_VASUL_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_BANK_VASUL_PER = 1D;
    private static final Double UPDATED_BANK_VASUL_PER = 2D;
    private static final Double SMALLER_BANK_VASUL_PER = 1D - 1D;

    private static final String DEFAULT_BANK_VASUL_PER_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_VASUL_PER_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_SHARE_CAPITAL = 1D;
    private static final Double UPDATED_SHARE_CAPITAL = 2D;
    private static final Double SMALLER_SHARE_CAPITAL = 1D - 1D;

    private static final String DEFAULT_SHARE_CAPITAL_MR = "AAAAAAAAAA";
    private static final String UPDATED_SHARE_CAPITAL_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_SHARE = 1D;
    private static final Double UPDATED_SHARE = 2D;
    private static final Double SMALLER_SHARE = 1D - 1D;

    private static final String DEFAULT_SHARE_MR = "AAAAAAAAAA";
    private static final String UPDATED_SHARE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_FUNDS = 1D;
    private static final Double UPDATED_FUNDS = 2D;
    private static final Double SMALLER_FUNDS = 1D - 1D;

    private static final String DEFAULT_FUNDS_MR = "AAAAAAAAAA";
    private static final String UPDATED_FUNDS_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DEPOSIT = 1D;
    private static final Double UPDATED_DEPOSIT = 2D;
    private static final Double SMALLER_DEPOSIT = 1D - 1D;

    private static final String DEFAULT_DEPOSIT_MR = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSIT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_PAYABLE = 1D;
    private static final Double UPDATED_PAYABLE = 2D;
    private static final Double SMALLER_PAYABLE = 1D - 1D;

    private static final String DEFAULT_PAYABLE_MR = "AAAAAAAAAA";
    private static final String UPDATED_PAYABLE_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_PROFIT = 1D;
    private static final Double UPDATED_PROFIT = 2D;
    private static final Double SMALLER_PROFIT = 1D - 1D;

    private static final String DEFAULT_PROFIT_MR = "AAAAAAAAAA";
    private static final String UPDATED_PROFIT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_CASH_IN_HAND = 1D;
    private static final Double UPDATED_CASH_IN_HAND = 2D;
    private static final Double SMALLER_CASH_IN_HAND = 1D - 1D;

    private static final String DEFAULT_CASH_IN_HAND_MR = "AAAAAAAAAA";
    private static final String UPDATED_CASH_IN_HAND_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_INVESTMENT = 1D;
    private static final Double UPDATED_INVESTMENT = 2D;
    private static final Double SMALLER_INVESTMENT = 1D - 1D;

    private static final String DEFAULT_INVESTMENT_MR = "AAAAAAAAAA";
    private static final String UPDATED_INVESTMENT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_DEAD_STOCK = 1D;
    private static final Double UPDATED_DEAD_STOCK = 2D;
    private static final Double SMALLER_DEAD_STOCK = 1D - 1D;

    private static final String DEFAULT_DEAD_STOCK_MR = "AAAAAAAAAA";
    private static final String UPDATED_DEAD_STOCK_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_OTHER_PAY = 1D;
    private static final Double UPDATED_OTHER_PAY = 2D;
    private static final Double SMALLER_OTHER_PAY = 1D - 1D;

    private static final String DEFAULT_OTHER_PAY_MR = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_PAY_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_LOSS = 1D;
    private static final Double UPDATED_LOSS = 2D;
    private static final Double SMALLER_LOSS = 1D - 1D;

    private static final String DEFAULT_LOSS_MR = "AAAAAAAAAA";
    private static final String UPDATED_LOSS_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_BAGAYAT = 1D;
    private static final Double UPDATED_TOTAL_BAGAYAT = 2D;
    private static final Double SMALLER_TOTAL_BAGAYAT = 1D - 1D;

    private static final String DEFAULT_TOTAL_BAGAYAT_MR = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_BAGAYAT_MR = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_JIRAYAT = 1D;
    private static final Double UPDATED_TOTAL_JIRAYAT = 2D;
    private static final Double SMALLER_TOTAL_JIRAYAT = 1D - 1D;

    private static final String DEFAULT_TOTAL_JIRAYAT_MR = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_JIRAYAT_MR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/kamal-societies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KamalSocietyRepository kamalSocietyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKamalSocietyMockMvc;

    private KamalSociety kamalSociety;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalSociety createEntity(EntityManager em) {
        KamalSociety kamalSociety = new KamalSociety()
            .pacsNumber(DEFAULT_PACS_NUMBER)
            .zindagiDate(DEFAULT_ZINDAGI_DATE)
            .zindagiDateMr(DEFAULT_ZINDAGI_DATE_MR)
            .village1(DEFAULT_VILLAGE_1)
            .village1Mr(DEFAULT_VILLAGE_1_MR)
            .village2(DEFAULT_VILLAGE_2)
            .village2Mr(DEFAULT_VILLAGE_2_MR)
            .village3(DEFAULT_VILLAGE_3)
            .village3Mr(DEFAULT_VILLAGE_3_MR)
            .totalLand(DEFAULT_TOTAL_LAND)
            .totalLandMr(DEFAULT_TOTAL_LAND_MR)
            .totalMem(DEFAULT_TOTAL_MEM)
            .totalMemMr(DEFAULT_TOTAL_MEM_MR)
            .totalNonMem(DEFAULT_TOTAL_NON_MEM)
            .totalNonMemMr(DEFAULT_TOTAL_NON_MEM_MR)
            .totalGMem(DEFAULT_TOTAL_G_MEM)
            .totalGMemMr(DEFAULT_TOTAL_G_MEM_MR)
            .memLoan(DEFAULT_MEM_LOAN)
            .memLoanMr(DEFAULT_MEM_LOAN_MR)
            .memDue(DEFAULT_MEM_DUE)
            .memDueMr(DEFAULT_MEM_DUE_MR)
            .memDueper(DEFAULT_MEM_DUEPER)
            .memDueperMr(DEFAULT_MEM_DUEPER_MR)
            .memVasulpatra(DEFAULT_MEM_VASULPATRA)
            .memVasulpatraMr(DEFAULT_MEM_VASULPATRA_MR)
            .memVasul(DEFAULT_MEM_VASUL)
            .memVasulMr(DEFAULT_MEM_VASUL_MR)
            .memVasulPer(DEFAULT_MEM_VASUL_PER)
            .memVasulPerMr(DEFAULT_MEM_VASUL_PER_MR)
            .bankLoan(DEFAULT_BANK_LOAN)
            .bankLoanMr(DEFAULT_BANK_LOAN_MR)
            .bankDue(DEFAULT_BANK_DUE)
            .bankDueMr(DEFAULT_BANK_DUE_MR)
            .bankDueper(DEFAULT_BANK_DUEPER)
            .bankDueperMr(DEFAULT_BANK_DUEPER_MR)
            .bankVasulpatra(DEFAULT_BANK_VASULPATRA)
            .bankVasulpatraMr(DEFAULT_BANK_VASULPATRA_MR)
            .bankVasul(DEFAULT_BANK_VASUL)
            .bankVasulMr(DEFAULT_BANK_VASUL_MR)
            .bankVasulPer(DEFAULT_BANK_VASUL_PER)
            .bankVasulPerMr(DEFAULT_BANK_VASUL_PER_MR)
            .shareCapital(DEFAULT_SHARE_CAPITAL)
            .shareCapitalMr(DEFAULT_SHARE_CAPITAL_MR)
            .share(DEFAULT_SHARE)
            .shareMr(DEFAULT_SHARE_MR)
            .funds(DEFAULT_FUNDS)
            .fundsMr(DEFAULT_FUNDS_MR)
            .deposit(DEFAULT_DEPOSIT)
            .depositMr(DEFAULT_DEPOSIT_MR)
            .payable(DEFAULT_PAYABLE)
            .payableMr(DEFAULT_PAYABLE_MR)
            .profit(DEFAULT_PROFIT)
            .profitMr(DEFAULT_PROFIT_MR)
            .cashInHand(DEFAULT_CASH_IN_HAND)
            .cashInHandMr(DEFAULT_CASH_IN_HAND_MR)
            .investment(DEFAULT_INVESTMENT)
            .investmentMr(DEFAULT_INVESTMENT_MR)
            .deadStock(DEFAULT_DEAD_STOCK)
            .deadStockMr(DEFAULT_DEAD_STOCK_MR)
            .otherPay(DEFAULT_OTHER_PAY)
            .otherPayMr(DEFAULT_OTHER_PAY_MR)
            .loss(DEFAULT_LOSS)
            .lossMr(DEFAULT_LOSS_MR)
            .totalBagayat(DEFAULT_TOTAL_BAGAYAT)
            .totalBagayatMr(DEFAULT_TOTAL_BAGAYAT_MR)
            .totalJirayat(DEFAULT_TOTAL_JIRAYAT)
            .totalJirayatMr(DEFAULT_TOTAL_JIRAYAT_MR);
        return kamalSociety;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KamalSociety createUpdatedEntity(EntityManager em) {
        KamalSociety kamalSociety = new KamalSociety()
            .pacsNumber(UPDATED_PACS_NUMBER)
            .zindagiDate(UPDATED_ZINDAGI_DATE)
            .zindagiDateMr(UPDATED_ZINDAGI_DATE_MR)
            .village1(UPDATED_VILLAGE_1)
            .village1Mr(UPDATED_VILLAGE_1_MR)
            .village2(UPDATED_VILLAGE_2)
            .village2Mr(UPDATED_VILLAGE_2_MR)
            .village3(UPDATED_VILLAGE_3)
            .village3Mr(UPDATED_VILLAGE_3_MR)
            .totalLand(UPDATED_TOTAL_LAND)
            .totalLandMr(UPDATED_TOTAL_LAND_MR)
            .totalMem(UPDATED_TOTAL_MEM)
            .totalMemMr(UPDATED_TOTAL_MEM_MR)
            .totalNonMem(UPDATED_TOTAL_NON_MEM)
            .totalNonMemMr(UPDATED_TOTAL_NON_MEM_MR)
            .totalGMem(UPDATED_TOTAL_G_MEM)
            .totalGMemMr(UPDATED_TOTAL_G_MEM_MR)
            .memLoan(UPDATED_MEM_LOAN)
            .memLoanMr(UPDATED_MEM_LOAN_MR)
            .memDue(UPDATED_MEM_DUE)
            .memDueMr(UPDATED_MEM_DUE_MR)
            .memDueper(UPDATED_MEM_DUEPER)
            .memDueperMr(UPDATED_MEM_DUEPER_MR)
            .memVasulpatra(UPDATED_MEM_VASULPATRA)
            .memVasulpatraMr(UPDATED_MEM_VASULPATRA_MR)
            .memVasul(UPDATED_MEM_VASUL)
            .memVasulMr(UPDATED_MEM_VASUL_MR)
            .memVasulPer(UPDATED_MEM_VASUL_PER)
            .memVasulPerMr(UPDATED_MEM_VASUL_PER_MR)
            .bankLoan(UPDATED_BANK_LOAN)
            .bankLoanMr(UPDATED_BANK_LOAN_MR)
            .bankDue(UPDATED_BANK_DUE)
            .bankDueMr(UPDATED_BANK_DUE_MR)
            .bankDueper(UPDATED_BANK_DUEPER)
            .bankDueperMr(UPDATED_BANK_DUEPER_MR)
            .bankVasulpatra(UPDATED_BANK_VASULPATRA)
            .bankVasulpatraMr(UPDATED_BANK_VASULPATRA_MR)
            .bankVasul(UPDATED_BANK_VASUL)
            .bankVasulMr(UPDATED_BANK_VASUL_MR)
            .bankVasulPer(UPDATED_BANK_VASUL_PER)
            .bankVasulPerMr(UPDATED_BANK_VASUL_PER_MR)
            .shareCapital(UPDATED_SHARE_CAPITAL)
            .shareCapitalMr(UPDATED_SHARE_CAPITAL_MR)
            .share(UPDATED_SHARE)
            .shareMr(UPDATED_SHARE_MR)
            .funds(UPDATED_FUNDS)
            .fundsMr(UPDATED_FUNDS_MR)
            .deposit(UPDATED_DEPOSIT)
            .depositMr(UPDATED_DEPOSIT_MR)
            .payable(UPDATED_PAYABLE)
            .payableMr(UPDATED_PAYABLE_MR)
            .profit(UPDATED_PROFIT)
            .profitMr(UPDATED_PROFIT_MR)
            .cashInHand(UPDATED_CASH_IN_HAND)
            .cashInHandMr(UPDATED_CASH_IN_HAND_MR)
            .investment(UPDATED_INVESTMENT)
            .investmentMr(UPDATED_INVESTMENT_MR)
            .deadStock(UPDATED_DEAD_STOCK)
            .deadStockMr(UPDATED_DEAD_STOCK_MR)
            .otherPay(UPDATED_OTHER_PAY)
            .otherPayMr(UPDATED_OTHER_PAY_MR)
            .loss(UPDATED_LOSS)
            .lossMr(UPDATED_LOSS_MR)
            .totalBagayat(UPDATED_TOTAL_BAGAYAT)
            .totalBagayatMr(UPDATED_TOTAL_BAGAYAT_MR)
            .totalJirayat(UPDATED_TOTAL_JIRAYAT)
            .totalJirayatMr(UPDATED_TOTAL_JIRAYAT_MR);
        return kamalSociety;
    }

    @BeforeEach
    public void initTest() {
        kamalSociety = createEntity(em);
    }

    @Test
    @Transactional
    void createKamalSociety() throws Exception {
        int databaseSizeBeforeCreate = kamalSocietyRepository.findAll().size();
        // Create the KamalSociety
        restKamalSocietyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalSociety)))
            .andExpect(status().isCreated());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeCreate + 1);
        KamalSociety testKamalSociety = kamalSocietyList.get(kamalSocietyList.size() - 1);
        assertThat(testKamalSociety.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKamalSociety.getZindagiDate()).isEqualTo(DEFAULT_ZINDAGI_DATE);
        assertThat(testKamalSociety.getZindagiDateMr()).isEqualTo(DEFAULT_ZINDAGI_DATE_MR);
        assertThat(testKamalSociety.getVillage1()).isEqualTo(DEFAULT_VILLAGE_1);
        assertThat(testKamalSociety.getVillage1Mr()).isEqualTo(DEFAULT_VILLAGE_1_MR);
        assertThat(testKamalSociety.getVillage2()).isEqualTo(DEFAULT_VILLAGE_2);
        assertThat(testKamalSociety.getVillage2Mr()).isEqualTo(DEFAULT_VILLAGE_2_MR);
        assertThat(testKamalSociety.getVillage3()).isEqualTo(DEFAULT_VILLAGE_3);
        assertThat(testKamalSociety.getVillage3Mr()).isEqualTo(DEFAULT_VILLAGE_3_MR);
        assertThat(testKamalSociety.getTotalLand()).isEqualTo(DEFAULT_TOTAL_LAND);
        assertThat(testKamalSociety.getTotalLandMr()).isEqualTo(DEFAULT_TOTAL_LAND_MR);
        assertThat(testKamalSociety.getTotalMem()).isEqualTo(DEFAULT_TOTAL_MEM);
        assertThat(testKamalSociety.getTotalMemMr()).isEqualTo(DEFAULT_TOTAL_MEM_MR);
        assertThat(testKamalSociety.getTotalNonMem()).isEqualTo(DEFAULT_TOTAL_NON_MEM);
        assertThat(testKamalSociety.getTotalNonMemMr()).isEqualTo(DEFAULT_TOTAL_NON_MEM_MR);
        assertThat(testKamalSociety.getTotalGMem()).isEqualTo(DEFAULT_TOTAL_G_MEM);
        assertThat(testKamalSociety.getTotalGMemMr()).isEqualTo(DEFAULT_TOTAL_G_MEM_MR);
        assertThat(testKamalSociety.getMemLoan()).isEqualTo(DEFAULT_MEM_LOAN);
        assertThat(testKamalSociety.getMemLoanMr()).isEqualTo(DEFAULT_MEM_LOAN_MR);
        assertThat(testKamalSociety.getMemDue()).isEqualTo(DEFAULT_MEM_DUE);
        assertThat(testKamalSociety.getMemDueMr()).isEqualTo(DEFAULT_MEM_DUE_MR);
        assertThat(testKamalSociety.getMemDueper()).isEqualTo(DEFAULT_MEM_DUEPER);
        assertThat(testKamalSociety.getMemDueperMr()).isEqualTo(DEFAULT_MEM_DUEPER_MR);
        assertThat(testKamalSociety.getMemVasulpatra()).isEqualTo(DEFAULT_MEM_VASULPATRA);
        assertThat(testKamalSociety.getMemVasulpatraMr()).isEqualTo(DEFAULT_MEM_VASULPATRA_MR);
        assertThat(testKamalSociety.getMemVasul()).isEqualTo(DEFAULT_MEM_VASUL);
        assertThat(testKamalSociety.getMemVasulMr()).isEqualTo(DEFAULT_MEM_VASUL_MR);
        assertThat(testKamalSociety.getMemVasulPer()).isEqualTo(DEFAULT_MEM_VASUL_PER);
        assertThat(testKamalSociety.getMemVasulPerMr()).isEqualTo(DEFAULT_MEM_VASUL_PER_MR);
        assertThat(testKamalSociety.getBankLoan()).isEqualTo(DEFAULT_BANK_LOAN);
        assertThat(testKamalSociety.getBankLoanMr()).isEqualTo(DEFAULT_BANK_LOAN_MR);
        assertThat(testKamalSociety.getBankDue()).isEqualTo(DEFAULT_BANK_DUE);
        assertThat(testKamalSociety.getBankDueMr()).isEqualTo(DEFAULT_BANK_DUE_MR);
        assertThat(testKamalSociety.getBankDueper()).isEqualTo(DEFAULT_BANK_DUEPER);
        assertThat(testKamalSociety.getBankDueperMr()).isEqualTo(DEFAULT_BANK_DUEPER_MR);
        assertThat(testKamalSociety.getBankVasulpatra()).isEqualTo(DEFAULT_BANK_VASULPATRA);
        assertThat(testKamalSociety.getBankVasulpatraMr()).isEqualTo(DEFAULT_BANK_VASULPATRA_MR);
        assertThat(testKamalSociety.getBankVasul()).isEqualTo(DEFAULT_BANK_VASUL);
        assertThat(testKamalSociety.getBankVasulMr()).isEqualTo(DEFAULT_BANK_VASUL_MR);
        assertThat(testKamalSociety.getBankVasulPer()).isEqualTo(DEFAULT_BANK_VASUL_PER);
        assertThat(testKamalSociety.getBankVasulPerMr()).isEqualTo(DEFAULT_BANK_VASUL_PER_MR);
        assertThat(testKamalSociety.getShareCapital()).isEqualTo(DEFAULT_SHARE_CAPITAL);
        assertThat(testKamalSociety.getShareCapitalMr()).isEqualTo(DEFAULT_SHARE_CAPITAL_MR);
        assertThat(testKamalSociety.getShare()).isEqualTo(DEFAULT_SHARE);
        assertThat(testKamalSociety.getShareMr()).isEqualTo(DEFAULT_SHARE_MR);
        assertThat(testKamalSociety.getFunds()).isEqualTo(DEFAULT_FUNDS);
        assertThat(testKamalSociety.getFundsMr()).isEqualTo(DEFAULT_FUNDS_MR);
        assertThat(testKamalSociety.getDeposit()).isEqualTo(DEFAULT_DEPOSIT);
        assertThat(testKamalSociety.getDepositMr()).isEqualTo(DEFAULT_DEPOSIT_MR);
        assertThat(testKamalSociety.getPayable()).isEqualTo(DEFAULT_PAYABLE);
        assertThat(testKamalSociety.getPayableMr()).isEqualTo(DEFAULT_PAYABLE_MR);
        assertThat(testKamalSociety.getProfit()).isEqualTo(DEFAULT_PROFIT);
        assertThat(testKamalSociety.getProfitMr()).isEqualTo(DEFAULT_PROFIT_MR);
        assertThat(testKamalSociety.getCashInHand()).isEqualTo(DEFAULT_CASH_IN_HAND);
        assertThat(testKamalSociety.getCashInHandMr()).isEqualTo(DEFAULT_CASH_IN_HAND_MR);
        assertThat(testKamalSociety.getInvestment()).isEqualTo(DEFAULT_INVESTMENT);
        assertThat(testKamalSociety.getInvestmentMr()).isEqualTo(DEFAULT_INVESTMENT_MR);
        assertThat(testKamalSociety.getDeadStock()).isEqualTo(DEFAULT_DEAD_STOCK);
        assertThat(testKamalSociety.getDeadStockMr()).isEqualTo(DEFAULT_DEAD_STOCK_MR);
        assertThat(testKamalSociety.getOtherPay()).isEqualTo(DEFAULT_OTHER_PAY);
        assertThat(testKamalSociety.getOtherPayMr()).isEqualTo(DEFAULT_OTHER_PAY_MR);
        assertThat(testKamalSociety.getLoss()).isEqualTo(DEFAULT_LOSS);
        assertThat(testKamalSociety.getLossMr()).isEqualTo(DEFAULT_LOSS_MR);
        assertThat(testKamalSociety.getTotalBagayat()).isEqualTo(DEFAULT_TOTAL_BAGAYAT);
        assertThat(testKamalSociety.getTotalBagayatMr()).isEqualTo(DEFAULT_TOTAL_BAGAYAT_MR);
        assertThat(testKamalSociety.getTotalJirayat()).isEqualTo(DEFAULT_TOTAL_JIRAYAT);
        assertThat(testKamalSociety.getTotalJirayatMr()).isEqualTo(DEFAULT_TOTAL_JIRAYAT_MR);
    }

    @Test
    @Transactional
    void createKamalSocietyWithExistingId() throws Exception {
        // Create the KamalSociety with an existing ID
        kamalSociety.setId(1L);

        int databaseSizeBeforeCreate = kamalSocietyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKamalSocietyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalSociety)))
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKamalSocieties() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].zindagiDate").value(hasItem(DEFAULT_ZINDAGI_DATE.toString())))
            .andExpect(jsonPath("$.[*].zindagiDateMr").value(hasItem(DEFAULT_ZINDAGI_DATE_MR)))
            .andExpect(jsonPath("$.[*].village1").value(hasItem(DEFAULT_VILLAGE_1)))
            .andExpect(jsonPath("$.[*].village1Mr").value(hasItem(DEFAULT_VILLAGE_1_MR)))
            .andExpect(jsonPath("$.[*].village2").value(hasItem(DEFAULT_VILLAGE_2)))
            .andExpect(jsonPath("$.[*].village2Mr").value(hasItem(DEFAULT_VILLAGE_2_MR)))
            .andExpect(jsonPath("$.[*].village3").value(hasItem(DEFAULT_VILLAGE_3)))
            .andExpect(jsonPath("$.[*].village3Mr").value(hasItem(DEFAULT_VILLAGE_3_MR)))
            .andExpect(jsonPath("$.[*].totalLand").value(hasItem(DEFAULT_TOTAL_LAND.doubleValue())))
            .andExpect(jsonPath("$.[*].totalLandMr").value(hasItem(DEFAULT_TOTAL_LAND_MR)))
            .andExpect(jsonPath("$.[*].totalMem").value(hasItem(DEFAULT_TOTAL_MEM.doubleValue())))
            .andExpect(jsonPath("$.[*].totalMemMr").value(hasItem(DEFAULT_TOTAL_MEM_MR)))
            .andExpect(jsonPath("$.[*].totalNonMem").value(hasItem(DEFAULT_TOTAL_NON_MEM.doubleValue())))
            .andExpect(jsonPath("$.[*].totalNonMemMr").value(hasItem(DEFAULT_TOTAL_NON_MEM_MR)))
            .andExpect(jsonPath("$.[*].totalGMem").value(hasItem(DEFAULT_TOTAL_G_MEM.doubleValue())))
            .andExpect(jsonPath("$.[*].totalGMemMr").value(hasItem(DEFAULT_TOTAL_G_MEM_MR)))
            .andExpect(jsonPath("$.[*].memLoan").value(hasItem(DEFAULT_MEM_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].memLoanMr").value(hasItem(DEFAULT_MEM_LOAN_MR)))
            .andExpect(jsonPath("$.[*].memDue").value(hasItem(DEFAULT_MEM_DUE.doubleValue())))
            .andExpect(jsonPath("$.[*].memDueMr").value(hasItem(DEFAULT_MEM_DUE_MR)))
            .andExpect(jsonPath("$.[*].memDueper").value(hasItem(DEFAULT_MEM_DUEPER.doubleValue())))
            .andExpect(jsonPath("$.[*].memDueperMr").value(hasItem(DEFAULT_MEM_DUEPER_MR)))
            .andExpect(jsonPath("$.[*].memVasulpatra").value(hasItem(DEFAULT_MEM_VASULPATRA.doubleValue())))
            .andExpect(jsonPath("$.[*].memVasulpatraMr").value(hasItem(DEFAULT_MEM_VASULPATRA_MR)))
            .andExpect(jsonPath("$.[*].memVasul").value(hasItem(DEFAULT_MEM_VASUL.doubleValue())))
            .andExpect(jsonPath("$.[*].memVasulMr").value(hasItem(DEFAULT_MEM_VASUL_MR)))
            .andExpect(jsonPath("$.[*].memVasulPer").value(hasItem(DEFAULT_MEM_VASUL_PER.doubleValue())))
            .andExpect(jsonPath("$.[*].memVasulPerMr").value(hasItem(DEFAULT_MEM_VASUL_PER_MR)))
            .andExpect(jsonPath("$.[*].bankLoan").value(hasItem(DEFAULT_BANK_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].bankLoanMr").value(hasItem(DEFAULT_BANK_LOAN_MR)))
            .andExpect(jsonPath("$.[*].bankDue").value(hasItem(DEFAULT_BANK_DUE.doubleValue())))
            .andExpect(jsonPath("$.[*].bankDueMr").value(hasItem(DEFAULT_BANK_DUE_MR)))
            .andExpect(jsonPath("$.[*].bankDueper").value(hasItem(DEFAULT_BANK_DUEPER.doubleValue())))
            .andExpect(jsonPath("$.[*].bankDueperMr").value(hasItem(DEFAULT_BANK_DUEPER_MR)))
            .andExpect(jsonPath("$.[*].bankVasulpatra").value(hasItem(DEFAULT_BANK_VASULPATRA.doubleValue())))
            .andExpect(jsonPath("$.[*].bankVasulpatraMr").value(hasItem(DEFAULT_BANK_VASULPATRA_MR)))
            .andExpect(jsonPath("$.[*].bankVasul").value(hasItem(DEFAULT_BANK_VASUL.doubleValue())))
            .andExpect(jsonPath("$.[*].bankVasulMr").value(hasItem(DEFAULT_BANK_VASUL_MR)))
            .andExpect(jsonPath("$.[*].bankVasulPer").value(hasItem(DEFAULT_BANK_VASUL_PER.doubleValue())))
            .andExpect(jsonPath("$.[*].bankVasulPerMr").value(hasItem(DEFAULT_BANK_VASUL_PER_MR)))
            .andExpect(jsonPath("$.[*].shareCapital").value(hasItem(DEFAULT_SHARE_CAPITAL.doubleValue())))
            .andExpect(jsonPath("$.[*].shareCapitalMr").value(hasItem(DEFAULT_SHARE_CAPITAL_MR)))
            .andExpect(jsonPath("$.[*].share").value(hasItem(DEFAULT_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].shareMr").value(hasItem(DEFAULT_SHARE_MR)))
            .andExpect(jsonPath("$.[*].funds").value(hasItem(DEFAULT_FUNDS.doubleValue())))
            .andExpect(jsonPath("$.[*].fundsMr").value(hasItem(DEFAULT_FUNDS_MR)))
            .andExpect(jsonPath("$.[*].deposit").value(hasItem(DEFAULT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].depositMr").value(hasItem(DEFAULT_DEPOSIT_MR)))
            .andExpect(jsonPath("$.[*].payable").value(hasItem(DEFAULT_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].payableMr").value(hasItem(DEFAULT_PAYABLE_MR)))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(DEFAULT_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].profitMr").value(hasItem(DEFAULT_PROFIT_MR)))
            .andExpect(jsonPath("$.[*].cashInHand").value(hasItem(DEFAULT_CASH_IN_HAND.doubleValue())))
            .andExpect(jsonPath("$.[*].cashInHandMr").value(hasItem(DEFAULT_CASH_IN_HAND_MR)))
            .andExpect(jsonPath("$.[*].investment").value(hasItem(DEFAULT_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].investmentMr").value(hasItem(DEFAULT_INVESTMENT_MR)))
            .andExpect(jsonPath("$.[*].deadStock").value(hasItem(DEFAULT_DEAD_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].deadStockMr").value(hasItem(DEFAULT_DEAD_STOCK_MR)))
            .andExpect(jsonPath("$.[*].otherPay").value(hasItem(DEFAULT_OTHER_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].otherPayMr").value(hasItem(DEFAULT_OTHER_PAY_MR)))
            .andExpect(jsonPath("$.[*].loss").value(hasItem(DEFAULT_LOSS.doubleValue())))
            .andExpect(jsonPath("$.[*].lossMr").value(hasItem(DEFAULT_LOSS_MR)))
            .andExpect(jsonPath("$.[*].totalBagayat").value(hasItem(DEFAULT_TOTAL_BAGAYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalBagayatMr").value(hasItem(DEFAULT_TOTAL_BAGAYAT_MR)))
            .andExpect(jsonPath("$.[*].totalJirayat").value(hasItem(DEFAULT_TOTAL_JIRAYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalJirayatMr").value(hasItem(DEFAULT_TOTAL_JIRAYAT_MR)));
    }

    @Test
    @Transactional
    void getKamalSociety() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get the kamalSociety
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL_ID, kamalSociety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kamalSociety.getId().intValue()))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER.intValue()))
            .andExpect(jsonPath("$.zindagiDate").value(DEFAULT_ZINDAGI_DATE.toString()))
            .andExpect(jsonPath("$.zindagiDateMr").value(DEFAULT_ZINDAGI_DATE_MR))
            .andExpect(jsonPath("$.village1").value(DEFAULT_VILLAGE_1))
            .andExpect(jsonPath("$.village1Mr").value(DEFAULT_VILLAGE_1_MR))
            .andExpect(jsonPath("$.village2").value(DEFAULT_VILLAGE_2))
            .andExpect(jsonPath("$.village2Mr").value(DEFAULT_VILLAGE_2_MR))
            .andExpect(jsonPath("$.village3").value(DEFAULT_VILLAGE_3))
            .andExpect(jsonPath("$.village3Mr").value(DEFAULT_VILLAGE_3_MR))
            .andExpect(jsonPath("$.totalLand").value(DEFAULT_TOTAL_LAND.doubleValue()))
            .andExpect(jsonPath("$.totalLandMr").value(DEFAULT_TOTAL_LAND_MR))
            .andExpect(jsonPath("$.totalMem").value(DEFAULT_TOTAL_MEM.doubleValue()))
            .andExpect(jsonPath("$.totalMemMr").value(DEFAULT_TOTAL_MEM_MR))
            .andExpect(jsonPath("$.totalNonMem").value(DEFAULT_TOTAL_NON_MEM.doubleValue()))
            .andExpect(jsonPath("$.totalNonMemMr").value(DEFAULT_TOTAL_NON_MEM_MR))
            .andExpect(jsonPath("$.totalGMem").value(DEFAULT_TOTAL_G_MEM.doubleValue()))
            .andExpect(jsonPath("$.totalGMemMr").value(DEFAULT_TOTAL_G_MEM_MR))
            .andExpect(jsonPath("$.memLoan").value(DEFAULT_MEM_LOAN.doubleValue()))
            .andExpect(jsonPath("$.memLoanMr").value(DEFAULT_MEM_LOAN_MR))
            .andExpect(jsonPath("$.memDue").value(DEFAULT_MEM_DUE.doubleValue()))
            .andExpect(jsonPath("$.memDueMr").value(DEFAULT_MEM_DUE_MR))
            .andExpect(jsonPath("$.memDueper").value(DEFAULT_MEM_DUEPER.doubleValue()))
            .andExpect(jsonPath("$.memDueperMr").value(DEFAULT_MEM_DUEPER_MR))
            .andExpect(jsonPath("$.memVasulpatra").value(DEFAULT_MEM_VASULPATRA.doubleValue()))
            .andExpect(jsonPath("$.memVasulpatraMr").value(DEFAULT_MEM_VASULPATRA_MR))
            .andExpect(jsonPath("$.memVasul").value(DEFAULT_MEM_VASUL.doubleValue()))
            .andExpect(jsonPath("$.memVasulMr").value(DEFAULT_MEM_VASUL_MR))
            .andExpect(jsonPath("$.memVasulPer").value(DEFAULT_MEM_VASUL_PER.doubleValue()))
            .andExpect(jsonPath("$.memVasulPerMr").value(DEFAULT_MEM_VASUL_PER_MR))
            .andExpect(jsonPath("$.bankLoan").value(DEFAULT_BANK_LOAN.doubleValue()))
            .andExpect(jsonPath("$.bankLoanMr").value(DEFAULT_BANK_LOAN_MR))
            .andExpect(jsonPath("$.bankDue").value(DEFAULT_BANK_DUE.doubleValue()))
            .andExpect(jsonPath("$.bankDueMr").value(DEFAULT_BANK_DUE_MR))
            .andExpect(jsonPath("$.bankDueper").value(DEFAULT_BANK_DUEPER.doubleValue()))
            .andExpect(jsonPath("$.bankDueperMr").value(DEFAULT_BANK_DUEPER_MR))
            .andExpect(jsonPath("$.bankVasulpatra").value(DEFAULT_BANK_VASULPATRA.doubleValue()))
            .andExpect(jsonPath("$.bankVasulpatraMr").value(DEFAULT_BANK_VASULPATRA_MR))
            .andExpect(jsonPath("$.bankVasul").value(DEFAULT_BANK_VASUL.doubleValue()))
            .andExpect(jsonPath("$.bankVasulMr").value(DEFAULT_BANK_VASUL_MR))
            .andExpect(jsonPath("$.bankVasulPer").value(DEFAULT_BANK_VASUL_PER.doubleValue()))
            .andExpect(jsonPath("$.bankVasulPerMr").value(DEFAULT_BANK_VASUL_PER_MR))
            .andExpect(jsonPath("$.shareCapital").value(DEFAULT_SHARE_CAPITAL.doubleValue()))
            .andExpect(jsonPath("$.shareCapitalMr").value(DEFAULT_SHARE_CAPITAL_MR))
            .andExpect(jsonPath("$.share").value(DEFAULT_SHARE.doubleValue()))
            .andExpect(jsonPath("$.shareMr").value(DEFAULT_SHARE_MR))
            .andExpect(jsonPath("$.funds").value(DEFAULT_FUNDS.doubleValue()))
            .andExpect(jsonPath("$.fundsMr").value(DEFAULT_FUNDS_MR))
            .andExpect(jsonPath("$.deposit").value(DEFAULT_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.depositMr").value(DEFAULT_DEPOSIT_MR))
            .andExpect(jsonPath("$.payable").value(DEFAULT_PAYABLE.doubleValue()))
            .andExpect(jsonPath("$.payableMr").value(DEFAULT_PAYABLE_MR))
            .andExpect(jsonPath("$.profit").value(DEFAULT_PROFIT.doubleValue()))
            .andExpect(jsonPath("$.profitMr").value(DEFAULT_PROFIT_MR))
            .andExpect(jsonPath("$.cashInHand").value(DEFAULT_CASH_IN_HAND.doubleValue()))
            .andExpect(jsonPath("$.cashInHandMr").value(DEFAULT_CASH_IN_HAND_MR))
            .andExpect(jsonPath("$.investment").value(DEFAULT_INVESTMENT.doubleValue()))
            .andExpect(jsonPath("$.investmentMr").value(DEFAULT_INVESTMENT_MR))
            .andExpect(jsonPath("$.deadStock").value(DEFAULT_DEAD_STOCK.doubleValue()))
            .andExpect(jsonPath("$.deadStockMr").value(DEFAULT_DEAD_STOCK_MR))
            .andExpect(jsonPath("$.otherPay").value(DEFAULT_OTHER_PAY.doubleValue()))
            .andExpect(jsonPath("$.otherPayMr").value(DEFAULT_OTHER_PAY_MR))
            .andExpect(jsonPath("$.loss").value(DEFAULT_LOSS.doubleValue()))
            .andExpect(jsonPath("$.lossMr").value(DEFAULT_LOSS_MR))
            .andExpect(jsonPath("$.totalBagayat").value(DEFAULT_TOTAL_BAGAYAT.doubleValue()))
            .andExpect(jsonPath("$.totalBagayatMr").value(DEFAULT_TOTAL_BAGAYAT_MR))
            .andExpect(jsonPath("$.totalJirayat").value(DEFAULT_TOTAL_JIRAYAT.doubleValue()))
            .andExpect(jsonPath("$.totalJirayatMr").value(DEFAULT_TOTAL_JIRAYAT_MR));
    }

    @Test
    @Transactional
    void getKamalSocietiesByIdFiltering() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        Long id = kamalSociety.getId();

        defaultKamalSocietyShouldBeFound("id.equals=" + id);
        defaultKamalSocietyShouldNotBeFound("id.notEquals=" + id);

        defaultKamalSocietyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKamalSocietyShouldNotBeFound("id.greaterThan=" + id);

        defaultKamalSocietyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKamalSocietyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber equals to DEFAULT_PACS_NUMBER
        defaultKamalSocietyShouldBeFound("pacsNumber.equals=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalSocietyList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKamalSocietyShouldNotBeFound("pacsNumber.equals=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber in DEFAULT_PACS_NUMBER or UPDATED_PACS_NUMBER
        defaultKamalSocietyShouldBeFound("pacsNumber.in=" + DEFAULT_PACS_NUMBER + "," + UPDATED_PACS_NUMBER);

        // Get all the kamalSocietyList where pacsNumber equals to UPDATED_PACS_NUMBER
        defaultKamalSocietyShouldNotBeFound("pacsNumber.in=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber is not null
        defaultKamalSocietyShouldBeFound("pacsNumber.specified=true");

        // Get all the kamalSocietyList where pacsNumber is null
        defaultKamalSocietyShouldNotBeFound("pacsNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber is greater than or equal to DEFAULT_PACS_NUMBER
        defaultKamalSocietyShouldBeFound("pacsNumber.greaterThanOrEqual=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalSocietyList where pacsNumber is greater than or equal to UPDATED_PACS_NUMBER
        defaultKamalSocietyShouldNotBeFound("pacsNumber.greaterThanOrEqual=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber is less than or equal to DEFAULT_PACS_NUMBER
        defaultKamalSocietyShouldBeFound("pacsNumber.lessThanOrEqual=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalSocietyList where pacsNumber is less than or equal to SMALLER_PACS_NUMBER
        defaultKamalSocietyShouldNotBeFound("pacsNumber.lessThanOrEqual=" + SMALLER_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber is less than DEFAULT_PACS_NUMBER
        defaultKamalSocietyShouldNotBeFound("pacsNumber.lessThan=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalSocietyList where pacsNumber is less than UPDATED_PACS_NUMBER
        defaultKamalSocietyShouldBeFound("pacsNumber.lessThan=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber is greater than DEFAULT_PACS_NUMBER
        defaultKamalSocietyShouldNotBeFound("pacsNumber.greaterThan=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalSocietyList where pacsNumber is greater than SMALLER_PACS_NUMBER
        defaultKamalSocietyShouldBeFound("pacsNumber.greaterThan=" + SMALLER_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiDate equals to DEFAULT_ZINDAGI_DATE
        defaultKamalSocietyShouldBeFound("zindagiDate.equals=" + DEFAULT_ZINDAGI_DATE);

        // Get all the kamalSocietyList where zindagiDate equals to UPDATED_ZINDAGI_DATE
        defaultKamalSocietyShouldNotBeFound("zindagiDate.equals=" + UPDATED_ZINDAGI_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiDate in DEFAULT_ZINDAGI_DATE or UPDATED_ZINDAGI_DATE
        defaultKamalSocietyShouldBeFound("zindagiDate.in=" + DEFAULT_ZINDAGI_DATE + "," + UPDATED_ZINDAGI_DATE);

        // Get all the kamalSocietyList where zindagiDate equals to UPDATED_ZINDAGI_DATE
        defaultKamalSocietyShouldNotBeFound("zindagiDate.in=" + UPDATED_ZINDAGI_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiDate is not null
        defaultKamalSocietyShouldBeFound("zindagiDate.specified=true");

        // Get all the kamalSocietyList where zindagiDate is null
        defaultKamalSocietyShouldNotBeFound("zindagiDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiDateMr equals to DEFAULT_ZINDAGI_DATE_MR
        defaultKamalSocietyShouldBeFound("zindagiDateMr.equals=" + DEFAULT_ZINDAGI_DATE_MR);

        // Get all the kamalSocietyList where zindagiDateMr equals to UPDATED_ZINDAGI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("zindagiDateMr.equals=" + UPDATED_ZINDAGI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiDateMr in DEFAULT_ZINDAGI_DATE_MR or UPDATED_ZINDAGI_DATE_MR
        defaultKamalSocietyShouldBeFound("zindagiDateMr.in=" + DEFAULT_ZINDAGI_DATE_MR + "," + UPDATED_ZINDAGI_DATE_MR);

        // Get all the kamalSocietyList where zindagiDateMr equals to UPDATED_ZINDAGI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("zindagiDateMr.in=" + UPDATED_ZINDAGI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiDateMr is not null
        defaultKamalSocietyShouldBeFound("zindagiDateMr.specified=true");

        // Get all the kamalSocietyList where zindagiDateMr is null
        defaultKamalSocietyShouldNotBeFound("zindagiDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiDateMr contains DEFAULT_ZINDAGI_DATE_MR
        defaultKamalSocietyShouldBeFound("zindagiDateMr.contains=" + DEFAULT_ZINDAGI_DATE_MR);

        // Get all the kamalSocietyList where zindagiDateMr contains UPDATED_ZINDAGI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("zindagiDateMr.contains=" + UPDATED_ZINDAGI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiDateMr does not contain DEFAULT_ZINDAGI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("zindagiDateMr.doesNotContain=" + DEFAULT_ZINDAGI_DATE_MR);

        // Get all the kamalSocietyList where zindagiDateMr does not contain UPDATED_ZINDAGI_DATE_MR
        defaultKamalSocietyShouldBeFound("zindagiDateMr.doesNotContain=" + UPDATED_ZINDAGI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1IsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1 equals to DEFAULT_VILLAGE_1
        defaultKamalSocietyShouldBeFound("village1.equals=" + DEFAULT_VILLAGE_1);

        // Get all the kamalSocietyList where village1 equals to UPDATED_VILLAGE_1
        defaultKamalSocietyShouldNotBeFound("village1.equals=" + UPDATED_VILLAGE_1);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1IsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1 in DEFAULT_VILLAGE_1 or UPDATED_VILLAGE_1
        defaultKamalSocietyShouldBeFound("village1.in=" + DEFAULT_VILLAGE_1 + "," + UPDATED_VILLAGE_1);

        // Get all the kamalSocietyList where village1 equals to UPDATED_VILLAGE_1
        defaultKamalSocietyShouldNotBeFound("village1.in=" + UPDATED_VILLAGE_1);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1IsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1 is not null
        defaultKamalSocietyShouldBeFound("village1.specified=true");

        // Get all the kamalSocietyList where village1 is null
        defaultKamalSocietyShouldNotBeFound("village1.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1ContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1 contains DEFAULT_VILLAGE_1
        defaultKamalSocietyShouldBeFound("village1.contains=" + DEFAULT_VILLAGE_1);

        // Get all the kamalSocietyList where village1 contains UPDATED_VILLAGE_1
        defaultKamalSocietyShouldNotBeFound("village1.contains=" + UPDATED_VILLAGE_1);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1NotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1 does not contain DEFAULT_VILLAGE_1
        defaultKamalSocietyShouldNotBeFound("village1.doesNotContain=" + DEFAULT_VILLAGE_1);

        // Get all the kamalSocietyList where village1 does not contain UPDATED_VILLAGE_1
        defaultKamalSocietyShouldBeFound("village1.doesNotContain=" + UPDATED_VILLAGE_1);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1MrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1Mr equals to DEFAULT_VILLAGE_1_MR
        defaultKamalSocietyShouldBeFound("village1Mr.equals=" + DEFAULT_VILLAGE_1_MR);

        // Get all the kamalSocietyList where village1Mr equals to UPDATED_VILLAGE_1_MR
        defaultKamalSocietyShouldNotBeFound("village1Mr.equals=" + UPDATED_VILLAGE_1_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1MrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1Mr in DEFAULT_VILLAGE_1_MR or UPDATED_VILLAGE_1_MR
        defaultKamalSocietyShouldBeFound("village1Mr.in=" + DEFAULT_VILLAGE_1_MR + "," + UPDATED_VILLAGE_1_MR);

        // Get all the kamalSocietyList where village1Mr equals to UPDATED_VILLAGE_1_MR
        defaultKamalSocietyShouldNotBeFound("village1Mr.in=" + UPDATED_VILLAGE_1_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1MrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1Mr is not null
        defaultKamalSocietyShouldBeFound("village1Mr.specified=true");

        // Get all the kamalSocietyList where village1Mr is null
        defaultKamalSocietyShouldNotBeFound("village1Mr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1MrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1Mr contains DEFAULT_VILLAGE_1_MR
        defaultKamalSocietyShouldBeFound("village1Mr.contains=" + DEFAULT_VILLAGE_1_MR);

        // Get all the kamalSocietyList where village1Mr contains UPDATED_VILLAGE_1_MR
        defaultKamalSocietyShouldNotBeFound("village1Mr.contains=" + UPDATED_VILLAGE_1_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage1MrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village1Mr does not contain DEFAULT_VILLAGE_1_MR
        defaultKamalSocietyShouldNotBeFound("village1Mr.doesNotContain=" + DEFAULT_VILLAGE_1_MR);

        // Get all the kamalSocietyList where village1Mr does not contain UPDATED_VILLAGE_1_MR
        defaultKamalSocietyShouldBeFound("village1Mr.doesNotContain=" + UPDATED_VILLAGE_1_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2IsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2 equals to DEFAULT_VILLAGE_2
        defaultKamalSocietyShouldBeFound("village2.equals=" + DEFAULT_VILLAGE_2);

        // Get all the kamalSocietyList where village2 equals to UPDATED_VILLAGE_2
        defaultKamalSocietyShouldNotBeFound("village2.equals=" + UPDATED_VILLAGE_2);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2IsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2 in DEFAULT_VILLAGE_2 or UPDATED_VILLAGE_2
        defaultKamalSocietyShouldBeFound("village2.in=" + DEFAULT_VILLAGE_2 + "," + UPDATED_VILLAGE_2);

        // Get all the kamalSocietyList where village2 equals to UPDATED_VILLAGE_2
        defaultKamalSocietyShouldNotBeFound("village2.in=" + UPDATED_VILLAGE_2);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2IsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2 is not null
        defaultKamalSocietyShouldBeFound("village2.specified=true");

        // Get all the kamalSocietyList where village2 is null
        defaultKamalSocietyShouldNotBeFound("village2.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2ContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2 contains DEFAULT_VILLAGE_2
        defaultKamalSocietyShouldBeFound("village2.contains=" + DEFAULT_VILLAGE_2);

        // Get all the kamalSocietyList where village2 contains UPDATED_VILLAGE_2
        defaultKamalSocietyShouldNotBeFound("village2.contains=" + UPDATED_VILLAGE_2);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2NotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2 does not contain DEFAULT_VILLAGE_2
        defaultKamalSocietyShouldNotBeFound("village2.doesNotContain=" + DEFAULT_VILLAGE_2);

        // Get all the kamalSocietyList where village2 does not contain UPDATED_VILLAGE_2
        defaultKamalSocietyShouldBeFound("village2.doesNotContain=" + UPDATED_VILLAGE_2);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2MrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2Mr equals to DEFAULT_VILLAGE_2_MR
        defaultKamalSocietyShouldBeFound("village2Mr.equals=" + DEFAULT_VILLAGE_2_MR);

        // Get all the kamalSocietyList where village2Mr equals to UPDATED_VILLAGE_2_MR
        defaultKamalSocietyShouldNotBeFound("village2Mr.equals=" + UPDATED_VILLAGE_2_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2MrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2Mr in DEFAULT_VILLAGE_2_MR or UPDATED_VILLAGE_2_MR
        defaultKamalSocietyShouldBeFound("village2Mr.in=" + DEFAULT_VILLAGE_2_MR + "," + UPDATED_VILLAGE_2_MR);

        // Get all the kamalSocietyList where village2Mr equals to UPDATED_VILLAGE_2_MR
        defaultKamalSocietyShouldNotBeFound("village2Mr.in=" + UPDATED_VILLAGE_2_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2MrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2Mr is not null
        defaultKamalSocietyShouldBeFound("village2Mr.specified=true");

        // Get all the kamalSocietyList where village2Mr is null
        defaultKamalSocietyShouldNotBeFound("village2Mr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2MrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2Mr contains DEFAULT_VILLAGE_2_MR
        defaultKamalSocietyShouldBeFound("village2Mr.contains=" + DEFAULT_VILLAGE_2_MR);

        // Get all the kamalSocietyList where village2Mr contains UPDATED_VILLAGE_2_MR
        defaultKamalSocietyShouldNotBeFound("village2Mr.contains=" + UPDATED_VILLAGE_2_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage2MrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village2Mr does not contain DEFAULT_VILLAGE_2_MR
        defaultKamalSocietyShouldNotBeFound("village2Mr.doesNotContain=" + DEFAULT_VILLAGE_2_MR);

        // Get all the kamalSocietyList where village2Mr does not contain UPDATED_VILLAGE_2_MR
        defaultKamalSocietyShouldBeFound("village2Mr.doesNotContain=" + UPDATED_VILLAGE_2_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3IsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3 equals to DEFAULT_VILLAGE_3
        defaultKamalSocietyShouldBeFound("village3.equals=" + DEFAULT_VILLAGE_3);

        // Get all the kamalSocietyList where village3 equals to UPDATED_VILLAGE_3
        defaultKamalSocietyShouldNotBeFound("village3.equals=" + UPDATED_VILLAGE_3);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3IsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3 in DEFAULT_VILLAGE_3 or UPDATED_VILLAGE_3
        defaultKamalSocietyShouldBeFound("village3.in=" + DEFAULT_VILLAGE_3 + "," + UPDATED_VILLAGE_3);

        // Get all the kamalSocietyList where village3 equals to UPDATED_VILLAGE_3
        defaultKamalSocietyShouldNotBeFound("village3.in=" + UPDATED_VILLAGE_3);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3IsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3 is not null
        defaultKamalSocietyShouldBeFound("village3.specified=true");

        // Get all the kamalSocietyList where village3 is null
        defaultKamalSocietyShouldNotBeFound("village3.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3ContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3 contains DEFAULT_VILLAGE_3
        defaultKamalSocietyShouldBeFound("village3.contains=" + DEFAULT_VILLAGE_3);

        // Get all the kamalSocietyList where village3 contains UPDATED_VILLAGE_3
        defaultKamalSocietyShouldNotBeFound("village3.contains=" + UPDATED_VILLAGE_3);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3NotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3 does not contain DEFAULT_VILLAGE_3
        defaultKamalSocietyShouldNotBeFound("village3.doesNotContain=" + DEFAULT_VILLAGE_3);

        // Get all the kamalSocietyList where village3 does not contain UPDATED_VILLAGE_3
        defaultKamalSocietyShouldBeFound("village3.doesNotContain=" + UPDATED_VILLAGE_3);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3MrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3Mr equals to DEFAULT_VILLAGE_3_MR
        defaultKamalSocietyShouldBeFound("village3Mr.equals=" + DEFAULT_VILLAGE_3_MR);

        // Get all the kamalSocietyList where village3Mr equals to UPDATED_VILLAGE_3_MR
        defaultKamalSocietyShouldNotBeFound("village3Mr.equals=" + UPDATED_VILLAGE_3_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3MrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3Mr in DEFAULT_VILLAGE_3_MR or UPDATED_VILLAGE_3_MR
        defaultKamalSocietyShouldBeFound("village3Mr.in=" + DEFAULT_VILLAGE_3_MR + "," + UPDATED_VILLAGE_3_MR);

        // Get all the kamalSocietyList where village3Mr equals to UPDATED_VILLAGE_3_MR
        defaultKamalSocietyShouldNotBeFound("village3Mr.in=" + UPDATED_VILLAGE_3_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3MrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3Mr is not null
        defaultKamalSocietyShouldBeFound("village3Mr.specified=true");

        // Get all the kamalSocietyList where village3Mr is null
        defaultKamalSocietyShouldNotBeFound("village3Mr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3MrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3Mr contains DEFAULT_VILLAGE_3_MR
        defaultKamalSocietyShouldBeFound("village3Mr.contains=" + DEFAULT_VILLAGE_3_MR);

        // Get all the kamalSocietyList where village3Mr contains UPDATED_VILLAGE_3_MR
        defaultKamalSocietyShouldNotBeFound("village3Mr.contains=" + UPDATED_VILLAGE_3_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillage3MrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where village3Mr does not contain DEFAULT_VILLAGE_3_MR
        defaultKamalSocietyShouldNotBeFound("village3Mr.doesNotContain=" + DEFAULT_VILLAGE_3_MR);

        // Get all the kamalSocietyList where village3Mr does not contain UPDATED_VILLAGE_3_MR
        defaultKamalSocietyShouldBeFound("village3Mr.doesNotContain=" + UPDATED_VILLAGE_3_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand equals to DEFAULT_TOTAL_LAND
        defaultKamalSocietyShouldBeFound("totalLand.equals=" + DEFAULT_TOTAL_LAND);

        // Get all the kamalSocietyList where totalLand equals to UPDATED_TOTAL_LAND
        defaultKamalSocietyShouldNotBeFound("totalLand.equals=" + UPDATED_TOTAL_LAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand in DEFAULT_TOTAL_LAND or UPDATED_TOTAL_LAND
        defaultKamalSocietyShouldBeFound("totalLand.in=" + DEFAULT_TOTAL_LAND + "," + UPDATED_TOTAL_LAND);

        // Get all the kamalSocietyList where totalLand equals to UPDATED_TOTAL_LAND
        defaultKamalSocietyShouldNotBeFound("totalLand.in=" + UPDATED_TOTAL_LAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand is not null
        defaultKamalSocietyShouldBeFound("totalLand.specified=true");

        // Get all the kamalSocietyList where totalLand is null
        defaultKamalSocietyShouldNotBeFound("totalLand.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand is greater than or equal to DEFAULT_TOTAL_LAND
        defaultKamalSocietyShouldBeFound("totalLand.greaterThanOrEqual=" + DEFAULT_TOTAL_LAND);

        // Get all the kamalSocietyList where totalLand is greater than or equal to UPDATED_TOTAL_LAND
        defaultKamalSocietyShouldNotBeFound("totalLand.greaterThanOrEqual=" + UPDATED_TOTAL_LAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand is less than or equal to DEFAULT_TOTAL_LAND
        defaultKamalSocietyShouldBeFound("totalLand.lessThanOrEqual=" + DEFAULT_TOTAL_LAND);

        // Get all the kamalSocietyList where totalLand is less than or equal to SMALLER_TOTAL_LAND
        defaultKamalSocietyShouldNotBeFound("totalLand.lessThanOrEqual=" + SMALLER_TOTAL_LAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand is less than DEFAULT_TOTAL_LAND
        defaultKamalSocietyShouldNotBeFound("totalLand.lessThan=" + DEFAULT_TOTAL_LAND);

        // Get all the kamalSocietyList where totalLand is less than UPDATED_TOTAL_LAND
        defaultKamalSocietyShouldBeFound("totalLand.lessThan=" + UPDATED_TOTAL_LAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand is greater than DEFAULT_TOTAL_LAND
        defaultKamalSocietyShouldNotBeFound("totalLand.greaterThan=" + DEFAULT_TOTAL_LAND);

        // Get all the kamalSocietyList where totalLand is greater than SMALLER_TOTAL_LAND
        defaultKamalSocietyShouldBeFound("totalLand.greaterThan=" + SMALLER_TOTAL_LAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLandMr equals to DEFAULT_TOTAL_LAND_MR
        defaultKamalSocietyShouldBeFound("totalLandMr.equals=" + DEFAULT_TOTAL_LAND_MR);

        // Get all the kamalSocietyList where totalLandMr equals to UPDATED_TOTAL_LAND_MR
        defaultKamalSocietyShouldNotBeFound("totalLandMr.equals=" + UPDATED_TOTAL_LAND_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLandMr in DEFAULT_TOTAL_LAND_MR or UPDATED_TOTAL_LAND_MR
        defaultKamalSocietyShouldBeFound("totalLandMr.in=" + DEFAULT_TOTAL_LAND_MR + "," + UPDATED_TOTAL_LAND_MR);

        // Get all the kamalSocietyList where totalLandMr equals to UPDATED_TOTAL_LAND_MR
        defaultKamalSocietyShouldNotBeFound("totalLandMr.in=" + UPDATED_TOTAL_LAND_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLandMr is not null
        defaultKamalSocietyShouldBeFound("totalLandMr.specified=true");

        // Get all the kamalSocietyList where totalLandMr is null
        defaultKamalSocietyShouldNotBeFound("totalLandMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLandMr contains DEFAULT_TOTAL_LAND_MR
        defaultKamalSocietyShouldBeFound("totalLandMr.contains=" + DEFAULT_TOTAL_LAND_MR);

        // Get all the kamalSocietyList where totalLandMr contains UPDATED_TOTAL_LAND_MR
        defaultKamalSocietyShouldNotBeFound("totalLandMr.contains=" + UPDATED_TOTAL_LAND_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLandMr does not contain DEFAULT_TOTAL_LAND_MR
        defaultKamalSocietyShouldNotBeFound("totalLandMr.doesNotContain=" + DEFAULT_TOTAL_LAND_MR);

        // Get all the kamalSocietyList where totalLandMr does not contain UPDATED_TOTAL_LAND_MR
        defaultKamalSocietyShouldBeFound("totalLandMr.doesNotContain=" + UPDATED_TOTAL_LAND_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMem equals to DEFAULT_TOTAL_MEM
        defaultKamalSocietyShouldBeFound("totalMem.equals=" + DEFAULT_TOTAL_MEM);

        // Get all the kamalSocietyList where totalMem equals to UPDATED_TOTAL_MEM
        defaultKamalSocietyShouldNotBeFound("totalMem.equals=" + UPDATED_TOTAL_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMem in DEFAULT_TOTAL_MEM or UPDATED_TOTAL_MEM
        defaultKamalSocietyShouldBeFound("totalMem.in=" + DEFAULT_TOTAL_MEM + "," + UPDATED_TOTAL_MEM);

        // Get all the kamalSocietyList where totalMem equals to UPDATED_TOTAL_MEM
        defaultKamalSocietyShouldNotBeFound("totalMem.in=" + UPDATED_TOTAL_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMem is not null
        defaultKamalSocietyShouldBeFound("totalMem.specified=true");

        // Get all the kamalSocietyList where totalMem is null
        defaultKamalSocietyShouldNotBeFound("totalMem.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMem is greater than or equal to DEFAULT_TOTAL_MEM
        defaultKamalSocietyShouldBeFound("totalMem.greaterThanOrEqual=" + DEFAULT_TOTAL_MEM);

        // Get all the kamalSocietyList where totalMem is greater than or equal to UPDATED_TOTAL_MEM
        defaultKamalSocietyShouldNotBeFound("totalMem.greaterThanOrEqual=" + UPDATED_TOTAL_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMem is less than or equal to DEFAULT_TOTAL_MEM
        defaultKamalSocietyShouldBeFound("totalMem.lessThanOrEqual=" + DEFAULT_TOTAL_MEM);

        // Get all the kamalSocietyList where totalMem is less than or equal to SMALLER_TOTAL_MEM
        defaultKamalSocietyShouldNotBeFound("totalMem.lessThanOrEqual=" + SMALLER_TOTAL_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMem is less than DEFAULT_TOTAL_MEM
        defaultKamalSocietyShouldNotBeFound("totalMem.lessThan=" + DEFAULT_TOTAL_MEM);

        // Get all the kamalSocietyList where totalMem is less than UPDATED_TOTAL_MEM
        defaultKamalSocietyShouldBeFound("totalMem.lessThan=" + UPDATED_TOTAL_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMem is greater than DEFAULT_TOTAL_MEM
        defaultKamalSocietyShouldNotBeFound("totalMem.greaterThan=" + DEFAULT_TOTAL_MEM);

        // Get all the kamalSocietyList where totalMem is greater than SMALLER_TOTAL_MEM
        defaultKamalSocietyShouldBeFound("totalMem.greaterThan=" + SMALLER_TOTAL_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMemMr equals to DEFAULT_TOTAL_MEM_MR
        defaultKamalSocietyShouldBeFound("totalMemMr.equals=" + DEFAULT_TOTAL_MEM_MR);

        // Get all the kamalSocietyList where totalMemMr equals to UPDATED_TOTAL_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalMemMr.equals=" + UPDATED_TOTAL_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMemMr in DEFAULT_TOTAL_MEM_MR or UPDATED_TOTAL_MEM_MR
        defaultKamalSocietyShouldBeFound("totalMemMr.in=" + DEFAULT_TOTAL_MEM_MR + "," + UPDATED_TOTAL_MEM_MR);

        // Get all the kamalSocietyList where totalMemMr equals to UPDATED_TOTAL_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalMemMr.in=" + UPDATED_TOTAL_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMemMr is not null
        defaultKamalSocietyShouldBeFound("totalMemMr.specified=true");

        // Get all the kamalSocietyList where totalMemMr is null
        defaultKamalSocietyShouldNotBeFound("totalMemMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMemMr contains DEFAULT_TOTAL_MEM_MR
        defaultKamalSocietyShouldBeFound("totalMemMr.contains=" + DEFAULT_TOTAL_MEM_MR);

        // Get all the kamalSocietyList where totalMemMr contains UPDATED_TOTAL_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalMemMr.contains=" + UPDATED_TOTAL_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalMemMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalMemMr does not contain DEFAULT_TOTAL_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalMemMr.doesNotContain=" + DEFAULT_TOTAL_MEM_MR);

        // Get all the kamalSocietyList where totalMemMr does not contain UPDATED_TOTAL_MEM_MR
        defaultKamalSocietyShouldBeFound("totalMemMr.doesNotContain=" + UPDATED_TOTAL_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMem equals to DEFAULT_TOTAL_NON_MEM
        defaultKamalSocietyShouldBeFound("totalNonMem.equals=" + DEFAULT_TOTAL_NON_MEM);

        // Get all the kamalSocietyList where totalNonMem equals to UPDATED_TOTAL_NON_MEM
        defaultKamalSocietyShouldNotBeFound("totalNonMem.equals=" + UPDATED_TOTAL_NON_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMem in DEFAULT_TOTAL_NON_MEM or UPDATED_TOTAL_NON_MEM
        defaultKamalSocietyShouldBeFound("totalNonMem.in=" + DEFAULT_TOTAL_NON_MEM + "," + UPDATED_TOTAL_NON_MEM);

        // Get all the kamalSocietyList where totalNonMem equals to UPDATED_TOTAL_NON_MEM
        defaultKamalSocietyShouldNotBeFound("totalNonMem.in=" + UPDATED_TOTAL_NON_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMem is not null
        defaultKamalSocietyShouldBeFound("totalNonMem.specified=true");

        // Get all the kamalSocietyList where totalNonMem is null
        defaultKamalSocietyShouldNotBeFound("totalNonMem.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMem is greater than or equal to DEFAULT_TOTAL_NON_MEM
        defaultKamalSocietyShouldBeFound("totalNonMem.greaterThanOrEqual=" + DEFAULT_TOTAL_NON_MEM);

        // Get all the kamalSocietyList where totalNonMem is greater than or equal to UPDATED_TOTAL_NON_MEM
        defaultKamalSocietyShouldNotBeFound("totalNonMem.greaterThanOrEqual=" + UPDATED_TOTAL_NON_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMem is less than or equal to DEFAULT_TOTAL_NON_MEM
        defaultKamalSocietyShouldBeFound("totalNonMem.lessThanOrEqual=" + DEFAULT_TOTAL_NON_MEM);

        // Get all the kamalSocietyList where totalNonMem is less than or equal to SMALLER_TOTAL_NON_MEM
        defaultKamalSocietyShouldNotBeFound("totalNonMem.lessThanOrEqual=" + SMALLER_TOTAL_NON_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMem is less than DEFAULT_TOTAL_NON_MEM
        defaultKamalSocietyShouldNotBeFound("totalNonMem.lessThan=" + DEFAULT_TOTAL_NON_MEM);

        // Get all the kamalSocietyList where totalNonMem is less than UPDATED_TOTAL_NON_MEM
        defaultKamalSocietyShouldBeFound("totalNonMem.lessThan=" + UPDATED_TOTAL_NON_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMem is greater than DEFAULT_TOTAL_NON_MEM
        defaultKamalSocietyShouldNotBeFound("totalNonMem.greaterThan=" + DEFAULT_TOTAL_NON_MEM);

        // Get all the kamalSocietyList where totalNonMem is greater than SMALLER_TOTAL_NON_MEM
        defaultKamalSocietyShouldBeFound("totalNonMem.greaterThan=" + SMALLER_TOTAL_NON_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMemMr equals to DEFAULT_TOTAL_NON_MEM_MR
        defaultKamalSocietyShouldBeFound("totalNonMemMr.equals=" + DEFAULT_TOTAL_NON_MEM_MR);

        // Get all the kamalSocietyList where totalNonMemMr equals to UPDATED_TOTAL_NON_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalNonMemMr.equals=" + UPDATED_TOTAL_NON_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMemMr in DEFAULT_TOTAL_NON_MEM_MR or UPDATED_TOTAL_NON_MEM_MR
        defaultKamalSocietyShouldBeFound("totalNonMemMr.in=" + DEFAULT_TOTAL_NON_MEM_MR + "," + UPDATED_TOTAL_NON_MEM_MR);

        // Get all the kamalSocietyList where totalNonMemMr equals to UPDATED_TOTAL_NON_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalNonMemMr.in=" + UPDATED_TOTAL_NON_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMemMr is not null
        defaultKamalSocietyShouldBeFound("totalNonMemMr.specified=true");

        // Get all the kamalSocietyList where totalNonMemMr is null
        defaultKamalSocietyShouldNotBeFound("totalNonMemMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMemMr contains DEFAULT_TOTAL_NON_MEM_MR
        defaultKamalSocietyShouldBeFound("totalNonMemMr.contains=" + DEFAULT_TOTAL_NON_MEM_MR);

        // Get all the kamalSocietyList where totalNonMemMr contains UPDATED_TOTAL_NON_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalNonMemMr.contains=" + UPDATED_TOTAL_NON_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalNonMemMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalNonMemMr does not contain DEFAULT_TOTAL_NON_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalNonMemMr.doesNotContain=" + DEFAULT_TOTAL_NON_MEM_MR);

        // Get all the kamalSocietyList where totalNonMemMr does not contain UPDATED_TOTAL_NON_MEM_MR
        defaultKamalSocietyShouldBeFound("totalNonMemMr.doesNotContain=" + UPDATED_TOTAL_NON_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMem equals to DEFAULT_TOTAL_G_MEM
        defaultKamalSocietyShouldBeFound("totalGMem.equals=" + DEFAULT_TOTAL_G_MEM);

        // Get all the kamalSocietyList where totalGMem equals to UPDATED_TOTAL_G_MEM
        defaultKamalSocietyShouldNotBeFound("totalGMem.equals=" + UPDATED_TOTAL_G_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMem in DEFAULT_TOTAL_G_MEM or UPDATED_TOTAL_G_MEM
        defaultKamalSocietyShouldBeFound("totalGMem.in=" + DEFAULT_TOTAL_G_MEM + "," + UPDATED_TOTAL_G_MEM);

        // Get all the kamalSocietyList where totalGMem equals to UPDATED_TOTAL_G_MEM
        defaultKamalSocietyShouldNotBeFound("totalGMem.in=" + UPDATED_TOTAL_G_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMem is not null
        defaultKamalSocietyShouldBeFound("totalGMem.specified=true");

        // Get all the kamalSocietyList where totalGMem is null
        defaultKamalSocietyShouldNotBeFound("totalGMem.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMem is greater than or equal to DEFAULT_TOTAL_G_MEM
        defaultKamalSocietyShouldBeFound("totalGMem.greaterThanOrEqual=" + DEFAULT_TOTAL_G_MEM);

        // Get all the kamalSocietyList where totalGMem is greater than or equal to UPDATED_TOTAL_G_MEM
        defaultKamalSocietyShouldNotBeFound("totalGMem.greaterThanOrEqual=" + UPDATED_TOTAL_G_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMem is less than or equal to DEFAULT_TOTAL_G_MEM
        defaultKamalSocietyShouldBeFound("totalGMem.lessThanOrEqual=" + DEFAULT_TOTAL_G_MEM);

        // Get all the kamalSocietyList where totalGMem is less than or equal to SMALLER_TOTAL_G_MEM
        defaultKamalSocietyShouldNotBeFound("totalGMem.lessThanOrEqual=" + SMALLER_TOTAL_G_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMem is less than DEFAULT_TOTAL_G_MEM
        defaultKamalSocietyShouldNotBeFound("totalGMem.lessThan=" + DEFAULT_TOTAL_G_MEM);

        // Get all the kamalSocietyList where totalGMem is less than UPDATED_TOTAL_G_MEM
        defaultKamalSocietyShouldBeFound("totalGMem.lessThan=" + UPDATED_TOTAL_G_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMem is greater than DEFAULT_TOTAL_G_MEM
        defaultKamalSocietyShouldNotBeFound("totalGMem.greaterThan=" + DEFAULT_TOTAL_G_MEM);

        // Get all the kamalSocietyList where totalGMem is greater than SMALLER_TOTAL_G_MEM
        defaultKamalSocietyShouldBeFound("totalGMem.greaterThan=" + SMALLER_TOTAL_G_MEM);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMemMr equals to DEFAULT_TOTAL_G_MEM_MR
        defaultKamalSocietyShouldBeFound("totalGMemMr.equals=" + DEFAULT_TOTAL_G_MEM_MR);

        // Get all the kamalSocietyList where totalGMemMr equals to UPDATED_TOTAL_G_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalGMemMr.equals=" + UPDATED_TOTAL_G_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMemMr in DEFAULT_TOTAL_G_MEM_MR or UPDATED_TOTAL_G_MEM_MR
        defaultKamalSocietyShouldBeFound("totalGMemMr.in=" + DEFAULT_TOTAL_G_MEM_MR + "," + UPDATED_TOTAL_G_MEM_MR);

        // Get all the kamalSocietyList where totalGMemMr equals to UPDATED_TOTAL_G_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalGMemMr.in=" + UPDATED_TOTAL_G_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMemMr is not null
        defaultKamalSocietyShouldBeFound("totalGMemMr.specified=true");

        // Get all the kamalSocietyList where totalGMemMr is null
        defaultKamalSocietyShouldNotBeFound("totalGMemMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMemMr contains DEFAULT_TOTAL_G_MEM_MR
        defaultKamalSocietyShouldBeFound("totalGMemMr.contains=" + DEFAULT_TOTAL_G_MEM_MR);

        // Get all the kamalSocietyList where totalGMemMr contains UPDATED_TOTAL_G_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalGMemMr.contains=" + UPDATED_TOTAL_G_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalGMemMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalGMemMr does not contain DEFAULT_TOTAL_G_MEM_MR
        defaultKamalSocietyShouldNotBeFound("totalGMemMr.doesNotContain=" + DEFAULT_TOTAL_G_MEM_MR);

        // Get all the kamalSocietyList where totalGMemMr does not contain UPDATED_TOTAL_G_MEM_MR
        defaultKamalSocietyShouldBeFound("totalGMemMr.doesNotContain=" + UPDATED_TOTAL_G_MEM_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan equals to DEFAULT_MEM_LOAN
        defaultKamalSocietyShouldBeFound("memLoan.equals=" + DEFAULT_MEM_LOAN);

        // Get all the kamalSocietyList where memLoan equals to UPDATED_MEM_LOAN
        defaultKamalSocietyShouldNotBeFound("memLoan.equals=" + UPDATED_MEM_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan in DEFAULT_MEM_LOAN or UPDATED_MEM_LOAN
        defaultKamalSocietyShouldBeFound("memLoan.in=" + DEFAULT_MEM_LOAN + "," + UPDATED_MEM_LOAN);

        // Get all the kamalSocietyList where memLoan equals to UPDATED_MEM_LOAN
        defaultKamalSocietyShouldNotBeFound("memLoan.in=" + UPDATED_MEM_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan is not null
        defaultKamalSocietyShouldBeFound("memLoan.specified=true");

        // Get all the kamalSocietyList where memLoan is null
        defaultKamalSocietyShouldNotBeFound("memLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan is greater than or equal to DEFAULT_MEM_LOAN
        defaultKamalSocietyShouldBeFound("memLoan.greaterThanOrEqual=" + DEFAULT_MEM_LOAN);

        // Get all the kamalSocietyList where memLoan is greater than or equal to UPDATED_MEM_LOAN
        defaultKamalSocietyShouldNotBeFound("memLoan.greaterThanOrEqual=" + UPDATED_MEM_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan is less than or equal to DEFAULT_MEM_LOAN
        defaultKamalSocietyShouldBeFound("memLoan.lessThanOrEqual=" + DEFAULT_MEM_LOAN);

        // Get all the kamalSocietyList where memLoan is less than or equal to SMALLER_MEM_LOAN
        defaultKamalSocietyShouldNotBeFound("memLoan.lessThanOrEqual=" + SMALLER_MEM_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan is less than DEFAULT_MEM_LOAN
        defaultKamalSocietyShouldNotBeFound("memLoan.lessThan=" + DEFAULT_MEM_LOAN);

        // Get all the kamalSocietyList where memLoan is less than UPDATED_MEM_LOAN
        defaultKamalSocietyShouldBeFound("memLoan.lessThan=" + UPDATED_MEM_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan is greater than DEFAULT_MEM_LOAN
        defaultKamalSocietyShouldNotBeFound("memLoan.greaterThan=" + DEFAULT_MEM_LOAN);

        // Get all the kamalSocietyList where memLoan is greater than SMALLER_MEM_LOAN
        defaultKamalSocietyShouldBeFound("memLoan.greaterThan=" + SMALLER_MEM_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoanMr equals to DEFAULT_MEM_LOAN_MR
        defaultKamalSocietyShouldBeFound("memLoanMr.equals=" + DEFAULT_MEM_LOAN_MR);

        // Get all the kamalSocietyList where memLoanMr equals to UPDATED_MEM_LOAN_MR
        defaultKamalSocietyShouldNotBeFound("memLoanMr.equals=" + UPDATED_MEM_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoanMr in DEFAULT_MEM_LOAN_MR or UPDATED_MEM_LOAN_MR
        defaultKamalSocietyShouldBeFound("memLoanMr.in=" + DEFAULT_MEM_LOAN_MR + "," + UPDATED_MEM_LOAN_MR);

        // Get all the kamalSocietyList where memLoanMr equals to UPDATED_MEM_LOAN_MR
        defaultKamalSocietyShouldNotBeFound("memLoanMr.in=" + UPDATED_MEM_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoanMr is not null
        defaultKamalSocietyShouldBeFound("memLoanMr.specified=true");

        // Get all the kamalSocietyList where memLoanMr is null
        defaultKamalSocietyShouldNotBeFound("memLoanMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoanMr contains DEFAULT_MEM_LOAN_MR
        defaultKamalSocietyShouldBeFound("memLoanMr.contains=" + DEFAULT_MEM_LOAN_MR);

        // Get all the kamalSocietyList where memLoanMr contains UPDATED_MEM_LOAN_MR
        defaultKamalSocietyShouldNotBeFound("memLoanMr.contains=" + UPDATED_MEM_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoanMr does not contain DEFAULT_MEM_LOAN_MR
        defaultKamalSocietyShouldNotBeFound("memLoanMr.doesNotContain=" + DEFAULT_MEM_LOAN_MR);

        // Get all the kamalSocietyList where memLoanMr does not contain UPDATED_MEM_LOAN_MR
        defaultKamalSocietyShouldBeFound("memLoanMr.doesNotContain=" + UPDATED_MEM_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue equals to DEFAULT_MEM_DUE
        defaultKamalSocietyShouldBeFound("memDue.equals=" + DEFAULT_MEM_DUE);

        // Get all the kamalSocietyList where memDue equals to UPDATED_MEM_DUE
        defaultKamalSocietyShouldNotBeFound("memDue.equals=" + UPDATED_MEM_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue in DEFAULT_MEM_DUE or UPDATED_MEM_DUE
        defaultKamalSocietyShouldBeFound("memDue.in=" + DEFAULT_MEM_DUE + "," + UPDATED_MEM_DUE);

        // Get all the kamalSocietyList where memDue equals to UPDATED_MEM_DUE
        defaultKamalSocietyShouldNotBeFound("memDue.in=" + UPDATED_MEM_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue is not null
        defaultKamalSocietyShouldBeFound("memDue.specified=true");

        // Get all the kamalSocietyList where memDue is null
        defaultKamalSocietyShouldNotBeFound("memDue.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue is greater than or equal to DEFAULT_MEM_DUE
        defaultKamalSocietyShouldBeFound("memDue.greaterThanOrEqual=" + DEFAULT_MEM_DUE);

        // Get all the kamalSocietyList where memDue is greater than or equal to UPDATED_MEM_DUE
        defaultKamalSocietyShouldNotBeFound("memDue.greaterThanOrEqual=" + UPDATED_MEM_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue is less than or equal to DEFAULT_MEM_DUE
        defaultKamalSocietyShouldBeFound("memDue.lessThanOrEqual=" + DEFAULT_MEM_DUE);

        // Get all the kamalSocietyList where memDue is less than or equal to SMALLER_MEM_DUE
        defaultKamalSocietyShouldNotBeFound("memDue.lessThanOrEqual=" + SMALLER_MEM_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue is less than DEFAULT_MEM_DUE
        defaultKamalSocietyShouldNotBeFound("memDue.lessThan=" + DEFAULT_MEM_DUE);

        // Get all the kamalSocietyList where memDue is less than UPDATED_MEM_DUE
        defaultKamalSocietyShouldBeFound("memDue.lessThan=" + UPDATED_MEM_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue is greater than DEFAULT_MEM_DUE
        defaultKamalSocietyShouldNotBeFound("memDue.greaterThan=" + DEFAULT_MEM_DUE);

        // Get all the kamalSocietyList where memDue is greater than SMALLER_MEM_DUE
        defaultKamalSocietyShouldBeFound("memDue.greaterThan=" + SMALLER_MEM_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueMr equals to DEFAULT_MEM_DUE_MR
        defaultKamalSocietyShouldBeFound("memDueMr.equals=" + DEFAULT_MEM_DUE_MR);

        // Get all the kamalSocietyList where memDueMr equals to UPDATED_MEM_DUE_MR
        defaultKamalSocietyShouldNotBeFound("memDueMr.equals=" + UPDATED_MEM_DUE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueMr in DEFAULT_MEM_DUE_MR or UPDATED_MEM_DUE_MR
        defaultKamalSocietyShouldBeFound("memDueMr.in=" + DEFAULT_MEM_DUE_MR + "," + UPDATED_MEM_DUE_MR);

        // Get all the kamalSocietyList where memDueMr equals to UPDATED_MEM_DUE_MR
        defaultKamalSocietyShouldNotBeFound("memDueMr.in=" + UPDATED_MEM_DUE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueMr is not null
        defaultKamalSocietyShouldBeFound("memDueMr.specified=true");

        // Get all the kamalSocietyList where memDueMr is null
        defaultKamalSocietyShouldNotBeFound("memDueMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueMr contains DEFAULT_MEM_DUE_MR
        defaultKamalSocietyShouldBeFound("memDueMr.contains=" + DEFAULT_MEM_DUE_MR);

        // Get all the kamalSocietyList where memDueMr contains UPDATED_MEM_DUE_MR
        defaultKamalSocietyShouldNotBeFound("memDueMr.contains=" + UPDATED_MEM_DUE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueMr does not contain DEFAULT_MEM_DUE_MR
        defaultKamalSocietyShouldNotBeFound("memDueMr.doesNotContain=" + DEFAULT_MEM_DUE_MR);

        // Get all the kamalSocietyList where memDueMr does not contain UPDATED_MEM_DUE_MR
        defaultKamalSocietyShouldBeFound("memDueMr.doesNotContain=" + UPDATED_MEM_DUE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueper equals to DEFAULT_MEM_DUEPER
        defaultKamalSocietyShouldBeFound("memDueper.equals=" + DEFAULT_MEM_DUEPER);

        // Get all the kamalSocietyList where memDueper equals to UPDATED_MEM_DUEPER
        defaultKamalSocietyShouldNotBeFound("memDueper.equals=" + UPDATED_MEM_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueper in DEFAULT_MEM_DUEPER or UPDATED_MEM_DUEPER
        defaultKamalSocietyShouldBeFound("memDueper.in=" + DEFAULT_MEM_DUEPER + "," + UPDATED_MEM_DUEPER);

        // Get all the kamalSocietyList where memDueper equals to UPDATED_MEM_DUEPER
        defaultKamalSocietyShouldNotBeFound("memDueper.in=" + UPDATED_MEM_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueper is not null
        defaultKamalSocietyShouldBeFound("memDueper.specified=true");

        // Get all the kamalSocietyList where memDueper is null
        defaultKamalSocietyShouldNotBeFound("memDueper.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueper is greater than or equal to DEFAULT_MEM_DUEPER
        defaultKamalSocietyShouldBeFound("memDueper.greaterThanOrEqual=" + DEFAULT_MEM_DUEPER);

        // Get all the kamalSocietyList where memDueper is greater than or equal to UPDATED_MEM_DUEPER
        defaultKamalSocietyShouldNotBeFound("memDueper.greaterThanOrEqual=" + UPDATED_MEM_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueper is less than or equal to DEFAULT_MEM_DUEPER
        defaultKamalSocietyShouldBeFound("memDueper.lessThanOrEqual=" + DEFAULT_MEM_DUEPER);

        // Get all the kamalSocietyList where memDueper is less than or equal to SMALLER_MEM_DUEPER
        defaultKamalSocietyShouldNotBeFound("memDueper.lessThanOrEqual=" + SMALLER_MEM_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueper is less than DEFAULT_MEM_DUEPER
        defaultKamalSocietyShouldNotBeFound("memDueper.lessThan=" + DEFAULT_MEM_DUEPER);

        // Get all the kamalSocietyList where memDueper is less than UPDATED_MEM_DUEPER
        defaultKamalSocietyShouldBeFound("memDueper.lessThan=" + UPDATED_MEM_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueper is greater than DEFAULT_MEM_DUEPER
        defaultKamalSocietyShouldNotBeFound("memDueper.greaterThan=" + DEFAULT_MEM_DUEPER);

        // Get all the kamalSocietyList where memDueper is greater than SMALLER_MEM_DUEPER
        defaultKamalSocietyShouldBeFound("memDueper.greaterThan=" + SMALLER_MEM_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueperMr equals to DEFAULT_MEM_DUEPER_MR
        defaultKamalSocietyShouldBeFound("memDueperMr.equals=" + DEFAULT_MEM_DUEPER_MR);

        // Get all the kamalSocietyList where memDueperMr equals to UPDATED_MEM_DUEPER_MR
        defaultKamalSocietyShouldNotBeFound("memDueperMr.equals=" + UPDATED_MEM_DUEPER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueperMr in DEFAULT_MEM_DUEPER_MR or UPDATED_MEM_DUEPER_MR
        defaultKamalSocietyShouldBeFound("memDueperMr.in=" + DEFAULT_MEM_DUEPER_MR + "," + UPDATED_MEM_DUEPER_MR);

        // Get all the kamalSocietyList where memDueperMr equals to UPDATED_MEM_DUEPER_MR
        defaultKamalSocietyShouldNotBeFound("memDueperMr.in=" + UPDATED_MEM_DUEPER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueperMr is not null
        defaultKamalSocietyShouldBeFound("memDueperMr.specified=true");

        // Get all the kamalSocietyList where memDueperMr is null
        defaultKamalSocietyShouldNotBeFound("memDueperMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueperMr contains DEFAULT_MEM_DUEPER_MR
        defaultKamalSocietyShouldBeFound("memDueperMr.contains=" + DEFAULT_MEM_DUEPER_MR);

        // Get all the kamalSocietyList where memDueperMr contains UPDATED_MEM_DUEPER_MR
        defaultKamalSocietyShouldNotBeFound("memDueperMr.contains=" + UPDATED_MEM_DUEPER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueperMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDueperMr does not contain DEFAULT_MEM_DUEPER_MR
        defaultKamalSocietyShouldNotBeFound("memDueperMr.doesNotContain=" + DEFAULT_MEM_DUEPER_MR);

        // Get all the kamalSocietyList where memDueperMr does not contain UPDATED_MEM_DUEPER_MR
        defaultKamalSocietyShouldBeFound("memDueperMr.doesNotContain=" + UPDATED_MEM_DUEPER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatra equals to DEFAULT_MEM_VASULPATRA
        defaultKamalSocietyShouldBeFound("memVasulpatra.equals=" + DEFAULT_MEM_VASULPATRA);

        // Get all the kamalSocietyList where memVasulpatra equals to UPDATED_MEM_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("memVasulpatra.equals=" + UPDATED_MEM_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatra in DEFAULT_MEM_VASULPATRA or UPDATED_MEM_VASULPATRA
        defaultKamalSocietyShouldBeFound("memVasulpatra.in=" + DEFAULT_MEM_VASULPATRA + "," + UPDATED_MEM_VASULPATRA);

        // Get all the kamalSocietyList where memVasulpatra equals to UPDATED_MEM_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("memVasulpatra.in=" + UPDATED_MEM_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatra is not null
        defaultKamalSocietyShouldBeFound("memVasulpatra.specified=true");

        // Get all the kamalSocietyList where memVasulpatra is null
        defaultKamalSocietyShouldNotBeFound("memVasulpatra.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatra is greater than or equal to DEFAULT_MEM_VASULPATRA
        defaultKamalSocietyShouldBeFound("memVasulpatra.greaterThanOrEqual=" + DEFAULT_MEM_VASULPATRA);

        // Get all the kamalSocietyList where memVasulpatra is greater than or equal to UPDATED_MEM_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("memVasulpatra.greaterThanOrEqual=" + UPDATED_MEM_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatra is less than or equal to DEFAULT_MEM_VASULPATRA
        defaultKamalSocietyShouldBeFound("memVasulpatra.lessThanOrEqual=" + DEFAULT_MEM_VASULPATRA);

        // Get all the kamalSocietyList where memVasulpatra is less than or equal to SMALLER_MEM_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("memVasulpatra.lessThanOrEqual=" + SMALLER_MEM_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatra is less than DEFAULT_MEM_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("memVasulpatra.lessThan=" + DEFAULT_MEM_VASULPATRA);

        // Get all the kamalSocietyList where memVasulpatra is less than UPDATED_MEM_VASULPATRA
        defaultKamalSocietyShouldBeFound("memVasulpatra.lessThan=" + UPDATED_MEM_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatra is greater than DEFAULT_MEM_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("memVasulpatra.greaterThan=" + DEFAULT_MEM_VASULPATRA);

        // Get all the kamalSocietyList where memVasulpatra is greater than SMALLER_MEM_VASULPATRA
        defaultKamalSocietyShouldBeFound("memVasulpatra.greaterThan=" + SMALLER_MEM_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatraMr equals to DEFAULT_MEM_VASULPATRA_MR
        defaultKamalSocietyShouldBeFound("memVasulpatraMr.equals=" + DEFAULT_MEM_VASULPATRA_MR);

        // Get all the kamalSocietyList where memVasulpatraMr equals to UPDATED_MEM_VASULPATRA_MR
        defaultKamalSocietyShouldNotBeFound("memVasulpatraMr.equals=" + UPDATED_MEM_VASULPATRA_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatraMr in DEFAULT_MEM_VASULPATRA_MR or UPDATED_MEM_VASULPATRA_MR
        defaultKamalSocietyShouldBeFound("memVasulpatraMr.in=" + DEFAULT_MEM_VASULPATRA_MR + "," + UPDATED_MEM_VASULPATRA_MR);

        // Get all the kamalSocietyList where memVasulpatraMr equals to UPDATED_MEM_VASULPATRA_MR
        defaultKamalSocietyShouldNotBeFound("memVasulpatraMr.in=" + UPDATED_MEM_VASULPATRA_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatraMr is not null
        defaultKamalSocietyShouldBeFound("memVasulpatraMr.specified=true");

        // Get all the kamalSocietyList where memVasulpatraMr is null
        defaultKamalSocietyShouldNotBeFound("memVasulpatraMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatraMr contains DEFAULT_MEM_VASULPATRA_MR
        defaultKamalSocietyShouldBeFound("memVasulpatraMr.contains=" + DEFAULT_MEM_VASULPATRA_MR);

        // Get all the kamalSocietyList where memVasulpatraMr contains UPDATED_MEM_VASULPATRA_MR
        defaultKamalSocietyShouldNotBeFound("memVasulpatraMr.contains=" + UPDATED_MEM_VASULPATRA_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulpatraMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulpatraMr does not contain DEFAULT_MEM_VASULPATRA_MR
        defaultKamalSocietyShouldNotBeFound("memVasulpatraMr.doesNotContain=" + DEFAULT_MEM_VASULPATRA_MR);

        // Get all the kamalSocietyList where memVasulpatraMr does not contain UPDATED_MEM_VASULPATRA_MR
        defaultKamalSocietyShouldBeFound("memVasulpatraMr.doesNotContain=" + UPDATED_MEM_VASULPATRA_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasul equals to DEFAULT_MEM_VASUL
        defaultKamalSocietyShouldBeFound("memVasul.equals=" + DEFAULT_MEM_VASUL);

        // Get all the kamalSocietyList where memVasul equals to UPDATED_MEM_VASUL
        defaultKamalSocietyShouldNotBeFound("memVasul.equals=" + UPDATED_MEM_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasul in DEFAULT_MEM_VASUL or UPDATED_MEM_VASUL
        defaultKamalSocietyShouldBeFound("memVasul.in=" + DEFAULT_MEM_VASUL + "," + UPDATED_MEM_VASUL);

        // Get all the kamalSocietyList where memVasul equals to UPDATED_MEM_VASUL
        defaultKamalSocietyShouldNotBeFound("memVasul.in=" + UPDATED_MEM_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasul is not null
        defaultKamalSocietyShouldBeFound("memVasul.specified=true");

        // Get all the kamalSocietyList where memVasul is null
        defaultKamalSocietyShouldNotBeFound("memVasul.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasul is greater than or equal to DEFAULT_MEM_VASUL
        defaultKamalSocietyShouldBeFound("memVasul.greaterThanOrEqual=" + DEFAULT_MEM_VASUL);

        // Get all the kamalSocietyList where memVasul is greater than or equal to UPDATED_MEM_VASUL
        defaultKamalSocietyShouldNotBeFound("memVasul.greaterThanOrEqual=" + UPDATED_MEM_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasul is less than or equal to DEFAULT_MEM_VASUL
        defaultKamalSocietyShouldBeFound("memVasul.lessThanOrEqual=" + DEFAULT_MEM_VASUL);

        // Get all the kamalSocietyList where memVasul is less than or equal to SMALLER_MEM_VASUL
        defaultKamalSocietyShouldNotBeFound("memVasul.lessThanOrEqual=" + SMALLER_MEM_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasul is less than DEFAULT_MEM_VASUL
        defaultKamalSocietyShouldNotBeFound("memVasul.lessThan=" + DEFAULT_MEM_VASUL);

        // Get all the kamalSocietyList where memVasul is less than UPDATED_MEM_VASUL
        defaultKamalSocietyShouldBeFound("memVasul.lessThan=" + UPDATED_MEM_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasul is greater than DEFAULT_MEM_VASUL
        defaultKamalSocietyShouldNotBeFound("memVasul.greaterThan=" + DEFAULT_MEM_VASUL);

        // Get all the kamalSocietyList where memVasul is greater than SMALLER_MEM_VASUL
        defaultKamalSocietyShouldBeFound("memVasul.greaterThan=" + SMALLER_MEM_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulMr equals to DEFAULT_MEM_VASUL_MR
        defaultKamalSocietyShouldBeFound("memVasulMr.equals=" + DEFAULT_MEM_VASUL_MR);

        // Get all the kamalSocietyList where memVasulMr equals to UPDATED_MEM_VASUL_MR
        defaultKamalSocietyShouldNotBeFound("memVasulMr.equals=" + UPDATED_MEM_VASUL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulMr in DEFAULT_MEM_VASUL_MR or UPDATED_MEM_VASUL_MR
        defaultKamalSocietyShouldBeFound("memVasulMr.in=" + DEFAULT_MEM_VASUL_MR + "," + UPDATED_MEM_VASUL_MR);

        // Get all the kamalSocietyList where memVasulMr equals to UPDATED_MEM_VASUL_MR
        defaultKamalSocietyShouldNotBeFound("memVasulMr.in=" + UPDATED_MEM_VASUL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulMr is not null
        defaultKamalSocietyShouldBeFound("memVasulMr.specified=true");

        // Get all the kamalSocietyList where memVasulMr is null
        defaultKamalSocietyShouldNotBeFound("memVasulMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulMr contains DEFAULT_MEM_VASUL_MR
        defaultKamalSocietyShouldBeFound("memVasulMr.contains=" + DEFAULT_MEM_VASUL_MR);

        // Get all the kamalSocietyList where memVasulMr contains UPDATED_MEM_VASUL_MR
        defaultKamalSocietyShouldNotBeFound("memVasulMr.contains=" + UPDATED_MEM_VASUL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulMr does not contain DEFAULT_MEM_VASUL_MR
        defaultKamalSocietyShouldNotBeFound("memVasulMr.doesNotContain=" + DEFAULT_MEM_VASUL_MR);

        // Get all the kamalSocietyList where memVasulMr does not contain UPDATED_MEM_VASUL_MR
        defaultKamalSocietyShouldBeFound("memVasulMr.doesNotContain=" + UPDATED_MEM_VASUL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPer equals to DEFAULT_MEM_VASUL_PER
        defaultKamalSocietyShouldBeFound("memVasulPer.equals=" + DEFAULT_MEM_VASUL_PER);

        // Get all the kamalSocietyList where memVasulPer equals to UPDATED_MEM_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("memVasulPer.equals=" + UPDATED_MEM_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPer in DEFAULT_MEM_VASUL_PER or UPDATED_MEM_VASUL_PER
        defaultKamalSocietyShouldBeFound("memVasulPer.in=" + DEFAULT_MEM_VASUL_PER + "," + UPDATED_MEM_VASUL_PER);

        // Get all the kamalSocietyList where memVasulPer equals to UPDATED_MEM_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("memVasulPer.in=" + UPDATED_MEM_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPer is not null
        defaultKamalSocietyShouldBeFound("memVasulPer.specified=true");

        // Get all the kamalSocietyList where memVasulPer is null
        defaultKamalSocietyShouldNotBeFound("memVasulPer.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPer is greater than or equal to DEFAULT_MEM_VASUL_PER
        defaultKamalSocietyShouldBeFound("memVasulPer.greaterThanOrEqual=" + DEFAULT_MEM_VASUL_PER);

        // Get all the kamalSocietyList where memVasulPer is greater than or equal to UPDATED_MEM_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("memVasulPer.greaterThanOrEqual=" + UPDATED_MEM_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPer is less than or equal to DEFAULT_MEM_VASUL_PER
        defaultKamalSocietyShouldBeFound("memVasulPer.lessThanOrEqual=" + DEFAULT_MEM_VASUL_PER);

        // Get all the kamalSocietyList where memVasulPer is less than or equal to SMALLER_MEM_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("memVasulPer.lessThanOrEqual=" + SMALLER_MEM_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPer is less than DEFAULT_MEM_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("memVasulPer.lessThan=" + DEFAULT_MEM_VASUL_PER);

        // Get all the kamalSocietyList where memVasulPer is less than UPDATED_MEM_VASUL_PER
        defaultKamalSocietyShouldBeFound("memVasulPer.lessThan=" + UPDATED_MEM_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPer is greater than DEFAULT_MEM_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("memVasulPer.greaterThan=" + DEFAULT_MEM_VASUL_PER);

        // Get all the kamalSocietyList where memVasulPer is greater than SMALLER_MEM_VASUL_PER
        defaultKamalSocietyShouldBeFound("memVasulPer.greaterThan=" + SMALLER_MEM_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPerMr equals to DEFAULT_MEM_VASUL_PER_MR
        defaultKamalSocietyShouldBeFound("memVasulPerMr.equals=" + DEFAULT_MEM_VASUL_PER_MR);

        // Get all the kamalSocietyList where memVasulPerMr equals to UPDATED_MEM_VASUL_PER_MR
        defaultKamalSocietyShouldNotBeFound("memVasulPerMr.equals=" + UPDATED_MEM_VASUL_PER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPerMr in DEFAULT_MEM_VASUL_PER_MR or UPDATED_MEM_VASUL_PER_MR
        defaultKamalSocietyShouldBeFound("memVasulPerMr.in=" + DEFAULT_MEM_VASUL_PER_MR + "," + UPDATED_MEM_VASUL_PER_MR);

        // Get all the kamalSocietyList where memVasulPerMr equals to UPDATED_MEM_VASUL_PER_MR
        defaultKamalSocietyShouldNotBeFound("memVasulPerMr.in=" + UPDATED_MEM_VASUL_PER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPerMr is not null
        defaultKamalSocietyShouldBeFound("memVasulPerMr.specified=true");

        // Get all the kamalSocietyList where memVasulPerMr is null
        defaultKamalSocietyShouldNotBeFound("memVasulPerMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPerMr contains DEFAULT_MEM_VASUL_PER_MR
        defaultKamalSocietyShouldBeFound("memVasulPerMr.contains=" + DEFAULT_MEM_VASUL_PER_MR);

        // Get all the kamalSocietyList where memVasulPerMr contains UPDATED_MEM_VASUL_PER_MR
        defaultKamalSocietyShouldNotBeFound("memVasulPerMr.contains=" + UPDATED_MEM_VASUL_PER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasulPerMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasulPerMr does not contain DEFAULT_MEM_VASUL_PER_MR
        defaultKamalSocietyShouldNotBeFound("memVasulPerMr.doesNotContain=" + DEFAULT_MEM_VASUL_PER_MR);

        // Get all the kamalSocietyList where memVasulPerMr does not contain UPDATED_MEM_VASUL_PER_MR
        defaultKamalSocietyShouldBeFound("memVasulPerMr.doesNotContain=" + UPDATED_MEM_VASUL_PER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan equals to DEFAULT_BANK_LOAN
        defaultKamalSocietyShouldBeFound("bankLoan.equals=" + DEFAULT_BANK_LOAN);

        // Get all the kamalSocietyList where bankLoan equals to UPDATED_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("bankLoan.equals=" + UPDATED_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan in DEFAULT_BANK_LOAN or UPDATED_BANK_LOAN
        defaultKamalSocietyShouldBeFound("bankLoan.in=" + DEFAULT_BANK_LOAN + "," + UPDATED_BANK_LOAN);

        // Get all the kamalSocietyList where bankLoan equals to UPDATED_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("bankLoan.in=" + UPDATED_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan is not null
        defaultKamalSocietyShouldBeFound("bankLoan.specified=true");

        // Get all the kamalSocietyList where bankLoan is null
        defaultKamalSocietyShouldNotBeFound("bankLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan is greater than or equal to DEFAULT_BANK_LOAN
        defaultKamalSocietyShouldBeFound("bankLoan.greaterThanOrEqual=" + DEFAULT_BANK_LOAN);

        // Get all the kamalSocietyList where bankLoan is greater than or equal to UPDATED_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("bankLoan.greaterThanOrEqual=" + UPDATED_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan is less than or equal to DEFAULT_BANK_LOAN
        defaultKamalSocietyShouldBeFound("bankLoan.lessThanOrEqual=" + DEFAULT_BANK_LOAN);

        // Get all the kamalSocietyList where bankLoan is less than or equal to SMALLER_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("bankLoan.lessThanOrEqual=" + SMALLER_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan is less than DEFAULT_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("bankLoan.lessThan=" + DEFAULT_BANK_LOAN);

        // Get all the kamalSocietyList where bankLoan is less than UPDATED_BANK_LOAN
        defaultKamalSocietyShouldBeFound("bankLoan.lessThan=" + UPDATED_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan is greater than DEFAULT_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("bankLoan.greaterThan=" + DEFAULT_BANK_LOAN);

        // Get all the kamalSocietyList where bankLoan is greater than SMALLER_BANK_LOAN
        defaultKamalSocietyShouldBeFound("bankLoan.greaterThan=" + SMALLER_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoanMr equals to DEFAULT_BANK_LOAN_MR
        defaultKamalSocietyShouldBeFound("bankLoanMr.equals=" + DEFAULT_BANK_LOAN_MR);

        // Get all the kamalSocietyList where bankLoanMr equals to UPDATED_BANK_LOAN_MR
        defaultKamalSocietyShouldNotBeFound("bankLoanMr.equals=" + UPDATED_BANK_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoanMr in DEFAULT_BANK_LOAN_MR or UPDATED_BANK_LOAN_MR
        defaultKamalSocietyShouldBeFound("bankLoanMr.in=" + DEFAULT_BANK_LOAN_MR + "," + UPDATED_BANK_LOAN_MR);

        // Get all the kamalSocietyList where bankLoanMr equals to UPDATED_BANK_LOAN_MR
        defaultKamalSocietyShouldNotBeFound("bankLoanMr.in=" + UPDATED_BANK_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoanMr is not null
        defaultKamalSocietyShouldBeFound("bankLoanMr.specified=true");

        // Get all the kamalSocietyList where bankLoanMr is null
        defaultKamalSocietyShouldNotBeFound("bankLoanMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoanMr contains DEFAULT_BANK_LOAN_MR
        defaultKamalSocietyShouldBeFound("bankLoanMr.contains=" + DEFAULT_BANK_LOAN_MR);

        // Get all the kamalSocietyList where bankLoanMr contains UPDATED_BANK_LOAN_MR
        defaultKamalSocietyShouldNotBeFound("bankLoanMr.contains=" + UPDATED_BANK_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoanMr does not contain DEFAULT_BANK_LOAN_MR
        defaultKamalSocietyShouldNotBeFound("bankLoanMr.doesNotContain=" + DEFAULT_BANK_LOAN_MR);

        // Get all the kamalSocietyList where bankLoanMr does not contain UPDATED_BANK_LOAN_MR
        defaultKamalSocietyShouldBeFound("bankLoanMr.doesNotContain=" + UPDATED_BANK_LOAN_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue equals to DEFAULT_BANK_DUE
        defaultKamalSocietyShouldBeFound("bankDue.equals=" + DEFAULT_BANK_DUE);

        // Get all the kamalSocietyList where bankDue equals to UPDATED_BANK_DUE
        defaultKamalSocietyShouldNotBeFound("bankDue.equals=" + UPDATED_BANK_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue in DEFAULT_BANK_DUE or UPDATED_BANK_DUE
        defaultKamalSocietyShouldBeFound("bankDue.in=" + DEFAULT_BANK_DUE + "," + UPDATED_BANK_DUE);

        // Get all the kamalSocietyList where bankDue equals to UPDATED_BANK_DUE
        defaultKamalSocietyShouldNotBeFound("bankDue.in=" + UPDATED_BANK_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue is not null
        defaultKamalSocietyShouldBeFound("bankDue.specified=true");

        // Get all the kamalSocietyList where bankDue is null
        defaultKamalSocietyShouldNotBeFound("bankDue.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue is greater than or equal to DEFAULT_BANK_DUE
        defaultKamalSocietyShouldBeFound("bankDue.greaterThanOrEqual=" + DEFAULT_BANK_DUE);

        // Get all the kamalSocietyList where bankDue is greater than or equal to UPDATED_BANK_DUE
        defaultKamalSocietyShouldNotBeFound("bankDue.greaterThanOrEqual=" + UPDATED_BANK_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue is less than or equal to DEFAULT_BANK_DUE
        defaultKamalSocietyShouldBeFound("bankDue.lessThanOrEqual=" + DEFAULT_BANK_DUE);

        // Get all the kamalSocietyList where bankDue is less than or equal to SMALLER_BANK_DUE
        defaultKamalSocietyShouldNotBeFound("bankDue.lessThanOrEqual=" + SMALLER_BANK_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue is less than DEFAULT_BANK_DUE
        defaultKamalSocietyShouldNotBeFound("bankDue.lessThan=" + DEFAULT_BANK_DUE);

        // Get all the kamalSocietyList where bankDue is less than UPDATED_BANK_DUE
        defaultKamalSocietyShouldBeFound("bankDue.lessThan=" + UPDATED_BANK_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue is greater than DEFAULT_BANK_DUE
        defaultKamalSocietyShouldNotBeFound("bankDue.greaterThan=" + DEFAULT_BANK_DUE);

        // Get all the kamalSocietyList where bankDue is greater than SMALLER_BANK_DUE
        defaultKamalSocietyShouldBeFound("bankDue.greaterThan=" + SMALLER_BANK_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueMr equals to DEFAULT_BANK_DUE_MR
        defaultKamalSocietyShouldBeFound("bankDueMr.equals=" + DEFAULT_BANK_DUE_MR);

        // Get all the kamalSocietyList where bankDueMr equals to UPDATED_BANK_DUE_MR
        defaultKamalSocietyShouldNotBeFound("bankDueMr.equals=" + UPDATED_BANK_DUE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueMr in DEFAULT_BANK_DUE_MR or UPDATED_BANK_DUE_MR
        defaultKamalSocietyShouldBeFound("bankDueMr.in=" + DEFAULT_BANK_DUE_MR + "," + UPDATED_BANK_DUE_MR);

        // Get all the kamalSocietyList where bankDueMr equals to UPDATED_BANK_DUE_MR
        defaultKamalSocietyShouldNotBeFound("bankDueMr.in=" + UPDATED_BANK_DUE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueMr is not null
        defaultKamalSocietyShouldBeFound("bankDueMr.specified=true");

        // Get all the kamalSocietyList where bankDueMr is null
        defaultKamalSocietyShouldNotBeFound("bankDueMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueMr contains DEFAULT_BANK_DUE_MR
        defaultKamalSocietyShouldBeFound("bankDueMr.contains=" + DEFAULT_BANK_DUE_MR);

        // Get all the kamalSocietyList where bankDueMr contains UPDATED_BANK_DUE_MR
        defaultKamalSocietyShouldNotBeFound("bankDueMr.contains=" + UPDATED_BANK_DUE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueMr does not contain DEFAULT_BANK_DUE_MR
        defaultKamalSocietyShouldNotBeFound("bankDueMr.doesNotContain=" + DEFAULT_BANK_DUE_MR);

        // Get all the kamalSocietyList where bankDueMr does not contain UPDATED_BANK_DUE_MR
        defaultKamalSocietyShouldBeFound("bankDueMr.doesNotContain=" + UPDATED_BANK_DUE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueper equals to DEFAULT_BANK_DUEPER
        defaultKamalSocietyShouldBeFound("bankDueper.equals=" + DEFAULT_BANK_DUEPER);

        // Get all the kamalSocietyList where bankDueper equals to UPDATED_BANK_DUEPER
        defaultKamalSocietyShouldNotBeFound("bankDueper.equals=" + UPDATED_BANK_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueper in DEFAULT_BANK_DUEPER or UPDATED_BANK_DUEPER
        defaultKamalSocietyShouldBeFound("bankDueper.in=" + DEFAULT_BANK_DUEPER + "," + UPDATED_BANK_DUEPER);

        // Get all the kamalSocietyList where bankDueper equals to UPDATED_BANK_DUEPER
        defaultKamalSocietyShouldNotBeFound("bankDueper.in=" + UPDATED_BANK_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueper is not null
        defaultKamalSocietyShouldBeFound("bankDueper.specified=true");

        // Get all the kamalSocietyList where bankDueper is null
        defaultKamalSocietyShouldNotBeFound("bankDueper.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueper is greater than or equal to DEFAULT_BANK_DUEPER
        defaultKamalSocietyShouldBeFound("bankDueper.greaterThanOrEqual=" + DEFAULT_BANK_DUEPER);

        // Get all the kamalSocietyList where bankDueper is greater than or equal to UPDATED_BANK_DUEPER
        defaultKamalSocietyShouldNotBeFound("bankDueper.greaterThanOrEqual=" + UPDATED_BANK_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueper is less than or equal to DEFAULT_BANK_DUEPER
        defaultKamalSocietyShouldBeFound("bankDueper.lessThanOrEqual=" + DEFAULT_BANK_DUEPER);

        // Get all the kamalSocietyList where bankDueper is less than or equal to SMALLER_BANK_DUEPER
        defaultKamalSocietyShouldNotBeFound("bankDueper.lessThanOrEqual=" + SMALLER_BANK_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueper is less than DEFAULT_BANK_DUEPER
        defaultKamalSocietyShouldNotBeFound("bankDueper.lessThan=" + DEFAULT_BANK_DUEPER);

        // Get all the kamalSocietyList where bankDueper is less than UPDATED_BANK_DUEPER
        defaultKamalSocietyShouldBeFound("bankDueper.lessThan=" + UPDATED_BANK_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueper is greater than DEFAULT_BANK_DUEPER
        defaultKamalSocietyShouldNotBeFound("bankDueper.greaterThan=" + DEFAULT_BANK_DUEPER);

        // Get all the kamalSocietyList where bankDueper is greater than SMALLER_BANK_DUEPER
        defaultKamalSocietyShouldBeFound("bankDueper.greaterThan=" + SMALLER_BANK_DUEPER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueperMr equals to DEFAULT_BANK_DUEPER_MR
        defaultKamalSocietyShouldBeFound("bankDueperMr.equals=" + DEFAULT_BANK_DUEPER_MR);

        // Get all the kamalSocietyList where bankDueperMr equals to UPDATED_BANK_DUEPER_MR
        defaultKamalSocietyShouldNotBeFound("bankDueperMr.equals=" + UPDATED_BANK_DUEPER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueperMr in DEFAULT_BANK_DUEPER_MR or UPDATED_BANK_DUEPER_MR
        defaultKamalSocietyShouldBeFound("bankDueperMr.in=" + DEFAULT_BANK_DUEPER_MR + "," + UPDATED_BANK_DUEPER_MR);

        // Get all the kamalSocietyList where bankDueperMr equals to UPDATED_BANK_DUEPER_MR
        defaultKamalSocietyShouldNotBeFound("bankDueperMr.in=" + UPDATED_BANK_DUEPER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueperMr is not null
        defaultKamalSocietyShouldBeFound("bankDueperMr.specified=true");

        // Get all the kamalSocietyList where bankDueperMr is null
        defaultKamalSocietyShouldNotBeFound("bankDueperMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueperMr contains DEFAULT_BANK_DUEPER_MR
        defaultKamalSocietyShouldBeFound("bankDueperMr.contains=" + DEFAULT_BANK_DUEPER_MR);

        // Get all the kamalSocietyList where bankDueperMr contains UPDATED_BANK_DUEPER_MR
        defaultKamalSocietyShouldNotBeFound("bankDueperMr.contains=" + UPDATED_BANK_DUEPER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueperMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDueperMr does not contain DEFAULT_BANK_DUEPER_MR
        defaultKamalSocietyShouldNotBeFound("bankDueperMr.doesNotContain=" + DEFAULT_BANK_DUEPER_MR);

        // Get all the kamalSocietyList where bankDueperMr does not contain UPDATED_BANK_DUEPER_MR
        defaultKamalSocietyShouldBeFound("bankDueperMr.doesNotContain=" + UPDATED_BANK_DUEPER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatra equals to DEFAULT_BANK_VASULPATRA
        defaultKamalSocietyShouldBeFound("bankVasulpatra.equals=" + DEFAULT_BANK_VASULPATRA);

        // Get all the kamalSocietyList where bankVasulpatra equals to UPDATED_BANK_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("bankVasulpatra.equals=" + UPDATED_BANK_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatra in DEFAULT_BANK_VASULPATRA or UPDATED_BANK_VASULPATRA
        defaultKamalSocietyShouldBeFound("bankVasulpatra.in=" + DEFAULT_BANK_VASULPATRA + "," + UPDATED_BANK_VASULPATRA);

        // Get all the kamalSocietyList where bankVasulpatra equals to UPDATED_BANK_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("bankVasulpatra.in=" + UPDATED_BANK_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatra is not null
        defaultKamalSocietyShouldBeFound("bankVasulpatra.specified=true");

        // Get all the kamalSocietyList where bankVasulpatra is null
        defaultKamalSocietyShouldNotBeFound("bankVasulpatra.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatra is greater than or equal to DEFAULT_BANK_VASULPATRA
        defaultKamalSocietyShouldBeFound("bankVasulpatra.greaterThanOrEqual=" + DEFAULT_BANK_VASULPATRA);

        // Get all the kamalSocietyList where bankVasulpatra is greater than or equal to UPDATED_BANK_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("bankVasulpatra.greaterThanOrEqual=" + UPDATED_BANK_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatra is less than or equal to DEFAULT_BANK_VASULPATRA
        defaultKamalSocietyShouldBeFound("bankVasulpatra.lessThanOrEqual=" + DEFAULT_BANK_VASULPATRA);

        // Get all the kamalSocietyList where bankVasulpatra is less than or equal to SMALLER_BANK_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("bankVasulpatra.lessThanOrEqual=" + SMALLER_BANK_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatra is less than DEFAULT_BANK_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("bankVasulpatra.lessThan=" + DEFAULT_BANK_VASULPATRA);

        // Get all the kamalSocietyList where bankVasulpatra is less than UPDATED_BANK_VASULPATRA
        defaultKamalSocietyShouldBeFound("bankVasulpatra.lessThan=" + UPDATED_BANK_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatra is greater than DEFAULT_BANK_VASULPATRA
        defaultKamalSocietyShouldNotBeFound("bankVasulpatra.greaterThan=" + DEFAULT_BANK_VASULPATRA);

        // Get all the kamalSocietyList where bankVasulpatra is greater than SMALLER_BANK_VASULPATRA
        defaultKamalSocietyShouldBeFound("bankVasulpatra.greaterThan=" + SMALLER_BANK_VASULPATRA);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatraMr equals to DEFAULT_BANK_VASULPATRA_MR
        defaultKamalSocietyShouldBeFound("bankVasulpatraMr.equals=" + DEFAULT_BANK_VASULPATRA_MR);

        // Get all the kamalSocietyList where bankVasulpatraMr equals to UPDATED_BANK_VASULPATRA_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulpatraMr.equals=" + UPDATED_BANK_VASULPATRA_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatraMr in DEFAULT_BANK_VASULPATRA_MR or UPDATED_BANK_VASULPATRA_MR
        defaultKamalSocietyShouldBeFound("bankVasulpatraMr.in=" + DEFAULT_BANK_VASULPATRA_MR + "," + UPDATED_BANK_VASULPATRA_MR);

        // Get all the kamalSocietyList where bankVasulpatraMr equals to UPDATED_BANK_VASULPATRA_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulpatraMr.in=" + UPDATED_BANK_VASULPATRA_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatraMr is not null
        defaultKamalSocietyShouldBeFound("bankVasulpatraMr.specified=true");

        // Get all the kamalSocietyList where bankVasulpatraMr is null
        defaultKamalSocietyShouldNotBeFound("bankVasulpatraMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatraMr contains DEFAULT_BANK_VASULPATRA_MR
        defaultKamalSocietyShouldBeFound("bankVasulpatraMr.contains=" + DEFAULT_BANK_VASULPATRA_MR);

        // Get all the kamalSocietyList where bankVasulpatraMr contains UPDATED_BANK_VASULPATRA_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulpatraMr.contains=" + UPDATED_BANK_VASULPATRA_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulpatraMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulpatraMr does not contain DEFAULT_BANK_VASULPATRA_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulpatraMr.doesNotContain=" + DEFAULT_BANK_VASULPATRA_MR);

        // Get all the kamalSocietyList where bankVasulpatraMr does not contain UPDATED_BANK_VASULPATRA_MR
        defaultKamalSocietyShouldBeFound("bankVasulpatraMr.doesNotContain=" + UPDATED_BANK_VASULPATRA_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasul equals to DEFAULT_BANK_VASUL
        defaultKamalSocietyShouldBeFound("bankVasul.equals=" + DEFAULT_BANK_VASUL);

        // Get all the kamalSocietyList where bankVasul equals to UPDATED_BANK_VASUL
        defaultKamalSocietyShouldNotBeFound("bankVasul.equals=" + UPDATED_BANK_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasul in DEFAULT_BANK_VASUL or UPDATED_BANK_VASUL
        defaultKamalSocietyShouldBeFound("bankVasul.in=" + DEFAULT_BANK_VASUL + "," + UPDATED_BANK_VASUL);

        // Get all the kamalSocietyList where bankVasul equals to UPDATED_BANK_VASUL
        defaultKamalSocietyShouldNotBeFound("bankVasul.in=" + UPDATED_BANK_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasul is not null
        defaultKamalSocietyShouldBeFound("bankVasul.specified=true");

        // Get all the kamalSocietyList where bankVasul is null
        defaultKamalSocietyShouldNotBeFound("bankVasul.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasul is greater than or equal to DEFAULT_BANK_VASUL
        defaultKamalSocietyShouldBeFound("bankVasul.greaterThanOrEqual=" + DEFAULT_BANK_VASUL);

        // Get all the kamalSocietyList where bankVasul is greater than or equal to UPDATED_BANK_VASUL
        defaultKamalSocietyShouldNotBeFound("bankVasul.greaterThanOrEqual=" + UPDATED_BANK_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasul is less than or equal to DEFAULT_BANK_VASUL
        defaultKamalSocietyShouldBeFound("bankVasul.lessThanOrEqual=" + DEFAULT_BANK_VASUL);

        // Get all the kamalSocietyList where bankVasul is less than or equal to SMALLER_BANK_VASUL
        defaultKamalSocietyShouldNotBeFound("bankVasul.lessThanOrEqual=" + SMALLER_BANK_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasul is less than DEFAULT_BANK_VASUL
        defaultKamalSocietyShouldNotBeFound("bankVasul.lessThan=" + DEFAULT_BANK_VASUL);

        // Get all the kamalSocietyList where bankVasul is less than UPDATED_BANK_VASUL
        defaultKamalSocietyShouldBeFound("bankVasul.lessThan=" + UPDATED_BANK_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasul is greater than DEFAULT_BANK_VASUL
        defaultKamalSocietyShouldNotBeFound("bankVasul.greaterThan=" + DEFAULT_BANK_VASUL);

        // Get all the kamalSocietyList where bankVasul is greater than SMALLER_BANK_VASUL
        defaultKamalSocietyShouldBeFound("bankVasul.greaterThan=" + SMALLER_BANK_VASUL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulMr equals to DEFAULT_BANK_VASUL_MR
        defaultKamalSocietyShouldBeFound("bankVasulMr.equals=" + DEFAULT_BANK_VASUL_MR);

        // Get all the kamalSocietyList where bankVasulMr equals to UPDATED_BANK_VASUL_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulMr.equals=" + UPDATED_BANK_VASUL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulMr in DEFAULT_BANK_VASUL_MR or UPDATED_BANK_VASUL_MR
        defaultKamalSocietyShouldBeFound("bankVasulMr.in=" + DEFAULT_BANK_VASUL_MR + "," + UPDATED_BANK_VASUL_MR);

        // Get all the kamalSocietyList where bankVasulMr equals to UPDATED_BANK_VASUL_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulMr.in=" + UPDATED_BANK_VASUL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulMr is not null
        defaultKamalSocietyShouldBeFound("bankVasulMr.specified=true");

        // Get all the kamalSocietyList where bankVasulMr is null
        defaultKamalSocietyShouldNotBeFound("bankVasulMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulMr contains DEFAULT_BANK_VASUL_MR
        defaultKamalSocietyShouldBeFound("bankVasulMr.contains=" + DEFAULT_BANK_VASUL_MR);

        // Get all the kamalSocietyList where bankVasulMr contains UPDATED_BANK_VASUL_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulMr.contains=" + UPDATED_BANK_VASUL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulMr does not contain DEFAULT_BANK_VASUL_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulMr.doesNotContain=" + DEFAULT_BANK_VASUL_MR);

        // Get all the kamalSocietyList where bankVasulMr does not contain UPDATED_BANK_VASUL_MR
        defaultKamalSocietyShouldBeFound("bankVasulMr.doesNotContain=" + UPDATED_BANK_VASUL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPer equals to DEFAULT_BANK_VASUL_PER
        defaultKamalSocietyShouldBeFound("bankVasulPer.equals=" + DEFAULT_BANK_VASUL_PER);

        // Get all the kamalSocietyList where bankVasulPer equals to UPDATED_BANK_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("bankVasulPer.equals=" + UPDATED_BANK_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPer in DEFAULT_BANK_VASUL_PER or UPDATED_BANK_VASUL_PER
        defaultKamalSocietyShouldBeFound("bankVasulPer.in=" + DEFAULT_BANK_VASUL_PER + "," + UPDATED_BANK_VASUL_PER);

        // Get all the kamalSocietyList where bankVasulPer equals to UPDATED_BANK_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("bankVasulPer.in=" + UPDATED_BANK_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPer is not null
        defaultKamalSocietyShouldBeFound("bankVasulPer.specified=true");

        // Get all the kamalSocietyList where bankVasulPer is null
        defaultKamalSocietyShouldNotBeFound("bankVasulPer.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPer is greater than or equal to DEFAULT_BANK_VASUL_PER
        defaultKamalSocietyShouldBeFound("bankVasulPer.greaterThanOrEqual=" + DEFAULT_BANK_VASUL_PER);

        // Get all the kamalSocietyList where bankVasulPer is greater than or equal to UPDATED_BANK_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("bankVasulPer.greaterThanOrEqual=" + UPDATED_BANK_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPer is less than or equal to DEFAULT_BANK_VASUL_PER
        defaultKamalSocietyShouldBeFound("bankVasulPer.lessThanOrEqual=" + DEFAULT_BANK_VASUL_PER);

        // Get all the kamalSocietyList where bankVasulPer is less than or equal to SMALLER_BANK_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("bankVasulPer.lessThanOrEqual=" + SMALLER_BANK_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPer is less than DEFAULT_BANK_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("bankVasulPer.lessThan=" + DEFAULT_BANK_VASUL_PER);

        // Get all the kamalSocietyList where bankVasulPer is less than UPDATED_BANK_VASUL_PER
        defaultKamalSocietyShouldBeFound("bankVasulPer.lessThan=" + UPDATED_BANK_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPer is greater than DEFAULT_BANK_VASUL_PER
        defaultKamalSocietyShouldNotBeFound("bankVasulPer.greaterThan=" + DEFAULT_BANK_VASUL_PER);

        // Get all the kamalSocietyList where bankVasulPer is greater than SMALLER_BANK_VASUL_PER
        defaultKamalSocietyShouldBeFound("bankVasulPer.greaterThan=" + SMALLER_BANK_VASUL_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPerMr equals to DEFAULT_BANK_VASUL_PER_MR
        defaultKamalSocietyShouldBeFound("bankVasulPerMr.equals=" + DEFAULT_BANK_VASUL_PER_MR);

        // Get all the kamalSocietyList where bankVasulPerMr equals to UPDATED_BANK_VASUL_PER_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulPerMr.equals=" + UPDATED_BANK_VASUL_PER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPerMr in DEFAULT_BANK_VASUL_PER_MR or UPDATED_BANK_VASUL_PER_MR
        defaultKamalSocietyShouldBeFound("bankVasulPerMr.in=" + DEFAULT_BANK_VASUL_PER_MR + "," + UPDATED_BANK_VASUL_PER_MR);

        // Get all the kamalSocietyList where bankVasulPerMr equals to UPDATED_BANK_VASUL_PER_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulPerMr.in=" + UPDATED_BANK_VASUL_PER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPerMr is not null
        defaultKamalSocietyShouldBeFound("bankVasulPerMr.specified=true");

        // Get all the kamalSocietyList where bankVasulPerMr is null
        defaultKamalSocietyShouldNotBeFound("bankVasulPerMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPerMr contains DEFAULT_BANK_VASUL_PER_MR
        defaultKamalSocietyShouldBeFound("bankVasulPerMr.contains=" + DEFAULT_BANK_VASUL_PER_MR);

        // Get all the kamalSocietyList where bankVasulPerMr contains UPDATED_BANK_VASUL_PER_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulPerMr.contains=" + UPDATED_BANK_VASUL_PER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasulPerMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasulPerMr does not contain DEFAULT_BANK_VASUL_PER_MR
        defaultKamalSocietyShouldNotBeFound("bankVasulPerMr.doesNotContain=" + DEFAULT_BANK_VASUL_PER_MR);

        // Get all the kamalSocietyList where bankVasulPerMr does not contain UPDATED_BANK_VASUL_PER_MR
        defaultKamalSocietyShouldBeFound("bankVasulPerMr.doesNotContain=" + UPDATED_BANK_VASUL_PER_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapital equals to DEFAULT_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("shareCapital.equals=" + DEFAULT_SHARE_CAPITAL);

        // Get all the kamalSocietyList where shareCapital equals to UPDATED_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("shareCapital.equals=" + UPDATED_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapital in DEFAULT_SHARE_CAPITAL or UPDATED_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("shareCapital.in=" + DEFAULT_SHARE_CAPITAL + "," + UPDATED_SHARE_CAPITAL);

        // Get all the kamalSocietyList where shareCapital equals to UPDATED_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("shareCapital.in=" + UPDATED_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapital is not null
        defaultKamalSocietyShouldBeFound("shareCapital.specified=true");

        // Get all the kamalSocietyList where shareCapital is null
        defaultKamalSocietyShouldNotBeFound("shareCapital.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapital is greater than or equal to DEFAULT_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("shareCapital.greaterThanOrEqual=" + DEFAULT_SHARE_CAPITAL);

        // Get all the kamalSocietyList where shareCapital is greater than or equal to UPDATED_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("shareCapital.greaterThanOrEqual=" + UPDATED_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapital is less than or equal to DEFAULT_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("shareCapital.lessThanOrEqual=" + DEFAULT_SHARE_CAPITAL);

        // Get all the kamalSocietyList where shareCapital is less than or equal to SMALLER_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("shareCapital.lessThanOrEqual=" + SMALLER_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapital is less than DEFAULT_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("shareCapital.lessThan=" + DEFAULT_SHARE_CAPITAL);

        // Get all the kamalSocietyList where shareCapital is less than UPDATED_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("shareCapital.lessThan=" + UPDATED_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapital is greater than DEFAULT_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("shareCapital.greaterThan=" + DEFAULT_SHARE_CAPITAL);

        // Get all the kamalSocietyList where shareCapital is greater than SMALLER_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("shareCapital.greaterThan=" + SMALLER_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapitalMr equals to DEFAULT_SHARE_CAPITAL_MR
        defaultKamalSocietyShouldBeFound("shareCapitalMr.equals=" + DEFAULT_SHARE_CAPITAL_MR);

        // Get all the kamalSocietyList where shareCapitalMr equals to UPDATED_SHARE_CAPITAL_MR
        defaultKamalSocietyShouldNotBeFound("shareCapitalMr.equals=" + UPDATED_SHARE_CAPITAL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapitalMr in DEFAULT_SHARE_CAPITAL_MR or UPDATED_SHARE_CAPITAL_MR
        defaultKamalSocietyShouldBeFound("shareCapitalMr.in=" + DEFAULT_SHARE_CAPITAL_MR + "," + UPDATED_SHARE_CAPITAL_MR);

        // Get all the kamalSocietyList where shareCapitalMr equals to UPDATED_SHARE_CAPITAL_MR
        defaultKamalSocietyShouldNotBeFound("shareCapitalMr.in=" + UPDATED_SHARE_CAPITAL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapitalMr is not null
        defaultKamalSocietyShouldBeFound("shareCapitalMr.specified=true");

        // Get all the kamalSocietyList where shareCapitalMr is null
        defaultKamalSocietyShouldNotBeFound("shareCapitalMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapitalMr contains DEFAULT_SHARE_CAPITAL_MR
        defaultKamalSocietyShouldBeFound("shareCapitalMr.contains=" + DEFAULT_SHARE_CAPITAL_MR);

        // Get all the kamalSocietyList where shareCapitalMr contains UPDATED_SHARE_CAPITAL_MR
        defaultKamalSocietyShouldNotBeFound("shareCapitalMr.contains=" + UPDATED_SHARE_CAPITAL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareCapitalMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareCapitalMr does not contain DEFAULT_SHARE_CAPITAL_MR
        defaultKamalSocietyShouldNotBeFound("shareCapitalMr.doesNotContain=" + DEFAULT_SHARE_CAPITAL_MR);

        // Get all the kamalSocietyList where shareCapitalMr does not contain UPDATED_SHARE_CAPITAL_MR
        defaultKamalSocietyShouldBeFound("shareCapitalMr.doesNotContain=" + UPDATED_SHARE_CAPITAL_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where share equals to DEFAULT_SHARE
        defaultKamalSocietyShouldBeFound("share.equals=" + DEFAULT_SHARE);

        // Get all the kamalSocietyList where share equals to UPDATED_SHARE
        defaultKamalSocietyShouldNotBeFound("share.equals=" + UPDATED_SHARE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where share in DEFAULT_SHARE or UPDATED_SHARE
        defaultKamalSocietyShouldBeFound("share.in=" + DEFAULT_SHARE + "," + UPDATED_SHARE);

        // Get all the kamalSocietyList where share equals to UPDATED_SHARE
        defaultKamalSocietyShouldNotBeFound("share.in=" + UPDATED_SHARE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where share is not null
        defaultKamalSocietyShouldBeFound("share.specified=true");

        // Get all the kamalSocietyList where share is null
        defaultKamalSocietyShouldNotBeFound("share.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where share is greater than or equal to DEFAULT_SHARE
        defaultKamalSocietyShouldBeFound("share.greaterThanOrEqual=" + DEFAULT_SHARE);

        // Get all the kamalSocietyList where share is greater than or equal to UPDATED_SHARE
        defaultKamalSocietyShouldNotBeFound("share.greaterThanOrEqual=" + UPDATED_SHARE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where share is less than or equal to DEFAULT_SHARE
        defaultKamalSocietyShouldBeFound("share.lessThanOrEqual=" + DEFAULT_SHARE);

        // Get all the kamalSocietyList where share is less than or equal to SMALLER_SHARE
        defaultKamalSocietyShouldNotBeFound("share.lessThanOrEqual=" + SMALLER_SHARE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where share is less than DEFAULT_SHARE
        defaultKamalSocietyShouldNotBeFound("share.lessThan=" + DEFAULT_SHARE);

        // Get all the kamalSocietyList where share is less than UPDATED_SHARE
        defaultKamalSocietyShouldBeFound("share.lessThan=" + UPDATED_SHARE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where share is greater than DEFAULT_SHARE
        defaultKamalSocietyShouldNotBeFound("share.greaterThan=" + DEFAULT_SHARE);

        // Get all the kamalSocietyList where share is greater than SMALLER_SHARE
        defaultKamalSocietyShouldBeFound("share.greaterThan=" + SMALLER_SHARE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareMr equals to DEFAULT_SHARE_MR
        defaultKamalSocietyShouldBeFound("shareMr.equals=" + DEFAULT_SHARE_MR);

        // Get all the kamalSocietyList where shareMr equals to UPDATED_SHARE_MR
        defaultKamalSocietyShouldNotBeFound("shareMr.equals=" + UPDATED_SHARE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareMr in DEFAULT_SHARE_MR or UPDATED_SHARE_MR
        defaultKamalSocietyShouldBeFound("shareMr.in=" + DEFAULT_SHARE_MR + "," + UPDATED_SHARE_MR);

        // Get all the kamalSocietyList where shareMr equals to UPDATED_SHARE_MR
        defaultKamalSocietyShouldNotBeFound("shareMr.in=" + UPDATED_SHARE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareMr is not null
        defaultKamalSocietyShouldBeFound("shareMr.specified=true");

        // Get all the kamalSocietyList where shareMr is null
        defaultKamalSocietyShouldNotBeFound("shareMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareMr contains DEFAULT_SHARE_MR
        defaultKamalSocietyShouldBeFound("shareMr.contains=" + DEFAULT_SHARE_MR);

        // Get all the kamalSocietyList where shareMr contains UPDATED_SHARE_MR
        defaultKamalSocietyShouldNotBeFound("shareMr.contains=" + UPDATED_SHARE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByShareMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where shareMr does not contain DEFAULT_SHARE_MR
        defaultKamalSocietyShouldNotBeFound("shareMr.doesNotContain=" + DEFAULT_SHARE_MR);

        // Get all the kamalSocietyList where shareMr does not contain UPDATED_SHARE_MR
        defaultKamalSocietyShouldBeFound("shareMr.doesNotContain=" + UPDATED_SHARE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where funds equals to DEFAULT_FUNDS
        defaultKamalSocietyShouldBeFound("funds.equals=" + DEFAULT_FUNDS);

        // Get all the kamalSocietyList where funds equals to UPDATED_FUNDS
        defaultKamalSocietyShouldNotBeFound("funds.equals=" + UPDATED_FUNDS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where funds in DEFAULT_FUNDS or UPDATED_FUNDS
        defaultKamalSocietyShouldBeFound("funds.in=" + DEFAULT_FUNDS + "," + UPDATED_FUNDS);

        // Get all the kamalSocietyList where funds equals to UPDATED_FUNDS
        defaultKamalSocietyShouldNotBeFound("funds.in=" + UPDATED_FUNDS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where funds is not null
        defaultKamalSocietyShouldBeFound("funds.specified=true");

        // Get all the kamalSocietyList where funds is null
        defaultKamalSocietyShouldNotBeFound("funds.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where funds is greater than or equal to DEFAULT_FUNDS
        defaultKamalSocietyShouldBeFound("funds.greaterThanOrEqual=" + DEFAULT_FUNDS);

        // Get all the kamalSocietyList where funds is greater than or equal to UPDATED_FUNDS
        defaultKamalSocietyShouldNotBeFound("funds.greaterThanOrEqual=" + UPDATED_FUNDS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where funds is less than or equal to DEFAULT_FUNDS
        defaultKamalSocietyShouldBeFound("funds.lessThanOrEqual=" + DEFAULT_FUNDS);

        // Get all the kamalSocietyList where funds is less than or equal to SMALLER_FUNDS
        defaultKamalSocietyShouldNotBeFound("funds.lessThanOrEqual=" + SMALLER_FUNDS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where funds is less than DEFAULT_FUNDS
        defaultKamalSocietyShouldNotBeFound("funds.lessThan=" + DEFAULT_FUNDS);

        // Get all the kamalSocietyList where funds is less than UPDATED_FUNDS
        defaultKamalSocietyShouldBeFound("funds.lessThan=" + UPDATED_FUNDS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where funds is greater than DEFAULT_FUNDS
        defaultKamalSocietyShouldNotBeFound("funds.greaterThan=" + DEFAULT_FUNDS);

        // Get all the kamalSocietyList where funds is greater than SMALLER_FUNDS
        defaultKamalSocietyShouldBeFound("funds.greaterThan=" + SMALLER_FUNDS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where fundsMr equals to DEFAULT_FUNDS_MR
        defaultKamalSocietyShouldBeFound("fundsMr.equals=" + DEFAULT_FUNDS_MR);

        // Get all the kamalSocietyList where fundsMr equals to UPDATED_FUNDS_MR
        defaultKamalSocietyShouldNotBeFound("fundsMr.equals=" + UPDATED_FUNDS_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where fundsMr in DEFAULT_FUNDS_MR or UPDATED_FUNDS_MR
        defaultKamalSocietyShouldBeFound("fundsMr.in=" + DEFAULT_FUNDS_MR + "," + UPDATED_FUNDS_MR);

        // Get all the kamalSocietyList where fundsMr equals to UPDATED_FUNDS_MR
        defaultKamalSocietyShouldNotBeFound("fundsMr.in=" + UPDATED_FUNDS_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where fundsMr is not null
        defaultKamalSocietyShouldBeFound("fundsMr.specified=true");

        // Get all the kamalSocietyList where fundsMr is null
        defaultKamalSocietyShouldNotBeFound("fundsMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where fundsMr contains DEFAULT_FUNDS_MR
        defaultKamalSocietyShouldBeFound("fundsMr.contains=" + DEFAULT_FUNDS_MR);

        // Get all the kamalSocietyList where fundsMr contains UPDATED_FUNDS_MR
        defaultKamalSocietyShouldNotBeFound("fundsMr.contains=" + UPDATED_FUNDS_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFundsMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where fundsMr does not contain DEFAULT_FUNDS_MR
        defaultKamalSocietyShouldNotBeFound("fundsMr.doesNotContain=" + DEFAULT_FUNDS_MR);

        // Get all the kamalSocietyList where fundsMr does not contain UPDATED_FUNDS_MR
        defaultKamalSocietyShouldBeFound("fundsMr.doesNotContain=" + UPDATED_FUNDS_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deposit equals to DEFAULT_DEPOSIT
        defaultKamalSocietyShouldBeFound("deposit.equals=" + DEFAULT_DEPOSIT);

        // Get all the kamalSocietyList where deposit equals to UPDATED_DEPOSIT
        defaultKamalSocietyShouldNotBeFound("deposit.equals=" + UPDATED_DEPOSIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deposit in DEFAULT_DEPOSIT or UPDATED_DEPOSIT
        defaultKamalSocietyShouldBeFound("deposit.in=" + DEFAULT_DEPOSIT + "," + UPDATED_DEPOSIT);

        // Get all the kamalSocietyList where deposit equals to UPDATED_DEPOSIT
        defaultKamalSocietyShouldNotBeFound("deposit.in=" + UPDATED_DEPOSIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deposit is not null
        defaultKamalSocietyShouldBeFound("deposit.specified=true");

        // Get all the kamalSocietyList where deposit is null
        defaultKamalSocietyShouldNotBeFound("deposit.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deposit is greater than or equal to DEFAULT_DEPOSIT
        defaultKamalSocietyShouldBeFound("deposit.greaterThanOrEqual=" + DEFAULT_DEPOSIT);

        // Get all the kamalSocietyList where deposit is greater than or equal to UPDATED_DEPOSIT
        defaultKamalSocietyShouldNotBeFound("deposit.greaterThanOrEqual=" + UPDATED_DEPOSIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deposit is less than or equal to DEFAULT_DEPOSIT
        defaultKamalSocietyShouldBeFound("deposit.lessThanOrEqual=" + DEFAULT_DEPOSIT);

        // Get all the kamalSocietyList where deposit is less than or equal to SMALLER_DEPOSIT
        defaultKamalSocietyShouldNotBeFound("deposit.lessThanOrEqual=" + SMALLER_DEPOSIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deposit is less than DEFAULT_DEPOSIT
        defaultKamalSocietyShouldNotBeFound("deposit.lessThan=" + DEFAULT_DEPOSIT);

        // Get all the kamalSocietyList where deposit is less than UPDATED_DEPOSIT
        defaultKamalSocietyShouldBeFound("deposit.lessThan=" + UPDATED_DEPOSIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deposit is greater than DEFAULT_DEPOSIT
        defaultKamalSocietyShouldNotBeFound("deposit.greaterThan=" + DEFAULT_DEPOSIT);

        // Get all the kamalSocietyList where deposit is greater than SMALLER_DEPOSIT
        defaultKamalSocietyShouldBeFound("deposit.greaterThan=" + SMALLER_DEPOSIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where depositMr equals to DEFAULT_DEPOSIT_MR
        defaultKamalSocietyShouldBeFound("depositMr.equals=" + DEFAULT_DEPOSIT_MR);

        // Get all the kamalSocietyList where depositMr equals to UPDATED_DEPOSIT_MR
        defaultKamalSocietyShouldNotBeFound("depositMr.equals=" + UPDATED_DEPOSIT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where depositMr in DEFAULT_DEPOSIT_MR or UPDATED_DEPOSIT_MR
        defaultKamalSocietyShouldBeFound("depositMr.in=" + DEFAULT_DEPOSIT_MR + "," + UPDATED_DEPOSIT_MR);

        // Get all the kamalSocietyList where depositMr equals to UPDATED_DEPOSIT_MR
        defaultKamalSocietyShouldNotBeFound("depositMr.in=" + UPDATED_DEPOSIT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where depositMr is not null
        defaultKamalSocietyShouldBeFound("depositMr.specified=true");

        // Get all the kamalSocietyList where depositMr is null
        defaultKamalSocietyShouldNotBeFound("depositMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where depositMr contains DEFAULT_DEPOSIT_MR
        defaultKamalSocietyShouldBeFound("depositMr.contains=" + DEFAULT_DEPOSIT_MR);

        // Get all the kamalSocietyList where depositMr contains UPDATED_DEPOSIT_MR
        defaultKamalSocietyShouldNotBeFound("depositMr.contains=" + UPDATED_DEPOSIT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDepositMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where depositMr does not contain DEFAULT_DEPOSIT_MR
        defaultKamalSocietyShouldNotBeFound("depositMr.doesNotContain=" + DEFAULT_DEPOSIT_MR);

        // Get all the kamalSocietyList where depositMr does not contain UPDATED_DEPOSIT_MR
        defaultKamalSocietyShouldBeFound("depositMr.doesNotContain=" + UPDATED_DEPOSIT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payable equals to DEFAULT_PAYABLE
        defaultKamalSocietyShouldBeFound("payable.equals=" + DEFAULT_PAYABLE);

        // Get all the kamalSocietyList where payable equals to UPDATED_PAYABLE
        defaultKamalSocietyShouldNotBeFound("payable.equals=" + UPDATED_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payable in DEFAULT_PAYABLE or UPDATED_PAYABLE
        defaultKamalSocietyShouldBeFound("payable.in=" + DEFAULT_PAYABLE + "," + UPDATED_PAYABLE);

        // Get all the kamalSocietyList where payable equals to UPDATED_PAYABLE
        defaultKamalSocietyShouldNotBeFound("payable.in=" + UPDATED_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payable is not null
        defaultKamalSocietyShouldBeFound("payable.specified=true");

        // Get all the kamalSocietyList where payable is null
        defaultKamalSocietyShouldNotBeFound("payable.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payable is greater than or equal to DEFAULT_PAYABLE
        defaultKamalSocietyShouldBeFound("payable.greaterThanOrEqual=" + DEFAULT_PAYABLE);

        // Get all the kamalSocietyList where payable is greater than or equal to UPDATED_PAYABLE
        defaultKamalSocietyShouldNotBeFound("payable.greaterThanOrEqual=" + UPDATED_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payable is less than or equal to DEFAULT_PAYABLE
        defaultKamalSocietyShouldBeFound("payable.lessThanOrEqual=" + DEFAULT_PAYABLE);

        // Get all the kamalSocietyList where payable is less than or equal to SMALLER_PAYABLE
        defaultKamalSocietyShouldNotBeFound("payable.lessThanOrEqual=" + SMALLER_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payable is less than DEFAULT_PAYABLE
        defaultKamalSocietyShouldNotBeFound("payable.lessThan=" + DEFAULT_PAYABLE);

        // Get all the kamalSocietyList where payable is less than UPDATED_PAYABLE
        defaultKamalSocietyShouldBeFound("payable.lessThan=" + UPDATED_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payable is greater than DEFAULT_PAYABLE
        defaultKamalSocietyShouldNotBeFound("payable.greaterThan=" + DEFAULT_PAYABLE);

        // Get all the kamalSocietyList where payable is greater than SMALLER_PAYABLE
        defaultKamalSocietyShouldBeFound("payable.greaterThan=" + SMALLER_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payableMr equals to DEFAULT_PAYABLE_MR
        defaultKamalSocietyShouldBeFound("payableMr.equals=" + DEFAULT_PAYABLE_MR);

        // Get all the kamalSocietyList where payableMr equals to UPDATED_PAYABLE_MR
        defaultKamalSocietyShouldNotBeFound("payableMr.equals=" + UPDATED_PAYABLE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payableMr in DEFAULT_PAYABLE_MR or UPDATED_PAYABLE_MR
        defaultKamalSocietyShouldBeFound("payableMr.in=" + DEFAULT_PAYABLE_MR + "," + UPDATED_PAYABLE_MR);

        // Get all the kamalSocietyList where payableMr equals to UPDATED_PAYABLE_MR
        defaultKamalSocietyShouldNotBeFound("payableMr.in=" + UPDATED_PAYABLE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payableMr is not null
        defaultKamalSocietyShouldBeFound("payableMr.specified=true");

        // Get all the kamalSocietyList where payableMr is null
        defaultKamalSocietyShouldNotBeFound("payableMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payableMr contains DEFAULT_PAYABLE_MR
        defaultKamalSocietyShouldBeFound("payableMr.contains=" + DEFAULT_PAYABLE_MR);

        // Get all the kamalSocietyList where payableMr contains UPDATED_PAYABLE_MR
        defaultKamalSocietyShouldNotBeFound("payableMr.contains=" + UPDATED_PAYABLE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPayableMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where payableMr does not contain DEFAULT_PAYABLE_MR
        defaultKamalSocietyShouldNotBeFound("payableMr.doesNotContain=" + DEFAULT_PAYABLE_MR);

        // Get all the kamalSocietyList where payableMr does not contain UPDATED_PAYABLE_MR
        defaultKamalSocietyShouldBeFound("payableMr.doesNotContain=" + UPDATED_PAYABLE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profit equals to DEFAULT_PROFIT
        defaultKamalSocietyShouldBeFound("profit.equals=" + DEFAULT_PROFIT);

        // Get all the kamalSocietyList where profit equals to UPDATED_PROFIT
        defaultKamalSocietyShouldNotBeFound("profit.equals=" + UPDATED_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profit in DEFAULT_PROFIT or UPDATED_PROFIT
        defaultKamalSocietyShouldBeFound("profit.in=" + DEFAULT_PROFIT + "," + UPDATED_PROFIT);

        // Get all the kamalSocietyList where profit equals to UPDATED_PROFIT
        defaultKamalSocietyShouldNotBeFound("profit.in=" + UPDATED_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profit is not null
        defaultKamalSocietyShouldBeFound("profit.specified=true");

        // Get all the kamalSocietyList where profit is null
        defaultKamalSocietyShouldNotBeFound("profit.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profit is greater than or equal to DEFAULT_PROFIT
        defaultKamalSocietyShouldBeFound("profit.greaterThanOrEqual=" + DEFAULT_PROFIT);

        // Get all the kamalSocietyList where profit is greater than or equal to UPDATED_PROFIT
        defaultKamalSocietyShouldNotBeFound("profit.greaterThanOrEqual=" + UPDATED_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profit is less than or equal to DEFAULT_PROFIT
        defaultKamalSocietyShouldBeFound("profit.lessThanOrEqual=" + DEFAULT_PROFIT);

        // Get all the kamalSocietyList where profit is less than or equal to SMALLER_PROFIT
        defaultKamalSocietyShouldNotBeFound("profit.lessThanOrEqual=" + SMALLER_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profit is less than DEFAULT_PROFIT
        defaultKamalSocietyShouldNotBeFound("profit.lessThan=" + DEFAULT_PROFIT);

        // Get all the kamalSocietyList where profit is less than UPDATED_PROFIT
        defaultKamalSocietyShouldBeFound("profit.lessThan=" + UPDATED_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profit is greater than DEFAULT_PROFIT
        defaultKamalSocietyShouldNotBeFound("profit.greaterThan=" + DEFAULT_PROFIT);

        // Get all the kamalSocietyList where profit is greater than SMALLER_PROFIT
        defaultKamalSocietyShouldBeFound("profit.greaterThan=" + SMALLER_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profitMr equals to DEFAULT_PROFIT_MR
        defaultKamalSocietyShouldBeFound("profitMr.equals=" + DEFAULT_PROFIT_MR);

        // Get all the kamalSocietyList where profitMr equals to UPDATED_PROFIT_MR
        defaultKamalSocietyShouldNotBeFound("profitMr.equals=" + UPDATED_PROFIT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profitMr in DEFAULT_PROFIT_MR or UPDATED_PROFIT_MR
        defaultKamalSocietyShouldBeFound("profitMr.in=" + DEFAULT_PROFIT_MR + "," + UPDATED_PROFIT_MR);

        // Get all the kamalSocietyList where profitMr equals to UPDATED_PROFIT_MR
        defaultKamalSocietyShouldNotBeFound("profitMr.in=" + UPDATED_PROFIT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profitMr is not null
        defaultKamalSocietyShouldBeFound("profitMr.specified=true");

        // Get all the kamalSocietyList where profitMr is null
        defaultKamalSocietyShouldNotBeFound("profitMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profitMr contains DEFAULT_PROFIT_MR
        defaultKamalSocietyShouldBeFound("profitMr.contains=" + DEFAULT_PROFIT_MR);

        // Get all the kamalSocietyList where profitMr contains UPDATED_PROFIT_MR
        defaultKamalSocietyShouldNotBeFound("profitMr.contains=" + UPDATED_PROFIT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByProfitMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where profitMr does not contain DEFAULT_PROFIT_MR
        defaultKamalSocietyShouldNotBeFound("profitMr.doesNotContain=" + DEFAULT_PROFIT_MR);

        // Get all the kamalSocietyList where profitMr does not contain UPDATED_PROFIT_MR
        defaultKamalSocietyShouldBeFound("profitMr.doesNotContain=" + UPDATED_PROFIT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHand equals to DEFAULT_CASH_IN_HAND
        defaultKamalSocietyShouldBeFound("cashInHand.equals=" + DEFAULT_CASH_IN_HAND);

        // Get all the kamalSocietyList where cashInHand equals to UPDATED_CASH_IN_HAND
        defaultKamalSocietyShouldNotBeFound("cashInHand.equals=" + UPDATED_CASH_IN_HAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHand in DEFAULT_CASH_IN_HAND or UPDATED_CASH_IN_HAND
        defaultKamalSocietyShouldBeFound("cashInHand.in=" + DEFAULT_CASH_IN_HAND + "," + UPDATED_CASH_IN_HAND);

        // Get all the kamalSocietyList where cashInHand equals to UPDATED_CASH_IN_HAND
        defaultKamalSocietyShouldNotBeFound("cashInHand.in=" + UPDATED_CASH_IN_HAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHand is not null
        defaultKamalSocietyShouldBeFound("cashInHand.specified=true");

        // Get all the kamalSocietyList where cashInHand is null
        defaultKamalSocietyShouldNotBeFound("cashInHand.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHand is greater than or equal to DEFAULT_CASH_IN_HAND
        defaultKamalSocietyShouldBeFound("cashInHand.greaterThanOrEqual=" + DEFAULT_CASH_IN_HAND);

        // Get all the kamalSocietyList where cashInHand is greater than or equal to UPDATED_CASH_IN_HAND
        defaultKamalSocietyShouldNotBeFound("cashInHand.greaterThanOrEqual=" + UPDATED_CASH_IN_HAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHand is less than or equal to DEFAULT_CASH_IN_HAND
        defaultKamalSocietyShouldBeFound("cashInHand.lessThanOrEqual=" + DEFAULT_CASH_IN_HAND);

        // Get all the kamalSocietyList where cashInHand is less than or equal to SMALLER_CASH_IN_HAND
        defaultKamalSocietyShouldNotBeFound("cashInHand.lessThanOrEqual=" + SMALLER_CASH_IN_HAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHand is less than DEFAULT_CASH_IN_HAND
        defaultKamalSocietyShouldNotBeFound("cashInHand.lessThan=" + DEFAULT_CASH_IN_HAND);

        // Get all the kamalSocietyList where cashInHand is less than UPDATED_CASH_IN_HAND
        defaultKamalSocietyShouldBeFound("cashInHand.lessThan=" + UPDATED_CASH_IN_HAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHand is greater than DEFAULT_CASH_IN_HAND
        defaultKamalSocietyShouldNotBeFound("cashInHand.greaterThan=" + DEFAULT_CASH_IN_HAND);

        // Get all the kamalSocietyList where cashInHand is greater than SMALLER_CASH_IN_HAND
        defaultKamalSocietyShouldBeFound("cashInHand.greaterThan=" + SMALLER_CASH_IN_HAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHandMr equals to DEFAULT_CASH_IN_HAND_MR
        defaultKamalSocietyShouldBeFound("cashInHandMr.equals=" + DEFAULT_CASH_IN_HAND_MR);

        // Get all the kamalSocietyList where cashInHandMr equals to UPDATED_CASH_IN_HAND_MR
        defaultKamalSocietyShouldNotBeFound("cashInHandMr.equals=" + UPDATED_CASH_IN_HAND_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHandMr in DEFAULT_CASH_IN_HAND_MR or UPDATED_CASH_IN_HAND_MR
        defaultKamalSocietyShouldBeFound("cashInHandMr.in=" + DEFAULT_CASH_IN_HAND_MR + "," + UPDATED_CASH_IN_HAND_MR);

        // Get all the kamalSocietyList where cashInHandMr equals to UPDATED_CASH_IN_HAND_MR
        defaultKamalSocietyShouldNotBeFound("cashInHandMr.in=" + UPDATED_CASH_IN_HAND_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHandMr is not null
        defaultKamalSocietyShouldBeFound("cashInHandMr.specified=true");

        // Get all the kamalSocietyList where cashInHandMr is null
        defaultKamalSocietyShouldNotBeFound("cashInHandMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHandMr contains DEFAULT_CASH_IN_HAND_MR
        defaultKamalSocietyShouldBeFound("cashInHandMr.contains=" + DEFAULT_CASH_IN_HAND_MR);

        // Get all the kamalSocietyList where cashInHandMr contains UPDATED_CASH_IN_HAND_MR
        defaultKamalSocietyShouldNotBeFound("cashInHandMr.contains=" + UPDATED_CASH_IN_HAND_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByCashInHandMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where cashInHandMr does not contain DEFAULT_CASH_IN_HAND_MR
        defaultKamalSocietyShouldNotBeFound("cashInHandMr.doesNotContain=" + DEFAULT_CASH_IN_HAND_MR);

        // Get all the kamalSocietyList where cashInHandMr does not contain UPDATED_CASH_IN_HAND_MR
        defaultKamalSocietyShouldBeFound("cashInHandMr.doesNotContain=" + UPDATED_CASH_IN_HAND_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investment equals to DEFAULT_INVESTMENT
        defaultKamalSocietyShouldBeFound("investment.equals=" + DEFAULT_INVESTMENT);

        // Get all the kamalSocietyList where investment equals to UPDATED_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("investment.equals=" + UPDATED_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investment in DEFAULT_INVESTMENT or UPDATED_INVESTMENT
        defaultKamalSocietyShouldBeFound("investment.in=" + DEFAULT_INVESTMENT + "," + UPDATED_INVESTMENT);

        // Get all the kamalSocietyList where investment equals to UPDATED_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("investment.in=" + UPDATED_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investment is not null
        defaultKamalSocietyShouldBeFound("investment.specified=true");

        // Get all the kamalSocietyList where investment is null
        defaultKamalSocietyShouldNotBeFound("investment.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investment is greater than or equal to DEFAULT_INVESTMENT
        defaultKamalSocietyShouldBeFound("investment.greaterThanOrEqual=" + DEFAULT_INVESTMENT);

        // Get all the kamalSocietyList where investment is greater than or equal to UPDATED_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("investment.greaterThanOrEqual=" + UPDATED_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investment is less than or equal to DEFAULT_INVESTMENT
        defaultKamalSocietyShouldBeFound("investment.lessThanOrEqual=" + DEFAULT_INVESTMENT);

        // Get all the kamalSocietyList where investment is less than or equal to SMALLER_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("investment.lessThanOrEqual=" + SMALLER_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investment is less than DEFAULT_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("investment.lessThan=" + DEFAULT_INVESTMENT);

        // Get all the kamalSocietyList where investment is less than UPDATED_INVESTMENT
        defaultKamalSocietyShouldBeFound("investment.lessThan=" + UPDATED_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investment is greater than DEFAULT_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("investment.greaterThan=" + DEFAULT_INVESTMENT);

        // Get all the kamalSocietyList where investment is greater than SMALLER_INVESTMENT
        defaultKamalSocietyShouldBeFound("investment.greaterThan=" + SMALLER_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investmentMr equals to DEFAULT_INVESTMENT_MR
        defaultKamalSocietyShouldBeFound("investmentMr.equals=" + DEFAULT_INVESTMENT_MR);

        // Get all the kamalSocietyList where investmentMr equals to UPDATED_INVESTMENT_MR
        defaultKamalSocietyShouldNotBeFound("investmentMr.equals=" + UPDATED_INVESTMENT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investmentMr in DEFAULT_INVESTMENT_MR or UPDATED_INVESTMENT_MR
        defaultKamalSocietyShouldBeFound("investmentMr.in=" + DEFAULT_INVESTMENT_MR + "," + UPDATED_INVESTMENT_MR);

        // Get all the kamalSocietyList where investmentMr equals to UPDATED_INVESTMENT_MR
        defaultKamalSocietyShouldNotBeFound("investmentMr.in=" + UPDATED_INVESTMENT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investmentMr is not null
        defaultKamalSocietyShouldBeFound("investmentMr.specified=true");

        // Get all the kamalSocietyList where investmentMr is null
        defaultKamalSocietyShouldNotBeFound("investmentMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investmentMr contains DEFAULT_INVESTMENT_MR
        defaultKamalSocietyShouldBeFound("investmentMr.contains=" + DEFAULT_INVESTMENT_MR);

        // Get all the kamalSocietyList where investmentMr contains UPDATED_INVESTMENT_MR
        defaultKamalSocietyShouldNotBeFound("investmentMr.contains=" + UPDATED_INVESTMENT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByInvestmentMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where investmentMr does not contain DEFAULT_INVESTMENT_MR
        defaultKamalSocietyShouldNotBeFound("investmentMr.doesNotContain=" + DEFAULT_INVESTMENT_MR);

        // Get all the kamalSocietyList where investmentMr does not contain UPDATED_INVESTMENT_MR
        defaultKamalSocietyShouldBeFound("investmentMr.doesNotContain=" + UPDATED_INVESTMENT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStock equals to DEFAULT_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("deadStock.equals=" + DEFAULT_DEAD_STOCK);

        // Get all the kamalSocietyList where deadStock equals to UPDATED_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("deadStock.equals=" + UPDATED_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStock in DEFAULT_DEAD_STOCK or UPDATED_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("deadStock.in=" + DEFAULT_DEAD_STOCK + "," + UPDATED_DEAD_STOCK);

        // Get all the kamalSocietyList where deadStock equals to UPDATED_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("deadStock.in=" + UPDATED_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStock is not null
        defaultKamalSocietyShouldBeFound("deadStock.specified=true");

        // Get all the kamalSocietyList where deadStock is null
        defaultKamalSocietyShouldNotBeFound("deadStock.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStock is greater than or equal to DEFAULT_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("deadStock.greaterThanOrEqual=" + DEFAULT_DEAD_STOCK);

        // Get all the kamalSocietyList where deadStock is greater than or equal to UPDATED_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("deadStock.greaterThanOrEqual=" + UPDATED_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStock is less than or equal to DEFAULT_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("deadStock.lessThanOrEqual=" + DEFAULT_DEAD_STOCK);

        // Get all the kamalSocietyList where deadStock is less than or equal to SMALLER_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("deadStock.lessThanOrEqual=" + SMALLER_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStock is less than DEFAULT_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("deadStock.lessThan=" + DEFAULT_DEAD_STOCK);

        // Get all the kamalSocietyList where deadStock is less than UPDATED_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("deadStock.lessThan=" + UPDATED_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStock is greater than DEFAULT_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("deadStock.greaterThan=" + DEFAULT_DEAD_STOCK);

        // Get all the kamalSocietyList where deadStock is greater than SMALLER_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("deadStock.greaterThan=" + SMALLER_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStockMr equals to DEFAULT_DEAD_STOCK_MR
        defaultKamalSocietyShouldBeFound("deadStockMr.equals=" + DEFAULT_DEAD_STOCK_MR);

        // Get all the kamalSocietyList where deadStockMr equals to UPDATED_DEAD_STOCK_MR
        defaultKamalSocietyShouldNotBeFound("deadStockMr.equals=" + UPDATED_DEAD_STOCK_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStockMr in DEFAULT_DEAD_STOCK_MR or UPDATED_DEAD_STOCK_MR
        defaultKamalSocietyShouldBeFound("deadStockMr.in=" + DEFAULT_DEAD_STOCK_MR + "," + UPDATED_DEAD_STOCK_MR);

        // Get all the kamalSocietyList where deadStockMr equals to UPDATED_DEAD_STOCK_MR
        defaultKamalSocietyShouldNotBeFound("deadStockMr.in=" + UPDATED_DEAD_STOCK_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStockMr is not null
        defaultKamalSocietyShouldBeFound("deadStockMr.specified=true");

        // Get all the kamalSocietyList where deadStockMr is null
        defaultKamalSocietyShouldNotBeFound("deadStockMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStockMr contains DEFAULT_DEAD_STOCK_MR
        defaultKamalSocietyShouldBeFound("deadStockMr.contains=" + DEFAULT_DEAD_STOCK_MR);

        // Get all the kamalSocietyList where deadStockMr contains UPDATED_DEAD_STOCK_MR
        defaultKamalSocietyShouldNotBeFound("deadStockMr.contains=" + UPDATED_DEAD_STOCK_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDeadStockMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where deadStockMr does not contain DEFAULT_DEAD_STOCK_MR
        defaultKamalSocietyShouldNotBeFound("deadStockMr.doesNotContain=" + DEFAULT_DEAD_STOCK_MR);

        // Get all the kamalSocietyList where deadStockMr does not contain UPDATED_DEAD_STOCK_MR
        defaultKamalSocietyShouldBeFound("deadStockMr.doesNotContain=" + UPDATED_DEAD_STOCK_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPay equals to DEFAULT_OTHER_PAY
        defaultKamalSocietyShouldBeFound("otherPay.equals=" + DEFAULT_OTHER_PAY);

        // Get all the kamalSocietyList where otherPay equals to UPDATED_OTHER_PAY
        defaultKamalSocietyShouldNotBeFound("otherPay.equals=" + UPDATED_OTHER_PAY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPay in DEFAULT_OTHER_PAY or UPDATED_OTHER_PAY
        defaultKamalSocietyShouldBeFound("otherPay.in=" + DEFAULT_OTHER_PAY + "," + UPDATED_OTHER_PAY);

        // Get all the kamalSocietyList where otherPay equals to UPDATED_OTHER_PAY
        defaultKamalSocietyShouldNotBeFound("otherPay.in=" + UPDATED_OTHER_PAY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPay is not null
        defaultKamalSocietyShouldBeFound("otherPay.specified=true");

        // Get all the kamalSocietyList where otherPay is null
        defaultKamalSocietyShouldNotBeFound("otherPay.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPay is greater than or equal to DEFAULT_OTHER_PAY
        defaultKamalSocietyShouldBeFound("otherPay.greaterThanOrEqual=" + DEFAULT_OTHER_PAY);

        // Get all the kamalSocietyList where otherPay is greater than or equal to UPDATED_OTHER_PAY
        defaultKamalSocietyShouldNotBeFound("otherPay.greaterThanOrEqual=" + UPDATED_OTHER_PAY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPay is less than or equal to DEFAULT_OTHER_PAY
        defaultKamalSocietyShouldBeFound("otherPay.lessThanOrEqual=" + DEFAULT_OTHER_PAY);

        // Get all the kamalSocietyList where otherPay is less than or equal to SMALLER_OTHER_PAY
        defaultKamalSocietyShouldNotBeFound("otherPay.lessThanOrEqual=" + SMALLER_OTHER_PAY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPay is less than DEFAULT_OTHER_PAY
        defaultKamalSocietyShouldNotBeFound("otherPay.lessThan=" + DEFAULT_OTHER_PAY);

        // Get all the kamalSocietyList where otherPay is less than UPDATED_OTHER_PAY
        defaultKamalSocietyShouldBeFound("otherPay.lessThan=" + UPDATED_OTHER_PAY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPay is greater than DEFAULT_OTHER_PAY
        defaultKamalSocietyShouldNotBeFound("otherPay.greaterThan=" + DEFAULT_OTHER_PAY);

        // Get all the kamalSocietyList where otherPay is greater than SMALLER_OTHER_PAY
        defaultKamalSocietyShouldBeFound("otherPay.greaterThan=" + SMALLER_OTHER_PAY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPayMr equals to DEFAULT_OTHER_PAY_MR
        defaultKamalSocietyShouldBeFound("otherPayMr.equals=" + DEFAULT_OTHER_PAY_MR);

        // Get all the kamalSocietyList where otherPayMr equals to UPDATED_OTHER_PAY_MR
        defaultKamalSocietyShouldNotBeFound("otherPayMr.equals=" + UPDATED_OTHER_PAY_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPayMr in DEFAULT_OTHER_PAY_MR or UPDATED_OTHER_PAY_MR
        defaultKamalSocietyShouldBeFound("otherPayMr.in=" + DEFAULT_OTHER_PAY_MR + "," + UPDATED_OTHER_PAY_MR);

        // Get all the kamalSocietyList where otherPayMr equals to UPDATED_OTHER_PAY_MR
        defaultKamalSocietyShouldNotBeFound("otherPayMr.in=" + UPDATED_OTHER_PAY_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPayMr is not null
        defaultKamalSocietyShouldBeFound("otherPayMr.specified=true");

        // Get all the kamalSocietyList where otherPayMr is null
        defaultKamalSocietyShouldNotBeFound("otherPayMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPayMr contains DEFAULT_OTHER_PAY_MR
        defaultKamalSocietyShouldBeFound("otherPayMr.contains=" + DEFAULT_OTHER_PAY_MR);

        // Get all the kamalSocietyList where otherPayMr contains UPDATED_OTHER_PAY_MR
        defaultKamalSocietyShouldNotBeFound("otherPayMr.contains=" + UPDATED_OTHER_PAY_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByOtherPayMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where otherPayMr does not contain DEFAULT_OTHER_PAY_MR
        defaultKamalSocietyShouldNotBeFound("otherPayMr.doesNotContain=" + DEFAULT_OTHER_PAY_MR);

        // Get all the kamalSocietyList where otherPayMr does not contain UPDATED_OTHER_PAY_MR
        defaultKamalSocietyShouldBeFound("otherPayMr.doesNotContain=" + UPDATED_OTHER_PAY_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where loss equals to DEFAULT_LOSS
        defaultKamalSocietyShouldBeFound("loss.equals=" + DEFAULT_LOSS);

        // Get all the kamalSocietyList where loss equals to UPDATED_LOSS
        defaultKamalSocietyShouldNotBeFound("loss.equals=" + UPDATED_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where loss in DEFAULT_LOSS or UPDATED_LOSS
        defaultKamalSocietyShouldBeFound("loss.in=" + DEFAULT_LOSS + "," + UPDATED_LOSS);

        // Get all the kamalSocietyList where loss equals to UPDATED_LOSS
        defaultKamalSocietyShouldNotBeFound("loss.in=" + UPDATED_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where loss is not null
        defaultKamalSocietyShouldBeFound("loss.specified=true");

        // Get all the kamalSocietyList where loss is null
        defaultKamalSocietyShouldNotBeFound("loss.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where loss is greater than or equal to DEFAULT_LOSS
        defaultKamalSocietyShouldBeFound("loss.greaterThanOrEqual=" + DEFAULT_LOSS);

        // Get all the kamalSocietyList where loss is greater than or equal to UPDATED_LOSS
        defaultKamalSocietyShouldNotBeFound("loss.greaterThanOrEqual=" + UPDATED_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where loss is less than or equal to DEFAULT_LOSS
        defaultKamalSocietyShouldBeFound("loss.lessThanOrEqual=" + DEFAULT_LOSS);

        // Get all the kamalSocietyList where loss is less than or equal to SMALLER_LOSS
        defaultKamalSocietyShouldNotBeFound("loss.lessThanOrEqual=" + SMALLER_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where loss is less than DEFAULT_LOSS
        defaultKamalSocietyShouldNotBeFound("loss.lessThan=" + DEFAULT_LOSS);

        // Get all the kamalSocietyList where loss is less than UPDATED_LOSS
        defaultKamalSocietyShouldBeFound("loss.lessThan=" + UPDATED_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where loss is greater than DEFAULT_LOSS
        defaultKamalSocietyShouldNotBeFound("loss.greaterThan=" + DEFAULT_LOSS);

        // Get all the kamalSocietyList where loss is greater than SMALLER_LOSS
        defaultKamalSocietyShouldBeFound("loss.greaterThan=" + SMALLER_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where lossMr equals to DEFAULT_LOSS_MR
        defaultKamalSocietyShouldBeFound("lossMr.equals=" + DEFAULT_LOSS_MR);

        // Get all the kamalSocietyList where lossMr equals to UPDATED_LOSS_MR
        defaultKamalSocietyShouldNotBeFound("lossMr.equals=" + UPDATED_LOSS_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where lossMr in DEFAULT_LOSS_MR or UPDATED_LOSS_MR
        defaultKamalSocietyShouldBeFound("lossMr.in=" + DEFAULT_LOSS_MR + "," + UPDATED_LOSS_MR);

        // Get all the kamalSocietyList where lossMr equals to UPDATED_LOSS_MR
        defaultKamalSocietyShouldNotBeFound("lossMr.in=" + UPDATED_LOSS_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where lossMr is not null
        defaultKamalSocietyShouldBeFound("lossMr.specified=true");

        // Get all the kamalSocietyList where lossMr is null
        defaultKamalSocietyShouldNotBeFound("lossMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where lossMr contains DEFAULT_LOSS_MR
        defaultKamalSocietyShouldBeFound("lossMr.contains=" + DEFAULT_LOSS_MR);

        // Get all the kamalSocietyList where lossMr contains UPDATED_LOSS_MR
        defaultKamalSocietyShouldNotBeFound("lossMr.contains=" + UPDATED_LOSS_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLossMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where lossMr does not contain DEFAULT_LOSS_MR
        defaultKamalSocietyShouldNotBeFound("lossMr.doesNotContain=" + DEFAULT_LOSS_MR);

        // Get all the kamalSocietyList where lossMr does not contain UPDATED_LOSS_MR
        defaultKamalSocietyShouldBeFound("lossMr.doesNotContain=" + UPDATED_LOSS_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayat equals to DEFAULT_TOTAL_BAGAYAT
        defaultKamalSocietyShouldBeFound("totalBagayat.equals=" + DEFAULT_TOTAL_BAGAYAT);

        // Get all the kamalSocietyList where totalBagayat equals to UPDATED_TOTAL_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("totalBagayat.equals=" + UPDATED_TOTAL_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayat in DEFAULT_TOTAL_BAGAYAT or UPDATED_TOTAL_BAGAYAT
        defaultKamalSocietyShouldBeFound("totalBagayat.in=" + DEFAULT_TOTAL_BAGAYAT + "," + UPDATED_TOTAL_BAGAYAT);

        // Get all the kamalSocietyList where totalBagayat equals to UPDATED_TOTAL_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("totalBagayat.in=" + UPDATED_TOTAL_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayat is not null
        defaultKamalSocietyShouldBeFound("totalBagayat.specified=true");

        // Get all the kamalSocietyList where totalBagayat is null
        defaultKamalSocietyShouldNotBeFound("totalBagayat.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayat is greater than or equal to DEFAULT_TOTAL_BAGAYAT
        defaultKamalSocietyShouldBeFound("totalBagayat.greaterThanOrEqual=" + DEFAULT_TOTAL_BAGAYAT);

        // Get all the kamalSocietyList where totalBagayat is greater than or equal to UPDATED_TOTAL_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("totalBagayat.greaterThanOrEqual=" + UPDATED_TOTAL_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayat is less than or equal to DEFAULT_TOTAL_BAGAYAT
        defaultKamalSocietyShouldBeFound("totalBagayat.lessThanOrEqual=" + DEFAULT_TOTAL_BAGAYAT);

        // Get all the kamalSocietyList where totalBagayat is less than or equal to SMALLER_TOTAL_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("totalBagayat.lessThanOrEqual=" + SMALLER_TOTAL_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayat is less than DEFAULT_TOTAL_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("totalBagayat.lessThan=" + DEFAULT_TOTAL_BAGAYAT);

        // Get all the kamalSocietyList where totalBagayat is less than UPDATED_TOTAL_BAGAYAT
        defaultKamalSocietyShouldBeFound("totalBagayat.lessThan=" + UPDATED_TOTAL_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayat is greater than DEFAULT_TOTAL_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("totalBagayat.greaterThan=" + DEFAULT_TOTAL_BAGAYAT);

        // Get all the kamalSocietyList where totalBagayat is greater than SMALLER_TOTAL_BAGAYAT
        defaultKamalSocietyShouldBeFound("totalBagayat.greaterThan=" + SMALLER_TOTAL_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayatMr equals to DEFAULT_TOTAL_BAGAYAT_MR
        defaultKamalSocietyShouldBeFound("totalBagayatMr.equals=" + DEFAULT_TOTAL_BAGAYAT_MR);

        // Get all the kamalSocietyList where totalBagayatMr equals to UPDATED_TOTAL_BAGAYAT_MR
        defaultKamalSocietyShouldNotBeFound("totalBagayatMr.equals=" + UPDATED_TOTAL_BAGAYAT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayatMr in DEFAULT_TOTAL_BAGAYAT_MR or UPDATED_TOTAL_BAGAYAT_MR
        defaultKamalSocietyShouldBeFound("totalBagayatMr.in=" + DEFAULT_TOTAL_BAGAYAT_MR + "," + UPDATED_TOTAL_BAGAYAT_MR);

        // Get all the kamalSocietyList where totalBagayatMr equals to UPDATED_TOTAL_BAGAYAT_MR
        defaultKamalSocietyShouldNotBeFound("totalBagayatMr.in=" + UPDATED_TOTAL_BAGAYAT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayatMr is not null
        defaultKamalSocietyShouldBeFound("totalBagayatMr.specified=true");

        // Get all the kamalSocietyList where totalBagayatMr is null
        defaultKamalSocietyShouldNotBeFound("totalBagayatMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayatMr contains DEFAULT_TOTAL_BAGAYAT_MR
        defaultKamalSocietyShouldBeFound("totalBagayatMr.contains=" + DEFAULT_TOTAL_BAGAYAT_MR);

        // Get all the kamalSocietyList where totalBagayatMr contains UPDATED_TOTAL_BAGAYAT_MR
        defaultKamalSocietyShouldNotBeFound("totalBagayatMr.contains=" + UPDATED_TOTAL_BAGAYAT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalBagayatMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalBagayatMr does not contain DEFAULT_TOTAL_BAGAYAT_MR
        defaultKamalSocietyShouldNotBeFound("totalBagayatMr.doesNotContain=" + DEFAULT_TOTAL_BAGAYAT_MR);

        // Get all the kamalSocietyList where totalBagayatMr does not contain UPDATED_TOTAL_BAGAYAT_MR
        defaultKamalSocietyShouldBeFound("totalBagayatMr.doesNotContain=" + UPDATED_TOTAL_BAGAYAT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayat equals to DEFAULT_TOTAL_JIRAYAT
        defaultKamalSocietyShouldBeFound("totalJirayat.equals=" + DEFAULT_TOTAL_JIRAYAT);

        // Get all the kamalSocietyList where totalJirayat equals to UPDATED_TOTAL_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("totalJirayat.equals=" + UPDATED_TOTAL_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayat in DEFAULT_TOTAL_JIRAYAT or UPDATED_TOTAL_JIRAYAT
        defaultKamalSocietyShouldBeFound("totalJirayat.in=" + DEFAULT_TOTAL_JIRAYAT + "," + UPDATED_TOTAL_JIRAYAT);

        // Get all the kamalSocietyList where totalJirayat equals to UPDATED_TOTAL_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("totalJirayat.in=" + UPDATED_TOTAL_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayat is not null
        defaultKamalSocietyShouldBeFound("totalJirayat.specified=true");

        // Get all the kamalSocietyList where totalJirayat is null
        defaultKamalSocietyShouldNotBeFound("totalJirayat.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayat is greater than or equal to DEFAULT_TOTAL_JIRAYAT
        defaultKamalSocietyShouldBeFound("totalJirayat.greaterThanOrEqual=" + DEFAULT_TOTAL_JIRAYAT);

        // Get all the kamalSocietyList where totalJirayat is greater than or equal to UPDATED_TOTAL_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("totalJirayat.greaterThanOrEqual=" + UPDATED_TOTAL_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayat is less than or equal to DEFAULT_TOTAL_JIRAYAT
        defaultKamalSocietyShouldBeFound("totalJirayat.lessThanOrEqual=" + DEFAULT_TOTAL_JIRAYAT);

        // Get all the kamalSocietyList where totalJirayat is less than or equal to SMALLER_TOTAL_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("totalJirayat.lessThanOrEqual=" + SMALLER_TOTAL_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayat is less than DEFAULT_TOTAL_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("totalJirayat.lessThan=" + DEFAULT_TOTAL_JIRAYAT);

        // Get all the kamalSocietyList where totalJirayat is less than UPDATED_TOTAL_JIRAYAT
        defaultKamalSocietyShouldBeFound("totalJirayat.lessThan=" + UPDATED_TOTAL_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayat is greater than DEFAULT_TOTAL_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("totalJirayat.greaterThan=" + DEFAULT_TOTAL_JIRAYAT);

        // Get all the kamalSocietyList where totalJirayat is greater than SMALLER_TOTAL_JIRAYAT
        defaultKamalSocietyShouldBeFound("totalJirayat.greaterThan=" + SMALLER_TOTAL_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayatMr equals to DEFAULT_TOTAL_JIRAYAT_MR
        defaultKamalSocietyShouldBeFound("totalJirayatMr.equals=" + DEFAULT_TOTAL_JIRAYAT_MR);

        // Get all the kamalSocietyList where totalJirayatMr equals to UPDATED_TOTAL_JIRAYAT_MR
        defaultKamalSocietyShouldNotBeFound("totalJirayatMr.equals=" + UPDATED_TOTAL_JIRAYAT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayatMr in DEFAULT_TOTAL_JIRAYAT_MR or UPDATED_TOTAL_JIRAYAT_MR
        defaultKamalSocietyShouldBeFound("totalJirayatMr.in=" + DEFAULT_TOTAL_JIRAYAT_MR + "," + UPDATED_TOTAL_JIRAYAT_MR);

        // Get all the kamalSocietyList where totalJirayatMr equals to UPDATED_TOTAL_JIRAYAT_MR
        defaultKamalSocietyShouldNotBeFound("totalJirayatMr.in=" + UPDATED_TOTAL_JIRAYAT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayatMr is not null
        defaultKamalSocietyShouldBeFound("totalJirayatMr.specified=true");

        // Get all the kamalSocietyList where totalJirayatMr is null
        defaultKamalSocietyShouldNotBeFound("totalJirayatMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayatMr contains DEFAULT_TOTAL_JIRAYAT_MR
        defaultKamalSocietyShouldBeFound("totalJirayatMr.contains=" + DEFAULT_TOTAL_JIRAYAT_MR);

        // Get all the kamalSocietyList where totalJirayatMr contains UPDATED_TOTAL_JIRAYAT_MR
        defaultKamalSocietyShouldNotBeFound("totalJirayatMr.contains=" + UPDATED_TOTAL_JIRAYAT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalJirayatMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalJirayatMr does not contain DEFAULT_TOTAL_JIRAYAT_MR
        defaultKamalSocietyShouldNotBeFound("totalJirayatMr.doesNotContain=" + DEFAULT_TOTAL_JIRAYAT_MR);

        // Get all the kamalSocietyList where totalJirayatMr does not contain UPDATED_TOTAL_JIRAYAT_MR
        defaultKamalSocietyShouldBeFound("totalJirayatMr.doesNotContain=" + UPDATED_TOTAL_JIRAYAT_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKamalCropIsEqualToSomething() throws Exception {
        KamalCrop kamalCrop;
        if (TestUtil.findAll(em, KamalCrop.class).isEmpty()) {
            kamalSocietyRepository.saveAndFlush(kamalSociety);
            kamalCrop = KamalCropResourceIT.createEntity(em);
        } else {
            kamalCrop = TestUtil.findAll(em, KamalCrop.class).get(0);
        }
        em.persist(kamalCrop);
        em.flush();
        kamalSociety.addKamalCrop(kamalCrop);
        kamalSocietyRepository.saveAndFlush(kamalSociety);
        Long kamalCropId = kamalCrop.getId();

        // Get all the kamalSocietyList where kamalCrop equals to kamalCropId
        defaultKamalSocietyShouldBeFound("kamalCropId.equals=" + kamalCropId);

        // Get all the kamalSocietyList where kamalCrop equals to (kamalCropId + 1)
        defaultKamalSocietyShouldNotBeFound("kamalCropId.equals=" + (kamalCropId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKamalSocietyShouldBeFound(String filter) throws Exception {
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kamalSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].zindagiDate").value(hasItem(DEFAULT_ZINDAGI_DATE.toString())))
            .andExpect(jsonPath("$.[*].zindagiDateMr").value(hasItem(DEFAULT_ZINDAGI_DATE_MR)))
            .andExpect(jsonPath("$.[*].village1").value(hasItem(DEFAULT_VILLAGE_1)))
            .andExpect(jsonPath("$.[*].village1Mr").value(hasItem(DEFAULT_VILLAGE_1_MR)))
            .andExpect(jsonPath("$.[*].village2").value(hasItem(DEFAULT_VILLAGE_2)))
            .andExpect(jsonPath("$.[*].village2Mr").value(hasItem(DEFAULT_VILLAGE_2_MR)))
            .andExpect(jsonPath("$.[*].village3").value(hasItem(DEFAULT_VILLAGE_3)))
            .andExpect(jsonPath("$.[*].village3Mr").value(hasItem(DEFAULT_VILLAGE_3_MR)))
            .andExpect(jsonPath("$.[*].totalLand").value(hasItem(DEFAULT_TOTAL_LAND.doubleValue())))
            .andExpect(jsonPath("$.[*].totalLandMr").value(hasItem(DEFAULT_TOTAL_LAND_MR)))
            .andExpect(jsonPath("$.[*].totalMem").value(hasItem(DEFAULT_TOTAL_MEM.doubleValue())))
            .andExpect(jsonPath("$.[*].totalMemMr").value(hasItem(DEFAULT_TOTAL_MEM_MR)))
            .andExpect(jsonPath("$.[*].totalNonMem").value(hasItem(DEFAULT_TOTAL_NON_MEM.doubleValue())))
            .andExpect(jsonPath("$.[*].totalNonMemMr").value(hasItem(DEFAULT_TOTAL_NON_MEM_MR)))
            .andExpect(jsonPath("$.[*].totalGMem").value(hasItem(DEFAULT_TOTAL_G_MEM.doubleValue())))
            .andExpect(jsonPath("$.[*].totalGMemMr").value(hasItem(DEFAULT_TOTAL_G_MEM_MR)))
            .andExpect(jsonPath("$.[*].memLoan").value(hasItem(DEFAULT_MEM_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].memLoanMr").value(hasItem(DEFAULT_MEM_LOAN_MR)))
            .andExpect(jsonPath("$.[*].memDue").value(hasItem(DEFAULT_MEM_DUE.doubleValue())))
            .andExpect(jsonPath("$.[*].memDueMr").value(hasItem(DEFAULT_MEM_DUE_MR)))
            .andExpect(jsonPath("$.[*].memDueper").value(hasItem(DEFAULT_MEM_DUEPER.doubleValue())))
            .andExpect(jsonPath("$.[*].memDueperMr").value(hasItem(DEFAULT_MEM_DUEPER_MR)))
            .andExpect(jsonPath("$.[*].memVasulpatra").value(hasItem(DEFAULT_MEM_VASULPATRA.doubleValue())))
            .andExpect(jsonPath("$.[*].memVasulpatraMr").value(hasItem(DEFAULT_MEM_VASULPATRA_MR)))
            .andExpect(jsonPath("$.[*].memVasul").value(hasItem(DEFAULT_MEM_VASUL.doubleValue())))
            .andExpect(jsonPath("$.[*].memVasulMr").value(hasItem(DEFAULT_MEM_VASUL_MR)))
            .andExpect(jsonPath("$.[*].memVasulPer").value(hasItem(DEFAULT_MEM_VASUL_PER.doubleValue())))
            .andExpect(jsonPath("$.[*].memVasulPerMr").value(hasItem(DEFAULT_MEM_VASUL_PER_MR)))
            .andExpect(jsonPath("$.[*].bankLoan").value(hasItem(DEFAULT_BANK_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].bankLoanMr").value(hasItem(DEFAULT_BANK_LOAN_MR)))
            .andExpect(jsonPath("$.[*].bankDue").value(hasItem(DEFAULT_BANK_DUE.doubleValue())))
            .andExpect(jsonPath("$.[*].bankDueMr").value(hasItem(DEFAULT_BANK_DUE_MR)))
            .andExpect(jsonPath("$.[*].bankDueper").value(hasItem(DEFAULT_BANK_DUEPER.doubleValue())))
            .andExpect(jsonPath("$.[*].bankDueperMr").value(hasItem(DEFAULT_BANK_DUEPER_MR)))
            .andExpect(jsonPath("$.[*].bankVasulpatra").value(hasItem(DEFAULT_BANK_VASULPATRA.doubleValue())))
            .andExpect(jsonPath("$.[*].bankVasulpatraMr").value(hasItem(DEFAULT_BANK_VASULPATRA_MR)))
            .andExpect(jsonPath("$.[*].bankVasul").value(hasItem(DEFAULT_BANK_VASUL.doubleValue())))
            .andExpect(jsonPath("$.[*].bankVasulMr").value(hasItem(DEFAULT_BANK_VASUL_MR)))
            .andExpect(jsonPath("$.[*].bankVasulPer").value(hasItem(DEFAULT_BANK_VASUL_PER.doubleValue())))
            .andExpect(jsonPath("$.[*].bankVasulPerMr").value(hasItem(DEFAULT_BANK_VASUL_PER_MR)))
            .andExpect(jsonPath("$.[*].shareCapital").value(hasItem(DEFAULT_SHARE_CAPITAL.doubleValue())))
            .andExpect(jsonPath("$.[*].shareCapitalMr").value(hasItem(DEFAULT_SHARE_CAPITAL_MR)))
            .andExpect(jsonPath("$.[*].share").value(hasItem(DEFAULT_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].shareMr").value(hasItem(DEFAULT_SHARE_MR)))
            .andExpect(jsonPath("$.[*].funds").value(hasItem(DEFAULT_FUNDS.doubleValue())))
            .andExpect(jsonPath("$.[*].fundsMr").value(hasItem(DEFAULT_FUNDS_MR)))
            .andExpect(jsonPath("$.[*].deposit").value(hasItem(DEFAULT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].depositMr").value(hasItem(DEFAULT_DEPOSIT_MR)))
            .andExpect(jsonPath("$.[*].payable").value(hasItem(DEFAULT_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].payableMr").value(hasItem(DEFAULT_PAYABLE_MR)))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(DEFAULT_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].profitMr").value(hasItem(DEFAULT_PROFIT_MR)))
            .andExpect(jsonPath("$.[*].cashInHand").value(hasItem(DEFAULT_CASH_IN_HAND.doubleValue())))
            .andExpect(jsonPath("$.[*].cashInHandMr").value(hasItem(DEFAULT_CASH_IN_HAND_MR)))
            .andExpect(jsonPath("$.[*].investment").value(hasItem(DEFAULT_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].investmentMr").value(hasItem(DEFAULT_INVESTMENT_MR)))
            .andExpect(jsonPath("$.[*].deadStock").value(hasItem(DEFAULT_DEAD_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].deadStockMr").value(hasItem(DEFAULT_DEAD_STOCK_MR)))
            .andExpect(jsonPath("$.[*].otherPay").value(hasItem(DEFAULT_OTHER_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].otherPayMr").value(hasItem(DEFAULT_OTHER_PAY_MR)))
            .andExpect(jsonPath("$.[*].loss").value(hasItem(DEFAULT_LOSS.doubleValue())))
            .andExpect(jsonPath("$.[*].lossMr").value(hasItem(DEFAULT_LOSS_MR)))
            .andExpect(jsonPath("$.[*].totalBagayat").value(hasItem(DEFAULT_TOTAL_BAGAYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalBagayatMr").value(hasItem(DEFAULT_TOTAL_BAGAYAT_MR)))
            .andExpect(jsonPath("$.[*].totalJirayat").value(hasItem(DEFAULT_TOTAL_JIRAYAT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalJirayatMr").value(hasItem(DEFAULT_TOTAL_JIRAYAT_MR)));

        // Check, that the count call also returns 1
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKamalSocietyShouldNotBeFound(String filter) throws Exception {
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKamalSocietyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKamalSociety() throws Exception {
        // Get the kamalSociety
        restKamalSocietyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKamalSociety() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();

        // Update the kamalSociety
        KamalSociety updatedKamalSociety = kamalSocietyRepository.findById(kamalSociety.getId()).get();
        // Disconnect from session so that the updates on updatedKamalSociety are not directly saved in db
        em.detach(updatedKamalSociety);
        updatedKamalSociety
            .pacsNumber(UPDATED_PACS_NUMBER)
            .zindagiDate(UPDATED_ZINDAGI_DATE)
            .zindagiDateMr(UPDATED_ZINDAGI_DATE_MR)
            .village1(UPDATED_VILLAGE_1)
            .village1Mr(UPDATED_VILLAGE_1_MR)
            .village2(UPDATED_VILLAGE_2)
            .village2Mr(UPDATED_VILLAGE_2_MR)
            .village3(UPDATED_VILLAGE_3)
            .village3Mr(UPDATED_VILLAGE_3_MR)
            .totalLand(UPDATED_TOTAL_LAND)
            .totalLandMr(UPDATED_TOTAL_LAND_MR)
            .totalMem(UPDATED_TOTAL_MEM)
            .totalMemMr(UPDATED_TOTAL_MEM_MR)
            .totalNonMem(UPDATED_TOTAL_NON_MEM)
            .totalNonMemMr(UPDATED_TOTAL_NON_MEM_MR)
            .totalGMem(UPDATED_TOTAL_G_MEM)
            .totalGMemMr(UPDATED_TOTAL_G_MEM_MR)
            .memLoan(UPDATED_MEM_LOAN)
            .memLoanMr(UPDATED_MEM_LOAN_MR)
            .memDue(UPDATED_MEM_DUE)
            .memDueMr(UPDATED_MEM_DUE_MR)
            .memDueper(UPDATED_MEM_DUEPER)
            .memDueperMr(UPDATED_MEM_DUEPER_MR)
            .memVasulpatra(UPDATED_MEM_VASULPATRA)
            .memVasulpatraMr(UPDATED_MEM_VASULPATRA_MR)
            .memVasul(UPDATED_MEM_VASUL)
            .memVasulMr(UPDATED_MEM_VASUL_MR)
            .memVasulPer(UPDATED_MEM_VASUL_PER)
            .memVasulPerMr(UPDATED_MEM_VASUL_PER_MR)
            .bankLoan(UPDATED_BANK_LOAN)
            .bankLoanMr(UPDATED_BANK_LOAN_MR)
            .bankDue(UPDATED_BANK_DUE)
            .bankDueMr(UPDATED_BANK_DUE_MR)
            .bankDueper(UPDATED_BANK_DUEPER)
            .bankDueperMr(UPDATED_BANK_DUEPER_MR)
            .bankVasulpatra(UPDATED_BANK_VASULPATRA)
            .bankVasulpatraMr(UPDATED_BANK_VASULPATRA_MR)
            .bankVasul(UPDATED_BANK_VASUL)
            .bankVasulMr(UPDATED_BANK_VASUL_MR)
            .bankVasulPer(UPDATED_BANK_VASUL_PER)
            .bankVasulPerMr(UPDATED_BANK_VASUL_PER_MR)
            .shareCapital(UPDATED_SHARE_CAPITAL)
            .shareCapitalMr(UPDATED_SHARE_CAPITAL_MR)
            .share(UPDATED_SHARE)
            .shareMr(UPDATED_SHARE_MR)
            .funds(UPDATED_FUNDS)
            .fundsMr(UPDATED_FUNDS_MR)
            .deposit(UPDATED_DEPOSIT)
            .depositMr(UPDATED_DEPOSIT_MR)
            .payable(UPDATED_PAYABLE)
            .payableMr(UPDATED_PAYABLE_MR)
            .profit(UPDATED_PROFIT)
            .profitMr(UPDATED_PROFIT_MR)
            .cashInHand(UPDATED_CASH_IN_HAND)
            .cashInHandMr(UPDATED_CASH_IN_HAND_MR)
            .investment(UPDATED_INVESTMENT)
            .investmentMr(UPDATED_INVESTMENT_MR)
            .deadStock(UPDATED_DEAD_STOCK)
            .deadStockMr(UPDATED_DEAD_STOCK_MR)
            .otherPay(UPDATED_OTHER_PAY)
            .otherPayMr(UPDATED_OTHER_PAY_MR)
            .loss(UPDATED_LOSS)
            .lossMr(UPDATED_LOSS_MR)
            .totalBagayat(UPDATED_TOTAL_BAGAYAT)
            .totalBagayatMr(UPDATED_TOTAL_BAGAYAT_MR)
            .totalJirayat(UPDATED_TOTAL_JIRAYAT)
            .totalJirayatMr(UPDATED_TOTAL_JIRAYAT_MR);

        restKamalSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKamalSociety.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKamalSociety))
            )
            .andExpect(status().isOk());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
        KamalSociety testKamalSociety = kamalSocietyList.get(kamalSocietyList.size() - 1);
        assertThat(testKamalSociety.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalSociety.getZindagiDate()).isEqualTo(UPDATED_ZINDAGI_DATE);
        assertThat(testKamalSociety.getZindagiDateMr()).isEqualTo(UPDATED_ZINDAGI_DATE_MR);
        assertThat(testKamalSociety.getVillage1()).isEqualTo(UPDATED_VILLAGE_1);
        assertThat(testKamalSociety.getVillage1Mr()).isEqualTo(UPDATED_VILLAGE_1_MR);
        assertThat(testKamalSociety.getVillage2()).isEqualTo(UPDATED_VILLAGE_2);
        assertThat(testKamalSociety.getVillage2Mr()).isEqualTo(UPDATED_VILLAGE_2_MR);
        assertThat(testKamalSociety.getVillage3()).isEqualTo(UPDATED_VILLAGE_3);
        assertThat(testKamalSociety.getVillage3Mr()).isEqualTo(UPDATED_VILLAGE_3_MR);
        assertThat(testKamalSociety.getTotalLand()).isEqualTo(UPDATED_TOTAL_LAND);
        assertThat(testKamalSociety.getTotalLandMr()).isEqualTo(UPDATED_TOTAL_LAND_MR);
        assertThat(testKamalSociety.getTotalMem()).isEqualTo(UPDATED_TOTAL_MEM);
        assertThat(testKamalSociety.getTotalMemMr()).isEqualTo(UPDATED_TOTAL_MEM_MR);
        assertThat(testKamalSociety.getTotalNonMem()).isEqualTo(UPDATED_TOTAL_NON_MEM);
        assertThat(testKamalSociety.getTotalNonMemMr()).isEqualTo(UPDATED_TOTAL_NON_MEM_MR);
        assertThat(testKamalSociety.getTotalGMem()).isEqualTo(UPDATED_TOTAL_G_MEM);
        assertThat(testKamalSociety.getTotalGMemMr()).isEqualTo(UPDATED_TOTAL_G_MEM_MR);
        assertThat(testKamalSociety.getMemLoan()).isEqualTo(UPDATED_MEM_LOAN);
        assertThat(testKamalSociety.getMemLoanMr()).isEqualTo(UPDATED_MEM_LOAN_MR);
        assertThat(testKamalSociety.getMemDue()).isEqualTo(UPDATED_MEM_DUE);
        assertThat(testKamalSociety.getMemDueMr()).isEqualTo(UPDATED_MEM_DUE_MR);
        assertThat(testKamalSociety.getMemDueper()).isEqualTo(UPDATED_MEM_DUEPER);
        assertThat(testKamalSociety.getMemDueperMr()).isEqualTo(UPDATED_MEM_DUEPER_MR);
        assertThat(testKamalSociety.getMemVasulpatra()).isEqualTo(UPDATED_MEM_VASULPATRA);
        assertThat(testKamalSociety.getMemVasulpatraMr()).isEqualTo(UPDATED_MEM_VASULPATRA_MR);
        assertThat(testKamalSociety.getMemVasul()).isEqualTo(UPDATED_MEM_VASUL);
        assertThat(testKamalSociety.getMemVasulMr()).isEqualTo(UPDATED_MEM_VASUL_MR);
        assertThat(testKamalSociety.getMemVasulPer()).isEqualTo(UPDATED_MEM_VASUL_PER);
        assertThat(testKamalSociety.getMemVasulPerMr()).isEqualTo(UPDATED_MEM_VASUL_PER_MR);
        assertThat(testKamalSociety.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testKamalSociety.getBankLoanMr()).isEqualTo(UPDATED_BANK_LOAN_MR);
        assertThat(testKamalSociety.getBankDue()).isEqualTo(UPDATED_BANK_DUE);
        assertThat(testKamalSociety.getBankDueMr()).isEqualTo(UPDATED_BANK_DUE_MR);
        assertThat(testKamalSociety.getBankDueper()).isEqualTo(UPDATED_BANK_DUEPER);
        assertThat(testKamalSociety.getBankDueperMr()).isEqualTo(UPDATED_BANK_DUEPER_MR);
        assertThat(testKamalSociety.getBankVasulpatra()).isEqualTo(UPDATED_BANK_VASULPATRA);
        assertThat(testKamalSociety.getBankVasulpatraMr()).isEqualTo(UPDATED_BANK_VASULPATRA_MR);
        assertThat(testKamalSociety.getBankVasul()).isEqualTo(UPDATED_BANK_VASUL);
        assertThat(testKamalSociety.getBankVasulMr()).isEqualTo(UPDATED_BANK_VASUL_MR);
        assertThat(testKamalSociety.getBankVasulPer()).isEqualTo(UPDATED_BANK_VASUL_PER);
        assertThat(testKamalSociety.getBankVasulPerMr()).isEqualTo(UPDATED_BANK_VASUL_PER_MR);
        assertThat(testKamalSociety.getShareCapital()).isEqualTo(UPDATED_SHARE_CAPITAL);
        assertThat(testKamalSociety.getShareCapitalMr()).isEqualTo(UPDATED_SHARE_CAPITAL_MR);
        assertThat(testKamalSociety.getShare()).isEqualTo(UPDATED_SHARE);
        assertThat(testKamalSociety.getShareMr()).isEqualTo(UPDATED_SHARE_MR);
        assertThat(testKamalSociety.getFunds()).isEqualTo(UPDATED_FUNDS);
        assertThat(testKamalSociety.getFundsMr()).isEqualTo(UPDATED_FUNDS_MR);
        assertThat(testKamalSociety.getDeposit()).isEqualTo(UPDATED_DEPOSIT);
        assertThat(testKamalSociety.getDepositMr()).isEqualTo(UPDATED_DEPOSIT_MR);
        assertThat(testKamalSociety.getPayable()).isEqualTo(UPDATED_PAYABLE);
        assertThat(testKamalSociety.getPayableMr()).isEqualTo(UPDATED_PAYABLE_MR);
        assertThat(testKamalSociety.getProfit()).isEqualTo(UPDATED_PROFIT);
        assertThat(testKamalSociety.getProfitMr()).isEqualTo(UPDATED_PROFIT_MR);
        assertThat(testKamalSociety.getCashInHand()).isEqualTo(UPDATED_CASH_IN_HAND);
        assertThat(testKamalSociety.getCashInHandMr()).isEqualTo(UPDATED_CASH_IN_HAND_MR);
        assertThat(testKamalSociety.getInvestment()).isEqualTo(UPDATED_INVESTMENT);
        assertThat(testKamalSociety.getInvestmentMr()).isEqualTo(UPDATED_INVESTMENT_MR);
        assertThat(testKamalSociety.getDeadStock()).isEqualTo(UPDATED_DEAD_STOCK);
        assertThat(testKamalSociety.getDeadStockMr()).isEqualTo(UPDATED_DEAD_STOCK_MR);
        assertThat(testKamalSociety.getOtherPay()).isEqualTo(UPDATED_OTHER_PAY);
        assertThat(testKamalSociety.getOtherPayMr()).isEqualTo(UPDATED_OTHER_PAY_MR);
        assertThat(testKamalSociety.getLoss()).isEqualTo(UPDATED_LOSS);
        assertThat(testKamalSociety.getLossMr()).isEqualTo(UPDATED_LOSS_MR);
        assertThat(testKamalSociety.getTotalBagayat()).isEqualTo(UPDATED_TOTAL_BAGAYAT);
        assertThat(testKamalSociety.getTotalBagayatMr()).isEqualTo(UPDATED_TOTAL_BAGAYAT_MR);
        assertThat(testKamalSociety.getTotalJirayat()).isEqualTo(UPDATED_TOTAL_JIRAYAT);
        assertThat(testKamalSociety.getTotalJirayatMr()).isEqualTo(UPDATED_TOTAL_JIRAYAT_MR);
    }

    @Test
    @Transactional
    void putNonExistingKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kamalSociety.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalSociety)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKamalSocietyWithPatch() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();

        // Update the kamalSociety using partial update
        KamalSociety partialUpdatedKamalSociety = new KamalSociety();
        partialUpdatedKamalSociety.setId(kamalSociety.getId());

        partialUpdatedKamalSociety
            .zindagiDate(UPDATED_ZINDAGI_DATE)
            .village1(UPDATED_VILLAGE_1)
            .village2Mr(UPDATED_VILLAGE_2_MR)
            .totalLandMr(UPDATED_TOTAL_LAND_MR)
            .totalGMem(UPDATED_TOTAL_G_MEM)
            .memLoanMr(UPDATED_MEM_LOAN_MR)
            .memDue(UPDATED_MEM_DUE)
            .memDueMr(UPDATED_MEM_DUE_MR)
            .memDueper(UPDATED_MEM_DUEPER)
            .memVasulPer(UPDATED_MEM_VASUL_PER)
            .memVasulPerMr(UPDATED_MEM_VASUL_PER_MR)
            .bankDue(UPDATED_BANK_DUE)
            .bankDueper(UPDATED_BANK_DUEPER)
            .bankDueperMr(UPDATED_BANK_DUEPER_MR)
            .bankVasulpatra(UPDATED_BANK_VASULPATRA)
            .bankVasulpatraMr(UPDATED_BANK_VASULPATRA_MR)
            .bankVasul(UPDATED_BANK_VASUL)
            .shareCapitalMr(UPDATED_SHARE_CAPITAL_MR)
            .share(UPDATED_SHARE)
            .shareMr(UPDATED_SHARE_MR)
            .depositMr(UPDATED_DEPOSIT_MR)
            .payable(UPDATED_PAYABLE)
            .payableMr(UPDATED_PAYABLE_MR)
            .profit(UPDATED_PROFIT)
            .cashInHand(UPDATED_CASH_IN_HAND)
            .investment(UPDATED_INVESTMENT)
            .deadStock(UPDATED_DEAD_STOCK)
            .deadStockMr(UPDATED_DEAD_STOCK_MR)
            .otherPay(UPDATED_OTHER_PAY)
            .otherPayMr(UPDATED_OTHER_PAY_MR)
            .loss(UPDATED_LOSS)
            .totalJirayatMr(UPDATED_TOTAL_JIRAYAT_MR);

        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalSociety))
            )
            .andExpect(status().isOk());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
        KamalSociety testKamalSociety = kamalSocietyList.get(kamalSocietyList.size() - 1);
        assertThat(testKamalSociety.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKamalSociety.getZindagiDate()).isEqualTo(UPDATED_ZINDAGI_DATE);
        assertThat(testKamalSociety.getZindagiDateMr()).isEqualTo(DEFAULT_ZINDAGI_DATE_MR);
        assertThat(testKamalSociety.getVillage1()).isEqualTo(UPDATED_VILLAGE_1);
        assertThat(testKamalSociety.getVillage1Mr()).isEqualTo(DEFAULT_VILLAGE_1_MR);
        assertThat(testKamalSociety.getVillage2()).isEqualTo(DEFAULT_VILLAGE_2);
        assertThat(testKamalSociety.getVillage2Mr()).isEqualTo(UPDATED_VILLAGE_2_MR);
        assertThat(testKamalSociety.getVillage3()).isEqualTo(DEFAULT_VILLAGE_3);
        assertThat(testKamalSociety.getVillage3Mr()).isEqualTo(DEFAULT_VILLAGE_3_MR);
        assertThat(testKamalSociety.getTotalLand()).isEqualTo(DEFAULT_TOTAL_LAND);
        assertThat(testKamalSociety.getTotalLandMr()).isEqualTo(UPDATED_TOTAL_LAND_MR);
        assertThat(testKamalSociety.getTotalMem()).isEqualTo(DEFAULT_TOTAL_MEM);
        assertThat(testKamalSociety.getTotalMemMr()).isEqualTo(DEFAULT_TOTAL_MEM_MR);
        assertThat(testKamalSociety.getTotalNonMem()).isEqualTo(DEFAULT_TOTAL_NON_MEM);
        assertThat(testKamalSociety.getTotalNonMemMr()).isEqualTo(DEFAULT_TOTAL_NON_MEM_MR);
        assertThat(testKamalSociety.getTotalGMem()).isEqualTo(UPDATED_TOTAL_G_MEM);
        assertThat(testKamalSociety.getTotalGMemMr()).isEqualTo(DEFAULT_TOTAL_G_MEM_MR);
        assertThat(testKamalSociety.getMemLoan()).isEqualTo(DEFAULT_MEM_LOAN);
        assertThat(testKamalSociety.getMemLoanMr()).isEqualTo(UPDATED_MEM_LOAN_MR);
        assertThat(testKamalSociety.getMemDue()).isEqualTo(UPDATED_MEM_DUE);
        assertThat(testKamalSociety.getMemDueMr()).isEqualTo(UPDATED_MEM_DUE_MR);
        assertThat(testKamalSociety.getMemDueper()).isEqualTo(UPDATED_MEM_DUEPER);
        assertThat(testKamalSociety.getMemDueperMr()).isEqualTo(DEFAULT_MEM_DUEPER_MR);
        assertThat(testKamalSociety.getMemVasulpatra()).isEqualTo(DEFAULT_MEM_VASULPATRA);
        assertThat(testKamalSociety.getMemVasulpatraMr()).isEqualTo(DEFAULT_MEM_VASULPATRA_MR);
        assertThat(testKamalSociety.getMemVasul()).isEqualTo(DEFAULT_MEM_VASUL);
        assertThat(testKamalSociety.getMemVasulMr()).isEqualTo(DEFAULT_MEM_VASUL_MR);
        assertThat(testKamalSociety.getMemVasulPer()).isEqualTo(UPDATED_MEM_VASUL_PER);
        assertThat(testKamalSociety.getMemVasulPerMr()).isEqualTo(UPDATED_MEM_VASUL_PER_MR);
        assertThat(testKamalSociety.getBankLoan()).isEqualTo(DEFAULT_BANK_LOAN);
        assertThat(testKamalSociety.getBankLoanMr()).isEqualTo(DEFAULT_BANK_LOAN_MR);
        assertThat(testKamalSociety.getBankDue()).isEqualTo(UPDATED_BANK_DUE);
        assertThat(testKamalSociety.getBankDueMr()).isEqualTo(DEFAULT_BANK_DUE_MR);
        assertThat(testKamalSociety.getBankDueper()).isEqualTo(UPDATED_BANK_DUEPER);
        assertThat(testKamalSociety.getBankDueperMr()).isEqualTo(UPDATED_BANK_DUEPER_MR);
        assertThat(testKamalSociety.getBankVasulpatra()).isEqualTo(UPDATED_BANK_VASULPATRA);
        assertThat(testKamalSociety.getBankVasulpatraMr()).isEqualTo(UPDATED_BANK_VASULPATRA_MR);
        assertThat(testKamalSociety.getBankVasul()).isEqualTo(UPDATED_BANK_VASUL);
        assertThat(testKamalSociety.getBankVasulMr()).isEqualTo(DEFAULT_BANK_VASUL_MR);
        assertThat(testKamalSociety.getBankVasulPer()).isEqualTo(DEFAULT_BANK_VASUL_PER);
        assertThat(testKamalSociety.getBankVasulPerMr()).isEqualTo(DEFAULT_BANK_VASUL_PER_MR);
        assertThat(testKamalSociety.getShareCapital()).isEqualTo(DEFAULT_SHARE_CAPITAL);
        assertThat(testKamalSociety.getShareCapitalMr()).isEqualTo(UPDATED_SHARE_CAPITAL_MR);
        assertThat(testKamalSociety.getShare()).isEqualTo(UPDATED_SHARE);
        assertThat(testKamalSociety.getShareMr()).isEqualTo(UPDATED_SHARE_MR);
        assertThat(testKamalSociety.getFunds()).isEqualTo(DEFAULT_FUNDS);
        assertThat(testKamalSociety.getFundsMr()).isEqualTo(DEFAULT_FUNDS_MR);
        assertThat(testKamalSociety.getDeposit()).isEqualTo(DEFAULT_DEPOSIT);
        assertThat(testKamalSociety.getDepositMr()).isEqualTo(UPDATED_DEPOSIT_MR);
        assertThat(testKamalSociety.getPayable()).isEqualTo(UPDATED_PAYABLE);
        assertThat(testKamalSociety.getPayableMr()).isEqualTo(UPDATED_PAYABLE_MR);
        assertThat(testKamalSociety.getProfit()).isEqualTo(UPDATED_PROFIT);
        assertThat(testKamalSociety.getProfitMr()).isEqualTo(DEFAULT_PROFIT_MR);
        assertThat(testKamalSociety.getCashInHand()).isEqualTo(UPDATED_CASH_IN_HAND);
        assertThat(testKamalSociety.getCashInHandMr()).isEqualTo(DEFAULT_CASH_IN_HAND_MR);
        assertThat(testKamalSociety.getInvestment()).isEqualTo(UPDATED_INVESTMENT);
        assertThat(testKamalSociety.getInvestmentMr()).isEqualTo(DEFAULT_INVESTMENT_MR);
        assertThat(testKamalSociety.getDeadStock()).isEqualTo(UPDATED_DEAD_STOCK);
        assertThat(testKamalSociety.getDeadStockMr()).isEqualTo(UPDATED_DEAD_STOCK_MR);
        assertThat(testKamalSociety.getOtherPay()).isEqualTo(UPDATED_OTHER_PAY);
        assertThat(testKamalSociety.getOtherPayMr()).isEqualTo(UPDATED_OTHER_PAY_MR);
        assertThat(testKamalSociety.getLoss()).isEqualTo(UPDATED_LOSS);
        assertThat(testKamalSociety.getLossMr()).isEqualTo(DEFAULT_LOSS_MR);
        assertThat(testKamalSociety.getTotalBagayat()).isEqualTo(DEFAULT_TOTAL_BAGAYAT);
        assertThat(testKamalSociety.getTotalBagayatMr()).isEqualTo(DEFAULT_TOTAL_BAGAYAT_MR);
        assertThat(testKamalSociety.getTotalJirayat()).isEqualTo(DEFAULT_TOTAL_JIRAYAT);
        assertThat(testKamalSociety.getTotalJirayatMr()).isEqualTo(UPDATED_TOTAL_JIRAYAT_MR);
    }

    @Test
    @Transactional
    void fullUpdateKamalSocietyWithPatch() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();

        // Update the kamalSociety using partial update
        KamalSociety partialUpdatedKamalSociety = new KamalSociety();
        partialUpdatedKamalSociety.setId(kamalSociety.getId());

        partialUpdatedKamalSociety
            .pacsNumber(UPDATED_PACS_NUMBER)
            .zindagiDate(UPDATED_ZINDAGI_DATE)
            .zindagiDateMr(UPDATED_ZINDAGI_DATE_MR)
            .village1(UPDATED_VILLAGE_1)
            .village1Mr(UPDATED_VILLAGE_1_MR)
            .village2(UPDATED_VILLAGE_2)
            .village2Mr(UPDATED_VILLAGE_2_MR)
            .village3(UPDATED_VILLAGE_3)
            .village3Mr(UPDATED_VILLAGE_3_MR)
            .totalLand(UPDATED_TOTAL_LAND)
            .totalLandMr(UPDATED_TOTAL_LAND_MR)
            .totalMem(UPDATED_TOTAL_MEM)
            .totalMemMr(UPDATED_TOTAL_MEM_MR)
            .totalNonMem(UPDATED_TOTAL_NON_MEM)
            .totalNonMemMr(UPDATED_TOTAL_NON_MEM_MR)
            .totalGMem(UPDATED_TOTAL_G_MEM)
            .totalGMemMr(UPDATED_TOTAL_G_MEM_MR)
            .memLoan(UPDATED_MEM_LOAN)
            .memLoanMr(UPDATED_MEM_LOAN_MR)
            .memDue(UPDATED_MEM_DUE)
            .memDueMr(UPDATED_MEM_DUE_MR)
            .memDueper(UPDATED_MEM_DUEPER)
            .memDueperMr(UPDATED_MEM_DUEPER_MR)
            .memVasulpatra(UPDATED_MEM_VASULPATRA)
            .memVasulpatraMr(UPDATED_MEM_VASULPATRA_MR)
            .memVasul(UPDATED_MEM_VASUL)
            .memVasulMr(UPDATED_MEM_VASUL_MR)
            .memVasulPer(UPDATED_MEM_VASUL_PER)
            .memVasulPerMr(UPDATED_MEM_VASUL_PER_MR)
            .bankLoan(UPDATED_BANK_LOAN)
            .bankLoanMr(UPDATED_BANK_LOAN_MR)
            .bankDue(UPDATED_BANK_DUE)
            .bankDueMr(UPDATED_BANK_DUE_MR)
            .bankDueper(UPDATED_BANK_DUEPER)
            .bankDueperMr(UPDATED_BANK_DUEPER_MR)
            .bankVasulpatra(UPDATED_BANK_VASULPATRA)
            .bankVasulpatraMr(UPDATED_BANK_VASULPATRA_MR)
            .bankVasul(UPDATED_BANK_VASUL)
            .bankVasulMr(UPDATED_BANK_VASUL_MR)
            .bankVasulPer(UPDATED_BANK_VASUL_PER)
            .bankVasulPerMr(UPDATED_BANK_VASUL_PER_MR)
            .shareCapital(UPDATED_SHARE_CAPITAL)
            .shareCapitalMr(UPDATED_SHARE_CAPITAL_MR)
            .share(UPDATED_SHARE)
            .shareMr(UPDATED_SHARE_MR)
            .funds(UPDATED_FUNDS)
            .fundsMr(UPDATED_FUNDS_MR)
            .deposit(UPDATED_DEPOSIT)
            .depositMr(UPDATED_DEPOSIT_MR)
            .payable(UPDATED_PAYABLE)
            .payableMr(UPDATED_PAYABLE_MR)
            .profit(UPDATED_PROFIT)
            .profitMr(UPDATED_PROFIT_MR)
            .cashInHand(UPDATED_CASH_IN_HAND)
            .cashInHandMr(UPDATED_CASH_IN_HAND_MR)
            .investment(UPDATED_INVESTMENT)
            .investmentMr(UPDATED_INVESTMENT_MR)
            .deadStock(UPDATED_DEAD_STOCK)
            .deadStockMr(UPDATED_DEAD_STOCK_MR)
            .otherPay(UPDATED_OTHER_PAY)
            .otherPayMr(UPDATED_OTHER_PAY_MR)
            .loss(UPDATED_LOSS)
            .lossMr(UPDATED_LOSS_MR)
            .totalBagayat(UPDATED_TOTAL_BAGAYAT)
            .totalBagayatMr(UPDATED_TOTAL_BAGAYAT_MR)
            .totalJirayat(UPDATED_TOTAL_JIRAYAT)
            .totalJirayatMr(UPDATED_TOTAL_JIRAYAT_MR);

        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKamalSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKamalSociety))
            )
            .andExpect(status().isOk());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
        KamalSociety testKamalSociety = kamalSocietyList.get(kamalSocietyList.size() - 1);
        assertThat(testKamalSociety.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalSociety.getZindagiDate()).isEqualTo(UPDATED_ZINDAGI_DATE);
        assertThat(testKamalSociety.getZindagiDateMr()).isEqualTo(UPDATED_ZINDAGI_DATE_MR);
        assertThat(testKamalSociety.getVillage1()).isEqualTo(UPDATED_VILLAGE_1);
        assertThat(testKamalSociety.getVillage1Mr()).isEqualTo(UPDATED_VILLAGE_1_MR);
        assertThat(testKamalSociety.getVillage2()).isEqualTo(UPDATED_VILLAGE_2);
        assertThat(testKamalSociety.getVillage2Mr()).isEqualTo(UPDATED_VILLAGE_2_MR);
        assertThat(testKamalSociety.getVillage3()).isEqualTo(UPDATED_VILLAGE_3);
        assertThat(testKamalSociety.getVillage3Mr()).isEqualTo(UPDATED_VILLAGE_3_MR);
        assertThat(testKamalSociety.getTotalLand()).isEqualTo(UPDATED_TOTAL_LAND);
        assertThat(testKamalSociety.getTotalLandMr()).isEqualTo(UPDATED_TOTAL_LAND_MR);
        assertThat(testKamalSociety.getTotalMem()).isEqualTo(UPDATED_TOTAL_MEM);
        assertThat(testKamalSociety.getTotalMemMr()).isEqualTo(UPDATED_TOTAL_MEM_MR);
        assertThat(testKamalSociety.getTotalNonMem()).isEqualTo(UPDATED_TOTAL_NON_MEM);
        assertThat(testKamalSociety.getTotalNonMemMr()).isEqualTo(UPDATED_TOTAL_NON_MEM_MR);
        assertThat(testKamalSociety.getTotalGMem()).isEqualTo(UPDATED_TOTAL_G_MEM);
        assertThat(testKamalSociety.getTotalGMemMr()).isEqualTo(UPDATED_TOTAL_G_MEM_MR);
        assertThat(testKamalSociety.getMemLoan()).isEqualTo(UPDATED_MEM_LOAN);
        assertThat(testKamalSociety.getMemLoanMr()).isEqualTo(UPDATED_MEM_LOAN_MR);
        assertThat(testKamalSociety.getMemDue()).isEqualTo(UPDATED_MEM_DUE);
        assertThat(testKamalSociety.getMemDueMr()).isEqualTo(UPDATED_MEM_DUE_MR);
        assertThat(testKamalSociety.getMemDueper()).isEqualTo(UPDATED_MEM_DUEPER);
        assertThat(testKamalSociety.getMemDueperMr()).isEqualTo(UPDATED_MEM_DUEPER_MR);
        assertThat(testKamalSociety.getMemVasulpatra()).isEqualTo(UPDATED_MEM_VASULPATRA);
        assertThat(testKamalSociety.getMemVasulpatraMr()).isEqualTo(UPDATED_MEM_VASULPATRA_MR);
        assertThat(testKamalSociety.getMemVasul()).isEqualTo(UPDATED_MEM_VASUL);
        assertThat(testKamalSociety.getMemVasulMr()).isEqualTo(UPDATED_MEM_VASUL_MR);
        assertThat(testKamalSociety.getMemVasulPer()).isEqualTo(UPDATED_MEM_VASUL_PER);
        assertThat(testKamalSociety.getMemVasulPerMr()).isEqualTo(UPDATED_MEM_VASUL_PER_MR);
        assertThat(testKamalSociety.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testKamalSociety.getBankLoanMr()).isEqualTo(UPDATED_BANK_LOAN_MR);
        assertThat(testKamalSociety.getBankDue()).isEqualTo(UPDATED_BANK_DUE);
        assertThat(testKamalSociety.getBankDueMr()).isEqualTo(UPDATED_BANK_DUE_MR);
        assertThat(testKamalSociety.getBankDueper()).isEqualTo(UPDATED_BANK_DUEPER);
        assertThat(testKamalSociety.getBankDueperMr()).isEqualTo(UPDATED_BANK_DUEPER_MR);
        assertThat(testKamalSociety.getBankVasulpatra()).isEqualTo(UPDATED_BANK_VASULPATRA);
        assertThat(testKamalSociety.getBankVasulpatraMr()).isEqualTo(UPDATED_BANK_VASULPATRA_MR);
        assertThat(testKamalSociety.getBankVasul()).isEqualTo(UPDATED_BANK_VASUL);
        assertThat(testKamalSociety.getBankVasulMr()).isEqualTo(UPDATED_BANK_VASUL_MR);
        assertThat(testKamalSociety.getBankVasulPer()).isEqualTo(UPDATED_BANK_VASUL_PER);
        assertThat(testKamalSociety.getBankVasulPerMr()).isEqualTo(UPDATED_BANK_VASUL_PER_MR);
        assertThat(testKamalSociety.getShareCapital()).isEqualTo(UPDATED_SHARE_CAPITAL);
        assertThat(testKamalSociety.getShareCapitalMr()).isEqualTo(UPDATED_SHARE_CAPITAL_MR);
        assertThat(testKamalSociety.getShare()).isEqualTo(UPDATED_SHARE);
        assertThat(testKamalSociety.getShareMr()).isEqualTo(UPDATED_SHARE_MR);
        assertThat(testKamalSociety.getFunds()).isEqualTo(UPDATED_FUNDS);
        assertThat(testKamalSociety.getFundsMr()).isEqualTo(UPDATED_FUNDS_MR);
        assertThat(testKamalSociety.getDeposit()).isEqualTo(UPDATED_DEPOSIT);
        assertThat(testKamalSociety.getDepositMr()).isEqualTo(UPDATED_DEPOSIT_MR);
        assertThat(testKamalSociety.getPayable()).isEqualTo(UPDATED_PAYABLE);
        assertThat(testKamalSociety.getPayableMr()).isEqualTo(UPDATED_PAYABLE_MR);
        assertThat(testKamalSociety.getProfit()).isEqualTo(UPDATED_PROFIT);
        assertThat(testKamalSociety.getProfitMr()).isEqualTo(UPDATED_PROFIT_MR);
        assertThat(testKamalSociety.getCashInHand()).isEqualTo(UPDATED_CASH_IN_HAND);
        assertThat(testKamalSociety.getCashInHandMr()).isEqualTo(UPDATED_CASH_IN_HAND_MR);
        assertThat(testKamalSociety.getInvestment()).isEqualTo(UPDATED_INVESTMENT);
        assertThat(testKamalSociety.getInvestmentMr()).isEqualTo(UPDATED_INVESTMENT_MR);
        assertThat(testKamalSociety.getDeadStock()).isEqualTo(UPDATED_DEAD_STOCK);
        assertThat(testKamalSociety.getDeadStockMr()).isEqualTo(UPDATED_DEAD_STOCK_MR);
        assertThat(testKamalSociety.getOtherPay()).isEqualTo(UPDATED_OTHER_PAY);
        assertThat(testKamalSociety.getOtherPayMr()).isEqualTo(UPDATED_OTHER_PAY_MR);
        assertThat(testKamalSociety.getLoss()).isEqualTo(UPDATED_LOSS);
        assertThat(testKamalSociety.getLossMr()).isEqualTo(UPDATED_LOSS_MR);
        assertThat(testKamalSociety.getTotalBagayat()).isEqualTo(UPDATED_TOTAL_BAGAYAT);
        assertThat(testKamalSociety.getTotalBagayatMr()).isEqualTo(UPDATED_TOTAL_BAGAYAT_MR);
        assertThat(testKamalSociety.getTotalJirayat()).isEqualTo(UPDATED_TOTAL_JIRAYAT);
        assertThat(testKamalSociety.getTotalJirayatMr()).isEqualTo(UPDATED_TOTAL_JIRAYAT_MR);
    }

    @Test
    @Transactional
    void patchNonExistingKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kamalSociety.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isBadRequest());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKamalSociety() throws Exception {
        int databaseSizeBeforeUpdate = kamalSocietyRepository.findAll().size();
        kamalSociety.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKamalSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kamalSociety))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KamalSociety in the database
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKamalSociety() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        int databaseSizeBeforeDelete = kamalSocietyRepository.findAll().size();

        // Delete the kamalSociety
        restKamalSocietyMockMvc
            .perform(delete(ENTITY_API_URL_ID, kamalSociety.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
