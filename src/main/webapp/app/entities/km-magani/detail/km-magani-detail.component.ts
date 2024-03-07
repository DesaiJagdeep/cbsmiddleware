import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKmMagani } from '../km-magani.model';

@Component({
  selector: 'jhi-km-magani-detail',
  templateUrl: './km-magani-detail.component.html',
})
export class KmMaganiDetailComponent implements OnInit {
  kmMagani: IKmMagani | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmMagani }) => {
      this.kmMagani = kmMagani;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
