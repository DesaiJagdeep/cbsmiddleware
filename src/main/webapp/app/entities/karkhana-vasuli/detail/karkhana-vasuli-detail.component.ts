import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKarkhanaVasuli } from '../karkhana-vasuli.model';

@Component({
  selector: 'jhi-karkhana-vasuli-detail',
  templateUrl: './karkhana-vasuli-detail.component.html',
})
export class KarkhanaVasuliDetailComponent implements OnInit {
  karkhanaVasuli: IKarkhanaVasuli | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ karkhanaVasuli }) => {
      this.karkhanaVasuli = karkhanaVasuli;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
