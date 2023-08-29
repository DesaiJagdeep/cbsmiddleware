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
                if (courtCase.getCode() != null) {
                    existingCourtCase.setCode(courtCase.getCode());
                }
                if (courtCase.getCaseDinank() != null) {
                    existingCourtCase.setCaseDinank(courtCase.getCaseDinank());
                }
                if (courtCase.getBankName() != null) {
                    existingCourtCase.setBankName(courtCase.getBankName());
                }
                if (courtCase.getTalukaName() != null) {
                    existingCourtCase.setTalukaName(courtCase.getTalukaName());
                }
                if (courtCase.getTalukaCode() != null) {
                    existingCourtCase.setTalukaCode(courtCase.getTalukaCode());
                }
                if (courtCase.getSabasadSavingAccNo() != null) {
                    existingCourtCase.setSabasadSavingAccNo(courtCase.getSabasadSavingAccNo());
                }
                if (courtCase.getSabasadName() != null) {
                    existingCourtCase.setSabasadName(courtCase.getSabasadName());
                }
                if (courtCase.getSabasadAddress() != null) {
                    existingCourtCase.setSabasadAddress(courtCase.getSabasadAddress());
                }
                if (courtCase.getKarjPrakar() != null) {
                    existingCourtCase.setKarjPrakar(courtCase.getKarjPrakar());
                }
                if (courtCase.getVasuliAdhikari() != null) {
                    existingCourtCase.setVasuliAdhikari(courtCase.getVasuliAdhikari());
                }
                if (courtCase.getEkunJama() != null) {
                    existingCourtCase.setEkunJama(courtCase.getEkunJama());
                }
                if (courtCase.getBaki() != null) {
                    existingCourtCase.setBaki(courtCase.getBaki());
                }
                if (courtCase.getArOffice() != null) {
                    existingCourtCase.setArOffice(courtCase.getArOffice());
                }
                if (courtCase.getEkunVyaj() != null) {
                    existingCourtCase.setEkunVyaj(courtCase.getEkunVyaj());
                }
                if (courtCase.getJamaVyaj() != null) {
                    existingCourtCase.setJamaVyaj(courtCase.getJamaVyaj());
                }
                if (courtCase.getDandVyaj() != null) {
                    existingCourtCase.setDandVyaj(courtCase.getDandVyaj());
                }
                if (courtCase.getKarjRakkam() != null) {
                    existingCourtCase.setKarjRakkam(courtCase.getKarjRakkam());
                }
                if (courtCase.getThakDinnank() != null) {
                    existingCourtCase.setThakDinnank(courtCase.getThakDinnank());
                }
                if (courtCase.getKarjDinnank() != null) {
                    existingCourtCase.setKarjDinnank(courtCase.getKarjDinnank());
                }
                if (courtCase.getMudatSampteDinank() != null) {
                    existingCourtCase.setMudatSampteDinank(courtCase.getMudatSampteDinank());
                }
                if (courtCase.getMudat() != null) {
                    existingCourtCase.setMudat(courtCase.getMudat());
                }
                if (courtCase.getVyaj() != null) {
                    existingCourtCase.setVyaj(courtCase.getVyaj());
                }
                if (courtCase.getHaptaRakkam() != null) {
                    existingCourtCase.setHaptaRakkam(courtCase.getHaptaRakkam());
                }
                if (courtCase.getShakhaVevsthapak() != null) {
                    existingCourtCase.setShakhaVevsthapak(courtCase.getShakhaVevsthapak());
                }
                if (courtCase.getSuchak() != null) {
                    existingCourtCase.setSuchak(courtCase.getSuchak());
                }
                if (courtCase.getAnumodak() != null) {
                    existingCourtCase.setAnumodak(courtCase.getAnumodak());
                }
                if (courtCase.getDava() != null) {
                    existingCourtCase.setDava(courtCase.getDava());
                }
                if (courtCase.getVyajDar() != null) {
                    existingCourtCase.setVyajDar(courtCase.getVyajDar());
                }
                if (courtCase.getSarcharge() != null) {
                    existingCourtCase.setSarcharge(courtCase.getSarcharge());
                }
                if (courtCase.getJyadaVyaj() != null) {
                    existingCourtCase.setJyadaVyaj(courtCase.getJyadaVyaj());
                }
                if (courtCase.getYeneVyaj() != null) {
                    existingCourtCase.setYeneVyaj(courtCase.getYeneVyaj());
                }
                if (courtCase.getVasuliKharch() != null) {
                    existingCourtCase.setVasuliKharch(courtCase.getVasuliKharch());
                }
                if (courtCase.getEtharKharch() != null) {
                    existingCourtCase.setEtharKharch(courtCase.getEtharKharch());
                }
                if (courtCase.getVima() != null) {
                    existingCourtCase.setVima(courtCase.getVima());
                }
                if (courtCase.getNotice() != null) {
                    existingCourtCase.setNotice(courtCase.getNotice());
                }
                if (courtCase.getTharavNumber() != null) {
                    existingCourtCase.setTharavNumber(courtCase.getTharavNumber());
                }
                if (courtCase.getTharavDinank() != null) {
                    existingCourtCase.setTharavDinank(courtCase.getTharavDinank());
                }
                if (courtCase.getVishayKramank() != null) {
                    existingCourtCase.setVishayKramank(courtCase.getVishayKramank());
                }
                if (courtCase.getNoticeOne() != null) {
                    existingCourtCase.setNoticeOne(courtCase.getNoticeOne());
                }
                if (courtCase.getNoticeTwo() != null) {
                    existingCourtCase.setNoticeTwo(courtCase.getNoticeTwo());
                }
                if (courtCase.getWar() != null) {
                    existingCourtCase.setWar(courtCase.getWar());
                }
                if (courtCase.getVel() != null) {
                    existingCourtCase.setVel(courtCase.getVel());
                }
                if (courtCase.getJamindarOne() != null) {
                    existingCourtCase.setJamindarOne(courtCase.getJamindarOne());
                }
                if (courtCase.getJamindarOneAddress() != null) {
                    existingCourtCase.setJamindarOneAddress(courtCase.getJamindarOneAddress());
                }
                if (courtCase.getJamindarTwo() != null) {
                    existingCourtCase.setJamindarTwo(courtCase.getJamindarTwo());
                }
                if (courtCase.getJamindarTwoAddress() != null) {
                    existingCourtCase.setJamindarTwoAddress(courtCase.getJamindarTwoAddress());
                }
                if (courtCase.getTaranType() != null) {
                    existingCourtCase.setTaranType(courtCase.getTaranType());
                }
                if (courtCase.getTaranDetails() != null) {
                    existingCourtCase.setTaranDetails(courtCase.getTaranDetails());
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
