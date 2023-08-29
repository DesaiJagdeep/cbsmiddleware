import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../court-case.test-samples';

import { CourtCaseFormService } from './court-case-form.service';

describe('CourtCase Form Service', () => {
  let service: CourtCaseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourtCaseFormService);
  });

  describe('Service methods', () => {
    describe('createCourtCaseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCourtCaseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            caseDinank: expect.any(Object),
            bankName: expect.any(Object),
            talukaName: expect.any(Object),
            talukaCode: expect.any(Object),
            sabasadSavingAccNo: expect.any(Object),
            sabasadName: expect.any(Object),
            sabasadAddress: expect.any(Object),
            karjPrakar: expect.any(Object),
            vasuliAdhikari: expect.any(Object),
            ekunJama: expect.any(Object),
            baki: expect.any(Object),
            arOffice: expect.any(Object),
            ekunVyaj: expect.any(Object),
            jamaVyaj: expect.any(Object),
            dandVyaj: expect.any(Object),
            karjRakkam: expect.any(Object),
            thakDinnank: expect.any(Object),
            karjDinnank: expect.any(Object),
            mudatSampteDinank: expect.any(Object),
            mudat: expect.any(Object),
            vyaj: expect.any(Object),
            haptaRakkam: expect.any(Object),
            shakhaVevsthapak: expect.any(Object),
            suchak: expect.any(Object),
            anumodak: expect.any(Object),
            dava: expect.any(Object),
            vyajDar: expect.any(Object),
            sarcharge: expect.any(Object),
            jyadaVyaj: expect.any(Object),
            yeneVyaj: expect.any(Object),
            vasuliKharch: expect.any(Object),
            etharKharch: expect.any(Object),
            vima: expect.any(Object),
            notice: expect.any(Object),
            tharavNumber: expect.any(Object),
            tharavDinank: expect.any(Object),
            vishayKramank: expect.any(Object),
            noticeOne: expect.any(Object),
            noticeTwo: expect.any(Object),
            war: expect.any(Object),
            vel: expect.any(Object),
            jamindarOne: expect.any(Object),
            jamindarOneAddress: expect.any(Object),
            jamindarTwo: expect.any(Object),
            jamindarTwoAddress: expect.any(Object),
            taranType: expect.any(Object),
            taranDetails: expect.any(Object),
          })
        );
      });

      it('passing ICourtCase should create a new form with FormGroup', () => {
        const formGroup = service.createCourtCaseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            caseDinank: expect.any(Object),
            bankName: expect.any(Object),
            talukaName: expect.any(Object),
            talukaCode: expect.any(Object),
            sabasadSavingAccNo: expect.any(Object),
            sabasadName: expect.any(Object),
            sabasadAddress: expect.any(Object),
            karjPrakar: expect.any(Object),
            vasuliAdhikari: expect.any(Object),
            ekunJama: expect.any(Object),
            baki: expect.any(Object),
            arOffice: expect.any(Object),
            ekunVyaj: expect.any(Object),
            jamaVyaj: expect.any(Object),
            dandVyaj: expect.any(Object),
            karjRakkam: expect.any(Object),
            thakDinnank: expect.any(Object),
            karjDinnank: expect.any(Object),
            mudatSampteDinank: expect.any(Object),
            mudat: expect.any(Object),
            vyaj: expect.any(Object),
            haptaRakkam: expect.any(Object),
            shakhaVevsthapak: expect.any(Object),
            suchak: expect.any(Object),
            anumodak: expect.any(Object),
            dava: expect.any(Object),
            vyajDar: expect.any(Object),
            sarcharge: expect.any(Object),
            jyadaVyaj: expect.any(Object),
            yeneVyaj: expect.any(Object),
            vasuliKharch: expect.any(Object),
            etharKharch: expect.any(Object),
            vima: expect.any(Object),
            notice: expect.any(Object),
            tharavNumber: expect.any(Object),
            tharavDinank: expect.any(Object),
            vishayKramank: expect.any(Object),
            noticeOne: expect.any(Object),
            noticeTwo: expect.any(Object),
            war: expect.any(Object),
            vel: expect.any(Object),
            jamindarOne: expect.any(Object),
            jamindarOneAddress: expect.any(Object),
            jamindarTwo: expect.any(Object),
            jamindarTwoAddress: expect.any(Object),
            taranType: expect.any(Object),
            taranDetails: expect.any(Object),
          })
        );
      });
    });

    describe('getCourtCase', () => {
      it('should return NewCourtCase for default CourtCase initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCourtCaseFormGroup(sampleWithNewData);

        const courtCase = service.getCourtCase(formGroup) as any;

        expect(courtCase).toMatchObject(sampleWithNewData);
      });

      it('should return NewCourtCase for empty CourtCase initial value', () => {
        const formGroup = service.createCourtCaseFormGroup();

        const courtCase = service.getCourtCase(formGroup) as any;

        expect(courtCase).toMatchObject({});
      });

      it('should return ICourtCase', () => {
        const formGroup = service.createCourtCaseFormGroup(sampleWithRequiredData);

        const courtCase = service.getCourtCase(formGroup) as any;

        expect(courtCase).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICourtCase should not enable id FormControl', () => {
        const formGroup = service.createCourtCaseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCourtCase should disable id FormControl', () => {
        const formGroup = service.createCourtCaseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
