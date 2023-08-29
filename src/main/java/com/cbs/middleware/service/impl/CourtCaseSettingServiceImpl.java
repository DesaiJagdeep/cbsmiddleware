package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.service.CourtCaseSettingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourtCaseSetting}.
 */
@Service
@Transactional
public class CourtCaseSettingServiceImpl implements CourtCaseSettingService {

    private final Logger log = LoggerFactory.getLogger(CourtCaseSettingServiceImpl.class);

    private final CourtCaseSettingRepository courtCaseSettingRepository;

    public CourtCaseSettingServiceImpl(CourtCaseSettingRepository courtCaseSettingRepository) {
        this.courtCaseSettingRepository = courtCaseSettingRepository;
    }

    @Override
    public CourtCaseSetting save(CourtCaseSetting courtCaseSetting) {
        log.debug("Request to save CourtCaseSetting : {}", courtCaseSetting);
        return courtCaseSettingRepository.save(courtCaseSetting);
    }

    @Override
    public CourtCaseSetting update(CourtCaseSetting courtCaseSetting) {
        log.debug("Request to update CourtCaseSetting : {}", courtCaseSetting);
        return courtCaseSettingRepository.save(courtCaseSetting);
    }

    @Override
    public Optional<CourtCaseSetting> partialUpdate(CourtCaseSetting courtCaseSetting) {
        log.debug("Request to partially update CourtCaseSetting : {}", courtCaseSetting);

        return courtCaseSettingRepository
            .findById(courtCaseSetting.getId())
            .map(existingCourtCaseSetting -> {
                if (courtCaseSetting.getDinank() != null) {
                    existingCourtCaseSetting.setDinank(courtCaseSetting.getDinank());
                }
                if (courtCaseSetting.getShakhaVevsthapak() != null) {
                    existingCourtCaseSetting.setShakhaVevsthapak(courtCaseSetting.getShakhaVevsthapak());
                }
                if (courtCaseSetting.getSuchak() != null) {
                    existingCourtCaseSetting.setSuchak(courtCaseSetting.getSuchak());
                }
                if (courtCaseSetting.getAanumodak() != null) {
                    existingCourtCaseSetting.setAanumodak(courtCaseSetting.getAanumodak());
                }
                if (courtCaseSetting.getVasuliAdhikari() != null) {
                    existingCourtCaseSetting.setVasuliAdhikari(courtCaseSetting.getVasuliAdhikari());
                }
                if (courtCaseSetting.getArOffice() != null) {
                    existingCourtCaseSetting.setArOffice(courtCaseSetting.getArOffice());
                }
                if (courtCaseSetting.getTharavNumber() != null) {
                    existingCourtCaseSetting.setTharavNumber(courtCaseSetting.getTharavNumber());
                }
                if (courtCaseSetting.getTharavDinank() != null) {
                    existingCourtCaseSetting.setTharavDinank(courtCaseSetting.getTharavDinank());
                }
                if (courtCaseSetting.getKarjFedNotice() != null) {
                    existingCourtCaseSetting.setKarjFedNotice(courtCaseSetting.getKarjFedNotice());
                }
                if (courtCaseSetting.getOneZeroOneNoticeOne() != null) {
                    existingCourtCaseSetting.setOneZeroOneNoticeOne(courtCaseSetting.getOneZeroOneNoticeOne());
                }
                if (courtCaseSetting.getOneZeroOneNoticeTwo() != null) {
                    existingCourtCaseSetting.setOneZeroOneNoticeTwo(courtCaseSetting.getOneZeroOneNoticeTwo());
                }
                if (courtCaseSetting.getVishayKramank() != null) {
                    existingCourtCaseSetting.setVishayKramank(courtCaseSetting.getVishayKramank());
                }
                if (courtCaseSetting.getWar() != null) {
                    existingCourtCaseSetting.setWar(courtCaseSetting.getWar());
                }
                if (courtCaseSetting.getVel() != null) {
                    existingCourtCaseSetting.setVel(courtCaseSetting.getVel());
                }
                if (courtCaseSetting.getMaganiNotice() != null) {
                    existingCourtCaseSetting.setMaganiNotice(courtCaseSetting.getMaganiNotice());
                }
                if (courtCaseSetting.getEtarKharch() != null) {
                    existingCourtCaseSetting.setEtarKharch(courtCaseSetting.getEtarKharch());
                }
                if (courtCaseSetting.getNoticeKharch() != null) {
                    existingCourtCaseSetting.setNoticeKharch(courtCaseSetting.getNoticeKharch());
                }
                if (courtCaseSetting.getVasuliKharch() != null) {
                    existingCourtCaseSetting.setVasuliKharch(courtCaseSetting.getVasuliKharch());
                }

                return existingCourtCaseSetting;
            })
            .map(courtCaseSettingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourtCaseSetting> findAll(Pageable pageable) {
        log.debug("Request to get all CourtCaseSettings");
        return courtCaseSettingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourtCaseSetting> findOne(Long id) {
        log.debug("Request to get CourtCaseSetting : {}", id);
        return courtCaseSettingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourtCaseSetting : {}", id);
        courtCaseSettingRepository.deleteById(id);
    }
}
