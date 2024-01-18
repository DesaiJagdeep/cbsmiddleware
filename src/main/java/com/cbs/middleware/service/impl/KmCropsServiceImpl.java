package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KmCrops;
import com.cbs.middleware.repository.KmCropsRepository;
import com.cbs.middleware.service.KmCropsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KmCrops}.
 */
@Service
@Transactional
public class KmCropsServiceImpl implements KmCropsService {

    private final Logger log = LoggerFactory.getLogger(KmCropsServiceImpl.class);

    private final KmCropsRepository kmCropsRepository;

    public KmCropsServiceImpl(KmCropsRepository kmCropsRepository) {
        this.kmCropsRepository = kmCropsRepository;
    }

    @Override
    public KmCrops save(KmCrops kmCrops) {
        log.debug("Request to save KmCrops : {}", kmCrops);
        return kmCropsRepository.save(kmCrops);
    }

    @Override
    public KmCrops update(KmCrops kmCrops) {
        log.debug("Request to update KmCrops : {}", kmCrops);
        return kmCropsRepository.save(kmCrops);
    }

    @Override
    public Optional<KmCrops> partialUpdate(KmCrops kmCrops) {
        log.debug("Request to partially update KmCrops : {}", kmCrops);

        return kmCropsRepository
            .findById(kmCrops.getId())
            .map(existingKmCrops -> {
                if (kmCrops.getHector() != null) {
                    existingKmCrops.setHector(kmCrops.getHector());
                }
                if (kmCrops.getHectorMr() != null) {
                    existingKmCrops.setHectorMr(kmCrops.getHectorMr());
                }
                if (kmCrops.getAre() != null) {
                    existingKmCrops.setAre(kmCrops.getAre());
                }
                if (kmCrops.getAreMr() != null) {
                    existingKmCrops.setAreMr(kmCrops.getAreMr());
                }
                if (kmCrops.getNoOfTree() != null) {
                    existingKmCrops.setNoOfTree(kmCrops.getNoOfTree());
                }
                if (kmCrops.getNoOfTreeMr() != null) {
                    existingKmCrops.setNoOfTreeMr(kmCrops.getNoOfTreeMr());
                }
                if (kmCrops.getDemand() != null) {
                    existingKmCrops.setDemand(kmCrops.getDemand());
                }
                if (kmCrops.getDemandMr() != null) {
                    existingKmCrops.setDemandMr(kmCrops.getDemandMr());
                }
                if (kmCrops.getSociety() != null) {
                    existingKmCrops.setSociety(kmCrops.getSociety());
                }
                if (kmCrops.getSocietyMr() != null) {
                    existingKmCrops.setSocietyMr(kmCrops.getSocietyMr());
                }
                if (kmCrops.getBankAmt() != null) {
                    existingKmCrops.setBankAmt(kmCrops.getBankAmt());
                }
                if (kmCrops.getBankAmtMr() != null) {
                    existingKmCrops.setBankAmtMr(kmCrops.getBankAmtMr());
                }
                if (kmCrops.getVibhagiAdhikari() != null) {
                    existingKmCrops.setVibhagiAdhikari(kmCrops.getVibhagiAdhikari());
                }
                if (kmCrops.getVibhagiAdhikariMr() != null) {
                    existingKmCrops.setVibhagiAdhikariMr(kmCrops.getVibhagiAdhikariMr());
                }
                if (kmCrops.getBranch() != null) {
                    existingKmCrops.setBranch(kmCrops.getBranch());
                }
                if (kmCrops.getBranchMr() != null) {
                    existingKmCrops.setBranchMr(kmCrops.getBranchMr());
                }
                if (kmCrops.getInspAmt() != null) {
                    existingKmCrops.setInspAmt(kmCrops.getInspAmt());
                }
                if (kmCrops.getInspAmtMr() != null) {
                    existingKmCrops.setInspAmtMr(kmCrops.getInspAmtMr());
                }

                return existingKmCrops;
            })
            .map(kmCropsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KmCrops> findAll(Pageable pageable) {
        log.debug("Request to get all KmCrops");
        return kmCropsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KmCrops> findOne(Long id) {
        log.debug("Request to get KmCrops : {}", id);
        return kmCropsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KmCrops : {}", id);
        kmCropsRepository.deleteById(id);
    }
}
