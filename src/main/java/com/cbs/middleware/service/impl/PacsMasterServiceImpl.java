package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.service.PacsMasterService;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PacsMaster}.
 */
@Service
@Transactional
public class PacsMasterServiceImpl implements PacsMasterService {

	@Autowired
	TranslationServiceUtility translationServiceUtility;
	 
    private final Logger log = LoggerFactory.getLogger(PacsMasterServiceImpl.class);

    private final PacsMasterRepository pacsMasterRepository;

    public PacsMasterServiceImpl(PacsMasterRepository pacsMasterRepository) {
        this.pacsMasterRepository = pacsMasterRepository;
    }

    @Override
    public PacsMaster save(PacsMaster pacsMaster) {
        log.debug("Request to save PacsMaster : {}", pacsMaster);
        
        
        
        if(StringUtils.isNotBlank(pacsMaster.getPacsName()))
        {
        	pacsMaster.setPacsNameMr(translationServiceUtility.translationText(pacsMaster.getPacsName()));
        }
        
        if(StringUtils.isNotBlank(pacsMaster.getPacsAddress()))
        {
        	pacsMaster.setPacsAddressMr(translationServiceUtility.translationText(pacsMaster.getPacsAddress()));
        }
        
        return pacsMasterRepository.save(pacsMaster);
    }

    @Override
    public PacsMaster update(PacsMaster pacsMaster) {
        log.debug("Request to update PacsMaster : {}", pacsMaster);
        
        if(StringUtils.isNotBlank(pacsMaster.getPacsName()))
        {
        	pacsMaster.setPacsNameMr(translationServiceUtility.translationText(pacsMaster.getPacsName()));
        }
        
        if(StringUtils.isNotBlank(pacsMaster.getPacsAddress()))
        {
        	pacsMaster.setPacsAddressMr(translationServiceUtility.translationText(pacsMaster.getPacsAddress()));
        }
        
        return pacsMasterRepository.save(pacsMaster);
    }

    @Override
    public Optional<PacsMaster> partialUpdate(PacsMaster pacsMaster) {
        log.debug("Request to partially update PacsMaster : {}", pacsMaster);

        return pacsMasterRepository
            .findById(pacsMaster.getId())
            .map(existingPacsMaster -> {
                if (pacsMaster.getPacsName() != null) {
                    existingPacsMaster.setPacsName(pacsMaster.getPacsName());
                }
                if (pacsMaster.getPacsNumber() != null) {
                    existingPacsMaster.setPacsNumber(pacsMaster.getPacsNumber());
                }

                return existingPacsMaster;
            })
            .map(pacsMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacsMaster> findAll(Pageable pageable) {
        log.debug("Request to get all PacsMasters");
        return pacsMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacsMaster> findOne(Long id) {
        log.debug("Request to get PacsMaster : {}", id);
        return pacsMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PacsMaster : {}", id);
        pacsMasterRepository.deleteById(id);
    }

    @Override
    public Page<PacsMaster> findAllWithEagerRelationships(Pageable pageable) {
        return pacsMasterRepository.findAllWithEagerRelationships(pageable);
    }
}
