package com.cbs.middleware.service.impl;

import com.cbs.middleware.domain.KamalSociety;
import com.cbs.middleware.repository.KamalSocietyRepository;
import com.cbs.middleware.service.KamalSocietyService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return kamalSocietyRepository.save(kamalSociety);
    }

    @Override
    public Optional<KamalSociety> partialUpdate(KamalSociety kamalSociety) {
        log.debug("Request to partially update KamalSociety : {}", kamalSociety);

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
                }
                if (kamalSociety.getHeadOfficeVerifiedFlag() != null) {
                    existingKamalSociety.setHeadOfficeVerifiedFlag(kamalSociety.getHeadOfficeVerifiedFlag());
                }
                if (kamalSociety.getIsSupplimenteryFlag() != null) {
                    existingKamalSociety.setIsSupplimenteryFlag(kamalSociety.getIsSupplimenteryFlag());
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
