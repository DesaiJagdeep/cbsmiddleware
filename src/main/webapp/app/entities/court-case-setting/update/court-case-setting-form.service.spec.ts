import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../court-case-setting.test-samples';

import { CourtCaseSettingFormService } from './court-case-setting-form.service';

describe('CourtCaseSetting Form Service', () => {
  let service: CourtCaseSettingFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourtCaseSettingFormService);
  });

  describe('Service methods', () => {
    describe('createCourtCaseSettingFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCourtCaseSettingFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dinank: expect.any(Object),
            shakhaVevsthapak: expect.any(Object),
            suchak: expect.any(Object),
            aanumodak: expect.any(Object),
            vasuliAdhikari: expect.any(Object),
            arOffice: expect.any(Object),
            tharavNumber: expect.any(Object),
            tharavDinank: expect.any(Object),
            karjFedNotice: expect.any(Object),
            oneZeroOneNoticeOne: expect.any(Object),
            oneZeroOneNoticeTwo: expect.any(Object),
            vishayKramank: expect.any(Object),
            war: expect.any(Object),
            vel: expect.any(Object),
            maganiNotice: expect.any(Object),
            etarKharch: expect.any(Object),
            noticeKharch: expect.any(Object),
            vasuliKharch: expect.any(Object),
          })
        );
      });

      it('passing ICourtCaseSetting should create a new form with FormGroup', () => {
        const formGroup = service.createCourtCaseSettingFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dinank: expect.any(Object),
            shakhaVevsthapak: expect.any(Object),
            suchak: expect.any(Object),
            aanumodak: expect.any(Object),
            vasuliAdhikari: expect.any(Object),
            arOffice: expect.any(Object),
            tharavNumber: expect.any(Object),
            tharavDinank: expect.any(Object),
            karjFedNotice: expect.any(Object),
            oneZeroOneNoticeOne: expect.any(Object),
            oneZeroOneNoticeTwo: expect.any(Object),
            vishayKramank: expect.any(Object),
            war: expect.any(Object),
            vel: expect.any(Object),
            maganiNotice: expect.any(Object),
            etarKharch: expect.any(Object),
            noticeKharch: expect.any(Object),
            vasuliKharch: expect.any(Object),
          })
        );
      });
    });

    describe('getCourtCaseSetting', () => {
      it('should return NewCourtCaseSetting for default CourtCaseSetting initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCourtCaseSettingFormGroup(sampleWithNewData);

        const courtCaseSetting = service.getCourtCaseSetting(formGroup) as any;

        expect(courtCaseSetting).toMatchObject(sampleWithNewData);
      });

      it('should return NewCourtCaseSetting for empty CourtCaseSetting initial value', () => {
        const formGroup = service.createCourtCaseSettingFormGroup();

        const courtCaseSetting = service.getCourtCaseSetting(formGroup) as any;

        expect(courtCaseSetting).toMatchObject({});
      });

      it('should return ICourtCaseSetting', () => {
        const formGroup = service.createCourtCaseSettingFormGroup(sampleWithRequiredData);

        const courtCaseSetting = service.getCourtCaseSetting(formGroup) as any;

        expect(courtCaseSetting).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICourtCaseSetting should not enable id FormControl', () => {
        const formGroup = service.createCourtCaseSettingFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCourtCaseSetting should disable id FormControl', () => {
        const formGroup = service.createCourtCaseSettingFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
