import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourtCaseDetails } from '../court-case-details.model';

@Component({
  selector: 'jhi-court-case-details-detail',
  templateUrl: './court-case-details-detail.component.html',
})
export class CourtCaseDetailsDetailComponent implements OnInit {
  courtCaseDetails: ICourtCaseDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courtCaseDetails }) => {
      this.courtCaseDetails = courtCaseDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
