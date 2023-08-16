package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.CbsDataReport;
import com.cbs.middleware.repository.CbsDataReportRepository;
import com.cbs.middleware.service.criteria.CbsDataReportCriteria;
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
 * Service for executing complex queries for {@link CbsDataReport} entities in the database.
 * The main input is a {@link CbsDataReportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CbsDataReport} or a {@link Page} of {@link CbsDataReport} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CbsDataReportQueryService extends QueryService<CbsDataReport> {

    private final Logger log = LoggerFactory.getLogger(CbsDataReportQueryService.class);

    private final CbsDataReportRepository cbsDataReportRepository;

    public CbsDataReportQueryService(CbsDataReportRepository cbsDataReportRepository) {
        this.cbsDataReportRepository = cbsDataReportRepository;
    }

    /**
     * Return a {@link List} of {@link CbsDataReport} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CbsDataReport> findByCriteria(CbsDataReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CbsDataReport> specification = createSpecification(criteria);
        return cbsDataReportRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CbsDataReport} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CbsDataReport> findByCriteria(CbsDataReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CbsDataReport> specification = createSpecification(criteria);
        return cbsDataReportRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CbsDataReportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CbsDataReport> specification = createSpecification(criteria);
        return cbsDataReportRepository.count(specification);
    }

    /**
     * Function to convert {@link CbsDataReportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CbsDataReport> createSpecification(CbsDataReportCriteria criteria) {
        Specification<CbsDataReport> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CbsDataReport_.id));
            }
            if (criteria.getFarmerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerName(), CbsDataReport_.farmerName));
            }
        }
        return specification;
    }
}
