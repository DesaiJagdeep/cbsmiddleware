import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';

@Component({
  selector: 'jhi-kamal-maryada-patrak-detail',
  templateUrl: './kamal-maryada-patrak-detail.component.html',
})
export class KamalMaryadaPatrakDetailComponent implements OnInit {
  kamalMaryadaPatrak: IKamalMaryadaPatrak | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kamalMaryadaPatrak }) => {
      this.kamalMaryadaPatrak = kamalMaryadaPatrak;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
