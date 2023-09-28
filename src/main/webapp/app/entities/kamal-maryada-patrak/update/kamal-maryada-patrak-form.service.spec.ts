import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../kamal-maryada-patrak.test-samples';

import { KamalMaryadaPatrakFormService } from './kamal-maryada-patrak-form.service';

describe('KamalMaryadaPatrak Form Service', () => {
  let service: KamalMaryadaPatrakFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KamalMaryadaPatrakFormService);
  });

  describe('Service methods', () => {
    describe('createKamalMaryadaPatrakFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKamalMaryadaPatrakFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cropLoan: expect.any(Object),
            kmChart: expect.any(Object),
            demand: expect.any(Object),
            kmSummary: expect.any(Object),
            kmDate: expect.any(Object),
            toKMDate: expect.any(Object),
            coverPage: expect.any(Object),
            cropTypeNumber: expect.any(Object),
            cropType: expect.any(Object),
            fromHector: expect.any(Object),
            toHector: expect.any(Object),
            defaulterName: expect.any(Object),
            suchakName: expect.any(Object),
            anumodakName: expect.any(Object),
            meetingDate: expect.any(Object),
            resolutionNumber: expect.any(Object),
          })
        );
      });

      it('passing IKamalMaryadaPatrak should create a new form with FormGroup', () => {
        const formGroup = service.createKamalMaryadaPatrakFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cropLoan: expect.any(Object),
            kmChart: expect.any(Object),
            demand: expect.any(Object),
            kmSummary: expect.any(Object),
            kmDate: expect.any(Object),
            toKMDate: expect.any(Object),
            coverPage: expect.any(Object),
            cropTypeNumber: expect.any(Object),
            cropType: expect.any(Object),
            fromHector: expect.any(Object),
            toHector: expect.any(Object),
            defaulterName: expect.any(Object),
            suchakName: expect.any(Object),
            anumodakName: expect.any(Object),
            meetingDate: expect.any(Object),
            resolutionNumber: expect.any(Object),
          })
        );
      });
    });

    describe('getKamalMaryadaPatrak', () => {
      it('should return NewKamalMaryadaPatrak for default KamalMaryadaPatrak initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKamalMaryadaPatrakFormGroup(sampleWithNewData);

        const kamalMaryadaPatrak = service.getKamalMaryadaPatrak(formGroup) as any;

        expect(kamalMaryadaPatrak).toMatchObject(sampleWithNewData);
      });

      it('should return NewKamalMaryadaPatrak for empty KamalMaryadaPatrak initial value', () => {
        const formGroup = service.createKamalMaryadaPatrakFormGroup();

        const kamalMaryadaPatrak = service.getKamalMaryadaPatrak(formGroup) as any;

        expect(kamalMaryadaPatrak).toMatchObject({});
      });

      it('should return IKamalMaryadaPatrak', () => {
        const formGroup = service.createKamalMaryadaPatrakFormGroup(sampleWithRequiredData);

        const kamalMaryadaPatrak = service.getKamalMaryadaPatrak(formGroup) as any;

        expect(kamalMaryadaPatrak).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKamalMaryadaPatrak should not enable id FormControl', () => {
        const formGroup = service.createKamalMaryadaPatrakFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKamalMaryadaPatrak should disable id FormControl', () => {
        const formGroup = service.createKamalMaryadaPatrakFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
