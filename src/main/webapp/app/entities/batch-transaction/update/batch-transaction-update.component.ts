import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { BatchTransactionFormService, BatchTransactionFormGroup } from './batch-transaction-form.service';
import { IBatchTransaction } from '../batch-transaction.model';
import { BatchTransactionService } from '../service/batch-transaction.service';

@Component({
  selector: 'jhi-batch-transaction-update',
  templateUrl: './batch-transaction-update.component.html',
})
export class BatchTransactionUpdateComponent implements OnInit {
  isSaving = false;
  batchTransaction: IBatchTransaction | null = null;

  editForm: BatchTransactionFormGroup = this.batchTransactionFormService.createBatchTransactionFormGroup();

  constructor(
    protected batchTransactionService: BatchTransactionService,
    protected batchTransactionFormService: BatchTransactionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ batchTransaction }) => {
      this.batchTransaction = batchTransaction;
      if (batchTransaction) {
        this.updateForm(batchTransaction);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const batchTransaction = this.batchTransactionFormService.getBatchTransaction(this.editForm);
    if (batchTransaction.id !== null) {
      this.subscribeToSaveResponse(this.batchTransactionService.update(batchTransaction));
    } else {
      this.subscribeToSaveResponse(this.batchTransactionService.create(batchTransaction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBatchTransaction>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(batchTransaction: IBatchTransaction): void {
    this.batchTransaction = batchTransaction;
    this.batchTransactionFormService.resetForm(this.editForm, batchTransaction);
  }
}
