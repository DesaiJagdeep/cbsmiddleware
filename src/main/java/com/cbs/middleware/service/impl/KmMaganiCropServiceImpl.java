package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KmMaganiCrop;
import com.cbs.middleware.repository.KmMaganiCropRepository;
import com.cbs.middleware.service.KmMaganiCropService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KmMaganiCrop}.
 */
@Service
@Transactional
public class KmMaganiCropServiceImpl implements KmMaganiCropService {

    private final Logger log = LoggerFactory.getLogger(KmMaganiCropServiceImpl.class);

    private final KmMaganiCropRepository kmMaganiCropRepository;

    public KmMaganiCropServiceImpl(KmMaganiCropRepository kmMaganiCropRepository) {
        this.kmMaganiCropRepository = kmMaganiCropRepository;
    }

    @Override
    public KmMaganiCrop save(KmMaganiCrop kmMaganiCrop) {
        log.debug("Request to save KmMaganiCrop : {}", kmMaganiCrop);
        return kmMaganiCropRepository.save(kmMaganiCrop);
    }

    @Override
    public KmMaganiCrop update(KmMaganiCrop kmMaganiCrop) {
        log.debug("Request to update KmMaganiCrop : {}", kmMaganiCrop);
        return kmMaganiCropRepository.save(kmMaganiCrop);
    }

    @Override
    public Optional<KmMaganiCrop> partialUpdate(KmMaganiCrop kmMaganiCrop) {
        log.debug("Request to partially update KmMaganiCrop : {}", kmMaganiCrop);

        return kmMaganiCropRepository
            .findById(kmMaganiCrop.getId())
            .map(existingKmMaganiCrop -> {
                if (kmMaganiCrop.getCropName() != null) {
                    existingKmMaganiCrop.setCropName(kmMaganiCrop.getCropName());
                }

                return existingKmMaganiCrop;
            })
            .map(kmMaganiCropRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KmMaganiCrop> findAll(Pageable pageable) {
        log.debug("Request to get all KmMaganiCrops");
        return kmMaganiCropRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KmMaganiCrop> findOne(Long id) {
        log.debug("Request to get KmMaganiCrop : {}", id);
        return kmMaganiCropRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KmMaganiCrop : {}", id);
        kmMaganiCropRepository.deleteById(id);
    }
}
