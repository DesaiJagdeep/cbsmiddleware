package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.LandTypeMaster;
import com.cbs.middleware.repository.LandTypeMasterRepository;
import com.cbs.middleware.service.LandTypeMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LandTypeMaster}.
 */
@Service
@Transactional
public class LandTypeMasterServiceImpl implements LandTypeMasterService {

    private final Logger log = LoggerFactory.getLogger(LandTypeMasterServiceImpl.class);

    private final LandTypeMasterRepository landTypeMasterRepository;

    public LandTypeMasterServiceImpl(LandTypeMasterRepository landTypeMasterRepository) {
        this.landTypeMasterRepository = landTypeMasterRepository;
    }

    @Override
    public LandTypeMaster save(LandTypeMaster landTypeMaster) {
        log.debug("Request to save LandTypeMaster : {}", landTypeMaster);
        return landTypeMasterRepository.save(landTypeMaster);
    }

    @Override
    public LandTypeMaster update(LandTypeMaster landTypeMaster) {
        log.debug("Request to update LandTypeMaster : {}", landTypeMaster);
        return landTypeMasterRepository.save(landTypeMaster);
    }

    @Override
    public Optional<LandTypeMaster> partialUpdate(LandTypeMaster landTypeMaster) {
        log.debug("Request to partially update LandTypeMaster : {}", landTypeMaster);

        return landTypeMasterRepository
            .findById(landTypeMaster.getId())
            .map(existingLandTypeMaster -> {
                if (landTypeMaster.getLandTypeCode() != null) {
                    existingLandTypeMaster.setLandTypeCode(landTypeMaster.getLandTypeCode());
                }
                if (landTypeMaster.getLandType() != null) {
                    existingLandTypeMaster.setLandType(landTypeMaster.getLandType());
                }

                return existingLandTypeMaster;
            })
            .map(landTypeMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LandTypeMaster> findAll(Pageable pageable) {
        log.debug("Request to get all LandTypeMasters");
        return landTypeMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LandTypeMaster> findOne(Long id) {
        log.debug("Request to get LandTypeMaster : {}", id);
        return landTypeMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LandTypeMaster : {}", id);
        landTypeMasterRepository.deleteById(id);
    }
}
