import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IKmMaster } from '../km-master.model';
import { KmMasterService } from '../service/km-master.service';
import { KmMasterFormService, KmMasterFormGroup } from './km-master-form.service';

@Component({
  standalone: true,
  selector: 'jhi-km-master-update',
  templateUrl: './km-master-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class KmMasterUpdateComponent implements OnInit {
  isSaving = false;
  kmMaster: IKmMaster | null = null;

  editForm: KmMasterFormGroup = this.kmMasterFormService.createKmMasterFormGroup();

  constructor(
    protected kmMasterService: KmMasterService,
    protected kmMasterFormService: KmMasterFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmMaster }) => {
      this.kmMaster = kmMaster;
      if (kmMaster) {
        this.updateForm(kmMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kmMaster = this.kmMasterFormService.getKmMaster(this.editForm);
    if (kmMaster.id !== null) {
      this.subscribeToSaveResponse(this.kmMasterService.update(kmMaster));
    } else {
      this.subscribeToSaveResponse(this.kmMasterService.create(kmMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKmMaster>>): void {
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

  protected updateForm(kmMaster: IKmMaster): void {
    this.kmMaster = kmMaster;
    this.kmMasterFormService.resetForm(this.editForm, kmMaster);
  }
}
