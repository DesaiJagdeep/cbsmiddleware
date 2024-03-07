import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../km-magani.test-samples';

import { KmMaganiFormService } from './km-magani-form.service';

describe('KmMagani Form Service', () => {
  let service: KmMaganiFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KmMaganiFormService);
  });

  describe('Service methods', () => {
    describe('createKmMaganiFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKmMaganiFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            kmNumber: expect.any(Object),
            memberNumber: expect.any(Object),
            memberName: expect.any(Object),
            pacsNumber: expect.any(Object),
            share: expect.any(Object),
            financialYear: expect.any(Object),
            kmDate: expect.any(Object),
            maganiDate: expect.any(Object),
          })
        );
      });

      it('passing IKmMagani should create a new form with FormGroup', () => {
        const formGroup = service.createKmMaganiFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            kmNumber: expect.any(Object),
            memberNumber: expect.any(Object),
            memberName: expect.any(Object),
            pacsNumber: expect.any(Object),
            share: expect.any(Object),
            financialYear: expect.any(Object),
            kmDate: expect.any(Object),
            maganiDate: expect.any(Object),
          })
        );
      });
    });

    describe('getKmMagani', () => {
      it('should return NewKmMagani for default KmMagani initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKmMaganiFormGroup(sampleWithNewData);

        const kmMagani = service.getKmMagani(formGroup) as any;

        expect(kmMagani).toMatchObject(sampleWithNewData);
      });

      it('should return NewKmMagani for empty KmMagani initial value', () => {
        const formGroup = service.createKmMaganiFormGroup();

        const kmMagani = service.getKmMagani(formGroup) as any;

        expect(kmMagani).toMatchObject({});
      });

      it('should return IKmMagani', () => {
        const formGroup = service.createKmMaganiFormGroup(sampleWithRequiredData);

        const kmMagani = service.getKmMagani(formGroup) as any;

        expect(kmMagani).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKmMagani should not enable id FormControl', () => {
        const formGroup = service.createKmMaganiFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKmMagani should disable id FormControl', () => {
        const formGroup = service.createKmMaganiFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
