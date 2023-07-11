import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../occupation-master.test-samples';

import { OccupationMasterFormService } from './occupation-master-form.service';

describe('OccupationMaster Form Service', () => {
  let service: OccupationMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OccupationMasterFormService);
  });

  describe('Service methods', () => {
    describe('createOccupationMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOccupationMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            occupationCode: expect.any(Object),
            occupationName: expect.any(Object),
          })
        );
      });

      it('passing IOccupationMaster should create a new form with FormGroup', () => {
        const formGroup = service.createOccupationMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            occupationCode: expect.any(Object),
            occupationName: expect.any(Object),
          })
        );
      });
    });

    describe('getOccupationMaster', () => {
      it('should return NewOccupationMaster for default OccupationMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOccupationMasterFormGroup(sampleWithNewData);

        const occupationMaster = service.getOccupationMaster(formGroup) as any;

        expect(occupationMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewOccupationMaster for empty OccupationMaster initial value', () => {
        const formGroup = service.createOccupationMasterFormGroup();

        const occupationMaster = service.getOccupationMaster(formGroup) as any;

        expect(occupationMaster).toMatchObject({});
      });

      it('should return IOccupationMaster', () => {
        const formGroup = service.createOccupationMasterFormGroup(sampleWithRequiredData);

        const occupationMaster = service.getOccupationMaster(formGroup) as any;

        expect(occupationMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOccupationMaster should not enable id FormControl', () => {
        const formGroup = service.createOccupationMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOccupationMaster should disable id FormControl', () => {
        const formGroup = service.createOccupationMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
