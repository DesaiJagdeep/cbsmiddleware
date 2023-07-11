import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../crop-master.test-samples';

import { CropMasterFormService } from './crop-master-form.service';

describe('CropMaster Form Service', () => {
  let service: CropMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CropMasterFormService);
  });

  describe('Service methods', () => {
    describe('createCropMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCropMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cropCode: expect.any(Object),
            cropName: expect.any(Object),
            categoryCode: expect.any(Object),
          })
        );
      });

      it('passing ICropMaster should create a new form with FormGroup', () => {
        const formGroup = service.createCropMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cropCode: expect.any(Object),
            cropName: expect.any(Object),
            categoryCode: expect.any(Object),
          })
        );
      });
    });

    describe('getCropMaster', () => {
      it('should return NewCropMaster for default CropMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCropMasterFormGroup(sampleWithNewData);

        const cropMaster = service.getCropMaster(formGroup) as any;

        expect(cropMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewCropMaster for empty CropMaster initial value', () => {
        const formGroup = service.createCropMasterFormGroup();

        const cropMaster = service.getCropMaster(formGroup) as any;

        expect(cropMaster).toMatchObject({});
      });

      it('should return ICropMaster', () => {
        const formGroup = service.createCropMasterFormGroup(sampleWithRequiredData);

        const cropMaster = service.getCropMaster(formGroup) as any;

        expect(cropMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICropMaster should not enable id FormControl', () => {
        const formGroup = service.createCropMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCropMaster should disable id FormControl', () => {
        const formGroup = service.createCropMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
