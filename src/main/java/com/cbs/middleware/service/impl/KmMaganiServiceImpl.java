package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KmMagani;
import com.cbs.middleware.repository.KmMaganiRepository;
import com.cbs.middleware.service.KmMaganiService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KmMagani}.
 */
@Service
@Transactional
public class KmMaganiServiceImpl implements KmMaganiService {

    private final Logger log = LoggerFactory.getLogger(KmMaganiServiceImpl.class);

    private final KmMaganiRepository kmMaganiRepository;

    public KmMaganiServiceImpl(KmMaganiRepository kmMaganiRepository) {
        this.kmMaganiRepository = kmMaganiRepository;
    }

    @Override
    public KmMagani save(KmMagani kmMagani) {
        log.debug("Request to save KmMagani : {}", kmMagani);
        return kmMaganiRepository.save(kmMagani);
    }

    @Override
    public KmMagani update(KmMagani kmMagani) {
        log.debug("Request to update KmMagani : {}", kmMagani);
        return kmMaganiRepository.save(kmMagani);
    }

    @Override
    public Optional<KmMagani> partialUpdate(KmMagani kmMagani) {
        log.debug("Request to partially update KmMagani : {}", kmMagani);

        return kmMaganiRepository
            .findById(kmMagani.getId())
            .map(existingKmMagani -> {
                if (kmMagani.getKmNumber() != null) {
                    existingKmMagani.setKmNumber(kmMagani.getKmNumber());
                }
                if (kmMagani.getMemberNumber() != null) {
                    existingKmMagani.setMemberNumber(kmMagani.getMemberNumber());
                }
                if (kmMagani.getMemberName() != null) {
                    existingKmMagani.setMemberName(kmMagani.getMemberName());
                }
                if (kmMagani.getPacsNumber() != null) {
                    existingKmMagani.setPacsNumber(kmMagani.getPacsNumber());
                }
                if (kmMagani.getShare() != null) {
                    existingKmMagani.setShare(kmMagani.getShare());
                }
                if (kmMagani.getFinancialYear() != null) {
                    existingKmMagani.setFinancialYear(kmMagani.getFinancialYear());
                }
                if (kmMagani.getKmDate() != null) {
                    existingKmMagani.setKmDate(kmMagani.getKmDate());
                }
                if (kmMagani.getMaganiDate() != null) {
                    existingKmMagani.setMaganiDate(kmMagani.getMaganiDate());
                }

                return existingKmMagani;
            })
            .map(kmMaganiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KmMagani> findAll(Pageable pageable) {
        log.debug("Request to get all KmMaganis");
        return kmMaganiRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KmMagani> findOne(Long id) {
        log.debug("Request to get KmMagani : {}", id);
        return kmMaganiRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KmMagani : {}", id);
        kmMaganiRepository.deleteById(id);
    }
}
