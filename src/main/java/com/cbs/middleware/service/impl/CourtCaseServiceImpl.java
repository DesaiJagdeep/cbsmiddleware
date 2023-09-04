package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.service.CourtCaseService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourtCase}.
 */
@Service
@Transactional
public class CourtCaseServiceImpl implements CourtCaseService {

    private final Logger log = LoggerFactory.getLogger(CourtCaseServiceImpl.class);

    private final CourtCaseRepository courtCaseRepository;

    public CourtCaseServiceImpl(CourtCaseRepository courtCaseRepository) {
        this.courtCaseRepository = courtCaseRepository;
    }

    @Override
    public CourtCase save(CourtCase courtCase) {
        log.debug("Request to save CourtCase : {}", courtCase);
        return courtCaseRepository.save(courtCase);
    }

    @Override
    public CourtCase update(CourtCase courtCase) {
        log.debug("Request to update CourtCase : {}", courtCase);
        return courtCaseRepository.save(courtCase);
    }

    @Override
    public Optional<CourtCase> partialUpdate(CourtCase courtCase) {
        log.debug("Request to partially update CourtCase : {}", courtCase);

        return courtCaseRepository
            .findById(courtCase.getId())
            .map(existingCourtCase -> {
                if (courtCase.getSrNo() != null) {
                    existingCourtCase.setSrNo(courtCase.getSrNo());
                }
                if (courtCase.getAccountNo() != null) {
                    existingCourtCase.setAccountNo(courtCase.getAccountNo());
                }
                if (courtCase.getNameOfDefaulter() != null) {
                    existingCourtCase.setNameOfDefaulter(courtCase.getNameOfDefaulter());
                }
                if (courtCase.getAddress() != null) {
                    existingCourtCase.setAddress(courtCase.getAddress());
                }
                if (courtCase.getLoanType() != null) {
                    existingCourtCase.setLoanType(courtCase.getLoanType());
                }
                if (courtCase.getLoanAmount() != null) {
                    existingCourtCase.setLoanAmount(courtCase.getLoanAmount());
                }
                if (courtCase.getLoanDate() != null) {
                    existingCourtCase.setLoanDate(courtCase.getLoanDate());
                }
                if (courtCase.getTermOfLoan() != null) {
                    existingCourtCase.setTermOfLoan(courtCase.getTermOfLoan());
                }
                if (courtCase.getInterestRate() != null) {
                    existingCourtCase.setInterestRate(courtCase.getInterestRate());
                }
                if (courtCase.getInstallmentAmount() != null) {
                    existingCourtCase.setInstallmentAmount(courtCase.getInstallmentAmount());
                }
                if (courtCase.getTotalCredit() != null) {
                    existingCourtCase.setTotalCredit(courtCase.getTotalCredit());
                }
                if (courtCase.getBalance() != null) {
                    existingCourtCase.setBalance(courtCase.getBalance());
                }
                if (courtCase.getInterestPaid() != null) {
                    existingCourtCase.setInterestPaid(courtCase.getInterestPaid());
                }
                if (courtCase.getPenalInterestPaid() != null) {
                    existingCourtCase.setPenalInterestPaid(courtCase.getPenalInterestPaid());
                }
                if (courtCase.getDueAmount() != null) {
                    existingCourtCase.setDueAmount(courtCase.getDueAmount());
                }
                if (courtCase.getDueDate() != null) {
                    existingCourtCase.setDueDate(courtCase.getDueDate());
                }
                if (courtCase.getDueInterest() != null) {
                    existingCourtCase.setDueInterest(courtCase.getDueInterest());
                }
                if (courtCase.getDuePenalInterest() != null) {
                    existingCourtCase.setDuePenalInterest(courtCase.getDuePenalInterest());
                }
                if (courtCase.getDueMoreInterest() != null) {
                    existingCourtCase.setDueMoreInterest(courtCase.getDueMoreInterest());
                }
                if (courtCase.getInterestRecivable() != null) {
                    existingCourtCase.setInterestRecivable(courtCase.getInterestRecivable());
                }
                if (courtCase.getGaurentorOne() != null) {
                    existingCourtCase.setGaurentorOne(courtCase.getGaurentorOne());
                }
                if (courtCase.getGaurentorOneAddress() != null) {
                    existingCourtCase.setGaurentorOneAddress(courtCase.getGaurentorOneAddress());
                }
                if (courtCase.getGaurentorTwo() != null) {
                    existingCourtCase.setGaurentorTwo(courtCase.getGaurentorTwo());
                }
                if (courtCase.getGaurentorTwoAddress() != null) {
                    existingCourtCase.setGaurentorTwoAddress(courtCase.getGaurentorTwoAddress());
                }
                if (courtCase.getFirstNoticeDate() != null) {
                    existingCourtCase.setFirstNoticeDate(courtCase.getFirstNoticeDate());
                }
                if (courtCase.getSecondNoticeDate() != null) {
                    existingCourtCase.setSecondNoticeDate(courtCase.getSecondNoticeDate());
                }

                return existingCourtCase;
            })
            .map(courtCaseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourtCase> findAll(Pageable pageable) {
        log.debug("Request to get all CourtCases");
        return courtCaseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourtCase> findOne(Long id) {
        log.debug("Request to get CourtCase : {}", id);
        return courtCaseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourtCase : {}", id);
        courtCaseRepository.deleteById(id);
    }
}
