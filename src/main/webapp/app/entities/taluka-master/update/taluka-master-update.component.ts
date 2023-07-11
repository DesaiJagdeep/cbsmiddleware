import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TalukaMasterFormService, TalukaMasterFormGroup } from './taluka-master-form.service';
import { ITalukaMaster } from '../taluka-master.model';
import { TalukaMasterService } from '../service/taluka-master.service';

@Component({
  selector: 'jhi-taluka-master-update',
  templateUrl: './taluka-master-update.component.html',
})
export class TalukaMasterUpdateComponent implements OnInit {
  isSaving = false;
  talukaMaster: ITalukaMaster | null = null;

  editForm: TalukaMasterFormGroup = this.talukaMasterFormService.createTalukaMasterFormGroup();

  constructor(
    protected talukaMasterService: TalukaMasterService,
    protected talukaMasterFormService: TalukaMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ talukaMaster }) => {
      this.talukaMaster = talukaMaster;
      if (talukaMaster) {
        this.updateForm(talukaMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const talukaMaster = this.talukaMasterFormService.getTalukaMaster(this.editForm);
    if (talukaMaster.id !== null) {
      this.subscribeToSaveResponse(this.talukaMasterService.update(talukaMaster));
    } else {
      this.subscribeToSaveResponse(this.talukaMasterService.create(talukaMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITalukaMaster>>): void {
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

  protected updateForm(talukaMaster: ITalukaMaster): void {
    this.talukaMaster = talukaMaster;
    this.talukaMasterFormService.resetForm(this.editForm, talukaMaster);
  }
}
