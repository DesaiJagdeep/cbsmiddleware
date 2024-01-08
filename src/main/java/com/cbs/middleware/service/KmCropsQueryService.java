package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KmCrops;
import com.cbs.middleware.repository.KmCropsRepository;
import com.cbs.middleware.service.criteria.KmCropsCriteria;
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
 * Service for executing complex queries for {@link KmCrops} entities in the database.
 * The main input is a {@link KmCropsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KmCrops} or a {@link Page} of {@link KmCrops} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KmCropsQueryService extends QueryService<KmCrops> {

    private final Logger log = LoggerFactory.getLogger(KmCropsQueryService.class);

    private final KmCropsRepository kmCropsRepository;

    public KmCropsQueryService(KmCropsRepository kmCropsRepository) {
        this.kmCropsRepository = kmCropsRepository;
    }

    /**
     * Return a {@link List} of {@link KmCrops} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KmCrops> findByCriteria(KmCropsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KmCrops> specification = createSpecification(criteria);
        return kmCropsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KmCrops} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KmCrops> findByCriteria(KmCropsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KmCrops> specification = createSpecification(criteria);
        return kmCropsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KmCropsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KmCrops> specification = createSpecification(criteria);
        return kmCropsRepository.count(specification);
    }

    /**
     * Function to convert {@link KmCropsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KmCrops> createSpecification(KmCropsCriteria criteria) {
        Specification<KmCrops> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KmCrops_.id));
            }
            if (criteria.getHector() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHector(), KmCrops_.hector));
            }
            if (criteria.getHectorMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHectorMr(), KmCrops_.hectorMr));
            }
            if (criteria.getAre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAre(), KmCrops_.are));
            }
            if (criteria.getAreMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreMr(), KmCrops_.areMr));
            }
            if (criteria.getNoOfTree() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfTree(), KmCrops_.noOfTree));
            }
            if (criteria.getNoOfTreeMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoOfTreeMr(), KmCrops_.noOfTreeMr));
            }
            if (criteria.getDemand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDemand(), KmCrops_.demand));
            }
            if (criteria.getDemandMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDemandMr(), KmCrops_.demandMr));
            }
            if (criteria.getSociety() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSociety(), KmCrops_.society));
            }
            if (criteria.getSocietyMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSocietyMr(), KmCrops_.societyMr));
            }
            if (criteria.getBankAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankAmt(), KmCrops_.bankAmt));
            }
            if (criteria.getBankAmtMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAmtMr(), KmCrops_.bankAmtMr));
            }
            if (criteria.getVibhagiAdhikari() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVibhagiAdhikari(), KmCrops_.vibhagiAdhikari));
            }
            if (criteria.getVibhagiAdhikariMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVibhagiAdhikariMr(), KmCrops_.vibhagiAdhikariMr));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), KmCrops_.branch));
            }
            if (criteria.getBranchMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchMr(), KmCrops_.branchMr));
            }
            if (criteria.getInspAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInspAmt(), KmCrops_.inspAmt));
            }
            if (criteria.getInspAmtMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInspAmtMr(), KmCrops_.inspAmtMr));
            }
            if (criteria.getCropMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCropMasterId(),
                            root -> root.join(KmCrops_.cropMaster, JoinType.LEFT).get(CropMaster_.id)
                        )
                    );
            }
            if (criteria.getKmDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKmDetailsId(),
                            root -> root.join(KmCrops_.kmDetails, JoinType.LEFT).get(KmDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
