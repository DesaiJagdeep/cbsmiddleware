package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.service.KmMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbs.middleware.domain.KmMaster}.
 */
@Service
@Transactional
public class KmMasterServiceImpl implements KmMasterService {

    private final Logger log = LoggerFactory.getLogger(KmMasterServiceImpl.class);

    private final KmMasterRepository kmMasterRepository;

    public KmMasterServiceImpl(KmMasterRepository kmMasterRepository) {
        this.kmMasterRepository = kmMasterRepository;
    }

    @Override
    public KmMaster save(KmMaster kmMaster) {
        log.debug("Request to save KmMaster : {}", kmMaster);
        return kmMasterRepository.save(kmMaster);
    }

    @Override
    public KmMaster update(KmMaster kmMaster) {
        log.debug("Request to update KmMaster : {}", kmMaster);
        return kmMasterRepository.save(kmMaster);
    }

    @Override
    public Optional<KmMaster> partialUpdate(KmMaster kmMaster) {
        log.debug("Request to partially update KmMaster : {}", kmMaster);

        return kmMasterRepository
            .findById(kmMaster.getId())
            .map(existingKmMaster -> {
                if (kmMaster.getBranchCode() != null) {
                    existingKmMaster.setBranchCode(kmMaster.getBranchCode());
                }
                if (kmMaster.getFarmerName() != null) {
                    existingKmMaster.setFarmerName(kmMaster.getFarmerName());
                }
                if (kmMaster.getFarmerNameMr() != null) {
                    existingKmMaster.setFarmerNameMr(kmMaster.getFarmerNameMr());
                }
                if (kmMaster.getFarmerAddress() != null) {
                    existingKmMaster.setFarmerAddress(kmMaster.getFarmerAddress());
                }
                if (kmMaster.getFarmerAddressMr() != null) {
                    existingKmMaster.setFarmerAddressMr(kmMaster.getFarmerAddressMr());
                }
                if (kmMaster.getGender() != null) {
                    existingKmMaster.setGender(kmMaster.getGender());
                }
                if (kmMaster.getCaste() != null) {
                    existingKmMaster.setCaste(kmMaster.getCaste());
                }
                if (kmMaster.getCasteMr() != null) {
                    existingKmMaster.setCasteMr(kmMaster.getCasteMr());
                }
                if (kmMaster.getPacsNumber() != null) {
                    existingKmMaster.setPacsNumber(kmMaster.getPacsNumber());
                }
                if (kmMaster.getAreaHect() != null) {
                    existingKmMaster.setAreaHect(kmMaster.getAreaHect());
                }
                if (kmMaster.getAadharNo() != null) {
                    existingKmMaster.setAadharNo(kmMaster.getAadharNo());
                }
                if (kmMaster.getAadharNoMr() != null) {
                    existingKmMaster.setAadharNoMr(kmMaster.getAadharNoMr());
                }
                if (kmMaster.getPanNo() != null) {
                    existingKmMaster.setPanNo(kmMaster.getPanNo());
                }
                if (kmMaster.getPanNoMr() != null) {
                    existingKmMaster.setPanNoMr(kmMaster.getPanNoMr());
                }
                if (kmMaster.getMobileNo() != null) {
                    existingKmMaster.setMobileNo(kmMaster.getMobileNo());
                }
                if (kmMaster.getMobileNoMr() != null) {
                    existingKmMaster.setMobileNoMr(kmMaster.getMobileNoMr());
                }
                if (kmMaster.getKccNo() != null) {
                    existingKmMaster.setKccNo(kmMaster.getKccNo());
                }
                if (kmMaster.getKccNoMr() != null) {
                    existingKmMaster.setKccNoMr(kmMaster.getKccNoMr());
                }
                if (kmMaster.getSavingNo() != null) {
                    existingKmMaster.setSavingNo(kmMaster.getSavingNo());
                }
                if (kmMaster.getSavingNoMr() != null) {
                    existingKmMaster.setSavingNoMr(kmMaster.getSavingNoMr());
                }
                if (kmMaster.getEntryFlag() != null) {
                    existingKmMaster.setEntryFlag(kmMaster.getEntryFlag());
                }

                return existingKmMaster;
            })
            .map(kmMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KmMaster> findAll(Pageable pageable) {
        log.debug("Request to get all KmMasters");
        return kmMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KmMaster> findOne(Long id) {
        log.debug("Request to get KmMaster : {}", id);
        return kmMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KmMaster : {}", id);
        kmMasterRepository.deleteById(id);
    }
}
