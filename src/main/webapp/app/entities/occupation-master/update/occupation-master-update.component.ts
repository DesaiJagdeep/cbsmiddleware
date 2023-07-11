import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OccupationMasterFormService, OccupationMasterFormGroup } from './occupation-master-form.service';
import { IOccupationMaster } from '../occupation-master.model';
import { OccupationMasterService } from '../service/occupation-master.service';

@Component({
  selector: 'jhi-occupation-master-update',
  templateUrl: './occupation-master-update.component.html',
})
export class OccupationMasterUpdateComponent implements OnInit {
  isSaving = false;
  occupationMaster: IOccupationMaster | null = null;

  editForm: OccupationMasterFormGroup = this.occupationMasterFormService.createOccupationMasterFormGroup();

  constructor(
    protected occupationMasterService: OccupationMasterService,
    protected occupationMasterFormService: OccupationMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ occupationMaster }) => {
      this.occupationMaster = occupationMaster;
      if (occupationMaster) {
        this.updateForm(occupationMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const occupationMaster = this.occupationMasterFormService.getOccupationMaster(this.editForm);
    if (occupationMaster.id !== null) {
      this.subscribeToSaveResponse(this.occupationMasterService.update(occupationMaster));
    } else {
      this.subscribeToSaveResponse(this.occupationMasterService.create(occupationMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOccupationMaster>>): void {
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

  protected updateForm(occupationMaster: IOccupationMaster): void {
    this.occupationMaster = occupationMaster;
    this.occupationMasterFormService.resetForm(this.editForm, occupationMaster);
  }
}
