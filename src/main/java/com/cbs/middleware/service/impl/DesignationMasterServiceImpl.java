package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.DesignationMaster;
import com.cbs.middleware.repository.DesignationMasterRepository;
import com.cbs.middleware.service.DesignationMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DesignationMaster}.
 */
@Service
@Transactional
public class DesignationMasterServiceImpl implements DesignationMasterService {

    private final Logger log = LoggerFactory.getLogger(DesignationMasterServiceImpl.class);

    private final DesignationMasterRepository designationMasterRepository;

    public DesignationMasterServiceImpl(DesignationMasterRepository designationMasterRepository) {
        this.designationMasterRepository = designationMasterRepository;
    }

    @Override
    public DesignationMaster save(DesignationMaster designationMaster) {
        log.debug("Request to save DesignationMaster : {}", designationMaster);
        return designationMasterRepository.save(designationMaster);
    }

    @Override
    public DesignationMaster update(DesignationMaster designationMaster) {
        log.debug("Request to update DesignationMaster : {}", designationMaster);
        return designationMasterRepository.save(designationMaster);
    }

    @Override
    public Optional<DesignationMaster> partialUpdate(DesignationMaster designationMaster) {
        log.debug("Request to partially update DesignationMaster : {}", designationMaster);

        return designationMasterRepository
            .findById(designationMaster.getId())
            .map(existingDesignationMaster -> {
                if (designationMaster.getDesignationCode() != null) {
                    existingDesignationMaster.setDesignationCode(designationMaster.getDesignationCode());
                }
                if (designationMaster.getDesignationName() != null) {
                    existingDesignationMaster.setDesignationName(designationMaster.getDesignationName());
                }

                return existingDesignationMaster;
            })
            .map(designationMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DesignationMaster> findAll(Pageable pageable) {
        log.debug("Request to get all DesignationMasters");
        return designationMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DesignationMaster> findOne(Long id) {
        log.debug("Request to get DesignationMaster : {}", id);
        return designationMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DesignationMaster : {}", id);
        designationMasterRepository.deleteById(id);
    }
}
