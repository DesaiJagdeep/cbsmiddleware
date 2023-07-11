import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FarmerCategoryMasterFormService, FarmerCategoryMasterFormGroup } from './farmer-category-master-form.service';
import { IFarmerCategoryMaster } from '../farmer-category-master.model';
import { FarmerCategoryMasterService } from '../service/farmer-category-master.service';

@Component({
  selector: 'jhi-farmer-category-master-update',
  templateUrl: './farmer-category-master-update.component.html',
})
export class FarmerCategoryMasterUpdateComponent implements OnInit {
  isSaving = false;
  farmerCategoryMaster: IFarmerCategoryMaster | null = null;

  editForm: FarmerCategoryMasterFormGroup = this.farmerCategoryMasterFormService.createFarmerCategoryMasterFormGroup();

  constructor(
    protected farmerCategoryMasterService: FarmerCategoryMasterService,
    protected farmerCategoryMasterFormService: FarmerCategoryMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ farmerCategoryMaster }) => {
      this.farmerCategoryMaster = farmerCategoryMaster;
      if (farmerCategoryMaster) {
        this.updateForm(farmerCategoryMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const farmerCategoryMaster = this.farmerCategoryMasterFormService.getFarmerCategoryMaster(this.editForm);
    if (farmerCategoryMaster.id !== null) {
      this.subscribeToSaveResponse(this.farmerCategoryMasterService.update(farmerCategoryMaster));
    } else {
      this.subscribeToSaveResponse(this.farmerCategoryMasterService.create(farmerCategoryMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFarmerCategoryMaster>>): void {
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

  protected updateForm(farmerCategoryMaster: IFarmerCategoryMaster): void {
    this.farmerCategoryMaster = farmerCategoryMaster;
    this.farmerCategoryMasterFormService.resetForm(this.editForm, farmerCategoryMaster);
  }
}
