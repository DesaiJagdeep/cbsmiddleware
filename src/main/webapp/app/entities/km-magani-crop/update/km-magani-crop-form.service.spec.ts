import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../km-magani-crop.test-samples';

import { KmMaganiCropFormService } from './km-magani-crop-form.service';

describe('KmMaganiCrop Form Service', () => {
  let service: KmMaganiCropFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KmMaganiCropFormService);
  });

  describe('Service methods', () => {
    describe('createKmMaganiCropFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKmMaganiCropFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cropName: expect.any(Object),
            kmMagani: expect.any(Object),
          })
        );
      });

      it('passing IKmMaganiCrop should create a new form with FormGroup', () => {
        const formGroup = service.createKmMaganiCropFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cropName: expect.any(Object),
            kmMagani: expect.any(Object),
          })
        );
      });
    });

    describe('getKmMaganiCrop', () => {
      it('should return NewKmMaganiCrop for default KmMaganiCrop initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKmMaganiCropFormGroup(sampleWithNewData);

        const kmMaganiCrop = service.getKmMaganiCrop(formGroup) as any;

        expect(kmMaganiCrop).toMatchObject(sampleWithNewData);
      });

      it('should return NewKmMaganiCrop for empty KmMaganiCrop initial value', () => {
        const formGroup = service.createKmMaganiCropFormGroup();

        const kmMaganiCrop = service.getKmMaganiCrop(formGroup) as any;

        expect(kmMaganiCrop).toMatchObject({});
      });

      it('should return IKmMaganiCrop', () => {
        const formGroup = service.createKmMaganiCropFormGroup(sampleWithRequiredData);

        const kmMaganiCrop = service.getKmMaganiCrop(formGroup) as any;

        expect(kmMaganiCrop).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKmMaganiCrop should not enable id FormControl', () => {
        const formGroup = service.createKmMaganiCropFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKmMaganiCrop should disable id FormControl', () => {
        const formGroup = service.createKmMaganiCropFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
