package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.repository.BankMasterRepository;
import com.cbs.middleware.service.BankMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankMaster}.
 */
@Service
@Transactional
public class BankMasterServiceImpl implements BankMasterService {

    private final Logger log = LoggerFactory.getLogger(BankMasterServiceImpl.class);

    private final BankMasterRepository bankMasterRepository;

    public BankMasterServiceImpl(BankMasterRepository bankMasterRepository) {
        this.bankMasterRepository = bankMasterRepository;
    }

    @Override
    public BankMaster save(BankMaster bankMaster) {
        log.debug("Request to save BankMaster : {}", bankMaster);
        return bankMasterRepository.save(bankMaster);
    }

    @Override
    public BankMaster update(BankMaster bankMaster) {
        log.debug("Request to update BankMaster : {}", bankMaster);
        return bankMasterRepository.save(bankMaster);
    }

    @Override
    public Optional<BankMaster> partialUpdate(BankMaster bankMaster) {
        log.debug("Request to partially update BankMaster : {}", bankMaster);

        return bankMasterRepository
            .findById(bankMaster.getId())
            .map(existingBankMaster -> {
                if (bankMaster.getBankCode() != null) {
                    existingBankMaster.setBankCode(bankMaster.getBankCode());
                }
                if (bankMaster.getBankName() != null) {
                    existingBankMaster.setBankName(bankMaster.getBankName());
                }

                return existingBankMaster;
            })
            .map(bankMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankMaster> findAll(Pageable pageable) {
        log.debug("Request to get all BankMasters");
        return bankMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankMaster> findOne(Long id) {
        log.debug("Request to get BankMaster : {}", id);
        return bankMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankMaster : {}", id);
        bankMasterRepository.deleteById(id);
    }
}
