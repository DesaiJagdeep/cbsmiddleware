package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KarkhanaVasuliRecords;
import com.cbs.middleware.repository.KarkhanaVasuliRecordsRepository;
import com.cbs.middleware.service.criteria.KarkhanaVasuliRecordsCriteria;
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
 * Service for executing complex queries for {@link KarkhanaVasuliRecords} entities in the database.
 * The main input is a {@link KarkhanaVasuliRecordsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KarkhanaVasuliRecords} or a {@link Page} of {@link KarkhanaVasuliRecords} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KarkhanaVasuliRecordsQueryService extends QueryService<KarkhanaVasuliRecords> {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliRecordsQueryService.class);

    private final KarkhanaVasuliRecordsRepository karkhanaVasuliRecordsRepository;

    public KarkhanaVasuliRecordsQueryService(KarkhanaVasuliRecordsRepository karkhanaVasuliRecordsRepository) {
        this.karkhanaVasuliRecordsRepository = karkhanaVasuliRecordsRepository;
    }

    /**
     * Return a {@link List} of {@link KarkhanaVasuliRecords} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KarkhanaVasuliRecords> findByCriteria(KarkhanaVasuliRecordsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KarkhanaVasuliRecords> specification = createSpecification(criteria);
        return karkhanaVasuliRecordsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KarkhanaVasuliRecords} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KarkhanaVasuliRecords> findByCriteria(KarkhanaVasuliRecordsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KarkhanaVasuliRecords> specification = createSpecification(criteria);
        return karkhanaVasuliRecordsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KarkhanaVasuliRecordsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KarkhanaVasuliRecords> specification = createSpecification(criteria);
        return karkhanaVasuliRecordsRepository.count(specification);
    }

    /**
     * Function to convert {@link KarkhanaVasuliRecordsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KarkhanaVasuliRecords> createSpecification(KarkhanaVasuliRecordsCriteria criteria) {
        Specification<KarkhanaVasuliRecords> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KarkhanaVasuliRecords_.id));
            }
            if (criteria.getFactoryMemberCode() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFactoryMemberCode(), KarkhanaVasuliRecords_.factoryMemberCode));
            }
            if (criteria.getKarkhanaMemberCodeMr() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getKarkhanaMemberCodeMr(), KarkhanaVasuliRecords_.karkhanaMemberCodeMr)
                    );
            }
            if (criteria.getMemberName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberName(), KarkhanaVasuliRecords_.memberName));
            }
            if (criteria.getMemberNameMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMemberNameMr(), KarkhanaVasuliRecords_.memberNameMr));
            }
            if (criteria.getVillageName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageName(), KarkhanaVasuliRecords_.villageName));
            }
            if (criteria.getVillageNameMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getVillageNameMr(), KarkhanaVasuliRecords_.villageNameMr));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), KarkhanaVasuliRecords_.amount));
            }
            if (criteria.getAmountMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmountMr(), KarkhanaVasuliRecords_.amountMr));
            }
            if (criteria.getSavingAccNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSavingAccNo(), KarkhanaVasuliRecords_.savingAccNo));
            }
            if (criteria.getSavingAccNoMr() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSavingAccNoMr(), KarkhanaVasuliRecords_.savingAccNoMr));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), KarkhanaVasuliRecords_.status));
            }
            if (criteria.getKarkhanaVasuliFileId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKarkhanaVasuliFileId(),
                            root -> root.join(KarkhanaVasuliRecords_.karkhanaVasuliFile, JoinType.LEFT).get(KarkhanaVasuliFile_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
