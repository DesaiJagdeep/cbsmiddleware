package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KmMagani;
import com.cbs.middleware.repository.KmMaganiRepository;
import com.cbs.middleware.service.criteria.KmMaganiCriteria;
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
 * Service for executing complex queries for {@link KmMagani} entities in the database.
 * The main input is a {@link KmMaganiCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KmMagani} or a {@link Page} of {@link KmMagani} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KmMaganiQueryService extends QueryService<KmMagani> {

    private final Logger log = LoggerFactory.getLogger(KmMaganiQueryService.class);

    private final KmMaganiRepository kmMaganiRepository;

    public KmMaganiQueryService(KmMaganiRepository kmMaganiRepository) {
        this.kmMaganiRepository = kmMaganiRepository;
    }

    /**
     * Return a {@link List} of {@link KmMagani} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KmMagani> findByCriteria(KmMaganiCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KmMagani> specification = createSpecification(criteria);
        return kmMaganiRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KmMagani} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KmMagani> findByCriteria(KmMaganiCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KmMagani> specification = createSpecification(criteria);
        return kmMaganiRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KmMaganiCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KmMagani> specification = createSpecification(criteria);
        return kmMaganiRepository.count(specification);
    }

    /**
     * Function to convert {@link KmMaganiCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KmMagani> createSpecification(KmMaganiCriteria criteria) {
        Specification<KmMagani> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KmMagani_.id));
            }
            if (criteria.getKmNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmNumber(), KmMagani_.kmNumber));
            }
            if (criteria.getMemberNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberNumber(), KmMagani_.memberNumber));
            }
            if (criteria.getMemberName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberName(), KmMagani_.memberName));
            }
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsNumber(), KmMagani_.pacsNumber));
            }
            if (criteria.getShare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShare(), KmMagani_.share));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), KmMagani_.financialYear));
            }
            if (criteria.getKmDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmDate(), KmMagani_.kmDate));
            }
            if (criteria.getMaganiDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaganiDate(), KmMagani_.maganiDate));
            }
        }
        return specification;
    }
}
