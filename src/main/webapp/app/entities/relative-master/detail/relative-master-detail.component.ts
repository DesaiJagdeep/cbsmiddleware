import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelativeMaster } from '../relative-master.model';

@Component({
  selector: 'jhi-relative-master-detail',
  templateUrl: './relative-master-detail.component.html',
})
export class RelativeMasterDetailComponent implements OnInit {
  relativeMaster: IRelativeMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relativeMaster }) => {
      this.relativeMaster = relativeMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
