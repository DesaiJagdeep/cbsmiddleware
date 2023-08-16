package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.service.criteria.ApplicationCriteria;
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
 * Service for executing complex queries for {@link Application} entities in the database.
 * The main input is a {@link ApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Application} or a {@link Page} of {@link Application} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationQueryService extends QueryService<Application> {

    private final Logger log = LoggerFactory.getLogger(ApplicationQueryService.class);

    private final ApplicationRepository applicationRepository;

    public ApplicationQueryService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /**
     * Return a {@link List} of {@link Application} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Application> findByCriteria(ApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Application} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Application> findByCriteria(ApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Application> createSpecification(ApplicationCriteria criteria) {
        Specification<Application> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Application_.id));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchId(), Application_.batchId));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), Application_.financialYear));
            }

            if (criteria.getBankCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankCode(), Application_.bankCode));
            }
            if (criteria.getBranchCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBranchCode(), Application_.branchCode));
            }
            if (criteria.getPacksCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPacksCode(), Application_.packsCode));
            }

            if (criteria.getUniqueId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUniqueId(), Application_.uniqueId));
            }
            if (criteria.getRecordStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecordStatus(), Application_.recordStatus));
            }
            if (criteria.getApplicationStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationStatus(), Application_.applicationStatus));
            }
            if (criteria.getKccStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKccStatus(), Application_.kccStatus));
            }
            if (criteria.getRecipientUniqueId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRecipientUniqueId(), Application_.recipientUniqueId));
            }
            if (criteria.getFarmerId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerId(), Application_.farmerId));
            }
            if (criteria.getIssFileParserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIssFileParserId(),
                            root -> root.join(Application_.issFileParser, JoinType.LEFT).get(IssFileParser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
