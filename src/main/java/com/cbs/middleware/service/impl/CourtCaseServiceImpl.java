package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.service.CourtCaseService;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TranslationServiceUtility translationServiceUtility;

    public CourtCaseServiceImpl(CourtCaseRepository courtCaseRepository) {
        this.courtCaseRepository = courtCaseRepository;
    }

    @Override
    public CourtCase save(CourtCase courtCase) {
        log.debug("Request to save CourtCase : {}", courtCase);

        try {
            if (StringUtils.isNotBlank(courtCase.getSrNo())) {
                // english
                courtCase.setSrNoEn(Long.parseLong(translationServiceUtility.translationTextMrToEn(courtCase.getSrNo())));
                // marathi
                // courtCase.setSrNo(courtCase.getSrNo());
            }

            if (StringUtils.isNotBlank(courtCase.getAccountNo())) {
                // english
                courtCase.setAccountNoEn(translationServiceUtility.translationTextMrToEn(courtCase.getAccountNo()));
                // marathi
                // courtCase.setAccountNo(courtCase.getAccountNo());

            }

            if (StringUtils.isNotBlank(courtCase.getNameOfDefaulter())) {
                // english
                courtCase.setNameOfDefaulterEn(translationServiceUtility.translationTextMrToEn(courtCase.getNameOfDefaulter()));
                // marathi
                // courtCase.setNameOfDefaulter(courtCase.getNameOfDefaulter());

            }

            if (StringUtils.isNotBlank(courtCase.getAddress())) {
                // english
                courtCase.setAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getAddress()));
                // marathi
                // courtCase.setAddress(courtCase.getAddress());

            }

            if (StringUtils.isNotBlank(courtCase.getLoanType())) {
                // english
                courtCase.setLoanTypeEn(translationServiceUtility.translationTextMrToEn(courtCase.getLoanType()));
                // marathi
                // courtCase.setLoanType(courtCase.getLoanType());

            }

            if (StringUtils.isNotBlank(courtCase.getLoanAmount())) {
                // english
                courtCase.setLoanAmountEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getLoanAmount())));
                // marathi
                // courtCase.setLoanAmount(courtCase.getLoanAmount());

            }

            courtCase.setLoanDate(courtCase.getLoanDate());

            if (StringUtils.isNotBlank(courtCase.getTermOfLoan())) {
                // english
                courtCase.setTermOfLoanEn(translationServiceUtility.translationTextMrToEn(courtCase.getTermOfLoan()));
                // marathi
                // courtCase.setTermOfLoan(courtCase.getTermOfLoan());

            }

            if (StringUtils.isNotBlank(courtCase.getInterestRate())) {
                // english
                courtCase.setInterestRateEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestRate()))
                );
                // marathi
                // courtCase.setInterestRate(courtCase.getInterestRate());

            }

            if (StringUtils.isNotBlank(courtCase.getInstallmentAmount())) {
                // english
                courtCase.setInstallmentAmountEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInstallmentAmount()))
                );
                // marathi
                // courtCase.setInstallmentAmount(courtCase.getInstallmentAmount());

            }

            if (StringUtils.isNotBlank(courtCase.getTotalCredit())) {
                // english
                courtCase.setTotalCreditEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getTotalCredit())));
                // marathi
                // courtCase.setTotalCredit(courtCase.getTotalCredit());

            }

            if (StringUtils.isNotBlank(courtCase.getBalance())) {
                // english
                courtCase.setBalanceEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getBalance())));
                // marathi
                // courtCase.setBalance(courtCase.getBalance());

            }

            if (StringUtils.isNotBlank(courtCase.getInterestPaid())) {
                // english
                courtCase.setInterestPaidEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestPaid()))
                );
                // marathi
                // courtCase.setInterestPaid(courtCase.getInterestPaid());

            }

            if (StringUtils.isNotBlank(courtCase.getPenalInterestPaid())) {
                // english
                courtCase.setPenalInterestPaidEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getPenalInterestPaid()))
                );
                // marathi
                // courtCase.setPenalInterestPaid(courtCase.getPenalInterestPaid());

            }

            if (StringUtils.isNotBlank(courtCase.getDueAmount())) {
                // english
                courtCase.setDueAmountEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueAmount())));
                // marathi
                // courtCase.setDueAmount(courtCase.getDueAmount());

            }

            courtCase.setDueDate(courtCase.getDueDate());

            if (StringUtils.isNotBlank(courtCase.getDueInterest())) {
                // english
                courtCase.setDueInterestEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueInterest())));
                // marathi
                // courtCase.setDueInterest(courtCase.getDueInterest());

            }

            if (StringUtils.isNotBlank(courtCase.getDuePenalInterest())) {
                // english
                courtCase.setDuePenalInterestEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDuePenalInterest()))
                );
                // marathi
                // courtCase.setDuePenalInterest(courtCase.getDuePenalInterest());

            }

            if (StringUtils.isNotBlank(courtCase.getDueMoreInterest())) {
                // english
                courtCase.setDueMoreInterestEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueMoreInterest()))
                );
                // marathi
                // courtCase.setDueMoreInterest(courtCase.getDueMoreInterest());

            }

            if (StringUtils.isNotBlank(courtCase.getInterestRecivable())) {
                // english
                courtCase.setInterestRecivableEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestRecivable()))
                );
                // marathi
                // courtCase.setInterestRecivable(courtCase.getInterestRecivable());

            }

            if (StringUtils.isNotBlank(courtCase.getGaurentorOne())) {
                // english
                courtCase.setGaurentorOneEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorOne()));
                // marathi
                // courtCase.setGaurentorOne(courtCase.getGaurentorOne());

            }

            if (StringUtils.isNotBlank(courtCase.getGaurentorOneAddress())) {
                // english
                courtCase.setGaurentorOneAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorOneAddress()));
                // marathi
                // courtCase.setGaurentorOneAddress(courtCase.getGaurentorOneAddress());

            }

            if (StringUtils.isNotBlank(courtCase.getGaurentorTwo())) {
                // english
                courtCase.setGaurentorTwoEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorTwo()));
                // marathi
                // courtCase.setGaurentorTwo(courtCase.getGaurentorTwo());

            }

            if (StringUtils.isNotBlank(courtCase.getGaurentorTwoAddress())) {
                // english
                courtCase.setGaurentorTwoAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorTwoAddress()));
                // marathi
                // courtCase.setGaurentorTwoAddress(courtCase.getGaurentorTwoAddress());

            }
            // courtCase.setFirstNoticeDate(courtCase.getFirstNoticeDate());

            // courtCase.setSecondNoticeDate(courtCase.getSecondNoticeDate());

            if (StringUtils.isNotBlank(courtCase.getBankName())) {
                // english
                courtCase.setBankNameEn(translationServiceUtility.translationTextMrToEn(courtCase.getBankName()));
                // marathi
                // courtCase.setBankName(courtCase.getBankName());

            }

            if (StringUtils.isNotBlank(courtCase.getTalukaName())) {
                // english
                courtCase.setTalukaNameEn(translationServiceUtility.translationTextMrToEn(courtCase.getTalukaName()));
                // marathi
                // courtCase.setTalukaName(courtCase.getTalukaName());

            }
        } catch (Exception e) {}

        return courtCaseRepository.save(courtCase);
    }

    @Override
    public CourtCase update(CourtCase courtCase) {
        log.debug("Request to update CourtCase : {}", courtCase);

        Optional<CourtCase> findById = courtCaseRepository.findById(courtCase.getId());
        CourtCase courtCaseCheck = findById.get();

        try {
            if (
                StringUtils.isNotBlank(courtCase.getSrNo()) &&
                StringUtils.isNotBlank(courtCaseCheck.getSrNo()) &&
                !courtCase.getSrNo().equals(courtCaseCheck.getSrNo())
            ) {
                // english
                courtCase.setSrNoEn(Long.parseLong(translationServiceUtility.translationTextMrToEn(courtCase.getSrNo())));
                // marathi
                // courtCase.setSrNo(courtCase.getSrNo());
            }

            if (
                StringUtils.isNotBlank(courtCase.getAccountNo()) &&
                StringUtils.isNotBlank(courtCaseCheck.getAccountNo()) &&
                !courtCase.getAccountNo().equals(courtCaseCheck.getAccountNo())
            ) {
                // english
                courtCase.setAccountNoEn(translationServiceUtility.translationTextMrToEn(courtCase.getAccountNo()));
                // marathi
                // courtCase.setAccountNo(courtCase.getAccountNo());

            }

            if (
                StringUtils.isNotBlank(courtCase.getNameOfDefaulter()) &&
                StringUtils.isNotBlank(courtCaseCheck.getNameOfDefaulter()) &&
                !courtCase.getNameOfDefaulter().equals(courtCaseCheck.getNameOfDefaulter())
            ) {
                // english
                courtCase.setNameOfDefaulterEn(translationServiceUtility.translationTextMrToEn(courtCase.getNameOfDefaulter()));
                // marathi
                // courtCase.setNameOfDefaulter(courtCase.getNameOfDefaulter());

            }

            if (
                StringUtils.isNotBlank(courtCase.getAddress()) &&
                StringUtils.isNotBlank(courtCaseCheck.getAddress()) &&
                !courtCase.getAddress().equals(courtCaseCheck.getAddress())
            ) {
                // english
                courtCase.setAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getAddress()));
                // marathi
                // courtCase.setAddress(courtCase.getAddress());

            }

            if (
                StringUtils.isNotBlank(courtCase.getLoanType()) &&
                StringUtils.isNotBlank(courtCaseCheck.getLoanType()) &&
                !courtCase.getLoanType().equals(courtCaseCheck.getLoanType())
            ) {
                // english
                courtCase.setLoanTypeEn(translationServiceUtility.translationTextMrToEn(courtCase.getLoanType()));
                // marathi
                // courtCase.setLoanType(courtCase.getLoanType());

            }

            if (
                StringUtils.isNotBlank(courtCase.getLoanAmount()) &&
                StringUtils.isNotBlank(courtCaseCheck.getLoanAmount()) &&
                !courtCase.getLoanAmount().equals(courtCaseCheck.getLoanAmount())
            ) {
                // english
                courtCase.setLoanAmountEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getLoanAmount())));
                // marathi
                // courtCase.setLoanAmount(courtCase.getLoanAmount());

            }

            courtCase.setLoanDate(courtCase.getLoanDate());

            if (
                StringUtils.isNotBlank(courtCase.getTermOfLoan()) &&
                StringUtils.isNotBlank(courtCaseCheck.getTermOfLoan()) &&
                !courtCase.getTermOfLoan().equals(courtCaseCheck.getTermOfLoan())
            ) {
                // english
                courtCase.setTermOfLoanEn(translationServiceUtility.translationTextMrToEn(courtCase.getTermOfLoan()));
                // marathi
                // courtCase.setTermOfLoan(courtCase.getTermOfLoan());

            }

            if (
                StringUtils.isNotBlank(courtCase.getInterestRate()) &&
                StringUtils.isNotBlank(courtCaseCheck.getInterestRate()) &&
                !courtCase.getInterestRate().equals(courtCaseCheck.getInterestRate())
            ) {
                // english
                courtCase.setInterestRateEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestRate()))
                );
                // marathi
                // courtCase.setInterestRate(courtCase.getInterestRate());

            }

            if (
                StringUtils.isNotBlank(courtCase.getInstallmentAmount()) &&
                StringUtils.isNotBlank(courtCaseCheck.getInstallmentAmount()) &&
                !courtCase.getInstallmentAmount().equals(courtCaseCheck.getInstallmentAmount())
            ) {
                // english
                courtCase.setInstallmentAmountEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInstallmentAmount()))
                );
                // marathi
                // courtCase.setInstallmentAmount(courtCase.getInstallmentAmount());

            }

            if (
                StringUtils.isNotBlank(courtCase.getTotalCredit()) &&
                StringUtils.isNotBlank(courtCaseCheck.getTotalCredit()) &&
                !courtCase.getTotalCredit().equals(courtCaseCheck.getTotalCredit())
            ) {
                // english
                courtCase.setTotalCreditEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getTotalCredit())));
                // marathi
                // courtCase.setTotalCredit(courtCase.getTotalCredit());

            }

            if (
                StringUtils.isNotBlank(courtCase.getBalance()) &&
                StringUtils.isNotBlank(courtCaseCheck.getBalance()) &&
                !courtCase.getBalance().equals(courtCaseCheck.getBalance())
            ) {
                // english
                courtCase.setBalanceEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getBalance())));
                // marathi
                // courtCase.setBalance(courtCase.getBalance());

            }

            if (
                StringUtils.isNotBlank(courtCase.getInterestPaid()) &&
                StringUtils.isNotBlank(courtCaseCheck.getInterestPaid()) &&
                !courtCase.getInterestPaid().equals(courtCaseCheck.getInterestPaid())
            ) {
                // english
                courtCase.setInterestPaidEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestPaid()))
                );
                // marathi
                // courtCase.setInterestPaid(courtCase.getInterestPaid());

            }

            if (
                StringUtils.isNotBlank(courtCase.getPenalInterestPaid()) &&
                StringUtils.isNotBlank(courtCaseCheck.getPenalInterestPaid()) &&
                !courtCase.getPenalInterestPaid().equals(courtCaseCheck.getPenalInterestPaid())
            ) {
                // english
                courtCase.setPenalInterestPaidEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getPenalInterestPaid()))
                );
                // marathi
                // courtCase.setPenalInterestPaid(courtCase.getPenalInterestPaid());

            }

            if (
                StringUtils.isNotBlank(courtCase.getDueAmount()) &&
                StringUtils.isNotBlank(courtCaseCheck.getDueAmount()) &&
                !courtCase.getDueAmount().equals(courtCaseCheck.getDueAmount())
            ) {
                // english
                courtCase.setDueAmountEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueAmount())));
                // marathi
                // courtCase.setDueAmount(courtCase.getDueAmount());

            }

            courtCase.setDueDate(courtCase.getDueDate());

            if (
                StringUtils.isNotBlank(courtCase.getDueInterest()) &&
                StringUtils.isNotBlank(courtCaseCheck.getDueInterest()) &&
                !courtCase.getDueInterest().equals(courtCaseCheck.getDueInterest())
            ) {
                // english
                courtCase.setDueInterestEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueInterest())));
                // marathi
                // courtCase.setDueInterest(courtCase.getDueInterest());

            }

            if (
                StringUtils.isNotBlank(courtCase.getDuePenalInterest()) &&
                StringUtils.isNotBlank(courtCaseCheck.getDuePenalInterest()) &&
                !courtCase.getDuePenalInterest().equals(courtCaseCheck.getDuePenalInterest())
            ) {
                // english
                courtCase.setDuePenalInterestEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDuePenalInterest()))
                );
                // marathi
                // courtCase.setDuePenalInterest(courtCase.getDuePenalInterest());

            }

            if (
                StringUtils.isNotBlank(courtCase.getDueMoreInterest()) &&
                StringUtils.isNotBlank(courtCaseCheck.getDueMoreInterest()) &&
                !courtCase.getDueMoreInterest().equals(courtCaseCheck.getDueMoreInterest())
            ) {
                // english
                courtCase.setDueMoreInterestEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueMoreInterest()))
                );
                // marathi
                // courtCase.setDueMoreInterest(courtCase.getDueMoreInterest());

            }

            if (
                StringUtils.isNotBlank(courtCase.getInterestRecivable()) &&
                StringUtils.isNotBlank(courtCaseCheck.getInterestRecivable()) &&
                !courtCase.getInterestRecivable().equals(courtCaseCheck.getInterestRecivable())
            ) {
                // english
                courtCase.setInterestRecivableEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestRecivable()))
                );
                // marathi
                // courtCase.setInterestRecivable(courtCase.getInterestRecivable());

            }

            if (
                StringUtils.isNotBlank(courtCase.getGaurentorOne()) &&
                StringUtils.isNotBlank(courtCaseCheck.getGaurentorOne()) &&
                !courtCase.getGaurentorOne().equals(courtCaseCheck.getGaurentorOne())
            ) {
                // english
                courtCase.setGaurentorOneEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorOne()));
                // marathi
                // courtCase.setGaurentorOne(courtCase.getGaurentorOne());

            }

            if (
                StringUtils.isNotBlank(courtCase.getGaurentorOneAddress()) &&
                StringUtils.isNotBlank(courtCaseCheck.getGaurentorOneAddress()) &&
                !courtCase.getGaurentorOneAddress().equals(courtCaseCheck.getGaurentorOneAddress())
            ) {
                // english
                courtCase.setGaurentorOneAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorOneAddress()));
                // marathi
                // courtCase.setGaurentorOneAddress(courtCase.getGaurentorOneAddress());

            }

            if (
                StringUtils.isNotBlank(courtCase.getGaurentorTwo()) &&
                StringUtils.isNotBlank(courtCaseCheck.getGaurentorTwo()) &&
                !courtCase.getGaurentorTwo().equals(courtCaseCheck.getGaurentorTwo())
            ) {
                // english
                courtCase.setGaurentorTwoEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorTwo()));
                // marathi
                // courtCase.setGaurentorTwo(courtCase.getGaurentorTwo());

            }

            if (
                StringUtils.isNotBlank(courtCase.getGaurentorTwoAddress()) &&
                StringUtils.isNotBlank(courtCaseCheck.getGaurentorTwoAddress()) &&
                !courtCase.getGaurentorTwoAddress().equals(courtCaseCheck.getGaurentorTwoAddress())
            ) {
                // english
                courtCase.setGaurentorTwoAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorTwoAddress()));
                // marathi
                // courtCase.setGaurentorTwoAddress(courtCase.getGaurentorTwoAddress());

            }
            // courtCase.setFirstNoticeDate(courtCase.getFirstNoticeDate());

            // courtCase.setSecondNoticeDate(courtCase.getSecondNoticeDate());

            if (
                StringUtils.isNotBlank(courtCase.getTalukaName()) &&
                StringUtils.isNotBlank(courtCaseCheck.getTalukaName()) &&
                !courtCase.getTalukaName().equals(courtCaseCheck.getTalukaName())
            ) {
                // english
                courtCase.setTalukaNameEn(translationServiceUtility.translationTextMrToEn(courtCase.getTalukaName()));
                // marathi
                // courtCase.setTalukaName(courtCase.getTalukaName());

            }

            if (
                StringUtils.isNotBlank(courtCase.getBankName()) &&
                StringUtils.isNotBlank(courtCaseCheck.getBankName()) &&
                !courtCase.getBankName().equals(courtCaseCheck.getBankName())
            ) {
                // english
                courtCase.setBankNameEn(translationServiceUtility.translationTextMrToEn(courtCase.getBankName()));
                // marathi
                // courtCase.setBankName(courtCase.getBankName());

            }
        } catch (Exception e) {}

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
