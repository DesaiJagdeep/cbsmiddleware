package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KamalMaryadaPatrak;
import com.cbs.middleware.repository.KamalMaryadaPatrakRepository;
import com.cbs.middleware.service.criteria.KamalMaryadaPatrakCriteria;
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
 * Service for executing complex queries for {@link KamalMaryadaPatrak} entities in the database.
 * The main input is a {@link KamalMaryadaPatrakCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KamalMaryadaPatrak} or a {@link Page} of {@link KamalMaryadaPatrak} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KamalMaryadaPatrakQueryService extends QueryService<KamalMaryadaPatrak> {

    private final Logger log = LoggerFactory.getLogger(KamalMaryadaPatrakQueryService.class);

    private final KamalMaryadaPatrakRepository kamalMaryadaPatrakRepository;

    public KamalMaryadaPatrakQueryService(KamalMaryadaPatrakRepository kamalMaryadaPatrakRepository) {
        this.kamalMaryadaPatrakRepository = kamalMaryadaPatrakRepository;
    }

    /**
     * Return a {@link List} of {@link KamalMaryadaPatrak} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KamalMaryadaPatrak> findByCriteria(KamalMaryadaPatrakCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KamalMaryadaPatrak> specification = createSpecification(criteria);
        return kamalMaryadaPatrakRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KamalMaryadaPatrak} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KamalMaryadaPatrak> findByCriteria(KamalMaryadaPatrakCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KamalMaryadaPatrak> specification = createSpecification(criteria);
        return kamalMaryadaPatrakRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KamalMaryadaPatrakCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KamalMaryadaPatrak> specification = createSpecification(criteria);
        return kamalMaryadaPatrakRepository.count(specification);
    }

    /**
     * Function to convert {@link KamalMaryadaPatrakCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KamalMaryadaPatrak> createSpecification(KamalMaryadaPatrakCriteria criteria) {
        Specification<KamalMaryadaPatrak> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KamalMaryadaPatrak_.id));
            }
            if (criteria.getCropLoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropLoan(), KamalMaryadaPatrak_.cropLoan));
            }
            if (criteria.getKmChart() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmChart(), KamalMaryadaPatrak_.kmChart));
            }
            if (criteria.getDemand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDemand(), KamalMaryadaPatrak_.demand));
            }
            if (criteria.getKmSummary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKmSummary(), KamalMaryadaPatrak_.kmSummary));
            }
            if (criteria.getKmDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKmDate(), KamalMaryadaPatrak_.kmDate));
            }
            if (criteria.getToKMDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToKMDate(), KamalMaryadaPatrak_.toKMDate));
            }
            if (criteria.getCoverPage() != null) {
                specification = specification.and(buildSpecification(criteria.getCoverPage(), KamalMaryadaPatrak_.coverPage));
            }
            if (criteria.getCropTypeNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCropTypeNumber(), KamalMaryadaPatrak_.cropTypeNumber));
            }
            if (criteria.getCropType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropType(), KamalMaryadaPatrak_.cropType));
            }
            if (criteria.getFromHector() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFromHector(), KamalMaryadaPatrak_.fromHector));
            }
            if (criteria.getToHector() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToHector(), KamalMaryadaPatrak_.toHector));
            }
            if (criteria.getDefaulterName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaulterName(), KamalMaryadaPatrak_.defaulterName));
            }
            if (criteria.getSuchakName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuchakName(), KamalMaryadaPatrak_.suchakName));
            }
            if (criteria.getAnumodakName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnumodakName(), KamalMaryadaPatrak_.anumodakName));
            }
            if (criteria.getMeetingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeetingDate(), KamalMaryadaPatrak_.meetingDate));
            }
            if (criteria.getResolutionNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getResolutionNumber(), KamalMaryadaPatrak_.resolutionNumber));
            }
        }
        return specification;
    }
}
