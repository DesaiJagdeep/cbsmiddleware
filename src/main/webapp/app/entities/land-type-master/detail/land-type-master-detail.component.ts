import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILandTypeMaster } from '../land-type-master.model';

@Component({
  selector: 'jhi-land-type-master-detail',
  templateUrl: './land-type-master-detail.component.html',
})
export class LandTypeMasterDetailComponent implements OnInit {
  landTypeMaster: ILandTypeMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ landTypeMaster }) => {
      this.landTypeMaster = landTypeMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
