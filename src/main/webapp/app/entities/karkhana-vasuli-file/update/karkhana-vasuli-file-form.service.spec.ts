import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../karkhana-vasuli-file.test-samples';

import { KarkhanaVasuliFileFormService } from './karkhana-vasuli-file-form.service';

describe('KarkhanaVasuliFile Form Service', () => {
  let service: KarkhanaVasuliFileFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KarkhanaVasuliFileFormService);
  });

  describe('Service methods', () => {
    describe('createKarkhanaVasuliFileFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKarkhanaVasuliFileFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            uniqueFileName: expect.any(Object),
            address: expect.any(Object),
            addressMr: expect.any(Object),
            hangam: expect.any(Object),
            hangamMr: expect.any(Object),
            factoryName: expect.any(Object),
            factoryNameMr: expect.any(Object),
            totalAmount: expect.any(Object),
            totalAmountMr: expect.any(Object),
            fromDate: expect.any(Object),
            toDate: expect.any(Object),
          }),
        );
      });

      it('passing IKarkhanaVasuliFile should create a new form with FormGroup', () => {
        const formGroup = service.createKarkhanaVasuliFileFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            uniqueFileName: expect.any(Object),
            address: expect.any(Object),
            addressMr: expect.any(Object),
            hangam: expect.any(Object),
            hangamMr: expect.any(Object),
            factoryName: expect.any(Object),
            factoryNameMr: expect.any(Object),
            totalAmount: expect.any(Object),
            totalAmountMr: expect.any(Object),
            fromDate: expect.any(Object),
            toDate: expect.any(Object),
          }),
        );
      });
    });

    describe('getKarkhanaVasuliFile', () => {
      it('should return NewKarkhanaVasuliFile for default KarkhanaVasuliFile initial value', () => {
        const formGroup = service.createKarkhanaVasuliFileFormGroup(sampleWithNewData);

        const karkhanaVasuliFile = service.getKarkhanaVasuliFile(formGroup) as any;

        expect(karkhanaVasuliFile).toMatchObject(sampleWithNewData);
      });

      it('should return NewKarkhanaVasuliFile for empty KarkhanaVasuliFile initial value', () => {
        const formGroup = service.createKarkhanaVasuliFileFormGroup();

        const karkhanaVasuliFile = service.getKarkhanaVasuliFile(formGroup) as any;

        expect(karkhanaVasuliFile).toMatchObject({});
      });

      it('should return IKarkhanaVasuliFile', () => {
        const formGroup = service.createKarkhanaVasuliFileFormGroup(sampleWithRequiredData);

        const karkhanaVasuliFile = service.getKarkhanaVasuliFile(formGroup) as any;

        expect(karkhanaVasuliFile).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKarkhanaVasuliFile should not enable id FormControl', () => {
        const formGroup = service.createKarkhanaVasuliFileFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKarkhanaVasuliFile should disable id FormControl', () => {
        const formGroup = service.createKarkhanaVasuliFileFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
