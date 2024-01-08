import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKmDetails } from '../km-details.model';

@Component({
  selector: 'jhi-km-details-detail',
  templateUrl: './km-details-detail.component.html',
})
export class KmDetailsDetailComponent implements OnInit {
  kmDetails: IKmDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmDetails }) => {
      this.kmDetails = kmDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
