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
