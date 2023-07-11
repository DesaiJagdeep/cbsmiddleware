import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFarmerCategoryMaster } from '../farmer-category-master.model';

@Component({
  selector: 'jhi-farmer-category-master-detail',
  templateUrl: './farmer-category-master-detail.component.html',
})
export class FarmerCategoryMasterDetailComponent implements OnInit {
  farmerCategoryMaster: IFarmerCategoryMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ farmerCategoryMaster }) => {
      this.farmerCategoryMaster = farmerCategoryMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
