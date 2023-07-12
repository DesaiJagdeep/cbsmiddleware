import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ApplicationFormService, ApplicationFormGroup } from './application-form.service';
import { IApplication } from '../application.model';
import { ApplicationService } from '../service/application.service';
import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';
import { IssFileParserService } from 'app/entities/iss-file-parser/service/iss-file-parser.service';

@Component({
  selector: 'jhi-application-update',
  templateUrl: './application-update.component.html',
})
export class ApplicationUpdateComponent implements OnInit {
  isSaving = false;
  application: IApplication | null = null;

  issFileParsersSharedCollection: IIssFileParser[] = [];

  editForm: ApplicationFormGroup = this.applicationFormService.createApplicationFormGroup();

  constructor(
    protected applicationService: ApplicationService,
    protected applicationFormService: ApplicationFormService,
    protected issFileParserService: IssFileParserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareIssFileParser = (o1: IIssFileParser | null, o2: IIssFileParser | null): boolean =>
    this.issFileParserService.compareIssFileParser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ application }) => {
      this.application = application;
      if (application) {
        this.updateForm(application);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const application = this.applicationFormService.getApplication(this.editForm);
    if (application.id !== null) {
      this.subscribeToSaveResponse(this.applicationService.update(application));
    } else {
      this.subscribeToSaveResponse(this.applicationService.create(application));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplication>>): void {
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

  protected updateForm(application: IApplication): void {
    this.application = application;
    this.applicationFormService.resetForm(this.editForm, application);

    this.issFileParsersSharedCollection = this.issFileParserService.addIssFileParserToCollectionIfMissing<IIssFileParser>(
      this.issFileParsersSharedCollection,
      application.issFileParser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.issFileParserService
      .query()
      .pipe(map((res: HttpResponse<IIssFileParser[]>) => res.body ?? []))
      .pipe(
        map((issFileParsers: IIssFileParser[]) =>
          this.issFileParserService.addIssFileParserToCollectionIfMissing<IIssFileParser>(issFileParsers, this.application?.issFileParser)
        )
      )
      .subscribe((issFileParsers: IIssFileParser[]) => (this.issFileParsersSharedCollection = issFileParsers));
  }
}
