import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICropRateMaster } from '../crop-rate-master.model';

@Component({
  selector: 'jhi-crop-rate-master-detail',
  templateUrl: './crop-rate-master-detail.component.html',
})
export class CropRateMasterDetailComponent implements OnInit {
  cropRateMaster: ICropRateMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cropRateMaster }) => {
      this.cropRateMaster = cropRateMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
