import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../crop-hangam.test-samples';

import { CropHangamFormService } from './crop-hangam-form.service';

describe('CropHangam Form Service', () => {
  let service: CropHangamFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CropHangamFormService);
  });

  describe('Service methods', () => {
    describe('createCropHangamFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCropHangamFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hangam: expect.any(Object),
            hangamMr: expect.any(Object),
          })
        );
      });

      it('passing ICropHangam should create a new form with FormGroup', () => {
        const formGroup = service.createCropHangamFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hangam: expect.any(Object),
            hangamMr: expect.any(Object),
          })
        );
      });
    });

    describe('getCropHangam', () => {
      it('should return NewCropHangam for default CropHangam initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCropHangamFormGroup(sampleWithNewData);

        const cropHangam = service.getCropHangam(formGroup) as any;

        expect(cropHangam).toMatchObject(sampleWithNewData);
      });

      it('should return NewCropHangam for empty CropHangam initial value', () => {
        const formGroup = service.createCropHangamFormGroup();

        const cropHangam = service.getCropHangam(formGroup) as any;

        expect(cropHangam).toMatchObject({});
      });

      it('should return ICropHangam', () => {
        const formGroup = service.createCropHangamFormGroup(sampleWithRequiredData);

        const cropHangam = service.getCropHangam(formGroup) as any;

        expect(cropHangam).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICropHangam should not enable id FormControl', () => {
        const formGroup = service.createCropHangamFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCropHangam should disable id FormControl', () => {
        const formGroup = service.createCropHangamFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
