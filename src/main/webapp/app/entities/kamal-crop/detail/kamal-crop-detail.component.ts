import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKamalCrop } from '../kamal-crop.model';

@Component({
  selector: 'jhi-kamal-crop-detail',
  templateUrl: './kamal-crop-detail.component.html',
})
export class KamalCropDetailComponent implements OnInit {
  kamalCrop: IKamalCrop | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kamalCrop }) => {
      this.kamalCrop = kamalCrop;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
