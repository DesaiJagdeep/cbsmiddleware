package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.StateMaster;
import com.cbs.middleware.repository.StateMasterRepository;
import com.cbs.middleware.service.StateMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StateMaster}.
 */
@Service
@Transactional
public class StateMasterServiceImpl implements StateMasterService {

    private final Logger log = LoggerFactory.getLogger(StateMasterServiceImpl.class);

    private final StateMasterRepository stateMasterRepository;

    public StateMasterServiceImpl(StateMasterRepository stateMasterRepository) {
        this.stateMasterRepository = stateMasterRepository;
    }

    @Override
    public StateMaster save(StateMaster stateMaster) {
        log.debug("Request to save StateMaster : {}", stateMaster);
        return stateMasterRepository.save(stateMaster);
    }

    @Override
    public StateMaster update(StateMaster stateMaster) {
        log.debug("Request to update StateMaster : {}", stateMaster);
        return stateMasterRepository.save(stateMaster);
    }

    @Override
    public Optional<StateMaster> partialUpdate(StateMaster stateMaster) {
        log.debug("Request to partially update StateMaster : {}", stateMaster);

        return stateMasterRepository
            .findById(stateMaster.getId())
            .map(existingStateMaster -> {
                if (stateMaster.getStateCode() != null) {
                    existingStateMaster.setStateCode(stateMaster.getStateCode());
                }
                if (stateMaster.getStateName() != null) {
                    existingStateMaster.setStateName(stateMaster.getStateName());
                }

                return existingStateMaster;
            })
            .map(stateMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateMaster> findAll(Pageable pageable) {
        log.debug("Request to get all StateMasters");
        return stateMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StateMaster> findOne(Long id) {
        log.debug("Request to get StateMaster : {}", id);
        return stateMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StateMaster : {}", id);
        stateMasterRepository.deleteById(id);
    }
}
