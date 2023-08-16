package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CbsDataReport;
import com.cbs.middleware.repository.CbsDataReportRepository;
import com.cbs.middleware.service.CbsDataReportService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CbsDataReport}.
 */
@Service
@Transactional
public class CbsDataReportServiceImpl implements CbsDataReportService {

    private final Logger log = LoggerFactory.getLogger(CbsDataReportServiceImpl.class);

    private final CbsDataReportRepository cbsDataReportRepository;

    public CbsDataReportServiceImpl(CbsDataReportRepository cbsDataReportRepository) {
        this.cbsDataReportRepository = cbsDataReportRepository;
    }

    @Override
    public CbsDataReport save(CbsDataReport cbsDataReport) {
        log.debug("Request to save CbsDataReport : {}", cbsDataReport);
        return cbsDataReportRepository.save(cbsDataReport);
    }

    @Override
    public CbsDataReport update(CbsDataReport cbsDataReport) {
        log.debug("Request to update CbsDataReport : {}", cbsDataReport);
        return cbsDataReportRepository.save(cbsDataReport);
    }

    @Override
    public Optional<CbsDataReport> partialUpdate(CbsDataReport cbsDataReport) {
        log.debug("Request to partially update CbsDataReport : {}", cbsDataReport);

        return cbsDataReportRepository
            .findById(cbsDataReport.getId())
            .map(existingCbsDataReport -> {
                if (cbsDataReport.getFarmerName() != null) {
                    existingCbsDataReport.setFarmerName(cbsDataReport.getFarmerName());
                }

                return existingCbsDataReport;
            })
            .map(cbsDataReportRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbsDataReport> findAll(Pageable pageable) {
        log.debug("Request to get all CbsDataReports");
        return cbsDataReportRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbsDataReport> findOne(Long id) {
        log.debug("Request to get CbsDataReport : {}", id);
        return cbsDataReportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbsDataReport : {}", id);
        cbsDataReportRepository.deleteById(id);
    }
}
