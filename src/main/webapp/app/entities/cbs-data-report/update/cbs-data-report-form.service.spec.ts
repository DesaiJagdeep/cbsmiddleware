import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cbs-data-report.test-samples';

import { CbsDataReportFormService } from './cbs-data-report-form.service';

describe('CbsDataReport Form Service', () => {
  let service: CbsDataReportFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbsDataReportFormService);
  });

  describe('Service methods', () => {
    describe('createCbsDataReportFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbsDataReportFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            farmerName: expect.any(Object),
          })
        );
      });

      it('passing ICbsDataReport should create a new form with FormGroup', () => {
        const formGroup = service.createCbsDataReportFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            farmerName: expect.any(Object),
          })
        );
      });
    });

    describe('getCbsDataReport', () => {
      it('should return NewCbsDataReport for default CbsDataReport initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCbsDataReportFormGroup(sampleWithNewData);

        const cbsDataReport = service.getCbsDataReport(formGroup) as any;

        expect(cbsDataReport).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbsDataReport for empty CbsDataReport initial value', () => {
        const formGroup = service.createCbsDataReportFormGroup();

        const cbsDataReport = service.getCbsDataReport(formGroup) as any;

        expect(cbsDataReport).toMatchObject({});
      });

      it('should return ICbsDataReport', () => {
        const formGroup = service.createCbsDataReportFormGroup(sampleWithRequiredData);

        const cbsDataReport = service.getCbsDataReport(formGroup) as any;

        expect(cbsDataReport).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbsDataReport should not enable id FormControl', () => {
        const formGroup = service.createCbsDataReportFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbsDataReport should disable id FormControl', () => {
        const formGroup = service.createCbsDataReportFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
