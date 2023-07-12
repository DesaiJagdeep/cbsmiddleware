import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../iss-portal-file.test-samples';

import { IssPortalFileFormService } from './iss-portal-file-form.service';

describe('IssPortalFile Form Service', () => {
  let service: IssPortalFileFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssPortalFileFormService);
  });

  describe('Service methods', () => {
    describe('createIssPortalFileFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIssPortalFileFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            fileExtension: expect.any(Object),
            branchCode: expect.any(Object),
            financialYear: expect.any(Object),
            fromDisbursementDate: expect.any(Object),
            toDisbursementDate: expect.any(Object),
            pacsCode: expect.any(Object),
            status: expect.any(Object),
            applicationCount: expect.any(Object),
            notes: expect.any(Object),
          })
        );
      });

      it('passing IIssPortalFile should create a new form with FormGroup', () => {
        const formGroup = service.createIssPortalFileFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            fileExtension: expect.any(Object),
            branchCode: expect.any(Object),
            financialYear: expect.any(Object),
            fromDisbursementDate: expect.any(Object),
            toDisbursementDate: expect.any(Object),
            pacsCode: expect.any(Object),
            status: expect.any(Object),
            applicationCount: expect.any(Object),
            notes: expect.any(Object),
          })
        );
      });
    });

    describe('getIssPortalFile', () => {
      it('should return NewIssPortalFile for default IssPortalFile initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIssPortalFileFormGroup(sampleWithNewData);

        const issPortalFile = service.getIssPortalFile(formGroup) as any;

        expect(issPortalFile).toMatchObject(sampleWithNewData);
      });

      it('should return NewIssPortalFile for empty IssPortalFile initial value', () => {
        const formGroup = service.createIssPortalFileFormGroup();

        const issPortalFile = service.getIssPortalFile(formGroup) as any;

        expect(issPortalFile).toMatchObject({});
      });

      it('should return IIssPortalFile', () => {
        const formGroup = service.createIssPortalFileFormGroup(sampleWithRequiredData);

        const issPortalFile = service.getIssPortalFile(formGroup) as any;

        expect(issPortalFile).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIssPortalFile should not enable id FormControl', () => {
        const formGroup = service.createIssPortalFileFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIssPortalFile should disable id FormControl', () => {
        const formGroup = service.createIssPortalFileFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
