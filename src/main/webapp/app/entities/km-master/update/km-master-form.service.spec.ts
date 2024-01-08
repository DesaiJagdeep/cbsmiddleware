import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../km-master.test-samples';

import { KmMasterFormService } from './km-master-form.service';

describe('KmMaster Form Service', () => {
  let service: KmMasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KmMasterFormService);
  });

  describe('Service methods', () => {
    describe('createKmMasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKmMasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            branchCode: expect.any(Object),
            farmerName: expect.any(Object),
            farmerNameMr: expect.any(Object),
            farmerAddress: expect.any(Object),
            farmerAddressMr: expect.any(Object),
            gender: expect.any(Object),
            cast: expect.any(Object),
            castMr: expect.any(Object),
            pacsNumber: expect.any(Object),
            areaHector: expect.any(Object),
            aadharNo: expect.any(Object),
            aadharNoMr: expect.any(Object),
            panNo: expect.any(Object),
            panNoMr: expect.any(Object),
            mobileNo: expect.any(Object),
            mobileNoMr: expect.any(Object),
            savingNo: expect.any(Object),
            savingNoMr: expect.any(Object),
            pacsMemberCode: expect.any(Object),
            entryFlag: expect.any(Object),
            farmerTypeMaster: expect.any(Object),
          }),
        );
      });

      it('passing IKmMaster should create a new form with FormGroup', () => {
        const formGroup = service.createKmMasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            branchCode: expect.any(Object),
            farmerName: expect.any(Object),
            farmerNameMr: expect.any(Object),
            farmerAddress: expect.any(Object),
            farmerAddressMr: expect.any(Object),
            gender: expect.any(Object),
            cast: expect.any(Object),
            castMr: expect.any(Object),
            pacsNumber: expect.any(Object),
            areaHector: expect.any(Object),
            aadharNo: expect.any(Object),
            aadharNoMr: expect.any(Object),
            panNo: expect.any(Object),
            panNoMr: expect.any(Object),
            mobileNo: expect.any(Object),
            mobileNoMr: expect.any(Object),
            savingNo: expect.any(Object),
            savingNoMr: expect.any(Object),
            pacsMemberCode: expect.any(Object),
            entryFlag: expect.any(Object),
            farmerTypeMaster: expect.any(Object),
          }),
        );
      });
    });

    describe('getKmMaster', () => {
      it('should return NewKmMaster for default KmMaster initial value', () => {
        const formGroup = service.createKmMasterFormGroup(sampleWithNewData);

        const kmMaster = service.getKmMaster(formGroup) as any;

        expect(kmMaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewKmMaster for empty KmMaster initial value', () => {
        const formGroup = service.createKmMasterFormGroup();

        const kmMaster = service.getKmMaster(formGroup) as any;

        expect(kmMaster).toMatchObject({});
      });

      it('should return IKmMaster', () => {
        const formGroup = service.createKmMasterFormGroup(sampleWithRequiredData);

        const kmMaster = service.getKmMaster(formGroup) as any;

        expect(kmMaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKmMaster should not enable id FormControl', () => {
        const formGroup = service.createKmMasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKmMaster should disable id FormControl', () => {
        const formGroup = service.createKmMasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
