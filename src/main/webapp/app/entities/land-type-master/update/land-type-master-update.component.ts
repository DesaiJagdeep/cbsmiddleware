import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LandTypeMasterFormService, LandTypeMasterFormGroup } from './land-type-master-form.service';
import { ILandTypeMaster } from '../land-type-master.model';
import { LandTypeMasterService } from '../service/land-type-master.service';

@Component({
  selector: 'jhi-land-type-master-update',
  templateUrl: './land-type-master-update.component.html',
})
export class LandTypeMasterUpdateComponent implements OnInit {
  isSaving = false;
  landTypeMaster: ILandTypeMaster | null = null;

  editForm: LandTypeMasterFormGroup = this.landTypeMasterFormService.createLandTypeMasterFormGroup();

  constructor(
    protected landTypeMasterService: LandTypeMasterService,
    protected landTypeMasterFormService: LandTypeMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ landTypeMaster }) => {
      this.landTypeMaster = landTypeMaster;
      if (landTypeMaster) {
        this.updateForm(landTypeMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const landTypeMaster = this.landTypeMasterFormService.getLandTypeMaster(this.editForm);
    if (landTypeMaster.id !== null) {
      this.subscribeToSaveResponse(this.landTypeMasterService.update(landTypeMaster));
    } else {
      this.subscribeToSaveResponse(this.landTypeMasterService.create(landTypeMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILandTypeMaster>>): void {
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

  protected updateForm(landTypeMaster: ILandTypeMaster): void {
    this.landTypeMaster = landTypeMaster;
    this.landTypeMasterFormService.resetForm(this.editForm, landTypeMaster);
  }
}
