package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.repository.ApplicationRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.service.IssPortalFileService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cbs.middleware.service.dto.IssPortalFileCountDTO;
import com.cbs.middleware.service.dto.PacsApplicationDTO;
import com.cbs.middleware.service.dto.TalukaApplicationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;

/**
 * Service Implementation for managing {@link IssPortalFile}.
 */
@Service
@Transactional
public class IssPortalFileServiceImpl implements IssPortalFileService {

    private final Logger log = LoggerFactory.getLogger(IssPortalFileServiceImpl.class);

    private final IssPortalFileRepository issPortalFileRepository;
    private final IssFileParserRepository issFileParserRepository;
    private final ApplicationRepository applicationRepository;

    public IssPortalFileServiceImpl(IssPortalFileRepository issPortalFileRepository,
                                    IssFileParserRepository issFileParserRepository,
                                    ApplicationRepository applicationRepository) {
        this.issPortalFileRepository = issPortalFileRepository;
        this.issFileParserRepository = issFileParserRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public IssPortalFile save(IssPortalFile issPortalFile) {
        // log.debug("Request to save IssPortalFile : {}", issPortalFile);
        return issPortalFileRepository.save(issPortalFile);
    }

    @Override
    public IssPortalFile update(IssPortalFile issPortalFile) {
        // log.debug("Request to update IssPortalFile : {}", issPortalFile);
        return issPortalFileRepository.save(issPortalFile);
    }

    @Override
    public Optional<IssPortalFile> partialUpdate(IssPortalFile issPortalFile) {
        log.debug("Request to partially update IssPortalFile : {}", issPortalFile);

        return issPortalFileRepository
            .findById(issPortalFile.getId())
            .map(existingIssPortalFile -> {
                if (issPortalFile.getFileName() != null) {
                    existingIssPortalFile.setFileName(issPortalFile.getFileName());
                }
                if (issPortalFile.getFileExtension() != null) {
                    existingIssPortalFile.setFileExtension(issPortalFile.getFileExtension());
                }
                if (issPortalFile.getSchemeWiseBranchCode() != null) {
                    existingIssPortalFile.setSchemeWiseBranchCode(issPortalFile.getSchemeWiseBranchCode());
                }
                if (issPortalFile.getFinancialYear() != null) {
                    existingIssPortalFile.setFinancialYear(issPortalFile.getFinancialYear());
                }
                if (issPortalFile.getFromDisbursementDate() != null) {
                    existingIssPortalFile.setFromDisbursementDate(issPortalFile.getFromDisbursementDate());
                }
                if (issPortalFile.getToDisbursementDate() != null) {
                    existingIssPortalFile.setToDisbursementDate(issPortalFile.getToDisbursementDate());
                }
                if (issPortalFile.getPacsCode() != null) {
                    existingIssPortalFile.setPacsCode(issPortalFile.getPacsCode());
                }
                if (issPortalFile.getStatus() != null) {
                    existingIssPortalFile.setStatus(issPortalFile.getStatus());
                }
                if (issPortalFile.getApplicationCount() != null) {
                    existingIssPortalFile.setApplicationCount(issPortalFile.getApplicationCount());
                }
                if (issPortalFile.getNotes() != null) {
                    existingIssPortalFile.setNotes(issPortalFile.getNotes());
                }

                return existingIssPortalFile;
            })
            .map(issPortalFileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IssPortalFile> findAll(Pageable pageable) {
        log.debug("Request to get all IssPortalFiles");
        return issPortalFileRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IssPortalFile> findOne(Long id) {
        log.debug("Request to get IssPortalFile : {}", id);
        return issPortalFileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IssPortalFile : {}", id);
        issPortalFileRepository.deleteById(id);
    }

    @Override
    public IssPortalFileCountDTO findCounts(String financialYear) {
        IssPortalFileCountDTO issPortalFileCountDTO = new IssPortalFileCountDTO();


        Integer totalApplication = issPortalFileRepository.findTotalApplicationCountByFinancialYear(financialYear);
        Integer validationError = issPortalFileRepository.findValidationErrorCountByFinancialYear(financialYear);
        Integer kccAccepted = issPortalFileRepository.findKccAcceptedCountByFinancialYear(financialYear);
        Integer kccRejected = issPortalFileRepository.findKccRejectedCountByFinancialYear(financialYear);
        Integer kccDuplicateOrAccountNumberProcessedCount = issPortalFileRepository.findKccDuplicateOrAccountNumberProcessedCount(financialYear);

        Integer KccPending = issPortalFileRepository.findKccPendingCountByFinancialYear(financialYear);
        Integer totalFarmers = issPortalFileRepository.findDistinctFarmersCountByFinancialYear(financialYear);


        issPortalFileCountDTO.setTotalApplications(totalApplication);
        issPortalFileCountDTO.setValidationErrors(validationError);
        issPortalFileCountDTO.setkCCAccepted(kccAccepted);
        issPortalFileCountDTO.setkCCRejected(kccRejected-kccDuplicateOrAccountNumberProcessedCount);
        issPortalFileCountDTO.setkCCPending(KccPending);
        issPortalFileCountDTO.setTotalFarmers(totalFarmers);

        return issPortalFileCountDTO;
    }

    @Override
    public List<TalukaApplicationDTO> findIssPortalFilesByTalukaIdAndFinacialYear(Long talukaId, String financialYear) {

        List<Object[]> talukaWiseBranches = issPortalFileRepository.findTalukaWiseBranches(talukaId, financialYear);

        List<TalukaApplicationDTO> talukaApplicationDTOList = new ArrayList<>();
        for (Object[] branch : talukaWiseBranches) {
            BigInteger schemeWiseBranchCode = (BigInteger) branch[0];
            String branchName = (String) branch[1];
            Long totalApps = issPortalFileRepository.findTotalAppByBranchCodeAndFinancialYear(schemeWiseBranchCode.longValue(), financialYear);
            Long validationError = issPortalFileRepository.findValidationErrorByBranchCodeAndFinancialYear(schemeWiseBranchCode.longValue(), financialYear);
            Long kccAccepted = issPortalFileRepository.findKccAcceptedByBranchCodeAndFinancialYear(schemeWiseBranchCode.longValue(), financialYear);
            Long kccRejected = issPortalFileRepository.findKccRejectedByBranchCodeAndFinancialYear(schemeWiseBranchCode.longValue(), financialYear);
            Long kccPending = issPortalFileRepository.findKccPendingByBranchCodeAndFinancialYear(schemeWiseBranchCode.longValue(), financialYear);

            Long pendingFromBranchAdmin = issPortalFileRepository.findBranchAdminApprovalPendingByBranchCodeAndFinancialYear(schemeWiseBranchCode.longValue(), financialYear);
            Long pendingFromBranchUser = issPortalFileRepository.findBranchUserApprovalPendingByBranchCodeAndFinancialYear(schemeWiseBranchCode.longValue(), financialYear);

            TalukaApplicationDTO talukaApplicationDTO = new TalukaApplicationDTO(
                totalApps, validationError, kccAccepted, kccRejected, kccPending, branchName, schemeWiseBranchCode.longValue(),pendingFromBranchAdmin,pendingFromBranchUser
            );
            talukaApplicationDTOList.add(talukaApplicationDTO);
        }

        return talukaApplicationDTOList;

    }

    @Override
    public List<PacsApplicationDTO> findPacsWiseDataBySchemeBranchCodeAndFinacialYear(Long schemeBranchCode, String financialYear) {

        List<Object[]> branchWisePacs = issPortalFileRepository.findBranchWisePacs(schemeBranchCode,financialYear);

        List<PacsApplicationDTO> pacsApplicationDTOList = new ArrayList<>();
        for (Object[] pacsApp : branchWisePacs) {
            BigInteger pacsCode = (BigInteger) pacsApp[0];
            String pacsName = (String) pacsApp[1];
            Long totalApps = issPortalFileRepository.findTotalAppByPacsCodeAndFinancialYear(pacsCode.longValue(), financialYear);
            Long validationError = issPortalFileRepository.findValidationErrorByPacsCodeAndFinancialYear(pacsCode.longValue(), financialYear);
            Long kccAccepted = issPortalFileRepository.findKccAcceptedByPacsCodeAndFinancialYear(pacsCode.longValue(), financialYear);
            Long kccRejected = issPortalFileRepository.findKccRejectedByPacsCodeAndFinancialYear(pacsCode.longValue(), financialYear);
            Long kccPending = issPortalFileRepository.findKccPendingByPacsCodeAndFinancialYear(pacsCode.longValue(), financialYear);

            PacsApplicationDTO pacsApplicationDTO = new PacsApplicationDTO(
                totalApps, validationError, kccAccepted, kccRejected, kccPending, pacsName, pacsCode.longValue()
            );
            pacsApplicationDTOList.add(pacsApplicationDTO);
        }

        return pacsApplicationDTOList;
    }

}
