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
                if (kamalSociety.getPacsNumber() != null) {
                    existingKamalSociety.setPacsNumber(kamalSociety.getPacsNumber());
                }
                if (kamalSociety.getZindagiDate() != null) {
                    existingKamalSociety.setZindagiDate(kamalSociety.getZindagiDate());
                }
                if (kamalSociety.getZindagiDateMr() != null) {
                    existingKamalSociety.setZindagiDateMr(kamalSociety.getZindagiDateMr());
                }
                if (kamalSociety.getVillage1() != null) {
                    existingKamalSociety.setVillage1(kamalSociety.getVillage1());
                }
                if (kamalSociety.getVillage1Mr() != null) {
                    existingKamalSociety.setVillage1Mr(kamalSociety.getVillage1Mr());
                }
                if (kamalSociety.getVillage2() != null) {
                    existingKamalSociety.setVillage2(kamalSociety.getVillage2());
                }
                if (kamalSociety.getVillage2Mr() != null) {
                    existingKamalSociety.setVillage2Mr(kamalSociety.getVillage2Mr());
                }
                if (kamalSociety.getVillage3() != null) {
                    existingKamalSociety.setVillage3(kamalSociety.getVillage3());
                }
                if (kamalSociety.getVillage3Mr() != null) {
                    existingKamalSociety.setVillage3Mr(kamalSociety.getVillage3Mr());
                }
                if (kamalSociety.getTotalLand() != null) {
                    existingKamalSociety.setTotalLand(kamalSociety.getTotalLand());
                }
                if (kamalSociety.getTotalLandMr() != null) {
                    existingKamalSociety.setTotalLandMr(kamalSociety.getTotalLandMr());
                }
                if (kamalSociety.getTotalMem() != null) {
                    existingKamalSociety.setTotalMem(kamalSociety.getTotalMem());
                }
                if (kamalSociety.getTotalMemMr() != null) {
                    existingKamalSociety.setTotalMemMr(kamalSociety.getTotalMemMr());
                }
                if (kamalSociety.getTotalNonMem() != null) {
                    existingKamalSociety.setTotalNonMem(kamalSociety.getTotalNonMem());
                }
                if (kamalSociety.getTotalNonMemMr() != null) {
                    existingKamalSociety.setTotalNonMemMr(kamalSociety.getTotalNonMemMr());
                }
                if (kamalSociety.getTotalGMem() != null) {
                    existingKamalSociety.setTotalGMem(kamalSociety.getTotalGMem());
                }
                if (kamalSociety.getTotalGMemMr() != null) {
                    existingKamalSociety.setTotalGMemMr(kamalSociety.getTotalGMemMr());
                }
                if (kamalSociety.getMemLoan() != null) {
                    existingKamalSociety.setMemLoan(kamalSociety.getMemLoan());
                }
                if (kamalSociety.getMemLoanMr() != null) {
                    existingKamalSociety.setMemLoanMr(kamalSociety.getMemLoanMr());
                }
                if (kamalSociety.getMemDue() != null) {
                    existingKamalSociety.setMemDue(kamalSociety.getMemDue());
                }
                if (kamalSociety.getMemDueMr() != null) {
                    existingKamalSociety.setMemDueMr(kamalSociety.getMemDueMr());
                }
                if (kamalSociety.getMemDueper() != null) {
                    existingKamalSociety.setMemDueper(kamalSociety.getMemDueper());
                }
                if (kamalSociety.getMemDueperMr() != null) {
                    existingKamalSociety.setMemDueperMr(kamalSociety.getMemDueperMr());
                }
                if (kamalSociety.getMemVasulpatra() != null) {
                    existingKamalSociety.setMemVasulpatra(kamalSociety.getMemVasulpatra());
                }
                if (kamalSociety.getMemVasulpatraMr() != null) {
                    existingKamalSociety.setMemVasulpatraMr(kamalSociety.getMemVasulpatraMr());
                }
                if (kamalSociety.getMemVasul() != null) {
                    existingKamalSociety.setMemVasul(kamalSociety.getMemVasul());
                }
                if (kamalSociety.getMemVasulMr() != null) {
                    existingKamalSociety.setMemVasulMr(kamalSociety.getMemVasulMr());
                }
                if (kamalSociety.getMemVasulPer() != null) {
                    existingKamalSociety.setMemVasulPer(kamalSociety.getMemVasulPer());
                }
                if (kamalSociety.getMemVasulPerMr() != null) {
                    existingKamalSociety.setMemVasulPerMr(kamalSociety.getMemVasulPerMr());
                }
                if (kamalSociety.getBankLoan() != null) {
                    existingKamalSociety.setBankLoan(kamalSociety.getBankLoan());
                }
                if (kamalSociety.getBankLoanMr() != null) {
                    existingKamalSociety.setBankLoanMr(kamalSociety.getBankLoanMr());
                }
                if (kamalSociety.getBankDue() != null) {
                    existingKamalSociety.setBankDue(kamalSociety.getBankDue());
                }
                if (kamalSociety.getBankDueMr() != null) {
                    existingKamalSociety.setBankDueMr(kamalSociety.getBankDueMr());
                }
                if (kamalSociety.getBankDueper() != null) {
                    existingKamalSociety.setBankDueper(kamalSociety.getBankDueper());
                }
                if (kamalSociety.getBankDueperMr() != null) {
                    existingKamalSociety.setBankDueperMr(kamalSociety.getBankDueperMr());
                }
                if (kamalSociety.getBankVasulpatra() != null) {
                    existingKamalSociety.setBankVasulpatra(kamalSociety.getBankVasulpatra());
                }
                if (kamalSociety.getBankVasulpatraMr() != null) {
                    existingKamalSociety.setBankVasulpatraMr(kamalSociety.getBankVasulpatraMr());
                }
                if (kamalSociety.getBankVasul() != null) {
                    existingKamalSociety.setBankVasul(kamalSociety.getBankVasul());
                }
                if (kamalSociety.getBankVasulMr() != null) {
                    existingKamalSociety.setBankVasulMr(kamalSociety.getBankVasulMr());
                }
                if (kamalSociety.getBankVasulPer() != null) {
                    existingKamalSociety.setBankVasulPer(kamalSociety.getBankVasulPer());
                }
                if (kamalSociety.getBankVasulPerMr() != null) {
                    existingKamalSociety.setBankVasulPerMr(kamalSociety.getBankVasulPerMr());
                }
                if (kamalSociety.getShareCapital() != null) {
                    existingKamalSociety.setShareCapital(kamalSociety.getShareCapital());
                }
                if (kamalSociety.getShareCapitalMr() != null) {
                    existingKamalSociety.setShareCapitalMr(kamalSociety.getShareCapitalMr());
                }
                if (kamalSociety.getShare() != null) {
                    existingKamalSociety.setShare(kamalSociety.getShare());
                }
                if (kamalSociety.getShareMr() != null) {
                    existingKamalSociety.setShareMr(kamalSociety.getShareMr());
                }
                if (kamalSociety.getFunds() != null) {
                    existingKamalSociety.setFunds(kamalSociety.getFunds());
                }
                if (kamalSociety.getFundsMr() != null) {
                    existingKamalSociety.setFundsMr(kamalSociety.getFundsMr());
                }
                if (kamalSociety.getDeposit() != null) {
                    existingKamalSociety.setDeposit(kamalSociety.getDeposit());
                }
                if (kamalSociety.getDepositMr() != null) {
                    existingKamalSociety.setDepositMr(kamalSociety.getDepositMr());
                }
                if (kamalSociety.getPayable() != null) {
                    existingKamalSociety.setPayable(kamalSociety.getPayable());
                }
                if (kamalSociety.getPayableMr() != null) {
                    existingKamalSociety.setPayableMr(kamalSociety.getPayableMr());
                }
                if (kamalSociety.getProfit() != null) {
                    existingKamalSociety.setProfit(kamalSociety.getProfit());
                }
                if (kamalSociety.getProfitMr() != null) {
                    existingKamalSociety.setProfitMr(kamalSociety.getProfitMr());
                }
                if (kamalSociety.getCashInHand() != null) {
                    existingKamalSociety.setCashInHand(kamalSociety.getCashInHand());
                }
                if (kamalSociety.getCashInHandMr() != null) {
                    existingKamalSociety.setCashInHandMr(kamalSociety.getCashInHandMr());
                }
                if (kamalSociety.getInvestment() != null) {
                    existingKamalSociety.setInvestment(kamalSociety.getInvestment());
                }
                if (kamalSociety.getInvestmentMr() != null) {
                    existingKamalSociety.setInvestmentMr(kamalSociety.getInvestmentMr());
                }
                if (kamalSociety.getDeadStock() != null) {
                    existingKamalSociety.setDeadStock(kamalSociety.getDeadStock());
                }
                if (kamalSociety.getDeadStockMr() != null) {
                    existingKamalSociety.setDeadStockMr(kamalSociety.getDeadStockMr());
                }
                if (kamalSociety.getOtherPay() != null) {
                    existingKamalSociety.setOtherPay(kamalSociety.getOtherPay());
                }
                if (kamalSociety.getOtherPayMr() != null) {
                    existingKamalSociety.setOtherPayMr(kamalSociety.getOtherPayMr());
                }
                if (kamalSociety.getLoss() != null) {
                    existingKamalSociety.setLoss(kamalSociety.getLoss());
                }
                if (kamalSociety.getLossMr() != null) {
                    existingKamalSociety.setLossMr(kamalSociety.getLossMr());
                }
                if (kamalSociety.getTotalBagayat() != null) {
                    existingKamalSociety.setTotalBagayat(kamalSociety.getTotalBagayat());
                }
                if (kamalSociety.getTotalBagayatMr() != null) {
                    existingKamalSociety.setTotalBagayatMr(kamalSociety.getTotalBagayatMr());
                }
                if (kamalSociety.getTotalJirayat() != null) {
                    existingKamalSociety.setTotalJirayat(kamalSociety.getTotalJirayat());
                }
                if (kamalSociety.getTotalJirayatMr() != null) {
                    existingKamalSociety.setTotalJirayatMr(kamalSociety.getTotalJirayatMr());
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
