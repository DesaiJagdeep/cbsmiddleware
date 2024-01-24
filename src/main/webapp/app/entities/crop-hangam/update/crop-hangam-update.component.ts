import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CropHangamFormService, CropHangamFormGroup } from './crop-hangam-form.service';
import { ICropHangam } from '../crop-hangam.model';
import { CropHangamService } from '../service/crop-hangam.service';

@Component({
  selector: 'jhi-crop-hangam-update',
  templateUrl: './crop-hangam-update.component.html',
})
export class CropHangamUpdateComponent implements OnInit {
  isSaving = false;
  cropHangam: ICropHangam | null = null;

  editForm: CropHangamFormGroup = this.cropHangamFormService.createCropHangamFormGroup();

  constructor(
    protected cropHangamService: CropHangamService,
    protected cropHangamFormService: CropHangamFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cropHangam }) => {
      this.cropHangam = cropHangam;
      if (cropHangam) {
        this.updateForm(cropHangam);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cropHangam = this.cropHangamFormService.getCropHangam(this.editForm);
    if (cropHangam.id !== null) {
      this.subscribeToSaveResponse(this.cropHangamService.update(cropHangam));
    } else {
      this.subscribeToSaveResponse(this.cropHangamService.create(cropHangam));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICropHangam>>): void {
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

  protected updateForm(cropHangam: ICropHangam): void {
    this.cropHangam = cropHangam;
    this.cropHangamFormService.resetForm(this.editForm, cropHangam);
  }
}
