import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationLogHistory } from '../application-log-history.model';

@Component({
  selector: 'jhi-application-log-history-detail',
  templateUrl: './application-log-history-detail.component.html',
})
export class ApplicationLogHistoryDetailComponent implements OnInit {
  applicationLogHistory: IApplicationLogHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationLogHistory }) => {
      this.applicationLogHistory = applicationLogHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
