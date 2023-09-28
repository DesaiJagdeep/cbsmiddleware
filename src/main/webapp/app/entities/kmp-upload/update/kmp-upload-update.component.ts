import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { KMPUploadFormService, KMPUploadFormGroup } from './kmp-upload-form.service';
import { IKMPUpload } from '../kmp-upload.model';
import { KMPUploadService } from '../service/kmp-upload.service';

@Component({
  selector: 'jhi-kmp-upload-update',
  templateUrl: './kmp-upload-update.component.html',
})
export class KMPUploadUpdateComponent implements OnInit {
  isSaving = false;
  kMPUpload: IKMPUpload | null = null;

  editForm: KMPUploadFormGroup = this.kMPUploadFormService.createKMPUploadFormGroup();

  constructor(
    protected kMPUploadService: KMPUploadService,
    protected kMPUploadFormService: KMPUploadFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kMPUpload }) => {
      this.kMPUpload = kMPUpload;
      if (kMPUpload) {
        this.updateForm(kMPUpload);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kMPUpload = this.kMPUploadFormService.getKMPUpload(this.editForm);
    if (kMPUpload.id !== null) {
      this.subscribeToSaveResponse(this.kMPUploadService.update(kMPUpload));
    } else {
      this.subscribeToSaveResponse(this.kMPUploadService.create(kMPUpload));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKMPUpload>>): void {
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

  protected updateForm(kMPUpload: IKMPUpload): void {
    this.kMPUpload = kMPUpload;
    this.kMPUploadFormService.resetForm(this.editForm, kMPUpload);
  }
}
