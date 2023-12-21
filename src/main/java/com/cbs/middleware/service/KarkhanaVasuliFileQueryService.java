package com.cbs.middleware.service;

import com.cbs.middleware.domain.*; // for static metamodels
import com.cbs.middleware.domain.KarkhanaVasuliFile;
import com.cbs.middleware.repository.KarkhanaVasuliFileRepository;
import com.cbs.middleware.service.criteria.KarkhanaVasuliFileCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link KarkhanaVasuliFile} entities in the database.
 * The main input is a {@link KarkhanaVasuliFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KarkhanaVasuliFile} or a {@link Page} of {@link KarkhanaVasuliFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KarkhanaVasuliFileQueryService extends QueryService<KarkhanaVasuliFile> {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliFileQueryService.class);

    private final KarkhanaVasuliFileRepository karkhanaVasuliFileRepository;

    public KarkhanaVasuliFileQueryService(KarkhanaVasuliFileRepository karkhanaVasuliFileRepository) {
        this.karkhanaVasuliFileRepository = karkhanaVasuliFileRepository;
    }

    /**
     * Return a {@link List} of {@link KarkhanaVasuliFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KarkhanaVasuliFile> findByCriteria(KarkhanaVasuliFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KarkhanaVasuliFile> specification = createSpecification(criteria);
        return karkhanaVasuliFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KarkhanaVasuliFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KarkhanaVasuliFile> findByCriteria(KarkhanaVasuliFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KarkhanaVasuliFile> specification = createSpecification(criteria);
        return karkhanaVasuliFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KarkhanaVasuliFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KarkhanaVasuliFile> specification = createSpecification(criteria);
        return karkhanaVasuliFileRepository.count(specification);
    }

    /**
     * Function to convert {@link KarkhanaVasuliFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KarkhanaVasuliFile> createSpecification(KarkhanaVasuliFileCriteria criteria) {
        Specification<KarkhanaVasuliFile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KarkhanaVasuliFile_.id));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), KarkhanaVasuliFile_.fileName));
            }
            if (criteria.getUniqueFileName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getUniqueFileName(), KarkhanaVasuliFile_.uniqueFileName));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), KarkhanaVasuliFile_.address));
            }
            if (criteria.getAddressMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressMr(), KarkhanaVasuliFile_.addressMr));
            }
            if (criteria.getHangam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHangam(), KarkhanaVasuliFile_.hangam));
            }
            if (criteria.getHangamMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHangamMr(), KarkhanaVasuliFile_.hangamMr));
            }
            if (criteria.getFactoryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFactoryName(), KarkhanaVasuliFile_.factoryName));
            }
            if (criteria.getFactoryNameMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFactoryNameMr(), KarkhanaVasuliFile_.factoryNameMr));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), KarkhanaVasuliFile_.totalAmount));
            }
            if (criteria.getTotalAmountMr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalAmountMr(), KarkhanaVasuliFile_.totalAmountMr));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), KarkhanaVasuliFile_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), KarkhanaVasuliFile_.toDate));
            }
            if (criteria.getBranchCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBranchCode(), KarkhanaVasuliFile_.branchCode));
            }
            if (criteria.getPacsName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPacsName(), KarkhanaVasuliFile_.pacsName));
            }
            if (criteria.getFactoryMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFactoryMasterId(),
                            root -> root.join(KarkhanaVasuliFile_.factoryMaster, JoinType.LEFT).get(FactoryMaster_.id)
                        )
                    );
            }
            if (criteria.getKarkhanaVasuliRecordsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKarkhanaVasuliRecordsId(),
                            root -> root.join(KarkhanaVasuliFile_.karkhanaVasuliRecords, JoinType.LEFT).get(KarkhanaVasuliRecords_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
