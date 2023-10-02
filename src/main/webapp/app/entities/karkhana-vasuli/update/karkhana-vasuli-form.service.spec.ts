import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../karkhana-vasuli.test-samples';

import { KarkhanaVasuliFormService } from './karkhana-vasuli-form.service';

describe('KarkhanaVasuli Form Service', () => {
  let service: KarkhanaVasuliFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KarkhanaVasuliFormService);
  });

  describe('Service methods', () => {
    describe('createKarkhanaVasuliFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKarkhanaVasuliFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            khataNumber: expect.any(Object),
            name: expect.any(Object),
            societyName: expect.any(Object),
            talukaName: expect.any(Object),
            branchName: expect.any(Object),
            defaulterName: expect.any(Object),
          })
        );
      });

      it('passing IKarkhanaVasuli should create a new form with FormGroup', () => {
        const formGroup = service.createKarkhanaVasuliFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            khataNumber: expect.any(Object),
            name: expect.any(Object),
            societyName: expect.any(Object),
            talukaName: expect.any(Object),
            branchName: expect.any(Object),
            defaulterName: expect.any(Object),
          })
        );
      });
    });

    describe('getKarkhanaVasuli', () => {
      it('should return NewKarkhanaVasuli for default KarkhanaVasuli initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKarkhanaVasuliFormGroup(sampleWithNewData);

        const karkhanaVasuli = service.getKarkhanaVasuli(formGroup) as any;

        expect(karkhanaVasuli).toMatchObject(sampleWithNewData);
      });

      it('should return NewKarkhanaVasuli for empty KarkhanaVasuli initial value', () => {
        const formGroup = service.createKarkhanaVasuliFormGroup();

        const karkhanaVasuli = service.getKarkhanaVasuli(formGroup) as any;

        expect(karkhanaVasuli).toMatchObject({});
      });

      it('should return IKarkhanaVasuli', () => {
        const formGroup = service.createKarkhanaVasuliFormGroup(sampleWithRequiredData);

        const karkhanaVasuli = service.getKarkhanaVasuli(formGroup) as any;

        expect(karkhanaVasuli).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKarkhanaVasuli should not enable id FormControl', () => {
        const formGroup = service.createKarkhanaVasuliFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKarkhanaVasuli should disable id FormControl', () => {
        const formGroup = service.createKarkhanaVasuliFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
