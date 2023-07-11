import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOccupationMaster } from '../occupation-master.model';

@Component({
  selector: 'jhi-occupation-master-detail',
  templateUrl: './occupation-master-detail.component.html',
})
export class OccupationMasterDetailComponent implements OnInit {
  occupationMaster: IOccupationMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ occupationMaster }) => {
      this.occupationMaster = occupationMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
