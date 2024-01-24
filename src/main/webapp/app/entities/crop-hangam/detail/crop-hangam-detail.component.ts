import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICropHangam } from '../crop-hangam.model';

@Component({
  selector: 'jhi-crop-hangam-detail',
  templateUrl: './crop-hangam-detail.component.html',
})
export class CropHangamDetailComponent implements OnInit {
  cropHangam: ICropHangam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cropHangam }) => {
      this.cropHangam = cropHangam;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
