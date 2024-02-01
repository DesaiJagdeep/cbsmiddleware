import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKamalSociety } from '../kamal-society.model';

@Component({
  selector: 'jhi-kamal-society-detail',
  templateUrl: './kamal-society-detail.component.html',
})
export class KamalSocietyDetailComponent implements OnInit {
  kamalSociety: IKamalSociety | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kamalSociety }) => {
      this.kamalSociety = kamalSociety;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
