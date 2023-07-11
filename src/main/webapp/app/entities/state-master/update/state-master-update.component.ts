import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StateMasterFormService, StateMasterFormGroup } from './state-master-form.service';
import { IStateMaster } from '../state-master.model';
import { StateMasterService } from '../service/state-master.service';

@Component({
  selector: 'jhi-state-master-update',
  templateUrl: './state-master-update.component.html',
})
export class StateMasterUpdateComponent implements OnInit {
  isSaving = false;
  stateMaster: IStateMaster | null = null;

  editForm: StateMasterFormGroup = this.stateMasterFormService.createStateMasterFormGroup();

  constructor(
    protected stateMasterService: StateMasterService,
    protected stateMasterFormService: StateMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stateMaster }) => {
      this.stateMaster = stateMaster;
      if (stateMaster) {
        this.updateForm(stateMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stateMaster = this.stateMasterFormService.getStateMaster(this.editForm);
    if (stateMaster.id !== null) {
      this.subscribeToSaveResponse(this.stateMasterService.update(stateMaster));
    } else {
      this.subscribeToSaveResponse(this.stateMasterService.create(stateMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStateMaster>>): void {
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

  protected updateForm(stateMaster: IStateMaster): void {
    this.stateMaster = stateMaster;
    this.stateMasterFormService.resetForm(this.editForm, stateMaster);
  }
}
