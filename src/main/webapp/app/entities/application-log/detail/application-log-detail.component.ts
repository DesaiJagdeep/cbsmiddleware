import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationLog } from '../application-log.model';

@Component({
  selector: 'jhi-application-log-detail',
  templateUrl: './application-log-detail.component.html',
})
export class ApplicationLogDetailComponent implements OnInit {
  applicationLog: IApplicationLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationLog }) => {
      this.applicationLog = applicationLog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
