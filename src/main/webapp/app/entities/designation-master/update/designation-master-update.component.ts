import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DesignationMasterFormService, DesignationMasterFormGroup } from './designation-master-form.service';
import { IDesignationMaster } from '../designation-master.model';
import { DesignationMasterService } from '../service/designation-master.service';

@Component({
  selector: 'jhi-designation-master-update',
  templateUrl: './designation-master-update.component.html',
})
export class DesignationMasterUpdateComponent implements OnInit {
  isSaving = false;
  designationMaster: IDesignationMaster | null = null;

  editForm: DesignationMasterFormGroup = this.designationMasterFormService.createDesignationMasterFormGroup();

  constructor(
    protected designationMasterService: DesignationMasterService,
    protected designationMasterFormService: DesignationMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ designationMaster }) => {
      this.designationMaster = designationMaster;
      if (designationMaster) {
        this.updateForm(designationMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const designationMaster = this.designationMasterFormService.getDesignationMaster(this.editForm);
    if (designationMaster.id !== null) {
      this.subscribeToSaveResponse(this.designationMasterService.update(designationMaster));
    } else {
      this.subscribeToSaveResponse(this.designationMasterService.create(designationMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDesignationMaster>>): void {
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

  protected updateForm(designationMaster: IDesignationMaster): void {
    this.designationMaster = designationMaster;
    this.designationMasterFormService.resetForm(this.editForm, designationMaster);
  }
}
