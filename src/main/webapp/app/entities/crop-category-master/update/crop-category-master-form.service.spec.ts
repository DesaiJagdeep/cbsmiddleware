import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../crop-category-master.test-samples';

import { CropCategoryMasterFormService } from './crop-category-master-form.service';

describe('CropCategoryMaster Form Service', () => {
  let service: CropCategoryMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CropCategoryMasterFormService);
  });

  describe('Service methods', () => {
    describe('createCropCategoryMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCropCategoryMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            categoryCode: expect.any(Object),
            categoryName: expect.any(Object),
          })
        );
      });

      it('passing ICropCategoryMaster should create a new form with FormGroup', () => {
        const formGroup = service.createCropCategoryMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            categoryCode: expect.any(Object),
            categoryName: expect.any(Object),
          })
        );
      });
    });

    describe('getCropCategoryMaster', () => {
      it('should return NewCropCategoryMaster for default CropCategoryMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCropCategoryMasterFormGroup(sampleWithNewData);

        const cropCategoryMaster = service.getCropCategoryMaster(formGroup) as any;

        expect(cropCategoryMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewCropCategoryMaster for empty CropCategoryMaster initial value', () => {
        const formGroup = service.createCropCategoryMasterFormGroup();

        const cropCategoryMaster = service.getCropCategoryMaster(formGroup) as any;

        expect(cropCategoryMaster).toMatchObject({});
      });

      it('should return ICropCategoryMaster', () => {
        const formGroup = service.createCropCategoryMasterFormGroup(sampleWithRequiredData);

        const cropCategoryMaster = service.getCropCategoryMaster(formGroup) as any;

        expect(cropCategoryMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICropCategoryMaster should not enable id FormControl', () => {
        const formGroup = service.createCropCategoryMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCropCategoryMaster should disable id FormControl', () => {
        const formGroup = service.createCropCategoryMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
