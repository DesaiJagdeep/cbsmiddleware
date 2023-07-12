import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBatchTransaction, NewBatchTransaction } from '../batch-transaction.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBatchTransaction for edit and NewBatchTransactionFormGroupInput for create.
 */
type BatchTransactionFormGroupInput = IBatchTransaction | PartialWithRequiredKeyOf<NewBatchTransaction>;

type BatchTransactionFormDefaults = Pick<NewBatchTransaction, 'id'>;

type BatchTransactionFormGroupContent = {
  id: FormControl<IBatchTransaction['id'] | NewBatchTransaction['id']>;
  status: FormControl<IBatchTransaction['status']>;
  batchDetails: FormControl<IBatchTransaction['batchDetails']>;
  applicationCount: FormControl<IBatchTransaction['applicationCount']>;
  notes: FormControl<IBatchTransaction['notes']>;
  batchId: FormControl<IBatchTransaction['batchId']>;
  batchAckId: FormControl<IBatchTransaction['batchAckId']>;
};

export type BatchTransactionFormGroup = FormGroup<BatchTransactionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BatchTransactionFormService {
  createBatchTransactionFormGroup(batchTransaction: BatchTransactionFormGroupInput = { id: null }): BatchTransactionFormGroup {
    const batchTransactionRawValue = {
      ...this.getFormDefaults(),
      ...batchTransaction,
    };
    return new FormGroup<BatchTransactionFormGroupContent>({
      id: new FormControl(
        { value: batchTransactionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      status: new FormControl(batchTransactionRawValue.status),
      batchDetails: new FormControl(batchTransactionRawValue.batchDetails),
      applicationCount: new FormControl(batchTransactionRawValue.applicationCount),
      notes: new FormControl(batchTransactionRawValue.notes),
      batchId: new FormControl(batchTransactionRawValue.batchId),
      batchAckId: new FormControl(batchTransactionRawValue.batchAckId),
    });
  }

  getBatchTransaction(form: BatchTransactionFormGroup): IBatchTransaction | NewBatchTransaction {
    return form.getRawValue() as IBatchTransaction | NewBatchTransaction;
  }

  resetForm(form: BatchTransactionFormGroup, batchTransaction: BatchTransactionFormGroupInput): void {
    const batchTransactionRawValue = { ...this.getFormDefaults(), ...batchTransaction };
    form.reset(
      {
        ...batchTransactionRawValue,
        id: { value: batchTransactionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BatchTransactionFormDefaults {
    return {
      id: null,
    };
  }
}
