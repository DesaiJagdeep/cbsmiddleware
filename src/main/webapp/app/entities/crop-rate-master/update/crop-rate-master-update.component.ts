import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CropRateMasterFormService, CropRateMasterFormGroup } from './crop-rate-master-form.service';
import { ICropRateMaster } from '../crop-rate-master.model';
import { CropRateMasterService } from '../service/crop-rate-master.service';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { CropMasterService } from 'app/entities/crop-master/service/crop-master.service';

@Component({
  selector: 'jhi-crop-rate-master-update',
  templateUrl: './crop-rate-master-update.component.html',
})
export class CropRateMasterUpdateComponent implements OnInit {
  isSaving = false;
  cropRateMaster: ICropRateMaster | null = null;

  cropMastersSharedCollection: ICropMaster[] = [];

  editForm: CropRateMasterFormGroup = this.cropRateMasterFormService.createCropRateMasterFormGroup();

  constructor(
    protected cropRateMasterService: CropRateMasterService,
    protected cropRateMasterFormService: CropRateMasterFormService,
    protected cropMasterService: CropMasterService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCropMaster = (o1: ICropMaster | null, o2: ICropMaster | null): boolean => this.cropMasterService.compareCropMaster(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cropRateMaster }) => {
      this.cropRateMaster = cropRateMaster;
      if (cropRateMaster) {
        this.updateForm(cropRateMaster);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cropRateMaster = this.cropRateMasterFormService.getCropRateMaster(this.editForm);
    if (cropRateMaster.id !== null) {
      this.subscribeToSaveResponse(this.cropRateMasterService.update(cropRateMaster));
    } else {
      this.subscribeToSaveResponse(this.cropRateMasterService.create(cropRateMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICropRateMaster>>): void {
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

  protected updateForm(cropRateMaster: ICropRateMaster): void {
    this.cropRateMaster = cropRateMaster;
    this.cropRateMasterFormService.resetForm(this.editForm, cropRateMaster);

    this.cropMastersSharedCollection = this.cropMasterService.addCropMasterToCollectionIfMissing<ICropMaster>(
      this.cropMastersSharedCollection,
      cropRateMaster.cropMaster
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cropMasterService
      .query()
      .pipe(map((res: HttpResponse<ICropMaster[]>) => res.body ?? []))
      .pipe(
        map((cropMasters: ICropMaster[]) =>
          this.cropMasterService.addCropMasterToCollectionIfMissing<ICropMaster>(cropMasters, this.cropRateMaster?.cropMaster)
        )
      )
      .subscribe((cropMasters: ICropMaster[]) => (this.cropMastersSharedCollection = cropMasters));
  }
}
