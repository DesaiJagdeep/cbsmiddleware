import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pacs-master.test-samples';

import { PacsMasterFormService } from './pacs-master-form.service';

describe('PacsMaster Form Service', () => {
  let service: PacsMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PacsMasterFormService);
  });

  describe('Service methods', () => {
    describe('createPacsMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPacsMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pacsName: expect.any(Object),
            pacsNumber: expect.any(Object),
          })
        );
      });

      it('passing IPacsMaster should create a new form with FormGroup', () => {
        const formGroup = service.createPacsMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pacsName: expect.any(Object),
            pacsNumber: expect.any(Object),
          })
        );
      });
    });

    describe('getPacsMaster', () => {
      it('should return NewPacsMaster for default PacsMaster initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPacsMasterFormGroup(sampleWithNewData);

        const pacsMaster = service.getPacsMaster(formGroup) as any;

        expect(pacsMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewPacsMaster for empty PacsMaster initial value', () => {
        const formGroup = service.createPacsMasterFormGroup();

        const pacsMaster = service.getPacsMaster(formGroup) as any;

        expect(pacsMaster).toMatchObject({});
      });

      it('should return IPacsMaster', () => {
        const formGroup = service.createPacsMasterFormGroup(sampleWithRequiredData);

        const pacsMaster = service.getPacsMaster(formGroup) as any;

        expect(pacsMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPacsMaster should not enable id FormControl', () => {
        const formGroup = service.createPacsMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPacsMaster should disable id FormControl', () => {
        const formGroup = service.createPacsMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
