import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../district-master.test-samples';

import { DistrictMasterFormService } from './district-master-form.service';

describe('DistrictMaster Form Service', () => {
  let service: DistrictMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DistrictMasterFormService);
  });

  describe('Service methods', () => {
    describe('createDistrictMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDistrictMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            districtCode: expect.any(Object),
            districtName: expect.any(Object),
            stateCode: expect.any(Object),
          })
        );
      });

      it('passing IDistrictMaster should create a new form with FormGroup', () => {
        const formGroup = service.createDistrictMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            districtCode: expect.any(Object),
            districtName: expect.any(Object),
            stateCode: expect.any(Object),
          })
        );
      });
    });

    describe('getDistrictMaster', () => {
      it('should return NewDistrictMaster for default DistrictMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDistrictMasterFormGroup(sampleWithNewData);

        const districtMaster = service.getDistrictMaster(formGroup) as any;

        expect(districtMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewDistrictMaster for empty DistrictMaster initial value', () => {
        const formGroup = service.createDistrictMasterFormGroup();

        const districtMaster = service.getDistrictMaster(formGroup) as any;

        expect(districtMaster).toMatchObject({});
      });

      it('should return IDistrictMaster', () => {
        const formGroup = service.createDistrictMasterFormGroup(sampleWithRequiredData);

        const districtMaster = service.getDistrictMaster(formGroup) as any;

        expect(districtMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDistrictMaster should not enable id FormControl', () => {
        const formGroup = service.createDistrictMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDistrictMaster should disable id FormControl', () => {
        const formGroup = service.createDistrictMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
