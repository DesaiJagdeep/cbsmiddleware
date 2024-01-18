import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../karkhana-vasuli-records.test-samples';

import { KarkhanaVasuliRecordsFormService } from './karkhana-vasuli-records-form.service';

describe('KarkhanaVasuliRecords Form Service', () => {
  let service: KarkhanaVasuliRecordsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KarkhanaVasuliRecordsFormService);
  });

  describe('Service methods', () => {
    describe('createKarkhanaVasuliRecordsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKarkhanaVasuliRecordsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            factoryMemberCode: expect.any(Object),
            karkhanaMemberCodeMr: expect.any(Object),
            memberName: expect.any(Object),
            memberNameMr: expect.any(Object),
            villageName: expect.any(Object),
            villageNameMr: expect.any(Object),
            amount: expect.any(Object),
            amountMr: expect.any(Object),
            savingAccNo: expect.any(Object),
            savingAccNoMr: expect.any(Object),
            status: expect.any(Object),
            karkhanaVasuliFile: expect.any(Object),
          })
        );
      });

      it('passing IKarkhanaVasuliRecords should create a new form with FormGroup', () => {
        const formGroup = service.createKarkhanaVasuliRecordsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            factoryMemberCode: expect.any(Object),
            karkhanaMemberCodeMr: expect.any(Object),
            memberName: expect.any(Object),
            memberNameMr: expect.any(Object),
            villageName: expect.any(Object),
            villageNameMr: expect.any(Object),
            amount: expect.any(Object),
            amountMr: expect.any(Object),
            savingAccNo: expect.any(Object),
            savingAccNoMr: expect.any(Object),
            status: expect.any(Object),
            karkhanaVasuliFile: expect.any(Object),
          })
        );
      });
    });

    describe('getKarkhanaVasuliRecords', () => {
      it('should return NewKarkhanaVasuliRecords for default KarkhanaVasuliRecords initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKarkhanaVasuliRecordsFormGroup(sampleWithNewData);

        const karkhanaVasuliRecords = service.getKarkhanaVasuliRecords(formGroup) as any;

        expect(karkhanaVasuliRecords).toMatchObject(sampleWithNewData);
      });

      it('should return NewKarkhanaVasuliRecords for empty KarkhanaVasuliRecords initial value', () => {
        const formGroup = service.createKarkhanaVasuliRecordsFormGroup();

        const karkhanaVasuliRecords = service.getKarkhanaVasuliRecords(formGroup) as any;

        expect(karkhanaVasuliRecords).toMatchObject({});
      });

      it('should return IKarkhanaVasuliRecords', () => {
        const formGroup = service.createKarkhanaVasuliRecordsFormGroup(sampleWithRequiredData);

        const karkhanaVasuliRecords = service.getKarkhanaVasuliRecords(formGroup) as any;

        expect(karkhanaVasuliRecords).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKarkhanaVasuliRecords should not enable id FormControl', () => {
        const formGroup = service.createKarkhanaVasuliRecordsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKarkhanaVasuliRecords should disable id FormControl', () => {
        const formGroup = service.createKarkhanaVasuliRecordsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
