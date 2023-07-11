import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RelativeMasterFormService, RelativeMasterFormGroup } from './relative-master-form.service';
import { IRelativeMaster } from '../relative-master.model';
import { RelativeMasterService } from '../service/relative-master.service';

@Component({
  selector: 'jhi-relative-master-update',
  templateUrl: './relative-master-update.component.html',
})
export class RelativeMasterUpdateComponent implements OnInit {
  isSaving = false;
  relativeMaster: IRelativeMaster | null = null;

  editForm: RelativeMasterFormGroup = this.relativeMasterFormService.createRelativeMasterFormGroup();

  constructor(
    protected relativeMasterService: RelativeMasterService,
    protected relativeMasterFormService: RelativeMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relativeMaster }) => {
      this.relativeMaster = relativeMaster;
      if (relativeMaster) {
        this.updateForm(relativeMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const relativeMaster = this.relativeMasterFormService.getRelativeMaster(this.editForm);
    if (relativeMaster.id !== null) {
      this.subscribeToSaveResponse(this.relativeMasterService.update(relativeMaster));
    } else {
      this.subscribeToSaveResponse(this.relativeMasterService.create(relativeMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelativeMaster>>): void {
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

  protected updateForm(relativeMaster: IRelativeMaster): void {
    this.relativeMaster = relativeMaster;
    this.relativeMasterFormService.resetForm(this.editForm, relativeMaster);
  }
}
