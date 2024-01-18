package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.VillageMaster;
import com.cbs.middleware.repository.VillageMasterRepository;
import com.cbs.middleware.service.VillageMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VillageMaster}.
 */
@Service
@Transactional
public class VillageMasterServiceImpl implements VillageMasterService {

    private final Logger log = LoggerFactory.getLogger(VillageMasterServiceImpl.class);

    private final VillageMasterRepository villageMasterRepository;

    public VillageMasterServiceImpl(VillageMasterRepository villageMasterRepository) {
        this.villageMasterRepository = villageMasterRepository;
    }

    @Override
    public VillageMaster save(VillageMaster villageMaster) {
        log.debug("Request to save VillageMaster : {}", villageMaster);
        return villageMasterRepository.save(villageMaster);
    }

    @Override
    public VillageMaster update(VillageMaster villageMaster) {
        log.debug("Request to update VillageMaster : {}", villageMaster);
        return villageMasterRepository.save(villageMaster);
    }

    @Override
    public Optional<VillageMaster> partialUpdate(VillageMaster villageMaster) {
        log.debug("Request to partially update VillageMaster : {}", villageMaster);

        return villageMasterRepository
            .findById(villageMaster.getId())
            .map(existingVillageMaster -> {
                if (villageMaster.getVillageName() != null) {
                    existingVillageMaster.setVillageName(villageMaster.getVillageName());
                }
                if (villageMaster.getVillageNameMr() != null) {
                    existingVillageMaster.setVillageNameMr(villageMaster.getVillageNameMr());
                }
                if (villageMaster.getVillageCode() != null) {
                    existingVillageMaster.setVillageCode(villageMaster.getVillageCode());
                }
                if (villageMaster.getVillageCodeMr() != null) {
                    existingVillageMaster.setVillageCodeMr(villageMaster.getVillageCodeMr());
                }

                return existingVillageMaster;
            })
            .map(villageMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VillageMaster> findAll(Pageable pageable) {
        log.debug("Request to get all VillageMasters");
        return villageMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VillageMaster> findOne(Long id) {
        log.debug("Request to get VillageMaster : {}", id);
        return villageMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VillageMaster : {}", id);
        villageMasterRepository.deleteById(id);
    }
}
