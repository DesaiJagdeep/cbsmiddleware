package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.ApplicationLogHistory;
import com.cbs.middleware.repository.ApplicationLogHistoryRepository;
import com.cbs.middleware.service.ApplicationLogHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationLogHistory}.
 */
@Service
@Transactional
public class ApplicationLogHistoryServiceImpl implements ApplicationLogHistoryService {

    private final Logger log = LoggerFactory.getLogger(ApplicationLogHistoryServiceImpl.class);

    private final ApplicationLogHistoryRepository applicationLogHistoryRepository;

    public ApplicationLogHistoryServiceImpl(ApplicationLogHistoryRepository applicationLogHistoryRepository) {
        this.applicationLogHistoryRepository = applicationLogHistoryRepository;
    }

    @Override
    public ApplicationLogHistory save(ApplicationLogHistory applicationLogHistory) {
        log.debug("Request to save ApplicationLogHistory : {}", applicationLogHistory);
        return applicationLogHistoryRepository.save(applicationLogHistory);
    }

    @Override
    public ApplicationLogHistory update(ApplicationLogHistory applicationLogHistory) {
        log.debug("Request to update ApplicationLogHistory : {}", applicationLogHistory);
        return applicationLogHistoryRepository.save(applicationLogHistory);
    }

    @Override
    public Optional<ApplicationLogHistory> partialUpdate(ApplicationLogHistory applicationLogHistory) {
        log.debug("Request to partially update ApplicationLogHistory : {}", applicationLogHistory);

        return applicationLogHistoryRepository
            .findById(applicationLogHistory.getId())
            .map(existingApplicationLogHistory -> {
                if (applicationLogHistory.getErrorType() != null) {
                    existingApplicationLogHistory.setErrorType(applicationLogHistory.getErrorType());
                }
                if (applicationLogHistory.getErrorCode() != null) {
                    existingApplicationLogHistory.setErrorCode(applicationLogHistory.getErrorCode());
                }
                if (applicationLogHistory.getErrorMessage() != null) {
                    existingApplicationLogHistory.setErrorMessage(applicationLogHistory.getErrorMessage());
                }
                if (applicationLogHistory.getRowNumber() != null) {
                    existingApplicationLogHistory.setRowNumber(applicationLogHistory.getRowNumber());
                }
                if (applicationLogHistory.getColumnNumber() != null) {
                    existingApplicationLogHistory.setColumnNumber(applicationLogHistory.getColumnNumber());
                }
                if (applicationLogHistory.getSevierity() != null) {
                    existingApplicationLogHistory.setSevierity(applicationLogHistory.getSevierity());
                }
                if (applicationLogHistory.getExpectedSolution() != null) {
                    existingApplicationLogHistory.setExpectedSolution(applicationLogHistory.getExpectedSolution());
                }
                if (applicationLogHistory.getStatus() != null) {
                    existingApplicationLogHistory.setStatus(applicationLogHistory.getStatus());
                }

                return existingApplicationLogHistory;
            })
            .map(applicationLogHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationLogHistory> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationLogHistories");
        return applicationLogHistoryRepository.findAll(pageable);
    }

    public Page<ApplicationLogHistory> findAllWithEagerRelationships(Pageable pageable) {
        return applicationLogHistoryRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationLogHistory> findOne(Long id) {
        log.debug("Request to get ApplicationLogHistory : {}", id);
        return applicationLogHistoryRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationLogHistory : {}", id);
        applicationLogHistoryRepository.deleteById(id);
    }
}
