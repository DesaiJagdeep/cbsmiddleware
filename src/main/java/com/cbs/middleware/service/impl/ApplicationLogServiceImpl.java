package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.repository.ApplicationLogRepository;
import com.cbs.middleware.service.ApplicationLogService;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApplicationLog}.
 */
@Service
@Transactional
public class ApplicationLogServiceImpl implements ApplicationLogService {

    private final Logger log = LoggerFactory.getLogger(ApplicationLogServiceImpl.class);

    private final ApplicationLogRepository applicationLogRepository;

    public ApplicationLogServiceImpl(ApplicationLogRepository applicationLogRepository) {
        this.applicationLogRepository = applicationLogRepository;
    }

    @Override
    public ApplicationLog save(ApplicationLog applicationLog) {
        log.debug("Request to save ApplicationLog : {}", applicationLog);
        return applicationLogRepository.save(applicationLog);
    }

    @Override
    public ApplicationLog update(ApplicationLog applicationLog) {
        //  log.debug("Request to update ApplicationLog : {}", applicationLog);
        return applicationLogRepository.save(applicationLog);
    }

    @Override
    public Optional<ApplicationLog> partialUpdate(ApplicationLog applicationLog) {
        log.debug("Request to partially update ApplicationLog : {}", applicationLog);

        return applicationLogRepository
            .findById(applicationLog.getId())
            .map(existingApplicationLog -> {
                if (applicationLog.getErrorType() != null) {
                    existingApplicationLog.setErrorType(applicationLog.getErrorType());
                }
                if (applicationLog.getErrorCode() != null) {
                    existingApplicationLog.setErrorCode(applicationLog.getErrorCode());
                }
                if (applicationLog.getErrorMessage() != null) {
                    existingApplicationLog.setErrorMessage(applicationLog.getErrorMessage());
                }
                if (applicationLog.getColumnNumber() != null) {
                    existingApplicationLog.setColumnNumber(applicationLog.getColumnNumber());
                }
                if (applicationLog.getSevierity() != null) {
                    existingApplicationLog.setSevierity(applicationLog.getSevierity());
                }
                if (applicationLog.getExpectedSolution() != null) {
                    existingApplicationLog.setExpectedSolution(applicationLog.getExpectedSolution());
                }
                if (applicationLog.getStatus() != null) {
                    existingApplicationLog.setStatus(applicationLog.getStatus());
                }
                if (applicationLog.getRowNumber() != null) {
                    existingApplicationLog.setRowNumber(applicationLog.getRowNumber());
                }
                if (applicationLog.getBatchId() != null) {
                    existingApplicationLog.setBatchId(applicationLog.getBatchId());
                }

                return existingApplicationLog;
            })
            .map(applicationLogRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationLog> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationLogs");
        return applicationLogRepository.findAll(pageable);
    }

    public Page<ApplicationLog> findAllWithEagerRelationships(Pageable pageable) {
        return applicationLogRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationLog> findOne(Long id) {
        log.debug("Request to get ApplicationLog : {}", id);
        return applicationLogRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationLog : {}", id);
        applicationLogRepository.deleteById(id);
    }

    @Override
    public List<ApplicationLog> findRejectedApplications() {
        return applicationLogRepository.findAllByKCCStatus();

    }
}
