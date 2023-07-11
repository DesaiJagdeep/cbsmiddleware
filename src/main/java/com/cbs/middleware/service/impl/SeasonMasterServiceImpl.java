package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.SeasonMaster;
import com.cbs.middleware.repository.SeasonMasterRepository;
import com.cbs.middleware.service.SeasonMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SeasonMaster}.
 */
@Service
@Transactional
public class SeasonMasterServiceImpl implements SeasonMasterService {

    private final Logger log = LoggerFactory.getLogger(SeasonMasterServiceImpl.class);

    private final SeasonMasterRepository seasonMasterRepository;

    public SeasonMasterServiceImpl(SeasonMasterRepository seasonMasterRepository) {
        this.seasonMasterRepository = seasonMasterRepository;
    }

    @Override
    public SeasonMaster save(SeasonMaster seasonMaster) {
        log.debug("Request to save SeasonMaster : {}", seasonMaster);
        return seasonMasterRepository.save(seasonMaster);
    }

    @Override
    public SeasonMaster update(SeasonMaster seasonMaster) {
        log.debug("Request to update SeasonMaster : {}", seasonMaster);
        return seasonMasterRepository.save(seasonMaster);
    }

    @Override
    public Optional<SeasonMaster> partialUpdate(SeasonMaster seasonMaster) {
        log.debug("Request to partially update SeasonMaster : {}", seasonMaster);

        return seasonMasterRepository
            .findById(seasonMaster.getId())
            .map(existingSeasonMaster -> {
                if (seasonMaster.getSeasonCode() != null) {
                    existingSeasonMaster.setSeasonCode(seasonMaster.getSeasonCode());
                }
                if (seasonMaster.getSeasonName() != null) {
                    existingSeasonMaster.setSeasonName(seasonMaster.getSeasonName());
                }

                return existingSeasonMaster;
            })
            .map(seasonMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SeasonMaster> findAll(Pageable pageable) {
        log.debug("Request to get all SeasonMasters");
        return seasonMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SeasonMaster> findOne(Long id) {
        log.debug("Request to get SeasonMaster : {}", id);
        return seasonMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeasonMaster : {}", id);
        seasonMasterRepository.deleteById(id);
    }
}
