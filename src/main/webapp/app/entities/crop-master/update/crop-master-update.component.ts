import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICropMaster } from '../crop-master.model';
import { CropMasterService } from '../service/crop-master.service';
import { CropMasterFormService, CropMasterFormGroup } from './crop-master-form.service';

@Component({
  standalone: true,
  selector: 'jhi-crop-master-update',
  templateUrl: './crop-master-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CropMasterUpdateComponent implements OnInit {
  isSaving = false;
  cropMaster: ICropMaster | null = null;

  editForm: CropMasterFormGroup = this.cropMasterFormService.createCropMasterFormGroup();

  constructor(
    protected cropMasterService: CropMasterService,
    protected cropMasterFormService: CropMasterFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cropMaster }) => {
      this.cropMaster = cropMaster;
      if (cropMaster) {
        this.updateForm(cropMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cropMaster = this.cropMasterFormService.getCropMaster(this.editForm);
    if (cropMaster.id !== null) {
      this.subscribeToSaveResponse(this.cropMasterService.update(cropMaster));
    } else {
      this.subscribeToSaveResponse(this.cropMasterService.create(cropMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICropMaster>>): void {
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

  protected updateForm(cropMaster: ICropMaster): void {
    this.cropMaster = cropMaster;
    this.cropMasterFormService.resetForm(this.editForm, cropMaster);
  }
}
