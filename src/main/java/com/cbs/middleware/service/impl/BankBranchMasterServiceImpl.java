package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.BankBranchMaster;
import com.cbs.middleware.domain.BankMaster;
import com.cbs.middleware.domain.PacsMaster;
import com.cbs.middleware.domain.domainUtil.BranchForPacksList;
import com.cbs.middleware.repository.BankBranchMasterRepository;
import com.cbs.middleware.repository.PacsMasterRepository;
import com.cbs.middleware.service.BankBranchMasterService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankBranchMaster}.
 */
@Service
@Transactional
public class BankBranchMasterServiceImpl implements BankBranchMasterService {

    private final Logger log = LoggerFactory.getLogger(BankBranchMasterServiceImpl.class);

    private final BankBranchMasterRepository bankBranchMasterRepository;

    @Autowired
    PacsMasterRepository pacsMasterRepository;

    public BankBranchMasterServiceImpl(BankBranchMasterRepository bankBranchMasterRepository) {
        this.bankBranchMasterRepository = bankBranchMasterRepository;
    }

    @Override
    public BankBranchMaster save(BankBranchMaster bankBranchMaster) {
        log.debug("Request to save BankBranchMaster : {}", bankBranchMaster);
        return bankBranchMasterRepository.save(bankBranchMaster);
    }

    @Override
    public BankBranchMaster update(BankBranchMaster bankBranchMaster) {
        log.debug("Request to update BankBranchMaster : {}", bankBranchMaster);
        return bankBranchMasterRepository.save(bankBranchMaster);
    }

    @Override
    public Optional<BankBranchMaster> partialUpdate(BankBranchMaster bankBranchMaster) {
        log.debug("Request to partially update BankBranchMaster : {}", bankBranchMaster);

        return bankBranchMasterRepository
            .findById(bankBranchMaster.getId())
            .map(existingBankBranchMaster -> {
                if (bankBranchMaster.getBranchCode() != null) {
                    existingBankBranchMaster.setBranchCode(bankBranchMaster.getBranchCode());
                }
                if (bankBranchMaster.getBranchName() != null) {
                    existingBankBranchMaster.setBranchName(bankBranchMaster.getBranchName());
                }
                if (bankBranchMaster.getBranchAddress() != null) {
                    existingBankBranchMaster.setBranchAddress(bankBranchMaster.getBranchAddress());
                }

                return existingBankBranchMaster;
            })
            .map(bankBranchMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankBranchMaster> findAll(Pageable pageable) {
        log.debug("Request to get all BankBranchMasters");
        return bankBranchMasterRepository.findAll(pageable);
    }

    public Page<BankBranchMaster> findAllWithEagerRelationships(Pageable pageable) {
        return bankBranchMasterRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankBranchMaster> findOne(Long id) {
        log.debug("Request to get BankBranchMaster : {}", id);
        return bankBranchMasterRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankBranchMaster : {}", id);
        bankBranchMasterRepository.deleteById(id);
    }
}
