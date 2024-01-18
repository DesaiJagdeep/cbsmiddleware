import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKmMaster } from '../km-master.model';

@Component({
  selector: 'jhi-km-master-detail',
  templateUrl: './km-master-detail.component.html',
})
export class KmMasterDetailComponent implements OnInit {
  kmMaster: IKmMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmMaster }) => {
      this.kmMaster = kmMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
