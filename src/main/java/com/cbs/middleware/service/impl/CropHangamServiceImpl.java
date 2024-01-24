package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CropHangam;
import com.cbs.middleware.repository.CropHangamRepository;
import com.cbs.middleware.service.CropHangamService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CropHangam}.
 */
@Service
@Transactional
public class CropHangamServiceImpl implements CropHangamService {

    private final Logger log = LoggerFactory.getLogger(CropHangamServiceImpl.class);

    private final CropHangamRepository cropHangamRepository;

    public CropHangamServiceImpl(CropHangamRepository cropHangamRepository) {
        this.cropHangamRepository = cropHangamRepository;
    }

    @Override
    public CropHangam save(CropHangam cropHangam) {
        log.debug("Request to save CropHangam : {}", cropHangam);
        return cropHangamRepository.save(cropHangam);
    }

    @Override
    public CropHangam update(CropHangam cropHangam) {
        log.debug("Request to update CropHangam : {}", cropHangam);
        return cropHangamRepository.save(cropHangam);
    }

    @Override
    public Optional<CropHangam> partialUpdate(CropHangam cropHangam) {
        log.debug("Request to partially update CropHangam : {}", cropHangam);

        return cropHangamRepository
            .findById(cropHangam.getId())
            .map(existingCropHangam -> {
                if (cropHangam.getHangam() != null) {
                    existingCropHangam.setHangam(cropHangam.getHangam());
                }
                if (cropHangam.getHangamMr() != null) {
                    existingCropHangam.setHangamMr(cropHangam.getHangamMr());
                }

                return existingCropHangam;
            })
            .map(cropHangamRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CropHangam> findAll(Pageable pageable) {
        log.debug("Request to get all CropHangams");
        return cropHangamRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CropHangam> findOne(Long id) {
        log.debug("Request to get CropHangam : {}", id);
        return cropHangamRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CropHangam : {}", id);
        cropHangamRepository.deleteById(id);
    }
}
