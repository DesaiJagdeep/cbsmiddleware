package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KamalCrop;
import com.cbs.middleware.repository.KamalCropRepository;
import com.cbs.middleware.service.KamalCropService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KamalCrop}.
 */
@Service
@Transactional
public class KamalCropServiceImpl implements KamalCropService {

    private final Logger log = LoggerFactory.getLogger(KamalCropServiceImpl.class);

    private final KamalCropRepository kamalCropRepository;

    public KamalCropServiceImpl(KamalCropRepository kamalCropRepository) {
        this.kamalCropRepository = kamalCropRepository;
    }

    @Override
    public KamalCrop save(KamalCrop kamalCrop) {
        log.debug("Request to save KamalCrop : {}", kamalCrop);
        return kamalCropRepository.save(kamalCrop);
    }

    @Override
    public KamalCrop update(KamalCrop kamalCrop) {
        log.debug("Request to update KamalCrop : {}", kamalCrop);
        return kamalCropRepository.save(kamalCrop);
    }

    @Override
    public Optional<KamalCrop> partialUpdate(KamalCrop kamalCrop) {
        log.debug("Request to partially update KamalCrop : {}", kamalCrop);

        return kamalCropRepository
            .findById(kamalCrop.getId())
            .map(existingKamalCrop -> {
                if (kamalCrop.getPacsNumber() != null) {
                    existingKamalCrop.setPacsNumber(kamalCrop.getPacsNumber());
                }
                if (kamalCrop.getMemNo() != null) {
                    existingKamalCrop.setMemNo(kamalCrop.getMemNo());
                }
                if (kamalCrop.getMemHector() != null) {
                    existingKamalCrop.setMemHector(kamalCrop.getMemHector());
                }
                if (kamalCrop.getMemNoMr() != null) {
                    existingKamalCrop.setMemNoMr(kamalCrop.getMemNoMr());
                }
                if (kamalCrop.getMemHectorMr() != null) {
                    existingKamalCrop.setMemHectorMr(kamalCrop.getMemHectorMr());
                }
                if (kamalCrop.getMemAar() != null) {
                    existingKamalCrop.setMemAar(kamalCrop.getMemAar());
                }
                if (kamalCrop.getMemAarMr() != null) {
                    existingKamalCrop.setMemAarMr(kamalCrop.getMemAarMr());
                }

                return existingKamalCrop;
            })
            .map(kamalCropRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KamalCrop> findAll(Pageable pageable) {
        log.debug("Request to get all KamalCrops");
        return kamalCropRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KamalCrop> findOne(Long id) {
        log.debug("Request to get KamalCrop : {}", id);
        return kamalCropRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KamalCrop : {}", id);
        kamalCropRepository.deleteById(id);
    }
}