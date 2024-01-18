package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.FactoryMaster;
import com.cbs.middleware.repository.FactoryMasterRepository;
import com.cbs.middleware.service.FactoryMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FactoryMaster}.
 */
@Service
@Transactional
public class FactoryMasterServiceImpl implements FactoryMasterService {

    private final Logger log = LoggerFactory.getLogger(FactoryMasterServiceImpl.class);

    private final FactoryMasterRepository factoryMasterRepository;

    public FactoryMasterServiceImpl(FactoryMasterRepository factoryMasterRepository) {
        this.factoryMasterRepository = factoryMasterRepository;
    }

    @Override
    public FactoryMaster save(FactoryMaster factoryMaster) {
        log.debug("Request to save FactoryMaster : {}", factoryMaster);
        return factoryMasterRepository.save(factoryMaster);
    }

    @Override
    public FactoryMaster update(FactoryMaster factoryMaster) {
        log.debug("Request to update FactoryMaster : {}", factoryMaster);
        return factoryMasterRepository.save(factoryMaster);
    }

    @Override
    public Optional<FactoryMaster> partialUpdate(FactoryMaster factoryMaster) {
        log.debug("Request to partially update FactoryMaster : {}", factoryMaster);

        return factoryMasterRepository
            .findById(factoryMaster.getId())
            .map(existingFactoryMaster -> {
                if (factoryMaster.getFactoryName() != null) {
                    existingFactoryMaster.setFactoryName(factoryMaster.getFactoryName());
                }
                if (factoryMaster.getFactoryNameMr() != null) {
                    existingFactoryMaster.setFactoryNameMr(factoryMaster.getFactoryNameMr());
                }
                if (factoryMaster.getFactoryCode() != null) {
                    existingFactoryMaster.setFactoryCode(factoryMaster.getFactoryCode());
                }
                if (factoryMaster.getFactoryCodeMr() != null) {
                    existingFactoryMaster.setFactoryCodeMr(factoryMaster.getFactoryCodeMr());
                }
                if (factoryMaster.getFactoryAddress() != null) {
                    existingFactoryMaster.setFactoryAddress(factoryMaster.getFactoryAddress());
                }
                if (factoryMaster.getFactoryAddressMr() != null) {
                    existingFactoryMaster.setFactoryAddressMr(factoryMaster.getFactoryAddressMr());
                }
                if (factoryMaster.getDescription() != null) {
                    existingFactoryMaster.setDescription(factoryMaster.getDescription());
                }
                if (factoryMaster.getStatus() != null) {
                    existingFactoryMaster.setStatus(factoryMaster.getStatus());
                }

                return existingFactoryMaster;
            })
            .map(factoryMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FactoryMaster> findAll(Pageable pageable) {
        log.debug("Request to get all FactoryMasters");
        return factoryMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FactoryMaster> findOne(Long id) {
        log.debug("Request to get FactoryMaster : {}", id);
        return factoryMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FactoryMaster : {}", id);
        factoryMasterRepository.deleteById(id);
    }
}
