package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.service.criteria.CourtCaseCriteria;
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
 * Service for executing complex queries for {@link CourtCase} entities in the database.
 * The main input is a {@link CourtCaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourtCase} or a {@link Page} of {@link CourtCase} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourtCaseQueryService extends QueryService<CourtCase> {

    private final Logger log = LoggerFactory.getLogger(CourtCaseQueryService.class);

    private final CourtCaseRepository courtCaseRepository;

    public CourtCaseQueryService(CourtCaseRepository courtCaseRepository) {
        this.courtCaseRepository = courtCaseRepository;
    }

    /**
     * Return a {@link List} of {@link CourtCase} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourtCase> findByCriteria(CourtCaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CourtCase} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourtCase> findByCriteria(CourtCaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourtCaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.count(specification);
    }

    /**
     * Function to convert {@link CourtCaseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourtCase> createSpecification(CourtCaseCriteria criteria) {
        Specification<CourtCase> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourtCase_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCode(), CourtCase_.code));
            }
            if (criteria.getCaseDinank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCaseDinank(), CourtCase_.caseDinank));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), CourtCase_.bankName));
            }
            if (criteria.getTalukaName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTalukaName(), CourtCase_.talukaName));
            }
            if (criteria.getTalukaCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTalukaCode(), CourtCase_.talukaCode));
            }
            if (criteria.getSabasadSavingAccNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSabasadSavingAccNo(), CourtCase_.sabasadSavingAccNo));
            }
            if (criteria.getSabasadName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSabasadName(), CourtCase_.sabasadName));
            }
            if (criteria.getSabasadAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSabasadAddress(), CourtCase_.sabasadAddress));
            }
            if (criteria.getKarjPrakar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKarjPrakar(), CourtCase_.karjPrakar));
            }
            if (criteria.getVasuliAdhikari() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVasuliAdhikari(), CourtCase_.vasuliAdhikari));
            }
            if (criteria.getEkunJama() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEkunJama(), CourtCase_.ekunJama));
            }
            if (criteria.getBaki() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBaki(), CourtCase_.baki));
            }
            if (criteria.getArOffice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArOffice(), CourtCase_.arOffice));
            }
            if (criteria.getEkunVyaj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEkunVyaj(), CourtCase_.ekunVyaj));
            }
            if (criteria.getJamaVyaj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJamaVyaj(), CourtCase_.jamaVyaj));
            }
            if (criteria.getDandVyaj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDandVyaj(), CourtCase_.dandVyaj));
            }
            if (criteria.getKarjRakkam() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKarjRakkam(), CourtCase_.karjRakkam));
            }
            if (criteria.getThakDinnank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThakDinnank(), CourtCase_.thakDinnank));
            }
            if (criteria.getKarjDinnank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKarjDinnank(), CourtCase_.karjDinnank));
            }
            if (criteria.getMudatSampteDinank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMudatSampteDinank(), CourtCase_.mudatSampteDinank));
            }
            if (criteria.getMudat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMudat(), CourtCase_.mudat));
            }
            if (criteria.getVyaj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVyaj(), CourtCase_.vyaj));
            }
            if (criteria.getHaptaRakkam() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHaptaRakkam(), CourtCase_.haptaRakkam));
            }
            if (criteria.getShakhaVevsthapak() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShakhaVevsthapak(), CourtCase_.shakhaVevsthapak));
            }
            if (criteria.getSuchak() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuchak(), CourtCase_.suchak));
            }
            if (criteria.getAnumodak() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnumodak(), CourtCase_.anumodak));
            }
            if (criteria.getDava() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDava(), CourtCase_.dava));
            }
            if (criteria.getVyajDar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVyajDar(), CourtCase_.vyajDar));
            }
            if (criteria.getSarcharge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSarcharge(), CourtCase_.sarcharge));
            }
            if (criteria.getJyadaVyaj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJyadaVyaj(), CourtCase_.jyadaVyaj));
            }
            if (criteria.getYeneVyaj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYeneVyaj(), CourtCase_.yeneVyaj));
            }
            if (criteria.getVasuliKharch() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVasuliKharch(), CourtCase_.vasuliKharch));
            }
            if (criteria.getEtharKharch() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEtharKharch(), CourtCase_.etharKharch));
            }
            if (criteria.getVima() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVima(), CourtCase_.vima));
            }
            if (criteria.getNotice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotice(), CourtCase_.notice));
            }
            if (criteria.getTharavNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTharavNumber(), CourtCase_.tharavNumber));
            }
            if (criteria.getTharavDinank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTharavDinank(), CourtCase_.tharavDinank));
            }
            if (criteria.getVishayKramank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVishayKramank(), CourtCase_.vishayKramank));
            }
            if (criteria.getNoticeOne() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoticeOne(), CourtCase_.noticeOne));
            }
            if (criteria.getNoticeTwo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoticeTwo(), CourtCase_.noticeTwo));
            }
            if (criteria.getWar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWar(), CourtCase_.war));
            }
            if (criteria.getVel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVel(), CourtCase_.vel));
            }
            if (criteria.getJamindarOne() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJamindarOne(), CourtCase_.jamindarOne));
            }
            if (criteria.getJamindarOneAddress() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getJamindarOneAddress(), CourtCase_.jamindarOneAddress));
            }
            if (criteria.getJamindarTwo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJamindarTwo(), CourtCase_.jamindarTwo));
            }
            if (criteria.getJamindarTwoAddress() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getJamindarTwoAddress(), CourtCase_.jamindarTwoAddress));
            }
            if (criteria.getTaranType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaranType(), CourtCase_.taranType));
            }
            if (criteria.getTaranDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaranDetails(), CourtCase_.taranDetails));
            }
        }
        return specification;
    }
}
