package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.service.IssPortalFileService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IssPortalFile}.
 */
@Service
@Transactional
public class IssPortalFileServiceImpl implements IssPortalFileService {

    private final Logger log = LoggerFactory.getLogger(IssPortalFileServiceImpl.class);

    private final IssPortalFileRepository issPortalFileRepository;

    public IssPortalFileServiceImpl(IssPortalFileRepository issPortalFileRepository) {
        this.issPortalFileRepository = issPortalFileRepository;
    }

    @Override
    public IssPortalFile save(IssPortalFile issPortalFile) {
        // log.debug("Request to save IssPortalFile : {}", issPortalFile);
        return issPortalFileRepository.save(issPortalFile);
    }

    @Override
    public IssPortalFile update(IssPortalFile issPortalFile) {
        // log.debug("Request to update IssPortalFile : {}", issPortalFile);
        return issPortalFileRepository.save(issPortalFile);
    }

    @Override
    public Optional<IssPortalFile> partialUpdate(IssPortalFile issPortalFile) {
        log.debug("Request to partially update IssPortalFile : {}", issPortalFile);

        return issPortalFileRepository
            .findById(issPortalFile.getId())
            .map(existingIssPortalFile -> {
                if (issPortalFile.getFileName() != null) {
                    existingIssPortalFile.setFileName(issPortalFile.getFileName());
                }
                if (issPortalFile.getFileExtension() != null) {
                    existingIssPortalFile.setFileExtension(issPortalFile.getFileExtension());
                }
                if (issPortalFile.getSchemeWiseBranchCode() != null) {
                    existingIssPortalFile.setSchemeWiseBranchCode(issPortalFile.getSchemeWiseBranchCode());
                }
                if (issPortalFile.getFinancialYear() != null) {
                    existingIssPortalFile.setFinancialYear(issPortalFile.getFinancialYear());
                }
                if (issPortalFile.getFromDisbursementDate() != null) {
                    existingIssPortalFile.setFromDisbursementDate(issPortalFile.getFromDisbursementDate());
                }
                if (issPortalFile.getToDisbursementDate() != null) {
                    existingIssPortalFile.setToDisbursementDate(issPortalFile.getToDisbursementDate());
                }
                if (issPortalFile.getPacsCode() != null) {
                    existingIssPortalFile.setPacsCode(issPortalFile.getPacsCode());
                }
                if (issPortalFile.getStatus() != null) {
                    existingIssPortalFile.setStatus(issPortalFile.getStatus());
                }
                if (issPortalFile.getApplicationCount() != null) {
                    existingIssPortalFile.setApplicationCount(issPortalFile.getApplicationCount());
                }
                if (issPortalFile.getNotes() != null) {
                    existingIssPortalFile.setNotes(issPortalFile.getNotes());
                }

                return existingIssPortalFile;
            })
            .map(issPortalFileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IssPortalFile> findAll(Pageable pageable) {
        log.debug("Request to get all IssPortalFiles");
        return issPortalFileRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IssPortalFile> findOne(Long id) {
        log.debug("Request to get IssPortalFile : {}", id);
        return issPortalFileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IssPortalFile : {}", id);
        issPortalFileRepository.deleteById(id);
    }
}
