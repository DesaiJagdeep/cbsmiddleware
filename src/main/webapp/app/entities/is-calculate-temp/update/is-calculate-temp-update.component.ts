import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IsCalculateTempFormService, IsCalculateTempFormGroup } from './is-calculate-temp-form.service';
import { IIsCalculateTemp } from '../is-calculate-temp.model';
import { IsCalculateTempService } from '../service/is-calculate-temp.service';

@Component({
  selector: 'jhi-is-calculate-temp-update',
  templateUrl: './is-calculate-temp-update.component.html',
})
export class IsCalculateTempUpdateComponent implements OnInit {
  isSaving = false;
  isCalculateTemp: IIsCalculateTemp | null = null;

  editForm: IsCalculateTempFormGroup = this.isCalculateTempFormService.createIsCalculateTempFormGroup();

  constructor(
    protected isCalculateTempService: IsCalculateTempService,
    protected isCalculateTempFormService: IsCalculateTempFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isCalculateTemp }) => {
      this.isCalculateTemp = isCalculateTemp;
      if (isCalculateTemp) {
        this.updateForm(isCalculateTemp);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const isCalculateTemp = this.isCalculateTempFormService.getIsCalculateTemp(this.editForm);
    if (isCalculateTemp.id !== null) {
      this.subscribeToSaveResponse(this.isCalculateTempService.update(isCalculateTemp));
    } else {
      this.subscribeToSaveResponse(this.isCalculateTempService.create(isCalculateTemp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIsCalculateTemp>>): void {
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

  protected updateForm(isCalculateTemp: IIsCalculateTemp): void {
    this.isCalculateTemp = isCalculateTemp;
    this.isCalculateTempFormService.resetForm(this.editForm, isCalculateTemp);
  }
}
