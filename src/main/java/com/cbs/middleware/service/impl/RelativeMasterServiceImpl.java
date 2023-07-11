package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.RelativeMaster;
import com.cbs.middleware.repository.RelativeMasterRepository;
import com.cbs.middleware.service.RelativeMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RelativeMaster}.
 */
@Service
@Transactional
public class RelativeMasterServiceImpl implements RelativeMasterService {

    private final Logger log = LoggerFactory.getLogger(RelativeMasterServiceImpl.class);

    private final RelativeMasterRepository relativeMasterRepository;

    public RelativeMasterServiceImpl(RelativeMasterRepository relativeMasterRepository) {
        this.relativeMasterRepository = relativeMasterRepository;
    }

    @Override
    public RelativeMaster save(RelativeMaster relativeMaster) {
        log.debug("Request to save RelativeMaster : {}", relativeMaster);
        return relativeMasterRepository.save(relativeMaster);
    }

    @Override
    public RelativeMaster update(RelativeMaster relativeMaster) {
        log.debug("Request to update RelativeMaster : {}", relativeMaster);
        return relativeMasterRepository.save(relativeMaster);
    }

    @Override
    public Optional<RelativeMaster> partialUpdate(RelativeMaster relativeMaster) {
        log.debug("Request to partially update RelativeMaster : {}", relativeMaster);

        return relativeMasterRepository
            .findById(relativeMaster.getId())
            .map(existingRelativeMaster -> {
                if (relativeMaster.getRelativeCode() != null) {
                    existingRelativeMaster.setRelativeCode(relativeMaster.getRelativeCode());
                }
                if (relativeMaster.getRelativeName() != null) {
                    existingRelativeMaster.setRelativeName(relativeMaster.getRelativeName());
                }

                return existingRelativeMaster;
            })
            .map(relativeMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RelativeMaster> findAll(Pageable pageable) {
        log.debug("Request to get all RelativeMasters");
        return relativeMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RelativeMaster> findOne(Long id) {
        log.debug("Request to get RelativeMaster : {}", id);
        return relativeMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RelativeMaster : {}", id);
        relativeMasterRepository.deleteById(id);
    }
}
