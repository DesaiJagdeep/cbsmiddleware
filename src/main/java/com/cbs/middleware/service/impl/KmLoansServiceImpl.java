package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KmLoans;
import com.cbs.middleware.repository.KmLoansRepository;
import com.cbs.middleware.service.KmLoansService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbs.middleware.domain.KmLoans}.
 */
@Service
@Transactional
public class KmLoansServiceImpl implements KmLoansService {

    private final Logger log = LoggerFactory.getLogger(KmLoansServiceImpl.class);

    private final KmLoansRepository kmLoansRepository;

    public KmLoansServiceImpl(KmLoansRepository kmLoansRepository) {
        this.kmLoansRepository = kmLoansRepository;
    }

    @Override
    public KmLoans save(KmLoans kmLoans) {
        log.debug("Request to save KmLoans : {}", kmLoans);
        return kmLoansRepository.save(kmLoans);
    }

    @Override
    public KmLoans update(KmLoans kmLoans) {
        log.debug("Request to update KmLoans : {}", kmLoans);
        return kmLoansRepository.save(kmLoans);
    }

    @Override
    public Optional<KmLoans> partialUpdate(KmLoans kmLoans) {
        log.debug("Request to partially update KmLoans : {}", kmLoans);

        return kmLoansRepository
            .findById(kmLoans.getId())
            .map(existingKmLoans -> {
                if (kmLoans.getCropName() != null) {
                    existingKmLoans.setCropName(kmLoans.getCropName());
                }
                if (kmLoans.getCropNameMr() != null) {
                    existingKmLoans.setCropNameMr(kmLoans.getCropNameMr());
                }
                if (kmLoans.getLoanDate() != null) {
                    existingKmLoans.setLoanDate(kmLoans.getLoanDate());
                }
                if (kmLoans.getLoanAmount() != null) {
                    existingKmLoans.setLoanAmount(kmLoans.getLoanAmount());
                }
                if (kmLoans.getLoanAmountMr() != null) {
                    existingKmLoans.setLoanAmountMr(kmLoans.getLoanAmountMr());
                }
                if (kmLoans.getAre() != null) {
                    existingKmLoans.setAre(kmLoans.getAre());
                }
                if (kmLoans.getAreMr() != null) {
                    existingKmLoans.setAreMr(kmLoans.getAreMr());
                }
                if (kmLoans.getReceivableAmt() != null) {
                    existingKmLoans.setReceivableAmt(kmLoans.getReceivableAmt());
                }
                if (kmLoans.getReceivableAmtMr() != null) {
                    existingKmLoans.setReceivableAmtMr(kmLoans.getReceivableAmtMr());
                }
                if (kmLoans.getDueAmt() != null) {
                    existingKmLoans.setDueAmt(kmLoans.getDueAmt());
                }
                if (kmLoans.getDueAmtMr() != null) {
                    existingKmLoans.setDueAmtMr(kmLoans.getDueAmtMr());
                }
                if (kmLoans.getDueDate() != null) {
                    existingKmLoans.setDueDate(kmLoans.getDueDate());
                }

                return existingKmLoans;
            })
            .map(kmLoansRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KmLoans> findAll(Pageable pageable) {
        log.debug("Request to get all KmLoans");
        return kmLoansRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KmLoans> findOne(Long id) {
        log.debug("Request to get KmLoans : {}", id);
        return kmLoansRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KmLoans : {}", id);
        kmLoansRepository.deleteById(id);
    }
}
