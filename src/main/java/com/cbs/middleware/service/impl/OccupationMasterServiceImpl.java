package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.OccupationMaster;
import com.cbs.middleware.repository.OccupationMasterRepository;
import com.cbs.middleware.service.OccupationMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OccupationMaster}.
 */
@Service
@Transactional
public class OccupationMasterServiceImpl implements OccupationMasterService {

    private final Logger log = LoggerFactory.getLogger(OccupationMasterServiceImpl.class);

    private final OccupationMasterRepository occupationMasterRepository;

    public OccupationMasterServiceImpl(OccupationMasterRepository occupationMasterRepository) {
        this.occupationMasterRepository = occupationMasterRepository;
    }

    @Override
    public OccupationMaster save(OccupationMaster occupationMaster) {
        log.debug("Request to save OccupationMaster : {}", occupationMaster);
        return occupationMasterRepository.save(occupationMaster);
    }

    @Override
    public OccupationMaster update(OccupationMaster occupationMaster) {
        log.debug("Request to update OccupationMaster : {}", occupationMaster);
        return occupationMasterRepository.save(occupationMaster);
    }

    @Override
    public Optional<OccupationMaster> partialUpdate(OccupationMaster occupationMaster) {
        log.debug("Request to partially update OccupationMaster : {}", occupationMaster);

        return occupationMasterRepository
            .findById(occupationMaster.getId())
            .map(existingOccupationMaster -> {
                if (occupationMaster.getOccupationCode() != null) {
                    existingOccupationMaster.setOccupationCode(occupationMaster.getOccupationCode());
                }
                if (occupationMaster.getOccupationName() != null) {
                    existingOccupationMaster.setOccupationName(occupationMaster.getOccupationName());
                }

                return existingOccupationMaster;
            })
            .map(occupationMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OccupationMaster> findAll(Pageable pageable) {
        log.debug("Request to get all OccupationMasters");
        return occupationMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OccupationMaster> findOne(Long id) {
        log.debug("Request to get OccupationMaster : {}", id);
        return occupationMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OccupationMaster : {}", id);
        occupationMasterRepository.deleteById(id);
    }
}
