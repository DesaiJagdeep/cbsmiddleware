import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IssFileParserFormService, IssFileParserFormGroup } from './iss-file-parser-form.service';
import { IIssFileParser } from '../iss-file-parser.model';
import { IssFileParserService } from '../service/iss-file-parser.service';
import { IIssPortalFile } from 'app/entities/iss-portal-file/iss-portal-file.model';
import { IssPortalFileService } from 'app/entities/iss-portal-file/service/iss-portal-file.service';

@Component({
  selector: 'jhi-iss-file-parser-update',
  templateUrl: './iss-file-parser-update.component.html',
})
export class IssFileParserUpdateComponent implements OnInit {
  isSaving = false;
  issFileParser: IIssFileParser | null = null;

  issPortalFilesSharedCollection: IIssPortalFile[] = [];

  editForm: IssFileParserFormGroup = this.issFileParserFormService.createIssFileParserFormGroup();

  constructor(
    protected issFileParserService: IssFileParserService,
    protected issFileParserFormService: IssFileParserFormService,
    protected issPortalFileService: IssPortalFileService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareIssPortalFile = (o1: IIssPortalFile | null, o2: IIssPortalFile | null): boolean =>
    this.issPortalFileService.compareIssPortalFile(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issFileParser }) => {
      this.issFileParser = issFileParser;
      if (issFileParser) {
        this.updateForm(issFileParser);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issFileParser = this.issFileParserFormService.getIssFileParser(this.editForm);
    if (issFileParser.id !== null) {
      this.subscribeToSaveResponse(this.issFileParserService.update(issFileParser));
    } else {
      this.subscribeToSaveResponse(this.issFileParserService.create(issFileParser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssFileParser>>): void {
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

  protected updateForm(issFileParser: IIssFileParser): void {
    this.issFileParser = issFileParser;
    this.issFileParserFormService.resetForm(this.editForm, issFileParser);

    this.issPortalFilesSharedCollection = this.issPortalFileService.addIssPortalFileToCollectionIfMissing<IIssPortalFile>(
      this.issPortalFilesSharedCollection,
      issFileParser.issPortalFile
    );
  }

  protected loadRelationshipsOptions(): void {
    this.issPortalFileService
      .query()
      .pipe(map((res: HttpResponse<IIssPortalFile[]>) => res.body ?? []))
      .pipe(
        map((issPortalFiles: IIssPortalFile[]) =>
          this.issPortalFileService.addIssPortalFileToCollectionIfMissing<IIssPortalFile>(issPortalFiles, this.issFileParser?.issPortalFile)
        )
      )
      .subscribe((issPortalFiles: IIssPortalFile[]) => (this.issPortalFilesSharedCollection = issPortalFiles));
  }
}
