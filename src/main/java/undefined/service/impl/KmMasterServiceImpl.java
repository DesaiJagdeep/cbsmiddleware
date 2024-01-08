package com.cbs.middleware.service.impl;

import com.cbs.middleware.service.KmMasterService;
import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing KmMaster.
 */
@Service
@Transactional
public class KmMasterServiceImpl implements KmMasterService{

    private final Logger log = LoggerFactory.getLogger(KmMasterServiceImpl.class);

    private final KmMasterRepository kmMasterRepository;
    public KmMasterServiceImpl(KmMasterRepository kmMasterRepository) {
        this.kmMasterRepository = kmMasterRepository;
    }

    /**
     * Save a kmMaster.
     *
     * @param kmMaster the entity to save
     * @return the persisted entity
     */
    @Override
    public KmMaster save(KmMaster kmMaster) {
        log.debug("Request to save KmMaster : {}", kmMaster);
        return kmMasterRepository.save(kmMaster);
    }

    /**
     *  Get all the kmMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KmMaster> findAll(Pageable pageable) {
        log.debug("Request to get all KmMasters");
        return kmMasterRepository.findAll(pageable);
    }


    /**
     *  get all the kmMasters where FarmerTypeMaster is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<KmMaster> findAllWhereFarmerTypeMasterIsNull() {
        log.debug("Request to get all kmMasters where FarmerTypeMaster is null");
        return StreamSupport
            .stream(kmMasterRepository.findAll().spliterator(), false)
            .filter(kmMaster -> kmMaster.getFarmerTypeMaster() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one kmMaster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KmMaster findOne(Long id) {
        log.debug("Request to get KmMaster : {}", id);
        return kmMasterRepository.findOne(id);
    }

    /**
     *  Delete the  kmMaster by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KmMaster : {}", id);
        kmMasterRepository.delete(id);
    }
}
