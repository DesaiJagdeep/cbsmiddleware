package com.cbs.middleware.service;

import com.cbs.middleware.config.Constants;
// for static metamodels
import com.cbs.middleware.domain.BatchTransaction;
import com.cbs.middleware.domain.BatchTransactionMapper;
import com.cbs.middleware.domain.BatchTransaction_;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.BatchTransactionRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.service.criteria.BatchTransactionCriteria;
import com.cbs.middleware.web.rest.errors.ForbiddenAuthRequestAlertException;
import com.cbs.middleware.web.rest.utility.BankBranchPacksCodeGet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

/**
 * Service for executing complex queries for {@link BatchTransaction} entities
 * in the database. The main input is a {@link BatchTransactionCriteria} which
 * gets converted to {@link Specification}, in a way that all the filters must
 * apply. It returns a {@link List} of {@link BatchTransaction} or a
 * {@link Page} of {@link BatchTransaction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BatchTransactionQueryService extends QueryService<BatchTransaction> {

    private final Logger log = LoggerFactory.getLogger(BatchTransactionQueryService.class);

    private final BatchTransactionRepository batchTransactionRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    IssPortalFileRepository issPortalFileRepository;

    @Autowired
    BankBranchPacksCodeGet bankBranchPacksCodeGet;

    public BatchTransactionQueryService(BatchTransactionRepository batchTransactionRepository) {
        this.batchTransactionRepository = batchTransactionRepository;
    }

    /**
     * Return a {@link List} of {@link BatchTransaction} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BatchTransaction> findByCriteria(BatchTransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BatchTransaction> specification = createSpecification(criteria);
        return batchTransactionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BatchTransaction} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BatchTransaction> findByCriteria(BatchTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BatchTransaction> specification = createSpecification(criteria);
        return batchTransactionRepository.findAll(specification, page);
    }

    @Transactional(readOnly = true)
    public List<BatchTransactionMapper> findByCriteriaByMapper(BatchTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);

		List<BatchTransactionMapper> batchTransactionMapperList = new ArrayList<>();
		List<BatchTransaction> content = new ArrayList<BatchTransaction>();
		Specification<BatchTransaction> specification = createSpecification(criteria);
		Page<BatchTransaction> findAll = batchTransactionRepository.findAll(specification, page);
		
		if(findAll.isEmpty())
		{
			//return error
		}
		
		content = findAll.getContent();

		
        

        for (BatchTransaction batchTransaction : content) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                JSONObject jsonObject = new JSONObject(batchTransaction);
                BatchTransactionMapper batchTransactionMapper = objectMapper.readValue(jsonObject.toString(), BatchTransactionMapper.class);

                Optional<IssPortalFile> findIssPortalFileById = issPortalFileRepository.findById(
                    applicationRepository.findIssFilePortalIdByBatchId(batchTransaction.getBatchId()).get(0)
                );

                if (findIssPortalFileById.isPresent()) {
                    IssPortalFile issPortalFile = findIssPortalFileById.get();
                    batchTransactionMapper.setIssPortalId(issPortalFile.getId());
                    batchTransactionMapper.setFileName(issPortalFile.getFileName());
                    batchTransactionMapper.setBranchName(issPortalFile.getBranchName());
                    batchTransactionMapper.setPacksName(issPortalFile.getPacsName());

                    batchTransactionMapperList.add(batchTransactionMapper);

                    
                }
            } catch (Exception e) {
                log.error("Error in portal responce api: " + e);
            }
        }
        return batchTransactionMapperList;
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BatchTransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BatchTransaction> specification = createSpecification(criteria);
        return batchTransactionRepository.count(specification);
    }

    /**
     * Function to convert {@link BatchTransactionCriteria} to a
     * {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BatchTransaction> createSpecification(BatchTransactionCriteria criteria) {
        Specification<BatchTransaction> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BatchTransaction_.id));
            }
            
            
			if (criteria.getPacksCode() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getPacksCode(), BatchTransaction_.packsCode));
			}

			else if (criteria.getSchemeWiseBranchCode() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getSchemeWiseBranchCode(),
						BatchTransaction_.schemeWiseBranchCode));
			}

			else if (criteria.getBankCode() != null) {
				System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+criteria.getBankCode() );
				specification = specification
						.and(buildRangeSpecification(criteria.getBankCode(), BatchTransaction_.bankCode));
			}
            
           
            
            
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), BatchTransaction_.status));
            }
            if (criteria.getBatchDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchDetails(), BatchTransaction_.batchDetails));
            }
            if (criteria.getApplicationCount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getApplicationCount(), BatchTransaction_.applicationCount));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), BatchTransaction_.notes));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchId(), BatchTransaction_.batchId));
            }
            if (criteria.getBatchAckId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchAckId(), BatchTransaction_.batchAckId));
            }
        }
        return specification;
    }
}
