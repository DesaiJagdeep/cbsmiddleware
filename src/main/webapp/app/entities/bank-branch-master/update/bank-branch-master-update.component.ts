import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { BankBranchMasterFormService, BankBranchMasterFormGroup } from './bank-branch-master-form.service';
import { IBankBranchMaster } from '../bank-branch-master.model';
import { BankBranchMasterService } from '../service/bank-branch-master.service';
import { IBankMaster } from 'app/entities/bank-master/bank-master.model';
import { BankMasterService } from 'app/entities/bank-master/service/bank-master.service';

@Component({
  selector: 'jhi-bank-branch-master-update',
  templateUrl: './bank-branch-master-update.component.html',
})
export class BankBranchMasterUpdateComponent implements OnInit {
  isSaving = false;
  bankBranchMaster: IBankBranchMaster | null = null;

  bankMastersSharedCollection: IBankMaster[] = [];

  editForm: BankBranchMasterFormGroup = this.bankBranchMasterFormService.createBankBranchMasterFormGroup();

  constructor(
    protected bankBranchMasterService: BankBranchMasterService,
    protected bankBranchMasterFormService: BankBranchMasterFormService,
    protected bankMasterService: BankMasterService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBankMaster = (o1: IBankMaster | null, o2: IBankMaster | null): boolean => this.bankMasterService.compareBankMaster(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankBranchMaster }) => {
      this.bankBranchMaster = bankBranchMaster;
      if (bankBranchMaster) {
        this.updateForm(bankBranchMaster);
      }

      this.loadRelationshipsOptions();
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

    this.bankMastersSharedCollection = this.bankMasterService.addBankMasterToCollectionIfMissing<IBankMaster>(
      this.bankMastersSharedCollection,
      bankBranchMaster.bankMaster
    );
  }

  protected loadRelationshipsOptions(): void {
    this.bankMasterService
      .query()
      .pipe(map((res: HttpResponse<IBankMaster[]>) => res.body ?? []))
      .pipe(
        map((bankMasters: IBankMaster[]) =>
          this.bankMasterService.addBankMasterToCollectionIfMissing<IBankMaster>(bankMasters, this.bankBranchMaster?.bankMaster)
        )
      )
      .subscribe((bankMasters: IBankMaster[]) => (this.bankMastersSharedCollection = bankMasters));
  }
}
