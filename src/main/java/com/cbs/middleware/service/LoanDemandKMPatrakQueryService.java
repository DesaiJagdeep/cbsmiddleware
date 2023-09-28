package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.LoanDemandKMPatrak;
import com.cbs.middleware.repository.LoanDemandKMPatrakRepository;
import com.cbs.middleware.service.criteria.LoanDemandKMPatrakCriteria;
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
 * Service for executing complex queries for {@link LoanDemandKMPatrak} entities in the database.
 * The main input is a {@link LoanDemandKMPatrakCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanDemandKMPatrak} or a {@link Page} of {@link LoanDemandKMPatrak} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanDemandKMPatrakQueryService extends QueryService<LoanDemandKMPatrak> {

    private final Logger log = LoggerFactory.getLogger(LoanDemandKMPatrakQueryService.class);

    private final LoanDemandKMPatrakRepository loanDemandKMPatrakRepository;

    public LoanDemandKMPatrakQueryService(LoanDemandKMPatrakRepository loanDemandKMPatrakRepository) {
        this.loanDemandKMPatrakRepository = loanDemandKMPatrakRepository;
    }

    /**
     * Return a {@link List} of {@link LoanDemandKMPatrak} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanDemandKMPatrak> findByCriteria(LoanDemandKMPatrakCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanDemandKMPatrak> specification = createSpecification(criteria);
        return loanDemandKMPatrakRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LoanDemandKMPatrak} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDemandKMPatrak> findByCriteria(LoanDemandKMPatrakCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanDemandKMPatrak> specification = createSpecification(criteria);
        return loanDemandKMPatrakRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanDemandKMPatrakCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanDemandKMPatrak> specification = createSpecification(criteria);
        return loanDemandKMPatrakRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanDemandKMPatrakCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanDemandKMPatrak> createSpecification(LoanDemandKMPatrakCriteria criteria) {
        Specification<LoanDemandKMPatrak> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanDemandKMPatrak_.id));
            }
            if (criteria.getDemandCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDemandCode(), LoanDemandKMPatrak_.demandCode));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), LoanDemandKMPatrak_.date));
            }
            if (criteria.getKmDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmDate(), LoanDemandKMPatrak_.kmDate));
            }
            if (criteria.getShares() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShares(), LoanDemandKMPatrak_.shares));
            }
            if (criteria.getPid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPid(), LoanDemandKMPatrak_.pid));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), LoanDemandKMPatrak_.code));
            }
            if (criteria.getDemandArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDemandArea(), LoanDemandKMPatrak_.demandArea));
            }
            if (criteria.getCropType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropType(), LoanDemandKMPatrak_.cropType));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotal(), LoanDemandKMPatrak_.total));
            }
            if (criteria.getCheck() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheck(), LoanDemandKMPatrak_.check));
            }
            if (criteria.getGoods() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoods(), LoanDemandKMPatrak_.goods));
            }
            if (criteria.getSharesn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSharesn(), LoanDemandKMPatrak_.sharesn));
            }
            if (criteria.getHn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHn(), LoanDemandKMPatrak_.hn));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArea(), LoanDemandKMPatrak_.area));
            }
            if (criteria.gethAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.gethAmount(), LoanDemandKMPatrak_.hAmount));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LoanDemandKMPatrak_.name));
            }
            if (criteria.getKhateCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKhateCode(), LoanDemandKMPatrak_.khateCode));
            }
            if (criteria.getRemaining() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemaining(), LoanDemandKMPatrak_.remaining));
            }
            if (criteria.getArrears() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArrears(), LoanDemandKMPatrak_.arrears));
            }
            if (criteria.getKmAcceptance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmAcceptance(), LoanDemandKMPatrak_.kmAcceptance));
            }
            if (criteria.getPaidDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaidDate(), LoanDemandKMPatrak_.paidDate));
            }
            if (criteria.getKmCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmCode(), LoanDemandKMPatrak_.kmCode));
            }
            if (criteria.getPendingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPendingDate(), LoanDemandKMPatrak_.pendingDate));
            }
            if (criteria.getDepositeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepositeDate(), LoanDemandKMPatrak_.depositeDate));
            }
            if (criteria.getAccountNumberB() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccountNumberB(), LoanDemandKMPatrak_.accountNumberB));
            }
            if (criteria.getLoanDue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanDue(), LoanDemandKMPatrak_.loanDue));
            }
            if (criteria.getArrearsB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArrearsB(), LoanDemandKMPatrak_.arrearsB));
            }
            if (criteria.getDueDateB() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDateB(), LoanDemandKMPatrak_.dueDateB));
            }
            if (criteria.getCropB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropB(), LoanDemandKMPatrak_.cropB));
            }
            if (criteria.getKmAcceptanceB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmAcceptanceB(), LoanDemandKMPatrak_.kmAcceptanceB));
            }
            if (criteria.getKmCodeB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmCodeB(), LoanDemandKMPatrak_.kmCodeB));
            }
            if (criteria.gethAgreementNumberB() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.gethAgreementNumberB(), LoanDemandKMPatrak_.hAgreementNumberB));
            }
            if (criteria.gethAgreementAreaB() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.gethAgreementAreaB(), LoanDemandKMPatrak_.hAgreementAreaB));
            }
            if (criteria.gethAgreementBurdenB() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.gethAgreementBurdenB(), LoanDemandKMPatrak_.hAgreementBurdenB));
            }
            if (criteria.getTotalPaidB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalPaidB(), LoanDemandKMPatrak_.totalPaidB));
            }
            if (criteria.getDemandAreaB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDemandAreaB(), LoanDemandKMPatrak_.demandAreaB));
            }
            if (criteria.getCheckInTheFormOfPaymentB() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCheckInTheFormOfPaymentB(), LoanDemandKMPatrak_.checkInTheFormOfPaymentB)
                    );
            }
            if (criteria.getSharesB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSharesB(), LoanDemandKMPatrak_.sharesB));
            }
            if (criteria.getVasulPatraRepaymentDateB() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVasulPatraRepaymentDateB(), LoanDemandKMPatrak_.vasulPatraRepaymentDateB)
                    );
            }
        }
        return specification;
    }
}
