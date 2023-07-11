import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../account-holder-master.test-samples';

import { AccountHolderMasterFormService } from './account-holder-master-form.service';

describe('AccountHolderMaster Form Service', () => {
  let service: AccountHolderMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountHolderMasterFormService);
  });

  describe('Service methods', () => {
    describe('createAccountHolderMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAccountHolderMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountHolderCode: expect.any(Object),
            accountHolder: expect.any(Object),
          })
        );
      });

      it('passing IAccountHolderMaster should create a new form with FormGroup', () => {
        const formGroup = service.createAccountHolderMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountHolderCode: expect.any(Object),
            accountHolder: expect.any(Object),
          })
        );
      });
    });

    describe('getAccountHolderMaster', () => {
      it('should return NewAccountHolderMaster for default AccountHolderMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAccountHolderMasterFormGroup(sampleWithNewData);

        const accountHolderMaster = service.getAccountHolderMaster(formGroup) as any;

        expect(accountHolderMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewAccountHolderMaster for empty AccountHolderMaster initial value', () => {
        const formGroup = service.createAccountHolderMasterFormGroup();

        const accountHolderMaster = service.getAccountHolderMaster(formGroup) as any;

        expect(accountHolderMaster).toMatchObject({});
      });

      it('should return IAccountHolderMaster', () => {
        const formGroup = service.createAccountHolderMasterFormGroup(sampleWithRequiredData);

        const accountHolderMaster = service.getAccountHolderMaster(formGroup) as any;

        expect(accountHolderMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAccountHolderMaster should not enable id FormControl', () => {
        const formGroup = service.createAccountHolderMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAccountHolderMaster should disable id FormControl', () => {
        const formGroup = service.createAccountHolderMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
