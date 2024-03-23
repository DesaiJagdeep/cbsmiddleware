import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../crop-rate-master.test-samples';

import { CropRateMasterFormService } from './crop-rate-master-form.service';

describe('CropRateMaster Form Service', () => {
  let service: CropRateMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CropRateMasterFormService);
  });

  describe('Service methods', () => {
    describe('createCropRateMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCropRateMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            financialYear: expect.any(Object),
            currentAmount: expect.any(Object),
            currentAmountMr: expect.any(Object),
            percentage: expect.any(Object),
            activeFlag: expect.any(Object),
            cropMaster: expect.any(Object),
          })
        );
      });

      it('passing ICropRateMaster should create a new form with FormGroup', () => {
        const formGroup = service.createCropRateMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            financialYear: expect.any(Object),
            currentAmount: expect.any(Object),
            currentAmountMr: expect.any(Object),
            percentage: expect.any(Object),
            activeFlag: expect.any(Object),
            cropMaster: expect.any(Object),
          })
        );
      });
    });

    describe('getCropRateMaster', () => {
      it('should return NewCropRateMaster for default CropRateMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCropRateMasterFormGroup(sampleWithNewData);

        const cropRateMaster = service.getCropRateMaster(formGroup) as any;

        expect(cropRateMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewCropRateMaster for empty CropRateMaster initial value', () => {
        const formGroup = service.createCropRateMasterFormGroup();

        const cropRateMaster = service.getCropRateMaster(formGroup) as any;

        expect(cropRateMaster).toMatchObject({});
      });

      it('should return ICropRateMaster', () => {
        const formGroup = service.createCropRateMasterFormGroup(sampleWithRequiredData);

        const cropRateMaster = service.getCropRateMaster(formGroup) as any;

        expect(cropRateMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICropRateMaster should not enable id FormControl', () => {
        const formGroup = service.createCropRateMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCropRateMaster should disable id FormControl', () => {
        const formGroup = service.createCropRateMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
