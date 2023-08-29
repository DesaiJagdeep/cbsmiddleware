import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CourtCaseSettingFormService, CourtCaseSettingFormGroup } from './court-case-setting-form.service';
import { ICourtCaseSetting } from '../court-case-setting.model';
import { CourtCaseSettingService } from '../service/court-case-setting.service';

@Component({
  selector: 'jhi-court-case-setting-update',
  templateUrl: './court-case-setting-update.component.html',
})
export class CourtCaseSettingUpdateComponent implements OnInit {
  isSaving = false;
  courtCaseSetting: ICourtCaseSetting | null = null;

  editForm: CourtCaseSettingFormGroup = this.courtCaseSettingFormService.createCourtCaseSettingFormGroup();

  constructor(
    protected courtCaseSettingService: CourtCaseSettingService,
    protected courtCaseSettingFormService: CourtCaseSettingFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courtCaseSetting }) => {
      this.courtCaseSetting = courtCaseSetting;
      if (courtCaseSetting) {
        this.updateForm(courtCaseSetting);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courtCaseSetting = this.courtCaseSettingFormService.getCourtCaseSetting(this.editForm);
    if (courtCaseSetting.id !== null) {
      this.subscribeToSaveResponse(this.courtCaseSettingService.update(courtCaseSetting));
    } else {
      this.subscribeToSaveResponse(this.courtCaseSettingService.create(courtCaseSetting));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourtCaseSetting>>): void {
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

  protected updateForm(courtCaseSetting: ICourtCaseSetting): void {
    this.courtCaseSetting = courtCaseSetting;
    this.courtCaseSettingFormService.resetForm(this.editForm, courtCaseSetting);
  }
}
