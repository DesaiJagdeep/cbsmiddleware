import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICropCategoryMaster } from '../crop-category-master.model';

@Component({
  selector: 'jhi-crop-category-master-detail',
  templateUrl: './crop-category-master-detail.component.html',
})
export class CropCategoryMasterDetailComponent implements OnInit {
  cropCategoryMaster: ICropCategoryMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cropCategoryMaster }) => {
      this.cropCategoryMaster = cropCategoryMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
