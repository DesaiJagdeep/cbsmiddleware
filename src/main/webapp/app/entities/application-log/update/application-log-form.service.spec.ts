import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../application-log.test-samples';

import { ApplicationLogFormService } from './application-log-form.service';

describe('ApplicationLog Form Service', () => {
  let service: ApplicationLogFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApplicationLogFormService);
  });

  describe('Service methods', () => {
    describe('createApplicationLogFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApplicationLogFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            errorType: expect.any(Object),
            errorCode: expect.any(Object),
            errorMessage: expect.any(Object),
            columnNumber: expect.any(Object),
            sevierity: expect.any(Object),
            expectedSolution: expect.any(Object),
            status: expect.any(Object),
            rowNumber: expect.any(Object),
            batchId: expect.any(Object),
            issFileParser: expect.any(Object),
          })
        );
      });

      it('passing IApplicationLog should create a new form with FormGroup', () => {
        const formGroup = service.createApplicationLogFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            errorType: expect.any(Object),
            errorCode: expect.any(Object),
            errorMessage: expect.any(Object),
            columnNumber: expect.any(Object),
            sevierity: expect.any(Object),
            expectedSolution: expect.any(Object),
            status: expect.any(Object),
            rowNumber: expect.any(Object),
            batchId: expect.any(Object),
            issFileParser: expect.any(Object),
          })
        );
      });
    });

    describe('getApplicationLog', () => {
      it('should return NewApplicationLog for default ApplicationLog initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createApplicationLogFormGroup(sampleWithNewData);

        const applicationLog = service.getApplicationLog(formGroup) as any;

        expect(applicationLog).toMatchObject(sampleWithNewData);
      });

      it('should return NewApplicationLog for empty ApplicationLog initial value', () => {
        const formGroup = service.createApplicationLogFormGroup();

        const applicationLog = service.getApplicationLog(formGroup) as any;

        expect(applicationLog).toMatchObject({});
      });

      it('should return IApplicationLog', () => {
        const formGroup = service.createApplicationLogFormGroup(sampleWithRequiredData);

        const applicationLog = service.getApplicationLog(formGroup) as any;

        expect(applicationLog).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApplicationLog should not enable id FormControl', () => {
        const formGroup = service.createApplicationLogFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApplicationLog should disable id FormControl', () => {
        const formGroup = service.createApplicationLogFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
