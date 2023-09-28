package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KMPUpload;
import com.cbs.middleware.repository.KMPUploadRepository;
import com.cbs.middleware.service.KMPUploadService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KMPUpload}.
 */
@Service
@Transactional
public class KMPUploadServiceImpl implements KMPUploadService {

    private final Logger log = LoggerFactory.getLogger(KMPUploadServiceImpl.class);

    private final KMPUploadRepository kMPUploadRepository;

    public KMPUploadServiceImpl(KMPUploadRepository kMPUploadRepository) {
        this.kMPUploadRepository = kMPUploadRepository;
    }

    @Override
    public KMPUpload save(KMPUpload kMPUpload) {
        log.debug("Request to save KMPUpload : {}", kMPUpload);
        return kMPUploadRepository.save(kMPUpload);
    }

    @Override
    public KMPUpload update(KMPUpload kMPUpload) {
        log.debug("Request to update KMPUpload : {}", kMPUpload);
        return kMPUploadRepository.save(kMPUpload);
    }

    @Override
    public Optional<KMPUpload> partialUpdate(KMPUpload kMPUpload) {
        log.debug("Request to partially update KMPUpload : {}", kMPUpload);

        return kMPUploadRepository
            .findById(kMPUpload.getId())
            .map(existingKMPUpload -> {
                if (kMPUpload.getFileName() != null) {
                    existingKMPUpload.setFileName(kMPUpload.getFileName());
                }
                if (kMPUpload.getUniqueFileName() != null) {
                    existingKMPUpload.setUniqueFileName(kMPUpload.getUniqueFileName());
                }

                return existingKMPUpload;
            })
            .map(kMPUploadRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KMPUpload> findAll(Pageable pageable) {
        log.debug("Request to get all KMPUploads");
        return kMPUploadRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KMPUpload> findOne(Long id) {
        log.debug("Request to get KMPUpload : {}", id);
        return kMPUploadRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KMPUpload : {}", id);
        kMPUploadRepository.deleteById(id);
    }
}
