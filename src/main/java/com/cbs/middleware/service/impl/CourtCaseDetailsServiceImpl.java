package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CourtCaseDetails;
import com.cbs.middleware.repository.CourtCaseDetailsRepository;
import com.cbs.middleware.service.CourtCaseDetailsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourtCaseDetails}.
 */
@Service
@Transactional
public class CourtCaseDetailsServiceImpl implements CourtCaseDetailsService {

    private final Logger log = LoggerFactory.getLogger(CourtCaseDetailsServiceImpl.class);

    private final CourtCaseDetailsRepository courtCaseDetailsRepository;

    public CourtCaseDetailsServiceImpl(CourtCaseDetailsRepository courtCaseDetailsRepository) {
        this.courtCaseDetailsRepository = courtCaseDetailsRepository;
    }

    @Override
    public CourtCaseDetails save(CourtCaseDetails courtCaseDetails) {
        log.debug("Request to save CourtCaseDetails : {}", courtCaseDetails);
        return courtCaseDetailsRepository.save(courtCaseDetails);
    }

    @Override
    public CourtCaseDetails update(CourtCaseDetails courtCaseDetails) {
        log.debug("Request to update CourtCaseDetails : {}", courtCaseDetails);
        return courtCaseDetailsRepository.save(courtCaseDetails);
    }

    @Override
    public Optional<CourtCaseDetails> partialUpdate(CourtCaseDetails courtCaseDetails) {
        log.debug("Request to partially update CourtCaseDetails : {}", courtCaseDetails);

        return courtCaseDetailsRepository
            .findById(courtCaseDetails.getId())
            .map(existingCourtCaseDetails -> {
                if (courtCaseDetails.getKramank() != null) {
                    existingCourtCaseDetails.setKramank(courtCaseDetails.getKramank());
                }
                if (courtCaseDetails.getDinank() != null) {
                    existingCourtCaseDetails.setDinank(courtCaseDetails.getDinank());
                }
                if (courtCaseDetails.getCaseDinank() != null) {
                    existingCourtCaseDetails.setCaseDinank(courtCaseDetails.getCaseDinank());
                }
                if (courtCaseDetails.getSabhasad() != null) {
                    existingCourtCaseDetails.setSabhasad(courtCaseDetails.getSabhasad());
                }
                if (courtCaseDetails.getSabhasadAccNo() != null) {
                    existingCourtCaseDetails.setSabhasadAccNo(courtCaseDetails.getSabhasadAccNo());
                }
                if (courtCaseDetails.getKarjPrakarType() != null) {
                    existingCourtCaseDetails.setKarjPrakarType(courtCaseDetails.getKarjPrakarType());
                }
                if (courtCaseDetails.getKarjPrakar() != null) {
                    existingCourtCaseDetails.setKarjPrakar(courtCaseDetails.getKarjPrakar());
                }
                if (courtCaseDetails.getCertificateMilale() != null) {
                    existingCourtCaseDetails.setCertificateMilale(courtCaseDetails.getCertificateMilale());
                }
                if (courtCaseDetails.getCertificateDinnank() != null) {
                    existingCourtCaseDetails.setCertificateDinnank(courtCaseDetails.getCertificateDinnank());
                }
                if (courtCaseDetails.getCertificateRakkam() != null) {
                    existingCourtCaseDetails.setCertificateRakkam(courtCaseDetails.getCertificateRakkam());
                }
                if (courtCaseDetails.getYenebaki() != null) {
                    existingCourtCaseDetails.setYenebaki(courtCaseDetails.getYenebaki());
                }
                if (courtCaseDetails.getVyaj() != null) {
                    existingCourtCaseDetails.setVyaj(courtCaseDetails.getVyaj());
                }
                if (courtCaseDetails.getEtar() != null) {
                    existingCourtCaseDetails.setEtar(courtCaseDetails.getEtar());
                }
                if (courtCaseDetails.getDimmandMilale() != null) {
                    existingCourtCaseDetails.setDimmandMilale(courtCaseDetails.getDimmandMilale());
                }
                if (courtCaseDetails.getDimmandDinnank() != null) {
                    existingCourtCaseDetails.setDimmandDinnank(courtCaseDetails.getDimmandDinnank());
                }
                if (courtCaseDetails.getJaptiAadhesh() != null) {
                    existingCourtCaseDetails.setJaptiAadhesh(courtCaseDetails.getJaptiAadhesh());
                }
                if (courtCaseDetails.getJaptiAadheshDinnank() != null) {
                    existingCourtCaseDetails.setJaptiAadheshDinnank(courtCaseDetails.getJaptiAadheshDinnank());
                }
                if (courtCaseDetails.getSthavr() != null) {
                    existingCourtCaseDetails.setSthavr(courtCaseDetails.getSthavr());
                }
                if (courtCaseDetails.getJangam() != null) {
                    existingCourtCaseDetails.setJangam(courtCaseDetails.getJangam());
                }
                if (courtCaseDetails.getVikriAadhesh() != null) {
                    existingCourtCaseDetails.setVikriAadhesh(courtCaseDetails.getVikriAadhesh());
                }
                if (courtCaseDetails.getVikriAddheshDinnank() != null) {
                    existingCourtCaseDetails.setVikriAddheshDinnank(courtCaseDetails.getVikriAddheshDinnank());
                }
                if (courtCaseDetails.getEtarTapshil() != null) {
                    existingCourtCaseDetails.setEtarTapshil(courtCaseDetails.getEtarTapshil());
                }

                return existingCourtCaseDetails;
            })
            .map(courtCaseDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourtCaseDetails> findAll(Pageable pageable) {
        log.debug("Request to get all CourtCaseDetails");
        return courtCaseDetailsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourtCaseDetails> findOne(Long id) {
        log.debug("Request to get CourtCaseDetails : {}", id);
        return courtCaseDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourtCaseDetails : {}", id);
        courtCaseDetailsRepository.deleteById(id);
    }
}
