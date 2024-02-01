package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.CropRateMaster;
import com.cbs.middleware.repository.CropRateMasterRepository;
import com.cbs.middleware.service.criteria.CropRateMasterCriteria;
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
 * Service for executing complex queries for {@link CropRateMaster} entities in the database.
 * The main input is a {@link CropRateMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CropRateMaster} or a {@link Page} of {@link CropRateMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CropRateMasterQueryService extends QueryService<CropRateMaster> {

    private final Logger log = LoggerFactory.getLogger(CropRateMasterQueryService.class);

    private final CropRateMasterRepository cropRateMasterRepository;

    public CropRateMasterQueryService(CropRateMasterRepository cropRateMasterRepository) {
        this.cropRateMasterRepository = cropRateMasterRepository;
    }

    /**
     * Return a {@link List} of {@link CropRateMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CropRateMaster> findByCriteria(CropRateMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CropRateMaster> specification = createSpecification(criteria);
        return cropRateMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CropRateMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CropRateMaster> findByCriteria(CropRateMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CropRateMaster> specification = createSpecification(criteria);
        return cropRateMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CropRateMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CropRateMaster> specification = createSpecification(criteria);
        return cropRateMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link CropRateMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CropRateMaster> createSpecification(CropRateMasterCriteria criteria) {
        Specification<CropRateMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CropRateMaster_.id));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), CropRateMaster_.financialYear));
            }
            if (criteria.getCurrentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentAmount(), CropRateMaster_.currentAmount));
            }
            if (criteria.getCurrentAmountMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentAmountMr(), CropRateMaster_.currentAmountMr));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), CropRateMaster_.percentage));
            }
            if (criteria.getActiveFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveFlag(), CropRateMaster_.activeFlag));
            }
            if (criteria.getCropMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCropMasterId(),
                            root -> root.join(CropRateMaster_.cropMaster, JoinType.LEFT).get(CropMaster_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
