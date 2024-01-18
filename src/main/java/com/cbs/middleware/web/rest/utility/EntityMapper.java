package com.cbs.middleware.web.rest.utility;

import com.cbs.middleware.domain.KmCrops;
import com.cbs.middleware.domain.KmLoans;
import com.cbs.middleware.domain.KmMaster;
import com.cbs.middleware.repository.FarmerTypeMasterRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EntityMapper {

    private final FarmerTypeMasterRepository farmerTypeMasterRepository;

    public EntityMapper(FarmerTypeMasterRepository farmerTypeMasterRepository) {
        this.farmerTypeMasterRepository = farmerTypeMasterRepository;
    }

    public KmCrops kmCropDtoToEntity(KmCrops kmCrop, KmCrops newKmCrop) {
        newKmCrop.setHector(kmCrop.getHector());
        newKmCrop.setHectorMr(kmCrop.getHectorMr());
        newKmCrop.setAre(kmCrop.getAre());
        newKmCrop.setAreMr(kmCrop.getAreMr());
        newKmCrop.setNoOfTree(kmCrop.getNoOfTree());
        newKmCrop.setNoOfTreeMr(kmCrop.getNoOfTreeMr());
        newKmCrop.setDemand(kmCrop.getDemand());
        newKmCrop.setDemandMr(kmCrop.getDemandMr());
        newKmCrop.setSociety(kmCrop.getSociety());
        newKmCrop.setSocietyMr(kmCrop.getSocietyMr());
        newKmCrop.setBankAmt(kmCrop.getBankAmt());
        newKmCrop.setBankAmtMr(kmCrop.getBankAmtMr());
        newKmCrop.setVibhagiAdhikari(kmCrop.getVibhagiAdhikari());
        newKmCrop.setVibhagiAdhikariMr(kmCrop.getVibhagiAdhikariMr());
        newKmCrop.setBranch(kmCrop.getBranch());
        newKmCrop.setBranchMr(kmCrop.getBranchMr());
        newKmCrop.setInspAmt(kmCrop.getInspAmt());
        newKmCrop.setInspAmtMr(kmCrop.getInspAmtMr());
        newKmCrop.setCropMaster(kmCrop.getCropMaster());

        return newKmCrop;
    }

    public KmLoans kmLoanDtoToEntity(KmLoans kmLoan, KmLoans newKmLoan) {
        newKmLoan.setHector(kmLoan.getHector());
        newKmLoan.setHectorMr(kmLoan.getHectorMr());
        newKmLoan.setAre(kmLoan.getAre());
        newKmLoan.setAremr(kmLoan.getAremr());
        newKmLoan.setNoOfTree(kmLoan.getNoOfTree());
        newKmLoan.setNoOfTreeMr(kmLoan.getNoOfTreeMr());
        newKmLoan.setSanctionAmt(kmLoan.getSanctionAmt());
        newKmLoan.setSanctionAmtMr(kmLoan.getSanctionAmtMr());
        newKmLoan.setLoanAmt(kmLoan.getLoanAmt());
        newKmLoan.setLoanAmtMr(kmLoan.getLoanAmtMr());
        newKmLoan.setReceivableAmt(kmLoan.getReceivableAmt());
        newKmLoan.setReceivableAmtMr(kmLoan.getReceivableAmtMr());
        newKmLoan.setDueAmt(kmLoan.getDueAmt());
        newKmLoan.setDueAmtMr(kmLoan.getDueAmtMr());
        newKmLoan.setDueDate(kmLoan.getDueDate());
        newKmLoan.setDueAmtMr(kmLoan.getDueAmtMr());
        newKmLoan.setCropMaster(kmLoan.getCropMaster());

        return newKmLoan;
    }

    public KmMaster kmMasterDtoToEntity(KmMaster kmMaster, KmMaster kmMasterToSave) {
kmMasterToSave.setBranchCode(kmMaster.getBranchCode());
kmMasterToSave.setBranchCodeMr(kmMaster.getBranchCodeMr());
kmMasterToSave.setFarmerName(kmMaster.getFarmerName());
kmMasterToSave.setFarmerNameMr(kmMaster.getFarmerNameMr());
kmMasterToSave.setFarmerAddress(kmMaster.getFarmerAddress());
kmMasterToSave.setFarmerAddressMr(kmMaster.getFarmerAddressMr());
kmMasterToSave.setGender(kmMasterToSave.getGender());
kmMasterToSave.setGenderMr(kmMaster.getGenderMr());
kmMasterToSave.setCaste(kmMaster.getCaste());
kmMasterToSave.setCasteMr(kmMaster.getCasteMr());
kmMasterToSave.setPacsNumber(kmMaster.getPacsNumber());
kmMasterToSave.setAreaHector(kmMaster.getAreaHector());
kmMasterToSave.setAreaHectorMr(kmMaster.getAreaHectorMr());
kmMasterToSave.setAadharNo(kmMaster.getAadharNo());
kmMasterToSave.setAadharNoMr(kmMaster.getAadharNoMr());
kmMasterToSave.setPanNo(kmMasterToSave.getPanNo());
kmMasterToSave.setPanNoMr(kmMaster.getPanNoMr());
kmMasterToSave.setMobileNo(kmMaster.getMobileNo());
kmMasterToSave.setMobileNoMr(kmMaster.getMobileNoMr());
kmMasterToSave.setKccNo(kmMaster.getKccNo());
kmMasterToSave.setKccNoMr(kmMaster.getKccNoMr());
//kmMasterToSave.setSavingAcNo(km);
//kmMasterToSave.setSavingAcNoMr();
//kmMasterToSave.setPacsMemberCode();
//kmMasterToSave.setPacsMemberCodeMr();
kmMasterToSave.setFarmerTypeMaster(farmerTypeMasterRepository.findById(kmMaster.getFarmerTypeMaster().getId()).get());
        return kmMaster;
    }
}
