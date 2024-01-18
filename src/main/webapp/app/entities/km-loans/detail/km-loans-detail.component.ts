import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKmLoans } from '../km-loans.model';

@Component({
  selector: 'jhi-km-loans-detail',
  templateUrl: './km-loans-detail.component.html',
})
export class KmLoansDetailComponent implements OnInit {
  kmLoans: IKmLoans | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmLoans }) => {
      this.kmLoans = kmLoans;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
