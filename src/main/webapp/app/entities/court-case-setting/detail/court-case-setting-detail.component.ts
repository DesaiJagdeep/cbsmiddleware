import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourtCaseSetting } from '../court-case-setting.model';

@Component({
  selector: 'jhi-court-case-setting-detail',
  templateUrl: './court-case-setting-detail.component.html',
})
export class CourtCaseSettingDetailComponent implements OnInit {
  courtCaseSetting: ICourtCaseSetting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courtCaseSetting }) => {
      this.courtCaseSetting = courtCaseSetting;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
