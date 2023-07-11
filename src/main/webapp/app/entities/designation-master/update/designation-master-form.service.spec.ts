import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../designation-master.test-samples';

import { DesignationMasterFormService } from './designation-master-form.service';

describe('DesignationMaster Form Service', () => {
  let service: DesignationMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DesignationMasterFormService);
  });

  describe('Service methods', () => {
    describe('createDesignationMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDesignationMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            designationCode: expect.any(Object),
            designationName: expect.any(Object),
          })
        );
      });

      it('passing IDesignationMaster should create a new form with FormGroup', () => {
        const formGroup = service.createDesignationMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            designationCode: expect.any(Object),
            designationName: expect.any(Object),
          })
        );
      });
    });

    describe('getDesignationMaster', () => {
      it('should return NewDesignationMaster for default DesignationMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDesignationMasterFormGroup(sampleWithNewData);

        const designationMaster = service.getDesignationMaster(formGroup) as any;

        expect(designationMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewDesignationMaster for empty DesignationMaster initial value', () => {
        const formGroup = service.createDesignationMasterFormGroup();

        const designationMaster = service.getDesignationMaster(formGroup) as any;

        expect(designationMaster).toMatchObject({});
      });

      it('should return IDesignationMaster', () => {
        const formGroup = service.createDesignationMasterFormGroup(sampleWithRequiredData);

        const designationMaster = service.getDesignationMaster(formGroup) as any;

        expect(designationMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDesignationMaster should not enable id FormControl', () => {
        const formGroup = service.createDesignationMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDesignationMaster should disable id FormControl', () => {
        const formGroup = service.createDesignationMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
