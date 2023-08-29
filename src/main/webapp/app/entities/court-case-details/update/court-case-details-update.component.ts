import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CourtCaseDetailsFormService, CourtCaseDetailsFormGroup } from './court-case-details-form.service';
import { ICourtCaseDetails } from '../court-case-details.model';
import { CourtCaseDetailsService } from '../service/court-case-details.service';

@Component({
  selector: 'jhi-court-case-details-update',
  templateUrl: './court-case-details-update.component.html',
})
export class CourtCaseDetailsUpdateComponent implements OnInit {
  isSaving = false;
  courtCaseDetails: ICourtCaseDetails | null = null;

  editForm: CourtCaseDetailsFormGroup = this.courtCaseDetailsFormService.createCourtCaseDetailsFormGroup();

  constructor(
    protected courtCaseDetailsService: CourtCaseDetailsService,
    protected courtCaseDetailsFormService: CourtCaseDetailsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courtCaseDetails }) => {
      this.courtCaseDetails = courtCaseDetails;
      if (courtCaseDetails) {
        this.updateForm(courtCaseDetails);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courtCaseDetails = this.courtCaseDetailsFormService.getCourtCaseDetails(this.editForm);
    if (courtCaseDetails.id !== null) {
      this.subscribeToSaveResponse(this.courtCaseDetailsService.update(courtCaseDetails));
    } else {
      this.subscribeToSaveResponse(this.courtCaseDetailsService.create(courtCaseDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourtCaseDetails>>): void {
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

  protected updateForm(courtCaseDetails: ICourtCaseDetails): void {
    this.courtCaseDetails = courtCaseDetails;
    this.courtCaseDetailsFormService.resetForm(this.editForm, courtCaseDetails);
  }
}
