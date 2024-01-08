import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../km-details.test-samples';

import { KmDetailsFormService } from './km-details-form.service';

describe('KmDetails Form Service', () => {
  let service: KmDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KmDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createKmDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKmDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            shares: expect.any(Object),
            sharesMr: expect.any(Object),
            sugarShares: expect.any(Object),
            sugarSharesMr: expect.any(Object),
            deposite: expect.any(Object),
            depositeMr: expect.any(Object),
            dueLoan: expect.any(Object),
            dueLoanMr: expect.any(Object),
            dueAmount: expect.any(Object),
            dueAmountMr: expect.any(Object),
            dueDateMr: expect.any(Object),
            dueDate: expect.any(Object),
            kmDate: expect.any(Object),
            kmDateMr: expect.any(Object),
            kmFromDate: expect.any(Object),
            kmFromDateMr: expect.any(Object),
            kmToDate: expect.any(Object),
            kmToDateMr: expect.any(Object),
            bagayatHector: expect.any(Object),
            bagayatHectorMr: expect.any(Object),
            bagayatAre: expect.any(Object),
            bagayatAreMr: expect.any(Object),
            jirayatHector: expect.any(Object),
            jirayatHectorMr: expect.any(Object),
            jirayatAre: expect.any(Object),
            jirayatAreMr: expect.any(Object),
            zindagiAmt: expect.any(Object),
            zindagiNo: expect.any(Object),
            surveyNo: expect.any(Object),
            landValue: expect.any(Object),
            landValueMr: expect.any(Object),
            eAgreementAmt: expect.any(Object),
            eAgreementAmtMr: expect.any(Object),
            eAgreementDate: expect.any(Object),
            eAgreementDateMr: expect.any(Object),
            bojaAmount: expect.any(Object),
            bojaAmountMr: expect.any(Object),
            bojaDate: expect.any(Object),
            bojaDateMr: expect.any(Object),
            kmMaster: expect.any(Object),
          }),
        );
      });

      it('passing IKmDetails should create a new form with FormGroup', () => {
        const formGroup = service.createKmDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            shares: expect.any(Object),
            sharesMr: expect.any(Object),
            sugarShares: expect.any(Object),
            sugarSharesMr: expect.any(Object),
            deposite: expect.any(Object),
            depositeMr: expect.any(Object),
            dueLoan: expect.any(Object),
            dueLoanMr: expect.any(Object),
            dueAmount: expect.any(Object),
            dueAmountMr: expect.any(Object),
            dueDateMr: expect.any(Object),
            dueDate: expect.any(Object),
            kmDate: expect.any(Object),
            kmDateMr: expect.any(Object),
            kmFromDate: expect.any(Object),
            kmFromDateMr: expect.any(Object),
            kmToDate: expect.any(Object),
            kmToDateMr: expect.any(Object),
            bagayatHector: expect.any(Object),
            bagayatHectorMr: expect.any(Object),
            bagayatAre: expect.any(Object),
            bagayatAreMr: expect.any(Object),
            jirayatHector: expect.any(Object),
            jirayatHectorMr: expect.any(Object),
            jirayatAre: expect.any(Object),
            jirayatAreMr: expect.any(Object),
            zindagiAmt: expect.any(Object),
            zindagiNo: expect.any(Object),
            surveyNo: expect.any(Object),
            landValue: expect.any(Object),
            landValueMr: expect.any(Object),
            eAgreementAmt: expect.any(Object),
            eAgreementAmtMr: expect.any(Object),
            eAgreementDate: expect.any(Object),
            eAgreementDateMr: expect.any(Object),
            bojaAmount: expect.any(Object),
            bojaAmountMr: expect.any(Object),
            bojaDate: expect.any(Object),
            bojaDateMr: expect.any(Object),
            kmMaster: expect.any(Object),
          }),
        );
      });
    });

    describe('getKmDetails', () => {
      it('should return NewKmDetails for default KmDetails initial value', () => {
        const formGroup = service.createKmDetailsFormGroup(sampleWithNewData);

        const kmDetails = service.getKmDetails(formGroup) as any;

        expect(kmDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewKmDetails for empty KmDetails initial value', () => {
        const formGroup = service.createKmDetailsFormGroup();

        const kmDetails = service.getKmDetails(formGroup) as any;

        expect(kmDetails).toMatchObject({});
      });

      it('should return IKmDetails', () => {
        const formGroup = service.createKmDetailsFormGroup(sampleWithRequiredData);

        const kmDetails = service.getKmDetails(formGroup) as any;

        expect(kmDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKmDetails should not enable id FormControl', () => {
        const formGroup = service.createKmDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKmDetails should disable id FormControl', () => {
        const formGroup = service.createKmDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
