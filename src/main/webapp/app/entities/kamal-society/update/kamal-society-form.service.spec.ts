import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../kamal-society.test-samples';

import { KamalSocietyFormService } from './kamal-society-form.service';

describe('KamalSociety Form Service', () => {
  let service: KamalSocietyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KamalSocietyFormService);
  });

  describe('Service methods', () => {
    describe('createKamalSocietyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKamalSocietyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            financialYear: expect.any(Object),
            kmDate: expect.any(Object),
          })
        );
      });

      it('passing IKamalSociety should create a new form with FormGroup', () => {
        const formGroup = service.createKamalSocietyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            financialYear: expect.any(Object),
            kmDate: expect.any(Object),
          })
        );
      });
    });

    describe('getKamalSociety', () => {
      it('should return NewKamalSociety for default KamalSociety initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKamalSocietyFormGroup(sampleWithNewData);

        const kamalSociety = service.getKamalSociety(formGroup) as any;

        expect(kamalSociety).toMatchObject(sampleWithNewData);
      });

      it('should return NewKamalSociety for empty KamalSociety initial value', () => {
        const formGroup = service.createKamalSocietyFormGroup();

        const kamalSociety = service.getKamalSociety(formGroup) as any;

        expect(kamalSociety).toMatchObject({});
      });

      it('should return IKamalSociety', () => {
        const formGroup = service.createKamalSocietyFormGroup(sampleWithRequiredData);

        const kamalSociety = service.getKamalSociety(formGroup) as any;

        expect(kamalSociety).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKamalSociety should not enable id FormControl', () => {
        const formGroup = service.createKamalSocietyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKamalSociety should disable id FormControl', () => {
        const formGroup = service.createKamalSocietyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
