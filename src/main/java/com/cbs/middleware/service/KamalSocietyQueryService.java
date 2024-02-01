package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.repository.KamalSocietyRepository;
import com.cbs.middleware.service.criteria.KamalSocietyCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link KamalSociety} entities in the database.
 * The main input is a {@link KamalSocietyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KamalSociety} or a {@link Page} of {@link KamalSociety} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KamalSocietyQueryService extends QueryService<KamalSociety> {

    private final Logger log = LoggerFactory.getLogger(KamalSocietyQueryService.class);

    private final KamalSocietyRepository kamalSocietyRepository;

    public KamalSocietyQueryService(KamalSocietyRepository kamalSocietyRepository) {
        this.kamalSocietyRepository = kamalSocietyRepository;
    }

    /**
     * Return a {@link List} of {@link KamalSociety} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KamalSociety> findByCriteria(KamalSocietyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KamalSociety> specification = createSpecification(criteria);
        return kamalSocietyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KamalSociety} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KamalSociety> findByCriteria(KamalSocietyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KamalSociety> specification = createSpecification(criteria);
        return kamalSocietyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KamalSocietyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KamalSociety> specification = createSpecification(criteria);
        return kamalSocietyRepository.count(specification);
    }

    /**
     * Function to convert {@link KamalSocietyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KamalSociety> createSpecification(KamalSocietyCriteria criteria) {
        Specification<KamalSociety> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KamalSociety_.id));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), KamalSociety_.financialYear));
            }
            if (criteria.getKmDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmDate(), KamalSociety_.kmDate));
            }
            if (criteria.getKmDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmDateMr(), KamalSociety_.kmDateMr));
            }
            if (criteria.getKmFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmFromDate(), KamalSociety_.kmFromDate));
            }
            if (criteria.getKmFromDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmFromDateMr(), KamalSociety_.kmFromDateMr));
            }
            if (criteria.getKmToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmToDate(), KamalSociety_.kmToDate));
            }
            if (criteria.getKmToDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmToDateMr(), KamalSociety_.kmToDateMr));
            }
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsNumber(), KamalSociety_.pacsNumber));
            }
            if (criteria.getZindagiPatrakDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getZindagiPatrakDate(), KamalSociety_.zindagiPatrakDate));
            }
            if (criteria.getZindagiPatrakDateMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getZindagiPatrakDateMr(), KamalSociety_.zindagiPatrakDateMr));
            }
            if (criteria.getBankTapasaniDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankTapasaniDate(), KamalSociety_.bankTapasaniDate));
            }
            if (criteria.getBankTapasaniDateMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getBankTapasaniDateMr(), KamalSociety_.bankTapasaniDateMr));
            }
            if (criteria.getGovTapasaniDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGovTapasaniDate(), KamalSociety_.govTapasaniDate));
            }
            if (criteria.getGovTapasaniDateMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getGovTapasaniDateMr(), KamalSociety_.govTapasaniDateMr));
            }
            if (criteria.getSansthaTapasaniDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSansthaTapasaniDate(), KamalSociety_.sansthaTapasaniDate));
            }
            if (criteria.getSansthaTapasaniDateMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSansthaTapasaniDateMr(), KamalSociety_.sansthaTapasaniDateMr));
            }
            if (criteria.getTotalLand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalLand(), KamalSociety_.totalLand));
            }
            if (criteria.getBagayat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBagayat(), KamalSociety_.bagayat));
            }
            if (criteria.getJirayat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJirayat(), KamalSociety_.jirayat));
            }
            if (criteria.getTotalFarmer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalFarmer(), KamalSociety_.totalFarmer));
            }
            if (criteria.getMemberFarmer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberFarmer(), KamalSociety_.memberFarmer));
            }
            if (criteria.getNonMemberFarmer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNonMemberFarmer(), KamalSociety_.nonMemberFarmer));
            }
            if (criteria.getTalebandDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTalebandDate(), KamalSociety_.talebandDate));
            }
            if (criteria.getMemLoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemLoan(), KamalSociety_.memLoan));
            }
            if (criteria.getMemDue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemDue(), KamalSociety_.memDue));
            }
            if (criteria.getMemVasuli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemVasuli(), KamalSociety_.memVasuli));
            }
            if (criteria.getMemVasuliPer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemVasuliPer(), KamalSociety_.memVasuliPer));
            }
            if (criteria.getBankLoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankLoan(), KamalSociety_.bankLoan));
            }
            if (criteria.getBankDue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankDue(), KamalSociety_.bankDue));
            }
            if (criteria.getBankVasuli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankVasuli(), KamalSociety_.bankVasuli));
            }
            if (criteria.getBankVasuliPer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankVasuliPer(), KamalSociety_.bankVasuliPer));
            }
            if (criteria.getBalanceSheetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalanceSheetDate(), KamalSociety_.balanceSheetDate));
            }
            if (criteria.getBalanceSheetDateMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getBalanceSheetDateMr(), KamalSociety_.balanceSheetDateMr));
            }
            if (criteria.getLiabilityAdhikrutShareCapital() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getLiabilityAdhikrutShareCapital(), KamalSociety_.liabilityAdhikrutShareCapital)
                    );
            }
            if (criteria.getLiabilityVasulShareCapital() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getLiabilityVasulShareCapital(), KamalSociety_.liabilityVasulShareCapital)
                    );
            }
            if (criteria.getLiabilityFund() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLiabilityFund(), KamalSociety_.liabilityFund));
            }
            if (criteria.getLiabilityDeposite() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLiabilityDeposite(), KamalSociety_.liabilityDeposite));
            }
            if (criteria.getLiabilityBalanceSheetBankLoan() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getLiabilityBalanceSheetBankLoan(), KamalSociety_.liabilityBalanceSheetBankLoan)
                    );
            }
            if (criteria.getLiabilityOtherPayable() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLiabilityOtherPayable(), KamalSociety_.liabilityOtherPayable));
            }
            if (criteria.getLiabilityProfit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLiabilityProfit(), KamalSociety_.liabilityProfit));
            }
            if (criteria.getAssetCash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetCash(), KamalSociety_.assetCash));
            }
            if (criteria.getAssetInvestment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetInvestment(), KamalSociety_.assetInvestment));
            }
            if (criteria.getAssetImaratFund() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetImaratFund(), KamalSociety_.assetImaratFund));
            }
            if (criteria.getAssetMemberLoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetMemberLoan(), KamalSociety_.assetMemberLoan));
            }
            if (criteria.getAssetDeadStock() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetDeadStock(), KamalSociety_.assetDeadStock));
            }
            if (criteria.getAssetOtherReceivable() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAssetOtherReceivable(), KamalSociety_.assetOtherReceivable));
            }
            if (criteria.getAssetLoss() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetLoss(), KamalSociety_.assetLoss));
            }
            if (criteria.getTotalLiability() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalLiability(), KamalSociety_.totalLiability));
            }
            if (criteria.getTotalAsset() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalAsset(), KamalSociety_.totalAsset));
            }
            if (criteria.getVillageCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageCode(), KamalSociety_.villageCode));
            }
            if (criteria.getPacsVerifiedFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getPacsVerifiedFlag(), KamalSociety_.pacsVerifiedFlag));
            }
            if (criteria.getBranchVerifiedFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getBranchVerifiedFlag(), KamalSociety_.branchVerifiedFlag));
            }
            if (criteria.getHeadOfficeVerifiedFlag() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getHeadOfficeVerifiedFlag(), KamalSociety_.headOfficeVerifiedFlag));
            }
            if (criteria.getIsSupplimenteryFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSupplimenteryFlag(), KamalSociety_.isSupplimenteryFlag));
            }
        }
        return specification;
    }
}
