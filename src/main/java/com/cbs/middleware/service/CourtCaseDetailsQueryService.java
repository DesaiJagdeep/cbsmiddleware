package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.CourtCaseDetails;
import com.cbs.middleware.repository.CourtCaseDetailsRepository;
import com.cbs.middleware.service.criteria.CourtCaseDetailsCriteria;
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
 * Service for executing complex queries for {@link CourtCaseDetails} entities in the database.
 * The main input is a {@link CourtCaseDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourtCaseDetails} or a {@link Page} of {@link CourtCaseDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourtCaseDetailsQueryService extends QueryService<CourtCaseDetails> {

    private final Logger log = LoggerFactory.getLogger(CourtCaseDetailsQueryService.class);

    private final CourtCaseDetailsRepository courtCaseDetailsRepository;

    public CourtCaseDetailsQueryService(CourtCaseDetailsRepository courtCaseDetailsRepository) {
        this.courtCaseDetailsRepository = courtCaseDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link CourtCaseDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourtCaseDetails> findByCriteria(CourtCaseDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourtCaseDetails> specification = createSpecification(criteria);
        return courtCaseDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CourtCaseDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourtCaseDetails> findByCriteria(CourtCaseDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourtCaseDetails> specification = createSpecification(criteria);
        return courtCaseDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourtCaseDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourtCaseDetails> specification = createSpecification(criteria);
        return courtCaseDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link CourtCaseDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourtCaseDetails> createSpecification(CourtCaseDetailsCriteria criteria) {
        Specification<CourtCaseDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourtCaseDetails_.id));
            }
            if (criteria.getKramank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKramank(), CourtCaseDetails_.kramank));
            }
            if (criteria.getDinank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDinank(), CourtCaseDetails_.dinank));
            }
            if (criteria.getCaseDinank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCaseDinank(), CourtCaseDetails_.caseDinank));
            }
            if (criteria.getSabhasad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSabhasad(), CourtCaseDetails_.sabhasad));
            }
            if (criteria.getSabhasadAccNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSabhasadAccNo(), CourtCaseDetails_.sabhasadAccNo));
            }
            if (criteria.getKarjPrakarType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKarjPrakarType(), CourtCaseDetails_.karjPrakarType));
            }
            if (criteria.getKarjPrakar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKarjPrakar(), CourtCaseDetails_.karjPrakar));
            }
            if (criteria.getCertificateMilale() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCertificateMilale(), CourtCaseDetails_.certificateMilale));
            }
            if (criteria.getCertificateDinnank() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCertificateDinnank(), CourtCaseDetails_.certificateDinnank));
            }
            if (criteria.getCertificateRakkam() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCertificateRakkam(), CourtCaseDetails_.certificateRakkam));
            }
            if (criteria.getYenebaki() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYenebaki(), CourtCaseDetails_.yenebaki));
            }
            if (criteria.getVyaj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVyaj(), CourtCaseDetails_.vyaj));
            }
            if (criteria.getEtar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEtar(), CourtCaseDetails_.etar));
            }
            if (criteria.getDimmandMilale() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDimmandMilale(), CourtCaseDetails_.dimmandMilale));
            }
            if (criteria.getDimmandDinnank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDimmandDinnank(), CourtCaseDetails_.dimmandDinnank));
            }
            if (criteria.getJaptiAadhesh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJaptiAadhesh(), CourtCaseDetails_.japtiAadhesh));
            }
            if (criteria.getJaptiAadheshDinnank() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getJaptiAadheshDinnank(), CourtCaseDetails_.japtiAadheshDinnank));
            }
            if (criteria.getSthavr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSthavr(), CourtCaseDetails_.sthavr));
            }
            if (criteria.getJangam() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJangam(), CourtCaseDetails_.jangam));
            }
            if (criteria.getVikriAadhesh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVikriAadhesh(), CourtCaseDetails_.vikriAadhesh));
            }
            if (criteria.getVikriAddheshDinnank() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getVikriAddheshDinnank(), CourtCaseDetails_.vikriAddheshDinnank));
            }
            if (criteria.getEtarTapshil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtarTapshil(), CourtCaseDetails_.etarTapshil));
            }
        }
        return specification;
    }
}
