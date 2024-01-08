package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.FactoryMaster;
import com.cbs.middleware.repository.FactoryMasterRepository;
import com.cbs.middleware.service.criteria.FactoryMasterCriteria;
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
 * Service for executing complex queries for {@link FactoryMaster} entities in the database.
 * The main input is a {@link FactoryMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FactoryMaster} or a {@link Page} of {@link FactoryMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FactoryMasterQueryService extends QueryService<FactoryMaster> {

    private final Logger log = LoggerFactory.getLogger(FactoryMasterQueryService.class);

    private final FactoryMasterRepository factoryMasterRepository;

    public FactoryMasterQueryService(FactoryMasterRepository factoryMasterRepository) {
        this.factoryMasterRepository = factoryMasterRepository;
    }

    /**
     * Return a {@link List} of {@link FactoryMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FactoryMaster> findByCriteria(FactoryMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FactoryMaster> specification = createSpecification(criteria);
        return factoryMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FactoryMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FactoryMaster> findByCriteria(FactoryMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FactoryMaster> specification = createSpecification(criteria);
        return factoryMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FactoryMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FactoryMaster> specification = createSpecification(criteria);
        return factoryMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link FactoryMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FactoryMaster> createSpecification(FactoryMasterCriteria criteria) {
        Specification<FactoryMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FactoryMaster_.id));
            }
            if (criteria.getFactoryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFactoryName(), FactoryMaster_.factoryName));
            }
            if (criteria.getFactoryNameMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFactoryNameMr(), FactoryMaster_.factoryNameMr));
            }
            if (criteria.getFactoryCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFactoryCode(), FactoryMaster_.factoryCode));
            }
            if (criteria.getFactoryCodeMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFactoryCodeMr(), FactoryMaster_.factoryCodeMr));
            }
            if (criteria.getFactoryAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFactoryAddress(), FactoryMaster_.factoryAddress));
            }
            if (criteria.getFactoryAddressMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFactoryAddressMr(), FactoryMaster_.factoryAddressMr));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), FactoryMaster_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), FactoryMaster_.status));
            }
        }
        return specification;
    }
}
