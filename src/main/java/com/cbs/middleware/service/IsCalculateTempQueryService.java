package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.service.criteria.IsCalculateTempCriteria;
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
 * Service for executing complex queries for {@link IsCalculateTemp} entities in the database.
 * The main input is a {@link IsCalculateTempCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IsCalculateTemp} or a {@link Page} of {@link IsCalculateTemp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IsCalculateTempQueryService extends QueryService<IsCalculateTemp> {

    private final Logger log = LoggerFactory.getLogger(IsCalculateTempQueryService.class);

    private final IsCalculateTempRepository isCalculateTempRepository;

    public IsCalculateTempQueryService(IsCalculateTempRepository isCalculateTempRepository) {
        this.isCalculateTempRepository = isCalculateTempRepository;
    }

    /**
     * Return a {@link List} of {@link IsCalculateTemp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IsCalculateTemp> findByCriteria(IsCalculateTempCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IsCalculateTemp> specification = createSpecification(criteria);
        return isCalculateTempRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link IsCalculateTemp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IsCalculateTemp> findByCriteria(IsCalculateTempCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IsCalculateTemp> specification = createSpecification(criteria);
        return isCalculateTempRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IsCalculateTempCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IsCalculateTemp> specification = createSpecification(criteria);
        return isCalculateTempRepository.count(specification);
    }

    /**
     * Function to convert {@link IsCalculateTempCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IsCalculateTemp> createSpecification(IsCalculateTempCriteria criteria) {
        Specification<IsCalculateTemp> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IsCalculateTemp_.id));
            }
            if (criteria.getSerialNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSerialNo(), IsCalculateTemp_.serialNo));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), IsCalculateTemp_.financialYear));
            }
            if (criteria.getIssFileParserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssFileParserId(), IsCalculateTemp_.issFileParserId));
            }
            if (criteria.getBranchCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchCode(), IsCalculateTemp_.branchCode));
            }
            if (criteria.getPacsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsNumber(), IsCalculateTemp_.pacsNumber));
            }
            if (criteria.getLoanAccountNumberKcc() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLoanAccountNumberKcc(), IsCalculateTemp_.loanAccountNumberKcc));
            }
            if (criteria.getFarmerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerName(), IsCalculateTemp_.farmerName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), IsCalculateTemp_.gender));
            }
            if (criteria.getAadharNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharNumber(), IsCalculateTemp_.aadharNumber));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), IsCalculateTemp_.mobileNo));
            }
            if (criteria.getFarmerType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFarmerType(), IsCalculateTemp_.farmerType));
            }
            if (criteria.getSocialCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSocialCategory(), IsCalculateTemp_.socialCategory));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), IsCalculateTemp_.accountNumber));
            }
//            if (criteria.getLoanSanctionDate() != null) {
//                specification =
//                    specification.and(buildStringSpecification(criteria.getLoanSanctionDate(), IsCalculateTemp_.loanSanctionDate));
//            }
//            if (criteria.getLoanSanctionAmount() != null) {
//                specification =
//                    specification.and(buildStringSpecification(criteria.getLoanSanctionAmount(), IsCalculateTemp_.loanSanctionAmount));
//            }
//            if (criteria.getDisbursementDate() != null) {
//                specification =
//                    specification.and(buildStringSpecification(criteria.getDisbursementDate(), IsCalculateTemp_.disbursementDate));
//            }
//            if (criteria.getDisburseAmount() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getDisburseAmount(), IsCalculateTemp_.disburseAmount));
//            }
//            if (criteria.getMaturityLoanDate() != null) {
//                specification =
//                    specification.and(buildStringSpecification(criteria.getMaturityLoanDate(), IsCalculateTemp_.maturityLoanDate));
//            }
//            if (criteria.getBankDate() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getBankDate(), IsCalculateTemp_.bankDate));
//            }
//            if (criteria.getCropName() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getCropName(), IsCalculateTemp_.cropName));
//            }
//            if (criteria.getRecoveryAmount() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getRecoveryAmount(), IsCalculateTemp_.recoveryAmount));
//            }
//            if (criteria.getRecoveryInterest() != null) {
//                specification =
//                    specification.and(buildStringSpecification(criteria.getRecoveryInterest(), IsCalculateTemp_.recoveryInterest));
//            }
//            if (criteria.getRecoveryDate() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getRecoveryDate(), IsCalculateTemp_.recoveryDate));
//            }
//            if (criteria.getBalanceAmount() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getBalanceAmount(), IsCalculateTemp_.balanceAmount));
//            }
            if (criteria.getPrevDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrevDays(), IsCalculateTemp_.prevDays));
            }
            if (criteria.getPresDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPresDays(), IsCalculateTemp_.presDays));
            }
            if (criteria.getActualDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualDays(), IsCalculateTemp_.actualDays));
            }
            if (criteria.getnProd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getnProd(), IsCalculateTemp_.nProd));
            }
//            if (criteria.getProductAmount() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getProductAmount(), IsCalculateTemp_.productAmount));
//            }
//            if (criteria.getProductBank() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getProductBank(), IsCalculateTemp_.productBank));
//            }
//            if (criteria.getProductAbh3Lakh() != null) {
//                specification =
//                    specification.and(buildStringSpecification(criteria.getProductAbh3Lakh(), IsCalculateTemp_.productAbh3Lakh));
//            }
//            if (criteria.getInterestFirst15() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getInterestFirst15(), IsCalculateTemp_.interestFirst15));
//            }
//            if (criteria.getInterestFirst25() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getInterestFirst25(), IsCalculateTemp_.interestFirst25));
//            }
            if (criteria.getInterestSecond15() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInterestSecond15(), IsCalculateTemp_.interestSecond15));
            }
            if (criteria.getInterestSecond25() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInterestSecond25(), IsCalculateTemp_.interestSecond25));
            }
            if (criteria.getInterestStateFirst3() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInterestStateFirst3(), IsCalculateTemp_.interestStateFirst3));
            }
            if (criteria.getInterestStateSecond3() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInterestStateSecond3(), IsCalculateTemp_.interestStateSecond3));
            }
//            if (criteria.getInterestFirstAbh3() != null) {
//                specification =
//                    specification.and(buildRangeSpecification(criteria.getInterestFirstAbh3(), IsCalculateTemp_.interestFirstAbh3));
//            }
//            if (criteria.getInterestSecondAbh3() != null) {
//                specification =
//                    specification.and(buildRangeSpecification(criteria.getInterestSecondAbh3(), IsCalculateTemp_.interestSecondAbh3));
//            }
//            if (criteria.getInterestAbove3Lakh() != null) {
//                specification =
//                    specification.and(buildRangeSpecification(criteria.getInterestAbove3Lakh(), IsCalculateTemp_.interestAbove3Lakh));
//            }
//            if (criteria.getPanjabraoInt3() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getPanjabraoInt3(), IsCalculateTemp_.panjabraoInt3));
//            }
//            if (criteria.getIsRecover() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getIsRecover(), IsCalculateTemp_.isRecover));
//            }
//            if (criteria.getAbh3LakhAmt() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getAbh3LakhAmt(), IsCalculateTemp_.abh3LakhAmt));
//            }
//            if (criteria.getUpto50000() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getUpto50000(), IsCalculateTemp_.upto50000));
//            }
        }
        return specification;
    }

}
