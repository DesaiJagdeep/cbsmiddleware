import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { BankMasterFormService, BankMasterFormGroup } from './bank-master-form.service';
import { IBankMaster } from '../bank-master.model';
import { BankMasterService } from '../service/bank-master.service';

@Component({
  selector: 'jhi-bank-master-update',
  templateUrl: './bank-master-update.component.html',
})
export class BankMasterUpdateComponent implements OnInit {
  isSaving = false;
  bankMaster: IBankMaster | null = null;

  editForm: BankMasterFormGroup = this.bankMasterFormService.createBankMasterFormGroup();

  constructor(
    protected bankMasterService: BankMasterService,
    protected bankMasterFormService: BankMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankMaster }) => {
      this.bankMaster = bankMaster;
      if (bankMaster) {
        this.updateForm(bankMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bankMaster = this.bankMasterFormService.getBankMaster(this.editForm);
    if (bankMaster.id !== null) {
      this.subscribeToSaveResponse(this.bankMasterService.update(bankMaster));
    } else {
      this.subscribeToSaveResponse(this.bankMasterService.create(bankMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankMaster>>): void {
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

  protected updateForm(bankMaster: IBankMaster): void {
    this.bankMaster = bankMaster;
    this.bankMasterFormService.resetForm(this.editForm, bankMaster);
  }
}
