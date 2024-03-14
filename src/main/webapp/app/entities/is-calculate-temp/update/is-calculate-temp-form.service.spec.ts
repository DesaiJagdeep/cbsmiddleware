import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../is-calculate-temp.test-samples';

import { IsCalculateTempFormService } from './is-calculate-temp-form.service';

describe('IsCalculateTemp Form Service', () => {
  let service: IsCalculateTempFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IsCalculateTempFormService);
  });

  describe('Service methods', () => {
    describe('createIsCalculateTempFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIsCalculateTempFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            serialNo: expect.any(Object),
            financialYear: expect.any(Object),
            issFileParserId: expect.any(Object),
            branchCode: expect.any(Object),
            loanAccountNumberKcc: expect.any(Object),
            farmerName: expect.any(Object),
            gender: expect.any(Object),
            aadharNumber: expect.any(Object),
            mobileNo: expect.any(Object),
            farmerType: expect.any(Object),
            socialCategory: expect.any(Object),
            accountNumber: expect.any(Object),
            loanSanctionDate: expect.any(Object),
            loanSanctionAmount: expect.any(Object),
            disbursementDate: expect.any(Object),
            disburseAmount: expect.any(Object),
            maturityLoanDate: expect.any(Object),
            bankDate: expect.any(Object),
            cropName: expect.any(Object),
            recoveryAmount: expect.any(Object),
            recoveryInterest: expect.any(Object),
            recoveryDate: expect.any(Object),
            balanceAmount: expect.any(Object),
            prevDays: expect.any(Object),
            presDays: expect.any(Object),
            actualDays: expect.any(Object),
            nProd: expect.any(Object),
            productAmount: expect.any(Object),
            productBank: expect.any(Object),
            productAbh3Lakh: expect.any(Object),
            interestFirst15: expect.any(Object),
            interestFirst25: expect.any(Object),
            interestSecond15: expect.any(Object),
            interestSecond25: expect.any(Object),
            interestStateFirst3: expect.any(Object),
            interestStateSecond3: expect.any(Object),
            interestFirstAbh3: expect.any(Object),
            interestSecondAbh3: expect.any(Object),
            interestAbove3Lakh: expect.any(Object),
            panjabraoInt3: expect.any(Object),
            isRecover: expect.any(Object),
            abh3LakhAmt: expect.any(Object),
            upto50000: expect.any(Object),
          })
        );
      });

      it('passing IIsCalculateTemp should create a new form with FormGroup', () => {
        const formGroup = service.createIsCalculateTempFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            serialNo: expect.any(Object),
            financialYear: expect.any(Object),
            issFileParserId: expect.any(Object),
            branchCode: expect.any(Object),
            loanAccountNumberKcc: expect.any(Object),
            farmerName: expect.any(Object),
            gender: expect.any(Object),
            aadharNumber: expect.any(Object),
            mobileNo: expect.any(Object),
            farmerType: expect.any(Object),
            socialCategory: expect.any(Object),
            accountNumber: expect.any(Object),
            loanSanctionDate: expect.any(Object),
            loanSanctionAmount: expect.any(Object),
            disbursementDate: expect.any(Object),
            disburseAmount: expect.any(Object),
            maturityLoanDate: expect.any(Object),
            bankDate: expect.any(Object),
            cropName: expect.any(Object),
            recoveryAmount: expect.any(Object),
            recoveryInterest: expect.any(Object),
            recoveryDate: expect.any(Object),
            balanceAmount: expect.any(Object),
            prevDays: expect.any(Object),
            presDays: expect.any(Object),
            actualDays: expect.any(Object),
            nProd: expect.any(Object),
            productAmount: expect.any(Object),
            productBank: expect.any(Object),
            productAbh3Lakh: expect.any(Object),
            interestFirst15: expect.any(Object),
            interestFirst25: expect.any(Object),
            interestSecond15: expect.any(Object),
            interestSecond25: expect.any(Object),
            interestStateFirst3: expect.any(Object),
            interestStateSecond3: expect.any(Object),
            interestFirstAbh3: expect.any(Object),
            interestSecondAbh3: expect.any(Object),
            interestAbove3Lakh: expect.any(Object),
            panjabraoInt3: expect.any(Object),
            isRecover: expect.any(Object),
            abh3LakhAmt: expect.any(Object),
            upto50000: expect.any(Object),
          })
        );
      });
    });

    describe('getIsCalculateTemp', () => {
      it('should return NewIsCalculateTemp for default IsCalculateTemp initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIsCalculateTempFormGroup(sampleWithNewData);

        const isCalculateTemp = service.getIsCalculateTemp(formGroup) as any;

        expect(isCalculateTemp).toMatchObject(sampleWithNewData);
      });

      it('should return NewIsCalculateTemp for empty IsCalculateTemp initial value', () => {
        const formGroup = service.createIsCalculateTempFormGroup();

        const isCalculateTemp = service.getIsCalculateTemp(formGroup) as any;

        expect(isCalculateTemp).toMatchObject({});
      });

      it('should return IIsCalculateTemp', () => {
        const formGroup = service.createIsCalculateTempFormGroup(sampleWithRequiredData);

        const isCalculateTemp = service.getIsCalculateTemp(formGroup) as any;

        expect(isCalculateTemp).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIsCalculateTemp should not enable id FormControl', () => {
        const formGroup = service.createIsCalculateTempFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIsCalculateTemp should disable id FormControl', () => {
        const formGroup = service.createIsCalculateTempFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
