import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DistrictMasterFormService, DistrictMasterFormGroup } from './district-master-form.service';
import { IDistrictMaster } from '../district-master.model';
import { DistrictMasterService } from '../service/district-master.service';

@Component({
  selector: 'jhi-district-master-update',
  templateUrl: './district-master-update.component.html',
})
export class DistrictMasterUpdateComponent implements OnInit {
  isSaving = false;
  districtMaster: IDistrictMaster | null = null;

  editForm: DistrictMasterFormGroup = this.districtMasterFormService.createDistrictMasterFormGroup();

  constructor(
    protected districtMasterService: DistrictMasterService,
    protected districtMasterFormService: DistrictMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districtMaster }) => {
      this.districtMaster = districtMaster;
      if (districtMaster) {
        this.updateForm(districtMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const districtMaster = this.districtMasterFormService.getDistrictMaster(this.editForm);
    if (districtMaster.id !== null) {
      this.subscribeToSaveResponse(this.districtMasterService.update(districtMaster));
    } else {
      this.subscribeToSaveResponse(this.districtMasterService.create(districtMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistrictMaster>>): void {
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

  protected updateForm(districtMaster: IDistrictMaster): void {
    this.districtMaster = districtMaster;
    this.districtMasterFormService.resetForm(this.editForm, districtMaster);
  }
}
