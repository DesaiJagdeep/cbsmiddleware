import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDesignationMaster } from '../designation-master.model';

@Component({
  selector: 'jhi-designation-master-detail',
  templateUrl: './designation-master-detail.component.html',
})
export class DesignationMasterDetailComponent implements OnInit {
  designationMaster: IDesignationMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ designationMaster }) => {
      this.designationMaster = designationMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
