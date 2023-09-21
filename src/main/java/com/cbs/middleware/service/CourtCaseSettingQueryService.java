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
            if (criteria.getVasuliAdhikariName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getVasuliAdhikariName(), CourtCaseSetting_.vasuliAdhikariName));
            }
            if (criteria.getArOfficeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArOfficeName(), CourtCaseSetting_.arOfficeName));
            }
            if (criteria.getChairmanName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChairmanName(), CourtCaseSetting_.chairmanName));
            }
            if (criteria.getSachivName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSachivName(), CourtCaseSetting_.sachivName));
            }
            if (criteria.getSuchakName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuchakName(), CourtCaseSetting_.suchakName));
            }
            if (criteria.getAnumodakName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnumodakName(), CourtCaseSetting_.anumodakName));
            }
            if (criteria.getVasuliExpense() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVasuliExpense(), CourtCaseSetting_.vasuliExpense));
            }
            if (criteria.getOtherExpense() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtherExpense(), CourtCaseSetting_.otherExpense));
            }
            if (criteria.getNoticeExpense() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoticeExpense(), CourtCaseSetting_.noticeExpense));
            }
            if (criteria.getMeetingNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMeetingNo(), CourtCaseSetting_.meetingNo));
            }
            if (criteria.getMeetingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeetingDate(), CourtCaseSetting_.meetingDate));
            }
            if (criteria.getSubjectNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectNo(), CourtCaseSetting_.subjectNo));
            }
            if (criteria.getMeetingDay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMeetingDay(), CourtCaseSetting_.meetingDay));
            }
            if (criteria.getMeetingTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMeetingTime(), CourtCaseSetting_.meetingTime));
            }
        }
        return specification;
    }
}
