package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KmCrops;
import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.domain.KmLoans;
import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.KmCropsRepository;
import com.cbs.middleware.repository.KmDetailsRepository;
import com.cbs.middleware.repository.KmLoansRepository;
import com.cbs.middleware.repository.KmMasterRepository;
import com.cbs.middleware.service.KmMasterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.cbs.middleware.web.rest.utility.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KmMaster}.
 */
@Service
@Transactional
public class KmMasterServiceImpl implements KmMasterService {
    private final Logger log = LoggerFactory.getLogger(KmMasterServiceImpl.class);
    private final KmMasterRepository kmMasterRepository;
    private final KmCropsRepository kmCropsRepository;
    private final KmLoansRepository kmLoansRepository;
    private final KmDetailsRepository kmDetailsRepository;
    @Autowired
    private EntityMapper entityMapper;

    public KmMasterServiceImpl(KmMasterRepository kmMasterRepository,
                               KmCropsRepository kmCropsRepository,
                               KmLoansRepository kmLoansRepository,
                               KmDetailsRepository kmDetailsRepository) {
        this.kmMasterRepository = kmMasterRepository;
        this.kmCropsRepository = kmCropsRepository;
        this.kmLoansRepository = kmLoansRepository;
        this.kmDetailsRepository = kmDetailsRepository;
    }

    @Override
    public KmMaster save(KmMaster kmMaster) {
        log.debug("Request to save KmMaster : {}", kmMaster);

        KmMaster kmMasterToSave = new KmMaster();

        kmMasterToSave= entityMapper.kmMasterDtoToEntity(kmMaster,kmMasterToSave);
        kmMasterToSave.setFarmerName(kmMaster.getFarmerName());
        kmMasterRepository.save(kmMasterToSave);

        KmDetails kmDetails = kmMaster.getKmDetails();
        kmDetails.setKmMaster(kmMasterToSave);
        kmDetailsRepository.save(kmMaster.getKmDetails());

        Set<KmCrops> kmCrops = kmMaster.getKmDetails().getKmCrops();
        List<KmCrops> updatedKmCrops = kmCrops.stream()
            .map(a -> {
                a.setKmDetails(kmDetails);
                return a;
            })
            .collect(Collectors.toList());

        kmCropsRepository.saveAll(updatedKmCrops);


        Set<KmLoans> kmLoans = kmMaster.getKmDetails().getKmLoans();
        List<KmLoans> updatedKmLoans = kmLoans.stream()
            .map(a -> {
                a.setKmDetails(kmDetails);
                return a;
            })
            .collect(Collectors.toList());

        kmLoansRepository.saveAll(updatedKmLoans);

        return kmMasterToSave;
    }

    @Override
    public KmMaster update(KmMaster kmMaster) {
        log.debug("Request to update KmMaster : {}", kmMaster);
        KmMaster kmMasterSaved = kmMasterRepository.save(kmMaster);

       /* Optional<KmMaster> kmMasterDB = kmMasterRepository.findById(kmMaster.getId());

        Set<KmCrops> kmCropsPayload = kmMaster.getKmDetails().getKmCrops();
        Set<KmLoans> kmLoansPayload = kmMaster.getKmDetails().getKmLoans();
        Long id = kmMaster.getKmDetails().getId();

        List<KmCrops> kmCropsDB = kmCropsRepository.findByKmDetailsEquals(kmMaster.getKmDetails());

        // List<KmLoans> kmLoansDB = kmLoansRepository.findByKmDetails_IdEquals(kmMaster.getKmDetails().getId());
        List<KmLoans> kmLoansDB = kmLoansRepository.findKmLoansByKmDetailsId(kmMaster.getKmDetails().getId());

        //delete entries which are in DB but not in payload for KmCrops
        List<KmCrops> kmCropsObjectsToDelete = new ArrayList<>(kmCropsDB);
        kmCropsObjectsToDelete.removeAll(kmCropsPayload);

        for (KmCrops kmCrop : kmCropsObjectsToDelete) {
            kmCropsRepository.delete(kmCrop);
        }

        //delete entries which are in DB but not in payload for KmCrops
        List<KmLoans> kmLoansObjectsToDelete = new ArrayList<>(kmLoansDB);
        kmLoansObjectsToDelete.removeAll(kmLoansPayload);

        for (KmLoans kmLoan : kmLoansObjectsToDelete) {
            kmLoansRepository.delete(kmLoan);
        }

        //set km_master_id in KmDetails
        Optional<KmDetails> kmDetailsDB = kmDetailsRepository.findById(kmMaster.getKmDetails().getId());
        kmDetailsDB.get().setKmMaster(kmMasterDB.get());
        kmDetailsRepository.save(kmDetailsDB.get());

        set km_details_id in km_crops and km_loans
        for (KmCrops kmCrop : kmCropsPayload) {
            if (kmCrop.getId() == null) {
                KmCrops newKmCrop = new KmCrops();
                newKmCrop = entityMapper.kmCropDtoToEntity(kmCrop, newKmCrop);
                newKmCrop.setKmDetails(kmMaster.getKmDetails());
                kmCropsRepository.save(newKmCrop);
            }
            else {
                Optional<KmCrops> kmCropDB = kmCropsRepository.findById(kmCrop.getId());
                kmCropDB.get().setKmDetails(kmMaster.getKmDetails());
                kmCropsRepository.save(kmCropDB.get());
            }

        }

        for (KmLoans kmLoan : kmLoansPayload) {
            if (kmLoan.getId() == null) {
                KmLoans newKmLoan = new KmLoans();
                newKmLoan = entityMapper.kmLoanDtoToEntity(kmLoan, newKmLoan);
                newKmLoan.setKmDetails(kmMaster.getKmDetails());
                kmLoansRepository.save(newKmLoan);
            } else {
                Optional<KmLoans> kmLoanDB = kmLoansRepository.findById(kmLoan.getId());
                kmLoanDB.get().setKmDetails(kmMaster.getKmDetails());
                kmLoansRepository.save(kmLoanDB.get());
            }

        }
*/

        return kmMasterSaved;
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
                if (kmMaster.getBranchCodeMr() != null) {
                    existingKmMaster.setBranchCodeMr(kmMaster.getBranchCodeMr());
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
                if (kmMaster.getGenderMr() != null) {
                    existingKmMaster.setGenderMr(kmMaster.getGenderMr());
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
                if (kmMaster.getAreaHector() != null) {
                    existingKmMaster.setAreaHector(kmMaster.getAreaHector());
                }
                if (kmMaster.getAreaHectorMr() != null) {
                    existingKmMaster.setAreaHectorMr(kmMaster.getAreaHectorMr());
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
                if (kmMaster.getSavingAcNo() != null) {
                    existingKmMaster.setSavingAcNo(kmMaster.getSavingAcNo());
                }
                if (kmMaster.getSavingAcNoMr() != null) {
                    existingKmMaster.setSavingAcNoMr(kmMaster.getSavingAcNoMr());
                }
                if (kmMaster.getPacsMemberCode() != null) {
                    existingKmMaster.setPacsMemberCode(kmMaster.getPacsMemberCode());
                }
                if (kmMaster.getPacsMemberCodeMr() != null) {
                    existingKmMaster.setPacsMemberCodeMr(kmMaster.getPacsMemberCodeMr());
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
