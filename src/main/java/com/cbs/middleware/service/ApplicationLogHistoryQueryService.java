package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.ApplicationLogHistory;
import com.cbs.middleware.repository.ApplicationLogHistoryRepository;
import com.cbs.middleware.service.criteria.ApplicationLogHistoryCriteria;
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
 * Service for executing complex queries for {@link ApplicationLogHistory} entities in the database.
 * The main input is a {@link ApplicationLogHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationLogHistory} or a {@link Page} of {@link ApplicationLogHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationLogHistoryQueryService extends QueryService<ApplicationLogHistory> {

    private final Logger log = LoggerFactory.getLogger(ApplicationLogHistoryQueryService.class);

    private final ApplicationLogHistoryRepository applicationLogHistoryRepository;

    public ApplicationLogHistoryQueryService(ApplicationLogHistoryRepository applicationLogHistoryRepository) {
        this.applicationLogHistoryRepository = applicationLogHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link ApplicationLogHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationLogHistory> findByCriteria(ApplicationLogHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationLogHistory> specification = createSpecification(criteria);
        return applicationLogHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApplicationLogHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationLogHistory> findByCriteria(ApplicationLogHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationLogHistory> specification = createSpecification(criteria);
        return applicationLogHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationLogHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationLogHistory> specification = createSpecification(criteria);
        return applicationLogHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationLogHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicationLogHistory> createSpecification(ApplicationLogHistoryCriteria criteria) {
        Specification<ApplicationLogHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicationLogHistory_.id));
            }
            if (criteria.getErrorType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorType(), ApplicationLogHistory_.errorType));
            }
            if (criteria.getErrorCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorCode(), ApplicationLogHistory_.errorCode));
            }
            if (criteria.getErrorMessage() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getErrorMessage(), ApplicationLogHistory_.errorMessage));
            }
            if (criteria.getRowNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRowNumber(), ApplicationLogHistory_.rowNumber));
            }
            if (criteria.getColumnNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColumnNumber(), ApplicationLogHistory_.columnNumber));
            }
            if (criteria.getSevierity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSevierity(), ApplicationLogHistory_.sevierity));
            }
            if (criteria.getExpectedSolution() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getExpectedSolution(), ApplicationLogHistory_.expectedSolution));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ApplicationLogHistory_.status));
            }
            if (criteria.getIssFileParserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIssFileParserId(),
                            root -> root.join(ApplicationLogHistory_.issFileParser, JoinType.LEFT).get(IssFileParser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
