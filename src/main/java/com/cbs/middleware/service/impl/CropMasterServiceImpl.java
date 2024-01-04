package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CropMaster;
import com.cbs.middleware.repository.CropMasterRepository;
import com.cbs.middleware.service.CropMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbs.middleware.domain.CropMaster}.
 */
@Service
@Transactional
public class CropMasterServiceImpl implements CropMasterService {

    private final Logger log = LoggerFactory.getLogger(CropMasterServiceImpl.class);

    private final CropMasterRepository cropMasterRepository;

    public CropMasterServiceImpl(CropMasterRepository cropMasterRepository) {
        this.cropMasterRepository = cropMasterRepository;
    }

    @Override
    public CropMaster save(CropMaster cropMaster) {
        log.debug("Request to save CropMaster : {}", cropMaster);
        return cropMasterRepository.save(cropMaster);
    }

    @Override
    public CropMaster update(CropMaster cropMaster) {
        log.debug("Request to update CropMaster : {}", cropMaster);
        return cropMasterRepository.save(cropMaster);
    }

    @Override
    public Optional<CropMaster> partialUpdate(CropMaster cropMaster) {
        log.debug("Request to partially update CropMaster : {}", cropMaster);

        return cropMasterRepository
            .findById(cropMaster.getId())
            .map(existingCropMaster -> {
                if (cropMaster.getCropCode() != null) {
                    existingCropMaster.setCropCode(cropMaster.getCropCode());
                }
                if (cropMaster.getCropName() != null) {
                    existingCropMaster.setCropName(cropMaster.getCropName());
                }
                if (cropMaster.getCategoryCode() != null) {
                    existingCropMaster.setCategoryCode(cropMaster.getCategoryCode());
                }
                if (cropMaster.getCategoryName() != null) {
                    existingCropMaster.setCategoryName(cropMaster.getCategoryName());
                }
                if (cropMaster.getVatapFromDay() != null) {
                    existingCropMaster.setVatapFromDay(cropMaster.getVatapFromDay());
                }
                if (cropMaster.getVatapToMonth() != null) {
                    existingCropMaster.setVatapToMonth(cropMaster.getVatapToMonth());
                }
                if (cropMaster.getLastToDay() != null) {
                    existingCropMaster.setLastToDay(cropMaster.getLastToDay());
                }
                if (cropMaster.getLastToMonth() != null) {
                    existingCropMaster.setLastToMonth(cropMaster.getLastToMonth());
                }
                if (cropMaster.getDueDay() != null) {
                    existingCropMaster.setDueDay(cropMaster.getDueDay());
                }
                if (cropMaster.getDueMonth() != null) {
                    existingCropMaster.setDueMonth(cropMaster.getDueMonth());
                }
                if (cropMaster.getCropPeriod() != null) {
                    existingCropMaster.setCropPeriod(cropMaster.getCropPeriod());
                }
                if (cropMaster.getSanctionAmt() != null) {
                    existingCropMaster.setSanctionAmt(cropMaster.getSanctionAmt());
                }
                if (cropMaster.getPreviousAmt() != null) {
                    existingCropMaster.setPreviousAmt(cropMaster.getPreviousAmt());
                }

                return existingCropMaster;
            })
            .map(cropMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CropMaster> findAll(Pageable pageable) {
        log.debug("Request to get all CropMasters");
        return cropMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CropMaster> findOne(Long id) {
        log.debug("Request to get CropMaster : {}", id);
        return cropMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CropMaster : {}", id);
        cropMasterRepository.deleteById(id);
    }
}
