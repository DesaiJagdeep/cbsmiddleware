package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.IssPortalFile;
import com.cbs.middleware.repository.IssPortalFileRepository;
import com.cbs.middleware.service.IssPortalFileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cbs.middleware.service.dto.IssPortalFileCountDTO;
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

    public IssPortalFileServiceImpl(IssPortalFileRepository issPortalFileRepository) {
        this.issPortalFileRepository = issPortalFileRepository;
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
        IssPortalFileCountDTO issPortalFileCountDTO=new IssPortalFileCountDTO();

        List<IssPortalFile> issPortalFiles = issPortalFileRepository.findAll();
        if (!issPortalFiles.isEmpty()){
            Integer sumOfapplicationCount = issPortalFileRepository.findSumOfapplicationCount(financialYear);
            Integer sumOfErrorRecordCount = issPortalFileRepository.findSumOfErrorRecordCount(financialYear);
            Integer sumOfAppSubmitedTokccCount = issPortalFileRepository.findSumOfAppSubmitedTokccCount(financialYear);
            Integer sumOfappAcceptedByKccCount = issPortalFileRepository.findSumOfappAcceptedByKccCount(financialYear);
            Integer sumOfkccErrorRecordCount = issPortalFileRepository.findSumOfkccErrorRecordCount(financialYear);

            issPortalFileCountDTO.setTotalApplications(sumOfapplicationCount);
            issPortalFileCountDTO.setValidationErrors(sumOfErrorRecordCount);
            issPortalFileCountDTO.setkCCSubmitted(sumOfAppSubmitedTokccCount);
            issPortalFileCountDTO.setkCCAccepted(sumOfappAcceptedByKccCount);
            issPortalFileCountDTO.setkCCErrors(sumOfkccErrorRecordCount);
        }
        return issPortalFileCountDTO;
    }

    @Override
    public List<TalukaApplicationDTO> findIssPortalFilesByTalukaIdAndFinacialYear(Long talukaId, String finacialYear) {


        List<Tuple> tuples = issPortalFileRepository.findIssPortalFilesByTalukaIdAndFinacialYear(talukaId,finacialYear);
        return tuples.stream()
            .map(tuple -> {
                TalukaApplicationDTO dto = new TalukaApplicationDTO();
                dto.setApplication_count(tuple.get("application_count", Long.class));
                dto.setError_record_count(tuple.get("error_record_count", Long.class));
                dto.setKcc_submitted(tuple.get("kcc_submitted",Long.class));
                dto.setKcc_accepted(tuple.get("kcc_accepted",Long.class));
                dto.setKcc_error_count(tuple.get("kcc_error_count",Long.class));
                dto.setBranch_name(tuple.get("branch_name",String.class));
                dto.setPacs_code(tuple.get("pacs_code",Long.class));
                dto.setPacs_name(tuple.get("pacs_name",String.class));
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<TalukaApplicationDTO> findIssPortalFilesBySchemeWiseBranchCodeAndFinacialYear(String sBranchCode, String finacialYear) {

        List<Tuple> tuples = issPortalFileRepository.findIssPortalFilesBySchemeWiseBranchCodeAndFinacialYear(sBranchCode,finacialYear);
        return tuples.stream()
            .map(tuple -> {
                TalukaApplicationDTO dto = new TalukaApplicationDTO();
                dto.setApplication_count(tuple.get("application_count", Long.class));
                dto.setError_record_count(tuple.get("error_record_count", Long.class));
                dto.setKcc_submitted(tuple.get("kcc_submitted",Long.class));
                dto.setKcc_accepted(tuple.get("kcc_accepted",Long.class));
                dto.setKcc_error_count(tuple.get("kcc_error_count",Long.class));
                dto.setBranch_name(tuple.get("branch_name",String.class));
                dto.setPacs_code(tuple.get("pacs_code",Long.class));
                dto.setPacs_name(tuple.get("pacs_name",String.class));
                return dto;
            })
            .collect(Collectors.toList());
    }


}
