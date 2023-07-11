import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../season-master.test-samples';

import { SeasonMasterFormService } from './season-master-form.service';

describe('SeasonMaster Form Service', () => {
  let service: SeasonMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SeasonMasterFormService);
  });

  describe('Service methods', () => {
    describe('createSeasonMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSeasonMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            seasonCode: expect.any(Object),
            seasonName: expect.any(Object),
          })
        );
      });

      it('passing ISeasonMaster should create a new form with FormGroup', () => {
        const formGroup = service.createSeasonMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            seasonCode: expect.any(Object),
            seasonName: expect.any(Object),
          })
        );
      });
    });

    describe('getSeasonMaster', () => {
      it('should return NewSeasonMaster for default SeasonMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSeasonMasterFormGroup(sampleWithNewData);

        const seasonMaster = service.getSeasonMaster(formGroup) as any;

        expect(seasonMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewSeasonMaster for empty SeasonMaster initial value', () => {
        const formGroup = service.createSeasonMasterFormGroup();

        const seasonMaster = service.getSeasonMaster(formGroup) as any;

        expect(seasonMaster).toMatchObject({});
      });

      it('should return ISeasonMaster', () => {
        const formGroup = service.createSeasonMasterFormGroup(sampleWithRequiredData);

        const seasonMaster = service.getSeasonMaster(formGroup) as any;

        expect(seasonMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISeasonMaster should not enable id FormControl', () => {
        const formGroup = service.createSeasonMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSeasonMaster should disable id FormControl', () => {
        const formGroup = service.createSeasonMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
