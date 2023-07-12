import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ApplicationLogFormService, ApplicationLogFormGroup } from './application-log-form.service';
import { IApplicationLog } from '../application-log.model';
import { ApplicationLogService } from '../service/application-log.service';
import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';
import { IssFileParserService } from 'app/entities/iss-file-parser/service/iss-file-parser.service';

@Component({
  selector: 'jhi-application-log-update',
  templateUrl: './application-log-update.component.html',
})
export class ApplicationLogUpdateComponent implements OnInit {
  isSaving = false;
  applicationLog: IApplicationLog | null = null;

  issFileParsersSharedCollection: IIssFileParser[] = [];

  editForm: ApplicationLogFormGroup = this.applicationLogFormService.createApplicationLogFormGroup();

  constructor(
    protected applicationLogService: ApplicationLogService,
    protected applicationLogFormService: ApplicationLogFormService,
    protected issFileParserService: IssFileParserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareIssFileParser = (o1: IIssFileParser | null, o2: IIssFileParser | null): boolean =>
    this.issFileParserService.compareIssFileParser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationLog }) => {
      this.applicationLog = applicationLog;
      if (applicationLog) {
        this.updateForm(applicationLog);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicationLog = this.applicationLogFormService.getApplicationLog(this.editForm);
    if (applicationLog.id !== null) {
      this.subscribeToSaveResponse(this.applicationLogService.update(applicationLog));
    } else {
      this.subscribeToSaveResponse(this.applicationLogService.create(applicationLog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationLog>>): void {
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

  protected updateForm(applicationLog: IApplicationLog): void {
    this.applicationLog = applicationLog;
    this.applicationLogFormService.resetForm(this.editForm, applicationLog);

    this.issFileParsersSharedCollection = this.issFileParserService.addIssFileParserToCollectionIfMissing<IIssFileParser>(
      this.issFileParsersSharedCollection,
      applicationLog.issFileParser
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
            this.applicationLog?.issFileParser
          )
        )
      )
      .subscribe((issFileParsers: IIssFileParser[]) => (this.issFileParsersSharedCollection = issFileParsers));
  }
}
