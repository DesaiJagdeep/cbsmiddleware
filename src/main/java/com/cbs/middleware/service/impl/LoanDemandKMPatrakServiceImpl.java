package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.LoanDemandKMPatrak;
import com.cbs.middleware.repository.LoanDemandKMPatrakRepository;
import com.cbs.middleware.service.LoanDemandKMPatrakService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanDemandKMPatrak}.
 */
@Service
@Transactional
public class LoanDemandKMPatrakServiceImpl implements LoanDemandKMPatrakService {

    private final Logger log = LoggerFactory.getLogger(LoanDemandKMPatrakServiceImpl.class);

    private final LoanDemandKMPatrakRepository loanDemandKMPatrakRepository;

    public LoanDemandKMPatrakServiceImpl(LoanDemandKMPatrakRepository loanDemandKMPatrakRepository) {
        this.loanDemandKMPatrakRepository = loanDemandKMPatrakRepository;
    }

    @Override
    public LoanDemandKMPatrak save(LoanDemandKMPatrak loanDemandKMPatrak) {
        log.debug("Request to save LoanDemandKMPatrak : {}", loanDemandKMPatrak);
        return loanDemandKMPatrakRepository.save(loanDemandKMPatrak);
    }

    @Override
    public LoanDemandKMPatrak update(LoanDemandKMPatrak loanDemandKMPatrak) {
        log.debug("Request to update LoanDemandKMPatrak : {}", loanDemandKMPatrak);
        return loanDemandKMPatrakRepository.save(loanDemandKMPatrak);
    }

    @Override
    public Optional<LoanDemandKMPatrak> partialUpdate(LoanDemandKMPatrak loanDemandKMPatrak) {
        log.debug("Request to partially update LoanDemandKMPatrak : {}", loanDemandKMPatrak);

        return loanDemandKMPatrakRepository
            .findById(loanDemandKMPatrak.getId())
            .map(existingLoanDemandKMPatrak -> {
                if (loanDemandKMPatrak.getDemandCode() != null) {
                    existingLoanDemandKMPatrak.setDemandCode(loanDemandKMPatrak.getDemandCode());
                }
                if (loanDemandKMPatrak.getDate() != null) {
                    existingLoanDemandKMPatrak.setDate(loanDemandKMPatrak.getDate());
                }
                if (loanDemandKMPatrak.getKmDate() != null) {
                    existingLoanDemandKMPatrak.setKmDate(loanDemandKMPatrak.getKmDate());
                }
                if (loanDemandKMPatrak.getShares() != null) {
                    existingLoanDemandKMPatrak.setShares(loanDemandKMPatrak.getShares());
                }
                if (loanDemandKMPatrak.getPid() != null) {
                    existingLoanDemandKMPatrak.setPid(loanDemandKMPatrak.getPid());
                }
                if (loanDemandKMPatrak.getCode() != null) {
                    existingLoanDemandKMPatrak.setCode(loanDemandKMPatrak.getCode());
                }
                if (loanDemandKMPatrak.getDemandArea() != null) {
                    existingLoanDemandKMPatrak.setDemandArea(loanDemandKMPatrak.getDemandArea());
                }
                if (loanDemandKMPatrak.getCropType() != null) {
                    existingLoanDemandKMPatrak.setCropType(loanDemandKMPatrak.getCropType());
                }
                if (loanDemandKMPatrak.getTotal() != null) {
                    existingLoanDemandKMPatrak.setTotal(loanDemandKMPatrak.getTotal());
                }
                if (loanDemandKMPatrak.getCheck() != null) {
                    existingLoanDemandKMPatrak.setCheck(loanDemandKMPatrak.getCheck());
                }
                if (loanDemandKMPatrak.getGoods() != null) {
                    existingLoanDemandKMPatrak.setGoods(loanDemandKMPatrak.getGoods());
                }
                if (loanDemandKMPatrak.getSharesn() != null) {
                    existingLoanDemandKMPatrak.setSharesn(loanDemandKMPatrak.getSharesn());
                }
                if (loanDemandKMPatrak.getHn() != null) {
                    existingLoanDemandKMPatrak.setHn(loanDemandKMPatrak.getHn());
                }
                if (loanDemandKMPatrak.getArea() != null) {
                    existingLoanDemandKMPatrak.setArea(loanDemandKMPatrak.getArea());
                }
                if (loanDemandKMPatrak.gethAmount() != null) {
                    existingLoanDemandKMPatrak.sethAmount(loanDemandKMPatrak.gethAmount());
                }
                if (loanDemandKMPatrak.getName() != null) {
                    existingLoanDemandKMPatrak.setName(loanDemandKMPatrak.getName());
                }
                if (loanDemandKMPatrak.getKhateCode() != null) {
                    existingLoanDemandKMPatrak.setKhateCode(loanDemandKMPatrak.getKhateCode());
                }
                if (loanDemandKMPatrak.getRemaining() != null) {
                    existingLoanDemandKMPatrak.setRemaining(loanDemandKMPatrak.getRemaining());
                }
                if (loanDemandKMPatrak.getArrears() != null) {
                    existingLoanDemandKMPatrak.setArrears(loanDemandKMPatrak.getArrears());
                }
                if (loanDemandKMPatrak.getKmAcceptance() != null) {
                    existingLoanDemandKMPatrak.setKmAcceptance(loanDemandKMPatrak.getKmAcceptance());
                }
                if (loanDemandKMPatrak.getPaidDate() != null) {
                    existingLoanDemandKMPatrak.setPaidDate(loanDemandKMPatrak.getPaidDate());
                }
                if (loanDemandKMPatrak.getKmCode() != null) {
                    existingLoanDemandKMPatrak.setKmCode(loanDemandKMPatrak.getKmCode());
                }
                if (loanDemandKMPatrak.getPendingDate() != null) {
                    existingLoanDemandKMPatrak.setPendingDate(loanDemandKMPatrak.getPendingDate());
                }
                if (loanDemandKMPatrak.getDepositeDate() != null) {
                    existingLoanDemandKMPatrak.setDepositeDate(loanDemandKMPatrak.getDepositeDate());
                }
                if (loanDemandKMPatrak.getAccountNumberB() != null) {
                    existingLoanDemandKMPatrak.setAccountNumberB(loanDemandKMPatrak.getAccountNumberB());
                }
                if (loanDemandKMPatrak.getLoanDue() != null) {
                    existingLoanDemandKMPatrak.setLoanDue(loanDemandKMPatrak.getLoanDue());
                }
                if (loanDemandKMPatrak.getArrearsB() != null) {
                    existingLoanDemandKMPatrak.setArrearsB(loanDemandKMPatrak.getArrearsB());
                }
                if (loanDemandKMPatrak.getDueDateB() != null) {
                    existingLoanDemandKMPatrak.setDueDateB(loanDemandKMPatrak.getDueDateB());
                }
                if (loanDemandKMPatrak.getCropB() != null) {
                    existingLoanDemandKMPatrak.setCropB(loanDemandKMPatrak.getCropB());
                }
                if (loanDemandKMPatrak.getKmAcceptanceB() != null) {
                    existingLoanDemandKMPatrak.setKmAcceptanceB(loanDemandKMPatrak.getKmAcceptanceB());
                }
                if (loanDemandKMPatrak.getKmCodeB() != null) {
                    existingLoanDemandKMPatrak.setKmCodeB(loanDemandKMPatrak.getKmCodeB());
                }
                if (loanDemandKMPatrak.gethAgreementNumberB() != null) {
                    existingLoanDemandKMPatrak.sethAgreementNumberB(loanDemandKMPatrak.gethAgreementNumberB());
                }
                if (loanDemandKMPatrak.gethAgreementAreaB() != null) {
                    existingLoanDemandKMPatrak.sethAgreementAreaB(loanDemandKMPatrak.gethAgreementAreaB());
                }
                if (loanDemandKMPatrak.gethAgreementBurdenB() != null) {
                    existingLoanDemandKMPatrak.sethAgreementBurdenB(loanDemandKMPatrak.gethAgreementBurdenB());
                }
                if (loanDemandKMPatrak.getTotalPaidB() != null) {
                    existingLoanDemandKMPatrak.setTotalPaidB(loanDemandKMPatrak.getTotalPaidB());
                }
                if (loanDemandKMPatrak.getDemandAreaB() != null) {
                    existingLoanDemandKMPatrak.setDemandAreaB(loanDemandKMPatrak.getDemandAreaB());
                }
                if (loanDemandKMPatrak.getCheckInTheFormOfPaymentB() != null) {
                    existingLoanDemandKMPatrak.setCheckInTheFormOfPaymentB(loanDemandKMPatrak.getCheckInTheFormOfPaymentB());
                }
                if (loanDemandKMPatrak.getSharesB() != null) {
                    existingLoanDemandKMPatrak.setSharesB(loanDemandKMPatrak.getSharesB());
                }
                if (loanDemandKMPatrak.getVasulPatraRepaymentDateB() != null) {
                    existingLoanDemandKMPatrak.setVasulPatraRepaymentDateB(loanDemandKMPatrak.getVasulPatraRepaymentDateB());
                }

                return existingLoanDemandKMPatrak;
            })
            .map(loanDemandKMPatrakRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoanDemandKMPatrak> findAll(Pageable pageable) {
        log.debug("Request to get all LoanDemandKMPatraks");
        return loanDemandKMPatrakRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoanDemandKMPatrak> findOne(Long id) {
        log.debug("Request to get LoanDemandKMPatrak : {}", id);
        return loanDemandKMPatrakRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoanDemandKMPatrak : {}", id);
        loanDemandKMPatrakRepository.deleteById(id);
    }
}
