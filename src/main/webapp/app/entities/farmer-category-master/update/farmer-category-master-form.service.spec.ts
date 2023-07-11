import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../farmer-category-master.test-samples';

import { FarmerCategoryMasterFormService } from './farmer-category-master-form.service';

describe('FarmerCategoryMaster Form Service', () => {
  let service: FarmerCategoryMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FarmerCategoryMasterFormService);
  });

  describe('Service methods', () => {
    describe('createFarmerCategoryMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFarmerCategoryMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            farmerCategoryCode: expect.any(Object),
            farmerCategory: expect.any(Object),
          })
        );
      });

      it('passing IFarmerCategoryMaster should create a new form with FormGroup', () => {
        const formGroup = service.createFarmerCategoryMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            farmerCategoryCode: expect.any(Object),
            farmerCategory: expect.any(Object),
          })
        );
      });
    });

    describe('getFarmerCategoryMaster', () => {
      it('should return NewFarmerCategoryMaster for default FarmerCategoryMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFarmerCategoryMasterFormGroup(sampleWithNewData);

        const farmerCategoryMaster = service.getFarmerCategoryMaster(formGroup) as any;

        expect(farmerCategoryMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewFarmerCategoryMaster for empty FarmerCategoryMaster initial value', () => {
        const formGroup = service.createFarmerCategoryMasterFormGroup();

        const farmerCategoryMaster = service.getFarmerCategoryMaster(formGroup) as any;

        expect(farmerCategoryMaster).toMatchObject({});
      });

      it('should return IFarmerCategoryMaster', () => {
        const formGroup = service.createFarmerCategoryMasterFormGroup(sampleWithRequiredData);

        const farmerCategoryMaster = service.getFarmerCategoryMaster(formGroup) as any;

        expect(farmerCategoryMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFarmerCategoryMaster should not enable id FormControl', () => {
        const formGroup = service.createFarmerCategoryMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFarmerCategoryMaster should disable id FormControl', () => {
        const formGroup = service.createFarmerCategoryMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
