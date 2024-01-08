import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFactoryMaster } from '../factory-master.model';

@Component({
  selector: 'jhi-factory-master-detail',
  templateUrl: './factory-master-detail.component.html',
})
export class FactoryMasterDetailComponent implements OnInit {
  factoryMaster: IFactoryMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factoryMaster }) => {
      this.factoryMaster = factoryMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
