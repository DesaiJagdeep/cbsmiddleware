import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICbsDataReport } from '../cbs-data-report.model';

@Component({
  selector: 'jhi-cbs-data-report-detail',
  templateUrl: './cbs-data-report-detail.component.html',
})
export class CbsDataReportDetailComponent implements OnInit {
  cbsDataReport: ICbsDataReport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbsDataReport }) => {
      this.cbsDataReport = cbsDataReport;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
