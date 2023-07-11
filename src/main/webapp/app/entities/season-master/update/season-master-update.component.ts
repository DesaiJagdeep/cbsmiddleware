import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SeasonMasterFormService, SeasonMasterFormGroup } from './season-master-form.service';
import { ISeasonMaster } from '../season-master.model';
import { SeasonMasterService } from '../service/season-master.service';

@Component({
  selector: 'jhi-season-master-update',
  templateUrl: './season-master-update.component.html',
})
export class SeasonMasterUpdateComponent implements OnInit {
  isSaving = false;
  seasonMaster: ISeasonMaster | null = null;

  editForm: SeasonMasterFormGroup = this.seasonMasterFormService.createSeasonMasterFormGroup();

  constructor(
    protected seasonMasterService: SeasonMasterService,
    protected seasonMasterFormService: SeasonMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seasonMaster }) => {
      this.seasonMaster = seasonMaster;
      if (seasonMaster) {
        this.updateForm(seasonMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const seasonMaster = this.seasonMasterFormService.getSeasonMaster(this.editForm);
    if (seasonMaster.id !== null) {
      this.subscribeToSaveResponse(this.seasonMasterService.update(seasonMaster));
    } else {
      this.subscribeToSaveResponse(this.seasonMasterService.create(seasonMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeasonMaster>>): void {
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

  protected updateForm(seasonMaster: ISeasonMaster): void {
    this.seasonMaster = seasonMaster;
    this.seasonMasterFormService.resetForm(this.editForm, seasonMaster);
  }
}
