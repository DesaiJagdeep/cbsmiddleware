package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.service.IssFileParserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IssFileParser}.
 */
@Service
@Transactional
public class IssFileParserServiceImpl implements IssFileParserService {

    private final Logger log = LoggerFactory.getLogger(IssFileParserServiceImpl.class);

    private final IssFileParserRepository issFileParserRepository;

    public IssFileParserServiceImpl(IssFileParserRepository issFileParserRepository) {
        this.issFileParserRepository = issFileParserRepository;
    }

    @Override
    public IssFileParser save(IssFileParser issFileParser) {
        log.debug("Request to save IssFileParser : {}", issFileParser);
        return issFileParserRepository.save(issFileParser);
    }

    @Override
    public IssFileParser update(IssFileParser issFileParser) {
        log.debug("Request to update IssFileParser : {}", issFileParser);
        return issFileParserRepository.save(issFileParser);
    }

    @Override
    public Optional<IssFileParser> partialUpdate(IssFileParser issFileParser) {
        log.debug("Request to partially update IssFileParser : {}", issFileParser);

        return issFileParserRepository
            .findById(issFileParser.getId())
            .map(existingIssFileParser -> {
                if (issFileParser.getFinancialYear() != null) {
                    existingIssFileParser.setFinancialYear(issFileParser.getFinancialYear());
                }
                if (issFileParser.getBankName() != null) {
                    existingIssFileParser.setBankName(issFileParser.getBankName());
                }
                if (issFileParser.getBankCode() != null) {
                    existingIssFileParser.setBankCode(issFileParser.getBankCode());
                }
                if (issFileParser.getBranchName() != null) {
                    existingIssFileParser.setBranchName(issFileParser.getBranchName());
                }
                if (issFileParser.getBranchCode() != null) {
                    existingIssFileParser.setBranchCode(issFileParser.getBranchCode());
                }
                if (issFileParser.getSchemeWiseBranchCode() != null) {
                    existingIssFileParser.setSchemeWiseBranchCode(issFileParser.getSchemeWiseBranchCode());
                }
                if (issFileParser.getIfsc() != null) {
                    existingIssFileParser.setIfsc(issFileParser.getIfsc());
                }
                if (issFileParser.getLoanAccountNumberkcc() != null) {
                    existingIssFileParser.setLoanAccountNumberkcc(issFileParser.getLoanAccountNumberkcc());
                }
                if (issFileParser.getFarmerName() != null) {
                    existingIssFileParser.setFarmerName(issFileParser.getFarmerName());
                }
                if (issFileParser.getGender() != null) {
                    existingIssFileParser.setGender(issFileParser.getGender());
                }
                if (issFileParser.getAadharNumber() != null) {
                    existingIssFileParser.setAadharNumber(issFileParser.getAadharNumber());
                }
                if (issFileParser.getDateofBirth() != null) {
                    existingIssFileParser.setDateofBirth(issFileParser.getDateofBirth());
                }
                if (issFileParser.getAgeAtTimeOfSanction() != null) {
                    existingIssFileParser.setAgeAtTimeOfSanction(issFileParser.getAgeAtTimeOfSanction());
                }
                if (issFileParser.getMobileNo() != null) {
                    existingIssFileParser.setMobileNo(issFileParser.getMobileNo());
                }
                if (issFileParser.getFarmersCategory() != null) {
                    existingIssFileParser.setFarmersCategory(issFileParser.getFarmersCategory());
                }
                if (issFileParser.getFarmerType() != null) {
                    existingIssFileParser.setFarmerType(issFileParser.getFarmerType());
                }
                if (issFileParser.getSocialCategory() != null) {
                    existingIssFileParser.setSocialCategory(issFileParser.getSocialCategory());
                }
                if (issFileParser.getRelativeType() != null) {
                    existingIssFileParser.setRelativeType(issFileParser.getRelativeType());
                }
                if (issFileParser.getRelativeName() != null) {
                    existingIssFileParser.setRelativeName(issFileParser.getRelativeName());
                }
                if (issFileParser.getStateName() != null) {
                    existingIssFileParser.setStateName(issFileParser.getStateName());
                }
                if (issFileParser.getStateCode() != null) {
                    existingIssFileParser.setStateCode(issFileParser.getStateCode());
                }
                if (issFileParser.getDistrictName() != null) {
                    existingIssFileParser.setDistrictName(issFileParser.getDistrictName());
                }
                if (issFileParser.getDistrictCode() != null) {
                    existingIssFileParser.setDistrictCode(issFileParser.getDistrictCode());
                }
                if (issFileParser.getBlockCode() != null) {
                    existingIssFileParser.setBlockCode(issFileParser.getBlockCode());
                }
                if (issFileParser.getBlockName() != null) {
                    existingIssFileParser.setBlockName(issFileParser.getBlockName());
                }
                if (issFileParser.getVillageCode() != null) {
                    existingIssFileParser.setVillageCode(issFileParser.getVillageCode());
                }
                if (issFileParser.getVillageName() != null) {
                    existingIssFileParser.setVillageName(issFileParser.getVillageName());
                }
                if (issFileParser.getAddress() != null) {
                    existingIssFileParser.setAddress(issFileParser.getAddress());
                }
                if (issFileParser.getPinCode() != null) {
                    existingIssFileParser.setPinCode(issFileParser.getPinCode());
                }
                if (issFileParser.getAccountType() != null) {
                    existingIssFileParser.setAccountType(issFileParser.getAccountType());
                }
                if (issFileParser.getAccountNumber() != null) {
                    existingIssFileParser.setAccountNumber(issFileParser.getAccountNumber());
                }
                if (issFileParser.getPacsName() != null) {
                    existingIssFileParser.setPacsName(issFileParser.getPacsName());
                }
                if (issFileParser.getPacsNumber() != null) {
                    existingIssFileParser.setPacsNumber(issFileParser.getPacsNumber());
                }
                if (issFileParser.getAccountHolderType() != null) {
                    existingIssFileParser.setAccountHolderType(issFileParser.getAccountHolderType());
                }
                if (issFileParser.getPrimaryOccupation() != null) {
                    existingIssFileParser.setPrimaryOccupation(issFileParser.getPrimaryOccupation());
                }
                if (issFileParser.getLoanSactionDate() != null) {
                    existingIssFileParser.setLoanSactionDate(issFileParser.getLoanSactionDate());
                }
                if (issFileParser.getLoanSanctionAmount() != null) {
                    existingIssFileParser.setLoanSanctionAmount(issFileParser.getLoanSanctionAmount());
                }
                if (issFileParser.getTenureOFLoan() != null) {
                    existingIssFileParser.setTenureOFLoan(issFileParser.getTenureOFLoan());
                }
                if (issFileParser.getDateOfOverDuePayment() != null) {
                    existingIssFileParser.setDateOfOverDuePayment(issFileParser.getDateOfOverDuePayment());
                }
                if (issFileParser.getCropName() != null) {
                    existingIssFileParser.setCropName(issFileParser.getCropName());
                }
                if (issFileParser.getSurveyNo() != null) {
                    existingIssFileParser.setSurveyNo(issFileParser.getSurveyNo());
                }
                if (issFileParser.getSatBaraSubsurveyNo() != null) {
                    existingIssFileParser.setSatBaraSubsurveyNo(issFileParser.getSatBaraSubsurveyNo());
                }
                if (issFileParser.getSeasonName() != null) {
                    existingIssFileParser.setSeasonName(issFileParser.getSeasonName());
                }
                if (issFileParser.getAreaHect() != null) {
                    existingIssFileParser.setAreaHect(issFileParser.getAreaHect());
                }
                if (issFileParser.getLandType() != null) {
                    existingIssFileParser.setLandType(issFileParser.getLandType());
                }
                if (issFileParser.getDisbursementDate() != null) {
                    existingIssFileParser.setDisbursementDate(issFileParser.getDisbursementDate());
                }
                if (issFileParser.getDisburseAmount() != null) {
                    existingIssFileParser.setDisburseAmount(issFileParser.getDisburseAmount());
                }
                if (issFileParser.getMaturityLoanDate() != null) {
                    existingIssFileParser.setMaturityLoanDate(issFileParser.getMaturityLoanDate());
                }
                if (issFileParser.getRecoveryAmountPrinciple() != null) {
                    existingIssFileParser.setRecoveryAmountPrinciple(issFileParser.getRecoveryAmountPrinciple());
                }
                if (issFileParser.getRecoveryAmountInterest() != null) {
                    existingIssFileParser.setRecoveryAmountInterest(issFileParser.getRecoveryAmountInterest());
                }
                if (issFileParser.getRecoveryDate() != null) {
                    existingIssFileParser.setRecoveryDate(issFileParser.getRecoveryDate());
                }

                return existingIssFileParser;
            })
            .map(issFileParserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IssFileParser> findAll(Pageable pageable) {
        log.debug("Request to get all IssFileParsers");
        return issFileParserRepository.findAll(pageable);
    }

    public Page<IssFileParser> findAllWithEagerRelationships(Pageable pageable) {
        return issFileParserRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IssFileParser> findOne(Long id) {
        log.debug("Request to get IssFileParser : {}", id);
        return issFileParserRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IssFileParser : {}", id);
        issFileParserRepository.deleteById(id);
    }
}
