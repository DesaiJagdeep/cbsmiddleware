package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KarkhanaVasuli;
import com.cbs.middleware.repository.KarkhanaVasuliRepository;
import com.cbs.middleware.service.KarkhanaVasuliService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KarkhanaVasuli}.
 */
@Service
@Transactional
public class KarkhanaVasuliServiceImpl implements KarkhanaVasuliService {

    private final Logger log = LoggerFactory.getLogger(KarkhanaVasuliServiceImpl.class);

    private final KarkhanaVasuliRepository karkhanaVasuliRepository;

    public KarkhanaVasuliServiceImpl(KarkhanaVasuliRepository karkhanaVasuliRepository) {
        this.karkhanaVasuliRepository = karkhanaVasuliRepository;
    }

    @Override
    public KarkhanaVasuli save(KarkhanaVasuli karkhanaVasuli) {
        log.debug("Request to save KarkhanaVasuli : {}", karkhanaVasuli);
        return karkhanaVasuliRepository.save(karkhanaVasuli);
    }

    @Override
    public KarkhanaVasuli update(KarkhanaVasuli karkhanaVasuli) {
        log.debug("Request to update KarkhanaVasuli : {}", karkhanaVasuli);
        return karkhanaVasuliRepository.save(karkhanaVasuli);
    }

    @Override
    public Optional<KarkhanaVasuli> partialUpdate(KarkhanaVasuli karkhanaVasuli) {
        log.debug("Request to partially update KarkhanaVasuli : {}", karkhanaVasuli);

        return karkhanaVasuliRepository
            .findById(karkhanaVasuli.getId())
            .map(existingKarkhanaVasuli -> {
                if (karkhanaVasuli.getKhataNumber() != null) {
                    existingKarkhanaVasuli.setKhataNumber(karkhanaVasuli.getKhataNumber());
                }
                if (karkhanaVasuli.getKarkhanaName() != null) {
                    existingKarkhanaVasuli.setKarkhanaName(karkhanaVasuli.getKarkhanaName());
                }
                if (karkhanaVasuli.getSocietyName() != null) {
                    existingKarkhanaVasuli.setSocietyName(karkhanaVasuli.getSocietyName());
                }
                if (karkhanaVasuli.getTalukaName() != null) {
                    existingKarkhanaVasuli.setTalukaName(karkhanaVasuli.getTalukaName());
                }
                if (karkhanaVasuli.getBranchName() != null) {
                    existingKarkhanaVasuli.setBranchName(karkhanaVasuli.getBranchName());
                }
                if (karkhanaVasuli.getDefaulterName() != null) {
                    existingKarkhanaVasuli.setDefaulterName(karkhanaVasuli.getDefaulterName());
                }

                return existingKarkhanaVasuli;
            })
            .map(karkhanaVasuliRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KarkhanaVasuli> findAll(Pageable pageable) {
        log.debug("Request to get all KarkhanaVasulis");
        return karkhanaVasuliRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KarkhanaVasuli> findOne(Long id) {
        log.debug("Request to get KarkhanaVasuli : {}", id);
        return karkhanaVasuliRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KarkhanaVasuli : {}", id);
        karkhanaVasuliRepository.deleteById(id);
    }
}
