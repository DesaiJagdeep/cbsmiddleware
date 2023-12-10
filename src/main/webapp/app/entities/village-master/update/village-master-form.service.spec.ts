import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../village-master.test-samples';

import { VillageMasterFormService } from './village-master-form.service';

describe('VillageMaster Form Service', () => {
  let service: VillageMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VillageMasterFormService);
  });

  describe('Service methods', () => {
    describe('createVillageMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVillageMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            villageName: expect.any(Object),
            villageNameMr: expect.any(Object),
            villageCode: expect.any(Object),
            villageCodeMr: expect.any(Object),
            talukaMaster: expect.any(Object),
          }),
        );
      });

      it('passing IVillageMaster should create a new form with FormGroup', () => {
        const formGroup = service.createVillageMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            villageName: expect.any(Object),
            villageNameMr: expect.any(Object),
            villageCode: expect.any(Object),
            villageCodeMr: expect.any(Object),
            talukaMaster: expect.any(Object),
          }),
        );
      });
    });

    describe('getVillageMaster', () => {
      it('should return NewVillageMaster for default VillageMaster initial value', () => {
        const formGroup = service.createVillageMasterFormGroup(sampleWithNewData);

        const villageMaster = service.getVillageMaster(formGroup) as any;

        expect(villageMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewVillageMaster for empty VillageMaster initial value', () => {
        const formGroup = service.createVillageMasterFormGroup();

        const villageMaster = service.getVillageMaster(formGroup) as any;

        expect(villageMaster).toMatchObject({});
      });

      it('should return IVillageMaster', () => {
        const formGroup = service.createVillageMasterFormGroup(sampleWithRequiredData);

        const villageMaster = service.getVillageMaster(formGroup) as any;

        expect(villageMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVillageMaster should not enable id FormControl', () => {
        const formGroup = service.createVillageMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVillageMaster should disable id FormControl', () => {
        const formGroup = service.createVillageMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
