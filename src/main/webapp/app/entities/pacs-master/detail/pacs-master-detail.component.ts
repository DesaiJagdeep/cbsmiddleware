import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPacsMaster } from '../pacs-master.model';

@Component({
  selector: 'jhi-pacs-master-detail',
  templateUrl: './pacs-master-detail.component.html',
})
export class PacsMasterDetailComponent implements OnInit {
  pacsMaster: IPacsMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pacsMaster }) => {
      this.pacsMaster = pacsMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
