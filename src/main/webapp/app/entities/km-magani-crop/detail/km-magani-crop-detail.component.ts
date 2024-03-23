import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKmMaganiCrop } from '../km-magani-crop.model';

@Component({
  selector: 'jhi-km-magani-crop-detail',
  templateUrl: './km-magani-crop-detail.component.html',
})
export class KmMaganiCropDetailComponent implements OnInit {
  kmMaganiCrop: IKmMaganiCrop | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmMaganiCrop }) => {
      this.kmMaganiCrop = kmMaganiCrop;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
