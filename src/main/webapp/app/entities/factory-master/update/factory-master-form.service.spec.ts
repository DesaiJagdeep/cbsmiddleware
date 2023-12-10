import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../factory-master.test-samples';

import { FactoryMasterFormService } from './factory-master-form.service';

describe('FactoryMaster Form Service', () => {
  let service: FactoryMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FactoryMasterFormService);
  });

  describe('Service methods', () => {
    describe('createFactoryMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFactoryMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            factoryName: expect.any(Object),
            factoryNameMr: expect.any(Object),
            factoryCode: expect.any(Object),
            factoryCodeMr: expect.any(Object),
            factoryAddress: expect.any(Object),
            factoryAddressMr: expect.any(Object),
            description: expect.any(Object),
            status: expect.any(Object),
          }),
        );
      });

      it('passing IFactoryMaster should create a new form with FormGroup', () => {
        const formGroup = service.createFactoryMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            factoryName: expect.any(Object),
            factoryNameMr: expect.any(Object),
            factoryCode: expect.any(Object),
            factoryCodeMr: expect.any(Object),
            factoryAddress: expect.any(Object),
            factoryAddressMr: expect.any(Object),
            description: expect.any(Object),
            status: expect.any(Object),
          }),
        );
      });
    });

    describe('getFactoryMaster', () => {
      it('should return NewFactoryMaster for default FactoryMaster initial value', () => {
        const formGroup = service.createFactoryMasterFormGroup(sampleWithNewData);

        const factoryMaster = service.getFactoryMaster(formGroup) as any;

        expect(factoryMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewFactoryMaster for empty FactoryMaster initial value', () => {
        const formGroup = service.createFactoryMasterFormGroup();

        const factoryMaster = service.getFactoryMaster(formGroup) as any;

        expect(factoryMaster).toMatchObject({});
      });

      it('should return IFactoryMaster', () => {
        const formGroup = service.createFactoryMasterFormGroup(sampleWithRequiredData);

        const factoryMaster = service.getFactoryMaster(formGroup) as any;

        expect(factoryMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFactoryMaster should not enable id FormControl', () => {
        const formGroup = service.createFactoryMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFactoryMaster should disable id FormControl', () => {
        const formGroup = service.createFactoryMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
