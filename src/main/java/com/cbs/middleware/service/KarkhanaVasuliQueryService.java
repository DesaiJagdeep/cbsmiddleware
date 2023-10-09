package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KarkhanaVasuli;
import com.cbs.middleware.repository.KarkhanaVasuliRepository;
import com.cbs.middleware.service.criteria.KarkhanaVasuliCriteria;
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
 * Service for executing complex queries for {@link KarkhanaVasuli} entities in the database.
 * The main input is a {@link KarkhanaVasuliCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KarkhanaVasuli} or a {@link Page} of {@link KarkhanaVasuli} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KarkhanaVasuliQueryService extends QueryService<KarkhanaVasuli> {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliQueryService.class);

    private final KarkhanaVasuliRepository karkhanaVasuliRepository;

    public KarkhanaVasuliQueryService(KarkhanaVasuliRepository karkhanaVasuliRepository) {
        this.karkhanaVasuliRepository = karkhanaVasuliRepository;
    }

    /**
     * Return a {@link List} of {@link KarkhanaVasuli} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KarkhanaVasuli> findByCriteria(KarkhanaVasuliCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KarkhanaVasuli> specification = createSpecification(criteria);
        return karkhanaVasuliRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KarkhanaVasuli} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KarkhanaVasuli> findByCriteria(KarkhanaVasuliCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KarkhanaVasuli> specification = createSpecification(criteria);
        return karkhanaVasuliRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KarkhanaVasuliCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KarkhanaVasuli> specification = createSpecification(criteria);
        return karkhanaVasuliRepository.count(specification);
    }

    /**
     * Function to convert {@link KarkhanaVasuliCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KarkhanaVasuli> createSpecification(KarkhanaVasuliCriteria criteria) {
        Specification<KarkhanaVasuli> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KarkhanaVasuli_.id));
            }
            if (criteria.getKhataNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKhataNumber(), KarkhanaVasuli_.khataNumber));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), KarkhanaVasuli_.karkhanaName));
            }
            if (criteria.getSocietyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSocietyName(), KarkhanaVasuli_.societyName));
            }
            if (criteria.getTalukaName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTalukaName(), KarkhanaVasuli_.talukaName));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), KarkhanaVasuli_.branchName));
            }
            if (criteria.getDefaulterName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaulterName(), KarkhanaVasuli_.defaulterName));
            }
        }
        return specification;
    }
}
