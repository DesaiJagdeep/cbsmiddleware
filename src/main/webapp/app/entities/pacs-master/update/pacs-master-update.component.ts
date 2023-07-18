import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PacsMasterFormService, PacsMasterFormGroup } from './pacs-master-form.service';
import { IPacsMaster } from '../pacs-master.model';
import { PacsMasterService } from '../service/pacs-master.service';

@Component({
  selector: 'jhi-pacs-master-update',
  templateUrl: './pacs-master-update.component.html',
})
export class PacsMasterUpdateComponent implements OnInit {
  isSaving = false;
  pacsMaster: IPacsMaster | null = null;

  editForm: PacsMasterFormGroup = this.pacsMasterFormService.createPacsMasterFormGroup();

  constructor(
    protected pacsMasterService: PacsMasterService,
    protected pacsMasterFormService: PacsMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pacsMaster }) => {
      this.pacsMaster = pacsMaster;
      if (pacsMaster) {
        this.updateForm(pacsMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pacsMaster = this.pacsMasterFormService.getPacsMaster(this.editForm);
    if (pacsMaster.id !== null) {
      this.subscribeToSaveResponse(this.pacsMasterService.update(pacsMaster));
    } else {
      this.subscribeToSaveResponse(this.pacsMasterService.create(pacsMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPacsMaster>>): void {
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

  protected updateForm(pacsMaster: IPacsMaster): void {
    this.pacsMaster = pacsMaster;
    this.pacsMasterFormService.resetForm(this.editForm, pacsMaster);
  }
}