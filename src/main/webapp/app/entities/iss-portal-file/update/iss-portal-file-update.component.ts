import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IssPortalFileFormService, IssPortalFileFormGroup } from './iss-portal-file-form.service';
import { IIssPortalFile } from '../iss-portal-file.model';
import { IssPortalFileService } from '../service/iss-portal-file.service';

@Component({
  selector: 'jhi-iss-portal-file-update',
  templateUrl: './iss-portal-file-update.component.html',
})
export class IssPortalFileUpdateComponent implements OnInit {
  isSaving = false;
  issPortalFile: IIssPortalFile | null = null;

  editForm: IssPortalFileFormGroup = this.issPortalFileFormService.createIssPortalFileFormGroup();

  constructor(
    protected issPortalFileService: IssPortalFileService,
    protected issPortalFileFormService: IssPortalFileFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issPortalFile }) => {
      this.issPortalFile = issPortalFile;
      if (issPortalFile) {
        this.updateForm(issPortalFile);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issPortalFile = this.issPortalFileFormService.getIssPortalFile(this.editForm);
    if (issPortalFile.id !== null) {
      this.subscribeToSaveResponse(this.issPortalFileService.update(issPortalFile));
    } else {
      this.subscribeToSaveResponse(this.issPortalFileService.create(issPortalFile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssPortalFile>>): void {
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

  protected updateForm(issPortalFile: IIssPortalFile): void {
    this.issPortalFile = issPortalFile;
    this.issPortalFileFormService.resetForm(this.editForm, issPortalFile);
  }
}
