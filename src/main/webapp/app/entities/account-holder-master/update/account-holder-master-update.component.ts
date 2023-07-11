import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AccountHolderMasterFormService, AccountHolderMasterFormGroup } from './account-holder-master-form.service';
import { IAccountHolderMaster } from '../account-holder-master.model';
import { AccountHolderMasterService } from '../service/account-holder-master.service';

@Component({
  selector: 'jhi-account-holder-master-update',
  templateUrl: './account-holder-master-update.component.html',
})
export class AccountHolderMasterUpdateComponent implements OnInit {
  isSaving = false;
  accountHolderMaster: IAccountHolderMaster | null = null;

  editForm: AccountHolderMasterFormGroup = this.accountHolderMasterFormService.createAccountHolderMasterFormGroup();

  constructor(
    protected accountHolderMasterService: AccountHolderMasterService,
    protected accountHolderMasterFormService: AccountHolderMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountHolderMaster }) => {
      this.accountHolderMaster = accountHolderMaster;
      if (accountHolderMaster) {
        this.updateForm(accountHolderMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountHolderMaster = this.accountHolderMasterFormService.getAccountHolderMaster(this.editForm);
    if (accountHolderMaster.id !== null) {
      this.subscribeToSaveResponse(this.accountHolderMasterService.update(accountHolderMaster));
    } else {
      this.subscribeToSaveResponse(this.accountHolderMasterService.create(accountHolderMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountHolderMaster>>): void {
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

  protected updateForm(accountHolderMaster: IAccountHolderMaster): void {
    this.accountHolderMaster = accountHolderMaster;
    this.accountHolderMasterFormService.resetForm(this.editForm, accountHolderMaster);
  }
}
