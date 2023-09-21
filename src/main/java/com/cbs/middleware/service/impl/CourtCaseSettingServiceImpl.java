package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.CourtCaseSetting;
import com.cbs.middleware.repository.CourtCaseSettingRepository;
import com.cbs.middleware.service.CourtCaseSettingService;
import com.cbs.middleware.web.rest.utility.TranslationServiceUtility;
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
 * Service Implementation for managing {@link CourtCaseSetting}.
 */
@Service
@Transactional
public class CourtCaseSettingServiceImpl implements CourtCaseSettingService {

    private final Logger log = LoggerFactory.getLogger(CourtCaseSettingServiceImpl.class);

    private final CourtCaseSettingRepository courtCaseSettingRepository;

    @Autowired
    TranslationServiceUtility translationServiceUtility;

    public CourtCaseSettingServiceImpl(CourtCaseSettingRepository courtCaseSettingRepository) {
        this.courtCaseSettingRepository = courtCaseSettingRepository;
    }

    @Override
    public CourtCaseSetting save(CourtCaseSetting courtCaseSetting) {
        log.debug("Request to save CourtCaseSetting : {}", courtCaseSetting);

        try {
            if (StringUtils.isNotBlank(courtCaseSetting.getVasuliAdhikariName())) {
                // English
                courtCaseSetting.setVasuliAdhikariNameEn(
                    translationServiceUtility.translationTextMrToEn(courtCaseSetting.getVasuliAdhikariName())
                );
                // Marathi
                // courtCaseSetting.setVasuliAdhikariName(courtCaseSetting.getVasuliAdhikariName());

            }

            if (StringUtils.isNotBlank(courtCaseSetting.getArOfficeName())) {
                // English
                courtCaseSetting.setArOfficeNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getArOfficeName()));
                // Marathi
                // courtCaseSetting.setArOfficeName(courtCaseSetting.getArOfficeName());

            }

            if (StringUtils.isNotBlank(courtCaseSetting.getChairmanName())) {
                // English
                courtCaseSetting.setChairmanNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getChairmanName()));
                // Marathi
                // courtCaseSetting.setChairmanName(translationServiceUtility.translationText(courtCaseSetting.getChairmanName()));

            }

            if (StringUtils.isNotBlank(courtCaseSetting.getSachivName())) {
                // English
                courtCaseSetting.setSachivNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getSachivName()));
                // Marathi
                // courtCaseSetting.setSachivName(translationServiceUtility.translationText(courtCaseSetting.getSachivName()));

            }

            if (StringUtils.isNotBlank(courtCaseSetting.getSuchakName())) {
                // English
                courtCaseSetting.setSuchakNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getSuchakName()));
                // Marathi
                // courtCaseSetting.setSuchakName(translationServiceUtility.translationText(courtCaseSetting.getSuchakName()));

            }

            if (StringUtils.isNotBlank(courtCaseSetting.getAnumodakName())) {
                // English
                courtCaseSetting.setAnumodakNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getAnumodakName()));
                // Marathi
                // courtCaseSetting.setAnumodakName(translationServiceUtility.translationText(courtCaseSetting.getAnumodakName()));

            }
            if (StringUtils.isNotBlank(courtCaseSetting.getVasuliExpense())) {
                // English
                courtCaseSetting.setVasuliExpenseEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getVasuliExpense()))
                );
                // Marathi

                // courtCaseSetting.setVasuliExpense(translationServiceUtility.translationText(courtCaseSetting.getVasuliExpense()));

            }

            if (StringUtils.isNotBlank(courtCaseSetting.getOtherExpense())) {
                // English
                courtCaseSetting.setOtherExpenseEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getOtherExpense()))
                );
                // Marathi
                // courtCaseSetting.setOtherExpense(translationServiceUtility.translationText(courtCaseSetting.getOtherExpense()));

            }
            if (StringUtils.isNotBlank(courtCaseSetting.getNoticeExpense())) {
                // English
                courtCaseSetting.setNoticeExpenseEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getNoticeExpense()))
                );
                // Marathi
                // courtCaseSetting.setNoticeExpense(translationServiceUtility.translationText(courtCaseSetting.getNoticeExpense()));

            }

            if (StringUtils.isNotBlank(courtCaseSetting.getMeetingNo())) {
                // English
                courtCaseSetting.setMeetingNoEn(
                    Long.parseLong(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getMeetingNo()))
                );
                // Marathi

                // courtCaseSetting.setMeetingNo(translationServiceUtility.translationText(courtCaseSetting.getMeetingNo()));

            }

            if (courtCaseSetting.getMeetingDate() != null) {
                courtCaseSetting.setMeetingDate(courtCaseSetting.getMeetingDate());
            }

            if (StringUtils.isNotBlank(courtCaseSetting.getSubjectNo())) {
                // English
                courtCaseSetting.setSubjectNoEn(
                    Long.parseLong(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getSubjectNo()))
                );
                // Marathi

                // courtCaseSetting.setSubjectNo(translationServiceUtility.translationText(courtCaseSetting.getSubjectNo()));
            }

            if (StringUtils.isNotBlank(courtCaseSetting.getMeetingDay())) {
                // English
                courtCaseSetting.setMeetingDayEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getMeetingDay()));
                // Marathi
                // courtCaseSetting.setMeetingDay(translationServiceUtility.translationText(courtCaseSetting.getMeetingDay()));

            }

            if (StringUtils.isNotBlank(courtCaseSetting.getMeetingTime())) {
                // English
                courtCaseSetting.setMeetingTimeEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getMeetingTime()));
                // Marathi
                // courtCaseSetting.setMeetingTime(translationServiceUtility.translationText(courtCaseSetting.getMeetingTime()));

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return courtCaseSettingRepository.save(courtCaseSetting);
    }

    @Override
    public CourtCaseSetting update(CourtCaseSetting courtCaseSetting) {
        log.debug("Request to update CourtCaseSetting : {}", courtCaseSetting);

        Optional<CourtCaseSetting> findById = courtCaseSettingRepository.findById(courtCaseSetting.getId());

        CourtCaseSetting courtCaseSettingCheck = findById.get();

        try {
            if (
                StringUtils.isNotBlank(courtCaseSetting.getVasuliAdhikariName()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getVasuliAdhikariName()) &&
                !courtCaseSetting.getVasuliAdhikariName().equals(courtCaseSettingCheck.getVasuliAdhikariName())
            ) {
                // English
                courtCaseSetting.setVasuliAdhikariNameEn(
                    translationServiceUtility.translationTextMrToEn(courtCaseSetting.getVasuliAdhikariName())
                );
                // Marathi
                // courtCaseSetting.setVasuliAdhikariName(courtCaseSetting.getVasuliAdhikariName());

            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getArOfficeName()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getArOfficeName()) &&
                !courtCaseSetting.getArOfficeName().equals(courtCaseSettingCheck.getArOfficeName())
            ) {
                // English
                courtCaseSetting.setArOfficeNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getArOfficeName()));
                // Marathi
                // courtCaseSetting.setArOfficeName(courtCaseSetting.getArOfficeName());

            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getChairmanName()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getChairmanName()) &&
                !courtCaseSetting.getChairmanName().equals(courtCaseSettingCheck.getChairmanName())
            ) {
                // English
                courtCaseSetting.setChairmanNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getChairmanName()));
                // Marathi
                // courtCaseSetting.setChairmanName(translationServiceUtility.translationText(courtCaseSetting.getChairmanName()));

            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getSachivName()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getSachivName()) &&
                !courtCaseSetting.getSachivName().equals(courtCaseSettingCheck.getSachivName())
            ) {
                // English
                courtCaseSetting.setSachivNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getSachivName()));
                // Marathi
                // courtCaseSetting.setSachivName(translationServiceUtility.translationText(courtCaseSetting.getSachivName()));

            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getSuchakName()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getSuchakName()) &&
                !courtCaseSetting.getSuchakName().equals(courtCaseSettingCheck.getSuchakName())
            ) {
                // English
                courtCaseSetting.setSuchakNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getSuchakName()));
                // Marathi
                // courtCaseSetting.setSuchakName(translationServiceUtility.translationText(courtCaseSetting.getSuchakName()));

            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getAnumodakName()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getAnumodakName()) &&
                !courtCaseSetting.getAnumodakName().equals(courtCaseSettingCheck.getAnumodakName())
            ) {
                // English
                courtCaseSetting.setAnumodakNameEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getAnumodakName()));
                // Marathi
                // courtCaseSetting.setAnumodakName(translationServiceUtility.translationText(courtCaseSetting.getAnumodakName()));

            }
            if (
                StringUtils.isNotBlank(courtCaseSetting.getVasuliExpense()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getVasuliExpense()) &&
                !courtCaseSetting.getVasuliExpense().equals(courtCaseSettingCheck.getVasuliExpense())
            ) {
                // English
                courtCaseSetting.setVasuliExpenseEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getVasuliExpense()))
                );
                // Marathi

                // courtCaseSetting.setVasuliExpense(translationServiceUtility.translationText(courtCaseSetting.getVasuliExpense()));

            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getOtherExpense()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getOtherExpense()) &&
                !courtCaseSetting.getOtherExpense().equals(courtCaseSettingCheck.getOtherExpense())
            ) {
                // English
                courtCaseSetting.setOtherExpenseEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getOtherExpense()))
                );
                // Marathi
                // courtCaseSetting.setOtherExpense(translationServiceUtility.translationText(courtCaseSetting.getOtherExpense()));

            }
            if (
                StringUtils.isNotBlank(courtCaseSetting.getNoticeExpense()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getNoticeExpense()) &&
                !courtCaseSetting.getNoticeExpense().equals(courtCaseSettingCheck.getNoticeExpense())
            ) {
                // English
                courtCaseSetting.setNoticeExpenseEn(
                    Double.parseDouble(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getNoticeExpense()))
                );
                // Marathi
                // courtCaseSetting.setNoticeExpense(translationServiceUtility.translationText(courtCaseSetting.getNoticeExpense()));

            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getMeetingNo()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getMeetingNo()) &&
                !courtCaseSetting.getMeetingNo().equals(courtCaseSettingCheck.getMeetingNo())
            ) {
                // English
                courtCaseSetting.setMeetingNoEn(
                    Long.parseLong(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getMeetingNo()))
                );
                // Marathi

                // courtCaseSetting.setMeetingNo(translationServiceUtility.translationText(courtCaseSetting.getMeetingNo()));

            }

            if (courtCaseSetting.getMeetingDate() != null) {
                courtCaseSetting.setMeetingDate(courtCaseSetting.getMeetingDate());
            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getSubjectNo()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getSubjectNo()) &&
                !courtCaseSetting.getSubjectNo().equals(courtCaseSettingCheck.getSubjectNo())
            ) {
                // English
                courtCaseSetting.setSubjectNoEn(
                    Long.parseLong(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getSubjectNo()))
                );
                // Marathi

                // courtCaseSetting.setSubjectNo(translationServiceUtility.translationText(courtCaseSetting.getSubjectNo()));
            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getMeetingDay()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getMeetingDay()) &&
                !courtCaseSetting.getMeetingDay().equals(courtCaseSettingCheck.getMeetingDay())
            ) {
                // English
                courtCaseSetting.setMeetingDayEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getMeetingDay()));
                // Marathi
                // courtCaseSetting.setMeetingDay(translationServiceUtility.translationText(courtCaseSetting.getMeetingDay()));

            }

            if (
                StringUtils.isNotBlank(courtCaseSetting.getMeetingTime()) &&
                StringUtils.isNotBlank(courtCaseSettingCheck.getMeetingTime()) &&
                !courtCaseSetting.getMeetingTime().equals(courtCaseSettingCheck.getMeetingTime())
            ) {
                // English
                courtCaseSetting.setMeetingTimeEn(translationServiceUtility.translationTextMrToEn(courtCaseSetting.getMeetingTime()));
                // Marathi
                // courtCaseSetting.setMeetingTime(translationServiceUtility.translationText(courtCaseSetting.getMeetingTime()));

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

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
