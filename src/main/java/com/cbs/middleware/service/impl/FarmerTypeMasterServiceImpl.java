package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.FarmerTypeMaster;
import com.cbs.middleware.repository.FarmerTypeMasterRepository;
import com.cbs.middleware.service.FarmerTypeMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FarmerTypeMaster}.
 */
@Service
@Transactional
public class FarmerTypeMasterServiceImpl implements FarmerTypeMasterService {

    private final Logger log = LoggerFactory.getLogger(FarmerTypeMasterServiceImpl.class);

    private final FarmerTypeMasterRepository farmerTypeMasterRepository;

    public FarmerTypeMasterServiceImpl(FarmerTypeMasterRepository farmerTypeMasterRepository) {
        this.farmerTypeMasterRepository = farmerTypeMasterRepository;
    }

    @Override
    public FarmerTypeMaster save(FarmerTypeMaster farmerTypeMaster) {
        log.debug("Request to save FarmerTypeMaster : {}", farmerTypeMaster);
        return farmerTypeMasterRepository.save(farmerTypeMaster);
    }

    @Override
    public FarmerTypeMaster update(FarmerTypeMaster farmerTypeMaster) {
        log.debug("Request to update FarmerTypeMaster : {}", farmerTypeMaster);
        return farmerTypeMasterRepository.save(farmerTypeMaster);
    }

    @Override
    public Optional<FarmerTypeMaster> partialUpdate(FarmerTypeMaster farmerTypeMaster) {
        log.debug("Request to partially update FarmerTypeMaster : {}", farmerTypeMaster);

        return farmerTypeMasterRepository
            .findById(farmerTypeMaster.getId())
            .map(existingFarmerTypeMaster -> {
                if (farmerTypeMaster.getFarmerTypeCode() != null) {
                    existingFarmerTypeMaster.setFarmerTypeCode(farmerTypeMaster.getFarmerTypeCode());
                }
                if (farmerTypeMaster.getFarmerType() != null) {
                    existingFarmerTypeMaster.setFarmerType(farmerTypeMaster.getFarmerType());
                }

                return existingFarmerTypeMaster;
            })
            .map(farmerTypeMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmerTypeMaster> findAll(Pageable pageable) {
        log.debug("Request to get all FarmerTypeMasters");
        return farmerTypeMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FarmerTypeMaster> findOne(Long id) {
        log.debug("Request to get FarmerTypeMaster : {}", id);
        return farmerTypeMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FarmerTypeMaster : {}", id);
        farmerTypeMasterRepository.deleteById(id);
    }
}
