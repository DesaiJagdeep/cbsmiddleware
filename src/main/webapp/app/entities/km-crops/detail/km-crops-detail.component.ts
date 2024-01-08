import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKmCrops } from '../km-crops.model';

@Component({
  selector: 'jhi-km-crops-detail',
  templateUrl: './km-crops-detail.component.html',
})
export class KmCropsDetailComponent implements OnInit {
  kmCrops: IKmCrops | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmCrops }) => {
      this.kmCrops = kmCrops;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
