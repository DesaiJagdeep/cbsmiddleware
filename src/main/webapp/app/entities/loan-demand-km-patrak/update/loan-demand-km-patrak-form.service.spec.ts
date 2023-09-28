import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-demand-km-patrak.test-samples';

import { LoanDemandKMPatrakFormService } from './loan-demand-km-patrak-form.service';

describe('LoanDemandKMPatrak Form Service', () => {
  let service: LoanDemandKMPatrakFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanDemandKMPatrakFormService);
  });

  describe('Service methods', () => {
    describe('createLoanDemandKMPatrakFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanDemandKMPatrakFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            demandCode: expect.any(Object),
            date: expect.any(Object),
            kmDate: expect.any(Object),
            shares: expect.any(Object),
            pid: expect.any(Object),
            code: expect.any(Object),
            demandArea: expect.any(Object),
            cropType: expect.any(Object),
            total: expect.any(Object),
            check: expect.any(Object),
            goods: expect.any(Object),
            sharesn: expect.any(Object),
            hn: expect.any(Object),
            area: expect.any(Object),
            hAmount: expect.any(Object),
            name: expect.any(Object),
            khateCode: expect.any(Object),
            remaining: expect.any(Object),
            arrears: expect.any(Object),
            kmAcceptance: expect.any(Object),
            paidDate: expect.any(Object),
            kmCode: expect.any(Object),
            pendingDate: expect.any(Object),
            depositeDate: expect.any(Object),
            accountNumberB: expect.any(Object),
            loanDue: expect.any(Object),
            arrearsB: expect.any(Object),
            dueDateB: expect.any(Object),
            cropB: expect.any(Object),
            kmAcceptanceB: expect.any(Object),
            kmCodeB: expect.any(Object),
            hAgreementNumberB: expect.any(Object),
            hAgreementAreaB: expect.any(Object),
            hAgreementBurdenB: expect.any(Object),
            totalPaidB: expect.any(Object),
            demandAreaB: expect.any(Object),
            checkInTheFormOfPaymentB: expect.any(Object),
            sharesB: expect.any(Object),
            vasulPatraRepaymentDateB: expect.any(Object),
          })
        );
      });

      it('passing ILoanDemandKMPatrak should create a new form with FormGroup', () => {
        const formGroup = service.createLoanDemandKMPatrakFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            demandCode: expect.any(Object),
            date: expect.any(Object),
            kmDate: expect.any(Object),
            shares: expect.any(Object),
            pid: expect.any(Object),
            code: expect.any(Object),
            demandArea: expect.any(Object),
            cropType: expect.any(Object),
            total: expect.any(Object),
            check: expect.any(Object),
            goods: expect.any(Object),
            sharesn: expect.any(Object),
            hn: expect.any(Object),
            area: expect.any(Object),
            hAmount: expect.any(Object),
            name: expect.any(Object),
            khateCode: expect.any(Object),
            remaining: expect.any(Object),
            arrears: expect.any(Object),
            kmAcceptance: expect.any(Object),
            paidDate: expect.any(Object),
            kmCode: expect.any(Object),
            pendingDate: expect.any(Object),
            depositeDate: expect.any(Object),
            accountNumberB: expect.any(Object),
            loanDue: expect.any(Object),
            arrearsB: expect.any(Object),
            dueDateB: expect.any(Object),
            cropB: expect.any(Object),
            kmAcceptanceB: expect.any(Object),
            kmCodeB: expect.any(Object),
            hAgreementNumberB: expect.any(Object),
            hAgreementAreaB: expect.any(Object),
            hAgreementBurdenB: expect.any(Object),
            totalPaidB: expect.any(Object),
            demandAreaB: expect.any(Object),
            checkInTheFormOfPaymentB: expect.any(Object),
            sharesB: expect.any(Object),
            vasulPatraRepaymentDateB: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanDemandKMPatrak', () => {
      it('should return NewLoanDemandKMPatrak for default LoanDemandKMPatrak initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanDemandKMPatrakFormGroup(sampleWithNewData);

        const loanDemandKMPatrak = service.getLoanDemandKMPatrak(formGroup) as any;

        expect(loanDemandKMPatrak).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanDemandKMPatrak for empty LoanDemandKMPatrak initial value', () => {
        const formGroup = service.createLoanDemandKMPatrakFormGroup();

        const loanDemandKMPatrak = service.getLoanDemandKMPatrak(formGroup) as any;

        expect(loanDemandKMPatrak).toMatchObject({});
      });

      it('should return ILoanDemandKMPatrak', () => {
        const formGroup = service.createLoanDemandKMPatrakFormGroup(sampleWithRequiredData);

        const loanDemandKMPatrak = service.getLoanDemandKMPatrak(formGroup) as any;

        expect(loanDemandKMPatrak).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanDemandKMPatrak should not enable id FormControl', () => {
        const formGroup = service.createLoanDemandKMPatrakFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanDemandKMPatrak should disable id FormControl', () => {
        const formGroup = service.createLoanDemandKMPatrakFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
