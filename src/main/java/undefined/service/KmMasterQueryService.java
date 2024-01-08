package com.cbs.middleware.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.service.dto.KmMasterCriteria;


/**
 * Service for executing complex queries for KmMaster entities in the database.
 * The main input is a {@link KmMasterCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link KmMaster} or a {@link Page} of {%link KmMaster} which fullfills the criterias
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
     * Return a {@link List} of {%link KmMaster} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KmMaster> findByCriteria(KmMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<KmMaster> specification = createSpecification(criteria);
        return kmMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link KmMaster} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KmMaster> findByCriteria(KmMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<KmMaster> specification = createSpecification(criteria);
        return kmMasterRepository.findAll(specification, page);
    }

    /**
     * Function to convert KmMasterCriteria to a {@link Specifications}
     */
    private Specifications<KmMaster> createSpecification(KmMasterCriteria criteria) {
        Specifications<KmMaster> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), KmMaster_.id));
            }
            if (criteria.getBranchCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchCode(), KmMaster_.branchCode));
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
            if (criteria.getCaste() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaste(), KmMaster_.caste));
            }
            if (criteria.getCasteMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCasteMr(), KmMaster_.casteMr));
            }
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsNumber(), KmMaster_.pacsNumber));
            }
            if (criteria.getAreaHect() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaHect(), KmMaster_.areaHect));
            }
            if (criteria.getAadharNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAadharNo(), KmMaster_.aadharNo));
            }
            if (criteria.getAadharNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharNoMr(), KmMaster_.aadharNoMr));
            }
            if (criteria.getPanNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPanNo(), KmMaster_.panNo));
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
            if (criteria.getSavingNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSavingNo(), KmMaster_.savingNo));
            }
            if (criteria.getSavingNoMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSavingNoMr(), KmMaster_.savingNoMr));
            }
            if (criteria.getEntryFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntryFlag(), KmMaster_.entryFlag));
            }
            if (criteria.getPacsMemberCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsMemberCode(), KmMaster_.pacsMemberCode));
            }
            if (criteria.getFarmerTypeMasterId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFarmerTypeMasterId(), KmMaster_.farmerTypeMaster, FarmerTypeMaster_.id));
            }
        }
        return specification;
    }

}
