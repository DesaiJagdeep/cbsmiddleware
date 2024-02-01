package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.repository.KamalSocietyRepository;
import com.cbs.middleware.service.KamalSocietyService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KamalSociety}.
 */
@Service
@Transactional
public class KamalSocietyServiceImpl implements KamalSocietyService {

    private final Logger log = LoggerFactory.getLogger(KamalSocietyServiceImpl.class);

    private final KamalSocietyRepository kamalSocietyRepository;

    public KamalSocietyServiceImpl(KamalSocietyRepository kamalSocietyRepository) {
        this.kamalSocietyRepository = kamalSocietyRepository;
    }

    @Override
    public KamalSociety save(KamalSociety kamalSociety) {
        log.debug("Request to save KamalSociety : {}", kamalSociety);
        return kamalSocietyRepository.save(kamalSociety);
    }

    @Override
    public KamalSociety update(KamalSociety kamalSociety) {
        log.debug("Request to update KamalSociety : {}", kamalSociety);
        return kamalSocietyRepository.save(kamalSociety);
    }

    @Override
    public Optional<KamalSociety> partialUpdate(KamalSociety kamalSociety) {
        log.debug("Request to partially update KamalSociety : {}", kamalSociety);

        return kamalSocietyRepository
            .findById(kamalSociety.getId())
            .map(existingKamalSociety -> {
                if (kamalSociety.getFinancialYear() != null) {
                    existingKamalSociety.setFinancialYear(kamalSociety.getFinancialYear());
                }
                if (kamalSociety.getKmDate() != null) {
                    existingKamalSociety.setKmDate(kamalSociety.getKmDate());
                }

                return existingKamalSociety;
            })
            .map(kamalSocietyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KamalSociety> findAll(Pageable pageable) {
        log.debug("Request to get all KamalSocieties");
        return kamalSocietyRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KamalSociety> findOne(Long id) {
        log.debug("Request to get KamalSociety : {}", id);
        return kamalSocietyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KamalSociety : {}", id);
        kamalSocietyRepository.deleteById(id);
    }
}
