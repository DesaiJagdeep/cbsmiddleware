package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.DistrictMaster;
import com.cbs.middleware.repository.DistrictMasterRepository;
import com.cbs.middleware.service.DistrictMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DistrictMaster}.
 */
@Service
@Transactional
public class DistrictMasterServiceImpl implements DistrictMasterService {

    private final Logger log = LoggerFactory.getLogger(DistrictMasterServiceImpl.class);

    private final DistrictMasterRepository districtMasterRepository;

    public DistrictMasterServiceImpl(DistrictMasterRepository districtMasterRepository) {
        this.districtMasterRepository = districtMasterRepository;
    }

    @Override
    public DistrictMaster save(DistrictMaster districtMaster) {
        log.debug("Request to save DistrictMaster : {}", districtMaster);
        return districtMasterRepository.save(districtMaster);
    }

    @Override
    public DistrictMaster update(DistrictMaster districtMaster) {
        log.debug("Request to update DistrictMaster : {}", districtMaster);
        return districtMasterRepository.save(districtMaster);
    }

    @Override
    public Optional<DistrictMaster> partialUpdate(DistrictMaster districtMaster) {
        log.debug("Request to partially update DistrictMaster : {}", districtMaster);

        return districtMasterRepository
            .findById(districtMaster.getId())
            .map(existingDistrictMaster -> {
                if (districtMaster.getDistrictCode() != null) {
                    existingDistrictMaster.setDistrictCode(districtMaster.getDistrictCode());
                }
                if (districtMaster.getDistrictName() != null) {
                    existingDistrictMaster.setDistrictName(districtMaster.getDistrictName());
                }
                if (districtMaster.getStateCode() != null) {
                    existingDistrictMaster.setStateCode(districtMaster.getStateCode());
                }

                return existingDistrictMaster;
            })
            .map(districtMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DistrictMaster> findAll(Pageable pageable) {
        log.debug("Request to get all DistrictMasters");
        return districtMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DistrictMaster> findOne(Long id) {
        log.debug("Request to get DistrictMaster : {}", id);
        return districtMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DistrictMaster : {}", id);
        districtMasterRepository.deleteById(id);
    }
}
