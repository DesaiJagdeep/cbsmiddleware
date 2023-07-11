import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../land-type-master.test-samples';

import { LandTypeMasterFormService } from './land-type-master-form.service';

describe('LandTypeMaster Form Service', () => {
  let service: LandTypeMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LandTypeMasterFormService);
  });

  describe('Service methods', () => {
    describe('createLandTypeMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLandTypeMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            landTypeCode: expect.any(Object),
            landType: expect.any(Object),
          })
        );
      });

      it('passing ILandTypeMaster should create a new form with FormGroup', () => {
        const formGroup = service.createLandTypeMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            landTypeCode: expect.any(Object),
            landType: expect.any(Object),
          })
        );
      });
    });

    describe('getLandTypeMaster', () => {
      it('should return NewLandTypeMaster for default LandTypeMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLandTypeMasterFormGroup(sampleWithNewData);

        const landTypeMaster = service.getLandTypeMaster(formGroup) as any;

        expect(landTypeMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewLandTypeMaster for empty LandTypeMaster initial value', () => {
        const formGroup = service.createLandTypeMasterFormGroup();

        const landTypeMaster = service.getLandTypeMaster(formGroup) as any;

        expect(landTypeMaster).toMatchObject({});
      });

      it('should return ILandTypeMaster', () => {
        const formGroup = service.createLandTypeMasterFormGroup(sampleWithRequiredData);

        const landTypeMaster = service.getLandTypeMaster(formGroup) as any;

        expect(landTypeMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILandTypeMaster should not enable id FormControl', () => {
        const formGroup = service.createLandTypeMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLandTypeMaster should disable id FormControl', () => {
        const formGroup = service.createLandTypeMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
