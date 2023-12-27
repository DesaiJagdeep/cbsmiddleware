package com.cbs.middleware.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.Application;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.ApplicationLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.domain.IssPortalFile_;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.service.criteria.IssPortalFileCriteria;

import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service for executing complex queries for {@link IssPortalFile} entities in
 * the database. The main input is a {@link IssPortalFileCriteria} which gets
 * converted to {@link Specification}, in a way that all the filters must apply.
 * It returns a {@link List} of {@link IssPortalFile} or a {@link Page} of
 * {@link IssPortalFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IssPortalFileQueryService extends QueryService<IssPortalFile> {

    private final Logger log = LoggerFactory.getLogger(IssPortalFileQueryService.class);

    private final IssPortalFileRepository issPortalFileRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationQueryService applicationQueryService;
    private final ApplicationLogRepository applicationLogRepository;

    public IssPortalFileQueryService(IssPortalFileRepository issPortalFileRepository,
                                     ApplicationLogRepository applicationLogRepository) {
        this.issPortalFileRepository = issPortalFileRepository;
        this.applicationLogRepository = applicationLogRepository;
    }

    /**
     * Return a {@link List} of {@link IssPortalFile} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IssPortalFile> findByCriteria(IssPortalFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IssPortalFile> specification = createSpecification(criteria);
        return issPortalFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link IssPortalFile} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IssPortalFile> findByCriteria(IssPortalFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IssPortalFile> specification = createSpecification(criteria);
        return issPortalFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IssPortalFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IssPortalFile> specification = createSpecification(criteria);
        return issPortalFileRepository.count(specification);
    }

    /**
     * Function to convert {@link IssPortalFileCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IssPortalFile> createSpecification(IssPortalFileCriteria criteria) {
        Specification<IssPortalFile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IssPortalFile_.id));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), IssPortalFile_.fileName));
            }
            if (criteria.getFileExtension() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileExtension(), IssPortalFile_.fileExtension));
            }
            if (criteria.getSchemeWiseBranchCode() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSchemeWiseBranchCode(), IssPortalFile_.schemeWiseBranchCode));
            }
            if (criteria.getFinancialYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinancialYear(), IssPortalFile_.financialYear));
            }
            if (criteria.getFromDisbursementDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFromDisbursementDate(), IssPortalFile_.fromDisbursementDate));
            }
            if (criteria.getToDisbursementDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getToDisbursementDate(), IssPortalFile_.toDisbursementDate));
            }
            if (criteria.getPacsCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPacsCode(), IssPortalFile_.pacsCode));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), IssPortalFile_.status));

            } if (criteria.getpacsName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getpacsName(), IssPortalFile_.pacsName));
            }
            if (criteria.getApplicationCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationCount(), IssPortalFile_.applicationCount));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), IssPortalFile_.notes));
            }
        }
        return specification;
    }

    public Page<IssPortalFile> findByCriteriaCount(IssPortalFileCriteria criteria, Pageable pageable) {
        final Specification<IssPortalFile> specification = createSpecification(criteria);

        List<IssPortalFile> issPortalFileMapperList = new ArrayList<>();
        Page<IssPortalFile> findAll = issPortalFileRepository.findAll(specification, pageable);
        if(!findAll.isEmpty())
        {
        for (IssPortalFile issPortalFile : findAll.getContent()) {

            Long appSubmitedToKccCount = applicationRepository.countByIssFilePortalIdAndBatchIdNotNull(issPortalFile.getId());

            List<Application> applicationList = applicationRepository.findAllByBatchIdAndApplicationStatusAndIssFilePortalId(
                null,
                Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD,
                issPortalFile.getId()
            );
            issPortalFile.setAppPendingToSubmitCount(Long.valueOf(applicationList.size()));

            Long applicationCriteriaSuccessRecord = applicationRepository.countByIssFilePortalIdAndApplicationStatus(
                issPortalFile.getId(),
                1
            );

            issPortalFile.setAppAcceptedByKccCount(applicationCriteriaSuccessRecord);

            //Long failRecord = applicationRepository.countByIssFilePortalIdAndApplicationStatus(issPortalFile.getId(), 0);
            Long failRecord = applicationLogRepository.countByKccError(issPortalFile.getId());
            issPortalFile.setKccErrorRecordCount(failRecord.intValue());

            Long  validationError= applicationLogRepository.countByValidationError(issPortalFile.getId());
            issPortalFile.setErrorRecordCount(validationError.intValue());

            issPortalFileMapperList.add(issPortalFile);

        }
        }

        return findAll;
    }

    public Page<IssPortalFile> findByCriteriaCountBySchemeWiseBranchCode(
        Long schemeWiseBranchCode,
        IssPortalFileCriteria criteria,
        Pageable pageable
    ) {
        LongFilter lf = new LongFilter();
        lf.setEquals(schemeWiseBranchCode);
        criteria.setSchemeWiseBranchCode(lf);

        final Specification<IssPortalFile> specification = createSpecification(criteria);

        List<IssPortalFile> issPortalFileMapperList = new ArrayList<>();
        Page<IssPortalFile> findAll = issPortalFileRepository.findAll(specification, pageable);

        if(!findAll.isEmpty())
        {
        for (IssPortalFile issPortalFile : findAll.getContent()) {
            List<Application> applicationList = applicationRepository.findAllByBatchIdAndApplicationStatusAndIssFilePortalId(
                null,
                Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD,
                issPortalFile.getId()
            );
            issPortalFile.setAppPendingToSubmitCount((long) applicationList.size());

            Long appSubmitedToKccCount = applicationRepository.countByIssFilePortalIdAndBatchIdNotNull(issPortalFile.getId());
            issPortalFile.setAppSubmitedToKccCount(appSubmitedToKccCount);

            Long applicationCriteriaSuccessRecord = applicationRepository.countByIssFilePortalIdAndApplicationStatus(
                issPortalFile.getId(),
                1
            );

            issPortalFile.setAppAcceptedByKccCount(applicationCriteriaSuccessRecord);

            //Long failRecord = applicationRepository.countByIssFilePortalIdAndApplicationStatus(issPortalFile.getId(), 0);
            Long failRecord = applicationLogRepository.countByKccError(issPortalFile.getId());
            Long  validationError= applicationLogRepository.countByValidationError(issPortalFile.getId());
            issPortalFile.setErrorRecordCount(validationError.intValue());
            issPortalFile.setKccErrorRecordCount(failRecord.intValue());
            issPortalFileMapperList.add(issPortalFile);
        }
        }
        return findAll;
    }

    public Page<IssPortalFile> findByCriteriaCountByPacsCode(Long pacsCode, Pageable pageable) {
        List<IssPortalFile> issPortalFileMapperList = new ArrayList<>();
        Page<IssPortalFile> findAll = issPortalFileRepository.findAllByPacsCode(pacsCode, pageable);

        if(!findAll.isEmpty())
        {
        	 for (IssPortalFile issPortalFile : findAll.getContent()) {

                 List<Application> applicationList = applicationRepository.findAllByBatchIdAndApplicationStatusAndIssFilePortalId(
                     null,
                     Constants.APPLICATION_INITIAL_STATUS_FOR_LOAD,
                     issPortalFile.getId()
                 );
                 issPortalFile.setAppPendingToSubmitCount(Long.valueOf(applicationList.size()));

                 Long appSubmitedToKccCount = applicationRepository.countByIssFilePortalIdAndBatchIdNotNull(issPortalFile.getId());
                 issPortalFile.setAppSubmitedToKccCount(appSubmitedToKccCount);

                 Long applicationCriteriaSuccessRecord = applicationRepository.countByIssFilePortalIdAndApplicationStatus(
                     issPortalFile.getId(),
                     1
                 );

                 issPortalFile.setAppAcceptedByKccCount(applicationCriteriaSuccessRecord);

                 //Long failRecord = applicationRepository.countByIssFilePortalIdAndApplicationStatus(issPortalFile.getId(), 0);
                 Long failRecord=applicationLogRepository.countByKccError(issPortalFile.getId());
                 issPortalFile.setKccErrorRecordCount(failRecord.intValue());

                 Long  validationError= applicationLogRepository.countByValidationError(issPortalFile.getId());
                 issPortalFile.setErrorRecordCount(validationError.intValue());


                 issPortalFileMapperList.add(issPortalFile);
             }
        }


        return findAll;
    }
}
