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
            pacsNumber: expect.any(Object),
            zindagiDate: expect.any(Object),
            zindagiDateMr: expect.any(Object),
            village1: expect.any(Object),
            village1Mr: expect.any(Object),
            village2: expect.any(Object),
            village2Mr: expect.any(Object),
            village3: expect.any(Object),
            village3Mr: expect.any(Object),
            totalLand: expect.any(Object),
            totalLandMr: expect.any(Object),
            totalMem: expect.any(Object),
            totalMemMr: expect.any(Object),
            totalNonMem: expect.any(Object),
            totalNonMemMr: expect.any(Object),
            totalGMem: expect.any(Object),
            totalGMemMr: expect.any(Object),
            memLoan: expect.any(Object),
            memLoanMr: expect.any(Object),
            memDue: expect.any(Object),
            memDueMr: expect.any(Object),
            memDueper: expect.any(Object),
            memDueperMr: expect.any(Object),
            memVasulpatra: expect.any(Object),
            memVasulpatraMr: expect.any(Object),
            memVasul: expect.any(Object),
            memVasulMr: expect.any(Object),
            memVasulPer: expect.any(Object),
            memVasulPerMr: expect.any(Object),
            bankLoan: expect.any(Object),
            bankLoanMr: expect.any(Object),
            bankDue: expect.any(Object),
            bankDueMr: expect.any(Object),
            bankDueper: expect.any(Object),
            bankDueperMr: expect.any(Object),
            bankVasulpatra: expect.any(Object),
            bankVasulpatraMr: expect.any(Object),
            bankVasul: expect.any(Object),
            bankVasulMr: expect.any(Object),
            bankVasulPer: expect.any(Object),
            bankVasulPerMr: expect.any(Object),
            shareCapital: expect.any(Object),
            shareCapitalMr: expect.any(Object),
            share: expect.any(Object),
            shareMr: expect.any(Object),
            funds: expect.any(Object),
            fundsMr: expect.any(Object),
            deposit: expect.any(Object),
            depositMr: expect.any(Object),
            payable: expect.any(Object),
            payableMr: expect.any(Object),
            profit: expect.any(Object),
            profitMr: expect.any(Object),
            cashInHand: expect.any(Object),
            cashInHandMr: expect.any(Object),
            investment: expect.any(Object),
            investmentMr: expect.any(Object),
            deadStock: expect.any(Object),
            deadStockMr: expect.any(Object),
            otherPay: expect.any(Object),
            otherPayMr: expect.any(Object),
            loss: expect.any(Object),
            lossMr: expect.any(Object),
          })
        );
      });

      it('passing IKamalSociety should create a new form with FormGroup', () => {
        const formGroup = service.createKamalSocietyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pacsNumber: expect.any(Object),
            zindagiDate: expect.any(Object),
            zindagiDateMr: expect.any(Object),
            village1: expect.any(Object),
            village1Mr: expect.any(Object),
            village2: expect.any(Object),
            village2Mr: expect.any(Object),
            village3: expect.any(Object),
            village3Mr: expect.any(Object),
            totalLand: expect.any(Object),
            totalLandMr: expect.any(Object),
            totalMem: expect.any(Object),
            totalMemMr: expect.any(Object),
            totalNonMem: expect.any(Object),
            totalNonMemMr: expect.any(Object),
            totalGMem: expect.any(Object),
            totalGMemMr: expect.any(Object),
            memLoan: expect.any(Object),
            memLoanMr: expect.any(Object),
            memDue: expect.any(Object),
            memDueMr: expect.any(Object),
            memDueper: expect.any(Object),
            memDueperMr: expect.any(Object),
            memVasulpatra: expect.any(Object),
            memVasulpatraMr: expect.any(Object),
            memVasul: expect.any(Object),
            memVasulMr: expect.any(Object),
            memVasulPer: expect.any(Object),
            memVasulPerMr: expect.any(Object),
            bankLoan: expect.any(Object),
            bankLoanMr: expect.any(Object),
            bankDue: expect.any(Object),
            bankDueMr: expect.any(Object),
            bankDueper: expect.any(Object),
            bankDueperMr: expect.any(Object),
            bankVasulpatra: expect.any(Object),
            bankVasulpatraMr: expect.any(Object),
            bankVasul: expect.any(Object),
            bankVasulMr: expect.any(Object),
            bankVasulPer: expect.any(Object),
            bankVasulPerMr: expect.any(Object),
            shareCapital: expect.any(Object),
            shareCapitalMr: expect.any(Object),
            share: expect.any(Object),
            shareMr: expect.any(Object),
            funds: expect.any(Object),
            fundsMr: expect.any(Object),
            deposit: expect.any(Object),
            depositMr: expect.any(Object),
            payable: expect.any(Object),
            payableMr: expect.any(Object),
            profit: expect.any(Object),
            profitMr: expect.any(Object),
            cashInHand: expect.any(Object),
            cashInHandMr: expect.any(Object),
            investment: expect.any(Object),
            investmentMr: expect.any(Object),
            deadStock: expect.any(Object),
            deadStockMr: expect.any(Object),
            otherPay: expect.any(Object),
            otherPayMr: expect.any(Object),
            loss: expect.any(Object),
            lossMr: expect.any(Object),
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
