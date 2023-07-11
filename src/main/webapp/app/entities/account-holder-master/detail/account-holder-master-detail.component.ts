import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountHolderMaster } from '../account-holder-master.model';

@Component({
  selector: 'jhi-account-holder-master-detail',
  templateUrl: './account-holder-master-detail.component.html',
})
export class AccountHolderMasterDetailComponent implements OnInit {
  accountHolderMaster: IAccountHolderMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountHolderMaster }) => {
      this.accountHolderMaster = accountHolderMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
