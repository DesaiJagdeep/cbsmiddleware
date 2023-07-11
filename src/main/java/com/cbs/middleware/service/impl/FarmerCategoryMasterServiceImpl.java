package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.FarmerCategoryMaster;
import com.cbs.middleware.repository.FarmerCategoryMasterRepository;
import com.cbs.middleware.service.FarmerCategoryMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FarmerCategoryMaster}.
 */
@Service
@Transactional
public class FarmerCategoryMasterServiceImpl implements FarmerCategoryMasterService {

    private final Logger log = LoggerFactory.getLogger(FarmerCategoryMasterServiceImpl.class);

    private final FarmerCategoryMasterRepository farmerCategoryMasterRepository;

    public FarmerCategoryMasterServiceImpl(FarmerCategoryMasterRepository farmerCategoryMasterRepository) {
        this.farmerCategoryMasterRepository = farmerCategoryMasterRepository;
    }

    @Override
    public FarmerCategoryMaster save(FarmerCategoryMaster farmerCategoryMaster) {
        log.debug("Request to save FarmerCategoryMaster : {}", farmerCategoryMaster);
        return farmerCategoryMasterRepository.save(farmerCategoryMaster);
    }

    @Override
    public FarmerCategoryMaster update(FarmerCategoryMaster farmerCategoryMaster) {
        log.debug("Request to update FarmerCategoryMaster : {}", farmerCategoryMaster);
        return farmerCategoryMasterRepository.save(farmerCategoryMaster);
    }

    @Override
    public Optional<FarmerCategoryMaster> partialUpdate(FarmerCategoryMaster farmerCategoryMaster) {
        log.debug("Request to partially update FarmerCategoryMaster : {}", farmerCategoryMaster);

        return farmerCategoryMasterRepository
            .findById(farmerCategoryMaster.getId())
            .map(existingFarmerCategoryMaster -> {
                if (farmerCategoryMaster.getFarmerCategoryCode() != null) {
                    existingFarmerCategoryMaster.setFarmerCategoryCode(farmerCategoryMaster.getFarmerCategoryCode());
                }
                if (farmerCategoryMaster.getFarmerCategory() != null) {
                    existingFarmerCategoryMaster.setFarmerCategory(farmerCategoryMaster.getFarmerCategory());
                }

                return existingFarmerCategoryMaster;
            })
            .map(farmerCategoryMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmerCategoryMaster> findAll(Pageable pageable) {
        log.debug("Request to get all FarmerCategoryMasters");
        return farmerCategoryMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FarmerCategoryMaster> findOne(Long id) {
        log.debug("Request to get FarmerCategoryMaster : {}", id);
        return farmerCategoryMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FarmerCategoryMaster : {}", id);
        farmerCategoryMasterRepository.deleteById(id);
    }
}
