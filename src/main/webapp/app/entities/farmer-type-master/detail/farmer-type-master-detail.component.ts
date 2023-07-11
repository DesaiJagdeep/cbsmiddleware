import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFarmerTypeMaster } from '../farmer-type-master.model';

@Component({
  selector: 'jhi-farmer-type-master-detail',
  templateUrl: './farmer-type-master-detail.component.html',
})
export class FarmerTypeMasterDetailComponent implements OnInit {
  farmerTypeMaster: IFarmerTypeMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ farmerTypeMaster }) => {
      this.farmerTypeMaster = farmerTypeMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
