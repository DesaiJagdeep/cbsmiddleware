package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CourtCase;
import com.cbs.middleware.repository.CourtCaseRepository;
import com.cbs.middleware.service.CourtCaseService;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
                courtCaseCheck.setSrNoEn(Long.parseLong(translationServiceUtility.translationTextMrToEn(courtCase.getSrNo())));
                // marathi
                // courtCase.setSrNo(courtCase.getSrNo());
            }

            if (
                StringUtils.isNotBlank(courtCase.getAccountNo()) &&
                StringUtils.isNotBlank(courtCaseCheck.getAccountNo()) &&
                !courtCase.getAccountNo().equals(courtCaseCheck.getAccountNo())
            ) {
                // english
                courtCaseCheck.setAccountNoEn(translationServiceUtility.translationTextMrToEn(courtCase.getAccountNo()));
                // marathi
                // courtCase.setAccountNo(courtCase.getAccountNo());

            }

            if (
                StringUtils.isNotBlank(courtCase.getNameOfDefaulter()) &&
                StringUtils.isNotBlank(courtCaseCheck.getNameOfDefaulter()) &&
                !courtCase.getNameOfDefaulter().equals(courtCaseCheck.getNameOfDefaulter())
            ) {
                // english
                //courtCaseCheck.setNameOfDefaulterEn(translationServiceUtility.translationTextMrToEn(courtCase.getNameOfDefaulter()));
                // marathi
                courtCaseCheck.setNameOfDefaulter(courtCase.getNameOfDefaulter());

            }

            if (
                StringUtils.isNotBlank(courtCase.getAddress()) &&
                StringUtils.isNotBlank(courtCaseCheck.getAddress()) &&
                !courtCase.getAddress().equals(courtCaseCheck.getAddress())
            ) {
                // english
                //courtCaseCheck.setAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getAddress()));
                // marathi
                courtCaseCheck.setAddress(courtCase.getAddress());

            }

            if (
                StringUtils.isNotBlank(courtCase.getLoanType()) &&
                StringUtils.isNotBlank(courtCaseCheck.getLoanType()) &&
                !courtCase.getLoanType().equals(courtCaseCheck.getLoanType())
            ) {
                // english
                courtCaseCheck.setLoanTypeEn(translationServiceUtility.translationTextMrToEn(courtCase.getLoanType()));
                // marathi
                courtCaseCheck.setLoanType(courtCase.getLoanType());

            }

            if (
                StringUtils.isNotBlank(courtCase.getLoanAmount()) &&
                StringUtils.isNotBlank(courtCaseCheck.getLoanAmount()) &&
                !courtCase.getLoanAmount().equals(courtCaseCheck.getLoanAmount())
            ) {
                // english
                courtCaseCheck.setLoanAmountEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getLoanAmount())));
                // marathi
                courtCaseCheck.setLoanAmount(courtCase.getLoanAmount());

            }

            courtCaseCheck.setLoanDate(courtCase.getLoanDate());
            if (
                StringUtils.isNotBlank(courtCase.getVasuliExpense()) &&
                    StringUtils.isNotBlank(courtCaseCheck.getVasuliExpense()) &&
                    !courtCase.getVasuliExpense().equals(courtCaseCheck.getVasuliExpense())
            ) {
                // english
                courtCaseCheck.setVasuliExpenseEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getVasuliExpense())));
                // marathi
                courtCaseCheck.setVasuliExpense(courtCase.getVasuliExpense());
            }

            if (
                StringUtils.isNotBlank(courtCase.getNoticeExpense()) &&
                    StringUtils.isNotBlank(courtCaseCheck.getNoticeExpense()) &&
                    !courtCase.getNoticeExpense().equals(courtCaseCheck.getNoticeExpense())
            ) {
                // english
                courtCaseCheck.setNoticeExpenseEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getNoticeExpense())));
                // marathi
                courtCaseCheck.setNoticeExpense(courtCase.getNoticeExpense());
            }

            if (
                StringUtils.isNotBlank(courtCase.getOtherExpense()) &&
                    StringUtils.isNotBlank(courtCaseCheck.getOtherExpense()) &&
                    !courtCase.getOtherExpense().equals(courtCaseCheck.getOtherExpense())
            ) {
                // english
                courtCaseCheck.setOtherExpenseEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getOtherExpense())));
                // marathi
                courtCaseCheck.setOtherExpense(courtCase.getOtherExpense());
            }

            if (
                StringUtils.isNotBlank(courtCase.getSurcharge()) &&
                    StringUtils.isNotBlank(courtCaseCheck.getSurcharge()) &&
                    !courtCase.getSurcharge().equals(courtCaseCheck.getSurcharge())
            ) {
                // english
                courtCaseCheck.setSurcharge(translationServiceUtility.translationTextMrToEn(courtCase.getSurcharge()));
                // marathi
                courtCaseCheck.setSurcharge(courtCase.getSurcharge());
            }

            if (
                StringUtils.isNotBlank(courtCase.getStampFee()) &&
                    StringUtils.isNotBlank(courtCaseCheck.getStampFee()) &&
                    !courtCase.getStampFee().equals(courtCaseCheck.getStampFee())
            ) {
                // english
                courtCaseCheck.setStampFee(translationServiceUtility.translationTextMrToEn(courtCase.getStampFee()));
                // marathi
                courtCaseCheck.setStampFee(courtCase.getStampFee());
            }

            if (
                StringUtils.isNotBlank(courtCase.getInquiryFee()) &&
                    StringUtils.isNotBlank(courtCaseCheck.getInquiryFee()) &&
                    !courtCase.getInquiryFee().equals(courtCaseCheck.getInquiryFee())
            ) {
                // english
                courtCaseCheck.setInquiryFee(translationServiceUtility.translationTextMrToEn(courtCase.getInquiryFee()));
                // marathi
                courtCaseCheck.setInquiryFee(courtCase.getInquiryFee());
            }

            if (
                StringUtils.isNotBlank(courtCase.getInsurance()) &&
                    StringUtils.isNotBlank(courtCaseCheck.getInsurance()) &&
                    !courtCase.getInsurance().equals(courtCaseCheck.getInsurance())
            ) {
                // english
                courtCaseCheck.setInsurance(translationServiceUtility.translationTextMrToEn(courtCase.getInsurance()));
                // marathi
                courtCaseCheck.setInsurance(courtCase.getInsurance());
            }


            if (
                StringUtils.isNotBlank(courtCase.getTermOfLoan()) &&
                StringUtils.isNotBlank(courtCaseCheck.getTermOfLoan()) &&
                !courtCase.getTermOfLoan().equals(courtCaseCheck.getTermOfLoan())
            ) {
                // english
                courtCaseCheck.setTermOfLoanEn(translationServiceUtility.translationTextMrToEn(courtCase.getTermOfLoan()));
                // marathi
                courtCaseCheck.setTermOfLoan(courtCase.getTermOfLoan());

            }

            if (
                StringUtils.isNotBlank(courtCase.getInterestRate()) &&
                StringUtils.isNotBlank(courtCaseCheck.getInterestRate()) &&
                !courtCase.getInterestRate().equals(courtCaseCheck.getInterestRate())
            ) {
                // english
                courtCaseCheck.setInterestRateEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestRate()))
                );
                // marathi
                courtCaseCheck.setInterestRate(courtCase.getInterestRate());

            }

            if (
                StringUtils.isNotBlank(courtCase.getInstallmentAmount()) &&
                StringUtils.isNotBlank(courtCaseCheck.getInstallmentAmount()) &&
                !courtCase.getInstallmentAmount().equals(courtCaseCheck.getInstallmentAmount())
            ) {
                // english
                courtCaseCheck.setInstallmentAmountEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInstallmentAmount()))
                );
                // marathi
                courtCaseCheck.setInstallmentAmount(courtCase.getInstallmentAmount());

            }

            if (
                StringUtils.isNotBlank(courtCase.getTotalCredit()) &&
                StringUtils.isNotBlank(courtCaseCheck.getTotalCredit()) &&
                !courtCase.getTotalCredit().equals(courtCaseCheck.getTotalCredit())
            ) {
                // english
                courtCaseCheck.setTotalCreditEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getTotalCredit())));
                // marathi
                courtCaseCheck.setTotalCredit(courtCase.getTotalCredit());

            }

            if (
                StringUtils.isNotBlank(courtCase.getBalance()) &&
                StringUtils.isNotBlank(courtCaseCheck.getBalance()) &&
                !courtCase.getBalance().equals(courtCaseCheck.getBalance())
            ) {
                // english
                courtCaseCheck.setBalanceEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getBalance())));
                // marathi
                courtCaseCheck.setBalance(courtCase.getBalance());

            }

            if (
                StringUtils.isNotBlank(courtCase.getInterestPaid()) &&
                StringUtils.isNotBlank(courtCaseCheck.getInterestPaid()) &&
                !courtCase.getInterestPaid().equals(courtCaseCheck.getInterestPaid())
            ) {
                // english
                courtCaseCheck.setInterestPaidEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestPaid()))
                );
                // marathi
                courtCaseCheck.setInterestPaid(courtCase.getInterestPaid());

            }

            if (
                StringUtils.isNotBlank(courtCase.getPenalInterestPaid()) &&
                StringUtils.isNotBlank(courtCaseCheck.getPenalInterestPaid()) &&
                !courtCase.getPenalInterestPaid().equals(courtCaseCheck.getPenalInterestPaid())
            ) {
                // english
                courtCaseCheck.setPenalInterestPaidEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getPenalInterestPaid()))
                );
                // marathi
                courtCaseCheck.setPenalInterestPaid(courtCase.getPenalInterestPaid());

            }

            if (
                StringUtils.isNotBlank(courtCase.getDueAmount()) &&
                StringUtils.isNotBlank(courtCaseCheck.getDueAmount()) &&
                !courtCase.getDueAmount().equals(courtCaseCheck.getDueAmount())
            ) {
                // english
                courtCaseCheck.setDueAmountEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueAmount())));
                // marathi
                courtCaseCheck.setDueAmount(courtCase.getDueAmount());

            }

            courtCaseCheck.setDueDate(courtCase.getDueDate());

            if (
                StringUtils.isNotBlank(courtCase.getDueInterest()) &&
                StringUtils.isNotBlank(courtCaseCheck.getDueInterest()) &&
                !courtCase.getDueInterest().equals(courtCaseCheck.getDueInterest())
            ) {
                // english
                courtCaseCheck.setDueInterestEn(Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueInterest())));
                // marathi
                courtCaseCheck.setDueInterest(courtCase.getDueInterest());

            }

            if (
                StringUtils.isNotBlank(courtCase.getDuePenalInterest()) &&
                StringUtils.isNotBlank(courtCaseCheck.getDuePenalInterest()) &&
                !courtCase.getDuePenalInterest().equals(courtCaseCheck.getDuePenalInterest())
            ) {
                // english
                courtCaseCheck.setDuePenalInterestEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDuePenalInterest()))
                );
                // marathi
                courtCaseCheck.setDuePenalInterest(courtCase.getDuePenalInterest());

            }

            if (
                StringUtils.isNotBlank(courtCase.getDueMoreInterest()) &&
                StringUtils.isNotBlank(courtCaseCheck.getDueMoreInterest()) &&
                !courtCase.getDueMoreInterest().equals(courtCaseCheck.getDueMoreInterest())
            ) {
                // english
                courtCaseCheck.setDueMoreInterestEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getDueMoreInterest()))
                );
                // marathi
                courtCaseCheck.setDueMoreInterest(courtCase.getDueMoreInterest());

            }

            if (
                StringUtils.isNotBlank(courtCase.getInterestRecivable()) &&
                StringUtils.isNotBlank(courtCaseCheck.getInterestRecivable()) &&
                !courtCase.getInterestRecivable().equals(courtCaseCheck.getInterestRecivable())
            ) {
                // english
                courtCaseCheck.setInterestRecivableEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCase.getInterestRecivable()))
                );
                // marathi
                courtCaseCheck.setInterestRecivable(courtCase.getInterestRecivable());

            }

            if (
                StringUtils.isNotBlank(courtCase.getGaurentorOne()) &&
                StringUtils.isNotBlank(courtCaseCheck.getGaurentorOne()) &&
                !courtCase.getGaurentorOne().equals(courtCaseCheck.getGaurentorOne())
            ) {
                // english
                //courtCaseCheck.setGaurentorOneEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorOne()));
                // marathi
                courtCaseCheck.setGaurentorOne(courtCase.getGaurentorOne());

            }

            if (
                StringUtils.isNotBlank(courtCase.getGaurentorOneAddress()) &&
                StringUtils.isNotBlank(courtCaseCheck.getGaurentorOneAddress()) &&
                !courtCase.getGaurentorOneAddress().equals(courtCaseCheck.getGaurentorOneAddress())
            ) {
                // english
                //courtCaseCheck.setGaurentorOneAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorOneAddress()));
                // marathi
                courtCaseCheck.setGaurentorOneAddress(courtCase.getGaurentorOneAddress());

            }

            if (
                StringUtils.isNotBlank(courtCase.getGaurentorTwo()) &&
                StringUtils.isNotBlank(courtCaseCheck.getGaurentorTwo()) &&
                !courtCase.getGaurentorTwo().equals(courtCaseCheck.getGaurentorTwo())
            ) {
                // english
               // courtCaseCheck.setGaurentorTwoEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorTwo()));
                // marathi
                courtCaseCheck.setGaurentorTwo(courtCase.getGaurentorTwo());

            }

            if (
                StringUtils.isNotBlank(courtCase.getGaurentorTwoAddress()) &&
                StringUtils.isNotBlank(courtCaseCheck.getGaurentorTwoAddress()) &&
                !courtCase.getGaurentorTwoAddress().equals(courtCaseCheck.getGaurentorTwoAddress())
            ) {
                // english
               // courtCaseCheck.setGaurentorTwoAddressEn(translationServiceUtility.translationTextMrToEn(courtCase.getGaurentorTwoAddress()));
                // marathi
                courtCaseCheck.setGaurentorTwoAddress(courtCase.getGaurentorTwoAddress());

            }
            if (courtCase.getClaimDate()!=null){
                courtCaseCheck.setClaimDate(courtCase.getClaimDate());
            }
            if (courtCase.getLoanDate()!=null){
                courtCaseCheck.setLoanDate(courtCase.getLoanDate());
            }
            if (courtCase.getMaturityLoanDateEn()!=null){
                courtCaseCheck.setMaturityLoanDateEn(courtCase.getMaturityLoanDateEn());
            }
            if (courtCase.getDueDate()!=null){
                courtCaseCheck.setDueDate(courtCase.getDueDate());
            }
            if (courtCase.getFirstNoticeDate()!=null){
                courtCaseCheck.setFirstNoticeDate(courtCase.getFirstNoticeDate());
            }
            if (courtCase.getSecondNoticeDate()!=null){
                courtCaseCheck.setSecondNoticeDate(courtCase.getSecondNoticeDate());
            }

            if (
                StringUtils.isNotBlank(courtCase.getTalukaName()) &&
                StringUtils.isNotBlank(courtCaseCheck.getTalukaName()) &&
                !courtCase.getTalukaName().equals(courtCaseCheck.getTalukaName())
            ) {
                // english
                courtCaseCheck.setTalukaNameEn(translationServiceUtility.translationTextMrToEn(courtCase.getTalukaName()));
                // marathi
                courtCaseCheck.setTalukaName(courtCase.getTalukaName());

            }

            if (
                StringUtils.isNotBlank(courtCase.getBankName()) &&
                StringUtils.isNotBlank(courtCaseCheck.getBankName()) &&
                !courtCase.getBankName().equals(courtCaseCheck.getBankName())
            ) {
                // english
                courtCaseCheck.setBankNameEn(translationServiceUtility.translationTextMrToEn(courtCase.getBankName()));
                // marathi
                courtCaseCheck.setBankName(courtCase.getBankName());

            }

        } catch (Exception e) {}

        return courtCaseRepository.save(courtCaseCheck);
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
