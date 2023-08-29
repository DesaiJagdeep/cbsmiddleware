import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CourtCaseFormService, CourtCaseFormGroup } from './court-case-form.service';
import { ICourtCase } from '../court-case.model';
import { CourtCaseService } from '../service/court-case.service';

@Component({
  selector: 'jhi-court-case-update',
  templateUrl: './court-case-update.component.html',
})
export class CourtCaseUpdateComponent implements OnInit {
  isSaving = false;
  courtCase: ICourtCase | null = null;

  editForm: CourtCaseFormGroup = this.courtCaseFormService.createCourtCaseFormGroup();

  constructor(
    protected courtCaseService: CourtCaseService,
    protected courtCaseFormService: CourtCaseFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courtCase }) => {
      this.courtCase = courtCase;
      if (courtCase) {
        this.updateForm(courtCase);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courtCase = this.courtCaseFormService.getCourtCase(this.editForm);
    if (courtCase.id !== null) {
      this.subscribeToSaveResponse(this.courtCaseService.update(courtCase));
    } else {
      this.subscribeToSaveResponse(this.courtCaseService.create(courtCase));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourtCase>>): void {
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

  protected updateForm(courtCase: ICourtCase): void {
    this.courtCase = courtCase;
    this.courtCaseFormService.resetForm(this.editForm, courtCase);
  }
}
