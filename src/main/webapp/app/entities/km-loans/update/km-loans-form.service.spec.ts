import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../km-loans.test-samples';

import { KmLoansFormService } from './km-loans-form.service';

describe('KmLoans Form Service', () => {
  let service: KmLoansFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KmLoansFormService);
  });

  describe('Service methods', () => {
    describe('createKmLoansFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKmLoansFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hector: expect.any(Object),
            hectorMr: expect.any(Object),
            are: expect.any(Object),
            aremr: expect.any(Object),
            noOfTree: expect.any(Object),
            noOfTreeMr: expect.any(Object),
            sanctionAmt: expect.any(Object),
            sanctionAmtMr: expect.any(Object),
            loanAmt: expect.any(Object),
            loanAmtMr: expect.any(Object),
            receivableAmt: expect.any(Object),
            receivableAmtMr: expect.any(Object),
            dueAmt: expect.any(Object),
            dueAmtMr: expect.any(Object),
            dueDate: expect.any(Object),
            dueDateMr: expect.any(Object),
            spare: expect.any(Object),
            cropMaster: expect.any(Object),
            kmDetails: expect.any(Object),
          })
        );
      });

      it('passing IKmLoans should create a new form with FormGroup', () => {
        const formGroup = service.createKmLoansFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hector: expect.any(Object),
            hectorMr: expect.any(Object),
            are: expect.any(Object),
            aremr: expect.any(Object),
            noOfTree: expect.any(Object),
            noOfTreeMr: expect.any(Object),
            sanctionAmt: expect.any(Object),
            sanctionAmtMr: expect.any(Object),
            loanAmt: expect.any(Object),
            loanAmtMr: expect.any(Object),
            receivableAmt: expect.any(Object),
            receivableAmtMr: expect.any(Object),
            dueAmt: expect.any(Object),
            dueAmtMr: expect.any(Object),
            dueDate: expect.any(Object),
            dueDateMr: expect.any(Object),
            spare: expect.any(Object),
            cropMaster: expect.any(Object),
            kmDetails: expect.any(Object),
          })
        );
      });
    });

    describe('getKmLoans', () => {
      it('should return NewKmLoans for default KmLoans initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKmLoansFormGroup(sampleWithNewData);

        const kmLoans = service.getKmLoans(formGroup) as any;

        expect(kmLoans).toMatchObject(sampleWithNewData);
      });

      it('should return NewKmLoans for empty KmLoans initial value', () => {
        const formGroup = service.createKmLoansFormGroup();

        const kmLoans = service.getKmLoans(formGroup) as any;

        expect(kmLoans).toMatchObject({});
      });

      it('should return IKmLoans', () => {
        const formGroup = service.createKmLoansFormGroup(sampleWithRequiredData);

        const kmLoans = service.getKmLoans(formGroup) as any;

        expect(kmLoans).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKmLoans should not enable id FormControl', () => {
        const formGroup = service.createKmLoansFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKmLoans should disable id FormControl', () => {
        const formGroup = service.createKmLoansFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
