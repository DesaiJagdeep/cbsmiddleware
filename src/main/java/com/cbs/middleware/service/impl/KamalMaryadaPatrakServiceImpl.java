package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KamalMaryadaPatrak;
import com.cbs.middleware.repository.KamalMaryadaPatrakRepository;
import com.cbs.middleware.service.KamalMaryadaPatrakService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KamalMaryadaPatrak}.
 */
@Service
@Transactional
public class KamalMaryadaPatrakServiceImpl implements KamalMaryadaPatrakService {

    private final Logger log = LoggerFactory.getLogger(KamalMaryadaPatrakServiceImpl.class);

    private final KamalMaryadaPatrakRepository kamalMaryadaPatrakRepository;

    public KamalMaryadaPatrakServiceImpl(KamalMaryadaPatrakRepository kamalMaryadaPatrakRepository) {
        this.kamalMaryadaPatrakRepository = kamalMaryadaPatrakRepository;
    }

    @Override
    public KamalMaryadaPatrak save(KamalMaryadaPatrak kamalMaryadaPatrak) {
        log.debug("Request to save KamalMaryadaPatrak : {}", kamalMaryadaPatrak);
        return kamalMaryadaPatrakRepository.save(kamalMaryadaPatrak);
    }

    @Override
    public KamalMaryadaPatrak update(KamalMaryadaPatrak kamalMaryadaPatrak) {
        log.debug("Request to update KamalMaryadaPatrak : {}", kamalMaryadaPatrak);
        return kamalMaryadaPatrakRepository.save(kamalMaryadaPatrak);
    }

    @Override
    public Optional<KamalMaryadaPatrak> partialUpdate(KamalMaryadaPatrak kamalMaryadaPatrak) {
        log.debug("Request to partially update KamalMaryadaPatrak : {}", kamalMaryadaPatrak);

        return kamalMaryadaPatrakRepository
            .findById(kamalMaryadaPatrak.getId())
            .map(existingKamalMaryadaPatrak -> {
                if (kamalMaryadaPatrak.getCropLoan() != null) {
                    existingKamalMaryadaPatrak.setCropLoan(kamalMaryadaPatrak.getCropLoan());
                }
                if (kamalMaryadaPatrak.getKmChart() != null) {
                    existingKamalMaryadaPatrak.setKmChart(kamalMaryadaPatrak.getKmChart());
                }
                if (kamalMaryadaPatrak.getDemand() != null) {
                    existingKamalMaryadaPatrak.setDemand(kamalMaryadaPatrak.getDemand());
                }
                if (kamalMaryadaPatrak.getKmSummary() != null) {
                    existingKamalMaryadaPatrak.setKmSummary(kamalMaryadaPatrak.getKmSummary());
                }
                if (kamalMaryadaPatrak.getKmDate() != null) {
                    existingKamalMaryadaPatrak.setKmDate(kamalMaryadaPatrak.getKmDate());
                }
                if (kamalMaryadaPatrak.getToKMDate() != null) {
                    existingKamalMaryadaPatrak.setToKMDate(kamalMaryadaPatrak.getToKMDate());
                }
                if (kamalMaryadaPatrak.getCoverPage() != null) {
                    existingKamalMaryadaPatrak.setCoverPage(kamalMaryadaPatrak.getCoverPage());
                }
                if (kamalMaryadaPatrak.getCropTypeNumber() != null) {
                    existingKamalMaryadaPatrak.setCropTypeNumber(kamalMaryadaPatrak.getCropTypeNumber());
                }
                if (kamalMaryadaPatrak.getCropType() != null) {
                    existingKamalMaryadaPatrak.setCropType(kamalMaryadaPatrak.getCropType());
                }
                if (kamalMaryadaPatrak.getFromHector() != null) {
                    existingKamalMaryadaPatrak.setFromHector(kamalMaryadaPatrak.getFromHector());
                }
                if (kamalMaryadaPatrak.getToHector() != null) {
                    existingKamalMaryadaPatrak.setToHector(kamalMaryadaPatrak.getToHector());
                }
                if (kamalMaryadaPatrak.getDefaulterName() != null) {
                    existingKamalMaryadaPatrak.setDefaulterName(kamalMaryadaPatrak.getDefaulterName());
                }
                if (kamalMaryadaPatrak.getSuchakName() != null) {
                    existingKamalMaryadaPatrak.setSuchakName(kamalMaryadaPatrak.getSuchakName());
                }
                if (kamalMaryadaPatrak.getAnumodakName() != null) {
                    existingKamalMaryadaPatrak.setAnumodakName(kamalMaryadaPatrak.getAnumodakName());
                }
                if (kamalMaryadaPatrak.getMeetingDate() != null) {
                    existingKamalMaryadaPatrak.setMeetingDate(kamalMaryadaPatrak.getMeetingDate());
                }
                if (kamalMaryadaPatrak.getResolutionNumber() != null) {
                    existingKamalMaryadaPatrak.setResolutionNumber(kamalMaryadaPatrak.getResolutionNumber());
                }

                return existingKamalMaryadaPatrak;
            })
            .map(kamalMaryadaPatrakRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KamalMaryadaPatrak> findAll(Pageable pageable) {
        log.debug("Request to get all KamalMaryadaPatraks");
        return kamalMaryadaPatrakRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KamalMaryadaPatrak> findOne(Long id) {
        log.debug("Request to get KamalMaryadaPatrak : {}", id);
        return kamalMaryadaPatrakRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KamalMaryadaPatrak : {}", id);
        kamalMaryadaPatrakRepository.deleteById(id);
    }
}
