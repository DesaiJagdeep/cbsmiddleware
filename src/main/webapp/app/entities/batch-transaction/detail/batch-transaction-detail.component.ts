import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBatchTransaction } from '../batch-transaction.model';

@Component({
  selector: 'jhi-batch-transaction-detail',
  templateUrl: './batch-transaction-detail.component.html',
})
export class BatchTransactionDetailComponent implements OnInit {
  batchTransaction: IBatchTransaction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ batchTransaction }) => {
      this.batchTransaction = batchTransaction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
