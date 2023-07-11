import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cast-category-master.test-samples';

import { CastCategoryMasterFormService } from './cast-category-master-form.service';

describe('CastCategoryMaster Form Service', () => {
  let service: CastCategoryMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CastCategoryMasterFormService);
  });

  describe('Service methods', () => {
    describe('createCastCategoryMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCastCategoryMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            castCategoryCode: expect.any(Object),
            castCategoryName: expect.any(Object),
          })
        );
      });

      it('passing ICastCategoryMaster should create a new form with FormGroup', () => {
        const formGroup = service.createCastCategoryMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            castCategoryCode: expect.any(Object),
            castCategoryName: expect.any(Object),
          })
        );
      });
    });

    describe('getCastCategoryMaster', () => {
      it('should return NewCastCategoryMaster for default CastCategoryMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCastCategoryMasterFormGroup(sampleWithNewData);

        const castCategoryMaster = service.getCastCategoryMaster(formGroup) as any;

        expect(castCategoryMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewCastCategoryMaster for empty CastCategoryMaster initial value', () => {
        const formGroup = service.createCastCategoryMasterFormGroup();

        const castCategoryMaster = service.getCastCategoryMaster(formGroup) as any;

        expect(castCategoryMaster).toMatchObject({});
      });

      it('should return ICastCategoryMaster', () => {
        const formGroup = service.createCastCategoryMasterFormGroup(sampleWithRequiredData);

        const castCategoryMaster = service.getCastCategoryMaster(formGroup) as any;

        expect(castCategoryMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICastCategoryMaster should not enable id FormControl', () => {
        const formGroup = service.createCastCategoryMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCastCategoryMaster should disable id FormControl', () => {
        const formGroup = service.createCastCategoryMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
