import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FarmerTypeMasterFormService, FarmerTypeMasterFormGroup } from './farmer-type-master-form.service';
import { IFarmerTypeMaster } from '../farmer-type-master.model';
import { FarmerTypeMasterService } from '../service/farmer-type-master.service';

@Component({
  selector: 'jhi-farmer-type-master-update',
  templateUrl: './farmer-type-master-update.component.html',
})
export class FarmerTypeMasterUpdateComponent implements OnInit {
  isSaving = false;
  farmerTypeMaster: IFarmerTypeMaster | null = null;

  editForm: FarmerTypeMasterFormGroup = this.farmerTypeMasterFormService.createFarmerTypeMasterFormGroup();

  constructor(
    protected farmerTypeMasterService: FarmerTypeMasterService,
    protected farmerTypeMasterFormService: FarmerTypeMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ farmerTypeMaster }) => {
      this.farmerTypeMaster = farmerTypeMaster;
      if (farmerTypeMaster) {
        this.updateForm(farmerTypeMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const farmerTypeMaster = this.farmerTypeMasterFormService.getFarmerTypeMaster(this.editForm);
    if (farmerTypeMaster.id !== null) {
      this.subscribeToSaveResponse(this.farmerTypeMasterService.update(farmerTypeMaster));
    } else {
      this.subscribeToSaveResponse(this.farmerTypeMasterService.create(farmerTypeMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFarmerTypeMaster>>): void {
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

  protected updateForm(farmerTypeMaster: IFarmerTypeMaster): void {
    this.farmerTypeMaster = farmerTypeMaster;
    this.farmerTypeMasterFormService.resetForm(this.editForm, farmerTypeMaster);
  }
}
