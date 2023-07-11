import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStateMaster } from '../state-master.model';

@Component({
  selector: 'jhi-state-master-detail',
  templateUrl: './state-master-detail.component.html',
})
export class StateMasterDetailComponent implements OnInit {
  stateMaster: IStateMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stateMaster }) => {
      this.stateMaster = stateMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
