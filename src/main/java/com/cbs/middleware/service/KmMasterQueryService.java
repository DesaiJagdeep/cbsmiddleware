package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.service.criteria.KmMasterCriteria;
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
 * Service for executing complex queries for {@link KmMaster} entities in the database.
 * The main input is a {@link KmMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KmMaster} or a {@link Page} of {@link KmMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KmMasterQueryService extends QueryService<KmMaster> {

    private final Logger log = LoggerFactory.getLogger(KmMasterQueryService.class);

    private final KmMasterRepository kmMasterRepository;

    public KmMasterQueryService(KmMasterRepository kmMasterRepository) {
        this.kmMasterRepository = kmMasterRepository;
    }

    /**
     * Return a {@link List} of {@link KmMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KmMaster> findByCriteria(KmMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KmMaster> specification = createSpecification(criteria);
        return kmMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KmMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KmMaster> findByCriteria(KmMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KmMaster> specification = createSpecification(criteria);
        return kmMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KmMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KmMaster> specification = createSpecification(criteria);
        return kmMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link KmMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KmMaster> createSpecification(KmMasterCriteria criteria) {
        Specification<KmMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KmMaster_.id));
            }
            if (criteria.getBranchCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchCode(), KmMaster_.branchCode));
            }
            if (criteria.getBranchCodeMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchCodeMr(), KmMaster_.branchCodeMr));
            }
            if (criteria.getFarmerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerName(), KmMaster_.farmerName));
            }
            if (criteria.getFarmerNameMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerNameMr(), KmMaster_.farmerNameMr));
            }
            if (criteria.getFarmerAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerAddress(), KmMaster_.farmerAddress));
            }
            if (criteria.getFarmerAddressMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerAddressMr(), KmMaster_.farmerAddressMr));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), KmMaster_.gender));
            }
            if (criteria.getGenderMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGenderMr(), KmMaster_.genderMr));
            }
            if (criteria.getCaste() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaste(), KmMaster_.caste));
            }
            if (criteria.getCasteMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCasteMr(), KmMaster_.casteMr));
            }
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsNumber(), KmMaster_.pacsNumber));
            }

            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthDate(), KmMaster_.birthDate));
            }
            if (criteria.getBirthDateMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthDateMr(), KmMaster_.birthDateMr));
            }
            if (criteria.getLoanAcNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanAcNo(), KmMaster_.loanAcNo));
            }
            if (criteria.getLoanAcNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanAcNoMr(), KmMaster_.loanAcNoMr));
            }

            if (criteria.getAreaHector() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAreaHector(), KmMaster_.areaHector));
            }
            if (criteria.getAreaHectorMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaHectorMr(), KmMaster_.areaHectorMr));
            }
            if (criteria.getAadharNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharNo(), KmMaster_.aadharNo));
            }
            if (criteria.getAadharNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharNoMr(), KmMaster_.aadharNoMr));
            }
            if (criteria.getPanNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanNo(), KmMaster_.panNo));
            }
            if (criteria.getPanNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanNoMr(), KmMaster_.panNoMr));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), KmMaster_.mobileNo));
            }
            if (criteria.getMobileNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNoMr(), KmMaster_.mobileNoMr));
            }
            if (criteria.getKccNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKccNo(), KmMaster_.kccNo));
            }
            if (criteria.getKccNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKccNoMr(), KmMaster_.kccNoMr));
            }
            if (criteria.getSavingAcNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSavingAcNo(), KmMaster_.savingAcNo));
            }
            if (criteria.getSavingAcNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSavingAcNoMr(), KmMaster_.savingAcNoMr));
            }
            if (criteria.getPacsMemberCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsMemberCode(), KmMaster_.pacsMemberCode));
            }
            if (criteria.getPacsMemberCodeMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsMemberCodeMr(), KmMaster_.pacsMemberCodeMr));
            }
            if (criteria.getEntryFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntryFlag(), KmMaster_.entryFlag));
            }
            if (criteria.getFarmerTypeMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFarmerTypeMasterId(),
                            root -> root.join(KmMaster_.farmerTypeMaster, JoinType.LEFT).get(FarmerTypeMaster_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
