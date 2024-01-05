package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KmLoans;
import com.cbs.middleware.repository.KmLoansRepository;
import com.cbs.middleware.service.criteria.KmLoansCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link KmLoans} entities in the database.
 * The main input is a {@link KmLoansCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KmLoans} or a {@link Page} of {@link KmLoans} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KmLoansQueryService extends QueryService<KmLoans> {

    private final Logger log = LoggerFactory.getLogger(KmLoansQueryService.class);

    private final KmLoansRepository kmLoansRepository;

    public KmLoansQueryService(KmLoansRepository kmLoansRepository) {
        this.kmLoansRepository = kmLoansRepository;
    }

    /**
     * Return a {@link List} of {@link KmLoans} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KmLoans> findByCriteria(KmLoansCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KmLoans> specification = createSpecification(criteria);
        return kmLoansRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KmLoans} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KmLoans> findByCriteria(KmLoansCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KmLoans> specification = createSpecification(criteria);
        return kmLoansRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KmLoansCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KmLoans> specification = createSpecification(criteria);
        return kmLoansRepository.count(specification);
    }

    /**
     * Function to convert {@link KmLoansCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KmLoans> createSpecification(KmLoansCriteria criteria) {
        Specification<KmLoans> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KmLoans_.id));
            }
            if (criteria.getCropName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropName(), KmLoans_.cropName));
            }
            if (criteria.getCropNameMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropNameMr(), KmLoans_.cropNameMr));
            }
            if (criteria.getLoanDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanDate(), KmLoans_.loanDate));
            }
            if (criteria.getLoanAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanAmount(), KmLoans_.loanAmount));
            }
            if (criteria.getLoanAmountMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanAmountMr(), KmLoans_.loanAmountMr));
            }
            if (criteria.getAre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAre(), KmLoans_.are));
            }
            if (criteria.getAreMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreMr(), KmLoans_.areMr));
            }
            if (criteria.getReceivableAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivableAmt(), KmLoans_.receivableAmt));
            }
            if (criteria.getReceivableAmtMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceivableAmtMr(), KmLoans_.receivableAmtMr));
            }
            if (criteria.getDueAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueAmt(), KmLoans_.dueAmt));
            }
            if (criteria.getDueAmtMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDueAmtMr(), KmLoans_.dueAmtMr));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), KmLoans_.dueDate));
            }
            if (criteria.getKmDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKmDetailsId(),
                            root -> root.join(KmLoans_.kmDetails, JoinType.LEFT).get(KmDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
