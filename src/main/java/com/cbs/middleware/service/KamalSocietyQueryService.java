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
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), KamalSociety_.financialYear));
            }
            if (criteria.getKmDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmDate(), KamalSociety_.kmDate));
            }
        }
        return specification;
    }
}
