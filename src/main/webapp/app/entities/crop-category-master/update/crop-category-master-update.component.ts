import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CropCategoryMasterFormService, CropCategoryMasterFormGroup } from './crop-category-master-form.service';
import { ICropCategoryMaster } from '../crop-category-master.model';
import { CropCategoryMasterService } from '../service/crop-category-master.service';

@Component({
  selector: 'jhi-crop-category-master-update',
  templateUrl: './crop-category-master-update.component.html',
})
export class CropCategoryMasterUpdateComponent implements OnInit {
  isSaving = false;
  cropCategoryMaster: ICropCategoryMaster | null = null;

  editForm: CropCategoryMasterFormGroup = this.cropCategoryMasterFormService.createCropCategoryMasterFormGroup();

  constructor(
    protected cropCategoryMasterService: CropCategoryMasterService,
    protected cropCategoryMasterFormService: CropCategoryMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cropCategoryMaster }) => {
      this.cropCategoryMaster = cropCategoryMaster;
      if (cropCategoryMaster) {
        this.updateForm(cropCategoryMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cropCategoryMaster = this.cropCategoryMasterFormService.getCropCategoryMaster(this.editForm);
    if (cropCategoryMaster.id !== null) {
      this.subscribeToSaveResponse(this.cropCategoryMasterService.update(cropCategoryMaster));
    } else {
      this.subscribeToSaveResponse(this.cropCategoryMasterService.create(cropCategoryMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICropCategoryMaster>>): void {
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

  protected updateForm(cropCategoryMaster: ICropCategoryMaster): void {
    this.cropCategoryMaster = cropCategoryMaster;
    this.cropCategoryMasterFormService.resetForm(this.editForm, cropCategoryMaster);
  }
}
