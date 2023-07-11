package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.AccountHolderMaster;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.service.AccountHolderMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountHolderMaster}.
 */
@Service
@Transactional
public class AccountHolderMasterServiceImpl implements AccountHolderMasterService {

    private final Logger log = LoggerFactory.getLogger(AccountHolderMasterServiceImpl.class);

    private final AccountHolderMasterRepository accountHolderMasterRepository;

    public AccountHolderMasterServiceImpl(AccountHolderMasterRepository accountHolderMasterRepository) {
        this.accountHolderMasterRepository = accountHolderMasterRepository;
    }

    @Override
    public AccountHolderMaster save(AccountHolderMaster accountHolderMaster) {
        log.debug("Request to save AccountHolderMaster : {}", accountHolderMaster);
        return accountHolderMasterRepository.save(accountHolderMaster);
    }

    @Override
    public AccountHolderMaster update(AccountHolderMaster accountHolderMaster) {
        log.debug("Request to update AccountHolderMaster : {}", accountHolderMaster);
        return accountHolderMasterRepository.save(accountHolderMaster);
    }

    @Override
    public Optional<AccountHolderMaster> partialUpdate(AccountHolderMaster accountHolderMaster) {
        log.debug("Request to partially update AccountHolderMaster : {}", accountHolderMaster);

        return accountHolderMasterRepository
            .findById(accountHolderMaster.getId())
            .map(existingAccountHolderMaster -> {
                if (accountHolderMaster.getAccountHolderCode() != null) {
                    existingAccountHolderMaster.setAccountHolderCode(accountHolderMaster.getAccountHolderCode());
                }
                if (accountHolderMaster.getAccountHolder() != null) {
                    existingAccountHolderMaster.setAccountHolder(accountHolderMaster.getAccountHolder());
                }

                return existingAccountHolderMaster;
            })
            .map(accountHolderMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountHolderMaster> findAll(Pageable pageable) {
        log.debug("Request to get all AccountHolderMasters");
        return accountHolderMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountHolderMaster> findOne(Long id) {
        log.debug("Request to get AccountHolderMaster : {}", id);
        return accountHolderMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountHolderMaster : {}", id);
        accountHolderMasterRepository.deleteById(id);
    }
}
