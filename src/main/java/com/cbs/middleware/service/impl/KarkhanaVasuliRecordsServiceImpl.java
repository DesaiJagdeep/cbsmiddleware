package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KarkhanaVasuliRecords;
import com.cbs.middleware.repository.KarkhanaVasuliRecordsRepository;
import com.cbs.middleware.service.KarkhanaVasuliRecordsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KarkhanaVasuliRecords}.
 */
@Service
@Transactional
public class KarkhanaVasuliRecordsServiceImpl implements KarkhanaVasuliRecordsService {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliRecordsServiceImpl.class);

    private final KarkhanaVasuliRecordsRepository karkhanaVasuliRecordsRepository;

    public KarkhanaVasuliRecordsServiceImpl(KarkhanaVasuliRecordsRepository karkhanaVasuliRecordsRepository) {
        this.karkhanaVasuliRecordsRepository = karkhanaVasuliRecordsRepository;
    }

    @Override
    public KarkhanaVasuliRecords save(KarkhanaVasuliRecords karkhanaVasuliRecords) {
        log.debug("Request to save KarkhanaVasuliRecords : {}", karkhanaVasuliRecords);
        return karkhanaVasuliRecordsRepository.save(karkhanaVasuliRecords);
    }

    @Override
    public KarkhanaVasuliRecords update(KarkhanaVasuliRecords karkhanaVasuliRecords) {
        log.debug("Request to update KarkhanaVasuliRecords : {}", karkhanaVasuliRecords);
        return karkhanaVasuliRecordsRepository.save(karkhanaVasuliRecords);
    }

    @Override
    public Optional<KarkhanaVasuliRecords> partialUpdate(KarkhanaVasuliRecords karkhanaVasuliRecords) {
        log.debug("Request to partially update KarkhanaVasuliRecords : {}", karkhanaVasuliRecords);

        return karkhanaVasuliRecordsRepository
            .findById(karkhanaVasuliRecords.getId())
            .map(existingKarkhanaVasuliRecords -> {
                if (karkhanaVasuliRecords.getFactoryMemberCode() != null) {
                    existingKarkhanaVasuliRecords.setFactoryMemberCode(karkhanaVasuliRecords.getFactoryMemberCode());
                }
                if (karkhanaVasuliRecords.getKarkhanaMemberCodeMr() != null) {
                    existingKarkhanaVasuliRecords.setKarkhanaMemberCodeMr(karkhanaVasuliRecords.getKarkhanaMemberCodeMr());
                }
                if (karkhanaVasuliRecords.getMemberName() != null) {
                    existingKarkhanaVasuliRecords.setMemberName(karkhanaVasuliRecords.getMemberName());
                }
                if (karkhanaVasuliRecords.getMemberNameMr() != null) {
                    existingKarkhanaVasuliRecords.setMemberNameMr(karkhanaVasuliRecords.getMemberNameMr());
                }
                if (karkhanaVasuliRecords.getVillageName() != null) {
                    existingKarkhanaVasuliRecords.setVillageName(karkhanaVasuliRecords.getVillageName());
                }
                if (karkhanaVasuliRecords.getVillageNameMr() != null) {
                    existingKarkhanaVasuliRecords.setVillageNameMr(karkhanaVasuliRecords.getVillageNameMr());
                }
                if (karkhanaVasuliRecords.getAmount() != null) {
                    existingKarkhanaVasuliRecords.setAmount(karkhanaVasuliRecords.getAmount());
                }
                if (karkhanaVasuliRecords.getAmountMr() != null) {
                    existingKarkhanaVasuliRecords.setAmountMr(karkhanaVasuliRecords.getAmountMr());
                }
                if (karkhanaVasuliRecords.getSavingAccNo() != null) {
                    existingKarkhanaVasuliRecords.setSavingAccNo(karkhanaVasuliRecords.getSavingAccNo());
                }
                if (karkhanaVasuliRecords.getSavingAccNoMr() != null) {
                    existingKarkhanaVasuliRecords.setSavingAccNoMr(karkhanaVasuliRecords.getSavingAccNoMr());
                }
                if (karkhanaVasuliRecords.getStatus() != null) {
                    existingKarkhanaVasuliRecords.setStatus(karkhanaVasuliRecords.getStatus());
                }

                return existingKarkhanaVasuliRecords;
            })
            .map(karkhanaVasuliRecordsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KarkhanaVasuliRecords> findAll(Pageable pageable) {
        log.debug("Request to get all KarkhanaVasuliRecords");
        return karkhanaVasuliRecordsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KarkhanaVasuliRecords> findOne(Long id) {
        log.debug("Request to get KarkhanaVasuliRecords : {}", id);
        return karkhanaVasuliRecordsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KarkhanaVasuliRecords : {}", id);
        karkhanaVasuliRecordsRepository.deleteById(id);
    }
}
