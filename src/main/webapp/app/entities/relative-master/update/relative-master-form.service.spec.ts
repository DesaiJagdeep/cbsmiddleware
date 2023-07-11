import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../relative-master.test-samples';

import { RelativeMasterFormService } from './relative-master-form.service';

describe('RelativeMaster Form Service', () => {
  let service: RelativeMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RelativeMasterFormService);
  });

  describe('Service methods', () => {
    describe('createRelativeMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRelativeMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            relativeCode: expect.any(Object),
            relativeName: expect.any(Object),
          })
        );
      });

      it('passing IRelativeMaster should create a new form with FormGroup', () => {
        const formGroup = service.createRelativeMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            relativeCode: expect.any(Object),
            relativeName: expect.any(Object),
          })
        );
      });
    });

    describe('getRelativeMaster', () => {
      it('should return NewRelativeMaster for default RelativeMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRelativeMasterFormGroup(sampleWithNewData);

        const relativeMaster = service.getRelativeMaster(formGroup) as any;

        expect(relativeMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewRelativeMaster for empty RelativeMaster initial value', () => {
        const formGroup = service.createRelativeMasterFormGroup();

        const relativeMaster = service.getRelativeMaster(formGroup) as any;

        expect(relativeMaster).toMatchObject({});
      });

      it('should return IRelativeMaster', () => {
        const formGroup = service.createRelativeMasterFormGroup(sampleWithRequiredData);

        const relativeMaster = service.getRelativeMaster(formGroup) as any;

        expect(relativeMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRelativeMaster should not enable id FormControl', () => {
        const formGroup = service.createRelativeMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRelativeMaster should disable id FormControl', () => {
        const formGroup = service.createRelativeMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
