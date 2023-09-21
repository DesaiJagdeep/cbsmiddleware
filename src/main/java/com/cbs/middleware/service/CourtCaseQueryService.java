package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
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
 * Service for executing complex queries for {@link CourtCase} entities in the database.
 * The main input is a {@link CourtCaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourtCase} or a {@link Page} of {@link CourtCase} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourtCaseQueryService extends QueryService<CourtCase> {

    private final Logger log = LoggerFactory.getLogger(CourtCaseQueryService.class);

    private final CourtCaseRepository courtCaseRepository;

    public CourtCaseQueryService(CourtCaseRepository courtCaseRepository) {
        this.courtCaseRepository = courtCaseRepository;
    }

    /**
     * Return a {@link List} of {@link CourtCase} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourtCase> findByCriteria(CourtCaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CourtCase} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourtCase> findByCriteria(CourtCaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourtCaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.count(specification);
    }

    /**
     * Function to convert {@link CourtCaseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourtCase> createSpecification(CourtCaseCriteria criteria) {
        Specification<CourtCase> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourtCase_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSrNo(), CourtCase_.srNo));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNo(), CourtCase_.accountNo));
            }
            if (criteria.getNameOfDefaulter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameOfDefaulter(), CourtCase_.nameOfDefaulter));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), CourtCase_.address));
            }
            if (criteria.getLoanType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanType(), CourtCase_.loanType));
            }
            if (criteria.getLoanAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanAmount(), CourtCase_.loanAmount));
            }
            if (criteria.getLoanDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanDate(), CourtCase_.loanDate));
            }
            if (criteria.getTermOfLoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermOfLoan(), CourtCase_.termOfLoan));
            }
            if (criteria.getInterestRate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInterestRate(), CourtCase_.interestRate));
            }
            if (criteria.getInstallmentAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstallmentAmount(), CourtCase_.installmentAmount));
            }
            if (criteria.getTotalCredit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalCredit(), CourtCase_.totalCredit));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBalance(), CourtCase_.balance));
            }
            if (criteria.getInterestPaid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInterestPaid(), CourtCase_.interestPaid));
            }
            if (criteria.getPenalInterestPaid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPenalInterestPaid(), CourtCase_.penalInterestPaid));
            }
            if (criteria.getDueAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDueAmount(), CourtCase_.dueAmount));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), CourtCase_.dueDate));
            }
            if (criteria.getDueInterest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDueInterest(), CourtCase_.dueInterest));
            }
            if (criteria.getDuePenalInterest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuePenalInterest(), CourtCase_.duePenalInterest));
            }
            if (criteria.getDueMoreInterest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDueMoreInterest(), CourtCase_.dueMoreInterest));
            }
            if (criteria.getInterestRecivable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInterestRecivable(), CourtCase_.interestRecivable));
            }
            if (criteria.getGaurentorOne() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGaurentorOne(), CourtCase_.gaurentorOne));
            }
            if (criteria.getGaurentorOneAddress() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getGaurentorOneAddress(), CourtCase_.gaurentorOneAddress));
            }
            if (criteria.getGaurentorTwo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGaurentorTwo(), CourtCase_.gaurentorTwo));
            }
            if (criteria.getGaurentorTwoAddress() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getGaurentorTwoAddress(), CourtCase_.gaurentorTwoAddress));
            }
            if (criteria.getFirstNoticeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstNoticeDate(), CourtCase_.firstNoticeDate));
            }
            if (criteria.getSecondNoticeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSecondNoticeDate(), CourtCase_.secondNoticeDate));
            }
        }
        return specification;
    }
}
