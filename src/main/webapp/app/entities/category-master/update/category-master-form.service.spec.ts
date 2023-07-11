import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../category-master.test-samples';

import { CategoryMasterFormService } from './category-master-form.service';

describe('CategoryMaster Form Service', () => {
  let service: CategoryMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoryMasterFormService);
  });

  describe('Service methods', () => {
    describe('createCategoryMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoryMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            castName: expect.any(Object),
            castCode: expect.any(Object),
            castCategoryCode: expect.any(Object),
          })
        );
      });

      it('passing ICategoryMaster should create a new form with FormGroup', () => {
        const formGroup = service.createCategoryMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            castName: expect.any(Object),
            castCode: expect.any(Object),
            castCategoryCode: expect.any(Object),
          })
        );
      });
    });

    describe('getCategoryMaster', () => {
      it('should return NewCategoryMaster for default CategoryMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategoryMasterFormGroup(sampleWithNewData);

        const categoryMaster = service.getCategoryMaster(formGroup) as any;

        expect(categoryMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategoryMaster for empty CategoryMaster initial value', () => {
        const formGroup = service.createCategoryMasterFormGroup();

        const categoryMaster = service.getCategoryMaster(formGroup) as any;

        expect(categoryMaster).toMatchObject({});
      });

      it('should return ICategoryMaster', () => {
        const formGroup = service.createCategoryMasterFormGroup(sampleWithRequiredData);

        const categoryMaster = service.getCategoryMaster(formGroup) as any;

        expect(categoryMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategoryMaster should not enable id FormControl', () => {
        const formGroup = service.createCategoryMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategoryMaster should disable id FormControl', () => {
        const formGroup = service.createCategoryMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
