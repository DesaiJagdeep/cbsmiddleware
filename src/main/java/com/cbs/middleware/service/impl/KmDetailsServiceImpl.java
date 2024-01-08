package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KmDetails;
import com.cbs.middleware.repository.KmDetailsRepository;
import com.cbs.middleware.service.KmDetailsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbs.middleware.domain.KmDetails}.
 */
@Service
@Transactional
public class KmDetailsServiceImpl implements KmDetailsService {

    private final Logger log = LoggerFactory.getLogger(KmDetailsServiceImpl.class);

    private final KmDetailsRepository kmDetailsRepository;

    public KmDetailsServiceImpl(KmDetailsRepository kmDetailsRepository) {
        this.kmDetailsRepository = kmDetailsRepository;
    }

    @Override
    public KmDetails save(KmDetails kmDetails) {
        log.debug("Request to save KmDetails : {}", kmDetails);
        return kmDetailsRepository.save(kmDetails);
    }

    @Override
    public KmDetails update(KmDetails kmDetails) {
        log.debug("Request to update KmDetails : {}", kmDetails);
        return kmDetailsRepository.save(kmDetails);
    }

    @Override
    public Optional<KmDetails> partialUpdate(KmDetails kmDetails) {
        log.debug("Request to partially update KmDetails : {}", kmDetails);

        return kmDetailsRepository
            .findById(kmDetails.getId())
            .map(existingKmDetails -> {
                if (kmDetails.getShares() != null) {
                    existingKmDetails.setShares(kmDetails.getShares());
                }
                if (kmDetails.getSharesMr() != null) {
                    existingKmDetails.setSharesMr(kmDetails.getSharesMr());
                }
                if (kmDetails.getSugarShares() != null) {
                    existingKmDetails.setSugarShares(kmDetails.getSugarShares());
                }
                if (kmDetails.getSugarSharesMr() != null) {
                    existingKmDetails.setSugarSharesMr(kmDetails.getSugarSharesMr());
                }
                if (kmDetails.getDeposite() != null) {
                    existingKmDetails.setDeposite(kmDetails.getDeposite());
                }
                if (kmDetails.getDepositeMr() != null) {
                    existingKmDetails.setDepositeMr(kmDetails.getDepositeMr());
                }
                if (kmDetails.getDueLoan() != null) {
                    existingKmDetails.setDueLoan(kmDetails.getDueLoan());
                }
                if (kmDetails.getDueLoanMr() != null) {
                    existingKmDetails.setDueLoanMr(kmDetails.getDueLoanMr());
                }
                if (kmDetails.getDueAmount() != null) {
                    existingKmDetails.setDueAmount(kmDetails.getDueAmount());
                }
                if (kmDetails.getDueAmountMr() != null) {
                    existingKmDetails.setDueAmountMr(kmDetails.getDueAmountMr());
                }
                if (kmDetails.getDueDateMr() != null) {
                    existingKmDetails.setDueDateMr(kmDetails.getDueDateMr());
                }
                if (kmDetails.getDueDate() != null) {
                    existingKmDetails.setDueDate(kmDetails.getDueDate());
                }
                if (kmDetails.getKmDate() != null) {
                    existingKmDetails.setKmDate(kmDetails.getKmDate());
                }
                if (kmDetails.getKmDateMr() != null) {
                    existingKmDetails.setKmDateMr(kmDetails.getKmDateMr());
                }
                if (kmDetails.getKmFromDate() != null) {
                    existingKmDetails.setKmFromDate(kmDetails.getKmFromDate());
                }
                if (kmDetails.getKmFromDateMr() != null) {
                    existingKmDetails.setKmFromDateMr(kmDetails.getKmFromDateMr());
                }
                if (kmDetails.getKmToDate() != null) {
                    existingKmDetails.setKmToDate(kmDetails.getKmToDate());
                }
                if (kmDetails.getKmToDateMr() != null) {
                    existingKmDetails.setKmToDateMr(kmDetails.getKmToDateMr());
                }
                if (kmDetails.getBagayatHector() != null) {
                    existingKmDetails.setBagayatHector(kmDetails.getBagayatHector());
                }
                if (kmDetails.getBagayatHectorMr() != null) {
                    existingKmDetails.setBagayatHectorMr(kmDetails.getBagayatHectorMr());
                }
                if (kmDetails.getBagayatAre() != null) {
                    existingKmDetails.setBagayatAre(kmDetails.getBagayatAre());
                }
                if (kmDetails.getBagayatAreMr() != null) {
                    existingKmDetails.setBagayatAreMr(kmDetails.getBagayatAreMr());
                }
                if (kmDetails.getJirayatHector() != null) {
                    existingKmDetails.setJirayatHector(kmDetails.getJirayatHector());
                }
                if (kmDetails.getJirayatHectorMr() != null) {
                    existingKmDetails.setJirayatHectorMr(kmDetails.getJirayatHectorMr());
                }
                if (kmDetails.getJirayatAre() != null) {
                    existingKmDetails.setJirayatAre(kmDetails.getJirayatAre());
                }
                if (kmDetails.getJirayatAreMr() != null) {
                    existingKmDetails.setJirayatAreMr(kmDetails.getJirayatAreMr());
                }
                if (kmDetails.getZindagiAmt() != null) {
                    existingKmDetails.setZindagiAmt(kmDetails.getZindagiAmt());
                }
                if (kmDetails.getZindagiNo() != null) {
                    existingKmDetails.setZindagiNo(kmDetails.getZindagiNo());
                }
                if (kmDetails.getSurveyNo() != null) {
                    existingKmDetails.setSurveyNo(kmDetails.getSurveyNo());
                }
                if (kmDetails.getLandValue() != null) {
                    existingKmDetails.setLandValue(kmDetails.getLandValue());
                }
                if (kmDetails.getLandValueMr() != null) {
                    existingKmDetails.setLandValueMr(kmDetails.getLandValueMr());
                }
                if (kmDetails.geteAgreementAmt() != null) {
                    existingKmDetails.seteAgreementAmt(kmDetails.geteAgreementAmt());
                }
                if (kmDetails.geteAgreementAmtMr() != null) {
                    existingKmDetails.seteAgreementAmtMr(kmDetails.geteAgreementAmtMr());
                }
                if (kmDetails.geteAgreementDate() != null) {
                    existingKmDetails.seteAgreementDate(kmDetails.geteAgreementDate());
                }
                if (kmDetails.geteAgreementDateMr() != null) {
                    existingKmDetails.seteAgreementDateMr(kmDetails.geteAgreementDateMr());
                }
                if (kmDetails.getBojaAmount() != null) {
                    existingKmDetails.setBojaAmount(kmDetails.getBojaAmount());
                }
                if (kmDetails.getBojaAmountMr() != null) {
                    existingKmDetails.setBojaAmountMr(kmDetails.getBojaAmountMr());
                }
                if (kmDetails.getBojaDate() != null) {
                    existingKmDetails.setBojaDate(kmDetails.getBojaDate());
                }
                if (kmDetails.getBojaDateMr() != null) {
                    existingKmDetails.setBojaDateMr(kmDetails.getBojaDateMr());
                }

                return existingKmDetails;
            })
            .map(kmDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KmDetails> findAll(Pageable pageable) {
        log.debug("Request to get all KmDetails");
        return kmDetailsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KmDetails> findOne(Long id) {
        log.debug("Request to get KmDetails : {}", id);
        return kmDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KmDetails : {}", id);
        kmDetailsRepository.deleteById(id);
    }
}
