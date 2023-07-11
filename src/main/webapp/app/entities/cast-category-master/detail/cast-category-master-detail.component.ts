import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICastCategoryMaster } from '../cast-category-master.model';

@Component({
  selector: 'jhi-cast-category-master-detail',
  templateUrl: './cast-category-master-detail.component.html',
})
export class CastCategoryMasterDetailComponent implements OnInit {
  castCategoryMaster: ICastCategoryMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ castCategoryMaster }) => {
      this.castCategoryMaster = castCategoryMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
