import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeasonMaster } from '../season-master.model';

@Component({
  selector: 'jhi-season-master-detail',
  templateUrl: './season-master-detail.component.html',
})
export class SeasonMasterDetailComponent implements OnInit {
  seasonMaster: ISeasonMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seasonMaster }) => {
      this.seasonMaster = seasonMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
