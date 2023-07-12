import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../batch-transaction.test-samples';

import { BatchTransactionFormService } from './batch-transaction-form.service';

describe('BatchTransaction Form Service', () => {
  let service: BatchTransactionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BatchTransactionFormService);
  });

  describe('Service methods', () => {
    describe('createBatchTransactionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBatchTransactionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            batchDetails: expect.any(Object),
            applicationCount: expect.any(Object),
            notes: expect.any(Object),
            batchId: expect.any(Object),
            batchAckId: expect.any(Object),
          })
        );
      });

      it('passing IBatchTransaction should create a new form with FormGroup', () => {
        const formGroup = service.createBatchTransactionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            status: expect.any(Object),
            batchDetails: expect.any(Object),
            applicationCount: expect.any(Object),
            notes: expect.any(Object),
            batchId: expect.any(Object),
            batchAckId: expect.any(Object),
          })
        );
      });
    });

    describe('getBatchTransaction', () => {
      it('should return NewBatchTransaction for default BatchTransaction initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBatchTransactionFormGroup(sampleWithNewData);

        const batchTransaction = service.getBatchTransaction(formGroup) as any;

        expect(batchTransaction).toMatchObject(sampleWithNewData);
      });

      it('should return NewBatchTransaction for empty BatchTransaction initial value', () => {
        const formGroup = service.createBatchTransactionFormGroup();

        const batchTransaction = service.getBatchTransaction(formGroup) as any;

        expect(batchTransaction).toMatchObject({});
      });

      it('should return IBatchTransaction', () => {
        const formGroup = service.createBatchTransactionFormGroup(sampleWithRequiredData);

        const batchTransaction = service.getBatchTransaction(formGroup) as any;

        expect(batchTransaction).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBatchTransaction should not enable id FormControl', () => {
        const formGroup = service.createBatchTransactionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBatchTransaction should disable id FormControl', () => {
        const formGroup = service.createBatchTransactionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
