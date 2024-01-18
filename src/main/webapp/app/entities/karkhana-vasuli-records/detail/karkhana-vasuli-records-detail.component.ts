import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';

@Component({
  selector: 'jhi-karkhana-vasuli-records-detail',
  templateUrl: './karkhana-vasuli-records-detail.component.html',
})
export class KarkhanaVasuliRecordsDetailComponent implements OnInit {
  karkhanaVasuliRecords: IKarkhanaVasuliRecords | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ karkhanaVasuliRecords }) => {
      this.karkhanaVasuliRecords = karkhanaVasuliRecords;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
