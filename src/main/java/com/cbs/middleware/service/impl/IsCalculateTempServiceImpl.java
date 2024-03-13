package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.service.IsCalculateTempService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IsCalculateTemp}.
 */
@Service
@Transactional
public class IsCalculateTempServiceImpl implements IsCalculateTempService {

    private final Logger log = LoggerFactory.getLogger(IsCalculateTempServiceImpl.class);

    private final IsCalculateTempRepository isCalculateTempRepository;

    public IsCalculateTempServiceImpl(IsCalculateTempRepository isCalculateTempRepository) {
        this.isCalculateTempRepository = isCalculateTempRepository;
    }

    @Override
    public IsCalculateTemp save(IsCalculateTemp isCalculateTemp) {
        log.debug("Request to save IsCalculateTemp : {}", isCalculateTemp);
        return isCalculateTempRepository.save(isCalculateTemp);
    }

    @Override
    public IsCalculateTemp update(IsCalculateTemp isCalculateTemp) {
        log.debug("Request to update IsCalculateTemp : {}", isCalculateTemp);
        return isCalculateTempRepository.save(isCalculateTemp);
    }

    @Override
    public Optional<IsCalculateTemp> partialUpdate(IsCalculateTemp isCalculateTemp) {
        log.debug("Request to partially update IsCalculateTemp : {}", isCalculateTemp);

        return isCalculateTempRepository
            .findById(isCalculateTemp.getId())
            .map(existingIsCalculateTemp -> {
                if (isCalculateTemp.getSerialNo() != null) {
                    existingIsCalculateTemp.setSerialNo(isCalculateTemp.getSerialNo());
                }
                if (isCalculateTemp.getFinancialYear() != null) {
                    existingIsCalculateTemp.setFinancialYear(isCalculateTemp.getFinancialYear());
                }

                return existingIsCalculateTemp;
            })
            .map(isCalculateTempRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IsCalculateTemp> findAll(Pageable pageable) {
        log.debug("Request to get all IsCalculateTemps");
        return isCalculateTempRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IsCalculateTemp> findOne(Long id) {
        log.debug("Request to get IsCalculateTemp : {}", id);
        return isCalculateTempRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IsCalculateTemp : {}", id);
        isCalculateTempRepository.deleteById(id);
    }
}
