package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KamalCrop;
import com.cbs.middleware.repository.KamalCropRepository;
import com.cbs.middleware.service.criteria.KamalCropCriteria;
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
 * Service for executing complex queries for {@link KamalCrop} entities in the database.
 * The main input is a {@link KamalCropCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KamalCrop} or a {@link Page} of {@link KamalCrop} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KamalCropQueryService extends QueryService<KamalCrop> {

    private final Logger log = LoggerFactory.getLogger(KamalCropQueryService.class);

    private final KamalCropRepository kamalCropRepository;

    public KamalCropQueryService(KamalCropRepository kamalCropRepository) {
        this.kamalCropRepository = kamalCropRepository;
    }

    /**
     * Return a {@link List} of {@link KamalCrop} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KamalCrop> findByCriteria(KamalCropCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KamalCrop> specification = createSpecification(criteria);
        return kamalCropRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KamalCrop} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KamalCrop> findByCriteria(KamalCropCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KamalCrop> specification = createSpecification(criteria);
        return kamalCropRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KamalCropCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KamalCrop> specification = createSpecification(criteria);
        return kamalCropRepository.count(specification);
    }

    /**
     * Function to convert {@link KamalCropCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KamalCrop> createSpecification(KamalCropCriteria criteria) {
        Specification<KamalCrop> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KamalCrop_.id));
            }
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsNumber(), KamalCrop_.pacsNumber));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), KamalCrop_.financialYear));
            }
            if (criteria.getMemberCount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberCount(), KamalCrop_.memberCount));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArea(), KamalCrop_.area));
            }
            if (criteria.getPacsAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsAmount(), KamalCrop_.pacsAmount));
            }
            if (criteria.getBranchAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchAmount(), KamalCrop_.branchAmount));
            }
            if (criteria.getHeadOfficeAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHeadOfficeAmount(), KamalCrop_.headOfficeAmount));
            }
            if (criteria.getDivisionalOfficeAmount() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDivisionalOfficeAmount(), KamalCrop_.divisionalOfficeAmount));
            }
            if (criteria.getCropEligibilityAmount() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCropEligibilityAmount(), KamalCrop_.cropEligibilityAmount));
            }
            if (criteria.getKamalSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKamalSocietyId(),
                            root -> root.join(KamalCrop_.kamalSociety, JoinType.LEFT).get(KamalSociety_.id)
                        )
                    );
            }
            if (criteria.getFarmerTypeMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFarmerTypeMasterId(),
                            root -> root.join(KamalCrop_.farmerTypeMaster, JoinType.LEFT).get(FarmerTypeMaster_.id)
                        )
                    );
            }
            if (criteria.getSeasonMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSeasonMasterId(),
                            root -> root.join(KamalCrop_.seasonMaster, JoinType.LEFT).get(SeasonMaster_.id)
                        )
                    );
            }
            if (criteria.getCropMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCropMasterId(),
                            root -> root.join(KamalCrop_.cropMaster, JoinType.LEFT).get(CropMaster_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
