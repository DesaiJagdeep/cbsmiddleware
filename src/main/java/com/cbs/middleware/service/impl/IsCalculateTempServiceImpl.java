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
                if (isCalculateTemp.getIssFileParserId() != null) {
                    existingIsCalculateTemp.setIssFileParserId(isCalculateTemp.getIssFileParserId());
                }
                if (isCalculateTemp.getBranchCode() != null) {
                    existingIsCalculateTemp.setBranchCode(isCalculateTemp.getBranchCode());
                }
                if (isCalculateTemp.getPacsNumber() != null) {
                    existingIsCalculateTemp.setPacsNumber(isCalculateTemp.getPacsNumber());
                }
                if (isCalculateTemp.getLoanAccountNumberKcc() != null) {
                    existingIsCalculateTemp.setLoanAccountNumberKcc(isCalculateTemp.getLoanAccountNumberKcc());
                }
                if (isCalculateTemp.getFarmerName() != null) {
                    existingIsCalculateTemp.setFarmerName(isCalculateTemp.getFarmerName());
                }
                if (isCalculateTemp.getGender() != null) {
                    existingIsCalculateTemp.setGender(isCalculateTemp.getGender());
                }
                if (isCalculateTemp.getAadharNumber() != null) {
                    existingIsCalculateTemp.setAadharNumber(isCalculateTemp.getAadharNumber());
                }
                if (isCalculateTemp.getMobileNo() != null) {
                    existingIsCalculateTemp.setMobileNo(isCalculateTemp.getMobileNo());
                }
                if (isCalculateTemp.getFarmerType() != null) {
                    existingIsCalculateTemp.setFarmerType(isCalculateTemp.getFarmerType());
                }
                if (isCalculateTemp.getSocialCategory() != null) {
                    existingIsCalculateTemp.setSocialCategory(isCalculateTemp.getSocialCategory());
                }
                if (isCalculateTemp.getAccountNumber() != null) {
                    existingIsCalculateTemp.setAccountNumber(isCalculateTemp.getAccountNumber());
                }
                if (isCalculateTemp.getLoanSanctionDate() != null) {
                    existingIsCalculateTemp.setLoanSanctionDate(isCalculateTemp.getLoanSanctionDate());
                }
                if (isCalculateTemp.getLoanSanctionAmount() != null) {
                    existingIsCalculateTemp.setLoanSanctionAmount(isCalculateTemp.getLoanSanctionAmount());
                }
                if (isCalculateTemp.getDisbursementDate() != null) {
                    existingIsCalculateTemp.setDisbursementDate(isCalculateTemp.getDisbursementDate());
                }
                if (isCalculateTemp.getDisburseAmount() != null) {
                    existingIsCalculateTemp.setDisburseAmount(isCalculateTemp.getDisburseAmount());
                }
                if (isCalculateTemp.getMaturityLoanDate() != null) {
                    existingIsCalculateTemp.setMaturityLoanDate(isCalculateTemp.getMaturityLoanDate());
                }
                if (isCalculateTemp.getBankDate() != null) {
                    existingIsCalculateTemp.setBankDate(isCalculateTemp.getBankDate());
                }
                if (isCalculateTemp.getCropName() != null) {
                    existingIsCalculateTemp.setCropName(isCalculateTemp.getCropName());
                }
                if (isCalculateTemp.getRecoveryAmount() != null) {
                    existingIsCalculateTemp.setRecoveryAmount(isCalculateTemp.getRecoveryAmount());
                }
                if (isCalculateTemp.getRecoveryInterest() != null) {
                    existingIsCalculateTemp.setRecoveryInterest(isCalculateTemp.getRecoveryInterest());
                }
                if (isCalculateTemp.getRecoveryDate() != null) {
                    existingIsCalculateTemp.setRecoveryDate(isCalculateTemp.getRecoveryDate());
                }
                if (isCalculateTemp.getBalanceAmount() != null) {
                    existingIsCalculateTemp.setBalanceAmount(isCalculateTemp.getBalanceAmount());
                }
                if (isCalculateTemp.getPrevDays() != null) {
                    existingIsCalculateTemp.setPrevDays(isCalculateTemp.getPrevDays());
                }
                if (isCalculateTemp.getPresDays() != null) {
                    existingIsCalculateTemp.setPresDays(isCalculateTemp.getPresDays());
                }
                if (isCalculateTemp.getActualDays() != null) {
                    existingIsCalculateTemp.setActualDays(isCalculateTemp.getActualDays());
                }
                if (isCalculateTemp.getnProd() != null) {
                    existingIsCalculateTemp.setnProd(isCalculateTemp.getnProd());
                }
                if (isCalculateTemp.getProductAmount() != null) {
                    existingIsCalculateTemp.setProductAmount(isCalculateTemp.getProductAmount());
                }
                if (isCalculateTemp.getProductBank() != null) {
                    existingIsCalculateTemp.setProductBank(isCalculateTemp.getProductBank());
                }
                if (isCalculateTemp.getProductAbh3Lakh() != null) {
                    existingIsCalculateTemp.setProductAbh3Lakh(isCalculateTemp.getProductAbh3Lakh());
                }
                if (isCalculateTemp.getInterestFirst15() != null) {
                    existingIsCalculateTemp.setInterestFirst15(isCalculateTemp.getInterestFirst15());
                }
                if (isCalculateTemp.getInterestFirst25() != null) {
                    existingIsCalculateTemp.setInterestFirst25(isCalculateTemp.getInterestFirst25());
                }
                if (isCalculateTemp.getInterestSecond15() != null) {
                    existingIsCalculateTemp.setInterestSecond15(isCalculateTemp.getInterestSecond15());
                }
                if (isCalculateTemp.getInterestSecond25() != null) {
                    existingIsCalculateTemp.setInterestSecond25(isCalculateTemp.getInterestSecond25());
                }
                if (isCalculateTemp.getInterestStateFirst3() != null) {
                    existingIsCalculateTemp.setInterestStateFirst3(isCalculateTemp.getInterestStateFirst3());
                }
                if (isCalculateTemp.getInterestStateSecond3() != null) {
                    existingIsCalculateTemp.setInterestStateSecond3(isCalculateTemp.getInterestStateSecond3());
                }

                if (isCalculateTemp.getInterestAbove3Lakh() != null) {
                    existingIsCalculateTemp.setInterestAbove3Lakh(isCalculateTemp.getInterestAbove3Lakh());
                }
                if (isCalculateTemp.getPanjabraoInt3() != null) {
                    existingIsCalculateTemp.setPanjabraoInt3(isCalculateTemp.getPanjabraoInt3());
                }
                if (isCalculateTemp.getIsRecover() != null) {
                    existingIsCalculateTemp.setIsRecover(isCalculateTemp.getIsRecover());
                }
                if (isCalculateTemp.getAbh3LakhAmt() != null) {
                    existingIsCalculateTemp.setAbh3LakhAmt(isCalculateTemp.getAbh3LakhAmt());
                }
                if (isCalculateTemp.getUpto50000() != null) {
                    existingIsCalculateTemp.setUpto50000(isCalculateTemp.getUpto50000());
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
