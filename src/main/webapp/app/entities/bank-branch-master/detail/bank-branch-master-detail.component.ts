import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankBranchMaster } from '../bank-branch-master.model';

@Component({
  selector: 'jhi-bank-branch-master-detail',
  templateUrl: './bank-branch-master-detail.component.html',
})
export class BankBranchMasterDetailComponent implements OnInit {
  bankBranchMaster: IBankBranchMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankBranchMaster }) => {
      this.bankBranchMaster = bankBranchMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
