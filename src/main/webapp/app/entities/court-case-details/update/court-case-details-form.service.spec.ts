import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../court-case-details.test-samples';

import { CourtCaseDetailsFormService } from './court-case-details-form.service';

describe('CourtCaseDetails Form Service', () => {
  let service: CourtCaseDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourtCaseDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createCourtCaseDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCourtCaseDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            kramank: expect.any(Object),
            dinank: expect.any(Object),
            caseDinank: expect.any(Object),
            sabhasad: expect.any(Object),
            sabhasadAccNo: expect.any(Object),
            karjPrakarType: expect.any(Object),
            karjPrakar: expect.any(Object),
            certificateMilale: expect.any(Object),
            certificateDinnank: expect.any(Object),
            certificateRakkam: expect.any(Object),
            yenebaki: expect.any(Object),
            vyaj: expect.any(Object),
            etar: expect.any(Object),
            dimmandMilale: expect.any(Object),
            dimmandDinnank: expect.any(Object),
            japtiAadhesh: expect.any(Object),
            japtiAadheshDinnank: expect.any(Object),
            sthavr: expect.any(Object),
            jangam: expect.any(Object),
            vikriAadhesh: expect.any(Object),
            vikriAddheshDinnank: expect.any(Object),
            etarTapshil: expect.any(Object),
          })
        );
      });

      it('passing ICourtCaseDetails should create a new form with FormGroup', () => {
        const formGroup = service.createCourtCaseDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            kramank: expect.any(Object),
            dinank: expect.any(Object),
            caseDinank: expect.any(Object),
            sabhasad: expect.any(Object),
            sabhasadAccNo: expect.any(Object),
            karjPrakarType: expect.any(Object),
            karjPrakar: expect.any(Object),
            certificateMilale: expect.any(Object),
            certificateDinnank: expect.any(Object),
            certificateRakkam: expect.any(Object),
            yenebaki: expect.any(Object),
            vyaj: expect.any(Object),
            etar: expect.any(Object),
            dimmandMilale: expect.any(Object),
            dimmandDinnank: expect.any(Object),
            japtiAadhesh: expect.any(Object),
            japtiAadheshDinnank: expect.any(Object),
            sthavr: expect.any(Object),
            jangam: expect.any(Object),
            vikriAadhesh: expect.any(Object),
            vikriAddheshDinnank: expect.any(Object),
            etarTapshil: expect.any(Object),
          })
        );
      });
    });

    describe('getCourtCaseDetails', () => {
      it('should return NewCourtCaseDetails for default CourtCaseDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCourtCaseDetailsFormGroup(sampleWithNewData);

        const courtCaseDetails = service.getCourtCaseDetails(formGroup) as any;

        expect(courtCaseDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewCourtCaseDetails for empty CourtCaseDetails initial value', () => {
        const formGroup = service.createCourtCaseDetailsFormGroup();

        const courtCaseDetails = service.getCourtCaseDetails(formGroup) as any;

        expect(courtCaseDetails).toMatchObject({});
      });

      it('should return ICourtCaseDetails', () => {
        const formGroup = service.createCourtCaseDetailsFormGroup(sampleWithRequiredData);

        const courtCaseDetails = service.getCourtCaseDetails(formGroup) as any;

        expect(courtCaseDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICourtCaseDetails should not enable id FormControl', () => {
        const formGroup = service.createCourtCaseDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCourtCaseDetails should disable id FormControl', () => {
        const formGroup = service.createCourtCaseDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
