package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.service.criteria.IssFileParserCriteria;
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
 * Service for executing complex queries for {@link IssFileParser} entities in the database.
 * The main input is a {@link IssFileParserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IssFileParser} or a {@link Page} of {@link IssFileParser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IssFileParserQueryService extends QueryService<IssFileParser> {

    private final Logger log = LoggerFactory.getLogger(IssFileParserQueryService.class);

    private final IssFileParserRepository issFileParserRepository;

    public IssFileParserQueryService(IssFileParserRepository issFileParserRepository) {
        this.issFileParserRepository = issFileParserRepository;
    }

    /**
     * Return a {@link List} of {@link IssFileParser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IssFileParser> findByCriteria(IssFileParserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IssFileParser> specification = createSpecification(criteria);
        return issFileParserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link IssFileParser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IssFileParser> findByCriteria(IssFileParserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IssFileParser> specification = createSpecification(criteria);
        return issFileParserRepository.findAll(specification, page);
    }

    @Transactional(readOnly = true)
    public Page<IssFileParser> findByCriteriaSchemeWiseBranchCode(
        IssFileParserCriteria criteria,
        Pageable page,
        String schemeWiseBranchCode
    ) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IssFileParser> specification = createSpecification(criteria);
        return issFileParserRepository.findAllBySchemeWiseBranchCode(schemeWiseBranchCode, page);
    }

    @Transactional(readOnly = true)
    public Page<IssFileParser> findByCriteriaPackNumber(IssFileParserCriteria criteria, Pageable page, String pacsNumber) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IssFileParser> specification = createSpecification(criteria);
        return issFileParserRepository.findAllByPacsNumber(pacsNumber, page);
    }

    @Transactional(readOnly = true)
    public Page<IssFileParser> findByIssPortalCriteria(IssPortalFile issPortalFile, IssFileParserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IssFileParser> specification = createSpecification(criteria);
        return issFileParserRepository.findAllByIssPortalFile(issPortalFile, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IssFileParserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IssFileParser> specification = createSpecification(criteria);
        return issFileParserRepository.count(specification);
    }

    /**
     * Function to convert {@link IssFileParserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IssFileParser> createSpecification(IssFileParserCriteria criteria) {
        Specification<IssFileParser> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IssFileParser_.id));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), IssFileParser_.financialYear));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), IssFileParser_.bankName));
            }
            if (criteria.getBankCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankCode(), IssFileParser_.bankCode));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), IssFileParser_.branchName));
            }
            if (criteria.getBranchCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchCode(), IssFileParser_.branchCode));
            }
            if (criteria.getSchemeWiseBranchCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSchemeWiseBranchCode(), IssFileParser_.schemeWiseBranchCode));
            }
            if (criteria.getIfsc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfsc(), IssFileParser_.ifsc));
            }
            if (criteria.getLoanAccountNumberkcc() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLoanAccountNumberkcc(), IssFileParser_.loanAccountNumberkcc));
            }
            if (criteria.getFarmerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerName(), IssFileParser_.farmerName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), IssFileParser_.gender));
            }
            if (criteria.getAadharNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharNumber(), IssFileParser_.aadharNumber));
            }
            if (criteria.getDateofBirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateofBirth(), IssFileParser_.dateofBirth));
            }
            if (criteria.getAgeAtTimeOfSanction() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAgeAtTimeOfSanction(), IssFileParser_.ageAtTimeOfSanction));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), IssFileParser_.mobileNo));
            }
            if (criteria.getFarmersCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmersCategory(), IssFileParser_.farmersCategory));
            }
            if (criteria.getFarmerType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerType(), IssFileParser_.farmerType));
            }
            if (criteria.getSocialCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSocialCategory(), IssFileParser_.socialCategory));
            }
            if (criteria.getRelativeType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelativeType(), IssFileParser_.relativeType));
            }
            if (criteria.getRelativeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelativeName(), IssFileParser_.relativeName));
            }
            if (criteria.getStateName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateName(), IssFileParser_.stateName));
            }
            if (criteria.getStateCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateCode(), IssFileParser_.stateCode));
            }
            if (criteria.getDistrictName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrictName(), IssFileParser_.districtName));
            }
            if (criteria.getDistrictCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrictCode(), IssFileParser_.districtCode));
            }
            if (criteria.getBlockCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBlockCode(), IssFileParser_.blockCode));
            }
            if (criteria.getBlockName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBlockName(), IssFileParser_.blockName));
            }
            if (criteria.getVillageCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageCode(), IssFileParser_.villageCode));
            }
            if (criteria.getVillageName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageName(), IssFileParser_.villageName));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), IssFileParser_.address));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPinCode(), IssFileParser_.pinCode));
            }
            if (criteria.getAccountType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountType(), IssFileParser_.accountType));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), IssFileParser_.accountNumber));
            }
            if (criteria.getPacsName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsName(), IssFileParser_.pacsName));
            }
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsNumber(), IssFileParser_.pacsNumber));
            }
            if (criteria.getAccountHolderType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccountHolderType(), IssFileParser_.accountHolderType));
            }
            if (criteria.getPrimaryOccupation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrimaryOccupation(), IssFileParser_.primaryOccupation));
            }
            if (criteria.getLoanSactionDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanSactionDate(), IssFileParser_.loanSactionDate));
            }
            if (criteria.getLoanSanctionAmount() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLoanSanctionAmount(), IssFileParser_.loanSanctionAmount));
            }
            if (criteria.getTenureOFLoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenureOFLoan(), IssFileParser_.tenureOFLoan));
            }
            if (criteria.getDateOfOverDuePayment() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDateOfOverDuePayment(), IssFileParser_.dateOfOverDuePayment));
            }
            if (criteria.getCropName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropName(), IssFileParser_.cropName));
            }
            if (criteria.getSurveyNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurveyNo(), IssFileParser_.surveyNo));
            }
            if (criteria.getSatBaraSubsurveyNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSatBaraSubsurveyNo(), IssFileParser_.satBaraSubsurveyNo));
            }
            if (criteria.getSeasonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSeasonName(), IssFileParser_.seasonName));
            }
            if (criteria.getAreaHect() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaHect(), IssFileParser_.areaHect));
            }
            if (criteria.getLandType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandType(), IssFileParser_.landType));
            }
            if (criteria.getDisbursementDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDisbursementDate(), IssFileParser_.disbursementDate));
            }
            if (criteria.getDisburseAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisburseAmount(), IssFileParser_.disburseAmount));
            }
            if (criteria.getMaturityLoanDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMaturityLoanDate(), IssFileParser_.maturityLoanDate));
            }
            if (criteria.getRecoveryAmountPrinciple() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getRecoveryAmountPrinciple(), IssFileParser_.recoveryAmountPrinciple)
                    );
            }
            if (criteria.getRecoveryAmountInterest() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getRecoveryAmountInterest(), IssFileParser_.recoveryAmountInterest)
                    );
            }
            if (criteria.getRecoveryDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecoveryDate(), IssFileParser_.recoveryDate));
            }
            if (criteria.getIssPortalFileId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIssPortalFileId(),
                            root -> root.join(IssFileParser_.issPortalFile, JoinType.LEFT).get(IssPortalFile_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
