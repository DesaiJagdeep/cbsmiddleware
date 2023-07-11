import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICropMaster } from '../crop-master.model';

@Component({
  selector: 'jhi-crop-master-detail',
  templateUrl: './crop-master-detail.component.html',
})
export class CropMasterDetailComponent implements OnInit {
  cropMaster: ICropMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cropMaster }) => {
      this.cropMaster = cropMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
