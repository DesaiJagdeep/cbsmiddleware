import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../kamal-society.test-samples';

import { KamalSocietyFormService } from './kamal-society-form.service';

describe('KamalSociety Form Service', () => {
  let service: KamalSocietyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KamalSocietyFormService);
  });

  describe('Service methods', () => {
    describe('createKamalSocietyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKamalSocietyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            financialYear: expect.any(Object),
            kmDate: expect.any(Object),
            kmDateMr: expect.any(Object),
            kmFromDate: expect.any(Object),
            kmFromDateMr: expect.any(Object),
            kmToDate: expect.any(Object),
            kmToDateMr: expect.any(Object),
            pacsNumber: expect.any(Object),
            pacsName: expect.any(Object),
            branchId: expect.any(Object),
            branchName: expect.any(Object),
            zindagiPatrakDate: expect.any(Object),
            zindagiPatrakDateMr: expect.any(Object),
            bankTapasaniDate: expect.any(Object),
            bankTapasaniDateMr: expect.any(Object),
            govTapasaniDate: expect.any(Object),
            govTapasaniDateMr: expect.any(Object),
            sansthaTapasaniDate: expect.any(Object),
            sansthaTapasaniDateMr: expect.any(Object),
            totalLand: expect.any(Object),
            bagayat: expect.any(Object),
            jirayat: expect.any(Object),
            totalFarmer: expect.any(Object),
            memberFarmer: expect.any(Object),
            nonMemberFarmer: expect.any(Object),
            talebandDate: expect.any(Object),
            memLoan: expect.any(Object),
            memDue: expect.any(Object),
            memVasuli: expect.any(Object),
            memVasuliPer: expect.any(Object),
            bankLoan: expect.any(Object),
            bankDue: expect.any(Object),
            bankVasuli: expect.any(Object),
            bankVasuliPer: expect.any(Object),
            balanceSheetDate: expect.any(Object),
            balanceSheetDateMr: expect.any(Object),
            liabilityAdhikrutShareCapital: expect.any(Object),
            liabilityVasulShareCapital: expect.any(Object),
            liabilityFund: expect.any(Object),
            liabilitySpareFund: expect.any(Object),
            liabilityDeposite: expect.any(Object),
            liabilityBalanceSheetBankLoan: expect.any(Object),
            liabilityOtherPayable: expect.any(Object),
            liabilityProfit: expect.any(Object),
            assetCash: expect.any(Object),
            assetInvestment: expect.any(Object),
            assetImaratFund: expect.any(Object),
            assetMemberLoan: expect.any(Object),
            assetDeadStock: expect.any(Object),
            assetOtherReceivable: expect.any(Object),
            assetLoss: expect.any(Object),
            totalLiability: expect.any(Object),
            totalAsset: expect.any(Object),
            villageCode: expect.any(Object),
            pacsVerifiedFlag: expect.any(Object),
            branchVerifiedFlag: expect.any(Object),
            headOfficeVerifiedFlag: expect.any(Object),
            divisionalOfficeVerifiedFlag: expect.any(Object),
            isSupplimenteryFlag: expect.any(Object),
            sansthaTapasaniVarg: expect.any(Object),
            branchVerifiedBy: expect.any(Object),
            branchVerifiedDate: expect.any(Object),
            headOfficeVerifiedBy: expect.any(Object),
            headOfficeVerifiedDate: expect.any(Object),
            divisionalOfficeVerifiedBy: expect.any(Object),
            divisionalOfficeVerifiedDate: expect.any(Object),
            doshPurtataDate: expect.any(Object),
            gambhirDosh: expect.any(Object),
          })
        );
      });

      it('passing IKamalSociety should create a new form with FormGroup', () => {
        const formGroup = service.createKamalSocietyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            financialYear: expect.any(Object),
            kmDate: expect.any(Object),
            kmDateMr: expect.any(Object),
            kmFromDate: expect.any(Object),
            kmFromDateMr: expect.any(Object),
            kmToDate: expect.any(Object),
            kmToDateMr: expect.any(Object),
            pacsNumber: expect.any(Object),
            pacsName: expect.any(Object),
            branchId: expect.any(Object),
            branchName: expect.any(Object),
            zindagiPatrakDate: expect.any(Object),
            zindagiPatrakDateMr: expect.any(Object),
            bankTapasaniDate: expect.any(Object),
            bankTapasaniDateMr: expect.any(Object),
            govTapasaniDate: expect.any(Object),
            govTapasaniDateMr: expect.any(Object),
            sansthaTapasaniDate: expect.any(Object),
            sansthaTapasaniDateMr: expect.any(Object),
            totalLand: expect.any(Object),
            bagayat: expect.any(Object),
            jirayat: expect.any(Object),
            totalFarmer: expect.any(Object),
            memberFarmer: expect.any(Object),
            nonMemberFarmer: expect.any(Object),
            talebandDate: expect.any(Object),
            memLoan: expect.any(Object),
            memDue: expect.any(Object),
            memVasuli: expect.any(Object),
            memVasuliPer: expect.any(Object),
            bankLoan: expect.any(Object),
            bankDue: expect.any(Object),
            bankVasuli: expect.any(Object),
            bankVasuliPer: expect.any(Object),
            balanceSheetDate: expect.any(Object),
            balanceSheetDateMr: expect.any(Object),
            liabilityAdhikrutShareCapital: expect.any(Object),
            liabilityVasulShareCapital: expect.any(Object),
            liabilityFund: expect.any(Object),
            liabilitySpareFund: expect.any(Object),
            liabilityDeposite: expect.any(Object),
            liabilityBalanceSheetBankLoan: expect.any(Object),
            liabilityOtherPayable: expect.any(Object),
            liabilityProfit: expect.any(Object),
            assetCash: expect.any(Object),
            assetInvestment: expect.any(Object),
            assetImaratFund: expect.any(Object),
            assetMemberLoan: expect.any(Object),
            assetDeadStock: expect.any(Object),
            assetOtherReceivable: expect.any(Object),
            assetLoss: expect.any(Object),
            totalLiability: expect.any(Object),
            totalAsset: expect.any(Object),
            villageCode: expect.any(Object),
            pacsVerifiedFlag: expect.any(Object),
            branchVerifiedFlag: expect.any(Object),
            headOfficeVerifiedFlag: expect.any(Object),
            divisionalOfficeVerifiedFlag: expect.any(Object),
            isSupplimenteryFlag: expect.any(Object),
            sansthaTapasaniVarg: expect.any(Object),
            branchVerifiedBy: expect.any(Object),
            branchVerifiedDate: expect.any(Object),
            headOfficeVerifiedBy: expect.any(Object),
            headOfficeVerifiedDate: expect.any(Object),
            divisionalOfficeVerifiedBy: expect.any(Object),
            divisionalOfficeVerifiedDate: expect.any(Object),
            doshPurtataDate: expect.any(Object),
            gambhirDosh: expect.any(Object),
          })
        );
      });
    });

    describe('getKamalSociety', () => {
      it('should return NewKamalSociety for default KamalSociety initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKamalSocietyFormGroup(sampleWithNewData);

        const kamalSociety = service.getKamalSociety(formGroup) as any;

        expect(kamalSociety).toMatchObject(sampleWithNewData);
      });

      it('should return NewKamalSociety for empty KamalSociety initial value', () => {
        const formGroup = service.createKamalSocietyFormGroup();

        const kamalSociety = service.getKamalSociety(formGroup) as any;

        expect(kamalSociety).toMatchObject({});
      });

      it('should return IKamalSociety', () => {
        const formGroup = service.createKamalSocietyFormGroup(sampleWithRequiredData);

        const kamalSociety = service.getKamalSociety(formGroup) as any;

        expect(kamalSociety).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKamalSociety should not enable id FormControl', () => {
        const formGroup = service.createKamalSocietyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKamalSociety should disable id FormControl', () => {
        const formGroup = service.createKamalSocietyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
