import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIsCalculateTemp } from '../is-calculate-temp.model';

@Component({
  selector: 'jhi-is-calculate-temp-detail',
  templateUrl: './is-calculate-temp-detail.component.html',
})
export class IsCalculateTempDetailComponent implements OnInit {
  isCalculateTemp: IIsCalculateTemp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ isCalculateTemp }) => {
      this.isCalculateTemp = isCalculateTemp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
