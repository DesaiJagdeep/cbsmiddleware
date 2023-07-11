import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bank-branch-master.test-samples';

import { BankBranchMasterFormService } from './bank-branch-master-form.service';

describe('BankBranchMaster Form Service', () => {
  let service: BankBranchMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BankBranchMasterFormService);
  });

  describe('Service methods', () => {
    describe('createBankBranchMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBankBranchMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            branchCode: expect.any(Object),
            branchName: expect.any(Object),
            branchAddress: expect.any(Object),
            bankCode: expect.any(Object),
          })
        );
      });

      it('passing IBankBranchMaster should create a new form with FormGroup', () => {
        const formGroup = service.createBankBranchMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            branchCode: expect.any(Object),
            branchName: expect.any(Object),
            branchAddress: expect.any(Object),
            bankCode: expect.any(Object),
          })
        );
      });
    });

    describe('getBankBranchMaster', () => {
      it('should return NewBankBranchMaster for default BankBranchMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBankBranchMasterFormGroup(sampleWithNewData);

        const bankBranchMaster = service.getBankBranchMaster(formGroup) as any;

        expect(bankBranchMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewBankBranchMaster for empty BankBranchMaster initial value', () => {
        const formGroup = service.createBankBranchMasterFormGroup();

        const bankBranchMaster = service.getBankBranchMaster(formGroup) as any;

        expect(bankBranchMaster).toMatchObject({});
      });

      it('should return IBankBranchMaster', () => {
        const formGroup = service.createBankBranchMasterFormGroup(sampleWithRequiredData);

        const bankBranchMaster = service.getBankBranchMaster(formGroup) as any;

        expect(bankBranchMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBankBranchMaster should not enable id FormControl', () => {
        const formGroup = service.createBankBranchMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBankBranchMaster should disable id FormControl', () => {
        const formGroup = service.createBankBranchMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
