package com.cbs.middleware.service;

import com.cbs.middleware.domain.KmMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing KmMaster.
 */
public interface KmMasterService {

    /**
     * Save a kmMaster.
     *
     * @param kmMaster the entity to save
     * @return the persisted entity
     */
    KmMaster save(KmMaster kmMaster);

    /**
     *  Get all the kmMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<KmMaster> findAll(Pageable pageable);
    /**
     *  Get all the KmMasterDTO where FarmerTypeMaster is null.
     *
     *  @return the list of entities
     */
    List<KmMaster> findAllWhereFarmerTypeMasterIsNull();

    /**
     *  Get the "id" kmMaster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    KmMaster findOne(Long id);

    /**
     *  Delete the "id" kmMaster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
