package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.domain.User;
import com.cbs.middleware.repository.KamalSocietyRepository;
import com.cbs.middleware.repository.UserRepository;
import com.cbs.middleware.security.AuthoritiesConstants;
import com.cbs.middleware.service.KamalSocietyService;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KamalSociety}.
 */
@Service
@Transactional
public class KamalSocietyServiceImpl implements KamalSocietyService {

    private final Logger log = LoggerFactory.getLogger(KamalSocietyServiceImpl.class);

    private final KamalSocietyRepository kamalSocietyRepository;

    public KamalSocietyServiceImpl(KamalSocietyRepository kamalSocietyRepository) {
        this.kamalSocietyRepository = kamalSocietyRepository;
    }

    @Override
    public KamalSociety save(KamalSociety kamalSociety) {
        log.debug("Request to save KamalSociety : {}", kamalSociety);
        return kamalSocietyRepository.save(kamalSociety);
    }

    @Override
    public KamalSociety update(KamalSociety kamalSociety) {
        log.debug("Request to update KamalSociety : {}", kamalSociety);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();

        Optional<KamalSociety> kamalSocietyDB = kamalSocietyRepository.findById(kamalSociety.getId());
        if (kamalSocietyDB.isPresent()) {
            kamalSociety.setPacsNumber(kamalSocietyDB.get().getPacsNumber());
            kamalSociety.setPacsName(kamalSocietyDB.get().getPacsName());
            kamalSociety.setBranchId(kamalSocietyDB.get().getBranchId());
            kamalSociety.setBranchName(kamalSocietyDB.get().getBranchName());
            kamalSociety.setTalukaId(kamalSocietyDB.get().getTalukaId());
            kamalSociety.setTalukaName(kamalSocietyDB.get().getTalukaName());
        }


        if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_USER)) {
            kamalSociety.setBranchVerifiedBy(auth.getName());
            kamalSociety.setBranchVerifiedDate(Instant.now());
        } else if (authority.toString().equals(AuthoritiesConstants.ROLE_BRANCH_ADMIN)) {
            kamalSociety.setHeadOfficeVerifiedBy(auth.getName());
            kamalSociety.setHeadOfficeVerifiedDate(Instant.now());
        } else if (authority.toString().equals(AuthoritiesConstants.ADMIN)) {
            kamalSociety.setDivisionalOfficeVerifiedBy(AuthoritiesConstants.ADMIN);
            kamalSociety.setDivisionalOfficeVerifiedDate(Instant.now());
        }
        return kamalSocietyRepository.save(kamalSociety);
    }

    @Override
    public Optional<KamalSociety> partialUpdate(KamalSociety kamalSociety) {

        log.debug("Request to partially update KamalSociety : {}", kamalSociety);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        GrantedAuthority authority = authorities.stream().findFirst().get();

        return kamalSocietyRepository
            .findById(kamalSociety.getId())
            .map(existingKamalSociety -> {
                if (kamalSociety.getFinancialYear() != null) {
                    existingKamalSociety.setFinancialYear(kamalSociety.getFinancialYear());
                }
                if (kamalSociety.getKmDate() != null) {
                    existingKamalSociety.setKmDate(kamalSociety.getKmDate());
                }
                if (kamalSociety.getKmDateMr() != null) {
                    existingKamalSociety.setKmDateMr(kamalSociety.getKmDateMr());
                }
                if (kamalSociety.getKmFromDate() != null) {
                    existingKamalSociety.setKmFromDate(kamalSociety.getKmFromDate());
                }
                if (kamalSociety.getKmFromDateMr() != null) {
                    existingKamalSociety.setKmFromDateMr(kamalSociety.getKmFromDateMr());
                }
                if (kamalSociety.getKmToDate() != null) {
                    existingKamalSociety.setKmToDate(kamalSociety.getKmToDate());
                }
                if (kamalSociety.getKmToDateMr() != null) {
                    existingKamalSociety.setKmToDateMr(kamalSociety.getKmToDateMr());
                }
                if (kamalSociety.getPacsNumber() != null) {
                    existingKamalSociety.setPacsNumber(kamalSociety.getPacsNumber());
                }
                if (kamalSociety.getPacsName() != null) {
                    existingKamalSociety.setPacsName(kamalSociety.getPacsName());
                }
                if (kamalSociety.getBranchId() != null) {
                    existingKamalSociety.setBranchId(kamalSociety.getBranchId());
                }
                if (kamalSociety.getBranchName() != null) {
                    existingKamalSociety.setBranchName(kamalSociety.getBranchName());
                }
                if (kamalSociety.getZindagiPatrakDate() != null) {
                    existingKamalSociety.setZindagiPatrakDate(kamalSociety.getZindagiPatrakDate());
                }
                if (kamalSociety.getZindagiPatrakDateMr() != null) {
                    existingKamalSociety.setZindagiPatrakDateMr(kamalSociety.getZindagiPatrakDateMr());
                }
                if (kamalSociety.getBankTapasaniDate() != null) {
                    existingKamalSociety.setBankTapasaniDate(kamalSociety.getBankTapasaniDate());
                }
                if (kamalSociety.getBankTapasaniDateMr() != null) {
                    existingKamalSociety.setBankTapasaniDateMr(kamalSociety.getBankTapasaniDateMr());
                }
                if (kamalSociety.getGovTapasaniDate() != null) {
                    existingKamalSociety.setGovTapasaniDate(kamalSociety.getGovTapasaniDate());
                }
                if (kamalSociety.getGovTapasaniDateMr() != null) {
                    existingKamalSociety.setGovTapasaniDateMr(kamalSociety.getGovTapasaniDateMr());
                }
                if (kamalSociety.getSansthaTapasaniDate() != null) {
                    existingKamalSociety.setSansthaTapasaniDate(kamalSociety.getSansthaTapasaniDate());
                }
                if (kamalSociety.getSansthaTapasaniDateMr() != null) {
                    existingKamalSociety.setSansthaTapasaniDateMr(kamalSociety.getSansthaTapasaniDateMr());
                }
                if (kamalSociety.getTotalLand() != null) {
                    existingKamalSociety.setTotalLand(kamalSociety.getTotalLand());
                }
                if (kamalSociety.getBagayat() != null) {
                    existingKamalSociety.setBagayat(kamalSociety.getBagayat());
                }
                if (kamalSociety.getJirayat() != null) {
                    existingKamalSociety.setJirayat(kamalSociety.getJirayat());
                }
                if (kamalSociety.getTotalFarmer() != null) {
                    existingKamalSociety.setTotalFarmer(kamalSociety.getTotalFarmer());
                }
                if (kamalSociety.getMemberFarmer() != null) {
                    existingKamalSociety.setMemberFarmer(kamalSociety.getMemberFarmer());
                }
                if (kamalSociety.getNonMemberFarmer() != null) {
                    existingKamalSociety.setNonMemberFarmer(kamalSociety.getNonMemberFarmer());
                }
                if (kamalSociety.getTalebandDate() != null) {
                    existingKamalSociety.setTalebandDate(kamalSociety.getTalebandDate());
                }
                if (kamalSociety.getMemLoan() != null) {
                    existingKamalSociety.setMemLoan(kamalSociety.getMemLoan());
                }
                if (kamalSociety.getMemDue() != null) {
                    existingKamalSociety.setMemDue(kamalSociety.getMemDue());
                }
                if (kamalSociety.getMemVasuli() != null) {
                    existingKamalSociety.setMemVasuli(kamalSociety.getMemVasuli());
                }
                if (kamalSociety.getMemVasuliPer() != null) {
                    existingKamalSociety.setMemVasuliPer(kamalSociety.getMemVasuliPer());
                }
                if (kamalSociety.getBankLoan() != null) {
                    existingKamalSociety.setBankLoan(kamalSociety.getBankLoan());
                }
                if (kamalSociety.getBankDue() != null) {
                    existingKamalSociety.setBankDue(kamalSociety.getBankDue());
                }
                if (kamalSociety.getBankVasuli() != null) {
                    existingKamalSociety.setBankVasuli(kamalSociety.getBankVasuli());
                }
                if (kamalSociety.getBankVasuliPer() != null) {
                    existingKamalSociety.setBankVasuliPer(kamalSociety.getBankVasuliPer());
                }
                if (kamalSociety.getBalanceSheetDate() != null) {
                    existingKamalSociety.setBalanceSheetDate(kamalSociety.getBalanceSheetDate());
                }
                if (kamalSociety.getBalanceSheetDateMr() != null) {
                    existingKamalSociety.setBalanceSheetDateMr(kamalSociety.getBalanceSheetDateMr());
                }
                if (kamalSociety.getLiabilityAdhikrutShareCapital() != null) {
                    existingKamalSociety.setLiabilityAdhikrutShareCapital(kamalSociety.getLiabilityAdhikrutShareCapital());
                }
                if (kamalSociety.getLiabilityVasulShareCapital() != null) {
                    existingKamalSociety.setLiabilityVasulShareCapital(kamalSociety.getLiabilityVasulShareCapital());
                }
                if (kamalSociety.getLiabilityFund() != null) {
                    existingKamalSociety.setLiabilityFund(kamalSociety.getLiabilityFund());
                }
                if (kamalSociety.getLiabilitySpareFund() != null) {
                    existingKamalSociety.setLiabilitySpareFund(kamalSociety.getLiabilitySpareFund());
                }
                if (kamalSociety.getLiabilityDeposite() != null) {
                    existingKamalSociety.setLiabilityDeposite(kamalSociety.getLiabilityDeposite());
                }
                if (kamalSociety.getLiabilityBalanceSheetBankLoan() != null) {
                    existingKamalSociety.setLiabilityBalanceSheetBankLoan(kamalSociety.getLiabilityBalanceSheetBankLoan());
                }
                if (kamalSociety.getLiabilityOtherPayable() != null) {
                    existingKamalSociety.setLiabilityOtherPayable(kamalSociety.getLiabilityOtherPayable());
                }
                if (kamalSociety.getLiabilityProfit() != null) {
                    existingKamalSociety.setLiabilityProfit(kamalSociety.getLiabilityProfit());
                }
                if (kamalSociety.getAssetCash() != null) {
                    existingKamalSociety.setAssetCash(kamalSociety.getAssetCash());
                }
                if (kamalSociety.getAssetInvestment() != null) {
                    existingKamalSociety.setAssetInvestment(kamalSociety.getAssetInvestment());
                }
                if (kamalSociety.getAssetImaratFund() != null) {
                    existingKamalSociety.setAssetImaratFund(kamalSociety.getAssetImaratFund());
                }
                if (kamalSociety.getAssetMemberLoan() != null) {
                    existingKamalSociety.setAssetMemberLoan(kamalSociety.getAssetMemberLoan());
                }
                if (kamalSociety.getAssetDeadStock() != null) {
                    existingKamalSociety.setAssetDeadStock(kamalSociety.getAssetDeadStock());
                }
                if (kamalSociety.getAssetOtherReceivable() != null) {
                    existingKamalSociety.setAssetOtherReceivable(kamalSociety.getAssetOtherReceivable());
                }
                if (kamalSociety.getAssetLoss() != null) {
                    existingKamalSociety.setAssetLoss(kamalSociety.getAssetLoss());
                }
                if (kamalSociety.getTotalLiability() != null) {
                    existingKamalSociety.setTotalLiability(kamalSociety.getTotalLiability());
                }
                if (kamalSociety.getTotalAsset() != null) {
                    existingKamalSociety.setTotalAsset(kamalSociety.getTotalAsset());
                }
                if (kamalSociety.getVillageCode() != null) {
                    existingKamalSociety.setVillageCode(kamalSociety.getVillageCode());
                }
                if (kamalSociety.getPacsVerifiedFlag() != null) {
                    existingKamalSociety.setPacsVerifiedFlag(kamalSociety.getPacsVerifiedFlag());
                }
                if (kamalSociety.getBranchVerifiedFlag() != null) {
                    existingKamalSociety.setBranchVerifiedFlag(kamalSociety.getBranchVerifiedFlag());
                    existingKamalSociety.setBranchVerifiedBy(auth.getName());
                    existingKamalSociety.setBranchVerifiedDate(Instant.now());
                }
                if (kamalSociety.getHeadOfficeVerifiedFlag() != null) {
                    existingKamalSociety.setHeadOfficeVerifiedFlag(kamalSociety.getHeadOfficeVerifiedFlag());
                    existingKamalSociety.setHeadOfficeVerifiedBy(auth.getName());
                    existingKamalSociety.setHeadOfficeVerifiedDate(Instant.now());
                }
                if (kamalSociety.getDivisionalOfficeVerifiedFlag() != null) {
                    existingKamalSociety.setDivisionalOfficeVerifiedFlag(kamalSociety.getDivisionalOfficeVerifiedFlag());
                    existingKamalSociety.setDivisionalOfficeVerifiedBy(auth.getName());
                    existingKamalSociety.setDivisionalOfficeVerifiedDate(Instant.now());
                }
                if (kamalSociety.getIsSupplimenteryFlag() != null) {
                    existingKamalSociety.setIsSupplimenteryFlag(kamalSociety.getIsSupplimenteryFlag());
                }
                if (kamalSociety.getSansthaTapasaniVarg() != null) {
                    existingKamalSociety.setSansthaTapasaniVarg(kamalSociety.getSansthaTapasaniVarg());
                }
                if (kamalSociety.getBranchVerifiedBy() != null) {
                    existingKamalSociety.setBranchVerifiedBy(kamalSociety.getBranchVerifiedBy());
                }
                if (kamalSociety.getBranchVerifiedDate() != null) {
                    existingKamalSociety.setBranchVerifiedDate(kamalSociety.getBranchVerifiedDate());
                }
                if (kamalSociety.getHeadOfficeVerifiedBy() != null) {
                    existingKamalSociety.setHeadOfficeVerifiedBy(kamalSociety.getHeadOfficeVerifiedBy());
                }
                if (kamalSociety.getHeadOfficeVerifiedDate() != null) {
                    existingKamalSociety.setHeadOfficeVerifiedDate(kamalSociety.getHeadOfficeVerifiedDate());
                }
                if (kamalSociety.getDivisionalOfficeVerifiedBy() != null) {
                    existingKamalSociety.setDivisionalOfficeVerifiedBy(kamalSociety.getDivisionalOfficeVerifiedBy());
                }
                if (kamalSociety.getDivisionalOfficeVerifiedDate() != null) {
                    existingKamalSociety.setDivisionalOfficeVerifiedDate(kamalSociety.getDivisionalOfficeVerifiedDate());
                }
                if (kamalSociety.getDoshPurtataDate() != null) {
                    existingKamalSociety.setDoshPurtataDate(kamalSociety.getDoshPurtataDate());
                }
                if (kamalSociety.getGambhirDosh() != null) {
                    existingKamalSociety.setGambhirDosh(kamalSociety.getGambhirDosh());
                }
                if (kamalSociety.getTalukaId() != null) {
                    existingKamalSociety.setTalukaId(kamalSociety.getTalukaId());
                }
                if (kamalSociety.getTalukaName() != null) {
                    existingKamalSociety.setTalukaName(kamalSociety.getTalukaName());
                }
                if (kamalSociety.getBranchInwardNumber() != null) {
                    existingKamalSociety.setBranchInwardNumber(kamalSociety.getBranchInwardNumber());
                }
                if (kamalSociety.getBranchInwardDate() != null) {
                    existingKamalSociety.setBranchInwardDate(kamalSociety.getBranchInwardDate());
                }
                if (kamalSociety.getBranchOutwardNumber() != null) {
                    existingKamalSociety.setBranchOutwardNumber(kamalSociety.getBranchOutwardNumber());
                }
                if (kamalSociety.getBranchOutwardDate() != null) {
                    existingKamalSociety.setBranchOutwardDate(kamalSociety.getBranchOutwardDate());
                }
                if (kamalSociety.getHeadOfficeInwardNumber() != null) {
                    existingKamalSociety.setHeadOfficeInwardNumber(kamalSociety.getHeadOfficeInwardNumber());
                }
                if (kamalSociety.getHeadOfficeInwardDate() != null) {
                    existingKamalSociety.setHeadOfficeInwardDate(kamalSociety.getHeadOfficeInwardDate());
                }
                if (kamalSociety.getHeadOfficeOutwardNumber() != null) {
                    existingKamalSociety.setHeadOfficeOutwardNumber(kamalSociety.getHeadOfficeOutwardNumber());
                }
                if (kamalSociety.getHeadOfficeOutwardDate() != null) {
                    existingKamalSociety.setHeadOfficeOutwardDate(kamalSociety.getHeadOfficeOutwardDate());
                }
                if (kamalSociety.getTharavNumber() != null) {
                    existingKamalSociety.setTharavNumber(kamalSociety.getTharavNumber());
                }
                if (kamalSociety.getTharavDate() != null) {
                    existingKamalSociety.setTharavDate(kamalSociety.getTharavDate());
                }

                if (kamalSociety.getKamalKarjMarayadaAmount() != null) {
                    existingKamalSociety.setKamalKarjMarayadaAmount(kamalSociety.getKamalKarjMarayadaAmount());
                }
                if (kamalSociety.getAgriAdminVerifiedFlag() != null) {
                    existingKamalSociety.setAgriAdminVerifiedFlag(kamalSociety.getAgriAdminVerifiedFlag());
                    existingKamalSociety.setAgriAdminVerifiedBy(auth.getName());
                    existingKamalSociety.setAgriAdminVerifiedDate(Instant.now());
                }
                if (kamalSociety.getAgriAdminVerifiedBy() != null) {
                    existingKamalSociety.setAgriAdminVerifiedBy(kamalSociety.getAgriAdminVerifiedBy());
                }
                if (kamalSociety.getAgriAdminVerifiedDate() != null) {
                    existingKamalSociety.setAgriAdminVerifiedDate(kamalSociety.getAgriAdminVerifiedDate());
                }
                return existingKamalSociety;
            })
            .map(kamalSocietyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KamalSociety> findAll(Pageable pageable) {
        log.debug("Request to get all KamalSocieties");
        return kamalSocietyRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KamalSociety> findOne(Long id) {
        log.debug("Request to get KamalSociety : {}", id);
        return kamalSocietyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KamalSociety : {}", id);
        kamalSocietyRepository.deleteById(id);
    }
}
