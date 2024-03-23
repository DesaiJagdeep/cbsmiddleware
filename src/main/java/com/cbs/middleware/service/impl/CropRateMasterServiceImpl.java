package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CropRateMaster;
import com.cbs.middleware.repository.CropRateMasterRepository;
import com.cbs.middleware.service.CropRateMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CropRateMaster}.
 */
@Service
@Transactional
public class CropRateMasterServiceImpl implements CropRateMasterService {

    private final Logger log = LoggerFactory.getLogger(CropRateMasterServiceImpl.class);

    private final CropRateMasterRepository cropRateMasterRepository;

    public CropRateMasterServiceImpl(CropRateMasterRepository cropRateMasterRepository) {
        this.cropRateMasterRepository = cropRateMasterRepository;
    }

    @Override
    public CropRateMaster save(CropRateMaster cropRateMaster) {
        log.debug("Request to save CropRateMaster : {}", cropRateMaster);
        return cropRateMasterRepository.save(cropRateMaster);
    }

    @Override
    public CropRateMaster update(CropRateMaster cropRateMaster) {
        log.debug("Request to update CropRateMaster : {}", cropRateMaster);
        return cropRateMasterRepository.save(cropRateMaster);
    }

    @Override
    public Optional<CropRateMaster> partialUpdate(CropRateMaster cropRateMaster) {
        log.debug("Request to partially update CropRateMaster : {}", cropRateMaster);

        return cropRateMasterRepository
            .findById(cropRateMaster.getId())
            .map(existingCropRateMaster -> {
                if (cropRateMaster.getFinancialYear() != null) {
                    existingCropRateMaster.setFinancialYear(cropRateMaster.getFinancialYear());
                }
                if (cropRateMaster.getCurrentAmount() != null) {
                    existingCropRateMaster.setCurrentAmount(cropRateMaster.getCurrentAmount());
                }
                if (cropRateMaster.getCurrentAmountMr() != null) {
                    existingCropRateMaster.setCurrentAmountMr(cropRateMaster.getCurrentAmountMr());
                }
                if (cropRateMaster.getPercentage() != null) {
                    existingCropRateMaster.setPercentage(cropRateMaster.getPercentage());
                }
                if (cropRateMaster.getActiveFlag() != null) {
                    existingCropRateMaster.setActiveFlag(cropRateMaster.getActiveFlag());
                }

                return existingCropRateMaster;
            })
            .map(cropRateMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CropRateMaster> findAll(Pageable pageable) {
        log.debug("Request to get all CropRateMasters");
        return cropRateMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CropRateMaster> findOne(Long id) {
        log.debug("Request to get CropRateMaster : {}", id);
        return cropRateMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CropRateMaster : {}", id);
        cropRateMasterRepository.deleteById(id);
    }
}
