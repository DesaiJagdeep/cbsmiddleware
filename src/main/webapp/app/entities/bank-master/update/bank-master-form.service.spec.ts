import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bank-master.test-samples';

import { BankMasterFormService } from './bank-master-form.service';

describe('BankMaster Form Service', () => {
  let service: BankMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BankMasterFormService);
  });

  describe('Service methods', () => {
    describe('createBankMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBankMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bankCode: expect.any(Object),
            bankName: expect.any(Object),
          })
        );
      });

      it('passing IBankMaster should create a new form with FormGroup', () => {
        const formGroup = service.createBankMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bankCode: expect.any(Object),
            bankName: expect.any(Object),
          })
        );
      });
    });

    describe('getBankMaster', () => {
      it('should return NewBankMaster for default BankMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBankMasterFormGroup(sampleWithNewData);

        const bankMaster = service.getBankMaster(formGroup) as any;

        expect(bankMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewBankMaster for empty BankMaster initial value', () => {
        const formGroup = service.createBankMasterFormGroup();

        const bankMaster = service.getBankMaster(formGroup) as any;

        expect(bankMaster).toMatchObject({});
      });

      it('should return IBankMaster', () => {
        const formGroup = service.createBankMasterFormGroup(sampleWithRequiredData);

        const bankMaster = service.getBankMaster(formGroup) as any;

        expect(bankMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBankMaster should not enable id FormControl', () => {
        const formGroup = service.createBankMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBankMaster should disable id FormControl', () => {
        const formGroup = service.createBankMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
