import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../taluka-master.test-samples';

import { TalukaMasterFormService } from './taluka-master-form.service';

describe('TalukaMaster Form Service', () => {
  let service: TalukaMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TalukaMasterFormService);
  });

  describe('Service methods', () => {
    describe('createTalukaMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTalukaMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            talukaCode: expect.any(Object),
            talukaName: expect.any(Object),
            districtCode: expect.any(Object),
          })
        );
      });

      it('passing ITalukaMaster should create a new form with FormGroup', () => {
        const formGroup = service.createTalukaMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            talukaCode: expect.any(Object),
            talukaName: expect.any(Object),
            districtCode: expect.any(Object),
          })
        );
      });
    });

    describe('getTalukaMaster', () => {
      it('should return NewTalukaMaster for default TalukaMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTalukaMasterFormGroup(sampleWithNewData);

        const talukaMaster = service.getTalukaMaster(formGroup) as any;

        expect(talukaMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewTalukaMaster for empty TalukaMaster initial value', () => {
        const formGroup = service.createTalukaMasterFormGroup();

        const talukaMaster = service.getTalukaMaster(formGroup) as any;

        expect(talukaMaster).toMatchObject({});
      });

      it('should return ITalukaMaster', () => {
        const formGroup = service.createTalukaMasterFormGroup(sampleWithRequiredData);

        const talukaMaster = service.getTalukaMaster(formGroup) as any;

        expect(talukaMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITalukaMaster should not enable id FormControl', () => {
        const formGroup = service.createTalukaMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTalukaMaster should disable id FormControl', () => {
        const formGroup = service.createTalukaMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
