package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.service.criteria.BatchTransactionCriteria;
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
 * Service for executing complex queries for {@link BatchTransaction} entities in the database.
 * The main input is a {@link BatchTransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BatchTransaction} or a {@link Page} of {@link BatchTransaction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BatchTransactionQueryService extends QueryService<BatchTransaction> {

    private final Logger log = LoggerFactory.getLogger(BatchTransactionQueryService.class);

    private final BatchTransactionRepository batchTransactionRepository;

    public BatchTransactionQueryService(BatchTransactionRepository batchTransactionRepository) {
        this.batchTransactionRepository = batchTransactionRepository;
    }

    /**
     * Return a {@link List} of {@link BatchTransaction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BatchTransaction> findByCriteria(BatchTransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BatchTransaction> specification = createSpecification(criteria);
        return batchTransactionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BatchTransaction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BatchTransaction> findByCriteria(BatchTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BatchTransaction> specification = createSpecification(criteria);
        return batchTransactionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BatchTransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BatchTransaction> specification = createSpecification(criteria);
        return batchTransactionRepository.count(specification);
    }

    /**
     * Function to convert {@link BatchTransactionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BatchTransaction> createSpecification(BatchTransactionCriteria criteria) {
        Specification<BatchTransaction> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BatchTransaction_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), BatchTransaction_.status));
            }
            if (criteria.getBatchDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchDetails(), BatchTransaction_.batchDetails));
            }
            if (criteria.getApplicationCount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getApplicationCount(), BatchTransaction_.applicationCount));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), BatchTransaction_.notes));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchId(), BatchTransaction_.batchId));
            }
            if (criteria.getBatchAckId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchAckId(), BatchTransaction_.batchAckId));
            }
        }
        return specification;
    }
}
