import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ApplicationLogHistoryFormService, ApplicationLogHistoryFormGroup } from './application-log-history-form.service';
import { IApplicationLogHistory } from '../application-log-history.model';
import { ApplicationLogHistoryService } from '../service/application-log-history.service';
import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';
import { IssFileParserService } from 'app/entities/iss-file-parser/service/iss-file-parser.service';

@Component({
  selector: 'jhi-application-log-history-update',
  templateUrl: './application-log-history-update.component.html',
})
export class ApplicationLogHistoryUpdateComponent implements OnInit {
  isSaving = false;
  applicationLogHistory: IApplicationLogHistory | null = null;

  issFileParsersSharedCollection: IIssFileParser[] = [];

  editForm: ApplicationLogHistoryFormGroup = this.applicationLogHistoryFormService.createApplicationLogHistoryFormGroup();

  constructor(
    protected applicationLogHistoryService: ApplicationLogHistoryService,
    protected applicationLogHistoryFormService: ApplicationLogHistoryFormService,
    protected issFileParserService: IssFileParserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareIssFileParser = (o1: IIssFileParser | null, o2: IIssFileParser | null): boolean =>
    this.issFileParserService.compareIssFileParser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationLogHistory }) => {
      this.applicationLogHistory = applicationLogHistory;
      if (applicationLogHistory) {
        this.updateForm(applicationLogHistory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicationLogHistory = this.applicationLogHistoryFormService.getApplicationLogHistory(this.editForm);
    if (applicationLogHistory.id !== null) {
      this.subscribeToSaveResponse(this.applicationLogHistoryService.update(applicationLogHistory));
    } else {
      this.subscribeToSaveResponse(this.applicationLogHistoryService.create(applicationLogHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationLogHistory>>): void {
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

  protected updateForm(applicationLogHistory: IApplicationLogHistory): void {
    this.applicationLogHistory = applicationLogHistory;
    this.applicationLogHistoryFormService.resetForm(this.editForm, applicationLogHistory);

    this.issFileParsersSharedCollection = this.issFileParserService.addIssFileParserToCollectionIfMissing<IIssFileParser>(
      this.issFileParsersSharedCollection,
      applicationLogHistory.issFileParser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.issFileParserService
      .query()
      .pipe(map((res: HttpResponse<IIssFileParser[]>) => res.body ?? []))
      .pipe(
        map((issFileParsers: IIssFileParser[]) =>
          this.issFileParserService.addIssFileParserToCollectionIfMissing<IIssFileParser>(
            issFileParsers,
            this.applicationLogHistory?.issFileParser
          )
        )
      )
      .subscribe((issFileParsers: IIssFileParser[]) => (this.issFileParsersSharedCollection = issFileParsers));
  }
}
