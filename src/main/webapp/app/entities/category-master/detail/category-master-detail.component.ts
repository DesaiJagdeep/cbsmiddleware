import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoryMaster } from '../category-master.model';

@Component({
  selector: 'jhi-category-master-detail',
  templateUrl: './category-master-detail.component.html',
})
export class CategoryMasterDetailComponent implements OnInit {
  categoryMaster: ICategoryMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryMaster }) => {
      this.categoryMaster = categoryMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
