package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.service.criteria.CourtCaseSettingCriteria;
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
 * Service for executing complex queries for {@link CourtCaseSetting} entities in the database.
 * The main input is a {@link CourtCaseSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourtCaseSetting} or a {@link Page} of {@link CourtCaseSetting} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourtCaseSettingQueryService extends QueryService<CourtCaseSetting> {

    private final Logger log = LoggerFactory.getLogger(CourtCaseSettingQueryService.class);

    private final CourtCaseSettingRepository courtCaseSettingRepository;

    public CourtCaseSettingQueryService(CourtCaseSettingRepository courtCaseSettingRepository) {
        this.courtCaseSettingRepository = courtCaseSettingRepository;
    }

    /**
     * Return a {@link List} of {@link CourtCaseSetting} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourtCaseSetting> findByCriteria(CourtCaseSettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourtCaseSetting> specification = createSpecification(criteria);
        return courtCaseSettingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CourtCaseSetting} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourtCaseSetting> findByCriteria(CourtCaseSettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourtCaseSetting> specification = createSpecification(criteria);
        return courtCaseSettingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourtCaseSettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourtCaseSetting> specification = createSpecification(criteria);
        return courtCaseSettingRepository.count(specification);
    }

    /**
     * Function to convert {@link CourtCaseSettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourtCaseSetting> createSpecification(CourtCaseSettingCriteria criteria) {
        Specification<CourtCaseSetting> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourtCaseSetting_.id));
            }
            if (criteria.getDinank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDinank(), CourtCaseSetting_.dinank));
            }
            if (criteria.getShakhaVevsthapak() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getShakhaVevsthapak(), CourtCaseSetting_.shakhaVevsthapak));
            }
            if (criteria.getSuchak() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuchak(), CourtCaseSetting_.suchak));
            }
            if (criteria.getAanumodak() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAanumodak(), CourtCaseSetting_.aanumodak));
            }
            if (criteria.getVasuliAdhikari() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVasuliAdhikari(), CourtCaseSetting_.vasuliAdhikari));
            }
            if (criteria.getArOffice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArOffice(), CourtCaseSetting_.arOffice));
            }
            if (criteria.getTharavNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTharavNumber(), CourtCaseSetting_.tharavNumber));
            }
            if (criteria.getTharavDinank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTharavDinank(), CourtCaseSetting_.tharavDinank));
            }
            if (criteria.getKarjFedNotice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKarjFedNotice(), CourtCaseSetting_.karjFedNotice));
            }
            if (criteria.getOneZeroOneNoticeOne() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getOneZeroOneNoticeOne(), CourtCaseSetting_.oneZeroOneNoticeOne));
            }
            if (criteria.getOneZeroOneNoticeTwo() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getOneZeroOneNoticeTwo(), CourtCaseSetting_.oneZeroOneNoticeTwo));
            }
            if (criteria.getVishayKramank() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVishayKramank(), CourtCaseSetting_.vishayKramank));
            }
            if (criteria.getWar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWar(), CourtCaseSetting_.war));
            }
            if (criteria.getVel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVel(), CourtCaseSetting_.vel));
            }
            if (criteria.getMaganiNotice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaganiNotice(), CourtCaseSetting_.maganiNotice));
            }
            if (criteria.getEtarKharch() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEtarKharch(), CourtCaseSetting_.etarKharch));
            }
            if (criteria.getNoticeKharch() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoticeKharch(), CourtCaseSetting_.noticeKharch));
            }
            if (criteria.getVasuliKharch() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVasuliKharch(), CourtCaseSetting_.vasuliKharch));
            }
        }
        return specification;
    }
}
