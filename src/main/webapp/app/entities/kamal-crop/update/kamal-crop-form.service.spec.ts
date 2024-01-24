import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../kamal-crop.test-samples';

import { KamalCropFormService } from './kamal-crop-form.service';

describe('KamalCrop Form Service', () => {
  let service: KamalCropFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KamalCropFormService);
  });

  describe('Service methods', () => {
    describe('createKamalCropFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKamalCropFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pacsNumber: expect.any(Object),
            memNo: expect.any(Object),
            memHector: expect.any(Object),
            memNoMr: expect.any(Object),
            memHectorMr: expect.any(Object),
            memAar: expect.any(Object),
            memAarMr: expect.any(Object),
            farmerTypeMaster: expect.any(Object),
            cropHangam: expect.any(Object),
            cropMaster: expect.any(Object),
            kamalSociety: expect.any(Object),
          })
        );
      });

      it('passing IKamalCrop should create a new form with FormGroup', () => {
        const formGroup = service.createKamalCropFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pacsNumber: expect.any(Object),
            memNo: expect.any(Object),
            memHector: expect.any(Object),
            memNoMr: expect.any(Object),
            memHectorMr: expect.any(Object),
            memAar: expect.any(Object),
            memAarMr: expect.any(Object),
            farmerTypeMaster: expect.any(Object),
            cropHangam: expect.any(Object),
            cropMaster: expect.any(Object),
            kamalSociety: expect.any(Object),
          })
        );
      });
    });

    describe('getKamalCrop', () => {
      it('should return NewKamalCrop for default KamalCrop initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKamalCropFormGroup(sampleWithNewData);

        const kamalCrop = service.getKamalCrop(formGroup) as any;

        expect(kamalCrop).toMatchObject(sampleWithNewData);
      });

      it('should return NewKamalCrop for empty KamalCrop initial value', () => {
        const formGroup = service.createKamalCropFormGroup();

        const kamalCrop = service.getKamalCrop(formGroup) as any;

        expect(kamalCrop).toMatchObject({});
      });

      it('should return IKamalCrop', () => {
        const formGroup = service.createKamalCropFormGroup(sampleWithRequiredData);

        const kamalCrop = service.getKamalCrop(formGroup) as any;

        expect(kamalCrop).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKamalCrop should not enable id FormControl', () => {
        const formGroup = service.createKamalCropFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKamalCrop should disable id FormControl', () => {
        const formGroup = service.createKamalCropFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
