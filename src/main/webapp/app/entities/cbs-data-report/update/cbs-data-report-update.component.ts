import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CbsDataReportFormService, CbsDataReportFormGroup } from './cbs-data-report-form.service';
import { ICbsDataReport } from '../cbs-data-report.model';
import { CbsDataReportService } from '../service/cbs-data-report.service';

@Component({
  selector: 'jhi-cbs-data-report-update',
  templateUrl: './cbs-data-report-update.component.html',
})
export class CbsDataReportUpdateComponent implements OnInit {
  isSaving = false;
  cbsDataReport: ICbsDataReport | null = null;

  editForm: CbsDataReportFormGroup = this.cbsDataReportFormService.createCbsDataReportFormGroup();

  constructor(
    protected cbsDataReportService: CbsDataReportService,
    protected cbsDataReportFormService: CbsDataReportFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbsDataReport }) => {
      this.cbsDataReport = cbsDataReport;
      if (cbsDataReport) {
        this.updateForm(cbsDataReport);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbsDataReport = this.cbsDataReportFormService.getCbsDataReport(this.editForm);
    if (cbsDataReport.id !== null) {
      this.subscribeToSaveResponse(this.cbsDataReportService.update(cbsDataReport));
    } else {
      this.subscribeToSaveResponse(this.cbsDataReportService.create(cbsDataReport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbsDataReport>>): void {
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

  protected updateForm(cbsDataReport: ICbsDataReport): void {
    this.cbsDataReport = cbsDataReport;
    this.cbsDataReportFormService.resetForm(this.editForm, cbsDataReport);
  }
}
