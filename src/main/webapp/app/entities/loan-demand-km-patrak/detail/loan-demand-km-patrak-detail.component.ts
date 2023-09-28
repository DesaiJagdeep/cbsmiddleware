import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanDemandKMPatrak } from '../loan-demand-km-patrak.model';

@Component({
  selector: 'jhi-loan-demand-km-patrak-detail',
  templateUrl: './loan-demand-km-patrak-detail.component.html',
})
export class LoanDemandKMPatrakDetailComponent implements OnInit {
  loanDemandKMPatrak: ILoanDemandKMPatrak | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDemandKMPatrak }) => {
      this.loanDemandKMPatrak = loanDemandKMPatrak;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
