import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { BankBranchMasterFormService, BankBranchMasterFormGroup } from './bank-branch-master-form.service';
import { IBankBranchMaster } from '../bank-branch-master.model';
import { BankBranchMasterService } from '../service/bank-branch-master.service';

@Component({
  selector: 'jhi-bank-branch-master-update',
  templateUrl: './bank-branch-master-update.component.html',
})
export class BankBranchMasterUpdateComponent implements OnInit {
  isSaving = false;
  bankBranchMaster: IBankBranchMaster | null = null;

  editForm: BankBranchMasterFormGroup = this.bankBranchMasterFormService.createBankBranchMasterFormGroup();

  constructor(
    protected bankBranchMasterService: BankBranchMasterService,
    protected bankBranchMasterFormService: BankBranchMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankBranchMaster }) => {
      this.bankBranchMaster = bankBranchMaster;
      if (bankBranchMaster) {
        this.updateForm(bankBranchMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bankBranchMaster = this.bankBranchMasterFormService.getBankBranchMaster(this.editForm);
    if (bankBranchMaster.id !== null) {
      this.subscribeToSaveResponse(this.bankBranchMasterService.update(bankBranchMaster));
    } else {
      this.subscribeToSaveResponse(this.bankBranchMasterService.create(bankBranchMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankBranchMaster>>): void {
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

  protected updateForm(bankBranchMaster: IBankBranchMaster): void {
    this.bankBranchMaster = bankBranchMaster;
    this.bankBranchMasterFormService.resetForm(this.editForm, bankBranchMaster);
  }
}
