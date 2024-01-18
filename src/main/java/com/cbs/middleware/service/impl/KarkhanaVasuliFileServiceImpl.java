package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KarkhanaVasuliFile;
import com.cbs.middleware.repository.KarkhanaVasuliFileRepository;
import com.cbs.middleware.service.KarkhanaVasuliFileService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KarkhanaVasuliFile}.
 */
@Service
@Transactional
public class KarkhanaVasuliFileServiceImpl implements KarkhanaVasuliFileService {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliFileServiceImpl.class);

    private final KarkhanaVasuliFileRepository karkhanaVasuliFileRepository;

    public KarkhanaVasuliFileServiceImpl(KarkhanaVasuliFileRepository karkhanaVasuliFileRepository) {
        this.karkhanaVasuliFileRepository = karkhanaVasuliFileRepository;
    }

    @Override
    public KarkhanaVasuliFile save(KarkhanaVasuliFile karkhanaVasuliFile) {
        log.debug("Request to save KarkhanaVasuliFile : {}", karkhanaVasuliFile);
        return karkhanaVasuliFileRepository.save(karkhanaVasuliFile);
    }

    @Override
    public KarkhanaVasuliFile update(KarkhanaVasuliFile karkhanaVasuliFile) {
        log.debug("Request to update KarkhanaVasuliFile : {}", karkhanaVasuliFile);
        return karkhanaVasuliFileRepository.save(karkhanaVasuliFile);
    }

    @Override
    public Optional<KarkhanaVasuliFile> partialUpdate(KarkhanaVasuliFile karkhanaVasuliFile) {
        log.debug("Request to partially update KarkhanaVasuliFile : {}", karkhanaVasuliFile);

        return karkhanaVasuliFileRepository
            .findById(karkhanaVasuliFile.getId())
            .map(existingKarkhanaVasuliFile -> {
                if (karkhanaVasuliFile.getFileName() != null) {
                    existingKarkhanaVasuliFile.setFileName(karkhanaVasuliFile.getFileName());
                }
                if (karkhanaVasuliFile.getUniqueFileName() != null) {
                    existingKarkhanaVasuliFile.setUniqueFileName(karkhanaVasuliFile.getUniqueFileName());
                }
                if (karkhanaVasuliFile.getAddress() != null) {
                    existingKarkhanaVasuliFile.setAddress(karkhanaVasuliFile.getAddress());
                }
                if (karkhanaVasuliFile.getAddressMr() != null) {
                    existingKarkhanaVasuliFile.setAddressMr(karkhanaVasuliFile.getAddressMr());
                }
                if (karkhanaVasuliFile.getHangam() != null) {
                    existingKarkhanaVasuliFile.setHangam(karkhanaVasuliFile.getHangam());
                }
                if (karkhanaVasuliFile.getHangamMr() != null) {
                    existingKarkhanaVasuliFile.setHangamMr(karkhanaVasuliFile.getHangamMr());
                }
                if (karkhanaVasuliFile.getFactoryName() != null) {
                    existingKarkhanaVasuliFile.setFactoryName(karkhanaVasuliFile.getFactoryName());
                }
                if (karkhanaVasuliFile.getFactoryNameMr() != null) {
                    existingKarkhanaVasuliFile.setFactoryNameMr(karkhanaVasuliFile.getFactoryNameMr());
                }
                if (karkhanaVasuliFile.getTotalAmount() != null) {
                    existingKarkhanaVasuliFile.setTotalAmount(karkhanaVasuliFile.getTotalAmount());
                }
                if (karkhanaVasuliFile.getTotalAmountMr() != null) {
                    existingKarkhanaVasuliFile.setTotalAmountMr(karkhanaVasuliFile.getTotalAmountMr());
                }
                if (karkhanaVasuliFile.getFromDate() != null) {
                    existingKarkhanaVasuliFile.setFromDate(karkhanaVasuliFile.getFromDate());
                }
                if (karkhanaVasuliFile.getToDate() != null) {
                    existingKarkhanaVasuliFile.setToDate(karkhanaVasuliFile.getToDate());
                }
                if (karkhanaVasuliFile.getBranchCode() != null) {
                    existingKarkhanaVasuliFile.setBranchCode(karkhanaVasuliFile.getBranchCode());
                }
                if (karkhanaVasuliFile.getPacsName() != null) {
                    existingKarkhanaVasuliFile.setPacsName(karkhanaVasuliFile.getPacsName());
                }

                return existingKarkhanaVasuliFile;
            })
            .map(karkhanaVasuliFileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KarkhanaVasuliFile> findAll(Pageable pageable) {
        log.debug("Request to get all KarkhanaVasuliFiles");
        return karkhanaVasuliFileRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KarkhanaVasuliFile> findOne(Long id) {
        log.debug("Request to get KarkhanaVasuliFile : {}", id);
        return karkhanaVasuliFileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KarkhanaVasuliFile : {}", id);
        karkhanaVasuliFileRepository.deleteById(id);
    }
}
