import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankMaster } from '../bank-master.model';

@Component({
  selector: 'jhi-bank-master-detail',
  templateUrl: './bank-master-detail.component.html',
})
export class BankMasterDetailComponent implements OnInit {
  bankMaster: IBankMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankMaster }) => {
      this.bankMaster = bankMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
