import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../km-crops.test-samples';

import { KmCropsFormService } from './km-crops-form.service';

describe('KmCrops Form Service', () => {
  let service: KmCropsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KmCropsFormService);
  });

  describe('Service methods', () => {
    describe('createKmCropsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKmCropsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hector: expect.any(Object),
            hectorMr: expect.any(Object),
            are: expect.any(Object),
            areMr: expect.any(Object),
            noOfTree: expect.any(Object),
            noOfTreeMr: expect.any(Object),
            demand: expect.any(Object),
            demandMr: expect.any(Object),
            society: expect.any(Object),
            societyMr: expect.any(Object),
            bankAmt: expect.any(Object),
            bankAmtMr: expect.any(Object),
            vibhagiAdhikari: expect.any(Object),
            vibhagiAdhikariMr: expect.any(Object),
            branch: expect.any(Object),
            branchMr: expect.any(Object),
            inspAmt: expect.any(Object),
            inspAmtMr: expect.any(Object),
            cropMaster: expect.any(Object),
            kmDetails: expect.any(Object),
          })
        );
      });

      it('passing IKmCrops should create a new form with FormGroup', () => {
        const formGroup = service.createKmCropsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            hector: expect.any(Object),
            hectorMr: expect.any(Object),
            are: expect.any(Object),
            areMr: expect.any(Object),
            noOfTree: expect.any(Object),
            noOfTreeMr: expect.any(Object),
            demand: expect.any(Object),
            demandMr: expect.any(Object),
            society: expect.any(Object),
            societyMr: expect.any(Object),
            bankAmt: expect.any(Object),
            bankAmtMr: expect.any(Object),
            vibhagiAdhikari: expect.any(Object),
            vibhagiAdhikariMr: expect.any(Object),
            branch: expect.any(Object),
            branchMr: expect.any(Object),
            inspAmt: expect.any(Object),
            inspAmtMr: expect.any(Object),
            cropMaster: expect.any(Object),
            kmDetails: expect.any(Object),
          })
        );
      });
    });

    describe('getKmCrops', () => {
      it('should return NewKmCrops for default KmCrops initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKmCropsFormGroup(sampleWithNewData);

        const kmCrops = service.getKmCrops(formGroup) as any;

        expect(kmCrops).toMatchObject(sampleWithNewData);
      });

      it('should return NewKmCrops for empty KmCrops initial value', () => {
        const formGroup = service.createKmCropsFormGroup();

        const kmCrops = service.getKmCrops(formGroup) as any;

        expect(kmCrops).toMatchObject({});
      });

      it('should return IKmCrops', () => {
        const formGroup = service.createKmCropsFormGroup(sampleWithRequiredData);

        const kmCrops = service.getKmCrops(formGroup) as any;

        expect(kmCrops).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKmCrops should not enable id FormControl', () => {
        const formGroup = service.createKmCropsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKmCrops should disable id FormControl', () => {
        const formGroup = service.createKmCropsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
