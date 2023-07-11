import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../farmer-type-master.test-samples';

import { FarmerTypeMasterFormService } from './farmer-type-master-form.service';

describe('FarmerTypeMaster Form Service', () => {
  let service: FarmerTypeMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FarmerTypeMasterFormService);
  });

  describe('Service methods', () => {
    describe('createFarmerTypeMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFarmerTypeMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            farmerTypeCode: expect.any(Object),
            farmerType: expect.any(Object),
          })
        );
      });

      it('passing IFarmerTypeMaster should create a new form with FormGroup', () => {
        const formGroup = service.createFarmerTypeMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            farmerTypeCode: expect.any(Object),
            farmerType: expect.any(Object),
          })
        );
      });
    });

    describe('getFarmerTypeMaster', () => {
      it('should return NewFarmerTypeMaster for default FarmerTypeMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFarmerTypeMasterFormGroup(sampleWithNewData);

        const farmerTypeMaster = service.getFarmerTypeMaster(formGroup) as any;

        expect(farmerTypeMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewFarmerTypeMaster for empty FarmerTypeMaster initial value', () => {
        const formGroup = service.createFarmerTypeMasterFormGroup();

        const farmerTypeMaster = service.getFarmerTypeMaster(formGroup) as any;

        expect(farmerTypeMaster).toMatchObject({});
      });

      it('should return IFarmerTypeMaster', () => {
        const formGroup = service.createFarmerTypeMasterFormGroup(sampleWithRequiredData);

        const farmerTypeMaster = service.getFarmerTypeMaster(formGroup) as any;

        expect(farmerTypeMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFarmerTypeMaster should not enable id FormControl', () => {
        const formGroup = service.createFarmerTypeMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFarmerTypeMaster should disable id FormControl', () => {
        const formGroup = service.createFarmerTypeMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
