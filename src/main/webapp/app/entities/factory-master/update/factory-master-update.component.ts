import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FactoryMasterFormService, FactoryMasterFormGroup } from './factory-master-form.service';
import { IFactoryMaster } from '../factory-master.model';
import { FactoryMasterService } from '../service/factory-master.service';

@Component({
  selector: 'jhi-factory-master-update',
  templateUrl: './factory-master-update.component.html',
})
export class FactoryMasterUpdateComponent implements OnInit {
  isSaving = false;
  factoryMaster: IFactoryMaster | null = null;

  editForm: FactoryMasterFormGroup = this.factoryMasterFormService.createFactoryMasterFormGroup();

  constructor(
    protected factoryMasterService: FactoryMasterService,
    protected factoryMasterFormService: FactoryMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factoryMaster }) => {
      this.factoryMaster = factoryMaster;
      if (factoryMaster) {
        this.updateForm(factoryMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factoryMaster = this.factoryMasterFormService.getFactoryMaster(this.editForm);
    if (factoryMaster.id !== null) {
      this.subscribeToSaveResponse(this.factoryMasterService.update(factoryMaster));
    } else {
      this.subscribeToSaveResponse(this.factoryMasterService.create(factoryMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactoryMaster>>): void {
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

  protected updateForm(factoryMaster: IFactoryMaster): void {
    this.factoryMaster = factoryMaster;
    this.factoryMasterFormService.resetForm(this.editForm, factoryMaster);
  }
}
