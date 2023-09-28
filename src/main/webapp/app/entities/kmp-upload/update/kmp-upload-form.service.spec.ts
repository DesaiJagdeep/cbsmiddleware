import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../kmp-upload.test-samples';

import { KMPUploadFormService } from './kmp-upload-form.service';

describe('KMPUpload Form Service', () => {
  let service: KMPUploadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KMPUploadFormService);
  });

  describe('Service methods', () => {
    describe('createKMPUploadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKMPUploadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            uniqueFileName: expect.any(Object),
          })
        );
      });

      it('passing IKMPUpload should create a new form with FormGroup', () => {
        const formGroup = service.createKMPUploadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            uniqueFileName: expect.any(Object),
          })
        );
      });
    });

    describe('getKMPUpload', () => {
      it('should return NewKMPUpload for default KMPUpload initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKMPUploadFormGroup(sampleWithNewData);

        const kMPUpload = service.getKMPUpload(formGroup) as any;

        expect(kMPUpload).toMatchObject(sampleWithNewData);
      });

      it('should return NewKMPUpload for empty KMPUpload initial value', () => {
        const formGroup = service.createKMPUploadFormGroup();

        const kMPUpload = service.getKMPUpload(formGroup) as any;

        expect(kMPUpload).toMatchObject({});
      });

      it('should return IKMPUpload', () => {
        const formGroup = service.createKMPUploadFormGroup(sampleWithRequiredData);

        const kMPUpload = service.getKMPUpload(formGroup) as any;

        expect(kMPUpload).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKMPUpload should not enable id FormControl', () => {
        const formGroup = service.createKMPUploadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKMPUpload should disable id FormControl', () => {
        const formGroup = service.createKMPUploadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
