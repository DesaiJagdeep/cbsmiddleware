package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.repository.KmDetailsRepository;
import com.cbs.middleware.service.criteria.KmDetailsCriteria;
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
 * Service for executing complex queries for {@link KmDetails} entities in the database.
 * The main input is a {@link KmDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KmDetails} or a {@link Page} of {@link KmDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KmDetailsQueryService extends QueryService<KmDetails> {

    private final Logger log = LoggerFactory.getLogger(KmDetailsQueryService.class);

    private final KmDetailsRepository kmDetailsRepository;

    public KmDetailsQueryService(KmDetailsRepository kmDetailsRepository) {
        this.kmDetailsRepository = kmDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link KmDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KmDetails> findByCriteria(KmDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KmDetails> specification = createSpecification(criteria);
        return kmDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KmDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KmDetails> findByCriteria(KmDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KmDetails> specification = createSpecification(criteria);
        return kmDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KmDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KmDetails> specification = createSpecification(criteria);
        return kmDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link KmDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KmDetails> createSpecification(KmDetailsCriteria criteria) {
        Specification<KmDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KmDetails_.id));
            }
            if (criteria.getShares() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShares(), KmDetails_.shares));
            }
            if (criteria.getSharesMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSharesMr(), KmDetails_.sharesMr));
            }
            if (criteria.getSugarShares() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSugarShares(), KmDetails_.sugarShares));
            }
            if (criteria.getSugarSharesMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSugarSharesMr(), KmDetails_.sugarSharesMr));
            }
            if (criteria.getDeposite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeposite(), KmDetails_.deposite));
            }
            if (criteria.getDepositeMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepositeMr(), KmDetails_.depositeMr));
            }
            if (criteria.getDueLoan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueLoan(), KmDetails_.dueLoan));
            }
            if (criteria.getDueLoanMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDueLoanMr(), KmDetails_.dueLoanMr));
            }
            if (criteria.getDueAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueAmount(), KmDetails_.dueAmount));
            }
            if (criteria.getDueAmountMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDueAmountMr(), KmDetails_.dueAmountMr));
            }
            if (criteria.getDueDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDueDateMr(), KmDetails_.dueDateMr));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), KmDetails_.dueDate));
            }
            if (criteria.getKmDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmDate(), KmDetails_.kmDate));
            }
            if (criteria.getKmDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmDateMr(), KmDetails_.kmDateMr));
            }
            if (criteria.getKmFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmFromDate(), KmDetails_.kmFromDate));
            }
            if (criteria.getKmFromDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmFromDateMr(), KmDetails_.kmFromDateMr));
            }
            if (criteria.getKmToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmToDate(), KmDetails_.kmToDate));
            }
            if (criteria.getKmToDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmToDateMr(), KmDetails_.kmToDateMr));
            }
            if (criteria.getBagayatHector() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBagayatHector(), KmDetails_.bagayatHector));
            }
            if (criteria.getBagayatHectorMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBagayatHectorMr(), KmDetails_.bagayatHectorMr));
            }
            if (criteria.getBagayatAre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBagayatAre(), KmDetails_.bagayatAre));
            }
            if (criteria.getBagayatAreMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBagayatAreMr(), KmDetails_.bagayatAreMr));
            }
            if (criteria.getJirayatHector() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJirayatHector(), KmDetails_.jirayatHector));
            }
            if (criteria.getJirayatHectorMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJirayatHectorMr(), KmDetails_.jirayatHectorMr));
            }
            if (criteria.getJirayatAre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJirayatAre(), KmDetails_.jirayatAre));
            }
            if (criteria.getJirayatAreMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJirayatAreMr(), KmDetails_.jirayatAreMr));
            }
            if (criteria.getZindagiAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZindagiAmt(), KmDetails_.zindagiAmt));
            }
            if (criteria.getZindagiNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZindagiNo(), KmDetails_.zindagiNo));
            }
            if (criteria.getSurveyNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurveyNo(), KmDetails_.surveyNo));
            }
            if (criteria.getSurveyNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurveyNoMr(), KmDetails_.surveyNoMr));
            }
            if (criteria.getLandValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLandValue(), KmDetails_.landValue));
            }
            if (criteria.getLandValueMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandValueMr(), KmDetails_.landValueMr));
            }
            if (criteria.geteAgreementAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteAgreementAmt(), KmDetails_.eAgreementAmt));
            }
            if (criteria.geteAgreementAmtMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.geteAgreementAmtMr(), KmDetails_.eAgreementAmtMr));
            }
            if (criteria.geteAgreementDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteAgreementDate(), KmDetails_.eAgreementDate));
            }
            if (criteria.geteAgreementDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.geteAgreementDateMr(), KmDetails_.eAgreementDateMr));
            }
            if (criteria.getBojaAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBojaAmount(), KmDetails_.bojaAmount));
            }
            if (criteria.getBojaAmountMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBojaAmountMr(), KmDetails_.bojaAmountMr));
            }
            if (criteria.getBojaDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBojaDate(), KmDetails_.bojaDate));
            }
            if (criteria.getBojaDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBojaDateMr(), KmDetails_.bojaDateMr));
            }
            if (criteria.getGattNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGattNo(), KmDetails_.gattNo));
            }
            if (criteria.getGattNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGattNoMr(), KmDetails_.gattNoMr));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), KmDetails_.dob));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), KmDetails_.financialYear));
            }

            if (criteria.getKmMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKmMasterId(),
                            root -> root.join(KmDetails_.kmMaster, JoinType.LEFT).get(KmMaster_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
