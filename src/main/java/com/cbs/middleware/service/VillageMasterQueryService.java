package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.VillageMaster;
import com.cbs.middleware.repository.VillageMasterRepository;
import com.cbs.middleware.service.criteria.VillageMasterCriteria;
import javax.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link VillageMaster} entities in the database.
 * The main input is a {@link VillageMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VillageMaster} or a {@link Page} of {@link VillageMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VillageMasterQueryService extends QueryService<VillageMaster> {

    private final Logger log = LoggerFactory.getLogger(VillageMasterQueryService.class);

    private final VillageMasterRepository villageMasterRepository;

    public VillageMasterQueryService(VillageMasterRepository villageMasterRepository) {
        this.villageMasterRepository = villageMasterRepository;
    }

    /**
     * Return a {@link List} of {@link VillageMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VillageMaster> findByCriteria(VillageMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VillageMaster> specification = createSpecification(criteria);
        return villageMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link VillageMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VillageMaster> findByCriteria(VillageMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VillageMaster> specification = createSpecification(criteria);
        return villageMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VillageMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VillageMaster> specification = createSpecification(criteria);
        return villageMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link VillageMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VillageMaster> createSpecification(VillageMasterCriteria criteria) {
        Specification<VillageMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VillageMaster_.id));
            }
            if (criteria.getVillageName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageName(), VillageMaster_.villageName));
            }
            if (criteria.getVillageNameMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageNameMr(), VillageMaster_.villageNameMr));
            }
            if (criteria.getVillageCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVillageCode(), VillageMaster_.villageCode));
            }
            if (criteria.getVillageCodeMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageCodeMr(), VillageMaster_.villageCodeMr));
            }
            if (criteria.getTalukaMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTalukaMasterId(),
                            root -> root.join(VillageMaster_.talukaMaster,JoinType.LEFT).get(TalukaMaster_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
