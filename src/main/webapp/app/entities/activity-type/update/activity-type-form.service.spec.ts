import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../activity-type.test-samples';

import { ActivityTypeFormService } from './activity-type-form.service';

describe('ActivityType Form Service', () => {
  let service: ActivityTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActivityTypeFormService);
  });

  describe('Service methods', () => {
    describe('createActivityTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createActivityTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            activityType: expect.any(Object),
            activityTypeCode: expect.any(Object),
          })
        );
      });

      it('passing IActivityType should create a new form with FormGroup', () => {
        const formGroup = service.createActivityTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            activityType: expect.any(Object),
            activityTypeCode: expect.any(Object),
          })
        );
      });
    });

    describe('getActivityType', () => {
      it('should return NewActivityType for default ActivityType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createActivityTypeFormGroup(sampleWithNewData);

        const activityType = service.getActivityType(formGroup) as any;

        expect(activityType).toMatchObject(sampleWithNewData);
      });

      it('should return NewActivityType for empty ActivityType initial value', () => {
        const formGroup = service.createActivityTypeFormGroup();

        const activityType = service.getActivityType(formGroup) as any;

        expect(activityType).toMatchObject({});
      });

      it('should return IActivityType', () => {
        const formGroup = service.createActivityTypeFormGroup(sampleWithRequiredData);

        const activityType = service.getActivityType(formGroup) as any;

        expect(activityType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IActivityType should not enable id FormControl', () => {
        const formGroup = service.createActivityTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewActivityType should disable id FormControl', () => {
        const formGroup = service.createActivityTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
