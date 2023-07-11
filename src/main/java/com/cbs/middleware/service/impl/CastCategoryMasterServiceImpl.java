package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CastCategoryMaster;
import com.cbs.middleware.repository.CastCategoryMasterRepository;
import com.cbs.middleware.service.CastCategoryMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CastCategoryMaster}.
 */
@Service
@Transactional
public class CastCategoryMasterServiceImpl implements CastCategoryMasterService {

    private final Logger log = LoggerFactory.getLogger(CastCategoryMasterServiceImpl.class);

    private final CastCategoryMasterRepository castCategoryMasterRepository;

    public CastCategoryMasterServiceImpl(CastCategoryMasterRepository castCategoryMasterRepository) {
        this.castCategoryMasterRepository = castCategoryMasterRepository;
    }

    @Override
    public CastCategoryMaster save(CastCategoryMaster castCategoryMaster) {
        log.debug("Request to save CastCategoryMaster : {}", castCategoryMaster);
        return castCategoryMasterRepository.save(castCategoryMaster);
    }

    @Override
    public CastCategoryMaster update(CastCategoryMaster castCategoryMaster) {
        log.debug("Request to update CastCategoryMaster : {}", castCategoryMaster);
        return castCategoryMasterRepository.save(castCategoryMaster);
    }

    @Override
    public Optional<CastCategoryMaster> partialUpdate(CastCategoryMaster castCategoryMaster) {
        log.debug("Request to partially update CastCategoryMaster : {}", castCategoryMaster);

        return castCategoryMasterRepository
            .findById(castCategoryMaster.getId())
            .map(existingCastCategoryMaster -> {
                if (castCategoryMaster.getCastCategoryCode() != null) {
                    existingCastCategoryMaster.setCastCategoryCode(castCategoryMaster.getCastCategoryCode());
                }
                if (castCategoryMaster.getCastCategoryName() != null) {
                    existingCastCategoryMaster.setCastCategoryName(castCategoryMaster.getCastCategoryName());
                }

                return existingCastCategoryMaster;
            })
            .map(castCategoryMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CastCategoryMaster> findAll(Pageable pageable) {
        log.debug("Request to get all CastCategoryMasters");
        return castCategoryMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CastCategoryMaster> findOne(Long id) {
        log.debug("Request to get CastCategoryMaster : {}", id);
        return castCategoryMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CastCategoryMaster : {}", id);
        castCategoryMasterRepository.deleteById(id);
    }
}
