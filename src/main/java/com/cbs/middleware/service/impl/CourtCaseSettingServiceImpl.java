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
                if (courtCaseSetting.getVasuliAdhikariName() != null) {
                    existingCourtCaseSetting.setVasuliAdhikariName(courtCaseSetting.getVasuliAdhikariName());
                }
                if (courtCaseSetting.getArOfficeName() != null) {
                    existingCourtCaseSetting.setArOfficeName(courtCaseSetting.getArOfficeName());
                }
                if (courtCaseSetting.getChairmanName() != null) {
                    existingCourtCaseSetting.setChairmanName(courtCaseSetting.getChairmanName());
                }
                if (courtCaseSetting.getSachivName() != null) {
                    existingCourtCaseSetting.setSachivName(courtCaseSetting.getSachivName());
                }
                if (courtCaseSetting.getSuchakName() != null) {
                    existingCourtCaseSetting.setSuchakName(courtCaseSetting.getSuchakName());
                }
                if (courtCaseSetting.getAnumodakName() != null) {
                    existingCourtCaseSetting.setAnumodakName(courtCaseSetting.getAnumodakName());
                }
                if (courtCaseSetting.getVasuliExpense() != null) {
                    existingCourtCaseSetting.setVasuliExpense(courtCaseSetting.getVasuliExpense());
                }
                if (courtCaseSetting.getOtherExpense() != null) {
                    existingCourtCaseSetting.setOtherExpense(courtCaseSetting.getOtherExpense());
                }
                if (courtCaseSetting.getNoticeExpense() != null) {
                    existingCourtCaseSetting.setNoticeExpense(courtCaseSetting.getNoticeExpense());
                }
                if (courtCaseSetting.getMeetingNo() != null) {
                    existingCourtCaseSetting.setMeetingNo(courtCaseSetting.getMeetingNo());
                }
                if (courtCaseSetting.getMeetingDate() != null) {
                    existingCourtCaseSetting.setMeetingDate(courtCaseSetting.getMeetingDate());
                }
                if (courtCaseSetting.getSubjectNo() != null) {
                    existingCourtCaseSetting.setSubjectNo(courtCaseSetting.getSubjectNo());
                }
                if (courtCaseSetting.getMeetingDay() != null) {
                    existingCourtCaseSetting.setMeetingDay(courtCaseSetting.getMeetingDay());
                }
                if (courtCaseSetting.getMeetingTime() != null) {
                    existingCourtCaseSetting.setMeetingTime(courtCaseSetting.getMeetingTime());
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
