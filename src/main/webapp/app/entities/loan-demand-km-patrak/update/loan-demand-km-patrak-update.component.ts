import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LoanDemandKMPatrakFormService, LoanDemandKMPatrakFormGroup } from './loan-demand-km-patrak-form.service';
import { ILoanDemandKMPatrak } from '../loan-demand-km-patrak.model';
import { LoanDemandKMPatrakService } from '../service/loan-demand-km-patrak.service';

@Component({
  selector: 'jhi-loan-demand-km-patrak-update',
  templateUrl: './loan-demand-km-patrak-update.component.html',
})
export class LoanDemandKMPatrakUpdateComponent implements OnInit {
  isSaving = false;
  loanDemandKMPatrak: ILoanDemandKMPatrak | null = null;

  editForm: LoanDemandKMPatrakFormGroup = this.loanDemandKMPatrakFormService.createLoanDemandKMPatrakFormGroup();

  constructor(
    protected loanDemandKMPatrakService: LoanDemandKMPatrakService,
    protected loanDemandKMPatrakFormService: LoanDemandKMPatrakFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDemandKMPatrak }) => {
      this.loanDemandKMPatrak = loanDemandKMPatrak;
      if (loanDemandKMPatrak) {
        this.updateForm(loanDemandKMPatrak);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanDemandKMPatrak = this.loanDemandKMPatrakFormService.getLoanDemandKMPatrak(this.editForm);
    if (loanDemandKMPatrak.id !== null) {
      this.subscribeToSaveResponse(this.loanDemandKMPatrakService.update(loanDemandKMPatrak));
    } else {
      this.subscribeToSaveResponse(this.loanDemandKMPatrakService.create(loanDemandKMPatrak));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanDemandKMPatrak>>): void {
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

  protected updateForm(loanDemandKMPatrak: ILoanDemandKMPatrak): void {
    this.loanDemandKMPatrak = loanDemandKMPatrak;
    this.loanDemandKMPatrakFormService.resetForm(this.editForm, loanDemandKMPatrak);
  }
}
