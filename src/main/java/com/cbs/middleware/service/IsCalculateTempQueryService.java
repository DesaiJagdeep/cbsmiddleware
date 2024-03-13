package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.service.criteria.IsCalculateTempCriteria;
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
 * Service for executing complex queries for {@link IsCalculateTemp} entities in the database.
 * The main input is a {@link IsCalculateTempCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IsCalculateTemp} or a {@link Page} of {@link IsCalculateTemp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IsCalculateTempQueryService extends QueryService<IsCalculateTemp> {

    private final Logger log = LoggerFactory.getLogger(IsCalculateTempQueryService.class);

    private final IsCalculateTempRepository isCalculateTempRepository;

    public IsCalculateTempQueryService(IsCalculateTempRepository isCalculateTempRepository) {
        this.isCalculateTempRepository = isCalculateTempRepository;
    }

    /**
     * Return a {@link List} of {@link IsCalculateTemp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IsCalculateTemp> findByCriteria(IsCalculateTempCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IsCalculateTemp> specification = createSpecification(criteria);
        return isCalculateTempRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link IsCalculateTemp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IsCalculateTemp> findByCriteria(IsCalculateTempCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IsCalculateTemp> specification = createSpecification(criteria);
        return isCalculateTempRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IsCalculateTempCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IsCalculateTemp> specification = createSpecification(criteria);
        return isCalculateTempRepository.count(specification);
    }

    /**
     * Function to convert {@link IsCalculateTempCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IsCalculateTemp> createSpecification(IsCalculateTempCriteria criteria) {
        Specification<IsCalculateTemp> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IsCalculateTemp_.id));
            }
            if (criteria.getSerialNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSerialNo(), IsCalculateTemp_.serialNo));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), IsCalculateTemp_.financialYear));
            }
        }
        return specification;
    }
}
