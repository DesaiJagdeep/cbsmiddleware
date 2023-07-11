package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CropCategoryMaster;
import com.cbs.middleware.repository.CropCategoryMasterRepository;
import com.cbs.middleware.service.CropCategoryMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CropCategoryMaster}.
 */
@Service
@Transactional
public class CropCategoryMasterServiceImpl implements CropCategoryMasterService {

    private final Logger log = LoggerFactory.getLogger(CropCategoryMasterServiceImpl.class);

    private final CropCategoryMasterRepository cropCategoryMasterRepository;

    public CropCategoryMasterServiceImpl(CropCategoryMasterRepository cropCategoryMasterRepository) {
        this.cropCategoryMasterRepository = cropCategoryMasterRepository;
    }

    @Override
    public CropCategoryMaster save(CropCategoryMaster cropCategoryMaster) {
        log.debug("Request to save CropCategoryMaster : {}", cropCategoryMaster);
        return cropCategoryMasterRepository.save(cropCategoryMaster);
    }

    @Override
    public CropCategoryMaster update(CropCategoryMaster cropCategoryMaster) {
        log.debug("Request to update CropCategoryMaster : {}", cropCategoryMaster);
        return cropCategoryMasterRepository.save(cropCategoryMaster);
    }

    @Override
    public Optional<CropCategoryMaster> partialUpdate(CropCategoryMaster cropCategoryMaster) {
        log.debug("Request to partially update CropCategoryMaster : {}", cropCategoryMaster);

        return cropCategoryMasterRepository
            .findById(cropCategoryMaster.getId())
            .map(existingCropCategoryMaster -> {
                if (cropCategoryMaster.getCategoryCode() != null) {
                    existingCropCategoryMaster.setCategoryCode(cropCategoryMaster.getCategoryCode());
                }
                if (cropCategoryMaster.getCategoryName() != null) {
                    existingCropCategoryMaster.setCategoryName(cropCategoryMaster.getCategoryName());
                }

                return existingCropCategoryMaster;
            })
            .map(cropCategoryMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CropCategoryMaster> findAll(Pageable pageable) {
        log.debug("Request to get all CropCategoryMasters");
        return cropCategoryMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CropCategoryMaster> findOne(Long id) {
        log.debug("Request to get CropCategoryMaster : {}", id);
        return cropCategoryMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CropCategoryMaster : {}", id);
        cropCategoryMasterRepository.deleteById(id);
    }
}
