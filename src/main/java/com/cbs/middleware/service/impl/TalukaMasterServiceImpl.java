package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.TalukaMaster;
import com.cbs.middleware.repository.TalukaMasterRepository;
import com.cbs.middleware.service.TalukaMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TalukaMaster}.
 */
@Service
@Transactional
public class TalukaMasterServiceImpl implements TalukaMasterService {

    private final Logger log = LoggerFactory.getLogger(TalukaMasterServiceImpl.class);

    private final TalukaMasterRepository talukaMasterRepository;

    public TalukaMasterServiceImpl(TalukaMasterRepository talukaMasterRepository) {
        this.talukaMasterRepository = talukaMasterRepository;
    }

    @Override
    public TalukaMaster save(TalukaMaster talukaMaster) {
        log.debug("Request to save TalukaMaster : {}", talukaMaster);
        return talukaMasterRepository.save(talukaMaster);
    }

    @Override
    public TalukaMaster update(TalukaMaster talukaMaster) {
        log.debug("Request to update TalukaMaster : {}", talukaMaster);
        return talukaMasterRepository.save(talukaMaster);
    }

    @Override
    public Optional<TalukaMaster> partialUpdate(TalukaMaster talukaMaster) {
        log.debug("Request to partially update TalukaMaster : {}", talukaMaster);

        return talukaMasterRepository
            .findById(talukaMaster.getId())
            .map(existingTalukaMaster -> {
                if (talukaMaster.getTalukaCode() != null) {
                    existingTalukaMaster.setTalukaCode(talukaMaster.getTalukaCode());
                }
                if (talukaMaster.getTalukaName() != null) {
                    existingTalukaMaster.setTalukaName(talukaMaster.getTalukaName());
                }
                if (talukaMaster.getDistrictCode() != null) {
                    existingTalukaMaster.setDistrictCode(talukaMaster.getDistrictCode());
                }

                return existingTalukaMaster;
            })
            .map(talukaMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TalukaMaster> findAll(Pageable pageable) {
        log.debug("Request to get all TalukaMasters");
        return talukaMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TalukaMaster> findOne(Long id) {
        log.debug("Request to get TalukaMaster : {}", id);
        return talukaMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TalukaMaster : {}", id);
        talukaMasterRepository.deleteById(id);
    }
}
