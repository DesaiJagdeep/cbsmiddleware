import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ActivityTypeFormService, ActivityTypeFormGroup } from './activity-type-form.service';
import { IActivityType } from '../activity-type.model';
import { ActivityTypeService } from '../service/activity-type.service';

@Component({
  selector: 'jhi-activity-type-update',
  templateUrl: './activity-type-update.component.html',
})
export class ActivityTypeUpdateComponent implements OnInit {
  isSaving = false;
  activityType: IActivityType | null = null;

  editForm: ActivityTypeFormGroup = this.activityTypeFormService.createActivityTypeFormGroup();

  constructor(
    protected activityTypeService: ActivityTypeService,
    protected activityTypeFormService: ActivityTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityType }) => {
      this.activityType = activityType;
      if (activityType) {
        this.updateForm(activityType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activityType = this.activityTypeFormService.getActivityType(this.editForm);
    if (activityType.id !== null) {
      this.subscribeToSaveResponse(this.activityTypeService.update(activityType));
    } else {
      this.subscribeToSaveResponse(this.activityTypeService.create(activityType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityType>>): void {
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

  protected updateForm(activityType: IActivityType): void {
    this.activityType = activityType;
    this.activityTypeFormService.resetForm(this.editForm, activityType);
  }
}
