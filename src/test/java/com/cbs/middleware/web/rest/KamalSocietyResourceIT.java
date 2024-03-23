package com.cbs.middleware.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbs.middleware.IntegrationTest;
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

    private static final String DEFAULT_FINANCIAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_YEAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_KM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_KM_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_KM_DATE_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_KM_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_KM_FROM_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_KM_FROM_DATE_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_KM_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_KM_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_KM_TO_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_KM_TO_DATE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PACS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACS_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_BRANCH_ID = 1L;
    private static final Long UPDATED_BRANCH_ID = 2L;
    private static final Long SMALLER_BRANCH_ID = 1L - 1L;

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_TALUKA_ID = 1L;
    private static final Long UPDATED_TALUKA_ID = 2L;
    private static final Long SMALLER_TALUKA_ID = 1L - 1L;

    private static final String DEFAULT_TALUKA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TALUKA_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_ZINDAGI_PATRAK_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ZINDAGI_PATRAK_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ZINDAGI_PATRAK_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_ZINDAGI_PATRAK_DATE_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_BANK_TAPASANI_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BANK_TAPASANI_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BANK_TAPASANI_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_BANK_TAPASANI_DATE_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_GOV_TAPASANI_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GOV_TAPASANI_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GOV_TAPASANI_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_GOV_TAPASANI_DATE_MR = "BBBBBBBBBB";

    private static final Instant DEFAULT_SANSTHA_TAPASANI_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SANSTHA_TAPASANI_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SANSTHA_TAPASANI_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_SANSTHA_TAPASANI_DATE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_LAND = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_LAND = "BBBBBBBBBB";

    private static final String DEFAULT_BAGAYAT = "AAAAAAAAAA";
    private static final String UPDATED_BAGAYAT = "BBBBBBBBBB";

    private static final String DEFAULT_JIRAYAT = "AAAAAAAAAA";
    private static final String UPDATED_JIRAYAT = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_FARMER = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_FARMER = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_FARMER = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_FARMER = "BBBBBBBBBB";

    private static final String DEFAULT_NON_MEMBER_FARMER = "AAAAAAAAAA";
    private static final String UPDATED_NON_MEMBER_FARMER = "BBBBBBBBBB";

    private static final Instant DEFAULT_TALEBAND_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TALEBAND_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MEM_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_MEM_LOAN = "BBBBBBBBBB";

    private static final String DEFAULT_MEM_DUE = "AAAAAAAAAA";
    private static final String UPDATED_MEM_DUE = "BBBBBBBBBB";

    private static final String DEFAULT_MEM_VASULI = "AAAAAAAAAA";
    private static final String UPDATED_MEM_VASULI = "BBBBBBBBBB";

    private static final String DEFAULT_MEM_VASULI_PER = "AAAAAAAAAA";
    private static final String UPDATED_MEM_VASULI_PER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_BANK_LOAN = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_DUE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_DUE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_VASULI = "AAAAAAAAAA";
    private static final String UPDATED_BANK_VASULI = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_VASULI_PER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_VASULI_PER = "BBBBBBBBBB";

    private static final Instant DEFAULT_BALANCE_SHEET_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BALANCE_SHEET_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BALANCE_SHEET_DATE_MR = "AAAAAAAAAA";
    private static final String UPDATED_BALANCE_SHEET_DATE_MR = "BBBBBBBBBB";

    private static final String DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL = "AAAAAAAAAA";
    private static final String UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL = "BBBBBBBBBB";

    private static final String DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL = "AAAAAAAAAA";
    private static final String UPDATED_LIABILITY_VASUL_SHARE_CAPITAL = "BBBBBBBBBB";

    private static final String DEFAULT_LIABILITY_FUND = "AAAAAAAAAA";
    private static final String UPDATED_LIABILITY_FUND = "BBBBBBBBBB";

    private static final String DEFAULT_LIABILITY_SPARE_FUND = "AAAAAAAAAA";
    private static final String UPDATED_LIABILITY_SPARE_FUND = "BBBBBBBBBB";

    private static final String DEFAULT_LIABILITY_DEPOSITE = "AAAAAAAAAA";
    private static final String UPDATED_LIABILITY_DEPOSITE = "BBBBBBBBBB";

    private static final String DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN = "BBBBBBBBBB";

    private static final String DEFAULT_LIABILITY_OTHER_PAYABLE = "AAAAAAAAAA";
    private static final String UPDATED_LIABILITY_OTHER_PAYABLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIABILITY_PROFIT = "AAAAAAAAAA";
    private static final String UPDATED_LIABILITY_PROFIT = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_CASH = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_CASH = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_INVESTMENT = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_INVESTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_IMARAT_FUND = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_IMARAT_FUND = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_MEMBER_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_MEMBER_LOAN = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_DEAD_STOCK = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_DEAD_STOCK = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_OTHER_RECEIVABLE = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_OTHER_RECEIVABLE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_LOSS = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_LOSS = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_LIABILITY = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_LIABILITY = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_ASSET = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_ASSET = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PACS_VERIFIED_FLAG = false;
    private static final Boolean UPDATED_PACS_VERIFIED_FLAG = true;

    private static final Boolean DEFAULT_BRANCH_VERIFIED_FLAG = false;
    private static final Boolean UPDATED_BRANCH_VERIFIED_FLAG = true;

    private static final Boolean DEFAULT_HEAD_OFFICE_VERIFIED_FLAG = false;
    private static final Boolean UPDATED_HEAD_OFFICE_VERIFIED_FLAG = true;

    private static final Boolean DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG = false;
    private static final Boolean UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG = true;

    private static final Boolean DEFAULT_IS_SUPPLIMENTERY_FLAG = false;
    private static final Boolean UPDATED_IS_SUPPLIMENTERY_FLAG = true;

    private static final String DEFAULT_SANSTHA_TAPASANI_VARG = "AAAAAAAAAA";
    private static final String UPDATED_SANSTHA_TAPASANI_VARG = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_VERIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_VERIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_BRANCH_VERIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BRANCH_VERIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_HEAD_OFFICE_VERIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_OFFICE_VERIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_HEAD_OFFICE_VERIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEAD_OFFICE_VERIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DOSH_PURTATA_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOSH_PURTATA_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GAMBHIR_DOSH = "AAAAAAAAAA";
    private static final String UPDATED_GAMBHIR_DOSH = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_INWARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_INWARD_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_BRANCH_INWARD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BRANCH_INWARD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BRANCH_OUTWARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_OUTWARD_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_BRANCH_OUTWARD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BRANCH_OUTWARD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_HEAD_OFFICE_INWARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_OFFICE_INWARD_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HEAD_OFFICE_INWARD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEAD_OFFICE_INWARD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_OFFICE_OUTWARD_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HEAD_OFFICE_OUTWARD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEAD_OFFICE_OUTWARD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_THARAV_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_THARAV_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_THARAV_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THARAV_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
            .financialYear(DEFAULT_FINANCIAL_YEAR)
            .kmDate(DEFAULT_KM_DATE)
            .kmDateMr(DEFAULT_KM_DATE_MR)
            .kmFromDate(DEFAULT_KM_FROM_DATE)
            .kmFromDateMr(DEFAULT_KM_FROM_DATE_MR)
            .kmToDate(DEFAULT_KM_TO_DATE)
            .kmToDateMr(DEFAULT_KM_TO_DATE_MR)
            .pacsNumber(DEFAULT_PACS_NUMBER)
            .pacsName(DEFAULT_PACS_NAME)
            .branchId(DEFAULT_BRANCH_ID)
            .branchName(DEFAULT_BRANCH_NAME)
            .talukaId(DEFAULT_TALUKA_ID)
            .talukaName(DEFAULT_TALUKA_NAME)
            .zindagiPatrakDate(DEFAULT_ZINDAGI_PATRAK_DATE)
            .zindagiPatrakDateMr(DEFAULT_ZINDAGI_PATRAK_DATE_MR)
            .bankTapasaniDate(DEFAULT_BANK_TAPASANI_DATE)
            .bankTapasaniDateMr(DEFAULT_BANK_TAPASANI_DATE_MR)
            .govTapasaniDate(DEFAULT_GOV_TAPASANI_DATE)
            .govTapasaniDateMr(DEFAULT_GOV_TAPASANI_DATE_MR)
            .sansthaTapasaniDate(DEFAULT_SANSTHA_TAPASANI_DATE)
            .sansthaTapasaniDateMr(DEFAULT_SANSTHA_TAPASANI_DATE_MR)
            .totalLand(DEFAULT_TOTAL_LAND)
            .bagayat(DEFAULT_BAGAYAT)
            .jirayat(DEFAULT_JIRAYAT)
            .totalFarmer(DEFAULT_TOTAL_FARMER)
            .memberFarmer(DEFAULT_MEMBER_FARMER)
            .nonMemberFarmer(DEFAULT_NON_MEMBER_FARMER)
            .talebandDate(DEFAULT_TALEBAND_DATE)
            .memLoan(DEFAULT_MEM_LOAN)
            .memDue(DEFAULT_MEM_DUE)
            .memVasuli(DEFAULT_MEM_VASULI)
            .memVasuliPer(DEFAULT_MEM_VASULI_PER)
            .bankLoan(DEFAULT_BANK_LOAN)
            .bankDue(DEFAULT_BANK_DUE)
            .bankVasuli(DEFAULT_BANK_VASULI)
            .bankVasuliPer(DEFAULT_BANK_VASULI_PER)
            .balanceSheetDate(DEFAULT_BALANCE_SHEET_DATE)
            .balanceSheetDateMr(DEFAULT_BALANCE_SHEET_DATE_MR)
            .liabilityAdhikrutShareCapital(DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL)
            .liabilityVasulShareCapital(DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL)
            .liabilityFund(DEFAULT_LIABILITY_FUND)
            .liabilitySpareFund(DEFAULT_LIABILITY_SPARE_FUND)
            .liabilityDeposite(DEFAULT_LIABILITY_DEPOSITE)
            .liabilityBalanceSheetBankLoan(DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN)
            .liabilityOtherPayable(DEFAULT_LIABILITY_OTHER_PAYABLE)
            .liabilityProfit(DEFAULT_LIABILITY_PROFIT)
            .assetCash(DEFAULT_ASSET_CASH)
            .assetInvestment(DEFAULT_ASSET_INVESTMENT)
            .assetImaratFund(DEFAULT_ASSET_IMARAT_FUND)
            .assetMemberLoan(DEFAULT_ASSET_MEMBER_LOAN)
            .assetDeadStock(DEFAULT_ASSET_DEAD_STOCK)
            .assetOtherReceivable(DEFAULT_ASSET_OTHER_RECEIVABLE)
            .assetLoss(DEFAULT_ASSET_LOSS)
            .totalLiability(DEFAULT_TOTAL_LIABILITY)
            .totalAsset(DEFAULT_TOTAL_ASSET)
            .villageCode(DEFAULT_VILLAGE_CODE)
            .pacsVerifiedFlag(DEFAULT_PACS_VERIFIED_FLAG)
            .branchVerifiedFlag(DEFAULT_BRANCH_VERIFIED_FLAG)
            .headOfficeVerifiedFlag(DEFAULT_HEAD_OFFICE_VERIFIED_FLAG)
            .divisionalOfficeVerifiedFlag(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG)
            .isSupplimenteryFlag(DEFAULT_IS_SUPPLIMENTERY_FLAG)
            .sansthaTapasaniVarg(DEFAULT_SANSTHA_TAPASANI_VARG)
            .branchVerifiedBy(DEFAULT_BRANCH_VERIFIED_BY)
            .branchVerifiedDate(DEFAULT_BRANCH_VERIFIED_DATE)
            .headOfficeVerifiedBy(DEFAULT_HEAD_OFFICE_VERIFIED_BY)
            .headOfficeVerifiedDate(DEFAULT_HEAD_OFFICE_VERIFIED_DATE)
            .divisionalOfficeVerifiedBy(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY)
            .divisionalOfficeVerifiedDate(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE)
            .doshPurtataDate(DEFAULT_DOSH_PURTATA_DATE)
            .gambhirDosh(DEFAULT_GAMBHIR_DOSH);
            //.branchInwardNumber(DEFAULT_BRANCH_INWARD_NUMBER)
            //.branchInwardDate(DEFAULT_BRANCH_INWARD_DATE)
            ///.branchOutwardNumber(DEFAULT_BRANCH_OUTWARD_NUMBER)
         /*   .branchOutwardDate(DEFAULT_BRANCH_OUTWARD_DATE)
            .headOfficeInwardNumber(DEFAULT_HEAD_OFFICE_INWARD_NUMBER)
            .headOfficeInwardDate(DEFAULT_HEAD_OFFICE_INWARD_DATE)
            .headOfficeOutwardNumber(DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER)
            .headOfficeOutwardDate(DEFAULT_HEAD_OFFICE_OUTWARD_DATE)
            .tharavNumber(DEFAULT_THARAV_NUMBER)
            .tharavDate(DEFAULT_THARAV_DATE);*/
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
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .kmDate(UPDATED_KM_DATE)
            .kmDateMr(UPDATED_KM_DATE_MR)
            .kmFromDate(UPDATED_KM_FROM_DATE)
            .kmFromDateMr(UPDATED_KM_FROM_DATE_MR)
            .kmToDate(UPDATED_KM_TO_DATE)
            .kmToDateMr(UPDATED_KM_TO_DATE_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .pacsName(UPDATED_PACS_NAME)
            .branchId(UPDATED_BRANCH_ID)
            .branchName(UPDATED_BRANCH_NAME)
            .talukaId(UPDATED_TALUKA_ID)
            .talukaName(UPDATED_TALUKA_NAME)
            .zindagiPatrakDate(UPDATED_ZINDAGI_PATRAK_DATE)
            .zindagiPatrakDateMr(UPDATED_ZINDAGI_PATRAK_DATE_MR)
            .bankTapasaniDate(UPDATED_BANK_TAPASANI_DATE)
            .bankTapasaniDateMr(UPDATED_BANK_TAPASANI_DATE_MR)
            .govTapasaniDate(UPDATED_GOV_TAPASANI_DATE)
            .govTapasaniDateMr(UPDATED_GOV_TAPASANI_DATE_MR)
            .sansthaTapasaniDate(UPDATED_SANSTHA_TAPASANI_DATE)
            .sansthaTapasaniDateMr(UPDATED_SANSTHA_TAPASANI_DATE_MR)
            .totalLand(UPDATED_TOTAL_LAND)
            .bagayat(UPDATED_BAGAYAT)
            .jirayat(UPDATED_JIRAYAT)
            .totalFarmer(UPDATED_TOTAL_FARMER)
            .memberFarmer(UPDATED_MEMBER_FARMER)
            .nonMemberFarmer(UPDATED_NON_MEMBER_FARMER)
            .talebandDate(UPDATED_TALEBAND_DATE)
            .memLoan(UPDATED_MEM_LOAN)
            .memDue(UPDATED_MEM_DUE)
            .memVasuli(UPDATED_MEM_VASULI)
            .memVasuliPer(UPDATED_MEM_VASULI_PER)
            .bankLoan(UPDATED_BANK_LOAN)
            .bankDue(UPDATED_BANK_DUE)
            .bankVasuli(UPDATED_BANK_VASULI)
            .bankVasuliPer(UPDATED_BANK_VASULI_PER)
            .balanceSheetDate(UPDATED_BALANCE_SHEET_DATE)
            .balanceSheetDateMr(UPDATED_BALANCE_SHEET_DATE_MR)
            .liabilityAdhikrutShareCapital(UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL)
            .liabilityVasulShareCapital(UPDATED_LIABILITY_VASUL_SHARE_CAPITAL)
            .liabilityFund(UPDATED_LIABILITY_FUND)
            .liabilitySpareFund(UPDATED_LIABILITY_SPARE_FUND)
            .liabilityDeposite(UPDATED_LIABILITY_DEPOSITE)
            .liabilityBalanceSheetBankLoan(UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN)
            .liabilityOtherPayable(UPDATED_LIABILITY_OTHER_PAYABLE)
            .liabilityProfit(UPDATED_LIABILITY_PROFIT)
            .assetCash(UPDATED_ASSET_CASH)
            .assetInvestment(UPDATED_ASSET_INVESTMENT)
            .assetImaratFund(UPDATED_ASSET_IMARAT_FUND)
            .assetMemberLoan(UPDATED_ASSET_MEMBER_LOAN)
            .assetDeadStock(UPDATED_ASSET_DEAD_STOCK)
            .assetOtherReceivable(UPDATED_ASSET_OTHER_RECEIVABLE)
            .assetLoss(UPDATED_ASSET_LOSS)
            .totalLiability(UPDATED_TOTAL_LIABILITY)
            .totalAsset(UPDATED_TOTAL_ASSET)
            .villageCode(UPDATED_VILLAGE_CODE)
            .pacsVerifiedFlag(UPDATED_PACS_VERIFIED_FLAG)
            .branchVerifiedFlag(UPDATED_BRANCH_VERIFIED_FLAG)
            .headOfficeVerifiedFlag(UPDATED_HEAD_OFFICE_VERIFIED_FLAG)
            .divisionalOfficeVerifiedFlag(UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG)
            .isSupplimenteryFlag(UPDATED_IS_SUPPLIMENTERY_FLAG)
            .sansthaTapasaniVarg(UPDATED_SANSTHA_TAPASANI_VARG)
            .branchVerifiedBy(UPDATED_BRANCH_VERIFIED_BY)
            .branchVerifiedDate(UPDATED_BRANCH_VERIFIED_DATE)
            .headOfficeVerifiedBy(UPDATED_HEAD_OFFICE_VERIFIED_BY)
            .headOfficeVerifiedDate(UPDATED_HEAD_OFFICE_VERIFIED_DATE)
            .divisionalOfficeVerifiedBy(UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY)
            .divisionalOfficeVerifiedDate(UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE)
            .doshPurtataDate(UPDATED_DOSH_PURTATA_DATE)
            .gambhirDosh(UPDATED_GAMBHIR_DOSH)
       /*     .branchInwardNumber(UPDATED_BRANCH_INWARD_NUMBER)
            .branchInwardDate(UPDATED_BRANCH_INWARD_DATE)
            .branchOutwardNumber(UPDATED_BRANCH_OUTWARD_NUMBER)
            .branchOutwardDate(UPDATED_BRANCH_OUTWARD_DATE)
            .headOfficeInwardNumber(UPDATED_HEAD_OFFICE_INWARD_NUMBER)
            .headOfficeInwardDate(UPDATED_HEAD_OFFICE_INWARD_DATE)
            .headOfficeOutwardNumber(UPDATED_HEAD_OFFICE_OUTWARD_NUMBER)
            .headOfficeOutwardDate(UPDATED_HEAD_OFFICE_OUTWARD_DATE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDate(UPDATED_THARAV_DATE)*/;
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
        assertThat(testKamalSociety.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testKamalSociety.getKmDate()).isEqualTo(DEFAULT_KM_DATE);
        assertThat(testKamalSociety.getKmDateMr()).isEqualTo(DEFAULT_KM_DATE_MR);
        assertThat(testKamalSociety.getKmFromDate()).isEqualTo(DEFAULT_KM_FROM_DATE);
        assertThat(testKamalSociety.getKmFromDateMr()).isEqualTo(DEFAULT_KM_FROM_DATE_MR);
        assertThat(testKamalSociety.getKmToDate()).isEqualTo(DEFAULT_KM_TO_DATE);
        assertThat(testKamalSociety.getKmToDateMr()).isEqualTo(DEFAULT_KM_TO_DATE_MR);
        assertThat(testKamalSociety.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKamalSociety.getPacsName()).isEqualTo(DEFAULT_PACS_NAME);
        assertThat(testKamalSociety.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testKamalSociety.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testKamalSociety.getTalukaId()).isEqualTo(DEFAULT_TALUKA_ID);
        assertThat(testKamalSociety.getTalukaName()).isEqualTo(DEFAULT_TALUKA_NAME);
        assertThat(testKamalSociety.getZindagiPatrakDate()).isEqualTo(DEFAULT_ZINDAGI_PATRAK_DATE);
        assertThat(testKamalSociety.getZindagiPatrakDateMr()).isEqualTo(DEFAULT_ZINDAGI_PATRAK_DATE_MR);
        assertThat(testKamalSociety.getBankTapasaniDate()).isEqualTo(DEFAULT_BANK_TAPASANI_DATE);
        assertThat(testKamalSociety.getBankTapasaniDateMr()).isEqualTo(DEFAULT_BANK_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getGovTapasaniDate()).isEqualTo(DEFAULT_GOV_TAPASANI_DATE);
        assertThat(testKamalSociety.getGovTapasaniDateMr()).isEqualTo(DEFAULT_GOV_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getSansthaTapasaniDate()).isEqualTo(DEFAULT_SANSTHA_TAPASANI_DATE);
        assertThat(testKamalSociety.getSansthaTapasaniDateMr()).isEqualTo(DEFAULT_SANSTHA_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getTotalLand()).isEqualTo(DEFAULT_TOTAL_LAND);
        assertThat(testKamalSociety.getBagayat()).isEqualTo(DEFAULT_BAGAYAT);
        assertThat(testKamalSociety.getJirayat()).isEqualTo(DEFAULT_JIRAYAT);
        assertThat(testKamalSociety.getTotalFarmer()).isEqualTo(DEFAULT_TOTAL_FARMER);
        assertThat(testKamalSociety.getMemberFarmer()).isEqualTo(DEFAULT_MEMBER_FARMER);
        assertThat(testKamalSociety.getNonMemberFarmer()).isEqualTo(DEFAULT_NON_MEMBER_FARMER);
        assertThat(testKamalSociety.getTalebandDate()).isEqualTo(DEFAULT_TALEBAND_DATE);
        assertThat(testKamalSociety.getMemLoan()).isEqualTo(DEFAULT_MEM_LOAN);
        assertThat(testKamalSociety.getMemDue()).isEqualTo(DEFAULT_MEM_DUE);
        assertThat(testKamalSociety.getMemVasuli()).isEqualTo(DEFAULT_MEM_VASULI);
        assertThat(testKamalSociety.getMemVasuliPer()).isEqualTo(DEFAULT_MEM_VASULI_PER);
        assertThat(testKamalSociety.getBankLoan()).isEqualTo(DEFAULT_BANK_LOAN);
        assertThat(testKamalSociety.getBankDue()).isEqualTo(DEFAULT_BANK_DUE);
        assertThat(testKamalSociety.getBankVasuli()).isEqualTo(DEFAULT_BANK_VASULI);
        assertThat(testKamalSociety.getBankVasuliPer()).isEqualTo(DEFAULT_BANK_VASULI_PER);
        assertThat(testKamalSociety.getBalanceSheetDate()).isEqualTo(DEFAULT_BALANCE_SHEET_DATE);
        assertThat(testKamalSociety.getBalanceSheetDateMr()).isEqualTo(DEFAULT_BALANCE_SHEET_DATE_MR);
        assertThat(testKamalSociety.getLiabilityAdhikrutShareCapital()).isEqualTo(DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL);
        assertThat(testKamalSociety.getLiabilityVasulShareCapital()).isEqualTo(DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL);
        assertThat(testKamalSociety.getLiabilityFund()).isEqualTo(DEFAULT_LIABILITY_FUND);
        assertThat(testKamalSociety.getLiabilitySpareFund()).isEqualTo(DEFAULT_LIABILITY_SPARE_FUND);
        assertThat(testKamalSociety.getLiabilityDeposite()).isEqualTo(DEFAULT_LIABILITY_DEPOSITE);
        assertThat(testKamalSociety.getLiabilityBalanceSheetBankLoan()).isEqualTo(DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN);
        assertThat(testKamalSociety.getLiabilityOtherPayable()).isEqualTo(DEFAULT_LIABILITY_OTHER_PAYABLE);
        assertThat(testKamalSociety.getLiabilityProfit()).isEqualTo(DEFAULT_LIABILITY_PROFIT);
        assertThat(testKamalSociety.getAssetCash()).isEqualTo(DEFAULT_ASSET_CASH);
        assertThat(testKamalSociety.getAssetInvestment()).isEqualTo(DEFAULT_ASSET_INVESTMENT);
        assertThat(testKamalSociety.getAssetImaratFund()).isEqualTo(DEFAULT_ASSET_IMARAT_FUND);
        assertThat(testKamalSociety.getAssetMemberLoan()).isEqualTo(DEFAULT_ASSET_MEMBER_LOAN);
        assertThat(testKamalSociety.getAssetDeadStock()).isEqualTo(DEFAULT_ASSET_DEAD_STOCK);
        assertThat(testKamalSociety.getAssetOtherReceivable()).isEqualTo(DEFAULT_ASSET_OTHER_RECEIVABLE);
        assertThat(testKamalSociety.getAssetLoss()).isEqualTo(DEFAULT_ASSET_LOSS);
        assertThat(testKamalSociety.getTotalLiability()).isEqualTo(DEFAULT_TOTAL_LIABILITY);
        assertThat(testKamalSociety.getTotalAsset()).isEqualTo(DEFAULT_TOTAL_ASSET);
        assertThat(testKamalSociety.getVillageCode()).isEqualTo(DEFAULT_VILLAGE_CODE);
        assertThat(testKamalSociety.getPacsVerifiedFlag()).isEqualTo(DEFAULT_PACS_VERIFIED_FLAG);
        assertThat(testKamalSociety.getBranchVerifiedFlag()).isEqualTo(DEFAULT_BRANCH_VERIFIED_FLAG);
        assertThat(testKamalSociety.getHeadOfficeVerifiedFlag()).isEqualTo(DEFAULT_HEAD_OFFICE_VERIFIED_FLAG);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedFlag()).isEqualTo(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG);
        assertThat(testKamalSociety.getIsSupplimenteryFlag()).isEqualTo(DEFAULT_IS_SUPPLIMENTERY_FLAG);
        assertThat(testKamalSociety.getSansthaTapasaniVarg()).isEqualTo(DEFAULT_SANSTHA_TAPASANI_VARG);
        assertThat(testKamalSociety.getBranchVerifiedBy()).isEqualTo(DEFAULT_BRANCH_VERIFIED_BY);
        assertThat(testKamalSociety.getBranchVerifiedDate()).isEqualTo(DEFAULT_BRANCH_VERIFIED_DATE);
        assertThat(testKamalSociety.getHeadOfficeVerifiedBy()).isEqualTo(DEFAULT_HEAD_OFFICE_VERIFIED_BY);
        assertThat(testKamalSociety.getHeadOfficeVerifiedDate()).isEqualTo(DEFAULT_HEAD_OFFICE_VERIFIED_DATE);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedBy()).isEqualTo(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedDate()).isEqualTo(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE);
        assertThat(testKamalSociety.getDoshPurtataDate()).isEqualTo(DEFAULT_DOSH_PURTATA_DATE);
        assertThat(testKamalSociety.getGambhirDosh()).isEqualTo(DEFAULT_GAMBHIR_DOSH);
        assertThat(testKamalSociety.getBranchInwardNumber()).isEqualTo(DEFAULT_BRANCH_INWARD_NUMBER);
        assertThat(testKamalSociety.getBranchInwardDate()).isEqualTo(DEFAULT_BRANCH_INWARD_DATE);
        assertThat(testKamalSociety.getBranchOutwardNumber()).isEqualTo(DEFAULT_BRANCH_OUTWARD_NUMBER);
        assertThat(testKamalSociety.getBranchOutwardDate()).isEqualTo(DEFAULT_BRANCH_OUTWARD_DATE);
        assertThat(testKamalSociety.getHeadOfficeInwardNumber()).isEqualTo(DEFAULT_HEAD_OFFICE_INWARD_NUMBER);
        assertThat(testKamalSociety.getHeadOfficeInwardDate()).isEqualTo(DEFAULT_HEAD_OFFICE_INWARD_DATE);
        assertThat(testKamalSociety.getHeadOfficeOutwardNumber()).isEqualTo(DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER);
        assertThat(testKamalSociety.getHeadOfficeOutwardDate()).isEqualTo(DEFAULT_HEAD_OFFICE_OUTWARD_DATE);
        assertThat(testKamalSociety.getTharavNumber()).isEqualTo(DEFAULT_THARAV_NUMBER);
        assertThat(testKamalSociety.getTharavDate()).isEqualTo(DEFAULT_THARAV_DATE);
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
    void checkFinancialYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = kamalSocietyRepository.findAll().size();
        // set the field null
        kamalSociety.setFinancialYear(null);

        // Create the KamalSociety, which fails.

        restKamalSocietyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kamalSociety)))
            .andExpect(status().isBadRequest());

        List<KamalSociety> kamalSocietyList = kamalSocietyRepository.findAll();
        assertThat(kamalSocietyList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDateMr").value(hasItem(DEFAULT_KM_DATE_MR)))
            .andExpect(jsonPath("$.[*].kmFromDate").value(hasItem(DEFAULT_KM_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmFromDateMr").value(hasItem(DEFAULT_KM_FROM_DATE_MR)))
            .andExpect(jsonPath("$.[*].kmToDate").value(hasItem(DEFAULT_KM_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmToDateMr").value(hasItem(DEFAULT_KM_TO_DATE_MR)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].pacsName").value(hasItem(DEFAULT_PACS_NAME)))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].talukaId").value(hasItem(DEFAULT_TALUKA_ID.intValue())))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].zindagiPatrakDate").value(hasItem(DEFAULT_ZINDAGI_PATRAK_DATE.toString())))
            .andExpect(jsonPath("$.[*].zindagiPatrakDateMr").value(hasItem(DEFAULT_ZINDAGI_PATRAK_DATE_MR)))
            .andExpect(jsonPath("$.[*].bankTapasaniDate").value(hasItem(DEFAULT_BANK_TAPASANI_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankTapasaniDateMr").value(hasItem(DEFAULT_BANK_TAPASANI_DATE_MR)))
            .andExpect(jsonPath("$.[*].govTapasaniDate").value(hasItem(DEFAULT_GOV_TAPASANI_DATE.toString())))
            .andExpect(jsonPath("$.[*].govTapasaniDateMr").value(hasItem(DEFAULT_GOV_TAPASANI_DATE_MR)))
            .andExpect(jsonPath("$.[*].sansthaTapasaniDate").value(hasItem(DEFAULT_SANSTHA_TAPASANI_DATE.toString())))
            .andExpect(jsonPath("$.[*].sansthaTapasaniDateMr").value(hasItem(DEFAULT_SANSTHA_TAPASANI_DATE_MR)))
            .andExpect(jsonPath("$.[*].totalLand").value(hasItem(DEFAULT_TOTAL_LAND)))
            .andExpect(jsonPath("$.[*].bagayat").value(hasItem(DEFAULT_BAGAYAT)))
            .andExpect(jsonPath("$.[*].jirayat").value(hasItem(DEFAULT_JIRAYAT)))
            .andExpect(jsonPath("$.[*].totalFarmer").value(hasItem(DEFAULT_TOTAL_FARMER)))
            .andExpect(jsonPath("$.[*].memberFarmer").value(hasItem(DEFAULT_MEMBER_FARMER)))
            .andExpect(jsonPath("$.[*].nonMemberFarmer").value(hasItem(DEFAULT_NON_MEMBER_FARMER)))
            .andExpect(jsonPath("$.[*].talebandDate").value(hasItem(DEFAULT_TALEBAND_DATE.toString())))
            .andExpect(jsonPath("$.[*].memLoan").value(hasItem(DEFAULT_MEM_LOAN)))
            .andExpect(jsonPath("$.[*].memDue").value(hasItem(DEFAULT_MEM_DUE)))
            .andExpect(jsonPath("$.[*].memVasuli").value(hasItem(DEFAULT_MEM_VASULI)))
            .andExpect(jsonPath("$.[*].memVasuliPer").value(hasItem(DEFAULT_MEM_VASULI_PER)))
            .andExpect(jsonPath("$.[*].bankLoan").value(hasItem(DEFAULT_BANK_LOAN)))
            .andExpect(jsonPath("$.[*].bankDue").value(hasItem(DEFAULT_BANK_DUE)))
            .andExpect(jsonPath("$.[*].bankVasuli").value(hasItem(DEFAULT_BANK_VASULI)))
            .andExpect(jsonPath("$.[*].bankVasuliPer").value(hasItem(DEFAULT_BANK_VASULI_PER)))
            .andExpect(jsonPath("$.[*].balanceSheetDate").value(hasItem(DEFAULT_BALANCE_SHEET_DATE.toString())))
            .andExpect(jsonPath("$.[*].balanceSheetDateMr").value(hasItem(DEFAULT_BALANCE_SHEET_DATE_MR)))
            .andExpect(jsonPath("$.[*].liabilityAdhikrutShareCapital").value(hasItem(DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL)))
            .andExpect(jsonPath("$.[*].liabilityVasulShareCapital").value(hasItem(DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL)))
            .andExpect(jsonPath("$.[*].liabilityFund").value(hasItem(DEFAULT_LIABILITY_FUND)))
            .andExpect(jsonPath("$.[*].liabilitySpareFund").value(hasItem(DEFAULT_LIABILITY_SPARE_FUND)))
            .andExpect(jsonPath("$.[*].liabilityDeposite").value(hasItem(DEFAULT_LIABILITY_DEPOSITE)))
            .andExpect(jsonPath("$.[*].liabilityBalanceSheetBankLoan").value(hasItem(DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN)))
            .andExpect(jsonPath("$.[*].liabilityOtherPayable").value(hasItem(DEFAULT_LIABILITY_OTHER_PAYABLE)))
            .andExpect(jsonPath("$.[*].liabilityProfit").value(hasItem(DEFAULT_LIABILITY_PROFIT)))
            .andExpect(jsonPath("$.[*].assetCash").value(hasItem(DEFAULT_ASSET_CASH)))
            .andExpect(jsonPath("$.[*].assetInvestment").value(hasItem(DEFAULT_ASSET_INVESTMENT)))
            .andExpect(jsonPath("$.[*].assetImaratFund").value(hasItem(DEFAULT_ASSET_IMARAT_FUND)))
            .andExpect(jsonPath("$.[*].assetMemberLoan").value(hasItem(DEFAULT_ASSET_MEMBER_LOAN)))
            .andExpect(jsonPath("$.[*].assetDeadStock").value(hasItem(DEFAULT_ASSET_DEAD_STOCK)))
            .andExpect(jsonPath("$.[*].assetOtherReceivable").value(hasItem(DEFAULT_ASSET_OTHER_RECEIVABLE)))
            .andExpect(jsonPath("$.[*].assetLoss").value(hasItem(DEFAULT_ASSET_LOSS)))
            .andExpect(jsonPath("$.[*].totalLiability").value(hasItem(DEFAULT_TOTAL_LIABILITY)))
            .andExpect(jsonPath("$.[*].totalAsset").value(hasItem(DEFAULT_TOTAL_ASSET)))
            .andExpect(jsonPath("$.[*].villageCode").value(hasItem(DEFAULT_VILLAGE_CODE)))
            .andExpect(jsonPath("$.[*].pacsVerifiedFlag").value(hasItem(DEFAULT_PACS_VERIFIED_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].branchVerifiedFlag").value(hasItem(DEFAULT_BRANCH_VERIFIED_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].headOfficeVerifiedFlag").value(hasItem(DEFAULT_HEAD_OFFICE_VERIFIED_FLAG.booleanValue())))
            .andExpect(
                jsonPath("$.[*].divisionalOfficeVerifiedFlag").value(hasItem(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].isSupplimenteryFlag").value(hasItem(DEFAULT_IS_SUPPLIMENTERY_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].sansthaTapasaniVarg").value(hasItem(DEFAULT_SANSTHA_TAPASANI_VARG)))
            .andExpect(jsonPath("$.[*].branchVerifiedBy").value(hasItem(DEFAULT_BRANCH_VERIFIED_BY)))
            .andExpect(jsonPath("$.[*].branchVerifiedDate").value(hasItem(DEFAULT_BRANCH_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].headOfficeVerifiedBy").value(hasItem(DEFAULT_HEAD_OFFICE_VERIFIED_BY)))
            .andExpect(jsonPath("$.[*].headOfficeVerifiedDate").value(hasItem(DEFAULT_HEAD_OFFICE_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].divisionalOfficeVerifiedBy").value(hasItem(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY)))
            .andExpect(jsonPath("$.[*].divisionalOfficeVerifiedDate").value(hasItem(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].doshPurtataDate").value(hasItem(DEFAULT_DOSH_PURTATA_DATE.toString())))
            .andExpect(jsonPath("$.[*].gambhirDosh").value(hasItem(DEFAULT_GAMBHIR_DOSH)))
            .andExpect(jsonPath("$.[*].branchInwardNumber").value(hasItem(DEFAULT_BRANCH_INWARD_NUMBER)))
            .andExpect(jsonPath("$.[*].branchInwardDate").value(hasItem(DEFAULT_BRANCH_INWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].branchOutwardNumber").value(hasItem(DEFAULT_BRANCH_OUTWARD_NUMBER)))
            .andExpect(jsonPath("$.[*].branchOutwardDate").value(hasItem(DEFAULT_BRANCH_OUTWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].headOfficeInwardNumber").value(hasItem(DEFAULT_HEAD_OFFICE_INWARD_NUMBER)))
            .andExpect(jsonPath("$.[*].headOfficeInwardDate").value(hasItem(DEFAULT_HEAD_OFFICE_INWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].headOfficeOutwardNumber").value(hasItem(DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER)))
            .andExpect(jsonPath("$.[*].headOfficeOutwardDate").value(hasItem(DEFAULT_HEAD_OFFICE_OUTWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].tharavNumber").value(hasItem(DEFAULT_THARAV_NUMBER)))
            .andExpect(jsonPath("$.[*].tharavDate").value(hasItem(DEFAULT_THARAV_DATE.toString())));
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
            .andExpect(jsonPath("$.financialYear").value(DEFAULT_FINANCIAL_YEAR))
            .andExpect(jsonPath("$.kmDate").value(DEFAULT_KM_DATE.toString()))
            .andExpect(jsonPath("$.kmDateMr").value(DEFAULT_KM_DATE_MR))
            .andExpect(jsonPath("$.kmFromDate").value(DEFAULT_KM_FROM_DATE.toString()))
            .andExpect(jsonPath("$.kmFromDateMr").value(DEFAULT_KM_FROM_DATE_MR))
            .andExpect(jsonPath("$.kmToDate").value(DEFAULT_KM_TO_DATE.toString()))
            .andExpect(jsonPath("$.kmToDateMr").value(DEFAULT_KM_TO_DATE_MR))
            .andExpect(jsonPath("$.pacsNumber").value(DEFAULT_PACS_NUMBER))
            .andExpect(jsonPath("$.pacsName").value(DEFAULT_PACS_NAME))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID.intValue()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.talukaId").value(DEFAULT_TALUKA_ID.intValue()))
            .andExpect(jsonPath("$.talukaName").value(DEFAULT_TALUKA_NAME))
            .andExpect(jsonPath("$.zindagiPatrakDate").value(DEFAULT_ZINDAGI_PATRAK_DATE.toString()))
            .andExpect(jsonPath("$.zindagiPatrakDateMr").value(DEFAULT_ZINDAGI_PATRAK_DATE_MR))
            .andExpect(jsonPath("$.bankTapasaniDate").value(DEFAULT_BANK_TAPASANI_DATE.toString()))
            .andExpect(jsonPath("$.bankTapasaniDateMr").value(DEFAULT_BANK_TAPASANI_DATE_MR))
            .andExpect(jsonPath("$.govTapasaniDate").value(DEFAULT_GOV_TAPASANI_DATE.toString()))
            .andExpect(jsonPath("$.govTapasaniDateMr").value(DEFAULT_GOV_TAPASANI_DATE_MR))
            .andExpect(jsonPath("$.sansthaTapasaniDate").value(DEFAULT_SANSTHA_TAPASANI_DATE.toString()))
            .andExpect(jsonPath("$.sansthaTapasaniDateMr").value(DEFAULT_SANSTHA_TAPASANI_DATE_MR))
            .andExpect(jsonPath("$.totalLand").value(DEFAULT_TOTAL_LAND))
            .andExpect(jsonPath("$.bagayat").value(DEFAULT_BAGAYAT))
            .andExpect(jsonPath("$.jirayat").value(DEFAULT_JIRAYAT))
            .andExpect(jsonPath("$.totalFarmer").value(DEFAULT_TOTAL_FARMER))
            .andExpect(jsonPath("$.memberFarmer").value(DEFAULT_MEMBER_FARMER))
            .andExpect(jsonPath("$.nonMemberFarmer").value(DEFAULT_NON_MEMBER_FARMER))
            .andExpect(jsonPath("$.talebandDate").value(DEFAULT_TALEBAND_DATE.toString()))
            .andExpect(jsonPath("$.memLoan").value(DEFAULT_MEM_LOAN))
            .andExpect(jsonPath("$.memDue").value(DEFAULT_MEM_DUE))
            .andExpect(jsonPath("$.memVasuli").value(DEFAULT_MEM_VASULI))
            .andExpect(jsonPath("$.memVasuliPer").value(DEFAULT_MEM_VASULI_PER))
            .andExpect(jsonPath("$.bankLoan").value(DEFAULT_BANK_LOAN))
            .andExpect(jsonPath("$.bankDue").value(DEFAULT_BANK_DUE))
            .andExpect(jsonPath("$.bankVasuli").value(DEFAULT_BANK_VASULI))
            .andExpect(jsonPath("$.bankVasuliPer").value(DEFAULT_BANK_VASULI_PER))
            .andExpect(jsonPath("$.balanceSheetDate").value(DEFAULT_BALANCE_SHEET_DATE.toString()))
            .andExpect(jsonPath("$.balanceSheetDateMr").value(DEFAULT_BALANCE_SHEET_DATE_MR))
            .andExpect(jsonPath("$.liabilityAdhikrutShareCapital").value(DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL))
            .andExpect(jsonPath("$.liabilityVasulShareCapital").value(DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL))
            .andExpect(jsonPath("$.liabilityFund").value(DEFAULT_LIABILITY_FUND))
            .andExpect(jsonPath("$.liabilitySpareFund").value(DEFAULT_LIABILITY_SPARE_FUND))
            .andExpect(jsonPath("$.liabilityDeposite").value(DEFAULT_LIABILITY_DEPOSITE))
            .andExpect(jsonPath("$.liabilityBalanceSheetBankLoan").value(DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN))
            .andExpect(jsonPath("$.liabilityOtherPayable").value(DEFAULT_LIABILITY_OTHER_PAYABLE))
            .andExpect(jsonPath("$.liabilityProfit").value(DEFAULT_LIABILITY_PROFIT))
            .andExpect(jsonPath("$.assetCash").value(DEFAULT_ASSET_CASH))
            .andExpect(jsonPath("$.assetInvestment").value(DEFAULT_ASSET_INVESTMENT))
            .andExpect(jsonPath("$.assetImaratFund").value(DEFAULT_ASSET_IMARAT_FUND))
            .andExpect(jsonPath("$.assetMemberLoan").value(DEFAULT_ASSET_MEMBER_LOAN))
            .andExpect(jsonPath("$.assetDeadStock").value(DEFAULT_ASSET_DEAD_STOCK))
            .andExpect(jsonPath("$.assetOtherReceivable").value(DEFAULT_ASSET_OTHER_RECEIVABLE))
            .andExpect(jsonPath("$.assetLoss").value(DEFAULT_ASSET_LOSS))
            .andExpect(jsonPath("$.totalLiability").value(DEFAULT_TOTAL_LIABILITY))
            .andExpect(jsonPath("$.totalAsset").value(DEFAULT_TOTAL_ASSET))
            .andExpect(jsonPath("$.villageCode").value(DEFAULT_VILLAGE_CODE))
            .andExpect(jsonPath("$.pacsVerifiedFlag").value(DEFAULT_PACS_VERIFIED_FLAG.booleanValue()))
            .andExpect(jsonPath("$.branchVerifiedFlag").value(DEFAULT_BRANCH_VERIFIED_FLAG.booleanValue()))
            .andExpect(jsonPath("$.headOfficeVerifiedFlag").value(DEFAULT_HEAD_OFFICE_VERIFIED_FLAG.booleanValue()))
            .andExpect(jsonPath("$.divisionalOfficeVerifiedFlag").value(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG.booleanValue()))
            .andExpect(jsonPath("$.isSupplimenteryFlag").value(DEFAULT_IS_SUPPLIMENTERY_FLAG.booleanValue()))
            .andExpect(jsonPath("$.sansthaTapasaniVarg").value(DEFAULT_SANSTHA_TAPASANI_VARG))
            .andExpect(jsonPath("$.branchVerifiedBy").value(DEFAULT_BRANCH_VERIFIED_BY))
            .andExpect(jsonPath("$.branchVerifiedDate").value(DEFAULT_BRANCH_VERIFIED_DATE.toString()))
            .andExpect(jsonPath("$.headOfficeVerifiedBy").value(DEFAULT_HEAD_OFFICE_VERIFIED_BY))
            .andExpect(jsonPath("$.headOfficeVerifiedDate").value(DEFAULT_HEAD_OFFICE_VERIFIED_DATE.toString()))
            .andExpect(jsonPath("$.divisionalOfficeVerifiedBy").value(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY))
            .andExpect(jsonPath("$.divisionalOfficeVerifiedDate").value(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE.toString()))
            .andExpect(jsonPath("$.doshPurtataDate").value(DEFAULT_DOSH_PURTATA_DATE.toString()))
            .andExpect(jsonPath("$.gambhirDosh").value(DEFAULT_GAMBHIR_DOSH))
            .andExpect(jsonPath("$.branchInwardNumber").value(DEFAULT_BRANCH_INWARD_NUMBER))
            .andExpect(jsonPath("$.branchInwardDate").value(DEFAULT_BRANCH_INWARD_DATE.toString()))
            .andExpect(jsonPath("$.branchOutwardNumber").value(DEFAULT_BRANCH_OUTWARD_NUMBER))
            .andExpect(jsonPath("$.branchOutwardDate").value(DEFAULT_BRANCH_OUTWARD_DATE.toString()))
            .andExpect(jsonPath("$.headOfficeInwardNumber").value(DEFAULT_HEAD_OFFICE_INWARD_NUMBER))
            .andExpect(jsonPath("$.headOfficeInwardDate").value(DEFAULT_HEAD_OFFICE_INWARD_DATE.toString()))
            .andExpect(jsonPath("$.headOfficeOutwardNumber").value(DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER))
            .andExpect(jsonPath("$.headOfficeOutwardDate").value(DEFAULT_HEAD_OFFICE_OUTWARD_DATE.toString()))
            .andExpect(jsonPath("$.tharavNumber").value(DEFAULT_THARAV_NUMBER))
            .andExpect(jsonPath("$.tharavDate").value(DEFAULT_THARAV_DATE.toString()));
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
    void getAllKamalSocietiesByFinancialYearIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear equals to DEFAULT_FINANCIAL_YEAR
        defaultKamalSocietyShouldBeFound("financialYear.equals=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalSocietyList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldNotBeFound("financialYear.equals=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear in DEFAULT_FINANCIAL_YEAR or UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldBeFound("financialYear.in=" + DEFAULT_FINANCIAL_YEAR + "," + UPDATED_FINANCIAL_YEAR);

        // Get all the kamalSocietyList where financialYear equals to UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldNotBeFound("financialYear.in=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear is not null
        defaultKamalSocietyShouldBeFound("financialYear.specified=true");

        // Get all the kamalSocietyList where financialYear is null
        defaultKamalSocietyShouldNotBeFound("financialYear.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear contains DEFAULT_FINANCIAL_YEAR
        defaultKamalSocietyShouldBeFound("financialYear.contains=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalSocietyList where financialYear contains UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldNotBeFound("financialYear.contains=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByFinancialYearNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where financialYear does not contain DEFAULT_FINANCIAL_YEAR
        defaultKamalSocietyShouldNotBeFound("financialYear.doesNotContain=" + DEFAULT_FINANCIAL_YEAR);

        // Get all the kamalSocietyList where financialYear does not contain UPDATED_FINANCIAL_YEAR
        defaultKamalSocietyShouldBeFound("financialYear.doesNotContain=" + UPDATED_FINANCIAL_YEAR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDate equals to DEFAULT_KM_DATE
        defaultKamalSocietyShouldBeFound("kmDate.equals=" + DEFAULT_KM_DATE);

        // Get all the kamalSocietyList where kmDate equals to UPDATED_KM_DATE
        defaultKamalSocietyShouldNotBeFound("kmDate.equals=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDate in DEFAULT_KM_DATE or UPDATED_KM_DATE
        defaultKamalSocietyShouldBeFound("kmDate.in=" + DEFAULT_KM_DATE + "," + UPDATED_KM_DATE);

        // Get all the kamalSocietyList where kmDate equals to UPDATED_KM_DATE
        defaultKamalSocietyShouldNotBeFound("kmDate.in=" + UPDATED_KM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDate is not null
        defaultKamalSocietyShouldBeFound("kmDate.specified=true");

        // Get all the kamalSocietyList where kmDate is null
        defaultKamalSocietyShouldNotBeFound("kmDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDateMr equals to DEFAULT_KM_DATE_MR
        defaultKamalSocietyShouldBeFound("kmDateMr.equals=" + DEFAULT_KM_DATE_MR);

        // Get all the kamalSocietyList where kmDateMr equals to UPDATED_KM_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmDateMr.equals=" + UPDATED_KM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDateMr in DEFAULT_KM_DATE_MR or UPDATED_KM_DATE_MR
        defaultKamalSocietyShouldBeFound("kmDateMr.in=" + DEFAULT_KM_DATE_MR + "," + UPDATED_KM_DATE_MR);

        // Get all the kamalSocietyList where kmDateMr equals to UPDATED_KM_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmDateMr.in=" + UPDATED_KM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDateMr is not null
        defaultKamalSocietyShouldBeFound("kmDateMr.specified=true");

        // Get all the kamalSocietyList where kmDateMr is null
        defaultKamalSocietyShouldNotBeFound("kmDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDateMr contains DEFAULT_KM_DATE_MR
        defaultKamalSocietyShouldBeFound("kmDateMr.contains=" + DEFAULT_KM_DATE_MR);

        // Get all the kamalSocietyList where kmDateMr contains UPDATED_KM_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmDateMr.contains=" + UPDATED_KM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmDateMr does not contain DEFAULT_KM_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmDateMr.doesNotContain=" + DEFAULT_KM_DATE_MR);

        // Get all the kamalSocietyList where kmDateMr does not contain UPDATED_KM_DATE_MR
        defaultKamalSocietyShouldBeFound("kmDateMr.doesNotContain=" + UPDATED_KM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmFromDate equals to DEFAULT_KM_FROM_DATE
        defaultKamalSocietyShouldBeFound("kmFromDate.equals=" + DEFAULT_KM_FROM_DATE);

        // Get all the kamalSocietyList where kmFromDate equals to UPDATED_KM_FROM_DATE
        defaultKamalSocietyShouldNotBeFound("kmFromDate.equals=" + UPDATED_KM_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmFromDate in DEFAULT_KM_FROM_DATE or UPDATED_KM_FROM_DATE
        defaultKamalSocietyShouldBeFound("kmFromDate.in=" + DEFAULT_KM_FROM_DATE + "," + UPDATED_KM_FROM_DATE);

        // Get all the kamalSocietyList where kmFromDate equals to UPDATED_KM_FROM_DATE
        defaultKamalSocietyShouldNotBeFound("kmFromDate.in=" + UPDATED_KM_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmFromDate is not null
        defaultKamalSocietyShouldBeFound("kmFromDate.specified=true");

        // Get all the kamalSocietyList where kmFromDate is null
        defaultKamalSocietyShouldNotBeFound("kmFromDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmFromDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmFromDateMr equals to DEFAULT_KM_FROM_DATE_MR
        defaultKamalSocietyShouldBeFound("kmFromDateMr.equals=" + DEFAULT_KM_FROM_DATE_MR);

        // Get all the kamalSocietyList where kmFromDateMr equals to UPDATED_KM_FROM_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmFromDateMr.equals=" + UPDATED_KM_FROM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmFromDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmFromDateMr in DEFAULT_KM_FROM_DATE_MR or UPDATED_KM_FROM_DATE_MR
        defaultKamalSocietyShouldBeFound("kmFromDateMr.in=" + DEFAULT_KM_FROM_DATE_MR + "," + UPDATED_KM_FROM_DATE_MR);

        // Get all the kamalSocietyList where kmFromDateMr equals to UPDATED_KM_FROM_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmFromDateMr.in=" + UPDATED_KM_FROM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmFromDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmFromDateMr is not null
        defaultKamalSocietyShouldBeFound("kmFromDateMr.specified=true");

        // Get all the kamalSocietyList where kmFromDateMr is null
        defaultKamalSocietyShouldNotBeFound("kmFromDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmFromDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmFromDateMr contains DEFAULT_KM_FROM_DATE_MR
        defaultKamalSocietyShouldBeFound("kmFromDateMr.contains=" + DEFAULT_KM_FROM_DATE_MR);

        // Get all the kamalSocietyList where kmFromDateMr contains UPDATED_KM_FROM_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmFromDateMr.contains=" + UPDATED_KM_FROM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmFromDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmFromDateMr does not contain DEFAULT_KM_FROM_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmFromDateMr.doesNotContain=" + DEFAULT_KM_FROM_DATE_MR);

        // Get all the kamalSocietyList where kmFromDateMr does not contain UPDATED_KM_FROM_DATE_MR
        defaultKamalSocietyShouldBeFound("kmFromDateMr.doesNotContain=" + UPDATED_KM_FROM_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmToDate equals to DEFAULT_KM_TO_DATE
        defaultKamalSocietyShouldBeFound("kmToDate.equals=" + DEFAULT_KM_TO_DATE);

        // Get all the kamalSocietyList where kmToDate equals to UPDATED_KM_TO_DATE
        defaultKamalSocietyShouldNotBeFound("kmToDate.equals=" + UPDATED_KM_TO_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmToDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmToDate in DEFAULT_KM_TO_DATE or UPDATED_KM_TO_DATE
        defaultKamalSocietyShouldBeFound("kmToDate.in=" + DEFAULT_KM_TO_DATE + "," + UPDATED_KM_TO_DATE);

        // Get all the kamalSocietyList where kmToDate equals to UPDATED_KM_TO_DATE
        defaultKamalSocietyShouldNotBeFound("kmToDate.in=" + UPDATED_KM_TO_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmToDate is not null
        defaultKamalSocietyShouldBeFound("kmToDate.specified=true");

        // Get all the kamalSocietyList where kmToDate is null
        defaultKamalSocietyShouldNotBeFound("kmToDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmToDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmToDateMr equals to DEFAULT_KM_TO_DATE_MR
        defaultKamalSocietyShouldBeFound("kmToDateMr.equals=" + DEFAULT_KM_TO_DATE_MR);

        // Get all the kamalSocietyList where kmToDateMr equals to UPDATED_KM_TO_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmToDateMr.equals=" + UPDATED_KM_TO_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmToDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmToDateMr in DEFAULT_KM_TO_DATE_MR or UPDATED_KM_TO_DATE_MR
        defaultKamalSocietyShouldBeFound("kmToDateMr.in=" + DEFAULT_KM_TO_DATE_MR + "," + UPDATED_KM_TO_DATE_MR);

        // Get all the kamalSocietyList where kmToDateMr equals to UPDATED_KM_TO_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmToDateMr.in=" + UPDATED_KM_TO_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmToDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmToDateMr is not null
        defaultKamalSocietyShouldBeFound("kmToDateMr.specified=true");

        // Get all the kamalSocietyList where kmToDateMr is null
        defaultKamalSocietyShouldNotBeFound("kmToDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmToDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmToDateMr contains DEFAULT_KM_TO_DATE_MR
        defaultKamalSocietyShouldBeFound("kmToDateMr.contains=" + DEFAULT_KM_TO_DATE_MR);

        // Get all the kamalSocietyList where kmToDateMr contains UPDATED_KM_TO_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmToDateMr.contains=" + UPDATED_KM_TO_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByKmToDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where kmToDateMr does not contain DEFAULT_KM_TO_DATE_MR
        defaultKamalSocietyShouldNotBeFound("kmToDateMr.doesNotContain=" + DEFAULT_KM_TO_DATE_MR);

        // Get all the kamalSocietyList where kmToDateMr does not contain UPDATED_KM_TO_DATE_MR
        defaultKamalSocietyShouldBeFound("kmToDateMr.doesNotContain=" + UPDATED_KM_TO_DATE_MR);
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
    void getAllKamalSocietiesByPacsNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber contains DEFAULT_PACS_NUMBER
        defaultKamalSocietyShouldBeFound("pacsNumber.contains=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalSocietyList where pacsNumber contains UPDATED_PACS_NUMBER
        defaultKamalSocietyShouldNotBeFound("pacsNumber.contains=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsNumber does not contain DEFAULT_PACS_NUMBER
        defaultKamalSocietyShouldNotBeFound("pacsNumber.doesNotContain=" + DEFAULT_PACS_NUMBER);

        // Get all the kamalSocietyList where pacsNumber does not contain UPDATED_PACS_NUMBER
        defaultKamalSocietyShouldBeFound("pacsNumber.doesNotContain=" + UPDATED_PACS_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsName equals to DEFAULT_PACS_NAME
        defaultKamalSocietyShouldBeFound("pacsName.equals=" + DEFAULT_PACS_NAME);

        // Get all the kamalSocietyList where pacsName equals to UPDATED_PACS_NAME
        defaultKamalSocietyShouldNotBeFound("pacsName.equals=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNameIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsName in DEFAULT_PACS_NAME or UPDATED_PACS_NAME
        defaultKamalSocietyShouldBeFound("pacsName.in=" + DEFAULT_PACS_NAME + "," + UPDATED_PACS_NAME);

        // Get all the kamalSocietyList where pacsName equals to UPDATED_PACS_NAME
        defaultKamalSocietyShouldNotBeFound("pacsName.in=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsName is not null
        defaultKamalSocietyShouldBeFound("pacsName.specified=true");

        // Get all the kamalSocietyList where pacsName is null
        defaultKamalSocietyShouldNotBeFound("pacsName.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNameContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsName contains DEFAULT_PACS_NAME
        defaultKamalSocietyShouldBeFound("pacsName.contains=" + DEFAULT_PACS_NAME);

        // Get all the kamalSocietyList where pacsName contains UPDATED_PACS_NAME
        defaultKamalSocietyShouldNotBeFound("pacsName.contains=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsNameNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsName does not contain DEFAULT_PACS_NAME
        defaultKamalSocietyShouldNotBeFound("pacsName.doesNotContain=" + DEFAULT_PACS_NAME);

        // Get all the kamalSocietyList where pacsName does not contain UPDATED_PACS_NAME
        defaultKamalSocietyShouldBeFound("pacsName.doesNotContain=" + UPDATED_PACS_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchId equals to DEFAULT_BRANCH_ID
        defaultKamalSocietyShouldBeFound("branchId.equals=" + DEFAULT_BRANCH_ID);

        // Get all the kamalSocietyList where branchId equals to UPDATED_BRANCH_ID
        defaultKamalSocietyShouldNotBeFound("branchId.equals=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchIdIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchId in DEFAULT_BRANCH_ID or UPDATED_BRANCH_ID
        defaultKamalSocietyShouldBeFound("branchId.in=" + DEFAULT_BRANCH_ID + "," + UPDATED_BRANCH_ID);

        // Get all the kamalSocietyList where branchId equals to UPDATED_BRANCH_ID
        defaultKamalSocietyShouldNotBeFound("branchId.in=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchId is not null
        defaultKamalSocietyShouldBeFound("branchId.specified=true");

        // Get all the kamalSocietyList where branchId is null
        defaultKamalSocietyShouldNotBeFound("branchId.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchId is greater than or equal to DEFAULT_BRANCH_ID
        defaultKamalSocietyShouldBeFound("branchId.greaterThanOrEqual=" + DEFAULT_BRANCH_ID);

        // Get all the kamalSocietyList where branchId is greater than or equal to UPDATED_BRANCH_ID
        defaultKamalSocietyShouldNotBeFound("branchId.greaterThanOrEqual=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchId is less than or equal to DEFAULT_BRANCH_ID
        defaultKamalSocietyShouldBeFound("branchId.lessThanOrEqual=" + DEFAULT_BRANCH_ID);

        // Get all the kamalSocietyList where branchId is less than or equal to SMALLER_BRANCH_ID
        defaultKamalSocietyShouldNotBeFound("branchId.lessThanOrEqual=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchIdIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchId is less than DEFAULT_BRANCH_ID
        defaultKamalSocietyShouldNotBeFound("branchId.lessThan=" + DEFAULT_BRANCH_ID);

        // Get all the kamalSocietyList where branchId is less than UPDATED_BRANCH_ID
        defaultKamalSocietyShouldBeFound("branchId.lessThan=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchId is greater than DEFAULT_BRANCH_ID
        defaultKamalSocietyShouldNotBeFound("branchId.greaterThan=" + DEFAULT_BRANCH_ID);

        // Get all the kamalSocietyList where branchId is greater than SMALLER_BRANCH_ID
        defaultKamalSocietyShouldBeFound("branchId.greaterThan=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchName equals to DEFAULT_BRANCH_NAME
        defaultKamalSocietyShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the kamalSocietyList where branchName equals to UPDATED_BRANCH_NAME
        defaultKamalSocietyShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultKamalSocietyShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the kamalSocietyList where branchName equals to UPDATED_BRANCH_NAME
        defaultKamalSocietyShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchName is not null
        defaultKamalSocietyShouldBeFound("branchName.specified=true");

        // Get all the kamalSocietyList where branchName is null
        defaultKamalSocietyShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchName contains DEFAULT_BRANCH_NAME
        defaultKamalSocietyShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the kamalSocietyList where branchName contains UPDATED_BRANCH_NAME
        defaultKamalSocietyShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultKamalSocietyShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the kamalSocietyList where branchName does not contain UPDATED_BRANCH_NAME
        defaultKamalSocietyShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaId equals to DEFAULT_TALUKA_ID
        defaultKamalSocietyShouldBeFound("talukaId.equals=" + DEFAULT_TALUKA_ID);

        // Get all the kamalSocietyList where talukaId equals to UPDATED_TALUKA_ID
        defaultKamalSocietyShouldNotBeFound("talukaId.equals=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaIdIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaId in DEFAULT_TALUKA_ID or UPDATED_TALUKA_ID
        defaultKamalSocietyShouldBeFound("talukaId.in=" + DEFAULT_TALUKA_ID + "," + UPDATED_TALUKA_ID);

        // Get all the kamalSocietyList where talukaId equals to UPDATED_TALUKA_ID
        defaultKamalSocietyShouldNotBeFound("talukaId.in=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaId is not null
        defaultKamalSocietyShouldBeFound("talukaId.specified=true");

        // Get all the kamalSocietyList where talukaId is null
        defaultKamalSocietyShouldNotBeFound("talukaId.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaId is greater than or equal to DEFAULT_TALUKA_ID
        defaultKamalSocietyShouldBeFound("talukaId.greaterThanOrEqual=" + DEFAULT_TALUKA_ID);

        // Get all the kamalSocietyList where talukaId is greater than or equal to UPDATED_TALUKA_ID
        defaultKamalSocietyShouldNotBeFound("talukaId.greaterThanOrEqual=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaId is less than or equal to DEFAULT_TALUKA_ID
        defaultKamalSocietyShouldBeFound("talukaId.lessThanOrEqual=" + DEFAULT_TALUKA_ID);

        // Get all the kamalSocietyList where talukaId is less than or equal to SMALLER_TALUKA_ID
        defaultKamalSocietyShouldNotBeFound("talukaId.lessThanOrEqual=" + SMALLER_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaId is less than DEFAULT_TALUKA_ID
        defaultKamalSocietyShouldNotBeFound("talukaId.lessThan=" + DEFAULT_TALUKA_ID);

        // Get all the kamalSocietyList where talukaId is less than UPDATED_TALUKA_ID
        defaultKamalSocietyShouldBeFound("talukaId.lessThan=" + UPDATED_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaId is greater than DEFAULT_TALUKA_ID
        defaultKamalSocietyShouldNotBeFound("talukaId.greaterThan=" + DEFAULT_TALUKA_ID);

        // Get all the kamalSocietyList where talukaId is greater than SMALLER_TALUKA_ID
        defaultKamalSocietyShouldBeFound("talukaId.greaterThan=" + SMALLER_TALUKA_ID);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaName equals to DEFAULT_TALUKA_NAME
        defaultKamalSocietyShouldBeFound("talukaName.equals=" + DEFAULT_TALUKA_NAME);

        // Get all the kamalSocietyList where talukaName equals to UPDATED_TALUKA_NAME
        defaultKamalSocietyShouldNotBeFound("talukaName.equals=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaNameIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaName in DEFAULT_TALUKA_NAME or UPDATED_TALUKA_NAME
        defaultKamalSocietyShouldBeFound("talukaName.in=" + DEFAULT_TALUKA_NAME + "," + UPDATED_TALUKA_NAME);

        // Get all the kamalSocietyList where talukaName equals to UPDATED_TALUKA_NAME
        defaultKamalSocietyShouldNotBeFound("talukaName.in=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaName is not null
        defaultKamalSocietyShouldBeFound("talukaName.specified=true");

        // Get all the kamalSocietyList where talukaName is null
        defaultKamalSocietyShouldNotBeFound("talukaName.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaNameContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaName contains DEFAULT_TALUKA_NAME
        defaultKamalSocietyShouldBeFound("talukaName.contains=" + DEFAULT_TALUKA_NAME);

        // Get all the kamalSocietyList where talukaName contains UPDATED_TALUKA_NAME
        defaultKamalSocietyShouldNotBeFound("talukaName.contains=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalukaNameNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talukaName does not contain DEFAULT_TALUKA_NAME
        defaultKamalSocietyShouldNotBeFound("talukaName.doesNotContain=" + DEFAULT_TALUKA_NAME);

        // Get all the kamalSocietyList where talukaName does not contain UPDATED_TALUKA_NAME
        defaultKamalSocietyShouldBeFound("talukaName.doesNotContain=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiPatrakDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiPatrakDate equals to DEFAULT_ZINDAGI_PATRAK_DATE
        defaultKamalSocietyShouldBeFound("zindagiPatrakDate.equals=" + DEFAULT_ZINDAGI_PATRAK_DATE);

        // Get all the kamalSocietyList where zindagiPatrakDate equals to UPDATED_ZINDAGI_PATRAK_DATE
        defaultKamalSocietyShouldNotBeFound("zindagiPatrakDate.equals=" + UPDATED_ZINDAGI_PATRAK_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiPatrakDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiPatrakDate in DEFAULT_ZINDAGI_PATRAK_DATE or UPDATED_ZINDAGI_PATRAK_DATE
        defaultKamalSocietyShouldBeFound("zindagiPatrakDate.in=" + DEFAULT_ZINDAGI_PATRAK_DATE + "," + UPDATED_ZINDAGI_PATRAK_DATE);

        // Get all the kamalSocietyList where zindagiPatrakDate equals to UPDATED_ZINDAGI_PATRAK_DATE
        defaultKamalSocietyShouldNotBeFound("zindagiPatrakDate.in=" + UPDATED_ZINDAGI_PATRAK_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiPatrakDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiPatrakDate is not null
        defaultKamalSocietyShouldBeFound("zindagiPatrakDate.specified=true");

        // Get all the kamalSocietyList where zindagiPatrakDate is null
        defaultKamalSocietyShouldNotBeFound("zindagiPatrakDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiPatrakDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiPatrakDateMr equals to DEFAULT_ZINDAGI_PATRAK_DATE_MR
        defaultKamalSocietyShouldBeFound("zindagiPatrakDateMr.equals=" + DEFAULT_ZINDAGI_PATRAK_DATE_MR);

        // Get all the kamalSocietyList where zindagiPatrakDateMr equals to UPDATED_ZINDAGI_PATRAK_DATE_MR
        defaultKamalSocietyShouldNotBeFound("zindagiPatrakDateMr.equals=" + UPDATED_ZINDAGI_PATRAK_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiPatrakDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiPatrakDateMr in DEFAULT_ZINDAGI_PATRAK_DATE_MR or UPDATED_ZINDAGI_PATRAK_DATE_MR
        defaultKamalSocietyShouldBeFound("zindagiPatrakDateMr.in=" + DEFAULT_ZINDAGI_PATRAK_DATE_MR + "," + UPDATED_ZINDAGI_PATRAK_DATE_MR);

        // Get all the kamalSocietyList where zindagiPatrakDateMr equals to UPDATED_ZINDAGI_PATRAK_DATE_MR
        defaultKamalSocietyShouldNotBeFound("zindagiPatrakDateMr.in=" + UPDATED_ZINDAGI_PATRAK_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiPatrakDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiPatrakDateMr is not null
        defaultKamalSocietyShouldBeFound("zindagiPatrakDateMr.specified=true");

        // Get all the kamalSocietyList where zindagiPatrakDateMr is null
        defaultKamalSocietyShouldNotBeFound("zindagiPatrakDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiPatrakDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiPatrakDateMr contains DEFAULT_ZINDAGI_PATRAK_DATE_MR
        defaultKamalSocietyShouldBeFound("zindagiPatrakDateMr.contains=" + DEFAULT_ZINDAGI_PATRAK_DATE_MR);

        // Get all the kamalSocietyList where zindagiPatrakDateMr contains UPDATED_ZINDAGI_PATRAK_DATE_MR
        defaultKamalSocietyShouldNotBeFound("zindagiPatrakDateMr.contains=" + UPDATED_ZINDAGI_PATRAK_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByZindagiPatrakDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where zindagiPatrakDateMr does not contain DEFAULT_ZINDAGI_PATRAK_DATE_MR
        defaultKamalSocietyShouldNotBeFound("zindagiPatrakDateMr.doesNotContain=" + DEFAULT_ZINDAGI_PATRAK_DATE_MR);

        // Get all the kamalSocietyList where zindagiPatrakDateMr does not contain UPDATED_ZINDAGI_PATRAK_DATE_MR
        defaultKamalSocietyShouldBeFound("zindagiPatrakDateMr.doesNotContain=" + UPDATED_ZINDAGI_PATRAK_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankTapasaniDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankTapasaniDate equals to DEFAULT_BANK_TAPASANI_DATE
        defaultKamalSocietyShouldBeFound("bankTapasaniDate.equals=" + DEFAULT_BANK_TAPASANI_DATE);

        // Get all the kamalSocietyList where bankTapasaniDate equals to UPDATED_BANK_TAPASANI_DATE
        defaultKamalSocietyShouldNotBeFound("bankTapasaniDate.equals=" + UPDATED_BANK_TAPASANI_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankTapasaniDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankTapasaniDate in DEFAULT_BANK_TAPASANI_DATE or UPDATED_BANK_TAPASANI_DATE
        defaultKamalSocietyShouldBeFound("bankTapasaniDate.in=" + DEFAULT_BANK_TAPASANI_DATE + "," + UPDATED_BANK_TAPASANI_DATE);

        // Get all the kamalSocietyList where bankTapasaniDate equals to UPDATED_BANK_TAPASANI_DATE
        defaultKamalSocietyShouldNotBeFound("bankTapasaniDate.in=" + UPDATED_BANK_TAPASANI_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankTapasaniDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankTapasaniDate is not null
        defaultKamalSocietyShouldBeFound("bankTapasaniDate.specified=true");

        // Get all the kamalSocietyList where bankTapasaniDate is null
        defaultKamalSocietyShouldNotBeFound("bankTapasaniDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankTapasaniDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankTapasaniDateMr equals to DEFAULT_BANK_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("bankTapasaniDateMr.equals=" + DEFAULT_BANK_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where bankTapasaniDateMr equals to UPDATED_BANK_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("bankTapasaniDateMr.equals=" + UPDATED_BANK_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankTapasaniDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankTapasaniDateMr in DEFAULT_BANK_TAPASANI_DATE_MR or UPDATED_BANK_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("bankTapasaniDateMr.in=" + DEFAULT_BANK_TAPASANI_DATE_MR + "," + UPDATED_BANK_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where bankTapasaniDateMr equals to UPDATED_BANK_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("bankTapasaniDateMr.in=" + UPDATED_BANK_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankTapasaniDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankTapasaniDateMr is not null
        defaultKamalSocietyShouldBeFound("bankTapasaniDateMr.specified=true");

        // Get all the kamalSocietyList where bankTapasaniDateMr is null
        defaultKamalSocietyShouldNotBeFound("bankTapasaniDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankTapasaniDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankTapasaniDateMr contains DEFAULT_BANK_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("bankTapasaniDateMr.contains=" + DEFAULT_BANK_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where bankTapasaniDateMr contains UPDATED_BANK_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("bankTapasaniDateMr.contains=" + UPDATED_BANK_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankTapasaniDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankTapasaniDateMr does not contain DEFAULT_BANK_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("bankTapasaniDateMr.doesNotContain=" + DEFAULT_BANK_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where bankTapasaniDateMr does not contain UPDATED_BANK_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("bankTapasaniDateMr.doesNotContain=" + UPDATED_BANK_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGovTapasaniDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where govTapasaniDate equals to DEFAULT_GOV_TAPASANI_DATE
        defaultKamalSocietyShouldBeFound("govTapasaniDate.equals=" + DEFAULT_GOV_TAPASANI_DATE);

        // Get all the kamalSocietyList where govTapasaniDate equals to UPDATED_GOV_TAPASANI_DATE
        defaultKamalSocietyShouldNotBeFound("govTapasaniDate.equals=" + UPDATED_GOV_TAPASANI_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGovTapasaniDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where govTapasaniDate in DEFAULT_GOV_TAPASANI_DATE or UPDATED_GOV_TAPASANI_DATE
        defaultKamalSocietyShouldBeFound("govTapasaniDate.in=" + DEFAULT_GOV_TAPASANI_DATE + "," + UPDATED_GOV_TAPASANI_DATE);

        // Get all the kamalSocietyList where govTapasaniDate equals to UPDATED_GOV_TAPASANI_DATE
        defaultKamalSocietyShouldNotBeFound("govTapasaniDate.in=" + UPDATED_GOV_TAPASANI_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGovTapasaniDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where govTapasaniDate is not null
        defaultKamalSocietyShouldBeFound("govTapasaniDate.specified=true");

        // Get all the kamalSocietyList where govTapasaniDate is null
        defaultKamalSocietyShouldNotBeFound("govTapasaniDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGovTapasaniDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where govTapasaniDateMr equals to DEFAULT_GOV_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("govTapasaniDateMr.equals=" + DEFAULT_GOV_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where govTapasaniDateMr equals to UPDATED_GOV_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("govTapasaniDateMr.equals=" + UPDATED_GOV_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGovTapasaniDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where govTapasaniDateMr in DEFAULT_GOV_TAPASANI_DATE_MR or UPDATED_GOV_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("govTapasaniDateMr.in=" + DEFAULT_GOV_TAPASANI_DATE_MR + "," + UPDATED_GOV_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where govTapasaniDateMr equals to UPDATED_GOV_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("govTapasaniDateMr.in=" + UPDATED_GOV_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGovTapasaniDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where govTapasaniDateMr is not null
        defaultKamalSocietyShouldBeFound("govTapasaniDateMr.specified=true");

        // Get all the kamalSocietyList where govTapasaniDateMr is null
        defaultKamalSocietyShouldNotBeFound("govTapasaniDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGovTapasaniDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where govTapasaniDateMr contains DEFAULT_GOV_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("govTapasaniDateMr.contains=" + DEFAULT_GOV_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where govTapasaniDateMr contains UPDATED_GOV_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("govTapasaniDateMr.contains=" + UPDATED_GOV_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGovTapasaniDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where govTapasaniDateMr does not contain DEFAULT_GOV_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("govTapasaniDateMr.doesNotContain=" + DEFAULT_GOV_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where govTapasaniDateMr does not contain UPDATED_GOV_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("govTapasaniDateMr.doesNotContain=" + UPDATED_GOV_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniDate equals to DEFAULT_SANSTHA_TAPASANI_DATE
        defaultKamalSocietyShouldBeFound("sansthaTapasaniDate.equals=" + DEFAULT_SANSTHA_TAPASANI_DATE);

        // Get all the kamalSocietyList where sansthaTapasaniDate equals to UPDATED_SANSTHA_TAPASANI_DATE
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniDate.equals=" + UPDATED_SANSTHA_TAPASANI_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniDate in DEFAULT_SANSTHA_TAPASANI_DATE or UPDATED_SANSTHA_TAPASANI_DATE
        defaultKamalSocietyShouldBeFound("sansthaTapasaniDate.in=" + DEFAULT_SANSTHA_TAPASANI_DATE + "," + UPDATED_SANSTHA_TAPASANI_DATE);

        // Get all the kamalSocietyList where sansthaTapasaniDate equals to UPDATED_SANSTHA_TAPASANI_DATE
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniDate.in=" + UPDATED_SANSTHA_TAPASANI_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniDate is not null
        defaultKamalSocietyShouldBeFound("sansthaTapasaniDate.specified=true");

        // Get all the kamalSocietyList where sansthaTapasaniDate is null
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniDateMr equals to DEFAULT_SANSTHA_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("sansthaTapasaniDateMr.equals=" + DEFAULT_SANSTHA_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where sansthaTapasaniDateMr equals to UPDATED_SANSTHA_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniDateMr.equals=" + UPDATED_SANSTHA_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniDateMr in DEFAULT_SANSTHA_TAPASANI_DATE_MR or UPDATED_SANSTHA_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound(
            "sansthaTapasaniDateMr.in=" + DEFAULT_SANSTHA_TAPASANI_DATE_MR + "," + UPDATED_SANSTHA_TAPASANI_DATE_MR
        );

        // Get all the kamalSocietyList where sansthaTapasaniDateMr equals to UPDATED_SANSTHA_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniDateMr.in=" + UPDATED_SANSTHA_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniDateMr is not null
        defaultKamalSocietyShouldBeFound("sansthaTapasaniDateMr.specified=true");

        // Get all the kamalSocietyList where sansthaTapasaniDateMr is null
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniDateMr contains DEFAULT_SANSTHA_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("sansthaTapasaniDateMr.contains=" + DEFAULT_SANSTHA_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where sansthaTapasaniDateMr contains UPDATED_SANSTHA_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniDateMr.contains=" + UPDATED_SANSTHA_TAPASANI_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniDateMr does not contain DEFAULT_SANSTHA_TAPASANI_DATE_MR
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniDateMr.doesNotContain=" + DEFAULT_SANSTHA_TAPASANI_DATE_MR);

        // Get all the kamalSocietyList where sansthaTapasaniDateMr does not contain UPDATED_SANSTHA_TAPASANI_DATE_MR
        defaultKamalSocietyShouldBeFound("sansthaTapasaniDateMr.doesNotContain=" + UPDATED_SANSTHA_TAPASANI_DATE_MR);
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
    void getAllKamalSocietiesByTotalLandContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand contains DEFAULT_TOTAL_LAND
        defaultKamalSocietyShouldBeFound("totalLand.contains=" + DEFAULT_TOTAL_LAND);

        // Get all the kamalSocietyList where totalLand contains UPDATED_TOTAL_LAND
        defaultKamalSocietyShouldNotBeFound("totalLand.contains=" + UPDATED_TOTAL_LAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLandNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLand does not contain DEFAULT_TOTAL_LAND
        defaultKamalSocietyShouldNotBeFound("totalLand.doesNotContain=" + DEFAULT_TOTAL_LAND);

        // Get all the kamalSocietyList where totalLand does not contain UPDATED_TOTAL_LAND
        defaultKamalSocietyShouldBeFound("totalLand.doesNotContain=" + UPDATED_TOTAL_LAND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBagayatIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bagayat equals to DEFAULT_BAGAYAT
        defaultKamalSocietyShouldBeFound("bagayat.equals=" + DEFAULT_BAGAYAT);

        // Get all the kamalSocietyList where bagayat equals to UPDATED_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("bagayat.equals=" + UPDATED_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBagayatIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bagayat in DEFAULT_BAGAYAT or UPDATED_BAGAYAT
        defaultKamalSocietyShouldBeFound("bagayat.in=" + DEFAULT_BAGAYAT + "," + UPDATED_BAGAYAT);

        // Get all the kamalSocietyList where bagayat equals to UPDATED_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("bagayat.in=" + UPDATED_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBagayatIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bagayat is not null
        defaultKamalSocietyShouldBeFound("bagayat.specified=true");

        // Get all the kamalSocietyList where bagayat is null
        defaultKamalSocietyShouldNotBeFound("bagayat.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBagayatContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bagayat contains DEFAULT_BAGAYAT
        defaultKamalSocietyShouldBeFound("bagayat.contains=" + DEFAULT_BAGAYAT);

        // Get all the kamalSocietyList where bagayat contains UPDATED_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("bagayat.contains=" + UPDATED_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBagayatNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bagayat does not contain DEFAULT_BAGAYAT
        defaultKamalSocietyShouldNotBeFound("bagayat.doesNotContain=" + DEFAULT_BAGAYAT);

        // Get all the kamalSocietyList where bagayat does not contain UPDATED_BAGAYAT
        defaultKamalSocietyShouldBeFound("bagayat.doesNotContain=" + UPDATED_BAGAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByJirayatIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where jirayat equals to DEFAULT_JIRAYAT
        defaultKamalSocietyShouldBeFound("jirayat.equals=" + DEFAULT_JIRAYAT);

        // Get all the kamalSocietyList where jirayat equals to UPDATED_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("jirayat.equals=" + UPDATED_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByJirayatIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where jirayat in DEFAULT_JIRAYAT or UPDATED_JIRAYAT
        defaultKamalSocietyShouldBeFound("jirayat.in=" + DEFAULT_JIRAYAT + "," + UPDATED_JIRAYAT);

        // Get all the kamalSocietyList where jirayat equals to UPDATED_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("jirayat.in=" + UPDATED_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByJirayatIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where jirayat is not null
        defaultKamalSocietyShouldBeFound("jirayat.specified=true");

        // Get all the kamalSocietyList where jirayat is null
        defaultKamalSocietyShouldNotBeFound("jirayat.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByJirayatContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where jirayat contains DEFAULT_JIRAYAT
        defaultKamalSocietyShouldBeFound("jirayat.contains=" + DEFAULT_JIRAYAT);

        // Get all the kamalSocietyList where jirayat contains UPDATED_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("jirayat.contains=" + UPDATED_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByJirayatNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where jirayat does not contain DEFAULT_JIRAYAT
        defaultKamalSocietyShouldNotBeFound("jirayat.doesNotContain=" + DEFAULT_JIRAYAT);

        // Get all the kamalSocietyList where jirayat does not contain UPDATED_JIRAYAT
        defaultKamalSocietyShouldBeFound("jirayat.doesNotContain=" + UPDATED_JIRAYAT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalFarmerIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalFarmer equals to DEFAULT_TOTAL_FARMER
        defaultKamalSocietyShouldBeFound("totalFarmer.equals=" + DEFAULT_TOTAL_FARMER);

        // Get all the kamalSocietyList where totalFarmer equals to UPDATED_TOTAL_FARMER
        defaultKamalSocietyShouldNotBeFound("totalFarmer.equals=" + UPDATED_TOTAL_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalFarmerIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalFarmer in DEFAULT_TOTAL_FARMER or UPDATED_TOTAL_FARMER
        defaultKamalSocietyShouldBeFound("totalFarmer.in=" + DEFAULT_TOTAL_FARMER + "," + UPDATED_TOTAL_FARMER);

        // Get all the kamalSocietyList where totalFarmer equals to UPDATED_TOTAL_FARMER
        defaultKamalSocietyShouldNotBeFound("totalFarmer.in=" + UPDATED_TOTAL_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalFarmerIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalFarmer is not null
        defaultKamalSocietyShouldBeFound("totalFarmer.specified=true");

        // Get all the kamalSocietyList where totalFarmer is null
        defaultKamalSocietyShouldNotBeFound("totalFarmer.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalFarmerContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalFarmer contains DEFAULT_TOTAL_FARMER
        defaultKamalSocietyShouldBeFound("totalFarmer.contains=" + DEFAULT_TOTAL_FARMER);

        // Get all the kamalSocietyList where totalFarmer contains UPDATED_TOTAL_FARMER
        defaultKamalSocietyShouldNotBeFound("totalFarmer.contains=" + UPDATED_TOTAL_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalFarmerNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalFarmer does not contain DEFAULT_TOTAL_FARMER
        defaultKamalSocietyShouldNotBeFound("totalFarmer.doesNotContain=" + DEFAULT_TOTAL_FARMER);

        // Get all the kamalSocietyList where totalFarmer does not contain UPDATED_TOTAL_FARMER
        defaultKamalSocietyShouldBeFound("totalFarmer.doesNotContain=" + UPDATED_TOTAL_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemberFarmerIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memberFarmer equals to DEFAULT_MEMBER_FARMER
        defaultKamalSocietyShouldBeFound("memberFarmer.equals=" + DEFAULT_MEMBER_FARMER);

        // Get all the kamalSocietyList where memberFarmer equals to UPDATED_MEMBER_FARMER
        defaultKamalSocietyShouldNotBeFound("memberFarmer.equals=" + UPDATED_MEMBER_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemberFarmerIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memberFarmer in DEFAULT_MEMBER_FARMER or UPDATED_MEMBER_FARMER
        defaultKamalSocietyShouldBeFound("memberFarmer.in=" + DEFAULT_MEMBER_FARMER + "," + UPDATED_MEMBER_FARMER);

        // Get all the kamalSocietyList where memberFarmer equals to UPDATED_MEMBER_FARMER
        defaultKamalSocietyShouldNotBeFound("memberFarmer.in=" + UPDATED_MEMBER_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemberFarmerIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memberFarmer is not null
        defaultKamalSocietyShouldBeFound("memberFarmer.specified=true");

        // Get all the kamalSocietyList where memberFarmer is null
        defaultKamalSocietyShouldNotBeFound("memberFarmer.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemberFarmerContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memberFarmer contains DEFAULT_MEMBER_FARMER
        defaultKamalSocietyShouldBeFound("memberFarmer.contains=" + DEFAULT_MEMBER_FARMER);

        // Get all the kamalSocietyList where memberFarmer contains UPDATED_MEMBER_FARMER
        defaultKamalSocietyShouldNotBeFound("memberFarmer.contains=" + UPDATED_MEMBER_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemberFarmerNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memberFarmer does not contain DEFAULT_MEMBER_FARMER
        defaultKamalSocietyShouldNotBeFound("memberFarmer.doesNotContain=" + DEFAULT_MEMBER_FARMER);

        // Get all the kamalSocietyList where memberFarmer does not contain UPDATED_MEMBER_FARMER
        defaultKamalSocietyShouldBeFound("memberFarmer.doesNotContain=" + UPDATED_MEMBER_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByNonMemberFarmerIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where nonMemberFarmer equals to DEFAULT_NON_MEMBER_FARMER
        defaultKamalSocietyShouldBeFound("nonMemberFarmer.equals=" + DEFAULT_NON_MEMBER_FARMER);

        // Get all the kamalSocietyList where nonMemberFarmer equals to UPDATED_NON_MEMBER_FARMER
        defaultKamalSocietyShouldNotBeFound("nonMemberFarmer.equals=" + UPDATED_NON_MEMBER_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByNonMemberFarmerIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where nonMemberFarmer in DEFAULT_NON_MEMBER_FARMER or UPDATED_NON_MEMBER_FARMER
        defaultKamalSocietyShouldBeFound("nonMemberFarmer.in=" + DEFAULT_NON_MEMBER_FARMER + "," + UPDATED_NON_MEMBER_FARMER);

        // Get all the kamalSocietyList where nonMemberFarmer equals to UPDATED_NON_MEMBER_FARMER
        defaultKamalSocietyShouldNotBeFound("nonMemberFarmer.in=" + UPDATED_NON_MEMBER_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByNonMemberFarmerIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where nonMemberFarmer is not null
        defaultKamalSocietyShouldBeFound("nonMemberFarmer.specified=true");

        // Get all the kamalSocietyList where nonMemberFarmer is null
        defaultKamalSocietyShouldNotBeFound("nonMemberFarmer.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByNonMemberFarmerContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where nonMemberFarmer contains DEFAULT_NON_MEMBER_FARMER
        defaultKamalSocietyShouldBeFound("nonMemberFarmer.contains=" + DEFAULT_NON_MEMBER_FARMER);

        // Get all the kamalSocietyList where nonMemberFarmer contains UPDATED_NON_MEMBER_FARMER
        defaultKamalSocietyShouldNotBeFound("nonMemberFarmer.contains=" + UPDATED_NON_MEMBER_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByNonMemberFarmerNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where nonMemberFarmer does not contain DEFAULT_NON_MEMBER_FARMER
        defaultKamalSocietyShouldNotBeFound("nonMemberFarmer.doesNotContain=" + DEFAULT_NON_MEMBER_FARMER);

        // Get all the kamalSocietyList where nonMemberFarmer does not contain UPDATED_NON_MEMBER_FARMER
        defaultKamalSocietyShouldBeFound("nonMemberFarmer.doesNotContain=" + UPDATED_NON_MEMBER_FARMER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalebandDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talebandDate equals to DEFAULT_TALEBAND_DATE
        defaultKamalSocietyShouldBeFound("talebandDate.equals=" + DEFAULT_TALEBAND_DATE);

        // Get all the kamalSocietyList where talebandDate equals to UPDATED_TALEBAND_DATE
        defaultKamalSocietyShouldNotBeFound("talebandDate.equals=" + UPDATED_TALEBAND_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalebandDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talebandDate in DEFAULT_TALEBAND_DATE or UPDATED_TALEBAND_DATE
        defaultKamalSocietyShouldBeFound("talebandDate.in=" + DEFAULT_TALEBAND_DATE + "," + UPDATED_TALEBAND_DATE);

        // Get all the kamalSocietyList where talebandDate equals to UPDATED_TALEBAND_DATE
        defaultKamalSocietyShouldNotBeFound("talebandDate.in=" + UPDATED_TALEBAND_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTalebandDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where talebandDate is not null
        defaultKamalSocietyShouldBeFound("talebandDate.specified=true");

        // Get all the kamalSocietyList where talebandDate is null
        defaultKamalSocietyShouldNotBeFound("talebandDate.specified=false");
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
    void getAllKamalSocietiesByMemLoanContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan contains DEFAULT_MEM_LOAN
        defaultKamalSocietyShouldBeFound("memLoan.contains=" + DEFAULT_MEM_LOAN);

        // Get all the kamalSocietyList where memLoan contains UPDATED_MEM_LOAN
        defaultKamalSocietyShouldNotBeFound("memLoan.contains=" + UPDATED_MEM_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemLoanNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memLoan does not contain DEFAULT_MEM_LOAN
        defaultKamalSocietyShouldNotBeFound("memLoan.doesNotContain=" + DEFAULT_MEM_LOAN);

        // Get all the kamalSocietyList where memLoan does not contain UPDATED_MEM_LOAN
        defaultKamalSocietyShouldBeFound("memLoan.doesNotContain=" + UPDATED_MEM_LOAN);
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
    void getAllKamalSocietiesByMemDueContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue contains DEFAULT_MEM_DUE
        defaultKamalSocietyShouldBeFound("memDue.contains=" + DEFAULT_MEM_DUE);

        // Get all the kamalSocietyList where memDue contains UPDATED_MEM_DUE
        defaultKamalSocietyShouldNotBeFound("memDue.contains=" + UPDATED_MEM_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemDueNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memDue does not contain DEFAULT_MEM_DUE
        defaultKamalSocietyShouldNotBeFound("memDue.doesNotContain=" + DEFAULT_MEM_DUE);

        // Get all the kamalSocietyList where memDue does not contain UPDATED_MEM_DUE
        defaultKamalSocietyShouldBeFound("memDue.doesNotContain=" + UPDATED_MEM_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuli equals to DEFAULT_MEM_VASULI
        defaultKamalSocietyShouldBeFound("memVasuli.equals=" + DEFAULT_MEM_VASULI);

        // Get all the kamalSocietyList where memVasuli equals to UPDATED_MEM_VASULI
        defaultKamalSocietyShouldNotBeFound("memVasuli.equals=" + UPDATED_MEM_VASULI);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuli in DEFAULT_MEM_VASULI or UPDATED_MEM_VASULI
        defaultKamalSocietyShouldBeFound("memVasuli.in=" + DEFAULT_MEM_VASULI + "," + UPDATED_MEM_VASULI);

        // Get all the kamalSocietyList where memVasuli equals to UPDATED_MEM_VASULI
        defaultKamalSocietyShouldNotBeFound("memVasuli.in=" + UPDATED_MEM_VASULI);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuli is not null
        defaultKamalSocietyShouldBeFound("memVasuli.specified=true");

        // Get all the kamalSocietyList where memVasuli is null
        defaultKamalSocietyShouldNotBeFound("memVasuli.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuli contains DEFAULT_MEM_VASULI
        defaultKamalSocietyShouldBeFound("memVasuli.contains=" + DEFAULT_MEM_VASULI);

        // Get all the kamalSocietyList where memVasuli contains UPDATED_MEM_VASULI
        defaultKamalSocietyShouldNotBeFound("memVasuli.contains=" + UPDATED_MEM_VASULI);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuli does not contain DEFAULT_MEM_VASULI
        defaultKamalSocietyShouldNotBeFound("memVasuli.doesNotContain=" + DEFAULT_MEM_VASULI);

        // Get all the kamalSocietyList where memVasuli does not contain UPDATED_MEM_VASULI
        defaultKamalSocietyShouldBeFound("memVasuli.doesNotContain=" + UPDATED_MEM_VASULI);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliPerIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuliPer equals to DEFAULT_MEM_VASULI_PER
        defaultKamalSocietyShouldBeFound("memVasuliPer.equals=" + DEFAULT_MEM_VASULI_PER);

        // Get all the kamalSocietyList where memVasuliPer equals to UPDATED_MEM_VASULI_PER
        defaultKamalSocietyShouldNotBeFound("memVasuliPer.equals=" + UPDATED_MEM_VASULI_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliPerIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuliPer in DEFAULT_MEM_VASULI_PER or UPDATED_MEM_VASULI_PER
        defaultKamalSocietyShouldBeFound("memVasuliPer.in=" + DEFAULT_MEM_VASULI_PER + "," + UPDATED_MEM_VASULI_PER);

        // Get all the kamalSocietyList where memVasuliPer equals to UPDATED_MEM_VASULI_PER
        defaultKamalSocietyShouldNotBeFound("memVasuliPer.in=" + UPDATED_MEM_VASULI_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuliPer is not null
        defaultKamalSocietyShouldBeFound("memVasuliPer.specified=true");

        // Get all the kamalSocietyList where memVasuliPer is null
        defaultKamalSocietyShouldNotBeFound("memVasuliPer.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliPerContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuliPer contains DEFAULT_MEM_VASULI_PER
        defaultKamalSocietyShouldBeFound("memVasuliPer.contains=" + DEFAULT_MEM_VASULI_PER);

        // Get all the kamalSocietyList where memVasuliPer contains UPDATED_MEM_VASULI_PER
        defaultKamalSocietyShouldNotBeFound("memVasuliPer.contains=" + UPDATED_MEM_VASULI_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByMemVasuliPerNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where memVasuliPer does not contain DEFAULT_MEM_VASULI_PER
        defaultKamalSocietyShouldNotBeFound("memVasuliPer.doesNotContain=" + DEFAULT_MEM_VASULI_PER);

        // Get all the kamalSocietyList where memVasuliPer does not contain UPDATED_MEM_VASULI_PER
        defaultKamalSocietyShouldBeFound("memVasuliPer.doesNotContain=" + UPDATED_MEM_VASULI_PER);
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
    void getAllKamalSocietiesByBankLoanContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan contains DEFAULT_BANK_LOAN
        defaultKamalSocietyShouldBeFound("bankLoan.contains=" + DEFAULT_BANK_LOAN);

        // Get all the kamalSocietyList where bankLoan contains UPDATED_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("bankLoan.contains=" + UPDATED_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankLoanNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankLoan does not contain DEFAULT_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("bankLoan.doesNotContain=" + DEFAULT_BANK_LOAN);

        // Get all the kamalSocietyList where bankLoan does not contain UPDATED_BANK_LOAN
        defaultKamalSocietyShouldBeFound("bankLoan.doesNotContain=" + UPDATED_BANK_LOAN);
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
    void getAllKamalSocietiesByBankDueContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue contains DEFAULT_BANK_DUE
        defaultKamalSocietyShouldBeFound("bankDue.contains=" + DEFAULT_BANK_DUE);

        // Get all the kamalSocietyList where bankDue contains UPDATED_BANK_DUE
        defaultKamalSocietyShouldNotBeFound("bankDue.contains=" + UPDATED_BANK_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankDueNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankDue does not contain DEFAULT_BANK_DUE
        defaultKamalSocietyShouldNotBeFound("bankDue.doesNotContain=" + DEFAULT_BANK_DUE);

        // Get all the kamalSocietyList where bankDue does not contain UPDATED_BANK_DUE
        defaultKamalSocietyShouldBeFound("bankDue.doesNotContain=" + UPDATED_BANK_DUE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuli equals to DEFAULT_BANK_VASULI
        defaultKamalSocietyShouldBeFound("bankVasuli.equals=" + DEFAULT_BANK_VASULI);

        // Get all the kamalSocietyList where bankVasuli equals to UPDATED_BANK_VASULI
        defaultKamalSocietyShouldNotBeFound("bankVasuli.equals=" + UPDATED_BANK_VASULI);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuli in DEFAULT_BANK_VASULI or UPDATED_BANK_VASULI
        defaultKamalSocietyShouldBeFound("bankVasuli.in=" + DEFAULT_BANK_VASULI + "," + UPDATED_BANK_VASULI);

        // Get all the kamalSocietyList where bankVasuli equals to UPDATED_BANK_VASULI
        defaultKamalSocietyShouldNotBeFound("bankVasuli.in=" + UPDATED_BANK_VASULI);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuli is not null
        defaultKamalSocietyShouldBeFound("bankVasuli.specified=true");

        // Get all the kamalSocietyList where bankVasuli is null
        defaultKamalSocietyShouldNotBeFound("bankVasuli.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuli contains DEFAULT_BANK_VASULI
        defaultKamalSocietyShouldBeFound("bankVasuli.contains=" + DEFAULT_BANK_VASULI);

        // Get all the kamalSocietyList where bankVasuli contains UPDATED_BANK_VASULI
        defaultKamalSocietyShouldNotBeFound("bankVasuli.contains=" + UPDATED_BANK_VASULI);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuli does not contain DEFAULT_BANK_VASULI
        defaultKamalSocietyShouldNotBeFound("bankVasuli.doesNotContain=" + DEFAULT_BANK_VASULI);

        // Get all the kamalSocietyList where bankVasuli does not contain UPDATED_BANK_VASULI
        defaultKamalSocietyShouldBeFound("bankVasuli.doesNotContain=" + UPDATED_BANK_VASULI);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliPerIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuliPer equals to DEFAULT_BANK_VASULI_PER
        defaultKamalSocietyShouldBeFound("bankVasuliPer.equals=" + DEFAULT_BANK_VASULI_PER);

        // Get all the kamalSocietyList where bankVasuliPer equals to UPDATED_BANK_VASULI_PER
        defaultKamalSocietyShouldNotBeFound("bankVasuliPer.equals=" + UPDATED_BANK_VASULI_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliPerIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuliPer in DEFAULT_BANK_VASULI_PER or UPDATED_BANK_VASULI_PER
        defaultKamalSocietyShouldBeFound("bankVasuliPer.in=" + DEFAULT_BANK_VASULI_PER + "," + UPDATED_BANK_VASULI_PER);

        // Get all the kamalSocietyList where bankVasuliPer equals to UPDATED_BANK_VASULI_PER
        defaultKamalSocietyShouldNotBeFound("bankVasuliPer.in=" + UPDATED_BANK_VASULI_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuliPer is not null
        defaultKamalSocietyShouldBeFound("bankVasuliPer.specified=true");

        // Get all the kamalSocietyList where bankVasuliPer is null
        defaultKamalSocietyShouldNotBeFound("bankVasuliPer.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliPerContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuliPer contains DEFAULT_BANK_VASULI_PER
        defaultKamalSocietyShouldBeFound("bankVasuliPer.contains=" + DEFAULT_BANK_VASULI_PER);

        // Get all the kamalSocietyList where bankVasuliPer contains UPDATED_BANK_VASULI_PER
        defaultKamalSocietyShouldNotBeFound("bankVasuliPer.contains=" + UPDATED_BANK_VASULI_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBankVasuliPerNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where bankVasuliPer does not contain DEFAULT_BANK_VASULI_PER
        defaultKamalSocietyShouldNotBeFound("bankVasuliPer.doesNotContain=" + DEFAULT_BANK_VASULI_PER);

        // Get all the kamalSocietyList where bankVasuliPer does not contain UPDATED_BANK_VASULI_PER
        defaultKamalSocietyShouldBeFound("bankVasuliPer.doesNotContain=" + UPDATED_BANK_VASULI_PER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBalanceSheetDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where balanceSheetDate equals to DEFAULT_BALANCE_SHEET_DATE
        defaultKamalSocietyShouldBeFound("balanceSheetDate.equals=" + DEFAULT_BALANCE_SHEET_DATE);

        // Get all the kamalSocietyList where balanceSheetDate equals to UPDATED_BALANCE_SHEET_DATE
        defaultKamalSocietyShouldNotBeFound("balanceSheetDate.equals=" + UPDATED_BALANCE_SHEET_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBalanceSheetDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where balanceSheetDate in DEFAULT_BALANCE_SHEET_DATE or UPDATED_BALANCE_SHEET_DATE
        defaultKamalSocietyShouldBeFound("balanceSheetDate.in=" + DEFAULT_BALANCE_SHEET_DATE + "," + UPDATED_BALANCE_SHEET_DATE);

        // Get all the kamalSocietyList where balanceSheetDate equals to UPDATED_BALANCE_SHEET_DATE
        defaultKamalSocietyShouldNotBeFound("balanceSheetDate.in=" + UPDATED_BALANCE_SHEET_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBalanceSheetDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where balanceSheetDate is not null
        defaultKamalSocietyShouldBeFound("balanceSheetDate.specified=true");

        // Get all the kamalSocietyList where balanceSheetDate is null
        defaultKamalSocietyShouldNotBeFound("balanceSheetDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBalanceSheetDateMrIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where balanceSheetDateMr equals to DEFAULT_BALANCE_SHEET_DATE_MR
        defaultKamalSocietyShouldBeFound("balanceSheetDateMr.equals=" + DEFAULT_BALANCE_SHEET_DATE_MR);

        // Get all the kamalSocietyList where balanceSheetDateMr equals to UPDATED_BALANCE_SHEET_DATE_MR
        defaultKamalSocietyShouldNotBeFound("balanceSheetDateMr.equals=" + UPDATED_BALANCE_SHEET_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBalanceSheetDateMrIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where balanceSheetDateMr in DEFAULT_BALANCE_SHEET_DATE_MR or UPDATED_BALANCE_SHEET_DATE_MR
        defaultKamalSocietyShouldBeFound("balanceSheetDateMr.in=" + DEFAULT_BALANCE_SHEET_DATE_MR + "," + UPDATED_BALANCE_SHEET_DATE_MR);

        // Get all the kamalSocietyList where balanceSheetDateMr equals to UPDATED_BALANCE_SHEET_DATE_MR
        defaultKamalSocietyShouldNotBeFound("balanceSheetDateMr.in=" + UPDATED_BALANCE_SHEET_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBalanceSheetDateMrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where balanceSheetDateMr is not null
        defaultKamalSocietyShouldBeFound("balanceSheetDateMr.specified=true");

        // Get all the kamalSocietyList where balanceSheetDateMr is null
        defaultKamalSocietyShouldNotBeFound("balanceSheetDateMr.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBalanceSheetDateMrContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where balanceSheetDateMr contains DEFAULT_BALANCE_SHEET_DATE_MR
        defaultKamalSocietyShouldBeFound("balanceSheetDateMr.contains=" + DEFAULT_BALANCE_SHEET_DATE_MR);

        // Get all the kamalSocietyList where balanceSheetDateMr contains UPDATED_BALANCE_SHEET_DATE_MR
        defaultKamalSocietyShouldNotBeFound("balanceSheetDateMr.contains=" + UPDATED_BALANCE_SHEET_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBalanceSheetDateMrNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where balanceSheetDateMr does not contain DEFAULT_BALANCE_SHEET_DATE_MR
        defaultKamalSocietyShouldNotBeFound("balanceSheetDateMr.doesNotContain=" + DEFAULT_BALANCE_SHEET_DATE_MR);

        // Get all the kamalSocietyList where balanceSheetDateMr does not contain UPDATED_BALANCE_SHEET_DATE_MR
        defaultKamalSocietyShouldBeFound("balanceSheetDateMr.doesNotContain=" + UPDATED_BALANCE_SHEET_DATE_MR);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityAdhikrutShareCapitalIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital equals to DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("liabilityAdhikrutShareCapital.equals=" + DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL);

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital equals to UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("liabilityAdhikrutShareCapital.equals=" + UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityAdhikrutShareCapitalIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital in DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL or UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound(
            "liabilityAdhikrutShareCapital.in=" + DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL + "," + UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        );

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital equals to UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("liabilityAdhikrutShareCapital.in=" + UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityAdhikrutShareCapitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital is not null
        defaultKamalSocietyShouldBeFound("liabilityAdhikrutShareCapital.specified=true");

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital is null
        defaultKamalSocietyShouldNotBeFound("liabilityAdhikrutShareCapital.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityAdhikrutShareCapitalContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital contains DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("liabilityAdhikrutShareCapital.contains=" + DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL);

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital contains UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("liabilityAdhikrutShareCapital.contains=" + UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityAdhikrutShareCapitalNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital does not contain DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("liabilityAdhikrutShareCapital.doesNotContain=" + DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL);

        // Get all the kamalSocietyList where liabilityAdhikrutShareCapital does not contain UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("liabilityAdhikrutShareCapital.doesNotContain=" + UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityVasulShareCapitalIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityVasulShareCapital equals to DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("liabilityVasulShareCapital.equals=" + DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL);

        // Get all the kamalSocietyList where liabilityVasulShareCapital equals to UPDATED_LIABILITY_VASUL_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("liabilityVasulShareCapital.equals=" + UPDATED_LIABILITY_VASUL_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityVasulShareCapitalIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityVasulShareCapital in DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL or UPDATED_LIABILITY_VASUL_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound(
            "liabilityVasulShareCapital.in=" + DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL + "," + UPDATED_LIABILITY_VASUL_SHARE_CAPITAL
        );

        // Get all the kamalSocietyList where liabilityVasulShareCapital equals to UPDATED_LIABILITY_VASUL_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("liabilityVasulShareCapital.in=" + UPDATED_LIABILITY_VASUL_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityVasulShareCapitalIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityVasulShareCapital is not null
        defaultKamalSocietyShouldBeFound("liabilityVasulShareCapital.specified=true");

        // Get all the kamalSocietyList where liabilityVasulShareCapital is null
        defaultKamalSocietyShouldNotBeFound("liabilityVasulShareCapital.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityVasulShareCapitalContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityVasulShareCapital contains DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("liabilityVasulShareCapital.contains=" + DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL);

        // Get all the kamalSocietyList where liabilityVasulShareCapital contains UPDATED_LIABILITY_VASUL_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("liabilityVasulShareCapital.contains=" + UPDATED_LIABILITY_VASUL_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityVasulShareCapitalNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityVasulShareCapital does not contain DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL
        defaultKamalSocietyShouldNotBeFound("liabilityVasulShareCapital.doesNotContain=" + DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL);

        // Get all the kamalSocietyList where liabilityVasulShareCapital does not contain UPDATED_LIABILITY_VASUL_SHARE_CAPITAL
        defaultKamalSocietyShouldBeFound("liabilityVasulShareCapital.doesNotContain=" + UPDATED_LIABILITY_VASUL_SHARE_CAPITAL);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityFundIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityFund equals to DEFAULT_LIABILITY_FUND
        defaultKamalSocietyShouldBeFound("liabilityFund.equals=" + DEFAULT_LIABILITY_FUND);

        // Get all the kamalSocietyList where liabilityFund equals to UPDATED_LIABILITY_FUND
        defaultKamalSocietyShouldNotBeFound("liabilityFund.equals=" + UPDATED_LIABILITY_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityFundIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityFund in DEFAULT_LIABILITY_FUND or UPDATED_LIABILITY_FUND
        defaultKamalSocietyShouldBeFound("liabilityFund.in=" + DEFAULT_LIABILITY_FUND + "," + UPDATED_LIABILITY_FUND);

        // Get all the kamalSocietyList where liabilityFund equals to UPDATED_LIABILITY_FUND
        defaultKamalSocietyShouldNotBeFound("liabilityFund.in=" + UPDATED_LIABILITY_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityFundIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityFund is not null
        defaultKamalSocietyShouldBeFound("liabilityFund.specified=true");

        // Get all the kamalSocietyList where liabilityFund is null
        defaultKamalSocietyShouldNotBeFound("liabilityFund.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityFundContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityFund contains DEFAULT_LIABILITY_FUND
        defaultKamalSocietyShouldBeFound("liabilityFund.contains=" + DEFAULT_LIABILITY_FUND);

        // Get all the kamalSocietyList where liabilityFund contains UPDATED_LIABILITY_FUND
        defaultKamalSocietyShouldNotBeFound("liabilityFund.contains=" + UPDATED_LIABILITY_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityFundNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityFund does not contain DEFAULT_LIABILITY_FUND
        defaultKamalSocietyShouldNotBeFound("liabilityFund.doesNotContain=" + DEFAULT_LIABILITY_FUND);

        // Get all the kamalSocietyList where liabilityFund does not contain UPDATED_LIABILITY_FUND
        defaultKamalSocietyShouldBeFound("liabilityFund.doesNotContain=" + UPDATED_LIABILITY_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilitySpareFundIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilitySpareFund equals to DEFAULT_LIABILITY_SPARE_FUND
        defaultKamalSocietyShouldBeFound("liabilitySpareFund.equals=" + DEFAULT_LIABILITY_SPARE_FUND);

        // Get all the kamalSocietyList where liabilitySpareFund equals to UPDATED_LIABILITY_SPARE_FUND
        defaultKamalSocietyShouldNotBeFound("liabilitySpareFund.equals=" + UPDATED_LIABILITY_SPARE_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilitySpareFundIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilitySpareFund in DEFAULT_LIABILITY_SPARE_FUND or UPDATED_LIABILITY_SPARE_FUND
        defaultKamalSocietyShouldBeFound("liabilitySpareFund.in=" + DEFAULT_LIABILITY_SPARE_FUND + "," + UPDATED_LIABILITY_SPARE_FUND);

        // Get all the kamalSocietyList where liabilitySpareFund equals to UPDATED_LIABILITY_SPARE_FUND
        defaultKamalSocietyShouldNotBeFound("liabilitySpareFund.in=" + UPDATED_LIABILITY_SPARE_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilitySpareFundIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilitySpareFund is not null
        defaultKamalSocietyShouldBeFound("liabilitySpareFund.specified=true");

        // Get all the kamalSocietyList where liabilitySpareFund is null
        defaultKamalSocietyShouldNotBeFound("liabilitySpareFund.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilitySpareFundContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilitySpareFund contains DEFAULT_LIABILITY_SPARE_FUND
        defaultKamalSocietyShouldBeFound("liabilitySpareFund.contains=" + DEFAULT_LIABILITY_SPARE_FUND);

        // Get all the kamalSocietyList where liabilitySpareFund contains UPDATED_LIABILITY_SPARE_FUND
        defaultKamalSocietyShouldNotBeFound("liabilitySpareFund.contains=" + UPDATED_LIABILITY_SPARE_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilitySpareFundNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilitySpareFund does not contain DEFAULT_LIABILITY_SPARE_FUND
        defaultKamalSocietyShouldNotBeFound("liabilitySpareFund.doesNotContain=" + DEFAULT_LIABILITY_SPARE_FUND);

        // Get all the kamalSocietyList where liabilitySpareFund does not contain UPDATED_LIABILITY_SPARE_FUND
        defaultKamalSocietyShouldBeFound("liabilitySpareFund.doesNotContain=" + UPDATED_LIABILITY_SPARE_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityDepositeIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityDeposite equals to DEFAULT_LIABILITY_DEPOSITE
        defaultKamalSocietyShouldBeFound("liabilityDeposite.equals=" + DEFAULT_LIABILITY_DEPOSITE);

        // Get all the kamalSocietyList where liabilityDeposite equals to UPDATED_LIABILITY_DEPOSITE
        defaultKamalSocietyShouldNotBeFound("liabilityDeposite.equals=" + UPDATED_LIABILITY_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityDepositeIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityDeposite in DEFAULT_LIABILITY_DEPOSITE or UPDATED_LIABILITY_DEPOSITE
        defaultKamalSocietyShouldBeFound("liabilityDeposite.in=" + DEFAULT_LIABILITY_DEPOSITE + "," + UPDATED_LIABILITY_DEPOSITE);

        // Get all the kamalSocietyList where liabilityDeposite equals to UPDATED_LIABILITY_DEPOSITE
        defaultKamalSocietyShouldNotBeFound("liabilityDeposite.in=" + UPDATED_LIABILITY_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityDepositeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityDeposite is not null
        defaultKamalSocietyShouldBeFound("liabilityDeposite.specified=true");

        // Get all the kamalSocietyList where liabilityDeposite is null
        defaultKamalSocietyShouldNotBeFound("liabilityDeposite.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityDepositeContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityDeposite contains DEFAULT_LIABILITY_DEPOSITE
        defaultKamalSocietyShouldBeFound("liabilityDeposite.contains=" + DEFAULT_LIABILITY_DEPOSITE);

        // Get all the kamalSocietyList where liabilityDeposite contains UPDATED_LIABILITY_DEPOSITE
        defaultKamalSocietyShouldNotBeFound("liabilityDeposite.contains=" + UPDATED_LIABILITY_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityDepositeNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityDeposite does not contain DEFAULT_LIABILITY_DEPOSITE
        defaultKamalSocietyShouldNotBeFound("liabilityDeposite.doesNotContain=" + DEFAULT_LIABILITY_DEPOSITE);

        // Get all the kamalSocietyList where liabilityDeposite does not contain UPDATED_LIABILITY_DEPOSITE
        defaultKamalSocietyShouldBeFound("liabilityDeposite.doesNotContain=" + UPDATED_LIABILITY_DEPOSITE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityBalanceSheetBankLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan equals to DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN
        defaultKamalSocietyShouldBeFound("liabilityBalanceSheetBankLoan.equals=" + DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN);

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan equals to UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("liabilityBalanceSheetBankLoan.equals=" + UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityBalanceSheetBankLoanIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan in DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN or UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN
        defaultKamalSocietyShouldBeFound(
            "liabilityBalanceSheetBankLoan.in=" +
            DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN +
            "," +
            UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN
        );

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan equals to UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("liabilityBalanceSheetBankLoan.in=" + UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityBalanceSheetBankLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan is not null
        defaultKamalSocietyShouldBeFound("liabilityBalanceSheetBankLoan.specified=true");

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan is null
        defaultKamalSocietyShouldNotBeFound("liabilityBalanceSheetBankLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityBalanceSheetBankLoanContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan contains DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN
        defaultKamalSocietyShouldBeFound("liabilityBalanceSheetBankLoan.contains=" + DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN);

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan contains UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("liabilityBalanceSheetBankLoan.contains=" + UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityBalanceSheetBankLoanNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan does not contain DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN
        defaultKamalSocietyShouldNotBeFound("liabilityBalanceSheetBankLoan.doesNotContain=" + DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN);

        // Get all the kamalSocietyList where liabilityBalanceSheetBankLoan does not contain UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN
        defaultKamalSocietyShouldBeFound("liabilityBalanceSheetBankLoan.doesNotContain=" + UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityOtherPayableIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityOtherPayable equals to DEFAULT_LIABILITY_OTHER_PAYABLE
        defaultKamalSocietyShouldBeFound("liabilityOtherPayable.equals=" + DEFAULT_LIABILITY_OTHER_PAYABLE);

        // Get all the kamalSocietyList where liabilityOtherPayable equals to UPDATED_LIABILITY_OTHER_PAYABLE
        defaultKamalSocietyShouldNotBeFound("liabilityOtherPayable.equals=" + UPDATED_LIABILITY_OTHER_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityOtherPayableIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityOtherPayable in DEFAULT_LIABILITY_OTHER_PAYABLE or UPDATED_LIABILITY_OTHER_PAYABLE
        defaultKamalSocietyShouldBeFound(
            "liabilityOtherPayable.in=" + DEFAULT_LIABILITY_OTHER_PAYABLE + "," + UPDATED_LIABILITY_OTHER_PAYABLE
        );

        // Get all the kamalSocietyList where liabilityOtherPayable equals to UPDATED_LIABILITY_OTHER_PAYABLE
        defaultKamalSocietyShouldNotBeFound("liabilityOtherPayable.in=" + UPDATED_LIABILITY_OTHER_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityOtherPayableIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityOtherPayable is not null
        defaultKamalSocietyShouldBeFound("liabilityOtherPayable.specified=true");

        // Get all the kamalSocietyList where liabilityOtherPayable is null
        defaultKamalSocietyShouldNotBeFound("liabilityOtherPayable.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityOtherPayableContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityOtherPayable contains DEFAULT_LIABILITY_OTHER_PAYABLE
        defaultKamalSocietyShouldBeFound("liabilityOtherPayable.contains=" + DEFAULT_LIABILITY_OTHER_PAYABLE);

        // Get all the kamalSocietyList where liabilityOtherPayable contains UPDATED_LIABILITY_OTHER_PAYABLE
        defaultKamalSocietyShouldNotBeFound("liabilityOtherPayable.contains=" + UPDATED_LIABILITY_OTHER_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityOtherPayableNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityOtherPayable does not contain DEFAULT_LIABILITY_OTHER_PAYABLE
        defaultKamalSocietyShouldNotBeFound("liabilityOtherPayable.doesNotContain=" + DEFAULT_LIABILITY_OTHER_PAYABLE);

        // Get all the kamalSocietyList where liabilityOtherPayable does not contain UPDATED_LIABILITY_OTHER_PAYABLE
        defaultKamalSocietyShouldBeFound("liabilityOtherPayable.doesNotContain=" + UPDATED_LIABILITY_OTHER_PAYABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityProfit equals to DEFAULT_LIABILITY_PROFIT
        defaultKamalSocietyShouldBeFound("liabilityProfit.equals=" + DEFAULT_LIABILITY_PROFIT);

        // Get all the kamalSocietyList where liabilityProfit equals to UPDATED_LIABILITY_PROFIT
        defaultKamalSocietyShouldNotBeFound("liabilityProfit.equals=" + UPDATED_LIABILITY_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityProfitIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityProfit in DEFAULT_LIABILITY_PROFIT or UPDATED_LIABILITY_PROFIT
        defaultKamalSocietyShouldBeFound("liabilityProfit.in=" + DEFAULT_LIABILITY_PROFIT + "," + UPDATED_LIABILITY_PROFIT);

        // Get all the kamalSocietyList where liabilityProfit equals to UPDATED_LIABILITY_PROFIT
        defaultKamalSocietyShouldNotBeFound("liabilityProfit.in=" + UPDATED_LIABILITY_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityProfit is not null
        defaultKamalSocietyShouldBeFound("liabilityProfit.specified=true");

        // Get all the kamalSocietyList where liabilityProfit is null
        defaultKamalSocietyShouldNotBeFound("liabilityProfit.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityProfitContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityProfit contains DEFAULT_LIABILITY_PROFIT
        defaultKamalSocietyShouldBeFound("liabilityProfit.contains=" + DEFAULT_LIABILITY_PROFIT);

        // Get all the kamalSocietyList where liabilityProfit contains UPDATED_LIABILITY_PROFIT
        defaultKamalSocietyShouldNotBeFound("liabilityProfit.contains=" + UPDATED_LIABILITY_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByLiabilityProfitNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where liabilityProfit does not contain DEFAULT_LIABILITY_PROFIT
        defaultKamalSocietyShouldNotBeFound("liabilityProfit.doesNotContain=" + DEFAULT_LIABILITY_PROFIT);

        // Get all the kamalSocietyList where liabilityProfit does not contain UPDATED_LIABILITY_PROFIT
        defaultKamalSocietyShouldBeFound("liabilityProfit.doesNotContain=" + UPDATED_LIABILITY_PROFIT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetCashIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetCash equals to DEFAULT_ASSET_CASH
        defaultKamalSocietyShouldBeFound("assetCash.equals=" + DEFAULT_ASSET_CASH);

        // Get all the kamalSocietyList where assetCash equals to UPDATED_ASSET_CASH
        defaultKamalSocietyShouldNotBeFound("assetCash.equals=" + UPDATED_ASSET_CASH);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetCashIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetCash in DEFAULT_ASSET_CASH or UPDATED_ASSET_CASH
        defaultKamalSocietyShouldBeFound("assetCash.in=" + DEFAULT_ASSET_CASH + "," + UPDATED_ASSET_CASH);

        // Get all the kamalSocietyList where assetCash equals to UPDATED_ASSET_CASH
        defaultKamalSocietyShouldNotBeFound("assetCash.in=" + UPDATED_ASSET_CASH);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetCashIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetCash is not null
        defaultKamalSocietyShouldBeFound("assetCash.specified=true");

        // Get all the kamalSocietyList where assetCash is null
        defaultKamalSocietyShouldNotBeFound("assetCash.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetCashContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetCash contains DEFAULT_ASSET_CASH
        defaultKamalSocietyShouldBeFound("assetCash.contains=" + DEFAULT_ASSET_CASH);

        // Get all the kamalSocietyList where assetCash contains UPDATED_ASSET_CASH
        defaultKamalSocietyShouldNotBeFound("assetCash.contains=" + UPDATED_ASSET_CASH);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetCashNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetCash does not contain DEFAULT_ASSET_CASH
        defaultKamalSocietyShouldNotBeFound("assetCash.doesNotContain=" + DEFAULT_ASSET_CASH);

        // Get all the kamalSocietyList where assetCash does not contain UPDATED_ASSET_CASH
        defaultKamalSocietyShouldBeFound("assetCash.doesNotContain=" + UPDATED_ASSET_CASH);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetInvestmentIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetInvestment equals to DEFAULT_ASSET_INVESTMENT
        defaultKamalSocietyShouldBeFound("assetInvestment.equals=" + DEFAULT_ASSET_INVESTMENT);

        // Get all the kamalSocietyList where assetInvestment equals to UPDATED_ASSET_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("assetInvestment.equals=" + UPDATED_ASSET_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetInvestmentIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetInvestment in DEFAULT_ASSET_INVESTMENT or UPDATED_ASSET_INVESTMENT
        defaultKamalSocietyShouldBeFound("assetInvestment.in=" + DEFAULT_ASSET_INVESTMENT + "," + UPDATED_ASSET_INVESTMENT);

        // Get all the kamalSocietyList where assetInvestment equals to UPDATED_ASSET_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("assetInvestment.in=" + UPDATED_ASSET_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetInvestmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetInvestment is not null
        defaultKamalSocietyShouldBeFound("assetInvestment.specified=true");

        // Get all the kamalSocietyList where assetInvestment is null
        defaultKamalSocietyShouldNotBeFound("assetInvestment.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetInvestmentContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetInvestment contains DEFAULT_ASSET_INVESTMENT
        defaultKamalSocietyShouldBeFound("assetInvestment.contains=" + DEFAULT_ASSET_INVESTMENT);

        // Get all the kamalSocietyList where assetInvestment contains UPDATED_ASSET_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("assetInvestment.contains=" + UPDATED_ASSET_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetInvestmentNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetInvestment does not contain DEFAULT_ASSET_INVESTMENT
        defaultKamalSocietyShouldNotBeFound("assetInvestment.doesNotContain=" + DEFAULT_ASSET_INVESTMENT);

        // Get all the kamalSocietyList where assetInvestment does not contain UPDATED_ASSET_INVESTMENT
        defaultKamalSocietyShouldBeFound("assetInvestment.doesNotContain=" + UPDATED_ASSET_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetImaratFundIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetImaratFund equals to DEFAULT_ASSET_IMARAT_FUND
        defaultKamalSocietyShouldBeFound("assetImaratFund.equals=" + DEFAULT_ASSET_IMARAT_FUND);

        // Get all the kamalSocietyList where assetImaratFund equals to UPDATED_ASSET_IMARAT_FUND
        defaultKamalSocietyShouldNotBeFound("assetImaratFund.equals=" + UPDATED_ASSET_IMARAT_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetImaratFundIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetImaratFund in DEFAULT_ASSET_IMARAT_FUND or UPDATED_ASSET_IMARAT_FUND
        defaultKamalSocietyShouldBeFound("assetImaratFund.in=" + DEFAULT_ASSET_IMARAT_FUND + "," + UPDATED_ASSET_IMARAT_FUND);

        // Get all the kamalSocietyList where assetImaratFund equals to UPDATED_ASSET_IMARAT_FUND
        defaultKamalSocietyShouldNotBeFound("assetImaratFund.in=" + UPDATED_ASSET_IMARAT_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetImaratFundIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetImaratFund is not null
        defaultKamalSocietyShouldBeFound("assetImaratFund.specified=true");

        // Get all the kamalSocietyList where assetImaratFund is null
        defaultKamalSocietyShouldNotBeFound("assetImaratFund.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetImaratFundContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetImaratFund contains DEFAULT_ASSET_IMARAT_FUND
        defaultKamalSocietyShouldBeFound("assetImaratFund.contains=" + DEFAULT_ASSET_IMARAT_FUND);

        // Get all the kamalSocietyList where assetImaratFund contains UPDATED_ASSET_IMARAT_FUND
        defaultKamalSocietyShouldNotBeFound("assetImaratFund.contains=" + UPDATED_ASSET_IMARAT_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetImaratFundNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetImaratFund does not contain DEFAULT_ASSET_IMARAT_FUND
        defaultKamalSocietyShouldNotBeFound("assetImaratFund.doesNotContain=" + DEFAULT_ASSET_IMARAT_FUND);

        // Get all the kamalSocietyList where assetImaratFund does not contain UPDATED_ASSET_IMARAT_FUND
        defaultKamalSocietyShouldBeFound("assetImaratFund.doesNotContain=" + UPDATED_ASSET_IMARAT_FUND);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetMemberLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetMemberLoan equals to DEFAULT_ASSET_MEMBER_LOAN
        defaultKamalSocietyShouldBeFound("assetMemberLoan.equals=" + DEFAULT_ASSET_MEMBER_LOAN);

        // Get all the kamalSocietyList where assetMemberLoan equals to UPDATED_ASSET_MEMBER_LOAN
        defaultKamalSocietyShouldNotBeFound("assetMemberLoan.equals=" + UPDATED_ASSET_MEMBER_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetMemberLoanIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetMemberLoan in DEFAULT_ASSET_MEMBER_LOAN or UPDATED_ASSET_MEMBER_LOAN
        defaultKamalSocietyShouldBeFound("assetMemberLoan.in=" + DEFAULT_ASSET_MEMBER_LOAN + "," + UPDATED_ASSET_MEMBER_LOAN);

        // Get all the kamalSocietyList where assetMemberLoan equals to UPDATED_ASSET_MEMBER_LOAN
        defaultKamalSocietyShouldNotBeFound("assetMemberLoan.in=" + UPDATED_ASSET_MEMBER_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetMemberLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetMemberLoan is not null
        defaultKamalSocietyShouldBeFound("assetMemberLoan.specified=true");

        // Get all the kamalSocietyList where assetMemberLoan is null
        defaultKamalSocietyShouldNotBeFound("assetMemberLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetMemberLoanContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetMemberLoan contains DEFAULT_ASSET_MEMBER_LOAN
        defaultKamalSocietyShouldBeFound("assetMemberLoan.contains=" + DEFAULT_ASSET_MEMBER_LOAN);

        // Get all the kamalSocietyList where assetMemberLoan contains UPDATED_ASSET_MEMBER_LOAN
        defaultKamalSocietyShouldNotBeFound("assetMemberLoan.contains=" + UPDATED_ASSET_MEMBER_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetMemberLoanNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetMemberLoan does not contain DEFAULT_ASSET_MEMBER_LOAN
        defaultKamalSocietyShouldNotBeFound("assetMemberLoan.doesNotContain=" + DEFAULT_ASSET_MEMBER_LOAN);

        // Get all the kamalSocietyList where assetMemberLoan does not contain UPDATED_ASSET_MEMBER_LOAN
        defaultKamalSocietyShouldBeFound("assetMemberLoan.doesNotContain=" + UPDATED_ASSET_MEMBER_LOAN);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetDeadStockIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetDeadStock equals to DEFAULT_ASSET_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("assetDeadStock.equals=" + DEFAULT_ASSET_DEAD_STOCK);

        // Get all the kamalSocietyList where assetDeadStock equals to UPDATED_ASSET_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("assetDeadStock.equals=" + UPDATED_ASSET_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetDeadStockIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetDeadStock in DEFAULT_ASSET_DEAD_STOCK or UPDATED_ASSET_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("assetDeadStock.in=" + DEFAULT_ASSET_DEAD_STOCK + "," + UPDATED_ASSET_DEAD_STOCK);

        // Get all the kamalSocietyList where assetDeadStock equals to UPDATED_ASSET_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("assetDeadStock.in=" + UPDATED_ASSET_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetDeadStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetDeadStock is not null
        defaultKamalSocietyShouldBeFound("assetDeadStock.specified=true");

        // Get all the kamalSocietyList where assetDeadStock is null
        defaultKamalSocietyShouldNotBeFound("assetDeadStock.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetDeadStockContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetDeadStock contains DEFAULT_ASSET_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("assetDeadStock.contains=" + DEFAULT_ASSET_DEAD_STOCK);

        // Get all the kamalSocietyList where assetDeadStock contains UPDATED_ASSET_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("assetDeadStock.contains=" + UPDATED_ASSET_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetDeadStockNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetDeadStock does not contain DEFAULT_ASSET_DEAD_STOCK
        defaultKamalSocietyShouldNotBeFound("assetDeadStock.doesNotContain=" + DEFAULT_ASSET_DEAD_STOCK);

        // Get all the kamalSocietyList where assetDeadStock does not contain UPDATED_ASSET_DEAD_STOCK
        defaultKamalSocietyShouldBeFound("assetDeadStock.doesNotContain=" + UPDATED_ASSET_DEAD_STOCK);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetOtherReceivableIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetOtherReceivable equals to DEFAULT_ASSET_OTHER_RECEIVABLE
        defaultKamalSocietyShouldBeFound("assetOtherReceivable.equals=" + DEFAULT_ASSET_OTHER_RECEIVABLE);

        // Get all the kamalSocietyList where assetOtherReceivable equals to UPDATED_ASSET_OTHER_RECEIVABLE
        defaultKamalSocietyShouldNotBeFound("assetOtherReceivable.equals=" + UPDATED_ASSET_OTHER_RECEIVABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetOtherReceivableIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetOtherReceivable in DEFAULT_ASSET_OTHER_RECEIVABLE or UPDATED_ASSET_OTHER_RECEIVABLE
        defaultKamalSocietyShouldBeFound(
            "assetOtherReceivable.in=" + DEFAULT_ASSET_OTHER_RECEIVABLE + "," + UPDATED_ASSET_OTHER_RECEIVABLE
        );

        // Get all the kamalSocietyList where assetOtherReceivable equals to UPDATED_ASSET_OTHER_RECEIVABLE
        defaultKamalSocietyShouldNotBeFound("assetOtherReceivable.in=" + UPDATED_ASSET_OTHER_RECEIVABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetOtherReceivableIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetOtherReceivable is not null
        defaultKamalSocietyShouldBeFound("assetOtherReceivable.specified=true");

        // Get all the kamalSocietyList where assetOtherReceivable is null
        defaultKamalSocietyShouldNotBeFound("assetOtherReceivable.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetOtherReceivableContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetOtherReceivable contains DEFAULT_ASSET_OTHER_RECEIVABLE
        defaultKamalSocietyShouldBeFound("assetOtherReceivable.contains=" + DEFAULT_ASSET_OTHER_RECEIVABLE);

        // Get all the kamalSocietyList where assetOtherReceivable contains UPDATED_ASSET_OTHER_RECEIVABLE
        defaultKamalSocietyShouldNotBeFound("assetOtherReceivable.contains=" + UPDATED_ASSET_OTHER_RECEIVABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetOtherReceivableNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetOtherReceivable does not contain DEFAULT_ASSET_OTHER_RECEIVABLE
        defaultKamalSocietyShouldNotBeFound("assetOtherReceivable.doesNotContain=" + DEFAULT_ASSET_OTHER_RECEIVABLE);

        // Get all the kamalSocietyList where assetOtherReceivable does not contain UPDATED_ASSET_OTHER_RECEIVABLE
        defaultKamalSocietyShouldBeFound("assetOtherReceivable.doesNotContain=" + UPDATED_ASSET_OTHER_RECEIVABLE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetLossIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetLoss equals to DEFAULT_ASSET_LOSS
        defaultKamalSocietyShouldBeFound("assetLoss.equals=" + DEFAULT_ASSET_LOSS);

        // Get all the kamalSocietyList where assetLoss equals to UPDATED_ASSET_LOSS
        defaultKamalSocietyShouldNotBeFound("assetLoss.equals=" + UPDATED_ASSET_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetLossIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetLoss in DEFAULT_ASSET_LOSS or UPDATED_ASSET_LOSS
        defaultKamalSocietyShouldBeFound("assetLoss.in=" + DEFAULT_ASSET_LOSS + "," + UPDATED_ASSET_LOSS);

        // Get all the kamalSocietyList where assetLoss equals to UPDATED_ASSET_LOSS
        defaultKamalSocietyShouldNotBeFound("assetLoss.in=" + UPDATED_ASSET_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetLossIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetLoss is not null
        defaultKamalSocietyShouldBeFound("assetLoss.specified=true");

        // Get all the kamalSocietyList where assetLoss is null
        defaultKamalSocietyShouldNotBeFound("assetLoss.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetLossContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetLoss contains DEFAULT_ASSET_LOSS
        defaultKamalSocietyShouldBeFound("assetLoss.contains=" + DEFAULT_ASSET_LOSS);

        // Get all the kamalSocietyList where assetLoss contains UPDATED_ASSET_LOSS
        defaultKamalSocietyShouldNotBeFound("assetLoss.contains=" + UPDATED_ASSET_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByAssetLossNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where assetLoss does not contain DEFAULT_ASSET_LOSS
        defaultKamalSocietyShouldNotBeFound("assetLoss.doesNotContain=" + DEFAULT_ASSET_LOSS);

        // Get all the kamalSocietyList where assetLoss does not contain UPDATED_ASSET_LOSS
        defaultKamalSocietyShouldBeFound("assetLoss.doesNotContain=" + UPDATED_ASSET_LOSS);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLiabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLiability equals to DEFAULT_TOTAL_LIABILITY
        defaultKamalSocietyShouldBeFound("totalLiability.equals=" + DEFAULT_TOTAL_LIABILITY);

        // Get all the kamalSocietyList where totalLiability equals to UPDATED_TOTAL_LIABILITY
        defaultKamalSocietyShouldNotBeFound("totalLiability.equals=" + UPDATED_TOTAL_LIABILITY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLiabilityIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLiability in DEFAULT_TOTAL_LIABILITY or UPDATED_TOTAL_LIABILITY
        defaultKamalSocietyShouldBeFound("totalLiability.in=" + DEFAULT_TOTAL_LIABILITY + "," + UPDATED_TOTAL_LIABILITY);

        // Get all the kamalSocietyList where totalLiability equals to UPDATED_TOTAL_LIABILITY
        defaultKamalSocietyShouldNotBeFound("totalLiability.in=" + UPDATED_TOTAL_LIABILITY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLiabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLiability is not null
        defaultKamalSocietyShouldBeFound("totalLiability.specified=true");

        // Get all the kamalSocietyList where totalLiability is null
        defaultKamalSocietyShouldNotBeFound("totalLiability.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLiabilityContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLiability contains DEFAULT_TOTAL_LIABILITY
        defaultKamalSocietyShouldBeFound("totalLiability.contains=" + DEFAULT_TOTAL_LIABILITY);

        // Get all the kamalSocietyList where totalLiability contains UPDATED_TOTAL_LIABILITY
        defaultKamalSocietyShouldNotBeFound("totalLiability.contains=" + UPDATED_TOTAL_LIABILITY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalLiabilityNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalLiability does not contain DEFAULT_TOTAL_LIABILITY
        defaultKamalSocietyShouldNotBeFound("totalLiability.doesNotContain=" + DEFAULT_TOTAL_LIABILITY);

        // Get all the kamalSocietyList where totalLiability does not contain UPDATED_TOTAL_LIABILITY
        defaultKamalSocietyShouldBeFound("totalLiability.doesNotContain=" + UPDATED_TOTAL_LIABILITY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalAssetIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalAsset equals to DEFAULT_TOTAL_ASSET
        defaultKamalSocietyShouldBeFound("totalAsset.equals=" + DEFAULT_TOTAL_ASSET);

        // Get all the kamalSocietyList where totalAsset equals to UPDATED_TOTAL_ASSET
        defaultKamalSocietyShouldNotBeFound("totalAsset.equals=" + UPDATED_TOTAL_ASSET);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalAssetIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalAsset in DEFAULT_TOTAL_ASSET or UPDATED_TOTAL_ASSET
        defaultKamalSocietyShouldBeFound("totalAsset.in=" + DEFAULT_TOTAL_ASSET + "," + UPDATED_TOTAL_ASSET);

        // Get all the kamalSocietyList where totalAsset equals to UPDATED_TOTAL_ASSET
        defaultKamalSocietyShouldNotBeFound("totalAsset.in=" + UPDATED_TOTAL_ASSET);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalAssetIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalAsset is not null
        defaultKamalSocietyShouldBeFound("totalAsset.specified=true");

        // Get all the kamalSocietyList where totalAsset is null
        defaultKamalSocietyShouldNotBeFound("totalAsset.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalAssetContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalAsset contains DEFAULT_TOTAL_ASSET
        defaultKamalSocietyShouldBeFound("totalAsset.contains=" + DEFAULT_TOTAL_ASSET);

        // Get all the kamalSocietyList where totalAsset contains UPDATED_TOTAL_ASSET
        defaultKamalSocietyShouldNotBeFound("totalAsset.contains=" + UPDATED_TOTAL_ASSET);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTotalAssetNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where totalAsset does not contain DEFAULT_TOTAL_ASSET
        defaultKamalSocietyShouldNotBeFound("totalAsset.doesNotContain=" + DEFAULT_TOTAL_ASSET);

        // Get all the kamalSocietyList where totalAsset does not contain UPDATED_TOTAL_ASSET
        defaultKamalSocietyShouldBeFound("totalAsset.doesNotContain=" + UPDATED_TOTAL_ASSET);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillageCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where villageCode equals to DEFAULT_VILLAGE_CODE
        defaultKamalSocietyShouldBeFound("villageCode.equals=" + DEFAULT_VILLAGE_CODE);

        // Get all the kamalSocietyList where villageCode equals to UPDATED_VILLAGE_CODE
        defaultKamalSocietyShouldNotBeFound("villageCode.equals=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillageCodeIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where villageCode in DEFAULT_VILLAGE_CODE or UPDATED_VILLAGE_CODE
        defaultKamalSocietyShouldBeFound("villageCode.in=" + DEFAULT_VILLAGE_CODE + "," + UPDATED_VILLAGE_CODE);

        // Get all the kamalSocietyList where villageCode equals to UPDATED_VILLAGE_CODE
        defaultKamalSocietyShouldNotBeFound("villageCode.in=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillageCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where villageCode is not null
        defaultKamalSocietyShouldBeFound("villageCode.specified=true");

        // Get all the kamalSocietyList where villageCode is null
        defaultKamalSocietyShouldNotBeFound("villageCode.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillageCodeContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where villageCode contains DEFAULT_VILLAGE_CODE
        defaultKamalSocietyShouldBeFound("villageCode.contains=" + DEFAULT_VILLAGE_CODE);

        // Get all the kamalSocietyList where villageCode contains UPDATED_VILLAGE_CODE
        defaultKamalSocietyShouldNotBeFound("villageCode.contains=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByVillageCodeNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where villageCode does not contain DEFAULT_VILLAGE_CODE
        defaultKamalSocietyShouldNotBeFound("villageCode.doesNotContain=" + DEFAULT_VILLAGE_CODE);

        // Get all the kamalSocietyList where villageCode does not contain UPDATED_VILLAGE_CODE
        defaultKamalSocietyShouldBeFound("villageCode.doesNotContain=" + UPDATED_VILLAGE_CODE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsVerifiedFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsVerifiedFlag equals to DEFAULT_PACS_VERIFIED_FLAG
        defaultKamalSocietyShouldBeFound("pacsVerifiedFlag.equals=" + DEFAULT_PACS_VERIFIED_FLAG);

        // Get all the kamalSocietyList where pacsVerifiedFlag equals to UPDATED_PACS_VERIFIED_FLAG
        defaultKamalSocietyShouldNotBeFound("pacsVerifiedFlag.equals=" + UPDATED_PACS_VERIFIED_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsVerifiedFlagIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsVerifiedFlag in DEFAULT_PACS_VERIFIED_FLAG or UPDATED_PACS_VERIFIED_FLAG
        defaultKamalSocietyShouldBeFound("pacsVerifiedFlag.in=" + DEFAULT_PACS_VERIFIED_FLAG + "," + UPDATED_PACS_VERIFIED_FLAG);

        // Get all the kamalSocietyList where pacsVerifiedFlag equals to UPDATED_PACS_VERIFIED_FLAG
        defaultKamalSocietyShouldNotBeFound("pacsVerifiedFlag.in=" + UPDATED_PACS_VERIFIED_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByPacsVerifiedFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where pacsVerifiedFlag is not null
        defaultKamalSocietyShouldBeFound("pacsVerifiedFlag.specified=true");

        // Get all the kamalSocietyList where pacsVerifiedFlag is null
        defaultKamalSocietyShouldNotBeFound("pacsVerifiedFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedFlag equals to DEFAULT_BRANCH_VERIFIED_FLAG
        defaultKamalSocietyShouldBeFound("branchVerifiedFlag.equals=" + DEFAULT_BRANCH_VERIFIED_FLAG);

        // Get all the kamalSocietyList where branchVerifiedFlag equals to UPDATED_BRANCH_VERIFIED_FLAG
        defaultKamalSocietyShouldNotBeFound("branchVerifiedFlag.equals=" + UPDATED_BRANCH_VERIFIED_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedFlagIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedFlag in DEFAULT_BRANCH_VERIFIED_FLAG or UPDATED_BRANCH_VERIFIED_FLAG
        defaultKamalSocietyShouldBeFound("branchVerifiedFlag.in=" + DEFAULT_BRANCH_VERIFIED_FLAG + "," + UPDATED_BRANCH_VERIFIED_FLAG);

        // Get all the kamalSocietyList where branchVerifiedFlag equals to UPDATED_BRANCH_VERIFIED_FLAG
        defaultKamalSocietyShouldNotBeFound("branchVerifiedFlag.in=" + UPDATED_BRANCH_VERIFIED_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedFlag is not null
        defaultKamalSocietyShouldBeFound("branchVerifiedFlag.specified=true");

        // Get all the kamalSocietyList where branchVerifiedFlag is null
        defaultKamalSocietyShouldNotBeFound("branchVerifiedFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedFlag equals to DEFAULT_HEAD_OFFICE_VERIFIED_FLAG
        defaultKamalSocietyShouldBeFound("headOfficeVerifiedFlag.equals=" + DEFAULT_HEAD_OFFICE_VERIFIED_FLAG);

        // Get all the kamalSocietyList where headOfficeVerifiedFlag equals to UPDATED_HEAD_OFFICE_VERIFIED_FLAG
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedFlag.equals=" + UPDATED_HEAD_OFFICE_VERIFIED_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedFlagIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedFlag in DEFAULT_HEAD_OFFICE_VERIFIED_FLAG or UPDATED_HEAD_OFFICE_VERIFIED_FLAG
        defaultKamalSocietyShouldBeFound(
            "headOfficeVerifiedFlag.in=" + DEFAULT_HEAD_OFFICE_VERIFIED_FLAG + "," + UPDATED_HEAD_OFFICE_VERIFIED_FLAG
        );

        // Get all the kamalSocietyList where headOfficeVerifiedFlag equals to UPDATED_HEAD_OFFICE_VERIFIED_FLAG
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedFlag.in=" + UPDATED_HEAD_OFFICE_VERIFIED_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedFlag is not null
        defaultKamalSocietyShouldBeFound("headOfficeVerifiedFlag.specified=true");

        // Get all the kamalSocietyList where headOfficeVerifiedFlag is null
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedFlag equals to DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG
        defaultKamalSocietyShouldBeFound("divisionalOfficeVerifiedFlag.equals=" + DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedFlag equals to UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedFlag.equals=" + UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedFlagIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedFlag in DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG or UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG
        defaultKamalSocietyShouldBeFound(
            "divisionalOfficeVerifiedFlag.in=" + DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG + "," + UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG
        );

        // Get all the kamalSocietyList where divisionalOfficeVerifiedFlag equals to UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedFlag.in=" + UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedFlag is not null
        defaultKamalSocietyShouldBeFound("divisionalOfficeVerifiedFlag.specified=true");

        // Get all the kamalSocietyList where divisionalOfficeVerifiedFlag is null
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByIsSupplimenteryFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where isSupplimenteryFlag equals to DEFAULT_IS_SUPPLIMENTERY_FLAG
        defaultKamalSocietyShouldBeFound("isSupplimenteryFlag.equals=" + DEFAULT_IS_SUPPLIMENTERY_FLAG);

        // Get all the kamalSocietyList where isSupplimenteryFlag equals to UPDATED_IS_SUPPLIMENTERY_FLAG
        defaultKamalSocietyShouldNotBeFound("isSupplimenteryFlag.equals=" + UPDATED_IS_SUPPLIMENTERY_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByIsSupplimenteryFlagIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where isSupplimenteryFlag in DEFAULT_IS_SUPPLIMENTERY_FLAG or UPDATED_IS_SUPPLIMENTERY_FLAG
        defaultKamalSocietyShouldBeFound("isSupplimenteryFlag.in=" + DEFAULT_IS_SUPPLIMENTERY_FLAG + "," + UPDATED_IS_SUPPLIMENTERY_FLAG);

        // Get all the kamalSocietyList where isSupplimenteryFlag equals to UPDATED_IS_SUPPLIMENTERY_FLAG
        defaultKamalSocietyShouldNotBeFound("isSupplimenteryFlag.in=" + UPDATED_IS_SUPPLIMENTERY_FLAG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByIsSupplimenteryFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where isSupplimenteryFlag is not null
        defaultKamalSocietyShouldBeFound("isSupplimenteryFlag.specified=true");

        // Get all the kamalSocietyList where isSupplimenteryFlag is null
        defaultKamalSocietyShouldNotBeFound("isSupplimenteryFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniVargIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniVarg equals to DEFAULT_SANSTHA_TAPASANI_VARG
        defaultKamalSocietyShouldBeFound("sansthaTapasaniVarg.equals=" + DEFAULT_SANSTHA_TAPASANI_VARG);

        // Get all the kamalSocietyList where sansthaTapasaniVarg equals to UPDATED_SANSTHA_TAPASANI_VARG
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniVarg.equals=" + UPDATED_SANSTHA_TAPASANI_VARG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniVargIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniVarg in DEFAULT_SANSTHA_TAPASANI_VARG or UPDATED_SANSTHA_TAPASANI_VARG
        defaultKamalSocietyShouldBeFound("sansthaTapasaniVarg.in=" + DEFAULT_SANSTHA_TAPASANI_VARG + "," + UPDATED_SANSTHA_TAPASANI_VARG);

        // Get all the kamalSocietyList where sansthaTapasaniVarg equals to UPDATED_SANSTHA_TAPASANI_VARG
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniVarg.in=" + UPDATED_SANSTHA_TAPASANI_VARG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniVargIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniVarg is not null
        defaultKamalSocietyShouldBeFound("sansthaTapasaniVarg.specified=true");

        // Get all the kamalSocietyList where sansthaTapasaniVarg is null
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniVarg.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniVargContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniVarg contains DEFAULT_SANSTHA_TAPASANI_VARG
        defaultKamalSocietyShouldBeFound("sansthaTapasaniVarg.contains=" + DEFAULT_SANSTHA_TAPASANI_VARG);

        // Get all the kamalSocietyList where sansthaTapasaniVarg contains UPDATED_SANSTHA_TAPASANI_VARG
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniVarg.contains=" + UPDATED_SANSTHA_TAPASANI_VARG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesBySansthaTapasaniVargNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where sansthaTapasaniVarg does not contain DEFAULT_SANSTHA_TAPASANI_VARG
        defaultKamalSocietyShouldNotBeFound("sansthaTapasaniVarg.doesNotContain=" + DEFAULT_SANSTHA_TAPASANI_VARG);

        // Get all the kamalSocietyList where sansthaTapasaniVarg does not contain UPDATED_SANSTHA_TAPASANI_VARG
        defaultKamalSocietyShouldBeFound("sansthaTapasaniVarg.doesNotContain=" + UPDATED_SANSTHA_TAPASANI_VARG);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedBy equals to DEFAULT_BRANCH_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("branchVerifiedBy.equals=" + DEFAULT_BRANCH_VERIFIED_BY);

        // Get all the kamalSocietyList where branchVerifiedBy equals to UPDATED_BRANCH_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("branchVerifiedBy.equals=" + UPDATED_BRANCH_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedBy in DEFAULT_BRANCH_VERIFIED_BY or UPDATED_BRANCH_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("branchVerifiedBy.in=" + DEFAULT_BRANCH_VERIFIED_BY + "," + UPDATED_BRANCH_VERIFIED_BY);

        // Get all the kamalSocietyList where branchVerifiedBy equals to UPDATED_BRANCH_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("branchVerifiedBy.in=" + UPDATED_BRANCH_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedBy is not null
        defaultKamalSocietyShouldBeFound("branchVerifiedBy.specified=true");

        // Get all the kamalSocietyList where branchVerifiedBy is null
        defaultKamalSocietyShouldNotBeFound("branchVerifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedByContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedBy contains DEFAULT_BRANCH_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("branchVerifiedBy.contains=" + DEFAULT_BRANCH_VERIFIED_BY);

        // Get all the kamalSocietyList where branchVerifiedBy contains UPDATED_BRANCH_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("branchVerifiedBy.contains=" + UPDATED_BRANCH_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedBy does not contain DEFAULT_BRANCH_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("branchVerifiedBy.doesNotContain=" + DEFAULT_BRANCH_VERIFIED_BY);

        // Get all the kamalSocietyList where branchVerifiedBy does not contain UPDATED_BRANCH_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("branchVerifiedBy.doesNotContain=" + UPDATED_BRANCH_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedDate equals to DEFAULT_BRANCH_VERIFIED_DATE
        defaultKamalSocietyShouldBeFound("branchVerifiedDate.equals=" + DEFAULT_BRANCH_VERIFIED_DATE);

        // Get all the kamalSocietyList where branchVerifiedDate equals to UPDATED_BRANCH_VERIFIED_DATE
        defaultKamalSocietyShouldNotBeFound("branchVerifiedDate.equals=" + UPDATED_BRANCH_VERIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedDate in DEFAULT_BRANCH_VERIFIED_DATE or UPDATED_BRANCH_VERIFIED_DATE
        defaultKamalSocietyShouldBeFound("branchVerifiedDate.in=" + DEFAULT_BRANCH_VERIFIED_DATE + "," + UPDATED_BRANCH_VERIFIED_DATE);

        // Get all the kamalSocietyList where branchVerifiedDate equals to UPDATED_BRANCH_VERIFIED_DATE
        defaultKamalSocietyShouldNotBeFound("branchVerifiedDate.in=" + UPDATED_BRANCH_VERIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchVerifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchVerifiedDate is not null
        defaultKamalSocietyShouldBeFound("branchVerifiedDate.specified=true");

        // Get all the kamalSocietyList where branchVerifiedDate is null
        defaultKamalSocietyShouldNotBeFound("branchVerifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedBy equals to DEFAULT_HEAD_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("headOfficeVerifiedBy.equals=" + DEFAULT_HEAD_OFFICE_VERIFIED_BY);

        // Get all the kamalSocietyList where headOfficeVerifiedBy equals to UPDATED_HEAD_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedBy.equals=" + UPDATED_HEAD_OFFICE_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedBy in DEFAULT_HEAD_OFFICE_VERIFIED_BY or UPDATED_HEAD_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldBeFound(
            "headOfficeVerifiedBy.in=" + DEFAULT_HEAD_OFFICE_VERIFIED_BY + "," + UPDATED_HEAD_OFFICE_VERIFIED_BY
        );

        // Get all the kamalSocietyList where headOfficeVerifiedBy equals to UPDATED_HEAD_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedBy.in=" + UPDATED_HEAD_OFFICE_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedBy is not null
        defaultKamalSocietyShouldBeFound("headOfficeVerifiedBy.specified=true");

        // Get all the kamalSocietyList where headOfficeVerifiedBy is null
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedByContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedBy contains DEFAULT_HEAD_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("headOfficeVerifiedBy.contains=" + DEFAULT_HEAD_OFFICE_VERIFIED_BY);

        // Get all the kamalSocietyList where headOfficeVerifiedBy contains UPDATED_HEAD_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedBy.contains=" + UPDATED_HEAD_OFFICE_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedBy does not contain DEFAULT_HEAD_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedBy.doesNotContain=" + DEFAULT_HEAD_OFFICE_VERIFIED_BY);

        // Get all the kamalSocietyList where headOfficeVerifiedBy does not contain UPDATED_HEAD_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("headOfficeVerifiedBy.doesNotContain=" + UPDATED_HEAD_OFFICE_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedDate equals to DEFAULT_HEAD_OFFICE_VERIFIED_DATE
        defaultKamalSocietyShouldBeFound("headOfficeVerifiedDate.equals=" + DEFAULT_HEAD_OFFICE_VERIFIED_DATE);

        // Get all the kamalSocietyList where headOfficeVerifiedDate equals to UPDATED_HEAD_OFFICE_VERIFIED_DATE
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedDate.equals=" + UPDATED_HEAD_OFFICE_VERIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedDate in DEFAULT_HEAD_OFFICE_VERIFIED_DATE or UPDATED_HEAD_OFFICE_VERIFIED_DATE
        defaultKamalSocietyShouldBeFound(
            "headOfficeVerifiedDate.in=" + DEFAULT_HEAD_OFFICE_VERIFIED_DATE + "," + UPDATED_HEAD_OFFICE_VERIFIED_DATE
        );

        // Get all the kamalSocietyList where headOfficeVerifiedDate equals to UPDATED_HEAD_OFFICE_VERIFIED_DATE
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedDate.in=" + UPDATED_HEAD_OFFICE_VERIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeVerifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeVerifiedDate is not null
        defaultKamalSocietyShouldBeFound("headOfficeVerifiedDate.specified=true");

        // Get all the kamalSocietyList where headOfficeVerifiedDate is null
        defaultKamalSocietyShouldNotBeFound("headOfficeVerifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy equals to DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("divisionalOfficeVerifiedBy.equals=" + DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy equals to UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedBy.equals=" + UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy in DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY or UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldBeFound(
            "divisionalOfficeVerifiedBy.in=" + DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY + "," + UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY
        );

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy equals to UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedBy.in=" + UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy is not null
        defaultKamalSocietyShouldBeFound("divisionalOfficeVerifiedBy.specified=true");

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy is null
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedByContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy contains DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("divisionalOfficeVerifiedBy.contains=" + DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy contains UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedBy.contains=" + UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy does not contain DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedBy.doesNotContain=" + DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedBy does not contain UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY
        defaultKamalSocietyShouldBeFound("divisionalOfficeVerifiedBy.doesNotContain=" + UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedDate equals to DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE
        defaultKamalSocietyShouldBeFound("divisionalOfficeVerifiedDate.equals=" + DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedDate equals to UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedDate.equals=" + UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedDate in DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE or UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE
        defaultKamalSocietyShouldBeFound(
            "divisionalOfficeVerifiedDate.in=" + DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE + "," + UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE
        );

        // Get all the kamalSocietyList where divisionalOfficeVerifiedDate equals to UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedDate.in=" + UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDivisionalOfficeVerifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where divisionalOfficeVerifiedDate is not null
        defaultKamalSocietyShouldBeFound("divisionalOfficeVerifiedDate.specified=true");

        // Get all the kamalSocietyList where divisionalOfficeVerifiedDate is null
        defaultKamalSocietyShouldNotBeFound("divisionalOfficeVerifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDoshPurtataDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where doshPurtataDate equals to DEFAULT_DOSH_PURTATA_DATE
        defaultKamalSocietyShouldBeFound("doshPurtataDate.equals=" + DEFAULT_DOSH_PURTATA_DATE);

        // Get all the kamalSocietyList where doshPurtataDate equals to UPDATED_DOSH_PURTATA_DATE
        defaultKamalSocietyShouldNotBeFound("doshPurtataDate.equals=" + UPDATED_DOSH_PURTATA_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDoshPurtataDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where doshPurtataDate in DEFAULT_DOSH_PURTATA_DATE or UPDATED_DOSH_PURTATA_DATE
        defaultKamalSocietyShouldBeFound("doshPurtataDate.in=" + DEFAULT_DOSH_PURTATA_DATE + "," + UPDATED_DOSH_PURTATA_DATE);

        // Get all the kamalSocietyList where doshPurtataDate equals to UPDATED_DOSH_PURTATA_DATE
        defaultKamalSocietyShouldNotBeFound("doshPurtataDate.in=" + UPDATED_DOSH_PURTATA_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByDoshPurtataDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where doshPurtataDate is not null
        defaultKamalSocietyShouldBeFound("doshPurtataDate.specified=true");

        // Get all the kamalSocietyList where doshPurtataDate is null
        defaultKamalSocietyShouldNotBeFound("doshPurtataDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGambhirDoshIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where gambhirDosh equals to DEFAULT_GAMBHIR_DOSH
        defaultKamalSocietyShouldBeFound("gambhirDosh.equals=" + DEFAULT_GAMBHIR_DOSH);

        // Get all the kamalSocietyList where gambhirDosh equals to UPDATED_GAMBHIR_DOSH
        defaultKamalSocietyShouldNotBeFound("gambhirDosh.equals=" + UPDATED_GAMBHIR_DOSH);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGambhirDoshIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where gambhirDosh in DEFAULT_GAMBHIR_DOSH or UPDATED_GAMBHIR_DOSH
        defaultKamalSocietyShouldBeFound("gambhirDosh.in=" + DEFAULT_GAMBHIR_DOSH + "," + UPDATED_GAMBHIR_DOSH);

        // Get all the kamalSocietyList where gambhirDosh equals to UPDATED_GAMBHIR_DOSH
        defaultKamalSocietyShouldNotBeFound("gambhirDosh.in=" + UPDATED_GAMBHIR_DOSH);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGambhirDoshIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where gambhirDosh is not null
        defaultKamalSocietyShouldBeFound("gambhirDosh.specified=true");

        // Get all the kamalSocietyList where gambhirDosh is null
        defaultKamalSocietyShouldNotBeFound("gambhirDosh.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGambhirDoshContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where gambhirDosh contains DEFAULT_GAMBHIR_DOSH
        defaultKamalSocietyShouldBeFound("gambhirDosh.contains=" + DEFAULT_GAMBHIR_DOSH);

        // Get all the kamalSocietyList where gambhirDosh contains UPDATED_GAMBHIR_DOSH
        defaultKamalSocietyShouldNotBeFound("gambhirDosh.contains=" + UPDATED_GAMBHIR_DOSH);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByGambhirDoshNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where gambhirDosh does not contain DEFAULT_GAMBHIR_DOSH
        defaultKamalSocietyShouldNotBeFound("gambhirDosh.doesNotContain=" + DEFAULT_GAMBHIR_DOSH);

        // Get all the kamalSocietyList where gambhirDosh does not contain UPDATED_GAMBHIR_DOSH
        defaultKamalSocietyShouldBeFound("gambhirDosh.doesNotContain=" + UPDATED_GAMBHIR_DOSH);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchInwardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchInwardNumber equals to DEFAULT_BRANCH_INWARD_NUMBER
        defaultKamalSocietyShouldBeFound("branchInwardNumber.equals=" + DEFAULT_BRANCH_INWARD_NUMBER);

        // Get all the kamalSocietyList where branchInwardNumber equals to UPDATED_BRANCH_INWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("branchInwardNumber.equals=" + UPDATED_BRANCH_INWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchInwardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchInwardNumber in DEFAULT_BRANCH_INWARD_NUMBER or UPDATED_BRANCH_INWARD_NUMBER
        defaultKamalSocietyShouldBeFound("branchInwardNumber.in=" + DEFAULT_BRANCH_INWARD_NUMBER + "," + UPDATED_BRANCH_INWARD_NUMBER);

        // Get all the kamalSocietyList where branchInwardNumber equals to UPDATED_BRANCH_INWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("branchInwardNumber.in=" + UPDATED_BRANCH_INWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchInwardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchInwardNumber is not null
        defaultKamalSocietyShouldBeFound("branchInwardNumber.specified=true");

        // Get all the kamalSocietyList where branchInwardNumber is null
        defaultKamalSocietyShouldNotBeFound("branchInwardNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchInwardNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchInwardNumber contains DEFAULT_BRANCH_INWARD_NUMBER
        defaultKamalSocietyShouldBeFound("branchInwardNumber.contains=" + DEFAULT_BRANCH_INWARD_NUMBER);

        // Get all the kamalSocietyList where branchInwardNumber contains UPDATED_BRANCH_INWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("branchInwardNumber.contains=" + UPDATED_BRANCH_INWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchInwardNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchInwardNumber does not contain DEFAULT_BRANCH_INWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("branchInwardNumber.doesNotContain=" + DEFAULT_BRANCH_INWARD_NUMBER);

        // Get all the kamalSocietyList where branchInwardNumber does not contain UPDATED_BRANCH_INWARD_NUMBER
        defaultKamalSocietyShouldBeFound("branchInwardNumber.doesNotContain=" + UPDATED_BRANCH_INWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchInwardDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchInwardDate equals to DEFAULT_BRANCH_INWARD_DATE
        defaultKamalSocietyShouldBeFound("branchInwardDate.equals=" + DEFAULT_BRANCH_INWARD_DATE);

        // Get all the kamalSocietyList where branchInwardDate equals to UPDATED_BRANCH_INWARD_DATE
        defaultKamalSocietyShouldNotBeFound("branchInwardDate.equals=" + UPDATED_BRANCH_INWARD_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchInwardDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchInwardDate in DEFAULT_BRANCH_INWARD_DATE or UPDATED_BRANCH_INWARD_DATE
        defaultKamalSocietyShouldBeFound("branchInwardDate.in=" + DEFAULT_BRANCH_INWARD_DATE + "," + UPDATED_BRANCH_INWARD_DATE);

        // Get all the kamalSocietyList where branchInwardDate equals to UPDATED_BRANCH_INWARD_DATE
        defaultKamalSocietyShouldNotBeFound("branchInwardDate.in=" + UPDATED_BRANCH_INWARD_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchInwardDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchInwardDate is not null
        defaultKamalSocietyShouldBeFound("branchInwardDate.specified=true");

        // Get all the kamalSocietyList where branchInwardDate is null
        defaultKamalSocietyShouldNotBeFound("branchInwardDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchOutwardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchOutwardNumber equals to DEFAULT_BRANCH_OUTWARD_NUMBER
        defaultKamalSocietyShouldBeFound("branchOutwardNumber.equals=" + DEFAULT_BRANCH_OUTWARD_NUMBER);

        // Get all the kamalSocietyList where branchOutwardNumber equals to UPDATED_BRANCH_OUTWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("branchOutwardNumber.equals=" + UPDATED_BRANCH_OUTWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchOutwardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchOutwardNumber in DEFAULT_BRANCH_OUTWARD_NUMBER or UPDATED_BRANCH_OUTWARD_NUMBER
        defaultKamalSocietyShouldBeFound("branchOutwardNumber.in=" + DEFAULT_BRANCH_OUTWARD_NUMBER + "," + UPDATED_BRANCH_OUTWARD_NUMBER);

        // Get all the kamalSocietyList where branchOutwardNumber equals to UPDATED_BRANCH_OUTWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("branchOutwardNumber.in=" + UPDATED_BRANCH_OUTWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchOutwardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchOutwardNumber is not null
        defaultKamalSocietyShouldBeFound("branchOutwardNumber.specified=true");

        // Get all the kamalSocietyList where branchOutwardNumber is null
        defaultKamalSocietyShouldNotBeFound("branchOutwardNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchOutwardNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchOutwardNumber contains DEFAULT_BRANCH_OUTWARD_NUMBER
        defaultKamalSocietyShouldBeFound("branchOutwardNumber.contains=" + DEFAULT_BRANCH_OUTWARD_NUMBER);

        // Get all the kamalSocietyList where branchOutwardNumber contains UPDATED_BRANCH_OUTWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("branchOutwardNumber.contains=" + UPDATED_BRANCH_OUTWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchOutwardNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchOutwardNumber does not contain DEFAULT_BRANCH_OUTWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("branchOutwardNumber.doesNotContain=" + DEFAULT_BRANCH_OUTWARD_NUMBER);

        // Get all the kamalSocietyList where branchOutwardNumber does not contain UPDATED_BRANCH_OUTWARD_NUMBER
        defaultKamalSocietyShouldBeFound("branchOutwardNumber.doesNotContain=" + UPDATED_BRANCH_OUTWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchOutwardDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchOutwardDate equals to DEFAULT_BRANCH_OUTWARD_DATE
        defaultKamalSocietyShouldBeFound("branchOutwardDate.equals=" + DEFAULT_BRANCH_OUTWARD_DATE);

        // Get all the kamalSocietyList where branchOutwardDate equals to UPDATED_BRANCH_OUTWARD_DATE
        defaultKamalSocietyShouldNotBeFound("branchOutwardDate.equals=" + UPDATED_BRANCH_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchOutwardDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchOutwardDate in DEFAULT_BRANCH_OUTWARD_DATE or UPDATED_BRANCH_OUTWARD_DATE
        defaultKamalSocietyShouldBeFound("branchOutwardDate.in=" + DEFAULT_BRANCH_OUTWARD_DATE + "," + UPDATED_BRANCH_OUTWARD_DATE);

        // Get all the kamalSocietyList where branchOutwardDate equals to UPDATED_BRANCH_OUTWARD_DATE
        defaultKamalSocietyShouldNotBeFound("branchOutwardDate.in=" + UPDATED_BRANCH_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByBranchOutwardDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where branchOutwardDate is not null
        defaultKamalSocietyShouldBeFound("branchOutwardDate.specified=true");

        // Get all the kamalSocietyList where branchOutwardDate is null
        defaultKamalSocietyShouldNotBeFound("branchOutwardDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeInwardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeInwardNumber equals to DEFAULT_HEAD_OFFICE_INWARD_NUMBER
        defaultKamalSocietyShouldBeFound("headOfficeInwardNumber.equals=" + DEFAULT_HEAD_OFFICE_INWARD_NUMBER);

        // Get all the kamalSocietyList where headOfficeInwardNumber equals to UPDATED_HEAD_OFFICE_INWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("headOfficeInwardNumber.equals=" + UPDATED_HEAD_OFFICE_INWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeInwardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeInwardNumber in DEFAULT_HEAD_OFFICE_INWARD_NUMBER or UPDATED_HEAD_OFFICE_INWARD_NUMBER
        defaultKamalSocietyShouldBeFound(
            "headOfficeInwardNumber.in=" + DEFAULT_HEAD_OFFICE_INWARD_NUMBER + "," + UPDATED_HEAD_OFFICE_INWARD_NUMBER
        );

        // Get all the kamalSocietyList where headOfficeInwardNumber equals to UPDATED_HEAD_OFFICE_INWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("headOfficeInwardNumber.in=" + UPDATED_HEAD_OFFICE_INWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeInwardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeInwardNumber is not null
        defaultKamalSocietyShouldBeFound("headOfficeInwardNumber.specified=true");

        // Get all the kamalSocietyList where headOfficeInwardNumber is null
        defaultKamalSocietyShouldNotBeFound("headOfficeInwardNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeInwardNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeInwardNumber contains DEFAULT_HEAD_OFFICE_INWARD_NUMBER
        defaultKamalSocietyShouldBeFound("headOfficeInwardNumber.contains=" + DEFAULT_HEAD_OFFICE_INWARD_NUMBER);

        // Get all the kamalSocietyList where headOfficeInwardNumber contains UPDATED_HEAD_OFFICE_INWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("headOfficeInwardNumber.contains=" + UPDATED_HEAD_OFFICE_INWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeInwardNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeInwardNumber does not contain DEFAULT_HEAD_OFFICE_INWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("headOfficeInwardNumber.doesNotContain=" + DEFAULT_HEAD_OFFICE_INWARD_NUMBER);

        // Get all the kamalSocietyList where headOfficeInwardNumber does not contain UPDATED_HEAD_OFFICE_INWARD_NUMBER
        defaultKamalSocietyShouldBeFound("headOfficeInwardNumber.doesNotContain=" + UPDATED_HEAD_OFFICE_INWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeInwardDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeInwardDate equals to DEFAULT_HEAD_OFFICE_INWARD_DATE
        defaultKamalSocietyShouldBeFound("headOfficeInwardDate.equals=" + DEFAULT_HEAD_OFFICE_INWARD_DATE);

        // Get all the kamalSocietyList where headOfficeInwardDate equals to UPDATED_HEAD_OFFICE_INWARD_DATE
        defaultKamalSocietyShouldNotBeFound("headOfficeInwardDate.equals=" + UPDATED_HEAD_OFFICE_INWARD_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeInwardDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeInwardDate in DEFAULT_HEAD_OFFICE_INWARD_DATE or UPDATED_HEAD_OFFICE_INWARD_DATE
        defaultKamalSocietyShouldBeFound(
            "headOfficeInwardDate.in=" + DEFAULT_HEAD_OFFICE_INWARD_DATE + "," + UPDATED_HEAD_OFFICE_INWARD_DATE
        );

        // Get all the kamalSocietyList where headOfficeInwardDate equals to UPDATED_HEAD_OFFICE_INWARD_DATE
        defaultKamalSocietyShouldNotBeFound("headOfficeInwardDate.in=" + UPDATED_HEAD_OFFICE_INWARD_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeInwardDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeInwardDate is not null
        defaultKamalSocietyShouldBeFound("headOfficeInwardDate.specified=true");

        // Get all the kamalSocietyList where headOfficeInwardDate is null
        defaultKamalSocietyShouldNotBeFound("headOfficeInwardDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeOutwardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeOutwardNumber equals to DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER
        defaultKamalSocietyShouldBeFound("headOfficeOutwardNumber.equals=" + DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER);

        // Get all the kamalSocietyList where headOfficeOutwardNumber equals to UPDATED_HEAD_OFFICE_OUTWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("headOfficeOutwardNumber.equals=" + UPDATED_HEAD_OFFICE_OUTWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeOutwardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeOutwardNumber in DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER or UPDATED_HEAD_OFFICE_OUTWARD_NUMBER
        defaultKamalSocietyShouldBeFound(
            "headOfficeOutwardNumber.in=" + DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER + "," + UPDATED_HEAD_OFFICE_OUTWARD_NUMBER
        );

        // Get all the kamalSocietyList where headOfficeOutwardNumber equals to UPDATED_HEAD_OFFICE_OUTWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("headOfficeOutwardNumber.in=" + UPDATED_HEAD_OFFICE_OUTWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeOutwardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeOutwardNumber is not null
        defaultKamalSocietyShouldBeFound("headOfficeOutwardNumber.specified=true");

        // Get all the kamalSocietyList where headOfficeOutwardNumber is null
        defaultKamalSocietyShouldNotBeFound("headOfficeOutwardNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeOutwardNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeOutwardNumber contains DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER
        defaultKamalSocietyShouldBeFound("headOfficeOutwardNumber.contains=" + DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER);

        // Get all the kamalSocietyList where headOfficeOutwardNumber contains UPDATED_HEAD_OFFICE_OUTWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("headOfficeOutwardNumber.contains=" + UPDATED_HEAD_OFFICE_OUTWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeOutwardNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeOutwardNumber does not contain DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER
        defaultKamalSocietyShouldNotBeFound("headOfficeOutwardNumber.doesNotContain=" + DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER);

        // Get all the kamalSocietyList where headOfficeOutwardNumber does not contain UPDATED_HEAD_OFFICE_OUTWARD_NUMBER
        defaultKamalSocietyShouldBeFound("headOfficeOutwardNumber.doesNotContain=" + UPDATED_HEAD_OFFICE_OUTWARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeOutwardDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeOutwardDate equals to DEFAULT_HEAD_OFFICE_OUTWARD_DATE
        defaultKamalSocietyShouldBeFound("headOfficeOutwardDate.equals=" + DEFAULT_HEAD_OFFICE_OUTWARD_DATE);

        // Get all the kamalSocietyList where headOfficeOutwardDate equals to UPDATED_HEAD_OFFICE_OUTWARD_DATE
        defaultKamalSocietyShouldNotBeFound("headOfficeOutwardDate.equals=" + UPDATED_HEAD_OFFICE_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeOutwardDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeOutwardDate in DEFAULT_HEAD_OFFICE_OUTWARD_DATE or UPDATED_HEAD_OFFICE_OUTWARD_DATE
        defaultKamalSocietyShouldBeFound(
            "headOfficeOutwardDate.in=" + DEFAULT_HEAD_OFFICE_OUTWARD_DATE + "," + UPDATED_HEAD_OFFICE_OUTWARD_DATE
        );

        // Get all the kamalSocietyList where headOfficeOutwardDate equals to UPDATED_HEAD_OFFICE_OUTWARD_DATE
        defaultKamalSocietyShouldNotBeFound("headOfficeOutwardDate.in=" + UPDATED_HEAD_OFFICE_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByHeadOfficeOutwardDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where headOfficeOutwardDate is not null
        defaultKamalSocietyShouldBeFound("headOfficeOutwardDate.specified=true");

        // Get all the kamalSocietyList where headOfficeOutwardDate is null
        defaultKamalSocietyShouldNotBeFound("headOfficeOutwardDate.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTharavNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where tharavNumber equals to DEFAULT_THARAV_NUMBER
        defaultKamalSocietyShouldBeFound("tharavNumber.equals=" + DEFAULT_THARAV_NUMBER);

        // Get all the kamalSocietyList where tharavNumber equals to UPDATED_THARAV_NUMBER
        defaultKamalSocietyShouldNotBeFound("tharavNumber.equals=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTharavNumberIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where tharavNumber in DEFAULT_THARAV_NUMBER or UPDATED_THARAV_NUMBER
        defaultKamalSocietyShouldBeFound("tharavNumber.in=" + DEFAULT_THARAV_NUMBER + "," + UPDATED_THARAV_NUMBER);

        // Get all the kamalSocietyList where tharavNumber equals to UPDATED_THARAV_NUMBER
        defaultKamalSocietyShouldNotBeFound("tharavNumber.in=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTharavNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where tharavNumber is not null
        defaultKamalSocietyShouldBeFound("tharavNumber.specified=true");

        // Get all the kamalSocietyList where tharavNumber is null
        defaultKamalSocietyShouldNotBeFound("tharavNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTharavNumberContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where tharavNumber contains DEFAULT_THARAV_NUMBER
        defaultKamalSocietyShouldBeFound("tharavNumber.contains=" + DEFAULT_THARAV_NUMBER);

        // Get all the kamalSocietyList where tharavNumber contains UPDATED_THARAV_NUMBER
        defaultKamalSocietyShouldNotBeFound("tharavNumber.contains=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTharavNumberNotContainsSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where tharavNumber does not contain DEFAULT_THARAV_NUMBER
        defaultKamalSocietyShouldNotBeFound("tharavNumber.doesNotContain=" + DEFAULT_THARAV_NUMBER);

        // Get all the kamalSocietyList where tharavNumber does not contain UPDATED_THARAV_NUMBER
        defaultKamalSocietyShouldBeFound("tharavNumber.doesNotContain=" + UPDATED_THARAV_NUMBER);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTharavDateIsEqualToSomething() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where tharavDate equals to DEFAULT_THARAV_DATE
        defaultKamalSocietyShouldBeFound("tharavDate.equals=" + DEFAULT_THARAV_DATE);

        // Get all the kamalSocietyList where tharavDate equals to UPDATED_THARAV_DATE
        defaultKamalSocietyShouldNotBeFound("tharavDate.equals=" + UPDATED_THARAV_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTharavDateIsInShouldWork() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where tharavDate in DEFAULT_THARAV_DATE or UPDATED_THARAV_DATE
        defaultKamalSocietyShouldBeFound("tharavDate.in=" + DEFAULT_THARAV_DATE + "," + UPDATED_THARAV_DATE);

        // Get all the kamalSocietyList where tharavDate equals to UPDATED_THARAV_DATE
        defaultKamalSocietyShouldNotBeFound("tharavDate.in=" + UPDATED_THARAV_DATE);
    }

    @Test
    @Transactional
    void getAllKamalSocietiesByTharavDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        kamalSocietyRepository.saveAndFlush(kamalSociety);

        // Get all the kamalSocietyList where tharavDate is not null
        defaultKamalSocietyShouldBeFound("tharavDate.specified=true");

        // Get all the kamalSocietyList where tharavDate is null
        defaultKamalSocietyShouldNotBeFound("tharavDate.specified=false");
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
            .andExpect(jsonPath("$.[*].financialYear").value(hasItem(DEFAULT_FINANCIAL_YEAR)))
            .andExpect(jsonPath("$.[*].kmDate").value(hasItem(DEFAULT_KM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmDateMr").value(hasItem(DEFAULT_KM_DATE_MR)))
            .andExpect(jsonPath("$.[*].kmFromDate").value(hasItem(DEFAULT_KM_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmFromDateMr").value(hasItem(DEFAULT_KM_FROM_DATE_MR)))
            .andExpect(jsonPath("$.[*].kmToDate").value(hasItem(DEFAULT_KM_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].kmToDateMr").value(hasItem(DEFAULT_KM_TO_DATE_MR)))
            .andExpect(jsonPath("$.[*].pacsNumber").value(hasItem(DEFAULT_PACS_NUMBER)))
            .andExpect(jsonPath("$.[*].pacsName").value(hasItem(DEFAULT_PACS_NAME)))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].talukaId").value(hasItem(DEFAULT_TALUKA_ID.intValue())))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].zindagiPatrakDate").value(hasItem(DEFAULT_ZINDAGI_PATRAK_DATE.toString())))
            .andExpect(jsonPath("$.[*].zindagiPatrakDateMr").value(hasItem(DEFAULT_ZINDAGI_PATRAK_DATE_MR)))
            .andExpect(jsonPath("$.[*].bankTapasaniDate").value(hasItem(DEFAULT_BANK_TAPASANI_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankTapasaniDateMr").value(hasItem(DEFAULT_BANK_TAPASANI_DATE_MR)))
            .andExpect(jsonPath("$.[*].govTapasaniDate").value(hasItem(DEFAULT_GOV_TAPASANI_DATE.toString())))
            .andExpect(jsonPath("$.[*].govTapasaniDateMr").value(hasItem(DEFAULT_GOV_TAPASANI_DATE_MR)))
            .andExpect(jsonPath("$.[*].sansthaTapasaniDate").value(hasItem(DEFAULT_SANSTHA_TAPASANI_DATE.toString())))
            .andExpect(jsonPath("$.[*].sansthaTapasaniDateMr").value(hasItem(DEFAULT_SANSTHA_TAPASANI_DATE_MR)))
            .andExpect(jsonPath("$.[*].totalLand").value(hasItem(DEFAULT_TOTAL_LAND)))
            .andExpect(jsonPath("$.[*].bagayat").value(hasItem(DEFAULT_BAGAYAT)))
            .andExpect(jsonPath("$.[*].jirayat").value(hasItem(DEFAULT_JIRAYAT)))
            .andExpect(jsonPath("$.[*].totalFarmer").value(hasItem(DEFAULT_TOTAL_FARMER)))
            .andExpect(jsonPath("$.[*].memberFarmer").value(hasItem(DEFAULT_MEMBER_FARMER)))
            .andExpect(jsonPath("$.[*].nonMemberFarmer").value(hasItem(DEFAULT_NON_MEMBER_FARMER)))
            .andExpect(jsonPath("$.[*].talebandDate").value(hasItem(DEFAULT_TALEBAND_DATE.toString())))
            .andExpect(jsonPath("$.[*].memLoan").value(hasItem(DEFAULT_MEM_LOAN)))
            .andExpect(jsonPath("$.[*].memDue").value(hasItem(DEFAULT_MEM_DUE)))
            .andExpect(jsonPath("$.[*].memVasuli").value(hasItem(DEFAULT_MEM_VASULI)))
            .andExpect(jsonPath("$.[*].memVasuliPer").value(hasItem(DEFAULT_MEM_VASULI_PER)))
            .andExpect(jsonPath("$.[*].bankLoan").value(hasItem(DEFAULT_BANK_LOAN)))
            .andExpect(jsonPath("$.[*].bankDue").value(hasItem(DEFAULT_BANK_DUE)))
            .andExpect(jsonPath("$.[*].bankVasuli").value(hasItem(DEFAULT_BANK_VASULI)))
            .andExpect(jsonPath("$.[*].bankVasuliPer").value(hasItem(DEFAULT_BANK_VASULI_PER)))
            .andExpect(jsonPath("$.[*].balanceSheetDate").value(hasItem(DEFAULT_BALANCE_SHEET_DATE.toString())))
            .andExpect(jsonPath("$.[*].balanceSheetDateMr").value(hasItem(DEFAULT_BALANCE_SHEET_DATE_MR)))
            .andExpect(jsonPath("$.[*].liabilityAdhikrutShareCapital").value(hasItem(DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL)))
            .andExpect(jsonPath("$.[*].liabilityVasulShareCapital").value(hasItem(DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL)))
            .andExpect(jsonPath("$.[*].liabilityFund").value(hasItem(DEFAULT_LIABILITY_FUND)))
            .andExpect(jsonPath("$.[*].liabilitySpareFund").value(hasItem(DEFAULT_LIABILITY_SPARE_FUND)))
            .andExpect(jsonPath("$.[*].liabilityDeposite").value(hasItem(DEFAULT_LIABILITY_DEPOSITE)))
            .andExpect(jsonPath("$.[*].liabilityBalanceSheetBankLoan").value(hasItem(DEFAULT_LIABILITY_BALANCE_SHEET_BANK_LOAN)))
            .andExpect(jsonPath("$.[*].liabilityOtherPayable").value(hasItem(DEFAULT_LIABILITY_OTHER_PAYABLE)))
            .andExpect(jsonPath("$.[*].liabilityProfit").value(hasItem(DEFAULT_LIABILITY_PROFIT)))
            .andExpect(jsonPath("$.[*].assetCash").value(hasItem(DEFAULT_ASSET_CASH)))
            .andExpect(jsonPath("$.[*].assetInvestment").value(hasItem(DEFAULT_ASSET_INVESTMENT)))
            .andExpect(jsonPath("$.[*].assetImaratFund").value(hasItem(DEFAULT_ASSET_IMARAT_FUND)))
            .andExpect(jsonPath("$.[*].assetMemberLoan").value(hasItem(DEFAULT_ASSET_MEMBER_LOAN)))
            .andExpect(jsonPath("$.[*].assetDeadStock").value(hasItem(DEFAULT_ASSET_DEAD_STOCK)))
            .andExpect(jsonPath("$.[*].assetOtherReceivable").value(hasItem(DEFAULT_ASSET_OTHER_RECEIVABLE)))
            .andExpect(jsonPath("$.[*].assetLoss").value(hasItem(DEFAULT_ASSET_LOSS)))
            .andExpect(jsonPath("$.[*].totalLiability").value(hasItem(DEFAULT_TOTAL_LIABILITY)))
            .andExpect(jsonPath("$.[*].totalAsset").value(hasItem(DEFAULT_TOTAL_ASSET)))
            .andExpect(jsonPath("$.[*].villageCode").value(hasItem(DEFAULT_VILLAGE_CODE)))
            .andExpect(jsonPath("$.[*].pacsVerifiedFlag").value(hasItem(DEFAULT_PACS_VERIFIED_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].branchVerifiedFlag").value(hasItem(DEFAULT_BRANCH_VERIFIED_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].headOfficeVerifiedFlag").value(hasItem(DEFAULT_HEAD_OFFICE_VERIFIED_FLAG.booleanValue())))
            .andExpect(
                jsonPath("$.[*].divisionalOfficeVerifiedFlag").value(hasItem(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_FLAG.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].isSupplimenteryFlag").value(hasItem(DEFAULT_IS_SUPPLIMENTERY_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].sansthaTapasaniVarg").value(hasItem(DEFAULT_SANSTHA_TAPASANI_VARG)))
            .andExpect(jsonPath("$.[*].branchVerifiedBy").value(hasItem(DEFAULT_BRANCH_VERIFIED_BY)))
            .andExpect(jsonPath("$.[*].branchVerifiedDate").value(hasItem(DEFAULT_BRANCH_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].headOfficeVerifiedBy").value(hasItem(DEFAULT_HEAD_OFFICE_VERIFIED_BY)))
            .andExpect(jsonPath("$.[*].headOfficeVerifiedDate").value(hasItem(DEFAULT_HEAD_OFFICE_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].divisionalOfficeVerifiedBy").value(hasItem(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_BY)))
            .andExpect(jsonPath("$.[*].divisionalOfficeVerifiedDate").value(hasItem(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].doshPurtataDate").value(hasItem(DEFAULT_DOSH_PURTATA_DATE.toString())))
            .andExpect(jsonPath("$.[*].gambhirDosh").value(hasItem(DEFAULT_GAMBHIR_DOSH)))
            .andExpect(jsonPath("$.[*].branchInwardNumber").value(hasItem(DEFAULT_BRANCH_INWARD_NUMBER)))
            .andExpect(jsonPath("$.[*].branchInwardDate").value(hasItem(DEFAULT_BRANCH_INWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].branchOutwardNumber").value(hasItem(DEFAULT_BRANCH_OUTWARD_NUMBER)))
            .andExpect(jsonPath("$.[*].branchOutwardDate").value(hasItem(DEFAULT_BRANCH_OUTWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].headOfficeInwardNumber").value(hasItem(DEFAULT_HEAD_OFFICE_INWARD_NUMBER)))
            .andExpect(jsonPath("$.[*].headOfficeInwardDate").value(hasItem(DEFAULT_HEAD_OFFICE_INWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].headOfficeOutwardNumber").value(hasItem(DEFAULT_HEAD_OFFICE_OUTWARD_NUMBER)))
            .andExpect(jsonPath("$.[*].headOfficeOutwardDate").value(hasItem(DEFAULT_HEAD_OFFICE_OUTWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].tharavNumber").value(hasItem(DEFAULT_THARAV_NUMBER)))
            .andExpect(jsonPath("$.[*].tharavDate").value(hasItem(DEFAULT_THARAV_DATE.toString())));

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
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .kmDate(UPDATED_KM_DATE)
            .kmDateMr(UPDATED_KM_DATE_MR)
            .kmFromDate(UPDATED_KM_FROM_DATE)
            .kmFromDateMr(UPDATED_KM_FROM_DATE_MR)
            .kmToDate(UPDATED_KM_TO_DATE)
            .kmToDateMr(UPDATED_KM_TO_DATE_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .pacsName(UPDATED_PACS_NAME)
            .branchId(UPDATED_BRANCH_ID)
            .branchName(UPDATED_BRANCH_NAME)
            .talukaId(UPDATED_TALUKA_ID)
            .talukaName(UPDATED_TALUKA_NAME)
            .zindagiPatrakDate(UPDATED_ZINDAGI_PATRAK_DATE)
            .zindagiPatrakDateMr(UPDATED_ZINDAGI_PATRAK_DATE_MR)
            .bankTapasaniDate(UPDATED_BANK_TAPASANI_DATE)
            .bankTapasaniDateMr(UPDATED_BANK_TAPASANI_DATE_MR)
            .govTapasaniDate(UPDATED_GOV_TAPASANI_DATE)
            .govTapasaniDateMr(UPDATED_GOV_TAPASANI_DATE_MR)
            .sansthaTapasaniDate(UPDATED_SANSTHA_TAPASANI_DATE)
            .sansthaTapasaniDateMr(UPDATED_SANSTHA_TAPASANI_DATE_MR)
            .totalLand(UPDATED_TOTAL_LAND)
            .bagayat(UPDATED_BAGAYAT)
            .jirayat(UPDATED_JIRAYAT)
            .totalFarmer(UPDATED_TOTAL_FARMER)
            .memberFarmer(UPDATED_MEMBER_FARMER)
            .nonMemberFarmer(UPDATED_NON_MEMBER_FARMER)
            .talebandDate(UPDATED_TALEBAND_DATE)
            .memLoan(UPDATED_MEM_LOAN)
            .memDue(UPDATED_MEM_DUE)
            .memVasuli(UPDATED_MEM_VASULI)
            .memVasuliPer(UPDATED_MEM_VASULI_PER)
            .bankLoan(UPDATED_BANK_LOAN)
            .bankDue(UPDATED_BANK_DUE)
            .bankVasuli(UPDATED_BANK_VASULI)
            .bankVasuliPer(UPDATED_BANK_VASULI_PER)
            .balanceSheetDate(UPDATED_BALANCE_SHEET_DATE)
            .balanceSheetDateMr(UPDATED_BALANCE_SHEET_DATE_MR)
            .liabilityAdhikrutShareCapital(UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL)
            .liabilityVasulShareCapital(UPDATED_LIABILITY_VASUL_SHARE_CAPITAL)
            .liabilityFund(UPDATED_LIABILITY_FUND)
            .liabilitySpareFund(UPDATED_LIABILITY_SPARE_FUND)
            .liabilityDeposite(UPDATED_LIABILITY_DEPOSITE)
            .liabilityBalanceSheetBankLoan(UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN)
            .liabilityOtherPayable(UPDATED_LIABILITY_OTHER_PAYABLE)
            .liabilityProfit(UPDATED_LIABILITY_PROFIT)
            .assetCash(UPDATED_ASSET_CASH)
            .assetInvestment(UPDATED_ASSET_INVESTMENT)
            .assetImaratFund(UPDATED_ASSET_IMARAT_FUND)
            .assetMemberLoan(UPDATED_ASSET_MEMBER_LOAN)
            .assetDeadStock(UPDATED_ASSET_DEAD_STOCK)
            .assetOtherReceivable(UPDATED_ASSET_OTHER_RECEIVABLE)
            .assetLoss(UPDATED_ASSET_LOSS)
            .totalLiability(UPDATED_TOTAL_LIABILITY)
            .totalAsset(UPDATED_TOTAL_ASSET)
            .villageCode(UPDATED_VILLAGE_CODE)
            .pacsVerifiedFlag(UPDATED_PACS_VERIFIED_FLAG)
            .branchVerifiedFlag(UPDATED_BRANCH_VERIFIED_FLAG)
            .headOfficeVerifiedFlag(UPDATED_HEAD_OFFICE_VERIFIED_FLAG)
            .divisionalOfficeVerifiedFlag(UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG)
            .isSupplimenteryFlag(UPDATED_IS_SUPPLIMENTERY_FLAG)
            .sansthaTapasaniVarg(UPDATED_SANSTHA_TAPASANI_VARG)
            .branchVerifiedBy(UPDATED_BRANCH_VERIFIED_BY)
            .branchVerifiedDate(UPDATED_BRANCH_VERIFIED_DATE)
            .headOfficeVerifiedBy(UPDATED_HEAD_OFFICE_VERIFIED_BY)
            .headOfficeVerifiedDate(UPDATED_HEAD_OFFICE_VERIFIED_DATE)
            .divisionalOfficeVerifiedBy(UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY)
            .divisionalOfficeVerifiedDate(UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE)
            .doshPurtataDate(UPDATED_DOSH_PURTATA_DATE)
            .gambhirDosh(UPDATED_GAMBHIR_DOSH)
         /*   .branchInwardNumber(UPDATED_BRANCH_INWARD_NUMBER)
            .branchInwardDate(UPDATED_BRANCH_INWARD_DATE)
            .branchOutwardNumber(UPDATED_BRANCH_OUTWARD_NUMBER)
            .branchOutwardDate(UPDATED_BRANCH_OUTWARD_DATE)
            .headOfficeInwardNumber(UPDATED_HEAD_OFFICE_INWARD_NUMBER)
            .headOfficeInwardDate(UPDATED_HEAD_OFFICE_INWARD_DATE)
            .headOfficeOutwardNumber(UPDATED_HEAD_OFFICE_OUTWARD_NUMBER)
            .headOfficeOutwardDate(UPDATED_HEAD_OFFICE_OUTWARD_DATE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDate(UPDATED_THARAV_DATE)*/;

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
        assertThat(testKamalSociety.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKamalSociety.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKamalSociety.getKmDateMr()).isEqualTo(UPDATED_KM_DATE_MR);
        assertThat(testKamalSociety.getKmFromDate()).isEqualTo(UPDATED_KM_FROM_DATE);
        assertThat(testKamalSociety.getKmFromDateMr()).isEqualTo(UPDATED_KM_FROM_DATE_MR);
        assertThat(testKamalSociety.getKmToDate()).isEqualTo(UPDATED_KM_TO_DATE);
        assertThat(testKamalSociety.getKmToDateMr()).isEqualTo(UPDATED_KM_TO_DATE_MR);
        assertThat(testKamalSociety.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalSociety.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
        assertThat(testKamalSociety.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testKamalSociety.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testKamalSociety.getTalukaId()).isEqualTo(UPDATED_TALUKA_ID);
        assertThat(testKamalSociety.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testKamalSociety.getZindagiPatrakDate()).isEqualTo(UPDATED_ZINDAGI_PATRAK_DATE);
        assertThat(testKamalSociety.getZindagiPatrakDateMr()).isEqualTo(UPDATED_ZINDAGI_PATRAK_DATE_MR);
        assertThat(testKamalSociety.getBankTapasaniDate()).isEqualTo(UPDATED_BANK_TAPASANI_DATE);
        assertThat(testKamalSociety.getBankTapasaniDateMr()).isEqualTo(UPDATED_BANK_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getGovTapasaniDate()).isEqualTo(UPDATED_GOV_TAPASANI_DATE);
        assertThat(testKamalSociety.getGovTapasaniDateMr()).isEqualTo(UPDATED_GOV_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getSansthaTapasaniDate()).isEqualTo(UPDATED_SANSTHA_TAPASANI_DATE);
        assertThat(testKamalSociety.getSansthaTapasaniDateMr()).isEqualTo(UPDATED_SANSTHA_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getTotalLand()).isEqualTo(UPDATED_TOTAL_LAND);
        assertThat(testKamalSociety.getBagayat()).isEqualTo(UPDATED_BAGAYAT);
        assertThat(testKamalSociety.getJirayat()).isEqualTo(UPDATED_JIRAYAT);
        assertThat(testKamalSociety.getTotalFarmer()).isEqualTo(UPDATED_TOTAL_FARMER);
        assertThat(testKamalSociety.getMemberFarmer()).isEqualTo(UPDATED_MEMBER_FARMER);
        assertThat(testKamalSociety.getNonMemberFarmer()).isEqualTo(UPDATED_NON_MEMBER_FARMER);
        assertThat(testKamalSociety.getTalebandDate()).isEqualTo(UPDATED_TALEBAND_DATE);
        assertThat(testKamalSociety.getMemLoan()).isEqualTo(UPDATED_MEM_LOAN);
        assertThat(testKamalSociety.getMemDue()).isEqualTo(UPDATED_MEM_DUE);
        assertThat(testKamalSociety.getMemVasuli()).isEqualTo(UPDATED_MEM_VASULI);
        assertThat(testKamalSociety.getMemVasuliPer()).isEqualTo(UPDATED_MEM_VASULI_PER);
        assertThat(testKamalSociety.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testKamalSociety.getBankDue()).isEqualTo(UPDATED_BANK_DUE);
        assertThat(testKamalSociety.getBankVasuli()).isEqualTo(UPDATED_BANK_VASULI);
        assertThat(testKamalSociety.getBankVasuliPer()).isEqualTo(UPDATED_BANK_VASULI_PER);
        assertThat(testKamalSociety.getBalanceSheetDate()).isEqualTo(UPDATED_BALANCE_SHEET_DATE);
        assertThat(testKamalSociety.getBalanceSheetDateMr()).isEqualTo(UPDATED_BALANCE_SHEET_DATE_MR);
        assertThat(testKamalSociety.getLiabilityAdhikrutShareCapital()).isEqualTo(UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL);
        assertThat(testKamalSociety.getLiabilityVasulShareCapital()).isEqualTo(UPDATED_LIABILITY_VASUL_SHARE_CAPITAL);
        assertThat(testKamalSociety.getLiabilityFund()).isEqualTo(UPDATED_LIABILITY_FUND);
        assertThat(testKamalSociety.getLiabilitySpareFund()).isEqualTo(UPDATED_LIABILITY_SPARE_FUND);
        assertThat(testKamalSociety.getLiabilityDeposite()).isEqualTo(UPDATED_LIABILITY_DEPOSITE);
        assertThat(testKamalSociety.getLiabilityBalanceSheetBankLoan()).isEqualTo(UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN);
        assertThat(testKamalSociety.getLiabilityOtherPayable()).isEqualTo(UPDATED_LIABILITY_OTHER_PAYABLE);
        assertThat(testKamalSociety.getLiabilityProfit()).isEqualTo(UPDATED_LIABILITY_PROFIT);
        assertThat(testKamalSociety.getAssetCash()).isEqualTo(UPDATED_ASSET_CASH);
        assertThat(testKamalSociety.getAssetInvestment()).isEqualTo(UPDATED_ASSET_INVESTMENT);
        assertThat(testKamalSociety.getAssetImaratFund()).isEqualTo(UPDATED_ASSET_IMARAT_FUND);
        assertThat(testKamalSociety.getAssetMemberLoan()).isEqualTo(UPDATED_ASSET_MEMBER_LOAN);
        assertThat(testKamalSociety.getAssetDeadStock()).isEqualTo(UPDATED_ASSET_DEAD_STOCK);
        assertThat(testKamalSociety.getAssetOtherReceivable()).isEqualTo(UPDATED_ASSET_OTHER_RECEIVABLE);
        assertThat(testKamalSociety.getAssetLoss()).isEqualTo(UPDATED_ASSET_LOSS);
        assertThat(testKamalSociety.getTotalLiability()).isEqualTo(UPDATED_TOTAL_LIABILITY);
        assertThat(testKamalSociety.getTotalAsset()).isEqualTo(UPDATED_TOTAL_ASSET);
        assertThat(testKamalSociety.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testKamalSociety.getPacsVerifiedFlag()).isEqualTo(UPDATED_PACS_VERIFIED_FLAG);
        assertThat(testKamalSociety.getBranchVerifiedFlag()).isEqualTo(UPDATED_BRANCH_VERIFIED_FLAG);
        assertThat(testKamalSociety.getHeadOfficeVerifiedFlag()).isEqualTo(UPDATED_HEAD_OFFICE_VERIFIED_FLAG);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedFlag()).isEqualTo(UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG);
        assertThat(testKamalSociety.getIsSupplimenteryFlag()).isEqualTo(UPDATED_IS_SUPPLIMENTERY_FLAG);
        assertThat(testKamalSociety.getSansthaTapasaniVarg()).isEqualTo(UPDATED_SANSTHA_TAPASANI_VARG);
        assertThat(testKamalSociety.getBranchVerifiedBy()).isEqualTo(UPDATED_BRANCH_VERIFIED_BY);
        assertThat(testKamalSociety.getBranchVerifiedDate()).isEqualTo(UPDATED_BRANCH_VERIFIED_DATE);
        assertThat(testKamalSociety.getHeadOfficeVerifiedBy()).isEqualTo(UPDATED_HEAD_OFFICE_VERIFIED_BY);
        assertThat(testKamalSociety.getHeadOfficeVerifiedDate()).isEqualTo(UPDATED_HEAD_OFFICE_VERIFIED_DATE);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedBy()).isEqualTo(UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedDate()).isEqualTo(UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE);
        assertThat(testKamalSociety.getDoshPurtataDate()).isEqualTo(UPDATED_DOSH_PURTATA_DATE);
        assertThat(testKamalSociety.getGambhirDosh()).isEqualTo(UPDATED_GAMBHIR_DOSH);
        assertThat(testKamalSociety.getBranchInwardNumber()).isEqualTo(UPDATED_BRANCH_INWARD_NUMBER);
        assertThat(testKamalSociety.getBranchInwardDate()).isEqualTo(UPDATED_BRANCH_INWARD_DATE);
        assertThat(testKamalSociety.getBranchOutwardNumber()).isEqualTo(UPDATED_BRANCH_OUTWARD_NUMBER);
        assertThat(testKamalSociety.getBranchOutwardDate()).isEqualTo(UPDATED_BRANCH_OUTWARD_DATE);
        assertThat(testKamalSociety.getHeadOfficeInwardNumber()).isEqualTo(UPDATED_HEAD_OFFICE_INWARD_NUMBER);
        assertThat(testKamalSociety.getHeadOfficeInwardDate()).isEqualTo(UPDATED_HEAD_OFFICE_INWARD_DATE);
        assertThat(testKamalSociety.getHeadOfficeOutwardNumber()).isEqualTo(UPDATED_HEAD_OFFICE_OUTWARD_NUMBER);
        assertThat(testKamalSociety.getHeadOfficeOutwardDate()).isEqualTo(UPDATED_HEAD_OFFICE_OUTWARD_DATE);
        assertThat(testKamalSociety.getTharavNumber()).isEqualTo(UPDATED_THARAV_NUMBER);
        assertThat(testKamalSociety.getTharavDate()).isEqualTo(UPDATED_THARAV_DATE);
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
            .kmDate(UPDATED_KM_DATE)
            .kmFromDate(UPDATED_KM_FROM_DATE)
            .kmToDateMr(UPDATED_KM_TO_DATE_MR)
            .branchName(UPDATED_BRANCH_NAME)
            .bankTapasaniDate(UPDATED_BANK_TAPASANI_DATE)
            .govTapasaniDateMr(UPDATED_GOV_TAPASANI_DATE_MR)
            .sansthaTapasaniDate(UPDATED_SANSTHA_TAPASANI_DATE)
            .sansthaTapasaniDateMr(UPDATED_SANSTHA_TAPASANI_DATE_MR)
            .totalLand(UPDATED_TOTAL_LAND)
            .talebandDate(UPDATED_TALEBAND_DATE)
            .memLoan(UPDATED_MEM_LOAN)
            .memVasuliPer(UPDATED_MEM_VASULI_PER)
            .bankDue(UPDATED_BANK_DUE)
            .bankVasuli(UPDATED_BANK_VASULI)
            .bankVasuliPer(UPDATED_BANK_VASULI_PER)
            .balanceSheetDate(UPDATED_BALANCE_SHEET_DATE)
            .balanceSheetDateMr(UPDATED_BALANCE_SHEET_DATE_MR)
            .liabilityDeposite(UPDATED_LIABILITY_DEPOSITE)
            .liabilityBalanceSheetBankLoan(UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN)
            .liabilityOtherPayable(UPDATED_LIABILITY_OTHER_PAYABLE)
            .assetImaratFund(UPDATED_ASSET_IMARAT_FUND)
            .assetMemberLoan(UPDATED_ASSET_MEMBER_LOAN)
            .assetDeadStock(UPDATED_ASSET_DEAD_STOCK)
            .assetOtherReceivable(UPDATED_ASSET_OTHER_RECEIVABLE)
            .totalLiability(UPDATED_TOTAL_LIABILITY)
            .villageCode(UPDATED_VILLAGE_CODE)
            .branchVerifiedFlag(UPDATED_BRANCH_VERIFIED_FLAG)
            .headOfficeVerifiedFlag(UPDATED_HEAD_OFFICE_VERIFIED_FLAG)
            .divisionalOfficeVerifiedFlag(UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG)
            .isSupplimenteryFlag(UPDATED_IS_SUPPLIMENTERY_FLAG)
            .sansthaTapasaniVarg(UPDATED_SANSTHA_TAPASANI_VARG)
            .divisionalOfficeVerifiedBy(UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY)
           /* .branchInwardNumber(UPDATED_BRANCH_INWARD_NUMBER)
            .branchInwardDate(UPDATED_BRANCH_INWARD_DATE)
            .branchOutwardNumber(UPDATED_BRANCH_OUTWARD_NUMBER)
            .branchOutwardDate(UPDATED_BRANCH_OUTWARD_DATE)
            .headOfficeOutwardNumber(UPDATED_HEAD_OFFICE_OUTWARD_NUMBER)*/;

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
        assertThat(testKamalSociety.getFinancialYear()).isEqualTo(DEFAULT_FINANCIAL_YEAR);
        assertThat(testKamalSociety.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKamalSociety.getKmDateMr()).isEqualTo(DEFAULT_KM_DATE_MR);
        assertThat(testKamalSociety.getKmFromDate()).isEqualTo(UPDATED_KM_FROM_DATE);
        assertThat(testKamalSociety.getKmFromDateMr()).isEqualTo(DEFAULT_KM_FROM_DATE_MR);
        assertThat(testKamalSociety.getKmToDate()).isEqualTo(DEFAULT_KM_TO_DATE);
        assertThat(testKamalSociety.getKmToDateMr()).isEqualTo(UPDATED_KM_TO_DATE_MR);
        assertThat(testKamalSociety.getPacsNumber()).isEqualTo(DEFAULT_PACS_NUMBER);
        assertThat(testKamalSociety.getPacsName()).isEqualTo(DEFAULT_PACS_NAME);
        assertThat(testKamalSociety.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testKamalSociety.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testKamalSociety.getTalukaId()).isEqualTo(DEFAULT_TALUKA_ID);
        assertThat(testKamalSociety.getTalukaName()).isEqualTo(DEFAULT_TALUKA_NAME);
        assertThat(testKamalSociety.getZindagiPatrakDate()).isEqualTo(DEFAULT_ZINDAGI_PATRAK_DATE);
        assertThat(testKamalSociety.getZindagiPatrakDateMr()).isEqualTo(DEFAULT_ZINDAGI_PATRAK_DATE_MR);
        assertThat(testKamalSociety.getBankTapasaniDate()).isEqualTo(UPDATED_BANK_TAPASANI_DATE);
        assertThat(testKamalSociety.getBankTapasaniDateMr()).isEqualTo(DEFAULT_BANK_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getGovTapasaniDate()).isEqualTo(DEFAULT_GOV_TAPASANI_DATE);
        assertThat(testKamalSociety.getGovTapasaniDateMr()).isEqualTo(UPDATED_GOV_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getSansthaTapasaniDate()).isEqualTo(UPDATED_SANSTHA_TAPASANI_DATE);
        assertThat(testKamalSociety.getSansthaTapasaniDateMr()).isEqualTo(UPDATED_SANSTHA_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getTotalLand()).isEqualTo(UPDATED_TOTAL_LAND);
        assertThat(testKamalSociety.getBagayat()).isEqualTo(DEFAULT_BAGAYAT);
        assertThat(testKamalSociety.getJirayat()).isEqualTo(DEFAULT_JIRAYAT);
        assertThat(testKamalSociety.getTotalFarmer()).isEqualTo(DEFAULT_TOTAL_FARMER);
        assertThat(testKamalSociety.getMemberFarmer()).isEqualTo(DEFAULT_MEMBER_FARMER);
        assertThat(testKamalSociety.getNonMemberFarmer()).isEqualTo(DEFAULT_NON_MEMBER_FARMER);
        assertThat(testKamalSociety.getTalebandDate()).isEqualTo(UPDATED_TALEBAND_DATE);
        assertThat(testKamalSociety.getMemLoan()).isEqualTo(UPDATED_MEM_LOAN);
        assertThat(testKamalSociety.getMemDue()).isEqualTo(DEFAULT_MEM_DUE);
        assertThat(testKamalSociety.getMemVasuli()).isEqualTo(DEFAULT_MEM_VASULI);
        assertThat(testKamalSociety.getMemVasuliPer()).isEqualTo(UPDATED_MEM_VASULI_PER);
        assertThat(testKamalSociety.getBankLoan()).isEqualTo(DEFAULT_BANK_LOAN);
        assertThat(testKamalSociety.getBankDue()).isEqualTo(UPDATED_BANK_DUE);
        assertThat(testKamalSociety.getBankVasuli()).isEqualTo(UPDATED_BANK_VASULI);
        assertThat(testKamalSociety.getBankVasuliPer()).isEqualTo(UPDATED_BANK_VASULI_PER);
        assertThat(testKamalSociety.getBalanceSheetDate()).isEqualTo(UPDATED_BALANCE_SHEET_DATE);
        assertThat(testKamalSociety.getBalanceSheetDateMr()).isEqualTo(UPDATED_BALANCE_SHEET_DATE_MR);
        assertThat(testKamalSociety.getLiabilityAdhikrutShareCapital()).isEqualTo(DEFAULT_LIABILITY_ADHIKRUT_SHARE_CAPITAL);
        assertThat(testKamalSociety.getLiabilityVasulShareCapital()).isEqualTo(DEFAULT_LIABILITY_VASUL_SHARE_CAPITAL);
        assertThat(testKamalSociety.getLiabilityFund()).isEqualTo(DEFAULT_LIABILITY_FUND);
        assertThat(testKamalSociety.getLiabilitySpareFund()).isEqualTo(DEFAULT_LIABILITY_SPARE_FUND);
        assertThat(testKamalSociety.getLiabilityDeposite()).isEqualTo(UPDATED_LIABILITY_DEPOSITE);
        assertThat(testKamalSociety.getLiabilityBalanceSheetBankLoan()).isEqualTo(UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN);
        assertThat(testKamalSociety.getLiabilityOtherPayable()).isEqualTo(UPDATED_LIABILITY_OTHER_PAYABLE);
        assertThat(testKamalSociety.getLiabilityProfit()).isEqualTo(DEFAULT_LIABILITY_PROFIT);
        assertThat(testKamalSociety.getAssetCash()).isEqualTo(DEFAULT_ASSET_CASH);
        assertThat(testKamalSociety.getAssetInvestment()).isEqualTo(DEFAULT_ASSET_INVESTMENT);
        assertThat(testKamalSociety.getAssetImaratFund()).isEqualTo(UPDATED_ASSET_IMARAT_FUND);
        assertThat(testKamalSociety.getAssetMemberLoan()).isEqualTo(UPDATED_ASSET_MEMBER_LOAN);
        assertThat(testKamalSociety.getAssetDeadStock()).isEqualTo(UPDATED_ASSET_DEAD_STOCK);
        assertThat(testKamalSociety.getAssetOtherReceivable()).isEqualTo(UPDATED_ASSET_OTHER_RECEIVABLE);
        assertThat(testKamalSociety.getAssetLoss()).isEqualTo(DEFAULT_ASSET_LOSS);
        assertThat(testKamalSociety.getTotalLiability()).isEqualTo(UPDATED_TOTAL_LIABILITY);
        assertThat(testKamalSociety.getTotalAsset()).isEqualTo(DEFAULT_TOTAL_ASSET);
        assertThat(testKamalSociety.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testKamalSociety.getPacsVerifiedFlag()).isEqualTo(DEFAULT_PACS_VERIFIED_FLAG);
        assertThat(testKamalSociety.getBranchVerifiedFlag()).isEqualTo(UPDATED_BRANCH_VERIFIED_FLAG);
        assertThat(testKamalSociety.getHeadOfficeVerifiedFlag()).isEqualTo(UPDATED_HEAD_OFFICE_VERIFIED_FLAG);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedFlag()).isEqualTo(UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG);
        assertThat(testKamalSociety.getIsSupplimenteryFlag()).isEqualTo(UPDATED_IS_SUPPLIMENTERY_FLAG);
        assertThat(testKamalSociety.getSansthaTapasaniVarg()).isEqualTo(UPDATED_SANSTHA_TAPASANI_VARG);
        assertThat(testKamalSociety.getBranchVerifiedBy()).isEqualTo(DEFAULT_BRANCH_VERIFIED_BY);
        assertThat(testKamalSociety.getBranchVerifiedDate()).isEqualTo(DEFAULT_BRANCH_VERIFIED_DATE);
        assertThat(testKamalSociety.getHeadOfficeVerifiedBy()).isEqualTo(DEFAULT_HEAD_OFFICE_VERIFIED_BY);
        assertThat(testKamalSociety.getHeadOfficeVerifiedDate()).isEqualTo(DEFAULT_HEAD_OFFICE_VERIFIED_DATE);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedBy()).isEqualTo(UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedDate()).isEqualTo(DEFAULT_DIVISIONAL_OFFICE_VERIFIED_DATE);
        assertThat(testKamalSociety.getDoshPurtataDate()).isEqualTo(DEFAULT_DOSH_PURTATA_DATE);
        assertThat(testKamalSociety.getGambhirDosh()).isEqualTo(DEFAULT_GAMBHIR_DOSH);
        assertThat(testKamalSociety.getBranchInwardNumber()).isEqualTo(UPDATED_BRANCH_INWARD_NUMBER);
        assertThat(testKamalSociety.getBranchInwardDate()).isEqualTo(UPDATED_BRANCH_INWARD_DATE);
        assertThat(testKamalSociety.getBranchOutwardNumber()).isEqualTo(UPDATED_BRANCH_OUTWARD_NUMBER);
        assertThat(testKamalSociety.getBranchOutwardDate()).isEqualTo(UPDATED_BRANCH_OUTWARD_DATE);
        assertThat(testKamalSociety.getHeadOfficeInwardNumber()).isEqualTo(DEFAULT_HEAD_OFFICE_INWARD_NUMBER);
        assertThat(testKamalSociety.getHeadOfficeInwardDate()).isEqualTo(DEFAULT_HEAD_OFFICE_INWARD_DATE);
        assertThat(testKamalSociety.getHeadOfficeOutwardNumber()).isEqualTo(UPDATED_HEAD_OFFICE_OUTWARD_NUMBER);
        assertThat(testKamalSociety.getHeadOfficeOutwardDate()).isEqualTo(DEFAULT_HEAD_OFFICE_OUTWARD_DATE);
        assertThat(testKamalSociety.getTharavNumber()).isEqualTo(DEFAULT_THARAV_NUMBER);
        assertThat(testKamalSociety.getTharavDate()).isEqualTo(DEFAULT_THARAV_DATE);
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
            .financialYear(UPDATED_FINANCIAL_YEAR)
            .kmDate(UPDATED_KM_DATE)
            .kmDateMr(UPDATED_KM_DATE_MR)
            .kmFromDate(UPDATED_KM_FROM_DATE)
            .kmFromDateMr(UPDATED_KM_FROM_DATE_MR)
            .kmToDate(UPDATED_KM_TO_DATE)
            .kmToDateMr(UPDATED_KM_TO_DATE_MR)
            .pacsNumber(UPDATED_PACS_NUMBER)
            .pacsName(UPDATED_PACS_NAME)
            .branchId(UPDATED_BRANCH_ID)
            .branchName(UPDATED_BRANCH_NAME)
            .talukaId(UPDATED_TALUKA_ID)
            .talukaName(UPDATED_TALUKA_NAME)
            .zindagiPatrakDate(UPDATED_ZINDAGI_PATRAK_DATE)
            .zindagiPatrakDateMr(UPDATED_ZINDAGI_PATRAK_DATE_MR)
            .bankTapasaniDate(UPDATED_BANK_TAPASANI_DATE)
            .bankTapasaniDateMr(UPDATED_BANK_TAPASANI_DATE_MR)
            .govTapasaniDate(UPDATED_GOV_TAPASANI_DATE)
            .govTapasaniDateMr(UPDATED_GOV_TAPASANI_DATE_MR)
            .sansthaTapasaniDate(UPDATED_SANSTHA_TAPASANI_DATE)
            .sansthaTapasaniDateMr(UPDATED_SANSTHA_TAPASANI_DATE_MR)
            .totalLand(UPDATED_TOTAL_LAND)
            .bagayat(UPDATED_BAGAYAT)
            .jirayat(UPDATED_JIRAYAT)
            .totalFarmer(UPDATED_TOTAL_FARMER)
            .memberFarmer(UPDATED_MEMBER_FARMER)
            .nonMemberFarmer(UPDATED_NON_MEMBER_FARMER)
            .talebandDate(UPDATED_TALEBAND_DATE)
            .memLoan(UPDATED_MEM_LOAN)
            .memDue(UPDATED_MEM_DUE)
            .memVasuli(UPDATED_MEM_VASULI)
            .memVasuliPer(UPDATED_MEM_VASULI_PER)
            .bankLoan(UPDATED_BANK_LOAN)
            .bankDue(UPDATED_BANK_DUE)
            .bankVasuli(UPDATED_BANK_VASULI)
            .bankVasuliPer(UPDATED_BANK_VASULI_PER)
            .balanceSheetDate(UPDATED_BALANCE_SHEET_DATE)
            .balanceSheetDateMr(UPDATED_BALANCE_SHEET_DATE_MR)
            .liabilityAdhikrutShareCapital(UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL)
            .liabilityVasulShareCapital(UPDATED_LIABILITY_VASUL_SHARE_CAPITAL)
            .liabilityFund(UPDATED_LIABILITY_FUND)
            .liabilitySpareFund(UPDATED_LIABILITY_SPARE_FUND)
            .liabilityDeposite(UPDATED_LIABILITY_DEPOSITE)
            .liabilityBalanceSheetBankLoan(UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN)
            .liabilityOtherPayable(UPDATED_LIABILITY_OTHER_PAYABLE)
            .liabilityProfit(UPDATED_LIABILITY_PROFIT)
            .assetCash(UPDATED_ASSET_CASH)
            .assetInvestment(UPDATED_ASSET_INVESTMENT)
            .assetImaratFund(UPDATED_ASSET_IMARAT_FUND)
            .assetMemberLoan(UPDATED_ASSET_MEMBER_LOAN)
            .assetDeadStock(UPDATED_ASSET_DEAD_STOCK)
            .assetOtherReceivable(UPDATED_ASSET_OTHER_RECEIVABLE)
            .assetLoss(UPDATED_ASSET_LOSS)
            .totalLiability(UPDATED_TOTAL_LIABILITY)
            .totalAsset(UPDATED_TOTAL_ASSET)
            .villageCode(UPDATED_VILLAGE_CODE)
            .pacsVerifiedFlag(UPDATED_PACS_VERIFIED_FLAG)
            .branchVerifiedFlag(UPDATED_BRANCH_VERIFIED_FLAG)
            .headOfficeVerifiedFlag(UPDATED_HEAD_OFFICE_VERIFIED_FLAG)
            .divisionalOfficeVerifiedFlag(UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG)
            .isSupplimenteryFlag(UPDATED_IS_SUPPLIMENTERY_FLAG)
            .sansthaTapasaniVarg(UPDATED_SANSTHA_TAPASANI_VARG)
            .branchVerifiedBy(UPDATED_BRANCH_VERIFIED_BY)
            .branchVerifiedDate(UPDATED_BRANCH_VERIFIED_DATE)
            .headOfficeVerifiedBy(UPDATED_HEAD_OFFICE_VERIFIED_BY)
            .headOfficeVerifiedDate(UPDATED_HEAD_OFFICE_VERIFIED_DATE)
            .divisionalOfficeVerifiedBy(UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY)
            .divisionalOfficeVerifiedDate(UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE)
            .doshPurtataDate(UPDATED_DOSH_PURTATA_DATE)
            .gambhirDosh(UPDATED_GAMBHIR_DOSH)
        /*    .branchInwardNumber(UPDATED_BRANCH_INWARD_NUMBER)
            .branchInwardDate(UPDATED_BRANCH_INWARD_DATE)
            .branchOutwardNumber(UPDATED_BRANCH_OUTWARD_NUMBER)
            .branchOutwardDate(UPDATED_BRANCH_OUTWARD_DATE)
            .headOfficeInwardNumber(UPDATED_HEAD_OFFICE_INWARD_NUMBER)
            .headOfficeInwardDate(UPDATED_HEAD_OFFICE_INWARD_DATE)
            .headOfficeOutwardNumber(UPDATED_HEAD_OFFICE_OUTWARD_NUMBER)
            .headOfficeOutwardDate(UPDATED_HEAD_OFFICE_OUTWARD_DATE)
            .tharavNumber(UPDATED_THARAV_NUMBER)
            .tharavDate(UPDATED_THARAV_DATE)*/;

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
        assertThat(testKamalSociety.getFinancialYear()).isEqualTo(UPDATED_FINANCIAL_YEAR);
        assertThat(testKamalSociety.getKmDate()).isEqualTo(UPDATED_KM_DATE);
        assertThat(testKamalSociety.getKmDateMr()).isEqualTo(UPDATED_KM_DATE_MR);
        assertThat(testKamalSociety.getKmFromDate()).isEqualTo(UPDATED_KM_FROM_DATE);
        assertThat(testKamalSociety.getKmFromDateMr()).isEqualTo(UPDATED_KM_FROM_DATE_MR);
        assertThat(testKamalSociety.getKmToDate()).isEqualTo(UPDATED_KM_TO_DATE);
        assertThat(testKamalSociety.getKmToDateMr()).isEqualTo(UPDATED_KM_TO_DATE_MR);
        assertThat(testKamalSociety.getPacsNumber()).isEqualTo(UPDATED_PACS_NUMBER);
        assertThat(testKamalSociety.getPacsName()).isEqualTo(UPDATED_PACS_NAME);
        assertThat(testKamalSociety.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testKamalSociety.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testKamalSociety.getTalukaId()).isEqualTo(UPDATED_TALUKA_ID);
        assertThat(testKamalSociety.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testKamalSociety.getZindagiPatrakDate()).isEqualTo(UPDATED_ZINDAGI_PATRAK_DATE);
        assertThat(testKamalSociety.getZindagiPatrakDateMr()).isEqualTo(UPDATED_ZINDAGI_PATRAK_DATE_MR);
        assertThat(testKamalSociety.getBankTapasaniDate()).isEqualTo(UPDATED_BANK_TAPASANI_DATE);
        assertThat(testKamalSociety.getBankTapasaniDateMr()).isEqualTo(UPDATED_BANK_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getGovTapasaniDate()).isEqualTo(UPDATED_GOV_TAPASANI_DATE);
        assertThat(testKamalSociety.getGovTapasaniDateMr()).isEqualTo(UPDATED_GOV_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getSansthaTapasaniDate()).isEqualTo(UPDATED_SANSTHA_TAPASANI_DATE);
        assertThat(testKamalSociety.getSansthaTapasaniDateMr()).isEqualTo(UPDATED_SANSTHA_TAPASANI_DATE_MR);
        assertThat(testKamalSociety.getTotalLand()).isEqualTo(UPDATED_TOTAL_LAND);
        assertThat(testKamalSociety.getBagayat()).isEqualTo(UPDATED_BAGAYAT);
        assertThat(testKamalSociety.getJirayat()).isEqualTo(UPDATED_JIRAYAT);
        assertThat(testKamalSociety.getTotalFarmer()).isEqualTo(UPDATED_TOTAL_FARMER);
        assertThat(testKamalSociety.getMemberFarmer()).isEqualTo(UPDATED_MEMBER_FARMER);
        assertThat(testKamalSociety.getNonMemberFarmer()).isEqualTo(UPDATED_NON_MEMBER_FARMER);
        assertThat(testKamalSociety.getTalebandDate()).isEqualTo(UPDATED_TALEBAND_DATE);
        assertThat(testKamalSociety.getMemLoan()).isEqualTo(UPDATED_MEM_LOAN);
        assertThat(testKamalSociety.getMemDue()).isEqualTo(UPDATED_MEM_DUE);
        assertThat(testKamalSociety.getMemVasuli()).isEqualTo(UPDATED_MEM_VASULI);
        assertThat(testKamalSociety.getMemVasuliPer()).isEqualTo(UPDATED_MEM_VASULI_PER);
        assertThat(testKamalSociety.getBankLoan()).isEqualTo(UPDATED_BANK_LOAN);
        assertThat(testKamalSociety.getBankDue()).isEqualTo(UPDATED_BANK_DUE);
        assertThat(testKamalSociety.getBankVasuli()).isEqualTo(UPDATED_BANK_VASULI);
        assertThat(testKamalSociety.getBankVasuliPer()).isEqualTo(UPDATED_BANK_VASULI_PER);
        assertThat(testKamalSociety.getBalanceSheetDate()).isEqualTo(UPDATED_BALANCE_SHEET_DATE);
        assertThat(testKamalSociety.getBalanceSheetDateMr()).isEqualTo(UPDATED_BALANCE_SHEET_DATE_MR);
        assertThat(testKamalSociety.getLiabilityAdhikrutShareCapital()).isEqualTo(UPDATED_LIABILITY_ADHIKRUT_SHARE_CAPITAL);
        assertThat(testKamalSociety.getLiabilityVasulShareCapital()).isEqualTo(UPDATED_LIABILITY_VASUL_SHARE_CAPITAL);
        assertThat(testKamalSociety.getLiabilityFund()).isEqualTo(UPDATED_LIABILITY_FUND);
        assertThat(testKamalSociety.getLiabilitySpareFund()).isEqualTo(UPDATED_LIABILITY_SPARE_FUND);
        assertThat(testKamalSociety.getLiabilityDeposite()).isEqualTo(UPDATED_LIABILITY_DEPOSITE);
        assertThat(testKamalSociety.getLiabilityBalanceSheetBankLoan()).isEqualTo(UPDATED_LIABILITY_BALANCE_SHEET_BANK_LOAN);
        assertThat(testKamalSociety.getLiabilityOtherPayable()).isEqualTo(UPDATED_LIABILITY_OTHER_PAYABLE);
        assertThat(testKamalSociety.getLiabilityProfit()).isEqualTo(UPDATED_LIABILITY_PROFIT);
        assertThat(testKamalSociety.getAssetCash()).isEqualTo(UPDATED_ASSET_CASH);
        assertThat(testKamalSociety.getAssetInvestment()).isEqualTo(UPDATED_ASSET_INVESTMENT);
        assertThat(testKamalSociety.getAssetImaratFund()).isEqualTo(UPDATED_ASSET_IMARAT_FUND);
        assertThat(testKamalSociety.getAssetMemberLoan()).isEqualTo(UPDATED_ASSET_MEMBER_LOAN);
        assertThat(testKamalSociety.getAssetDeadStock()).isEqualTo(UPDATED_ASSET_DEAD_STOCK);
        assertThat(testKamalSociety.getAssetOtherReceivable()).isEqualTo(UPDATED_ASSET_OTHER_RECEIVABLE);
        assertThat(testKamalSociety.getAssetLoss()).isEqualTo(UPDATED_ASSET_LOSS);
        assertThat(testKamalSociety.getTotalLiability()).isEqualTo(UPDATED_TOTAL_LIABILITY);
        assertThat(testKamalSociety.getTotalAsset()).isEqualTo(UPDATED_TOTAL_ASSET);
        assertThat(testKamalSociety.getVillageCode()).isEqualTo(UPDATED_VILLAGE_CODE);
        assertThat(testKamalSociety.getPacsVerifiedFlag()).isEqualTo(UPDATED_PACS_VERIFIED_FLAG);
        assertThat(testKamalSociety.getBranchVerifiedFlag()).isEqualTo(UPDATED_BRANCH_VERIFIED_FLAG);
        assertThat(testKamalSociety.getHeadOfficeVerifiedFlag()).isEqualTo(UPDATED_HEAD_OFFICE_VERIFIED_FLAG);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedFlag()).isEqualTo(UPDATED_DIVISIONAL_OFFICE_VERIFIED_FLAG);
        assertThat(testKamalSociety.getIsSupplimenteryFlag()).isEqualTo(UPDATED_IS_SUPPLIMENTERY_FLAG);
        assertThat(testKamalSociety.getSansthaTapasaniVarg()).isEqualTo(UPDATED_SANSTHA_TAPASANI_VARG);
        assertThat(testKamalSociety.getBranchVerifiedBy()).isEqualTo(UPDATED_BRANCH_VERIFIED_BY);
        assertThat(testKamalSociety.getBranchVerifiedDate()).isEqualTo(UPDATED_BRANCH_VERIFIED_DATE);
        assertThat(testKamalSociety.getHeadOfficeVerifiedBy()).isEqualTo(UPDATED_HEAD_OFFICE_VERIFIED_BY);
        assertThat(testKamalSociety.getHeadOfficeVerifiedDate()).isEqualTo(UPDATED_HEAD_OFFICE_VERIFIED_DATE);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedBy()).isEqualTo(UPDATED_DIVISIONAL_OFFICE_VERIFIED_BY);
        assertThat(testKamalSociety.getDivisionalOfficeVerifiedDate()).isEqualTo(UPDATED_DIVISIONAL_OFFICE_VERIFIED_DATE);
        assertThat(testKamalSociety.getDoshPurtataDate()).isEqualTo(UPDATED_DOSH_PURTATA_DATE);
        assertThat(testKamalSociety.getGambhirDosh()).isEqualTo(UPDATED_GAMBHIR_DOSH);
        assertThat(testKamalSociety.getBranchInwardNumber()).isEqualTo(UPDATED_BRANCH_INWARD_NUMBER);
        assertThat(testKamalSociety.getBranchInwardDate()).isEqualTo(UPDATED_BRANCH_INWARD_DATE);
        assertThat(testKamalSociety.getBranchOutwardNumber()).isEqualTo(UPDATED_BRANCH_OUTWARD_NUMBER);
        assertThat(testKamalSociety.getBranchOutwardDate()).isEqualTo(UPDATED_BRANCH_OUTWARD_DATE);
        assertThat(testKamalSociety.getHeadOfficeInwardNumber()).isEqualTo(UPDATED_HEAD_OFFICE_INWARD_NUMBER);
        assertThat(testKamalSociety.getHeadOfficeInwardDate()).isEqualTo(UPDATED_HEAD_OFFICE_INWARD_DATE);
        assertThat(testKamalSociety.getHeadOfficeOutwardNumber()).isEqualTo(UPDATED_HEAD_OFFICE_OUTWARD_NUMBER);
        assertThat(testKamalSociety.getHeadOfficeOutwardDate()).isEqualTo(UPDATED_HEAD_OFFICE_OUTWARD_DATE);
        assertThat(testKamalSociety.getTharavNumber()).isEqualTo(UPDATED_THARAV_NUMBER);
        assertThat(testKamalSociety.getTharavDate()).isEqualTo(UPDATED_THARAV_DATE);
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
