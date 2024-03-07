package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KmMaganiCrop;
import com.cbs.middleware.repository.KmMaganiCropRepository;
import com.cbs.middleware.service.criteria.KmMaganiCropCriteria;
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
 * Service for executing complex queries for {@link KmMaganiCrop} entities in the database.
 * The main input is a {@link KmMaganiCropCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KmMaganiCrop} or a {@link Page} of {@link KmMaganiCrop} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KmMaganiCropQueryService extends QueryService<KmMaganiCrop> {

    private final Logger log = LoggerFactory.getLogger(KmMaganiCropQueryService.class);

    private final KmMaganiCropRepository kmMaganiCropRepository;

    public KmMaganiCropQueryService(KmMaganiCropRepository kmMaganiCropRepository) {
        this.kmMaganiCropRepository = kmMaganiCropRepository;
    }

    /**
     * Return a {@link List} of {@link KmMaganiCrop} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KmMaganiCrop> findByCriteria(KmMaganiCropCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KmMaganiCrop> specification = createSpecification(criteria);
        return kmMaganiCropRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KmMaganiCrop} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KmMaganiCrop> findByCriteria(KmMaganiCropCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KmMaganiCrop> specification = createSpecification(criteria);
        return kmMaganiCropRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KmMaganiCropCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KmMaganiCrop> specification = createSpecification(criteria);
        return kmMaganiCropRepository.count(specification);
    }

    /**
     * Function to convert {@link KmMaganiCropCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KmMaganiCrop> createSpecification(KmMaganiCropCriteria criteria) {
        Specification<KmMaganiCrop> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KmMaganiCrop_.id));
            }
            if (criteria.getCropName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropName(), KmMaganiCrop_.cropName));
            }
            if (criteria.getCropBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCropBalance(), KmMaganiCrop_.cropBalance));
            }
            if (criteria.getCropDue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCropDue(), KmMaganiCrop_.cropDue));
            }
            if (criteria.getCropDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCropDueDate(), KmMaganiCrop_.cropDueDate));
            }
            if (criteria.getCropVasuliPatraDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCropVasuliPatraDate(), KmMaganiCrop_.cropVasuliPatraDate));
            }
            if (criteria.getKmManjuri() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmManjuri(), KmMaganiCrop_.kmManjuri));
            }
            if (criteria.getKmArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmArea(), KmMaganiCrop_.kmArea));
            }
            if (criteria.geteKararNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.geteKararNumber(), KmMaganiCrop_.eKararNumber));
            }
            if (criteria.geteKararAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteKararAmount(), KmMaganiCrop_.eKararAmount));
            }
            if (criteria.geteKararArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteKararArea(), KmMaganiCrop_.eKararArea));
            }
            if (criteria.getMaganiArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaganiArea(), KmMaganiCrop_.maganiArea));
            }
            if (criteria.getMaganiAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaganiAmount(), KmMaganiCrop_.maganiAmount));
            }
            if (criteria.getMaganiShare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaganiShare(), KmMaganiCrop_.maganiShare));
            }
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsNumber(), KmMaganiCrop_.pacsNumber));
            }
            if (criteria.getKmMaganiId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKmMaganiId(),
                            root -> root.join(KmMaganiCrop_.kmMagani, JoinType.LEFT).get(KmMagani_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
