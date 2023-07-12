package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.service.criteria.ApplicationLogCriteria;
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
 * Service for executing complex queries for {@link ApplicationLog} entities in the database.
 * The main input is a {@link ApplicationLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationLog} or a {@link Page} of {@link ApplicationLog} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationLogQueryService extends QueryService<ApplicationLog> {

    private final Logger log = LoggerFactory.getLogger(ApplicationLogQueryService.class);

    private final ApplicationLogRepository applicationLogRepository;

    public ApplicationLogQueryService(ApplicationLogRepository applicationLogRepository) {
        this.applicationLogRepository = applicationLogRepository;
    }

    /**
     * Return a {@link List} of {@link ApplicationLog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationLog> findByCriteria(ApplicationLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationLog> specification = createSpecification(criteria);
        return applicationLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApplicationLog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationLog> findByCriteria(ApplicationLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationLog> specification = createSpecification(criteria);
        return applicationLogRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationLog> specification = createSpecification(criteria);
        return applicationLogRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicationLog> createSpecification(ApplicationLogCriteria criteria) {
        Specification<ApplicationLog> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicationLog_.id));
            }
            if (criteria.getErrorType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorType(), ApplicationLog_.errorType));
            }
            if (criteria.getErrorCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorCode(), ApplicationLog_.errorCode));
            }
            if (criteria.getErrorMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorMessage(), ApplicationLog_.errorMessage));
            }
            if (criteria.getColumnNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColumnNumber(), ApplicationLog_.columnNumber));
            }
            if (criteria.getSevierity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSevierity(), ApplicationLog_.sevierity));
            }
            if (criteria.getExpectedSolution() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getExpectedSolution(), ApplicationLog_.expectedSolution));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ApplicationLog_.status));
            }
            if (criteria.getRowNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRowNumber(), ApplicationLog_.rowNumber));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchId(), ApplicationLog_.batchId));
            }
            if (criteria.getIssFileParserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIssFileParserId(),
                            root -> root.join(ApplicationLog_.issFileParser, JoinType.LEFT).get(IssFileParser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
