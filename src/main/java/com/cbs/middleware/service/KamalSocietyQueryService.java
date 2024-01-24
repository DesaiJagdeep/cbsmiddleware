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
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPacsNumber(), KamalSociety_.pacsNumber));
            }
            if (criteria.getZindagiDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZindagiDate(), KamalSociety_.zindagiDate));
            }
            if (criteria.getZindagiDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZindagiDateMr(), KamalSociety_.zindagiDateMr));
            }
            if (criteria.getVillage1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillage1(), KamalSociety_.village1));
            }
            if (criteria.getVillage1Mr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillage1Mr(), KamalSociety_.village1Mr));
            }
            if (criteria.getVillage2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillage2(), KamalSociety_.village2));
            }
            if (criteria.getVillage2Mr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillage2Mr(), KamalSociety_.village2Mr));
            }
            if (criteria.getVillage3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillage3(), KamalSociety_.village3));
            }
            if (criteria.getVillage3Mr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillage3Mr(), KamalSociety_.village3Mr));
            }
            if (criteria.getTotalLand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalLand(), KamalSociety_.totalLand));
            }
            if (criteria.getTotalLandMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalLandMr(), KamalSociety_.totalLandMr));
            }
            if (criteria.getTotalMem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMem(), KamalSociety_.totalMem));
            }
            if (criteria.getTotalMemMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalMemMr(), KamalSociety_.totalMemMr));
            }
            if (criteria.getTotalNonMem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalNonMem(), KamalSociety_.totalNonMem));
            }
            if (criteria.getTotalNonMemMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalNonMemMr(), KamalSociety_.totalNonMemMr));
            }
            if (criteria.getTotalGMem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalGMem(), KamalSociety_.totalGMem));
            }
            if (criteria.getTotalGMemMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalGMemMr(), KamalSociety_.totalGMemMr));
            }
            if (criteria.getMemLoan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMemLoan(), KamalSociety_.memLoan));
            }
            if (criteria.getMemLoanMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemLoanMr(), KamalSociety_.memLoanMr));
            }
            if (criteria.getMemDue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMemDue(), KamalSociety_.memDue));
            }
            if (criteria.getMemDueMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemDueMr(), KamalSociety_.memDueMr));
            }
            if (criteria.getMemDueper() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMemDueper(), KamalSociety_.memDueper));
            }
            if (criteria.getMemDueperMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemDueperMr(), KamalSociety_.memDueperMr));
            }
            if (criteria.getMemVasulpatra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMemVasulpatra(), KamalSociety_.memVasulpatra));
            }
            if (criteria.getMemVasulpatraMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemVasulpatraMr(), KamalSociety_.memVasulpatraMr));
            }
            if (criteria.getMemVasul() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMemVasul(), KamalSociety_.memVasul));
            }
            if (criteria.getMemVasulMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemVasulMr(), KamalSociety_.memVasulMr));
            }
            if (criteria.getMemVasulPer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMemVasulPer(), KamalSociety_.memVasulPer));
            }
            if (criteria.getMemVasulPerMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemVasulPerMr(), KamalSociety_.memVasulPerMr));
            }
            if (criteria.getBankLoan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankLoan(), KamalSociety_.bankLoan));
            }
            if (criteria.getBankLoanMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankLoanMr(), KamalSociety_.bankLoanMr));
            }
            if (criteria.getBankDue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankDue(), KamalSociety_.bankDue));
            }
            if (criteria.getBankDueMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankDueMr(), KamalSociety_.bankDueMr));
            }
            if (criteria.getBankDueper() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankDueper(), KamalSociety_.bankDueper));
            }
            if (criteria.getBankDueperMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankDueperMr(), KamalSociety_.bankDueperMr));
            }
            if (criteria.getBankVasulpatra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankVasulpatra(), KamalSociety_.bankVasulpatra));
            }
            if (criteria.getBankVasulpatraMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankVasulpatraMr(), KamalSociety_.bankVasulpatraMr));
            }
            if (criteria.getBankVasul() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankVasul(), KamalSociety_.bankVasul));
            }
            if (criteria.getBankVasulMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankVasulMr(), KamalSociety_.bankVasulMr));
            }
            if (criteria.getBankVasulPer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankVasulPer(), KamalSociety_.bankVasulPer));
            }
            if (criteria.getBankVasulPerMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankVasulPerMr(), KamalSociety_.bankVasulPerMr));
            }
            if (criteria.getShareCapital() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShareCapital(), KamalSociety_.shareCapital));
            }
            if (criteria.getShareCapitalMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShareCapitalMr(), KamalSociety_.shareCapitalMr));
            }
            if (criteria.getShare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShare(), KamalSociety_.share));
            }
            if (criteria.getShareMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShareMr(), KamalSociety_.shareMr));
            }
            if (criteria.getFunds() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFunds(), KamalSociety_.funds));
            }
            if (criteria.getFundsMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFundsMr(), KamalSociety_.fundsMr));
            }
            if (criteria.getDeposit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeposit(), KamalSociety_.deposit));
            }
            if (criteria.getDepositMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepositMr(), KamalSociety_.depositMr));
            }
            if (criteria.getPayable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPayable(), KamalSociety_.payable));
            }
            if (criteria.getPayableMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPayableMr(), KamalSociety_.payableMr));
            }
            if (criteria.getProfit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfit(), KamalSociety_.profit));
            }
            if (criteria.getProfitMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfitMr(), KamalSociety_.profitMr));
            }
            if (criteria.getCashInHand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCashInHand(), KamalSociety_.cashInHand));
            }
            if (criteria.getCashInHandMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCashInHandMr(), KamalSociety_.cashInHandMr));
            }
            if (criteria.getInvestment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvestment(), KamalSociety_.investment));
            }
            if (criteria.getInvestmentMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvestmentMr(), KamalSociety_.investmentMr));
            }
            if (criteria.getDeadStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeadStock(), KamalSociety_.deadStock));
            }
            if (criteria.getDeadStockMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeadStockMr(), KamalSociety_.deadStockMr));
            }
            if (criteria.getOtherPay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOtherPay(), KamalSociety_.otherPay));
            }
            if (criteria.getOtherPayMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtherPayMr(), KamalSociety_.otherPayMr));
            }
            if (criteria.getLoss() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoss(), KamalSociety_.loss));
            }
            if (criteria.getLossMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLossMr(), KamalSociety_.lossMr));
            }
            if (criteria.getKamalCropId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKamalCropId(),
                            root -> root.join(KamalSociety_.kamalCrops, JoinType.LEFT).get(KamalCrop_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
