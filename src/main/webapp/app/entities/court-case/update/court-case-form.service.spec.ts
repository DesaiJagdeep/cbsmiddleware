import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../court-case.test-samples';

import { CourtCaseFormService } from './court-case-form.service';

describe('CourtCase Form Service', () => {
  let service: CourtCaseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourtCaseFormService);
  });

  describe('Service methods', () => {
    describe('createCourtCaseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCourtCaseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            srNo: expect.any(Object),
            accountNo: expect.any(Object),
            nameOfDefaulter: expect.any(Object),
            address: expect.any(Object),
            loanType: expect.any(Object),
            loanAmount: expect.any(Object),
            loanDate: expect.any(Object),
            termOfLoan: expect.any(Object),
            interestRate: expect.any(Object),
            installmentAmount: expect.any(Object),
            totalCredit: expect.any(Object),
            balance: expect.any(Object),
            interestPaid: expect.any(Object),
            penalInterestPaid: expect.any(Object),
            dueAmount: expect.any(Object),
            dueDate: expect.any(Object),
            dueInterest: expect.any(Object),
            duePenalInterest: expect.any(Object),
            dueMoreInterest: expect.any(Object),
            interestRecivable: expect.any(Object),
            gaurentorOne: expect.any(Object),
            gaurentorOneAddress: expect.any(Object),
            gaurentorTwo: expect.any(Object),
            gaurentorTwoAddress: expect.any(Object),
            firstNoticeDate: expect.any(Object),
            secondNoticeDate: expect.any(Object),
          })
        );
      });

      it('passing ICourtCase should create a new form with FormGroup', () => {
        const formGroup = service.createCourtCaseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            srNo: expect.any(Object),
            accountNo: expect.any(Object),
            nameOfDefaulter: expect.any(Object),
            address: expect.any(Object),
            loanType: expect.any(Object),
            loanAmount: expect.any(Object),
            loanDate: expect.any(Object),
            termOfLoan: expect.any(Object),
            interestRate: expect.any(Object),
            installmentAmount: expect.any(Object),
            totalCredit: expect.any(Object),
            balance: expect.any(Object),
            interestPaid: expect.any(Object),
            penalInterestPaid: expect.any(Object),
            dueAmount: expect.any(Object),
            dueDate: expect.any(Object),
            dueInterest: expect.any(Object),
            duePenalInterest: expect.any(Object),
            dueMoreInterest: expect.any(Object),
            interestRecivable: expect.any(Object),
            gaurentorOne: expect.any(Object),
            gaurentorOneAddress: expect.any(Object),
            gaurentorTwo: expect.any(Object),
            gaurentorTwoAddress: expect.any(Object),
            firstNoticeDate: expect.any(Object),
            secondNoticeDate: expect.any(Object),
          })
        );
      });
    });

    describe('getCourtCase', () => {
      it('should return NewCourtCase for default CourtCase initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCourtCaseFormGroup(sampleWithNewData);

        const courtCase = service.getCourtCase(formGroup) as any;

        expect(courtCase).toMatchObject(sampleWithNewData);
      });

      it('should return NewCourtCase for empty CourtCase initial value', () => {
        const formGroup = service.createCourtCaseFormGroup();

        const courtCase = service.getCourtCase(formGroup) as any;

        expect(courtCase).toMatchObject({});
      });

      it('should return ICourtCase', () => {
        const formGroup = service.createCourtCaseFormGroup(sampleWithRequiredData);

        const courtCase = service.getCourtCase(formGroup) as any;

        expect(courtCase).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICourtCase should not enable id FormControl', () => {
        const formGroup = service.createCourtCaseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCourtCase should disable id FormControl', () => {
        const formGroup = service.createCourtCaseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
