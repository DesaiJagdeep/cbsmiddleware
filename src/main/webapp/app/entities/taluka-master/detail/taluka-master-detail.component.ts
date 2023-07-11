import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITalukaMaster } from '../taluka-master.model';

@Component({
  selector: 'jhi-taluka-master-detail',
  templateUrl: './taluka-master-detail.component.html',
})
export class TalukaMasterDetailComponent implements OnInit {
  talukaMaster: ITalukaMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ talukaMaster }) => {
      this.talukaMaster = talukaMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
