import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistrictMaster } from '../district-master.model';

@Component({
  selector: 'jhi-district-master-detail',
  templateUrl: './district-master-detail.component.html',
})
export class DistrictMasterDetailComponent implements OnInit {
  districtMaster: IDistrictMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districtMaster }) => {
      this.districtMaster = districtMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
