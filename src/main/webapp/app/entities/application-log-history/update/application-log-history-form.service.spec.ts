import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../application-log-history.test-samples';

import { ApplicationLogHistoryFormService } from './application-log-history-form.service';

describe('ApplicationLogHistory Form Service', () => {
  let service: ApplicationLogHistoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApplicationLogHistoryFormService);
  });

  describe('Service methods', () => {
    describe('createApplicationLogHistoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApplicationLogHistoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            errorType: expect.any(Object),
            errorCode: expect.any(Object),
            errorMessage: expect.any(Object),
            rowNumber: expect.any(Object),
            columnNumber: expect.any(Object),
            sevierity: expect.any(Object),
            expectedSolution: expect.any(Object),
            status: expect.any(Object),
            issFileParser: expect.any(Object),
          })
        );
      });

      it('passing IApplicationLogHistory should create a new form with FormGroup', () => {
        const formGroup = service.createApplicationLogHistoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            errorType: expect.any(Object),
            errorCode: expect.any(Object),
            errorMessage: expect.any(Object),
            rowNumber: expect.any(Object),
            columnNumber: expect.any(Object),
            sevierity: expect.any(Object),
            expectedSolution: expect.any(Object),
            status: expect.any(Object),
            issFileParser: expect.any(Object),
          })
        );
      });
    });

    describe('getApplicationLogHistory', () => {
      it('should return NewApplicationLogHistory for default ApplicationLogHistory initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createApplicationLogHistoryFormGroup(sampleWithNewData);

        const applicationLogHistory = service.getApplicationLogHistory(formGroup) as any;

        expect(applicationLogHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewApplicationLogHistory for empty ApplicationLogHistory initial value', () => {
        const formGroup = service.createApplicationLogHistoryFormGroup();

        const applicationLogHistory = service.getApplicationLogHistory(formGroup) as any;

        expect(applicationLogHistory).toMatchObject({});
      });

      it('should return IApplicationLogHistory', () => {
        const formGroup = service.createApplicationLogHistoryFormGroup(sampleWithRequiredData);

        const applicationLogHistory = service.getApplicationLogHistory(formGroup) as any;

        expect(applicationLogHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApplicationLogHistory should not enable id FormControl', () => {
        const formGroup = service.createApplicationLogHistoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApplicationLogHistory should disable id FormControl', () => {
        const formGroup = service.createApplicationLogHistoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
