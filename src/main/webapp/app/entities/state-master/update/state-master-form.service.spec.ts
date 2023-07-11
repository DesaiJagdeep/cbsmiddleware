import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../state-master.test-samples';

import { StateMasterFormService } from './state-master-form.service';

describe('StateMaster Form Service', () => {
  let service: StateMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StateMasterFormService);
  });

  describe('Service methods', () => {
    describe('createStateMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStateMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stateCode: expect.any(Object),
            stateName: expect.any(Object),
          })
        );
      });

      it('passing IStateMaster should create a new form with FormGroup', () => {
        const formGroup = service.createStateMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stateCode: expect.any(Object),
            stateName: expect.any(Object),
          })
        );
      });
    });

    describe('getStateMaster', () => {
      it('should return NewStateMaster for default StateMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStateMasterFormGroup(sampleWithNewData);

        const stateMaster = service.getStateMaster(formGroup) as any;

        expect(stateMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewStateMaster for empty StateMaster initial value', () => {
        const formGroup = service.createStateMasterFormGroup();

        const stateMaster = service.getStateMaster(formGroup) as any;

        expect(stateMaster).toMatchObject({});
      });

      it('should return IStateMaster', () => {
        const formGroup = service.createStateMasterFormGroup(sampleWithRequiredData);

        const stateMaster = service.getStateMaster(formGroup) as any;

        expect(stateMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStateMaster should not enable id FormControl', () => {
        const formGroup = service.createStateMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStateMaster should disable id FormControl', () => {
        const formGroup = service.createStateMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
