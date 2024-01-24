package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.CropHangam;
import com.cbs.middleware.repository.CropHangamRepository;
import com.cbs.middleware.service.criteria.CropHangamCriteria;
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
 * Service for executing complex queries for {@link CropHangam} entities in the database.
 * The main input is a {@link CropHangamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CropHangam} or a {@link Page} of {@link CropHangam} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CropHangamQueryService extends QueryService<CropHangam> {

    private final Logger log = LoggerFactory.getLogger(CropHangamQueryService.class);

    private final CropHangamRepository cropHangamRepository;

    public CropHangamQueryService(CropHangamRepository cropHangamRepository) {
        this.cropHangamRepository = cropHangamRepository;
    }

    /**
     * Return a {@link List} of {@link CropHangam} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CropHangam> findByCriteria(CropHangamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CropHangam> specification = createSpecification(criteria);
        return cropHangamRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CropHangam} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CropHangam> findByCriteria(CropHangamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CropHangam> specification = createSpecification(criteria);
        return cropHangamRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CropHangamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CropHangam> specification = createSpecification(criteria);
        return cropHangamRepository.count(specification);
    }

    /**
     * Function to convert {@link CropHangamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CropHangam> createSpecification(CropHangamCriteria criteria) {
        Specification<CropHangam> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CropHangam_.id));
            }
            if (criteria.getHangam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHangam(), CropHangam_.hangam));
            }
            if (criteria.getHangamMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHangamMr(), CropHangam_.hangamMr));
            }
        }
        return specification;
    }
}
